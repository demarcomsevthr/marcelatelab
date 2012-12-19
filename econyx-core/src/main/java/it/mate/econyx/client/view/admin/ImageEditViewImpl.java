package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.ui.AdminTabPanel;
import it.mate.econyx.client.view.ImageEditView;
import it.mate.econyx.shared.model.Image;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class ImageEditViewImpl extends AbstractBaseView<ImageEditView.Presenter> implements ImageEditView {

  public interface ViewUiBinder extends UiBinder<Widget, ImageEditViewImpl> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField (provided=true) AdminTabPanel<ImageEditView.Presenter> adminTab;

  public ImageEditViewImpl() {
    initUI();
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }

  protected void initProvided() {
    adminTab = new AdminTabPanel<Presenter>(
        new AdminTabPanel.Configuration().setSaveButtonEnabled(true)) {
      @Override
      public void onSave(Object model) {  
        getPresenter().update((Image)model);
      }
      @Override
      public void onNewModelRequested() { }
    };
    
  }

  private void initSections(Image image) {
    List<AdminTabPanel.Section<Presenter>> sections = new ArrayList<AdminTabPanel.Section<ImageEditView.Presenter>>();
    sections.add(new AdminTabPanel.Section<Presenter>()
        .setText("Generale")
        .setView(new ImageEditGeneralView()));
    adminTab.setSections(sections);
  }
  
  @Override
  public void setModel(Object model, String tag) {
    Image image = (Image)model;
    initSections(image);
    adminTab.setPresenter(getPresenter());
    adminTab.setModel(model, null);
  }
  
}
