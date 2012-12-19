package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.PortalUserEditView;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class PortalUserEditViewImpl extends AbstractBaseView<PortalUserEditView.Presenter> implements PortalUserEditView {
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalUserEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) AdminTabPanel<PortalUserEditView.Presenter> adminTab;
  
  public PortalUserEditViewImpl() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Generale")
        .setView(new PortalUserEditGeneralView()));
    
    AdminTabPanel.Section<PortalUserEditView.Presenter> customSection = AppClientFactory.Customizer.cast().getCustomPortalUserEditSection();
    if (customSection != null) {
      sections.add(customSection);
    }
    
    adminTab.setSections(sections);
  }
  
  protected void initProvided() {
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setSaveButtonEnabled(true)) {
      @Override
      public void onSave(Object model) { 
        getPresenter().update((PortalUser)model);
      }
      @Override
      public void onNewModelRequested() { }
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
