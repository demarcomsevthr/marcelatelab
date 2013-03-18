package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.ArticleListView;
import it.mate.econyx.shared.model.Article;
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

public class ArticleListGeneralView extends AbstractAdminTabPage<ArticleListView.Presenter> implements ArticleListView, IsAdminTabPage<ArticleListView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CellTableExt<Article> listTable;
  @UiField Panel pagerPanel;
  
  private SingleSelectionModel<Article> selectionModel;
  
  public ArticleListGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    ProvidesKey<Article> keyProvider = new ProvidesKey<Article>() {
      public Object getKey(Article article) {
        return article.getId();
      }
    };
    
    listTable = new CellTableExt<Article>(keyProvider);
    
    listTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    selectionModel = new SingleSelectionModel<Article>(keyProvider);
    
    listTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Article> createCheckboxManager());    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.Options<Article, Boolean>(new ColumnUtil.ValueGetter<Article, Boolean>() {
      public Boolean getValue(Article article) {
        return selectionModel.isSelected(article);
      }
    }, new CheckboxCell(true, false), null).setCellTable(listTable).setWidth("8px")), SafeHtmlUtils.fromSafeConstant("&nbsp;"));

    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<Article>(), 
    new AnchorCell<Article>() {
      protected String getCellValue(Article model) {
        return model.getName();
      }
      protected void onConsumedEvent(NativeEvent event, Article value) {
        getPresenter().edit(value);
      }
    }, 
    null), "Nome");
    
    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Article, String>() {
      public String getValue(Article article) {
        return article.getCode() != null ? article.getCode() : "";
      }
    }, new TextCell(), null), "Codice");

    
    listTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<Article, String>() {
      public String getValue(Article article) {
        return article.getOrderNm() != null ? article.getOrderNm().toString() : "";
      }
    }, new TextCell(), null), "Ordine");
    
    listTable.addFillerColumn();
    
    listTable.addCellPreviewHandler(new CellPreviewEvent.Handler<Article>() {
      public void onCellPreview(CellPreviewEvent<Article> event) {
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
      List<Article> articles = (List<Article>)model;
      listTable.setRowDataExt(articles);
      listTable.adaptToViewHeight(this, pagerPanel);
    }
  }

  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {

  }
  
}
