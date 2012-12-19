package it.mate.econyx.client.activities;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.ImagePlace;
import it.mate.econyx.client.view.ImageEditView;
import it.mate.econyx.client.view.ImageListView;
import it.mate.econyx.shared.model.Image;
import it.mate.econyx.shared.services.ImageServiceAsync;
import it.mate.gwtcommons.client.mvp.BaseActivity;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ImageActivity extends BaseActivity implements 
    ImageListView.Presenter, ImageEditView.Presenter {

  private ImagePlace place;
  
  private ImageServiceAsync imageService = AppClientFactory.IMPL.getGinjector().getImageService();
  
  public ImageActivity(ImagePlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    registerHandlers(eventBus);
    if (place.getToken().equals(ImagePlace.LIST)) {
      initView(AppClientFactory.IMPL.getGinjector().getImageListView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(ImagePlace.EDIT)) {
      initView(AppClientFactory.IMPL.getGinjector().getImageEditView(), panel);
      retrieveModel();
    }
    if (place.getToken().equals(ImagePlace.VIEW)) {
//    initView(AppClientFactory.IMPL.getGinjector().getOrderView(), panel);
      retrieveModel();
    }
  }
  
  private void registerHandlers(EventBus eventBus) {

  }
  
  @Override
  public void onDispose() {
    super.onDispose();
  }
  
  private void retrieveModel() {
    if (place.getToken().equals(ImagePlace.LIST)) {
      if (AppClientFactory.isAdminModule) {
        imageService.findAll(new AsyncCallback<List<Image>>() {
          public void onSuccess(List<Image> images) {
            getView().setModel(images, null);
          }
          public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
          }
        });
      }
    } else if (place.getToken().equals(ImagePlace.EDIT)) {
      getView().setModel(place.getModel(), null);
    } else if (place.getToken().equals(ImagePlace.VIEW)) {
      getView().setModel(place.getModel(), null);
    } else {
      getView().setModel(null, null);
    }
  }
  
  public void editNew(Image image) {
    imageService.create(image, new AsyncCallback<Image>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Image image) {
        edit(image);
      }
    });
  }
  
  @Override
  public void edit(Image image) {
    goTo(new ImagePlace(ImagePlace.EDIT, image));
  }

  @Override
  public void update(Image image) {
    if (image.getId() == null) {
      imageService.create(image, new AsyncCallback<Image>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Image image) {
          refresh(image);
        }
      });
    } else {
      imageService.update(image, new AsyncCallback<Image>() {
        public void onFailure(Throwable caught) {
          Window.alert(caught.getMessage());
        }
        public void onSuccess(Image image) {
          refresh(image);
        }
      });
    }
  }

  @Override
  public void refresh(Image image) {
    imageService.findById(image.getId(), new AsyncCallback<Image>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Image image) {
        edit(image);
      }
    });
  }
  
  public void delete(final Image image) {
    
    imageService.delete(image, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        Window.alert(caught.getMessage());
      }
      public void onSuccess(Void result) {
        goTo(new ImagePlace(ImagePlace.LIST));
      }
    });
    
  }
  
  
}
