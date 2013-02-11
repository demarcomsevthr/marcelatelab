package it.mate.abaco.client.activities.mapper;

import it.mate.abaco.client.places.MainPlace;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

public class MainAnimationMapper implements AnimationMapper {

  @Override
  public Animation getAnimation(Place oldPlace, Place newPlace) {
    
    if (newPlace instanceof MainPlace) {
      MainPlace mainPlace = (MainPlace)newPlace;
      if (mainPlace.getToken().equals(MainPlace.HOME)) {
        return Animation.SLIDE_REVERSE;
      }
    }
    
    return Animation.SLIDE;
//  return null;
  }
  
}
