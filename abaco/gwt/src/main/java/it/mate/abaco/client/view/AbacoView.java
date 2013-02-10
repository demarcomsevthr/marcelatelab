package it.mate.abaco.client.view;

import it.mate.abaco.client.view.AbacoView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.Spacer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class AbacoView extends BaseMgwtView<Presenter> {

  public interface Presenter extends BasePresenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, AbacoView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  
  public AbacoView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getTitle().setHTML("IRC Test Demo");
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(new Spacer("0.8em"));
    hp.add(new Image(UriUtils.fromTrustedString("images/kidneys1tr.jpg")));
    getHeaderPanel().setLeftWidget(hp);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    // from mgwt quirks:
//  getScrollPanel().setUsePos(MGWT.getOsDetection().isAndroid());
    
  }
  
  @Override
  public void setModel(Object model, String tag) {

  }

}
