package it.mate.ckd.client.view;

import it.mate.ckd.client.constants.AppConstants;
import it.mate.ckd.client.model.CKD;
import it.mate.ckd.client.view.CKDInputViewWrapper.Presenter;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQueryUtils;
import it.mate.phgcommons.client.utils.OsDetectionPatch;
import it.mate.phgcommons.client.view.BaseMgwtView;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;

public class CKDInputViewWrapper extends BaseMgwtView <Presenter> {
  
  private boolean isTablet = OsDetectionPatch.isTablet();
  
  private CKDInputView inputView;
  private CKDOutputView outputView;

  public interface Presenter extends BasePresenter {
    void goToCkdOutput(CKD ckd);
    void goToHome();
  }

  public CKDInputViewWrapper() {
    initUI();
  }

  private void initUI() {
    
    if (OsDetectionPatch.isTablet()) {
      setTitleHtml(AppConstants.IMPL.tabletAppName());
    } else {
      setTitleHtml(AppConstants.IMPL.phoneAppName());
    }
    
    HorizontalPanel layoutPanel = new HorizontalPanel();
    
    inputView = new CKDInputView();
    layoutPanel.add(inputView.asWidget());

    if (isTablet) {
//    outputView = new CKDOutputView();
//    outputView.setVisible(false);
//    layoutPanel.add(outputView.asWidget());
    }
    
    initWidget(layoutPanel);
    
    getHeaderBackButton().setText("Home");
    getHeaderBackButton().addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        getPresenter().goToHome();
      }
    });

    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        Element tdElem = JQueryUtils.selectFirst(".mgwt-ScrollPanel-container td");
        tdElem.setAttribute("align", "center");
      }
    });
    
  }
  
  @Override
  public void setModel(Object model, String tag) {
    inputView.setModel(model, tag);
  }
  
  @Override
  public void setPresenter(final Presenter presenter) {
    super.setPresenter(presenter);
    
    inputView.setPresenter(new CKDInputView.Presenter() {
      public void goToPrevious() {

      }
      public BaseView getView() {
        return null;
      }
      public void goToHome() {
        presenter.goToHome();
      }
      public void goToCkdOutput(CKD ckd) {
        if (isTablet) {
//        outputView.setVisible(true);
//        outputView.setModel(ckd, null);
          presenter.goToCkdOutput(ckd);
        } else {
          presenter.goToCkdOutput(ckd);
        }
      }
    });
    
  }
  
}
