package it.mate.econyx.client.view.site;

import it.mate.econyx.client.util.PortalPageClientUtil;
import it.mate.econyx.client.view.PortalPageView;
import it.mate.econyx.shared.model.ArticleFolderPage;
import it.mate.econyx.shared.model.ArticlePage;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQueryUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageViewImpl extends AbstractBaseView<PortalPageView.Presenter> implements PortalPageView {

  public interface ViewUiBinder extends UiBinder<Widget, PortalPageViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField FlexTable childreenTable;
  @UiField HTML htmlPanel;
  @UiField SimplePanel productListPanel;
  @UiField SimplePanel productPanel;
  @UiField SimplePanel articlePanel;
  @UiField FlexTable inlineChildreenTable;
  
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
      
      boolean childreenRendered = false;

      if (page instanceof ProductFolderPage) {
        ProductFolderPage productFolderPage = (ProductFolderPage)page;
        if (!productFolderPage.getHideChildreen()) {
          if (productFolderPage.getChildreen() != null && productFolderPage.getChildreen().size() > 0 &&
              productFolderPage.getChildreen().get(0) instanceof ProductPage) {
            childreenRendered = true;
            String productListHeader = null;
            if (productFolderPage.getParent() != null && productFolderPage.getParent() instanceof PortalFolderPage) {
              PortalFolderPage parentPage = (PortalFolderPage)productFolderPage.getParent();
              if (parentPage.getShowChildreenContent()) {
                productListHeader = "";
              }
            }
            getPresenter().initProductListView(productListPanel, productFolderPage, true, productListHeader);
          }
        }
      }
      
      if (!childreenRendered && page instanceof PortalFolderPage) {
        PortalFolderPage portalFolderPage = (PortalFolderPage)page;
        if (!portalFolderPage.getShowChildreenContent()) {
          if (portalFolderPage.getChildreen() != null) {
            childreenRendered = true;
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
      }
      
      if (page instanceof WebContentPage) {
        boolean renderHtmlContent = true;
        if (page.getParent() != null && page.getParent() instanceof PortalFolderPage) {
          PortalFolderPage parentPage = (PortalFolderPage)page.getParent();
          if (parentPage.getShowChildreenContent()) {
            renderHtmlContent = false;
          }
        }
        if (renderHtmlContent) {
          WebContentPage webContentPage = (WebContentPage)page;
          HtmlContent content = webContentPage.getHtmlContent(HtmlContent.Type.MEDIUM);
          if (content != null && content.getContent() != null) {
            String actualHtmlContent = content.getContent();
            actualHtmlContent += "<div id='"+PortalPageClientUtil.getPageContentRenderFinishedDivId()+"'></div>";
            htmlPanel.setHTML(SafeHtmlUtils.fromTrustedString(actualHtmlContent));
            GwtUtils.deferredExecution(100, new Delegate<Void>() {
              public void execute(Void element) {
                JsArray<Element> elements = JQueryUtils.select("[id^='page$']");
                if (elements != null) {
                  final Element elem = (Element)elements.get(0);
                  GwtUtils.addElementEventListener(elem, Event.ONCLICK, new EventListener() {
                    public void onBrowserEvent(Event event) {
                      String pageCode = elem.getId().split("\\$")[1];
                      PortalPageClientUtil.goToPageByCode(pageCode);
                    }
                  });
                }
              }
            });
          } else {
            String actualHtmlContent = "<div id='"+PortalPageClientUtil.getPageContentRenderFinishedDivId()+"'></div>";
            htmlPanel.setHTML(SafeHtmlUtils.fromTrustedString(actualHtmlContent));
          }
        }
      }
      
      if (page instanceof ProductPage) {
        ProductPage productPage = (ProductPage)page;
        getPresenter().initProductView(productPanel, productPage);
      }
      
      if (page instanceof ArticleFolderPage) {
        ArticleFolderPage articleFolderPage = (ArticleFolderPage)page;
        getPresenter().initArticleFolderPageView(articlePanel, articleFolderPage);
      }
      
      if (page instanceof ArticlePage) {
        ArticlePage articlePage = (ArticlePage)page;
        getPresenter().initArticlePageView(articlePanel, articlePage);
      }
      
      if (!childreenRendered && page instanceof PortalFolderPage) {
        PortalFolderPage portalFolderPage = (PortalFolderPage)page;
        if (portalFolderPage.getShowChildreenContent()) {
          if (portalFolderPage.getChildreen() != null) {
            childreenRendered = true;
            new ChildreenTableUpdater(portalFolderPage, inlineChildreenTable);
          }
        }
      }
      
    }
  }
  
  private class ChildreenTableUpdater {
    int row, col;
    PortalFolderPage portalFolderPage;
    FlexTable childreenTableToUpdate;
    public ChildreenTableUpdater(PortalFolderPage portalFolderPage, FlexTable childreenTable) {
      this.portalFolderPage = portalFolderPage;
      this.childreenTableToUpdate = childreenTable;
      GwtUtils.showWaitPanel();
      updateTable();
      GwtUtils.hideWaitPanel(true);
    }
    private void updateTable() {
      updateNextChild(portalFolderPage.getChildreen().iterator());
    }
    
    private void updateNextChild(final Iterator<PortalPage> it) {
      if (it.hasNext()) {
        final PortalPage childPage = it.next();
        
        col = 0;
        if (childPage instanceof WebContentPage) {
          WebContentPage childContentPage = (WebContentPage)childPage;
          getPresenter().fetchHtmls(childContentPage, new Delegate<WebContentPage>() {
            public void execute(WebContentPage childContentPage) {
              if (childContentPage != null) {
                HtmlContent content = childContentPage.getHtmlContent(HtmlContent.Type.SHORT);
                if (content != null && content.getContent() != null && !content.getContent().equals("")) {
                  HorizontalPanel childHorizontalPanel = new HorizontalPanel();
                  HTML childHtml = new HTML(SafeHtmlUtils.fromTrustedString(content.getContent()));
                  childHorizontalPanel.add(childHtml);
                  childHorizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
                  childreenTableToUpdate.setWidget(row, col, childHorizontalPanel);
                  GwtUtils.setFlexCellColSpan(childreenTableToUpdate, row, col, 2);
                  row++;
                }
              }
              if (childContentPage != null) {
                HtmlContent content = childContentPage.getHtmlContent(HtmlContent.Type.MEDIUM);
                if (content != null && content.getContent() != null && !content.getContent().equals("")) {
                  VerticalPanel childVerticalPanel = new VerticalPanel();
                  HTML childHtml = new HTML(SafeHtmlUtils.fromTrustedString(content.getContent()));
                  childVerticalPanel.add(childHtml);
                  childreenTableToUpdate.setWidget(row, col, childVerticalPanel);
                  String childHtmlContentWidth = PropertiesHolder.getString("client.PortalPageView.childreenTable.childHtmlContent.width");
                  if (childHtmlContentWidth != null) {
                    GwtUtils.setFlexCellWidth(childreenTableToUpdate, row, col, childHtmlContentWidth);
                  }
                  col++;
                }
              }
              initChildPortalPage(childPage, portalFolderPage, row++, col);
              updateNextChild(it);
            }
          });
        } else {
          initChildPortalPage(childPage, portalFolderPage, row++, col);
          updateNextChild(it);
        }
        
      }
    }
    
    private void initChildPortalPage(PortalPage childPage, PortalPage parentPage, int row, int col) {
      SimplePanel childPanel = new SimplePanel();
      childreenTableToUpdate.setWidget(row, col, childPanel);
      getPresenter().initChildPortalPageView(childPanel, childPage, parentPage);
    }
  }

}
