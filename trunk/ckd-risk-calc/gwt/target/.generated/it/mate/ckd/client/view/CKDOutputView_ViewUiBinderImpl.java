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

public class CKDOutputView_ViewUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, it.mate.ckd.client.view.CKDOutputView>, it.mate.ckd.client.view.CKDOutputView.ViewUiBinder {
  static CKDOutputViewViewUiBinderImplGenMessages messages = (CKDOutputViewViewUiBinderImplGenMessages) GWT.create(CKDOutputViewViewUiBinderImplGenMessages.class);

  interface Template extends SafeHtmlTemplates {
    @Template("<table> <tr> <td colspan='2'> <span id='{0}'></span> </td> </tr> <tr> <td> <span id='{1}'></span> </td> <td> <span id='{2}'></span> </td> </tr> <tr> <td> <span id='{3}'></span> </td> <td> <span id='{4}'></span> </td> </tr> <tr> <td colspan='2'> <span id='{5}'></span> </td> </tr>  <tr> <td colspan='2'> <span id='{6}'></span> </td> </tr> <tr> <td> <span id='{7}'></span> </td> <td> <span id='{8}'></span> </td> </tr> <tr> <td> <span id='{9}'></span> </td> <td> <span id='{10}'></span> </td> </tr> <tr> <td colspan='2'> <span id='{11}'></span> </td> </tr>  <tr> <td colspan='2'> <span id='{12}'></span> </td> </tr> <tr> <td> <span id='{13}'></span> </td> <td> <span id='{14}'></span> </td> </tr> <tr> <td> <span id='{15}'></span> </td> <td> <span id='{16}'></span> </td> </tr> <tr> <td colspan='2'> <span id='{17}'></span> </td> </tr> </table>")
    SafeHtml html1(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14, String arg15, String arg16, String arg17);
     
  }

  Template template = GWT.create(Template.class);


  public com.google.gwt.user.client.ui.Widget createAndBindUi(final it.mate.ckd.client.view.CKDOutputView owner) {


    return new Widgets(owner).get_f_HTMLPanel1();
  }

  /**
   * Encapsulates the access to all inner widgets
   */
  class Widgets {
    private final it.mate.ckd.client.view.CKDOutputView owner;


    public Widgets(final it.mate.ckd.client.view.CKDOutputView owner) {
      this.owner = owner;
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
    }

    SafeHtml template_html1() {
      return template.html1(get_domId0(), get_domId1(), get_domId2(), get_domId3(), get_domId4(), get_domId5(), get_domId6(), get_domId7(), get_domId8(), get_domId9(), get_domId10(), get_domId11(), get_domId12(), get_domId13(), get_domId14(), get_domId15(), get_domId16(), get_domId17());
    }

    /**
     * Getter for clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay called 0 times. Type: GENERATED_BUNDLE. Build precedence: 1.
     */
    private it.mate.ckd.client.view.CKDOutputView_ViewUiBinderImpl_GenBundle get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      return build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay();
    }
    private it.mate.ckd.client.view.CKDOutputView_ViewUiBinderImpl_GenBundle build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      // Creation section.
      final it.mate.ckd.client.view.CKDOutputView_ViewUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (it.mate.ckd.client.view.CKDOutputView_ViewUiBinderImpl_GenBundle) GWT.create(it.mate.ckd.client.view.CKDOutputView_ViewUiBinderImpl_GenBundle.class);
      // Setup section.


