package it.mate.ckd.client.activities.mapper;

import it.mate.ckd.client.places.MainPlace;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

public class MainAnimationMapper implements AnimationMapper {

  @Override
  public Animation getAnimation(Place oldPlace, Place newPlace) {
    if (newPlace instanceof MainPlace && oldPlace instanceof MainPlace) {
      MainPlace newMainPlace = (MainPlace)newPlace;
      MainPlace oldMainPlace = (MainPlace)oldPlace;
      if (newMainPlace.getToken().equals(MainPlace.HOME)) {
        if (oldMainPlace == null || oldMainPlace.getToken().equals(MainPlace.HOME)) {
          return null;
        }
        return Animation.SLIDE_REVERSE;
      }
      if (oldMainPlace.getToken().equals(MainPlace.CKD_OUTPUT) && 
          newMainPlace.getToken().equals(MainPlace.CKD_INPUT)) {
        return Animation.SLIDE_REVERSE;
      }
      if (oldMainPlace.getToken().equals(MainPlace.CKD_OUTPUT_HELP) && 
          newMainPlace.getToken().equals(MainPlace.CKD_OUTPUT)) {
        return Animation.SLIDE_REVERSE;
      }
    }
    return Animation.SLIDE;
  }
  
}
