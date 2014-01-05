package it.mate.stickmail.server.endpoint;

import it.mate.stickmail.server.services.AdapterException;
import it.mate.stickmail.server.services.AdapterUtil;
import it.mate.stickmail.server.services.StickAdapter;
import it.mate.stickmail.shared.model.EndpointResponse;
import it.mate.stickmail.shared.model.MyAutoBeanFactory;
import it.mate.stickmail.shared.model.ServerTime;
import it.mate.stickmail.shared.model.StickMail;

import java.util.Date;

import javax.inject.Named;

import org.apache.log4j.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;

/**
 * 
 *  ATTENZIONE
 *  
 *      - NON FUNZIONA LA @PostConstruct
 *  
 *      - NON FUNZIONANO I METODI CHE INIZIANO CON GET (DA 404)
 *        NON HO PROVATO IL METODO POST... (ma potrebbe non funzionare analogamente)
 *        (sembra anche ininfluente l'utilizza della property name dell'annotation)
 *      
 * 
 *  NOTA BENE PER I TEST
 *  
 *  IN LOCALE:
 *  
 *  http://127.0.0.1:8080/_ah/api/stickMailAPI/v1/get/1
 *  http://127.0.0.1:8080/_ah/api/stickMailAPI/v1/testService
 *  http://127.0.0.1:8080/_ah/api/stickMailAPI/v1/serverTime
 *  
 *  (in locale non funziona l'api explorer:
 *    http://localhost:8080/_ah/api/explorer
 *  )
 * 
 *  SU APPSPOT:
 *    RICORDARSI DI ANDARE SEMPRE IN HTTPS !!!
 *  
 *  https://stickmail-server.appspot.com/_ah/api/explorer
 *  https://stickmail-server.appspot.com/_ah/api/stickMailAPI/v1/testService
 *
 */



@Api (name="stickMailAPI", version="v1", description="Stick Mail API")
public class StickMailEndpoint {

  private static Logger logger = Logger.getLogger(StickMailEndpoint.class);
  
  private MyAutoBeanFactory factory;
  
  private StickAdapter adapter;
  
  private void ensureInstances() {
    boolean initOk = false;
    if (factory == null) {
      factory = AutoBeanFactorySource.create(MyAutoBeanFactory.class);
      initOk = true;
    }
    if (adapter == null) {
      adapter = AdapterUtil.getAdapter();
      initOk = true;
    }
    if (initOk) {
      logger.debug("initialized " + this);
    }
  }
  
  @ApiMethod (httpMethod=HttpMethod.GET)
  public EndpointResponse testService() {
    logger.debug("executing method");
    ensureInstances();
    EndpointResponse response = EndpointResponse.OK;
    response.setPayload("SERVICE IS ACTIVE");
    return response;
  }
  
  @ApiMethod (httpMethod=HttpMethod.GET)
  public EndpointResponse serverTime() {
    logger.debug("executing method");
    ensureInstances();
    EndpointResponse response = EndpointResponse.OK;
    ServerTime time = new ServerTime.Impl(new Date());
    response.setPayload(AutoBeanCodex.encode(factory.getServerTime(time)).getPayload());
    return response;
  }
  
  
  @ApiMethod (name="postNewMail", httpMethod=HttpMethod.PUT)
  public EndpointResponse postNewMail(@Named("payload") String payload) {
    ensureInstances();
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
