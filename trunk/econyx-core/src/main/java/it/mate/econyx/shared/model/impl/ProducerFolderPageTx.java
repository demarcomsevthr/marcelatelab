package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProducerFolderPage;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.VirtualPage;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.ProducerFolderPageDs")
public class ProducerFolderPageTx extends PortalFolderPageTx implements ProducerFolderPage {

  private ProduttoreTx entity;
  
  @Override
  public Produttore getEntity() {
    return entity;
  }

  @Override
  @CloneableProperty (targetClass=ProduttoreTx.class)
  public void setEntity(Produttore entity) {
    if (entity == null) {
      this.entity = null;
    } else if (entity instanceof ProduttoreTx) {
      this.entity = (ProduttoreTx)entity;
    } else {
      throw new CloneablePropertyMissingException(entity);
    }
  }
  
  @Override
  public List<PortalPage> getChildreen() {
    List<PortalPage> childreen = new ArrayList<PortalPage>();
    if (entity != null && entity.getProducts() != null) {
      for (Articolo product : entity.getProducts()) {
        ProducerProductPageTx productPageTx = new ProducerProductPageTx(product);
        childreen.add(productPageTx);
      }
    }
    return childreen;
  }
  
  @Override
  public void setChildreen(List<PortalPage> childreen) {

  }
  
  public static class ProducerProductPageTx extends ProductPageTx implements VirtualPage {

    public ProducerProductPageTx() {
      super();
    }
    
    public ProducerProductPageTx(Articolo product) {
      this();
      this.setId(ProducerProductPageTx.class.getName()+"@"+product.getCodice());
      this.setEntity(product);
      this.setName(product.getNome());
      this.setCode(product.getCodice());
      this.setHtmls(product.getHtmls());
    }
    
    public static boolean isVirtualPageId(String id) {
      return (id.startsWith(ProducerProductPageTx.class.getName()+"@"));
    }
    
    public static String getEntityCodeFromPageId(String id) {
      return id.substring((ProducerProductPageTx.class.getName()+"@").length());
    }

  }
  
}
