package it.mate.ckd.client.view;

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

public class CKDInputView_ViewUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, it.mate.ckd.client.view.CKDInputView>, it.mate.ckd.client.view.CKDInputView.ViewUiBinder {
  static CKDInputViewViewUiBinderImplGenMessages messages = (CKDInputViewViewUiBinderImplGenMessages) GWT.create(CKDInputViewViewUiBinderImplGenMessages.class);

  interface Template extends SafeHtmlTemplates {
    @Template("<table cellpadding='0' cellspacing='0'> <tr> <td colspan='3'> <span id='{0}'></span> </td> </tr>  <tr> <td class='firstColumn'> <span id='{1}'></span> </td> <td class='secondColumn'> <span id='{2}'></span> </td> <td> <span id='{3}'></span> </td> </tr> <tr> <td class='firstColumn'> <span id='{4}'></span> </td> <td class='secondColumn'> <span id='{5}'></span> </td> <td> <span id='{6}'></span> </td> </tr> <tr> <td class='firstColumn'> <span id='{7}'></span> </td> <td class='secondColumn'> <span id='{8}'></span> </td> <td> <span id='{9}'></span> </td> </tr> <tr> <td class='firstColumn'> <span id='{10}'></span> </td> <td class='secondColumn'> <span id='{11}'></span> </td> <td> <span id='{12}'></span> </td> </tr> <tr> <td colspan='3'> <span id='{13}'></span> </td> </tr>  <tr> <td class='firstColumn' colspan='3'> <span id='{14}'></span> </td> </tr> <tr> <td colspan='3'> <span id='{15}'></span> </td> </tr>  <tr> <td class='firstColumn'> <span id='{16}'></span> </td> <td class='secondColumn'> <span id='{17}'></span> </td> <td> <span id='{18}'></span> </td> </tr> <tr> <td> <span id='{19}'></span> </td> </tr> <tr> <td colspan='3'> <span id='{20}'></span> </td> </tr> <tr> <td colspan='3'> <span id='{21}'></span> </td> </tr> </table>")
    SafeHtml html1(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14, String arg15, String arg16, String arg17, String arg18, String arg19, String arg20, String arg21);
     
  }

  Template template = GWT.create(Template.class);


  public com.google.gwt.user.client.ui.Widget createAndBindUi(final it.mate.ckd.client.view.CKDInputView owner) {


    return new Widgets(owner).get_f_HTMLPanel1();
  }

  /**
   * Encapsulates the access to all inner widgets
   */
  class Widgets {
    private final it.mate.ckd.client.view.CKDInputView owner;


    final com.google.gwt.event.dom.client.ClickHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1 = new com.google.gwt.event.dom.client.ClickHandler() {
      public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
        owner.onCreatinineUmAnc(event);
      }
    };

