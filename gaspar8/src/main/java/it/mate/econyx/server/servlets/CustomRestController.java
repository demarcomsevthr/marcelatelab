package it.mate.econyx.server.servlets;

import it.mate.econyx.server.services.CustomAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomRestController {
  
  @Autowired CustomAdapter customAdapter;

  @RequestMapping ("/cu/sintesiPrepagato")
  public void sintesiPrepagato(HttpServletRequest request, HttpServletResponse response) throws Exception {
    byte[] content = customAdapter.exportPortalUsersToExcel();
    response.setContentType("application/x-ms-excel");
    response.setHeader("Content-Disposition", "attachment;filename=\"SintesiPrepagato.xls\"");
    response.getOutputStream().write(content);
  }
 
}
