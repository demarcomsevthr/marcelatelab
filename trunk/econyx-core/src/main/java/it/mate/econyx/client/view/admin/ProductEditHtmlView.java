package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.HtmlContentEditor;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.ProductEditView;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ProductEditHtmlView extends AbstractAdminTabPage<ProductEditView.Presenter> implements ProductEditView, IsAdminTabPage<ProductEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ProductEditHtmlView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HtmlContentEditor shortHtmlContentEditor;
  @UiField HtmlContentEditor mediumHtmlContentEditor;
  
//private Product product;
  
  public ProductEditHtmlView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    
    if (model instanceof Articolo) {
      Articolo product = (Articolo)model;
      Delegate<Articolo> delegate = new Delegate<Articolo>() {
        public void execute(Articolo product) {
          shortHtmlContentEditor.setModel(product.getHtmlContent(HtmlContent.Type.SMALL));
          mediumHtmlContentEditor.setModel(product.getHtmlContent(HtmlContent.Type.MEDIUM));
        }
      };
      if (product.areHtmlsInitialized()) {
        delegate.execute(product);
      } else {
        getPresenter().fetchHtmls(product, delegate);
      }
    }
    
  }
  
  @Override
  public void updateModel(Object updatedModel, final Delegate<Object> delegate) {
    Articolo updatedProduct = (Articolo)updatedModel;
    getPresenter().updateHtmlContent(updatedProduct, shortHtmlContentEditor.getUpdatedModel(), shortHtmlContentEditor.isContentModified(), new Delegate<Articolo>() {
      public void execute(Articolo updatedProduct) {
        getPresenter().updateHtmlContent(updatedProduct, mediumHtmlContentEditor.getUpdatedModel(), mediumHtmlContentEditor.isContentModified(), new Delegate<Articolo>() {
          public void execute(Articolo updatedProduct) {
            delegate.execute(updatedProduct);
          }
        });
      }
    });
  }
  
}
