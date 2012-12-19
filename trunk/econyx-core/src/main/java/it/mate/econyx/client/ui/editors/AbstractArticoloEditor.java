package it.mate.econyx.client.ui.editors;

import it.mate.econyx.client.text.CurrencyBox;
import it.mate.econyx.shared.model.Articolo;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;

public abstract class AbstractArticoloEditor <A extends Articolo> extends Composite implements Editor<A> {
  
  public @UiField TextBox codice;
  public @UiField TextBox nome;
  public @UiField CurrencyBox prezzo;
  
  public abstract void setModel(A articolo);
  
  public abstract A flushModel();
  
}
