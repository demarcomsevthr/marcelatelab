package it.mate.econyx.client.view.site.custom;

import it.mate.econyx.client.util.PortalPageClientUtil;
import it.mate.econyx.client.view.PortalPageExplorerView.TreeModel;
import it.mate.econyx.client.view.custom.PortalPageExplorerViewCustomizerDefaultImpl;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.impl.PortalPageTx;
import it.mate.gwtcommons.client.ui.BulletPanel;
import it.mate.gwtcommons.client.ui.WaitingCursorUtil;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Panel;

public class PortalPageExplorerViewCustomizerImpl extends PortalPageExplorerViewCustomizerDefaultImpl {
  
  
  public class ExplorerItem extends Anchor {

    private PortalPage portalPage;

    private BulletPanel innerPanel;
    
    private BulletPanel parentPanel;
    
    private boolean submenuOpened = false;
    
    private String type;
    
    public ExplorerItem(PortalPage portalPage, SafeHtml html) {
      super(html);
      this.portalPage = portalPage;
      
      this.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          ExplorerItem thisItem = ExplorerItem.this;
          thisItem.submenuOpened = thisItem.innerPanel != null && !thisItem.submenuOpened;
          GwtUtils.log(getClass(), "onClick", "clicked on page " + thisItem.portalPage);
          GwtUtils.log(getClass(), "onClick", "submenuOpen = " + thisItem.submenuOpened);
          if (thisItem.innerPanel != null) {
            thisItem.innerPanel.setVisible(thisItem.submenuOpened);
            if (thisItem.submenuOpened) {
              getParentElement().setClassName("last_menu");
            } else {
              getParentElement().setClassName(type);
              getParentElement().addClassName("segue");
            }
          }
          WaitingCursorUtil.setTargetElement(thisItem.getElement());
          WaitingCursorUtil.setPreviousCursor("pointer");
          PortalPageClientUtil.goToPage(thisItem.portalPage, true);
        }
      });
      
    }
    
    public ExplorerItem(BulletPanel bulletPanel) {
      super();
      this.innerPanel = bulletPanel;
    }
    
    public void setType(String type) {
      this.type = type;
      getParentElement().setClassName(type);
    }
    
    public BulletPanel getInnerPanel() {
      return innerPanel;
    }

    public void setInnerPanel(BulletPanel innerBulletPanel) {
      this.innerPanel = innerBulletPanel;
    }
    
    public PortalPage getPortalPage() {
      return portalPage;
    }
    
    /*
    public void addClassName(String classname) {
      this.getElement().getParentElement().addClassName(classname);
    }
    */
    
    public void addItem(ExplorerItem item) {
      innerPanel.add(item);
      item.parentPanel = innerPanel;
    }
    
    public BulletPanel getParentPanel() {
      return parentPanel;
    }
    
    public void createInnerPanel() {
      BulletPanel bulletPanel = new BulletPanel();
      bulletPanel.setVisible(false);
      this.setInnerPanel(bulletPanel);
      Element liElem = this.getElement().getParentElement().cast();
      this.getParentPanel().addOnItem(getInnerPanel(), liElem);
      getParentElement().addClassName("segue");
    }
    
    private Element getParentElement() {
      return getElement().getParentElement().cast();
    }
    
  }
  
  private ExplorerItem rootItem;

  @Override
  public void applyModelOnPanel(TreeModel treeModel, Panel panel) {
    
    if (treeModel.parent == null) {

      BulletPanel rootPanel = new BulletPanel();
      rootPanel.setStyleName("menu_explorer_sx");
      rootItem = new ExplorerItem(rootPanel);
      
      panel.add(rootItem.getInnerPanel());
      
      for (PortalPage page : treeModel.childreen) {
        if (((PortalPageTx)page).isVisibleInExplorer()) {
          ExplorerItem item = createExplorerItem(page, rootItem);
          item.setType("menu");
        }
      }
      
    } else {
      
      ExplorerItem parentItem = findChildByPage(rootItem, treeModel.parent);
      if (parentItem != null) {

        boolean hasVisibleChilds = false;
        for (PortalPage page : treeModel.childreen) {
          if (((PortalPageTx)page).isVisibleInExplorer()) {
            hasVisibleChilds = true;
            break;
          }
        }
        
        if (parentItem.getInnerPanel() == null && hasVisibleChilds) {
          parentItem.createInnerPanel();
        }
        
        for (PortalPage page : treeModel.childreen) {
          if (((PortalPageTx)page).isVisibleInExplorer()) {
            ExplorerItem item = createExplorerItem(page, parentItem);
            item.setType("submenu2");
          }
        }
        
      }
      
    }
    
  }
  
  private ExplorerItem createExplorerItem (PortalPage page, ExplorerItem parentItem) {
    ExplorerItem item = new ExplorerItem(page, SafeHtmlUtils.fromTrustedString("<span>" + page.getName() + "</span>"));
    parentItem.addItem(item);
    retrieveChildrenIfNeed(page);
    return item;
  }
  
  private void retrieveChildrenIfNeed (PortalPage page) {
    boolean retrieveChildreen = true;
    if (page instanceof ProductFolderPage) {
      retrieveChildreen = false;
      ProductFolderPage productFolderPage = (ProductFolderPage)page;
      for (PortalPage childPage : productFolderPage.getChildreen()) {
        if (!(childPage instanceof ProductPage)) {
          retrieveChildreen = true;
        }
      }
    }
    if (retrieveChildreen) {
      presenter.retrieveChildreen(page);
    }
  }
  
  private ExplorerItem findChildByPage (ExplorerItem parentItem, PortalPage page) {
    if (parentItem.getInnerPanel() != null) {
      BulletPanel innerBulletPanel = parentItem.getInnerPanel();
      for (int it = 0; it < innerBulletPanel.getWidgetCount(); it++) {
        if (innerBulletPanel.getWidget(it) instanceof ExplorerItem) {
          ExplorerItem childItem = (ExplorerItem)innerBulletPanel.getWidget(it);
          if (childItem.getPortalPage().equals(page)) {
            return childItem;
          }
          ExplorerItem nestedItem = findChildByPage(childItem, page);
          if (nestedItem != null) {
            return nestedItem;
          }
        }
      }
    }
    return null;
  }
  
}
