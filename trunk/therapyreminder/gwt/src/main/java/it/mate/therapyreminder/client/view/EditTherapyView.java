package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.TouchCombo;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.ui.ph.PhTimeBox;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.ui.SignPanel;
import it.mate.therapyreminder.client.view.EditTherapyView.Presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class EditTherapyView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter, SignPanel.Presenter {
    public void goToHome();
  }

  public interface ViewUiBinder extends UiBinder<Widget, EditTherapyView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField TouchCombo tipoTerapiaCombo;
  @UiField TouchCombo tipoRicorrenzaCombo;
  @UiField Panel ricorrenzaGiornalieraPanel;
  @UiField Panel ricorrenzaSettimanalePanel;
  @UiField Panel ricorrenzaMensilePanel;
  @UiField TouchCombo tipoOrariCombo;
  @UiField Panel orariRegolariPanel;
  @UiField Panel orariFissiPanel;
  @UiField ComplexPanel orariListPanel;
  @UiField TouchCombo umCombo;
  
  @UiField MTextBox qtaBox;
  
  @UiField Spacer filler;
  
  List<PhTimeBox> orariBox = new ArrayList<PhTimeBox>();
  
  int initialFillerHeight;
  
  private String[] umDescriptions = new String[] {
      "Compress/a/e",
      "Fial/a/e",
      "Ovul/o/i",
      "Suppost/a/e",
      "Gocc/ia/e",
      "Bustin/a/e",
      "Garz/a/e",
      "Flacon/e/i",
      "Capsul/a/e",
      "Confett/o/i",
      "Flacon/e/i"
    };
  
  public EditTherapyView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initHeaderBackButton(SafeHtmlUtils.fromTrustedString("<img src='main/images/home-back.png'/>"), new Delegate<TapEvent>() {
      public void execute(TapEvent element) {
        getPresenter().goToHome();
      }
    });
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    initialFillerHeight = filler.getOffsetHeight();
    
    wrapperPanel.getElement().getStyle().clearHeight();
    
    tipoTerapiaCombo.addItem("O", "Orale", false);
    tipoTerapiaCombo.addItem("I", "Intramuscolare", false);
    tipoTerapiaCombo.addItem("E", "Endovenosa", false);
    tipoTerapiaCombo.addItem("S", "Sottocute", false);
    tipoTerapiaCombo.addItem("T", "Transcutanea", false);
    tipoTerapiaCombo.addItem("R", "Rettale", false);
    tipoTerapiaCombo.addItem("C", "Oculare", false);
    tipoTerapiaCombo.addItem("L", "Sublinguale", false);
    tipoTerapiaCombo.addItem("A", "Altro", false);
    /*
    tipoTerapiaCombo.addItem("4", "Infusionale", false);
    tipoTerapiaCombo.addItem("5", "Insulinica", false);
    tipoTerapiaCombo.addItem("6", "Antibiotica", false);
    */
    tipoTerapiaCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        PhonegapUtils.log("selected value is " + event.getValue());
      }
    });
    
//  tipoRicorrenzaCombo.addItem("1", "Tutti i giorni", false);
    tipoRicorrenzaCombo.addItem("2", "Giornaliera", false);
    tipoRicorrenzaCombo.addItem("3", "Settimanale", false);
    tipoRicorrenzaCombo.addItem("4", "Mensile", false);
    tipoRicorrenzaCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        if ("1".equals(event.getValue())) {
          ricorrenzaGiornalieraPanel.setVisible(false);
          ricorrenzaSettimanalePanel.setVisible(false);
          ricorrenzaMensilePanel.setVisible(false);
        } else if ("2".equals(event.getValue())) {
          ricorrenzaGiornalieraPanel.setVisible(true);
          ricorrenzaSettimanalePanel.setVisible(false);
          ricorrenzaMensilePanel.setVisible(false);
        } else if ("3".equals(event.getValue())) {
          ricorrenzaGiornalieraPanel.setVisible(false);
          ricorrenzaSettimanalePanel.setVisible(true);
          ricorrenzaMensilePanel.setVisible(false);
        } else if ("4".equals(event.getValue())) {
          ricorrenzaGiornalieraPanel.setVisible(false);
          ricorrenzaSettimanalePanel.setVisible(false);
          ricorrenzaMensilePanel.setVisible(true);
        }
        refreshScrollPanel();
      }
    });
    
    tipoOrariCombo.addItem("1", "A intervalli regolari", false);
    tipoOrariCombo.addItem("2", "A orari fissi", false);
    tipoOrariCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        if ("1".equals(event.getValue())) {
          orariRegolariPanel.setVisible(true);
          orariFissiPanel.setVisible(false);
        } else if ("2".equals(event.getValue())) {
          orariRegolariPanel.setVisible(false);
          orariFissiPanel.setVisible(true);
          showOrariListPanel();
        }
        refreshScrollPanel();
      }
    });

    qtaBox.addBlurHandler(new BlurHandler() {
      public void onBlur(BlurEvent event) {
        adaptUmDescription(qtaBox.getValue());
      }
    });
    
    adaptUmDescription(qtaBox.getValue());

  }
  
  private void adaptUmDescription(String value) {
    boolean singular = "1".equals(value.trim());
    for (int it = 0; it < umDescriptions.length; it++) {
      String[] tokens = umDescriptions[it].split("/");
      String desc = tokens[0] + (singular ? tokens[1] : tokens[2]);
      umCombo.addItem(""+it, desc, it == 0 ? true : false);
    }
  }
  
  private void showOrariListPanel() {
    if (orariBox.size() == 0) {
      orariBox.add(createOrarioBox());
    }
    orariListPanel.clear();
    HorizontalPanel row = null;
    for (final PhTimeBox orarioBox : orariBox) {
      row = new HorizontalPanel();
      row.add(orarioBox);
      TouchHTML removeBtn = new TouchHTML();
      removeBtn.addStyleName("ui-remove-btn");
      row.add(removeBtn);
      removeBtn.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          orariBox.remove(orarioBox);
          showOrariListPanel();
        }
      });
      orariListPanel.add(row);
    }
    TouchHTML addBtn = new TouchHTML();
    addBtn.addStyleName("ui-add-btn");
    row.add(addBtn);
    addBtn.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        orariBox.add(createOrarioBox());
        showOrariListPanel();
      }
    });
    orariListPanel.add(row);
    refreshScrollPanel();
    addScrollY(50);
  }
  
  private PhTimeBox createOrarioBox() {
    PhTimeBox orarioBox = new PhTimeBox();
    orarioBox.addStyleName("ui-app-timebox");
    return orarioBox;
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  private void refreshScrollPanel() {
    getScrollPanel().refresh();
  }
  
  private void addScrollY(final int deltaY) {
    GwtUtils.deferredExecution(100, new Delegate<Void>() {
      public void execute(Void element) {
        PhonegapUtils.log("scrollPanelImpl.y = " + getScrollPanelImpl().getY());
        int newY = getScrollPanelImpl().getY() - deltaY;
        setTransform(wrapperPanel.getElement().getStyle(), "translate3d(0px, "+ newY +"px, 0px)");
      }
    });
  }
  
  private native void setTransform(Style style, String transform) /*-{
    style['-webkit-transform'] = transform;
  }-*/;

}
