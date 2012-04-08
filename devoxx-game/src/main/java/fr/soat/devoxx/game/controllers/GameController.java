package fr.soat.devoxx.game.controllers;

import fr.soat.devoxx.game.exceptions.AlreadyAnsweredException;
import fr.soat.devoxx.game.exceptions.InvalidQuestionException;
import fr.soat.devoxx.game.exceptions.NoMoreQuestionException;
import fr.soat.devoxx.game.forms.AnswerForm;
import fr.soat.devoxx.game.forms.QuestionsProgressTracker;
import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.QuestionChoice;
import fr.soat.devoxx.game.model.UserQuestion;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.UserServices;
import fr.soat.devoxx.game.tools.TilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
@SessionAttributes("questionsProgressTracker")
public class GameController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private QuestionServices questionServices;

    @RequestMapping(value = {"", "/", "/home","/index", "/pause"})
    public String index(final Map model, final Principal principal) {

        final DevoxxUser user = convertPrincipalToDevoxxUser(principal);

        return processIndexPageForUser(model, user);
    }

    private String processIndexPageForUser(final Map model, final DevoxxUser user) {
        if(!user.isRulesApproved()) {
            return TilesUtil.DFR_GAME_RULES_APPROVAL;
        }

        final QuestionsProgressTracker questionsProgressTracker = new QuestionsProgressTracker(
                questionServices.getPendingQuestionsForUser(user));

        model.put("questionsProgressTracker", questionsProgressTracker);

        model.put("approved", user.isEnabled());
        model.put("username", user.getUserForname());

        model.put("rank", userServices.getPosition(user));
        model.put("nbUsers", userServices.nbOfUsers());
        model.put("waitingQuestions", questionsProgressTracker.getNbOfQuestionsToAnswer());


        return TilesUtil.DFR_GAME_INDEX_PAGE;
    }

    @RequestMapping("/approveRules")
    public String approveRules(final Map model, final Principal principal) {
        final DevoxxUser user = convertPrincipalToDevoxxUser(principal);

        userServices.approveRules(user);
        
        return processIndexPageForUser(model, user);
    }

    @RequestMapping("/play")
    public String play(@ModelAttribute("questionsProgressTracker") final QuestionsProgressTracker questionsProgressTracker, 
                       final Map model, 
                       final Principal principal) {
        try {
            final UserQuestion nextQuestion = questionsProgressTracker.nextQuestion();

            nextQuestion.setStartQuestion(System.currentTimeMillis());

            questionServices.updateUserQuestion(nextQuestion);

            model.put("answerForm", new AnswerForm(nextQuestion.getQuestion().getIdQuestion()));
            model.put("question", nextQuestion.getQuestion());
            addQuestionsProgressInformationToModel(questionsProgressTracker, model);

            return TilesUtil.DFR_GAME_PLAY_PAGE;
        } catch(NoMoreQuestionException e) {
            return index(model, principal);
        }
    }

    @RequestMapping(value = "/next")
    public String nextQuestion(@ModelAttribute("questionsProgressTracker") QuestionsProgressTracker questionsProgressTracker,
                               @RequestParam("questionId") Long questionId,
                               @RequestParam("answer") Long answer,
                               Map model, Principal principal) {
        try {
            final DevoxxUser currentUser = (DevoxxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            addQuestionsProgressInformationToModel(questionsProgressTracker, model);

            final UserQuestion question = questionsProgressTracker.findQuestionById(questionId);

            checkQuestionNotAlreadyAnswered(question);

            updateQuestionWithAnswer(answer, question);

            updatePlayerScore(answer, currentUser, question);

            model.put("answerDelayInSeconds", question.getAnsweringTimeInSeconds());
            model.put("isAnswerCorrect", question.isAnswerCorrect());
            model.put("rightAnswer", question.getCorrectAnswer());

            return TilesUtil.DFR_GAME_ANSWER_PAGE;
        } catch(AlreadyAnsweredException e) {
            return index(model, principal);
        } catch (InvalidQuestionException e) {
            return index(model, principal);
        }
    }

    @RequestMapping("/rules")
    public String rules() {
        return TilesUtil.DFR_GAME_RULES_PAGE;
    }

    @RequestMapping("/about")
    public String about() {
        return TilesUtil.DFR_GAME_ABOUT_PAGE;
    }

    private DevoxxUser convertPrincipalToDevoxxUser(Principal principal) {
        return (DevoxxUser)((OpenIDAuthenticationToken)principal).getPrincipal();
    }

    private void updatePlayerScore(Long answer, DevoxxUser currentUser, UserQuestion question) {
        if(answer.equals(question.getCorrectAnswer().getQuestionChoiceId())) {
            currentUser.addToScore(1);
        }
        currentUser.addToTime(question.getAnsweringTimeInSeconds());
        userServices.updateUser(currentUser);
    }

    private void addQuestionsProgressInformationToModel(QuestionsProgressTracker questionsProgressTracker, Map model) {
        model.put("nbOfQuestionsAnswered", questionsProgressTracker.getNbOfQuestionAnswered());
        model.put("nbOfQuestionsTotal", questionsProgressTracker.getNbOfQuestionsInProgress());
        model.put("nbOfQuestionLeft", questionsProgressTracker.getNbOfQuestionsToAnswer());
    }


    private void updateQuestionWithAnswer(Long answer, UserQuestion userQuestion) {
        for(QuestionChoice choice : userQuestion.getQuestion().getChoices()) {
            if(choice.getQuestionChoiceId().equals(answer)) {
                userQuestion.setResponse(choice);
                userQuestion.setEndQuestion(System.currentTimeMillis());
                questionServices.updateUserQuestion(userQuestion);
            }
        }
    }

    private void checkQuestionNotAlreadyAnswered(UserQuestion userQuestion) {
        if(userQuestion.getResponse() != null) {
            throw new AlreadyAnsweredException();
        }
    }

    public void setUserServices(UserServices userServices) {
        this.userServices = userServices;
    }

    public void setQuestionServices(QuestionServices questionServices) {
        this.questionServices = questionServices;
    }
}
