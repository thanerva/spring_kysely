package com.example.kyselyhomma.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;
    private String questionText;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name ="questionnaireId")
    private Questionnaire questionnaire;

    @OneToMany(cascade = CascadeType.ALL, mappedBy ="question")
    private List<Answer> answers;

    public Question() {}

    public Question(String questionText, Questionnaire questionnaire) {
        super();
        this.questionText = questionText;
        this.questionnaire = questionnaire;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question [questionId=" + questionId + ", questionnaire=" + questionnaire + ", questionText="
                + questionText + "]";
    }
}
