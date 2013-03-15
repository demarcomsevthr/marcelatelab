package it.mate.econyx.client.util;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.google.gwt.core.client.JavaScriptObject;

public class CKConfigExt extends CKConfig {

  public enum TOOLBAR_OPTIONS {
    Source, Save, NewPage, Preview, Templates, Cut, Copy, Paste, PasteText, PasteFromWord, Print, SpellChecker, Scayt, Undo, Redo, 
    Find, Replace, SelectAll, RemoveFormat, Form, Checkbox, Radio, TextField, Textarea, Select, Button, ImageButton, HiddenField, 
    Bold, Italic, Underline, Strike, Subscript, Superscript, NumberedList, BulletedList, Outdent, Indent, Blockquote, 
    JustifyLeft, JustifyCenter, JustifyRight, JustifyBlock, Link, Unlink, Anchor, Image, Flash, Table, HorizontalRule, 
    Smiley, SpecialChar, PageBreak, Styles, Format, Font, FontSize, TextColor, BGColor, Maximize, ShowBlocks, About, _ ,
    Readmore
  }
  
  enum LINE_TYPE {
    NORMAL, SEPARATOR
  }

  public CKConfigExt() {
    super();
  }

  public CKConfigExt(PRESET_TOOLBAR toolbar) {
    super(toolbar);
  }
  
  
  @Override
  public JavaScriptObject getConfigObject() {
    JavaScriptObject config = super.getConfigObject();
    setNativeExtraPlugins(config, "readmore");
    return config;
  }
  
  private native void setNativeExtraPlugins(JavaScriptObject config, String extraPlugins)/*-{
    config.extraPlugins = extraPlugins;
  }-*/;
  
}
