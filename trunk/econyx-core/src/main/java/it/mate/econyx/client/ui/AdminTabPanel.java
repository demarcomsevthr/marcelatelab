package it.mate.econyx.client.ui;

import it.mate.econyx.client.events.ModelUdateEvent;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class AdminTabPanel <P extends BasePresenter> extends Composite implements BaseView<P> {

  public abstract void onSave(Object model);
  
  public abstract void onNewModelRequested();
  
  public void onEdit(Object model) { }
  
  public void onDelete(Object model) { }
  
  private TabLayoutPanel tabLayoutPanel;
  
  private List<Section<P>> sections;
  
  private Object model;
  
  private Configuration config;
  
  private HorizontalPanel buttonBar;
  
  public AdminTabPanel(Configuration config) {
    this.config = config;
    initUI();
  }
  
  @Override
  public void setWidth(String width) {
//  super.setWidth(width);
    tabLayoutPanel.setWidth(width);
  }
  
  @Override
  public void setHeight(String height) {
//  super.setHeight(height);
    tabLayoutPanel.setHeight(height);
  }
  
  protected void initUI() {
    VerticalPanel vPanel = new VerticalPanel();

    tabLayoutPanel = new TabLayoutPanel(1.5, Unit.EM);
    tabLayoutPanel.setWidth(config.width != null ? config.width : AdminClientUtils.defaultWidth());
    tabLayoutPanel.setHeight(config.height != null ? config.height : AdminClientUtils.defaultHeight());
    
    if (config.height == null) {
      // 30/11/2012
//    ResizeUtils.setHeightRelativeToScreen(tabLayoutPanel, AdminClientUtils.adminTabPanelRelativeHeight());
      AdminClientUtils.applyDefaultResizePolicy(tabLayoutPanel);
    }
    
    vPanel.add(tabLayoutPanel);
    
    vPanel.add(new Spacer("1px", "6px"));

    this.buttonBar = new HorizontalPanel();
    
    if (config.saveButtonEnabled) {
      addButton(createButton("Salva", new ClickHandler() {
        public void onClick(ClickEvent event) {
          updateNextSection(sections.iterator());
          AppClientFactory.IMPL.getEventBus().fireEvent(new ModelUdateEvent(model));
        }
      }));
    }
    if (config.newButtonEnabled) {
      addButton(createButton("Nuovo", new ClickHandler() {
        public void onClick(ClickEvent event) {
          onNewModelRequested();
        }
      }));
    }
    if (config.editButtonEnabled) {
      addButton(createButton("Modifica", new ClickHandler() {
        public void onClick(ClickEvent event) {
          for (Section<P> section : sections) {
            Object selectedObject = section.view.getSelectedObject();
            if (selectedObject != null) {
              onEdit(selectedObject);
              break;
            }
          }
        }
      }));
    }
    
    if (config.deleteButtonEnabled) {
      addButton(createButton("Cancella", new ClickHandler() {
        public void onClick(ClickEvent event) {
          for (Section<P> section : sections) {
            final Object selectedObject = section.view.getSelectedObject();
            if (selectedObject != null) {
              GwtUtils.messageBox("Confermi la cancellazione dell'oggetto selezionato?", MessageBox.BUTTONS_YESNO, MessageBox.ICON_QUESTION,
                  new MessageBox.Callbacks() {
                    public void onYes() {
                      AdminTabPanel.this.onDelete(selectedObject);
                    }
                  });
              break;
            }
          }
        }
      }));
    }
    
    vPanel.add(buttonBar);
    
    initWidget(vPanel);
  }
  
  public void setTabHeight(String height) {
    tabLayoutPanel.setHeight(height);
  }
  
  public void setTabWidth(String width) {
    tabLayoutPanel.setWidth(width);
  }
  
  public void addButton (Button button) {
    if (buttonBar.getWidgetCount() > 0) {
      buttonBar.add(new Spacer("6px"));
    }
    buttonBar.add(button);
  }
  
  private Button createButton(String text, ClickHandler handler) {
    Button btn = new Button(text, handler);
    return btn;
  }
  
  private void updateNextSection(final Iterator<Section<P>> iterator) {
    if (iterator.hasNext()) {
      Section<P> section = iterator.next();
      section.view.updateModel(model, new Delegate<Object>() {
        public void execute(Object model) {
          if (model != null) {
            AdminTabPanel.this.model = model;
          }
          updateNextSection(iterator);
        }
      });
    } else {
      onSave(model);
    }
  }
  
  public void setSections (List<Section<P>> sections) {
    this.sections = sections;
    for (Section<P> section : sections) {
      tabLayoutPanel.add(section.view, section.text);
    }
  }

  @Override
  public void setPresenter(P activity) {
    for (Section<P> section : sections) {
      section.view.setPresenter(activity);
    }
  }
  
  @Override
  public void setModel(Object model) {
    setModel(model, null);
  }

  @Override
  public void setModel(Object model, String tag) {
    this.model = model;
    for (Section<P> section : sections) {
      section.view.setModel(model, null);
    }
  }
  
  public static class Section <A extends BasePresenter> {
    private IsAdminTabPage<A> view;
    private String text;
    public Section<A> setView(IsAdminTabPage<A> view) {
      this.view = view;
      return this;
    }
    public Section<A> setText(String text) {
      this.text = text;
      return this;
    }
  }
  
  public static class Configuration {
    private boolean saveButtonEnabled = false;
    private boolean newButtonEnabled = false;
    private boolean editButtonEnabled = false;
    private boolean deleteButtonEnabled = false;
    private String width;
    private String height;
    public Configuration setSaveButtonEnabled(boolean saveButtonEnabled) {
      this.saveButtonEnabled = saveButtonEnabled;
      return this;
    }
    public Configuration setNewButtonEnabled(boolean newButtonEnabled) {
      this.newButtonEnabled = newButtonEnabled;
      return this;
    }
    public Configuration setEditButtonEnabled(boolean editButtonEnabled) {
      this.editButtonEnabled = editButtonEnabled;
      return this;
    }
    public Configuration setWidth(String width) {
      this.width = width;
      return this;
    }
    public Configuration setHeight(String height) {
      this.height = height;
      return this;
    }
    public Configuration setDeleteButtonEnabled(boolean deleteButtonEnabled) {
      this.deleteButtonEnabled = deleteButtonEnabled;
      return this;
    }
  }
  
}
