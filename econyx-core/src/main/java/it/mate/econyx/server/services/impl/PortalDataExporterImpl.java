package it.mate.econyx.server.services.impl;

import it.mate.commons.server.utils.BlobUtils;
import it.mate.commons.server.utils.CacheUtils;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.XStreamUtils;
import it.mate.econyx.server.model.PortalDataExportModel;
import it.mate.econyx.server.model.VisitContext;
import it.mate.econyx.server.model.converters.HtmlContentConverter;
import it.mate.econyx.server.model.converters.ImageContentConverter;
import it.mate.econyx.server.model.converters.ProductPageConverter;
import it.mate.econyx.server.model.converters.TipoArticoloConverter;
import it.mate.econyx.server.model.impl.AbstractArticoloDs;
import it.mate.econyx.server.model.impl.ExportJobDs;
import it.mate.econyx.server.model.impl.ImageDs;
import it.mate.econyx.server.services.ArticleAdapter;
import it.mate.econyx.server.services.BlogAdapter;
import it.mate.econyx.server.services.CustomAdapter;
import it.mate.econyx.server.services.CustomerAdapter;
import it.mate.econyx.server.services.DocumentAdapter;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.services.PortalDataExporter;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
import it.mate.econyx.server.services.ProductAdapter;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.ArticleFolderPage;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogPage;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.DocumentFolderPage;
import it.mate.econyx.shared.model.Image;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.OrderState;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.ProducerFolderPage;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.UnitaDiMisura;
import it.mate.econyx.shared.model.impl.AbstractIndirizzoTx;
import it.mate.econyx.shared.model.impl.ArticleFolderTx;
import it.mate.econyx.shared.model.impl.ArticlePageTx;
import it.mate.econyx.shared.model.impl.ArticleTx;
import it.mate.econyx.shared.model.impl.ArticoloTx;
import it.mate.econyx.shared.model.impl.BlogTx;
import it.mate.econyx.shared.model.impl.CustomerTx;
import it.mate.econyx.shared.model.impl.DocumentFolderTx;
import it.mate.econyx.shared.model.impl.DocumentTx;
import it.mate.econyx.shared.model.impl.ImageTx;
import it.mate.econyx.shared.model.impl.ModalitaSpedizioneTx;
import it.mate.econyx.shared.model.impl.OrderItemTx;
import it.mate.econyx.shared.model.impl.OrderStateConfigTx;
import it.mate.econyx.shared.model.impl.OrderStateTx;
import it.mate.econyx.shared.model.impl.OrderTx;
import it.mate.econyx.shared.model.impl.PortalPageTx;
import it.mate.econyx.shared.model.impl.PortalUserTx;
import it.mate.econyx.shared.model.impl.ProduttoreTx;
import it.mate.econyx.shared.model.impl.UnitaDiMisuraTx;
import it.mate.econyx.shared.model.impl.WebContentPageTx;

import java.io.File;
import java.io.FileInputStream;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

@Service
public class PortalDataExporterImpl implements PortalDataExporter {
  
  private static Logger logger = Logger.getLogger(PortalDataExporterImpl.class.getName());

  @Autowired private GeneralAdapter generalAdapter;
  
  @Autowired private PortalUserAdapter portalUserAdapter;
  
  @Autowired private CustomerAdapter customerAdapter;
  
  @Autowired private PortalPageAdapter portalPageAdapter;
  
  @Autowired private ProductAdapter productAdapter;
  
  @Autowired private OrderAdapter orderAdapter;
  
  @Autowired private ImageAdapter imageAdapter;
  
  @Autowired private ArticleAdapter articleAdapter;
  
  @Autowired private DocumentAdapter documentAdapter;
  
  @Autowired private BlogAdapter blogAdapter;
  
  @Autowired private CustomAdapter customAdapter;
  
  private static final String INIT_FILE = "META-INF/setup-data/portaldata.xml";
  
  private FileService fileService = FileServiceFactory.getFileService();
  
  private XStreamUtils xStreamUtils;
  
