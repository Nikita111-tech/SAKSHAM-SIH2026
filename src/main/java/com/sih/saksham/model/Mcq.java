package com.sih.saksham.model;
import lombok.Data;
@Data
public class Mcq {
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private String explanation;
}
