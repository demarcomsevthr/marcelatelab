package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.DocumentListView;
import it.mate.econyx.shared.model.Document;
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

public class DocumentListGeneralView extends AbstractAdminTabPage<DocumentListView.Presenter> implements DocumentListView, IsAdminTabPage<DocumentListView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CellTableExt<Document> listTable;
  @UiField Panel pagerPanel;
  
  private SingleSelectionModel<Document> selectionModel;
  
  public DocumentListGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    ProvidesKey<Document> keyProvider = new ProvidesKey<Document>() {
      public Object getKey(Document document) {
        return document.getId();
      }
    };
    
    listTable = new CellTableExt<Document>(keyProvider);
    
    listTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    selectionModel = new SingleSelectionModel<Document>(keyProvider);
    
    listTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Document> createCheckboxManager());    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.Options<Document, Boolean>(new ColumnUtil.ValueGetter<Document, Boolean>() {
      public Boolean getValue(Document document) {
        return selectionModel.isSelected(document);
      }
    }, new CheckboxCell(true, false), null).setCellTable(listTable).setWidth("8px")), SafeHtmlUtils.fromSafeConstant("&nbsp;"));

    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<Document>(), 
    new AnchorCell<Document>() {
      protected String getCellValue(Document model) {
        return model.getName();
      }
      protected void onConsumedEvent(NativeEvent event, Document value) {
        getPresenter().edit(value);
      }
    }, 
    null), "Nome");
    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Document, String>() {
      public String getValue(Document document) {
        return document.getCode() != null ? document.getCode() : "";
      }
    }, new TextCell(), null), "Codice");

    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Document, String>() {
      public String getValue(Document document) {
        return document.getOrderNm() != null ? document.getOrderNm().toString() : "";
      }
    }, new TextCell(), null), "Ordine");
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Document, String>() {
      public String getValue(Document document) {
        return document.getAuthor() != null ? document.getAuthor().getScreenName() : "";
      }
    }, new TextCell(), null), "Author");
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Document, String>() {
      public String getValue(Document document) {
        return document.getCreated() != null ? GwtUtils.dateToString(document.getCreated()) : "";
      }
    }, new TextCell(), null), "Creato il");
    
    listTable.addFillerColumn();
    
    listTable.addCellPreviewHandler(new CellPreviewEvent.Handler<Document>() {
      public void onCellPreview(CellPreviewEvent<Document> event) {
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
      List<Document> documents = (List<Document>)model;
      try {
        listTable.setRowDataExt(documents);
        listTable.adaptToViewHeight(this, pagerPanel);
      } catch (Exception ex) {
        // 21/03/2013 Strani errori gwt-rpc generator
        ex.printStackTrace();
      }
    }
  }

  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {

  }
  
}
