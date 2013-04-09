package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;

import java.util.List;

public interface BlogAdapter {

  public List<Blog> findAllBlogs();
  
  public Blog updateBlog(Blog entity);

  public void deleteBlog(Blog entity);

  public Blog createBlog(Blog entity);

  public Blog findBlogById(String id);
  
  public Blog findBlogByCode(String code);
  
  public BlogDiscussion findDiscussionById(String id, boolean fetchComments);
  
  public BlogDiscussion addCommentToDiscussion(String id, BlogComment comment);
  
  public BlogDiscussion updateDiscussion(BlogDiscussion entity);
  
}
