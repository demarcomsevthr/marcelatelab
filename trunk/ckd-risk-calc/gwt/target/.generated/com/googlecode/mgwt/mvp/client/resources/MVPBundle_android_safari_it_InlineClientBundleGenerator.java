package com.googlecode.mgwt.mvp.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class MVPBundle_android_safari_it_InlineClientBundleGenerator implements com.googlecode.mgwt.mvp.client.resources.MVPBundle {
  private static MVPBundle_android_safari_it_InlineClientBundleGenerator _instance0 = new MVPBundle_android_safari_it_InlineClientBundleGenerator();
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
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GO3PFYUFI{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GO3PFYUEI{position:" + ("absolute")  + ";top:" + ("0")  + ";right:" + ("0")  + ";left:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}@-webkit-keyframes donothing {\n    from { opacity: 0.9; }\n    to { opacity: 1; }\n}.GO3PFYUJI{-webkit-animation-timing-function:") + (("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("10")  + ";}.GO3PFYUKI{-webkit-animation-timing-function:" + ("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("0")  + " !important;}.GO3PFYUNI.GO3PFYUJI{-webkit-animation-name:" + ("slideinfromright")  + ";}.GO3PFYUNI.GO3PFYUKI{-webkit-animation-name:" + ("slideouttoleft")  + ";}.GO3PFYUNI.GO3PFYUJI.GO3PFYUMI{-webkit-animation-name:" + ("slideinfromleft") ) + (";}.GO3PFYUNI.GO3PFYUKI.GO3PFYUMI{-webkit-animation-name:" + ("slideouttoright")  + ";}@-webkit-keyframes slideinfromright {\n    from { -webkit-transform: translateX(100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideinfromleft {\n    from { -webkit-transform: translateX(-100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideouttoleft {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(-100%); }\n}@-webkit-keyframes slideouttoright {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(100%); }\n}.GO3PFYUII{-webkit-animation-duration:" + ("0.65s")  + ";-webkit-backface-visibility:" + ("hidden")  + ";}.GO3PFYUII.GO3PFYUJI{-webkit-animation-name:" + ("flipinfromleft")  + ";}.GO3PFYUII.GO3PFYUKI{-webkit-animation-name:" + ("flipouttoleft")  + ";}.GO3PFYUII.GO3PFYUJI.GO3PFYUMI{-webkit-animation-name:" + ("flipinfromright")  + ";}.GO3PFYUII.GO3PFYUKI.GO3PFYUMI{-webkit-animation-name:" + ("flipouttoright")  + ";}@-webkit-keyframes flipinfromright {\n    from { -webkit-transform: rotateY(-180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipinfromleft {\n    from { -webkit-transform: rotateY(180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipouttoleft {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(-180deg) scale(.8); }\n}@-webkit-keyframes flipouttoright {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(180deg) scale(.8); }\n}@-webkit-keyframes fadein {\n    from { opacity: 0; }\n    to { opacity: 1; }\n}@-webkit-keyframes fadeout {\n    from { opacity: 1; }\n    to { opacity: 0; }\n}.GO3PFYUHI.GO3PFYUJI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("fadein")  + ";}.GO3PFYUHI.GO3PFYUKI{z-index:" + ("8")  + ";-webkit-animation-name:") + (("fadeout")  + ";}.GO3PFYUGI.GO3PFYUJI{-webkit-animation-name:" + ("fadein")  + ";}.GO3PFYUGI.GO3PFYUKI{-webkit-animation-name:" + ("fadeout")  + ";}.GO3PFYUOI.GO3PFYUJI{-webkit-animation-name:" + ("slideupfrombottom")  + ";z-index:" + ("10")  + ";}.GO3PFYUOI.GO3PFYUKI{-webkit-animation-name:" + ("slideupfrommiddle")  + ";z-index:" + ("0")  + ";}.GO3PFYUOI.GO3PFYUKI.GO3PFYUMI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("slidedownfrommiddle")  + ";}.GO3PFYUOI.GO3PFYUJI.GO3PFYUMI{z-index:" + ("0")  + ";-webkit-animation-name:" + ("slidedownfromtop") ) + (";}@-webkit-keyframes slideupfrombottom {\n    from { -webkit-transform: translateY(100%); }\n    to { -webkit-transform: translateY(0); }\n}@-webkit-keyframes slidedownfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(100%); }\n}@-webkit-keyframes slideupfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(-100%); }\n}@-webkit-keyframes slidedownfromtop {\n    from { -webkit-transform: translateY(-100%); }\n    to { -webkit-transform: translateY(0%); }\n}.GO3PFYUPI{-webkit-transform:" + ("perspective(" + "800" + ")")  + ";-webkit-animation-duration:" + ("0.7s")  + ";}.GO3PFYUPI.GO3PFYUKI{-webkit-animation-name:" + ("swapouttoleft")  + ";}.GO3PFYUPI.GO3PFYUJI{-webkit-animation-name:" + ("swapinfromright")  + ";}.GO3PFYUPI.GO3PFYUKI.GO3PFYUMI{-webkit-animation-name:" + ("swapouttoright")  + ";}.GO3PFYUPI.GO3PFYUJI.GO3PFYUMI{-webkit-animation-name:" + ("swapinfromleft")  + ";}@-webkit-keyframes swapouttoright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n    \n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n       \n    }\n    100% {\n        -webkit-transform:  translate3d(0px, 0px, -800px) rotateY(70deg);\n         opacity: 0;\n    }\n}@-webkit-keyframes swapouttoleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n        -webkit-transform:  translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        opacity: 0;\n    }\n}@-webkit-keyframes swapinfromright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}@-webkit-keyframes swapinfromleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}.GO3PFYULI.GO3PFYUJI{-webkit-animation-name:" + ("popin")  + ";}.GO3PFYULI.GO3PFYUKI{-webkit-animation-name:" + ("popout")  + ";}@-webkit-keyframes popin {\n    from {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n    to {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n}@-webkit-keyframes popout {\n    from {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n    to {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n}")) : ((".GO3PFYUFI{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GO3PFYUEI{position:" + ("absolute")  + ";top:" + ("0")  + ";left:" + ("0")  + ";right:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}@-webkit-keyframes donothing {\n    from { opacity: 0.9; }\n    to { opacity: 1; }\n}.GO3PFYUJI{-webkit-animation-timing-function:") + (("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("10")  + ";}.GO3PFYUKI{-webkit-animation-timing-function:" + ("ease-in-out")  + ";-webkit-animation-duration:" + ("300ms")  + ";-webkit-animation-fill-mode:" + ("both")  + ";z-index:" + ("0")  + " !important;}.GO3PFYUNI.GO3PFYUJI{-webkit-animation-name:" + ("slideinfromright")  + ";}.GO3PFYUNI.GO3PFYUKI{-webkit-animation-name:" + ("slideouttoleft")  + ";}.GO3PFYUNI.GO3PFYUJI.GO3PFYUMI{-webkit-animation-name:" + ("slideinfromleft") ) + (";}.GO3PFYUNI.GO3PFYUKI.GO3PFYUMI{-webkit-animation-name:" + ("slideouttoright")  + ";}@-webkit-keyframes slideinfromright {\n    from { -webkit-transform: translateX(100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideinfromleft {\n    from { -webkit-transform: translateX(-100%); }\n    to { -webkit-transform: translateX(0); }\n}@-webkit-keyframes slideouttoleft {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(-100%); }\n}@-webkit-keyframes slideouttoright {\n    from { -webkit-transform: translateX(0); }\n    to { -webkit-transform: translateX(100%); }\n}.GO3PFYUII{-webkit-animation-duration:" + ("0.65s")  + ";-webkit-backface-visibility:" + ("hidden")  + ";}.GO3PFYUII.GO3PFYUJI{-webkit-animation-name:" + ("flipinfromleft")  + ";}.GO3PFYUII.GO3PFYUKI{-webkit-animation-name:" + ("flipouttoleft")  + ";}.GO3PFYUII.GO3PFYUJI.GO3PFYUMI{-webkit-animation-name:" + ("flipinfromright")  + ";}.GO3PFYUII.GO3PFYUKI.GO3PFYUMI{-webkit-animation-name:" + ("flipouttoright")  + ";}@-webkit-keyframes flipinfromright {\n    from { -webkit-transform: rotateY(-180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipinfromleft {\n    from { -webkit-transform: rotateY(180deg) scale(.8); }\n    to { -webkit-transform: rotateY(0) scale(1); }\n}@-webkit-keyframes flipouttoleft {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(-180deg) scale(.8); }\n}@-webkit-keyframes flipouttoright {\n    from { -webkit-transform: rotateY(0) scale(1); }\n    to { -webkit-transform: rotateY(180deg) scale(.8); }\n}@-webkit-keyframes fadein {\n    from { opacity: 0; }\n    to { opacity: 1; }\n}@-webkit-keyframes fadeout {\n    from { opacity: 1; }\n    to { opacity: 0; }\n}.GO3PFYUHI.GO3PFYUJI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("fadein")  + ";}.GO3PFYUHI.GO3PFYUKI{z-index:" + ("8")  + ";-webkit-animation-name:") + (("fadeout")  + ";}.GO3PFYUGI.GO3PFYUJI{-webkit-animation-name:" + ("fadein")  + ";}.GO3PFYUGI.GO3PFYUKI{-webkit-animation-name:" + ("fadeout")  + ";}.GO3PFYUOI.GO3PFYUJI{-webkit-animation-name:" + ("slideupfrombottom")  + ";z-index:" + ("10")  + ";}.GO3PFYUOI.GO3PFYUKI{-webkit-animation-name:" + ("slideupfrommiddle")  + ";z-index:" + ("0")  + ";}.GO3PFYUOI.GO3PFYUKI.GO3PFYUMI{z-index:" + ("10")  + ";-webkit-animation-name:" + ("slidedownfrommiddle")  + ";}.GO3PFYUOI.GO3PFYUJI.GO3PFYUMI{z-index:" + ("0")  + ";-webkit-animation-name:" + ("slidedownfromtop") ) + (";}@-webkit-keyframes slideupfrombottom {\n    from { -webkit-transform: translateY(100%); }\n    to { -webkit-transform: translateY(0); }\n}@-webkit-keyframes slidedownfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(100%); }\n}@-webkit-keyframes slideupfrommiddle {\n    from { -webkit-transform: translateY(0); }\n    to { -webkit-transform: translateY(-100%); }\n}@-webkit-keyframes slidedownfromtop {\n    from { -webkit-transform: translateY(-100%); }\n    to { -webkit-transform: translateY(0%); }\n}.GO3PFYUPI{-webkit-transform:" + ("perspective(" + "800" + ")")  + ";-webkit-animation-duration:" + ("0.7s")  + ";}.GO3PFYUPI.GO3PFYUKI{-webkit-animation-name:" + ("swapouttoleft")  + ";}.GO3PFYUPI.GO3PFYUJI{-webkit-animation-name:" + ("swapinfromright")  + ";}.GO3PFYUPI.GO3PFYUKI.GO3PFYUMI{-webkit-animation-name:" + ("swapouttoright")  + ";}.GO3PFYUPI.GO3PFYUJI.GO3PFYUMI{-webkit-animation-name:" + ("swapinfromleft")  + ";}@-webkit-keyframes swapouttoright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n    \n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n       \n    }\n    100% {\n        -webkit-transform:  translate3d(0px, 0px, -800px) rotateY(70deg);\n         opacity: 0;\n    }\n}@-webkit-keyframes swapouttoleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    50% {\n        -webkit-transform:  translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in;\n        opacity: 0.8;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        opacity: 0;\n    }\n}@-webkit-keyframes swapinfromright {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(-180px, 0px, -400px) rotateY(20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}@-webkit-keyframes swapinfromleft {\n    0% {\n        -webkit-transform: translate3d(0px, 0px, -800px) rotateY(-70deg);\n        -webkit-animation-timing-function: ease-out;\n    }\n    50% {\n        -webkit-transform: translate3d(180px, 0px, -400px) rotateY(-20deg);\n        -webkit-animation-timing-function: ease-in-out;\n    }\n    100% {\n        -webkit-transform: translate3d(0px, 0px, 0px) rotateY(0deg);\n    }\n}.GO3PFYULI.GO3PFYUJI{-webkit-animation-name:" + ("popin")  + ";}.GO3PFYULI.GO3PFYUKI{-webkit-animation-name:" + ("popout")  + ";}@-webkit-keyframes popin {\n    from {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n    to {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n}@-webkit-keyframes popout {\n    from {\n        -webkit-transform: scale(1);\n        opacity: 1;\n    }\n    to {\n        -webkit-transform: scale(.3);\n        opacity: 0;\n    }\n}"));
      }
      public java.lang.String display(){
        return "GO3PFYUEI";
      }
      public java.lang.String displayContainer(){
        return "GO3PFYUFI";
      }
      public java.lang.String dissolve(){
        return "GO3PFYUGI";
      }
      public java.lang.String fade(){
        return "GO3PFYUHI";
      }
      public java.lang.String flip(){
        return "GO3PFYUII";
      }
      public java.lang.String in(){
        return "GO3PFYUJI";
      }
      public java.lang.String out(){
        return "GO3PFYUKI";
      }
      public java.lang.String pop(){
        return "GO3PFYULI";
      }
      public java.lang.String reverse(){
        return "GO3PFYUMI";
      }
      public java.lang.String slide(){
        return "GO3PFYUNI";
      }
      public java.lang.String slideup(){
        return "GO3PFYUOI";
      }
      public java.lang.String swap(){
        return "GO3PFYUPI";
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
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GO3PFYUAJ{position:" + ("absolute")  + ";top:" + ("0")  + ";right:" + ("0")  + ";left:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}.GO3PFYUBJ{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GO3PFYUKJ,.GO3PFYULJ{-webkit-transition-property:") + (("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GO3PFYUKJ.GO3PFYUGJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GO3PFYUKJ.GO3PFYUGJ.GO3PFYUDJ,.GO3PFYUKJ.GO3PFYUHJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GO3PFYUKJ.GO3PFYUHJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GO3PFYUKJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GO3PFYUKJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GO3PFYUKJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GO3PFYUKJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GO3PFYULJ.GO3PFYUGJ.GO3PFYUMJ{-webkit-transform:" + ("translateY(" + "100%" + ")") ) + (";}.GO3PFYULJ.GO3PFYUGJ.GO3PFYUDJ,.GO3PFYULJ.GO3PFYUHJ.GO3PFYUMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GO3PFYULJ.GO3PFYUHJ.GO3PFYUDJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GO3PFYULJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GO3PFYULJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateY(" + "100%" + ")")  + ";}.GO3PFYULJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GO3PFYULJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GO3PFYUCJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GO3PFYUCJ.GO3PFYUGJ.GO3PFYUMJ{opacity:" + ("0")  + ";}.GO3PFYUCJ.GO3PFYUGJ.GO3PFYUDJ,.GO3PFYUCJ.GO3PFYUHJ.GO3PFYUMJ{opacity:") + (("1")  + ";}.GO3PFYUCJ.GO3PFYUHJ.GO3PFYUDJ{opacity:" + ("0")  + ";}.GO3PFYUCJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{opacity:" + ("1")  + ";}.GO3PFYUCJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ,.GO3PFYUCJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{opacity:" + ("0")  + ";}.GO3PFYUCJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{opacity:" + ("1")  + ";}.GO3PFYUFJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out"+ ","+ " " +"ease-in-out")  + ";-webkit-perspective:" + ("800")  + ";-webkit-transform-style:" + ("preserve-3d")  + ";-webkit-backface-visibility:" + ("hidden") ) + (";}.GO3PFYUFJ.GO3PFYUGJ.GO3PFYUMJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GO3PFYUFJ.GO3PFYUGJ.GO3PFYUDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GO3PFYUFJ.GO3PFYUHJ.GO3PFYUMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GO3PFYUFJ.GO3PFYUHJ.GO3PFYUDJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:") + (("0")  + ";opacity:" + ("0")  + ";}.GO3PFYUFJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GO3PFYUFJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GO3PFYUFJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1") ) + (";}.GO3PFYUFJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GO3PFYUNJ{-webkit-transition-property:" + ("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GO3PFYUNJ.GO3PFYUGJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("1")  + ";}.GO3PFYUNJ.GO3PFYUGJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";}.GO3PFYUNJ.GO3PFYUHJ.GO3PFYUMJ{-webkit-transform:") + (("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GO3PFYUNJ.GO3PFYUHJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GO3PFYUNJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GO3PFYUNJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GO3PFYUNJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";}.GO3PFYUNJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";}.GO3PFYUIJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity") ) + (";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GO3PFYUIJ.GO3PFYUGJ.GO3PFYUMJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GO3PFYUIJ.GO3PFYUGJ.GO3PFYUDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GO3PFYUIJ.GO3PFYUHJ.GO3PFYUMJ{opacity:" + ("1")  + ";-webkit-transform:" + ("scale(" + "1" + ")")  + ";z-index:") + (("0")  + ";}.GO3PFYUIJ.GO3PFYUHJ.GO3PFYUDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GO3PFYUIJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("1")  + ";}.GO3PFYUIJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GO3PFYUIJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("scale(" + "0.3" + ")") ) + (";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GO3PFYUIJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GO3PFYUEJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GO3PFYUEJ.GO3PFYUGJ.GO3PFYUMJ{opacity:" + ("0")  + ";}.GO3PFYUEJ.GO3PFYUGJ.GO3PFYUDJ,.GO3PFYUEJ.GO3PFYUHJ.GO3PFYUMJ{opacity:" + ("1")  + ";}.GO3PFYUEJ.GO3PFYUHJ.GO3PFYUDJ{opacity:") + (("0")  + ";}.GO3PFYUEJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{opacity:" + ("1")  + ";}.GO3PFYUEJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ,.GO3PFYUEJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{opacity:" + ("0")  + ";}.GO3PFYUEJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{opacity:" + ("1")  + ";}")) : ((".GO3PFYUAJ{position:" + ("absolute")  + ";top:" + ("0")  + ";left:" + ("0")  + ";right:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}.GO3PFYUBJ{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GO3PFYUKJ,.GO3PFYULJ{-webkit-transition-property:") + (("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GO3PFYUKJ.GO3PFYUGJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GO3PFYUKJ.GO3PFYUGJ.GO3PFYUDJ,.GO3PFYUKJ.GO3PFYUHJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GO3PFYUKJ.GO3PFYUHJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GO3PFYUKJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GO3PFYUKJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "100%" + ")")  + ";}.GO3PFYUKJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")")  + ";}.GO3PFYUKJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "0" + ")")  + ";}.GO3PFYULJ.GO3PFYUGJ.GO3PFYUMJ{-webkit-transform:" + ("translateY(" + "100%" + ")") ) + (";}.GO3PFYULJ.GO3PFYUGJ.GO3PFYUDJ,.GO3PFYULJ.GO3PFYUHJ.GO3PFYUMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GO3PFYULJ.GO3PFYUHJ.GO3PFYUDJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GO3PFYULJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GO3PFYULJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateY(" + "100%" + ")")  + ";}.GO3PFYULJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateY(" + "-100%" + ")")  + ";}.GO3PFYULJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateY(" + "0" + ")")  + ";}.GO3PFYUCJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GO3PFYUCJ.GO3PFYUGJ.GO3PFYUMJ{opacity:" + ("0")  + ";}.GO3PFYUCJ.GO3PFYUGJ.GO3PFYUDJ,.GO3PFYUCJ.GO3PFYUHJ.GO3PFYUMJ{opacity:") + (("1")  + ";}.GO3PFYUCJ.GO3PFYUHJ.GO3PFYUDJ{opacity:" + ("0")  + ";}.GO3PFYUCJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{opacity:" + ("1")  + ";}.GO3PFYUCJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ,.GO3PFYUCJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{opacity:" + ("0")  + ";}.GO3PFYUCJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{opacity:" + ("1")  + ";}.GO3PFYUFJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out"+ ","+ " " +"ease-in-out")  + ";-webkit-perspective:" + ("800")  + ";-webkit-transform-style:" + ("preserve-3d")  + ";-webkit-backface-visibility:" + ("hidden") ) + (";}.GO3PFYUFJ.GO3PFYUGJ.GO3PFYUMJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GO3PFYUFJ.GO3PFYUGJ.GO3PFYUDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GO3PFYUFJ.GO3PFYUHJ.GO3PFYUMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GO3PFYUFJ.GO3PFYUHJ.GO3PFYUDJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:") + (("0")  + ";opacity:" + ("0")  + ";}.GO3PFYUFJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GO3PFYUFJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("rotateY(" + "180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("0")  + ";opacity:" + ("0")  + ";}.GO3PFYUFJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("rotateY(" + "-180deg" + ")"+ " " +"scale(" + "0.8" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1") ) + (";}.GO3PFYUFJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("rotateY(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";opacity:" + ("1")  + ";}.GO3PFYUNJ{-webkit-transition-property:" + ("-webkit-transform")  + ";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GO3PFYUNJ.GO3PFYUGJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("1")  + ";}.GO3PFYUNJ.GO3PFYUGJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("1")  + ";}.GO3PFYUNJ.GO3PFYUHJ.GO3PFYUMJ{-webkit-transform:") + (("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GO3PFYUNJ.GO3PFYUHJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GO3PFYUNJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";z-index:" + ("0")  + ";}.GO3PFYUNJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";z-index:" + ("0")  + ";}.GO3PFYUNJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("translateX(" + "-100%" + ")"+ " " +"scale(" + "0.3" + ")")  + ";}.GO3PFYUNJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("translateX(" + "0" + ")"+ " " +"scale(" + "1" + ")")  + ";}.GO3PFYUIJ{-webkit-transition-property:" + ("-webkit-transform"+ ","+ " " +"opacity") ) + (";-webkit-transition-duration:" + ("350ms"+ ","+ " " +"350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GO3PFYUIJ.GO3PFYUGJ.GO3PFYUMJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GO3PFYUIJ.GO3PFYUGJ.GO3PFYUDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GO3PFYUIJ.GO3PFYUHJ.GO3PFYUMJ{opacity:" + ("1")  + ";-webkit-transform:" + ("scale(" + "1" + ")")  + ";z-index:") + (("0")  + ";}.GO3PFYUIJ.GO3PFYUHJ.GO3PFYUDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GO3PFYUIJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("1")  + ";}.GO3PFYUIJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("scale(" + "0.3" + ")")  + ";opacity:" + ("0")  + ";z-index:" + ("1")  + ";}.GO3PFYUIJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{-webkit-transform:" + ("scale(" + "0.3" + ")") ) + (";opacity:" + ("0")  + ";z-index:" + ("0")  + ";}.GO3PFYUIJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{-webkit-transform:" + ("scale(" + "1" + ")")  + ";opacity:" + ("1")  + ";z-index:" + ("0")  + ";}.GO3PFYUEJ{-webkit-transition-property:" + ("opacity")  + ";-webkit-transition-duration:" + ("350ms")  + ";-webkit-transition-timing-function:" + ("ease-in-out")  + ";}.GO3PFYUEJ.GO3PFYUGJ.GO3PFYUMJ{opacity:" + ("0")  + ";}.GO3PFYUEJ.GO3PFYUGJ.GO3PFYUDJ,.GO3PFYUEJ.GO3PFYUHJ.GO3PFYUMJ{opacity:" + ("1")  + ";}.GO3PFYUEJ.GO3PFYUHJ.GO3PFYUDJ{opacity:") + (("0")  + ";}.GO3PFYUEJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUMJ{opacity:" + ("1")  + ";}.GO3PFYUEJ.GO3PFYUHJ.GO3PFYUJJ.GO3PFYUDJ,.GO3PFYUEJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUMJ{opacity:" + ("0")  + ";}.GO3PFYUEJ.GO3PFYUGJ.GO3PFYUJJ.GO3PFYUDJ{opacity:" + ("1")  + ";}"));
      }
      public java.lang.String display(){
        return "GO3PFYUAJ";
      }
      public java.lang.String displayContainer(){
        return "GO3PFYUBJ";
      }
      public java.lang.String dissolve(){
        return "GO3PFYUCJ";
      }
      public java.lang.String end(){
        return "GO3PFYUDJ";
      }
      public java.lang.String fade(){
        return "GO3PFYUEJ";
      }
      public java.lang.String flip(){
        return "GO3PFYUFJ";
      }
      public java.lang.String in(){
        return "GO3PFYUGJ";
      }
      public java.lang.String out(){
        return "GO3PFYUHJ";
      }
      public java.lang.String pop(){
        return "GO3PFYUIJ";
      }
      public java.lang.String reverse(){
        return "GO3PFYUJJ";
      }
      public java.lang.String slide(){
        return "GO3PFYUKJ";
      }
      public java.lang.String slideup(){
        return "GO3PFYULJ";
      }
      public java.lang.String start(){
        return "GO3PFYUMJ";
      }
      public java.lang.String swap(){
        return "GO3PFYUNJ";
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
