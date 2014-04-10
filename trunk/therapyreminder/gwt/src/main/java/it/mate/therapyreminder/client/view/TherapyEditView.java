package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.StatePanel;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.StatePanelUtil;
import it.mate.phgcommons.client.ui.HasTag;
import it.mate.phgcommons.client.ui.TouchAnchor;
import it.mate.phgcommons.client.ui.TouchCombo;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.ui.ph.PhCalendarBox;
import it.mate.phgcommons.client.ui.ph.PhTextBox;
import it.mate.phgcommons.client.ui.ph.PhTimeBox;
import it.mate.phgcommons.client.utils.PhgDialogUtils;
import it.mate.phgcommons.client.utils.Time;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.ui.SignPanel;
import it.mate.therapyreminder.client.view.TherapyEditView.Presenter;
import it.mate.therapyreminder.shared.model.Dosaggio;
import it.mate.therapyreminder.shared.model.Prescrizione;
import it.mate.therapyreminder.shared.model.UdM;
import it.mate.therapyreminder.shared.model.impl.DosaggioTx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

public class TherapyEditView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_PRESCRIZIONE = "prescrizione";

  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToHome();
    public void savePrescrizione(Prescrizione prescrizione, Delegate<Prescrizione> delegate);
    public void goToDosageEditView(Dosaggio dosaggio);
    public void adaptUmDescription(Double qta, String currentUdmCode, Delegate<UdM> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, TherapyEditView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  @UiField MTextBox titleBox;
  @UiField PhCalendarBox inizioBox;
//@UiField TouchCombo tipoTerapiaCombo;
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
  @UiField PhTextBox rangeBox;
  @UiField PhTextBox rangeOrariBox;
  @UiField PhTimeBox orarioInizioBox;
  
  StatePanelUtil statePanelUtil = new StatePanelUtil();
  
  private Widget bottomBar = null;
  
  private Prescrizione prescrizione;
  
  public TherapyEditView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    wrapperPanel.getElement().getStyle().clearHeight();
    
    /*
    tipoTerapiaCombo.addItem("O", "Orale", false);
    tipoTerapiaCombo.addItem("I", "Intramuscolare", false);
    tipoTerapiaCombo.addItem("E", "Endovenosa", false);
    tipoTerapiaCombo.addItem("S", "Sottocute", false);
    tipoTerapiaCombo.addItem("T", "Transcutanea", false);
    tipoTerapiaCombo.addItem("R", "Rettale", false);
    tipoTerapiaCombo.addItem("C", "Oculare", false);
    tipoTerapiaCombo.addItem("L", "Sublinguale", false);
    tipoTerapiaCombo.addItem("A", "Altro", false);
    tipoTerapiaCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        PhonegapUtils.log("selected value is " + event.getValue());
      }
    });
    */
    
    tipoRicorrenzaCombo.setItem(Prescrizione.TIPO_RICORRENZA_GIORNALIERA, "Giornaliera", false);
    tipoRicorrenzaCombo.setItem(Prescrizione.TIPO_RICORRENZA_SETTIMANALE, "Settimanale", false);
    tipoRicorrenzaCombo.setItem(Prescrizione.TIPO_RICORRENZA_MENSILE, "Mensile", false);
    tipoRicorrenzaCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        checkTipoRicorrenzaValue();
      }
    });
    
    tipoOrariCombo.setItem(Prescrizione.TIPO_ORARI_A_INTERVALLI, "A intervalli regolari", false);
    tipoOrariCombo.setItem(Prescrizione.TIPO_ORARI_FISSI, "A orari fissi", false);
    tipoOrariCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        checkTipoOrarioValue();
      }
    });

    qtaBox.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        getPresenter().adaptUmDescription(qtaBox.getValueAsDouble(), null, new Delegate<UdM>() {
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
    
    initBottomBar();
    
  }
  
  private String getLastStatePanel() {
    return (String)GwtUtils.getClientAttribute("TherapyEditView.lastStatePanel");
  }
  
  private void setLastStatePanel(String value) {
    GwtUtils.setClientAttribute("TherapyEditView.lastStatePanel", value);
  }
  
  private void checkTipoRicorrenzaValue() {
    String value = tipoRicorrenzaCombo.getValue();
    if (Prescrizione.TIPO_RICORRENZA_GIORNALIERA.equals(value)) {
      ricorrenzaLabel.setText("giorni");
      ricorrenzaPanel.setVisible(true);
    } else if (Prescrizione.TIPO_RICORRENZA_SETTIMANALE.equals(value)) {
      ricorrenzaLabel.setText("settimane");
      ricorrenzaPanel.setVisible(true);
    } else if (Prescrizione.TIPO_RICORRENZA_MENSILE.equals(value)) {
      ricorrenzaLabel.setText("mesi");
      ricorrenzaPanel.setVisible(true);
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
  
  @Override
  public void onUnload() {
    if (bottomBar != null) {
      bottomBar.setVisible(false);
      bottomBar = null;
    }
    super.onUnload();
  }
  
  private void showOrariListPanel() {
    if (prescrizione.getDosaggi().size() == 0) {
      prescrizione.getDosaggi().add(new DosaggioTx(prescrizione));
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
      orarioBox.setDefaultTime(new Time().setMinutes(00));
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
            
            flushPrescrizione(new Delegate<Prescrizione>() {
              public void execute(Prescrizione prescrizione) {
                getPresenter().savePrescrizione(prescrizione, new Delegate<Prescrizione>() {
                  public void execute(Prescrizione prescrizione) {
                    
                    Dosaggio dosaggio = (Dosaggio)orarioBox.getModel();
                    if (dosaggio.getQuantita() == null) {
                      dosaggio.setQuantita(qtaBox.getValueAsDouble());
                    }
                    dosaggio.setCodUdM(umCombo.getValue());
                    setLastStatePanel(orariPrescrizionePanel.getStateId());
                    getPresenter().goToDosageEditView(dosaggio);
                    
                  }
                });
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
        prescrizione.getDosaggi().add(new DosaggioTx(prescrizione));
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
//  WebkitCssUtil.moveScrollPanelY(getScrollPanelImpl(), 50);
  }
  
  private PhTimeBox createOrarioBox(Dosaggio dosaggio) {
    PhTimeBox orarioBox = new PhTimeBox();
    orarioBox.addStyleName("ui-app-timebox");
    orarioBox.setModel(dosaggio);
    return orarioBox;
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
  }
  
  private void refreshScrollPanel() {
    getScrollPanel().refresh();
  }

  private void initBottomBar() {
    HorizontalPanel bottomBar = new HorizontalPanel();
    bottomBar.addStyleName("ui-bottom-button-bar");
    bottomBar.setSpacing(0);
    initBottomBarItem(bottomBar, "What", "what", "estremiPrescrizionePanel");
    initBottomBarItem(bottomBar, "When", "when", "ricorrenzaPrescrizionePanel");
    initBottomBarItem(bottomBar, "Hours", "hours", "orariPrescrizionePanel");
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

  @Override
  public void setModel(Object model, String tag) {
    if (TAG_PRESCRIZIONE.equals(tag)) {
      this.prescrizione = (Prescrizione)model;
      titleBox.setValue(prescrizione.getNome());
      inizioBox.setValue(prescrizione.getDataInizio());
      qtaBox.setValue(prescrizione.getQuantita());
      umCombo.setValue(prescrizione.getCodUdM());
      tipoRicorrenzaCombo.setValue(prescrizione.getTipoRicorrenza());
      rangeBox.setValue(prescrizione.getValoreRicorrenza());
      tipoOrariCombo.setValue(prescrizione.getTipoRicorrenzaOraria());
      if (Prescrizione.TIPO_ORARI_A_INTERVALLI.equals(prescrizione.getTipoRicorrenzaOraria())) {
        rangeOrariBox.setValue(prescrizione.getIntervalloOrario());
        if (prescrizione.getDosaggi() != null && prescrizione.getDosaggi().size() == 1) {
          orarioInizioBox.setValueAsString(prescrizione.getDosaggi().get(0).getOrario());
        }
      } else if (Prescrizione.TIPO_ORARI_FISSI.equals(prescrizione.getTipoRicorrenzaOraria())) {
        showOrariListPanel();
      }
    }
  }
  
  @UiHandler ("saveBtn")
  public void onSaveBtn (TouchEndEvent event) {
    flushPrescrizione(new Delegate<Prescrizione>() {
      public void execute(Prescrizione prescrizione) {
        getPresenter().savePrescrizione(prescrizione, null);
      }
    });
  }
  
  private void flushPrescrizione(Delegate<Prescrizione> delegate) {
    Double qtaUnica = qtaBox.getValueAsDouble();
    prescrizione.setNome(titleBox.getValue());
    prescrizione.setDataInizio(inizioBox.getValue());
    prescrizione.setQuantita(qtaUnica);
    prescrizione.setTipoRicorrenza(tipoRicorrenzaCombo.getValue());
    prescrizione.setCodUdM(umCombo.getValue());
    prescrizione.setValoreRicorrenza(rangeBox.getValueAsInteger());
    prescrizione.setTipoRicorrenzaOraria(tipoOrariCombo.getValue());
    if (Prescrizione.TIPO_ORARI_A_INTERVALLI.equals(prescrizione.getTipoRicorrenzaOraria())) {
      prescrizione.setIntervalloOrario(rangeOrariBox.getValueAsInteger());
      if (orarioInizioBox.getValue() == null) {
        PhgDialogUtils.showMessageDialog("Devi inserire l'orario di inizio prescrizione", "Alert", PhgDialogUtils.BUTTONS_OK);
        return;
      }
      prescrizione.setDosaggi(new ArrayList<Dosaggio>());
      prescrizione.getDosaggi().add(new DosaggioTx(prescrizione, qtaUnica, orarioInizioBox.getValue().asString()));
    } else if (Prescrizione.TIPO_ORARI_FISSI.equals(prescrizione.getTipoRicorrenzaOraria())) {
      for (Dosaggio dosaggio : prescrizione.getDosaggi()) {
        if (dosaggio.getQuantita() == null) {
          dosaggio.setQuantita(qtaUnica);
        }
      }
    }
    delegate.execute(prescrizione);
  }

}
