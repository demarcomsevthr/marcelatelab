package it.mate.econyx.client.activities;

import it.mate.econyx.client.events.PortalInitCompleteEvent;
import it.mate.econyx.client.events.PortalPageExplorerRetrieveEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.ArticlePlace;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.econyx.client.places.ProductPlace;
import it.mate.econyx.client.util.PortalPageClientUtil;
import it.mate.econyx.client.util.PortalUtils;
import it.mate.econyx.client.view.PortalPageEditView;
import it.mate.econyx.client.view.PortalPageExplorerView;
import it.mate.econyx.client.view.PortalPageListView;
import it.mate.econyx.client.view.PortalPageView;
import it.mate.econyx.client.view.ProductListView;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.ArticleFolderPage;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProductFolderPage;
import it.mate.econyx.shared.model.ProductPage;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.econyx.shared.model.impl.PortalPageTx;
import it.mate.econyx.shared.services.PortalPageServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PortalPageActivity extends BaseActivity implements 
      PortalPageListView.Presenter,
      PortalPageExplorerView.Presenter,
      PortalPageEditView.Presenter,
      PortalPageView.Presenter {

  private final PortalPagePlace place;
  
  private PortalPageServiceAsync portalPageService = AppClientFactory.IMPL.getGinjector().getPortalPageService();
  
  private PortalPage currentPage = null;
  
  public PortalPageActivity(PortalPagePlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    
    GwtUtils.log(getClass(), hashCode(), "start", "place = " + place);
    
    registerHandlers(eventBus);
    if (place.getToken().equals(PortalPagePlace.EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getPortalPageEditView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(PortalPagePlace.LIST)) {
      initView(AppClientFactory.IMPL.getGinjector().getPortalPageListView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(PortalPagePlace.LIST_EXPLORER)) {
      initView(AppClientFactory.IMPL.getGinjector().getPortalPageExplorerView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(PortalPagePlace.LIST_MENU)) {
      initView(AppClientFactory.IMPL.getGinjector().getPortalPageListMenuView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(PortalPagePlace.VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getPortalPageView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(PortalPagePlace.VIEW_BY_CODE)) {
      initView(AppClientFactory.IMPL.getGinjector().getPortalPageView(), panel);
      retrieveModel();
    }
    
  }
  
  @Override
  public void onDispose() {
    super.onDispose();
  }
  
  private void registerHandlers(EventBus eventBus) {
    
  }

  private void retrieveModel() {
    if (place.getToken().equals(PortalPagePlace.LIST)) {
      retrieveChildreen(null);
    } else if (place.getToken().equals(PortalPagePlace.LIST_EXPLORER)) {
      retrieveChildreen(null);
    } else if (place.getToken().equals(PortalPagePlace.LIST_MENU)) {
      retrieveChildreen(null, true);
    } else if (place.getToken().equals(PortalPagePlace.EDIT)) {
      if (place.getModel() instanceof PortalPage) {
        PortalPage page = (PortalPage)place.getModel();
        if (page.getId() != null) {
          findById(page.getId(), true, true, true, new Delegate<PortalPage>() {
            public void execute(PortalPage page) {
              GwtUtils.log(getClass(), "edit", "refreshed page " + page);
              getView().setModel(page);
            }
          });
        } else {
          getView().setModel(page);
        }
      }
    } else if (place.getToken().equals(PortalPagePlace.VIEW)) {
      if (place.getModel() != null) {
        getView().setModel(place.getModel());
      } else {
        retrieveChildreen(null, true);
      }
    } else if (place.getToken().equals(PortalPagePlace.VIEW_BY_CODE)) {
      String code = (String)place.getModel();
      findByCode(code, new Delegate<PortalPage>() {
        public void execute(PortalPage portalPage) {
          getView().setModel(portalPage);
        }
      });
    } else {
      getView().setModel(null, null);
    }
  }
  
  public void retrieveChildreen (PortalPage parent) {
    retrieveChildreen(parent, false);
  }
  
  public void retrieveChildreen (PortalPage parent, boolean menu) {
    if (parent == null) {
      final AsyncCallback<List<PortalPage>> pagesCallback = new AsyncCallback<List<PortalPage>>() {
        public void onSuccess(List<PortalPage> pages) {
          if (place.getToken().equals(PortalPagePlace.LIST)) {
            getView().setModel(pages);
          } else if (place.getToken().equals(PortalPagePlace.LIST_EXPLORER)) {
            PortalPageExplorerView.TreeModel model = new PortalPageExplorerView.TreeModel();
            model.childreen = pages;
            getView().setModel(model);
          } else if (place.getToken().equals(PortalPagePlace.LIST_MENU)) {
            getView().setModel(pages);
            goToHomePage(pages);
          } else if (place.getToken().equals(PortalPagePlace.VIEW)) {
            goToHomePage(pages);
          }
        }
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
      };
      PortalUtils.hideLoadingPanel();
      AppClientFactory.IMPL.getEventBus().fireEvent(new PortalInitCompleteEvent());
      if (menu) {
        portalPageService.findAllRootMenu(pagesCallback);
      } else {
        if (AppClientFactory.isAdminModule) {
          portalPageService.findAllRoot(pagesCallback);
        } else {
          portalPageService.findAllRootExplorer(new AsyncCallback<List<PortalPage>>() {
            public void onFailure(Throwable caught) {
              pagesCallback.onFailure(caught);
            }
            public void onSuccess(List<PortalPage> results) {
              AppClientFactory.IMPL.getEventBus().fireEvent(new PortalPageExplorerRetrieveEvent());
              pagesCallback.onSuccess(results);
            }
          });
        }
      }
    } else {
      if (parent instanceof PortalFolderPage) {
        PortalFolderPage parentPage = (PortalFolderPage)parent;
        
        AsyncCallback<PortalFolderPage> callback = new AsyncCallback<PortalFolderPage>() {
          public void onSuccess(PortalFolderPage parent) {
            PortalPageClientUtil.putInCache(parent);
            PortalPageExplorerView.TreeModel model = new PortalPageExplorerView.TreeModel();
            model.parent = parent;
            model.childreen = parent.getChildreen();
            getView().setModel(model, null);
          }
          public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
          }
        };
        
        PortalFolderPage cachedPage = (PortalFolderPage)PortalPageClientUtil.getFromCache(parentPage.getId());
        if (cachedPage != null && cachedPage.getChildreen() != null && cachedPage.getChildreen().size() > 0) {
          GwtUtils.log(getClass(), "retrieveChildreen", "found page in cache " + cachedPage);
          callback.onSuccess(cachedPage);
        } else {
          portalPageService.fetchChildreen(parentPage, callback);
        }
        
      }
    }
  }
  
  private void goToHomePage (List<PortalPage> pages) {
    if (AppClientFactory.IMPL.getPortalSessionState().getCurrentPageId() == null) {
      for (PortalPage page : pages) {
        if (page.getHomePage()) {
          goToPage(page);
        }
      }
    }
  }
  
  public void goToPage (PortalPage page) {
    //////////////////////////////////////////////////////////////////////////////
    
    // 06/11/2012 sostituito con PortalPageUtils.goToPage
    
    PortalPageClientUtil.goToPage(page, true);
    
  }
  
  @Override
  public void edit(PortalPage page) {
    goTo(new PortalPagePlace(PortalPagePlace.EDIT, page));
  }
  
  @Override
  public void delete(final PortalPage page) {
    portalPageService.delete(page, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        if (page.getParentId() != null) {
          findById(page.getParentId(), true, true, true, new Delegate<PortalPage>() {
            public void execute(PortalPage parentPage) {
              goTo(new PortalPagePlace(PortalPagePlace.EDIT, parentPage));
            }
          });
        } else {
          goTo(new PortalPagePlace(PortalPagePlace.LIST));
        }
      }
    });
  }

  @Override
  public void show(PortalPage page) {
    goTo(new PortalPagePlace(PortalPagePlace.VIEW, page));
  }
  
  @Override
  public void update(PortalPage page) {
    if (page.getId() == null) {
      portalPageService.create(page, new AsyncCallback<PortalPage>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(PortalPage page) {
          GwtUtils.log(getClass(), "update.success", "created page with id " + page.getId());
          refresh(page);
        }
      });
    } else {
      portalPageService.update(page, new AsyncCallback<PortalPage>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(PortalPage page) {
          GwtUtils.log(getClass(), "update.success", "updated page with id " + page.getId());
          refresh(page);
        }
      });
    }
  }

  @Override
  public void refresh(PortalPage page) {
    findById(page.getId(), false, false, false, new Delegate<PortalPage>() {
      public void execute(PortalPage page) {
        if (getView() instanceof PortalPageEditView) {
          getView().setModel(page);
        } else {
          goTo(new PortalPagePlace(PortalPagePlace.EDIT, page));
        }
      }
    });
  }
  
  private void findById(String id, final boolean fetchChildreen, final boolean fetchProducts, final boolean fetchHtmls, final Delegate<PortalPage> delegate) {
    portalPageService.findById(id, fetchChildreen, fetchProducts, fetchHtmls, new AsyncCallback<PortalPage>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(PortalPage page) {
        delegate.execute(page);
      }
    });
  }
  
  private void findByCode(String code, final Delegate<PortalPage> delegate) {
    portalPageService.findByCode(code, new AsyncCallback<PortalPage>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(PortalPage result) {
        delegate.execute(result);
      }
    });
  }
  
  @Override
  public void fetchHtmls(WebContentPage page, final Delegate<WebContentPage> delegate) {
    if (page.getId() == null)
      delegate.execute(page);
    
    AsyncCallback<WebContentPage> callback = new AsyncCallback<WebContentPage>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(WebContentPage page) {
        PortalPageClientUtil.putInCache(page);
        delegate.execute(page);
      }
    };
    
    WebContentPage cachedPage = (WebContentPage)PortalPageClientUtil.getFromCache(page.getId());
    if (cachedPage != null && cachedPage.getHtmls() != null && cachedPage.getHtmls().size() > 0) {
      GwtUtils.log(getClass(), "fetchHtmls", "found page in cache " + cachedPage);
      callback.onSuccess(cachedPage);
    } else {
      portalPageService.fetchHtmls(page, callback);
    }
    
  }

  @Override
  public void updateHtmlContent(WebContentPage page, HtmlContent content, boolean isHtmlContentModified, final Delegate<WebContentPage> delegate) {
    if (isHtmlContentModified) {
      portalPageService.updateHtmlContent(page.getId(), content, new AsyncCallback<WebContentPage>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(WebContentPage result) {
          delegate.execute(result);
        }
      });
    } else {
      delegate.execute(page);
    }
  }
  
  public void initProductListView (AcceptsOneWidget panel, ProductFolderPage productFolderPage, boolean useProductPageList, String productListHeader) {
    Object model = null;
    if (useProductPageList) {
      ProductListView.ProductPageList productPageList = new ProductListView.ProductPageList();
      for (PortalPage childPage : productFolderPage.getChildreen()) {
        ProductPage productPage = (ProductPage)childPage;
        productPageList.add(productPage);
      }
      model = productPageList;
    } else {
      List<Articolo> products = new ArrayList<Articolo>();
      for (PortalPage childPage : productFolderPage.getChildreen()) {
        ProductPage productPage = (ProductPage)childPage;
        products.add(productPage.getEntity());
      }
      model = products;
    }
    ProductActivity childActivity = startProductActivity(panel, new ProductPlace(ProductPlace.LIST, model));
    if (productListHeader != null) {
      childActivity.getView().setModel(productListHeader);
    }
  }
  
  public void initProductView (AcceptsOneWidget panel, ProductPage productPage) {
    Articolo product = productPage.getEntity();
    startProductActivity(panel, new ProductPlace(ProductPlace.VIEW, product));
  }
  
  private ProductActivity startProductActivity(AcceptsOneWidget panel, ProductPlace place) {
    ProductActivity productActivity = new ProductActivity(place, AppClientFactory.IMPL);
    productActivity.start(panel, AppClientFactory.IMPL.getEventBus());
    return productActivity;
  }
  
  public void initArticleFolderView (AcceptsOneWidget panel, ArticleFolderPage page) {
    ArticleFolder entity = page.getEntity();
    startArticleActivity(panel, new ArticlePlace(ArticlePlace.VIEW, entity));
  }
  
  private ArticleActivity startArticleActivity(AcceptsOneWidget panel, ArticlePlace place) {
    ArticleActivity activity = new ArticleActivity(place, AppClientFactory.IMPL);
    activity.start(panel, AppClientFactory.IMPL.getEventBus());
    return activity;
  }
  
  public void initChildPortalPageView(final AcceptsOneWidget panel, PortalPage childPage, final PortalPage parentPage) {
    findById(childPage.getId(), true, false, false, new Delegate<PortalPage>() {
      public void execute(PortalPage childPage) {
        // bug-fix perche' con la fetch si perde il riferimento al parent
        if (childPage instanceof PortalPageTx && childPage.getParent() == null) {
          PortalPageTx childPageTx = (PortalPageTx)childPage;
          childPageTx.setParent((PortalPageTx)parentPage);
        }
        PortalPageActivity childActivity = new PortalPageActivity(new PortalPagePlace(PortalPagePlace.VIEW, childPage), AppClientFactory.IMPL);
        childActivity.start(panel, AppClientFactory.IMPL.getEventBus());
      }
    });
  }

  @Override
  public void newInstance(String classname, final Delegate<PortalPage> delegate) {
    portalPageService.newInstance(classname, new AsyncCallback<PortalPage>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(PortalPage page) {
        if (page != null) {
          delegate.execute(page);
        }
      }
    });
  }
  
}
