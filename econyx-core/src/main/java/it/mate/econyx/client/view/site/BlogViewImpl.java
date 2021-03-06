package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.ui.PostViewer;
import it.mate.econyx.client.view.BlogView;
import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.Post;
import it.mate.econyx.shared.model.impl.BlogDiscussionTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class BlogViewImpl extends AbstractBaseView<BlogView.Presenter> implements BlogView {

  public interface ViewUiBinder extends UiBinder<Widget, BlogViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField FlexTable discussionsTable;
  @UiField TextBox titleBox;
  @UiField TextArea newDiscussionArea;
  
  private Blog blog;
  
  public BlogViewImpl() {
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
    } else if (model instanceof Blog) {
      this.blog = (Blog)model;
      discussionsTable.clear();
      if (blog.getDiscussions() != null) {
        int row = 0;
        for (final BlogDiscussion discussion : blog.getDiscussions()) {
          PostViewer postViewer = new PostViewer((Post)discussion, true, false);
          discussionsTable.setWidget(row, 0, postViewer);
          row++;
        }
      }
    }
  }
  
  @UiHandler ("addDiscussionBtn")
  public void onAddDiscussionBtn(ClickEvent event) {
    if (AppClientFactory.IMPL.getPortalSessionState().getLoggedUser() == null) {
      Window.alert("Devi effettuare il login per poter inserire una nuova discussione");
      return;
    }
    if (titleBox.getValue() == null || titleBox.getValue().trim().length() == 0) {
      Window.alert("Devi inserire il titolo della discussione");
      return;
    }
    if (newDiscussionArea.getText() == null || newDiscussionArea.getText().trim().length() == 0) {
      Window.alert("Devi inserire il testo della discussione");
      return;
    }
    BlogDiscussion discussion = new BlogDiscussionTx();
    discussion.setTitle(titleBox.getValue());
    discussion.setContent(newDiscussionArea.getText());
    discussion.setAuthor(AppClientFactory.IMPL.getPortalSessionState().getLoggedUser());
    discussion.setCreated(new Date());
    getPresenter().addDiscussionToBlog(blog.getId(), discussion);
  }
  
}
