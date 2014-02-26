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

public class NewMailView_ViewUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, it.mate.postscriptum.client.view.NewMailView>, it.mate.postscriptum.client.view.NewMailView.ViewUiBinder {

  interface Template extends SafeHtmlTemplates {
    @Template("<span id='{0}'></span> <span id='{1}'></span> <span id='{2}'></span> <span id='{3}'></span> <span id='{4}'></span> <span id='{5}'></span>")
    SafeHtml html1(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5);
     
  }

  Template template = GWT.create(Template.class);


  public com.google.gwt.user.client.ui.Widget createAndBindUi(final it.mate.postscriptum.client.view.NewMailView owner) {


    return new Widgets(owner).get_wrapperPanel();
  }

  /**
   * Encapsulates the access to all inner widgets
   */
  class Widgets {
    private final it.mate.postscriptum.client.view.NewMailView owner;


    final com.google.gwt.event.logical.shared.ValueChangeHandler<java.util.Date> handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1 = new com.google.gwt.event.logical.shared.ValueChangeHandler<java.util.Date>() {
      public void onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent<java.util.Date> event) {
        owner.onCalChange(event);
      }
    };

    final com.google.gwt.event.logical.shared.ValueChangeHandler<it.mate.phgcommons.client.utils.Time> handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames2 = new com.google.gwt.event.logical.shared.ValueChangeHandler<it.mate.phgcommons.client.utils.Time>() {
      public void onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent<it.mate.phgcommons.client.utils.Time> event) {
        owner.onTimeChange(event);
      }
    };

    final com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames3 = new com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler() {
      public void onTouchEnd(com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent event) {
        owner.onNowBtn(event);
      }
    };

    final com.googlecode.mgwt.dom.client.event.tap.TapHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames4 = new com.googlecode.mgwt.dom.client.event.tap.TapHandler() {
      public void onTap(com.googlecode.mgwt.dom.client.event.tap.TapEvent event) {
        owner.onTouchBtn(event);
      }
    };

    public Widgets(final it.mate.postscriptum.client.view.NewMailView owner) {
      this.owner = owner;
      build_domId0();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId1();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId2();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId3();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId4();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId5();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId0Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId1Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId2Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId3Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId4Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId5Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
    }

    SafeHtml template_html1() {
      return template.html1(get_domId0(), get_domId1(), get_domId2(), get_domId3(), get_domId4(), get_domId5());
    }

    /**
     * Getter for clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay called 0 times. Type: GENERATED_BUNDLE. Build precedence: 1.
     */
    private it.mate.postscriptum.client.view.NewMailView_ViewUiBinderImpl_GenBundle get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      return build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay();
    }
    private it.mate.postscriptum.client.view.NewMailView_ViewUiBinderImpl_GenBundle build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      // Creation section.
      final it.mate.postscriptum.client.view.NewMailView_ViewUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (it.mate.postscriptum.client.view.NewMailView_ViewUiBinderImpl_GenBundle) GWT.create(it.mate.postscriptum.client.view.NewMailView_ViewUiBinderImpl_GenBundle.class);
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
      UiBinderUtil.TempAttachment attachRecord1 = UiBinderUtil.attachToDom(wrapperPanel.getElement());
      get_domId0Element().get();
      get_domId1Element().get();
      get_domId2Element().get();
      get_domId3Element().get();
      get_domId4Element().get();
      get_domId5Element().get();

      // Detach section.
      attachRecord1.detach();
      wrapperPanel.addAndReplaceElement(get_signPanel(), get_domId0Element().get());
      wrapperPanel.addAndReplaceElement(get_subjectBox(), get_domId1Element().get());
      wrapperPanel.addAndReplaceElement(get_bodyArea(), get_domId2Element().get());
      wrapperPanel.addAndReplaceElement(get_f_Label1(), get_domId3Element().get());
      wrapperPanel.addAndReplaceElement(get_f_HorizontalPanel2(), get_domId4Element().get());
      wrapperPanel.addAndReplaceElement(get_sendBtn(), get_domId5Element().get());

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
     * Getter for subjectBox called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.phgcommons.client.ui.MTextBoxPatched get_subjectBox() {
      return build_subjectBox();
    }
    private it.mate.phgcommons.client.ui.MTextBoxPatched build_subjectBox() {
      // Creation section.
      final it.mate.phgcommons.client.ui.MTextBoxPatched subjectBox = (it.mate.phgcommons.client.ui.MTextBoxPatched) GWT.create(it.mate.phgcommons.client.ui.MTextBoxPatched.class);
      // Setup section.
      subjectBox.addStyleName("ui-subjectBox");


      owner.subjectBox = subjectBox;

      return subjectBox;
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
     * Getter for bodyArea called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.googlecode.mgwt.ui.client.widget.MTextArea get_bodyArea() {
      return build_bodyArea();
    }
    private com.googlecode.mgwt.ui.client.widget.MTextArea build_bodyArea() {
      // Creation section.
      final com.googlecode.mgwt.ui.client.widget.MTextArea bodyArea = (com.googlecode.mgwt.ui.client.widget.MTextArea) GWT.create(com.googlecode.mgwt.ui.client.widget.MTextArea.class);
      // Setup section.
      bodyArea.addStyleName("ui-bodyArea");


      owner.bodyArea = bodyArea;

      return bodyArea;
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
     * Getter for f_Label1 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label1() {
      return build_f_Label1();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label1() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label1 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label1.addStyleName("ui-signLbl");
      f_Label1.setText("Schedule on:");


      return f_Label1;
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

    /**
     * Getter for domId4 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId4;
    private java.lang.String get_domId4() {
      return domId4;
    }
    private java.lang.String build_domId4() {
      // Creation section.
      domId4 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId4;
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
      f_HorizontalPanel2.add(get_calBox());
      f_HorizontalPanel2.add(get_timeBox());
      f_HorizontalPanel2.add(get_nowBtn());


      return f_HorizontalPanel2;
    }

    /**
     * Getter for calBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.phgcommons.client.ui.ph.PhCalendarBox get_calBox() {
      return build_calBox();
    }
    private it.mate.phgcommons.client.ui.ph.PhCalendarBox build_calBox() {
      // Creation section.
      final it.mate.phgcommons.client.ui.ph.PhCalendarBox calBox = (it.mate.phgcommons.client.ui.ph.PhCalendarBox) GWT.create(it.mate.phgcommons.client.ui.ph.PhCalendarBox.class);
      // Setup section.
      calBox.addStyleName("ui-calBox");
      calBox.addValueChangeHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1);


      owner.calBox = calBox;

      return calBox;
    }

    /**
     * Getter for timeBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.phgcommons.client.ui.ph.PhTimeBox get_timeBox() {
      return build_timeBox();
    }
    private it.mate.phgcommons.client.ui.ph.PhTimeBox build_timeBox() {
      // Creation section.
      final it.mate.phgcommons.client.ui.ph.PhTimeBox timeBox = (it.mate.phgcommons.client.ui.ph.PhTimeBox) GWT.create(it.mate.phgcommons.client.ui.ph.PhTimeBox.class);
      // Setup section.
      timeBox.addStyleName("ui-timeBox");
      timeBox.addValueChangeHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames2);


      owner.timeBox = timeBox;

      return timeBox;
    }

    /**
     * Getter for nowBtn called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.phgcommons.client.ui.TouchAnchor get_nowBtn() {
      return build_nowBtn();
    }
    private it.mate.phgcommons.client.ui.TouchAnchor build_nowBtn() {
      // Creation section.
      final it.mate.phgcommons.client.ui.TouchAnchor nowBtn = (it.mate.phgcommons.client.ui.TouchAnchor) GWT.create(it.mate.phgcommons.client.ui.TouchAnchor.class);
      // Setup section.
      nowBtn.addStyleName("ui-nowBtn");
      nowBtn.setText("NOW");
      nowBtn.addTouchEndHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames3);


      return nowBtn;
    }

    /**
     * Getter for domId4Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId4Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId4Element() {
      return domId4Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId4Element() {
      // Creation section.
      domId4Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId4());
      // Setup section.


      return domId4Element;
    }

    /**
     * Getter for domId5 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId5;
    private java.lang.String get_domId5() {
      return domId5;
    }
    private java.lang.String build_domId5() {
      // Creation section.
      domId5 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId5;
    }

    /**
     * Getter for sendBtn called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.phgcommons.client.ui.TouchButton get_sendBtn() {
      return build_sendBtn();
    }
    private it.mate.phgcommons.client.ui.TouchButton build_sendBtn() {
      // Creation section.
      final it.mate.phgcommons.client.ui.TouchButton sendBtn = (it.mate.phgcommons.client.ui.TouchButton) GWT.create(it.mate.phgcommons.client.ui.TouchButton.class);
      // Setup section.
      sendBtn.addStyleName("ui-sendBtn");
      sendBtn.setText("POST THIS MAIL");
      sendBtn.addTapHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames4);


      owner.sendBtn = sendBtn;

      return sendBtn;
    }

    /**
     * Getter for domId5Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId5Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId5Element() {
      return domId5Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId5Element() {
      // Creation section.
      domId5Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId5());
      // Setup section.


      return domId5Element;
    }
  }
}