  private void setupXStream() {
    
    final List<String> CDATA_FIELDS = Arrays.asList(new String[] {"name", "content", "customData"}); 

    xStreamUtils = new XStreamUtils(new XppDriver() {
      @Override
      public HierarchicalStreamWriter createWriter(Writer out) {
        return new PrettyPrintWriter(out) {
          boolean cdata = false;
          public void startNode(String name, Class clazz) {
            super.startNode(name, clazz);
            cdata = CDATA_FIELDS.contains(name);
          }
          protected void writeText(QuickWriter writer, String text) {
            if (cdata) {
              writer.write("<![CDATA[");
              super.writeText(writer, HtmlUtils.htmlEscape(text));
              writer.write("]]>");
            } else {
              super.writeText(writer, text);
            }
          }
        };
      }
    });
    
    xStreamUtils.getXStream().setMode(XStream.NO_REFERENCES);
    xStreamUtils.registerConverter(new TipoArticoloConverter());
    xStreamUtils.registerConverter(new HtmlContentConverter());
    xStreamUtils.registerConverter(new ImageContentConverter());
    xStreamUtils.registerConverter(new ProductPageConverter(xStreamUtils.getXStream().getMapper(), xStreamUtils.getXStream().getReflectionProvider()));
    xStreamUtils.getXStream().omitField(PortalPageTx.class, "id");
    xStreamUtils.getXStream().omitField(PortalPageTx.class, "parent");
    xStreamUtils.getXStream().omitField(PortalPageTx.class, "parentId");
    xStreamUtils.getXStream().omitField(WebContentPageTx.class, "htmlsInitialized");
    xStreamUtils.getXStream().useAttributeFor(PortalPageTx.class, "visibleInExplorer");
    xStreamUtils.getXStream().useAttributeFor(PortalPageTx.class, "visibleInMenu");
    xStreamUtils.getXStream().useAttributeFor(PortalPageTx.class, "homePage");
    xStreamUtils.getXStream().omitField(ArticoloTx.class, "id");
    xStreamUtils.getXStream().omitField(ArticoloTx.class, "htmlsInitialized");
    xStreamUtils.getXStream().omitField(CustomerTx.class, "id");
    xStreamUtils.getXStream().omitField(AbstractIndirizzoTx.class, "id");
    xStreamUtils.getXStream().omitField(PortalUserTx.class, "id");
    xStreamUtils.getXStream().omitField(UnitaDiMisuraTx.class, "id");
    xStreamUtils.getXStream().omitField(ProduttoreTx.class, "id");
    xStreamUtils.getXStream().omitField(ModalitaSpedizioneTx.class, "id");
    xStreamUtils.getXStream().omitField(ImageTx.class, "id");
    xStreamUtils.getXStream().omitField(OrderStateConfigTx.class, "id");
    xStreamUtils.getXStream().omitField(ArticleFolderTx.class, "id");
    xStreamUtils.getXStream().omitField(ArticleTx.class, "id");
    xStreamUtils.getXStream().omitField(DocumentFolderTx.class, "id");
    xStreamUtils.getXStream().omitField(DocumentTx.class, "id");
    xStreamUtils.getXStream().omitField(BlogTx.class, "id");
    
    xStreamUtils.getXStream().omitField(ArticlePageTx.class, "id");
    
    xStreamUtils.getXStream().omitField(OrderTx.class, "id");
    xStreamUtils.getXStream().omitField(OrderItemTx.class, "id");
    xStreamUtils.getXStream().omitField(OrderStateTx.class, "id");
    xStreamUtils.getXStream().omitField(OrderItemTx.class, "order");
    xStreamUtils.getXStream().omitField(OrderStateTx.class, "order");
    
  }
  
  public PortalDataExportModel load () {
    try {
      File file = new ClassPathResource(INIT_FILE).getFile();
      load(file);
    } catch (Exception ex) {
      logger.log(Level.SEVERE, "error", ex);
    }
    return null;
  }
  
  public PortalDataExportModel load (String portalDataXml) {
    try {
      setupXStream();
      PortalDataExportModel dataModel = (PortalDataExportModel)xStreamUtils.buildGraph(portalDataXml);
      ensureProductsDataModel(dataModel);
      load(dataModel);
    } catch (Exception ex) {
      logger.log(Level.SEVERE, "error", ex);
    }
    return null;
  }
  
  private PortalDataExportModel load (File portalDataXml) {
    try {
      setupXStream();
      PortalDataExportModel dataModel = (PortalDataExportModel)xStreamUtils.buildGraph(portalDataXml);
      ensureProductsDataModel(dataModel);
      load(dataModel);
    } catch (Exception ex) {
      logger.log(Level.SEVERE, "error", ex);
    }
    return null;
  }
  
