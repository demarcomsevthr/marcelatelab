package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.WebContentPage;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PortalPageServiceAsync {

  void create(PortalPage entity, AsyncCallback<PortalPage> callback);

  void delete(PortalPage entity, AsyncCallback<Void> callback);

  void fetchChildreen(PortalFolderPage page, AsyncCallback<PortalFolderPage> callback);

  void findAll(AsyncCallback<List<PortalPage>> callback);

  void findAllRoot(AsyncCallback<List<PortalPage>> callback);

  void findById(String id, AsyncCallback<PortalPage> callback);

  void update(PortalPage entity, AsyncCallback<PortalPage> callback);

  void findById(String id, boolean resolveChildreen, boolean resolveProducts, boolean resolveHtmls, AsyncCallback<PortalPage> callback);

  void fetchHtmls(WebContentPage contentPage, AsyncCallback<WebContentPage> callback);

  void updateHtmlContent(String pageId, HtmlContent content, AsyncCallback<WebContentPage> callback);

  void findAllRootMenu(AsyncCallback<List<PortalPage>> callback);

  void findAllRootExplorer(AsyncCallback<List<PortalPage>> callback);

  void newInstance(String classname, AsyncCallback<PortalPage> callback);

  void findByCode(String code, AsyncCallback<PortalPage> callback);

}
