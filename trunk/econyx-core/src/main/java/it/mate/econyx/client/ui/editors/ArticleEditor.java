package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.Article;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ArticleEditor extends Composite implements Editor<Article> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<Article, ArticleEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox name;
  @UiField TextBox code;
  @UiField IntegerBox orderNm;
  @UiField TextBox title;
  
  public ArticleEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(Article page) {
    driver.edit(page);
  }
  
  public Article flushModel() {
    Article page = driver.flush();
    return page;
  }
  
}
