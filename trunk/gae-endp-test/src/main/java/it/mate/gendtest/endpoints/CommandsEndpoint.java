package it.mate.gendtest.endpoints;

import it.mate.gendtest.model.Command;
import it.mate.gendtest.services.AdapterUtil;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api (name="commands", version="v1", description="Commands API - build 1")
public class CommandsEndpoint {
  
  @ApiMethod (name="getCommand", httpMethod=HttpMethod.GET)
  public Command getCommand(@Named("id") Integer id) {
    return AdapterUtil.getAdapter().popPendingCommand("");
  }

  @ApiMethod (name="addAction", httpMethod=HttpMethod.PUT)
  public VoidResult addAction(@Named("action") Integer action) {
    AdapterUtil.getAdapter().setPendingAction(action);
    return VoidResult.VOID;
  }

}
