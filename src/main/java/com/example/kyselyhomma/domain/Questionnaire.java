package com.example.kyselyhomma.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Questionnaire {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long questionnaireId;

    private String title;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionnaire")
    private List<Question> questions;

    public Questionnaire() {}

    public Questionnaire(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Questionnaire [questionnaireId=" + questionnaireId + ", questions=" + questions + ", title=" + title
                + ", description=" + description + "]";
    }
}