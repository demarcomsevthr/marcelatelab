package it.mate.protons.client.places;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers ({
  IngredientPlace.Tokenizer.class 
})
public interface IngredientPlaceHistoryMapper extends PlaceHistoryMapper {

}
