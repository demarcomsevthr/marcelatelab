package it.mate.econyx.client.ui;

import it.mate.econyx.shared.util.FontTypes;
import it.mate.gwtcommons.client.ui.ContainerPanel;
import it.mate.gwtcommons.client.ui.ListPanel;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class TextControlBar extends ContainerPanel {
  
  public static class FontType {
    private String description;
    private String family;
    private int key;
    public FontType(int key, String description, String family) {
      super();
      this.description = description;
      this.family = family;
      this.key = key;
    }
    public String getDescription() {
      return description;
    }
    public void setDescription(String description) {
      this.description = description;
    }
    public String getFamily() {
      return family;
    }
    public void setFamily(String family) {
      this.family = family;
    }
    public int getKey() {
      return key;
    }
    public void setKey(int key) {
      this.key = key;
    }
  }
  
  private static List<FontType> fontTypes = new ArrayList<TextControlBar.FontType>(); 
  
  static {
    fontTypes.add(new FontType(FontTypes.TYPE_ARIAL, "Sans Serif", "arial,helvetica,sans-serif"));
    fontTypes.add(new FontType(FontTypes.TYPE_TIMES_NEW_ROMAN, "Serif", "times new roman,serif"));
    fontTypes.add(new FontType(FontTypes.TYPE_ARIAL_BLACK, "Arial Largo", "arial black,sans-serif"));
    fontTypes.add(new FontType(FontTypes.TYPE_ARIAL_NARROW, "Arial Stretto", "arial narrow,sans-serif"));
    fontTypes.add(new FontType(FontTypes.TYPE_COMIC_SANS_MS, "Comic Sans MS", "comic sans ms,sans-serif"));
    fontTypes.add(new FontType(FontTypes.TYPE_COURIER_NEW, "Courier New", "courier new,monospace"));
    fontTypes.add(new FontType(FontTypes.TYPE_GARAMOND, "Garamond", "garamond,serif"));
    fontTypes.add(new FontType(FontTypes.TYPE_GEORGIA, "Georgia", "georgia,serif"));
    fontTypes.add(new FontType(FontTypes.TYPE_TAHOMA, "Tahoma", "tahoma,sans-serif"));
    fontTypes.add(new FontType(FontTypes.TYPE_TREBUCHET, "Trebuchet", "trebuchet ms,sans-serif"));
    fontTypes.add(new FontType(FontTypes.TYPE_VERDANA, "Verdana", "verdana,sans-serif"));
  }
  
  public TextControlBar() {
    super();
    initAll();
  }
  
  public TextControlBar(Settings settings) {
    super();
    this.settings = settings;
    initAll();
  }
  
  private void initAll() {
    addStyleName("textControlBar");
    initUI();
  }
  
  public void setSettingsChangeDelegate(Delegate<Settings> settingsChangeDelegate) {
    this.settingsChangeDelegate = settingsChangeDelegate;
    settingsChangeDelegate.execute(settings);
  }
  
  public Settings getSettings() {
    return settings;
  }
  
  private Settings settings = new Settings();
  
  private Delegate<Settings> settingsChangeDelegate;
  
  private Panel alignLeftControl;
  private Panel alignCenterControl;
  private Panel alignRightControl;
  
  private void onSettingsChange(Settings settings) {
    GwtUtils.log(getClass(), "onSettingsChange", "settings = " + settings);
    checkTextControlState(alignLeftControl, settings.alignLeft);
    checkTextControlState(alignCenterControl, settings.alignCenter);
    checkTextControlState(alignRightControl, settings.alignRight);
    if (settingsChangeDelegate != null) {
      settingsChangeDelegate.execute(settings);
    }
  }

  private void initUI() {
    createTextControlBoolean("textControlBold", settings.bold);
    createTextControlBoolean("textControlItalic", settings.italic);
    createTextControlBoolean("textControlUnderline", settings.underline);
    createTextControlCombo("textControlFontType", settings.fontTypeSelected, new ClickHandler() {
      public void onClick(final ClickEvent event) {
        if (event.getSource() instanceof Panel) {
          final Panel textControlPanel = (Panel)event.getSource();
          final PopupPanel popup = new FontTypesPopupPanel(textControlPanel) {
            public void hide() {
              super.hide();
              checkTextControlState(textControlPanel, settings.fontTypeSelected);
            };
          };
          popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            public void setPosition(int offsetWidth, int offsetHeight) {
              int left = textControlPanel.getAbsoluteLeft();
              int top = textControlPanel.getAbsoluteTop() + textControlPanel.getOffsetHeight();
              popup.setPopupPosition(left, top);
            }
          });
        }
      }
    });
    createTextControlCombo("textControlFontSize", settings.fontSizeSelected, new ClickHandler() {
      public void onClick(final ClickEvent event) {
        if (event.getSource() instanceof Panel) {
          final Panel textControlPanel = (Panel)event.getSource();
          final PopupPanel popup = new FontSizesPopupPanel(textControlPanel) {
            public void hide() {
              super.hide();
              checkTextControlState(textControlPanel, settings.fontSizeSelected);
            };
          };
          popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            public void setPosition(int offsetWidth, int offsetHeight) {
              int left = textControlPanel.getAbsoluteLeft();
              int top = textControlPanel.getAbsoluteTop() + textControlPanel.getOffsetHeight();
              popup.setPopupPosition(left, top);
            }
          });
        }
      }
    });
    alignLeftControl = createTextControlBoolean("textControlAlignLeft", settings.alignLeft);
    alignCenterControl = createTextControlBoolean("textControlAlignCenter", settings.alignCenter);
    alignRightControl = createTextControlBoolean("textControlAlignRight", settings.alignRight);
    
  }
  
  private Panel createTextControlBoolean(String stylename, final BooleanWrapper state) {
    final SimplePanel textControlPanel = new SimplePanel();
    textControlPanel.addStyleName("textControlSimple");
    SimplePanel subpanel;
    
    subpanel = new SimplePanel();
    subpanel.addStyleName("textControlPng");
    subpanel.addStyleName(stylename);
    textControlPanel.add(subpanel);
    
    textControlPanel.addDomHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        state.setValue(!state.getValue());
        onSettingsChange(settings);
        checkTextControlState(textControlPanel, state);
      }
    }, ClickEvent.getType());
    
    add(textControlPanel, "textControlContainer");
    checkTextControlState(textControlPanel, state);
    return textControlPanel;
  }
  
  private void createTextControlCombo(String stylename, final BooleanWrapper state, final ClickHandler handler) {
    final Panel textControlPanel = new ListPanel();
    textControlPanel.addStyleName("textControlCombo");
    SimplePanel subpanel;
    
    subpanel = new SimplePanel();
    subpanel.addStyleName("textControlPng");
    subpanel.addStyleName(stylename);
    textControlPanel.add(subpanel);
    
    subpanel = new SimplePanel();
    subpanel.addStyleName("textControlArrowDown");
    textControlPanel.add(subpanel);
    
    textControlPanel.addDomHandler(handler, ClickEvent.getType());
    add(textControlPanel, "textControlContainer");
    checkTextControlState(textControlPanel, state);
  }

  private void checkTextControlState (Panel textControlPanel, BooleanWrapper state) {
    if (state.getValue()) {
      textControlPanel.removeStyleName("textControlNotSelected");
      textControlPanel.addStyleName("textControlSelected");
    } else {
      textControlPanel.removeStyleName("textControlSelected");
      textControlPanel.addStyleName("textControlNotSelected");
    }
  }
  
  private class FontTypesPopupPanel extends PopupPanel {
    protected Panel textControlPanel;
    private ListPanel fontTypesPanel = new ListPanel();
    public FontTypesPopupPanel(Panel textControlPanel) {
      super(true);
      this.textControlPanel = textControlPanel;
      setStyleName("font-PopupPanel");
      fontTypesPanel.addStyleName("textControlFontTypesPanel");
      for (FontType fontType : fontTypes) {
        createFontTypePanel(this, fontTypesPanel, fontType.getDescription(), fontType.getFamily(), fontType.getKey());
      }
      setWidget(fontTypesPanel);
    }
    @Override
    public void setPopupPositionAndShow(PositionCallback callback) {
      super.setPopupPositionAndShow(callback);
      GwtUtils.setStyleAttribute(fontTypesPanel.getParent().getParent(), "border", "none");
    }
  }

  private void createFontTypePanel (final PopupPanel popup, ListPanel fontTypesPanel, String text, final String fontFamily, final int fontType) {
    SimplePanel innerPanel = new SimplePanel();
    innerPanel.setStyleName("textControlFontTypeInnerPanel");
    if (settings.getFontType() == fontType) {
      innerPanel.addStyleName("textControlFontTypeSelected");
    }
    Label fontLabel = new Label(text);
    fontLabel.addStyleName("textControlFontTypeFontLabel");
    GwtUtils.setStyleAttribute(fontLabel, "fontFamily", fontFamily);
    innerPanel.add(fontLabel);
    innerPanel.addDomHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        settings.setFontType(fontType);
        onSettingsChange(settings);
        popup.hide();
      }
    }, ClickEvent.getType());
    fontTypesPanel.add(innerPanel);
  }
  
  private class FontSizesPopupPanel extends PopupPanel {
    protected Panel textControlPanel;
    private ListPanel fontSizesPanel = new ListPanel();
    public FontSizesPopupPanel(Panel textControlPanel) {
      super(true);
      this.textControlPanel = textControlPanel;
      setStyleName("font-PopupPanel");
      fontSizesPanel.addStyleName("textControlFontSizesPanel");
      createFontSizePanel(this, fontSizesPanel, 8);
      createFontSizePanel(this, fontSizesPanel, 9);
      createFontSizePanel(this, fontSizesPanel, 10);
      createFontSizePanel(this, fontSizesPanel, 11);
      createFontSizePanel(this, fontSizesPanel, 12);
      setWidget(fontSizesPanel);
    }
    @Override
    public void setPopupPositionAndShow(PositionCallback callback) {
      super.setPopupPositionAndShow(callback);
      GwtUtils.setStyleAttribute(fontSizesPanel.getParent().getParent(), "border", "none");
    }
  }

  private void createFontSizePanel (final PopupPanel popup, ListPanel fontSizesPanel, final int fontSize) {
    SimplePanel innerPanel = new SimplePanel();
    innerPanel.setStyleName("textControlFontTypeInnerPanel");
    if (settings.fontSize == fontSize) {
      innerPanel.addStyleName("textControlFontTypeSelected");
    }
    Label fontLabel = new Label(""+fontSize);
    fontLabel.addStyleName("textControlFontTypeFontLabel");
    GwtUtils.setStyleAttribute(fontLabel, "fontSize", (fontSize+2)+"px");
    innerPanel.add(fontLabel);
    innerPanel.addDomHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        settings.fontSize = fontSize;
        onSettingsChange(settings);
        popup.hide();
      }
    }, ClickEvent.getType());
    fontSizesPanel.add(innerPanel);
  }
  
  private static class BooleanWrapper {
    boolean value = false;
    public BooleanWrapper() {
    }
    public BooleanWrapper(boolean value) {
      this.value = value;
    }
    public void setValue(boolean value) {
      this.value = value;
    }
    public boolean getValue() {
      return value;
    }
    @Override
    public String toString() {
      return "" + value + "";
    }
  }
  
  public static class Settings {
    private FontType fontType = fontTypes.get(0);
    private int fontSize;
    private BooleanWrapper bold = new BooleanWrapper();
    private BooleanWrapper italic = new BooleanWrapper();
    private BooleanWrapper underline = new BooleanWrapper();
    private BooleanWrapper alignLeft = new BooleanWrapper(true) {
      public void setValue(boolean value) {
        super.setValue(value);
        if (value) {
          alignCenter.value = false;
          alignRight.value = false;
        }
      };
    };
    private BooleanWrapper alignCenter = new BooleanWrapper() {
      public void setValue(boolean value) {
        super.setValue(value);
        if (value) {
          alignLeft.value = false;
          alignRight.value = false;
        }
      };
    };
    private BooleanWrapper alignRight = new BooleanWrapper() {
      public void setValue(boolean value) {
        super.setValue(value);
        if (value) {
          alignCenter.value = false;
          alignLeft.value = false;
        }
      };
    };
    private BooleanWrapper fontTypeSelected = new BooleanWrapper() {
      public boolean getValue() {
        return fontType != null;
      };
    };
    private BooleanWrapper fontSizeSelected = new BooleanWrapper() {
      public boolean getValue() {
        return fontSize != 0;
      };
    };
    
    public Settings() {
      super();
    }
    public Settings(boolean alignCenter) {
      this();
      this.alignCenter.setValue(alignCenter);
    }
    
    public int getFontType() {
      return fontType != null ? fontType.getKey() : 0;
    }
    public int getFontSize() {
      return fontSize;
    }
    public boolean isBold() {
      return bold.getValue();
    }
    public boolean isItalic() {
      return italic.getValue();
    }
    public boolean isUnderline() {
      return underline.getValue();
    }
    public boolean isAlignLeft() {
      return alignLeft.getValue();
    }
    public boolean isAlignCenter() {
      return alignCenter.getValue();
    }
    public boolean isAlignRight() {
      return alignRight.getValue();
    }
    public String getFontFamily() {
      return fontType != null ? fontType.getFamily() : null;
    }
    
    public void setFontType(Integer key) {
      for (FontType fontType : fontTypes) {
        if (fontType.getKey() == key) {
          this.fontType = fontType;
        }
      }
    }
    
    public void setFontSize(Integer fontSize) {
      this.fontSize = fontSize;
    }
    
    public void setBold(Boolean value) {
      this.bold.setValue(value);
    }
    
    public void setItalic(Boolean value) {
      this.italic.setValue(value);
    }
    
    public void setUnderline(Boolean value) {
      this.underline.setValue(value);
    }
    
    public void setAlignLeft(Boolean value) {
      this.alignLeft.setValue(value);
    }
    
    public void setAlignCenter(Boolean value) {
      this.alignCenter.setValue(value);
    }
    
    public void setAlignRight(Boolean value) {
      this.alignRight.setValue(value);
    }
    
    @Override
    public String toString() {
      return "Settings [fontType=" + getFontType() + ", fontSize=" + fontSize + ", bold=" + bold + ", italic=" + italic + ", underline="
          + underline + ", alignLeft=" + alignLeft + ", alignCenter=" + alignCenter + ", alignRight=" + alignRight + ", fontFamily="
          + getFontFamily() + "]";
    }
  }
  
}
