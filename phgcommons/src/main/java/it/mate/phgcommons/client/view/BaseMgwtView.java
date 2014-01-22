package it.mate.phgcommons.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
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
    main = new LayoutPanel() {
      @Override
      protected void onUnload() {
        BaseMgwtView.this.onUnload();
        super.onUnload();
      }
    };
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
    
    headerPanel.getElement().setId("mgwtHeaderPanel");
    
    main.addAttachHandler(new AttachEvent.Handler() {
      public void onAttachOrDetach(AttachEvent event) {
        onAttach(event);
      }
    });
    
  }
  
  public void onAttach(AttachEvent event) {

  }
  
  public void onUnload() {
    
  }
  
  public void setTitleHtml (String html) {
    getTitle().setHTML(html);
  }
  
  public Widget asWidget() {
    return main;
  }
  
  protected void initWidget(Widget widget) {
    scrollPanel.setWidget(widget);
  }
  
  public HTML getTitle() {
    return title;
  }
  
  protected ScrollPanel getScrollPanel() {
    return scrollPanel;
  }
  
  public HeaderPanel getHeaderPanel() {
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
  
  protected void initHeaderBackButton(String text, final Delegate<TapEvent> delegate) {
    getHeaderBackButton().setText(text);
    getHeaderBackButton().addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        delegate.execute(event);
      }
    });
  }

  public abstract void setModel(Object model, String tag);  
  
}
