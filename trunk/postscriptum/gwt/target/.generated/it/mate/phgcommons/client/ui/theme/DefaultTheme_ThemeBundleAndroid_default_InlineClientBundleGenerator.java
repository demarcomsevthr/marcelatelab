package it.mate.phgcommons.client.ui.theme;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class DefaultTheme_ThemeBundleAndroid_default_InlineClientBundleGenerator implements it.mate.phgcommons.client.ui.theme.DefaultTheme.ThemeBundleAndroid {
  private static DefaultTheme_ThemeBundleAndroid_default_InlineClientBundleGenerator _instance0 = new DefaultTheme_ThemeBundleAndroid_default_InlineClientBundleGenerator();
  private void calendarImageInitializer() {
    calendarImage = // jar:file:/C:/Users/marcello/.m2/repository/it/mate/phgcommons/1.0/phgcommons-1.0.jar!/it/mate/phgcommons/client/ui/theme/resources/calendar.png
    new com.google.gwt.resources.client.impl.DataResourcePrototype(
      "calendarImage",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABIAAAASCAQAAAD8x0bcAAAAaElEQVR4AeWNwQmAMBAEt628BF+CbWyJgl+/6SNwZcSckrCfnAV4A4EMA4v3eDWgqOmqNqCoARfmoeqgm8zVI3s+B0+NxJhHrhLRSBKp8ShEIkyRKH6/ov/M7ZOxrUeFNaQQXluQmK/c4tIuKSVzbdkAAAAASUVORK5CYII=")
    );
  }
  private static class calendarImageInitializer {
    static {
      _instance0.calendarImageInitializer();
    }
    static com.google.gwt.resources.client.DataResource get() {
      return calendarImage;
    }
  }
  public com.google.gwt.resources.client.DataResource calendarImage() {
    return calendarImageInitializer.get();
  }
  private void clockImageInitializer() {
    clockImage = // jar:file:/C:/Users/marcello/.m2/repository/it/mate/phgcommons/1.0/phgcommons-1.0.jar!/it/mate/phgcommons/client/ui/theme/resources/clock.png
    new com.google.gwt.resources.client.impl.DataResourcePrototype(
      "clockImage",
      com.google.gwt.safehtml.shared.UriUtils.fromTrustedString("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABIAAAASCAYAAABWzo5XAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAArdJREFUeNpslNlrE1EUxs/MZKk2EYxNpWDBljR1RbC2KFSLPqjQ+iAu+AdIoVR9KLg+CHkQQYJBEBdQfHGBWkWxoj6IoK2KqA9FbG1iqbQ+dDEuaUuWmTt+Jz2RMeTCj5l77j3fnOXe0aLRKJUYi8FasAmUAxukQB8YBL+LHVwlRPaBDtAC9KK1LHgBYuCZc8G50QeugG6wtYQIDw/YAR6Bs85AXI7ndbBf5pxKn4vMboOsLzZpCvOmDHl3eyjbiHd3jtwn5OOHCwKaTAoiE+Ckm3I3xvRqSmoBhGaTx848X6GGzn3VQ0cs0iO1asSvSO/E3rfgFguF2VFE0uAQRHomtUrqdzUTvtyukV0Dp1Mu07S+68tiKc03U6fiV2HjII5yvbgOe0FQhG7CqcdEoN+M5YSNVEbpZi9l2pCmnyOc1cphVVyGu+KzjtNmoZ2OYl6CEFrjoYQeIkTGNgtk8J4eh1CFPU0N5gdlkXEP9pz4bWGhKpkkGBb6oS0hI++fHz9BA9jjgd+UFqSgmuJo38A2LXuqdOkQOdQppfuJ07PzfaDL4CW4rZGKpDS/e9gI84dM2FShy7pjwnXyIGRaY32iDdb7glgc9jY5Y6exfmfEqGUhPv0LxFex0IBMKsA2fsF5oXpriBrNd9w1JGmkINoB0S4IpKutMbZtx9aA+A5w++9L53h0gYd8FbjgIZWgReYfiut1tNCeo1GjJsY1DKvhStTooPjMzB9eoifgFdgMNoLzIpjl1JaqCQqoJLHASmuQU/Xh/QKeq0XoMfjIqf2SA5mUhU6JsmW+A+68CBceR6AVV6QX5gOydxJEuEaFu9YPjoOLwAtaRegzGJW7tgrPelnnMQva5bfy32/kGhgHZ8B6uZBNQvF4DY5JACX/R085X7BLGsAiZbI2J+fpAeiVg/pv/BVgALWv5VZhfXsQAAAAAElFTkSuQmCC")
    );
  }
  private static class clockImageInitializer {
    static {
      _instance0.clockImageInitializer();
    }
    static com.google.gwt.resources.client.DataResource get() {
      return clockImage;
    }
  }
  public com.google.gwt.resources.client.DataResource clockImage() {
    return clockImageInitializer.get();
  }
  private void cssInitializer() {
    css = new com.google.gwt.resources.client.CssResource() {
      private boolean injected;
      public boolean ensureInjected() {
        if (!injected) {
          injected = true;
          com.google.gwt.dom.client.StyleInjector.inject(getText());
          return true;
        }
        return false;
      }
      public String getName() {
        return "css";
      }
      public String getText() {
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".gwt-PopupPanel{border:" + ("none")  + ";padding:" + ("0")  + ";background:" + ("white")  + ";}.mgwt-TextBox .mgwt-InputBox-box{-webkit-user-modify:" + ("initial")  + ";margin:" + ("-10px"+ " " +"-13px")  + ";}.mgwt-PasswordTextBox .mgwt-InputBox-box{-webkit-user-modify:" + ("initial")  + ";}.mgwt-TextArea .mgwt-InputBox-box{-webkit-user-modify:" + ("initial")  + ";color:" + ("black")  + ";margin:" + ("-10px"+ " " +"-13px")  + ";}.phg-PopupPanelGlass{background-color:" + ("#000")  + ";opacity:") + (("0.3")  + ";-webkit-opacity:" + ("0.3")  + ";}.phg-TouchAnchor,.phg-TouchAnchor a{text-decoration:" + ("none")  + ";}.phg-DateBox{background-image:" + ("url('" + ((com.google.gwt.resources.client.DataResource)(DefaultTheme_ThemeBundleAndroid_default_InlineClientBundleGenerator.this.calendarImage())).getUrl() + "')")  + ";background-repeat:" + ("no-repeat")  + ";background-position:" + ("2%"+ " " +"42%")  + ";}.phg-TimeBox{background-image:" + ("url('" + ((com.google.gwt.resources.client.DataResource)(DefaultTheme_ThemeBundleAndroid_default_InlineClientBundleGenerator.this.clockImage())).getUrl() + "')")  + ";background-repeat:" + ("no-repeat")  + ";background-position:" + ("2%"+ " " +"42%")  + ";}.phg-CalendarDialog{color:" + ("black")  + ";}.phg-CalendarDialog-Header{background-color:" + ("black") ) + (";color:" + ("white")  + ";}.phg-CalendarDialog-Header .phg-TouchHTML{text-align:" + ("center")  + ";padding-top:" + ("5px")  + ";}.phg-CalendarDialog-MonthWrapper{border:" + ("1px"+ " " +"solid"+ " " +"#787878")  + ";background:" + ("white")  + ";color:" + ("black")  + ";font-size:" + ("26px")  + ";text-align:" + ("center")  + ";}.phg-CalendarDialog-MonthTable{width:" + ("100%")  + ";padding:" + ("6px")  + ";}.phg-CalendarDialog-MonthHeader{color:") + (("#778088")  + ";font-family:" + ("Trebuchet"+ " " +"MS")  + ";font-weight:" + ("600")  + ";}.phg-CalendarDialog-MonthHeader-WeekDay{font-family:" + ("Trebuchet"+ " " +"MS")  + ";font-size:" + ("12px")  + ";color:" + ("#778088")  + ";padding-bottom:" + ("6px")  + ";}.phg-CalendarDialog-Month-Day{border-bottom:" + ("1px"+ " " +"solid"+ " " +"#bcc0c1")  + ";border-left:" + ("1px"+ " " +"solid"+ " " +"#bcc0c1")  + ";color:" + ("#bcc0c1")  + ";background-color:" + ("#f5f7f9") ) + (";font-size:" + ("22px")  + ";padding-top:" + ("10px")  + ";}.phg-CalendarDialog-Month-Day.phg-firstRow{border-top:" + ("1px"+ " " +"solid"+ " " +"#bcc0c1")  + ";}.phg-CalendarDialog-Month-Day.phg-firstCol{border-right:" + ("1px"+ " " +"solid"+ " " +"#bcc0c1")  + ";}.phg-CalendarDialog-Month-Day.phg-outsideMonth{background-color:" + ("#d7d9db")  + ";}.phg-CalendarDialog-Month-Day-selected{background-color:" + ("rgba(" + "13"+ ","+ " " +"13"+ ","+ " " +"255"+ ","+ " " +"0.75" + ")")  + ";}.phg-TimePickerDialog{color:" + ("black")  + ";background-color:" + ("#efefef")  + ";}.phg-TimePickerDialog-Header{color:" + ("white")  + ";background-color:" + ("black")  + ";}.phg-TimePickerDialog-TimeRow{width:") + (("60%")  + ";padding-right:" + ("16%")  + ";}.phg-TimePickerDialog-TimeDigit{background-color:" + ("#fff")  + ";margin:" + ("6px")  + ";text-align:" + ("center")  + ";font-size:" + ("40px")  + ";padding:" + ("6px")  + ";border:" + ("2px"+ " " +"solid"+ " " +"#23238e")  + ";border-radius:" + ("5px")  + ";height:" + ("42px")  + ";padding-top:" + ("4px") ) + (";}.phg-TimePickerDialog-TimeDigit-selected{background-color:" + ("rgba(" + "197"+ ","+ " " +"197"+ ","+ " " +"219"+ ","+ " " +"0.8" + ")")  + ";}.phg-TimePickerDialog-Keypad{padding-right:" + ("11.5%")  + ";}.phg-TimePickerDialog-KeypadCell{border:" + ("1px"+ " " +"solid"+ " " +"#bababa")  + ";background-image:" + ("-webkit-gradient(" + "linear"+ ","+ " " +"left"+ " " +"top"+ ","+ " " +"left"+ " " +"bottom"+ ","+ " " +"color-stop(" + "0"+ ","+ " " +"#f4f4f4" + ")"+ ","+ " " +"color-stop(" + "1"+ ","+ " " +"#d4d4d4" + ")" + ")")  + ";width:" + ("56px")  + ";height:" + ("44px")  + ";border-radius:" + ("3px")  + ";text-align:" + ("center")  + ";vertical-align:" + ("middle")  + ";font-size:" + ("26px")  + ";padding-top:") + (("10px")  + ";}.phg-TimePickerDialog-KeypadCell-minutes{background:" + ("rgba(" + "15"+ ","+ " " +"112"+ ","+ " " +"118"+ ","+ " " +"0.8" + ")")  + ";color:" + ("white")  + ";}.phg-TimePickerDialog-KeypadCell-hours{background:" + ("rgba(" + "33"+ ","+ " " +"33"+ ","+ " " +"134"+ ","+ " " +"0.94" + ")")  + ";color:" + ("white")  + ";}.phg-TimePickerDialog-Bottom{padding-right:" + ("16%")  + ";padding-top:" + ("10px")  + ";}.phg-TimePickerDialog-BottomButton{border:" + ("1px"+ " " +"solid"+ " " +"#bababa")  + ";background-image:" + ("-webkit-gradient(" + "linear"+ ","+ " " +"left"+ " " +"top"+ ","+ " " +"left"+ " " +"bottom"+ ","+ " " +"color-stop(" + "0"+ ","+ " " +"#f4f4f4" + ")"+ ","+ " " +"color-stop(" + "1"+ ","+ " " +"#d4d4d4" + ")" + ")")  + ";height:" + ("30px")  + ";border-radius:" + ("3px") ) + (";text-align:" + ("center")  + ";vertical-align:" + ("middle")  + ";font-size:" + ("18px")  + ";width:" + ("100px")  + ";padding-top:" + ("6px")  + ";margin-right:" + ("4px")  + ";}.phg-PopinDialog-Wrapper{background:" + ("transparent")  + ";color:" + ("white")  + ";font-weight:" + ("bold")  + ";text-decoration:" + ("none")  + ";}.phg-PopinDialog-TitleWrapper{background:") + (("#5f6e8b")  + ";border-top-right-radius:" + ("16px"+ " " +"16px")  + ";border-top-left-radius:" + ("16px"+ " " +"16px")  + ";border-top:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";border-right:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";border-left:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";padding-top:" + ("10px")  + ";padding-right:" + ("40px")  + ";padding-left:" + ("40px")  + ";padding-bottom:" + ("10px")  + ";font-size:" + ("1.2em") ) + (";}.phg-PopinDialog-BodyWrapper{background:" + ("#24385d")  + ";border-bottom-right-radius:" + ("16px"+ " " +"16px")  + ";border-bottom-left-radius:" + ("16px"+ " " +"16px")  + ";border-bottom:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";border-right:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";border-left:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";}.phg-PopinDialog-Wrapper a{color:" + ("white")  + ";font-weight:" + ("bold")  + ";text-decoration:" + ("none")  + ";}.phg-PopinDialog-BodyWrapper div div{padding-top:" + ("20px")  + ";}.phg-PopinDialog-MessageRow{font-size:") + (("0.7em")  + ";padding-right:" + ("1.5em")  + ";padding-left:" + ("1.5em")  + ";}.phg-PopinDialog-Separator{border-top:" + ("1px"+ " " +"solid"+ " " +"rgba(" + "128"+ ","+ " " +"128"+ ","+ " " +"128"+ ","+ " " +"0.98" + ")")  + ";margin-top:" + ("1em")  + ";}.phg-PopinDialog-ButtonsRow{padding-right:" + ("0.5em")  + ";padding-left:" + ("0.5em")  + ";}.phg-PopinDialog-ButtonsRow td{width:" + ("3em")  + ";padding-top:" + ("0")  + ";padding-bottom:" + ("1em")  + ";padding-right:" + ("1em") ) + (";padding-left:" + ("0.3em")  + ";}.phg-PopinDialog-ButtonsRow-2ndBtn{border-right:" + ("1px"+ " " +"solid"+ " " +"rgba(" + "128"+ ","+ " " +"128"+ ","+ " " +"128"+ ","+ " " +"0.98" + ")")  + ";}.phg-PopinDialog-Wrapper{font-size:" + ("20px")  + ";}.phg-InvisibleTouch{position:" + ("absolute")  + ";top:" + ("0")  + ";right:" + ("0")  + ";width:" + ("4px")  + ";height:" + ("4px")  + ";background-color:" + ("transparent")  + ";border:" + ("none")  + ";}.phg-InvisibleTouch input{border:") + (("0")  + " !important;z-index:" + ("1")  + " !important;}.phg-CheckBox{width:" + ("28px")  + ";height:" + ("28px")  + ";border:" + ("1px"+ " " +"solid"+ " " +"black")  + ";background-color:" + ("white")  + ";margin:" + ("0.2em")  + ";border-radius:" + ("1px")  + ";}.phg-CheckBox-selected{background:" + ("url(main/images/check-28.png)"+ " " +"no-repeat"+ " " +"#fff")  + ";}")) : ((".gwt-PopupPanel{border:" + ("none")  + ";padding:" + ("0")  + ";background:" + ("white")  + ";}.mgwt-TextBox .mgwt-InputBox-box{-webkit-user-modify:" + ("initial")  + ";margin:" + ("-10px"+ " " +"-13px")  + ";}.mgwt-PasswordTextBox .mgwt-InputBox-box{-webkit-user-modify:" + ("initial")  + ";}.mgwt-TextArea .mgwt-InputBox-box{-webkit-user-modify:" + ("initial")  + ";color:" + ("black")  + ";margin:" + ("-10px"+ " " +"-13px")  + ";}.phg-PopupPanelGlass{background-color:" + ("#000")  + ";opacity:") + (("0.3")  + ";-webkit-opacity:" + ("0.3")  + ";}.phg-TouchAnchor,.phg-TouchAnchor a{text-decoration:" + ("none")  + ";}.phg-DateBox{background-image:" + ("url('" + ((com.google.gwt.resources.client.DataResource)(DefaultTheme_ThemeBundleAndroid_default_InlineClientBundleGenerator.this.calendarImage())).getUrl() + "')")  + ";background-repeat:" + ("no-repeat")  + ";background-position:" + ("98%"+ " " +"42%")  + ";}.phg-TimeBox{background-image:" + ("url('" + ((com.google.gwt.resources.client.DataResource)(DefaultTheme_ThemeBundleAndroid_default_InlineClientBundleGenerator.this.clockImage())).getUrl() + "')")  + ";background-repeat:" + ("no-repeat")  + ";background-position:" + ("98%"+ " " +"42%")  + ";}.phg-CalendarDialog{color:" + ("black")  + ";}.phg-CalendarDialog-Header{background-color:" + ("black") ) + (";color:" + ("white")  + ";}.phg-CalendarDialog-Header .phg-TouchHTML{text-align:" + ("center")  + ";padding-top:" + ("5px")  + ";}.phg-CalendarDialog-MonthWrapper{border:" + ("1px"+ " " +"solid"+ " " +"#787878")  + ";background:" + ("white")  + ";color:" + ("black")  + ";font-size:" + ("26px")  + ";text-align:" + ("center")  + ";}.phg-CalendarDialog-MonthTable{width:" + ("100%")  + ";padding:" + ("6px")  + ";}.phg-CalendarDialog-MonthHeader{color:") + (("#778088")  + ";font-family:" + ("Trebuchet"+ " " +"MS")  + ";font-weight:" + ("600")  + ";}.phg-CalendarDialog-MonthHeader-WeekDay{font-family:" + ("Trebuchet"+ " " +"MS")  + ";font-size:" + ("12px")  + ";color:" + ("#778088")  + ";padding-bottom:" + ("6px")  + ";}.phg-CalendarDialog-Month-Day{border-bottom:" + ("1px"+ " " +"solid"+ " " +"#bcc0c1")  + ";border-right:" + ("1px"+ " " +"solid"+ " " +"#bcc0c1")  + ";color:" + ("#bcc0c1")  + ";background-color:" + ("#f5f7f9") ) + (";font-size:" + ("22px")  + ";padding-top:" + ("10px")  + ";}.phg-CalendarDialog-Month-Day.phg-firstRow{border-top:" + ("1px"+ " " +"solid"+ " " +"#bcc0c1")  + ";}.phg-CalendarDialog-Month-Day.phg-firstCol{border-left:" + ("1px"+ " " +"solid"+ " " +"#bcc0c1")  + ";}.phg-CalendarDialog-Month-Day.phg-outsideMonth{background-color:" + ("#d7d9db")  + ";}.phg-CalendarDialog-Month-Day-selected{background-color:" + ("rgba(" + "13"+ ","+ " " +"13"+ ","+ " " +"255"+ ","+ " " +"0.75" + ")")  + ";}.phg-TimePickerDialog{color:" + ("black")  + ";background-color:" + ("#efefef")  + ";}.phg-TimePickerDialog-Header{color:" + ("white")  + ";background-color:" + ("black")  + ";}.phg-TimePickerDialog-TimeRow{width:") + (("60%")  + ";padding-left:" + ("16%")  + ";}.phg-TimePickerDialog-TimeDigit{background-color:" + ("#fff")  + ";margin:" + ("6px")  + ";text-align:" + ("center")  + ";font-size:" + ("40px")  + ";padding:" + ("6px")  + ";border:" + ("2px"+ " " +"solid"+ " " +"#23238e")  + ";border-radius:" + ("5px")  + ";height:" + ("42px")  + ";padding-top:" + ("4px") ) + (";}.phg-TimePickerDialog-TimeDigit-selected{background-color:" + ("rgba(" + "197"+ ","+ " " +"197"+ ","+ " " +"219"+ ","+ " " +"0.8" + ")")  + ";}.phg-TimePickerDialog-Keypad{padding-left:" + ("11.5%")  + ";}.phg-TimePickerDialog-KeypadCell{border:" + ("1px"+ " " +"solid"+ " " +"#bababa")  + ";background-image:" + ("-webkit-gradient(" + "linear"+ ","+ " " +"left"+ " " +"top"+ ","+ " " +"left"+ " " +"bottom"+ ","+ " " +"color-stop(" + "0"+ ","+ " " +"#f4f4f4" + ")"+ ","+ " " +"color-stop(" + "1"+ ","+ " " +"#d4d4d4" + ")" + ")")  + ";width:" + ("56px")  + ";height:" + ("44px")  + ";border-radius:" + ("3px")  + ";text-align:" + ("center")  + ";vertical-align:" + ("middle")  + ";font-size:" + ("26px")  + ";padding-top:") + (("10px")  + ";}.phg-TimePickerDialog-KeypadCell-minutes{background:" + ("rgba(" + "15"+ ","+ " " +"112"+ ","+ " " +"118"+ ","+ " " +"0.8" + ")")  + ";color:" + ("white")  + ";}.phg-TimePickerDialog-KeypadCell-hours{background:" + ("rgba(" + "33"+ ","+ " " +"33"+ ","+ " " +"134"+ ","+ " " +"0.94" + ")")  + ";color:" + ("white")  + ";}.phg-TimePickerDialog-Bottom{padding-left:" + ("16%")  + ";padding-top:" + ("10px")  + ";}.phg-TimePickerDialog-BottomButton{border:" + ("1px"+ " " +"solid"+ " " +"#bababa")  + ";background-image:" + ("-webkit-gradient(" + "linear"+ ","+ " " +"left"+ " " +"top"+ ","+ " " +"left"+ " " +"bottom"+ ","+ " " +"color-stop(" + "0"+ ","+ " " +"#f4f4f4" + ")"+ ","+ " " +"color-stop(" + "1"+ ","+ " " +"#d4d4d4" + ")" + ")")  + ";height:" + ("30px")  + ";border-radius:" + ("3px") ) + (";text-align:" + ("center")  + ";vertical-align:" + ("middle")  + ";font-size:" + ("18px")  + ";width:" + ("100px")  + ";padding-top:" + ("6px")  + ";margin-left:" + ("4px")  + ";}.phg-PopinDialog-Wrapper{background:" + ("transparent")  + ";color:" + ("white")  + ";font-weight:" + ("bold")  + ";text-decoration:" + ("none")  + ";}.phg-PopinDialog-TitleWrapper{background:") + (("#5f6e8b")  + ";border-top-left-radius:" + ("16px"+ " " +"16px")  + ";border-top-right-radius:" + ("16px"+ " " +"16px")  + ";border-top:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";border-left:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";border-right:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";padding-top:" + ("10px")  + ";padding-left:" + ("40px")  + ";padding-right:" + ("40px")  + ";padding-bottom:" + ("10px")  + ";font-size:" + ("1.2em") ) + (";}.phg-PopinDialog-BodyWrapper{background:" + ("#24385d")  + ";border-bottom-left-radius:" + ("16px"+ " " +"16px")  + ";border-bottom-right-radius:" + ("16px"+ " " +"16px")  + ";border-bottom:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";border-left:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";border-right:" + ("3px"+ " " +"solid"+ " " +"#bcc2cd")  + ";}.phg-PopinDialog-Wrapper a{color:" + ("white")  + ";font-weight:" + ("bold")  + ";text-decoration:" + ("none")  + ";}.phg-PopinDialog-BodyWrapper div div{padding-top:" + ("20px")  + ";}.phg-PopinDialog-MessageRow{font-size:") + (("0.7em")  + ";padding-left:" + ("1.5em")  + ";padding-right:" + ("1.5em")  + ";}.phg-PopinDialog-Separator{border-top:" + ("1px"+ " " +"solid"+ " " +"rgba(" + "128"+ ","+ " " +"128"+ ","+ " " +"128"+ ","+ " " +"0.98" + ")")  + ";margin-top:" + ("1em")  + ";}.phg-PopinDialog-ButtonsRow{padding-left:" + ("0.5em")  + ";padding-right:" + ("0.5em")  + ";}.phg-PopinDialog-ButtonsRow td{width:" + ("3em")  + ";padding-top:" + ("0")  + ";padding-bottom:" + ("1em")  + ";padding-left:" + ("1em") ) + (";padding-right:" + ("0.3em")  + ";}.phg-PopinDialog-ButtonsRow-2ndBtn{border-left:" + ("1px"+ " " +"solid"+ " " +"rgba(" + "128"+ ","+ " " +"128"+ ","+ " " +"128"+ ","+ " " +"0.98" + ")")  + ";}.phg-PopinDialog-Wrapper{font-size:" + ("20px")  + ";}.phg-InvisibleTouch{position:" + ("absolute")  + ";top:" + ("0")  + ";left:" + ("0")  + ";width:" + ("4px")  + ";height:" + ("4px")  + ";background-color:" + ("transparent")  + ";border:" + ("none")  + ";}.phg-InvisibleTouch input{border:") + (("0")  + " !important;z-index:" + ("1")  + " !important;}.phg-CheckBox{width:" + ("28px")  + ";height:" + ("28px")  + ";border:" + ("1px"+ " " +"solid"+ " " +"black")  + ";background-color:" + ("white")  + ";margin:" + ("0.2em")  + ";border-radius:" + ("1px")  + ";}.phg-CheckBox-selected{background:" + ("url(main/images/check-28.png)"+ " " +"no-repeat"+ " " +"#fff")  + ";}"));
      }
    }
    ;
  }
  private static class cssInitializer {
    static {
      _instance0.cssInitializer();
    }
    static com.google.gwt.resources.client.CssResource get() {
      return css;
    }
  }
  public com.google.gwt.resources.client.CssResource css() {
    return cssInitializer.get();
  }
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static com.google.gwt.resources.client.DataResource calendarImage;
  private static com.google.gwt.resources.client.DataResource clockImage;
  private static com.google.gwt.resources.client.CssResource css;
  
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      calendarImage(), 
      clockImage(), 
      css(), 
    };
  }
  public ResourcePrototype getResource(String name) {
    if (GWT.isScript()) {
      return getResourceNative(name);
    } else {
      if (resourceMap == null) {
        resourceMap = new java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype>();
        resourceMap.put("calendarImage", calendarImage());
        resourceMap.put("clockImage", clockImage());
        resourceMap.put("css", css());
      }
      return resourceMap.get(name);
    }
  }
  private native ResourcePrototype getResourceNative(String name) /*-{
    switch (name) {
      case 'calendarImage': return this.@it.mate.phgcommons.client.ui.theme.DefaultTheme.ThemeBundle::calendarImage()();
      case 'clockImage': return this.@it.mate.phgcommons.client.ui.theme.DefaultTheme.ThemeBundle::clockImage()();
      case 'css': return this.@it.mate.phgcommons.client.ui.theme.DefaultTheme.ThemeBundleAndroid::css()();
    }
    return null;
  }-*/;
}
