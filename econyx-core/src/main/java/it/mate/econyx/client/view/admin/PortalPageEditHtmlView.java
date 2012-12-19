package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.HtmlContentEditor;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.PortalPageEditView;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageEditHtmlView extends AbstractAdminTabPage<PortalPageEditView.Presenter> implements PortalPageEditView, IsAdminTabPage<PortalPageEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalPageEditHtmlView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HtmlContentEditor shortHtmlContentEditor;
  @UiField HtmlContentEditor mediumHtmlContentEditor;
  
  private WebContentPage page;
  
  public PortalPageEditHtmlView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        int height = getAvailableHeight();
        int width = getAvailableWidth();
        if (height > 0 && width > 0) {
          if (shortHtmlContentEditor.isEditing()) {
            mediumHtmlContentEditor.setVisible(false);
          } else if (mediumHtmlContentEditor.isEditing()) {
            shortHtmlContentEditor.setVisible(false);
          } else {
            shortHtmlContentEditor.setVisible(true);
            mediumHtmlContentEditor.setVisible(true);
          }
          if (shortHtmlContentEditor.isEmpty()) {
            shortHtmlContentEditor.setViewerHeight(0);
            mediumHtmlContentEditor.setViewerHeight(height);
          } else {
            shortHtmlContentEditor.setViewerHeight(height / 5);
            mediumHtmlContentEditor.setViewerHeight(height / 5 * 4);
          }
          shortHtmlContentEditor.setEditorHeight(height / 5 * 4);
          mediumHtmlContentEditor.setEditorHeight(height / 5 * 4);
          shortHtmlContentEditor.setWidth(width);
          mediumHtmlContentEditor.setWidth(width);
        }
      }
    });
  }
  
  private int getAvailableHeight() {
    int height = PortalPageEditHtmlView.this.getOffsetHeight();
    if (height > 0)
      height = height - 90;
    return height;
  }
  
  private int getAvailableWidth() {
    int width = PortalPageEditHtmlView.this.getOffsetWidth();
    if (width > 0)
      width = width - 20;
    return width;
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    
    if (model instanceof WebContentPage) {
      this.page = (WebContentPage)model;

      Delegate<WebContentPage> delegate = new Delegate<WebContentPage>() {
        public void execute(WebContentPage page) {
          if (page != null) {
            shortHtmlContentEditor.setModel(page.getHtmlContent(HtmlContent.Type.SHORT));
            mediumHtmlContentEditor.setModel(page.getHtmlContent(HtmlContent.Type.MEDIUM));
          }
        }
      };
      if (page.areHtmlsInitialized()) {
        delegate.execute(page);
      } else {
        getPresenter().fetchHtmls(page, delegate);
      }
    }
    
    
  }
  
  @Override
  public void updateModel(Object model, final Delegate<Object> delegate) {
    final PortalPage pageToUpdate = (PortalPage)model;
    getPresenter().updateHtmlContent(page, shortHtmlContentEditor.getUpdatedModel(), shortHtmlContentEditor.isContentModified(), new Delegate<WebContentPage>() {
      public void execute(WebContentPage updatedPage) {
        getPresenter().updateHtmlContent(page, mediumHtmlContentEditor.getUpdatedModel(), mediumHtmlContentEditor.isContentModified(), new Delegate<WebContentPage>() {
          public void execute(WebContentPage updatedPage) {
            delegate.execute(pageToUpdate);
          }
        });
      }
    });
  }
  
}
