package it.mate.gwtcommons.client.ui;

import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;

public class CellTableExt<M> extends CellTable<M> {

  private Map<Object, Column<M, ?>> columnMap = new HashMap<Object, Column<M,?>>();
  
  private Map<Object, Comparator<M>> comparatorMap = new HashMap<Object, Comparator<M>>();
  
  private ListDataProvider<M> dataProvider;
  
  private ListHandler<M> sortHandler;
  
  public interface ValueGetter <M, C> {
    C getValue(M model);
  }
  
  public static class SimpleValueGetter <M> implements ValueGetter<M, M> {
    public M getValue(M model) {return model;};
  }
  
  public CellTableExt(ProvidesKey<M> keyProvider) {
    super(10, keyProvider);
    init();
  }
  
  public CellTableExt() {
    super(10);
    init();
  }
  
  private void init() {
    super.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
  }
  
  public static class ColumnInfo<M, C> {
    private ValueGetter<M, C> getter;
    private Cell<C> cell;
    private FieldUpdater<M, C> fieldUpdater;
    private boolean sortable;
    private Comparator<M> comparator;
    private String headerText;
    public ColumnInfo<M, C> setGetter(ValueGetter<M, C> getter) {
      this.getter = getter;
      return this;
    }
    public ColumnInfo<M, C> setCell(Cell<C> cell) {
      this.cell = cell;
      return this;
    }
    public ColumnInfo<M, C> setFieldUpdater(FieldUpdater<M, C> fieldUpdater) {
      this.fieldUpdater = fieldUpdater;
      return this;
    }
    public ColumnInfo<M, C> setSortable(boolean sortable) {
      this.sortable = sortable;
      return this;
    }
    public ColumnInfo<M, C> setComparator(Comparator<M> comparator) {
      setSortable(true);
      this.comparator = comparator;
      return this;
    }
    public ColumnInfo<M, C> setHeaderText(String headerText) {
      this.headerText = headerText;
      return this;
    }  
  }
  
  public <C> void addColumn(Object key, Column<M, C> column, String headerText) {
    internalAddColumn(key, column, headerText, null);
  }
  
  public <C> void addColumn(Object key, Column<M, C> column, String headerText, Comparator<M> comparator) {
    internalAddColumn(key, column, headerText, comparator);
  }
  
  public <C> Column<M, C> addColumnExt(Object key, ColumnInfo<M, C> info) {
    if (info.getter == null) {
      info.getter = (ValueGetter<M, C>)new SimpleValueGetter<M>();
    }
    Column<M, C> column = createColumn(info.getter, info.cell, info.fieldUpdater, info.sortable);
    internalAddColumn(key, column, info.headerText, info.comparator);
    return column;
  }
  
  private <C> void internalAddColumn(Object key, Column<M, C> column, String headerText, Comparator<M> comparator) {
    columnMap.put(key, column);
    super.addColumn(column, headerText);
    if (comparator != null) {
      comparatorMap.put(key, comparator);
    }
  }
  
  private <C> Column<M, C> createColumn (
      final ValueGetter<M, C> getter, Cell<C> cell, FieldUpdater<M, C> fieldUpdater, boolean sortable) {
    Column<M, C> column = new Column<M, C>(cell) {
      public C getValue(M model) {
        return getter.getValue(model);
      }
    };
    if (fieldUpdater != null) {
      column.setFieldUpdater(fieldUpdater);
    }
    column.setSortable(sortable);
    return column;
  }
  
  public <C> Column<M, C> getColumn (Object key) {
    return (Column<M, C>)columnMap.get(key);
  }
  
  public void setRowDataExt (List<M> data) {
    setRowDataExt(data, null);
  }
    
  public void setRowDataExt (List<M> data, Object firstSortedColumnKey) {

    if (dataProvider != null) {
      dataProvider.setList(new ArrayList<M>());
      dataProvider.refresh();
    }
    
    if (data == null) {
      dataProvider = new ListDataProvider<M>(new ArrayList<M>());
      dataProvider.addDataDisplay(this);
      return;
    }

    if (dataProvider == null) {
      dataProvider = new ListDataProvider<M>(data);
      dataProvider.addDataDisplay(this);
    } else {
      dataProvider.setList(data);
      dataProvider.refresh();
    }

    sortHandler = new ListHandler<M>(dataProvider.getList());

    for (Object key : columnMap.keySet()) {
      Column<M, ?> column = columnMap.get(key);
      Comparator<M> comparator = comparatorMap.get(key);
      sortHandler.setComparator(column, comparator);
    }
    
    super.addColumnSortHandler(sortHandler);

    if (firstSortedColumnKey != null) {
      super.getColumnSortList().push(columnMap.get(firstSortedColumnKey));
    }
    
  }
  
  public void refreshDataProvider() {
    dataProvider.refresh();
  }
  
