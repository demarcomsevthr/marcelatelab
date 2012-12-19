package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.ProducerListView;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.client.ui.AnchorCell;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

public class ProducerListGeneralView extends AbstractAdminTabPage<ProducerListView.Presenter> implements ProducerListView, IsAdminTabPage<ProducerListView.Presenter> {

  public interface ViewUiBinder extends UiBinder<Widget, ProducerListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CellTableExt<Produttore> listTable;
  @UiField Panel pagerPanel;
  
  private SingleSelectionModel<Produttore> selectionModel;
  
  public ProducerListGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    ProvidesKey<Produttore> keyProvider = new ProvidesKey<Produttore>() {
      public Object getKey(Produttore item) {
        return item.getId();
      }
    };
    
    listTable = new CellTableExt<Produttore>(keyProvider);
    
    listTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<Produttore>(), 
    new AnchorCell<Produttore>() {
      protected String getCellValue(Produttore item) {
        return item.getNome();
      }
      protected void onConsumedEvent(NativeEvent event, Produttore item) {
        getPresenter().edit(item);
      }
    }, 
    null), "Nome");
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Produttore, String>() {
      public String getValue(Produttore item) {
        return item.getCodice();
      }
    }, new TextCell(), null), "Codice");

    listTable.addFillerColumn();
    
    listTable.addCellPreviewHandler(new CellPreviewEvent.Handler<Produttore>() {
      public void onCellPreview(CellPreviewEvent<Produttore> event) {
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
  
  @SuppressWarnings("unchecked")
  public void setModel(Object model, String tag) {
    if (model == null) {
      listTable.setRowCount(0);
    } else if (model instanceof List) {
      List<Produttore> items = ((List<Produttore>)model);
      Collections.sort(items, new Comparator<Produttore>() {
        public int compare(Produttore i1, Produttore i2) {
          return i1.getCodice().compareTo(i2.getCodice());
        }
      });
      listTable.setRowDataExt(items);
      /*
      listTable.adaptToViewHeight(this, new Delegate<SimplePager>() {
        public void execute(SimplePager pager) {
          pagerPanel.add(pager);
        }
      });
      */
      listTable.adaptToViewHeight(this, pagerPanel);
    }
  }

  public void updateModel(Object model, Delegate<Object> delegate) {
    
  }

}
