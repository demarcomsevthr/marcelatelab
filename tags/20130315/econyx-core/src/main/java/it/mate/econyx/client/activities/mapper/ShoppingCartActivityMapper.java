package it.mate.econyx.client.activities.mapper;

import it.mate.econyx.client.activities.CustomerActivity;
import it.mate.econyx.client.activities.ShoppingCartActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.CustomerPlace;
import it.mate.econyx.client.places.ShoppingCartPlace;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class ShoppingCartActivityMapper extends BaseActivityMapper {

  public ShoppingCartActivityMapper(AppClientFactory clientFactory) {
    super(clientFactory);
  }

  public String getHistoryName() {
    return "shoppingCartMvp";
  }

  public Place getDefaultPlace() {
    return new ShoppingCartPlace(ShoppingCartPlace.SUMMARY);
  }

  public Activity getActivity(Place place) {
    Activity activityAlreadyInstantiated = popActivityAlreadyInstantiated();
    if (activityAlreadyInstantiated != null) {
      return activityAlreadyInstantiated;
    }
    if (place instanceof ShoppingCartPlace) {
      beforePlaceChange(place);
      return new ShoppingCartActivity((ShoppingCartPlace)place, (AppClientFactory)clientFactory);
    }
    if (place instanceof CustomerPlace) {
      beforePlaceChange(place);
      return new CustomerActivity((CustomerPlace)place, (AppClientFactory)clientFactory);
    }
    return null;
  }

}
