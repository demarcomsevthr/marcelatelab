package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;


public interface BlogView extends BaseView<BlogView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    
    public void addDiscussionToBlog(String blogId, BlogDiscussion discussion);
    
  }
  
  public class NotImpl extends UnimplementedView<BlogView.Presenter> implements BlogView {

  }
  
}
