Ogn volta che si modificano/aggiungono classi di questo package

la it.mate.econyx.server.services.impl.PortalServiceAdapterImpl

potrebbe dare problemi legati al ClassLoader nel riconoscere le

classi delle portlet.


  POSSIBILI WORKAROUND
  
1) fare un clean del workspace da eclipse

2) provare a ripristinare l'elenco fisso delle classi portlet in PortalServiceAdapterImpl

