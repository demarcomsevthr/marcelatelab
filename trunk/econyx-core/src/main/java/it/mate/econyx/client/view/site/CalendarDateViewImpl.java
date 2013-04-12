package it.mate.econyx.client.view.site;

import it.mate.econyx.client.events.CalendarDateChangeEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.ui.PostViewer;
import it.mate.econyx.client.view.CalendarView;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.Post;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class CalendarDateViewImpl extends AbstractBaseView<CalendarView.Presenter> implements CalendarView {

  public interface ViewUiBinder extends UiBinder<Widget, CalendarDateViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField FlexTable flexTable;
  
  List<CalEvent> calEvents;

  public CalendarDateViewImpl() {
    super();
    initUI();
    GwtUtils.log("INITIALIZING " + this);
  }
  
  @Override
  public String toString() {
    return getClass()+"@"+hashCode();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
    
    AppClientFactory.IMPL.getEventBus().addHandler(CalendarDateChangeEvent.TYPE, new CalendarDateChangeEvent.Handler() {
      public void onCalendarDateChange(CalendarDateChangeEvent event) {
        setModel(event.getCalEvents());
      }
    });
    
  }

  @SuppressWarnings("unchecked")
  public void setModel(Object model, String tag) {
    if (model instanceof List) {
      calEvents = (List<CalEvent>)model;
      showEvents(calEvents);
    }
  }
  
  private void showEvents(List<CalEvent> calEvents) {
    int row = 0;
    for (int it = 0; it < calEvents.size(); it++) {
      
      CalEvent calEvent = calEvents.get(it);
      
      PostViewer postViewer = new PostViewer((Post)calEvent, true, false);
      flexTable.setWidget(row, 0, postViewer);
      row++;
      
      /*
      HTML title = new HTML(calEvent.getTitle());
      title.addStyleName("ecxCalEventTitle");
      flexTable.setWidget(row++, 0, title);
      
      HTML date = new HTML(GwtUtils.dateToString(calEvent.getDate()));
      title.addStyleName("ecxCalEventDate");
      flexTable.setWidget(row++, 0, date);

      HtmlContent content = calEvent.getHtml();
      if (content != null && content.getContent() != null) {
        HTML body = new HTML(calEvent.getHtml().getContent());
        title.addStyleName("ecxCalEventBody");
        flexTable.setWidget(row++, 0, body);
      }
      
      flexTable.setWidget(row++, 0, new Spacer("1px", "50px"));
      */
      
    }
  }
  
}
