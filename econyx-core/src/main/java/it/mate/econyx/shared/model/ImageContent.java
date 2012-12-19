package it.mate.econyx.shared.model;

import java.util.List;

public interface ImageContent extends PortalContentFragment {
  
  public enum Type {
    SMALL ("S"), 
    MEDIUM ("M"), 
    LARGE ("L");
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
    public static ImageContent imageOfType (List<? extends ImageContent> images, Type type) {
      for (ImageContent image : images) {
        if (image.getType() == type) {
          return image;
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
  
  public String getContentString();
  
  public void setContentString(String content);

}
