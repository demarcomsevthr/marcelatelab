package it.mate.stickmail.server.endpoint;

import it.mate.stickmail.server.services.StickAdapter;
import it.mate.stickmail.shared.model.EndpointResponse;

import org.apache.log4j.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

/**
 *  ATTENZIONE
 *  
 *  TIPS & TRICKS
 *  
 *      - NON FUNZIONA LA @PostConstruct
 *        PERO' EMPIRICAMENTE RILEVO CHE SE LA TOLGO NON FUNZIONA IL WRAPPER JS gapi.client...
 *  
 *      - NON FUNZIONANO I METODI CHE INIZIANO CON GET (DA 404)
 *        NON HO PROVATO IL METODO POST... (ma potrebbe non funzionare analogamente)
 *        (sembra anche ininfluente l'utilizza della property name dell'annotation)
 *        
 *      - SE NON FUNZIONA IL WRAPPER JS gapi.client PROVARE A MODIFICARE L'ENDPOINT (IN MODO CHE VENGA RICREATO)  
 *      
 *      - IMPORTANTE: NEL NOME DEL SERVER NON CI DEVONO ESSERE CARATTERI "STRANI" (-) [01/02/2014]
 *      
 *      
 *      
 * 
 *  LINK PER I TEST
 *  
 *    API EXPLORER:
 *    
 *      https://stickmailsrv.appspot.com/_ah/api/explorer
 *  
 *    IN LOCALE:
 *  
 *      http://127.0.0.1:8080/_ah/api/stickmailEP/v1/buildNumber
 *  
 *      (in locale non funziona l'api explorer!)
 * 
 *    SU APPSPOT:
 *      RICORDARSI DI ANDARE SEMPRE IN HTTPS !!!
 *  
 *      https://stickmailsrv.appspot.com/_ah/api/stickmailEP/v1/buildNumber
 *  
 *  
 *
 */



@Api (name="stickmailEP", version="v1", description="Stick Mail API")
public class StickMailEndpoint {

  private static Logger logger = Logger.getLogger(StickMailEndpoint.class);

//private MyAutoBeanFactory factory;
  
  private StickAdapter adapter;

  /*
  @PostConstruct
  public void onPostConstruct() {
    System.out.println("NON SERVE A NIENTE E NON VIENE ESEGUITA, MA EMPIRICAMENTE RILEVO CHE SE LA TOLGO NON FUNZIONA IL CLIENT JS GAPI.CLIENT...");
  }
  */
  
  private void ensureInstances() {
    boolean initOk = false;
    /*
    if (factory == null) {
      factory = AutoBeanFactorySource.create(MyAutoBeanFactory.class);
      initOk = true;
    }
    if (adapter == null) {
      adapter = AdapterUtil.getAdapter();
      initOk = true;
    }
    if (initOk) {
      logger.debug("initialized " + this);
    }
    */
  }
  
  @ApiMethod (name="buildNumber", httpMethod=HttpMethod.GET)
  public EndpointResponse buildNumber() {
//  logger.debug("executing method - received payload " + payload);
    ensureInstances();
    EndpointResponse response = EndpointResponse.OK;
//  response.setPayload("SERVICE IS ACTIVE AND HAS RECEIVED " + payload);
    response.setPayload("SERVICE IS ACTIVE");
    response.setBuildNumber("1002");
    return response;
  }

  /*
  @ApiMethod (httpMethod=HttpMethod.GET)
  public EndpointResponse serverTime() {
    logger.debug("executing method");
    ensureInstances();
    EndpointResponse response = EndpointResponse.OK;
    ServerTime time = new ServerTime.Impl(new Date());
    response.setPayload(AutoBeanCodex.encode(factory.getServerTime(time)).getPayload());
    return response;
  }
  
  
  @ApiMethod (name="postNewMail", httpMethod=HttpMethod.PUT)
  public EndpointResponse postNewMail(@Named("payload") String payload) {
    ensureInstances();
    EndpointResponse response = EndpointResponse.OK;
    try {
      StickMail mail = AutoBeanCodex.decode(factory, StickMail.class, payload).as();
      adapter.postNewMail(mail);
    } catch (AdapterException ex) {
      response = EndpointResponse.ERROR;
    } catch (Exception ex) {
      response = EndpointResponse.ERROR;
    }
    return response;
  }
  
  @ApiMethod (name="authUser", httpMethod=HttpMethod.POST)
  public AuthUser authUser(@Named("token") String token, User googleUser) {
    AuthUser authUser = new AuthUser();
    authUser.setEmail(googleUser.getEmail());
    authUser.setUserId(googleUser.getUserId());
    authUser.setNickname(googleUser.getNickname());
    authUser.setAuthDomain(googleUser.getAuthDomain());
    authUser.setFederatedIdentity(googleUser.getFederatedIdentity());
    return authUser;
  }
  
  */
  
}
