package it.mate.ckd.client.view;

import it.mate.ckd.client.constants.AppConstants;
import it.mate.ckd.client.factories.AppClientFactory;
import it.mate.ckd.client.ui.theme.custom.CustomMainCss;
import it.mate.ckd.client.ui.theme.custom.CustomTheme;
import it.mate.ckd.client.utils.OsDetectionPatch;
import it.mate.ckd.client.view.HomeView.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQueryUtils;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.ui.client.widget.Button;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    void goToCkdInput();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CustomTheme.CustomBundle bundle;
  @UiField (provided=true) CustomMainCss style;
  
  @UiField Panel wrapperPanel;
  @UiField Button paramBtn;
  @UiField Label devInfo;
  
  public HomeView() {
    bundle = CustomTheme.Instance.get();
    style = bundle.css();
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {

    getHeaderPanel().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    paramBtn.setText(AppConstants.IMPL.HomeView_paramBtn_text());

    devInfo.setVisible(Boolean.parseBoolean(AppConstants.IMPL.HomeView_devInfo_visible()));
    String info = "Width " + Window.getClientWidth();
    info += " Height " + Window.getClientHeight();
    if (OsDetectionPatch.isTabletLandscape()) {
      info += " isTabletLandscape";
    } else if (OsDetectionPatch.isTabletPortrait()) {
      info += " isTabletPortrait";
    } else {
      info += " isPhone";
    }
    devInfo.setText(info);
    
    wrapperPanel.getElement().setId("homeWrapperPanel");
    if (OsDetectionPatch.isTablet()) {
      GwtUtils.onAvailable("homeWrapperPanel", new Delegate<Element>() {
        public void execute(final Element wrapperPanelElem) {
          int wrapperPct = AppClientFactory.IMPL.getWrapperPct();
          int height = Window.getClientHeight() * wrapperPct / 100;
          wrapperPanelElem.getStyle().setHeight(height, Unit.PX);
          int verMargin = ( Window.getClientHeight() - height ) / 2;
          wrapperPanelElem.getStyle().setMarginTop(verMargin, Unit.PX);
          wrapperPanelElem.getStyle().setMarginBottom(verMargin, Unit.PX);
          int width = Window.getClientWidth() * wrapperPct / 100;
          wrapperPanelElem.getStyle().setWidth(width, Unit.PX);
          int horMargin = ( Window.getClientWidth() - width ) / 2;
          wrapperPanelElem.getStyle().setMarginLeft(horMargin, Unit.PX);
          wrapperPanelElem.getStyle().setMarginRight(horMargin, Unit.PX);
          List<Element> spacers = JQueryUtils.selectList(".ckdHomeSpacer");
          for (Element spacer : spacers) {
            int spacerHeight = spacer.getClientHeight() / 2;
            spacer.getStyle().setHeight(spacerHeight, Unit.PX);
          }
        }
      });
    }
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  @UiHandler("paramBtn")
  public void onParamBtn(TouchStartEvent event) {
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().goToCkdInput();
      }
    });
  }

}
