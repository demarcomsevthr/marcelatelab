package it.mate.protons.client.activities.mapper;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.history.BaseActivityMapper;
import it.mate.protons.client.activities.IngredientActivity;
import it.mate.protons.client.places.IngredientPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class IngredientActivityMapper extends BaseActivityMapper {
  
  @SuppressWarnings("rawtypes")
  public IngredientActivityMapper(BaseClientFactory clientFactory) {
    super(clientFactory);
  }

  @Override
  public Activity getActivity(Place place) {
    if (place instanceof IngredientPlace) {
      return new IngredientActivity(clientFactory, (IngredientPlace)place);
    }
    return null;
  }

  @Override
  public Place getDefaultPlace() {
    return null;
  }

  @Override
  public String getHistoryName() {
    return "ingredients";
  }

}
