package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.HtmlContentEditor;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.ArticleEditView;
import it.mate.econyx.shared.model.Article;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ArticleEditHtmlView extends AbstractAdminTabPage<ArticleEditView.Presenter> implements ArticleEditView, IsAdminTabPage<ArticleEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleEditHtmlView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HtmlContentEditor htmlContentEditor;
  
  private Article article;
  
  public ArticleEditHtmlView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        int height = getAvailableHeight();
        int width = getAvailableWidth();
        /*
        if (height > 0 && width > 0) {
          if (htmlContentEditor.isEmpty()) {
            htmlContentEditor.setViewerHeight(0);
          } else {
            htmlContentEditor.setViewerHeight(height / 5);
          }
          htmlContentEditor.setEditorHeight(height / 5 * 4);
          htmlContentEditor.setWidth(width);
        }
        */
      }
    });
  }
  
  private int getAvailableHeight() {
    int height = ArticleEditHtmlView.this.getOffsetHeight();
    if (height > 0)
      height = height - 90;
    return height;
  }
  
  private int getAvailableWidth() {
    int width = ArticleEditHtmlView.this.getOffsetWidth();
    if (width > 0)
      width = width - 20;
    return width;
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof Article) {
      this.article = (Article)model;
      htmlContentEditor.setModel(article.getHtml());
    }
  }
  
  @Override
  public void updateModel(Object model, final Delegate<Object> delegate) {
    Article articleToUpdate = (Article)model;
    if (htmlContentEditor.isContentModified()) {
      articleToUpdate.setHtml(htmlContentEditor.getUpdatedModel());
    }
    delegate.execute(articleToUpdate);
  }
  
}
