package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.ui.PortalPageOptionsDialog;
import it.mate.econyx.client.view.PortalPageListView;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageListViewImpl extends AbstractBaseView<PortalPageListView.Presenter> implements PortalPageListView {
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalPageListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) AdminTabPanel<PortalPageListView.Presenter> adminTab;
  
  private String width;
  
  private String height;
  
  public PortalPageListViewImpl() {
    this(null, null);
  }
  
  public PortalPageListViewImpl(String width, String height) {
    super();
    this.width = width;
    this.height = height;
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Generale")
        .setView(new PortalPageListGeneralView()));
    adminTab.setSections(sections);
  }
  
  public void setHeight(String height) {
    this.height = height;
    adminTab.setTabHeight(height);
  }
  
  public void setWidth(String width) {
    this.width = width;
    adminTab.setTabWidth(width);
  }
  
  protected void initProvided() {  
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setNewButtonEnabled(true).setWidth(width).setHeight(height).setEditButtonEnabled(true)
        .setDeleteButtonEnabled(true)) {
      public void onSave(Object model) { }
      public void onNewModelRequested() {
        new PortalPageOptionsDialog(new Delegate<PortalPageOptionsDialog.Options>() {
          public void execute(final PortalPageOptionsDialog.Options options) {
            GwtUtils.log(getClass(), "onNewModelRequested", options.getPortalPageType());
            getPresenter().newInstance(options.getPortalPageType(), new Delegate<PortalPage>() {
              public void execute(PortalPage page) {
                page.setName(options.getPortalPageName());
                getPresenter().edit(page);
              }
            });
          }
        });
      }
      public void onEdit(Object model) {
        if (model instanceof PortalPage) {
          getPresenter().edit((PortalPage)model);
        }
      }
      public void onDelete(Object model) {
        PortalPage portalPage = (PortalPage)model;
        getPresenter().delete(portalPage);
      }
    };
  }
  
  @Override
  public void setPresenter(Presenter activity) {
    super.setPresenter(activity);
    adminTab.setPresenter(activity);
  }
  
  public void setModel(Object model, String tag) {
    adminTab.setModel(model, null);
  }
  
}
