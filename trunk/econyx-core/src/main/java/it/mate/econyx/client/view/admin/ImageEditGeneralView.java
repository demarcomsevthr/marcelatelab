package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.UploadFileDialog;
import it.mate.econyx.client.ui.editors.ImageEditor;
import it.mate.econyx.client.util.UrlUtils;
import it.mate.econyx.client.view.ImageEditView;
import it.mate.econyx.shared.model.Image;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ImageEditGeneralView extends AbstractAdminTabPage<ImageEditView.Presenter> implements ImageEditView, IsAdminTabPage<ImageEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ImageEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField ImageEditor editor;
  @UiField com.google.gwt.user.client.ui.Image img;
  @UiField Button imageUploadBtn;
  
  @UiField TextBox url;
  
  private Image image;
  
  private static final String MODIFY_TEXT_URL = UrlUtils.getThemeResourceUrl("images/common/add.png");
  interface ButtonTemplate extends SafeHtmlTemplates {
    @Template("<img src=\"{0}\"></img> {1}")
    SafeHtml render(SafeUri url, String text);
  }
  private static ButtonTemplate buttonTemplate = GWT.create(ButtonTemplate.class);
  
  public ImageEditGeneralView() {
    initUI();
    imageUploadBtn.setHTML(buttonTemplate.render(UriUtils.fromSafeConstant(MODIFY_TEXT_URL), "Inserisci il file dell'immagine"));
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    this.image = (Image)model;
    editor.setModel(image);
    img.setUrl(UrlUtils.getImageUrl(image.getCode()) + "?v=" + Random.nextInt());
    url.setText("/" + UrlUtils.getRelativeImageUrl(image.getCode()));
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    Image flushedImage = editor.flushModel();
    Image image = (Image)model;
    image.setCode(flushedImage.getCode());
    image.setName(flushedImage.getName());
    image.setOrderNm(flushedImage.getOrderNm());
    delegate.execute(image);
  }
  
  public UploadFileDialog showUploadFileDialog(final Image image) {
    return new UploadFileDialog("image", image.getCode(), "", new Delegate<String>() {
      public void execute(String results) {
        getPresenter().refresh(image);
      }
    });
  }
  
  @UiHandler ("imageUploadBtn")
  public void onImageUploadBtn (ClickEvent event) {
    showUploadFileDialog(image);
  }
  
}
