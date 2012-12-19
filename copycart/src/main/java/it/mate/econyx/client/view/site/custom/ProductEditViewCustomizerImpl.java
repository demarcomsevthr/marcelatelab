package it.mate.econyx.client.view.site.custom;

import it.mate.econyx.client.ui.TimbroEditor;
import it.mate.econyx.client.ui.editors.AbstractArticoloEditor;
import it.mate.econyx.client.view.custom.ProductEditViewCustomizer;

public class ProductEditViewCustomizerImpl implements ProductEditViewCustomizer {

  public AbstractArticoloEditor getArticoloEditor() {
    return new TimbroEditor();
  }
  
}
