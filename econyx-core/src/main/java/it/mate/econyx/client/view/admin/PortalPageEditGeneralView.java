package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.ui.editors.PortalPageEditor;
import it.mate.econyx.client.view.PortalPageEditView;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageEditGeneralView extends AbstractAdminTabPage<PortalPageEditView.Presenter> implements PortalPageEditView, IsAdminTabPage<PortalPageEditView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalPageEditGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField PortalPageEditor editor;
  
  public PortalPageEditGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {

  }
  
  public void setModel(Object page, String tag) {
    editor.setModel((PortalPage)page);
  }
  
  @Override
  public void updateModel(final Object model, final Delegate<Object> delegate) {
    PortalPage editedInstance = editor.flushModel();
    if (!validatePortalPage(editedInstance)) {
      return;
    }
    PortalPage page = (PortalPage)model;
    page.setName(editedInstance.getName());
    page.setCode(editedInstance.getCode());
    page.setOrderNm(editedInstance.getOrderNm());
    page.setParentId(editedInstance.getParentId());
    page.setTemplateName(editedInstance.getTemplateName());
    page.setVisibleInExplorer(editedInstance.getVisibleInExplorer());
    page.setVisibleInMenu(editedInstance.getVisibleInMenu());
    /* ANNULLO GLI htmls PER FAR SCATTARE IL BUGFIX PortalPageAdapterImpl@20120828 **/
    if (page instanceof WebContentPage) {
      WebContentPage webContentPage = (WebContentPage)page;
      webContentPage.setHtmls(null);
    }
    delegate.execute(page);
  }
  
  private boolean validatePortalPage(PortalPage page) {
    if (GwtUtils.isEmpty(page.getName())) {
      Window.alert("Il nome della pagina è vuoto");
      return false;
    }
    if (GwtUtils.isEmpty(page.getCode())) {
      Window.alert("Il codice della pagina è vuoto");
      return false;
    }
    if (page.getCode().contains(" ")) {
      Window.alert("Il codice della pagina non può contenere spazi");
      return false;
    }
    return true;
  }
  
}
