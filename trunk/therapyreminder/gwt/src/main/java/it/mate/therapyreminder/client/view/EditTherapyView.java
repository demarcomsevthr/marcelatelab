package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
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
  @UiField TouchCombo tipoOrariCombo;
  @UiField Panel orariRegolariPanel;
  @UiField Panel orariFissiPanel;
  @UiField ComplexPanel orariListPanel;
  
  List<PhTimeBox> orariBox = new ArrayList<PhTimeBox>();
  
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
    
    tipoTerapiaCombo.addItem("1", "Orale", false);
    tipoTerapiaCombo.addItem("2", "Intramuscolare", false);
    tipoTerapiaCombo.addItem("3", "Endovenosa", false);
    tipoTerapiaCombo.addItem("4", "Infusionale", false);
    tipoTerapiaCombo.addItem("5", "Insulinica", false);
    tipoTerapiaCombo.addItem("6", "Antibiotica", false);
    tipoTerapiaCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        PhonegapUtils.log("selected value is " + event.getValue());
      }
    });
    
    tipoRicorrenzaCombo.addItem("1", "Tutti i giorni", false);
    tipoRicorrenzaCombo.addItem("2", "Giornaliera", false);
    tipoRicorrenzaCombo.addItem("3", "Settimanale", false);
    tipoRicorrenzaCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        if ("1".equals(event.getValue())) {
          ricorrenzaGiornalieraPanel.setVisible(false);
          ricorrenzaSettimanalePanel.setVisible(false);
        } else if ("2".equals(event.getValue())) {
          ricorrenzaGiornalieraPanel.setVisible(true);
          ricorrenzaSettimanalePanel.setVisible(false);
        } else if ("3".equals(event.getValue())) {
          ricorrenzaGiornalieraPanel.setVisible(false);
          ricorrenzaSettimanalePanel.setVisible(true);
        }
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
      }
    });
    
    
  }
  
  private void showOrariListPanel() {
    if (orariBox.size() == 0) {
      orariBox.add(createOrarioBox());
    }
    orariListPanel.clear();
    for (final PhTimeBox orarioBox : orariBox) {
      HorizontalPanel row = new HorizontalPanel();
      row.add(orarioBox);
      TouchHTML removeBtn = new TouchHTML("[--]");
      row.add(removeBtn);
      removeBtn.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          orariBox.remove(orarioBox);
          showOrariListPanel();
        }
      });
      orariListPanel.add(row);
    }
    HorizontalPanel row = new HorizontalPanel();
    row.add(new Spacer("3em"));
    TouchHTML addBtn = new TouchHTML("[++]");
    row.add(addBtn);
    addBtn.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        orariBox.add(createOrarioBox());
        showOrariListPanel();
      }
    });
    orariListPanel.add(row);
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
  
}
