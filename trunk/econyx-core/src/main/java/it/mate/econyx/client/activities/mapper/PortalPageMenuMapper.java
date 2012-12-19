package it.mate.econyx.client.activities.mapper;

import it.mate.econyx.client.activities.PortalPageActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class PortalPageMenuMapper extends BaseActivityMapper {

  public PortalPageMenuMapper(AppClientFactory clientFactory) {
    super(clientFactory);
  }

  public String getHistoryName() {
    return "portalMenu";
  }

  public Place getDefaultPlace() {
    return new PortalPagePlace(PortalPagePlace.LIST_MENU);
  }

  public Activity getActivity(Place place) {
    Activity activityAlreadyInstantiated = popActivityAlreadyInstantiated();
    if (activityAlreadyInstantiated != null) {
      return activityAlreadyInstantiated;
    }
    if (place instanceof PortalPagePlace && ((PortalPagePlace)place).getToken().equals(PortalPagePlace.LIST_MENU)) {
      return new PortalPageActivity((PortalPagePlace)getDefaultPlace(), (AppClientFactory)clientFactory);
    }
    return null;
  }

}