  private PortalDataExportModel load (PortalDataExportModel model) {
    if (model.loadMethod == PortalDataExportModel.LOAD_METHOD_ORDERS) {
      model = loadOrders(model);
    } else {
      model = loadAll(model);
    }
    return model;
  }
  
  
  private PortalDataExportModel loadOrders (PortalDataExportModel model) {
    
    // CANCELLAZIONI
    generalAdapter.deleteOrdersData();
    customAdapter.deleteExtraData();
    
    // FACCIO IL MERGE DI UTENTI, CUSTOMER, PRODUCTS
    List<PortalUser> usersToImport = model.users;
    model.users = portalUserAdapter.findAll();
    for (PortalUser portalUser : usersToImport) {
      visitPortalUser(model, true, portalUser);
    }
    
    List<Customer> customersToImport = model.customers;
    model.customers = customerAdapter.findAll();
    for (Customer customer : customersToImport) {
      try {
        customerAdapter.setDisableCreateCustomerCustomAdapter(true);
        visitCustomer(model, true, customer);
      } catch (RuntimeException re) {
        throw re;
      } finally {
        customerAdapter.setDisableCreateCustomerCustomAdapter(false);
      }
    }
    
    List<Produttore> producersToImport = model.producers;
    model.producers = productAdapter.findAllProducers();
    for (Produttore producer : producersToImport) {
      visitProducer(model, true, producer);
    }
    
    List<Articolo> productsToImport = model.products;
    model.products = productAdapter.findAll();
    for (Articolo articolo : productsToImport) {
      visitProduct(model, true, articolo);
    }
    
    for (ModalitaSpedizione modalitaSpedizione : model.listaModalitaSpedizione) {
      visitModalitaSpedizione(model, true, modalitaSpedizione);
    }
    
    for (ModalitaPagamento modalitaPagamento : model.listaModalitaPagamento) {
      visitModalitaPagamento(model, true, modalitaPagamento);
    }
    
    for (OrderStateConfig orderState : model.orderStates) {
      visitOrderStateConfig(model, true, orderState);
    }
    
    for (Order order : model.orders) {
      visitOrder(model, true, order);
    }
    
    customAdapter.loadExtraData(model);
    
    CacheUtils.clearAll();
    
    return model;
  }
  
  private PortalDataExportModel loadAll (PortalDataExportModel model) {
    
    // CANCELLAZIONI
    generalAdapter.deleteAllData();
    
    for (PortalUser user : model.users) {
      visitPortalUser(model, true, user);
    }
    for (Customer customer : model.customers) {
      visitCustomer(model, true, customer);
    }
    for (Image image : model.images) {
      visitImage(model, true, image);
    }
    for (PortalPage page : model.pages) {
      visitPortalPage(model, true, page);
    }
    for (ModalitaSpedizione modalitaSpedizione : model.listaModalitaSpedizione) {
      visitModalitaSpedizione(model, true, modalitaSpedizione);
    }
    for (ModalitaPagamento modalitaPagamento : model.listaModalitaPagamento) {
      visitModalitaPagamento(model, true, modalitaPagamento);
    }
    for (OrderStateConfig orderState : model.orderStates) {
      visitOrderStateConfig(model, true, orderState);
    }
    for (Articolo articolo : model.products) {
      visitProduct(model, true, articolo);
    }
    if (model.articleFolders != null) {
      for (int it = 0; it < model.articleFolders.size(); it++) {
        ArticleFolder articleFolder = model.articleFolders.get(it);
        visitArticleFolder(model, true, articleFolder);
      }
    }
    if (model.documentFolders != null) {
      for (int it = 0; it < model.documentFolders.size(); it++) {
        DocumentFolder documentFolder = model.documentFolders.get(it);
        visitDocumentFolder(model, true, documentFolder);
      }
    }
    if (model.blogs != null) {
      for (int it = 0; it < model.blogs.size(); it++) {
        Blog blog = model.blogs.get(it);
        visitBlog(model, true, blog);
      }
    }
    return model;
  }
  
  @Override
  public void unloadDeferred(String jobId, int loadMethod) {
    ExportJobDs exportJob = generalAdapter.findExportJobById(jobId);
    String xml = unload(loadMethod);
    exportJob.setResult(new Text(xml));
    exportJob.setCompleted(new Date());
    generalAdapter.updateExportJob(exportJob);
  }

  public String unload(int loadMethod) {
    String xml = null;
    try {
      PortalDataExportModel model = new PortalDataExportModel();
      
      if (loadMethod == PortalDataExportModel.LOAD_METHOD_ALL) {
        unloadAll(model);
      } else if (loadMethod == PortalDataExportModel.LOAD_METHOD_ORDERS) {
        unloadOrders(model);
      }
      
      // 17/05/2013
      setupXStream();
      
//    xml = xStreamUtils.parseGraph(model, "UTF-8");
      xml = xStreamUtils.parseGraph(model);
      
    } catch (Throwable th) {
      logger.log(Level.SEVERE, "error", th);
    }
    return xml;
  }
  
