package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.activities.PortalPageActivity;
import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.PortalPageEditView;
import it.mate.econyx.client.view.PortalPageListView;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

public class PortalPageEditChildreenListView extends AbstractAdminTabPage<PortalPageEditView.Presenter> implements PortalPageEditView, IsAdminTabPage<PortalPageEditView.Presenter> {
  
  private PortalPageListView childreenListView;
  
  private PortalFolderPage portalFolderPage;
  
  public PortalPageEditChildreenListView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(childreenListView.asWidget());
  }
  
  protected void initProvided() {
    
    childreenListView = new PortalPageListViewImpl("640px", "480px");
    
    childreenListView.setPresenter(new PortalPageListView.Presenter() {
      public BaseView getView() {
        return null;
      }
      public void goToPrevious() {  }
      public void edit(PortalPage page) {
        GwtUtils.log(getClass(), "edit", "selected child page " + page);
        PortalPageEditChildreenListView.this.getPresenter().edit(page);
      }
      public void show(PortalPage page) {  }
      public void retrieveChildreen(PortalPage parent) {  }
      public void goToPage(PortalPage page) {  }
      public void newInstance(String classname, final Delegate<PortalPage> delegate) {  
        if (PortalPageEditChildreenListView.this.getPresenter() instanceof PortalPageActivity && portalFolderPage != null) {
          PortalPageActivity portalPageActivity = (PortalPageActivity)PortalPageEditChildreenListView.this.getPresenter();
          portalPageActivity.newInstance(classname, new Delegate<PortalPage>() {
            public void execute(PortalPage newPage) {
              newPage.setParentId(portalFolderPage.getId());
              delegate.execute(newPage);
            }
          });
        }
      }
      public void delete(PortalPage page) {
        if (PortalPageEditChildreenListView.this.getPresenter() instanceof PortalPageActivity) {
          PortalPageActivity portalPageActivity = (PortalPageActivity)PortalPageEditChildreenListView.this.getPresenter();
          portalPageActivity.delete(page);
        }
      }
    });
    
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof PortalFolderPage) {
      this.portalFolderPage = (PortalFolderPage)model;
      if (portalFolderPage.getChildreen() != null) {
        childreenListView.setModel(portalFolderPage.getChildreen(), null);
      }
    }
  }
  
  @Override
  protected void onAttach() {
    super.onAttach();
    GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        int w = PortalPageEditChildreenListView.this.getParent().getOffsetWidth() - 60;
        int h = PortalPageEditChildreenListView.this.getParent().getOffsetHeight() - 60;
        if (w > 0 && h > 0) {
          childreenListView.setWidth(w+"px");
          childreenListView.setHeight(h+"px");
        }
      }
    });
  }

  @Override
  public void updateModel(Object model, final Delegate<Object> delegate) {
    PortalPage page = (PortalPage)model;
    delegate.execute(page);
  }
  
}
