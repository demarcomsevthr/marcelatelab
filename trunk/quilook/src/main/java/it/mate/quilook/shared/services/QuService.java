package it.mate.quilook.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".quService")
public interface QuService extends RemoteService {
  
  public String getQuMessage();

}
