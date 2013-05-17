package it.mate.econyx.server.services.impl;

import it.mate.econyx.client.factories.DefaultCustomClientFactory;
import it.mate.econyx.server.services.CustomerAdapter;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.services.InitAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.services.PortalDataExporter;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
import it.mate.econyx.server.tasks.GenerateOperationTask;
import it.mate.econyx.server.tasks.PortalDataInitializeDeferredTask;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.CacheDumpEntry;
import it.mate.econyx.shared.model.impl.PortalUserTx;
import it.mate.econyx.shared.services.GeneralService;
import it.mate.econyx.shared.services.PropertiesConstants;
import it.mate.econyx.shared.util.PropertyConstants;
import it.mate.gwtcommons.client.factories.AbstractCustomClientFactory;
import it.mate.gwtcommons.server.utils.HttpUtils;
import it.mate.gwtcommons.server.utils.LoggingUtils;
import it.mate.gwtcommons.server.utils.PropertiesHolderConfigurer;
import it.mate.gwtcommons.server.utils.SpringUtils;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;
import it.mate.portlets.server.services.PortalServiceAdapter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class GeneralServiceImpl extends RemoteServiceServlet implements GeneralService {

  private static Logger logger = Logger.getLogger(GeneralServiceImpl.class);

  private InitAdapter initAdapter;

  private PortalPageAdapter portalPageAdapter;

  private PortalDataExporter portalDataMarshaller;

  private GeneralAdapter generalAdapter;

  private PortalUserAdapter portalUserAdapter;

  private CustomerAdapter customerAdapter;

  private OrderAdapter orderAdapter;
  
  private PortalServiceAdapter portalServiceAdapter;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.initAdapter = AdaptersUtil.getInitAdapter();
    this.portalPageAdapter = AdaptersUtil.getPortalPageAdapter();
    this.portalDataMarshaller = AdaptersUtil.getPortalDataMarshaller();
    this.generalAdapter = AdaptersUtil.getGeneralAdapter();
    this.portalUserAdapter = AdaptersUtil.getPortalUserAdapter();
    this.customerAdapter = AdaptersUtil.getCustomerAdapter();
    this.orderAdapter = AdaptersUtil.getOrderAdapter();
    this.portalServiceAdapter = AdaptersUtil.getPortalServiceAdapter();
    logger.debug("initialized " + this);
  }

  public AbstractCustomClientFactory getCustomClientFactory() {
    try {
      List<Class<? extends AbstractCustomClientFactory>> customClientFactoryClasses = SpringUtils.scanPackageClasses("it.mate.econyx.client.factories",
          AbstractCustomClientFactory.class);
      if (customClientFactoryClasses != null && customClientFactoryClasses.size() > 0) {
        for (Class<? extends AbstractCustomClientFactory> customClientFactoryClass : customClientFactoryClasses) {
          if (customClientFactoryClasses.size() > 1 && customClientFactoryClass != DefaultCustomClientFactory.class) {
            return customClientFactoryClass.newInstance();
          } else {
            return customClientFactoryClass.newInstance();
          }
        }
      }
    } catch (Exception ex) {
      logger.error("error", ex);
    }
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Map<String, String> getPropertiesFromServer() {
    Map<String, String> results = new HashMap<String, String>();

    PropertiesHolderConfigurer.reloadFromClassPathResource("econyx.properties");
    
    boolean devMode = (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development);
    ((Map<Object, Object>)PropertiesHolder.getProperties()).put(PropertiesConstants.SYSTEM_ENVIRONMENT_DEVELOPMENT_MODE, ""+devMode);
    
    Map<? extends Object, ? extends Object> properties = PropertiesHolder.getProperties();
    for (Object key : properties.keySet()) {
      String value = (String) properties.get(key);
      results.put((String) key, value);
    }
    
    return results;
  }

  public void loadFoldersData() {
    initAdapter.loadFoldersData();
  }

  public void exportFoldersData() {

  }

  public void initPagesData() {
    initAdapter.loadPagesData();
  }

  public void loadPagesData() {
    portalPageAdapter.loadFromXml();
  }

  public void exportPagesData() {
    portalPageAdapter.exportToXml();
  }

  @Override
  public void importPortalData() {
    Queue queue = QueueFactory.getDefaultQueue();
    // 12/10/2012
    // queue.add(TaskOptions.Builder.withUrl("/exec.cron").param("op",
    // "importPortalData").header("X-AppEngine-Cron", "true"));
    queue.add(TaskOptions.Builder.withPayload(new PortalDataInitializeDeferredTask()));
  }

  @Override
  public void exportPortalData() {
    portalDataMarshaller.unload();
  }

  @Override
  public void clearCache() {
    generalAdapter.clearCache();
    portalServiceAdapter.clearCache();
  }

  @Override
  public void storePortalSessionState(PortalSessionState state) {

    if (state.getModuleType() == PortalSessionState.MODULE_ADMIN && state.getLoggedUser() == null) {
      PortalUser adminPortalUser = null;
      try {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
          adminPortalUser = portalUserAdapter.findByEmail(user.getEmail());
        }
      } catch (Exception ex) {
        logger.error("error", ex);
      } finally {
        /* 28/11/2012
        if (adminPortalUser == null) {
          adminPortalUser = new PortalUserTx();
          adminPortalUser.setScreenName("NOT FOUND PORTAL USER!");
        }
        */
        state.setLoggedUser(adminPortalUser);
      }
    }

    generalAdapter.storePortalSessionState(getThreadLocalRequest(), state);
  }

  @Override
  public PortalSessionState retrievePortalSessionState(int moduleType) {
    PortalSessionState portalSessionState = generalAdapter.retrievePortalSessionState(getThreadLocalRequest());

    if (portalSessionState == null || portalSessionState.getModuleType() != moduleType) {
      portalSessionState = new PortalSessionState(moduleType);
    }

    if (moduleType == PortalSessionState.MODULE_SITE) {

      boolean userVerified = false;

      portalSessionState.setGoogleAuthentication(false);
      
      if (!userVerified) {
        if (getThreadLocalRequest().getCookies() != null) {
          for (Cookie cookie : getThreadLocalRequest().getCookies()) {
            if (cookie.getName().equals(GeneralService.PORTAL_USER_COOKIE)) {
              if (cookie.getValue() != null && cookie.getValue().length() > 0) {
                StringTokenizer cookieTokens = new StringTokenizer(cookie.getValue(), "|");
                if (cookieTokens.countTokens() == 2) {
                  PortalUser cookiePortalUser = new PortalUserTx();
                  cookiePortalUser.setEmailAddress(cookieTokens.nextToken());
                  cookiePortalUser.setPassword(cookieTokens.nextToken());
                  cookiePortalUser.setPasswordEncrypted(true);
                  try {
                    cookiePortalUser = portalUserAdapter.login(cookiePortalUser);
                    logger.debug("found portalUserCookie = " + cookiePortalUser);
                  } catch (ServiceException ex) {
                    cookiePortalUser = null;
                    portalSessionState.setCustomer(null);
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    getThreadLocalResponse().addCookie(cookie);
                  }
                  portalSessionState.setLoggedUser(cookiePortalUser);
                  if (cookiePortalUser != null && cookiePortalUser.getId() != null) {
                    // 22/11/2012
                    // fa scattare la find sotto
                    portalSessionState.setCustomer(null);
                    /*
                     * Customer customer =
                     * customerAdapter.findByPortalUser(cookiePortalUser);
                     * portalSessionState.setCustomer(customer); if (customer !=
                     * null) portalSessionState.setOpenOrder(orderAdapter.
                     * findOpenOrderByCustomer(customer));
                     */
                    userVerified = true;
                  }
                }
              }
              break;
            }
          }
        }
      }

      if (!userVerified) {
        if (PropertiesHolder.getBoolean(PropertyConstants.SITE_GOOGLE_ACCOUNT_AUTHENTICATION_ENABLED, false)) {
          UserService userService = UserServiceFactory.getUserService();
          User googleUser = userService.getCurrentUser();
          if (googleUser != null) {
            PortalUser googlePortalUser = portalUserAdapter.findByEmail(googleUser.getEmail());
            if (googlePortalUser == null) {
              throw new ServiceException(
        "Attenzione: sei autenticato con un account Google che non è abilitato ad accedere a questo sito. Prova ad autenticarti con un altro account Google abilitato oppure prova ad entrare utilizzando il link di login del sito."
                );
            } else {
              portalSessionState.setLoggedUser(googlePortalUser);
              portalSessionState.setGoogleAuthentication(true);
              // fa scattare la find sotto
              portalSessionState.setCustomer(null);
              userVerified = true;
            }
          }
        }
      }

      if (portalSessionState.getLoggedUser() != null && portalSessionState.getLoggedUser().getId() != null) {
        if (portalSessionState.getCustomer() == null) {
          Customer customer = customerAdapter.findByPortalUser(portalSessionState.getLoggedUser());
          portalSessionState.setCustomer(customer);
          if (customer != null) {
            portalSessionState.setOpenOrder(orderAdapter.findOpenOrderByCustomer(customer));
          } else {
            throw new ServiceException("Attenzione: sei entrato con un account che non è abilitato a inserire ordini.");
          }
        }
      }


    // 28/11/2012
    } else if (moduleType == PortalSessionState.MODULE_ADMIN) {
      
      UserService userService = UserServiceFactory.getUserService();
      User googleUser = userService.getCurrentUser();
      if (googleUser != null) {
        PortalUser googlePortalUser = portalUserAdapter.findByEmail(googleUser.getEmail());
        if (googlePortalUser == null) {
          
          // 28/03/2013
          // Lo accetto solo solo nel caso il db sia vuoto (inizializzaizone) 
          
           /* non lancio nessuna eccezione, per coprire il caso in cui parto con un db vuoto
          throw new ServiceException(
    "Attenzione: sei autenticato con un account Google che non è abilitato ad accedere a questa sezione del sito."
            );
            */
          
          if (portalUserAdapter.findAll().size() == 0) {
            // OK
            
            googlePortalUser = new PortalUserTx();
            googlePortalUser.setEmailAddress(googleUser.getEmail());
            googlePortalUser.setAdminUser(true);
            portalSessionState.setLoggedUser(googlePortalUser);
            portalSessionState.setGoogleAuthentication(true);
            portalSessionState.setCustomer(null);
            
          } else {
            throw new ServiceException("Attenzione: sei autenticato con l'account Google "+ googleUser.getEmail() +" che non è abilitato ad accedere a questa sezione del sito.");
          }
          
        } else {
          portalSessionState.setLoggedUser(googlePortalUser);
          portalSessionState.setGoogleAuthentication(true);
          portalSessionState.setCustomer(null);
        }
      }
      
    }
    
    LoggingUtils.getJavaLogging(this.getClass()).info(String.format("RETRIEVED %s", portalSessionState));
    
    return portalSessionState;
  }

  @Override
  public void deleteAll() {
    // portalDataMarshaller.deleteAll();
    generalAdapter.deleteAll();
  }

  @Override
  public String getServerContextUrl() {
    return HttpUtils.getContextUrl(getThreadLocalRequest());
  }

  public void reloadProperties() {
    PropertiesHolderConfigurer.reloadFromClassPathResource("econyx.properties", true);
  }

  @Override
  public void generateRandomCustomers(int number, Date date) {
    QueueFactory.getDefaultQueue().add(TaskOptions.Builder.withPayload(new GenerateOperationTask(GenerateOperationTask.GENERATE_RANDOM_CUSTOMERS, number, date)));
  }

  @Override
  public void generateRandomOrders(int number, Date date) {
    QueueFactory.getDefaultQueue().add(TaskOptions.Builder.withPayload(new GenerateOperationTask(GenerateOperationTask.GENERATE_RANDOM_ORDERS, number, date)));
  }
  
  @Override
  public void refreshUsersCache() {
    QueueFactory.getDefaultQueue().add(TaskOptions.Builder.withPayload(new GenerateOperationTask(GenerateOperationTask.REFRESH_USERS_CACHE, 0, null)));
  }

  public String gdataSpreadsheetTest() {
    
    String consumerKey = PropertiesHolder.getString("generalService.gdata.consumerKey");
    String consumerSecret = PropertiesHolder.getString("generalService.gdata.consumerSecret");
    String scope = PropertiesHolder.getString("generalService.gdata.scope", "https://docs.google.com/feeds/");
    String oauthCallback = PropertiesHolder.getString("generalService.gdata.oauthCallback", "http://your_app_id.appspot.com/step2");

    GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
    oauthParameters.setOAuthConsumerKey(consumerKey);
    oauthParameters.setOAuthConsumerSecret(consumerSecret);

    // Set the scope. In general, we want to limit the scope as much as
    // possible. For this example, we just ask for access to all feeds.
    oauthParameters.setScope(scope);

    // This sets the callback URL. This is where we want the user to be
    // sent after they have granted us access. Sometimes, developers
    // generate different URLs based on the environment. You should set
    // this value to "http://localhost:8888/step2" if you are running
    // the development server locally.
    oauthParameters.setOAuthCallback(oauthCallback);

    GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());

    try {
      // Remember that your request token is still unauthorized. We
      // need to first get a unique token for the user to promote.
      oauthHelper.getUnauthorizedRequestToken(oauthParameters);

      // Generate the authorization URL
      String approvalPageUrl = oauthHelper.createUserAuthorizationUrl(oauthParameters);

      // Store the token secret in the session. We use this later after
      // the user grants access. Note that this method isn't foolproof
      // or even close. This assumes the user won't sign out of their
      // browser or the sessions are swept between the time the user
      // is redirected and the callback is invoked.
      getThreadLocalRequest().getSession().setAttribute("oauthTokenSecret", oauthParameters.getOAuthTokenSecret());

      return approvalPageUrl;

    } catch (OAuthException ex) {
      logger.error("error", ex);
    }
    
    return null;

  }

  @Override
  public void testServiceException() throws ServiceException {
    throw new ServiceException("Simulazione service exception");
  }
  
  
  @Override
  public List<CacheDumpEntry> instanceCacheDump () {
    return generalAdapter.instanceCacheDump();
  }
  
  
  @Override
  public void cobraTest() {
    generalAdapter.cobraTest();
  }
  
  
  public String createBlobstoreUploadUrl(String url) {
    return BlobstoreServiceFactory.getBlobstoreService().createUploadUrl(url);
  }

}
