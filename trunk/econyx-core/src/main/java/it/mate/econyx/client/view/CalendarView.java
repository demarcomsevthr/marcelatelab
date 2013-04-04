package it.mate.econyx.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

import java.util.Date;

public interface CalendarView extends BaseView<CalendarView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    public void goToDate(Date date);
  }
  
  public abstract class AbstractPresenter implements Presenter {
    public void goToPrevious() { }
    public BaseView getView() {
      return null;
    }
  }
  
  public class NotImpl extends UnimplementedView<CalendarView.Presenter> implements CalendarView { }
  
}
