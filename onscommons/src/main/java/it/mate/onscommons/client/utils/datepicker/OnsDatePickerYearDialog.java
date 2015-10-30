package it.mate.onscommons.client.utils.datepicker;

import it.mate.onscommons.client.ui.OnsDialogCombo;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;


public class OnsDatePickerYearDialog extends OnsDialogCombo {
  
  public OnsDatePickerYearDialog(final int initialYear) {
    super(10, 20, new OnsDialogCombo.ItemDelegate() {
      public Item getItem(int index) {
        int year = initialYear + index;
        PhgUtils.log("Creating item for index " + index + " with value " + year);
        return new OnsDialogCombo.Item(SafeHtmlUtils.fromTrustedString(""+year), ""+year);
      }
    });
    setLazyLoading(true);
  }

  @Override
  public void onItemSelected(Item item) {
    PhgUtils.log("Received item " + item.getValue());
  }

}
