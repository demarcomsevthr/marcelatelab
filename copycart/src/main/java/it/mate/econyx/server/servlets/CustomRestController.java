package it.mate.econyx.server.servlets;

import it.mate.econyx.server.model.impl.OrderItemStampDetailDs;
import it.mate.econyx.server.services.CustomAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomRestController {
  
  @Autowired CustomAdapter customAdapter;

  @RequestMapping ("/cu/oil/{id}")
  public void getOrderItemLogo(@PathVariable ("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
    OrderItemStampDetailDs detailDs = (OrderItemStampDetailDs)customAdapter.findOrderItemDetailDs(id, request.getSession());
    if (detailDs.getLogoAsBlob() != null && detailDs.getLogoAsBlob().getBytes() != null) {
      response.getOutputStream().write(detailDs.getLogoAsBlob().getBytes());
    }
  }
 
}
