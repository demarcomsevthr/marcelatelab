package it.mate.postscriptum.server.tasks;

import it.mate.postscriptum.server.utils.AdapterUtils;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class UpdateUserInfosTask implements DeferredTask {
  
  public static void enqueue() {
    Queue queue = QueueFactory.getDefaultQueue();
    UpdateUserInfosTask task = new UpdateUserInfosTask();
    queue.add(TaskOptions.Builder.withPayload(task));
  }

  @Override
  public void run() {
    AdapterUtils.getStickAdapter().updateUserInfos();
  }
  
}