      return clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay;
    }

    /**
     * Getter for style called 26 times. Type: IMPORTED. Build precedence: 1.
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
      f_HTMLPanel1.addStyleName("" + get_style().outputPanel() + "");

      // Attach section.
      UiBinderUtil.TempAttachment attachRecord2 = UiBinderUtil.attachToDom(f_HTMLPanel1.getElement());
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

      // Detach section.
      attachRecord2.detach();
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel2(), get_domId0Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel5(), get_domId1Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel8(), get_domId2Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel11(), get_domId3Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel14(), get_domId4Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Spacer16(), get_domId5Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel17(), get_domId6Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel20(), get_domId7Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel23(), get_domId8Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel26(), get_domId9Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel29(), get_domId10Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Spacer31(), get_domId11Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel32(), get_domId12Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel35(), get_domId13Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel38(), get_domId14Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel41(), get_domId15Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_HorizontalPanel44(), get_domId16Element().get());
      f_HTMLPanel1.addAndReplaceElement(get_f_Spacer46(), get_domId17Element().get());

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
      f_HorizontalPanel2.setVerticalAlignment(com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE);
      f_HorizontalPanel2.add(get_f_Spacer3());
      f_HorizontalPanel2.add(get_f_Label4());
      f_HorizontalPanel2.setWidth("100%");


      return f_HorizontalPanel2;
    }

    /**
     * Getter for f_Spacer3 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer3() {
      return build_f_Spacer3();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer3() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer3 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer3.setWidth("1.2em");


      return f_Spacer3;
    }

    /**
     * Getter for f_Label4 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label4() {
      return build_f_Label4();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label4() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label4 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label4.addStyleName("" + get_style().valoriLbl() + "");
      f_Label4.setText("Cockcroft - Gault");


      return f_Label4;
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
      f_HorizontalPanel5.add(get_f_Label7());


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
      f_Spacer6.setWidth("1.2em");


      return f_Spacer6;
    }

    /**
     * Getter for f_Label7 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label7() {
      return build_f_Label7();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label7() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label7 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label7.addStyleName("" + get_style().ckdLabel() + "");
      f_Label7.setText("GFR ml/min");


      return f_Label7;
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
      f_HorizontalPanel8.add(get_cockcroftGfrBox());
      f_HorizontalPanel8.add(get_f_Spacer10());
      f_HorizontalPanel8.add(get_cockcroftGfrStadium());


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
     * Getter for cockcroftGfrBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_cockcroftGfrBox() {
      return build_cockcroftGfrBox();
    }
    private com.google.gwt.user.client.ui.Label build_cockcroftGfrBox() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label cockcroftGfrBox = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      cockcroftGfrBox.addStyleName("" + get_style().ckdGfr() + "");
      cockcroftGfrBox.setHeight("1.6em");
      cockcroftGfrBox.setWidth("7.1em");


      owner.cockcroftGfrBox = cockcroftGfrBox;

      return cockcroftGfrBox;
    }

    /**
     * Getter for f_Spacer10 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer10() {
      return build_f_Spacer10();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer10() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer10 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer10.setWidth("0.3em");


      return f_Spacer10;
    }

    /**
     * Getter for cockcroftGfrStadium called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_cockcroftGfrStadium() {
      return build_cockcroftGfrStadium();
    }
    private com.google.gwt.user.client.ui.Label build_cockcroftGfrStadium() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label cockcroftGfrStadium = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      cockcroftGfrStadium.addStyleName("" + get_style().ckdStadium() + "");
      cockcroftGfrStadium.setHeight("1.6em");
      cockcroftGfrStadium.setWidth("2.0em");


      owner.cockcroftGfrStadium = cockcroftGfrStadium;

      return cockcroftGfrStadium;
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
     * Getter for f_HorizontalPanel11 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel11() {
      return build_f_HorizontalPanel11();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel11() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel11 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel11.add(get_f_Spacer12());
      f_HorizontalPanel11.add(get_f_Label13());


      return f_HorizontalPanel11;
    }

    /**
     * Getter for f_Spacer12 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer12() {
      return build_f_Spacer12();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer12() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer12 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer12.setWidth("1.2em");


      return f_Spacer12;
    }

    /**
     * Getter for f_Label13 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label13() {
      return build_f_Label13();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label13() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label13 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label13.addStyleName("" + get_style().ckdLabel() + "");
      f_Label13.setText(messages.message1());


      return f_Label13;
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
     * Getter for f_HorizontalPanel14 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel14() {
      return build_f_HorizontalPanel14();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel14() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel14 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel14.add(get_f_Spacer15());
      f_HorizontalPanel14.add(get_cockcroftRiskBox());


      return f_HorizontalPanel14;
    }

    /**
     * Getter for f_Spacer15 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer15() {
      return build_f_Spacer15();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer15() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer15 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer15.addStyleName("" + get_style().boxSpacer() + "");


      return f_Spacer15;
    }

    /**
     * Getter for cockcroftRiskBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_cockcroftRiskBox() {
      return build_cockcroftRiskBox();
    }
    private com.google.gwt.user.client.ui.Label build_cockcroftRiskBox() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label cockcroftRiskBox = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      cockcroftRiskBox.addStyleName("" + get_style().ckdRisk() + "");
      cockcroftRiskBox.setHeight("1.6em");
      cockcroftRiskBox.setWidth("9.6em");


      owner.cockcroftRiskBox = cockcroftRiskBox;

      return cockcroftRiskBox;
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
     * Getter for f_Spacer16 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer16() {
      return build_f_Spacer16();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer16() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer16 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer16.setHeight("1em");


      return f_Spacer16;
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
     * Getter for f_HorizontalPanel17 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel17() {
      return build_f_HorizontalPanel17();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel17() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel17 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel17.setHorizontalAlignment(com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER);
      f_HorizontalPanel17.setVerticalAlignment(com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE);
      f_HorizontalPanel17.add(get_f_Spacer18());
      f_HorizontalPanel17.add(get_f_Label19());
      f_HorizontalPanel17.setWidth("100%");


      return f_HorizontalPanel17;
    }

    /**
     * Getter for f_Spacer18 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer18() {
      return build_f_Spacer18();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer18() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer18 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer18.setWidth("0.2em");


      return f_Spacer18;
    }

    /**
     * Getter for f_Label19 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label19() {
      return build_f_Label19();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label19() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label19 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label19.addStyleName("" + get_style().valoriLbl() + "");
      f_Label19.setText("MDRD");
      f_Label19.setWidth("100%");


      return f_Label19;
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
     * Getter for f_HorizontalPanel20 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel20() {
      return build_f_HorizontalPanel20();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel20() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel20 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel20.add(get_f_Spacer21());
      f_HorizontalPanel20.add(get_f_Label22());


      return f_HorizontalPanel20;
    }

    /**
     * Getter for f_Spacer21 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer21() {
      return build_f_Spacer21();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer21() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer21 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer21.setWidth("1.2em");


      return f_Spacer21;
    }

    /**
     * Getter for f_Label22 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label22() {
      return build_f_Label22();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label22() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label22 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label22.addStyleName("" + get_style().ckdLabel() + "");
      f_Label22.setText("GFR ml/min");


      return f_Label22;
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
     * Getter for f_HorizontalPanel23 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel23() {
      return build_f_HorizontalPanel23();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel23() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel23 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel23.add(get_f_Spacer24());
      f_HorizontalPanel23.add(get_mdrdGfrBox());
      f_HorizontalPanel23.add(get_f_Spacer25());
      f_HorizontalPanel23.add(get_mdrdGfrStadium());


      return f_HorizontalPanel23;
    }

    /**
     * Getter for f_Spacer24 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer24() {
      return build_f_Spacer24();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer24() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer24 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer24.addStyleName("" + get_style().boxSpacer() + "");


      return f_Spacer24;
    }

    /**
     * Getter for mdrdGfrBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_mdrdGfrBox() {
      return build_mdrdGfrBox();
    }
    private com.google.gwt.user.client.ui.Label build_mdrdGfrBox() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label mdrdGfrBox = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      mdrdGfrBox.addStyleName("" + get_style().ckdGfr() + "");
      mdrdGfrBox.setHeight("1.6em");
      mdrdGfrBox.setWidth("7.1em");


      owner.mdrdGfrBox = mdrdGfrBox;

      return mdrdGfrBox;
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
      f_Spacer25.setWidth("0.3em");


      return f_Spacer25;
    }

    /**
     * Getter for mdrdGfrStadium called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_mdrdGfrStadium() {
      return build_mdrdGfrStadium();
    }
    private com.google.gwt.user.client.ui.Label build_mdrdGfrStadium() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label mdrdGfrStadium = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      mdrdGfrStadium.addStyleName("" + get_style().ckdStadium() + "");
      mdrdGfrStadium.setHeight("1.6em");
      mdrdGfrStadium.setWidth("2.0em");


      owner.mdrdGfrStadium = mdrdGfrStadium;

      return mdrdGfrStadium;
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
     * Getter for f_HorizontalPanel26 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel26() {
      return build_f_HorizontalPanel26();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel26() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel26 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel26.add(get_f_Spacer27());
      f_HorizontalPanel26.add(get_f_Label28());


      return f_HorizontalPanel26;
    }

    /**
     * Getter for f_Spacer27 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer27() {
      return build_f_Spacer27();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer27() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer27 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer27.setWidth("1.2em");


      return f_Spacer27;
    }

    /**
     * Getter for f_Label28 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label28() {
      return build_f_Label28();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label28() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label28 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label28.addStyleName("" + get_style().ckdLabel() + "");
      f_Label28.setText(messages.message2());


      return f_Label28;
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
     * Getter for f_HorizontalPanel29 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel29() {
      return build_f_HorizontalPanel29();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel29() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel29 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel29.add(get_f_Spacer30());
      f_HorizontalPanel29.add(get_mdrdRiskBox());


      return f_HorizontalPanel29;
    }

    /**
     * Getter for f_Spacer30 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer30() {
      return build_f_Spacer30();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer30() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer30 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer30.addStyleName("" + get_style().boxSpacer() + "");


      return f_Spacer30;
    }

    /**
     * Getter for mdrdRiskBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_mdrdRiskBox() {
      return build_mdrdRiskBox();
    }
    private com.google.gwt.user.client.ui.Label build_mdrdRiskBox() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label mdrdRiskBox = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      mdrdRiskBox.addStyleName("" + get_style().ckdRisk() + "");
      mdrdRiskBox.setHeight("1.6em");
      mdrdRiskBox.setWidth("9.6em");


      owner.mdrdRiskBox = mdrdRiskBox;

      return mdrdRiskBox;
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
     * Getter for f_Spacer31 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer31() {
      return build_f_Spacer31();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer31() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer31 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer31.setHeight("1em");


      return f_Spacer31;
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
     * Getter for f_HorizontalPanel32 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel32() {
      return build_f_HorizontalPanel32();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel32() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel32 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel32.setHorizontalAlignment(com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER);
      f_HorizontalPanel32.setVerticalAlignment(com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE);
      f_HorizontalPanel32.add(get_f_Spacer33());
      f_HorizontalPanel32.add(get_f_Label34());
      f_HorizontalPanel32.setWidth("100%");


      return f_HorizontalPanel32;
    }

    /**
     * Getter for f_Spacer33 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer33() {
      return build_f_Spacer33();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer33() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer33 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer33.setWidth("0.3em");


      return f_Spacer33;
    }

    /**
     * Getter for f_Label34 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label34() {
      return build_f_Label34();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label34() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label34 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label34.addStyleName("" + get_style().valoriLbl() + "");
      f_Label34.setText("CKD-EPI");
      f_Label34.setWidth("100%");


      return f_Label34;
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
     * Getter for f_HorizontalPanel35 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel35() {
      return build_f_HorizontalPanel35();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel35() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel35 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel35.add(get_f_Spacer36());
      f_HorizontalPanel35.add(get_f_Label37());


      return f_HorizontalPanel35;
    }

    /**
     * Getter for f_Spacer36 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer36() {
      return build_f_Spacer36();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer36() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer36 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer36.setWidth("1.2em");


      return f_Spacer36;
    }

    /**
     * Getter for f_Label37 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label37() {
      return build_f_Label37();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label37() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label37 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label37.addStyleName("" + get_style().ckdLabel() + "");
      f_Label37.setText("GFR ml/min");


      return f_Label37;
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
     * Getter for f_HorizontalPanel38 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel38() {
      return build_f_HorizontalPanel38();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel38() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel38 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel38.add(get_f_Spacer39());
      f_HorizontalPanel38.add(get_epiGfrBox());
      f_HorizontalPanel38.add(get_f_Spacer40());
      f_HorizontalPanel38.add(get_epiGfrStadium());


      return f_HorizontalPanel38;
    }

    /**
     * Getter for f_Spacer39 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer39() {
      return build_f_Spacer39();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer39() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer39 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer39.addStyleName("" + get_style().boxSpacer() + "");


      return f_Spacer39;
    }

    /**
     * Getter for epiGfrBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_epiGfrBox() {
      return build_epiGfrBox();
    }
    private com.google.gwt.user.client.ui.Label build_epiGfrBox() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label epiGfrBox = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      epiGfrBox.addStyleName("" + get_style().ckdGfr() + "");
      epiGfrBox.setHeight("1.6em");
      epiGfrBox.setWidth("7.1em");


      owner.epiGfrBox = epiGfrBox;

      return epiGfrBox;
    }

    /**
     * Getter for f_Spacer40 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer40() {
      return build_f_Spacer40();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer40() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer40 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer40.setWidth("0.3em");


      return f_Spacer40;
    }

    /**
     * Getter for epiGfrStadium called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_epiGfrStadium() {
      return build_epiGfrStadium();
    }
    private com.google.gwt.user.client.ui.Label build_epiGfrStadium() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label epiGfrStadium = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      epiGfrStadium.addStyleName("" + get_style().ckdStadium() + "");
      epiGfrStadium.setHeight("1.6em");
      epiGfrStadium.setWidth("2.0em");


      owner.epiGfrStadium = epiGfrStadium;

      return epiGfrStadium;
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
     * Getter for f_HorizontalPanel41 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel41() {
      return build_f_HorizontalPanel41();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel41() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel41 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel41.add(get_f_Spacer42());
      f_HorizontalPanel41.add(get_f_Label43());


      return f_HorizontalPanel41;
    }

    /**
     * Getter for f_Spacer42 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer42() {
      return build_f_Spacer42();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer42() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer42 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer42.setWidth("1.2em");


      return f_Spacer42;
    }

    /**
     * Getter for f_Label43 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label43() {
      return build_f_Label43();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label43() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label43 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label43.addStyleName("" + get_style().ckdLabel() + "");
      f_Label43.setText(messages.message3());


      return f_Label43;
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
     * Getter for f_HorizontalPanel44 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel44() {
      return build_f_HorizontalPanel44();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel44() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel44 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel44.add(get_f_Spacer45());
      f_HorizontalPanel44.add(get_epiRiskBox());


      return f_HorizontalPanel44;
    }

    /**
     * Getter for f_Spacer45 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer45() {
      return build_f_Spacer45();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer45() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer45 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer45.addStyleName("" + get_style().boxSpacer() + "");


      return f_Spacer45;
    }

    /**
     * Getter for epiRiskBox called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_epiRiskBox() {
      return build_epiRiskBox();
    }
    private com.google.gwt.user.client.ui.Label build_epiRiskBox() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label epiRiskBox = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      epiRiskBox.addStyleName("" + get_style().ckdRisk() + "");
      epiRiskBox.setHeight("1.6em");
      epiRiskBox.setWidth("9.6em");


      owner.epiRiskBox = epiRiskBox;

      return epiRiskBox;
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
     * Getter for f_Spacer46 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer46() {
      return build_f_Spacer46();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer46() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer46 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer46.setHeight("1em");


      return f_Spacer46;
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
  }
}
