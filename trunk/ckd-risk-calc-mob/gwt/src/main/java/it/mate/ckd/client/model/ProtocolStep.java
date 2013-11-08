package it.mate.ckd.client.model;

import it.mate.ckd.client.constants.AppConstants;

public enum ProtocolStep {

  STEP1 (1, 
      AppConstants.IMPL.ProtocolStep_1_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_1_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_1_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_1_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_1_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_1_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_1_Answer2EndText(),
      AppConstants.IMPL.ProtocolStep_1_Answer3Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_1_Answer3Step()), 
      AppConstants.IMPL.ProtocolStep_1_Answer3EndText()),
  
  STEP2 (2, 
      AppConstants.IMPL.ProtocolStep_2_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_2_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_2_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_2_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_2_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_2_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_2_Answer2EndText(),
      AppConstants.IMPL.ProtocolStep_2_Answer3Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_2_Answer3Step()), 
      AppConstants.IMPL.ProtocolStep_2_Answer3EndText()),
      
  STEP3 (3, 
      AppConstants.IMPL.ProtocolStep_3_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_3_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_3_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_3_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_3_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_3_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_3_Answer2EndText(),
      AppConstants.IMPL.ProtocolStep_3_Answer3Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_3_Answer3Step()), 
      AppConstants.IMPL.ProtocolStep_3_Answer3EndText()),
          
  STEP4 (4, 
      AppConstants.IMPL.ProtocolStep_4_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_4_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_4_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_4_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_4_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_4_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_4_Answer2EndText(),
      AppConstants.IMPL.ProtocolStep_4_Answer3Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_4_Answer3Step()), 
      AppConstants.IMPL.ProtocolStep_4_Answer3EndText()),
              
  STEP5 (5, 
      AppConstants.IMPL.ProtocolStep_5_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_5_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_5_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_5_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_5_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_5_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_5_Answer2EndText(),
      AppConstants.IMPL.ProtocolStep_5_Answer3Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_5_Answer3Step()), 
      AppConstants.IMPL.ProtocolStep_5_Answer3EndText()),
                  
  STEP6 (6, 
      AppConstants.IMPL.ProtocolStep_6_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_6_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_6_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_6_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_6_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_6_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_6_Answer2EndText(),
      AppConstants.IMPL.ProtocolStep_6_Answer3Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_6_Answer3Step()), 
      AppConstants.IMPL.ProtocolStep_6_Answer3EndText()),
                      
  START (STEP1),
  
  END (9, "", "", -1, "", "", -1, "", "", -1, "")
                          
  ;
  
  
  public static ProtocolStep getProtocolStepById(int id) {
    for (ProtocolStep step : ProtocolStep.values()) {
      if (step.id == id) {
        return step;
      }
    }
    return null;
  }
  

  /*********************************************************/
  
  private int id;
  private String bodyText;
  private String answer1Text;
  private int answer1Step;
  private String answer1EndText;
  private String answer2Text;
  private int answer2Step;
  private String answer2EndText;
  private String answer3Text;
  private int answer3Step;
  private String answer3EndText;
  
  private ProtocolStep(int id, String bodyText, 
      String answer1Text, int answer1Step, String answer1EndText, 
      String answer2Text, int answer2Step, String answer2EndText,
      String answer3Text, int answer3Step, String answer3EndText) {
    this.id = id;
    this.bodyText = bodyText;
    this.answer1Text = answer1Text;
    this.answer1Step = answer1Step;
    this.answer1EndText = answer1EndText;
    this.answer2Text = answer2Text;
    this.answer2Step = answer2Step;
    this.answer2EndText = answer2EndText;
    this.answer3Text = answer3Text;
    this.answer3Step = answer3Step;
    this.answer3EndText = answer3EndText;
  }
  
  private ProtocolStep(ProtocolStep that) {
    this(that.id, that.bodyText, 
        that.answer1Text, that.answer1Step, that.answer1EndText, 
        that.answer2Text, that.answer2Step, that.answer2EndText,
        that.answer3Text, that.answer3Step, that.answer3EndText);
  }

  public int getId() {
    return id;
  }
  
  public String getBodyText() {
    return bodyText;
  }
  
  public void setBodyText(String bodyText) {
    this.bodyText = bodyText;
  }
  
  public boolean equals (ProtocolStep that) {
    return this.id == that.id;
  }
  
  public String getAnswer1Text() {
    return answer1Text;
  }
  
  public int getAnswer1Step() {
    return answer1Step;
  }
  
  public String getAnswer1EndText() {
    return answer1EndText;
  }
  
  public String getAnswer2Text() {
    return answer2Text;
  }
  
  public int getAnswer2Step() {
    return answer2Step;
  }

  public String getAnswer2EndText() {
    return answer2EndText;
  }

  public String getAnswer3Text() {
    return answer3Text;
  }

  public int getAnswer3Step() {
    return answer3Step;
  }

  public String getAnswer3EndText() {
    return answer3EndText;
  }
  
}
