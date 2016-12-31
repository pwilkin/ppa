package org.ilintar.study.question;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SimpleAnswerHolder implements  AnswerHolder{

    private List list = new ArrayList<String>();
    @Override
    public void putAnswer(Object answer) {
        list.add(answer);
    }

    @Override
    public Collection<Objects> getAnswers() {
        return list;
    }
}
