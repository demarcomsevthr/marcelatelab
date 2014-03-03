package it.mate.therapyreminder.shared.service;

import it.mate.therapyreminder.shared.model.RemoteUser;
import it.mate.therapyreminder.shared.model.StickMail;

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

  List<StickMail> findScheduledMailsByUser(RemoteUser user);
  
  void delete(List<StickMail> mails);

}
