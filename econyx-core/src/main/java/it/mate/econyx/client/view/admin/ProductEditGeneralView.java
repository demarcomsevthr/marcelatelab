package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.editors.AbstractArticoloEditor;
import it.mate.econyx.client.view.ProductEditView;
import it.mate.econyx.client.view.custom.ProductEditViewCustomizer;
import it.mate.econyx.shared.model.Articolo;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ProductEditGeneralView extends AbstractAdminTabPage<ProductEditView.Presenter> implements ProductEditView, IsAdminTabPage<ProductEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ProductEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  private ProductEditViewCustomizer customizer = AppClientFactory.Customizer.cast().getProductEditViewCustomizer();
  
  @UiField(provided=true) AbstractArticoloEditor editor;
  
  public ProductEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    editor = customizer.getArticoloEditor();
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof Articolo) {
      editor.setModel((Articolo)model);
    }
  }
  
  @Override
  protected void onDetach() {
    super.onDetach();
    System.out.println("detach " + this.getClass());
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    Articolo editedInstance = editor.flushModel();
    Articolo product = (Articolo)model;
    product.setCodice(editedInstance.getCodice());
    product.setName(editedInstance.getName());
    product.setPrezzo(editedInstance.getPrezzo());
    delegate.execute(product);
  }
  
}
