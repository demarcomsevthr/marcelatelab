package it.mate.econyx.client.ui.editors;

import it.mate.econyx.shared.model.Indirizzo;
import it.mate.econyx.shared.model.impl.AbstractIndirizzoTx;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class IndirizzoEditor extends Composite implements Editor<Indirizzo> { 

  public static final int TIPO_CONTROLLI_SPEDIZIONE = 1;
  
  public static final int TIPO_CONTROLLI_FATTURAZIONE = 2;
  
  public interface ViewUiBinder extends UiBinder<Widget, IndirizzoEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  public interface Driver extends SimpleBeanEditorDriver<Indirizzo, IndirizzoEditor> { }
  
  private Driver driver = GWT.create(Driver.class);
  
  @UiField TextBox nome;
  @UiField TextBox cognome;
  @UiField TextBox azienda;
  @UiField TextBox via;
  @UiField TextBox cap;
  @UiField TextBox citta;
  @UiField TextBox telefono;
  @UiField TextBox email;
  
  private int tipoControlli = TIPO_CONTROLLI_SPEDIZIONE;
  
  public IndirizzoEditor() {
    initUI();
    driver.initialize(this);
  }

  public void setTipoControlli(int tipoControlli) {
    this.tipoControlli = tipoControlli;
  }
  
  private void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setModel(Indirizzo indirizzo) {
    driver.edit(indirizzo);
  }
  
  public void setEnabled(boolean enabled) {
    nome.setEnabled(enabled);
    cognome.setEnabled(enabled);
    azienda.setEnabled(enabled);
    via.setEnabled(enabled);
    cap.setEnabled(enabled);
    citta.setEnabled(enabled);
    telefono.setEnabled(enabled);
    email.setEnabled(enabled);
  }
  
  public Indirizzo flushModel() {
    if (tipoControlli == TIPO_CONTROLLI_SPEDIZIONE) {
      if (nome.getText().length() == 0) {
        Window.alert("Inserire il nome");
        return null;
      }
      if (cognome.getText().length() == 0) {
        Window.alert("Inserire il cognome");
        return null;
      }
      if (via.getText().length() == 0) {
        Window.alert("Inserire la via");
        return null;
      }
      if (cap.getText().length() == 0) {
        Window.alert("Inserire il Cap");
        return null;
      }
      if (citta.getText().length() == 0) {
        Window.alert("Inserire la città");
        return null;
      }
      if (telefono.getText().length() == 0) {
        Window.alert("Inserire il telefono");
        return null;
      }
    }
    Indirizzo indirizzo = driver.flush();
    if (indirizzo instanceof AbstractIndirizzoTx) {
      indirizzo = ((AbstractIndirizzoTx)indirizzo).toUpperCase();
    }
    return indirizzo;
  }
  
}
