package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BlogServiceAsync {

  void addCommentToDiscussion(String id, BlogComment comment, AsyncCallback<BlogDiscussion> callback);

  void createBlog(Blog entity, AsyncCallback<Blog> callback);

  void deleteBlog(Blog entity, AsyncCallback<Void> callback);

  void findAllBlogs(AsyncCallback<List<Blog>> callback);

  void findBlogByCode(String code, AsyncCallback<Blog> callback);

  void findBlogById(String id, AsyncCallback<Blog> callback);

  void findDiscussionById(String id, boolean fetchComments, AsyncCallback<BlogDiscussion> callback);

  void updateBlog(Blog entity, AsyncCallback<Blog> callback);

  void updateDiscussion(BlogDiscussion entity, AsyncCallback<BlogDiscussion> callback);

  void addDiscussionToBlog(String id, BlogDiscussion discussion, AsyncCallback<Blog> callback);

}
