package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.UploadFileDialog;
import it.mate.econyx.client.util.UrlUtils;
import it.mate.econyx.client.view.ProductEditView;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.ImageContent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public abstract class ProductEditImagesView extends AbstractAdminTabPage<ProductEditView.Presenter> implements ProductEditView, IsAdminTabPage<ProductEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ProductEditImagesView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Image smallImg;
  @UiField Button smallImageUploadBtn;
  
  @UiField Image mediumImg;
  @UiField Button mediumImageUploadBtn;
  
  private Articolo product;
  
  public abstract UploadFileDialog showUploadFileDialog(Articolo product, String tipo);
  
  public ProductEditImagesView() {
    initUI();
  }

  private static final String MODIFY_TEXT_URL = UrlUtils.getThemeResourceUrl("images/common/add.png");
  interface ButtonTemplate extends SafeHtmlTemplates {
    @Template("<img src=\"{0}\"></img> {1}")
    SafeHtml render(SafeUri url, String text);
  }
  private static ButtonTemplate buttonTemplate = GWT.create(ButtonTemplate.class);
  
  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    smallImageUploadBtn.setHTML(buttonTemplate.render(UriUtils.fromSafeConstant(MODIFY_TEXT_URL), "Carica file"));
    mediumImageUploadBtn.setHTML(buttonTemplate.render(UriUtils.fromSafeConstant(MODIFY_TEXT_URL), "Carica file"));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof Articolo) {
      this.product = (Articolo)model;
      smallImg.setUrl(UrlUtils.getProductImageUrl(this.product.getId(), ImageContent.Type.SMALL.name()));
      mediumImg.setUrl(UrlUtils.getProductImageUrl(this.product.getId(), ImageContent.Type.MEDIUM.name()));
    }
  }
  
  @UiHandler ("smallImageUploadBtn")
  public void onSmallImageUploadBtn (ClickEvent event) {
    showUploadFileDialog(product, ImageContent.Type.SMALL.name());
  }
  
  @UiHandler ("mediumImageUploadBtn")
  public void onMediumImageUploadBtn (ClickEvent event) {
    showUploadFileDialog(product, ImageContent.Type.MEDIUM.name());
  }
  
}
