package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.ProductListView;
import it.mate.econyx.shared.model.impl.ArticoloTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ProductListViewImpl extends AbstractBaseView<ProductListView.Presenter> implements ProductListView {
  
  public interface ViewUiBinder extends UiBinder<Widget, ProductListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) AdminTabPanel<ProductListView.Presenter> adminTab;
  
  private String width;
  
  private String height;
  
  public ProductListViewImpl() {
    this(null, null);
  }
  
  public ProductListViewImpl(String width, String height) {
    super();
    this.width = width;
    this.height = height;
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Generale")
        .setView(new ProductListGeneralView()));
    adminTab.setSections(sections);
  }
  
  protected void initProvided() {  
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setNewButtonEnabled(true).setWidth(width).setHeight(height)) {
      public void onSave(Object model) { }
      public void onNewModelRequested() {
        getPresenter().edit(new ArticoloTx());
      }
    };
  }
  
  @Override
  public void setPresenter(Presenter activity) {
    super.setPresenter(activity);
    adminTab.setPresenter(activity);
  }
  
  public void setModel(Object model, String tag) {
    adminTab.setModel(model, null);
  }
  
}
