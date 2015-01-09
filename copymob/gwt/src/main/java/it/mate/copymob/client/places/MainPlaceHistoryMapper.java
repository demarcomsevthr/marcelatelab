package it.mate.copymob.client.places;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers ({
  MainPlace.Tokenizer.class 
})
public interface MainPlaceHistoryMapper extends PlaceHistoryMapper {

}
