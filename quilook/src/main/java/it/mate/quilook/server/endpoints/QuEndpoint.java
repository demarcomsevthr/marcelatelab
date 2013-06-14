package it.mate.quilook.server.endpoints;

import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.model.PortalUser;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api (
  name="qu",
  version="v1" /* ,
  description="Qu Api Test",
  scopes={"ss0", "ss1"},
  defaultVersion=AnnotationBoolean.TRUE */
)
public class QuEndpoint {
  
  private static Logger logger = Logger.getLogger(QuEndpoint.class);
  
  @ApiMethod (name="getMessage", httpMethod=HttpMethod.GET, path="message")
  public QuMessage getQuMessage() {
    List<PortalUser> users = AdaptersUtil.getPortalUserAdapter().findAll();
    String msg = String.format("trovati %s utenti", users.size());
    logger.debug(msg);
    return new QuMessage(msg);
  }

}
