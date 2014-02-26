package it.mate.postscriptum.client.places;

import com.google.gwt.place.impl.AbstractPlaceHistoryMapper;
import it.mate.postscriptum.client.places.MainPlaceHistoryMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.impl.AbstractPlaceHistoryMapper.PrefixAndToken;
import com.google.gwt.core.client.GWT;

public class MainPlaceHistoryMapperImpl extends AbstractPlaceHistoryMapper<Void> implements MainPlaceHistoryMapper {
  
  protected PrefixAndToken getPrefixAndToken(Place newPlace) {
    if (newPlace instanceof it.mate.postscriptum.client.places.MainPlace) {
      it.mate.postscriptum.client.places.MainPlace place = (it.mate.postscriptum.client.places.MainPlace) newPlace;
      PlaceTokenizer<it.mate.postscriptum.client.places.MainPlace> t = GWT.create(it.mate.postscriptum.client.places.MainPlace.Tokenizer.class);
      return new PrefixAndToken("MainPlace", t.getToken((it.mate.postscriptum.client.places.MainPlace) place));
    }
    return null;
  }
  
  protected PlaceTokenizer<?> getTokenizer(String prefix) {
    if ("MainPlace".equals(prefix)) {
      return GWT.create(it.mate.postscriptum.client.places.MainPlace.Tokenizer.class);
    }
    return null;
  }

}
