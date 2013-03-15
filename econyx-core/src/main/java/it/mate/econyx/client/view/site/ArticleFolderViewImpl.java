package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.view.ArticleFolderView;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQueryUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ArticleFolderViewImpl extends AbstractBaseView<ArticleFolderView.Presenter> implements ArticleFolderView {

  public interface ViewUiBinder extends UiBinder<Widget, ArticleFolderViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  interface Style extends CssResource {

  }
  
  @UiField Style style;
  
  @UiField FlexTable articlesTable;
  
  private ArticleFolder articleFolder;
  
  private final static String HTML_CONTAINER_ID = "ecx-htmlContainer";
  
  public ArticleFolderViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  private void initProvided() {
    
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof PortalSessionState) {
      //
    } else if (model instanceof ArticleFolder) {
      this.articleFolder = (ArticleFolder)model;
      articlesTable.clear();
      if (articleFolder.getArticles() != null) {
        int row = 0;
        for (Article article : articleFolder.getArticles()) {
          if (articleFolder.getSelectedArticleCode() != null) {
            if (articleFolder.getSelectedArticleCode().equals(article.getCode())) {
              HTML html = createHtml(article, true);
              articlesTable.setWidget(row++, 0, html);
            }
          } else {
            HTML html = createHtml(article, false);
            articlesTable.setWidget(row++, 0, html);
          }
        }
      }
    }
  }
  
  private HTML createHtml(final Article article, final boolean showWholeArticle) {
    final HTML html = new HTML();
    HtmlContent htmlContent = article.getHtml();
    String actualHtmlContent = htmlContent.getContent();
    html.getElement().setId(HTML_CONTAINER_ID);
    html.getElement().getStyle().setHeight(0, Unit.PX);
    html.setHTML(SafeHtmlUtils.fromTrustedString(actualHtmlContent));
    GwtUtils.deferredExecution(100, new Delegate<Void>() {
      public void execute(Void element) {
        Element readmoreElement = JQueryUtils.selectFirst("[id='cke-readmore']");
        if (readmoreElement != null) {
          if (showWholeArticle) {
            readmoreElement.getStyle().setVisibility(Visibility.HIDDEN);
          } else {
            processCkeReadmore(readmoreElement);
            GwtUtils.addElementEventListener(readmoreElement, Event.ONCLICK, new EventListener() {
              public void onBrowserEvent(Event event) {
                String currentPageCode = AppClientFactory.IMPL.getPortalSessionState().getCurrentPageCode();
                GwtUtils.setLocationHash(article.getCode());
              }
            });
          }
        }
        html.getElement().getStyle().clearHeight();
      }
    });
    return html;
  }
  
  private void processCkeReadmore(Element readmoreElement) {
    Element nextElem = readmoreElement;
    while((nextElem = nextElem.getNextSiblingElement()) != null) {
      nextElem.getStyle().setVisibility(Visibility.HIDDEN);
    }
    Element parentElem = readmoreElement.getParentElement();
    if (!HTML_CONTAINER_ID.equals(parentElem.getId())) {
      processCkeReadmore(parentElem);
    }
  }
  
}
