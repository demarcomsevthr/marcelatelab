package it.mate.therapyreminder.client.places;

import it.mate.gwtcommons.client.places.HasToken;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MainPlace extends Place implements HasToken {
  
  public static final String HOME = "home";
  
  public static final String SETTINGS = "settings";
  
  public static final String THERAPY_LIST = "listTherapy";
  
  public static final String THERAPY_EDIT = "editTherapy";
  
  public static final String DOSAGE_EDIT = "editDosage";
  
  public static final String REMINDER_LIST = "listReminder";
  
  public static final String REMINDER_EDIT = "editReminder";
  
  public static final String ACCOUNT_EDIT = "editAccount";
  
  public static final String ABOUT = "about";
  
  public static final String CONTACT_MENU = "contactsMenu";
  
  public static final String CONTACT_DOCTOR_LIST = "listDoctors";
  
  public static final String CONTACT_TUTOR_LIST = "listTutors";
  
  public static final String CONTACT_DOCTOR_EDIT = "editDoctor";
  
  public static final String CONTACT_TUTOR_EDIT = "editTutor";
  
  public static final String PATIENT_LIST = "listPatients";
  
  public static final String PATIENT_EDIT = "editPatient";
  
  public static final String TEST = "test";
  
  private String token;
  
  private Object model;
  
  public MainPlace() {
    this.token = HOME;
  }
  
  public MainPlace(String token) {
    this(token, null);
  }
  
  @Override
  public String toString() {
    return "MainPlace [token=" + token + "]";
  }

  public MainPlace(String token, Object model) {
    this.token = token;
    this.model = model;
  }
  
  public String getToken() {
    return token;
  }
  
  public Object getModel() {
    return model;
  }
  
  
  public static class Tokenizer implements PlaceTokenizer<MainPlace> {

    @Override
    public String getToken(MainPlace place) {
      return place.getToken();
    }

    @Override
    public MainPlace getPlace(String token) {
      return new MainPlace(token);
    }

  }
  
}
