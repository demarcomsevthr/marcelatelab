package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.AbstractArticoloDs;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.services.InitAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.services.PortalDataExporter;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
import it.mate.econyx.server.services.ProductAdapter;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderItem;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.econyx.shared.model.impl.ArticoloTx;
import it.mate.econyx.shared.model.impl.HtmlContentTx;
import it.mate.econyx.shared.model.impl.OrderItemTx;
import it.mate.econyx.shared.model.impl.OrderTx;
import it.mate.econyx.shared.model.impl.PortalFolderPageTx;
import it.mate.econyx.shared.model.impl.ProductFolderPageTx;
import it.mate.econyx.shared.model.impl.ProductPageTx;
import it.mate.econyx.shared.model.impl.TipoArticoloTx;
import it.mate.econyx.shared.model.impl.WebContentPageTx;
import it.mate.econyx.shared.services.PropertiesConstants;
import it.mate.gwtcommons.server.utils.BlobUtils;
import it.mate.gwtcommons.server.utils.CloneUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitAdapterImpl implements InitAdapter {

  private static Logger logger = Logger.getLogger(InitAdapterImpl.class);

  @Autowired private GeneralAdapter generalAdapter;

  @Autowired private ProductAdapter productAdapter;

  @Autowired private OrderAdapter orderAdapter;

  @Autowired private PortalPageAdapter portalPageAdapter;

  @Autowired private PortalUserAdapter portalUserAdapter;

//@Autowired private PortalPageMarshaller portalPageMarshaller;

  @Autowired private PortalDataExporter portalDataMarshaller;

  private static boolean INIT_ON_STARTUP = PropertiesHolder.getBoolean(PropertiesConstants.INIT_SERVICE_INIT_ON_STARTUP);
  
  private static boolean ALWAYS_INIT_ON_STARTUP = PropertiesHolder.getBoolean(PropertiesConstants.INIT_SERVICE_ALWAYS_INIT_ON_STARTUP);
  
  private static boolean COUNTER_TEST = PropertiesHolder.getBoolean(PropertiesConstants.INIT_SERVICE_COUNTER_TEST);
  
  private static boolean INIT_PRODUCT_IMAGES = PropertiesHolder.getBoolean(PropertiesConstants.INIT_SERVICE_INIT_PRODUCT_IMAGES);
  
  
  
  @PostConstruct
  public void init() {
    if (COUNTER_TEST)
      counterTest();
    List<? extends Articolo> list = productAdapter.findAll();
    logger.debug(String.format("ALWAYS_INIT_ON_STARTUP = %s", ALWAYS_INIT_ON_STARTUP));
    logger.debug(String.format("INIT_ON_STARTUP = %s", INIT_ON_STARTUP));
    logger.debug(String.format("products number = %s", (list != null ? list.size() : "null")));
    if (ALWAYS_INIT_ON_STARTUP || (INIT_ON_STARTUP && (list == null || list.size() == 0))) {
//    loadFoldersData();
      initPortalData();
    }
  }
  
  @Override
  public void loadFoldersData() {
    logger.debug("initializing test data...");
    initFoldersData();
  }
  
  public void initPortalData() {
    portalDataMarshaller.load();
//  portalDataMarshaller.marshall();
  }
  
  public void loadPagesData() {
    
  }
  
  @SuppressWarnings("unused")
  private void initCategoriesData() {
    initCounter();
    initProducts();
    initOrders();
  }
  
  private void initFoldersData() {
    initCounter();
    deleteProducts();
  }
  
  private void counterTest() {
    initCounter();
    for (int it = 1001; it < 1010; it++) {
      long count;
      count = generalAdapter.findNextCounterValue();
      logger.debug("new counter is " + count);
      if (count != it)
        throw new RuntimeException("COUNTER TEST ERROR");
    }
  }
  
  
  /*****************************************************************************************
   * XSTREAM INIT
   */
  
  private void printPagesToXml() {
    portalPageAdapter.printPagesToXml();
  }
  
  
  
  /*****************************************************************************************
   * INIT COUNTER
   */
  
  private void initCounter() {
    deleteCounter();
  }
  
  /*****************************************************************************************
   * INIT PRODUCTS
   */

  private void initProducts() {
    deleteProducts();
    
    List<TipoArticolo> productTypes;
    TipoArticolo productType = null;
    Articolo product;
    
    logger.debug("creating product types...");
    
    productType = new TipoArticoloTx();
    productType.setCodice("PROD_TYPE_CODE_" + generalAdapter.findNextCounterValue());
    productType.setCodiceInterno(TipoArticolo.Generic);
    productType = productAdapter.create(productType);
    logger.debug("created " + productType);
    
    productType = new TipoArticoloTx();
    productType.setCodice("PROD_TYPE_CODE_" + generalAdapter.findNextCounterValue());
    productType.setCodiceInterno(TipoArticolo.Stamp);
    productType = productAdapter.create(productType);
    logger.debug("created " + productType);

    productTypes = findProductTypes();
    
    assertCondition(productTypes.size() == 2, "Product Types assert error");
    
    logger.debug("creating products...");
    
    /*
    product = new ProductTx();
    product.setCode("PROD_CODE_" + generalAdapter.findNextCounterValue());
    product.setName("PRODOTTO UNO");
    product.setPrice(123.45f);
    assertCondition(productTypes.size() == 2, "Product Types assert error");
    product.setType(ProductType.Decoder.typeFromCode(productTypes, ProductType.Generic));
    createProduct(product);
    
    product = new ProductTx();
    product.setCode("PROD_CODE_" + generalAdapter.findNextCounterValue());
    product.setName("PRODOTTO DUE");
    product.setPrice(123.45f);
    assertCondition(productTypes.size() == 2, "Product Types assert error");
    product.setType(ProductType.Decoder.typeFromCode(productTypes, ProductType.Generic));
    createProduct(product);

    product = new ProductTx();
    product.setCode("PROD_CODE_" + generalAdapter.findNextCounterValue());
    product.setName("PRODOTTO TRE");
    product.setPrice(123.45f);
    try {
      assertCondition(productTypes.size() == 2, "Product Types assert error");
      product.setType(ProductType.Decoder.typeFromCode(productTypes, ProductType.Stamp));
    } catch (IllegalArgumentException ex) {
      logger.error("error", ex);
    }
    createProduct(product);
    
    product = new ProductTx();
    product.setCode("PROD_CODE_" + generalAdapter.findNextCounterValue());
    product.setName("PRODOTTO QUATTRO");
    product.setPrice(123.45f);
    assertCondition(productTypes.size() == 2, "Product Types assert error");
    product.setType(ProductType.Decoder.typeFromCode(productTypes, ProductType.Stamp));
    createProduct(product);
    
    product = new ProductTx();
    product.setCode("PROD_CODE_" + generalAdapter.findNextCounterValue());
    product.setName("PRODOTTO CINQUE");
    product.setPrice(123.45f);
    assertCondition(productTypes.size() == 2, "Product Types assert error");
    product.setType(ProductType.Decoder.typeFromCode(productTypes, ProductType.Generic));
    createProduct(product);

    product = new ProductTx();
    product.setCode("PROD_CODE_" + generalAdapter.findNextCounterValue());
    product.setName("PRODOTTO SEI");
    product.setPrice(123.45f);
    assertCondition(productTypes.size() == 2, "Product Types assert error");
    product.setType(ProductType.Decoder.typeFromCode(productTypes, ProductType.Generic));
    createProduct(product);
    
    */

    List<Articolo> products = null;
    
    logger.debug("PRODUCT CHECK N. 1");
    products = findProducts();
    assertCondition(products.size() == 6, "Product assert error");
    productTypes = findProductTypes();
    assertCondition(productTypes.size() == 2, "Product Types assert error");
    
    logger.debug("PRODUCT CHECK N. 2");
    products = findProducts();
    if (INIT_PRODUCT_IMAGES) {
      updateProductImages(products);
      findProducts();
    }
    
    logger.debug("PRODUCT CHECK N. 3");
    products = findProducts();
    for (Articolo prod : products) {
      fetchProductImages(prod);
    }
    findProducts();
    
    logger.debug("PRODUCT CHECK N. 4");
    products = findProducts();
    updateProductHtmls(products);

    
  }
  

  
  /*****************************************************************************************
   * INIT PORTAL PAGES
   */

  
  private void internalInitPortalPages() {
    deletePages();
    
    List<PortalPage> pages;
    PortalFolderPage page1 = null;
    PortalPage page2 = null;
    PortalPage page3 = null;
    
    List<Articolo> products = findProducts();
    
    logger.debug("creating pages...");
    
    page1 = new PortalFolderPageTx();
    page1.setName("FOLDER PAGE 1 UNO CON SOTTOCARTELLE");
    page1 = (PortalFolderPage)createPortalPage(page1);
    
      page2 = new WebContentPageTx();
      page2.setName("SOTTOCARTELLA UNO - A");
      page2 = createPortalPage(page2);
      page1.getChildreen().add(page2);
      page1 = (PortalFolderPage)updatePortalPage(page1);
      
      page2 = new WebContentPageTx();
      page2.setName("SOTTOCARTELLA UNO - B");
      page2 = createPortalPage(page2);
      page1.getChildreen().add(page2);
      page1 = (PortalFolderPage)updatePortalPage(page1);
      
      page2 = new WebContentPageTx();
      page2.setName("SOTTOCARTELLA UNO - C");
      page2 = createPortalPage(page2);
      page1.getChildreen().add(page2);
      page1 = (PortalFolderPage)updatePortalPage(page1);
      
    page1 = new PortalFolderPageTx();
    page1.setName("FOLDER PAGE 2 DUE CON SOTTOCARTELLE");
    page1 = (PortalFolderPage)createPortalPage(page1);
    
      page2 = new ProductFolderPageTx();
      page2.setName("SOTTOCARTELLA DUE - A");
      page2 = createPortalPage(page2);
      page1.getChildreen().add(page2);
      page1 = (PortalFolderPage)updatePortalPage(page1);

      page2 = new ProductFolderPageTx();
      page2.setName("SOTTOCARTELLA DUE - B");
      page2 = createPortalPage(page2);
      page1.getChildreen().add(page2);
      page1 = (PortalFolderPage)updatePortalPage(page1);

    page1 = new ProductFolderPageTx();
    page1.setName("FOLDER PAGE 3 TRE CON PRODOTTI");
    page1 = (PortalFolderPage)createPortalPage(page1);

      page2 = createProductPageFromProduct1(products.get(0));
      page2 = createPortalPage(page2);
      page1.getChildreen().add(page2);
      page1 = (PortalFolderPage)updatePortalPage(page1);

      page2 = createProductPageFromProduct1(products.get(1));
      page2 = createPortalPage(page2);
      page1.getChildreen().add(page2);
      page1 = (PortalFolderPage)updatePortalPage(page1);

      page2 = createProductPageFromProduct1(products.get(2));
      page2 = createPortalPage(page2);
      page1.getChildreen().add(page2);
      page1 = (PortalFolderPage)updatePortalPage(page1);

    page1 = new PortalFolderPageTx();
    page1.setName("FOLDER PAGE 4 QUATTRO CON SOTTOCARTELLE");
    page1 = (PortalFolderPage)createPortalPage(page1);
      
      page2 = new ProductFolderPageTx();
      page2.setName("SOTTOCARTELLA QUATTRO / A");
      page2 = createPortalPage(page2);
      page1.getChildreen().add(page2);
      page1 = (PortalFolderPage)updatePortalPage(page1);
      page2 = page1.getChildreen().get(0);

        page3 = createProductPageFromProduct1(products.get(3));
        page3 = createPortalPage(page3);
        ((PortalFolderPage)page2).getChildreen().add(page3);
        page2 = updatePortalPage(page2);
    
        page3 = createProductPageFromProduct1(products.get(4));
        page3 = createPortalPage(page3);
        ((PortalFolderPage)page2).getChildreen().add(page3);
        page2 = updatePortalPage(page2);
      
        page3 = createProductPageFromProduct1(products.get(5));
        page3 = createPortalPage(page3);
        ((PortalFolderPage)page2).getChildreen().add(page3);
        page2 = updatePortalPage(page2);
      
      page2 = new ProductFolderPageTx();
      page2.setName("SOTTOCARTELLA QUATTRO / B");
      page2 = createPortalPage(page2);
      page1.getChildreen().add(page2);
      page1 = (PortalFolderPage)updatePortalPage(page1);
      page2 = page1.getChildreen().get(1);

        page3 = createProductPageFromProduct1(products.get(6));
        page3 = createPortalPage(page3);
        ((PortalFolderPage)page2).getChildreen().add(page3);
        page2 = updatePortalPage(page2);

        page3 = createProductPageFromProduct1(products.get(7));
        page3 = createPortalPage(page3);
        ((PortalFolderPage)page2).getChildreen().add(page3);
        page2 = updatePortalPage(page2);

        page3 = createProductPageFromProduct1(products.get(8));
        page3 = createPortalPage(page3);
        ((PortalFolderPage)page2).getChildreen().add(page3);
        page2 = updatePortalPage(page2);


        
    logger.debug("PAGE CHECK N. 1 (find all)");
    pages = findPortalPages();
    for (PortalPage page : pages) {
      logger.debug("fetched page " + page);
    }
    
    logger.debug("PAGE CHECK N. 2 (find roots + childreen)");
    pages = findRootPortalPages();
    for (PortalPage page : pages) {
      if (page instanceof PortalFolderPage) {
        PortalFolderPage folderPage = (PortalFolderPage)page;
        folderPage = (PortalFolderPage)portalPageAdapter.fetchChildreen(folderPage);
        ((PortalFolderPage)page).setChildreen(folderPage.getChildreen());
      }
      logger.debug("fetched relationships for " + page);
    }
    
    logger.debug("PAGE CHECK N. 3 (find roots + resolve all dependencies)");
    pages = findRootPortalPages();
    for (PortalPage page : pages) {
      portalPageAdapter.resolveAllDependencies(page);
      logger.debug("fetched relationships for " + page);
    }
    
    logger.debug("PAGE CHECK N. 4 (find roots + resolve all dependencies + generate test htmls)");
    pages = findRootPortalPages();
    for (PortalPage page : pages) {
      page = portalPageAdapter.resolveAllDependencies(page);
      page = generateWebContentPageHtmls(page);
      logger.debug("generated html for page " + page);
    }
    
  }
  
  private ProductPage createProductPageFromProduct1(Articolo product) {
    ProductPage page = new ProductPageTx();
    page.setEntity(CloneUtils.clone(product, ArticoloTx.class));
    page.setName(product.getName());
    page.setOrderNm(product.getOrderNm());
    return page;
  }
  
  private ProductPage createProductPageFromProduct2(Articolo product) {
    ProductPage page = CloneUtils.clone(product, ProductPageTx.class);
    return page;
  }
  
  private PortalPage generateWebContentPageHtmls(PortalPage page) {
    if (page instanceof WebContentPage) {
      try {
        HtmlContent content = new HtmlContentTx();
        content.setType(HtmlContent.Type.SHORT);
        content.setContent("<p>testo generato automaticamente a scopo di test</p>");
        page = portalPageAdapter.updateHtmlContent(page.getId(), content);
        logger.debug("updated html for page "+page);
      } catch (Exception ex) {
        logger.error("error", ex);
      }
    }
    if (page instanceof PortalFolderPage) {
      PortalFolderPage portalFolderPage = (PortalFolderPage)page;
      List<PortalPage> childreen = portalFolderPage.getChildreen();
      for (PortalPage child : childreen) {
        generateWebContentPageHtmls(child);
      }
    }
    return page;
  }
  
  
  /*****************************************************************************************
   * INIT ORDERS
   */
  
  private void initOrders() {
    List<Articolo> products;
    List<Order> orders;
    Order order;
    OrderItem orderItem;

    deleteOrders();
    
    products = findProducts();
    
    logger.debug("creating orders...");
    
    order = new OrderTx();
    order.setCode("ORD_CODE_" + generalAdapter.findNextCounterValue());
    order.setDescription("ORDINE NUMERO UNO");
    order.setCreated(new Date());
    
    orderItem = new OrderItemTx();
    orderItem.setProduct(products.get(0));
    order.addItem(orderItem);
    createOrder(order);

    logger.debug("ORDER CHECK N. 1");
    orders = findOrders();
    assertCondition(orders.size() == 1, "Orders assert error");
    findOrderItems();
    findProducts();
    findProductTypes();
    
    orderItem = new OrderItemTx();
    orderItem.setProduct(products.get(1));
    orders.get(0).addItem(orderItem);
    updateOrder(orders.get(0));

    logger.debug("ORDER CHECK N. 2");
    orders = findOrders();
    findOrderItems();
    findProducts();
    findProductTypes();
    
    logger.debug("ORDER CHECK N. 3");
    orders = findOrders();
    order = orders.get(0);
    order = orderAdapter.fetchItems(order);
    logger.debug("fetchItems for " + order);
    findOrderItems();
    findProducts();
    findProductTypes();
  }
  
  
  
  
  
  /**********************************************************************************
   * PRODUCT METHODS
   * 
   */
  
  private List<Articolo> findProducts () {
    List<Articolo> products = productAdapter.findAll();
    for (Articolo product : products) {
      logger.debug("found " + product);
    }
    return products;
  }
  
  private void createProduct(Articolo product) {
    product = productAdapter.create(product);
    logger.debug("created " + product);
  }
  
  private void fetchProductImages (Articolo product) {
    AbstractArticoloDs pds = CloneUtils.clone(product, AbstractArticoloDs.class);
    pds = productAdapter.fetchImages(pds);
    logger.debug("fetch images for " + pds);
  }
  
  private void updateProductImages(List<Articolo> products) {
    for (int it = 0; it < products.size(); it++) {
      fetchProductImages(products.get(it));
      AbstractArticoloDs product = CloneUtils.clone(products.get(it), AbstractArticoloDs.class);
      try {
        // per il momento funziona solo in locale
        // da capire come inizializzare le immagini sull'appengine
        String userdir = System.getProperty("user.dir");
        FileInputStream fis = new FileInputStream(String.format(userdir+"/../../data/images/products/prod%02dmed.jpeg", (it + 1)));
        product.setImageMedium(BlobUtils.inputStreamToBlob(fis));
        productAdapter.updateDs(product, false, false);
        logger.debug("updated "+product);
      } catch (Exception ex) {
        logger.error("error", ex);
      }
    }
  }
  
  private void fetchProductHtmls (Articolo product) {
    AbstractArticoloDs pds = CloneUtils.clone(product, AbstractArticoloDs.class);
    Articolo fetchedProduct = productAdapter.fetchHtmls(pds);
    logger.debug("fetch htmls for " + fetchedProduct);
  }
  
  private void updateProductHtmls(List<Articolo> products) {
    for (int it = 0; it < products.size(); it++) {
//    fetchProductImages(products.get(it));
      AbstractArticoloDs product = CloneUtils.clone(products.get(it), AbstractArticoloDs.class);
      try {
        HtmlContent content = new HtmlContentTx();
        content.setType(HtmlContent.Type.SHORT);
        content.setContent("<p>prova testo</p>");
        productAdapter.updateHtmlContent(product.getId(), content);
        logger.debug("updated "+product);
      } catch (Exception ex) {
        logger.error("error", ex);
      }
    }
  }
  
  private void deleteProducts() {
    logger.debug("deleting products...");
    List<Articolo> products = productAdapter.findAll();
    for (Articolo product : products) {
      productAdapter.delete(product);
    }
    logger.debug("deleting product types...");
    List<TipoArticolo> productTypes = productAdapter.findAllProductTypes();
    for (TipoArticolo type : productTypes) {
      productAdapter.delete(type);
    }
  }

  
  /**********************************************************************************
   * PORTAL USER METHODS
   * 
   */
  
  private void deleteUsers() {
    List<PortalUser> users = portalUserAdapter.findAll();
    for (PortalUser user : users) {
      portalUserAdapter.delete(user);
    }
  }
  
  
  /**********************************************************************************
   * PORTAL PAGE METHODS
   * 
   */
  
  private List<PortalPage> findPortalPages () {
    List<PortalPage> pages = portalPageAdapter.findAll();
    for (PortalPage page : pages) {
      logger.debug("found " + page);
    }
    return pages;
  }
  
  private List<PortalPage> findRootPortalPages () {
    List<PortalPage> pages = portalPageAdapter.findAllRoot();
    for (PortalPage page : pages) {
      logger.debug("found " + page);
    }
    return pages;
  }
  
  private PortalPage createPortalPage(PortalPage page) {
    page = portalPageAdapter.create(page);
    logger.debug("created " + page);
    return page;
  }
  
  private void deletePages() {
    List<PortalPage> pages = portalPageAdapter.findAll();
    for (PortalPage page : pages) {
      portalPageAdapter.delete(page);
    }
  }
  
  private PortalPage updatePortalPage(PortalPage page) {
    page = portalPageAdapter.update(page);
    logger.debug("updated " + page);
    return page;
  }
  
  
  
  /**********************************************************************************
   * ORDER METHODS
   * 
   */
  
  private List<Order> findOrders () {
    List<Order> orders = orderAdapter.findAll();
    for (Order order : orders) {
      logger.debug("found " + order);
    }
    return orders;
  }
  
  private void createOrder(Order order) {
    order = orderAdapter.create(order);
    logger.debug("created " + order);
  }
  
  private void updateOrder(Order order) {
    order = orderAdapter.update(order);
    logger.debug("updated " + order);
  }
  
  private void deleteCounter() {
    logger.debug("deleting counter...");
    generalAdapter.deleteCounter();
  }
  
  private void deleteOrders() {
    logger.debug("deleting orders...");
    List<Order> orders = orderAdapter.findAll();
    for (Order order : orders) {
      orderAdapter.delete(order);
    }
  }
  
  private List<OrderItem> findOrderItems () {
    List<OrderItem> items = orderAdapter.findAllItems();
    for (OrderItem item : items) {
      logger.debug("found " + item);
    }
    return items;
  }
  
  
  
  
  
  private List<TipoArticolo> findProductTypes () {
    List<TipoArticolo> productTypes = productAdapter.findAllProductTypes();
    for (TipoArticolo type : productTypes) {
      logger.debug("found " + type);
    }
    return productTypes;
  }
  
  
  private void assertCondition (boolean condition, String message) {
    if (!condition) {
      throw new AssertionError(message);
    }
  }
  
  
  
}
