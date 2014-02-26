package it.mate.postscriptum.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class HomeView_ViewUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, it.mate.postscriptum.client.view.HomeView>, it.mate.postscriptum.client.view.HomeView.ViewUiBinder {

  interface Template extends SafeHtmlTemplates {
    @Template("<span id='{0}'></span> <span id='{1}'></span> <span id='{2}'></span> <span id='{3}'></span>")
    SafeHtml html1(String arg0, String arg1, String arg2, String arg3);
     
  }

  Template template = GWT.create(Template.class);


  public com.google.gwt.user.client.ui.Widget createAndBindUi(final it.mate.postscriptum.client.view.HomeView owner) {


    return new Widgets(owner).get_wrapperPanel();
  }

  /**
   * Encapsulates the access to all inner widgets
   */
  class Widgets {
    private final it.mate.postscriptum.client.view.HomeView owner;


    final com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1 = new com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler() {
      public void onTouchEnd(com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent event) {
        owner.onMailListBtn(event);
      }
    };

    final com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames2 = new com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler() {
      public void onTouchEnd(com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent event) {
        owner.onNewMailBtn(event);
      }
    };

    final com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames3 = new com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler() {
      public void onTouchEnd(com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent event) {
        owner.onHelloBtn(event);
      }
    };

    public Widgets(final it.mate.postscriptum.client.view.HomeView owner) {
      this.owner = owner;
      build_domId0();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId1();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId2();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId3();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId0Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId1Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId2Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId3Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
    }

    SafeHtml template_html1() {
      return template.html1(get_domId0(), get_domId1(), get_domId2(), get_domId3());
    }

    /**
     * Getter for clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay called 0 times. Type: GENERATED_BUNDLE. Build precedence: 1.
     */
    private it.mate.postscriptum.client.view.HomeView_ViewUiBinderImpl_GenBundle get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      return build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay();
    }
    private it.mate.postscriptum.client.view.HomeView_ViewUiBinderImpl_GenBundle build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      // Creation section.
      final it.mate.postscriptum.client.view.HomeView_ViewUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (it.mate.postscriptum.client.view.HomeView_ViewUiBinderImpl_GenBundle) GWT.create(it.mate.postscriptum.client.view.HomeView_ViewUiBinderImpl_GenBundle.class);
      // Setup section.


      return clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay;
    }

    /**
     * Getter for wrapperPanel called 1 times. Type: DEFAULT. Build precedence: 1.
     */
    private com.google.gwt.user.client.ui.HTMLPanel get_wrapperPanel() {
      return build_wrapperPanel();
    }
    private com.google.gwt.user.client.ui.HTMLPanel build_wrapperPanel() {
      // Creation section.
      final com.google.gwt.user.client.ui.HTMLPanel wrapperPanel = new com.google.gwt.user.client.ui.HTMLPanel(template_html1().asString());
      // Setup section.
      wrapperPanel.addStyleName("ui-homePanel");

      // Attach section.
      UiBinderUtil.TempAttachment attachRecord0 = UiBinderUtil.attachToDom(wrapperPanel.getElement());
      get_domId0Element().get();
      get_domId1Element().get();
      get_domId2Element().get();
      get_domId3Element().get();

      // Detach section.
      attachRecord0.detach();
      wrapperPanel.addAndReplaceElement(get_f_Spacer1(), get_domId0Element().get());
      wrapperPanel.addAndReplaceElement(get_f_HorizontalPanel2(), get_domId1Element().get());
      wrapperPanel.addAndReplaceElement(get_homeLbl(), get_domId2Element().get());
      wrapperPanel.addAndReplaceElement(get_firstRunPanel(), get_domId3Element().get());

      owner.wrapperPanel = wrapperPanel;

      return wrapperPanel;
    }

    /**
     * Getter for domId0 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId0;
    private java.lang.String get_domId0() {
      return domId0;
    }
    private java.lang.String build_domId0() {
      // Creation section.
      domId0 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId0;
    }

    /**
     * Getter for f_Spacer1 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer1() {
      return build_f_Spacer1();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer1() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer1 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer1.addStyleName("ui-homeSpacer1");


      return f_Spacer1;
    }

    /**
     * Getter for domId0Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId0Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId0Element() {
      return domId0Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId0Element() {
      // Creation section.
      domId0Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId0());
      // Setup section.


      return domId0Element;
    }

    /**
     * Getter for domId1 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId1;
    private java.lang.String get_domId1() {
      return domId1;
    }
    private java.lang.String build_domId1() {
      // Creation section.
      domId1 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId1;
    }

    /**
     * Getter for f_HorizontalPanel2 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel2() {
      return build_f_HorizontalPanel2();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel2() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel2 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel2.setHorizontalAlignment(com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER);
      f_HorizontalPanel2.add(get_mailListBtn2());
      f_HorizontalPanel2.add(get_newMailBtn2());
      f_HorizontalPanel2.setWidth("100%");


      return f_HorizontalPanel2;
    }

    /**
     * Getter for mailListBtn2 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.phgcommons.client.ui.TouchAnchor get_mailListBtn2() {
      return build_mailListBtn2();
    }
    private it.mate.phgcommons.client.ui.TouchAnchor build_mailListBtn2() {
      // Creation section.
      final it.mate.phgcommons.client.ui.TouchAnchor mailListBtn2 = (it.mate.phgcommons.client.ui.TouchAnchor) GWT.create(it.mate.phgcommons.client.ui.TouchAnchor.class);
      // Setup section.
      mailListBtn2.addStyleName("ui-scheduled-mails-btn");
      mailListBtn2.setText("Scheduled Mails");
      mailListBtn2.addTouchEndHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1);


      return mailListBtn2;
    }

    /**
     * Getter for newMailBtn2 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.phgcommons.client.ui.TouchAnchor get_newMailBtn2() {
      return build_newMailBtn2();
    }
    private it.mate.phgcommons.client.ui.TouchAnchor build_newMailBtn2() {
      // Creation section.
      final it.mate.phgcommons.client.ui.TouchAnchor newMailBtn2 = (it.mate.phgcommons.client.ui.TouchAnchor) GWT.create(it.mate.phgcommons.client.ui.TouchAnchor.class);
      // Setup section.
      newMailBtn2.addStyleName("ui-new-mail-btn");
      newMailBtn2.setText("New Mail");
      newMailBtn2.addTouchEndHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames2);


      return newMailBtn2;
    }

    /**
     * Getter for domId1Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId1Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId1Element() {
      return domId1Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId1Element() {
      // Creation section.
      domId1Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId1());
      // Setup section.


      return domId1Element;
    }

    /**
     * Getter for domId2 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId2;
    private java.lang.String get_domId2() {
      return domId2;
    }
    private java.lang.String build_domId2() {
      // Creation section.
      domId2 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId2;
    }

    /**
     * Getter for homeLbl called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_homeLbl() {
      return build_homeLbl();
    }
    private com.google.gwt.user.client.ui.Label build_homeLbl() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label homeLbl = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      homeLbl.addStyleName("ui-homeLbl");


      owner.homeLbl = homeLbl;

      return homeLbl;
    }

    /**
     * Getter for domId2Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId2Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId2Element() {
      return domId2Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId2Element() {
      // Creation section.
      domId2Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId2());
      // Setup section.


      return domId2Element;
    }

    /**
     * Getter for domId3 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId3;
    private java.lang.String get_domId3() {
      return domId3;
    }
    private java.lang.String build_domId3() {
      // Creation section.
      domId3 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId3;
    }

    /**
     * Getter for firstRunPanel called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.gwtcommons.client.ui.SimpleContainer get_firstRunPanel() {
      return build_firstRunPanel();
    }
    private it.mate.gwtcommons.client.ui.SimpleContainer build_firstRunPanel() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.SimpleContainer firstRunPanel = (it.mate.gwtcommons.client.ui.SimpleContainer) GWT.create(it.mate.gwtcommons.client.ui.SimpleContainer.class);
      // Setup section.
      firstRunPanel.add(get_f_SimpleContainer3());
      firstRunPanel.add(get_f_SimpleContainer4());
      firstRunPanel.addStyleName("ui-firstRunPanel");


      owner.firstRunPanel = firstRunPanel;

      return firstRunPanel;
    }

    /**
     * Getter for f_SimpleContainer3 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.SimpleContainer get_f_SimpleContainer3() {
      return build_f_SimpleContainer3();
    }
    private it.mate.gwtcommons.client.ui.SimpleContainer build_f_SimpleContainer3() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.SimpleContainer f_SimpleContainer3 = (it.mate.gwtcommons.client.ui.SimpleContainer) GWT.create(it.mate.gwtcommons.client.ui.SimpleContainer.class);
      // Setup section.
      f_SimpleContainer3.add(get_firstRunTitleLbl());
      f_SimpleContainer3.addStyleName("ui-firstRunPanel-top");


      return f_SimpleContainer3;
    }

    /**
     * Getter for firstRunTitleLbl called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.user.client.ui.Label get_firstRunTitleLbl() {
      return build_firstRunTitleLbl();
    }
    private com.google.gwt.user.client.ui.Label build_firstRunTitleLbl() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label firstRunTitleLbl = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.


      owner.firstRunTitleLbl = firstRunTitleLbl;

      return firstRunTitleLbl;
    }

    /**
     * Getter for f_SimpleContainer4 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.SimpleContainer get_f_SimpleContainer4() {
      return build_f_SimpleContainer4();
    }
    private it.mate.gwtcommons.client.ui.SimpleContainer build_f_SimpleContainer4() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.SimpleContainer f_SimpleContainer4 = (it.mate.gwtcommons.client.ui.SimpleContainer) GWT.create(it.mate.gwtcommons.client.ui.SimpleContainer.class);
      // Setup section.
      f_SimpleContainer4.add(get_f_Spacer5());
      f_SimpleContainer4.add(get_firstRunLbl());
      f_SimpleContainer4.add(get_helloBtn());
      f_SimpleContainer4.add(get_f_Spacer6());
      f_SimpleContainer4.addStyleName("ui-firstRunPanel-content");


      return f_SimpleContainer4;
    }

    /**
     * Getter for f_Spacer5 called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer5() {
      return build_f_Spacer5();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer5() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer5 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer5.addStyleName("ui-firstRunPanel-content-spacer");
      f_Spacer5.setHeight("1px");


      return f_Spacer5;
    }

    /**
     * Getter for firstRunLbl called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private com.google.gwt.user.client.ui.HTML get_firstRunLbl() {
      return build_firstRunLbl();
    }
    private com.google.gwt.user.client.ui.HTML build_firstRunLbl() {
      // Creation section.
      final com.google.gwt.user.client.ui.HTML firstRunLbl = (com.google.gwt.user.client.ui.HTML) GWT.create(com.google.gwt.user.client.ui.HTML.class);
      // Setup section.
      firstRunLbl.addStyleName("ui-firstRunLbl");


      owner.firstRunLbl = firstRunLbl;

      return firstRunLbl;
    }

    /**
     * Getter for helloBtn called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private it.mate.phgcommons.client.ui.TouchHTML get_helloBtn() {
      return build_helloBtn();
    }
    private it.mate.phgcommons.client.ui.TouchHTML build_helloBtn() {
      // Creation section.
      final it.mate.phgcommons.client.ui.TouchHTML helloBtn = (it.mate.phgcommons.client.ui.TouchHTML) GWT.create(it.mate.phgcommons.client.ui.TouchHTML.class);
      // Setup section.
      helloBtn.addStyleName("ui-firstRunLink");
      helloBtn.setText("login");
      helloBtn.addTouchEndHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames3);


      return helloBtn;
    }

    /**
     * Getter for f_Spacer6 called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer6() {
      return build_f_Spacer6();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer6() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer6 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer6.setHeight("1.2em");


      return f_Spacer6;
    }

    /**
     * Getter for domId3Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId3Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId3Element() {
      return domId3Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId3Element() {
      // Creation section.
      domId3Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId3());
      // Setup section.


      return domId3Element;
    }
  }
}
