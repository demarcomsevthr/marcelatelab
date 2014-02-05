package it.mate.stickmail.shared.service;

import it.mate.stickmail.shared.model.RemoteUser;
import it.mate.stickmail.shared.model.StickMail;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(".stickFacade")
public interface StickFacade extends RemoteService {
  
  RemoteUser getRemoteUser();
  
  Date getServerTime();
  
  void createStickMail(StickMail stickMail);

}
