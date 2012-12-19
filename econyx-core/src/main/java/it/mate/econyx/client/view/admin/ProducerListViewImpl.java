package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.ProducerListView;
import it.mate.econyx.shared.model.Produttore;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ProducerListViewImpl extends AbstractBaseView<ProducerListView.Presenter> implements ProducerListView {

  public interface ViewUiBinder extends UiBinder<Widget, ProducerListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) AdminTabPanel<ProducerListView.Presenter> adminTab;
  
  
  public ProducerListViewImpl() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Generale")
        .setView(new ProducerListGeneralView()));
    adminTab.setSections(sections);
  }


  protected void initProvided() {  
    adminTab = new AdminTabPanel<Presenter>(
          new AdminTabPanel.Configuration()
        ) {
      public void onSave(Object model) { }
      public void onNewModelRequested() {
      }
      public void onEdit(Object model) {
        if (model instanceof Produttore) {
          getPresenter().edit((Produttore)model);
        }
      }
      public void onDelete(Object model) {
        getPresenter().delete((Produttore)model);
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
