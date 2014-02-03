package it.mate.stickmail.server.endpoint;

import it.mate.stickmail.shared.model.EndpointResponse;
import it.mate.stickmail.shared.model.RemoteUser;

import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;

/**
 *  ATTENZIONE
 *  
 *  TIPS & TRICKS
 *  
 *      - NEL NOME DEL SERVER NON CI DEVONO ESSERE CARATTERI "STRANI" 
 *        (Es: stickmail-server.appspot.com -> -) [01/02/2014]
 *      
 *      - NON FUNZIONANO I METODI CHE INIZIANO CON GET (DA 404)
 *        NON HO PROVATO IL METODO POST... (ma potrebbe non funzionare analogamente)
 *        (sembra anche ininfluente l'utilizza della property name dell'annotation)
 *        
 *      
 *      
 * 
 *  LINK PER I TEST
 *  
 *    API EXPLORER:
 *    
 *      https://stickmailsrv.appspot.com/_ah/api/explorer
 *  
 *    IN LOCALE:
 *  
 *      http://127.0.0.1:8080/_ah/api/stickmailEP/v1/buildNumber
 *  
 *      (in locale non funziona l'api explorer!)
 * 
 *    SU APPSPOT:
 *      RICORDARSI DI ANDARE SEMPRE IN HTTPS !!!
 *  
 *      https://stickmailsrv.appspot.com/_ah/api/stickmailEP/v1/buildNumber
 *  
 *  
 *
 */



@Api (name="stickmailEP", version="v1", description="Stick Mail API", clientIds={
    com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID,
    "834127897640.apps.googleusercontent.com",
    "834127897640-pqkmabsf4d4ichgs0cvuqq0fnhf36rjh.apps.googleusercontent.com"
})
public class StickMailEndpoint {

  private static Logger logger = Logger.getLogger(StickMailEndpoint.class.getName());

  @ApiMethod (name="getBuildNumber", httpMethod=HttpMethod.GET)
  public EndpointResponse getBuildNumber() {
    logger.info("request buildNumber");
    EndpointResponse response = EndpointResponse.OK;
    response.setPayload("SERVICE IS ACTIVE");
    response.setBuildNumber("1002");
    return response;
  }

  @ApiMethod (name="getRemoteUser", httpMethod=HttpMethod.POST)
  public RemoteUser getRemoteUser(@Named("token") String token, User googleUser) {
    logger.info("request authUser");
    if (googleUser == null) {
      logger.info("USER NULL!");
      return RemoteUser.NULL_USER;
    }
    RemoteUser remoteUser = new RemoteUser();
    remoteUser.setEmail(googleUser.getEmail());
    remoteUser.setUserId(googleUser.getUserId());
    remoteUser.setNickname(googleUser.getNickname());
    remoteUser.setAuthDomain(googleUser.getAuthDomain());
    remoteUser.setFederatedIdentity(googleUser.getFederatedIdentity());
    return remoteUser;
  }
  
  /*
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
  
  */
  
}
