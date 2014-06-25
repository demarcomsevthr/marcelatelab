package it.mate.therapyreminder.client.constants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Messages;

public interface AppMessages extends Messages {

  AppMessages IMPL = GWT.create(AppMessages.class);
  
  @DefaultMessage("")
  String MainActivity_goToContactTutorListView_msg1();
  
  @DefaultMessage("")
  String MainActivity_saveContatto_msg1();
  
  @DefaultMessage("")
  String MainActivity_saveContatto_msg2();
  
  @DefaultMessage("")
  String MainActivity_saveContatto_msg3();
  
  @DefaultMessage("")
  String MainActivity_saveContatto_msg4();
  
  @DefaultMessage("")
  String MainActivity_dataConnectionOff_msg();
  
  @DefaultMessage("")
  String MainActivity_saveAccount_msg1();
  
  @DefaultMessage("")
  String MainActivity_saveAccount_msg2();
  
  @DefaultMessage("")
  String MainActivity_deleteContatto_msg1();
  
  @DefaultMessage("")
  String MainController_checkConnectionIfOnlineMode_msg1();
  
  @DefaultMessage("")
  String ContactEditView_onClosingView_msg1();
  
  @DefaultMessage("")
  String ContactEditView_flushModel_msg1();
  
  @DefaultMessage("")
  String ContactEditView_flushModel_msg2();
  
  @DefaultMessage("")
  String ContactEditView_onDeleteBtn_msg1();
  
  @DefaultMessage("")
  String ReminderEditView_setModel_msg1();
  
  @DefaultMessage("")
  String ReminderEditView_setModel_title1();
  
  @DefaultMessage("")
  String ReminderEditView_setModel_msg2(int qta);
  
  @DefaultMessage("")
  String TherapyEditView_onDeleteBtn_msg1();
  
  @DefaultMessage("")
  String TherapyEditView_onClosingView_msg1();
  
  @DefaultMessage("")
  String TherapyEditView_flushPrescrizione_msg1();
  
  @DefaultMessage("")
  String TherapyEditView_flushPrescrizione_msg2();
  
  @DefaultMessage("")
  String TherapyEditView_flushPrescrizione_msg3();
  
  @DefaultMessage("")
  String TherapyEditView_fillTutorCombo_msg1();
  
  @DefaultMessage("")
  String TherapyListView_onDeleteBtn_msg1();
  
  @DefaultMessage("")
  String ContactListView_showListaContatti_msg1();
  
  @DefaultMessage("")
  String DosageEditView_setModel_msg1(String orario);
  
  @DefaultMessage("")
  String ReminderListView_showListaSomministrazioni_msg1();
  
  @DefaultMessage("")
  String ReminderListView_showRow_at_text();
  
  @DefaultMessage("")
  String TherapyEditView_tipoRicorrenzaCombo_ogniGiorno();
  
  @DefaultMessage("")
  String TherapyEditView_tipoRicorrenzaCombo_giorniAlterni();
  
  @DefaultMessage("")
  String TherapyEditView_tipoRicorrenzaCombo_settimanale();
  
  @DefaultMessage("")
  String TherapyEditView_tipoRicorrenzaCombo_mensile();
  
  @DefaultMessage("")
  String TherapyEditView_tipoRicorrenzaCombo_trimestrale();
  
  @DefaultMessage("")
  String TherapyEditView_tipoRicorrenzaCombo_semestrale();
  
  @DefaultMessage("")
  String TherapyEditView_tipoOrariCombo_aIntervalli();
  
  @DefaultMessage("")
  String TherapyEditView_tipoOrariCombo_aOrariFissi();
  
  @DefaultMessage("")
  String TherapyEditView_bottomBar_what();
  
  @DefaultMessage("")
  String TherapyEditView_bottomBar_when();
  
  @DefaultMessage("")
  String TherapyEditView_bottomBar_hours();
  
  @DefaultMessage("")
  String ReminderListView_today_text();
  
  @DefaultMessage("")
  String ReminderListView_tomorrow_text();
  
}
