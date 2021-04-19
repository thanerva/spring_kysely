package com.example.kyselyhomma.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    List<Answer> findByAnswerText(String answertext);
}