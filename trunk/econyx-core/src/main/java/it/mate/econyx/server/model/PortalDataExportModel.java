package it.mate.econyx.server.model;

import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.Image;
import it.mate.econyx.shared.model.ModalitaPagamento;
import it.mate.econyx.shared.model.ModalitaSpedizione;
import it.mate.econyx.shared.model.Order;
import it.mate.econyx.shared.model.OrderStateConfig;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.UnitaDiMisura;

import java.util.ArrayList;
import java.util.List;

public class PortalDataExportModel implements VisitContext {
  public static final int LOAD_METHOD_ALL = 0;
  public static final int LOAD_METHOD_ORDERS = 1;
  public int loadMethod;
  public boolean createProducts = false;
  public boolean checkImageResources = false;
  public boolean checkDocumentResources = false;
  public List<PortalUser> users = new ArrayList<PortalUser>();
  public List<Customer> customers = new ArrayList<Customer>();
  public List<Articolo> products = new ArrayList<Articolo>();
  public List<TipoArticolo> productTypes = new ArrayList<TipoArticolo>();
  public List<UnitaDiMisura> unitOfMeasures = new ArrayList<UnitaDiMisura>();
  public List<PortalPage> pages;
  public List<Produttore> producers = new ArrayList<Produttore>();
  public List<Image> images = new ArrayList<Image>();
  public List<ModalitaSpedizione> listaModalitaSpedizione = new ArrayList<ModalitaSpedizione>();
  public List<ModalitaPagamento> listaModalitaPagamento = new ArrayList<ModalitaPagamento>();
  public List<OrderStateConfig> orderStates = new ArrayList<OrderStateConfig>();
  public List<ArticleFolder> articleFolders;
  public List<DocumentFolder> documentFolders;
  public List<Blog> blogs;
  public List<Order> orders;
  public Object customData;
}
