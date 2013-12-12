package it.mate.phgcommons.client.ui;

import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.phgcommons.client.utils.Time;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;

public class TimePickerDialog {
  
  public class Options {
    private String title = "Set a time";
    private int top = -1;
    private int left = -1;
    private int width = 300;
    private int height = 374;
    public Options setTitle(String title) {
      this.title = title;
      return this;
    }
    public Options setTop(int top) {
      this.top = top;
      return this;
    }
    public Options setLeft(int left) {
      this.left = left;
      return this;
    }
    public Options setWidth(int width) {
      this.width = width;
      return this;
    }
    public Options setHeight(int height) {
      this.height = height;
      return this;
    }
  }
  
  private PopupPanel popup;
  
  private Options options;
  
  private Time selectedTime = new Time(0, 0);
  
  private int selectedIndex = 0;
  
  private TouchHTML digits[] = new TouchHTML[4];
  
  public TimePickerDialog() {
    this(null);
  }
  
  public TimePickerDialog(Options options) {
    this.options = options != null ? options : new Options();
    initUI();
  }
  
  private void initUI() {
    
    popup = new PopupPanel();
    popup.addStyleName("phg-TimePickerDialog");
    popup.setWidth(options.width+"px");
    popup.setHeight(options.height+"px");
    
    SimpleContainer container = new SimpleContainer();
    
    HTML header = new HTML(options.title);
    header.addStyleName("phg-TimePickerDialog-Header");
    container.add(header);
    
    HorizontalPanel digitsRow = new HorizontalPanel();
    digitsRow.addStyleName("phg-TimePickerDialog-TimeRow");
    container.add(digitsRow);
    
    digitsRow.add(createDigitHtml(0, getDigit(selectedTime.getHour(), 0)));
    digitsRow.add(createDigitHtml(1, getDigit(selectedTime.getHour(), 1)));
    digitsRow.add(new Label(":"));
    digitsRow.add(createDigitHtml(2, getDigit(selectedTime.getMinute(), 0)));
    digitsRow.add(createDigitHtml(3, getDigit(selectedTime.getMinute(), 1)));
    
    FlexTable keypad = new FlexTable();
    keypad.addStyleName("phg-TimePickerDialog-Keypad");
    container.add(keypad);
    
    keypad.setWidget(0, 0, createKeypadCell("1"));
    keypad.setWidget(0, 1, createKeypadCell("2"));
    keypad.setWidget(0, 2, createKeypadCell("3"));
    keypad.setWidget(0, 3, createKeypadCell("15"));
    keypad.setWidget(1, 0, createKeypadCell("4"));
    keypad.setWidget(1, 1, createKeypadCell("5"));
    keypad.setWidget(1, 2, createKeypadCell("6"));
    keypad.setWidget(1, 3, createKeypadCell("30"));
    keypad.setWidget(2, 0, createKeypadCell("7"));
    keypad.setWidget(2, 1, createKeypadCell("8"));
    keypad.setWidget(2, 2, createKeypadCell("9"));
    keypad.setWidget(2, 3, createKeypadCell("45"));
    keypad.setWidget(3, 0, createKeypadCell("-1h"));
    keypad.setWidget(3, 1, createKeypadCell("0"));
    keypad.setWidget(3, 2, createKeypadCell("+1h"));
    keypad.setWidget(3, 3, createKeypadCell("00"));
    
    
    HorizontalPanel bottom = new HorizontalPanel();
    bottom.addStyleName("phg-TimePickerDialog-Bottom");
    container.add(bottom);
    
    TouchHTML okBtn = new TouchHTML("OK");
    okBtn.addStyleName("phg-TimePickerDialog-BottomButton");
    bottom.add(okBtn);
    
    TouchHTML cancelBtn = new TouchHTML("Cancel");
    cancelBtn.addStyleName("phg-TimePickerDialog-BottomButton");
    bottom.add(cancelBtn);
    
    
    popup.add(container);

    if (options.top == -1 && options.left == -1) {
      popup.center();
    } else {
      popup.setPopupPosition(options.left, options.top);
      popup.show();
    }
    
  }
  
  private TouchHTML createDigitHtml(final int index, int value) {
    TouchHTML digit = new TouchHTML(""+value);
    digit.addStyleName("phg-TimePickerDialog-TimeDigit");
    digit.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        selectedIndex = index;
      }
    });
    digits[index] = digit;
    return digit;
  }
  
  private TouchHTML createKeypadCell(String text) {
    TouchHTML keypadCell = new TouchHTML(text);
    keypadCell.addStyleName("phg-TimePickerDialog-KeypadCell");
    return keypadCell;
  }
  
  private int getDigit(int value, int pos) {
    assert value >= 00 && value < 59;
    String xx = "0" + value;
    if (xx.length() > 2) {
      xx = xx.substring(xx.length() - 2);
    }
    return xx.charAt(pos) - '0';
  }

}
