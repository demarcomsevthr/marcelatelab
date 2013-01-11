package it.mate.econyx.server.services;

import it.mate.econyx.shared.model.PortalUser;

import java.util.List;

public interface PortalUserAdapter {
  
  public PortalUser update(PortalUser entity);

  public PortalUser create(PortalUser entity);

  public PortalUser create(PortalUser user, boolean fromMarshaller);

  public PortalUser findById(String id);

  public List<PortalUser> findAll();
  
  public void delete(PortalUser entity);
  
  public PortalUser login(PortalUser portalUser);
  
  public PortalUser updateUserNotActive (PortalUser user);
  
  public PortalUser activateUserByToken (String activationToken);
  
  public PortalUser findByEmail(String email);
  
  public PortalUser updatePassword(PortalUser portalUser, String passwordAttuale, String nuovaPassword, String confermaPassword);
  
}
