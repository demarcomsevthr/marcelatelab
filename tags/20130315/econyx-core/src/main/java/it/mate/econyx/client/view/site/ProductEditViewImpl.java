package it.mate.econyx.client.view.site;

import it.mate.econyx.client.view.ProductEditView;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class ProductEditViewImpl extends AbstractBaseView<ProductEditView.Presenter> implements ProductEditView {

  public interface ViewUiBinder extends UiBinder<Widget, ProductEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  private PortalSessionState portalSessionState;
  
  public ProductEditViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void setModel(Object model, String tag) {
    if (model instanceof PortalSessionState) {
      this.portalSessionState = (PortalSessionState)model;
    }
  }
  
}
