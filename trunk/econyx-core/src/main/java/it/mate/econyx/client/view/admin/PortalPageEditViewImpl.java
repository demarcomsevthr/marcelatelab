package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.PortalPageEditView;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageEditViewImpl extends AbstractBaseView<PortalPageEditView.Presenter> implements PortalPageEditView {
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalPageEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Label nameLabel;
  @UiField (provided=true) AdminTabPanel<PortalPageEditView.Presenter> adminTab;
  
  List<AdminTabPanel.Section<Presenter>> sections;
  
  public PortalPageEditViewImpl() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setSaveButtonEnabled(true)) {
      @Override
      public void onSave(Object model) { 
        getPresenter().update((PortalPage)model);
      }
      @Override
      public void onNewModelRequested() { }
    };
    
  }
  
  private void initSections(PortalPage page) {
    if (sections == null) {
      sections = new ArrayList<AdminTabPanel.Section<PortalPageEditView.Presenter>>();
      sections.add(new AdminTabPanel.Section<Presenter>()
          .setText("Generale")
          .setView(new PortalPageEditGeneralView()));
      if (page instanceof PortalFolderPage) {
        sections.add(new AdminTabPanel.Section<Presenter>()
            .setText("Sottopagine")
            .setView(new PortalPageEditChildreenListView()));
      }
      if (page instanceof WebContentPage) {
        sections.add(new AdminTabPanel.Section<Presenter>()
            .setText("Testi")
            .setView(new PortalPageEditHtmlView()));
      }
      adminTab.setSections(sections);
    }
  }
  
  public void setModel(Object model, String tag) {
    PortalPage page = (PortalPage)model;
    nameLabel.setText("Editing page: " + (page != null ? page.getName() : "new page"));
    initSections(page);
    adminTab.setPresenter(getPresenter());
    adminTab.setModel(model, null);
  }
  
}
