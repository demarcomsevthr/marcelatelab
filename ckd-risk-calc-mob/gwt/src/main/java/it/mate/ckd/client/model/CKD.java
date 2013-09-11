package it.mate.ckd.client.model;

public class CKD {
  
  public final static int MG_DL_UNIT = 1;
  
  public final static int PMOL_L_UNIT = 2;
  
  public final static int MG_G_UNIT = 3;
  
  public final static int MG_MMOL_UNIT = 4;
  
  public final static int LOW_RISK = 1;
  
  public final static int MIDDLE_RISK = 2;
  
  public final static int HIGH_RISK = 3;
  
  public final static int VERY_HIGH_RISK = 4;
  
  private final static double FACTOR_PMOLL_TO_MGDL = 88.4;
  
  double scr;
  
  int scrUnit = MG_DL_UNIT;
  
  int age;
  
  Integer weight;
  
  Integer height;
  
  Integer albumin;
  
  int albUnit = MG_G_UNIT;
  
  boolean female;
  
  boolean black;
  
  boolean useBsa;
  
  public double getCockcroftGFR() {
    double gfr = (140d - age) * weight;
    gfr /= (72d * getScrMgDl());
    gfr *= (female ? 0.85 : 1d);
    return convertGfr(gfr);
  }
  
  public double getMdrdGFR() {
    double gfr = 175d * Math.pow(getScrMgDl(), -1.154);
    gfr *= Math.pow(age, -0.203);
    gfr *= (female ? 0.742 : 1d);
    gfr *= (black ? 1.210 : 1d);
    return gfr;
  }
  
  public double getMdrdNcGFR() {
    double gfr = 186d * Math.pow(getScrMgDl(), -1.154);
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
  
  private double convertGfr(double gfr) {
    if (useBsa) {
      Double bsa = getBSA();
      if (bsa != null) {
        gfr = gfr * bsa / 1.73;
      }
    }
    return gfr;
  }
  
  public Double getBSA() {
    if (weight == null || weight == 0) {
      return null;
    }
    if (height == null || height == 0) {
      return null;
    }
    double bsa = Math.pow(weight, 0.425);
    bsa = bsa * Math.pow(height, 0.725);
    bsa = bsa * 0.007184;
    return bsa;
  }
  
  private double getScrMgDl() {
    return getScrMgDl(this.scr);
  }
  
  public double getScrMgDl(double originalValue) {
    double scr = originalValue;
    if (scrUnit == PMOL_L_UNIT) {
      scr = convertPMollToMgDl(scr);
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

  public CKD setHeight(Integer height) {
    this.height = height;
    return this;
  }

  public CKD setAlbumin(Integer albumin) {
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
    } else if (gfr >= 45) {
      stadioVfg = "G3a";
    } else if (gfr >= 30) {
      stadioVfg = "G3b";
    } else if (gfr >= 15) {
      stadioVfg = "G4";
    } else {
      stadioVfg = "G5";
    }
    return stadioVfg;
  }
  
  private Integer getAlbuminMgG() {
    Integer albumin = this.albumin;
    if (albUnit == MG_MMOL_UNIT) {
      albumin = convertMMolToMgG(albumin);
    }
    return albumin;
  }
  
  public int getRiskStadium(double gfr) {
    if (getAlbumin() == null) {
      return -1;
    }
    int risk = LOW_RISK;
    if (getAlbuminMgG() < 30) {
      if (gfr >= 60) {
        risk = LOW_RISK;
      } else if (gfr >= 45) {
        risk = MIDDLE_RISK;
      } else if (gfr >= 30) {
        risk = HIGH_RISK;
      } else {
        risk = VERY_HIGH_RISK;
      }
    } else if (getAlbuminMgG() < 300) {
      if (gfr >= 60) {
        risk = MIDDLE_RISK;
      } else if (gfr >= 45) {
        risk = HIGH_RISK;
      } else {
        risk = VERY_HIGH_RISK;
      }
    } else {
      if (gfr >= 60) {
        risk = HIGH_RISK;
      } else {
        risk = VERY_HIGH_RISK;
      }
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

  public Integer getHeight() {
    return height;
  }

  public Integer getAlbumin() {
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

  public void setUseBsa(boolean useBsa) {
    this.useBsa = useBsa;
  }
  
  public boolean isUseBsa() {
    return useBsa;
  }
  
  public static Double convertPMollToMgDl(Double pmoll) {
    if (pmoll == null)
      return null;
    if (pmoll == 0d)
      return 0d;
    return pmoll / FACTOR_PMOLL_TO_MGDL;
  }

  public static Double convertMgDlToPMoll(Double mgdl) {
    if (mgdl == null)
      return null;
    return mgdl * FACTOR_PMOLL_TO_MGDL;
  }

  public static Integer convertMMolToMgG (Integer mmol) {
    if (mmol == null)
      return null;
    return mmol * 10;
  }
  
  public static Integer convertMgGToMMol (Integer mgg) {
    if (mgg == null)
      return null;
    if (mgg == 0)
      return 0;
    return mgg / 10;
  }
  
}
