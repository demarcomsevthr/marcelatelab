package it.mate.ckd.client.view;

public class HomeView_ViewUiBinderImpl_TemplateImpl implements it.mate.ckd.client.view.HomeView_ViewUiBinderImpl.Template {
  
  public com.google.gwt.safehtml.shared.SafeHtml html1(com.google.gwt.safehtml.shared.SafeHtml arg0) {
    StringBuilder sb = new java.lang.StringBuilder();
    sb.append("<div>");
    sb.append(arg0.asString());
    sb.append("</div>");
return new com.google.gwt.safehtml.shared.OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml(sb.toString());
}

public com.google.gwt.safehtml.shared.SafeHtml html2(java.lang.String arg0) {
StringBuilder sb = new java.lang.StringBuilder();
sb.append("<div class='");
sb.append(com.google.gwt.safehtml.shared.SafeHtmlUtils.htmlEscape(arg0));
sb.append("' id='debugDiv'></div>");
return new com.google.gwt.safehtml.shared.OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml(sb.toString());
}

public com.google.gwt.safehtml.shared.SafeHtml html3(java.lang.String arg0) {
StringBuilder sb = new java.lang.StringBuilder();
sb.append("<span id='");
sb.append(com.google.gwt.safehtml.shared.SafeHtmlUtils.htmlEscape(arg0));
sb.append("'></span>");
return new com.google.gwt.safehtml.shared.OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml(sb.toString());
}
}
