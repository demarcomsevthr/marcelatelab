package it.mate.econyx.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

public interface TestView extends BaseView<TestView.Presenter> {

  public interface Presenter extends BasePresenter {
    
  }
  
  public class NotImpl extends UnimplementedView<TestView.Presenter> implements TestView {

  }
  
}
