package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".blogService")
public interface BlogService extends RemoteService {
  
  public List<Blog> findAllBlogs()throws ServiceException;
  
  public Blog updateBlog(Blog entity)throws ServiceException;

  public void deleteBlog(Blog entity)throws ServiceException;

  public Blog createBlog(Blog entity)throws ServiceException;

  public Blog findBlogById(String id)throws ServiceException;
  
  public Blog findBlogByCode(String code)throws ServiceException;
  
  public BlogDiscussion findDiscussionById(String id, boolean fetchComments)throws ServiceException;
  
  public BlogDiscussion addCommentToDiscussion(String id, BlogComment comment)throws ServiceException;
  
  public BlogDiscussion updateDiscussion(BlogDiscussion entity)throws ServiceException;
  
  public Blog addDiscussionToBlog(String id, BlogDiscussion discussion) throws ServiceException;
  
}
