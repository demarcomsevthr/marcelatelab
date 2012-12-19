package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.ImageListView;
import it.mate.econyx.shared.model.Image;
import it.mate.gwtcommons.client.ui.AnchorCell;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
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

public class ImageListGeneralView extends AbstractAdminTabPage<ImageListView.Presenter> implements ImageListView, IsAdminTabPage<ImageListView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ImageListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  private SingleSelectionModel<Image> selectionModel;

//@UiField (provided=true) CellTable<Image> imagesTable;
  @UiField (provided=true) CellTableExt<Image> listTable;
  
  @UiField Panel pagerPanel;

  public ImageListGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    ProvidesKey<Image> keyProvider = new ProvidesKey<Image>() {
      public Object getKey(Image image) {
        return image.getId();
      }
    };

    /*
    imagesTable = new CellTable<Image>(new ProvidesKey<Image>() {
      public Object getKey(Image image) {
        return image.getId();
      }
    });
    */
    
    listTable = new CellTableExt<Image>(keyProvider);
    
    listTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    selectionModel = new SingleSelectionModel<Image>(keyProvider);
    
    listTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Image> createCheckboxManager());    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.Options<Image, Boolean>(new ColumnUtil.ValueGetter<Image, Boolean>() {
      public Boolean getValue(Image image) {
        return selectionModel.isSelected(image);
      }
    }, new CheckboxCell(true, false), null).setCellTable(listTable).setWidth("8px")), SafeHtmlUtils.fromSafeConstant("&nbsp;"));

    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<Image>(), 
    new AnchorCell<Image>() {
      protected String getCellValue(Image image) {
        return image.getCode();
      }
      protected void onConsumedEvent(NativeEvent event, Image image) {
        getPresenter().edit(image);
      }
    }, 
    null), "Codice");
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<Image>(), 
    new AnchorCell<Image>() {
      protected String getCellUrl(Image image) {
        return "/re/im/" + image.getCode();
      }
      protected String getCellTarget(Image model) {
        return "imgTarget";
      }
    }, 
    null), "Url");
    
    listTable.addCellPreviewHandler(new CellPreviewEvent.Handler<Image>() {
      public void onCellPreview(CellPreviewEvent<Image> event) {
        if ("dblclick".equals(event.getNativeEvent().getType())) {
          getPresenter().edit(event.getValue());
        }
      }
    });
    
    listTable.addFillerColumn();
    
    listTable.sinkEvents(Event.ONDBLCLICK);
    
  }

  public void setModel(Object model, String tag) {
    final List<Image> images = (List<Image>)model;
//  imagesTable.setRowCount(images.size());
//  imagesTable.setRowData(images);
    
    if (images != null) {
      Collections.sort(images, new Comparator<Image>() {
        public int compare(Image i1, Image i2) {
          return i1.getCode().compareTo(i2.getCode());
        }
      });
    }
    
    listTable.setRowDataExt(images);
    
    listTable.adaptToViewHeight(this, new Delegate<SimplePager>() {
      public void execute(SimplePager pager) {
        pagerPanel.add(pager);
      }
    });

  }
  
  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {
    
  }
  
  @Override
  public Object getSelectedObject() {
    return selectionModel.getSelectedObject();
  }
  
  
}