  private void unloadAll(PortalDataExportModel model) {
    model.users = portalUserAdapter.findAll();
    model.customers = customerAdapter.findAll();
    model.products = productAdapter.findAll();
    
    model.pages = portalPageAdapter.findAllRoot();
    for (PortalPage page : model.pages) {
      portalPageAdapter.resolveAllDependencies(page);
      visitPortalPage(model, false, page);
    }
    
    Collections.sort(model.products, new Comparator<Articolo>() {
      public int compare(Articolo o1, Articolo o2) {
        return o1.getCodice().compareTo(o2.getCodice());
      }
    });

    model.images = imageAdapter.findAll();
    
    model.listaModalitaSpedizione = orderAdapter.findAllModalitaSpedizione();
    model.orderStates = orderAdapter.findAllOrderStates();
    
    model.articleFolders = articleAdapter.findAll();
    model.documentFolders = documentAdapter.findAllFolders();
  }
  
  private void unloadOrders(PortalDataExportModel model) {
    model.loadMethod = PortalDataExportModel.LOAD_METHOD_ORDERS;
    model.users = portalUserAdapter.findAll();
    model.customers = customerAdapter.findAll();
    model.products = productAdapter.findAll();
    
    Collections.sort(model.products, new Comparator<Articolo>() {
      public int compare(Articolo o1, Articolo o2) {
        return o1.getCodice().compareTo(o2.getCodice());
      }
    });

    model.listaModalitaSpedizione = orderAdapter.findAllModalitaSpedizione();
    model.orderStates = orderAdapter.findAllOrderStates();
    
    model.orders = orderAdapter.findAll();
    for (Order order : model.orders) {
      visitOrder(model, false, order);
    }
    
    customAdapter.unloadExtraData(model);
    
    for (Customer customer : model.customers) {
      customer.setPortalUser(reducePortalUser(customer.getPortalUser()));
    }
    
    for (Order order : model.orders) {
      List<OrderItem> orderItems = order.getItems();
      for (int it = 0; it < orderItems.size(); it++) {
        OrderItem orderItem = orderItems.get(it);
        orderItem.setProduct(reduceProduct(orderItem.getProduct()));
        orderItems.set(it, orderItem);
      }
      order.getCustomer().setPortalUser(reducePortalUser(order.getCustomer().getPortalUser()));
      List<OrderState> orderStates = order.getStates();
      for (int it = 0; it < orderStates.size(); it++) {
        OrderState orderState = orderStates.get(it);
        orderState.setConfig(reduceOrderStateConfig(orderState.getConfig()));
        orderState.setPortalUser(reducePortalUser(orderState.getPortalUser()));
        orderStates.set(it, orderState);
      }
    }
    
  }
  
  private PortalUser reducePortalUser(PortalUser portalUser) {
    if (portalUser == null)
      return null;
    PortalUser reducedPortalUser = new PortalUserTx();
    reducedPortalUser.setEmailAddress(portalUser.getEmailAddress());
    return reducedPortalUser;
  }
  
  private Articolo reduceProduct(Articolo product) {
    Articolo reducedProduct = new ArticoloTx();
    reducedProduct.setCodice(product.getCodice());
    return reducedProduct;
  }
  
  private OrderStateConfig reduceOrderStateConfig(OrderStateConfig orderStateConfig) {
    OrderStateConfig reducedOrderStateConfig = new OrderStateConfigTx();
    reducedOrderStateConfig.setCode(orderStateConfig.getCode());
    return reducedOrderStateConfig;
  }
  
  private void ensureProductsDataModel(PortalDataExportModel dataModel) {
    if (dataModel.products == null || dataModel.products.size() == 0) {
      dataModel.products = productAdapter.findAll();
    }
    dataModel.createProducts = true;
  }
  
  private ModalitaSpedizione visitModalitaSpedizione(VisitContext context, boolean loadMode, ModalitaSpedizione modalitaSpedizione) {
    if (loadMode) {
      modalitaSpedizione = orderAdapter.create(modalitaSpedizione);
    }
    return modalitaSpedizione;
  }
  
  private ModalitaPagamento visitModalitaPagamento(VisitContext context, boolean loadMode, ModalitaPagamento modalitaPagamento) {
    if (loadMode) {
      modalitaPagamento = orderAdapter.create(modalitaPagamento);
    }
    return modalitaPagamento;
  }
  
  private OrderStateConfig visitOrderStateConfig(VisitContext context, boolean loadMode, OrderStateConfig orderState) {
    PortalDataExportModel model = (PortalDataExportModel)context;
    for (int it = 0; it < model.orderStates.size(); it++) {
      OrderStateConfig cachedOrderState = model.orderStates.get(it);
      if (cachedOrderState.getCode().equals(orderState.getCode())) {
        if (loadMode && cachedOrderState.getId() == null) {
          cachedOrderState = orderAdapter.create(cachedOrderState);
          model.orderStates.set(it, cachedOrderState);
        }
        return cachedOrderState;
      }
    }
    if (loadMode && orderState.getId() == null) {
      orderState = orderAdapter.create(orderState);
      model.orderStates.add(orderState);
    }
    return orderState;
  }
  
