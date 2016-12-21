package org.ilintar.study.question;

/**
 * Created by Konrad on 2016-12-20.
 */
public class AnswerImp implements Answer{

    private final String answerString;
    private final String answerCode;

    public AnswerImp (String answerString, String answerCode) {
        this.answerString = answerString;
        this.answerCode = answerCode;
    }

    @Override
    public String getAnswerCode() {
        return answerCode;
    }

    @Override
    public String getAnswerString() {
        return answerString;
    }
}
