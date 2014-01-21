package it.mate.gwtcommons.client.mvp;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("unchecked")
public abstract class BaseActivity extends AbstractActivity implements BasePresenter {

  private BaseClientFactory clientFactory;
  
  private BaseView view;
  
  public BaseActivity(BaseClientFactory clientFactory) {
    this.clientFactory = clientFactory;
    GwtUtils.log(getClass(), hashCode(), "init", "initialized " + this);
  }
  
  public void onDispose() {
    this.view = null;
  }
  
  @Override
  public void onCancel() {
    onDispose();
    super.onCancel();
  }
  
  @Override
  public void onStop() {
    onDispose();
    super.onStop();
  }
  
  /*
  @Override
  public boolean equals(Object that) {
    // trucco per obbligare com.google.gwt.activity.shared.ActivityManager.onPlaceChange riutilizzi la stessa istanza 
    if (that instanceof BaseActivity)
      return false;
    return super.equals(that);
  }
  */

  protected void goTo (Place place) {
    GwtUtils.log(getClass(), "goTo", "place = " + place + " this = " + this);
    clientFactory.getPlaceController().goTo(place);
  }
  
  protected void initView(BaseView view, AcceptsOneWidget panel) {
    this.view = view;
    view.setPresenter(this);
    panel.setWidget(view.asWidget());
  }

  public BaseView getView() {
    // 16/10/2012 per evitare che le getView vadano in null pointer exc
    if (view == null) {
      return new BaseView<BasePresenter>() {
        public Widget asWidget() {
          return null;
        }
        public void setPresenter(BasePresenter activity) {  }
        public void setModel(Object model, String tag) {  }
        public void setModel(Object model) {  }
        public void setWidth(String width) {  }
        public void setHeight(String height) {  }
      };
    }
    return view;
  }
  
  public void goToPrevious() {
    if (clientFactory instanceof CurrentPlaceHolder) {
      CurrentPlaceHolder currentPlaceHolder = (CurrentPlaceHolder)clientFactory;
      Place currentPlace = currentPlaceHolder.getCurrentPlace();
      if (currentPlace != null && currentPlace instanceof ReversiblePlace) {
        Place previousPlace = ((ReversiblePlace)currentPlace).getPreviousPlace();
        if (previousPlace != null) {
          goTo(previousPlace);
        }
      }
    }
  }
  
}