    final com.google.gwt.event.dom.client.ClickHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames2 = new com.google.gwt.event.dom.client.ClickHandler() {
      public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
        owner.onAlbuminUmAnc(event);
      }
    };

    final com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames3 = new com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler() {
      public void onTouchStart(com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent event) {
        owner.onCalcBtn(event);
      }
    };

    public Widgets(final it.mate.ckd.client.view.CKDInputView owner) {
      this.owner = owner;
      build_bundle();  // more than one getter call detected. Type: IMPORTED, precedence: 1
      build_style();  // more than one getter call detected. Type: IMPORTED, precedence: 1
      build_domId0();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId1();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId2();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId3();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId4();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId5();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId6();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId7();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId8();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId9();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId10();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId11();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId12();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId13();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId14();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId15();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId16();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId17();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId18();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId19();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId20();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId21();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId0Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId1Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId2Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId3Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId4Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId5Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId6Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId7Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId8Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId9Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId10Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId11Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId12Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId13Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId14Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId15Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId16Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId17Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId18Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId19Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId20Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
      build_domId21Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
    }

    SafeHtml template_html1() {
      return template.html1(get_domId0(), get_domId1(), get_domId2(), get_domId3(), get_domId4(), get_domId5(), get_domId6(), get_domId7(), get_domId8(), get_domId9(), get_domId10(), get_domId11(), get_domId12(), get_domId13(), get_domId14(), get_domId15(), get_domId16(), get_domId17(), get_domId18(), get_domId19(), get_domId20(), get_domId21());
    }

    /**
     * Getter for clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay called 0 times. Type: GENERATED_BUNDLE. Build precedence: 1.
     */
    private it.mate.ckd.client.view.CKDInputView_ViewUiBinderImpl_GenBundle get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      return build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay();
    }
    private it.mate.ckd.client.view.CKDInputView_ViewUiBinderImpl_GenBundle build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      // Creation section.
      final it.mate.ckd.client.view.CKDInputView_ViewUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (it.mate.ckd.client.view.CKDInputView_ViewUiBinderImpl_GenBundle) GWT.create(it.mate.ckd.client.view.CKDInputView_ViewUiBinderImpl_GenBundle.class);
      // Setup section.


      return clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay;
    }

    /**
     * Getter for bundle called 2 times. Type: IMPORTED. Build precedence: 1.
     */
    private it.mate.ckd.client.ui.theme.custom.MGWTCustomClientBundle bundle;
    private it.mate.ckd.client.ui.theme.custom.MGWTCustomClientBundle get_bundle() {
      return bundle;
    }
    private it.mate.ckd.client.ui.theme.custom.MGWTCustomClientBundle build_bundle() {
      // Creation section.
      bundle = owner.bundle;
      assert bundle != null : "UiField bundle with 'provided = true' was null";
      // Setup section.


      return bundle;
    }

    /**
     * Getter for style called 7 times. Type: IMPORTED. Build precedence: 1.
     */
    private it.mate.ckd.client.ui.theme.custom.CustomMainCss style;
    private it.mate.ckd.client.ui.theme.custom.CustomMainCss get_style() {
      return style;
    }
    private it.mate.ckd.client.ui.theme.custom.CustomMainCss build_style() {
      // Creation section.
      style = owner.style;
      assert style != null : "UiField style with 'provided = true' was null";
      // Setup section.


      return style;
    }

    /**
     * Getter for f_HTMLPanel1 called 1 times. Type: DEFAULT. Build precedence: 1.
     */
    private com.google.gwt.user.client.ui.HTMLPanel get_f_HTMLPanel1() {
      return build_f_HTMLPanel1();
    }
    private com.google.gwt.user.client.ui.HTMLPanel build_f_HTMLPanel1() {
      // Creation section.
      final com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel1 = new com.google.gwt.user.client.ui.HTMLPanel(template_html1().asString());
      // Setup section.
      f_HTMLPanel1.addStyleName("" + get_style().homePanel() + "");
      f_HTMLPanel1.addStyleName("" + get_style().inputPanel() + "");

      // Attach section.
      UiBinderUtil.TempAttachment attachRecord1 = UiBinderUtil.attachToDom(f_HTMLPanel1.getElement());
      get_domId0Element().get();
      get_domId1Element().get();
      get_domId2Element().get();
      get_domId3Element().get();
      get_domId4Element().get();
      get_domId5Element().get();
      get_domId6Element().get();
      get_domId7Element().get();
      get_domId8Element().get();
      get_domId9Element().get();
      get_domId10Element().get();
      get_domId11Element().get();
      get_domId12Element().get();
      get_domId13Element().get();
      get_domId14Element().get();
      get_domId15Element().get();
      get_domId16Element().get();
      get_domId17Element().get();
      get_domId18Element().get();
      get_domId19Element().get();
      get_domId20Element().get();
      get_domId21Element().get();

      // Detach section.
      attachRecord1.detach();
      f_HTMLPanel1.addAndReplaceElement(get_f_Spacer2(), get_domId0Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Label3(), get_domId1Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Label4(), get_domId2Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel5(), get_domId3Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Label7(), get_domId4Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_creatinineUmAnc(), get_domId5Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel8(), get_domId6Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Label10(), get_domId7Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Label11(), get_domId8Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel12(), get_domId9Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Label14(), get_domId10Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Label15(), get_domId11Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel16(), get_domId12Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Spacer18(), get_domId13Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel19(), get_domId14Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Spacer26(), get_domId15Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Label27(), get_domId16Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_albuminUmAnc(), get_domId17Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel28(), get_domId18Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Spacer30(), get_domId19Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel31(), get_domId20Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Spacer32(), get_domId21Element().get());

      return f_HTMLPanel1;
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
     * Getter for f_Spacer2 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer2() {
      return build_f_Spacer2();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer2() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer2 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer2.setHeight("0.6em");


      return f_Spacer2;
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
     * Getter for f_Label3 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label3() {
      return build_f_Label3();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label3() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label3 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label3.setText(messages.message1());


      return f_Label3;
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
     * Getter for f_Label4 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label4() {
      return build_f_Label4();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label4() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label4 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label4.setText(messages.message2());
      f_Label4.setWidth("50px");


      return f_Label4;
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
     * Getter for f_HorizontalPanel5 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel5() {
      return build_f_HorizontalPanel5();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel5() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel5 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel5.add(get_f_Spacer6());
      f_HorizontalPanel5.add(get_etaSpinBox());


      return f_HorizontalPanel5;
    }

    /**
     * Getter for f_Spacer6 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer6() {
      return build_f_Spacer6();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer6() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer6 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer6.addStyleName("" + get_style().boxSpacer() + "");


      return f_Spacer6;
    }

    /**
     * Getter for etaSpinBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.ckd.client.ui.SpinnerIntegerBox get_etaSpinBox() {
      return build_etaSpinBox();
    }
    private it.mate.ckd.client.ui.SpinnerIntegerBox build_etaSpinBox() {
      // Creation section.
      final it.mate.ckd.client.ui.SpinnerIntegerBox etaSpinBox = (it.mate.ckd.client.ui.SpinnerIntegerBox) GWT.create(it.mate.ckd.client.ui.SpinnerIntegerBox.class);
      // Setup section.
      etaSpinBox.setValue(50);


      owner.etaSpinBox = etaSpinBox;

      return etaSpinBox;
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
     * Getter for f_Label7 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label7() {
      return build_f_Label7();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label7() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label7 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label7.setText(messages.message3());


      return f_Label7;
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
     * Getter for creatinineUmAnc called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Anchor get_creatinineUmAnc() {
      return build_creatinineUmAnc();
    }
    private com.google.gwt.user.client.ui.Anchor build_creatinineUmAnc() {
      // Creation section.
      final com.google.gwt.user.client.ui.Anchor creatinineUmAnc = (com.google.gwt.user.client.ui.Anchor) GWT.create(com.google.gwt.user.client.ui.Anchor.class);
      // Setup section.
      creatinineUmAnc.setText(messages.message4());
      creatinineUmAnc.addClickHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1);


      owner.creatinineUmAnc = creatinineUmAnc;

      return creatinineUmAnc;
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

    /**
     * Getter for domId6 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId6;
    private java.lang.String get_domId6() {
      return domId6;
    }
    private java.lang.String build_domId6() {
      // Creation section.
      domId6 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId6;
    }

    /**
     * Getter for f_HorizontalPanel8 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel8() {
      return build_f_HorizontalPanel8();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel8() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel8 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel8.add(get_f_Spacer9());
      f_HorizontalPanel8.add(get_creatininaSpinBox());


      return f_HorizontalPanel8;
    }

    /**
     * Getter for f_Spacer9 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer9() {
      return build_f_Spacer9();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer9() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer9 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer9.addStyleName("" + get_style().boxSpacer() + "");


      return f_Spacer9;
    }

    /**
     * Getter for creatininaSpinBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.ckd.client.ui.SpinnerDoubleBox get_creatininaSpinBox() {
      return build_creatininaSpinBox();
    }
    private it.mate.ckd.client.ui.SpinnerDoubleBox build_creatininaSpinBox() {
      // Creation section.
      final it.mate.ckd.client.ui.SpinnerDoubleBox creatininaSpinBox = (it.mate.ckd.client.ui.SpinnerDoubleBox) GWT.create(it.mate.ckd.client.ui.SpinnerDoubleBox.class);
      // Setup section.


      owner.creatininaSpinBox = creatininaSpinBox;

      return creatininaSpinBox;
    }

    /**
     * Getter for domId6Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId6Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId6Element() {
      return domId6Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId6Element() {
      // Creation section.
      domId6Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId6());
      // Setup section.


      return domId6Element;
    }

    /**
     * Getter for domId7 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId7;
    private java.lang.String get_domId7() {
      return domId7;
    }
    private java.lang.String build_domId7() {
      // Creation section.
      domId7 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId7;
    }

    /**
     * Getter for f_Label10 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label10() {
      return build_f_Label10();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label10() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label10 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label10.setText(messages.message5());


      return f_Label10;
    }

    /**
     * Getter for domId7Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId7Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId7Element() {
      return domId7Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId7Element() {
      // Creation section.
      domId7Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId7());
      // Setup section.


      return domId7Element;
    }

    /**
     * Getter for domId8 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId8;
    private java.lang.String get_domId8() {
      return domId8;
    }
    private java.lang.String build_domId8() {
      // Creation section.
      domId8 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId8;
    }

    /**
     * Getter for f_Label11 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label11() {
      return build_f_Label11();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label11() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label11 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label11.setText(messages.message6());


      return f_Label11;
    }

    /**
     * Getter for domId8Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId8Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId8Element() {
      return domId8Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId8Element() {
      // Creation section.
      domId8Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId8());
      // Setup section.


      return domId8Element;
    }

    /**
     * Getter for domId9 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId9;
    private java.lang.String get_domId9() {
      return domId9;
    }
    private java.lang.String build_domId9() {
      // Creation section.
      domId9 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId9;
    }

    /**
     * Getter for f_HorizontalPanel12 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel12() {
      return build_f_HorizontalPanel12();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel12() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel12 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel12.add(get_f_Spacer13());
      f_HorizontalPanel12.add(get_pesoSpinBox());


      return f_HorizontalPanel12;
    }

    /**
     * Getter for f_Spacer13 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer13() {
      return build_f_Spacer13();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer13() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer13 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer13.addStyleName("" + get_style().boxSpacer() + "");


      return f_Spacer13;
    }

    /**
     * Getter for pesoSpinBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.ckd.client.ui.SpinnerIntegerBox get_pesoSpinBox() {
      return build_pesoSpinBox();
    }
    private it.mate.ckd.client.ui.SpinnerIntegerBox build_pesoSpinBox() {
      // Creation section.
      final it.mate.ckd.client.ui.SpinnerIntegerBox pesoSpinBox = (it.mate.ckd.client.ui.SpinnerIntegerBox) GWT.create(it.mate.ckd.client.ui.SpinnerIntegerBox.class);
      // Setup section.
      pesoSpinBox.setValue(70);


      owner.pesoSpinBox = pesoSpinBox;

      return pesoSpinBox;
    }

    /**
     * Getter for domId9Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId9Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId9Element() {
      return domId9Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId9Element() {
      // Creation section.
      domId9Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId9());
      // Setup section.


      return domId9Element;
    }

    /**
     * Getter for domId10 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId10;
    private java.lang.String get_domId10() {
      return domId10;
    }
    private java.lang.String build_domId10() {
      // Creation section.
      domId10 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId10;
    }

    /**
     * Getter for f_Label14 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label14() {
      return build_f_Label14();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label14() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label14 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label14.setText(messages.message7());


      return f_Label14;
    }

    /**
     * Getter for domId10Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId10Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId10Element() {
      return domId10Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId10Element() {
      // Creation section.
      domId10Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId10());
      // Setup section.


      return domId10Element;
    }

    /**
     * Getter for domId11 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId11;
    private java.lang.String get_domId11() {
      return domId11;
    }
    private java.lang.String build_domId11() {
      // Creation section.
      domId11 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId11;
    }

    /**
     * Getter for f_Label15 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label15() {
      return build_f_Label15();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label15() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label15 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label15.setText(messages.message8());


      return f_Label15;
    }

    /**
     * Getter for domId11Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId11Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId11Element() {
      return domId11Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId11Element() {
      // Creation section.
      domId11Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId11());
      // Setup section.


      return domId11Element;
    }

    /**
     * Getter for domId12 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId12;
    private java.lang.String get_domId12() {
      return domId12;
    }
    private java.lang.String build_domId12() {
      // Creation section.
      domId12 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId12;
    }

    /**
     * Getter for f_HorizontalPanel16 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel16() {
      return build_f_HorizontalPanel16();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel16() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel16 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel16.add(get_f_Spacer17());
      f_HorizontalPanel16.add(get_altezzaSpinBox());


      return f_HorizontalPanel16;
    }

    /**
     * Getter for f_Spacer17 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer17() {
      return build_f_Spacer17();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer17() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer17 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer17.addStyleName("" + get_style().boxSpacer() + "");


      return f_Spacer17;
    }

    /**
     * Getter for altezzaSpinBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.ckd.client.ui.SpinnerIntegerBox get_altezzaSpinBox() {
      return build_altezzaSpinBox();
    }
    private it.mate.ckd.client.ui.SpinnerIntegerBox build_altezzaSpinBox() {
      // Creation section.
      final it.mate.ckd.client.ui.SpinnerIntegerBox altezzaSpinBox = (it.mate.ckd.client.ui.SpinnerIntegerBox) GWT.create(it.mate.ckd.client.ui.SpinnerIntegerBox.class);
      // Setup section.
      altezzaSpinBox.setValue(170);


      owner.altezzaSpinBox = altezzaSpinBox;

      return altezzaSpinBox;
    }

    /**
     * Getter for domId12Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId12Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId12Element() {
      return domId12Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId12Element() {
      // Creation section.
      domId12Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId12());
      // Setup section.


      return domId12Element;
    }

    /**
     * Getter for domId13 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId13;
    private java.lang.String get_domId13() {
      return domId13;
    }
    private java.lang.String build_domId13() {
      // Creation section.
      domId13 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId13;
    }

    /**
     * Getter for f_Spacer18 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer18() {
      return build_f_Spacer18();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer18() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer18 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer18.setHeight("0.6em");


      return f_Spacer18;
    }

    /**
     * Getter for domId13Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId13Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId13Element() {
      return domId13Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId13Element() {
      // Creation section.
      domId13Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId13());
      // Setup section.


      return domId13Element;
    }

    /**
     * Getter for domId14 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId14;
    private java.lang.String get_domId14() {
      return domId14;
    }
    private java.lang.String build_domId14() {
      // Creation section.
      domId14 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId14;
    }

    /**
     * Getter for f_HorizontalPanel19 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel19() {
      return build_f_HorizontalPanel19();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel19() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel19 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel19.add(get_f_Spacer20());
      f_HorizontalPanel19.add(get_f_Label21());
      f_HorizontalPanel19.add(get_f_Spacer22());
      f_HorizontalPanel19.add(get_fBtn());
      f_HorizontalPanel19.add(get_f_Spacer23());
      f_HorizontalPanel19.add(get_f_Label24());
      f_HorizontalPanel19.add(get_f_Spacer25());
      f_HorizontalPanel19.add(get_bBtn());


      return f_HorizontalPanel19;
    }

    /**
     * Getter for f_Spacer20 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer20() {
      return build_f_Spacer20();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer20() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer20 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer20.setWidth("10px");


      return f_Spacer20;
    }

    /**
     * Getter for f_Label21 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label21() {
      return build_f_Label21();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label21() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label21 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label21.setText(messages.message9());


      return f_Label21;
    }

    /**
     * Getter for f_Spacer22 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer22() {
      return build_f_Spacer22();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer22() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer22 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer22.setWidth("10px");


      return f_Spacer22;
    }

    /**
     * Getter for fBtn called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.ckd.client.ui.CkdCheckBox get_fBtn() {
      return build_fBtn();
    }
    private it.mate.ckd.client.ui.CkdCheckBox build_fBtn() {
      // Creation section.
      final it.mate.ckd.client.ui.CkdCheckBox fBtn = (it.mate.ckd.client.ui.CkdCheckBox) GWT.create(it.mate.ckd.client.ui.CkdCheckBox.class);
      // Setup section.
      fBtn.setImage(get_bundle().checkImage());


      owner.fBtn = fBtn;

      return fBtn;
    }

    /**
     * Getter for f_Spacer23 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer23() {
      return build_f_Spacer23();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer23() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer23 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer23.setWidth("30px");


      return f_Spacer23;
    }

    /**
     * Getter for f_Label24 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label24() {
      return build_f_Label24();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label24() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label24 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label24.setText(messages.message10());


      return f_Label24;
    }

    /**
     * Getter for f_Spacer25 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer25() {
      return build_f_Spacer25();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer25() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer25 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer25.setWidth("10px");


      return f_Spacer25;
    }

    /**
     * Getter for bBtn called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.ckd.client.ui.CkdCheckBox get_bBtn() {
      return build_bBtn();
    }
    private it.mate.ckd.client.ui.CkdCheckBox build_bBtn() {
      // Creation section.
      final it.mate.ckd.client.ui.CkdCheckBox bBtn = (it.mate.ckd.client.ui.CkdCheckBox) GWT.create(it.mate.ckd.client.ui.CkdCheckBox.class);
      // Setup section.
      bBtn.setImage(get_bundle().checkImage());


      owner.bBtn = bBtn;

      return bBtn;
    }

    /**
     * Getter for domId14Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId14Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId14Element() {
      return domId14Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId14Element() {
      // Creation section.
      domId14Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId14());
      // Setup section.


      return domId14Element;
    }

    /**
     * Getter for domId15 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId15;
    private java.lang.String get_domId15() {
      return domId15;
    }
    private java.lang.String build_domId15() {
      // Creation section.
      domId15 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId15;
    }

    /**
     * Getter for f_Spacer26 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer26() {
      return build_f_Spacer26();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer26() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer26 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer26.setHeight("0.6em");


      return f_Spacer26;
    }

    /**
     * Getter for domId15Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId15Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId15Element() {
      return domId15Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId15Element() {
      // Creation section.
      domId15Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId15());
      // Setup section.


      return domId15Element;
    }

    /**
     * Getter for domId16 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId16;
    private java.lang.String get_domId16() {
      return domId16;
    }
    private java.lang.String build_domId16() {
      // Creation section.
      domId16 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId16;
    }

    /**
     * Getter for f_Label27 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label27() {
      return build_f_Label27();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label27() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label27 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label27.setText(messages.message11());


      return f_Label27;
    }

    /**
     * Getter for domId16Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId16Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId16Element() {
      return domId16Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId16Element() {
      // Creation section.
      domId16Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId16());
      // Setup section.


      return domId16Element;
    }

    /**
     * Getter for domId17 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId17;
    private java.lang.String get_domId17() {
      return domId17;
    }
    private java.lang.String build_domId17() {
      // Creation section.
      domId17 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId17;
    }

    /**
     * Getter for albuminUmAnc called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Anchor get_albuminUmAnc() {
      return build_albuminUmAnc();
    }
    private com.google.gwt.user.client.ui.Anchor build_albuminUmAnc() {
      // Creation section.
      final com.google.gwt.user.client.ui.Anchor albuminUmAnc = (com.google.gwt.user.client.ui.Anchor) GWT.create(com.google.gwt.user.client.ui.Anchor.class);
      // Setup section.
      albuminUmAnc.setText(messages.message12());
      albuminUmAnc.addClickHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames2);


      owner.albuminUmAnc = albuminUmAnc;

      return albuminUmAnc;
    }

    /**
     * Getter for domId17Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId17Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId17Element() {
      return domId17Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId17Element() {
      // Creation section.
      domId17Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId17());
      // Setup section.


      return domId17Element;
    }

    /**
     * Getter for domId18 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId18;
    private java.lang.String get_domId18() {
      return domId18;
    }
    private java.lang.String build_domId18() {
      // Creation section.
      domId18 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId18;
    }

    /**
     * Getter for f_HorizontalPanel28 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel28() {
      return build_f_HorizontalPanel28();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel28() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel28 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel28.add(get_f_Spacer29());
      f_HorizontalPanel28.add(get_albuminuriaSpinBox());


      return f_HorizontalPanel28;
    }

    /**
     * Getter for f_Spacer29 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer29() {
      return build_f_Spacer29();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer29() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer29 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer29.addStyleName("" + get_style().boxSpacer() + "");


      return f_Spacer29;
    }

    /**
     * Getter for albuminuriaSpinBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.ckd.client.ui.SpinnerIntegerBox get_albuminuriaSpinBox() {
      return build_albuminuriaSpinBox();
    }
    private it.mate.ckd.client.ui.SpinnerIntegerBox build_albuminuriaSpinBox() {
      // Creation section.
      final it.mate.ckd.client.ui.SpinnerIntegerBox albuminuriaSpinBox = (it.mate.ckd.client.ui.SpinnerIntegerBox) GWT.create(it.mate.ckd.client.ui.SpinnerIntegerBox.class);
      // Setup section.
      albuminuriaSpinBox.setValue(10);


      owner.albuminuriaSpinBox = albuminuriaSpinBox;

      return albuminuriaSpinBox;
    }

    /**
     * Getter for domId18Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId18Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId18Element() {
      return domId18Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId18Element() {
      // Creation section.
      domId18Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId18());
      // Setup section.


      return domId18Element;
    }

    /**
     * Getter for domId19 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId19;
    private java.lang.String get_domId19() {
      return domId19;
    }
    private java.lang.String build_domId19() {
      // Creation section.
      domId19 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId19;
    }

    /**
     * Getter for f_Spacer30 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer30() {
      return build_f_Spacer30();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer30() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer30 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer30.setHeight("0.6em");


      return f_Spacer30;
    }

    /**
     * Getter for domId19Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId19Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId19Element() {
      return domId19Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId19Element() {
      // Creation section.
      domId19Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId19());
      // Setup section.


      return domId19Element;
    }

    /**
     * Getter for domId20 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId20;
    private java.lang.String get_domId20() {
      return domId20;
    }
    private java.lang.String build_domId20() {
      // Creation section.
      domId20 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId20;
    }

    /**
     * Getter for f_HorizontalPanel31 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel31() {
      return build_f_HorizontalPanel31();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel31() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel31 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel31.setHorizontalAlignment(com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER);
      f_HorizontalPanel31.add(get_ckdOutputBtn());
      f_HorizontalPanel31.setWidth("100%");


      return f_HorizontalPanel31;
    }

    /**
     * Getter for ckdOutputBtn called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.googlecode.mgwt.ui.client.widget.Button get_ckdOutputBtn() {
      return build_ckdOutputBtn();
    }
    private com.googlecode.mgwt.ui.client.widget.Button build_ckdOutputBtn() {
      // Creation section.
      final com.googlecode.mgwt.ui.client.widget.Button ckdOutputBtn = (com.googlecode.mgwt.ui.client.widget.Button) GWT.create(com.googlecode.mgwt.ui.client.widget.Button.class);
      // Setup section.
      ckdOutputBtn.setText(messages.message13());
      ckdOutputBtn.setWidth("10em");
      ckdOutputBtn.setRound(true);
      ckdOutputBtn.addTouchStartHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames3);


      return ckdOutputBtn;
    }

    /**
     * Getter for domId20Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId20Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId20Element() {
      return domId20Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId20Element() {
      // Creation section.
      domId20Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId20());
      // Setup section.


      return domId20Element;
    }

    /**
     * Getter for domId21 called 2 times. Type: DOM_ID_HOLDER. Build precedence: 2.
     */
    private java.lang.String domId21;
    private java.lang.String get_domId21() {
      return domId21;
    }
    private java.lang.String build_domId21() {
      // Creation section.
      domId21 = com.google.gwt.dom.client.Document.get().createUniqueId();
      // Setup section.


      return domId21;
    }

    /**
     * Getter for f_Spacer32 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer32() {
      return build_f_Spacer32();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer32() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer32 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer32.setHeight("1em");


      return f_Spacer32;
    }

    /**
     * Getter for domId21Element called 2 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.uibinder.client.LazyDomElement domId21Element;
    private com.google.gwt.uibinder.client.LazyDomElement get_domId21Element() {
      return domId21Element;
    }
    private com.google.gwt.uibinder.client.LazyDomElement build_domId21Element() {
      // Creation section.
      domId21Element = new com.google.gwt.uibinder.client.LazyDomElement<Element>(get_domId21());
      // Setup section.


      return domId21Element;
    }
  }
}
