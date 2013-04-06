package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.DocumentListView;
import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.impl.DocumentTx;
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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DocumentListViewImpl extends AbstractBaseView<DocumentListView.Presenter> implements DocumentListView {
  
  public interface ViewUiBinder extends UiBinder<Widget, DocumentListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) AdminTabPanel<DocumentListView.Presenter> adminTab;
  
  private String width;
  
  private String height;
  
  private DocumentFolder documentFolder;
  
  public DocumentListViewImpl() {
    this(null, null);
  }
  
  public DocumentListViewImpl(String width, String height) {
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
        .setText("Generale")
        .setView(new DocumentListGeneralView()));
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
  
  public class DocumentOptionDialog {
    TextBox name = new TextBox();
    public DocumentOptionDialog(final Delegate<Document> delegate) {
      VerticalPanel table = new VerticalPanel();
      HorizontalPanel row;
      
      row = new HorizontalPanel();
      row.add(new Spacer("1px", "2em"));
      Label label = new Label("Titolo:");
      label.setWidth("6em");
      row.add(label);
      row.add(name);
      table.add(row);
      
      MessageBoxUtils.popupOkCancel("Nuovo Documento", table, "400px", new Delegate<MessageBox.Callbacks>() {
        public void execute(Callbacks callbacks) {
          Document document = new DocumentTx();
          document.setName(name.getText());
          ((DocumentTx)document).setDocumentFolder(documentFolder);
          document.setCreated(new Date());
          delegate.execute(document);
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
      public void onSave(Object model) { 

      }
      public void onNewModelRequested() {
        new DocumentOptionDialog(new Delegate<Document>() {
          public void execute(Document document) {
            getPresenter().edit(document);
          }
        });
      }
      public void onEdit(Object model) {
        if (model instanceof Document) {
          getPresenter().edit((Document)model);
        }
      }
      public void onDelete(Object model) {
        Document Document = (Document)model;
        getPresenter().delete(Document);
      }
    };
  }
  
  @Override
  public void setPresenter(Presenter activity) {
    super.setPresenter(activity);
    adminTab.setPresenter(activity);
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof DocumentFolder) {
      this.documentFolder = (DocumentFolder)model;
      adminTab.setModel(documentFolder.getDocuments());
    }
  }
  
}
