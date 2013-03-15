package it.mate.econyx.client.ui;

import it.mate.econyx.client.util.CKConfigExt;
import it.mate.econyx.client.util.CKConfigExt.TOOLBAR_OPTIONS;
import it.mate.econyx.client.util.ToolbarLineExt;
import it.mate.econyx.client.util.UrlUtils;
import it.mate.econyx.shared.model.HtmlContent;

import com.axeiya.gwtckeditor.client.CKConfig.PRESET_TOOLBAR;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.axeiya.gwtckeditor.client.Toolbar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class HtmlContentEditor extends Composite {
  
  public interface ViewUiBinder extends UiBinder<Widget, HtmlContentEditor> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField Panel htmlEditorContainer;
  @UiField HTML htmlViewer;
  @UiField Button modifyTextBtn;
  @UiField Label htmlEditorLabel;
  
  CKEditor htmlEditor;
  
  private HtmlContent htmlContent;
  private boolean isHtmlContentModified = false;
  
  private Integer width = 820;
  
  private Integer editorHeight = 220;
  
  private boolean isEditing;
  
  private static final String MODIFY_TEXT_URL = UrlUtils.getThemeResourceUrl("images/ecommerce/modify_text_14.png");
  interface ButtonTemplate extends SafeHtmlTemplates {
    @Template("<img src=\"{0}\"></img> {1}")
    SafeHtml render(SafeUri url, String text);
  }
  private static ButtonTemplate buttonTemplate = GWT.create(ButtonTemplate.class);
  
  public HtmlContentEditor() {
    initUI();
  }
  
  public void setLabelText(String text) {
    htmlEditorLabel.setText(text);
  }
  
  public void setViewerHeight(Integer viewerHeight) {
    if (!isEmpty() && htmlViewer.isVisible()) {
      htmlViewer.setHeight(viewerHeight+"px");
    }
  }
  
  public void setEditorHeight(Integer editorHeight) {
    this.editorHeight = editorHeight;
  }
  
  public void setWidth(Integer width) {
    this.width = width;
  }
  
  public boolean isEditing() {
    return isEditing;
  }
  
  public boolean isEmpty () {
    return htmlContent.getContent() == null || htmlContent.getContent().length() == 0 ;
  }
  
  public boolean isContentModified() {
    return isHtmlContentModified;
  }

  protected void initUI() {
    initProvided();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  protected void initProvided() {
    
  }
  
  protected CKEditor createHtmlEditor() {
    CKConfigExt ckConfig = new CKConfigExt(PRESET_TOOLBAR.FULL);
    
    Toolbar toolbar = new Toolbar();
    ToolbarLineExt line;
    TOOLBAR_OPTIONS[] options;
    
    line = new ToolbarLineExt();
    options = new TOOLBAR_OPTIONS[] {TOOLBAR_OPTIONS.Cut, TOOLBAR_OPTIONS.Copy, TOOLBAR_OPTIONS.Paste, TOOLBAR_OPTIONS.PasteText, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.SpellChecker, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.Undo, TOOLBAR_OPTIONS.Redo, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.Find, TOOLBAR_OPTIONS.Replace, TOOLBAR_OPTIONS.SelectAll, TOOLBAR_OPTIONS.RemoveFormat, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.Image, TOOLBAR_OPTIONS.Flash, TOOLBAR_OPTIONS.Table, TOOLBAR_OPTIONS.HorizontalRule, TOOLBAR_OPTIONS.Smiley, TOOLBAR_OPTIONS.SpecialChar};
    line.addAllExt(options);
    toolbar.add(line);
    
    toolbar.addSeparator();
    line = new ToolbarLineExt();
    options = new TOOLBAR_OPTIONS[] {TOOLBAR_OPTIONS.Bold, TOOLBAR_OPTIONS.Italic, TOOLBAR_OPTIONS.Underline, TOOLBAR_OPTIONS.Strike, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.Subscript, TOOLBAR_OPTIONS.Superscript, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.NumberedList, TOOLBAR_OPTIONS.BulletedList, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.Indent, TOOLBAR_OPTIONS.Blockquote, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.JustifyLeft, TOOLBAR_OPTIONS.JustifyCenter, TOOLBAR_OPTIONS.JustifyRight, TOOLBAR_OPTIONS.JustifyBlock, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.Link, TOOLBAR_OPTIONS.Unlink, TOOLBAR_OPTIONS.Anchor, TOOLBAR_OPTIONS.Source};
    line.addAllExt(options);
    toolbar.add(line);
    
    toolbar.addSeparator();
    line = new ToolbarLineExt();
    options = new TOOLBAR_OPTIONS[] {TOOLBAR_OPTIONS.Styles, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.Format, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.Font, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.FontSize, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.TextColor, TOOLBAR_OPTIONS.BGColor};
    line.addAllExt(options);
    toolbar.add(line);
    
    toolbar.addSeparator();
    line = new ToolbarLineExt();
    options = new TOOLBAR_OPTIONS[] {TOOLBAR_OPTIONS.Readmore};
    line.addAllExt(options);
    toolbar.add(line);
    
    ckConfig.setToolbar(toolbar);
    
    ckConfig.setWidth((width - 40)+"px");
    ckConfig.setHeight((editorHeight - 60)+"px");
    htmlEditor = new CKEditor(true, ckConfig);
    htmlEditor.setVisible(false);
    
    htmlEditorContainer.clear();
    htmlEditorContainer.add(htmlEditor);
    
    return htmlEditor;
    
  }
  
  public void setModel(HtmlContent htmlContent) {
    if (htmlEditorLabel.getText().length() == 0) {
      if (htmlContent.getType() == HtmlContent.Type.SHORT) {
        setLabelText("Descrizione breve");
      } else if (htmlContent.getType() == HtmlContent.Type.MEDIUM) {
        setLabelText("Descrizione");
      } else if (htmlContent.getType() == HtmlContent.Type.LONG) {
        setLabelText("Descrizione estesa");
      }
    }
    this.htmlContent = htmlContent;
    htmlViewer.setVisible(false);
    switchState();
  }
  
  public HtmlContent getUpdatedModel() {
    if (htmlEditor != null && htmlEditor.isVisible())
      switchState();
    return htmlContent;
  }
  
  @UiHandler("modifyTextBtn")
  public void onModifyTextBtn(ClickEvent event) {
    switchState();
  }
  
  private void switchState() {
    isHtmlContentModified = switchState(htmlContent, modifyTextBtn, htmlViewer, htmlEditor) || isHtmlContentModified;
  }
  
  private boolean switchState(HtmlContent htmlContent, Button modifyTextBtn, HTML htmlViewer, CKEditor htmlEditor) {
    if (htmlViewer.isVisible()) {
      htmlViewer.setVisible(false);
      htmlEditor = createHtmlEditor();
      htmlEditor.setHTML(htmlContent.getContent());
      htmlEditor.setVisible(true);
      modifyTextBtn.setHTML(buttonTemplate.render(UriUtils.fromSafeConstant(MODIFY_TEXT_URL), "Fine modifica"));
      isEditing = true;
      return true;
    } else {
      htmlContent.setContent(htmlEditor != null && htmlEditor.isVisible() ? htmlEditor.getHTML() : htmlContent.getContent());
      htmlViewer.setHTML(htmlContent.getContent());
      htmlViewer.setVisible(true);
      if (htmlEditor != null) {
        htmlEditor.setVisible(false);
      }
      modifyTextBtn.setHTML(buttonTemplate.render(UriUtils.fromSafeConstant(MODIFY_TEXT_URL), "Modifica testo"));
      isEditing = false;
      return false;
    }
  }
  
}
