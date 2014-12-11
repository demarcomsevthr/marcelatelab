package it.mate.protoph.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.protoph.client.view.ApplicationApplyView.Presenter;
import it.mate.protoph.shared.model.Applicazione;
import it.mate.protoph.shared.model.PrincipioAttivo;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class ApplicationApplyView extends BaseMgwtView <Presenter> {
  
  public final static String TAG_APPLICAZIONE = "applicazione";

  public interface Presenter extends BasePresenter {
    public void deleteWorkDir(Delegate<String> delegate);
    public void applyFile(String fileName, final Delegate<String> delegate);
    public void applyApplication(Applicazione applicazione, Delegate<Applicazione> delegate);
    public void finishApplyApplication(Applicazione applicazione, Delegate<Applicazione> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ApplicationApplyView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField HTML headerBox;
  @UiField HTML applyPanel;
  
  private Applicazione applicazione;
  
  public ApplicationApplyView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    getScrollPanel().setScrollingEnabledX(false);
    getScrollPanel().setScrollingEnabledY(false);
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
  }

  @Override
  public void setModel(Object model, String tag) {
    if (TAG_APPLICAZIONE.equals(tag)) {
      this.applicazione = (Applicazione)model;
      populateApplyPanel();
      
      getPresenter().applyApplication(applicazione, new Delegate<Applicazione>() {
        public void execute(Applicazione element) {
          headerBox.setHTML("Applying " + applicazione.getNome());
        }
      });
      
      /*
      getPresenter().deleteWorkDir(new Delegate<String>() {
        public void execute(String element) {
          iteratePrincipiAttivi(applicazione.getPrincipiAttivi().iterator(), new Delegate<Void>() {
            public void execute(Void element) {
              headerBox.setHTML("Applying " + applicazione.getNome());
            }
          });
        }
      });
      */
      
    }
  }
  
  private void populateApplyPanel() {
    applyPanel.getElement().getStyle().setWidth(Window.getClientWidth(), Unit.PX);
    applyPanel.getElement().getStyle().setHeight(Window.getClientHeight(), Unit.PX);
  }
  
  private void populateApplyPanel_v0_3() {
    String html = "";
    html += "<table class='pph-OvalTable'>";
    for (int ir = 0; ir < 9; ir++) {
      html += "<tr>";
      for (int ic = 0; ic < 3; ic++) {
        html += "<td>";
        html += "<div class='pph-Oval'></div>";
        html += "</td>";
      }
      html += "</tr>";
    }
    html += "</table>";
    applyPanel.setHTML(html);
  }
  
  private void iteratePrincipiAttivi(final Iterator<PrincipioAttivo> it, final Delegate<Void> delegate) {
    if (it.hasNext()) {
      PrincipioAttivo principio = it.next();
      String fileName = principio.getPath();
      getPresenter().applyFile(fileName, new Delegate<String>() {
        public void execute(String element) {
          iteratePrincipiAttivi(it, delegate);
        }
      });
    } else {
      delegate.execute(null);
    }
  }
  
  @UiHandler ("finishBtn")
  public void onFinishBtn (TouchEndEvent event) {

    getPresenter().finishApplyApplication(applicazione, new Delegate<Applicazione>() {
      public void execute(Applicazione element) {
        getPresenter().goToPrevious();
      }
    });
    
    /*
    getPresenter().deleteWorkDir(new Delegate<String>() {
      public void execute(String element) {
        getPresenter().goToPrevious();
      }
    });
    */
    
  }
  
}
