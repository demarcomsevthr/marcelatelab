package it.mate.econyx.client.view.admin.scaffold;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public abstract class ModelListViewImpl <M extends HasId> extends AbstractBaseView<ModelListView.Presenter<M>> implements ModelListView<M> {

  
  @SuppressWarnings("rawtypes")
  public interface ViewUiBinder extends UiBinder<Widget, ModelListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) AdminTabPanel<ModelListView.Presenter> adminTab;

  public ModelListViewImpl() {

  }
  
  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    populateSections(sections);
    adminTab.setSections(sections);
  }
  
  protected abstract void populateSections(List<AdminTabPanel.Section<Presenter>> sections);
  
  protected void initProvided() {  
    
    AdminTabPanel.Configuration configuration = new AdminTabPanel.Configuration() {
        public void onSave(Object model) { 
          
        }
        public void onNewModelRequested() {
          
        }
        public void onEdit(Object model) {
          getPresenter().edit((M)model);
        }
        public void onDelete(Object model) {
          getPresenter().delete((M)model);
        }
      };
    configuration.setNewButtonEnabled(true);
    configuration.setEditButtonEnabled(true);
    configuration.setDeleteButtonEnabled(true);
      
//  adminTab = new AdminTabPanel<ModelListView.Presenter>(configuration);
    adminTab = createAdminTabPanel(configuration);
    
  }
  
  protected abstract AdminTabPanel<ModelListView.Presenter> createAdminTabPanel(AdminTabPanel.Configuration configuration);
  
  
}
