package it.mate.ckd.client.view;

import it.mate.ckd.client.constants.AppConstants;
import it.mate.ckd.client.model.ProtocolStep;
import it.mate.ckd.client.view.CKDOutputViewWrapper.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.phgcommons.client.utils.OsDetectionPatch;
import it.mate.phgcommons.client.view.BaseMgwtView;

import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;

public class CKDOutputViewWrapper extends BaseMgwtView <Presenter> {

  private CKDOutputView outputView;
  
  public interface Presenter extends BasePresenter {
    void goToCkdInput();
    void goToProtocolStep(ProtocolStep protocolStep);
    void goToReferralDecision();
  }

  public CKDOutputViewWrapper() {
    initUI();
  }

  private void initUI() {
    
    if (OsDetectionPatch.isTablet()) {
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
      public void goToReferralDecision() {
        presenter.goToReferralDecision();
      }
    });
  }
  
}
