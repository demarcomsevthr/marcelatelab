package it.mate.econyx.client.view.custom;

import it.mate.econyx.client.ui.editors.AbstractArticoloEditor;
import it.mate.econyx.client.ui.editors.ArticoloEditor;

public class ProductEditViewCustomizerDefaultImpl implements ProductEditViewCustomizer {

  public AbstractArticoloEditor getArticoloEditor() {
    return new ArticoloEditor();
  }
  
}
