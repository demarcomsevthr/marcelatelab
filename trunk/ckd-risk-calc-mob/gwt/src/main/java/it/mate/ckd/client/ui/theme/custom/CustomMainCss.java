package it.mate.ckd.client.ui.theme.custom;

import com.googlecode.mgwt.ui.client.theme.base.MainCss;

public interface CustomMainCss extends MainCss {
  
  @ClassName("verLbl")
  public String verLbl();

  @ClassName("homePanel")
  public String homePanel();

  @ClassName("boxSpacer")
  public String boxSpacer();

  @ClassName("valoriLbl")
  public String valoriLbl();

  @ClassName("inputPanel")
  public String inputPanel();

  @ClassName("outputPanel")
  public String outputPanel();

  @ClassName("ckd-Risk")
  public String ckdRisk();

  @ClassName("ckd-Stadium")
  public String ckdStadium();

  @ClassName("ckd-Gfr")
  public String ckdGfr();

  @ClassName("ckd-Label")
  public String ckdLabel();

  @ClassName("ckd-Bsa")
  public String ckdBsa();

}
