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
      AppConstants.IMPL.ProtocolStep_1_Answer2EndText()),
  
  STEP2 (2, 
      AppConstants.IMPL.ProtocolStep_2_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_2_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_2_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_2_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_2_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_2_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_2_Answer2EndText()),
      
  STEP3 (3, 
      AppConstants.IMPL.ProtocolStep_3_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_3_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_3_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_3_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_3_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_3_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_3_Answer2EndText()),
          
  STEP4 (4, 
      AppConstants.IMPL.ProtocolStep_4_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_4_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_4_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_4_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_4_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_4_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_4_Answer2EndText()),
              
  STEP5 (5, 
      AppConstants.IMPL.ProtocolStep_5_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_5_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_5_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_5_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_5_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_5_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_5_Answer2EndText()),
                  
  STEP6 (6, 
      AppConstants.IMPL.ProtocolStep_6_QuestionText(), 
      AppConstants.IMPL.ProtocolStep_6_Answer1Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_6_Answer1Step()), 
      AppConstants.IMPL.ProtocolStep_6_Answer1EndText(),
      AppConstants.IMPL.ProtocolStep_6_Answer2Text(), 
      Integer.parseInt(AppConstants.IMPL.ProtocolStep_6_Answer2Step()), 
      AppConstants.IMPL.ProtocolStep_6_Answer2EndText()),
                      
  START (STEP1)
  
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
  private String questionText;
  private String answer1Text;
  private int answer1Step;
  private String answer1EndText;
  private String answer2Text;
  private int answer2Step;
  private String answer2EndText;
  
  private ProtocolStep(int id, String questionText, 
      String answer1Text, int answer1Step, String answer1EndText, 
      String answer2Text, int answer2Step, String answer2EndText) {
    this.id = id;
    this.questionText = questionText;
    this.answer1Text = answer1Text;
    this.answer1Step = answer1Step;
    this.answer1EndText = answer1EndText;
    this.answer2Text = answer2Text;
    this.answer2Step = answer2Step;
    this.answer2EndText = answer2EndText;
  }
  
  private ProtocolStep(ProtocolStep that) {
    this(that.id, that.questionText, 
        that.answer1Text, that.answer1Step, that.answer1EndText, 
        that.answer2Text, that.answer2Step, that.answer2EndText);
  }

  public int getId() {
    return id;
  }
  
  public String getQuestionText() {
    return questionText;
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
  
}
