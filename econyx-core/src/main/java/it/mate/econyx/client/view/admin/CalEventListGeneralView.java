package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.CalEventListView;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.gwtcommons.client.ui.AnchorCell;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

public class CalEventListGeneralView extends AbstractAdminTabPage<CalEventListView.Presenter> implements CalEventListView, IsAdminTabPage<CalEventListView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, CalEventListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CellTableExt<CalEvent> listTable;
  @UiField Panel pagerPanel;
  
  private SingleSelectionModel<CalEvent> selectionModel;
  
  public CalEventListGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    ProvidesKey<CalEvent> keyProvider = new ProvidesKey<CalEvent>() {
      public Object getKey(CalEvent event) {
        return event.getId();
      }
    };
    
    listTable = new CellTableExt<CalEvent>(keyProvider);
    
    listTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    selectionModel = new SingleSelectionModel<CalEvent>(keyProvider);
    
    listTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<CalEvent> createCheckboxManager());    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.Options<CalEvent, Boolean>(new ColumnUtil.ValueGetter<CalEvent, Boolean>() {
      public Boolean getValue(CalEvent event) {
        return selectionModel.isSelected(event);
      }
    }, new CheckboxCell(true, false), null).setCellTable(listTable).setWidth("8px")), SafeHtmlUtils.fromSafeConstant("&nbsp;"));

    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<CalEvent>(), 
    new AnchorCell<CalEvent>() {
      protected String getCellValue(CalEvent model) {
        return model.getName();
      }
      protected void onConsumedEvent(NativeEvent event, CalEvent value) {
        getPresenter().edit(value);
      }
    }, 
    null), "Nome");
    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<CalEvent, String>() {
      public String getValue(CalEvent event) {
        return event.getCode() != null ? event.getCode() : "";
      }
    }, new TextCell(), null), "Codice");

    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<CalEvent, String>() {
      public String getValue(CalEvent event) {
        return GwtUtils.dateToString(event.getDate());
      }
    }, new TextCell(), null), "Data");
    
    listTable.addFillerColumn();
    
    listTable.addCellPreviewHandler(new CellPreviewEvent.Handler<CalEvent>() {
      public void onCellPreview(CellPreviewEvent<CalEvent> event) {
        if ("dblclick".equals(event.getNativeEvent().getType())) {
          getPresenter().edit(event.getValue());
        }
      }
    });
    
    listTable.sinkEvents(Event.ONDBLCLICK);
    
  }
  
  @Override
  public Object getSelectedObject() {
    return selectionModel.getSelectedObject();
  }
  
  @Override
  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof List) {
      List<CalEvent> events = (List<CalEvent>)model;
      listTable.setRowDataExt(events);
      listTable.adaptToViewHeight(this, pagerPanel);
    }
  }

  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {

  }
  
}
