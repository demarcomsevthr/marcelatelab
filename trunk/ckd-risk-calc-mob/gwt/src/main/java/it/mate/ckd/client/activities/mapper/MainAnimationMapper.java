package it.mate.ckd.client.activities.mapper;

import it.mate.ckd.client.places.MainPlace;

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
      if (oldPlace.getToken().equals(MainPlace.CKD_OUTPUT) && 
          newPlace.getToken().equals(MainPlace.CKD_INPUT)) {
        return Animation.SLIDE_REVERSE;
      }
      if (oldPlace.getToken().equals(MainPlace.CKD_PROTOCOL_STEP) && 
          newPlace.getToken().equals(MainPlace.CKD_EXTENDED_VIEW)) {
        return Animation.SLIDE_REVERSE;
      }
      if (oldPlace.getToken().equals(MainPlace.CKD_PROTOCOL_STEP) && 
          newPlace.getToken().equals(MainPlace.CKD_OUTPUT)) {
        return Animation.SLIDE_REVERSE;
      }
      if (oldPlace.getToken().equals(MainPlace.CKD_EXTENDED_VIEW) && 
          newPlace.getToken().equals(MainPlace.CKD_OUTPUT)) {
        return Animation.SLIDE_REVERSE;
      }
    }
    return Animation.SLIDE;
  }
  
}
