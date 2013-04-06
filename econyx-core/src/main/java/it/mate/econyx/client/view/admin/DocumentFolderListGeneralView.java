package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.DocumentFolderListView;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.gwtcommons.client.ui.AnchorCell;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;

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

public class DocumentFolderListGeneralView extends AbstractAdminTabPage<DocumentFolderListView.Presenter> implements DocumentFolderListView, IsAdminTabPage<DocumentFolderListView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentFolderListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CellTableExt<DocumentFolder> listTable;
  @UiField Panel pagerPanel;
  
  private SingleSelectionModel<DocumentFolder> selectionModel;
  
  public DocumentFolderListGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    ProvidesKey<DocumentFolder> keyProvider = new ProvidesKey<DocumentFolder>() {
      public Object getKey(DocumentFolder folder) {
        return folder.getId();
      }
    };
    
    listTable = new CellTableExt<DocumentFolder>(keyProvider);
    
    listTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    selectionModel = new SingleSelectionModel<DocumentFolder>(keyProvider);
    
    listTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<DocumentFolder> createCheckboxManager());    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.Options<DocumentFolder, Boolean>(new ColumnUtil.ValueGetter<DocumentFolder, Boolean>() {
      public Boolean getValue(DocumentFolder folder) {
        return selectionModel.isSelected(folder);
      }
    }, new CheckboxCell(true, false), null).setCellTable(listTable).setWidth("8px")), SafeHtmlUtils.fromSafeConstant("&nbsp;"));

    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<DocumentFolder>(), 
    new AnchorCell<DocumentFolder>() {
      protected String getCellValue(DocumentFolder model) {
        return model.getName();
      }
      protected void onConsumedEvent(NativeEvent event, DocumentFolder value) {
        getPresenter().edit(value);
      }
    }, 
    null), "Nome");
    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<DocumentFolder, String>() {
      public String getValue(DocumentFolder folder) {
        return folder.getCode() != null ? folder.getCode() : "";
      }
    }, new TextCell(), null), "Codice");

    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<DocumentFolder, String>() {
      public String getValue(DocumentFolder folder) {
        return folder.getOrderNm() != null ? folder.getOrderNm().toString() : "";
      }
    }, new TextCell(), null), "Ordine");
    
    listTable.addFillerColumn();
    
    listTable.addCellPreviewHandler(new CellPreviewEvent.Handler<DocumentFolder>() {
      public void onCellPreview(CellPreviewEvent<DocumentFolder> event) {
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
      List<DocumentFolder> folders = (List<DocumentFolder>)model;
      listTable.setRowDataExt(folders);
      listTable.adaptToViewHeight(this, pagerPanel);
    }
  }

  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {

  }
  
}
