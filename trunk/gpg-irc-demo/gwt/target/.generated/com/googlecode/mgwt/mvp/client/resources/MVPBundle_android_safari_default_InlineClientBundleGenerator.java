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
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GOE-WOTBFI{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GOE-WOTBEI{position:" + ("absolute")  + ";top:" + ("0")  + ";right:" + ("0")  + ";left:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}@-webkit-keyframes donothing {\n    from { opacity: 0.9; }\n    to { opacity: 1; }\n}.GOE-WOTBJI{-webkit-animation-timing-function:") + (("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("10")  + ";}.GOE-WOTBKI{-webkit-animation-timing-function:" + ("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("0")  + " !important;}.GOE-WOTBNI.GOE-WOTBJI{-webkit-animation-name:" + ("slideinfromright")  + ";}.GOE-WOTBNI.GOE-WOTBKI{-webkit-animation-name:" + ("slideouttoleft")  + ";}.GOE-WOTBNI.GOE-WOTBJI.GOE-WOTBMI{-webkit-animation-name:" + ("slideinfromleft") ) + (";}.GOE-WOTBNI.GOE-WOTBKI.GOE-WOTBMI{-webkit-animation-name:" + ("slideouttoright")  + ";}@-webkit-keyframes slideinfromright {\n    from { -webkit-transform: translateX(100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideinfromleft {\n    from { -webkit-transform: translateX(-100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideouttoleft {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(-100%); }\n}@-webkit-keyframes slideouttoright {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(100%); }\n}.GOE-WOTBII{-webkit-animation-duration:" + ("0.65s")  + ";-webkit-backface-visibility:" + ("hidden")  + ";}.GOE-WOTBII.GOE-WOTBJI{-webkit-animation-name:" + ("flipinfromleft")  + ";}.GOE-WOTBII.GOE-WOTBKI{-webkit-animation-name:" + ("flipouttoleft")  + ";}.GOE-WOTBII.GOE-WOTBJI.GOE-WOTBMI{-webkit-animation-name:" + ("flipinfromright")  + ";}.GOE-WOTBII.GOE-WOTBKI.GOE-WOTBMI{-webkit-animation-name:" + ("flipouttoright")  + ";}@-webkit-keyframes flipinfromright {\n    from { -webkit-transform: rotateY(-180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipinfromleft {\n    from { -webkit-transform: rotateY(180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipouttoleft {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(-180deg) scale(.8); }\n}@-webkit-keyframes flipouttoright {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(180deg) scale(.8); }\n}@-webkit-keyframes fadein {\n    from { opacity: 0; }\n    to { opacity: 1; }\n}@-webkit-keyframes fadeout {\n    from { opacity: 1; }\n    to { opacity: 0; }\n}.GOE-WOTBHI.GOE-WOTBJI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("fadein")  + ";}.GOE-WOTBHI.GOE-WOTBKI{z-index:" + ("8")  + ";-webkit-animation-name:") + (("fadeout")  + ";}.GOE-WOTBGI.GOE-WOTBJI{-webkit-animation-name:" + ("fadein")  + ";}.GOE-WOTBGI.GOE-WOTBKI{-webkit-animation-name:" + ("fadeout")  + ";}.GOE-WOTBOI.GOE-WOTBJI{-webkit-animation-name:" + ("slideupfrombottom")  + ";z-index:" + ("10")  + ";}.GOE-WOTBOI.GOE-WOTBKI{-webkit-animation-name:" + ("slideupfrommiddle")  + ";z-index:" + ("0")  + ";}.GOE-WOTBOI.GOE-WOTBKI.GOE-WOTBMI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("slidedownfrommiddle")  + ";}.GOE-WOTBOI.GOE-WOTBJI.GOE-WOTBMI{z-index:" + ("0")  + ";-webkit-animation-name:" + ("slidedownfromtop") ) + (";}@-webkit-keyframes slideupfrombottom {\n    from { -webkit-transform: translateY(100%); }\n    to { -webkit-transform: translateY(0); }\n}@-webkit-keyframes slidedownfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(100%); }\n}@-webkit-keyframes slideupfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(-100%); }\n}@-webkit-keyframes slidedownfromtop {\n    from { -webkit-transform: translateY(-100%); }\n    to { -webkit-transform: translateY(0%); }\n}.GOE-WOTBPI{-webkit-transform:" + ("perspective(" + "800" + ")")  + ";-webkit-animation-duration:" + ("0.7s")  + ";}.GOE-WOTBPI.GOE-WOTBKI{-webkit-animation-name:" + ("swapouttoleft")  + ";}.GOE-WOTBPI.GOE-WOTBJI{-webkit-animation-name:" + ("swapinfromright")  + ";}.GOE-WOTBPI.GOE-WOTBKI.GOE-WOTBMI{-webkit-animation-name:" + ("swapouttoright")  + ";}.GOE-WOTBPI.GOE-WOTBJI.GOE-WOTBMI{-webkit-animation-name:" + ("swapinfromleft")  + ";}@-webkit-keyframes swapouttoright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n    \n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n       \n    }\n    100% {\n        -webkit-transform:  translate3d(0px, 0px, -800px) rotateY(70deg);\n         opacity: 0;\n    }\n}@-webkit-keyframes swapouttoleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n        -webkit-transform:  translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        opacity: 0;\n    }\n}@-webkit-keyframes swapinfromright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}@-webkit-keyframes swapinfromleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}.GOE-WOTBLI.GOE-WOTBJI{-webkit-animation-name:" + ("popin")  + ";}.GOE-WOTBLI.GOE-WOTBKI{-webkit-animation-name:" + ("popout")  + ";}@-webkit-keyframes popin {\n    from {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n    to {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n}@-webkit-keyframes popout {\n    from {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n    to {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n}")) : ((".GOE-WOTBFI{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GOE-WOTBEI{position:" + ("absolute")  + ";top:" + ("0")  + ";left:" + ("0")  + ";right:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}@-webkit-keyframes donothing {\n    from { opacity: 0.9; }\n    to { opacity: 1; }\n}.GOE-WOTBJI{-webkit-animation-timing-function:") + (("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("10")  + ";}.GOE-WOTBKI{-webkit-animation-timing-function:" + ("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("0")  + " !important;}.GOE-WOTBNI.GOE-WOTBJI{-webkit-animation-name:" + ("slideinfromright")  + ";}.GOE-WOTBNI.GOE-WOTBKI{-webkit-animation-name:" + ("slideouttoleft")  + ";}.GOE-WOTBNI.GOE-WOTBJI.GOE-WOTBMI{-webkit-animation-name:" + ("slideinfromleft") ) + (";}.GOE-WOTBNI.GOE-WOTBKI.GOE-WOTBMI{-webkit-animation-name:" + ("slideouttoright")  + ";}@-webkit-keyframes slideinfromright {\n    from { -webkit-transform: translateX(100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideinfromleft {\n    from { -webkit-transform: translateX(-100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideouttoleft {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(-100%); }\n}@-webkit-keyframes slideouttoright {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(100%); }\n}.GOE-WOTBII{-webkit-animation-duration:" + ("0.65s")  + ";-webkit-backface-visibility:" + ("hidden")  + ";}.GOE-WOTBII.GOE-WOTBJI{-webkit-animation-name:" + ("flipinfromleft")  + ";}.GOE-WOTBII.GOE-WOTBKI{-webkit-animation-name:" + ("flipouttoleft")  + ";}.GOE-WOTBII.GOE-WOTBJI.GOE-WOTBMI{-webkit-animation-name:" + ("flipinfromright")  + ";}.GOE-WOTBII.GOE-WOTBKI.GOE-WOTBMI{-webkit-animation-name:" + ("flipouttoright")  + ";}@-webkit-keyframes flipinfromright {\n    from { -webkit-transform: rotateY(-180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipinfromleft {\n    from { -webkit-transform: rotateY(180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipouttoleft {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(-180deg) scale(.8); }\n}@-webkit-keyframes flipouttoright {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(180deg) scale(.8); }\n}@-webkit-keyframes fadein {\n    from { opacity: 0; }\n    to { opacity: 1; }\n}@-webkit-keyframes fadeout {\n    from { opacity: 1; }\n    to { opacity: 0; }\n}.GOE-WOTBHI.GOE-WOTBJI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("fadein")  + ";}.GOE-WOTBHI.GOE-WOTBKI{z-index:" + ("8")  + ";-webkit-animation-name:") + (("fadeout")  + ";}.GOE-WOTBGI.GOE-WOTBJI{-webkit-animation-name:" + ("fadein")  + ";}.GOE-WOTBGI.GOE-WOTBKI{-webkit-animation-name:" + ("fadeout")  + ";}.GOE-WOTBOI.GOE-WOTBJI{-webkit-animation-name:" + ("slideupfrombottom")  + ";z-index:" + ("10")  + ";}.GOE-WOTBOI.GOE-WOTBKI{-webkit-animation-name:" + ("slideupfrommiddle")  + ";z-index:" + ("0")  + ";}.GOE-WOTBOI.GOE-WOTBKI.GOE-WOTBMI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("slidedownfrommiddle")  + ";}.GOE-WOTBOI.GOE-WOTBJI.GOE-WOTBMI{z-index:" + ("0")  + ";-webkit-animation-name:" + ("slidedownfromtop") ) + (";}@-webkit-keyframes slideupfrombottom {\n    from { -webkit-transform: translateY(100%); }\n    to { -webkit-transform: translateY(0); }\n}@-webkit-keyframes slidedownfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(100%); }\n}@-webkit-keyframes slideupfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(-100%); }\n}@-webkit-keyframes slidedownfromtop {\n    from { -webkit-transform: translateY(-100%); }\n    to { -webkit-transform: translateY(0%); }\n}.GOE-WOTBPI{-webkit-transform:" + ("perspective(" + "800" + ")")  + ";-webkit-animation-duration:" + ("0.7s")  + ";}.GOE-WOTBPI.GOE-WOTBKI{-webkit-animation-name:" + ("swapouttoleft")  + ";}.GOE-WOTBPI.GOE-WOTBJI{-webkit-animation-name:" + ("swapinfromright")  + ";}.GOE-WOTBPI.GOE-WOTBKI.GOE-WOTBMI{-webkit-animation-name:" + ("swapouttoright")  + ";}.GOE-WOTBPI.GOE-WOTBJI.GOE-WOTBMI{-webkit-animation-name:" + ("swapinfromleft")  + ";}@-webkit-keyframes swapouttoright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n    \n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n       \n    }\n    100% {\n        -webkit-transform:  translate3d(0px, 0px, -800px) rotateY(70deg);\n         opacity: 0;\n    }\n}@-webkit-keyframes swapouttoleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n        -webkit-transform:  translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        opacity: 0;\n    }\n}@-webkit-keyframes swapinfromright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}@-webkit-keyframes swapinfromleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}.GOE-WOTBLI.GOE-WOTBJI{-webkit-animation-name:" + ("popin")  + ";}.GOE-WOTBLI.GOE-WOTBKI{-webkit-animation-name:" + ("popout")  + ";}@-webkit-keyframes popin {\n    from {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n    to {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n}@-webkit-keyframes popout {\n    from {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n    to {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n}"));
      }
      public java.lang.String display(){
        return "GOE-WOTBEI";
      }
      public java.lang.String displayContainer(){
        return "GOE-WOTBFI";
      }
      public java.lang.String dissolve(){
        return "GOE-WOTBGI";
      }
      public java.lang.String fade(){
        return "GOE-WOTBHI";
      }
      public java.lang.String flip(){
        return "GOE-WOTBII";
      }
      public java.lang.String in(){
        return "GOE-WOTBJI";
      }
      public java.lang.String out(){
        return "GOE-WOTBKI";
      }
      public java.lang.String pop(){
        return "GOE-WOTBLI";
      }
      public java.lang.String reverse(){
        return "GOE-WOTBMI";
      }
      public java.lang.String slide(){
        return "GOE-WOTBNI";
      }
      public java.lang.String slideup(){
        return "GOE-WOTBOI";
      }
      public java.lang.String swap(){
        return "GOE-WOTBPI";
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
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GOE-WOTBAJ{position:" + ("absolute")  + ";top:" + ("0")  + ";right:" + ("0")  + ";left:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}.GOE-WOTBBJ{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GOE-WOTBKJ,.GOE-WOTBLJ{-webkit-transition-property:") + (("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GOE-WOTBKJ.GOE-WOTBGJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBGJ.GOE-WOTBDJ,.GOE-WOTBKJ.GOE-WOTBHJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBHJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBGJ.GOE-WOTBMJ{-webkit-transform:" + ("translateY(" + "100%" + ")") ) + (";}.GOE-WOTBLJ.GOE-WOTBGJ.GOE-WOTBDJ,.GOE-WOTBLJ.GOE-WOTBHJ.GOE-WOTBMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBHJ.GOE-WOTBDJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateY(" + "100%" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GOE-WOTBCJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GOE-WOTBCJ.GOE-WOTBGJ.GOE-WOTBMJ{opacity:" + ("0")  + ";}.GOE-WOTBCJ.GOE-WOTBGJ.GOE-WOTBDJ,.GOE-WOTBCJ.GOE-WOTBHJ.GOE-WOTBMJ{opacity:") + (("1")  + ";}.GOE-WOTBCJ.GOE-WOTBHJ.GOE-WOTBDJ{opacity:" + ("0")  + ";}.GOE-WOTBCJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{opacity:" + ("1")  + ";}.GOE-WOTBCJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ,.GOE-WOTBCJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{opacity:" + ("0")  + ";}.GOE-WOTBCJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{opacity:" + ("1")  + ";}.GOE-WOTBFJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out"+ ","+ " " +"ease-in-out")  + ";-webkit-perspective:" + ("800")  + ";-webkit-transform-style:" + ("preserve-3d")  + ";-webkit-backface-visibility:" + ("hidden") ) + (";}.GOE-WOTBFJ.GOE-WOTBGJ.GOE-WOTBMJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GOE-WOTBFJ.GOE-WOTBGJ.GOE-WOTBDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GOE-WOTBFJ.GOE-WOTBHJ.GOE-WOTBMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GOE-WOTBFJ.GOE-WOTBHJ.GOE-WOTBDJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:") + (("0")  + ";opacity:" + ("0")  + ";}.GOE-WOTBFJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GOE-WOTBFJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GOE-WOTBFJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1") ) + (";}.GOE-WOTBFJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GOE-WOTBNJ{-webkit-transition-property:" + ("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GOE-WOTBNJ.GOE-WOTBGJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("1")  + ";}.GOE-WOTBNJ.GOE-WOTBGJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";}.GOE-WOTBNJ.GOE-WOTBHJ.GOE-WOTBMJ{-webkit-transform:") + (("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GOE-WOTBNJ.GOE-WOTBHJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GOE-WOTBNJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GOE-WOTBNJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GOE-WOTBNJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";}.GOE-WOTBNJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";}.GOE-WOTBIJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity") ) + (";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GOE-WOTBIJ.GOE-WOTBGJ.GOE-WOTBMJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GOE-WOTBIJ.GOE-WOTBGJ.GOE-WOTBDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GOE-WOTBIJ.GOE-WOTBHJ.GOE-WOTBMJ{opacity:" + ("1")  + ";-webkit-transform:" + ("scale(" + "1" + ")")  + ";z-index:") + (("0")  + ";}.GOE-WOTBIJ.GOE-WOTBHJ.GOE-WOTBDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GOE-WOTBIJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("1")  + ";}.GOE-WOTBIJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GOE-WOTBIJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("scale(" + "0.3" + ")") ) + (";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GOE-WOTBIJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GOE-WOTBEJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GOE-WOTBEJ.GOE-WOTBGJ.GOE-WOTBMJ{opacity:" + ("0")  + ";}.GOE-WOTBEJ.GOE-WOTBGJ.GOE-WOTBDJ,.GOE-WOTBEJ.GOE-WOTBHJ.GOE-WOTBMJ{opacity:" + ("1")  + ";}.GOE-WOTBEJ.GOE-WOTBHJ.GOE-WOTBDJ{opacity:") + (("0")  + ";}.GOE-WOTBEJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{opacity:" + ("1")  + ";}.GOE-WOTBEJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ,.GOE-WOTBEJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{opacity:" + ("0")  + ";}.GOE-WOTBEJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{opacity:" + ("1")  + ";}")) : ((".GOE-WOTBAJ{position:" + ("absolute")  + ";top:" + ("0")  + ";left:" + ("0")  + ";right:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}.GOE-WOTBBJ{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GOE-WOTBKJ,.GOE-WOTBLJ{-webkit-transition-property:") + (("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GOE-WOTBKJ.GOE-WOTBGJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBGJ.GOE-WOTBDJ,.GOE-WOTBKJ.GOE-WOTBHJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBHJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GOE-WOTBKJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBGJ.GOE-WOTBMJ{-webkit-transform:" + ("translateY(" + "100%" + ")") ) + (";}.GOE-WOTBLJ.GOE-WOTBGJ.GOE-WOTBDJ,.GOE-WOTBLJ.GOE-WOTBHJ.GOE-WOTBMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBHJ.GOE-WOTBDJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateY(" + "100%" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GOE-WOTBLJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GOE-WOTBCJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GOE-WOTBCJ.GOE-WOTBGJ.GOE-WOTBMJ{opacity:" + ("0")  + ";}.GOE-WOTBCJ.GOE-WOTBGJ.GOE-WOTBDJ,.GOE-WOTBCJ.GOE-WOTBHJ.GOE-WOTBMJ{opacity:") + (("1")  + ";}.GOE-WOTBCJ.GOE-WOTBHJ.GOE-WOTBDJ{opacity:" + ("0")  + ";}.GOE-WOTBCJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{opacity:" + ("1")  + ";}.GOE-WOTBCJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ,.GOE-WOTBCJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{opacity:" + ("0")  + ";}.GOE-WOTBCJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{opacity:" + ("1")  + ";}.GOE-WOTBFJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out"+ ","+ " " +"ease-in-out")  + ";-webkit-perspective:" + ("800")  + ";-webkit-transform-style:" + ("preserve-3d")  + ";-webkit-backface-visibility:" + ("hidden") ) + (";}.GOE-WOTBFJ.GOE-WOTBGJ.GOE-WOTBMJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GOE-WOTBFJ.GOE-WOTBGJ.GOE-WOTBDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GOE-WOTBFJ.GOE-WOTBHJ.GOE-WOTBMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GOE-WOTBFJ.GOE-WOTBHJ.GOE-WOTBDJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:") + (("0")  + ";opacity:" + ("0")  + ";}.GOE-WOTBFJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GOE-WOTBFJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GOE-WOTBFJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1") ) + (";}.GOE-WOTBFJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GOE-WOTBNJ{-webkit-transition-property:" + ("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GOE-WOTBNJ.GOE-WOTBGJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("1")  + ";}.GOE-WOTBNJ.GOE-WOTBGJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";}.GOE-WOTBNJ.GOE-WOTBHJ.GOE-WOTBMJ{-webkit-transform:") + (("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GOE-WOTBNJ.GOE-WOTBHJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GOE-WOTBNJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GOE-WOTBNJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GOE-WOTBNJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";}.GOE-WOTBNJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";}.GOE-WOTBIJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity") ) + (";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GOE-WOTBIJ.GOE-WOTBGJ.GOE-WOTBMJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GOE-WOTBIJ.GOE-WOTBGJ.GOE-WOTBDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GOE-WOTBIJ.GOE-WOTBHJ.GOE-WOTBMJ{opacity:" + ("1")  + ";-webkit-transform:" + ("scale(" + "1" + ")")  + ";z-index:") + (("0")  + ";}.GOE-WOTBIJ.GOE-WOTBHJ.GOE-WOTBDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GOE-WOTBIJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("1")  + ";}.GOE-WOTBIJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GOE-WOTBIJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{-webkit-transform:" + ("scale(" + "0.3" + ")") ) + (";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GOE-WOTBIJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GOE-WOTBEJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GOE-WOTBEJ.GOE-WOTBGJ.GOE-WOTBMJ{opacity:" + ("0")  + ";}.GOE-WOTBEJ.GOE-WOTBGJ.GOE-WOTBDJ,.GOE-WOTBEJ.GOE-WOTBHJ.GOE-WOTBMJ{opacity:" + ("1")  + ";}.GOE-WOTBEJ.GOE-WOTBHJ.GOE-WOTBDJ{opacity:") + (("0")  + ";}.GOE-WOTBEJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBMJ{opacity:" + ("1")  + ";}.GOE-WOTBEJ.GOE-WOTBHJ.GOE-WOTBJJ.GOE-WOTBDJ,.GOE-WOTBEJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBMJ{opacity:" + ("0")  + ";}.GOE-WOTBEJ.GOE-WOTBGJ.GOE-WOTBJJ.GOE-WOTBDJ{opacity:" + ("1")  + ";}"));
      }
      public java.lang.String display(){
        return "GOE-WOTBAJ";
      }
      public java.lang.String displayContainer(){
        return "GOE-WOTBBJ";
      }
      public java.lang.String dissolve(){
        return "GOE-WOTBCJ";
      }
      public java.lang.String end(){
        return "GOE-WOTBDJ";
      }
      public java.lang.String fade(){
        return "GOE-WOTBEJ";
      }
      public java.lang.String flip(){
        return "GOE-WOTBFJ";
      }
      public java.lang.String in(){
        return "GOE-WOTBGJ";
      }
      public java.lang.String out(){
        return "GOE-WOTBHJ";
      }
      public java.lang.String pop(){
        return "GOE-WOTBIJ";
      }
      public java.lang.String reverse(){
        return "GOE-WOTBJJ";
      }
      public java.lang.String slide(){
        return "GOE-WOTBKJ";
      }
      public java.lang.String slideup(){
        return "GOE-WOTBLJ";
      }
      public java.lang.String start(){
        return "GOE-WOTBMJ";
      }
      public java.lang.String swap(){
        return "GOE-WOTBNJ";
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
