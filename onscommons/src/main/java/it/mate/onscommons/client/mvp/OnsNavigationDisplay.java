package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.places.HasToken;
import it.mate.onscommons.client.ui.OnsNavigator;
import it.mate.onscommons.client.ui.OnsTemplate;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

public class OnsNavigationDisplay extends Composite {
  
  OnsActivityMapper mapper;
  
  List<OnsTemplate> templates;
  
  public OnsNavigationDisplay(OnsActivityMapper mapper) {
    this.mapper = mapper;
    
    OnsNavigator navigator = new OnsNavigator();
    
    /*
    OnsPage defaultPage = new OnsPage();
    OnsToolbar defaultToolbar = new OnsToolbar("<div class=\"center ons-initial-toolbar\">Initial Toolbar</div>");
    defaultPage.add(defaultToolbar);
    navigator.add(defaultPage);
    */
    
    initWidget(navigator);
    
    RootPanel.get().add(this);
    createTemplates();
    
  }
  
  protected void createTemplates() {
    templates = new ArrayList<OnsTemplate>();
    String[] tokens = mapper.getTokenList();
    for (String token : tokens) {
      OnsTemplate template = new OnsTemplate(token);
      RootPanel.get().add(template);
      templates.add(template);
    }
  }
  
  public OnsTemplate getPlaceTemplate(HasToken place) {
    String token = place.getToken();
    for (OnsTemplate template : templates) {
      if (template.getToken().equals(token)) {
        return template;
      }
    }
    return null;
  }


}
