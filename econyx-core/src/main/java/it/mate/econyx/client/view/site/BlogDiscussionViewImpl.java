package it.mate.econyx.client.view.site;

import it.mate.econyx.client.ui.PostViewer;
import it.mate.econyx.client.view.BlogDiscussionView;
import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.Post;
import it.mate.econyx.shared.model.PostComment;
import it.mate.econyx.shared.model.impl.BlogCommentTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class BlogDiscussionViewImpl extends AbstractBaseView<BlogDiscussionView.Presenter> implements BlogDiscussionView {

  public interface ViewUiBinder extends UiBinder<Widget, BlogDiscussionViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HTML bodyHtml;
  @UiField Panel postPanel;
  
  private BlogDiscussion discussion;
  
  public BlogDiscussionViewImpl() {
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
    } else if (model instanceof BlogDiscussion) {
      this.discussion = (BlogDiscussion)model;
      postPanel.clear();
      postPanel.add(new PostViewer((Post)discussion, false, true).setPresenter(new PostViewer.Presenter() {
        public void onPostSelected(Post post) {
          
        }
        public PostComment newPostCommentInstance() {
          return new BlogCommentTx();
        }
        public void addComment(String id, PostComment comment) {
          getPresenter().addCommentToDiscussion(discussion.getId(), (BlogComment)comment);
        }
      }));
    }
  }
  
}
