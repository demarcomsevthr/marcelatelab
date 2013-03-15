package it.mate.econyx.client.ui;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.econyx.client.util.PortalPageClientUtil;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.gwtcommons.client.places.HistoryPlace;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.MvpUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PageBreadcrumb extends Composite {
  
  private HorizontalPanel panel = new HorizontalPanel();
  
  private static List<Place> history = new ArrayList<Place>();
  
  private AppClientFactory clientFactory;
  
  private static PortalPage homePage;

  public PageBreadcrumb() {
    initUI();
  }
  
  public void setClientFactory(AppClientFactory clientFactory) {
    this.clientFactory = clientFactory;
    updateView();
  }
  
  protected void initUI() {
    VerticalPanel vp = new VerticalPanel();
    if (AppClientFactory.isAdminModule) {
      vp.add(new Spacer("1px", "10px"));
    }
    vp.add(panel);
    if (AppClientFactory.isAdminModule) {
      vp.add(new Spacer("1px", "10px"));
    }
    initWidget(vp);
    MvpUtils.addPlaceChangeHandler(AppClientFactory.IMPL.getEventBus(), new PlaceChangeEvent.Handler() {
      public void onPlaceChange(PlaceChangeEvent event) {
        GwtUtils.log(getClass(), "onPlaceChange", event.getNewPlace().toString());
        Place place = event.getNewPlace();
        if (place instanceof PortalPagePlace) {
          PortalPagePlace portalPagePlace = (PortalPagePlace)place;
          if (portalPagePlace.getModel() instanceof PortalPage) {
            PortalPage page = (PortalPage)portalPagePlace.getModel();
            if (page.getName() != null)
              updatePagePlace(portalPagePlace);
          }
        } else if (place instanceof HistoryPlace) {
          HistoryPlace placeWithName = (HistoryPlace)place;
          if (placeWithName.getHistoryName() != null) {
            addPlaceAndUpdate(place);
          } else {
            updateView();
          }
        } else {
          updateView();
        }
      }
    });
  }
  
  private void updatePagePlace(PortalPagePlace newPagePlace) {
    PortalPage newPage = (PortalPage)newPagePlace.getModel();
    GwtUtils.log(getClass(), "updatePage", "updating new page '" + newPage.getName()+"' with parentId = " + newPage.getParentId());
    if (newPage.getHomePage()) {
      PageBreadcrumb.homePage = newPage;
    }
    if (newPage.getParentId() == null) {
      addPageAndUpdate(true, newPagePlace);
    } else {
      boolean found = false;
      for (Iterator<Place> it = history.iterator(); it.hasNext();) {
        Place historyPlace = it.next();
        if (historyPlace instanceof PortalPagePlace) {
          PortalPage historyPage = (PortalPage)((PortalPagePlace)historyPlace).getModel();
          if (newPage.getParentId().equals(historyPage.getId())) {
            found = true;
            while (it.hasNext()) {
              it.next();
              it.remove();
            }
            addPageAndUpdate(false, newPagePlace);
            break;
          }
        }
      }
      if (!found) {
        addPageAndUpdate(true, newPagePlace);
      }
    }
  }

  private void addPageAndUpdate(boolean clear, PortalPagePlace pagePlace) {
    if (clear)
      history.clear();
    PortalPage page = (PortalPage)pagePlace.getModel();
    if (!page.getHomePage()) {
      history.add(pagePlace);
    }
    updateView();
  }
  
  private void addPlaceAndUpdate(Place place) {
    if (place instanceof HistoryPlace) {
      HistoryPlace historyPlace = (HistoryPlace)place;
      if (!((HistoryPlace)historyPlace).isHistoryAppend()) {
        history.clear();
      }
      if (history.size() > 0) {
        for (int it = 0; it < history.size(); it++) {
          Place historyItem = history.get(it);
          if (historyItem instanceof HistoryPlace) {
            HistoryPlace placeInHistory = (HistoryPlace)historyItem;
            if (placeInHistory.getHistoryName().equals(historyPlace.getHistoryName())) {
              history = history.subList(0, it + 1);
              updateView();
              return;
            }
          }
        }
      }
      history.add(place);
      updateView();
    }
  }
  
  private void updateView() {
    panel.clear();
    Anchor anchor = new Anchor("Home");
    anchor.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        history.clear();
        updateView();
        if (AppClientFactory.isSiteModule) {
          if (PageBreadcrumb.homePage != null) {
            goToPage(PageBreadcrumb.homePage);
          } else {
            clientFactory.getPlaceController().goTo(new PortalPagePlace(PortalPagePlace.VIEW));
          }
        } else {
          clientFactory.getPlaceController().goTo(new PortalPagePlace(PortalPagePlace.LIST));
        }
      }
    });
    panel.add(anchor);
    for (final Place historyPlace : history) {
      if (historyPlace instanceof PortalPagePlace) {
        final PortalPage page = (PortalPage)((PortalPagePlace)historyPlace).getModel();
        anchor = new Anchor(SafeHtmlUtils.fromTrustedString(page.getName()));
        anchor.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            goToPage(page);
          }
        });
        panel.add(new HTML(SafeHtmlUtils.fromSafeConstant("&nbsp;/&nbsp;")));
        panel.add(anchor);
      } else if (historyPlace instanceof HistoryPlace) {
        HistoryPlace namedPlace = (HistoryPlace)historyPlace;
        anchor = new Anchor(SafeHtmlUtils.fromTrustedString(namedPlace.getHistoryName()));
        anchor.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            goToPlace(historyPlace);
          }
        });
        panel.add(new HTML(SafeHtmlUtils.fromSafeConstant("&nbsp;/&nbsp;")));
        panel.add(anchor);
      }
    }
  }
  
  private void goToPage(PortalPage page) {
    String placeToken;
    if (clientFactory.isAdminModule) {
      placeToken = PortalPagePlace.EDIT;
      clientFactory.getPlaceController().goTo(new PortalPagePlace(placeToken, page));
    } else {
      PortalPageClientUtil.goToPage(page, true);
    }
  }
  
  private void goToPlace(Place place) {
    clientFactory.getPlaceController().goTo(place);
  }
  
  public static PortalPage getPreviousPage() {
    if (history != null && history.size() > 1) {
      Place historyPlace = history.get(history.size() - 2);
      if (historyPlace instanceof PortalPagePlace) {
        return (PortalPage)((PortalPagePlace)historyPlace).getModel();
      }
    }
    return null;
  }
  
}
