package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.PortalDataExportModel;
import it.mate.econyx.server.model.VisitContext;
import it.mate.econyx.server.model.converters.HtmlContentConverter;
import it.mate.econyx.server.model.converters.ImageContentConverter;
import it.mate.econyx.server.model.converters.ProductPageConverter;
import it.mate.econyx.server.model.converters.TipoArticoloConverter;
import it.mate.econyx.server.model.impl.AbstractArticoloDs;
import it.mate.econyx.server.model.impl.ImageDs;
import it.mate.econyx.server.services.ArticleAdapter;
import it.mate.econyx.server.services.CustomerAdapter;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.services.ImageAdapter;
import it.mate.econyx.server.services.OrderAdapter;
import it.mate.econyx.server.services.PortalDataExporter;
import it.mate.econyx.server.services.PortalPageAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
import it.mate.econyx.server.services.ProductAdapter;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.ArticleFolderPage;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.Image;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.UnitaDiMisura;
import it.mate.econyx.shared.model.impl.AbstractIndirizzoTx;
import it.mate.econyx.shared.model.impl.ArticleFolderTx;
import it.mate.econyx.shared.model.impl.ArticleTx;
import it.mate.econyx.shared.model.impl.ArticoloTx;
import it.mate.econyx.shared.model.impl.CustomerTx;
import it.mate.econyx.shared.model.impl.ImageTx;
import it.mate.econyx.shared.model.impl.ModalitaSpedizioneTx;
import it.mate.econyx.shared.model.impl.OrderStateConfigTx;
import it.mate.econyx.shared.model.impl.PortalPageTx;
import it.mate.econyx.shared.model.impl.PortalUserTx;
import it.mate.econyx.shared.model.impl.ProduttoreTx;
import it.mate.econyx.shared.model.impl.UnitaDiMisuraTx;
import it.mate.econyx.shared.model.impl.WebContentPageTx;
import it.mate.gwtcommons.server.utils.BlobUtils;
import it.mate.gwtcommons.server.utils.CloneUtils;
import it.mate.gwtcommons.server.utils.XStreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Blob;
import com.thoughtworks.xstream.XStream;

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
  
  private static final String INIT_FILE = "META-INF/setup-data/portaldata.xml";
  
  private void setupXStream() {
    XStreamUtils.getXStream().setMode(XStream.NO_REFERENCES);
    XStreamUtils.registerConverter(new TipoArticoloConverter());
    XStreamUtils.registerConverter(new HtmlContentConverter());
    XStreamUtils.registerConverter(new ImageContentConverter());
    XStreamUtils.registerConverter(new ProductPageConverter());
    XStreamUtils.getXStream().omitField(PortalPageTx.class, "id");
    XStreamUtils.getXStream().omitField(PortalPageTx.class, "parent");
    XStreamUtils.getXStream().omitField(PortalPageTx.class, "parentId");
    XStreamUtils.getXStream().omitField(WebContentPageTx.class, "htmlsInitialized");
    XStreamUtils.getXStream().useAttributeFor(PortalPageTx.class, "visibleInExplorer");
    XStreamUtils.getXStream().useAttributeFor(PortalPageTx.class, "visibleInMenu");
    XStreamUtils.getXStream().useAttributeFor(PortalPageTx.class, "homePage");
    XStreamUtils.getXStream().omitField(ArticoloTx.class, "id");
//  XStreamUtils.getXStream().omitField(ArticoloTx.class, "htmls");
    XStreamUtils.getXStream().omitField(ArticoloTx.class, "htmlsInitialized");
    XStreamUtils.getXStream().omitField(CustomerTx.class, "id");
    XStreamUtils.getXStream().omitField(AbstractIndirizzoTx.class, "id");
    XStreamUtils.getXStream().omitField(PortalUserTx.class, "id");
    XStreamUtils.getXStream().omitField(UnitaDiMisuraTx.class, "id");
    XStreamUtils.getXStream().omitField(ProduttoreTx.class, "id");
    XStreamUtils.getXStream().omitField(ModalitaSpedizioneTx.class, "id");
    XStreamUtils.getXStream().omitField(ImageTx.class, "id");
    XStreamUtils.getXStream().omitField(OrderStateConfigTx.class, "id");
    XStreamUtils.getXStream().omitField(ArticleFolderTx.class, "id");
    XStreamUtils.getXStream().omitField(ArticleTx.class, "id");
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
      PortalDataExportModel dataModel = (PortalDataExportModel)XStreamUtils.buildGraph(portalDataXml);
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
      PortalDataExportModel dataModel = (PortalDataExportModel)XStreamUtils.buildGraph(portalDataXml);
      ensureProductsDataModel(dataModel);
      load(dataModel);
    } catch (Exception ex) {
      logger.log(Level.SEVERE, "error", ex);
    }
    return null;
  }
  
  private PortalDataExportModel load (PortalDataExportModel dataModel) {
    generalAdapter.deleteAll();
    for (PortalUser user : dataModel.users) {
      visitPortalUser(dataModel, true, user);
    }
    for (Customer customer : dataModel.customers) {
      visitCustomer(dataModel, true, customer);
    }
    for (Image image : dataModel.images) {
      visitImage(dataModel, true, image);
    }
    for (PortalPage page : dataModel.pages) {
      visitPortalPage(dataModel, true, page);
    }
    for (ModalitaSpedizione modalitaSpedizione : dataModel.listaModalitaSpedizione) {
      visitModalitaSpedizione(dataModel, true, modalitaSpedizione);
    }
    for (ModalitaPagamento modalitaPagamento : dataModel.listaModalitaPagamento) {
      visitModalitaPagamento(dataModel, true, modalitaPagamento);
    }
    for (OrderStateConfig orderState : dataModel.orderStates) {
      visitOrderState(dataModel, true, orderState);
    }
    for (Articolo articolo : dataModel.products) {
      visitProduct(dataModel, true, articolo);
    }
    for (ArticleFolder articleFolder : dataModel.articleFolders) {
      visitArticleFolder(dataModel, true, articleFolder);
    }
    return dataModel;
  }
  
  public String unload() {
    String xml = null;
    try {
      PortalDataExportModel model = new PortalDataExportModel();
      model.users = portalUserAdapter.findAll();
      model.customers = customerAdapter.findAll();
      model.pages = portalPageAdapter.findAllRoot();
      model.products = productAdapter.findAll();
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
      setupXStream();
      xml = XStreamUtils.parseGraph(model);
    } catch (Throwable th) {
      logger.log(Level.SEVERE, "error", th);
    }
    return xml;
  }
  
  private void ensureProductsDataModel(PortalDataExportModel dataModel) {
    if (dataModel.products == null || dataModel.products.size() == 0) {
      dataModel.products = productAdapter.findAll();
    }
    dataModel.createProducts = true;
  }
  
  private PortalUser visitPortalUser(VisitContext context, boolean loadMode, PortalUser user) {
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
  
  private OrderStateConfig visitOrderState(VisitContext context, boolean loadMode, OrderStateConfig orderState) {
    if (loadMode) {
      orderState = orderAdapter.create(orderState);
    }
    return orderState;
  }
  
  private Customer visitCustomer(VisitContext context, boolean loadMode, Customer customer) {
    if (loadMode) {
      customer.setPortalUser(visitPortalUser(context, loadMode, customer.getPortalUser()));
      customer = customerAdapter.create(customer);
    }
    return customer;
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
          File imageFile = getImageFileAllowNull(fileName + ".jpg");
          if (imageFile == null) {
            imageFile = getImageFileAllowNull(fileName + ".png");
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
  
  private File getImageFileAllowNull(String filename) {
    File imageFile = null;
    try {
      imageFile = new ClassPathResource( filename ).getFile();
    } catch (Exception ex) {
      return null;
    }
    if (imageFile == null || !imageFile.exists()) {
      return null;
    }
    return imageFile;
  }
  
  private PortalPage visitPortalPage(VisitContext context, boolean loadMode, PortalPage page) {
    if (loadMode) {
      page = portalPageAdapter.create(page);
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
    return page;
  }
  
  private ArticleFolder visitArticleFolder(VisitContext context, boolean loadMode, ArticleFolder articleFolder) {
    PortalDataExportModel model = (PortalDataExportModel)context;
    for (int it = 0; it < model.articleFolders.size(); it++) {
      ArticleFolder cachedArticleFolder = model.articleFolders.get(it);
      if (articleFolder.getCode().equals(cachedArticleFolder.getCode())) {
        if (loadMode && cachedArticleFolder.getId() == null) {
          cachedArticleFolder = createArticleFolder(context, cachedArticleFolder);
          model.articleFolders.set(it, cachedArticleFolder);
        }
        return cachedArticleFolder;
      }
    }
    if (loadMode) {
      articleFolder = createArticleFolder(context, articleFolder);
      model.articleFolders.add(articleFolder);
    }
    return articleFolder;
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
  
  private ArticleFolder createArticleFolder(VisitContext context, ArticleFolder articleFolder) {
    articleFolder = articleAdapter.create(articleFolder);
    return articleFolder;
  }
  
  private PortalDataExportModel cast(VisitContext context) {
    return (PortalDataExportModel)context;
  }
  
}
