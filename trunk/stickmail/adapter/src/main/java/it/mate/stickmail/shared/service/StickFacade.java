package it.mate.stickmail.shared.service;

import it.mate.stickmail.shared.model.RemoteUser;
import it.mate.stickmail.shared.model.StickMail;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(".stickFacade")
public interface StickFacade extends RemoteService {
  
  RemoteUser getRemoteUser();
  
  Date getServerTime();
  
  StickMail create(StickMail stickMail);
  
  List<StickMail> findMailsByUser(RemoteUser user);

}
