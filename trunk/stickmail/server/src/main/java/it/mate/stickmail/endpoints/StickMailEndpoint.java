package it.mate.stickmail.endpoints;

import it.mate.stickmail.server.services.AdapterException;
import it.mate.stickmail.server.services.AdapterUtil;
import it.mate.stickmail.server.services.StickAdapter;
import it.mate.stickmail.shared.model.EndpointResponse;
import it.mate.stickmail.shared.model.MyAutoBeanFactory;
import it.mate.stickmail.shared.model.ServerTime;
import it.mate.stickmail.shared.model.StickMail;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;

/**
 * 
 *  NOTA BENE
 *  
 *  PER TEST IN LOCALE:
 *  
 *  http://127.0.0.1:8080/_ah/api/commandsAPI/v1/get/1
 *  (non funziona l'api explorer in locale)
 *  
 * 
 *  SU APPSPOT:
 *  RICORDARSI DI ANDARE SEMPRE IN https !!!
 *
 */



@Api (name="stickMailAPI", version="v1", description="Stick Mail API")
public class StickMailEndpoint {

  private static Logger logger = Logger.getLogger(StickMailEndpoint.class);
  
  private MyAutoBeanFactory factory;
  
  private StickAdapter adapter;
  
  
  @PostConstruct
  public void onPostConstruct() {
    factory = AutoBeanFactorySource.create(MyAutoBeanFactory.class);
    adapter = AdapterUtil.getAdapter();
    logger.debug("initialized " + this);
  }
  
  
  @ApiMethod (name="getServerTime", httpMethod=HttpMethod.GET)
  public EndpointResponse getServerTime() {
    EndpointResponse response = EndpointResponse.OK;
    ServerTime time = new ServerTime.Impl(new Date());
    response.setPayload(AutoBeanCodex.encode(factory.getServerTime(time)).getPayload());
    return response;
  }
  
  
  @ApiMethod (name="postNewMail", httpMethod=HttpMethod.PUT)
  public EndpointResponse postNewMail(@Named("payload") String payload) {
    EndpointResponse response = EndpointResponse.OK;
    try {
      StickMail mail = AutoBeanCodex.decode(factory, StickMail.class, payload).as();
      adapter.postNewMail(mail);
    } catch (AdapterException ex) {
      response = EndpointResponse.ERROR;
    } catch (Exception ex) {
      response = EndpointResponse.ERROR;
    }
    return response;
  }

  
}
