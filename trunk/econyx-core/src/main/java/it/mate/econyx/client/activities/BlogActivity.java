package it.mate.econyx.client.activities;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.BlogPlace;
import it.mate.econyx.client.view.BlogDiscussionView;
import it.mate.econyx.client.view.BlogView;
import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.services.BlogServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class BlogActivity extends BaseActivity implements 
    BlogView.Presenter,
    BlogDiscussionView.Presenter {

  private BlogPlace place;
  
  private BlogServiceAsync blogService = AppClientFactory.IMPL.getGinjector().getBlogService();
  
  public BlogActivity(BlogPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    registerHandlers(eventBus);
    if (place.getToken().equals(BlogPlace.BLOG_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getBlogView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(BlogPlace.DISCUSSION_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getBlogDiscussionView(), panel);
      retrieveModel();
    }
  }
  
  private void retrieveModel() {
    getView().setModel(AppClientFactory.IMPL.getPortalSessionState());
    if (place.getToken().equals(BlogPlace.BLOG_VIEW)) {
      getView().setModel(place.getModel());
    }
    if (place.getToken().equals(BlogPlace.DISCUSSION_VIEW)) {
      getView().setModel(place.getModel());
    }
  }
  
  private void registerHandlers(EventBus eventBus) {

  }

  @Override
  public void onDispose() {
    GwtUtils.log("disposing " + this);
    super.onDispose();
  }

  @Override
  public void addDiscussionToBlog(String blogId, BlogDiscussion discussion) {
    blogService.addDiscussionToBlog(blogId, discussion, new AsyncCallback<Blog>() {
      public void onSuccess(Blog blog) {
        goTo(new BlogPlace(BlogPlace.BLOG_VIEW, blog));
      }
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
    });
  }

  @Override
  public void addCommentToDiscussion(String discussionId, BlogComment comment) {
    blogService.addCommentToDiscussion(discussionId, comment, new AsyncCallback<BlogDiscussion>() {
      public void onSuccess(BlogDiscussion discussion) {
        goTo(new BlogPlace(BlogPlace.DISCUSSION_VIEW, discussion));
      }
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
    });
  }

}