  private Customer visitCustomer(VisitContext context, boolean loadMode, Customer customer) {
    PortalDataExportModel model = (PortalDataExportModel)context;
    customer.setPortalUser(visitPortalUser(context, loadMode, customer.getPortalUser()));
    for (int it = 0; it < model.customers.size(); it++) {
      Customer cachedCustomer = model.customers.get(it);
      if (cachedCustomer.getPortalUser().getEmailAddress().equals(customer.getPortalUser().getEmailAddress())) {
        if (loadMode && cachedCustomer.getId() == null) {
          cachedCustomer = customerAdapter.create(cachedCustomer);
          model.customers.set(it, cachedCustomer);
        }
        return cachedCustomer;
      }
    }
    if (loadMode && customer.getId() == null) {
      customer = customerAdapter.create(customer);
    }
    model.customers.add(customer);
    return customer;
  }
  
  private PortalUser visitPortalUser(VisitContext context, boolean loadMode, PortalUser user) {
    if (user == null)
      return null;
    PortalDataExportModel model = (PortalDataExportModel)context;
    for (int it = 0; it < model.users.size(); it++) {
      PortalUser cachedUser = model.users.get(it);
      if (cachedUser.getEmailAddress().equals(user.getEmailAddress())) {
        if (loadMode && cachedUser.getId() == null) {
          cachedUser = portalUserAdapter.create(cachedUser, true);
          model.users.set(it, cachedUser);
        }
        return cachedUser;
      }
    }
    if (loadMode && user.getId() == null) {
      user = portalUserAdapter.create(user, true);
    }
    model.users.add(user);
    return user;
  }
  
  private Image visitImage(VisitContext context, boolean loadMode, Image image) {
    if (loadMode) {
      if (image.getName() == null && image.getCode() != null) {
        image.setName(image.getCode());
      }
      image = imageAdapter.create(image);
      if (cast(context).checkImageResources) {
        try {
          String fileName = String.format("META-INF/setup-data/images/image.%s", image.getCode());
          File imageFile = getResourceFileAllowNull(fileName + ".jpg");
          if (imageFile == null) {
            imageFile = getResourceFileAllowNull(fileName + ".png");
          }
          if (imageFile != null && imageFile.exists()) {
            Blob imageBlob = BlobUtils.inputStreamToBlob(new FileInputStream(imageFile));
            ImageDs ds = CloneUtils.clone(image, ImageDs.class);
            ds.setContent(imageBlob);
            imageAdapter.updateDs(ds);
          }
        } catch (Exception e) {   }
      }
    }
    return image;
  }
  
  private File getResourceFileAllowNull(String filename) {
    File resourceFile = null;
    try {
      resourceFile = new ClassPathResource( filename ).getFile();
    } catch (Exception ex) {
      return null;
    }
    if (resourceFile == null || !resourceFile.exists()) {
      return null;
    }
    return resourceFile;
  }
  
  private Order visitOrder(VisitContext context, boolean loadMode, Order order) {
    PortalDataExportModel model = (PortalDataExportModel)context;
    boolean needUpdate = false;
    if (order.getProducer() != null) {
      order.setProducer(visitProducer(context, loadMode, order.getProducer()));
      needUpdate = true;
    }
    if (order.getCustomer() != null) {
      order.setCustomer(visitCustomer(context, loadMode, order.getCustomer()));
      needUpdate = true;
    }
    if (order.getItems() != null) {
      List<OrderItem> orderItems = order.getItems();
      for (int it = 0; it < orderItems.size(); it++) {
        OrderItem orderItem = orderItems.get(it);
        orderItem.setProduct(visitProduct(context, loadMode, orderItem.getProduct()));
        orderItems.set(it, orderItem);
      }
      needUpdate = true;
    }
    if (order.getStates() != null) {
      List<OrderState> orderStates = order.getStates();
      for (int it = 0; it < orderStates.size(); it++) {
        OrderState orderState = orderStates.get(it);
        orderState.setConfig(visitOrderStateConfig(context, loadMode, orderState.getConfig()));
        orderState.setPortalUser(visitPortalUser(context, loadMode, orderState.getPortalUser()));
        
        if (loadMode) {
          if (orderState.getOrder() == null) {
            orderState.setOrder(order);
          }
        }
        
        orderStates.set(it, orderState);
      }
      needUpdate = true;
    }
    if (loadMode || needUpdate) {
      if (order.getId() == null) {
        try {
          // serve per disabilitare gli aggiornamenti automatici su contoutente
          orderAdapter.setDisableOrderStateChangeCustomAdapter(true);
          orderAdapter.setDisableOrderStateChangeDeferredTask(true);
          order = orderAdapter.createWithoutInitialState(order, null);
          // aggiorno id in model per il successivo load dei conti utente
          for (Order orderInModel : model.orders) {
            if (orderInModel.getCode().equals(order.getCode())) {
              orderInModel.setId(order.getId());
            }
          }
        } catch (RuntimeException re) {
          throw re;
        } finally {
          orderAdapter.setDisableOrderStateChangeCustomAdapter(false);
          orderAdapter.setDisableOrderStateChangeDeferredTask(false);
        }
      } else {
        order = orderAdapter.update(order, null);
      }
    }
    return order;
  }
  
