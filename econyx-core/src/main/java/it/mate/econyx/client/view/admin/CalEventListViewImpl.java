package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.CalEventListView;
import it.mate.econyx.shared.model.CalEvent;
import it.mate.econyx.shared.model.impl.CalEventTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.MessageBox.Callbacks;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class CalEventListViewImpl extends AbstractBaseView<CalEventListView.Presenter> implements CalEventListView {
  
  public interface ViewUiBinder extends UiBinder<Widget, CalEventListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) AdminTabPanel<CalEventListView.Presenter> adminTab;
  
  private String width;
  
  private String height;
  
  public CalEventListViewImpl() {
    this(null, null);
  }
  
  public CalEventListViewImpl(String width, String height) {
    super();
    this.width = width;
    this.height = height;
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Eventi")
        .setView(new CalEventListGeneralView()));
    adminTab.setSections(sections);
  }
  
  public void setHeight(String height) {
    this.height = height;
    adminTab.setTabHeight(height);
  }
  
  public void setWidth(String width) {
    this.width = width;
    adminTab.setTabWidth(width);
  }
  
  public class CalEventOptionDialog {
    TextBox title = new TextBox();
    DateBox date = new DateBox();
    VerticalPanel table = new VerticalPanel();
    public CalEventOptionDialog(final Delegate<CalEvent> delegate) {
      date.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
      date.setValue(new Date());
      addRow("Titolo:", title);
      addRow("Data:", date);
      MessageBoxUtils.popupOkCancel("Nuovo Evento", table, "400px", new Delegate<MessageBox.Callbacks>() {
        public void execute(Callbacks callbacks) {
          CalEvent event = new CalEventTx();
          event.setTitle(title.getText());
          event.setDate(date.getValue());
          event.setName(event.getTitle());
          delegate.execute(event);
        }
      }, new Delegate<DialogBox>() {
        public void execute(DialogBox element) {
          title.setFocus(true);
        }
      });
    }
    private void addRow(String text, Widget w) {
      HorizontalPanel row;
      row = new HorizontalPanel();
      row.add(new Spacer("1px", "2em"));
      Label label = new Label(text);
      label.setWidth("6em");
      row.add(label);
      row.add(w);
      table.add(row);
    }
  }
  
  protected void initProvided() {  
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setNewButtonEnabled(true).setWidth(width).setHeight(height).setEditButtonEnabled(true)
        .setDeleteButtonEnabled(true)) {
      public void onSave(Object model) { }
      public void onNewModelRequested() {
        new CalEventOptionDialog(new Delegate<CalEvent>() {
          public void execute(CalEvent event) {
            getPresenter().edit(event);
          }
        });
      }
      public void onEdit(Object model) {
        if (model instanceof CalEvent) {
          getPresenter().edit((CalEvent)model);
        }
      }
      public void onDelete(Object model) {
        CalEvent CalEvent = (CalEvent)model;
        getPresenter().delete(CalEvent);
      }
    };
  }
  
  @Override
  public void setPresenter(Presenter activity) {
    super.setPresenter(activity);
    adminTab.setPresenter(activity);
  }
  
  public void setModel(Object model, String tag) {
    adminTab.setModel(model, null);
  }
  
}
