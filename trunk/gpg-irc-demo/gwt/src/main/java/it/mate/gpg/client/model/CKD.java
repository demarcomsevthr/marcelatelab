package it.mate.gpg.client.model;

public class CKD {
  
  public final static int MG_DL_UNIT = 1;
  
  public final static int PMOL_L_UNIT = 2;
  
  double scr;
  
  int scrUnit;
  
  int age;
  
  int weight;
  
  int height;
  
  double albumin;
  
  boolean female;
  
  boolean black;
  
  public double getCockcroftGFR() {
    double gfr = (140d - age) * weight;
    gfr /= (72d * scr);
    gfr *= (female ? 0.85 : 1d);
    return gfr;
  }
  
  public double getMdrdGFR() {
    double gfr = 175d * Math.pow(scr, -1.154);
    gfr *= Math.pow(age, -0.203);
    gfr *= (female ? 0.742 : 1d);
    gfr *= (black ? 1.210 : 1d);
    return gfr;
  }
  
  public double getCkdEpiGFR() {
    double k = female ? 0.7 : 0.9;
    double a = female ? -0.329 : -0.411;
    double scrk = scr / k;
    double gfr = 141d * Math.pow(Math.min(scrk, 1d), a);
    gfr *= Math.pow(Math.max(scrk, 1), -1.209);
    gfr *= Math.pow(0.993, age);
    gfr *= (female ? 1.018 : 1d);
    gfr *= (black ? 1.159 : 1d);
    return gfr;
  }
  

  public CKD setScr(double scr) {
    this.scr = scr;
    return this;
  }

  public CKD setScrUnit(int scrUnit) {
    this.scrUnit = scrUnit;
    return this;
  }

  public CKD setAge(int age) {
    this.age = age;
    return this;
  }

  public CKD setWeight(int weight) {
    this.weight = weight;
    return this;
  }

  public CKD setHeight(int height) {
    this.height = height;
    return this;
  }

  public CKD setAlbumin(double albumin) {
    this.albumin = albumin;
    return this;
  }

  public CKD setFemale(boolean female) {
    this.female = female;
    return this;
  }
  
  public CKD setBlack(boolean black) {
    this.black = black;
    return this;
  }

}
