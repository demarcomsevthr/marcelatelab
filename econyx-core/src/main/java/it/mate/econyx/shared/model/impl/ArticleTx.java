package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleComment;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.gwtcommons.client.utils.CollectionUtilsClient;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ArticleTx implements Article {
  
  String id;
  
  String code;
  
  String name;
  
  Integer orderNm;
  
  HtmlContentTx html;
  
  List<ArticleCommentTx> comments;
  
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
      throw new IllegalArgumentException("cannot add item of type " + html.getClass() + ", forget CloneableProperty annotation");
    }
  }
  
  public List<ArticleComment> getComments() {
    return CollectionUtilsClient.wrapListOfInterfaces(comments, ArticleCommentTx.class);
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
          throw new IllegalArgumentException("cannot add item of type " + comment.getClass() + ", must use CloneableProperty annotation");
        }
      }
    } else {
      this.comments = null;
    }
  }
  
}
