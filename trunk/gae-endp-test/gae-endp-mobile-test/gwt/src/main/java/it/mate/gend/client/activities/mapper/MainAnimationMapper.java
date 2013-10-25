package it.mate.gend.client.activities.mapper;

import it.mate.gend.client.places.MainPlace;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

public class MainAnimationMapper implements AnimationMapper {

  @Override
  public Animation getAnimation(Place parOldPlace, Place parNewPlace) {
    if (parNewPlace instanceof MainPlace && parOldPlace instanceof MainPlace) {
      MainPlace newPlace = (MainPlace)parNewPlace;
      MainPlace oldPlace = (MainPlace)parOldPlace;
      if (newPlace.getToken().equals(MainPlace.HOME)) {
        if (oldPlace == null || oldPlace.getToken().equals(MainPlace.HOME)) {
          return null;
        }
        return Animation.SLIDE_REVERSE;
      }
    }
    return Animation.SLIDE;
  }
  
}
