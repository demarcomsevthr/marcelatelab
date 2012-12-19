package it.mate.econyx.server.services.impl;

import it.mate.econyx.shared.model.ContoUtente;
import it.mate.econyx.shared.services.CustomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service ("customService")
public class CustomServiceImpl implements CustomService {
  
  @Autowired CustomAdapterImpl adapter;

  @Override
  public ContoUtente findContoUtenteByPortalUser(String portalUserId) {
    return adapter.findContoUtenteByPortalUser(portalUserId);
  }
  
  @Override
  public ContoUtente updateContoUtente(ContoUtente contoUtente) {
    return adapter.updateContoUtente(contoUtente);
  }
  
}
