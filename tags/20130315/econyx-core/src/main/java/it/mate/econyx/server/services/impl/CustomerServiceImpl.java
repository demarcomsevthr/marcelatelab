package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.CustomerAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.model.Customer;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.services.CustomerService;
import it.mate.gwtcommons.server.utils.HttpUtils;
import it.mate.gwtcommons.shared.services.ServiceException;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class CustomerServiceImpl extends RemoteServiceServlet implements CustomerService {
  
  private static Logger logger = Logger.getLogger(CustomerServiceImpl.class);
  
  private CustomerAdapter adapter;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getCustomerAdapter();
    logger.debug("initialized " + this);
  }

  @Override
  public List<Customer> findAll() {
    return adapter.findAll();
  }

  @Override
  public Customer update(Customer entity) {
    return adapter.update(entity);
  }

  @Override
  public void delete(Customer entity) {
    adapter.create(entity);
  }

  @Override
  public Customer create(Customer entity) {
    return adapter.create(entity);
  }

  @Override
  public Customer register(Customer entity) {
    Customer customer = adapter.register(entity);
    try {
      PortalUser user = customer.getPortalUser();
      HttpServletRequest request = getThreadLocalRequest();
      String activationUrl = HttpUtils.getContextUrl(request) + "/userActivation?tid=" + user.getActivationToken();
      logger.debug("activation url = " + activationUrl);
      if (PropertiesHolder.getBoolean("customerService.register.sendActivationMail")) {
        AdaptersUtil.getMailAdapter().sendActivationMail(user, activationUrl);
      }
    } catch (MessagingException ex) {
      throw new ServiceException(ex);
    }
    return customer;
  }
  
  public Customer findByPortalUser(PortalUser portalUser) {
    return adapter.findByPortalUser(portalUser);
  }

}
