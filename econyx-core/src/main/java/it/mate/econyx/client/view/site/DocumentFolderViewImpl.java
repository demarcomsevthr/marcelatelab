package it.mate.econyx.client.view.site;

import it.mate.econyx.client.util.UrlUtils;
import it.mate.econyx.client.view.DocumentFolderView;
import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class DocumentFolderViewImpl extends AbstractBaseView<DocumentFolderView.Presenter> implements DocumentFolderView {

  public interface ViewUiBinder extends UiBinder<Widget, DocumentFolderViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField FlexTable documentsTable;
  
  private DocumentFolder documentFolder;
  
  public DocumentFolderViewImpl() {
    super();
    initUI();
  }
  
  private void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  private void initProvided() {
    
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof PortalSessionState) {
      //
    } else if (model instanceof DocumentFolder) {
      this.documentFolder = (DocumentFolder)model;
      documentsTable.clear();
      if (documentFolder.getDocuments() != null) {
        int row = 0;
        for (Document document : documentFolder.getDocuments()) {
          HTML title = new HTML(document.getTitle());
          documentsTable.setWidget(row, 0, title);
          Anchor docLink = new Anchor(UrlUtils.getDocumentContentUrl(document.getCode()), UrlUtils.getDocumentContentUrl(document.getCode()));
          docLink.setTarget(document.getTitle());
          documentsTable.setWidget(row, 1, docLink);
          HTML author = new HTML(document.getAuthor().getScreenName());
          documentsTable.setWidget(row, 2, author);
          HTML created = new HTML(GwtUtils.dateToString(document.getCreated()));
          documentsTable.setWidget(row, 3, created);
          row++;
        }
      }
    }
  }
  
}
