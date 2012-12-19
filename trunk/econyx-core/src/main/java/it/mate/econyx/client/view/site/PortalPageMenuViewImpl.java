package it.mate.econyx.client.view.site;

import it.mate.econyx.client.activities.PortalPageActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.view.custom.PortalPageMenuViewCustomizerImpl;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageMenuViewImpl extends AbstractBaseView<PortalPageActivity> {

  public interface ViewUiBinder extends UiBinder<Widget, PortalPageMenuViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapper;
  
  PortalPageMenuViewCustomizerImpl customer = AppClientFactory.Customizer.cast().getPortalPageMenuViewCustomizer();
  
  ComplexPanel menuContainer;
  
  public PortalPageMenuViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    wrapper.setStyleName("navBar");
    // serve per evitare l'effetto flip flop del menu
    menuContainer = customer.getMenuContainerPanel();
    wrapper.add(menuContainer);
    Anchor anchor = new Anchor();
    menuContainer.add(anchor);
  }

  public void setModel(Object model, String tag) {
    if (model instanceof List) {
      wrapper.clear();
      menuContainer = customer.getMenuContainerPanel();
      wrapper.add(menuContainer);
      List<PortalPage> pages = (List<PortalPage>)model;
      for (final PortalPage page : pages) {
        Anchor anchor = new Anchor(SafeHtmlUtils.fromTrustedString(page.getName()));
        anchor.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            getPresenter().goToPage(page);
          }
        });
        menuContainer.add(anchor);
      }
    }
  }
  
}