  public SimplePager createPager () {
    SimplePager pager = new SimplePager(TextLocation.CENTER, 
        (SimplePager.Resources)GWT.create(SimplePager.Resources.class), 
        false, 1000, true) {
      @Override
      protected String createText() {
        // Default text is 1 based.
        NumberFormat formatter = NumberFormat.getFormat("#,###");
        HasRows display = getDisplay();
        Range range = display.getVisibleRange();
        int pageStart = range.getStart() + 1;
        int pageSize = range.getLength();
        int dataSize = display.getRowCount();
        int endIndex = Math.min(dataSize, pageStart + pageSize - 1);
        endIndex = Math.max(pageStart, endIndex);
        boolean exact = display.isRowCountExact();
        return formatter.format(pageStart) + "-" + formatter.format(endIndex)
            + (exact ? " di " : " di oltre ") + formatter.format(dataSize);
      }
    };
    pager.setDisplay(this);
    return pager;
  }
  
  public void addFillerColumn() {
    addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<M>(), 
    new HtmlCell<M>() {
      protected SafeHtml getCellHtml(M model) {
        return SafeHtmlUtils.fromTrustedString("<div id=\"fillerColumn\" style=\"height:100%;width:1px\">&nbsp;</div>");
      }
    }, 
    null), "");
  }
  
  private class Semaphore {
    private boolean red = false;
    public boolean isRed() {
      return red;
    }
    public void setRed() {
      this.red = true;
    }
    public void setGreen() {
      this.red = false;
    }
  }
  
  private class WrappedValue <T> {
    private T value;
    public T get() {
      return value;
    }
    public void set(T value) {
      this.value = value;
    }
    public WrappedValue(T value) {
      super();
      this.value = value;
    }
  }
  
  public void adaptToViewHeight(final Composite view, final Panel pagerPanel) {
    List<M> model = dataProvider.getList();
    if (model != null && model.size() > 0) {
      final int modelRows = model.size();
      final Semaphore timerSemaphore = new Semaphore();
      @SuppressWarnings({ "unchecked", "rawtypes" }) 
      final WrappedValue<Integer> previousHeight = new WrappedValue(0);
      @SuppressWarnings({ "unchecked", "rawtypes" }) 
      final WrappedValue<Integer> rowHeightWrapper = new WrappedValue(0);
      GwtUtils.createTimer(100, new Delegate<Void>() {
        public void execute(Void element) {
          if (timerSemaphore.isRed()) {
            timerSemaphore.setGreen();
            return;
          }
          int viewHeight = view.getOffsetHeight();
          if (previousHeight.get() == viewHeight) {
            return;
          } else {
            previousHeight.set(viewHeight);
          }
          if (rowHeightWrapper.get() == 0) {
            Element fillerColumn = null;
            try {
              fillerColumn = DOM.getElementById("fillerColumn");
            } catch (JavaScriptException ex) { 
            } catch (Exception ex) { }
            if (fillerColumn == null) {
              throw new NullPointerException("Manca la fillerColumn!");
            }
            rowHeightWrapper.set(fillerColumn.getParentElement().getParentElement().getClientHeight());
          }
          int spacerHeight = 0;
          int rowHeight = rowHeightWrapper.get();
          if (rowHeight > 0) {
            rowHeight += 4; // aggiungo i bordi
            int maxRowsPerPage = viewHeight / rowHeight - 3;
            if (maxRowsPerPage > 0) {
              if (modelRows > maxRowsPerPage) {
                timerSemaphore.setRed();
                CellTableExt.this.setHeight( (viewHeight - rowHeight - 12) + "px");
              } else {
                spacerHeight = viewHeight - (modelRows + 2) * rowHeight - 12;
              }
              CellTableExt.this.setPageSize(maxRowsPerPage);
            }
          }
          pagerPanel.clear();
          VerticalPanel vp = new VerticalPanel();
          if (spacerHeight > 0) {
            vp.add(new Spacer("1px", spacerHeight+"px"));
          }
          vp.add(CellTableExt.this.createPager());
          pagerPanel.add(vp);
        }
      });
      
    } else {
      pagerPanel.clear();
    }
  }
  
  public void adaptToViewHeight(Composite view, final Delegate<SimplePager> pagerDelegate) {
    adaptToViewHeight(view.getOffsetHeight(), pagerDelegate);
  }
  
  public void adaptToViewHeight(final int viewHeight, final Delegate<SimplePager> pagerDelegate) {
    List<M> model = dataProvider.getList();
    if (model != null && model.size() > 0) {
      final int modelRows = model.size();
      GwtUtils.deferredExecution(100, new Delegate<Void>() {
        public void execute(Void element) {
          Element fillerColumn = null;
          try {
            fillerColumn = DOM.getElementById("fillerColumn");
          } catch (JavaScriptException ex) { 
          } catch (Exception ex) { }
          if (fillerColumn == null) {
            throw new NullPointerException("Manca la fillerColumn!");
          }
          int rowHeight = fillerColumn.getParentElement().getParentElement().getClientHeight();
          if (rowHeight > 0) {
            rowHeight += 4; // aggiungo i bordi
            int maxRowsPerPage = viewHeight / rowHeight - 3;
            if (maxRowsPerPage > 0) {
              if (modelRows > maxRowsPerPage) {
                CellTableExt.this.setHeight( (viewHeight - rowHeight - 12) + "px");
              }
              CellTableExt.this.setPageSize(maxRowsPerPage);
            }
          }
          pagerDelegate.execute(CellTableExt.this.createPager());
        }
      });
    }
  }
  
  
}
