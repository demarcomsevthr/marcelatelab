package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.ProductListView;
import it.mate.econyx.shared.model.Articolo;
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
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ProvidesKey;

public class ProductListGeneralView extends AbstractAdminTabPage<ProductListView.Presenter> implements ProductListView, IsAdminTabPage<ProductListView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ProductListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CellTableExt<Articolo> productsTable;
  @UiField Panel pagerPanel;
  
  public ProductListGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    productsTable = new CellTableExt<Articolo>(new ProvidesKey<Articolo>() {
      public Object getKey(Articolo product) {
        return product.getId();
      }
    });
    
    productsTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);

    productsTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<Articolo>(), 
    new AnchorCell<Articolo>() {
      protected String getCellValue(Articolo model) {
        return model.getCodice();
      }
      protected void onConsumedEvent(NativeEvent event, Articolo value) {
        getPresenter().edit(value);
      }
    }, 
    null), "Codice");
    
    productsTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Articolo, String>() {
      public String getValue(Articolo product) {
        return product.getName();
      }
    }, new TextCell(), null), "Descrizione");
    
    productsTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Articolo, String>() {
      public String getValue(Articolo product) {
        return product.getUnitaDiMisura() != null ? product.getUnitaDiMisura().getNome() : "";
      }
    }, new TextCell(), null), "UdM");
    
    productsTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Articolo, String>() {
      public String getValue(Articolo product) {
        return product.getProducer() != null ? product.getProducer().getNome() : "";
      }
    }, new TextCell(), null), "Produttore");
    
    productsTable.addFillerColumn();
    
    productsTable.addCellPreviewHandler(new CellPreviewEvent.Handler<Articolo>() {
      public void onCellPreview(CellPreviewEvent<Articolo> event) {
        if ("dblclick".equals(event.getNativeEvent().getType())) {
          getPresenter().edit(event.getValue());
        }
      }
    });
    
    productsTable.sinkEvents(Event.ONDBLCLICK);
    
  }
  
  @Override
  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof List) {
      List<Articolo> products = (List<Articolo>)model;
      if (products == null) {
        productsTable.setRowCount(0);
      } else {
        Collections.sort(products, new Comparator<Articolo>() {
          public int compare(Articolo a1, Articolo a2) {
            return a1.getCodice().compareTo(a2.getCodice());
          }
        });
        /*
        productsTable.setRowCount(products.size());
        productsTable.setRowData(0, products);
        */
        productsTable.setRowDataExt(products);
        productsTable.adaptToViewHeight(this.getOffsetHeight(), new Delegate<SimplePager>() {
          public void execute(SimplePager pager) {
            pagerPanel.add(pager);
          }
        });
      }
    }
  }

  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {

  }
  
}
