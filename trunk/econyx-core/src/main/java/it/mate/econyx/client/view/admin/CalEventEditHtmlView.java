package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.HtmlContentEditor;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.CalEventEditView;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class CalEventEditHtmlView extends AbstractAdminTabPage<CalEventEditView.Presenter> implements CalEventEditView, IsAdminTabPage<CalEventEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, CalEventEditHtmlView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField HtmlContentEditor htmlContentEditor;
  
  private CalEvent event;
  
  public CalEventEditHtmlView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        int height = getAvailableHeight();
        int width = getAvailableWidth();
      }
    });
  }
  
  private int getAvailableHeight() {
    int height = CalEventEditHtmlView.this.getOffsetHeight();
    if (height > 0)
      height = height - 90;
    return height;
  }
  
  private int getAvailableWidth() {
    int width = CalEventEditHtmlView.this.getOffsetWidth();
    if (width > 0)
      width = width - 20;
    return width;
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof CalEvent) {
      this.event = (CalEvent)model;
      htmlContentEditor.setModel(event.getHtml());
    }
  }
  
  @Override
  public void updateModel(Object model, final Delegate<Object> delegate) {
    CalEvent eventToUpdate = (CalEvent)model;
    if (htmlContentEditor.isContentModified()) {
      eventToUpdate.setHtml(htmlContentEditor.getUpdatedModel());
    }
    delegate.execute(eventToUpdate);
  }
  
}
