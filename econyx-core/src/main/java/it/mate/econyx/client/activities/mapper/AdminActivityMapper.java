package it.mate.econyx.client.activities.mapper;

import it.mate.econyx.client.activities.ArticleActivity;
import it.mate.econyx.client.activities.CalEventActivity;
import it.mate.econyx.client.activities.GeneralActivity;
import it.mate.econyx.client.activities.ImageActivity;
import it.mate.econyx.client.activities.OrderActivity;
import it.mate.econyx.client.activities.PortalPageActivity;
import it.mate.econyx.client.activities.PortalUserActivity;
import it.mate.econyx.client.activities.ProductActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.ArticlePlace;
import it.mate.econyx.client.places.CalEventPlace;
import it.mate.econyx.client.places.GeneralPlace;
import it.mate.econyx.client.places.ImagePlace;
import it.mate.econyx.client.places.OrderPlace;
import it.mate.econyx.client.places.PortalPagePlace;
import it.mate.econyx.client.places.PortalUserPlace;
import it.mate.econyx.client.places.ProductPlace;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AdminActivityMapper extends BaseActivityMapper {

  public AdminActivityMapper(AppClientFactory clientFactory) {
    super(clientFactory);
  }

  public Activity getActivity(Place place) {
    // 10/03/2012
    // trucco per impedire che venga istanziata due volte l'activity per lo stesso activity manager 
    // (vedi it.mate.gwtcommons.client.history.MappedActivityManager.onPlaceChange) 
    Activity activityAlreadyInstantiated = popActivityAlreadyInstantiated();
    if (activityAlreadyInstantiated != null) {
      return activityAlreadyInstantiated;
    }
    if (place instanceof OrderPlace) {
      beforePlaceChange(place);
      return new OrderActivity((OrderPlace)place, (AppClientFactory)clientFactory);
    } else if (place instanceof ProductPlace) {
      beforePlaceChange(place);
      return new ProductActivity((ProductPlace)place, (AppClientFactory)clientFactory);
    } else if (place instanceof ArticlePlace) {
      beforePlaceChange(place);
      return new ArticleActivity((ArticlePlace)place, (AppClientFactory)clientFactory);
    } else if (place instanceof CalEventPlace) {
      beforePlaceChange(place);
      return new CalEventActivity((CalEventPlace)place, (AppClientFactory)clientFactory);
    } else if (place instanceof PortalPagePlace) {
      beforePlaceChange(place);
      return new PortalPageActivity((PortalPagePlace)place, (AppClientFactory)clientFactory);
    } else if (place instanceof GeneralPlace) {
      beforePlaceChange(place);
      return new GeneralActivity((GeneralPlace)place, (AppClientFactory)clientFactory);
    } else if (place instanceof PortalUserPlace) {
      beforePlaceChange(place);
      return new PortalUserActivity((PortalUserPlace)place, (AppClientFactory)clientFactory);
    } else if (place instanceof ImagePlace) {
      beforePlaceChange(place);
      return new ImageActivity((ImagePlace)place, (AppClientFactory)clientFactory);
    }
    return null;
  }

  public Place getDefaultPlace() {
    return new PortalPagePlace();
  }

  public String getHistoryName() {
    return "adminMvp";
  }

}
