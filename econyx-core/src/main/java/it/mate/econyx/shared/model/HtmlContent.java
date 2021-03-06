package it.mate.econyx.shared.model;

import it.mate.econyx.shared.model.impl.HtmlContentTx;

import java.util.List;

public interface HtmlContent extends PortalContentFragment {
  
  public static class Factory {
    public static HtmlContent createOnClient(WebContent entity, Type type) {
      return createOnClient(entity, type, "");
    }
    public static HtmlContent createOnClient(WebContent entity, Type type, String content) {
      HtmlContent html = new HtmlContentTx();
      html.setType(type);
      html.setEntity(entity);
      html.setContent(content);
      return html;
    }
  }

  public String getContent();

  public void setContent(String content);
  
  public enum Type {
    SMALL ("S"), 
    MEDIUM ("M"), 
    LONG ("L");
    private String code;
    private Type(String code) {
      this.code = code;
    }
    public static Type valueOfCode(String code) {
      for (Type item : Type.values()) {
        if (item.code.equals(code)) {
          return item;
        }
      }
      throw new IllegalArgumentException(code);
    }
    public static HtmlContent imageOfType (List<? extends HtmlContent> htmls, Type type) {
      for (HtmlContent html : htmls) {
        if (html.getType() == type) {
          return html;
        }
      }
      throw new IllegalArgumentException(type.code);
    }
    public String getCode() {
      return code;
    }
  };
  
  public Type getType();
  
  public void setType(Type type);
  
  
}
