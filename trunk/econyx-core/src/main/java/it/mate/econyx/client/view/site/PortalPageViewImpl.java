package it.mate.econyx.client.view.site;

import it.mate.econyx.client.view.PortalPageView;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageViewImpl extends AbstractBaseView<PortalPageView.Presenter> implements PortalPageView {

  public interface ViewUiBinder extends UiBinder<Widget, PortalPageViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField FlexTable childreenTable;
  @UiField HTML htmlPanel;
  @UiField SimplePanel productListPanel;
  @UiField SimplePanel productPanel;
  
  public PortalPageViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void setModel(Object model, String tag) {
    if (model instanceof PortalPage) {
      PortalPage page = (PortalPage)model;

      if (page instanceof ProductFolderPage) {
        ProductFolderPage productFolderPage = (ProductFolderPage)page;
        getPresenter().initProductListView(productListPanel, productFolderPage, true);
      } else if (page instanceof PortalFolderPage) {
        PortalFolderPage portalFolderPage = (PortalFolderPage)page;
        if (portalFolderPage.getChildreen() != null) {
          int row = 0;
          for (final PortalPage child : portalFolderPage.getChildreen()) {
            Anchor childAnchor = new Anchor(SafeHtmlUtils.fromTrustedString(child.getName()));
            childAnchor.addClickHandler(new ClickHandler() {
              public void onClick(ClickEvent event) {
                getPresenter().goToPage(child);
              }
            });
            childreenTable.setWidget(row++, 0, childAnchor);
          }
        }
      }
      
      if (page instanceof WebContentPage) {
        WebContentPage webContentPage = (WebContentPage)page;
        HtmlContent content = webContentPage.getHtmlContent(HtmlContent.Type.MEDIUM);
        if (content != null && content.getContent() != null) {
          htmlPanel.setHTML(SafeHtmlUtils.fromTrustedString(content.getContent()));
        }
      }
      
      if (page instanceof ProductPage) {
//      GwtUtils.log(getClass(), "setModel", "ERROR - product page must be showed by ProductActivity!");
        ProductPage productPage = (ProductPage)page;
        getPresenter().initProductView(productPanel, productPage);
      }
      
    }
  }

}
