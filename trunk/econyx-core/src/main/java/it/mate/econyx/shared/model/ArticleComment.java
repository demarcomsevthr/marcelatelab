package it.mate.econyx.shared.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface ArticleComment extends IsSerializable, Serializable {

  public String getId();

  public void setId(String id);

  public String getName();

  public void setName(String name);
  
  public Integer getOrderNm();
  
  public void setOrderNm(Integer orderNm);

  public String getContent();
  
  public void setContent(String content);
  
  public PortalUser getAuthor();

  public void setAuthor(PortalUser author);

  public Date getPosted();

  public void setPosted(Date posted);
  
}
