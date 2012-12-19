package it.mate.econyx.client.view.site;

import it.mate.econyx.client.model.ArticoloDaOrdinare;
import it.mate.econyx.client.ui.QuantitaBox;
import it.mate.econyx.client.util.UrlUtils;
import it.mate.econyx.client.view.ProductView;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.ImageContent;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.impl.ArticoloTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.ImageButton;
import it.mate.gwtcommons.client.ui.Spacer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ProductViewImpl extends AbstractBaseView<ProductView.Presenter> implements ProductView {

  public interface ViewUiBinder extends UiBinder<Widget, ProductViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  interface Style extends CssResource {
    String qtaBox();
  }
  
  @UiField Style style;
  
  @UiField Label screenName;
  @UiField Panel mediumImagePanel;
  @UiField Panel shortHtmlPanel;
  @UiField Panel mediumHtmlPanel;
  @UiField Panel ordinaPanel;
  @UiField ImageButton cartBtn;
  
  @UiField HorizontalPanel qtaBoxPanel;
  // 23/11/2012
//@UiField SpinnerIntegerBox qtaBox;
  
  /* 30/11/2012
  SpinnerIntegerBox integerQtaBox;
  DecimalBox decimalQtaBox;
  */
  QuantitaBox quantitaBox;
  
  private Articolo articolo;
  
  public ProductViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));

  }
  
  private void initProvided() {
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof PortalSessionState) {

    } else if (model instanceof Articolo) {
      this.articolo = (Articolo)model;
      screenName.setText(articolo.getName());
      if (articolo instanceof ArticoloTx) {
        ArticoloTx productTx = (ArticoloTx)articolo;
        if (productTx.hasImageOfType(ImageContent.Type.MEDIUM)) {
          Image image = new Image(UrlUtils.getProductImageUrl(articolo.getId(), "medium"));
          mediumImagePanel.add(image);
          mediumImagePanel.add(new Spacer("1px", "10px"));
        }
        if (productTx.hasHtmlOfType(HtmlContent.Type.SHORT)) {
          HTML html = new HTML(SafeHtmlUtils.fromTrustedString(productTx.getHtmlOfType(HtmlContent.Type.SHORT).getContent()));
          shortHtmlPanel.add(html);
          shortHtmlPanel.add(new Spacer("1px", "10px"));
        }
        if (productTx.hasHtmlOfType(HtmlContent.Type.MEDIUM)) {
          HTML html = new HTML(SafeHtmlUtils.fromTrustedString(productTx.getHtmlOfType(HtmlContent.Type.MEDIUM).getContent()));
          mediumHtmlPanel.add(html);
          mediumHtmlPanel.add(new Spacer("1px", "10px"));
        }
      }
      
      if (articolo.getUnitaDiMisura() != null) {
        createQtaBox(articolo.getUnitaDiMisura().getNome(), articolo.getUnitaDiMisura().getDecimali());
      } else {
        createQtaBox("PZ", 0);
      }
      
    }
  }
  
  private void createQtaBox(String um, int decimali) {
    Double initialValue = (decimali > 0 ? null : 1d);
    quantitaBox = new QuantitaBox(initialValue, um, decimali, qtaBoxPanel, style.qtaBox());
    /* 30/11/2012
    Label umLab = new Label(um);
    umLab.setHeight("100%");
    GwtUtils.setStyleAttribute(umLab, "verticalAlign", "middle");
    qtaBoxPanel.add(umLab);
    qtaBoxPanel.setCellVerticalAlignment(umLab, HasVerticalAlignment.ALIGN_MIDDLE);
    qtaBoxPanel.add(new Spacer("4px"));
    if (decimali > 0) {
      decimalQtaBox = new DecimalBox();
      decimalQtaBox.setValue(1d);
      decimalQtaBox.setWidth("3em");
      decimalQtaBox.addStyleName(style.qtaBox());
      qtaBoxPanel.add(decimalQtaBox);
    } else {
      integerQtaBox = new SpinnerIntegerBox(1);
      integerQtaBox.setWidth("3em");
      integerQtaBox.addStyleName(style.qtaBox());
      qtaBoxPanel.add(integerQtaBox);
    }
    */
  }
  
  
  @UiHandler ("cartBtn")
  public void onCartBtn(ClickEvent event) {
    
    Double qta = null;

    /* 30/11/2012
    if (integerQtaBox != null) {
      qta = (double)integerQtaBox.getValue();
    } else {
      qta = decimalQtaBox.getValue();
    }
    */
    qta = quantitaBox.getQuantita();
    if (qta == null) {
      return;
    }
    
    ArticoloDaOrdinare articoloDaOrdinare = new ArticoloDaOrdinare();
    articoloDaOrdinare.setArticolo(articolo);
    articoloDaOrdinare.setQuantity(qta);

    // 23/11/2012
    getPresenter().orderProduct(articoloDaOrdinare, qta, null);
    /*
    getPresenter().goToProductOrderDetailView(articoloDaOrdinare);
    */
    
  }
}
