package it.mate.econyx.server.services;

import it.mate.econyx.server.model.PortalDataExportModel;

public interface PortalDataExporter {

  public String unload();
  
  public PortalDataExportModel load ();
  
  public PortalDataExportModel load (String portalDataXml);

  /* 26/11/2012
  public PortalDataExportModel load (PortalDataExportModel xmlModel);
  public PortalDataExportModel load (File portalDataXml);
  */
  
  // 26/11/2012
//public void deleteAll();
  
}
