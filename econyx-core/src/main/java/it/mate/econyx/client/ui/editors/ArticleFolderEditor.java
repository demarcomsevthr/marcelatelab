package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.ArticleFolder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ArticleFolderEditor extends Composite implements Editor<ArticleFolder> { 
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleFolderEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<ArticleFolder, ArticleFolderEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox name;
  @UiField TextBox code;
  @UiField IntegerBox orderNm;
  
  public ArticleFolderEditor() {
    initUI();
    driver.initialize(this);
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(ArticleFolder page) {
    driver.edit(page);
  }
  
  public ArticleFolder flushModel() {
    ArticleFolder page = driver.flush();
    return page;
  }
  
}
