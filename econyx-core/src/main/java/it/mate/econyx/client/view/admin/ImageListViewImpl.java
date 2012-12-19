package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.ui.ImageOptionsDialog;
import it.mate.econyx.client.ui.ImageOptionsDialog.Options;
import it.mate.econyx.client.view.ImageListView;
import it.mate.econyx.shared.model.Image;
import it.mate.econyx.shared.model.impl.ImageTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.utils.Delegate;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ImageListViewImpl extends AbstractBaseView<ImageListView.Presenter> implements ImageListView {

  public interface ViewUiBinder extends UiBinder<Widget, ImageListViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  
  @UiField (provided=true) AdminTabPanel<ImageListView.Presenter> adminTab;

  private String width;
  
  private String height;
  
  
  public ImageListViewImpl() {
    initUI();
  }
  
  public ImageListViewImpl(String width, String height) {
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
        .setView(new ImageListGeneralView()));
    adminTab.setSections(sections);
  }
  
  protected void initProvided() {
    
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setNewButtonEnabled(true).setWidth(width).setHeight(height)
                        .setEditButtonEnabled(true).setDeleteButtonEnabled(true)) {
      public void onSave(Object model) { }
      public void onNewModelRequested() { 
//      getPresenter().edit(new ImageTx());
        
        new ImageOptionsDialog(new Delegate<ImageOptionsDialog.Options>() {
          public void execute(final Options imageOptions) {
            
            Image image = new ImageTx();
            image.setName(imageOptions.getName());
            image.setCode(imageOptions.getName());
            
            getPresenter().editNew(image);
            
          }
        });
        
        
      }
      
      @Override
      public void onEdit(Object model) {
        Image image = (Image)model;
        getPresenter().edit(image);
      }
      
      @Override
      public void onDelete(Object model) {
        Image image = (Image)model;
        getPresenter().delete(image);
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
