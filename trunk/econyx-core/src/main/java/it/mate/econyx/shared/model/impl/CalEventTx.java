package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.Post;
import it.mate.econyx.shared.model.PostComment;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class CalEventTx implements CalEvent, Post {

  String id;
  
  String code;
  
  Date date;
  
  String name;
  
  Integer orderNm;
  
  String title;
  
  PortalUserTx author;
  
  Date created;
  
  String content;
  
  public CalEventTx() {

  }

  /**
   * serve solo per debug
   */
  public CalEventTx(Date date, String title) {
    super();
    this.date = date;
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getOrderNm() {
    return orderNm;
  }

  public void setOrderNm(Integer orderNm) {
    this.orderNm = orderNm;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PortalUser getAuthor() {
    return author;
  }

  @CloneableProperty (targetClass=PortalUserTx.class)
  public void setAuthor(PortalUser author) {
    if (author == null) {
      this.author = null;
    } else if (author instanceof PortalUserTx) {
      this.author = (PortalUserTx)author;
    } else {
      throw new CloneablePropertyMissingException(author);
    }
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }
  
  @Override
  public HtmlContent getHtml() {
    HtmlContentTx html = new HtmlContentTx();
    html.setType(HtmlContent.Type.MEDIUM);
    html.setContent(content);
    return html;
  }

  @CloneableProperty (targetClass=HtmlContentTx.class)
  public void setHtml(HtmlContent html) {
    if (html == null) {
      this.content = null;
    } else if (html instanceof HtmlContentTx) {
      this.content = html.getContent();
    } else {
      throw new CloneablePropertyMissingException(html);
    }
  }

  @Override
  public Date getPostDate() {
    return getDate();
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getBody() {
    HtmlContent html = getHtml();
    return html != null ? html.getContent() : "";
  }

  public List<PostComment> getPostComments() {
    return null;
  }

  public Integer getCommentsCount() {
    return 0;
  }

  public String getTags() {
    return "";
  }

}
