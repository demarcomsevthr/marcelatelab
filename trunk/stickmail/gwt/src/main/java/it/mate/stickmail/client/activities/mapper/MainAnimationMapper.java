package it.mate.stickmail.client.activities.mapper;

import it.mate.stickmail.client.places.MainPlace;

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
      if (newPlace != null && oldPlace != null) {
        if (oldPlace.getToken().equals(MainPlace.NEW_MAIL) && newPlace.getToken().equals(MainPlace.HOME)) {
          return Animation.SWAP_REVERSE;
        }
        if (oldPlace.getToken().equals(MainPlace.MAIL_LIST) && newPlace.getToken().equals(MainPlace.HOME)) {
          return Animation.SWAP_REVERSE;
        }
      } else if (oldPlace == null) {
        return null;
      }
      return Animation.SWAP;
    }
    return Animation.SLIDE;
  }
  
}
