package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleComment;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.utils.ListPropertyWrapper;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ArticleTx implements Article {
  
  String id;
  
  String code;
  
  String name;
  
  Integer orderNm;
  
  HtmlContentTx html;
  
  String title;
  
  PortalUserTx author;
  
  Date created;
  
  List<ArticleCommentTx> comments = new ArrayList<ArticleCommentTx>();
  
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

  @Override
  public HtmlContent getHtml() {
    return html;
  }

  @CloneableProperty (targetClass=HtmlContentTx.class)
  public void setHtml(HtmlContent html) {
    if (html instanceof HtmlContentTx) {
      this.html = (HtmlContentTx)html;
    } else {
      throw new CloneablePropertyMissingException(html);
    }
  }

  public List<ArticleComment> getComments() {
//  return (List<ArticleComment>)CollectionUtilsClient.wrapConcreteList(ArticleComment.class, comments, ArticleCommentTx.class);
    return new ListPropertyWrapper<ArticleComment, ArticleCommentTx>(comments, ArticleCommentTx.class);
  }

  /*
  public static void main(String[] args) {
    ArticleTx article = new ArticleTx();
    article.setComments(new ArrayList<ArticleComment>());
    article.getComments().add(new ArticleCommentTx());
    article.getComments().get(0).setName("commento 1");
    article.getComments().add(new ArticleCommentTx());
    article.getComments().get(1).setName("commento 2");
    article.getComments().add(new ArticleCommentTx());
    article.getComments().get(2).setName("commento 3");
    article.getComments().add(new ArticleCommentTx());
    article.getComments().get(3).setName("commento 4");
    List<ArticleComment> comments = article.getComments();
    for (ArticleComment comment : comments) {
      System.out.println(comment.getName());
    }
  }
  */

  /*
  public List<ArticleComment> getComments() {
    if (comments == null)
      return null;
    return new AbstractList<ArticleComment>() {
      public int size() {
        return comments.size();
      }
      public ArticleComment get(int index) {
        return comments.get(index);
      }
      public ArticleComment set(int index, ArticleComment element) {
        if (element instanceof ArticleCommentTx) {
          return comments.set(index, (ArticleCommentTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean add(ArticleComment element) {
        if (element instanceof ArticleCommentTx) {
          return comments.add((ArticleCommentTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean remove(Object o) {
        return comments.remove(o);
      }
      public ArticleComment remove(int index) {
        return comments.remove(index);
      }
    };
  }
  */

  @CloneableProperty (targetClass=ArticleCommentTx.class)
  public void setComments(List<ArticleComment> comments) {
    if (comments != null) {
      this.comments = new ArrayList<ArticleCommentTx>();
      for (ArticleComment comment : comments) {
        if (comment instanceof ArticleCommentTx) {
          this.comments.add((ArticleCommentTx)comment);
        } else {
          throw new CloneablePropertyMissingException(comment);
        }
      }
    } else {
      this.comments = null;
    }
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
    if (author instanceof PortalUserTx) {
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
  
}
