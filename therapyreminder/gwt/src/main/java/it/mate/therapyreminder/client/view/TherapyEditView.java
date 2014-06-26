package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.StatePanel;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.NumberUtils;
import it.mate.gwtcommons.client.utils.StatePanelUtil;
import it.mate.phgcommons.client.ui.HasTag;
import it.mate.phgcommons.client.ui.TouchAnchor;
import it.mate.phgcommons.client.ui.TouchCombo;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.ui.ph.PhCalendarBox;
import it.mate.phgcommons.client.ui.ph.PhCheckBox;
import it.mate.phgcommons.client.ui.ph.PhTextBox;
import it.mate.phgcommons.client.ui.ph.PhTimeBox;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.Time;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.phgcommons.client.view.HasClosingViewHandler;
import it.mate.therapyreminder.client.constants.AppMessages;
import it.mate.therapyreminder.client.logic.MainController;
import it.mate.therapyreminder.client.ui.SignPanel;
import it.mate.therapyreminder.client.view.TherapyEditView.Presenter;
import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.UdM;
import it.mate.therapyreminder.shared.model.impl.ContattoTx;
import it.mate.therapyreminder.shared.model.impl.DosaggioEditModel;
import it.mate.therapyreminder.shared.model.impl.DosaggioTx;
import it.mate.therapyreminder.shared.model.impl.PrescrizioneTx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class TherapyEditView extends BaseMgwtView <Presenter> implements HasClosingViewHandler  {
  
  public static final String TAG_PRESCRIZIONE = "prescrizione";

  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToHome();
    public void savePrescrizione(Prescrizione newPrescrizione, Prescrizione oldPrescrizione, final Delegate<Prescrizione> delegate);
    public void goToDosageEditView(DosaggioEditModel model);
    public void getUdmDescription(Double qta, String currentUdmCode, Delegate<UdM> delegate);
    public boolean isOnlineMode();
    public void findAllTutors(Delegate<List<Contatto>> delegate);
    public void goToContactTutorListView();
    public void goToContactEditView(Contatto contatto);
    public void deletePrescrizioni(List<Prescrizione> prescrizioni);
    public void setOnlineMode(boolean onlineMode);
    public void goToSettingsView();
  }

  public interface ViewUiBinder extends UiBinder<Widget, TherapyEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  @UiField MTextBox titleBox;
  @UiField PhCalendarBox inizioBox;
  @UiField TouchCombo tipoRicorrenzaCombo;
  @UiField TouchCombo tipoOrariCombo;
  @UiField Panel orariRegolariPanel;
  @UiField Panel orariFissiPanel;
  @UiField ComplexPanel orariListPanel;
  @UiField TouchCombo umCombo;
  @UiField Panel ricorrenzaPanel;
  @UiField Label ricorrenzaLabel;
  @UiField PhTextBox qtaBox;
  @UiField StatePanel estremiPrescrizionePanel;
  @UiField StatePanel ricorrenzaPrescrizionePanel;
  @UiField StatePanel orariPrescrizionePanel;
  @UiField TouchCombo rangeCombo;
  @UiField PhTextBox rangeOrariBox;
  @UiField PhTimeBox orarioInizioBox;
  @UiField PhCheckBox cbxAlertRiordino;
  @UiField Panel alertRiordinoPanel;
  @UiField PhTextBox qtaConfezBox;
  @UiField PhTextBox qtaRiordinoBox;
  @UiField PhTextBox qtaRimanenteBox;
  @UiField PhTextBox numConfezBox;
  @UiField Panel notificheTutorPanel;
  @UiField PhCheckBox cbxNotificheTutor;
  @UiField Panel tutorPanel;
  @UiField TouchCombo tutorCombo;
  
  StatePanelUtil statePanelUtil = new StatePanelUtil();
  private Widget bottomBar = null;
  private Prescrizione oldPrescrizione;
  private Prescrizione prescrizione;
  private List<Contatto> tutors;
  
  
  public TherapyEditView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    wrapperPanel.getElement().getStyle().clearHeight();
    
    tipoRicorrenzaCombo.setItem(Prescrizione.TIPO_RICORRENZA_OGNI_GIORNO, AppMessages.IMPL.TherapyEditView_tipoRicorrenzaCombo_ogniGiorno(), true);
    tipoRicorrenzaCombo.setItem(Prescrizione.TIPO_RICORRENZA_GIORNI_ALTERNI, AppMessages.IMPL.TherapyEditView_tipoRicorrenzaCombo_giorniAlterni(), false);

