package it.mate.econyx.client.view.custom;

import it.mate.econyx.client.view.PortalPageExplorerView;
import it.mate.econyx.client.view.PortalPageExplorerView.TreeModel;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.ProductPage;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class PortalPageExplorerViewCustomizerDefaultImpl implements PortalPageExplorerViewCustomizer {
  
  protected PortalPageExplorerView.Presenter presenter;
  
  private Tree tree = null;
  
  public void setPresenter(PortalPageExplorerView.Presenter presenter) {
    this.presenter = presenter;
  }
  
  public void applyModelOnPanel (TreeModel treeModel, Panel panel) {
    
    if (treeModel.parent == null) {
      tree = new Tree();
      panel.add(tree);
      for (PortalPage page : treeModel.childreen) {
        if (isVisibleInExplorer(page)) {
          tree.addItem(createTreeItem(page));
        }
      }
      tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
        public void onSelection(SelectionEvent<TreeItem> event) {
          PortalPage page = (PortalPage)event.getSelectedItem().getUserObject();
          if (page != null) {
            presenter.goToPage(page);
          }
        }
      });
    } else {
      TreeItem parentItem = findItemByPage(null, treeModel.parent);
      if (parentItem != null) {
        for (PortalPage page : treeModel.childreen) {
          if (isVisibleInExplorer(page)) {
            parentItem.addItem(createTreeItem(page));
          }
        }
      }
    }
    
  }
  
  private boolean isVisibleInExplorer(PortalPage page) {
    return page.getVisibleInExplorer() != null ? page.getVisibleInExplorer() : true;
  }

  private TreeItem createTreeItem (PortalPage page) {
    TreeItem item = new TreeItem(SafeHtmlUtils.fromTrustedString(page.getName()));
    item.setUserObject(page);
    boolean retrieveChildreen = true;
    // se sto su una product folder page, se ho solo child product non li mostro nel tree,
    // se trovo un child != product page mostro tutti i child
    if (page instanceof ProductFolderPage) {
      retrieveChildreen = false;
      ProductFolderPage productFolderPage = (ProductFolderPage)page;
      for (PortalPage childPage : productFolderPage.getChildreen()) {
        if (!(childPage instanceof ProductPage)) {
          retrieveChildreen = true;
        }
      }
    }
    if (retrieveChildreen)
      presenter.retrieveChildreen(page);
    return item;
  }
  
  private TreeItem findItemByPage (TreeItem parentItem, PortalPage page) {
    if (parentItem == null) {
      for (int it = 0; it < tree.getItemCount(); it++) {
        TreeItem found = isPageOnItem(tree.getItem(it), page);
        if (found != null) {
          return found;
        }
      }
    } else {
      for (int it = 0; it < parentItem.getChildCount(); it++) {
        TreeItem found = isPageOnItem(parentItem.getChild(it), page);
        if (found != null) {
          return found;
        }
      }
    }
    return null;
  }
  
  private TreeItem isPageOnItem (TreeItem item, PortalPage page) {
    if (item.getUserObject().equals(page)) {
      return item;
    }
    TreeItem found = findItemByPage(item, page);
    if (found != null) {
      return found;
    }
    return null;
  }
  
}
