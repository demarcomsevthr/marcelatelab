package it.mate.gpg.client.view;

import it.mate.gpg.client.factories.AppClientFactory;
import it.mate.gpg.client.ui.MvpPhonePanel;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;

public class LayoutView extends Composite {

  protected LayoutPanel main;
  protected ScrollPanel scrollPanel;
  protected HeaderPanel headerPanel;
  protected HeaderButton headerBackButton;
  protected HeaderButton headerMainButton;
  protected HTML title;
  
  protected MvpPhonePanel mvpPanel;
  

  public LayoutView() {
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
    headerBackButton.setVisible(!MGWT.getOsDetection().isAndroid());

    headerMainButton = new HeaderButton();
    headerMainButton.setRoundButton(true);

    if (!MGWT.getOsDetection().isPhone()) {
      headerPanel.setLeftWidget(headerMainButton);
      headerMainButton.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getUtilCss().portraitonly());
    } else {
      headerPanel.setLeftWidget(headerBackButton);
    }

    main.add(headerPanel);
    main.add(scrollPanel);
    
    mvpPanel = new MvpPhonePanel();
//  scrollPanel.add(mvpPanel);
    scrollPanel.setWidget(mvpPanel);
    
    initWidget(main);

  }
  
  public MvpPhonePanel getMvpPanel() {
    return mvpPanel;
  }
  
  public void initMvp(AppClientFactory clientFactory, BaseActivityMapper activityMapper) {
    mvpPanel.initMvp(clientFactory, activityMapper);
  }

}
