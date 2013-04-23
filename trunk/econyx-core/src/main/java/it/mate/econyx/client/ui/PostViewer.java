package it.mate.econyx.client.ui;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.shared.model.Post;
import it.mate.econyx.shared.model.PostComment;
import it.mate.gwtcommons.client.ui.ListPanel;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQueryUtils;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class PostViewer extends Composite {

  public interface ViewUiBinder extends UiBinder<Widget, PostViewer> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Presenter {
    public void addComment(String id, PostComment comment);
    public PostComment newPostCommentInstance();
    public void onPostSelected(Post post);
  }
  
  @UiField FlexTable postTable;
  @UiField Panel commentsPanel;
  @UiField HTML commentsHeaderCount;
  @UiField FlexTable commentsTable;
  @UiField TextArea commentArea;
  
  private Post post;
  
  private final static String HTML_CONTAINER_ID = "ecx-htmlContainer";
  
  private boolean showReadmoreElements;
  private boolean showCommentsPanel;
  
  private Presenter presenter;
  
  public PostViewer(Post post, boolean showReadmoreElements, boolean showCommentsPanel) {
    this.post = post;
    this.showReadmoreElements = showReadmoreElements;
    this.showCommentsPanel = showCommentsPanel;
    initUI();
  }
  
  public PostViewer setPresenter(Presenter presenter) {
    this.presenter = presenter;
    return this;
  }
  
  private void initUI() {
    
    initWidget(uiBinder.createAndBindUi(this));
    
    int postRow = 0;
    
    HTML created = new HTML(GwtUtils.dateToString(post.getPostDate()));
    created.addStyleName("ecxPostCreated");
    postTable.setWidget(postRow, 0, created);
    postRow++;
    
    HTML title = new HTML(post.getTitle());
    title.addStyleName("ecxPostTitle");
    postTable.setWidget(postRow, 0, title);
    postRow++;
    
    HTML body = createHtml(post.getBody(), post.getCode());
    created.addStyleName("ecxPostBody");
    postTable.setWidget(postRow, 0, body);
    postRow++;
    
    ListPanel footer = new ListPanel();
    footer.addStyleName("ecxPostFooter");
    postTable.setWidget(postRow, 0, footer);
    postRow++;
    
    HorizontalPanel footerLine1 = new HorizontalPanel();
    footer.add(footerLine1);
    
    HTML author = new HTML("Inserito da " + post.getAuthor() != null ? post.getAuthor().getScreenName() : "<author null!>");
    author.addStyleName("ecxPostAuthor");
    footerLine1.add(author);
    
    footerLine1.add(new Spacer("1em"));
    
    HTML commentsCount = new HTML(post.getCommentsCount() + " commenti");
    commentsCount.addStyleName("ecxPostCommentsCount");
    footerLine1.add(commentsCount);
    
    HorizontalPanel footerLine2 = new HorizontalPanel();
    footer.add(footerLine2);
    
    HTML tags = new HTML("Tags: " + post.getTags());
    tags.addStyleName("ecxPostTags");
    footerLine2.add(tags);
    
    postTable.setWidget(postRow, 0, new Spacer("1px", "4em"));
    postRow++;
    
    GwtUtils.setWidgetModel(title, post);
    title.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        Post post = GwtUtils.getEventSourceWidgetModel(event, Post.class);
        if (presenter != null) {
          presenter.onPostSelected(post);
        } else {
          GwtUtils.setLocationHash(post.getCode());
        }
      }
    });
    
    
    if (showCommentsPanel) {
      
      commentsPanel.setVisible(true);
      commentsHeaderCount.setHTML(post.getCommentsCount() + " commenti:");
      commentsTable.clear();
      if (post.getPostComments() != null) {
        int commentRow = 0;
        for (int it = 0; it < post.getPostComments().size(); it++) {
          
          PostComment comment = post.getPostComments().get(it);
          
          Image commentImage = new Image("/main/images/commons/post-comment-large.png");
          commentImage.addStyleName("ecxCommentImage");
          commentsTable.setWidget(commentRow, 0, commentImage);
          
          GwtUtils.setFlexCellRowSpan(commentsTable, commentRow, 0, 3);

          HTML commentAuthor = new HTML(comment.getAuthor().getScreenName());
          commentAuthor.addStyleName("ecxCommentAuthor");
          commentsTable.setWidget(commentRow, 1, commentAuthor);
          
          HTML commentPosted = new HTML(GwtUtils.dateToString(comment.getPosted(), "'il' dd/MM/yyyy 'alle' HH:mm"));
          commentPosted.addStyleName("ecxCommentPosted");
          commentsTable.setWidget(commentRow, 2, commentPosted);
          
          HTML commentLabel1 = new HTML("ha risposto:");
          commentLabel1.addStyleName("ecxCommentLabel1");
          commentsTable.setWidget(commentRow, 3, commentLabel1);
          commentRow++;
          
          HTML commentContent = new HTML(comment.getBody());
          commentContent.addStyleName("ecxCommentContent");
          commentsTable.setWidget(commentRow, 0, commentContent);
          commentRow++;

          Spacer commentSpacer = new Spacer();
          commentSpacer.addStyleName("ecxCommentSpacer");
          commentsTable.setWidget(commentRow, 0, commentSpacer);
          commentRow++;
          
        }
      }
      
    }
    
    
  }
  
  private static String getPostRootElemId (String postCode) {
    return HTML_CONTAINER_ID+"-Post"+postCode;
  }
  
  private HTML createHtml(String htmlContent, final String postCode) {
    final HTML html = new HTML();
    if (htmlContent == null)
      return html;
    String actualHtmlContent = htmlContent;
    html.getElement().setId(getPostRootElemId(postCode));
    html.getElement().getStyle().setHeight(0, Unit.PX);
    html.setHTML(SafeHtmlUtils.fromTrustedString(actualHtmlContent));
    GwtUtils.deferredExecution(100, new Delegate<Void>() {
      public void execute(Void element) {
        List<Element> elements = JQueryUtils.selectList("#"+getPostRootElemId(postCode)+" #cke-readmore");
        for (Element readmoreElement : elements) {
          
          if (showReadmoreElements) {
            processCkeReadmore(readmoreElement);
            GwtUtils.addElementEventListener(readmoreElement, Event.ONCLICK, new EventListener() {
              public void onBrowserEvent(Event event) {
                GwtUtils.setLocationHash(postCode);
              }
            });
          } else {
            readmoreElement.getStyle().setHeight(0, Unit.PX);
            readmoreElement.getStyle().setDisplay(Display.NONE);
            html.getElement().getStyle().clearHeight();
          }
          
        }
        html.getElement().getStyle().clearHeight();
      }
    });
    return html;
  }
  
  private void processCkeReadmore(Element readmoreElement) {
    Element itElem = readmoreElement.getNextSiblingElement();
    while(itElem != null) {
      itElem.getStyle().setHeight(0, Unit.PX);
      itElem.getStyle().setDisplay(Display.NONE);
      itElem = itElem.getNextSiblingElement();
    }
    Element parentElem = readmoreElement.getParentElement();
    if (parentElem.getId() != null && parentElem.getId().startsWith(HTML_CONTAINER_ID)) {
      //NO-OP
    } else {
      processCkeReadmore(parentElem);
    }
  }
  
  @UiHandler ("addCommentBtn")
  public void onAddCommentBtn(ClickEvent event) {
    if (AppClientFactory.IMPL.getPortalSessionState().getLoggedUser() == null) {
      Window.alert("Devi effettuare il login per poter inserire un commento");
      return;
    }
    PostComment comment = presenter.newPostCommentInstance();
    comment.setBody(commentArea.getText());
    comment.setAuthor(AppClientFactory.IMPL.getPortalSessionState().getLoggedUser());
    comment.setPosted(new Date());
    commentArea.setText("");
    presenter.addComment(post.getId(), comment);
  }
  
}
