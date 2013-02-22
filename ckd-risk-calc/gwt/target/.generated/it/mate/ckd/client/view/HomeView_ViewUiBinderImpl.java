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

public class HomeView_ViewUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, it.mate.ckd.client.view.HomeView>, it.mate.ckd.client.view.HomeView.ViewUiBinder {
  static HomeViewViewUiBinderImplGenMessages messages = (HomeViewViewUiBinderImplGenMessages) GWT.create(HomeViewViewUiBinderImplGenMessages.class);

  interface Template extends SafeHtmlTemplates {
    @Template("<div>{0}</div>")
    SafeHtml html1(SafeHtml arg0);
     
    @Template("<div class='{0}' id='debugDiv'></div>")
    SafeHtml html2(String arg0);
     
    @Template("<span id='{0}'></span>")
    SafeHtml html3(String arg0);
     
  }

  Template template = GWT.create(Template.class);


  public com.google.gwt.user.client.ui.Widget createAndBindUi(final it.mate.ckd.client.view.HomeView owner) {


    return new Widgets(owner).get_f_HTMLPanel1();
  }

  /**
   * Encapsulates the access to all inner widgets
   */
  class Widgets {
    private final it.mate.ckd.client.view.HomeView owner;


    final com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1 = new com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler() {
      public void onTouchStart(com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent event) {
        owner.onParamBtn(event);
      }
    };

    public Widgets(final it.mate.ckd.client.view.HomeView owner) {
      this.owner = owner;
      build_bundle();  // more than one getter call detected. Type: IMPORTED, precedence: 1
      build_style();  // more than one getter call detected. Type: IMPORTED, precedence: 1
      build_domId0();  // more than one getter call detected. Type: DOM_ID_HOLDER, precedence: 2
      build_domId0Element();  // more than one getter call detected. Type: DEFAULT, precedence: 2
    }

    SafeHtml template_html1() {
      return template.html1(SafeHtmlUtils.fromSafeConstant(messages.message1()));
    }
    SafeHtml template_html2() {
      return template.html2("" + get_style().verLbl() + "");
    }
    SafeHtml template_html3() {
      return template.html3(get_domId0());
    }

    /**
     * Getter for clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay called 0 times. Type: GENERATED_BUNDLE. Build precedence: 1.
     */
    private it.mate.ckd.client.view.HomeView_ViewUiBinderImpl_GenBundle get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      return build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay();
    }
    private it.mate.ckd.client.view.HomeView_ViewUiBinderImpl_GenBundle build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      // Creation section.
      final it.mate.ckd.client.view.HomeView_ViewUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (it.mate.ckd.client.view.HomeView_ViewUiBinderImpl_GenBundle) GWT.create(it.mate.ckd.client.view.HomeView_ViewUiBinderImpl_GenBundle.class);
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
     * Getter for style called 2 times. Type: IMPORTED. Build precedence: 1.
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
      final com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel1 = new com.google.gwt.user.client.ui.HTMLPanel(template_html3().asString());
      // Setup section.

      // Attach section.
      UiBinderUtil.TempAttachment attachRecord0 = UiBinderUtil.attachToDom(f_HTMLPanel1.getElement());
      get_domId0Element().get();

      // Detach section.
      attachRecord0.detach();
      f_HTMLPanel1.addAndReplaceElement(get_f_VerticalPanel2(), get_domId0Element().get());

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
     * Getter for f_VerticalPanel2 called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.VerticalPanel get_f_VerticalPanel2() {
      return build_f_VerticalPanel2();
    }
    private com.google.gwt.user.client.ui.VerticalPanel build_f_VerticalPanel2() {
      // Creation section.
      final com.google.gwt.user.client.ui.VerticalPanel f_VerticalPanel2 = (com.google.gwt.user.client.ui.VerticalPanel) GWT.create(com.google.gwt.user.client.ui.VerticalPanel.class);
      // Setup section.
      f_VerticalPanel2.setHorizontalAlignment(com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER);
      f_VerticalPanel2.add(get_f_Spacer3());
      f_VerticalPanel2.add(get_f_HTMLPanel4());
      f_VerticalPanel2.add(get_f_Spacer5());
      f_VerticalPanel2.add(get_paramBtn());
      f_VerticalPanel2.add(get_f_Spacer6());
      f_VerticalPanel2.add(get_f_HorizontalPanel7());
      f_VerticalPanel2.add(get_f_Spacer11());
      f_VerticalPanel2.add(get_f_Label12());
      f_VerticalPanel2.add(get_f_HTMLPanel13());
      f_VerticalPanel2.setWidth("100%");


      return f_VerticalPanel2;
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
      f_Spacer3.setHeight("3.6em");


      return f_Spacer3;
    }

