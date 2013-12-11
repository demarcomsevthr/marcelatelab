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
      if (parOldPlace instanceof MainPlace) {
        MainPlace oldPlace = (MainPlace)parOldPlace;
      }
      return Animation.SWAP;
    }
    return Animation.SLIDE;
  }
  
}
