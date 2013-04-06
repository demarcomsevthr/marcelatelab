package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.DocumentFolderEditView;
import it.mate.econyx.client.view.DocumentListView;
import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

public class DocumentFolderEditChildreenListView extends AbstractAdminTabPage<DocumentFolderEditView.Presenter> implements DocumentFolderEditView, IsAdminTabPage<DocumentFolderEditView.Presenter> {
  
  private DocumentListView itemListView;
  
  private DocumentFolder documentFolder;
  
  public DocumentFolderEditChildreenListView() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(itemListView.asWidget());
  }
  
  protected void initProvided() {
    
    itemListView = new DocumentListViewImpl("640px", "480px");
    
    itemListView.setPresenter(new DocumentListView.Presenter() {
      public BaseView getView() {
        return null;
      }
      public void goToPrevious() {  }
      public void edit(Document document) {
        GwtUtils.log(getClass(), "edit", "selected document " + document);
        DocumentFolderEditChildreenListView.this.getPresenter().edit(document);
      }
      /*
      public void newInstance(String classname, final Delegate<Document> delegate) {  
        if (DocumentFolderEditItemListView.this.getPresenter() instanceof DocumentActivity && documentFolder != null) {
          DocumentActivity documentActivity = (DocumentActivity)DocumentFolderEditItemListView.this.getPresenter();
          documentActivity.newInstance(classname, new Delegate<Document>() {
            public void execute(Document newPage) {
              newPage.setParentId(documentFolder.getId());
              delegate.execute(newPage);
            }
          });
        }
      }
      */
      /*
      public void delete(Document document) {
        if (DocumentFolderEditChildreenListView.this.getPresenter() instanceof DocumentActivity) {
          DocumentActivity documentActivity = (DocumentActivity)DocumentFolderEditChildreenListView.this.getPresenter();
          documentActivity.delete(document);
        }
      }
      */
      public void delete(Document document) {

      }
      /*
      public void save(Document updatedDocument) {
        boolean documentFolderUpdate = false;
        if (updatedDocument.getId() == null) {
          documentFolder.getDocuments().add(updatedDocument);
          documentFolderUpdate = true;
        } else {
          for (int it = 0; it < documentFolder.getDocuments().size(); it++) {
            Document attachedDocument = (Document)documentFolder.getDocuments().get(it);
            if (attachedDocument.getId().equals(updatedDocument.getId())) {
              documentFolder.getDocuments().set(it, updatedDocument);
              documentFolderUpdate = true;
              break;
            }
          }
        }
        if (documentFolderUpdate) {
          getPresenter().update(documentFolder);
        }
      }
      */
    });
    
  }
  
  public void setModel(Object model, String tag) {
    if (model instanceof DocumentFolder) {
      this.documentFolder = (DocumentFolder)model;
      itemListView.setModel(documentFolder);
    }
  }
  
  @Override
  protected void onAttach() {
    super.onAttach();
    GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        int w = DocumentFolderEditChildreenListView.this.getParent().getOffsetWidth() - 60;
        int h = DocumentFolderEditChildreenListView.this.getParent().getOffsetHeight() - 60;
        if (w > 0 && h > 0) {
          itemListView.setWidth(w+"px");
          itemListView.setHeight(h+"px");
        }
      }
    });
  }

  @Override
  public void updateModel(Object model, final Delegate<Object> delegate) {
    delegate.execute(model);
  }
  
}
