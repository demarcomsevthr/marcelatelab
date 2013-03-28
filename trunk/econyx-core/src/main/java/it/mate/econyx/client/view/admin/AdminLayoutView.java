package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.events.PortalSessionStateChangeEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.ArticlePlace;
import it.mate.econyx.client.places.GeneralPlace;
import it.mate.econyx.client.places.ImagePlace;
import it.mate.econyx.client.places.OrderPlace;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.econyx.client.places.PortalUserPlace;
import it.mate.econyx.client.places.ProductPlace;
import it.mate.econyx.client.ui.AdminClientUtils;
import it.mate.econyx.client.ui.PageBreadcrumb;
import it.mate.econyx.client.util.EconyxUtils;
import it.mate.gwtcommons.client.ui.MvpPanel;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class AdminLayoutView extends Composite {
  
  public interface ViewUiBinder extends UiBinder<Widget, AdminLayoutView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField Panel loggedStatePanel;
  @UiField Panel notloggedStatePanel;
  @UiField Panel roundedPanel;
  @UiField MenuBar menubar;
  @UiField PageBreadcrumb breadcrumb;
  @UiField MvpPanel adminMainMvp;
  @UiField Panel adminTabContainerPanel;
  @UiField Label loggedUserLabel;
  @UiField Anchor googleLoginAnchor;
  @UiField Anchor googleLogoutAnchor;
  @UiField Anchor forceGoogleLogoutAnchor;
  
  public AdminLayoutView() {
    initUI();
    
    GwtUtils.setBorderRadius(roundedPanel, "5px");
    
    AppClientFactory.IMPL.getEventBus().addHandler(PortalSessionStateChangeEvent.TYPE, new PortalSessionStateChangeEvent.Handler() {
      public void onPortalSessionStateChange(PortalSessionStateChangeEvent event) {
        if (event.getState().getLoggedUser() == null) {
          
          // 28/11/2012
          if (Window.Location.getPath().contains(EconyxUtils.SECURE_ADMIN_PAGE_URL)) {
            setLoggedState(true);
          } else {
            setLoggedState(false);
            AppClientFactory.IMPL.getGinjector().getPortalUserService().getGoogleLoginURL(EconyxUtils.getCompleteUrl(EconyxUtils.SECURE_ADMIN_PAGE_URL), new AsyncCallback<String>() {
              public void onSuccess(String googleLoginUrl) {
                googleLoginAnchor.setHref(googleLoginUrl);
              }
              public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
              }
            });
            AppClientFactory.IMPL.getGinjector().getPortalUserService().getGoogleLogoutURL(EconyxUtils.getCompleteUrl(EconyxUtils.NOT_SECURE_ADMIN_PAGE_URL), new AsyncCallback<String>() {
              public void onSuccess(String googleLogoutUrl) {
                forceGoogleLogoutAnchor.setHref(googleLogoutUrl);
              }
              public void onFailure(Throwable caught) {
              }
            });
          }
          
        } else {
          if (Window.Location.getPath().contains(EconyxUtils.SECURE_ADMIN_PAGE_URL)) {
            // siamo nella pagina sicura
            setLoggedState(true);
            GwtUtils.log(getClass(), "onPortalSessionStateChange", "received " + event.getState());
            loggedUserLabel.setText("Logged as " + event.getState().getLoggedUser().getScreenName());
            AppClientFactory.IMPL.getGinjector().getPortalUserService().getGoogleLogoutURL(EconyxUtils.getCompleteUrl(EconyxUtils.NOT_SECURE_ADMIN_PAGE_URL), new AsyncCallback<String>() {
              public void onSuccess(String googleLogoutUrl) {
                googleLogoutAnchor.setHref(googleLogoutUrl);
              }
              public void onFailure(Throwable caught) {
              }
            });
          } else {
            
            if (event.getState().getLoggedUser().isAdminUser()) {
              // redirigo sulla pagina sicura
              Window.Location.replace(EconyxUtils.getCompleteUrl(EconyxUtils.SECURE_ADMIN_PAGE_URL));
            } else {
              Window.alert("ATTENZIONE: l'account " + event.getState().getLoggedUser().getEmailAddress() + " non è un amministratore di questo sito!");
            }
            
          }
        }
      }
    });
    
  }
  
  private void setLoggedState (boolean logged) {
    loggedStatePanel.setVisible(logged);
    notloggedStatePanel.setVisible(!logged);
  }
  
  protected void initUI() {
    initWidget(uiBinder.createAndBindUi(this));

    /*
    addMenu(menubar, "Generale", null, new MenuItemInfo[] {
        new MenuItemInfo("Impostazioni", new GeneralPlace())
      });
      */
    addMenu(menubar, "Generale", new GeneralPlace(), null);
    
    addMenu(menubar, "Pagine", new PortalPagePlace(PortalPagePlace.LIST).setHistoryName("Pagine"), null);
    addMenu(menubar, "Immagini", new ImagePlace(ImagePlace.LIST), null);
    addMenu(menubar, "Articoli", new ArticlePlace(ArticlePlace.FOLDER_LIST).setHistoryName("Articoli"), null);
    addMenu(menubar, "Ordini", new OrderPlace(OrderPlace.LIST).setHistoryName("Ordini"), null);
    addMenu(menubar, "Prodotti", new ProductPlace(ProductPlace.LIST).setHistoryName("Prodotti"), null);
    addMenu(menubar, "Produttori", new ProductPlace(ProductPlace.PRODUCER_LIST).setHistoryName("Produttori"), null);
    addMenu(menubar, "Utenti", new PortalUserPlace(PortalUserPlace.LIST).setHistoryName("Utenti"), null);

    /* 30/11/2012
    addMenu(menubar, "Ordini", null, new MenuItemInfo[] {
        new MenuItemInfo("Elenco ordini", new OrderPlace(OrderPlace.LIST).name("Ordini"))
      });
    addMenu(menubar, "Pagine", null, new MenuItemInfo[] {
        new MenuItemInfo("Elenco pagine", new PortalPagePlace(PortalPagePlace.LIST).name("Pagine"))
      });
    addMenu(menubar, "Immagini", null, new MenuItemInfo[] {
        new MenuItemInfo("Elenco immagini", new ImagePlace(ImagePlace.LIST))
      });
    addMenu(menubar, "Prodotti", null, new MenuItemInfo[] {
        new MenuItemInfo("Elenco prodotti", new ProductPlace(ProductPlace.LIST).name("Prodotti"))
      });
    addMenu(menubar, "Produttori", null, new MenuItemInfo[] {
        new MenuItemInfo("Elenco produttori", new ProductPlace(ProductPlace.PRODUCER_LIST).name("Produttori"))
      });
    addMenu(menubar, "Utenti", null, new MenuItemInfo[] {
        new MenuItemInfo("Elenco utenti", new PortalUserPlace(PortalUserPlace.LIST).name("Utenti"))
      });
     */
    
    breadcrumb.setClientFactory(AppClientFactory.IMPL);

    adminMainMvp.initMvp(AppClientFactory.IMPL, AppClientFactory.IMPL.getGinjector().getAdminActivityMapper());

    // 30/11/2012
//  ResizeUtils.setHeightRelativeToScreen(adminTabContainerPanel, AdminClientProperties.adminTabPanelRelativeHeight());
    AdminClientUtils.applyDefaultResizePolicy(adminTabContainerPanel);
    
  }
  
  public MvpPanel getMvpPanel() {
    return adminMainMvp;
  }
  
  private class MenuItemInfo {
    String text;
    Place place;
    private MenuItemInfo(String text, Place place) {
      super();
      this.text = text;
      this.place = place;
    }
  }
  
  /*
  private void addMenu (MenuBar menubar, String title, MenuItemInfo[] items) {
    MenuBar folderMenu = new MenuBar(true);
    menubar.addItem(title, folderMenu);
    for (MenuItemInfo item : items) {
      folderMenu.addItem(item.text, createPlaceMenuCommand(item.place));
    }
  }
  */
  
  private void addMenu (MenuBar menubar, String title, Place rootPlace, MenuItemInfo[] items) {
    if (rootPlace != null) {
      menubar.addItem(title, createPlaceMenuCommand(rootPlace));
    } else {
      MenuBar folderMenu = new MenuBar(true);
      menubar.addItem(title, folderMenu);
      for (MenuItemInfo item : items) {
        folderMenu.addItem(item.text, createPlaceMenuCommand(item.place));
      }
    }
  }

  private <P extends Place> Command createPlaceMenuCommand(final Place place) {
    return new Command() {
      public void execute() {
        AppClientFactory.IMPL.getPlaceController().goTo(place);
      }
    };
  }

}
