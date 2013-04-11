package it.mate.econyx.client.view.site;

import it.mate.econyx.client.view.ArticleFolderView;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.ListPanel;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQueryUtils;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ArticleFolderViewImpl extends AbstractBaseView<ArticleFolderView.Presenter> implements ArticleFolderView {

  public interface ViewUiBinder extends UiBinder<Widget, ArticleFolderViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
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
          
          HTML created = new HTML(GwtUtils.dateToString(article.getCreated()));
          created.addStyleName("ecxPostCreated");
          articlesTable.setWidget(row, 0, created);
          row++;
          
          HTML title = new HTML(article.getTitle());
          title.addStyleName("ecxPostTitle");
          articlesTable.setWidget(row, 0, title);
          row++;
          
          HTML body = createHtml(article.getHtml(), article.getCode());
          created.addStyleName("ecxPostBody");
          articlesTable.setWidget(row, 0, body);
          row++;
          
          ListPanel footer = new ListPanel();
          footer.addStyleName("ecxPostFooter");
          articlesTable.setWidget(row, 0, footer);
          row++;
          
          HorizontalPanel footerLine1 = new HorizontalPanel();
          footer.add(footerLine1);
          
          HTML author = new HTML("Inserito da " + article.getAuthor().getScreenName());
          author.addStyleName("ecxPostAuthor");
          footerLine1.add(author);
          
          footerLine1.add(new Spacer("1em"));
          
          HTML commentsCount = new HTML(article.getCommentsCount() + " commenti");
          commentsCount.addStyleName("ecxPostCommentsCount");
          footerLine1.add(commentsCount);
          
          HorizontalPanel footerLine2 = new HorizontalPanel();
          footer.add(footerLine2);
          
          HTML tags = new HTML("Tags: " + article.getTags());
          tags.addStyleName("ecxPostTags");
          footerLine2.add(tags);
          
          
          articlesTable.setWidget(row, 0, new Spacer("1px", "4em"));
          row++;
          
          GwtUtils.setWidgetModel(title, article);
          title.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
              Article article = GwtUtils.getEventSourceWidgetModel(event, Article.class);
              GwtUtils.setLocationHash(article.getCode());
            }
          });
          
        }
      }
    }
  }
  
  private static String getArticleRootElemId (String articleCode) {
    return HTML_CONTAINER_ID+"-Article"+articleCode;
  }
  
  private HTML createHtml(HtmlContent htmlContent, final String articleCode) {
    final HTML html = new HTML();
    String actualHtmlContent = htmlContent.getContent();
    html.getElement().setId(getArticleRootElemId(articleCode));
    html.getElement().getStyle().setHeight(0, Unit.PX);
    html.setHTML(SafeHtmlUtils.fromTrustedString(actualHtmlContent));
    GwtUtils.deferredExecution(100, new Delegate<Void>() {
      public void execute(Void element) {
        
        List<Element> elements = JQueryUtils.selectList("#"+getArticleRootElemId(articleCode)+" #cke-readmore");
        for (Element readmoreElement : elements) {
          processCkeReadmore(readmoreElement);
          GwtUtils.addElementEventListener(readmoreElement, Event.ONCLICK, new EventListener() {
            public void onBrowserEvent(Event event) {
              GwtUtils.setLocationHash(articleCode);
            }
          });
        }

        /*
        JsArray<Element> elements = JQueryUtils.select("#"+getArticleRootElemId(articleCode)+" #cke-readmore");
        for (int it = 0; it < elements.length(); it++) {
          Element readmoreElement = elements.get(it);
          if (readmoreElement != null) {
            processCkeReadmore(readmoreElement);
            GwtUtils.addElementEventListener(readmoreElement, Event.ONCLICK, new EventListener() {
              public void onBrowserEvent(Event event) {
                GwtUtils.setLocationHash(articleCode);
              }
            });
          }
        }
        */
        
        html.getElement().getStyle().clearHeight();
      }
    });
    return html;
  }
  
  private void processCkeReadmore(Element readmoreElement) {
    Element itElem = readmoreElement.getNextSiblingElement();
    while(itElem != null) {
      itElem.getStyle().setHeight(0, Unit.PX);
      itElem.getStyle().setDisplay(Display.NONE);
      itElem = itElem.getNextSiblingElement();
    }
    Element parentElem = readmoreElement.getParentElement();
    if (parentElem.getId() != null && parentElem.getId().startsWith(HTML_CONTAINER_ID)) {
      //NO-OP
    } else {
      processCkeReadmore(parentElem);
    }
  }
  
}
