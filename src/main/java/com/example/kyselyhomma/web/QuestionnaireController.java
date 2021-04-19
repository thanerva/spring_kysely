package com.example.kyselyhomma.web;

import com.example.kyselyhomma.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
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


    @RequestMapping(value = {"/", "/questionnairelist"}, method = RequestMethod.GET)
    public String QuestionnaireList(Model model) {
        List<Questionnaire> questionnaires = (List<Questionnaire>) questionnaireRepo.findAll();
        model.addAttribute("questionnaires", questionnaires);
/*        model.addAttribute("questionnaires", questionnaireRepo.findAll());
        model.addAttribute("questions", questionRepo.findAll());*/
        return "questionnairelist";
    }

    @RequestMapping(value = "/newquestionnaire", method = RequestMethod.GET)
    public String getNewQuestionnaireForm(Model model) {
        Questionnaire questionnaire = new Questionnaire();

        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question());
        questionnaire.setQuestions(questions);

        model.addAttribute("questionnaire", questionnaire);

        return "questionnaireform";
    }

    @RequestMapping(value = "/savequestionnaire", params = {"addQuestion"})
    public String addQuestion(@ModelAttribute Questionnaire questionnaire) {

        List<Question> questions = questionnaire.getQuestions();

        questions.add(new Question());

        questionnaire.setQuestions(questions);

        return "questionnaireform";
    }

    @RequestMapping(value = "/savequestionnaire", method = RequestMethod.POST)
    public String saveQuestionnaire(@ModelAttribute Questionnaire questionnaire) {

        Questionnaire saved = questionnaireRepo.save(questionnaire);
        List<Question> questions = questionnaire.getQuestions();

        for(Question question: questions) {
            question.setQuestionnaire(saved);
        }
        questionRepo.saveAll(questions);
        return "redirect:questionnairelist";
    }
    @GetMapping(value = "/deletequestionnaire/{id}")
    public String deleteQuestionnaire(@PathVariable("id") Long questionnaireId, Model model) {
        questionnaireRepo.deleteById(questionnaireId);

        return "redirect:../questionnairelist";
    }
    @RequestMapping(value="/addanswer/{id}")
    public String AddAnswer(@PathVariable("id") Long questionId, Model model) {
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
    @GetMapping(value = "/deletequestion/{id}")
    public String deleteQuestion(@PathVariable("id") Long questionId, Model model) {
        questionRepo.deleteById(questionId);
        return "redirect:../questionnairelist";
    }





}
