package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.view.ArticleView;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleComment;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.impl.ArticleCommentTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQueryUtils;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ArticleViewImpl extends AbstractBaseView<ArticleView.Presenter> implements ArticleView {

  public interface ViewUiBinder extends UiBinder<Widget, ArticleViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HTML articleHtml;
  
  @UiField FlexTable commentsTable;
  
  @UiField TextArea commentArea;
  
  private Article article;
  
  private final static String HTML_CONTAINER_ID = "ecx-htmlContainer";
  
  public ArticleViewImpl() {
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
    } else if (model instanceof Article) {
      this.article = (Article)model;
      updateHtml(articleHtml, article);
      
      commentsTable.clear();
      if (article.getComments() != null) {
        for (int it = 0; it < article.getComments().size(); it++) {
          int row = it * 3;
          ArticleComment comment = article.getComments().get(it);
          commentsTable.setWidget(row, 0, new Label(comment.getAuthor().getScreenName() + " ha scritto:"));
          commentsTable.setWidget(row + 1, 0, new HTML(comment.getContent()));
          commentsTable.setWidget(row + 2, 0, new Label("postato il " + GwtUtils.dateToString(comment.getPosted(), "dd/MM/yyyy HH:mm")));
        }
      }
      
    }
  }
  
  private HTML updateHtml(final HTML html, final Article article) {
    HtmlContent htmlContent = article.getHtml();
    String actualHtmlContent = htmlContent.getContent();
    html.getElement().setId(HTML_CONTAINER_ID);
    html.getElement().getStyle().setHeight(0, Unit.PX);
    html.setHTML(SafeHtmlUtils.fromTrustedString(actualHtmlContent));
    GwtUtils.deferredExecution(100, new Delegate<Void>() {
      public void execute(Void element) {
        Element readmoreElement = JQueryUtils.selectFirst("[id='cke-readmore']");
        if (readmoreElement != null) {
          readmoreElement.getStyle().setVisibility(Visibility.HIDDEN);
        }
        html.getElement().getStyle().clearHeight();
      }
    });
    return html;
  }

  @UiHandler ("addCommentBtn")
  public void onAddCommentBtn(ClickEvent event) {
    if (AppClientFactory.IMPL.getPortalSessionState().getLoggedUser() == null) {
      Window.alert("Devi effettuare il login per poter inserire un commento");
      return;
    }
    ArticleComment comment = new ArticleCommentTx();
    comment.setContent(commentArea.getText());
    comment.setAuthor(AppClientFactory.IMPL.getPortalSessionState().getLoggedUser());
    comment.setPosted(new Date());
    getPresenter().addCommentToArticle(article.getId(), comment);
  }
  
}
