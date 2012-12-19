package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.HtmlContent.Type;
import it.mate.econyx.shared.model.WebContentPage;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.WebContentPageDs")
public class WebContentPageTx extends PortalPageTx implements WebContentPage {

  List<? extends HtmlContent> htmls = new ArrayList<HtmlContentTx>();
  
  boolean htmlsInitialized = false;
  
  public List<HtmlContent> getHtmls() {
    return (List<HtmlContent>)htmls;
  }
  
  @CloneableProperty (targetClass=HtmlContentTx.class)
  public void setHtmls(List<HtmlContent> htmls) {
    this.htmls = htmls;
  }

  public boolean areHtmlsInitialized() {
    return htmlsInitialized;
  }
  
  public void setHtmlsInitialized(boolean htmlsInitialized) {
    this.htmlsInitialized = htmlsInitialized;
  }

  public void ensureHtmls() {
    if (htmls == null) {
      htmls = new ArrayList<HtmlContentTx>();
    }
  }
  
  public HtmlContent getHtmlContent(Type type) {
    ensureHtmls();
    for (HtmlContent html : htmls) {
      if (html.getType() == type) {
        return html;
      }
    }
    HtmlContent html = HtmlContent.Factory.createOnClient(this, type);
    addHtmlTx(html);
    return html;
  }
  
  private void addHtmlTx(HtmlContent html) {
    ((List<HtmlContentTx>)htmls).add((HtmlContentTx)html);
  }
  
}