    /**
     * Getter for f_HTMLPanel4 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.HTMLPanel get_f_HTMLPanel4() {
      return build_f_HTMLPanel4();
    }
    private com.google.gwt.user.client.ui.HTMLPanel build_f_HTMLPanel4() {
      // Creation section.
      final com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel4 = new com.google.gwt.user.client.ui.HTMLPanel(template_html1().asString());
      // Setup section.


      return f_HTMLPanel4;
    }

    /**
     * Getter for f_Spacer5 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer5() {
      return build_f_Spacer5();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer5() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer5 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer5.setHeight("8em");


      return f_Spacer5;
    }

    /**
     * Getter for paramBtn called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.googlecode.mgwt.ui.client.widget.Button get_paramBtn() {
      return build_paramBtn();
    }
    private com.googlecode.mgwt.ui.client.widget.Button build_paramBtn() {
      // Creation section.
      final com.googlecode.mgwt.ui.client.widget.Button paramBtn = (com.googlecode.mgwt.ui.client.widget.Button) GWT.create(com.googlecode.mgwt.ui.client.widget.Button.class);
      // Setup section.
      paramBtn.setWidth("10em");
      paramBtn.setRound(true);
      paramBtn.addTouchStartHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1);


      owner.paramBtn = paramBtn;

      return paramBtn;
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
      f_Spacer6.setHeight("7em");


      return f_Spacer6;
    }

    /**
     * Getter for f_HorizontalPanel7 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel7() {
      return build_f_HorizontalPanel7();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel7() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel7 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel7.add(get_f_LocaleImage8());
      f_HorizontalPanel7.add(get_f_Spacer9());
      f_HorizontalPanel7.add(get_f_LocaleImage10());


      return f_HorizontalPanel7;
    }

    /**
     * Getter for f_LocaleImage8 called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private it.mate.ckd.client.ui.LocaleImage get_f_LocaleImage8() {
      return build_f_LocaleImage8();
    }
    private it.mate.ckd.client.ui.LocaleImage build_f_LocaleImage8() {
      // Creation section.
      final it.mate.ckd.client.ui.LocaleImage f_LocaleImage8 = (it.mate.ckd.client.ui.LocaleImage) GWT.create(it.mate.ckd.client.ui.LocaleImage.class);
      // Setup section.
      f_LocaleImage8.setLocale("en");
      f_LocaleImage8.setResource(get_bundle().flagEnImage());


      return f_LocaleImage8;
    }

    /**
     * Getter for f_Spacer9 called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer9() {
      return build_f_Spacer9();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer9() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer9 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer9.setWidth("0.4em");


      return f_Spacer9;
    }

    /**
     * Getter for f_LocaleImage10 called 1 times. Type: DEFAULT. Build precedence: 4.
     */
    private it.mate.ckd.client.ui.LocaleImage get_f_LocaleImage10() {
      return build_f_LocaleImage10();
    }
    private it.mate.ckd.client.ui.LocaleImage build_f_LocaleImage10() {
      // Creation section.
      final it.mate.ckd.client.ui.LocaleImage f_LocaleImage10 = (it.mate.ckd.client.ui.LocaleImage) GWT.create(it.mate.ckd.client.ui.LocaleImage.class);
      // Setup section.
      f_LocaleImage10.setLocale("it");
      f_LocaleImage10.setResource(get_bundle().flagItImage());


      return f_LocaleImage10;
    }

    /**
     * Getter for f_Spacer11 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private it.mate.gwtcommons.client.ui.Spacer get_f_Spacer11() {
      return build_f_Spacer11();
    }
    private it.mate.gwtcommons.client.ui.Spacer build_f_Spacer11() {
      // Creation section.
      final it.mate.gwtcommons.client.ui.Spacer f_Spacer11 = (it.mate.gwtcommons.client.ui.Spacer) GWT.create(it.mate.gwtcommons.client.ui.Spacer.class);
      // Setup section.
      f_Spacer11.setHeight("1em");


      return f_Spacer11;
    }

    /**
     * Getter for f_Label12 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.Label get_f_Label12() {
      return build_f_Label12();
    }
    private com.google.gwt.user.client.ui.Label build_f_Label12() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label f_Label12 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      f_Label12.addStyleName("" + get_style().verLbl() + "");
      f_Label12.setText(messages.message2());


      return f_Label12;
    }

    /**
     * Getter for f_HTMLPanel13 called 1 times. Type: DEFAULT. Build precedence: 3.
     */
    private com.google.gwt.user.client.ui.HTMLPanel get_f_HTMLPanel13() {
      return build_f_HTMLPanel13();
    }
    private com.google.gwt.user.client.ui.HTMLPanel build_f_HTMLPanel13() {
      // Creation section.
      final com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel13 = new com.google.gwt.user.client.ui.HTMLPanel(template_html2().asString());
      // Setup section.


      return f_HTMLPanel13;
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
  }
}
