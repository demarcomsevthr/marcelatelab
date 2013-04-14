package it.mate.econyx.client.view.site;

import it.mate.econyx.client.view.PortalPageSummaryView;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.portlets.client.ui.BoxContainerPortlet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageSummaryViewImpl extends AbstractBaseView<PortalPageSummaryView.Presenter> implements PortalPageSummaryView {

  public interface ViewUiBinder extends UiBinder<Widget, PortalPageSummaryViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HTML htmlPanel;
  
  public PortalPageSummaryViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void setModel(Object model, String tag) {
    if (model instanceof PortalPage) {
      
      PortalPage page = (PortalPage)model;

      if (page instanceof WebContentPage) {
        WebContentPage webContentPage = (WebContentPage)page;
        HtmlContent content = webContentPage.getHtmlContent(HtmlContent.Type.SMALL);
        Widget boxContainerPortlet = GwtUtils.getParentWidgetByClassAndStyle(this, BoxContainerPortlet.class.getName(), "mwt-BoxContainer");
        if (content != null && content.getContent() != null && content.getContent().trim().length() > 0) {
          boxContainerPortlet.setVisible(true);
          String actualHtmlContent = content.getContent();
          htmlPanel.setHTML(SafeHtmlUtils.fromTrustedString(actualHtmlContent));
        } else {
          boxContainerPortlet.setVisible(false);
        }
      }
      
    }
  }

}
