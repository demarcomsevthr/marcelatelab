package it.mate.gpg.client.model;

public class CKD {
  
  public final static int MG_DL_UNIT = 1;
  
  public final static int PMOL_L_UNIT = 2;
  
  public final static int MG_G_UNIT = 3;
  
  public final static int MG_MMOL_UNIT = 4;
  
  public final static int VERY_LOW_RISK = 1;
  
  public final static int LOW_RISK = 2;
  
  public final static int MIDDLE_RISK = 3;
  
  public final static int HIGH_RISK = 4;
  
  public final static int VERY_HIGH_RISK = 5;
  
  double scr;
  
  int scrUnit = MG_DL_UNIT;
  
  int age;
  
  int weight;
  
  int height;
  
  double albumin;
  
  int albUnit = MG_G_UNIT;
  
  boolean female;
  
  boolean black;
  
  public double getCockcroftGFR() {
    double gfr = (140d - age) * weight;
    gfr /= (72d * getScrMgDl());
    gfr *= (female ? 0.85 : 1d);
    return gfr;
  }
  
  public double getMdrdGFR() {
    double gfr = 175d * Math.pow(getScrMgDl(), -1.154);
    gfr *= Math.pow(age, -0.203);
    gfr *= (female ? 0.742 : 1d);
    gfr *= (black ? 1.210 : 1d);
    return gfr;
  }
  
  public double getCkdEpiGFR() {
    double k = female ? 0.7 : 0.9;
    double a = female ? -0.329 : -0.411;
    double scrk = getScrMgDl() / k;
    double gfr = 141d * Math.pow(Math.min(scrk, 1d), a);
    gfr *= Math.pow(Math.max(scrk, 1), -1.209);
    gfr *= Math.pow(0.993, age);
    gfr *= (female ? 1.018 : 1d);
    gfr *= (black ? 1.159 : 1d);
    return gfr;
  }
  
  private double getScrMgDl() {
    double scr = this.scr;
    if (scrUnit == PMOL_L_UNIT) {
      scr = scr / 88.4;
    }
    return scr;
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
  
  public static String getGFRStadium(double gfr) {
    String stadioVfg = "";
    if (gfr >= 90) {
      stadioVfg = "G1";
    } else if (gfr >= 60) {
      stadioVfg = "G2";
    } else if (gfr >= 30) {
      stadioVfg = "G3";
    } else if (gfr >= 15) {
      stadioVfg = "G4";
    } else {
      stadioVfg = "G5";
    }
    return stadioVfg;
  }
  
  public int getRiskStadium(double gfr) {
    int risk = VERY_LOW_RISK;
    if (albumin < 10) {
      if (gfr >= 60) {
        risk = VERY_LOW_RISK;
      } else if (gfr >= 45) {
        risk = LOW_RISK;
      } else if (gfr >= 30) {
        risk = MIDDLE_RISK;
      } else if (gfr >= 15) {
        risk = HIGH_RISK;
      } else {
        risk = VERY_HIGH_RISK;
      }
    } else if (albumin <= 29) {
      if (gfr >= 60) {
        risk = VERY_LOW_RISK;
      } else if (gfr >= 45) {
        risk = LOW_RISK;
      } else if (gfr >= 30) {
        risk = MIDDLE_RISK;
      } else if (gfr >= 15) {
        risk = HIGH_RISK;
      } else {
        risk = VERY_HIGH_RISK;
      }
    } else if (albumin <= 299) {
      if (gfr >= 60) {
        risk = LOW_RISK;
      } else if (gfr >= 45) {
        risk = MIDDLE_RISK;
      } else if (gfr >= 15) {
        risk = HIGH_RISK;
      } else {
        risk = VERY_HIGH_RISK;
      }
    } else if (albumin <= 1999) {
      if (gfr >= 60) {
        risk = MIDDLE_RISK;
      } else if (gfr >= 15) {
        risk = HIGH_RISK;
      } else {
        risk = VERY_HIGH_RISK;
      }
    } else {
      risk = VERY_HIGH_RISK;
    }
    return risk;
  }

  public double getScr() {
    return scr;
  }

  public int getScrUnit() {
    return scrUnit;
  }

  public int getAge() {
    return age;
  }

  public int getWeight() {
    return weight;
  }

  public int getHeight() {
    return height;
  }

  public double getAlbumin() {
    return albumin;
  }

  public boolean isFemale() {
    return female;
  }

  public boolean isBlack() {
    return black;
  }

  public int getAlbUnit() {
    return albUnit;
  }

  public void setAlbUnit(int albUnit) {
    this.albUnit = albUnit;
  }
  
}
