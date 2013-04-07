package it.mate.ckd.client.view;

import it.mate.ckd.client.utils.OsDetectionPatch;
import it.mate.gwtcommons.client.mvp.BasePresenter;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;

public abstract class BaseMgwtView <P extends BasePresenter> {
  
  protected LayoutPanel main;
  protected ScrollPanel scrollPanel;
  protected HeaderPanel headerPanel;
  protected HeaderButton headerBackButton;
  protected HeaderButton headerMainButton;
  protected HTML title;
  
  private P presenter;
  
  public BaseMgwtView() {
    initUI();
  }

  private void initUI() {
    main = new LayoutPanel();
    scrollPanel = new ScrollPanel();
    headerPanel = new HeaderPanel();
    title = new HTML();
    headerPanel.setCenterWidget(title);

    headerBackButton = new HeaderButton();
    headerBackButton.setBackButton(true);
  
    // 21/02/2013
//  headerBackButton.setVisible(!MGWT.getOsDetection().isAndroid());

    headerMainButton = new HeaderButton();
    headerMainButton.setRoundButton(true);

    if (!MGWT.getOsDetection().isPhone()) {
      headerPanel.setLeftWidget(headerMainButton);
//    headerMainButton.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getUtilCss().portraitonly());
    } else {
      headerPanel.setLeftWidget(headerBackButton);
    }

    main.add(headerPanel);
    main.add(scrollPanel);
    
    if (OsDetectionPatch.isTablet()) {
      getTitle().setHTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CKD Risk Calculator");
    } else {
      getTitle().setHTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CKD Risk Calc");
    }
    
  }
  
  public Widget asWidget() {
    return main;
  }
  
  protected void initWidget(Widget widget) {
    scrollPanel.setWidget(widget);
  }
  
  protected HTML getTitle() {
    return title;
  }
  
  protected ScrollPanel getScrollPanel() {
    return scrollPanel;
  }
  
  protected HeaderPanel getHeaderPanel() {
    return headerPanel;
  }
  
  protected HeaderButton getHeaderBackButton() {
    return headerBackButton;
  }
  
  protected P getPresenter() {
    return presenter;
  }

  public void setPresenter(P presenter) {
    this.presenter = presenter;
  }

  public abstract void setModel(Object model, String tag);  
  
}
