package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.ui.UploadFileDialog;
import it.mate.econyx.client.view.ProductEditView;
import it.mate.econyx.shared.model.Articolo;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ProductEditViewImpl extends AbstractBaseView<ProductEditView.Presenter> implements ProductEditView {
  
  public interface ViewUiBinder extends UiBinder<Widget, ProductEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) AdminTabPanel<ProductEditView.Presenter> adminTab;
  
  public ProductEditViewImpl() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Generale")
        .setView(new ProductEditGeneralView()));
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Testi")
        .setView(new ProductEditHtmlView()));
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Immagini")
        .setView(new ProductEditImagesView() {
          @Override
          public UploadFileDialog showUploadFileDialog(final Articolo product, String size) {
            return new UploadFileDialog("product", product.getId(), size, new Delegate<String>() {
              public void execute(String results) {
                getPresenter().refresh(product);
              }
            });
          }
          public void updateModel(Object model, Delegate<Object> delegate) {
            delegate.execute(model);
          }
        }));
    adminTab.setSections(sections);
  }
  
  protected void initProvided() {
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setSaveButtonEnabled(true)) {
      @Override
      public void onSave(Object model) { 
        getPresenter().update((Articolo)model);
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
