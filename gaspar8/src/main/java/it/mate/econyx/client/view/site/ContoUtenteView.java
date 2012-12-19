package it.mate.econyx.client.view.site;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.factories.CustomClientFactory;
import it.mate.econyx.client.text.CurrencyBox;
import it.mate.econyx.shared.model.ContoUtente;
import it.mate.econyx.shared.model.ContoUtenteMovimento;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.ui.CellTableExt;
import it.mate.gwtcommons.client.utils.ColumnUtil;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;

public class ContoUtenteView extends AbstractBaseView<ContoUtenteView.Presenter> {

  public interface ViewUiBinder extends UiBinder<Widget, ContoUtenteView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField CurrencyBox saldoBox;
  @UiField (provided=true) CellTableExt<ContoUtenteMovimento> movimentiTable;
  @UiField Panel pagerPanel;
  @UiField Label alertLabel;
  
  private CustomClientFactory customClientFactory = AppClientFactory.Customizer.cast(CustomClientFactory.class);
  
  private PortalUser user;
  
  public interface Presenter extends BasePresenter {
    
  }

  public ContoUtenteView() {
    this(null);
  }

  public ContoUtenteView(String height) {
    initUI();
    if (height != null) {
      this.setHeight(height);
    }
  }

  private void initUI() {
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
    
    movimentiTable.addColumn(ColumnUtil.createColumn(new ColumnUtil.ValueGetter<ContoUtenteMovimento, String>() {
      public String getValue(ContoUtenteMovimento item) {
        return item.getRegisteringPortalUser() != null ? item.getRegisteringPortalUser().getScreenName() : "";
      }
    }, new TextCell(), null), "Referente");
    
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
//  this.contoUtente = contoUtente;
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
          pagerPanel.add(pager);
        }
      });
    } else {
      movimentiTable.setRowCount(0);
    }
  }
  
}
