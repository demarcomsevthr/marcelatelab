package it.mate.protons.client.activities.mapper;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.protons.client.activities.MainActivity;
import it.mate.protons.client.places.MainPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class MainActivityMapper extends BaseActivityMapper {
  
  @SuppressWarnings("rawtypes")
  public MainActivityMapper(BaseClientFactory clientFactory) {
    super(clientFactory);
  }

  @Override
  public Activity getActivity(Place place) {
    if (place instanceof MainPlace) {
      return new MainActivity(clientFactory, (MainPlace)place);
    }
    return null;
  }

  @Override
  public Place getDefaultPlace() {
//  return new MainPlace();
    return null;
  }

  @Override
  public String getHistoryName() {
    return "main";
  }
  
}
