package it.mate.gpg.client.places;

import com.google.gwt.place.impl.AbstractPlaceHistoryMapper;
import it.mate.gpg.client.places.MainPlaceHistoryMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.impl.AbstractPlaceHistoryMapper.PrefixAndToken;
import com.google.gwt.core.client.GWT;

public class MainPlaceHistoryMapperImpl extends AbstractPlaceHistoryMapper<Void> implements MainPlaceHistoryMapper {
  
  protected PrefixAndToken getPrefixAndToken(Place newPlace) {
    if (newPlace instanceof it.mate.gpg.client.places.HomePlace) {
      it.mate.gpg.client.places.HomePlace place = (it.mate.gpg.client.places.HomePlace) newPlace;
      PlaceTokenizer<it.mate.gpg.client.places.HomePlace> t = GWT.create(it.mate.gpg.client.places.HomePlace.Tokenizer.class);
      return new PrefixAndToken("HomePlace", t.getToken((it.mate.gpg.client.places.HomePlace) place));
    }
    return null;
  }
  
  protected PlaceTokenizer<?> getTokenizer(String prefix) {
    if ("HomePlace".equals(prefix)) {
      return GWT.create(it.mate.gpg.client.places.HomePlace.Tokenizer.class);
    }
    return null;
  }

}
