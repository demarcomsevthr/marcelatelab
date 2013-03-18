package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.ArticleFolderListView;
import it.mate.econyx.shared.model.ArticleFolder;
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

public class ArticleFolderListGeneralView extends AbstractAdminTabPage<ArticleFolderListView.Presenter> implements ArticleFolderListView, IsAdminTabPage<ArticleFolderListView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleFolderListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CellTableExt<ArticleFolder> listTable;
  @UiField Panel pagerPanel;
  
  private SingleSelectionModel<ArticleFolder> selectionModel;
  
  public ArticleFolderListGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    ProvidesKey<ArticleFolder> keyProvider = new ProvidesKey<ArticleFolder>() {
      public Object getKey(ArticleFolder folder) {
        return folder.getId();
      }
    };
    
    listTable = new CellTableExt<ArticleFolder>(keyProvider);
    
    listTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    selectionModel = new SingleSelectionModel<ArticleFolder>(keyProvider);
    
    listTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<ArticleFolder> createCheckboxManager());    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.Options<ArticleFolder, Boolean>(new ColumnUtil.ValueGetter<ArticleFolder, Boolean>() {
      public Boolean getValue(ArticleFolder folder) {
        return selectionModel.isSelected(folder);
      }
    }, new CheckboxCell(true, false), null).setCellTable(listTable).setWidth("8px")), SafeHtmlUtils.fromSafeConstant("&nbsp;"));

    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<ArticleFolder>(), 
    new AnchorCell<ArticleFolder>() {
      protected String getCellValue(ArticleFolder model) {
        return model.getName();
      }
      protected void onConsumedEvent(NativeEvent event, ArticleFolder value) {
        getPresenter().edit(value);
      }
    }, 
    null), "Nome");
    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<ArticleFolder, String>() {
      public String getValue(ArticleFolder folder) {
        return folder.getCode() != null ? folder.getCode() : "";
      }
    }, new TextCell(), null), "Codice");

    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<ArticleFolder, String>() {
      public String getValue(ArticleFolder folder) {
        return folder.getOrderNm() != null ? folder.getOrderNm().toString() : "";
      }
    }, new TextCell(), null), "Ordine");
    
    listTable.addFillerColumn();
    
    listTable.addCellPreviewHandler(new CellPreviewEvent.Handler<ArticleFolder>() {
      public void onCellPreview(CellPreviewEvent<ArticleFolder> event) {
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
      List<ArticleFolder> folders = (List<ArticleFolder>)model;
      listTable.setRowDataExt(folders);
      listTable.adaptToViewHeight(this, pagerPanel);
    }
  }

  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {

  }
  
}