// Gorini 16/06/2014: chiede di togliere questa opzione    
//  tipoRicorrenzaCombo.setItem(Prescrizione.TIPO_RICORRENZA_GIORNALIERA, "Giornaliera", false);
    
    tipoRicorrenzaCombo.setItem(Prescrizione.TIPO_RICORRENZA_SETTIMANALE, AppMessages.IMPL.TherapyEditView_tipoRicorrenzaCombo_settimanale(), false);
    tipoRicorrenzaCombo.setItem(Prescrizione.TIPO_RICORRENZA_MENSILE, AppMessages.IMPL.TherapyEditView_tipoRicorrenzaCombo_mensile(), false);
    tipoRicorrenzaCombo.setItem(Prescrizione.TIPO_RICORRENZA_TRIMESTRALE, AppMessages.IMPL.TherapyEditView_tipoRicorrenzaCombo_trimestrale(), false);
    tipoRicorrenzaCombo.setItem(Prescrizione.TIPO_RICORRENZA_SEMESTRALE, AppMessages.IMPL.TherapyEditView_tipoRicorrenzaCombo_semestrale(), false);
    tipoRicorrenzaCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        checkTipoRicorrenzaValue();
      }
    });
    
    tipoOrariCombo.setItem(Prescrizione.TIPO_ORARI_A_INTERVALLI, AppMessages.IMPL.TherapyEditView_tipoOrariCombo_aIntervalli(), false);
    tipoOrariCombo.setItem(Prescrizione.TIPO_ORARI_FISSI, AppMessages.IMPL.TherapyEditView_tipoOrariCombo_aOrariFissi(), false);
    tipoOrariCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        checkTipoOrarioValue();
      }
    });

    qtaBox.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        getPresenter().getUdmDescription(qtaBox.getValueAsDouble(), null, new Delegate<UdM>() {
          public void execute(UdM udm) {
            umCombo.setItem(udm.getCodice(), udm.getDescrizione(), umCombo.getItems().size() == 0 ? true : false);
          }
        });
      }
    });
    
    statePanelUtil.add(estremiPrescrizionePanel);
    statePanelUtil.add(ricorrenzaPrescrizionePanel);
    statePanelUtil.add(orariPrescrizionePanel);
    
    String initialStatePanel = getLastStatePanel();
    if (initialStatePanel == null) {
      initialStatePanel = estremiPrescrizionePanel.getStateId();
    }
    statePanelUtil.setCurrentState(initialStatePanel);
    setLastStatePanel(null);
    
    for (int it = 1; it <= 10; it++) {
      rangeCombo.setItem(""+it, ""+it, it == 1 ? true : false);
    }
    
    initBottomBar();
    
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
//  notificheTutorPanel.setVisible(getPresenter().isOnlineMode());
    notificheTutorPanel.setVisible(true);
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (TAG_PRESCRIZIONE.equals(tag)) {
      this.prescrizione = (Prescrizione)model;
      
      oldPrescrizione = new PrescrizioneTx(prescrizione);
      
      titleBox.setValue(prescrizione.getNome());
      inizioBox.setValue(prescrizione.getDataInizio());
      qtaBox.setValue(prescrizione.getQuantita());
      umCombo.setValue(prescrizione.getCodUdM());
      
      String tipoRicorrenza = prescrizione.getTipoRicorrenza();
      Integer valoreRicorrenza = prescrizione.getValoreRicorrenza();
      if (Prescrizione.TIPO_RICORRENZA_GIORNALIERA.equals(tipoRicorrenza) && valoreRicorrenza == 1) {
        tipoRicorrenza = Prescrizione.TIPO_RICORRENZA_OGNI_GIORNO;
      }
      if (Prescrizione.TIPO_RICORRENZA_GIORNALIERA.equals(tipoRicorrenza) && valoreRicorrenza == 2) {
        tipoRicorrenza = Prescrizione.TIPO_RICORRENZA_GIORNI_ALTERNI;
      }
      if (Prescrizione.TIPO_RICORRENZA_MENSILE.equals(tipoRicorrenza) && valoreRicorrenza == 3) {
        tipoRicorrenza = Prescrizione.TIPO_RICORRENZA_TRIMESTRALE;
      }
      if (Prescrizione.TIPO_RICORRENZA_MENSILE.equals(tipoRicorrenza) && valoreRicorrenza == 6) {
        tipoRicorrenza = Prescrizione.TIPO_RICORRENZA_SEMESTRALE;
      }
      tipoRicorrenzaCombo.setValue(tipoRicorrenza);
      rangeCombo.setValue(valoreRicorrenza.toString());
      tipoOrariCombo.setValue(prescrizione.getTipoRicorrenzaOraria());
      if (Prescrizione.TIPO_ORARI_A_INTERVALLI.equals(prescrizione.getTipoRicorrenzaOraria())) {
        rangeOrariBox.setValue(prescrizione.getIntervalloOrario());
        if (prescrizione.getDosaggi() != null && prescrizione.getDosaggi().size() == 1) {
          orarioInizioBox.setValueAsString(prescrizione.getDosaggi().get(0).getOrario());
        }
      } else if (Prescrizione.TIPO_ORARI_FISSI.equals(prescrizione.getTipoRicorrenzaOraria())) {
        showOrariListPanel();
      }
      
      cbxAlertRiordino.setValue(prescrizione.isGstAvvisoRiordino());
      qtaConfezBox.setValue(NumberUtils.doubleAsInteger(prescrizione.getQtaPerConfez()));
      qtaRiordinoBox.setValue(NumberUtils.doubleAsInteger(prescrizione.getQtaPerAvviso()));
      qtaRimanenteBox.setValue(NumberUtils.doubleAsInteger(prescrizione.getQtaRimanente()));
      numConfezBox.setValue(0);
      
      if (prescrizione.isPersistent()) {
        if (prescrizione.getTutor() != null) {
          cbxNotificheTutor.setValue(true);
        }
      } else {
        if (getPresenter().isOnlineMode()) {
          cbxNotificheTutor.setValue(true);
        }
      }
      
      if (prescrizione.isPersistent()) {
        titleBox.setReadOnly(true);
      }
      
    }
  }
  
  @Override
  public void onUnload() {
    if (bottomBar != null) {
      bottomBar.setVisible(false);
      bottomBar = null;
    }
    super.onUnload();
  }
  
  private String getLastStatePanel() {
    return (String)GwtUtils.getClientAttribute("TherapyEditView.lastStatePanel");
  }
  
  private void setLastStatePanel(String value) {
    GwtUtils.setClientAttribute("TherapyEditView.lastStatePanel", value);
  }
  
  private void checkTipoRicorrenzaValue() {
    String value = tipoRicorrenzaCombo.getValue();
    if (Prescrizione.TIPO_RICORRENZA_OGNI_GIORNO.equals(value)) {
      ricorrenzaPanel.setVisible(false);
    } else if (Prescrizione.TIPO_RICORRENZA_GIORNI_ALTERNI.equals(value)) {
        ricorrenzaPanel.setVisible(false);
    } else if (Prescrizione.TIPO_RICORRENZA_GIORNALIERA.equals(value)) {
      ricorrenzaLabel.setText("giorni");
      ricorrenzaPanel.setVisible(true);
    } else if (Prescrizione.TIPO_RICORRENZA_SETTIMANALE.equals(value)) {
      ricorrenzaLabel.setText("settimane");
      ricorrenzaPanel.setVisible(true);
    } else if (Prescrizione.TIPO_RICORRENZA_MENSILE.equals(value)) {
      ricorrenzaLabel.setText("mesi");
      ricorrenzaPanel.setVisible(true);
    } else if (Prescrizione.TIPO_RICORRENZA_TRIMESTRALE.equals(value)) {
      ricorrenzaPanel.setVisible(false);
    } else if (Prescrizione.TIPO_RICORRENZA_SEMESTRALE.equals(value)) {
      ricorrenzaPanel.setVisible(false);
    } else {
      ricorrenzaPanel.setVisible(false);
    }
    refreshScrollPanel();
  }
  
  private void checkTipoOrarioValue() {
    String value = tipoOrariCombo.getValue();
    if (Prescrizione.TIPO_ORARI_A_INTERVALLI.equals(value)) {
      orariRegolariPanel.setVisible(true);
      orariFissiPanel.setVisible(false);
    } else if (Prescrizione.TIPO_ORARI_FISSI.equals(value)) {
      orariRegolariPanel.setVisible(false);
      orariFissiPanel.setVisible(true);
      showOrariListPanel();
      refreshScrollPanel();
    } else {
      orariRegolariPanel.setVisible(false);
      orariFissiPanel.setVisible(false);
    }
  }
  
  private void showOrariListPanel() {
    if (prescrizione.getDosaggi().size() == 0) {
      prescrizione.getDosaggi().add(new DosaggioTx());
    }
    Collections.sort(prescrizione.getDosaggi(), new Comparator<Dosaggio>() {
      public int compare(Dosaggio d1, Dosaggio d2) {
        if (d1.getOrario() != null && d2.getOrario() != null) {
          return d1.getOrario().compareTo(d2.getOrario());
        }
        return 0;
      }
    });
    orariListPanel.clear();
    HorizontalPanel row = null;
    PhTimeBox lastOrarioBox = null;
    for (Dosaggio dosaggio : prescrizione.getDosaggi()) {
      final PhTimeBox orarioBox = createOrarioBox(dosaggio);
      orarioBox.setValueAsString(dosaggio.getOrario());
      orarioBox.setDefaultTime(new Time().setMinutes(00).incHours(+1));
      orarioBox.addValueChangeHandler(new ValueChangeHandler<Time>() {
        public void onValueChange(ValueChangeEvent<Time> event) {
          if (event.getValue() != null) {
            ((Dosaggio)orarioBox.getModel()).setOrario(orarioBox.getValue().asString());
          }
        }
      });
      row = new HorizontalPanel();
      row.add(orarioBox);
      TouchHTML editBtn = new TouchHTML();
      editBtn.addStyleName("ui-edit-btn");
      editBtn.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          if (orarioBox.getValue() != null) {
            
            savePrescrizione(flushPrescrizione(true), new Delegate<Prescrizione>() {
              public void execute(Prescrizione prescrizioneSalvata) {
                
                Dosaggio dosaggio = (Dosaggio)orarioBox.getModel();
                if (dosaggio.getQuantita() == null) {
                  dosaggio.setQuantita(qtaBox.getValueAsDouble());
                }
                dosaggio.setCodUdM(umCombo.getValue());
                setLastStatePanel(orariPrescrizionePanel.getStateId());
                
                getPresenter().goToDosageEditView(new DosaggioEditModel(prescrizione, dosaggio));
                
              }
            });
            
          }
        }
      });
      row.add(editBtn);
      TouchHTML removeBtn = new TouchHTML();
      removeBtn.addStyleName("ui-remove-btn");
      row.add(removeBtn);
      removeBtn.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          prescrizione.getDosaggi().remove(((Dosaggio)orarioBox.getModel()));
          showOrariListPanel();
        }
      });
      orariListPanel.add(row);
      lastOrarioBox = orarioBox;
    }
    final TouchHTML addBtn = new TouchHTML();
    addBtn.setVisible(false);
    addBtn.addStyleName("ui-add-btn");
    row.add(addBtn);
    addBtn.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        prescrizione.getDosaggi().add(new DosaggioTx());
        showOrariListPanel();
      }
    });
    if (lastOrarioBox != null) {
      if (lastOrarioBox.getValue() != null) {
        addBtn.setVisible(true);
      } else {
        lastOrarioBox.addValueChangeHandler(new ValueChangeHandler<Time>() {
          public void onValueChange(ValueChangeEvent<Time> event) {
            if (event.getValue() != null) {
              addBtn.setVisible(true);
            }
          }
        });
      }
    }
    orariListPanel.add(row);
    refreshScrollPanel();
  }
  
  private PhTimeBox createOrarioBox(Dosaggio dosaggio) {
    PhTimeBox orarioBox = new PhTimeBox();
    orarioBox.addStyleName("ui-app-timebox");
    orarioBox.setModel(dosaggio);
    return orarioBox;
  }
  
  private void initBottomBar() {
    HorizontalPanel bottomBar = new HorizontalPanel();
    bottomBar.addStyleName("ui-bottom-button-bar");
    bottomBar.setSpacing(0);
    initBottomBarItem(bottomBar, AppMessages.IMPL.TherapyEditView_bottomBar_what(), "what", "estremiPrescrizionePanel");
    initBottomBarItem(bottomBar, AppMessages.IMPL.TherapyEditView_bottomBar_when(), "when", "ricorrenzaPrescrizionePanel");
    initBottomBarItem(bottomBar, AppMessages.IMPL.TherapyEditView_bottomBar_hours(), "hours", "orariPrescrizionePanel");
    RootPanel.get().add(bottomBar);
    this.bottomBar = bottomBar;
  }
  
  private void initBottomBarItem(Panel bottomBar, String text, String css, String tag) {
    TouchAnchor button = new TouchAnchor();
    button.setText(text);
    button.addStyleName("ui-bottom-action-btn");
    button.addStyleName("ui-bottom-action-"+css+"-btn");
    button.setTag(tag);
    button.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        HasTag taggable = (HasTag)event.getSource();
        statePanelUtil.setCurrentState(taggable.getTag());
        checkTipoOrarioValue();
        checkTipoRicorrenzaValue();
      }
    });
    bottomBar.add(button);
  }
  
  public static int getBottomBarHeightPx() {
    return (Window.getClientHeight() / 8);
  }
  public static String getBottomBarHeight() {
    return getBottomBarHeightPx()+"px";
  }
  public static int getBottomBarTopPx() {
    return (Window.getClientHeight() - getBottomBarHeightPx());
  }
  public static String getBottomBarTop() {
    return getBottomBarTopPx()+"px";
  }

  @UiHandler ("saveBtn")
  public void onSaveBtn (TouchEndEvent event) {
    savePrescrizione(flushPrescrizione(true), null);
  }
  
  @UiHandler ("deleteBtn")
  public void onDeleteBtn (TouchEndEvent event) {
    if (oldPrescrizione.getId() != null) {
      PhgDialogUtils.showMessageDialog(AppMessages.IMPL.TherapyEditView_onDeleteBtn_msg1(), "Confirm", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
        public void execute(Integer selectedButton) {
          if (selectedButton == 1) {
            List<Prescrizione> prescrizioni = new ArrayList<Prescrizione>();
            prescrizioni.add(oldPrescrizione);
            getPresenter().deletePrescrizioni(prescrizioni);
          }
        }
      });
    }
  }
  
  @Override
  public void onClosingView(final ClosingHandler handler) {
    final Prescrizione prescrizione = flushPrescrizione(false);
    if (prescrizione == null || prescrizione.equals(oldPrescrizione)) {
      handler.doClose();
    } else {
      PhgDialogUtils.showMessageDialog(AppMessages.IMPL.TherapyEditView_onClosingView_msg1(), "Confirm", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
        public void execute(Integer selectedButton) {
          if (selectedButton == 1) {
            savePrescrizione(prescrizione, new Delegate<Prescrizione>() {
              public void execute(Prescrizione prescrizioneSalvata) {
                handler.doClose();
              }
            });
          } else {
            handler.doClose();
          }
        }
      });
    }
  }

  private Prescrizione flushPrescrizione(boolean verbose) {
    Double qtaUnica = qtaBox.getValueAsDouble();
    prescrizione.setId(oldPrescrizione.getId());
    prescrizione.setNome(titleBox.getValue());
    prescrizione.setDataInizio(inizioBox.getValue());
    prescrizione.setQuantita(qtaUnica);
    prescrizione.setCodUdM(umCombo.getValue());
    String tipoRicorrenza = tipoRicorrenzaCombo.getValue();
    Integer valoreRicorrenza = null;
    /*
    Integer valoreRicorrenza = rangeBox.getValueAsInteger();
    */
    if (Prescrizione.TIPO_RICORRENZA_OGNI_GIORNO.equals(tipoRicorrenza)) {
      tipoRicorrenza = Prescrizione.TIPO_RICORRENZA_GIORNALIERA;
      valoreRicorrenza = 1;
    } else if (Prescrizione.TIPO_RICORRENZA_GIORNI_ALTERNI.equals(tipoRicorrenza)) { 
      tipoRicorrenza = Prescrizione.TIPO_RICORRENZA_GIORNALIERA;
      valoreRicorrenza = 2;
    } else if (Prescrizione.TIPO_RICORRENZA_TRIMESTRALE.equals(tipoRicorrenza)) { 
      tipoRicorrenza = Prescrizione.TIPO_RICORRENZA_MENSILE;
      valoreRicorrenza = 3;
    } else if (Prescrizione.TIPO_RICORRENZA_SEMESTRALE.equals(tipoRicorrenza)) { 
      tipoRicorrenza = Prescrizione.TIPO_RICORRENZA_MENSILE;
      valoreRicorrenza = 6;
    } else {
      valoreRicorrenza = Integer.parseInt(rangeCombo.getValue());
    }
    prescrizione.setTipoRicorrenza(tipoRicorrenza);
    prescrizione.setValoreRicorrenza(valoreRicorrenza);
    prescrizione.setTipoRicorrenzaOraria(tipoOrariCombo.getValue());
    if (Prescrizione.TIPO_ORARI_A_INTERVALLI.equals(prescrizione.getTipoRicorrenzaOraria())) {
      prescrizione.setIntervalloOrario(rangeOrariBox.getValueAsInteger());
      if (orarioInizioBox.getValue() == null) {
        if (verbose) {
          PhgDialogUtils.showMessageDialog(AppMessages.IMPL.TherapyEditView_flushPrescrizione_msg1(), "Alert", PhgDialogUtils.BUTTONS_OK);
        }
        return null;
      }
      prescrizione.setDosaggi(new ArrayList<Dosaggio>());
      prescrizione.getDosaggi().add(new DosaggioTx(qtaUnica, orarioInizioBox.getValue().asString()));
    } else if (Prescrizione.TIPO_ORARI_FISSI.equals(prescrizione.getTipoRicorrenzaOraria())) {
      if (prescrizione.getDosaggi() == null) {
        if (verbose) {
          PhgDialogUtils.showMessageDialog(AppMessages.IMPL.TherapyEditView_flushPrescrizione_msg2(), "Alert", PhgDialogUtils.BUTTONS_OK);
        }
        return null;
      }
      for (Dosaggio dosaggio : prescrizione.getDosaggi()) {
        if (dosaggio.getOrario() == null) {
          if (verbose) {
            PhgDialogUtils.showMessageDialog(AppMessages.IMPL.TherapyEditView_flushPrescrizione_msg3(), "Alert", PhgDialogUtils.BUTTONS_OK);
          }
          return null;
        }
        if (dosaggio.getQuantita() == null) {
          dosaggio.setQuantita(qtaUnica);
        }
      }
    }
    
    prescrizione.setGstAvvisoRiordino(cbxAlertRiordino.getValue());
    prescrizione.setQtaPerAvviso(qtaRiordinoBox.getValueAsDouble());
    prescrizione.setQtaPerConfez(qtaConfezBox.getValueAsDouble());
    prescrizione.setQtaRimanente(qtaRimanenteBox.getValueAsDouble());
    
    prescrizione.setTutor(getSelectedTutor());

    return prescrizione;
    
  }

  private void savePrescrizione(Prescrizione prescrizione, Delegate<Prescrizione> successDelegate) {
    if (prescrizione == null) {
      return;
    }
    
    if (prescrizione.equals(oldPrescrizione)) {
      if (successDelegate != null)
        successDelegate.execute(prescrizione);
      return;
    }
    
    String validateError = MainController.validatePrescrizione(prescrizione);
    if (validateError != null) {
      PhgDialogUtils.showMessageDialog(validateError, "Alert", PhgDialogUtils.BUTTONS_OK);
    } else {
      getPresenter().savePrescrizione(prescrizione, oldPrescrizione, successDelegate);
    }
  }
  
  @UiHandler ("cbxAlertRiordino")
  public void onCkbAlertRiordino(ValueChangeEvent<Boolean> event) {
    alertRiordinoPanel.setVisible(event.getValue());
    if (event.getValue()) {
      refreshScrollPanel();
    }
  }
  
  @UiHandler ("numConfezBox")
  public void onNumConfezBox(ValueChangeEvent<String> event) {
    updateQtaRimanenteBox();
  }
  
  @UiHandler ("qtaConfezBox")
  public void onQtaConfezBox(ValueChangeEvent<String> event) {
    updateQtaRimanenteBox();
  }
  
  private void updateQtaRimanenteBox() {
    Integer nuoveConfezioni = numConfezBox.getValueAsInt();
    if (nuoveConfezioni > 0) {
      int qtaPerConfezione = qtaConfezBox.getValueAsInt();
      int qtaRimanenteIniziale = NumberUtils.doubleAsInt(oldPrescrizione.getQtaRimanente());
      qtaRimanenteBox.setValue(qtaRimanenteIniziale + nuoveConfezioni * qtaPerConfezione);
    }
  }
  
  @UiHandler ("cbxNotificheTutor")
  public void onCbxNotificheTutor(ValueChangeEvent<Boolean> event) {
    tutorPanel.setVisible(event.getValue());
    fillTutorCombo();
    refreshScrollPanel();
  }
  
  private void fillTutorCombo() {
    if (cbxNotificheTutor.getValue()) {

      if (!getPresenter().isOnlineMode()) {
        PhgDialogUtils.showMessageDialog(AppMessages.IMPL.TherapyEditView_fillTutorCombo_msg2(), 
            "Alert", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
              public void execute(Integer btn) {
                if (btn == 1) {
                  getPresenter().setOnlineMode(true);
                  getPresenter().goToSettingsView();
                } else {
                  cbxNotificheTutor.setValue(false);
                }
              }
            });
      } else if (tutorCombo.getItems().size() == 0) {
        getPresenter().findAllTutors(new Delegate<List<Contatto>>() {
          public void execute(List<Contatto> results) {
            tutors = results;
            if (results != null && results.size() > 0) {
              boolean onlyonerow = (results.size() == 1);
              for (Contatto tutor : results) {
                boolean selected = onlyonerow;
                if (prescrizione != null && prescrizione.getTutor() != null) {
                  if (prescrizione.getTutor().getId().equals(tutor.getId())) {
                    selected = true;
                  }
                }
                tutorCombo.setItem(tutor.getId().toString(), tutor.getNome(), selected);
              }
            } else {
              PhgDialogUtils.showMessageDialog(AppMessages.IMPL.TherapyEditView_fillTutorCombo_msg1(), 
                  "Alert", PhgDialogUtils.BUTTONS_YESNO, new Delegate<Integer>() {
                    public void execute(Integer btn) {
                      if (btn == 1) {
                        //getPresenter().goToContactTutorListView();
                        getPresenter().goToContactEditView(new ContattoTx(Contatto.TIPO_TUTOR));
                      } else {
                        cbxNotificheTutor.setValue(false);
                      }
                    }
                  });
            }
          }
        });
      }
      
    }
  }
  
  private Contatto getSelectedTutor() {
    String selectedTutorId = tutorCombo.getValue();
    if (tutors != null && selectedTutorId != null) {
      for (Contatto tutor : tutors) {
        if (tutor.getId().toString().equals(selectedTutorId)) {
          return tutor;
        }
      }
    }
    return null;
  }

}
