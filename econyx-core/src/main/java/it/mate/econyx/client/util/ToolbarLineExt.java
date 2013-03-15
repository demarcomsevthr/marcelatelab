package it.mate.econyx.client.util;

import it.mate.econyx.client.util.CKConfigExt.LINE_TYPE;
import it.mate.econyx.client.util.CKConfigExt.TOOLBAR_OPTIONS;

import java.util.ArrayList;
import java.util.Collection;

import com.axeiya.gwtckeditor.client.ToolbarLine;
import com.google.gwt.core.client.JavaScriptObject;

public class ToolbarLineExt extends ToolbarLine {
  
  private ArrayList<TOOLBAR_OPTIONS> blocks;
  private LINE_TYPE type = LINE_TYPE.NORMAL;
  
  public ToolbarLineExt(){
    blocks = new ArrayList<TOOLBAR_OPTIONS>();
  }
  
  public ToolbarLineExt(LINE_TYPE t){
    this();
    type = t;
  }
  
  public void add(TOOLBAR_OPTIONS t){
    blocks.add(t);
  }

  public void addAllExt(Collection<TOOLBAR_OPTIONS> options){
    blocks.addAll(options);
  }
  
  public void addAllExt(TOOLBAR_OPTIONS[] options){
    for(int i=0;i<options.length;i++){
      blocks.add(options[i]);
    }
  }
  
  public void addBlockSeparator(){
    blocks.add(TOOLBAR_OPTIONS._);
  }
  
  public Object getRepresentation(){
    if(type == LINE_TYPE.SEPARATOR){
      return getSeparator();
    }else{
      JavaScriptObject array = JavaScriptObject.createArray();
      for(TOOLBAR_OPTIONS opt:blocks){
        if(opt == TOOLBAR_OPTIONS._)
          array = addToArray(array,"-");
        else
          array = addToArray(array,opt.toString());
      }
      return array;
    }
  }
  
  private static native String getSeparator() /*-{
    var temp = new String("/");
    return temp;
  }-*/;
  
  private static native JavaScriptObject addToArray(JavaScriptObject base, String option) /*-{
    base[base.length] = option;
    return base;
  }-*/;
  
}
