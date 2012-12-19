package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.view.PortalPageExplorerView;
import it.mate.econyx.client.view.custom.PortalPageExplorerViewCustomizer;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageExplorerViewImpl extends AbstractBaseView<PortalPageExplorerView.Presenter> implements PortalPageExplorerView {

  public interface ViewUiBinder extends UiBinder<Widget, PortalPageExplorerViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField VerticalPanel vPanel;
  
  private PortalPageExplorerViewCustomizer customizer = AppClientFactory.Customizer.cast().getPortalPageExplorerViewCustomizer();
  
  public PortalPageExplorerViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof TreeModel) {
      TreeModel treeModel = (TreeModel)model;
      if (treeModel.childreen != null && treeModel.childreen.size() > 0) {
        customizer.setPresenter(getPresenter());
        customizer.applyModelOnPanel(treeModel, vPanel);
      }
    }
  }
  
  
}
