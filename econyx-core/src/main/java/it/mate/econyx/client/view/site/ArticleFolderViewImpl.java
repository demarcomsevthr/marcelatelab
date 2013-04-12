package it.mate.econyx.client.view.site;

import it.mate.econyx.client.ui.PostViewer;
import it.mate.econyx.client.view.ArticleFolderView;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.Post;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class ArticleFolderViewImpl extends AbstractBaseView<ArticleFolderView.Presenter> implements ArticleFolderView {

  public interface ViewUiBinder extends UiBinder<Widget, ArticleFolderViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField FlexTable articlesTable;
  
  private ArticleFolder articleFolder;
  
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
          PostViewer postViewer = new PostViewer((Post)article, true, false);
          articlesTable.setWidget(row, 0, postViewer);
          row++;
        }
      }
    }
  }
  
}
