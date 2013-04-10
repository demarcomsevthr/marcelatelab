package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.view.BlogDiscussionView;
import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.impl.BlogCommentTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class BlogDiscussionViewImpl extends AbstractBaseView<BlogDiscussionView.Presenter> implements BlogDiscussionView {

  public interface ViewUiBinder extends UiBinder<Widget, BlogDiscussionViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HTML discussionContent;
  @UiField FlexTable commentsTable;
  @UiField TextArea newCommentArea;
  
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
      commentsTable.clear();
      if (discussion.getContent() != null) {
        discussionContent.setHTML(discussion.getContent());
      } else {
        discussionContent.setHTML("");
      }
      if (discussion.getComments() != null) {
        int row = 0;
        for (BlogComment comment : discussion.getComments()) {
          HTML author = new HTML("Inserito da " + comment.getAuthor().getScreenName());
          commentsTable.setWidget(row, 0, author);
          row++;
          HTML posted = new HTML("Il " + GwtUtils.dateToString(comment.getPosted()));
          commentsTable.setWidget(row, 0, posted);
          row++;
          HTML body = new HTML(comment.getContent());
          commentsTable.setWidget(row, 0, body);
          row++;
        }
      }
    }
  }
  
  @UiHandler ("addCommentBtn")
  public void onAddCommentBtn(ClickEvent event) {
    if (AppClientFactory.IMPL.getPortalSessionState().getLoggedUser() == null) {
      Window.alert("Devi effettuare il login per poter inserire un commento");
      return;
    }
    BlogComment comment = new BlogCommentTx();
    comment.setContent(newCommentArea.getText());
    comment.setAuthor(AppClientFactory.IMPL.getPortalSessionState().getLoggedUser());
    comment.setPosted(new Date());
    getPresenter().addCommentToDiscussion(discussion.getId(), comment);
  }
  
}
