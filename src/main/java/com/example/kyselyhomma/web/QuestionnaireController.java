package com.example.kyselyhomma.web;

import com.example.kyselyhomma.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionnaireController {

    @Autowired
    private QuestionnaireRepository questionnaireRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private AnswerRepository answerRepo;

    //Haetaan kyselyt ja niiden sisältö ja lisätään ne thymeleaffille atribuutiksi
    @RequestMapping(value = {"/", "/questionnairelist"}, method = RequestMethod.GET)
    public String QuestionnaireList(Model model) {
        List<Questionnaire> questionnaires = (List<Questionnaire>) questionnaireRepo.findAll();
        model.addAttribute("questionnaires", questionnaires);
/*        model.addAttribute("questionnaires", questionnaireRepo.findAll());
        model.addAttribute("questions", questionRepo.findAll());*/
        return "questionnairelist";
    }

    //Kyselyn luonti
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/newquestionnaire", method = RequestMethod.GET)
    public String getNewQuestionnaireForm(Model model) {
        Questionnaire questionnaire = new Questionnaire();

        //luodaan kysymyslista ja lisätään siihen kysymys joka annetaan kysely oliolle
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question());
        questionnaire.setQuestions(questions);

        model.addAttribute("questionnaire", questionnaire);

        return "questionnaireform";
    }
    //kysymyksen lisäys kyselylomakkeeseen
    @RequestMapping(value = "/savequestionnaire", params = {"addQuestion"})
    public String addQuestion(@ModelAttribute Questionnaire questionnaire) {
        //haetaan kyselyn kysymykset ja lisätään niihin uusi kysymys
        List<Question> questions = questionnaire.getQuestions();
        questions.add(new Question());

        questionnaire.setQuestions(questions);

        return "questionnaireform";
    }
    // kysely lomakkeelle syötettyjen kyssäreiden vastaanotto ja tallennus tietokantaan
    @RequestMapping(value = "/savequestionnaire", method = RequestMethod.POST)
    public String saveQuestionnaire(@ModelAttribute Questionnaire questionnaire) {
        //Tallennetaan tietokantaan jotta saadaan id
        Questionnaire saved = questionnaireRepo.save(questionnaire);
        List<Question> questions = questionnaire.getQuestions();
        //Käydään kysymykset läpi ja asetetaan niille viite oikeaan kyselyyn
        for(Question question: questions) {
            question.setQuestionnaire(saved);
        }
        questionRepo.saveAll(questions);
        return "redirect:questionnairelist";
    }

    //poistetaan kysely id:n perusteella
    @GetMapping(value = "/deletequestionnaire/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteQuestionnaire(@PathVariable("id") Long questionnaireId, Model model) {
        questionnaireRepo.deleteById(questionnaireId);

        return "redirect:../questionnairelist";
    }

    @RequestMapping(value="/addanswer/{id}")
    public String AddAnswer(@PathVariable("id") Long questionId, String questionText, Model model) {
        model.addAttribute("question", questionRepo.findById(questionId).get().getQuestionId());

        return "addanswer";
    }

    @PostMapping(value = "/saveanswer")
    public String saveAnswer(@RequestParam(value="questionId") Long questionId,
                             @RequestParam(value="answerText") String answerText) {
        Question question = questionRepo.findById(questionId).get();
        answerRepo.save(new Answer(answerText, question));
        return "redirect:/questionnairelist";
    }

    // poistetaan yksittäinen kysymys kyselystä id:n perusteella
    @GetMapping(value = "/deletequestion/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteQuestion(@PathVariable("id") Long questionId, Model model) {
        questionRepo.deleteById(questionId);
        return "redirect:../questionnairelist";
    }

    @GetMapping(value="/login")
    public String loginPage() {
        
        return "login";
    }






}
