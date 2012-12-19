package it.mate.econyx.client.view.admin.scaffold;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

public abstract class ModelListGeneralViewImpl <M extends HasId> extends AbstractBaseView<ModelListView.Presenter<M>> implements ModelListView<M> {

  @SuppressWarnings("rawtypes")
  public interface ViewUiBinder extends UiBinder<Widget, ModelListGeneralViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  protected @UiField (provided=true) CellTableExt<M> listTable;
  @UiField Panel pagerPanel;
  
  protected List<M> model;
  
  private SingleSelectionModel<M> selectionModel;
  
  public ModelListGeneralViewImpl() {
    initUI();
  }
  
  
  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }

  protected void initProvided() {  
    
    ProvidesKey<M> keyProvider = new ProvidesKey<M>() {
      public Object getKey(M model) {
        return model.getId();
      }
    };
    
    listTable = new CellTableExt<M>(keyProvider);
    
    listTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    selectionModel = new SingleSelectionModel<M>(keyProvider);
    
    listTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<M> createCheckboxManager());    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.Options<M, Boolean>(new ColumnUtil.ValueGetter<M, Boolean>() {
      public Boolean getValue(M item) {
        return selectionModel.isSelected(item);
      }
    }, new CheckboxCell(true, false), null).setCellTable(listTable).setWidth("8px")), SafeHtmlUtils.fromSafeConstant("&nbsp;"));

    createColumns();
    
    listTable.addFillerColumn();
    
    listTable.addCellPreviewHandler(new CellPreviewEvent.Handler<M>() {
      public void onCellPreview(CellPreviewEvent<M> event) {
        if ("dblclick".equals(event.getNativeEvent().getType())) {
          getPresenter().edit(event.getValue());
        }
      }
    });
    
    listTable.sinkEvents(Event.ONDBLCLICK);
    
  }
  
  protected abstract void createColumns();
  
  @Override
  public void setModel(Object model, String tag) {
    this.model = (List<M>)model;
    
    listTable.setRowDataExt(this.model);
    
    listTable.adaptToViewHeight(this, new Delegate<SimplePager>() {
      public void execute(SimplePager pager) {
        pagerPanel.add(pager);
      }
    });
  }
  
  
}
