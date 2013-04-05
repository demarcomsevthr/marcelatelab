package it.mate.econyx.client.view;

import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.Period;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;

import java.util.Date;
import java.util.List;

public interface CalendarView extends BaseView<CalendarView.Presenter> {
  
  public interface Presenter extends BasePresenter {
    public void goToDate(Date date, List<CalEvent> calEvents);
    public void findByPeriod(Period period);
  }
  
  public abstract class AbstractPresenter implements Presenter {
    public void goToPrevious() { }
    public BaseView getView() {
      return null;
    }
  }
  
  public class NotImpl extends UnimplementedView<CalendarView.Presenter> implements CalendarView { }
  
}
