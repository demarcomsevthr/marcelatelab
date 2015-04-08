package it.mate.copymob.server.services;

import it.mate.commons.server.utils.LoggingUtils;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.service.AdminFacade;
import it.mate.copymob.shared.service.FacadeException;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class AdminFacadeImpl extends RemoteServiceServlet implements AdminFacade {

  private MainAdapter adapter = null;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdapterUtil.initContext(config.getServletContext());
    adapter = AdapterUtil.getMainAdapter();
  }

  @Override
  public List<Order> findAllOrders() throws FacadeException {
    try {
      LoggingUtils.debug(getClass(), "before find");
      return adapter.findAllOrders();
    } catch (Exception ex) {
      LoggingUtils.error(getClass(), "errore", ex);
      throw new FacadeException(ex.getMessage(), ex);
    }
  }

  @Override
  public Order findOrderById(String id) throws FacadeException {
    try {
      return adapter.findOrderById(id);
    } catch (Exception ex) {
      LoggingUtils.error(getClass(), "errore", ex);
      throw new FacadeException(ex.getMessage(), ex);
    }
  }
  
  public Order saveOrder(Order order) throws FacadeException {
    try {
      return adapter.saveOrder(order);
    } catch (Exception ex) {
      LoggingUtils.error(getClass(), "errore", ex);
      throw new FacadeException(ex.getMessage(), ex);
    }
  }

}
