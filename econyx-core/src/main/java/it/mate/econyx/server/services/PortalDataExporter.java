package it.mate.econyx.server.services;

import it.mate.econyx.server.model.PortalDataExportModel;

public interface PortalDataExporter {

  public String unload(int loadMethod);
  
  public PortalDataExportModel load ();
  
  public PortalDataExportModel load (String portalDataXml);

}
