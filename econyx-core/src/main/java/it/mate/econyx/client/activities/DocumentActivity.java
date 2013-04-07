package it.mate.econyx.client.activities;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.DocumentPlace;
import it.mate.econyx.client.view.DocumentEditView;
import it.mate.econyx.client.view.DocumentFolderEditView;
import it.mate.econyx.client.view.DocumentFolderListView;
import it.mate.econyx.client.view.DocumentFolderView;
import it.mate.econyx.client.view.DocumentView;
import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.impl.DocumentTx;
import it.mate.econyx.shared.services.DocumentServiceAsync;
import it.mate.econyx.shared.services.GeneralServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class DocumentActivity extends BaseActivity implements 
    DocumentFolderView.Presenter,
    DocumentFolderListView.Presenter,
    DocumentFolderEditView.Presenter,
    DocumentView.Presenter,
    DocumentEditView.Presenter {

  private DocumentPlace place;
  
  private DocumentServiceAsync documentService = AppClientFactory.IMPL.getGinjector().getDocumentService();
  
  private GeneralServiceAsync generalService = AppClientFactory.IMPL.getGinjector().getGeneralService();
  
  public DocumentActivity(DocumentPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    registerHandlers(eventBus);
    if (place.getToken().equals(DocumentPlace.FOLDER_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getDocumentFolderView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(DocumentPlace.FOLDER_LIST)) {
      initView(AppClientFactory.IMPL.getGinjector().getDocumentFolderListView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(DocumentPlace.FOLDER_EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getDocumentFolderEditView(), panel);
      retrieveModel();
    }
    /*
    if (place.getToken().equals(DocumentPlace.DOCUMENT_VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getDocumentView(), panel);
      retrieveModel();
    }
    */
    if (place.getToken().equals(DocumentPlace.DOCUMENT_EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getDocumentEditView(), panel);
      retrieveModel();
    }
  }
  
  private void retrieveModel() {
    getView().setModel(AppClientFactory.IMPL.getPortalSessionState());
    if (place.getToken().equals(DocumentPlace.FOLDER_VIEW)) {
      getView().setModel(place.getModel());
    }
    if (place.getToken().equals(DocumentPlace.FOLDER_LIST)) {
      documentService.findAllFolders(new AsyncCallback<List<DocumentFolder>>() {
        public void onSuccess(List<DocumentFolder> results) {
          getView().setModel(results);
        }
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
      });
    }
    if (place.getToken().equals(DocumentPlace.FOLDER_EDIT)) {
      getView().setModel(place.getModel());
    }
    /*
    if (place.getToken().equals(DocumentPlace.DOCUMENT_VIEW)) {
      getView().setModel(place.getModel());
    }
    */
    if (place.getToken().equals(DocumentPlace.DOCUMENT_EDIT)) {
      getView().setModel(place.getModel());
    }
  }
  
  private void registerHandlers(EventBus eventBus) {

  }

  @Override
  public void onDispose() {
    GwtUtils.log("disposing " + this);
    super.onDispose();
  }
  
  @Override
  public void edit(DocumentFolder folder) {
    goTo(new DocumentPlace(DocumentPlace.FOLDER_EDIT, folder));
  }

  @Override
  public void update(DocumentFolder folder) {
    if (folder.getId() == null) {
      documentService.createFolder(folder, new AsyncCallback<DocumentFolder>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(DocumentFolder result) {
          edit(result);
        }
      });
    } else {
      documentService.updateFolder(folder, new AsyncCallback<DocumentFolder>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(DocumentFolder result) {
          edit(result);
        }
      });
    }
  }

  @Override
  public void delete(DocumentFolder folder) {
    documentService.deleteFolder(folder, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        goTo(new DocumentPlace(DocumentPlace.FOLDER_LIST));
      }
    });
  }

  @Override
  public void edit(Document document) {
    goTo(new DocumentPlace(DocumentPlace.DOCUMENT_EDIT, document));
  }

  @Override
  public void update(Document document) {
    if (document.getAuthor() == null) {
      document.setAuthor(AppClientFactory.IMPL.getPortalSessionState().getLoggedUser());
    }
    if (document.getId() == null) {
      DocumentFolder documentFolder = ((DocumentTx)document).getDocumentFolder();
      documentFolder.getDocuments().add(document);
      documentService.updateFolder(documentFolder, new AsyncCallback<DocumentFolder>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(DocumentFolder documentFolder) {
          goTo(new DocumentPlace(DocumentPlace.FOLDER_EDIT, documentFolder));
        }
      });
    } else {
      documentService.updateDocument(document, new AsyncCallback<Document>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Document document) {
          goTo(new DocumentPlace(DocumentPlace.DOCUMENT_EDIT, document));
        }
      });
    }
  }
  
  public void createBlobstoreUploadUrl (String url, final Delegate<String> delegate) {
    generalService.createBlobstoreUploadUrl(url, new AsyncCallback<String>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(String result) {
        delegate.execute(result);
      }
    });
  }

}
