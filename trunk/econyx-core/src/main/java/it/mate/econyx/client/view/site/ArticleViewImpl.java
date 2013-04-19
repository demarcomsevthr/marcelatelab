package it.mate.econyx.client.view.site;

import it.mate.econyx.client.ui.PostViewer;
import it.mate.econyx.client.view.ArticleView;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleComment;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.Post;
import it.mate.econyx.shared.model.PostComment;
import it.mate.econyx.shared.model.impl.ArticleCommentTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ArticleViewImpl extends AbstractBaseView<ArticleView.Presenter> implements ArticleView {

  public interface ViewUiBinder extends UiBinder<Widget, ArticleViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HTML articleHtml;
  @UiField Panel articlePanel;
  
  private Article article;
  
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
      articlePanel.clear();
      articlePanel.add(new PostViewer((Post)article, false, true).setPresenter(new PostViewer.Presenter() {
        public PostComment newPostCommentInstance() {
          return new ArticleCommentTx();
        }
        public void addComment(String id, PostComment comment) {
          getPresenter().addCommentToArticle(article.getId(), (ArticleComment)comment);
        }
        public void onPostSelected(Post post) {
          
        }
      }));
    }
  }
  
}
