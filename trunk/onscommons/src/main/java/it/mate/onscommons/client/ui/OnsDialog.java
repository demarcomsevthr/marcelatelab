package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.onsen.dom.Dialog;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.phgcommons.client.utils.callbacks.JSOCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class OnsDialog extends HTMLPanel implements AcceptsOneWidget {

  private final static String TAG_NAME = "ons-dialog";
  
  private String varName;
  
  private Dialog dialog;
  
  public OnsDialog() {
    this("");
  }
  
  public OnsDialog(String html) {
    super(TAG_NAME, html);
    PhgUtils.ensureId(getElement());
    initVarName();
  }
  
  private void initVarName() {
    varName = GwtUtils.replaceEx(getElement().getId(), "-", "") + "Dialog" ;
    getElement().setAttribute("var", varName);
  }
  
  public String getVarName() {
    return varName;
  }
  
  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
    if (widget.getElement().getNodeName().toLowerCase().startsWith("ons")) {
      OnsenUi.compileElement(widget.getElement());
    }
  }

  @Override
  public void setWidget(IsWidget w) {
    add(w);
  }
  
  public void show () {
    if (dialog != null) {
      showDialogImpl(dialog);
      return;
    }
    final String dialogId = getThis().getElement().getId();
    GwtUtils.onAvailable(dialogId, new Delegate<Element>() {
      public void execute(Element dialogElem) {
        String templateId = dialogId + "Template";
        OnsTemplate template = new OnsTemplate(templateId);
        template.add(getThis());
        RootPanel.get().add(template);
        templateId = template.getElement().getId();
        PhgUtils.log("finding template " + templateId);
        GwtUtils.onAvailable(templateId, new Delegate<Element>() {
          public void execute(Element templateElem) {
            OnsenUi.compileElement(templateElem);
            PhgUtils.log("creating dialog " + templateElem.getId());
            createDialogImpl(templateElem.getId(), getThis().getVarName(), new JSOCallback() {
              public void handle(JavaScriptObject jso) {
                getThis().dialog = jso.cast();
              }
            });
          }
        });
      }
    });
  }
  
  public void getRealWidth(final Delegate<Integer> delegate) {
    GwtUtils.onAvailable(getElement(), new Delegate<Element>() {
      public void execute(Element dialogElement) {
        for (int it = 0; it < dialogElement.getChildCount(); it++) {
          Element child = dialogElement.getChild(it).cast();
          if (child.getTagName() != null && child.getTagName().toLowerCase().contains("div") && 
              child.getClassName() != null && child.getClassName().toLowerCase().trim().equals("dialog")) {
            delegate.execute(child.getOffsetWidth());
          }
        }
      }
    });
  }
  
  public void show (Widget content, final JavaScriptObject options, final boolean cancelable) {
    this.add(content);
    final String templateId = getVarName() + "Template" ;
    OnsTemplate template = new OnsTemplate(templateId);
    template.add(this);
    RootPanel.get().add(template);
    GwtUtils.onAvailable(templateId, new Delegate<Element>() {
      public void execute(Element templateElement) {
        OnsenUi.compileElement(templateElement);
        GwtUtils.deferredExecution(new Delegate<Void>() {
          public void execute(Void element) {
            createDialogImpl(templateId);
            GwtUtils.deferredExecution(new Delegate<Void>() {
              public void execute(Void element) {
                showDialogImpl(getVarName(), options);
                setCancelable(cancelable);
              }
            });
          }
        });
      }
    });
  }
  
  public void setCancelable(final boolean cancelable) {
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        getDialogObject().setCancelable(cancelable);;
      }
    });
  }
  
  protected static native void showDialogImpl(String id, JavaScriptObject options) /*-{
    $wnd[id].show(options);
  }-*/;
  
  protected static native void setCancelableImpl(String id, boolean cancelable) /*-{
    $wnd[id].setCancelable(cancelable);
  }-*/;

  private OnsDialog getThis() {
    return this;
  }
  
  public void hide() {
    getDialogObject().hide();
  }  
  
  protected static native void createDialogImpl(String templateId) /*-{
    $wnd.ons.createDialog(templateId).then(function(dialog) {
  
    });
  }-*/;
  
  protected static native void createDialogImpl(String templateId, String varName, JSOCallback callback) /*-{
    $wnd.ons.createDialog(templateId).then(function(dlg) {
      var dialog = $wnd[varName];
      dialog.show();
      @it.mate.phgcommons.client.utils.PhgUtils::log(Ljava/lang/String;)('showing dialog ' + dialog);
      callback.@it.mate.phgcommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(dialog);
    });
  }-*/;
  
  protected static native void showDialogImpl(Dialog dialog) /*-{
    dialog.show();
  }-*/;

  public Dialog getDialogObject() {
    return getDialogObjectImpl(varName);
  }

  protected static native Dialog getDialogObjectImpl(String varName) /*-{
    return $wnd[varName];
  }-*/;
  
}
