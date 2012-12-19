package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.ProductAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.services.ProductService;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@Service (".productService")
public class ProductServiceImpl extends RemoteServiceServlet implements ProductService {

  private static Logger logger = Logger.getLogger(ProductServiceImpl.class);
  
  private ProductAdapter adapter;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getProductAdapter();
    logger.debug("initialized " + this);
  }

  @Override
  public List<Articolo> findAll() {
    return adapter.findAll();
  }

  @Override
  public Articolo update(Articolo entity) {
    return adapter.update(entity);
  }

  @Override
  public void delete(Articolo entity) {
    adapter.delete(entity);
  }

  @Override
  public Articolo create(Articolo entity) {
    return adapter.create(entity);
  }

  @Override
  public Articolo findById(String id) {
    return adapter.findById(id);
  }

  @Override
  public Articolo fetchHtmls(Articolo product) {
    return adapter.fetchHtmls(product);
  }

  @Override
  public Articolo updateHtmlContent(String productId, HtmlContent content) {
    return adapter.updateHtmlContent(productId, content);
  }

  @Override
  public List<Produttore> findAllProducers() {
    return adapter.findAllProducers();
  }

}
