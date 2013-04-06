package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.DocumentFolderListView;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.impl.DocumentFolderTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.MessageBox.Callbacks;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DocumentFolderListViewImpl extends AbstractBaseView<DocumentFolderListView.Presenter> implements DocumentFolderListView {
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentFolderListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) AdminTabPanel<DocumentFolderListView.Presenter> adminTab;
  
  private String width;
  
  private String height;
  
  public DocumentFolderListViewImpl() {
    this(null, null);
  }
  
  public DocumentFolderListViewImpl(String width, String height) {
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
        .setText("Raccolte")
        .setView(new DocumentFolderListGeneralView()));
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
  
  public class DocumentFolderOptionDialog {
    TextBox name = new TextBox();
    public DocumentFolderOptionDialog(final Delegate<DocumentFolder> delegate) {
      VerticalPanel table = new VerticalPanel();
      HorizontalPanel row;
      
      row = new HorizontalPanel();
      row.add(new Spacer("1px", "2em"));
      Label label = new Label("Nome:");
      label.setWidth("6em");
      row.add(label);
      row.add(name);
      table.add(row);
      
      MessageBoxUtils.popupOkCancel("Nuova Raccolta", table, "400px", new Delegate<MessageBox.Callbacks>() {
        public void execute(Callbacks callbacks) {
          DocumentFolder folder = new DocumentFolderTx();
          folder.setName(name.getText());
          delegate.execute(folder);
        }
      }, new Delegate<DialogBox>() {
        public void execute(DialogBox element) {
          name.setFocus(true);
        }
      });
      
    }
  }
  
  protected void initProvided() {  
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setNewButtonEnabled(true).setWidth(width).setHeight(height).setEditButtonEnabled(true)
        .setDeleteButtonEnabled(true)) {
      public void onSave(Object model) { }
      public void onNewModelRequested() {
        new DocumentFolderOptionDialog(new Delegate<DocumentFolder>() {
          public void execute(DocumentFolder folder) {
            getPresenter().edit(folder);
          }
        });
      }
      public void onEdit(Object model) {
        if (model instanceof DocumentFolder) {
          getPresenter().edit((DocumentFolder)model);
        }
      }
      public void onDelete(Object model) {
        DocumentFolder folder = (DocumentFolder)model;
        getPresenter().delete(folder);
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
