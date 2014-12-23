package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

public abstract class OnsActivityMapper extends BaseActivityMapper {

  @SuppressWarnings("rawtypes")
  public OnsActivityMapper(BaseClientFactory clientFactory) {
    super(clientFactory);
  }

  public abstract String[] getTokenList();

}