  private PortalPage visitPortalPage(VisitContext context, boolean loadMode, PortalPage page) {
    if (loadMode) {
      page = portalPageAdapter.create(page);
    }
    if (page instanceof ProducerFolderPage) {
      ProducerFolderPage producerFolderPage = (ProducerFolderPage)page;
      Produttore producer = producerFolderPage.getEntity();
      producer = visitProducer(context, loadMode, producer);
      producerFolderPage.setEntity(producer);
    }
    if (page instanceof PortalFolderPage) {
      PortalFolderPage portalFolderPage = (PortalFolderPage)page;
      for (int it = 0; it < portalFolderPage.getChildreen().size(); it++) {
        PortalPage childPage = portalFolderPage.getChildreen().get(it);
        childPage = visitPortalPage(context, loadMode, childPage);
        if (loadMode) {
          portalFolderPage.getChildreen().set(it, childPage);
        }
      }
      if (loadMode) {
        page = portalPageAdapter.update(page);
      }
    } 
    if (page instanceof ProductPage) {
      ProductPage productPage = (ProductPage)page;
      Articolo product = productPage.getEntity();
      product = visitProduct(context, loadMode, product);
      if (loadMode) {
        productPage.setEntity(product);
        if (productPage.getName() == null) {
          productPage.setName(productPage.getEntity().getName());
        }
        page = portalPageAdapter.update(page);
      }
    } 
    if (page instanceof ArticleFolderPage) {
      ArticleFolderPage articleFolderPage = (ArticleFolderPage)page;
      ArticleFolder articleFolder = articleFolderPage.getEntity();
      articleFolder = visitArticleFolder(context, loadMode, articleFolder);
      if (loadMode) {
        articleFolderPage.setEntity(articleFolder);
        if (articleFolderPage.getName() == null) {
          articleFolderPage.setName(articleFolderPage.getEntity().getName());
        }
        page = portalPageAdapter.update(page);
      }
    }
    if (page instanceof DocumentFolderPage) {
      DocumentFolderPage documentFolderPage = (DocumentFolderPage)page;
      DocumentFolder documentFolder = documentFolderPage.getEntity();
      documentFolder = visitDocumentFolder(context, loadMode, documentFolder);
      if (loadMode) {
        documentFolderPage.setEntity(documentFolder);
        if (documentFolderPage.getName() == null) {
          documentFolderPage.setName(documentFolderPage.getEntity().getName());
        }
        page = portalPageAdapter.update(page);
      }
    }
    if (page instanceof BlogPage) {
      BlogPage blogPage = (BlogPage)page;
      Blog blog = blogPage.getEntity();
      blog = visitBlog(context, loadMode, blog);
      if (loadMode) {
        blogPage.setEntity(blog);
        if (blogPage.getName() == null) {
          blogPage.setName(blogPage.getEntity().getName());
        }
        page = portalPageAdapter.update(page);
      }
    }
    return page;
  }
  
  private ArticleFolder visitArticleFolder(VisitContext context, boolean loadMode, ArticleFolder articleFolder) {
    PortalDataExportModel model = (PortalDataExportModel)context;
    if (model.articleFolders == null) {
      model.articleFolders = new ArrayList<ArticleFolder>();
    }
    for (int it = 0; it < model.articleFolders.size(); it++) {
      ArticleFolder cachedArticleFolder = model.articleFolders.get(it);
      boolean matched = false;
      if (articleFolder.getCode() != null && articleFolder.getCode().equals(cachedArticleFolder.getCode())) {
        matched = true;
      }
      if (articleFolder.getName() != null && articleFolder.getName().equals(cachedArticleFolder.getName())) {
        matched = true;
      }
      if (matched) {
        if (loadMode && cachedArticleFolder.getId() == null) {
          cachedArticleFolder = createArticleFolder(context, loadMode, cachedArticleFolder);
          model.articleFolders.set(it, cachedArticleFolder);
        }
        return cachedArticleFolder;
      }
    }
    if (loadMode) {
      articleFolder = createArticleFolder(context, loadMode, articleFolder);
      model.articleFolders.add(articleFolder);
    }
    return articleFolder;
  }
  
