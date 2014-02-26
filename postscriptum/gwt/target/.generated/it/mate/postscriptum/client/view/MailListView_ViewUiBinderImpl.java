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

public class MailListView_ViewUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, it.mate.postscriptum.client.view.MailListView>, it.mate.postscriptum.client.view.MailListView.ViewUiBinder {

  interface Template extends SafeHtmlTemplates {
    @Template("<span id='{0}'></span> <span id='{1}'></span> <span id='{2}'></span>")
    SafeHtml html1(String arg0, String arg1, String arg2);
     
  }

  Template template = GWT.create(Template.class);


  public com.google.gwt.user.client.ui.Widget createAndBindUi(final it.mate.postscriptum.client.view.MailListView owner) {


    return new Widgets(owner).get_wrapperPanel();
  }

  /**
   * Encapsulates the access to all inner widgets
   */
  class Widgets {
    private final it.mate.postscriptum.client.view.MailListView owner;


    final com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1 = new com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler() {
      public void onTouchEnd(com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent event) {
        owner.onDeleteBtn(event);
      }
    };

    public Widgets(final it.mate.postscriptum.client.view.MailListView owner) {
      this.owner = owner;
      build_domId0();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId1();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId2();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId0Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId1Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId2Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
    }

    SafeHtml template_html1() {
      return template.html1(get_domId0(), get_domId1(), get_domId2());
    }

    /**
     * Getter for clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay called 0 times. Type: GENERATED_BUNDLE. Build precedence: 1.
     */
    private it.mate.postscriptum.client.view.MailListView_ViewUiBinderImpl_GenBundle get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      return build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay();
    }
    private it.mate.postscriptum.client.view.MailListView_ViewUiBinderImpl_GenBundle build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      // Creation section.
      final it.mate.postscriptum.client.view.MailListView_ViewUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (it.mate.postscriptum.client.view.MailListView_ViewUiBinderImpl_GenBundle) GWT.create(it.mate.postscriptum.client.view.MailListView_ViewUiBinderImpl_GenBundle.class);
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
      UiBinderUtil.TempAttachment attachRecord2 = UiBinderUtil.attachToDom(wrapperPanel.getElement());
      get_domId0Element().get();
      get_domId1Element().get();
      get_domId2Element().get();

      // Detach section.
      attachRecord2.detach();
      wrapperPanel.addAndReplaceElement(get_signPanel(), get_domId0Element().get());
      wrapperPanel.addAndReplaceElement(get_deleteBtn(), get_domId1Element().get());
      wrapperPanel.addAndReplaceElement(get_resultsPanel(), get_domId2Element().get());

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
     * Getter for signPanel called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.postscriptum.client.ui.SignPanel get_signPanel() {
      return build_signPanel();
    }
    private it.mate.postscriptum.client.ui.SignPanel build_signPanel() {
      // Creation section.
      final it.mate.postscriptum.client.ui.SignPanel signPanel = (it.mate.postscriptum.client.ui.SignPanel) GWT.create(it.mate.postscriptum.client.ui.SignPanel.class);
      // Setup section.


      owner.signPanel = signPanel;

      return signPanel;
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
     * Getter for deleteBtn called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.phgcommons.client.ui.TouchButton get_deleteBtn() {
      return build_deleteBtn();
    }
    private it.mate.phgcommons.client.ui.TouchButton build_deleteBtn() {
      // Creation section.
      final it.mate.phgcommons.client.ui.TouchButton deleteBtn = (it.mate.phgcommons.client.ui.TouchButton) GWT.create(it.mate.phgcommons.client.ui.TouchButton.class);
      // Setup section.
      deleteBtn.addStyleName("ui-delete-btn");
      deleteBtn.setText("Delete selected mails");
      deleteBtn.setVisible(false);
      deleteBtn.addTouchEndHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1);


      owner.deleteBtn = deleteBtn;

      return deleteBtn;
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
     * Getter for resultsPanel called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.googlecode.mgwt.ui.client.widget.ScrollPanel get_resultsPanel() {
      return build_resultsPanel();
    }
    private com.googlecode.mgwt.ui.client.widget.ScrollPanel build_resultsPanel() {
      // Creation section.
      final com.googlecode.mgwt.ui.client.widget.ScrollPanel resultsPanel = (com.googlecode.mgwt.ui.client.widget.ScrollPanel) GWT.create(com.googlecode.mgwt.ui.client.widget.ScrollPanel.class);
      // Setup section.
      resultsPanel.addStyleName("ui-results-panel");


      owner.resultsPanel = resultsPanel;

      return resultsPanel;
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
  }
}
