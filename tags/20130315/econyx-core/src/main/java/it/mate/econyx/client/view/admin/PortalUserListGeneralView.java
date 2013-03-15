package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.PortalUserListView;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.ui.AnchorCell;
import it.mate.gwtcommons.client.ui.AnchorImageCell;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ProvidesKey;

public class PortalUserListGeneralView extends AbstractAdminTabPage<PortalUserListView.Presenter> implements PortalUserListView, IsAdminTabPage<PortalUserListView.Presenter> {
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalUserListGeneralView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) CellTableExt<PortalUser> usersTable;
  @UiField Panel pagerPanel;
  
  public PortalUserListGeneralView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    usersTable = new CellTableExt<PortalUser>(new ProvidesKey<PortalUser>() {
      public Object getKey(PortalUser PortalUser) {
        return PortalUser.getId();
      }
    });
    
    usersTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    
    usersTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<PortalUser>(), 
    new AnchorCell<PortalUser>() {
      protected String getCellValue(PortalUser item) {
        return item.getScreenName();
      }
      protected void onConsumedEvent(NativeEvent event, PortalUser item) {
        getPresenter().edit(item);
      }
    }, 
    null), "Nome");
    
    usersTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<PortalUser, String>() {
      public String getValue(PortalUser user) {
        return user.getEmailAddress();
      }
    }, new TextCell(), null), "Email");
    
    usersTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<PortalUser, String>() {
      public String getValue(PortalUser user) {
        return GwtUtils.formatCurrency(user.getBillingAccount());
      }
    }, new TextCell(), null), "Saldo");
    
    usersTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<PortalUser, String>() {
      public String getValue(PortalUser user) {
        return user.isActive() ? "SI" : "NO";
      }
    }, new TextCell(), null), "Attivo");
    
    usersTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<PortalUser>(), 
    new AnchorImageCell<PortalUser>() {
      protected String getCellImageUrl(PortalUser model) {
        return "images/commons/round-email.png";
      }
      @Override
      protected String getCellValue(PortalUser model) {
        return "invia mail di attivazione";
      }
      protected void onConsumedEvent(NativeEvent event, PortalUser user) {
        getPresenter().sendActivationMail(user);
      }
    }, 
    null), "");
    
    usersTable.addFillerColumn();
    
    usersTable.addCellPreviewHandler(new CellPreviewEvent.Handler<PortalUser>() {
      public void onCellPreview(CellPreviewEvent<PortalUser> event) {
        if ("dblclick".equals(event.getNativeEvent().getType())) {
          getPresenter().edit(event.getValue());
        }
      }
    });
    
    usersTable.sinkEvents(Event.ONDBLCLICK);
    
  }
  
  @Override
  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);

  }
  
  public void setModel(Object model, String tag) {
    List<PortalUser> users = (List<PortalUser>)model;
    if (users == null) {
      usersTable.setRowCount(0);
    } else {
      Collections.sort(users, new Comparator<PortalUser>() {
        public int compare(PortalUser i1, PortalUser i2) {
          return i1.getScreenName().compareTo(i2.getScreenName());
        }
      });
      /*
      usersTable.setRowCount(users.size());
      usersTable.setRowData(0, users);
      */
      usersTable.setRowDataExt(users);
      // 29/11/2012: aspetto che si carichino le immagini perchè cambiano l'altezza della riga!
      GwtUtils.deferredExecution(2000, new Delegate<Void>() {
        public void execute(Void element) {
          usersTable.adaptToViewHeight(PortalUserListGeneralView.this.getOffsetHeight(), new Delegate<SimplePager>() {
            public void execute(SimplePager pager) {
              pagerPanel.add(pager);
            }
          });
        }
      });
    }
  }

  @Override
  public void updateModel(Object model, Delegate<Object> delegate) {

  }
  
}
