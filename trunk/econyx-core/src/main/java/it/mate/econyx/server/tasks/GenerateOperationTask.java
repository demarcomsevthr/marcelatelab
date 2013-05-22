package it.mate.econyx.server.tasks;

import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.util.AdaptersUtil;

import java.util.Date;

import com.google.appengine.api.taskqueue.DeferredTask;

@SuppressWarnings("serial")
public class GenerateOperationTask implements DeferredTask {
  
  public static final int GENERATE_RANDOM_CUSTOMERS = 1;
  
  public static final int GENERATE_RANDOM_ORDERS = 2;
  
  public static final int REFRESH_USERS_CACHE = 3;
  
  private int command;

  private int number;
  
  private Date date;
  
  private String loggedUserId;

  public GenerateOperationTask(int command, int number, Date date) {
    this.command = command;
    this.number = number;
    this.date = date;
  }
  
  @Override
  public void run() {
    GeneralAdapter adapter = AdaptersUtil.getGeneralAdapter();
    if (command == GENERATE_RANDOM_CUSTOMERS) {
      adapter.generateRandomCustomers(number, date);
    } else if (command == GENERATE_RANDOM_ORDERS) {
      adapter.generateRandomOrders(number, date, loggedUserId);
    } else if (command == REFRESH_USERS_CACHE) {
      adapter.refreshUsersCache();
    }
  }
  
  public GenerateOperationTask setLoggedUserId(String loggedUserId) {
    this.loggedUserId = loggedUserId;
    return this;
  }

}
