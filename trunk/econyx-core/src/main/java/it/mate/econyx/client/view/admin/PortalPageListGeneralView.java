package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.PortalPageListView;
import it.mate.econyx.shared.model.PortalPage;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

public class PortalPageListGeneralView extends AbstractAdminTabPage<PortalPageListView.Presenter> implements PortalPageListView, IsAdminTabPage<PortalPageListView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalPageListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CellTableExt<PortalPage> listTable;
  @UiField Panel pagerPanel;
  
  private SingleSelectionModel<PortalPage> selectionModel;
  
  public PortalPageListGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    ProvidesKey<PortalPage> keyProvider = new ProvidesKey<PortalPage>() {
      public Object getKey(PortalPage page) {
        return page.getId();
      }
    };
    
    listTable = new CellTableExt<PortalPage>(keyProvider);
    
    listTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    selectionModel = new SingleSelectionModel<PortalPage>(keyProvider);
    
    listTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<PortalPage> createCheckboxManager());    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.Options<PortalPage, Boolean>(new ColumnUtil.ValueGetter<PortalPage, Boolean>() {
      public Boolean getValue(PortalPage page) {
        return selectionModel.isSelected(page);
      }
    }, new CheckboxCell(true, false), null).setCellTable(listTable).setWidth("8px")), SafeHtmlUtils.fromSafeConstant("&nbsp;"));

    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<PortalPage>(), 
    new AnchorCell<PortalPage>() {
      protected String getCellValue(PortalPage model) {
        return model.getName();
      }
      protected void onConsumedEvent(NativeEvent event, PortalPage value) {
        getPresenter().edit(value);
      }
    }, 
    null), "Nome");
    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<PortalPage, String>() {
      public String getValue(PortalPage page) {
        return page.getCode() != null ? page.getCode() : "";
      }
    }, new TextCell(), null), "Codice");

    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<PortalPage>(), 
    new AnchorCell<PortalPage>() {
      protected String getCellUrl(PortalPage page) {
        String url = getCellValue(page);
        String gwtCodesvr = Window.Location.getParameter("gwt.codesvr");
        if (url.length() > 0 && gwtCodesvr != null) {
          url += "?gwt.codesvr=" + gwtCodesvr;
        }
        return url;
      }
      @Override
      protected String getCellValue(PortalPage page) {
        String url = page.getCode() != null ? ("/re/pg/" + page.getCode()) : "";
        return url;
      }
      protected String getCellTarget(PortalPage page) {
        return page.getCode() != null ? page.getCode() : "";
      }
    }, 
    null), "Url");
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<PortalPage, String>() {
      public String getValue(PortalPage page) {
        return page.getOrderNm() != null ? page.getOrderNm().toString() : "";
      }
    }, new TextCell(), null), "Ordine");
    
    listTable.addFillerColumn();
    
    listTable.addCellPreviewHandler(new CellPreviewEvent.Handler<PortalPage>() {
      public void onCellPreview(CellPreviewEvent<PortalPage> event) {
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
    List<PortalPage> pages = (List<PortalPage>)model;
    listTable.setRowDataExt(pages);
    /*
    listTable.adaptToViewHeight(this, new Delegate<SimplePager>() {
      public void execute(SimplePager pager) {
        pagerPanel.add(pager);
      }
    });
    */
    listTable.adaptToViewHeight(this, pagerPanel);
  }

  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {

  }
  
}
