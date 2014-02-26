package it.mate.postscriptum.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class SignPanel_ViewUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, it.mate.postscriptum.client.ui.SignPanel>, it.mate.postscriptum.client.ui.SignPanel.ViewUiBinder {


  public com.google.gwt.user.client.ui.Widget createAndBindUi(final it.mate.postscriptum.client.ui.SignPanel owner) {


    return new Widgets(owner).get_f_HorizontalPanel1();
  }

  /**
   * Encapsulates the access to all inner widgets
   */
  class Widgets {
    private final it.mate.postscriptum.client.ui.SignPanel owner;


    public Widgets(final it.mate.postscriptum.client.ui.SignPanel owner) {
      this.owner = owner;
    }


    /**
     * Getter for clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay called 0 times. Type: GENERATED_BUNDLE. Build precedence: 1.
     */
    private it.mate.postscriptum.client.ui.SignPanel_ViewUiBinderImpl_GenBundle get_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      return build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay();
    }
    private it.mate.postscriptum.client.ui.SignPanel_ViewUiBinderImpl_GenBundle build_clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay() {
      // Creation section.
      final it.mate.postscriptum.client.ui.SignPanel_ViewUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (it.mate.postscriptum.client.ui.SignPanel_ViewUiBinderImpl_GenBundle) GWT.create(it.mate.postscriptum.client.ui.SignPanel_ViewUiBinderImpl_GenBundle.class);
      // Setup section.


      return clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay;
    }

    /**
     * Getter for f_HorizontalPanel1 called 1 times. Type: DEFAULT. Build precedence: 1.
     */
    private com.google.gwt.user.client.ui.HorizontalPanel get_f_HorizontalPanel1() {
      return build_f_HorizontalPanel1();
    }
    private com.google.gwt.user.client.ui.HorizontalPanel build_f_HorizontalPanel1() {
      // Creation section.
      final com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel1 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
      // Setup section.
      f_HorizontalPanel1.add(get_signPromptLbl());
      f_HorizontalPanel1.add(get_signLbl());


      return f_HorizontalPanel1;
    }

    /**
     * Getter for signPromptLbl called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_signPromptLbl() {
      return build_signPromptLbl();
    }
    private com.google.gwt.user.client.ui.Label build_signPromptLbl() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label signPromptLbl = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      signPromptLbl.addStyleName("ui-signLbl");
      signPromptLbl.setText("Signed as");


      owner.signPromptLbl = signPromptLbl;

      return signPromptLbl;
    }

    /**
     * Getter for signLbl called 1 times. Type: DEFAULT. Build precedence: 2.
     */
    private com.google.gwt.user.client.ui.Label get_signLbl() {
      return build_signLbl();
    }
    private com.google.gwt.user.client.ui.Label build_signLbl() {
      // Creation section.
      final com.google.gwt.user.client.ui.Label signLbl = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
      // Setup section.
      signLbl.addStyleName("ui-signedLbl");
      signLbl.setText("");


      owner.signLbl = signLbl;

      return signLbl;
    }
  }
}
