package it.mate.econyx.server.tasks;

import it.mate.econyx.server.services.GeneralAdapter;
import it.mate.econyx.server.util.AdaptersUtil;

import com.google.appengine.api.taskqueue.DeferredTask;

@SuppressWarnings("serial")
public class GenerateOperationTask implements DeferredTask {
  
  public static final int GENERATE_RANDOM_CUSTOMERS = 1;
  
  public static final int GENERATE_RANDOM_ORDERS = 2;
  
  private int command;

  private int number;

  public GenerateOperationTask(int command, int number) {
    this.command = command;
    this.number = number;
  }
  
  @Override
  public void run() {
    GeneralAdapter adapter = AdaptersUtil.getGeneralAdapter();
    if (command == GENERATE_RANDOM_CUSTOMERS) {
      adapter.generateRandomCustomers(number);
    } else if (command == GENERATE_RANDOM_ORDERS) {
      adapter.generateRandomOrders(number);
    }
  }

}
