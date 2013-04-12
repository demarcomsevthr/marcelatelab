package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.BlogAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.server.util.PortalSessionStateServerUtils;
import it.mate.econyx.server.util.ServletThreadUtils;
import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.services.BlogService;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@Service (".blogService")
public class BlogServiceImpl extends RemoteServiceServlet implements BlogService {

  private static Logger logger = Logger.getLogger(BlogServiceImpl.class);
  
  private BlogAdapter adapter;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getBlogAdapter();
    logger.debug("initialized " + this);
  }

  @Override
  protected void onBeforeRequestDeserialized(String serializedRequest) {
    ServletThreadUtils.set(getThreadLocalRequest(), getThreadLocalResponse());
    PortalSessionStateServerUtils.setInThread(AdaptersUtil.getGeneralAdapter().retrievePortalSessionState(getThreadLocalRequest()));
    super.onBeforeRequestDeserialized(serializedRequest);
  }
  
  @Override
  public List<Blog> findAllBlogs() throws ServiceException {
    return adapter.findAllBlogs();
  }

  @Override
  public Blog updateBlog(Blog entity) throws ServiceException {
    return adapter.updateBlog(entity);
  }

  @Override
  public void deleteBlog(Blog entity) throws ServiceException {
    adapter.deleteBlog(entity);
  }

  @Override
  public Blog createBlog(Blog entity) throws ServiceException {
    return adapter.createBlog(entity);
  }

  @Override
  public Blog findBlogById(String id) throws ServiceException {
    return adapter.findBlogById(id);
  }

  @Override
  public Blog findBlogByCode(String code) throws ServiceException {
    return adapter.findBlogByCode(code);
  }

  @Override
  public BlogDiscussion findDiscussionById(String id, boolean fetchComments) throws ServiceException {
    return adapter.findDiscussionById(id, fetchComments);
  }

  @Override
  public BlogDiscussion addCommentToDiscussion(String id, BlogComment comment) throws ServiceException {
    return adapter.addCommentToDiscussion(id, comment);
  }

  @Override
  public BlogDiscussion updateDiscussion(BlogDiscussion entity) throws ServiceException {
    return adapter.updateDiscussion(entity);
  }
  
  @Override
  public Blog addDiscussionToBlog(String id, BlogDiscussion discussion) throws ServiceException {
    return adapter.addDiscussionToBlog(id, discussion);
  }

}
