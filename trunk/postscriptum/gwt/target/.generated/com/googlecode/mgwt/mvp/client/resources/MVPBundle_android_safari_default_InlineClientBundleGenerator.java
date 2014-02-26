package com.googlecode.mgwt.mvp.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class MVPBundle_android_safari_default_InlineClientBundleGenerator implements com.googlecode.mgwt.mvp.client.resources.MVPBundle {
  private static MVPBundle_android_safari_default_InlineClientBundleGenerator _instance0 = new MVPBundle_android_safari_default_InlineClientBundleGenerator();
  private void animationCssInitializer() {
    animationCss = new com.googlecode.mgwt.mvp.client.resources.AnimationCss() {
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
        return "animationCss";
      }
      public String getText() {
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GEEN40JDFI{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GEEN40JDEI{position:" + ("absolute")  + ";top:" + ("0")  + ";right:" + ("0")  + ";left:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}@-webkit-keyframes donothing {\n    from { opacity: 0.9; }\n    to { opacity: 1; }\n}.GEEN40JDJI{-webkit-animation-timing-function:") + (("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("10")  + ";}.GEEN40JDKI{-webkit-animation-timing-function:" + ("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("0")  + " !important;}.GEEN40JDNI.GEEN40JDJI{-webkit-animation-name:" + ("slideinfromright")  + ";}.GEEN40JDNI.GEEN40JDKI{-webkit-animation-name:" + ("slideouttoleft")  + ";}.GEEN40JDNI.GEEN40JDJI.GEEN40JDMI{-webkit-animation-name:" + ("slideinfromleft") ) + (";}.GEEN40JDNI.GEEN40JDKI.GEEN40JDMI{-webkit-animation-name:" + ("slideouttoright")  + ";}@-webkit-keyframes slideinfromright {\n    from { -webkit-transform: translateX(100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideinfromleft {\n    from { -webkit-transform: translateX(-100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideouttoleft {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(-100%); }\n}@-webkit-keyframes slideouttoright {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(100%); }\n}.GEEN40JDII{-webkit-animation-duration:" + ("0.65s")  + ";-webkit-backface-visibility:" + ("hidden")  + ";}.GEEN40JDII.GEEN40JDJI{-webkit-animation-name:" + ("flipinfromleft")  + ";}.GEEN40JDII.GEEN40JDKI{-webkit-animation-name:" + ("flipouttoleft")  + ";}.GEEN40JDII.GEEN40JDJI.GEEN40JDMI{-webkit-animation-name:" + ("flipinfromright")  + ";}.GEEN40JDII.GEEN40JDKI.GEEN40JDMI{-webkit-animation-name:" + ("flipouttoright")  + ";}@-webkit-keyframes flipinfromright {\n    from { -webkit-transform: rotateY(-180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipinfromleft {\n    from { -webkit-transform: rotateY(180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipouttoleft {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(-180deg) scale(.8); }\n}@-webkit-keyframes flipouttoright {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(180deg) scale(.8); }\n}@-webkit-keyframes fadein {\n    from { opacity: 0; }\n    to { opacity: 1; }\n}@-webkit-keyframes fadeout {\n    from { opacity: 1; }\n    to { opacity: 0; }\n}.GEEN40JDHI.GEEN40JDJI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("fadein")  + ";}.GEEN40JDHI.GEEN40JDKI{z-index:" + ("8")  + ";-webkit-animation-name:") + (("fadeout")  + ";}.GEEN40JDGI.GEEN40JDJI{-webkit-animation-name:" + ("fadein")  + ";}.GEEN40JDGI.GEEN40JDKI{-webkit-animation-name:" + ("fadeout")  + ";}.GEEN40JDOI.GEEN40JDJI{-webkit-animation-name:" + ("slideupfrombottom")  + ";z-index:" + ("10")  + ";}.GEEN40JDOI.GEEN40JDKI{-webkit-animation-name:" + ("slideupfrommiddle")  + ";z-index:" + ("0")  + ";}.GEEN40JDOI.GEEN40JDKI.GEEN40JDMI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("slidedownfrommiddle")  + ";}.GEEN40JDOI.GEEN40JDJI.GEEN40JDMI{z-index:" + ("0")  + ";-webkit-animation-name:" + ("slidedownfromtop") ) + (";}@-webkit-keyframes slideupfrombottom {\n    from { -webkit-transform: translateY(100%); }\n    to { -webkit-transform: translateY(0); }\n}@-webkit-keyframes slidedownfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(100%); }\n}@-webkit-keyframes slideupfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(-100%); }\n}@-webkit-keyframes slidedownfromtop {\n    from { -webkit-transform: translateY(-100%); }\n    to { -webkit-transform: translateY(0%); }\n}.GEEN40JDPI{-webkit-transform:" + ("perspective(" + "800" + ")")  + ";-webkit-animation-duration:" + ("0.7s")  + ";}.GEEN40JDPI.GEEN40JDKI{-webkit-animation-name:" + ("swapouttoleft")  + ";}.GEEN40JDPI.GEEN40JDJI{-webkit-animation-name:" + ("swapinfromright")  + ";}.GEEN40JDPI.GEEN40JDKI.GEEN40JDMI{-webkit-animation-name:" + ("swapouttoright")  + ";}.GEEN40JDPI.GEEN40JDJI.GEEN40JDMI{-webkit-animation-name:" + ("swapinfromleft")  + ";}@-webkit-keyframes swapouttoright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n    \n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n       \n    }\n    100% {\n        -webkit-transform:  translate3d(0px, 0px, -800px) rotateY(70deg);\n         opacity: 0;\n    }\n}@-webkit-keyframes swapouttoleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n        -webkit-transform:  translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        opacity: 0;\n    }\n}@-webkit-keyframes swapinfromright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}@-webkit-keyframes swapinfromleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}.GEEN40JDLI.GEEN40JDJI{-webkit-animation-name:" + ("popin")  + ";}.GEEN40JDLI.GEEN40JDKI{-webkit-animation-name:" + ("popout")  + ";}@-webkit-keyframes popin {\n    from {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n    to {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n}@-webkit-keyframes popout {\n    from {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n    to {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n}")) : ((".GEEN40JDFI{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GEEN40JDEI{position:" + ("absolute")  + ";top:" + ("0")  + ";left:" + ("0")  + ";right:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}@-webkit-keyframes donothing {\n    from { opacity: 0.9; }\n    to { opacity: 1; }\n}.GEEN40JDJI{-webkit-animation-timing-function:") + (("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("10")  + ";}.GEEN40JDKI{-webkit-animation-timing-function:" + ("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("0")  + " !important;}.GEEN40JDNI.GEEN40JDJI{-webkit-animation-name:" + ("slideinfromright")  + ";}.GEEN40JDNI.GEEN40JDKI{-webkit-animation-name:" + ("slideouttoleft")  + ";}.GEEN40JDNI.GEEN40JDJI.GEEN40JDMI{-webkit-animation-name:" + ("slideinfromleft") ) + (";}.GEEN40JDNI.GEEN40JDKI.GEEN40JDMI{-webkit-animation-name:" + ("slideouttoright")  + ";}@-webkit-keyframes slideinfromright {\n    from { -webkit-transform: translateX(100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideinfromleft {\n    from { -webkit-transform: translateX(-100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideouttoleft {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(-100%); }\n}@-webkit-keyframes slideouttoright {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(100%); }\n}.GEEN40JDII{-webkit-animation-duration:" + ("0.65s")  + ";-webkit-backface-visibility:" + ("hidden")  + ";}.GEEN40JDII.GEEN40JDJI{-webkit-animation-name:" + ("flipinfromleft")  + ";}.GEEN40JDII.GEEN40JDKI{-webkit-animation-name:" + ("flipouttoleft")  + ";}.GEEN40JDII.GEEN40JDJI.GEEN40JDMI{-webkit-animation-name:" + ("flipinfromright")  + ";}.GEEN40JDII.GEEN40JDKI.GEEN40JDMI{-webkit-animation-name:" + ("flipouttoright")  + ";}@-webkit-keyframes flipinfromright {\n    from { -webkit-transform: rotateY(-180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipinfromleft {\n    from { -webkit-transform: rotateY(180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipouttoleft {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(-180deg) scale(.8); }\n}@-webkit-keyframes flipouttoright {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(180deg) scale(.8); }\n}@-webkit-keyframes fadein {\n    from { opacity: 0; }\n    to { opacity: 1; }\n}@-webkit-keyframes fadeout {\n    from { opacity: 1; }\n    to { opacity: 0; }\n}.GEEN40JDHI.GEEN40JDJI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("fadein")  + ";}.GEEN40JDHI.GEEN40JDKI{z-index:" + ("8")  + ";-webkit-animation-name:") + (("fadeout")  + ";}.GEEN40JDGI.GEEN40JDJI{-webkit-animation-name:" + ("fadein")  + ";}.GEEN40JDGI.GEEN40JDKI{-webkit-animation-name:" + ("fadeout")  + ";}.GEEN40JDOI.GEEN40JDJI{-webkit-animation-name:" + ("slideupfrombottom")  + ";z-index:" + ("10")  + ";}.GEEN40JDOI.GEEN40JDKI{-webkit-animation-name:" + ("slideupfrommiddle")  + ";z-index:" + ("0")  + ";}.GEEN40JDOI.GEEN40JDKI.GEEN40JDMI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("slidedownfrommiddle")  + ";}.GEEN40JDOI.GEEN40JDJI.GEEN40JDMI{z-index:" + ("0")  + ";-webkit-animation-name:" + ("slidedownfromtop") ) + (";}@-webkit-keyframes slideupfrombottom {\n    from { -webkit-transform: translateY(100%); }\n    to { -webkit-transform: translateY(0); }\n}@-webkit-keyframes slidedownfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(100%); }\n}@-webkit-keyframes slideupfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(-100%); }\n}@-webkit-keyframes slidedownfromtop {\n    from { -webkit-transform: translateY(-100%); }\n    to { -webkit-transform: translateY(0%); }\n}.GEEN40JDPI{-webkit-transform:" + ("perspective(" + "800" + ")")  + ";-webkit-animation-duration:" + ("0.7s")  + ";}.GEEN40JDPI.GEEN40JDKI{-webkit-animation-name:" + ("swapouttoleft")  + ";}.GEEN40JDPI.GEEN40JDJI{-webkit-animation-name:" + ("swapinfromright")  + ";}.GEEN40JDPI.GEEN40JDKI.GEEN40JDMI{-webkit-animation-name:" + ("swapouttoright")  + ";}.GEEN40JDPI.GEEN40JDJI.GEEN40JDMI{-webkit-animation-name:" + ("swapinfromleft")  + ";}@-webkit-keyframes swapouttoright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n    \n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n       \n    }\n    100% {\n        -webkit-transform:  translate3d(0px, 0px, -800px) rotateY(70deg);\n         opacity: 0;\n    }\n}@-webkit-keyframes swapouttoleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n        -webkit-transform:  translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        opacity: 0;\n    }\n}@-webkit-keyframes swapinfromright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}@-webkit-keyframes swapinfromleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}.GEEN40JDLI.GEEN40JDJI{-webkit-animation-name:" + ("popin")  + ";}.GEEN40JDLI.GEEN40JDKI{-webkit-animation-name:" + ("popout")  + ";}@-webkit-keyframes popin {\n    from {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n    to {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n}@-webkit-keyframes popout {\n    from {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n    to {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n}"));
      }
      public java.lang.String display(){
        return "GEEN40JDEI";
      }
      public java.lang.String displayContainer(){
        return "GEEN40JDFI";
      }
      public java.lang.String dissolve(){
        return "GEEN40JDGI";
      }
      public java.lang.String fade(){
        return "GEEN40JDHI";
      }
      public java.lang.String flip(){
        return "GEEN40JDII";
      }
      public java.lang.String in(){
        return "GEEN40JDJI";
      }
      public java.lang.String out(){
        return "GEEN40JDKI";
      }
      public java.lang.String pop(){
        return "GEEN40JDLI";
      }
      public java.lang.String reverse(){
        return "GEEN40JDMI";
      }
      public java.lang.String slide(){
        return "GEEN40JDNI";
      }
      public java.lang.String slideup(){
        return "GEEN40JDOI";
      }
      public java.lang.String swap(){
        return "GEEN40JDPI";
      }
    }
    ;
  }
  private static class animationCssInitializer {
    static {
      _instance0.animationCssInitializer();
    }
    static com.googlecode.mgwt.mvp.client.resources.AnimationCss get() {
      return animationCss;
    }
  }
  public com.googlecode.mgwt.mvp.client.resources.AnimationCss animationCss() {
    return animationCssInitializer.get();
  }
  private void transitionCssInitializer() {
    transitionCss = new com.googlecode.mgwt.mvp.client.resources.TransistionCss() {
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
        return "transitionCss";
      }
      public String getText() {
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GEEN40JDAJ{position:" + ("absolute")  + ";top:" + ("0")  + ";right:" + ("0")  + ";left:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}.GEEN40JDBJ{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GEEN40JDKJ,.GEEN40JDLJ{-webkit-transition-property:") + (("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GEEN40JDKJ.GEEN40JDGJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GEEN40JDKJ.GEEN40JDGJ.GEEN40JDDJ,.GEEN40JDKJ.GEEN40JDHJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GEEN40JDKJ.GEEN40JDHJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GEEN40JDKJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GEEN40JDKJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GEEN40JDKJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GEEN40JDKJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GEEN40JDLJ.GEEN40JDGJ.GEEN40JDMJ{-webkit-transform:" + ("translateY(" + "100%" + ")") ) + (";}.GEEN40JDLJ.GEEN40JDGJ.GEEN40JDDJ,.GEEN40JDLJ.GEEN40JDHJ.GEEN40JDMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GEEN40JDLJ.GEEN40JDHJ.GEEN40JDDJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GEEN40JDLJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GEEN40JDLJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateY(" + "100%" + ")")  + ";}.GEEN40JDLJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GEEN40JDLJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GEEN40JDCJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GEEN40JDCJ.GEEN40JDGJ.GEEN40JDMJ{opacity:" + ("0")  + ";}.GEEN40JDCJ.GEEN40JDGJ.GEEN40JDDJ,.GEEN40JDCJ.GEEN40JDHJ.GEEN40JDMJ{opacity:") + (("1")  + ";}.GEEN40JDCJ.GEEN40JDHJ.GEEN40JDDJ{opacity:" + ("0")  + ";}.GEEN40JDCJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{opacity:" + ("1")  + ";}.GEEN40JDCJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ,.GEEN40JDCJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{opacity:" + ("0")  + ";}.GEEN40JDCJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{opacity:" + ("1")  + ";}.GEEN40JDFJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out"+ ","+ " " +"ease-in-out")  + ";-webkit-perspective:" + ("800")  + ";-webkit-transform-style:" + ("preserve-3d")  + ";-webkit-backface-visibility:" + ("hidden") ) + (";}.GEEN40JDFJ.GEEN40JDGJ.GEEN40JDMJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GEEN40JDFJ.GEEN40JDGJ.GEEN40JDDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GEEN40JDFJ.GEEN40JDHJ.GEEN40JDMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GEEN40JDFJ.GEEN40JDHJ.GEEN40JDDJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:") + (("0")  + ";opacity:" + ("0")  + ";}.GEEN40JDFJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GEEN40JDFJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GEEN40JDFJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1") ) + (";}.GEEN40JDFJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GEEN40JDNJ{-webkit-transition-property:" + ("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GEEN40JDNJ.GEEN40JDGJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("1")  + ";}.GEEN40JDNJ.GEEN40JDGJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";}.GEEN40JDNJ.GEEN40JDHJ.GEEN40JDMJ{-webkit-transform:") + (("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GEEN40JDNJ.GEEN40JDHJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GEEN40JDNJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GEEN40JDNJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GEEN40JDNJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";}.GEEN40JDNJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";}.GEEN40JDIJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity") ) + (";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GEEN40JDIJ.GEEN40JDGJ.GEEN40JDMJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GEEN40JDIJ.GEEN40JDGJ.GEEN40JDDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GEEN40JDIJ.GEEN40JDHJ.GEEN40JDMJ{opacity:" + ("1")  + ";-webkit-transform:" + ("scale(" + "1" + ")")  + ";z-index:") + (("0")  + ";}.GEEN40JDIJ.GEEN40JDHJ.GEEN40JDDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GEEN40JDIJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("1")  + ";}.GEEN40JDIJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GEEN40JDIJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("scale(" + "0.3" + ")") ) + (";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GEEN40JDIJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GEEN40JDEJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GEEN40JDEJ.GEEN40JDGJ.GEEN40JDMJ{opacity:" + ("0")  + ";}.GEEN40JDEJ.GEEN40JDGJ.GEEN40JDDJ,.GEEN40JDEJ.GEEN40JDHJ.GEEN40JDMJ{opacity:" + ("1")  + ";}.GEEN40JDEJ.GEEN40JDHJ.GEEN40JDDJ{opacity:") + (("0")  + ";}.GEEN40JDEJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{opacity:" + ("1")  + ";}.GEEN40JDEJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ,.GEEN40JDEJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{opacity:" + ("0")  + ";}.GEEN40JDEJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{opacity:" + ("1")  + ";}")) : ((".GEEN40JDAJ{position:" + ("absolute")  + ";top:" + ("0")  + ";left:" + ("0")  + ";right:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}.GEEN40JDBJ{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GEEN40JDKJ,.GEEN40JDLJ{-webkit-transition-property:") + (("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GEEN40JDKJ.GEEN40JDGJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GEEN40JDKJ.GEEN40JDGJ.GEEN40JDDJ,.GEEN40JDKJ.GEEN40JDHJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GEEN40JDKJ.GEEN40JDHJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GEEN40JDKJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GEEN40JDKJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GEEN40JDKJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GEEN40JDKJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GEEN40JDLJ.GEEN40JDGJ.GEEN40JDMJ{-webkit-transform:" + ("translateY(" + "100%" + ")") ) + (";}.GEEN40JDLJ.GEEN40JDGJ.GEEN40JDDJ,.GEEN40JDLJ.GEEN40JDHJ.GEEN40JDMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GEEN40JDLJ.GEEN40JDHJ.GEEN40JDDJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GEEN40JDLJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GEEN40JDLJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateY(" + "100%" + ")")  + ";}.GEEN40JDLJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GEEN40JDLJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GEEN40JDCJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GEEN40JDCJ.GEEN40JDGJ.GEEN40JDMJ{opacity:" + ("0")  + ";}.GEEN40JDCJ.GEEN40JDGJ.GEEN40JDDJ,.GEEN40JDCJ.GEEN40JDHJ.GEEN40JDMJ{opacity:") + (("1")  + ";}.GEEN40JDCJ.GEEN40JDHJ.GEEN40JDDJ{opacity:" + ("0")  + ";}.GEEN40JDCJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{opacity:" + ("1")  + ";}.GEEN40JDCJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ,.GEEN40JDCJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{opacity:" + ("0")  + ";}.GEEN40JDCJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{opacity:" + ("1")  + ";}.GEEN40JDFJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out"+ ","+ " " +"ease-in-out")  + ";-webkit-perspective:" + ("800")  + ";-webkit-transform-style:" + ("preserve-3d")  + ";-webkit-backface-visibility:" + ("hidden") ) + (";}.GEEN40JDFJ.GEEN40JDGJ.GEEN40JDMJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GEEN40JDFJ.GEEN40JDGJ.GEEN40JDDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GEEN40JDFJ.GEEN40JDHJ.GEEN40JDMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GEEN40JDFJ.GEEN40JDHJ.GEEN40JDDJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:") + (("0")  + ";opacity:" + ("0")  + ";}.GEEN40JDFJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GEEN40JDFJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GEEN40JDFJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1") ) + (";}.GEEN40JDFJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GEEN40JDNJ{-webkit-transition-property:" + ("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GEEN40JDNJ.GEEN40JDGJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("1")  + ";}.GEEN40JDNJ.GEEN40JDGJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";}.GEEN40JDNJ.GEEN40JDHJ.GEEN40JDMJ{-webkit-transform:") + (("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GEEN40JDNJ.GEEN40JDHJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GEEN40JDNJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GEEN40JDNJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GEEN40JDNJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";}.GEEN40JDNJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";}.GEEN40JDIJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity") ) + (";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GEEN40JDIJ.GEEN40JDGJ.GEEN40JDMJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GEEN40JDIJ.GEEN40JDGJ.GEEN40JDDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GEEN40JDIJ.GEEN40JDHJ.GEEN40JDMJ{opacity:" + ("1")  + ";-webkit-transform:" + ("scale(" + "1" + ")")  + ";z-index:") + (("0")  + ";}.GEEN40JDIJ.GEEN40JDHJ.GEEN40JDDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GEEN40JDIJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("1")  + ";}.GEEN40JDIJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GEEN40JDIJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{-webkit-transform:" + ("scale(" + "0.3" + ")") ) + (";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GEEN40JDIJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GEEN40JDEJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GEEN40JDEJ.GEEN40JDGJ.GEEN40JDMJ{opacity:" + ("0")  + ";}.GEEN40JDEJ.GEEN40JDGJ.GEEN40JDDJ,.GEEN40JDEJ.GEEN40JDHJ.GEEN40JDMJ{opacity:" + ("1")  + ";}.GEEN40JDEJ.GEEN40JDHJ.GEEN40JDDJ{opacity:") + (("0")  + ";}.GEEN40JDEJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDMJ{opacity:" + ("1")  + ";}.GEEN40JDEJ.GEEN40JDHJ.GEEN40JDJJ.GEEN40JDDJ,.GEEN40JDEJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDMJ{opacity:" + ("0")  + ";}.GEEN40JDEJ.GEEN40JDGJ.GEEN40JDJJ.GEEN40JDDJ{opacity:" + ("1")  + ";}"));
      }
      public java.lang.String display(){
        return "GEEN40JDAJ";
      }
      public java.lang.String displayContainer(){
        return "GEEN40JDBJ";
      }
      public java.lang.String dissolve(){
        return "GEEN40JDCJ";
      }
      public java.lang.String end(){
        return "GEEN40JDDJ";
      }
      public java.lang.String fade(){
        return "GEEN40JDEJ";
      }
      public java.lang.String flip(){
        return "GEEN40JDFJ";
      }
      public java.lang.String in(){
        return "GEEN40JDGJ";
      }
      public java.lang.String out(){
        return "GEEN40JDHJ";
      }
      public java.lang.String pop(){
        return "GEEN40JDIJ";
      }
      public java.lang.String reverse(){
        return "GEEN40JDJJ";
      }
      public java.lang.String slide(){
        return "GEEN40JDKJ";
      }
      public java.lang.String slideup(){
        return "GEEN40JDLJ";
      }
      public java.lang.String start(){
        return "GEEN40JDMJ";
      }
      public java.lang.String swap(){
        return "GEEN40JDNJ";
      }
    }
    ;
  }
  private static class transitionCssInitializer {
    static {
      _instance0.transitionCssInitializer();
    }
    static com.googlecode.mgwt.mvp.client.resources.TransistionCss get() {
      return transitionCss;
    }
  }
  public com.googlecode.mgwt.mvp.client.resources.TransistionCss transitionCss() {
    return transitionCssInitializer.get();
  }
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static com.googlecode.mgwt.mvp.client.resources.AnimationCss animationCss;
  private static com.googlecode.mgwt.mvp.client.resources.TransistionCss transitionCss;
  
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      animationCss(), 
      transitionCss(), 
    };
  }
  public ResourcePrototype getResource(String name) {
    if (GWT.isScript()) {
      return getResourceNative(name);
    } else {
      if (resourceMap == null) {
        resourceMap = new java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype>();
        resourceMap.put("animationCss", animationCss());
        resourceMap.put("transitionCss", transitionCss());
      }
      return resourceMap.get(name);
    }
  }
  private native ResourcePrototype getResourceNative(String name) /*-{
    switch (name) {
      case 'animationCss': return this.@com.googlecode.mgwt.mvp.client.resources.MVPBundle::animationCss()();
      case 'transitionCss': return this.@com.googlecode.mgwt.mvp.client.resources.MVPBundle::transitionCss()();
    }
    return null;
  }-*/;
}
