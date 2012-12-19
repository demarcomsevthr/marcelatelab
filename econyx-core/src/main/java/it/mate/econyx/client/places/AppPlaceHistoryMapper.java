package it.mate.econyx.client.places;


import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers ( {
  ProductPlace.Tokenizer.class,
  OrderPlace.Tokenizer.class,
  GeneralPlace.Tokenizer.class
} )
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
