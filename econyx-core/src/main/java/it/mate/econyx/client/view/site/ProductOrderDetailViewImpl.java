package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.model.ArticoloDaOrdinare;
import it.mate.econyx.client.util.UrlUtils;
import it.mate.econyx.client.view.ProductOrderDetailView;
import it.mate.econyx.client.view.ProductView;
import it.mate.econyx.client.view.custom.OrderItemDetailCustomizer;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.ImageContent;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.impl.ArticoloTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.ImageButton;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.ui.SpinnerIntegerBox;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ProductOrderDetailViewImpl extends AbstractBaseView<ProductView.Presenter> implements ProductOrderDetailView {

  public interface ViewUiBinder extends UiBinder<Widget, ProductOrderDetailViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  private OrderItemDetailCustomizer customizer = AppClientFactory.Customizer.cast().getOrderItemDetailCustomizer();
  
  private PortalSessionState portalSessionState;
  
  private Articolo articolo;
  
  @UiField Label screenName;
  @UiField Panel mediumImagePanel;
  @UiField Panel shortHtmlPanel;
  @UiField Panel ordinaPanel;
  
  @UiField SpinnerIntegerBox qtaBox;
  
  @UiField ImageButton cartBtn;
  
  @UiField Panel detailPanel;
  
  public ProductOrderDetailViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    customizer.setDetailPanel(detailPanel);
  }
  
  private void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof PortalSessionState) {
      this.portalSessionState = (PortalSessionState)model;
    } else if (model instanceof ArticoloDaOrdinare) {
      ArticoloDaOrdinare articoloDaOrdinare = (ArticoloDaOrdinare)model;
      setArticolo(articoloDaOrdinare.getArticolo());
      qtaBox.setValue(articoloDaOrdinare.getQuantity().intValue());
    } else if (model instanceof Articolo) {
      setArticolo((Articolo)model);
      qtaBox.setVisible(false);
    }
  }
  
  private void setArticolo(Articolo articolo) {
    this.articolo = articolo;
    screenName.setText(articolo.getName());
    if (articolo instanceof ArticoloTx) {
      ArticoloTx productTx = (ArticoloTx)articolo;
      if (productTx.hasImageOfType(ImageContent.Type.MEDIUM)) {
        Image image = new Image(UrlUtils.getProductImageUrl(articolo.getId(), "medium"));
        image.setWidth("120px");
        image.setHeight("120px");
        mediumImagePanel.add(image);
        mediumImagePanel.add(new Spacer("1px", "10px"));
      }
      if (productTx.hasHtmlOfType(HtmlContent.Type.SHORT)) {
        HTML html = new HTML(SafeHtmlUtils.fromTrustedString(productTx.getHtmlOfType(HtmlContent.Type.SHORT).getContent()));
        shortHtmlPanel.add(html);
        shortHtmlPanel.add(new Spacer("1px", "10px"));
      }
    }
    customizer.setArticolo(articolo);
  }
  
  @UiHandler ("cartBtn")
  public void onCartBtn(ClickEvent event) {
    // 23/11/2012
    List<OrderItemDetail> details = customizer.getDetails();
    Double qta = qtaBox.isVisible() ? (double)qtaBox.getValue() : null;
    getPresenter().orderProduct(articolo, qta, details);
    /*
    if (checkPortalSessionState()) {
      List<OrderItemDetail> details = customizer.getDetails();
      getPresenter().orderProduct(articolo, (double)qtaBox.getValue(), details);
    } else {
      Window.alert("Devi registrarti o inserire il tuo account per procedere");
    }
    */
  }
  
  private boolean checkPortalSessionState() {
    return (portalSessionState != null && portalSessionState.getLoggedUser() != null && portalSessionState.getCustomer() != null);
  }

  
}
