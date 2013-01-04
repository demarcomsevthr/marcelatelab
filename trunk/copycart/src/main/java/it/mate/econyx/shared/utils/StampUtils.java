package it.mate.econyx.shared.utils;

import it.mate.econyx.client.ui.TextControlBar;
import it.mate.econyx.shared.model.OrderItemDetail;
import it.mate.econyx.shared.model.impl.OrderItemStampDetailTx;
import it.mate.econyx.shared.util.FontAlignment;
import it.mate.econyx.shared.util.FontTypes;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import com.google.gwt.user.client.ui.Widget;

public class StampUtils {
  
  public static final String ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT = "T";
  
  public static final String ORDER_ITEM_STAMP_DETAIL_TYPE_LOGO = "L";
  
  public static final String ORDER_ITEM_STAMP_DETAIL_TYPE_BORDER = "B";
  
  public static OrderItemDetail convertSettingsToOrderItemDetail (String text, TextControlBar.Settings settings) {
    OrderItemStampDetailTx detail = new OrderItemStampDetailTx();
    detail.setType(ORDER_ITEM_STAMP_DETAIL_TYPE_TEXT);
    detail.setText(text);
    detail.setFontType(settings.getFontType());
    detail.setFontSize(settings.getFontSize());
    detail.setBold(settings.isBold());
    detail.setItalic(settings.isItalic());
    detail.setUnderline(settings.isUnderline());
    detail.setAlign(settings.isAlignLeft() ? FontAlignment.LEFT : settings.isAlignCenter() ? FontAlignment.CENTER : FontAlignment.RIGHT);
    return detail;
  }

  public static TextControlBar.Settings convertOrderItemDetailToSettings (OrderItemStampDetailTx stampDetail) {
    TextControlBar.Settings settings = new TextControlBar.Settings();
    settings.setFontType(stampDetail.getFontType());
    settings.setFontSize(stampDetail.getFontSize());
    settings.setBold(stampDetail.getBold());
    settings.setItalic(stampDetail.getItalic());
    settings.setUnderline(stampDetail.getUnderline());
    if ("left".equals(stampDetail.getAlign())) {
      settings.setAlignLeft(true);
    } else if ("center".equals(stampDetail.getAlign())) {
      settings.setAlignCenter(true);
    } else {
      settings.setAlignRight(true);
    }
    return settings;
  }
  
  public static void applySettingsOnWidget(Widget widget, TextControlBar.Settings settings) {
    if (settings.getFontFamily() != null)
      GwtUtils.setStyleAttribute(widget, "fontFamily", settings.getFontFamily());
    if (settings.getFontSize() == 0)
      settings.setFontSize(PropertiesHolder.getInt("timbro.defaultFontSize", 10));
    GwtUtils.setStyleAttribute(widget, "fontSize", settings.getFontSize()+"px");
    GwtUtils.setStyleAttribute(widget, "fontWeight", settings.isBold() ? "bold" : "normal");
    GwtUtils.setStyleAttribute(widget, "fontStyle", settings.isItalic() ? "italic" : "normal");
    GwtUtils.setStyleAttribute(widget, "textDecoration", settings.isUnderline() ? "underline" : "none");
    GwtUtils.setStyleAttribute(widget, "textAlign", settings.isAlignLeft() ? "left" : settings.isAlignCenter() ? "center" : "right");
  }
  
}
