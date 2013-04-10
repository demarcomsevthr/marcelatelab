package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.BlogComment;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;


public interface BlogDiscussionView extends BaseView<BlogDiscussionView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    public void addCommentToDiscussion(String discussionId, BlogComment comment);
  }
  
  public class NotImpl extends UnimplementedView<BlogDiscussionView.Presenter> implements BlogDiscussionView {

  }
  
}
