package it.mate.therapyreminder.client.activities.mapper;

import it.mate.therapyreminder.client.places.MainPlace;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

public class MainAnimationMapper implements AnimationMapper {
  
  @Override
  public Animation getAnimation(Place parOldPlace, Place parNewPlace) {
    if (parNewPlace instanceof MainPlace) {
      MainPlace newPlace = (MainPlace)parNewPlace;
      MainPlace oldPlace = null;
      if (parOldPlace instanceof MainPlace) {
        oldPlace = (MainPlace)parOldPlace;
      }
      if (oldPlace == null) {
        return Animation.DISSOLVE_REVERSE;
      } else if (newPlace.getToken().equals(MainPlace.HOME)) {
        return Animation.SWAP_REVERSE;
      } else if (oldPlace.getToken().equals(MainPlace.THERAPY_EDIT) && newPlace.getToken().equals(MainPlace.THERAPY_LIST)) {
        return Animation.SWAP_REVERSE;
      } else if (oldPlace.getToken().equals(MainPlace.THERAPY_EDIT) && newPlace.getToken().equals(MainPlace.DOSAGE_EDIT)) {
        return Animation.SLIDE;
      } else if (oldPlace.getToken().equals(MainPlace.DOSAGE_EDIT) && newPlace.getToken().equals(MainPlace.THERAPY_EDIT)) {
        return Animation.SLIDE_REVERSE;
      }
      return Animation.SWAP;
    }
    return Animation.SLIDE;
  }
  
}
