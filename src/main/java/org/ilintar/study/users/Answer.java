package org.ilintar.study.users;

import javax.persistence.*;

/**
 * Created by pwilkin on 06-Apr-17.
 */
@Entity
@Table(name = "ANSWER")
public class Answer {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @ManyToOne(targetEntity = Participant.class, cascade = { CascadeType.ALL }, optional = false)
    @JoinColumn(name = "PARTICIPANT")
    protected Participant participant;

    @Column(name = "QUESTION_CODE")
    protected String questionCode;

    @Column(name = "ANSWER")
    protected String answer;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", questionCode='" + questionCode + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
