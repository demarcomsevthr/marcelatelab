package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.editors.ArticleFolderEditor;
import it.mate.econyx.client.view.ArticleFolderEditView;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class ArticleFolderEditGeneralView extends AbstractAdminTabPage<ArticleFolderEditView.Presenter> implements ArticleFolderEditView, IsAdminTabPage<ArticleFolderEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleFolderEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField ArticleFolderEditor editor;
  
  public ArticleFolderEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof ArticleFolder) {
      editor.setModel((ArticleFolder)model);
    }
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    ArticleFolder editedInstance = editor.flushModel();
    if (!validateArticleFolder(editedInstance)) {
      return;
    }
    ArticleFolder folder = (ArticleFolder)model;
    folder.setName(editedInstance.getName());
    folder.setCode(editedInstance.getCode());
    folder.setOrderNm(editedInstance.getOrderNm());
    delegate.execute(folder);
  }
  
  private boolean validateArticleFolder(ArticleFolder folder) {
    if (GwtUtils.isEmpty(folder.getName())) {
      Window.alert("Il nome della pagina è vuoto");
      return false;
    }
    if (GwtUtils.isEmpty(folder.getCode())) {
      Window.alert("Il codice della pagina è vuoto");
      return false;
    }
    if (folder.getCode().contains(" ")) {
      Window.alert("Il codice della pagina non può contenere spazi");
      return false;
    }
    return true;
  }
  
}
