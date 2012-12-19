package it.mate.econyx.server.model;

import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.Articolo;
import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.UnitaDiMisura;

import java.util.ArrayList;
import java.util.List;

public class PortalPageXmlModel implements VisitContext {
  public List<Articolo> products = new ArrayList<Articolo>();
  public List<TipoArticolo> productTypes = new ArrayList<TipoArticolo>();
  public List<UnitaDiMisura> unitOfMeasures = new ArrayList<UnitaDiMisura>();
  public List<PortalPage> pages;
  public boolean createProducts = false;
}
