package it.mate.ckd.client.view;

import it.mate.ckd.client.constants.AppConstants;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.view.CKDOutputViewWrapper.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;

public class CKDOutputViewWrapper extends BaseMgwtView <Presenter> {

  private CKDOutputView outputView;
  
  public interface Presenter extends BasePresenter {
    void goToCkdInput();
    void goToProtocolStep(ProtocolStep protocolStep);
    void goToExtendedView(String selectedGFR);
    /*
    void goToReferralDecision();
    */
    void openHelpPage();
    boolean applyCKD(CKD ckd, double gfr, Label gfrBox, Label gfrStadiumBox, Label riskBox, Panel riskPanel, Double overhead);
  }

  public CKDOutputViewWrapper() {
    initUI();
  }

  private void initUI() {
    
    if (OsDetectionUtils.isTablet()) {
      setTitleHtml(AppConstants.IMPL.tabletAppName());
    } else {
      setTitleHtml(AppConstants.IMPL.phoneAppName());
    }
    
    outputView = new CKDOutputView();
    
    initWidget(outputView.asWidget());
    
    getHeaderBackButton().setText(AppConstants.IMPL.CKDOutputView_headerBackButton_text());
    getHeaderBackButton().addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        getPresenter().goToCkdInput();
      }
    });
    
    
    HeaderButton helpBtn = new HeaderButton();
    helpBtn.setText("?");
    getHeaderPanel().setRightWidget(helpBtn);
    helpBtn.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        getPresenter().openHelpPage();
      }
    });
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    outputView.setModel(model, tag);
  }
  
  @Override
  public void setPresenter(final Presenter presenter) {
    super.setPresenter(presenter);
    outputView.setPresenter(new CKDOutputView.Presenter() {
      public void goToPrevious() {
        
      }
      public BaseView getView() {
        return null;
      }
      public void goToCkdInput() {
        presenter.goToCkdInput();
      }
      public void goToProtocolStep(ProtocolStep protocolStep) {
        presenter.goToProtocolStep(protocolStep);
      }
      public void openHelpPage() {
        presenter.openHelpPage();
      }
      public void goToExtendedView(String selectedGFR) {
        presenter.goToExtendedView(selectedGFR);
      }
      public boolean applyCKD(CKD ckd, double gfr, Label gfrBox, Label gfrStadiumBox, Label riskBox, Panel riskPanel, Double overhead) {
        return presenter.applyCKD(ckd, gfr, gfrBox, gfrStadiumBox, riskBox, riskPanel, overhead);
      }
    });
  }
  
}