  private Blog visitBlog(VisitContext context, boolean loadMode, Blog blog) {
    PortalDataExportModel model = (PortalDataExportModel)context;
    if (model.blogs == null) {
      model.blogs = new ArrayList<Blog>();
    }
    for (int it = 0; it < model.blogs.size(); it++) {
      Blog cachedBlog = model.blogs.get(it);
      boolean matched = false;
      if (blog.getCode() != null && blog.getCode().equals(cachedBlog.getCode())) {
        matched = true;
      }
      if (matched) {
        if (loadMode && cachedBlog.getId() == null) {
          cachedBlog = createBlog(context, loadMode, cachedBlog);
          model.blogs.set(it, cachedBlog);
        }
        return cachedBlog;
      }
    }
    if (loadMode) {
      blog = createBlog(context, loadMode, blog);
      model.blogs.add(blog);
    }
    return blog;
  }
  
  private DocumentFolder visitDocumentFolder(VisitContext context, boolean loadMode, DocumentFolder documentFolder) {
    PortalDataExportModel model = (PortalDataExportModel)context;
    if (model.documentFolders == null) {
      model.documentFolders = new ArrayList<DocumentFolder>();
    }
    for (int it = 0; it < model.documentFolders.size(); it++) {
      DocumentFolder cachedDocumentFolder = model.documentFolders.get(it);
      boolean matched = false;
      if (documentFolder.getCode() != null && documentFolder.getCode().equals(cachedDocumentFolder.getCode())) {
        matched = true;
      }
      if (documentFolder.getName() != null && documentFolder.getName().equals(cachedDocumentFolder.getName())) {
        matched = true;
      }
      if (matched) {
        if (loadMode && cachedDocumentFolder.getId() == null) {
          cachedDocumentFolder = createDocumentFolder(context, loadMode, cachedDocumentFolder);
          model.documentFolders.set(it, cachedDocumentFolder);
        }
        return cachedDocumentFolder;
      }
    }
    if (loadMode) {
      documentFolder = createDocumentFolder(context, loadMode, documentFolder);
      model.documentFolders.add(documentFolder);
    }
    return documentFolder;
  }
  
  private Articolo visitProduct(VisitContext context, boolean loadMode, Articolo product) {
    PortalDataExportModel model = (PortalDataExportModel)context;
    for (int it = 0; it < model.products.size(); it++) {
      Articolo cachedProduct = model.products.get(it);
      if (product.getCodice().equals(cachedProduct.getCodice())) {
        if (loadMode && cachedProduct.getId() == null) {
          updateProductReferences(context, loadMode, cachedProduct);
          cachedProduct = createProduct(context, cachedProduct);
          model.products.set(it, cachedProduct);
        }
        return cachedProduct;
      }
    }
    updateProductReferences(context, loadMode, product);
    if (!loadMode) {
      product = productAdapter.resolveAllDependencies(product);
    }
    model.products.add(product);
    if (loadMode && model.createProducts) {
      product = createProduct(context, product);
    }
    return product;
  }
  
  private Articolo createProduct(VisitContext context, Articolo product) {
    product = productAdapter.create(product);
    if (cast(context).checkImageResources) {
      try {
        File smallImageFile = new ClassPathResource(String.format("META-INF/setup-data/images/product.%s.small.jpg", product.getCodice())).getFile();
        if (smallImageFile.exists()) {
          Blob imageBlob = BlobUtils.inputStreamToBlob(new FileInputStream(smallImageFile));
          AbstractArticoloDs productDs = productAdapter.fetchImages(product.getId());
          productDs.setImageSmall(imageBlob);
          productAdapter.updateDs(productDs, true, false);
        }
      } catch (Exception e) {   }
      try {
        File mediumImageFile = new ClassPathResource(String.format("META-INF/setup-data/images/product.%s.medium.jpg", product.getCodice())).getFile();
        if (mediumImageFile.exists()) {
          Blob imageBlob = BlobUtils.inputStreamToBlob(new FileInputStream(mediumImageFile));
          AbstractArticoloDs productDs = productAdapter.fetchImages(product.getId());
          productDs.setImageMedium(imageBlob);
          productAdapter.updateDs(productDs, true, false);
        }
      } catch (Exception e) {   }
    }
    return product;
  }
  
  private void updateProductReferences(VisitContext context, boolean loadMode, Articolo product) {
    product.setTipoArticolo(visitProductType(context, loadMode, product.getTipoArticolo()));
    product.setUnitaDiMisura(visitUnitOfMeasure(context, loadMode, product.getUnitaDiMisura()));
    product.setProducer(visitProducer(context, loadMode, product.getProducer()));
  }
  
