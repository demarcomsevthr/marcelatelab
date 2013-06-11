package it.mate.quilook.server.endpoints;

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
  
  @ApiMethod (name="getMessage", httpMethod=HttpMethod.GET, path="message")
  public QuMessage getQuMessage() {
    return new QuMessage("Hello Qu World!");
  }

}
