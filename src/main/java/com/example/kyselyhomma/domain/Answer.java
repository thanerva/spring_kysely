package com.example.kyselyhomma.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Answer {
    // attribuutit
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long answerId;
    public String answerText;

    // relaatiot
    @ManyToOne
    // @JsonIgnoreProperties - vältetään tällä loputtomat loopit kahdensuuntaisten relaatioiden JSON serialisaatioissa
    @JsonIgnore
    @JoinColumn(name = "questionId")
    private Question question;

    // konstruktori ilman attribuutteja
    public Answer() {}

    // konstruktorit
    public Answer(String answerText, Question question) {
        super();
        this.answerText = answerText;
        this.question = question;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer [answerId=" + answerId + ", answerText=" + answerText + ", question =" + this.getQuestion() + "]";
    }
}