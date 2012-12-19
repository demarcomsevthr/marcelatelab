package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.factories.CustomClientFactory;
import it.mate.econyx.client.text.CurrencyBox;
import it.mate.econyx.client.ui.AbstractAdminTabPage;
import it.mate.econyx.client.ui.IsAdminTabPage;
import it.mate.econyx.client.view.PortalUserEditView;
import it.mate.econyx.shared.model.ContoUtente;
import it.mate.econyx.shared.model.ContoUtenteMovimento;
import it.mate.econyx.shared.model.PortalSessionState;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.ContoUtenteMovimentoTx;
import it.mate.gwtcommons.client.ui.AnchorCell;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.ProvidesKey;

public class PortalUserEditContoView extends AbstractAdminTabPage<PortalUserEditView.Presenter> implements PortalUserEditView, IsAdminTabPage<PortalUserEditView.Presenter> {

  public interface ViewUiBinder extends UiBinder<Widget, PortalUserEditContoView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField CurrencyBox saldoBox;
  @UiField (provided=true) CellTableExt<ContoUtenteMovimento> movimentiTable;
  @UiField Panel pagerPanel;
  @UiField Label alertLabel;
  
  private CustomClientFactory customClientFactory = AppClientFactory.Customizer.cast(CustomClientFactory.class);
  
  private PortalUser user;
  
  private ContoUtente contoUtente;
  
  public PortalUserEditContoView() {
    initUI();
  }
  
  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
    ProvidesKey<ContoUtenteMovimento> keyProvider = new ProvidesKey<ContoUtenteMovimento>() {
      public Object getKey(ContoUtenteMovimento item) {
        return item.getId();
      }
    };
    
    movimentiTable = new CellTableExt<ContoUtenteMovimento>(keyProvider);
    
    movimentiTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);

    movimentiTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<ContoUtenteMovimento, String>() {
      public String getValue(ContoUtenteMovimento item) {
        return GwtUtils.dateToString(item.getData(), 10);
      }
    }, new TextCell(), null), "Data");
    
    movimentiTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<ContoUtenteMovimento, String>() {
      public String getValue(ContoUtenteMovimento item) {
        return ContoUtenteMovimento.POSITIVO.equals(item.getSegno()) ? "+" : "-";
      }
    }, new TextCell(), null), "Segno");
    
    movimentiTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<ContoUtenteMovimento, String>() {
      public String getValue(ContoUtenteMovimento item) {
        return GwtUtils.formatCurrency(item.getImporto());
      }
    }, new TextCell(), null), "Importo");
    
    movimentiTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<ContoUtenteMovimento, String>() {
      public String getValue(ContoUtenteMovimento item) {
        return item.getCausale();
      }
    }, new TextCell(), null), "Causale");
    
    
    movimentiTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<ContoUtenteMovimento>(), 
    new AnchorCell<ContoUtenteMovimento>() {
      protected String getCellValue(ContoUtenteMovimento movimento) {
        if (movimento.getOrder() != null) {
          return movimento.getOrder().getCode();
        }
        return "";
      }
      protected void onConsumedEvent(NativeEvent event, ContoUtenteMovimento movimento) {
        if (movimento.getOrder() != null) {
          getPresenter().editOrder(movimento.getOrder());
        }
      }
    }, 
    null), "Ordine");
    
    movimentiTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<ContoUtenteMovimento, String>() {
      public String getValue(ContoUtenteMovimento item) {
        return item.getRegisteringPortalUser() != null ? item.getRegisteringPortalUser().getScreenName() : "";
      }
    }, new TextCell(), null), "Referente");
    
    movimentiTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.SimpleValueGetter<ContoUtenteMovimento>(), 
    new AnchorCell<ContoUtenteMovimento>() {
      protected String getCellValue(ContoUtenteMovimento model) {
        return "storna";
      }
      protected void onConsumedEvent(NativeEvent event, final ContoUtenteMovimento movimentoDaStornare) {
        MessageBox.create(new MessageBox.Configuration()
          .setBodyText("Confermi lo storno del'operazione?")
          .setButtonType(MessageBox.BUTTONS_YESNO)
          .setIconType(MessageBox.ICON_QUESTION)
          .setCallbacks(new MessageBox.Callbacks() {
            public void onYes() {
              if (movimentoDaStornare.getId() != null) {
                for (Iterator<ContoUtenteMovimento> it = contoUtente.getMovimenti().iterator(); it.hasNext();) {
                  ContoUtenteMovimento item = it.next();
                  if (movimentoDaStornare.getId().equals(item.getId())) {
                    it.remove();
                    customClientFactory.updateContoUtente(contoUtente, new Delegate<ContoUtente>() {
                      public void execute(ContoUtente result) {
                        setContoUtente(result);
                      }
                    });
                    break;
                  }
                }
              }
            }
          })
        );
      }
    }, 
    null), "");
    
    
    movimentiTable.addFillerColumn();
    
  }

  public void setModel(Object model, String tag) {
    if (model instanceof PortalUser) {
      this.user = (PortalUser)model;
      customClientFactory.findContoUtenteByPortalUser(user.getId(), new Delegate<ContoUtente>() {
        public void execute(ContoUtente result) {
          setContoUtente(result);
        }
      });
    }
  }
  
  private void setContoUtente(ContoUtente contoUtente) {
    this.contoUtente = contoUtente;
    saldoBox.setValue(contoUtente.getSaldo());
    if (contoUtente.getSaldo() < 0) {
      alertLabel.setText("Attenzione: saldo negativo!");
      alertLabel.setVisible(true);
    } else {
      alertLabel.setVisible(false);
    }
    List<ContoUtenteMovimento> movimenti = contoUtente.getMovimenti();
    if (movimenti != null) {
      Collections.sort(movimenti, new Comparator<ContoUtenteMovimento>() {
        public int compare(ContoUtenteMovimento m1, ContoUtenteMovimento m2) {
          return m2.getData().compareTo(m1.getData());
        }
      });
      movimentiTable.setRowDataExt(contoUtente.getMovimenti());
      movimentiTable.adaptToViewHeight(this, new Delegate<SimplePager>() {
        public void execute(SimplePager pager) {
          pagerPanel.clear();
          pagerPanel.add(pager);
        }
      });
    } else {
      movimentiTable.setRowCount(0);
    }
  }

  public void updateModel(final Object model, final Delegate<Object> delegate) {
    PortalUser user = (PortalUser)model;
    delegate.execute(user);
  }

  @UiHandler ("inserisciBtn")
  public void onInserisciBtn (ClickEvent event) {
    
    VerticalPanel popupPanel = new VerticalPanel();
    
    final CurrencyBox importoBox = new CurrencyBox();
    popupPanel.add(createMovimentoItemPanel("Importo:", importoBox));
    
    final ListBox segnoBox = new ListBox();
    segnoBox.addItem("ACCREDITO", ContoUtenteMovimento.POSITIVO);
    segnoBox.addItem("ADDEBITO", ContoUtenteMovimento.NEGATIVO);
    popupPanel.add(createMovimentoItemPanel("Segno:", segnoBox));
    
    final TextBox causaleBox = new TextBox();
    popupPanel.add(createMovimentoItemPanel("Causale:", causaleBox));
    
    final DateBox dataBox = new DateBox(new DatePicker(), new Date(), new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
    dataBox.getTextBox().setReadOnly(true);
    popupPanel.add(createMovimentoItemPanel("Data:", dataBox));
    
    MessageBoxUtils.popupOkCancel("Inserire gli estremi del movimento", popupPanel, "400px", new Delegate<MessageBox.Callbacks> () {
      public void execute(MessageBox.Callbacks callbacks) {
        ContoUtenteMovimento movimento = new ContoUtenteMovimentoTx();
        movimento.setImporto(importoBox.getValue());
        movimento.setSegno(segnoBox.getValue(segnoBox.getSelectedIndex()));
        movimento.setCausale(causaleBox.getValue());
        movimento.setData(dataBox.getValue());
        PortalSessionState portalSessionState = AppClientFactory.IMPL.getPortalSessionState();
        if (portalSessionState != null) {
          movimento.setRegisteringPortalUser(portalSessionState.getLoggedUser());
        }
        
        if (movimento.getImporto() == null || movimento.getImporto() == 0) {
          Window.alert("Inserire un importo non nullo");
          callbacks.cancelClose();
          return;
        }
        
        if (movimento.getImporto() < 0) {
          Window.alert("Impossibile inserire un importo negativo! (utilizzare la tipologia 'addebito' per indicare un'operazione negativa)");
          callbacks.cancelClose();
          return;
        }
        
        if (contoUtente.getMovimenti() == null) {
          contoUtente.setMovimenti(new ArrayList<ContoUtenteMovimento>());
        }
        contoUtente.getMovimenti().add(movimento);
        
        customClientFactory.updateContoUtente(contoUtente, new Delegate<ContoUtente>() {
          public void execute(ContoUtente result) {
            setContoUtente(result);
          }
        });
      }
    });

    /*
    MessageBox.create(new MessageBox.Configuration()
      .setCaptionText("Inserire gli estremi del movimento")
      .setButtonType(MessageBox.BUTTONS_OKCANCEL)
      .setIconType(MessageBox.ICON_INFO)
      .setBodyWidget(popupPanel)
      .setBodyWidth("400px")
      .setCallbacks(new MessageBox.Callbacks() {
        public void onOk() {
          ContoUtenteMovimento movimento = new ContoUtenteMovimentoTx();
          movimento.setImporto(importoBox.getValue());
          movimento.setSegno(segnoBox.getValue(segnoBox.getSelectedIndex()));
          movimento.setCausale(causaleBox.getValue());
          movimento.setData(dataBox.getValue());
          PortalSessionState portalSessionState = AppClientFactory.IMPL.getPortalSessionState();
          if (portalSessionState != null) {
            movimento.setRegisteringPortalUser(portalSessionState.getLoggedUser());
          }
          
          if (movimento.getImporto() == null || movimento.getImporto() == 0) {
            Window.alert("Inserire un importo non nullo");
            cancelClose();
            return;
          }
          
          if (movimento.getImporto() < 0) {
            Window.alert("Impossibile inserire un importo negativo! (utilizzare la tipologia 'addebito' per indicare un'operazione negativa)");
            cancelClose();
            return;
          }
          
          if (contoUtente.getMovimenti() == null) {
            contoUtente.setMovimenti(new ArrayList<ContoUtenteMovimento>());
          }
          contoUtente.getMovimenti().add(movimento);
          
          customClientFactory.updateContoUtente(contoUtente, new Delegate<ContoUtente>() {
            public void execute(ContoUtente result) {
              setContoUtente(result);
            }
          });
          
        }
      })
    );
    
    */
    
  }
  
  private Panel createMovimentoItemPanel(String labelText, Widget box) {
    HorizontalPanel panel = new HorizontalPanel();
    panel.add(new Spacer("1px", "2em"));
    Label label = new Label(labelText);
    label.setWidth("6em");
    panel.add(label);
    panel.add(box);
    return panel;
  }
  
}
