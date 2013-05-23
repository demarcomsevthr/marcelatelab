package it.mate.econyx.server.model.impl;

import it.mate.commons.server.dao.EntityRelationshipsResolverHandler;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.commons.server.utils.CollectionUtils;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.HtmlContent.Type;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true", identityType=IdentityType.APPLICATION)
@Inheritance (strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class AbstractWebContentPageDs extends AbstractPortalPageDs implements WebContentPage, EntityRelationshipsResolverHandler {

  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<Key> htmlKeys;
  
  @UnownedRelationship (key="htmlKeys", itemClass=HtmlWebContentDs.class)
  transient List<HtmlWebContentDs> htmls;
  
  
  /****************************************************************************
   * 
   * METODI UTILIZZATI IN OneToManyAdapterSupport
   * 
   */
  
  public boolean hasSavedHtmls() {
    return (htmlKeys != null && htmlKeys.size() > 0);
  }
  
  @Override
  public List<HtmlContent> getHtmls() {
    return htmls != null ? new ArrayList<HtmlContent>(htmls) : null;
  }

  @Override
  @CloneableProperty (targetClass=HtmlWebContentDs.class)
  public void setHtmls(List<HtmlContent> htmls) {
    if (htmls == null) {
      this.htmlKeys = null;
      this.htmls = null;
    } else {
      this.htmlKeys = new ArrayList<Key>();
      this.htmls = new ArrayList<HtmlWebContentDs>();
      for (HtmlContent html : htmls) {
        if (html instanceof HtmlWebContentDs) {
          HtmlWebContentDs htmlDs = (HtmlWebContentDs)html;
          attachHtml(htmlDs);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + html.getClass() + ", do you forget CloneableProperty annotation?");
        }
      }
    }
  }

  public void initializeHtmls() {
    CollectionUtils.traverseCollection(this.htmlKeys);
  }
  
  public HtmlContent getAttachedHtml(HtmlContent html) {
    if (htmls != null) {
      for (HtmlContent attachedHtml : htmls) {
        if (attachedHtml.getType() == html.getType()) {
          return attachedHtml;
        }
      }
    }
    return null;
  }

  public HtmlContent setAttachedHtml(HtmlContent html) {
    HtmlContent attachedHtml = getAttachedHtml(html);
    if (attachedHtml == null) {
      attachedHtml = attachHtml(html);
    } else {
      attachedHtml.setContent(html.getContent());
    }
    return attachedHtml;
  }
  
  public HtmlContent attachHtml(HtmlContent html) {
    if (html instanceof HtmlWebContentDs) {
      HtmlWebContentDs htmlDs = (HtmlWebContentDs)html;
      if (htmls == null)
        htmls = new ArrayList<HtmlWebContentDs>();
      htmls.add(htmlDs);
      if (htmlDs.getKey() != null) {
        if (htmlKeys == null)
          htmlKeys = new ArrayList<Key>();
        htmlKeys.add(htmlDs.getKey());
      }
      htmlDs.setEntity(this);
    }
    return html;
  }
  
  /********************************************************************/

  
  /** METODI NON PIU' UTILIZZATI DALL'ADAPTER **/
  
  @Override
  public HtmlContent getHtmlContent(Type type) {
    if (htmls != null) {
      for (HtmlContent html : htmls) {
        if (html.getType() == type) {
          return html;
        }
      }
    }
    return null;
  }

  @Override
  public boolean areHtmlsInitialized() {
    return htmls != null;
  }

  @Override
  public void ensureHtmls() {
    htmls = new ArrayList<HtmlWebContentDs>();
  }

  /*
   * 
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<HtmlWebContentDs> htmls;

  public void initializeHtmls() {
    CollectionUtils.traverseCollection(this.htmls);
  }

  public boolean areHtmlsInitialized() {
    return htmls != null;
  }
  
  public List<HtmlContent> getHtmls() {
    return htmls != null ? new ArrayList<HtmlContent>(htmls) : null;
  }

  @CloneableProperty (targetClass=HtmlWebContentDs.class)
  public void setHtmls(List<HtmlContent> htmls) {
    if (htmls != null) {
      for (HtmlContent html : htmls) {
        internalSetHtmlContent(html);
      }
    }
  }
  
  public void setHtmlContent(HtmlContent content) {
    HtmlWebContentDs html = getHtmlWebContentDs(content.getType());
    if (html == null) {
      html = new HtmlWebContentDs(content.getType().getCode());
      internalSetHtmlContent(html);
    }
    html.setContent(content.getContent());
  }
  
  @Override
  public HtmlContent getHtmlContent(HtmlContent.Type type) {
    if (htmls != null) {
      for (HtmlContent html : htmls) {
        if (html.getType() == type) {
          return html;
        }
      }
    }
    return null;
  }
  
  public boolean containsHtmlContent(HtmlContent.Type type) {
    return getHtmlContent(type) != null;
  }

  private HtmlWebContentDs getHtmlWebContentDs(HtmlContent.Type type) {
    if (areHtmlsInitialized()) {
      for (HtmlContent html : htmls) {
        if (html.getType() == type) {
          return (HtmlWebContentDs)html;
        }
      }
    }
    return null;
  }
  
  public void ensureHtmls() {
    
  }
  
  private void internalSetHtmlContent (HtmlContent html) {
    if (this.htmls == null) {
      this.htmls = new ArrayList<HtmlWebContentDs>();
    }
    ((List<HtmlWebContentDs>)htmls).add((HtmlWebContentDs)html);
    htmls.get(htmls.size() - 1).setEntity(this);
  }
  
  */
  
}
