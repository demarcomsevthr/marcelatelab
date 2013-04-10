package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.BlogCommentDs;
import it.mate.econyx.server.model.impl.BlogDiscussionDs;
import it.mate.econyx.server.model.impl.BlogDs;
import it.mate.econyx.server.services.BlogAdapter;
import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.impl.BlogDiscussionTx;
import it.mate.econyx.shared.model.impl.BlogTx;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.dao.FindContext;
import it.mate.gwtcommons.server.utils.CacheUtils;
import it.mate.gwtcommons.server.utils.CloneUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogAdapterImpl implements BlogAdapter {

  private static Logger logger = Logger.getLogger(BlogAdapterImpl.class);

  @Autowired private GeneralAdapter generalAdapter;
  
  @Autowired private Dao dao;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
  }

  @Override
  public List<Blog> findAllBlogs() {
    List<BlogDs> blogs = dao.findAll(BlogDs.class);
    if (blogs != null) {
      Collections.sort(blogs, new Comparator<Blog>() {
        public int compare(Blog f1, Blog f2) {
          return (f1.getOrderNm() != null && f2.getOrderNm() != null) ? f1.getOrderNm().compareTo(f2.getOrderNm()) : 0;
        }
      });
    }
    return CloneUtils.clone(blogs, BlogTx.class, Blog.class);
  }

  @Override
  public Blog updateBlog(Blog entity) {
    BlogDs blogDs = CloneUtils.clone(entity, BlogDs.class);
    blogDs = createOrUpdateBlogDs(blogDs);
    return CloneUtils.clone (blogDs, BlogTx.class);
  }

  @Override
  public void deleteBlog(Blog entity) {
    BlogDs blogDs = dao.findById(BlogDs.class, entity.getId());
    deleteDiscussions(blogDs.getDiscussions());
    dao.delete(blogDs);
  }

  @Override
  public Blog createBlog(Blog entity) {
    BlogDs blogDs = CloneUtils.clone(entity, BlogDs.class);
    blogDs = createOrUpdateBlogDs(blogDs);
    return CloneUtils.clone (blogDs, BlogTx.class);
  }

  @Override
  public Blog findBlogById(String id) {
    Blog ds = dao.findById(BlogDs.class, id);
    return CloneUtils.clone(ds, BlogTx.class);
  }
  
  @Override
  public BlogDiscussion updateDiscussion(BlogDiscussion discussion) {
    discussion = createOrUpdateDiscussionDs(CloneUtils.clone(discussion, BlogDiscussionDs.class));
    return CloneUtils.clone(discussion, BlogDiscussionTx.class);
  }

  private void deleteDiscussions(List<BlogDiscussion> discussions) {
    if (discussions != null) {
      for (BlogDiscussion discussion : discussions) {
        BlogDiscussionDs discussionDs = CloneUtils.clone(discussion, BlogDiscussionDs.class);
        deleteDiscussionDs(discussionDs);
      }
    }
  }

  private void deleteDiscussionDs (BlogDiscussionDs discussionDs) {
    List<BlogComment> comments = discussionDs.getComments();
    if (comments != null) {
      for (int it = 0; it < comments.size(); it++) {
        BlogCommentDs commentDs = (BlogCommentDs)comments.get(it);
        if (commentDs.getKey() != null) {
          dao.delete(commentDs);
        }
      }
    }
    dao.delete(discussionDs);
  }
  
  private BlogDs createOrUpdateBlogDs (BlogDs blogDs) {
    List<BlogDiscussion> discussions = blogDs.getDiscussions();
    if (discussions != null) {
      for (int it = 0; it < discussions.size(); it++) {
        BlogDiscussionDs discussionDs = (BlogDiscussionDs)discussions.get(it);
        discussionDs = createOrUpdateDiscussionDs(discussionDs);
        discussions.set(it, discussionDs);
      }
      blogDs.setDiscussions(discussions);
    }
    if (blogDs.getKey() == null) {
      if (blogDs.getCode() == null) {
        blogDs.setCode(getNextCodeCounter());
      }
      blogDs = dao.create(blogDs);
    } else {
      blogDs = dao.update(blogDs);
    }
    return blogDs;
  }
  
  private BlogDiscussionDs createOrUpdateDiscussionDs (BlogDiscussionDs discussionDs) {
    List<BlogComment> comments = discussionDs.getComments();
    if (comments != null) {
      for (int it = 0; it < comments.size(); it++) {
        BlogCommentDs commentDs = (BlogCommentDs)comments.get(it);
        if (commentDs.getKey() == null) {
          commentDs = dao.create(commentDs);
        } else {
          commentDs = dao.update(commentDs);
        }
        comments.set(it, commentDs);
      }
      discussionDs.setComments(comments);
    }
    if (discussionDs.getKey() == null) {
      if (discussionDs.getCode() == null) {
        discussionDs.setCode(getNextCodeCounter());
      }
      discussionDs = dao.create(discussionDs);
    } else {
      discussionDs = dao.update(discussionDs);
    }
    return discussionDs;
  }
  
  public BlogDiscussion findDiscussionByCode(String code) {
    BlogDiscussionDs discussionDs = dao.findSingle(BlogDiscussionDs.class, "code == codeParam", String.class.getName() + " codeParam", null, code);
    return CloneUtils.clone(discussionDs, BlogDiscussionTx.class);
  }
  
  public Blog findBlogByCode(String code) {
    BlogDs blogDs = dao.findSingle(BlogDs.class, "code == codeParam", String.class.getName() + " codeParam", null, code);
    return CloneUtils.clone(blogDs, BlogTx.class);
  }
  
  public BlogDiscussion findDiscussionById(String id, boolean fetchComments) {
    BlogDiscussion discussion = internalFindDiscussionById(id, true);
    return CloneUtils.clone(discussion, BlogDiscussionTx.class);
  }
  
  private BlogDiscussionDs internalFindDiscussionById(String id, boolean fetchComments) {
    if (fetchComments) {
      CacheUtils.deleteByKeyWithCondition(id, BlogDiscussion.class, new CacheUtils.Condition<BlogDiscussion>() {
        public boolean evaluate(BlogDiscussion cachedEntity) {
          return (cachedEntity.getComments() == null || cachedEntity.getComments().size() == 0);
        }
      });
    }
    FindContext<BlogDiscussionDs> context = new FindContext<BlogDiscussionDs>(BlogDiscussionDs.class).setId(id);
    if (fetchComments) {
      context.includedField("commentsKeys");
    }
    BlogDiscussionDs ds = dao.findById(context);
    return ds;
  }
  
  private BlogDs internalFindBlogById(String id, boolean fetchDiscussions) {
    if (fetchDiscussions) {
      CacheUtils.deleteByKeyWithCondition(id, Blog.class, new CacheUtils.Condition<Blog>() {
        public boolean evaluate(Blog cachedEntity) {
          return (cachedEntity.getDiscussions() == null || cachedEntity.getDiscussions().size() == 0);
        }
      });
    }
    FindContext<BlogDs> context = new FindContext<BlogDs>(BlogDs.class).setId(id);
    if (fetchDiscussions) {
      context.includedField("discussionsKeys");
    }
    BlogDs ds = dao.findById(context);
    return ds;
  }
  
  public Blog addDiscussionToBlog(String id, BlogDiscussion discussion) {
    BlogDs blog = internalFindBlogById(id, true);
    List<BlogDiscussion> discussions = blog.getDiscussions();
    discussions.add(CloneUtils.clone(discussion, BlogDiscussionDs.class));
    blog.setDiscussions(discussions);
    blog = createOrUpdateBlogDs(blog);
    return CloneUtils.clone(blog, BlogTx.class);
  }
  
  public BlogDiscussion addCommentToDiscussion(String id, BlogComment comment) {
    BlogDiscussionDs discussion = internalFindDiscussionById(id, true);
    List<BlogComment> comments = discussion.getComments();
    comments.add(CloneUtils.clone(comment, BlogCommentDs.class));
    discussion.setComments(comments);
    discussion = createOrUpdateDiscussionDs(discussion);
    return CloneUtils.clone(discussion, BlogDiscussionTx.class);
  }
  
  private String getNextCodeCounter() {
    return ""+generalAdapter.findNextCounterValue();
  }
  
}
