package it.mate.econyx.shared.services;

import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(".portalUserService")
public interface PortalUserService extends RemoteService {

  public PortalUser update(PortalUser entity) throws ServiceException;

  public PortalUser create(PortalUser entity) throws ServiceException;

  public PortalUser findById(String id) throws ServiceException;
  
  public List<PortalUser> findAll() throws ServiceException;
  
  public PortalUser login(PortalUser portalUser, boolean keepConnection) throws ServiceException;
  
  public void logout(PortalUser portalUser);

  public void sendActivationMail (PortalUser user);
  
  public PortalUser updateUserNotActive (PortalUser user);
  
  public String getGoogleLoginURL(String redirectURL);
  
  public String getGoogleLogoutURL(String redirectURL);
  
  public PortalUser updatePassword(PortalUser portalUser, String passwordAttuale, String nuovaPassword, String confermaPassword) throws ServiceException;
  
  public PortalUser activateUserById (String id);
  
  public Double getSaldoByPortalUserId(String portalUserId);
  
}
