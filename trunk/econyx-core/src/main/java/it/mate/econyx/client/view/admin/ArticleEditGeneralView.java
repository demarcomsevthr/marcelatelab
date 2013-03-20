package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.editors.ArticleEditor;
import it.mate.econyx.client.view.ArticleEditView;
import it.mate.econyx.shared.model.Article;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class ArticleEditGeneralView extends AbstractAdminTabPage<ArticleEditView.Presenter> implements ArticleEditView, IsAdminTabPage<ArticleEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, ArticleEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField ArticleEditor editor;
  
  public ArticleEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof Article) {
      editor.setModel((Article)model);
    }
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    Article editedInstance = editor.flushModel();
    if (!validateArticle(editedInstance)) {
      return;
    }
    Article folder = (Article)model;
    folder.setName(editedInstance.getName());
    folder.setCode(editedInstance.getCode());
    folder.setOrderNm(editedInstance.getOrderNm());
    delegate.execute(folder);
  }
  
  private boolean validateArticle(Article folder) {
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
