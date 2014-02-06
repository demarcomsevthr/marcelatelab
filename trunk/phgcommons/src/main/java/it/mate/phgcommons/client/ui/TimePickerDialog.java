package it.mate.phgcommons.client.ui;

import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.Time;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;

public class TimePickerDialog {
  
  public static class Options {
    private String title = "Set a time";
    private int top = -1;
    private int left = -1;
    private int width = 300;
    private int height = 374;
    private Delegate<Time> onCloseDelegate;
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
    public Options setOnClose(Delegate<Time> onCloseDelegate) {
      this.onCloseDelegate = onCloseDelegate;
      return this;
    }
  }
  
  private PopupPanel popup;
  
  private Options options;
  
  private Time currentTime = new Time(0, 0);
  
  private int currentDigit = 0;
  
  private TouchHTML digits[] = new TouchHTML[4];
  
  public TimePickerDialog() {
    this(null);
  }
  
  public TimePickerDialog(Options options) {
    this.options = options != null ? options : new Options();
    initUI();
  }
  
  public void setTime(Time time) {
    this.currentTime = time;
    renderCurrentTime();
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
    
    digitsRow.add(createDigitHtml(0, getPositionalDigit(currentTime.getHour(), 0)));
    digitsRow.add(createDigitHtml(1, getPositionalDigit(currentTime.getHour(), 1)));
    digitsRow.add(new Label(":"));
    digitsRow.add(createDigitHtml(2, getPositionalDigit(currentTime.getMinute(), 0)));
    digitsRow.add(createDigitHtml(3, getPositionalDigit(currentTime.getMinute(), 1)));
    
    FlexTable keypad = new FlexTable();
    keypad.addStyleName("phg-TimePickerDialog-Keypad");
    container.add(keypad);
    
    TouchEndHandler digitHandler = new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        Widget source = (Widget)event.getSource();
        int newDigit = getWidgetValue(source);
        if (currentDigit == 0) {
          int d1 = getWidgetValue(digits[1]);
          if (d1 <= 3) {
            if (newDigit <= 2) {
              int hours = currentTime.getHour();
              currentTime.setHour(setPositionalDigit(hours, 0, newDigit));
              renderCurrentTime();
            }
          } else {
            if (newDigit <= 1) {
              int hours = currentTime.getHour();
              currentTime.setHour(setPositionalDigit(hours, 0, newDigit));
              renderCurrentTime();
            }
          }
          currentDigit ++;
        } else if (currentDigit == 1) {
          int d0 = getWidgetValue(digits[0]);
          if (d0 == 0 || d0 == 1) {
            int hours = currentTime.getHour();
            currentTime.setHour(setPositionalDigit(hours, 1, newDigit));
            renderCurrentTime();
          } else if (d0 == 2) {
            if (newDigit <= 4) {
              int hours = currentTime.getHour();
              currentTime.setHour(setPositionalDigit(hours, 1, newDigit));
              renderCurrentTime();
            }
          }
          currentDigit ++;
        } else if (currentDigit == 2) {
          if (newDigit <= 5) {
            int minutes = currentTime.getMinute();
            currentTime.setMinute(setPositionalDigit(minutes, 0, newDigit));
            renderCurrentTime();
          }
          currentDigit ++;
        } else if (currentDigit == 3) {
          int minutes = currentTime.getMinute();
          currentTime.setMinute(setPositionalDigit(minutes, 1, newDigit));
          renderCurrentTime();
          currentDigit = 0;
        }
      }
    };
    
    TouchEndHandler minutesHandler = new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        Widget source = (Widget)event.getSource();
        int minutes = getWidgetValue(source);
        currentTime.setMinute(minutes);
        renderCurrentTime();
      }
    };
    
    TouchEndHandler hoursHandler = new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        Widget source = (Widget)event.getSource();
        int incr = getWidgetValue(source);
        int hours = currentTime.getHour();
        if (incr == +1) {
          if (hours == 23) {
            hours = 00;
          } else {
            hours ++;
          }
        } else if (incr == -1) {
          if (hours == 00) {
            hours = 23;
          } else {
            hours --;
          }
        }
        currentTime.setHour(hours);
        renderCurrentTime();
      }
    };
    
    keypad.setWidget(0, 0, createKeypadCell("1", 1, digitHandler));
    keypad.setWidget(0, 1, createKeypadCell("2", 2, digitHandler));
    keypad.setWidget(0, 2, createKeypadCell("3", 3, digitHandler));
    keypad.setWidget(0, 3, createKeypadCell("15", 15, minutesHandler));
    keypad.setWidget(1, 0, createKeypadCell("4", 4, digitHandler));
    keypad.setWidget(1, 1, createKeypadCell("5", 5, digitHandler));
    keypad.setWidget(1, 2, createKeypadCell("6", 6, digitHandler));
    keypad.setWidget(1, 3, createKeypadCell("30", 30, minutesHandler));
    keypad.setWidget(2, 0, createKeypadCell("7", 7, digitHandler));
    keypad.setWidget(2, 1, createKeypadCell("8", 8, digitHandler));
    keypad.setWidget(2, 2, createKeypadCell("9", 9, digitHandler));
    keypad.setWidget(2, 3, createKeypadCell("45", 45, minutesHandler));
    keypad.setWidget(3, 0, createKeypadCell("-1h", -1, hoursHandler));
    keypad.setWidget(3, 1, createKeypadCell("0", 0, digitHandler));
    keypad.setWidget(3, 2, createKeypadCell("+1h", +1, hoursHandler));
    keypad.setWidget(3, 3, createKeypadCell("00", 00, minutesHandler));
    
    
    HorizontalPanel bottom = new HorizontalPanel();
    bottom.addStyleName("phg-TimePickerDialog-Bottom");
    container.add(bottom);
    
    TouchHTML okBtn = new TouchHTML("OK");
    okBtn.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        popup.hide();
        if (options.onCloseDelegate != null) {
          options.onCloseDelegate.execute(currentTime);
        }
      }
    });
    okBtn.addStyleName("phg-TimePickerDialog-BottomButton");
    bottom.add(okBtn);
    
    TouchHTML cancelBtn = new TouchHTML("Cancel");
    cancelBtn.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        popup.hide();
      }
    });
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
  
  private void renderCurrentTime() {
    int hours = currentTime.getHour();
    int d0 = hours / 10;
    int d1 = hours % 10;
    int minutes = currentTime.getMinute();
    int d2 = minutes / 10;
    int d3 = minutes % 10;
    setDigitHtmlValue(digits[0], d0);
    setDigitHtmlValue(digits[1], d1);
    setDigitHtmlValue(digits[2], d2);
    setDigitHtmlValue(digits[3], d3);
  }
  
  private void setDigitHtmlValue(TouchHTML digit, int d) {
    digit.setHtml(SafeHtmlUtils.fromTrustedString(""+d));
    setWidgetValue(digit, d);
  }
  
  private TouchHTML createDigitHtml(final int index, int value) {
    TouchHTML digit = new TouchHTML(""+value);
    digit.addStyleName("phg-TimePickerDialog-TimeDigit");
    digit.addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        currentDigit = index;
      }
    });
    setWidgetValue(digit, value);
    digits[index] = digit;
    return digit;
  }
  
  private TouchHTML createKeypadCell(String text, int value, TouchEndHandler handler) {
    TouchHTML keypadCell = new TouchHTML(text);
    keypadCell.addStyleName("phg-TimePickerDialog-KeypadCell");
    keypadCell.addTouchEndHandler(handler);
    setWidgetValue(keypadCell, value);
    return keypadCell;
  }
  
  private int getWidgetValue(Widget widget) {
    return widget.getElement().getPropertyInt("model");
  }
  
  private void setWidgetValue(Widget widget, int value) {
    widget.getElement().setPropertyInt("model", value);
  }
  
  private static int getPositionalDigit(int value, int pos) {
    assert value >= 00 && value <= 59;
    assert pos == 0 || pos == 1;
    if (pos == 0)
      return value / 10;
    if (pos == 1)
      return value % 10;
    return -1;
  }
  
  private static int setPositionalDigit(int value, int pos, int digit) {
    assert value >= 00 && value <= 59;
    assert pos == 0 || pos == 1;
    if (pos == 0) {
      value = (digit * 10) + (value % 10);
    } else if (pos == 1) {
      value = (value / 10) * 10 + (digit);
    }
    return value;
  }
  
}