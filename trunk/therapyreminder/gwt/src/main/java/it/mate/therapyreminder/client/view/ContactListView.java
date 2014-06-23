package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.TouchHTML;
import it.mate.phgcommons.client.ui.ph.PhCheckBox;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.phgcommons.client.utils.TouchUtils;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.view.ContactListView.Presenter;
import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.impl.ContattoTx;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.dom.client.recognizer.longtap.LongTapEvent;
import com.googlecode.mgwt.dom.client.recognizer.longtap.LongTapHandler;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.event.scroll.ScrollMoveEvent;

public class ContactListView extends BaseMgwtView <Presenter> {
  
  public static final String TAG_CONTATTI = "contatti";

  public static final String TAG_TIPO_CONTATTO = "tipoContatto";

  public interface Presenter extends BasePresenter {
    public void goToContactEditView(Contatto contatto);
    public void deleteContatto(Contatto contatto);
  }

  public interface ViewUiBinder extends UiBinder<Widget, ContactListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  
  @UiField ScrollPanel resultsPanel;
  
  private boolean scrollInProgress = false;
  
  private Contatto selectedContatto = null;
  
  private String tipoContatto = null;
  
  public ContactListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getHeaderBackButton().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    
    getScrollPanel().setScrollingEnabledX(false);
    getScrollPanel().setScrollingEnabledY(false);
    resultsPanel.setScrollingEnabledY(true);
    
    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        int resultsHeight = Window.getClientHeight() - resultsPanel.getAbsoluteTop();
        resultsPanel.getElement().getStyle().setHeight(resultsHeight, Unit.PX);
      }
    });
    
    resultsPanel.addScrollMoveHandler(new ScrollMoveEvent.Handler() {
      public void onScrollMove(ScrollMoveEvent event) {
        scrollInProgress = true;
      }
    });
    resultsPanel.addScrollEndHandler(new ScrollEndEvent.Handler() {
      public void onScrollEnd(ScrollEndEvent event) {
        scrollInProgress = false;
      }
    });
    
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
  }

  @Override
  public void setModel(Object model, String tag) {
    if (TAG_TIPO_CONTATTO.equals(tag)) {
      tipoContatto = (String)model;
    } else if (TAG_CONTATTI.equals(tag)) {
      @SuppressWarnings("unchecked")
      List<Contatto> contatti = (List<Contatto>)model;
      showListaContatti(contatti);
    }
  }
  
  @UiHandler ("newBtn")
  public void onNewBtn (TouchEndEvent event) {
    getPresenter().goToContactEditView(new ContattoTx(tipoContatto));
  }
  
  private void showListaContatti(List<Contatto> contatti) {
    resultsPanel.clear();
    if (contatti == null || contatti.size() == 0) {
      Label noResultsLbl = new Label("You have no contacts of this type.");
      noResultsLbl.addStyleName("ui-no-results");
      resultsPanel.add(noResultsLbl);
      return;
    }
    Collections.sort(contatti, new Comparator<Contatto>() {
      public int compare(Contatto c1, Contatto c2) {
        return c1.getNome().compareTo(c2.getNome());
      }
    });
    SimpleContainer list = new SimpleContainer();
    for (final Contatto contatto : contatti) {
      HorizontalPanel row = new HorizontalPanel();
      row.addStyleName("ui-row");
      list.add(row);
      PhCheckBox check = new PhCheckBox();
      /*
      check.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (!scrollInProgress) {
            if (event.getValue()) {
              selectedPrescrizioni.add(prescrizione);
            } else {
              selectedPrescrizioni.remove(prescrizione);
            }
          }
        }
      });
      */
      row.add(check);
      TouchHTML rowHtml = new TouchHTML("<p class='ui-row-subject'>" + contatto.getNome() + "</p>");
      row.add(rowHtml);
      rowHtml.addTouchEndHandler(new TouchEndHandler() {
        public void onTouchEnd(TouchEndEvent event) {
          if (!scrollInProgress) {
            getPresenter().goToContactEditView(contatto);
          }
        }
      });
      
      rowHtml.addLongTapHandler(new LongTapHandler() {
        public void onLongTap(LongTapEvent event) {
          PhonegapUtils.log("long tapped " + contatto);
//        getPresenter().goToTherapyEditView(prescrizione);
        }
      });
      
    }
    resultsPanel.add(list);
    TouchUtils.applyFocusPatch();
    GwtUtils.deferredExecution(500, new Delegate<Void>() {
      public void execute(Void element) {
        resultsPanel.setHeight("" + (Window.getClientHeight() - resultsPanel.getAbsoluteTop()) + "px");
      }
    });
  }

}
