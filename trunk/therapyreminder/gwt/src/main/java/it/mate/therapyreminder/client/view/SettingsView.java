package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.plugins.EmailComposerPlugin;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.dao.AppSqlDao;
import it.mate.therapyreminder.client.factories.AppClientFactory;
import it.mate.therapyreminder.client.ui.SignPanel;
import it.mate.therapyreminder.client.view.SettingsView.Presenter;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.Somministrazione;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class SettingsView extends BaseMgwtView <Presenter> {
  
  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToHome();
    public void dropDB();
  }

  public interface ViewUiBinder extends UiBinder<Widget, SettingsView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  public SettingsView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    wrapperPanel.getElement().getStyle().clearHeight();
    
  }
  

  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  @UiHandler ("resetBtn")
  public void onResetBtn (TouchEndEvent event) {
    getPresenter().dropDB();
  }
  
  private class EmailWrapper {
    private String body = "";
    private String[] toRecipients = new String[] {"dev.medup@gmail.com"};
    protected void println(String row) {
      body += row + "<br/>";
    }
    protected void showMailComposer() {
      EmailComposerPlugin.showEmailComposer("THR db dump", body, toRecipients, null, null, true, null, null);
    }
  }

  @UiHandler ("dumpBtn")
  public void onDumpBtn (TouchEndEvent event) {

    final AppSqlDao dao = AppClientFactory.IMPL.getAppSqlDao();
    
    final EmailWrapper email = new EmailWrapper();
    
    dao.setErrorDelegate(new Delegate<String>() {
      public void execute(String error) {
        PhonegapUtils.log(">>> intercettato errore db " + error);
        dao.setErrorDelegate(null);
        email.println(error);
        email.showMailComposer();
      }
    });
    
    email.println(">>> START DUMP <<<");
    email.println("finding all prescrizioni...");
    dao.findAllPrescrizioni(new Delegate<List<Prescrizione>>() {
      public void execute(List<Prescrizione> prescrizioni) {
        if (prescrizioni == null)
          prescrizioni = new ArrayList<Prescrizione>();
        email.println("found " + prescrizioni.size() + " prescrizioni");
        for (Prescrizione prescrizione : prescrizioni) {
          email.println("found " + prescrizione);
          email.println("finding all somministrazioni by prescrizione...");
          
          dao.findSomministrazioniSchedulateByPrescrizione(prescrizione, new Delegate<List<Somministrazione>>() {
            public void execute(List<Somministrazione> somministrazioni) {
              if (somministrazioni == null)
                somministrazioni = new ArrayList<Somministrazione>();
              email.println("found " + somministrazioni.size() + " somministrazioni");
              for (Somministrazione somministrazione : somministrazioni) {
                email.println("found " + somministrazione);
              }
              
              email.println(">>> END DUMP <<<");
              dao.setErrorDelegate(null);
              email.showMailComposer();
              
            }
          });
          
        }
      }
    });

  }

}
