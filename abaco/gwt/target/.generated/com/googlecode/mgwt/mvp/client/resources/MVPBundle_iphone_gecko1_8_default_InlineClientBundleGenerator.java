package com.googlecode.mgwt.mvp.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class MVPBundle_iphone_gecko1_8_default_InlineClientBundleGenerator implements com.googlecode.mgwt.mvp.client.resources.MVPBundle {
  private static MVPBundle_iphone_gecko1_8_default_InlineClientBundleGenerator _instance0 = new MVPBundle_iphone_gecko1_8_default_InlineClientBundleGenerator();
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
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GOE-WOTBFI{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GOE-WOTBEI{position:" + ("absolute")  + ";top:" + ("0")  + ";right:" + ("0")  + ";left:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}.GOE-WOTBJI{-moz-animation-timing-function:") + (("ease")  + ";-moz-animation-duration:" + ("300ms")  + ";-moz-animation-fill-mode:" + ("both")  + ";z-index:" + ("10")  + ";}.GOE-WOTBKI{-moz-animation-timing-function:" + ("ease")  + ";-moz-animation-duration:" + ("300ms")  + ";-moz-animation-fill-mode:" + ("both")  + ";z-index:" + ("0")  + " !important;}.GOE-WOTBNI.GOE-WOTBJI{-moz-animation-name:" + ("slideinfromright")  + ";}.GOE-WOTBNI.GOE-WOTBKI{-moz-animation-name:" + ("slideouttoleft")  + ";}.GOE-WOTBNI.GOE-WOTBJI.GOE-WOTBMI{-moz-animation-name:" + ("slideinfromleft") ) + (";}.GOE-WOTBNI.GOE-WOTBKI.GOE-WOTBMI{-moz-animation-name:" + ("slideouttoright")  + ";}@-moz-keyframes slideinfromright {\n    from { -moz-transform: translateX(100%); }\n    to { -moz-transform: translateX(0); }\n}@-moz-keyframes slideinfromleft {\n    from { -moz-transform: translateX(-100%); }\n    to { -moz-transform: translateX(0); }\n}@-moz-keyframes slideouttoleft {\n    from { -moz-transform: translateX(0); }\n    to { -moz-transform: translateX(-100%); }\n}@-moz-keyframes slideouttoright {\n    from { -moz-transform: translateX(0); }\n    to { -moz-transform: translateX(100%); }\n}.GOE-WOTBII{-moz-animation-duration:" + ("0.65s")  + ";-moz-backface-visibility:" + ("hidden")  + ";}.GOE-WOTBII.GOE-WOTBJI{-moz-animation-name:" + ("flipinfromleft")  + ";}.GOE-WOTBII.GOE-WOTBKI{-moz-animation-name:" + ("flipouttoleft")  + ";}.GOE-WOTBII.GOE-WOTBJI.GOE-WOTBMI{-moz-animation-name:" + ("flipinfromright")  + ";}.GOE-WOTBII.GOE-WOTBKI.GOE-WOTBMI{-moz-animation-name:" + ("flipouttoright")  + ";}@-moz-keyframes flipinfromright {\n    from { -moz-transform: rotate(-180deg) scale(.8); opacity: 0;}\n    to { -moz-transform: rotate(0) scale(1); opacity: 1;}\n}@-moz-keyframes flipinfromleft {\n    from { -moz-transform: rotate(180deg) scale(.8); opacity: 0; }\n    to { -moz-transform: rotate(0) scale(1);  opacity: 1;}\n}@-moz-keyframes flipouttoleft {\n    from { -moz-transform: rotate(0) scale(1);\n    	opacity: 1;\n     }\n    to { -moz-transform: rotate(-180deg) scale(.8); \n    	opacity: 0;\n    }\n}@-moz-keyframes flipouttoright {\n    from { -moz-transform: rotate(0) scale(1); opacity: 1;}\n    to { -moz-transform: rotate(180deg) scale(.8); opacity: 0;}\n}@-moz-keyframes fadein {\n    from { opacity: 0; }\n    to { opacity: 1; }\n}@-moz-keyframes fadeout {\n    from { opacity: 1; }\n    to { opacity: 0; }\n}.GOE-WOTBHI.GOE-WOTBJI{z-index:" + ("10")  + ";-moz-animation-name:" + ("fadein")  + ";}.GOE-WOTBHI.GOE-WOTBKI{z-index:" + ("8")  + ";-moz-animation-name:") + (("fadeout")  + ";}.GOE-WOTBGI.GOE-WOTBJI{-moz-animation-name:" + ("fadein")  + ";}.GOE-WOTBGI.GOE-WOTBKI{-moz-animation-name:" + ("fadeout")  + ";}.GOE-WOTBOI.GOE-WOTBJI{-moz-animation-name:" + ("slideupfrombottom")  + ";z-index:" + ("10")  + ";}.GOE-WOTBOI.GOE-WOTBKI{-moz-animation-name:" + ("slideupfrommiddle")  + ";z-index:" + ("0")  + ";}.GOE-WOTBOI.GOE-WOTBKI.GOE-WOTBMI{z-index:" + ("10")  + ";-moz-animation-name:" + ("slidedownfrommiddle")  + ";}.GOE-WOTBOI.GOE-WOTBJI.GOE-WOTBMI{z-index:" + ("0")  + ";-moz-animation-name:" + ("slidedownfromtop") ) + (";}@-moz-keyframes slideupfrombottom {\n    from { -moz-transform: translateY(100%); }\n    to { -moz-transform: translateY(0); }\n}@-moz-keyframes slidedownfrommiddle {\n    from { -moz-transform: translateY(0); }\n    to { -moz-transform: translateY(100%); }\n}@-moz-keyframes slideupfrommiddle {\n    from { -moz-transform: translateY(0); }\n    to { -moz-transform: translateY(-100%); }\n}@-moz-keyframes slidedownfromtop {\n    from { -moz-transform: translateY(-100%); }\n    to { -moz-transform: translateY(0%); }\n}.GOE-WOTBPI{-moz-transform:" + ("perspective(" + "800" + ")")  + ";-moz-animation-duration:" + ("0.7s")  + ";}.GOE-WOTBPI.GOE-WOTBKI{-moz-animation-name:" + ("swapouttoleft")  + ";}.GOE-WOTBPI.GOE-WOTBJI{-moz-animation-name:" + ("swapinfromright")  + ";}.GOE-WOTBPI.GOE-WOTBKI.GOE-WOTBMI{-moz-animation-name:" + ("swapouttoright")  + ";}.GOE-WOTBPI.GOE-WOTBJI.GOE-WOTBMI{-moz-animation-name:" + ("swapinfromleft")  + ";}@-moz-keyframes swapouttoright {\n    0% {\n         \\-moz-transform: translateX(0%) scale(0.3);\n        \n    }\n   \n    100% {\n         \\-moz-transform: translateX(100%) scale(1);\n    }\n}@-moz-keyframes swapouttoleft {\n    0% {\n         \\-moz-transform: translateX(0%) scale(1);\n       \n    }\n   \n    100% {\n        \\-moz-transform: translateX(-100%) scale(0.3);\n    }\n}@-moz-keyframes swapinfromright {\n    0% {\n        \\-moz-transform: translateX(100%) scale(0.3);\n    }\n   \n    100% {\n        \\-moz-transform: translateX(0%) scale(1);\n    }\n}@-moz-keyframes swapinfromleft {\n    0% {\n        \\-moz-transform: translateX(-100%) scale(0.3);\n       \n    }\n   \n    100% {\n        \\-moz-transform: translateX(0%) scale(1);\n    }\n}.GOE-WOTBLI.GOE-WOTBJI{-moz-animation-name:" + ("popin")  + ";}.GOE-WOTBLI.GOE-WOTBKI{-moz-animation-name:" + ("popout")  + ";}@-moz-keyframes popin {\n    from {\n        -moz-transform: scale(.3);\n        opacity: 0;\n    }\n    to {\n        -moz-transform: scale(1);\n        opacity: 1;\n    }\n}@-moz-keyframes popout {\n    from {\n        -moz-transform: scale(1);\n        opacity: 1;\n    }\n    to {\n        -moz-transform: scale(.3);\n        opacity: 0;\n    }\n}")) : ((".GOE-WOTBFI{position:" + ("absolute")  + ";width:" + ("100%")  + ";height:" + ("100%")  + ";overflow:" + ("hidden")  + ";}.GOE-WOTBEI{position:" + ("absolute")  + ";top:" + ("0")  + ";left:" + ("0")  + ";right:" + ("0")  + ";bottom:" + ("0")  + ";overflow:" + ("hidden")  + ";}.GOE-WOTBJI{-moz-animation-timing-function:") + (("ease")  + ";-moz-animation-duration:" + ("300ms")  + ";-moz-animation-fill-mode:" + ("both")  + ";z-index:" + ("10")  + ";}.GOE-WOTBKI{-moz-animation-timing-function:" + ("ease")  + ";-moz-animation-duration:" + ("300ms")  + ";-moz-animation-fill-mode:" + ("both")  + ";z-index:" + ("0")  + " !important;}.GOE-WOTBNI.GOE-WOTBJI{-moz-animation-name:" + ("slideinfromright")  + ";}.GOE-WOTBNI.GOE-WOTBKI{-moz-animation-name:" + ("slideouttoleft")  + ";}.GOE-WOTBNI.GOE-WOTBJI.GOE-WOTBMI{-moz-animation-name:" + ("slideinfromleft") ) + (";}.GOE-WOTBNI.GOE-WOTBKI.GOE-WOTBMI{-moz-animation-name:" + ("slideouttoright")  + ";}@-moz-keyframes slideinfromright {\n    from { -moz-transform: translateX(100%); }\n    to { -moz-transform: translateX(0); }\n}@-moz-keyframes slideinfromleft {\n    from { -moz-transform: translateX(-100%); }\n    to { -moz-transform: translateX(0); }\n}@-moz-keyframes slideouttoleft {\n    from { -moz-transform: translateX(0); }\n    to { -moz-transform: translateX(-100%); }\n}@-moz-keyframes slideouttoright {\n    from { -moz-transform: translateX(0); }\n    to { -moz-transform: translateX(100%); }\n}.GOE-WOTBII{-moz-animation-duration:" + ("0.65s")  + ";-moz-backface-visibility:" + ("hidden")  + ";}.GOE-WOTBII.GOE-WOTBJI{-moz-animation-name:" + ("flipinfromleft")  + ";}.GOE-WOTBII.GOE-WOTBKI{-moz-animation-name:" + ("flipouttoleft")  + ";}.GOE-WOTBII.GOE-WOTBJI.GOE-WOTBMI{-moz-animation-name:" + ("flipinfromright")  + ";}.GOE-WOTBII.GOE-WOTBKI.GOE-WOTBMI{-moz-animation-name:" + ("flipouttoright")  + ";}@-moz-keyframes flipinfromright {\n    from { -moz-transform: rotate(-180deg) scale(.8); opacity: 0;}\n    to { -moz-transform: rotate(0) scale(1); opacity: 1;}\n}@-moz-keyframes flipinfromleft {\n    from { -moz-transform: rotate(180deg) scale(.8); opacity: 0; }\n    to { -moz-transform: rotate(0) scale(1);  opacity: 1;}\n}@-moz-keyframes flipouttoleft {\n    from { -moz-transform: rotate(0) scale(1);\n    	opacity: 1;\n     }\n    to { -moz-transform: rotate(-180deg) scale(.8); \n    	opacity: 0;\n    }\n}@-moz-keyframes flipouttoright {\n    from { -moz-transform: rotate(0) scale(1); opacity: 1;}\n    to { -moz-transform: rotate(180deg) scale(.8); opacity: 0;}\n}@-moz-keyframes fadein {\n    from { opacity: 0; }\n    to { opacity: 1; }\n}@-moz-keyframes fadeout {\n    from { opacity: 1; }\n    to { opacity: 0; }\n}.GOE-WOTBHI.GOE-WOTBJI{z-index:" + ("10")  + ";-moz-animation-name:" + ("fadein")  + ";}.GOE-WOTBHI.GOE-WOTBKI{z-index:" + ("8")  + ";-moz-animation-name:") + (("fadeout")  + ";}.GOE-WOTBGI.GOE-WOTBJI{-moz-animation-name:" + ("fadein")  + ";}.GOE-WOTBGI.GOE-WOTBKI{-moz-animation-name:" + ("fadeout")  + ";}.GOE-WOTBOI.GOE-WOTBJI{-moz-animation-name:" + ("slideupfrombottom")  + ";z-index:" + ("10")  + ";}.GOE-WOTBOI.GOE-WOTBKI{-moz-animation-name:" + ("slideupfrommiddle")  + ";z-index:" + ("0")  + ";}.GOE-WOTBOI.GOE-WOTBKI.GOE-WOTBMI{z-index:" + ("10")  + ";-moz-animation-name:" + ("slidedownfrommiddle")  + ";}.GOE-WOTBOI.GOE-WOTBJI.GOE-WOTBMI{z-index:" + ("0")  + ";-moz-animation-name:" + ("slidedownfromtop") ) + (";}@-moz-keyframes slideupfrombottom {\n    from { -moz-transform: translateY(100%); }\n    to { -moz-transform: translateY(0); }\n}@-moz-keyframes slidedownfrommiddle {\n    from { -moz-transform: translateY(0); }\n    to { -moz-transform: translateY(100%); }\n}@-moz-keyframes slideupfrommiddle {\n    from { -moz-transform: translateY(0); }\n    to { -moz-transform: translateY(-100%); }\n}@-moz-keyframes slidedownfromtop {\n    from { -moz-transform: translateY(-100%); }\n    to { -moz-transform: translateY(0%); }\n}.GOE-WOTBPI{-moz-transform:" + ("perspective(" + "800" + ")")  + ";-moz-animation-duration:" + ("0.7s")  + ";}.GOE-WOTBPI.GOE-WOTBKI{-moz-animation-name:" + ("swapouttoleft")  + ";}.GOE-WOTBPI.GOE-WOTBJI{-moz-animation-name:" + ("swapinfromright")  + ";}.GOE-WOTBPI.GOE-WOTBKI.GOE-WOTBMI{-moz-animation-name:" + ("swapouttoright")  + ";}.GOE-WOTBPI.GOE-WOTBJI.GOE-WOTBMI{-moz-animation-name:" + ("swapinfromleft")  + ";}@-moz-keyframes swapouttoright {\n    0% {\n         \\-moz-transform: translateX(0%) scale(0.3);\n        \n    }\n   \n    100% {\n         \\-moz-transform: translateX(100%) scale(1);\n    }\n}@-moz-keyframes swapouttoleft {\n    0% {\n         \\-moz-transform: translateX(0%) scale(1);\n       \n    }\n   \n    100% {\n        \\-moz-transform: translateX(-100%) scale(0.3);\n    }\n}@-moz-keyframes swapinfromright {\n    0% {\n        \\-moz-transform: translateX(100%) scale(0.3);\n    }\n   \n    100% {\n        \\-moz-transform: translateX(0%) scale(1);\n    }\n}@-moz-keyframes swapinfromleft {\n    0% {\n        \\-moz-transform: translateX(-100%) scale(0.3);\n       \n    }\n   \n    100% {\n        \\-moz-transform: translateX(0%) scale(1);\n    }\n}.GOE-WOTBLI.GOE-WOTBJI{-moz-animation-name:" + ("popin")  + ";}.GOE-WOTBLI.GOE-WOTBKI{-moz-animation-name:" + ("popout")  + ";}@-moz-keyframes popin {\n    from {\n        -moz-transform: scale(.3);\n        opacity: 0;\n    }\n    to {\n        -moz-transform: scale(1);\n        opacity: 1;\n    }\n}@-moz-keyframes popout {\n    from {\n        -moz-transform: scale(1);\n        opacity: 1;\n    }\n    to {\n        -moz-transform: scale(.3);\n        opacity: 0;\n    }\n}"));
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
