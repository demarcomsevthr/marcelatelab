package it.mate.gpg.client.ui;

import it.mate.gpg.client.utils.MvpPhoneUtils;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.gwtcommons.client.history.BaseActivityMapper;

import com.google.gwt.user.client.ui.SimplePanel;

public class MvpPhonePanel extends SimplePanel {

  private String historyId;
  
  public MvpPhonePanel() {
    super();
  }
  
  public void setHistoryId(String historyId) {
    this.historyId = historyId;
  }
  
  public String getHistoryId() {
    return historyId;
  }

  public void initMvp(BaseClientFactory<? extends CommonGinjector> clientFactory, BaseActivityMapper activityMapper) {
    MvpPhoneUtils.initMvp(clientFactory, this, historyId, activityMapper);
  }
  
}
