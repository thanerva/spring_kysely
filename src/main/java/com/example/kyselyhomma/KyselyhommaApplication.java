package com.example.kyselyhomma;

import com.example.kyselyhomma.domain.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class KyselyhommaApplication {
	private static final Logger log = LoggerFactory.getLogger(KyselyhommaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KyselyhommaApplication.class, args);
	}

	@Bean
	public CommandLineRunner initialQuestions(
			QuestionRepository questionRepo,
			QuestionnaireRepository questionnaireRepo,
			AnswerRepository answerRepo){
		return (args) -> {
			log.info("insert a test question and questionnaire");
			//Luodaan kysely, jolle annetaan parametreina otsikko ja kuvaus
			Questionnaire questionnaire = new Questionnaire("Henkilökysely", "Tämä on henkilötietokysely");
			Questionnaire questionnaire1 = new Questionnaire("Harrastuskysely", "kysellään harrastuksia");

			//Tallennetaan kysely kantaan ja tulostetaan siitä tietoja
			log.info("FIRST QUESTIONNAIRE ID: " + questionnaireRepo.save(questionnaire).getQuestionnaireId().toString());
			log.info("SCND QUESTIONNAIRE ID: " + questionnaireRepo.save(questionnaire1).getQuestionnaireId().toString());


			//testi kysely
			ArrayList<Question> questionlist = new ArrayList<Question>();
			questionlist.add(new Question("Kuka olet?", questionnaire));
			questionlist.add(new Question("Mikä olet?", questionnaire));
			questionlist.add(new Question("Miksi?", questionnaire));




			ArrayList<Question> questionlist1 = new ArrayList<Question>();
			questionlist1.add(new Question("Mitä harrastat?", questionnaire1));
			questionlist1.add(new Question("Milloin harrastat?", questionnaire1));
			questionlist1.add(new Question("Miksi harrastat?", questionnaire1));
			questionlist1.add(new Question("Läheks lätkii kiekkoo?", questionnaire1));

			//Tallennetaan kysymykset kantaan
			questionRepo.saveAll(questionlist);
			questionRepo.saveAll(questionlist1);


		};
	}

}