  private TipoArticolo visitProductType(VisitContext context, boolean loadMode, TipoArticolo productType) {
    PortalDataExportModel model = (PortalDataExportModel)context;
    for (int it = 0; it < model.productTypes.size(); it++) {
      TipoArticolo cachedProductType = model.productTypes.get(it);
      if (productType.getCodice().equals(cachedProductType.getCodice())) {
        if (loadMode && cachedProductType.getId() == null) {
          cachedProductType = productAdapter.create(cachedProductType);
          model.productTypes.set(it, cachedProductType);
        }
        return cachedProductType;
      }
    }
    if (loadMode) {
      productType = productAdapter.create(productType);
    }
    model.productTypes.add(productType);
    return productType;
  }
  
  private UnitaDiMisura visitUnitOfMeasure(VisitContext context, boolean loadMode, UnitaDiMisura unitOfMeasure) {
    if (unitOfMeasure == null)
      return null;
    PortalDataExportModel model = (PortalDataExportModel)context;
    for (int it = 0; it < model.unitOfMeasures.size(); it++) {
      UnitaDiMisura cachedUnitOfMeasure = model.unitOfMeasures.get(it);
      if (unitOfMeasure.getCodice().equals(cachedUnitOfMeasure.getCodice())) {
        if (loadMode && cachedUnitOfMeasure.getId() == null) {
          cachedUnitOfMeasure = productAdapter.create(cachedUnitOfMeasure);
          model.unitOfMeasures.set(it, cachedUnitOfMeasure);
        }
        return cachedUnitOfMeasure;
      }
    }
    if (loadMode) {
      unitOfMeasure = productAdapter.create(unitOfMeasure);
    }
    model.unitOfMeasures.add(unitOfMeasure);
    return unitOfMeasure;
  }
  
  private Produttore visitProducer(VisitContext context, boolean loadMode, Produttore producer) {
    if (producer == null)
      return null;
    PortalDataExportModel model = (PortalDataExportModel)context;
    for (int it = 0; it < model.producers.size(); it++) {
      Produttore cachedProducer = model.producers.get(it);
      if (producer.getCodice().equals(cachedProducer.getCodice())) {
        if (loadMode && cachedProducer.getId() == null) {
          cachedProducer = productAdapter.create(cachedProducer);
          model.producers.set(it, cachedProducer);
        }
        return cachedProducer;
      }
    }
    if (loadMode) {
      producer = productAdapter.create(producer);
    }
    model.producers.add(producer);
    return producer;
  }
  
  private ArticleFolder createArticleFolder(VisitContext context, boolean loadMode, ArticleFolder articleFolder) {
    for (Article article : articleFolder.getArticles()) {
      article.setAuthor(visitPortalUser(context, loadMode, article.getAuthor()));
      if (article.getCreated() == null)
        article.setCreated(new Date());
    }
    articleFolder = articleAdapter.create(articleFolder);
    return articleFolder;
  }
  
  private Document visitDocument(VisitContext context, boolean loadMode, Document document) {
    if (loadMode) {
      if (document.getId() == null) {
        document.setAuthor(visitPortalUser(context, loadMode, document.getAuthor()));
        if (document.getCreated() == null) {
          document.setCreated(new Date());
        }
        if (cast(context).checkDocumentResources && document.getCode() != null) {
          try {
            String filename = String.format("META-INF/setup-data/documents/%s.pdf", document.getCode());
            File resourceFile = getResourceFileAllowNull(filename);
            if (resourceFile != null && resourceFile.exists()) {
              AppEngineFile blobFile = fileService.createNewBlobFile("application/pdf");
              FileWriteChannel blobWriteChannel = fileService.openWriteChannel(blobFile, true);
              FileInputStream resourceInputStream = new FileInputStream(resourceFile);
              FileChannel resourceInputChannel = resourceInputStream.getChannel();
              resourceInputChannel.transferTo(0, resourceInputChannel.size(), blobWriteChannel);
              blobWriteChannel.closeFinally();
              BlobKey blobKey = fileService.getBlobKey(blobFile);
              document.setBlobKey(blobKey.getKeyString());
            }
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
        document = documentAdapter.updateDocument(document);
      }
    }
    return document;
  }
  
  private DocumentFolder createDocumentFolder(VisitContext context, boolean loadMode, DocumentFolder documentFolder) {
    if (documentFolder.getDocuments() != null) {
      for (int it = 0; it < documentFolder.getDocuments().size(); it++) {
        Document document = documentFolder.getDocuments().get(it);
        document = visitDocument(context, loadMode, document);
        documentFolder.getDocuments().set(it, document);
      }
    }
    documentFolder = documentAdapter.createFolder(documentFolder);
    return documentFolder;
  }
  
  private Blog createBlog(VisitContext context, boolean loadMode, Blog blog) {
    blog = blogAdapter.createBlog(blog);
    return blog;
  }
  
  private PortalDataExportModel cast(VisitContext context) {
    return (PortalDataExportModel)context;
  }
  
}
