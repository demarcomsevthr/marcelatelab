package it.mate.copymob.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.ui.OnsList;
import it.mate.onscommons.client.ui.OnsListItem;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.copymob.client.view.ApplicationListView.Presenter;
import it.mate.copymob.shared.model.Applicazione;
import it.mate.copymob.shared.model.impl.ApplicazioneTx;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationListView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToHomeView();
    public void goToApplicationEditView(Applicazione applicazione);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ApplicationListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField OnsList listPanel;
  
  public ApplicationListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof List) {
      List<Applicazione> applicazioni = (List<Applicazione>)model;
      for (final Applicazione applicazione : applicazioni) {
        OnsListItem item = new OnsListItem(applicazione.getNome());
        PhgUtils.log("adding " + applicazione);
        listPanel.add(item);
        item.addTapHandler(new TapHandler() {
          public void onTap(TapEvent event) {
            getPresenter().goToApplicationEditView(applicazione);
          }
        });
      }
    }
  }
  
  @UiHandler("btnAdd")
  public void onBtnAdd(TapEvent event) {
    getPresenter().goToApplicationEditView(new ApplicazioneTx("untitled"));
  }
  
}
