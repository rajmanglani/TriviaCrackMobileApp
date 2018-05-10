/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Answeroptions;
import entity.Player;
import entity.Questions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import session.AnsweroptionsFacade;
import session.PlayerFacade;
import session.QuestionsFacade;

/**
 * Refer to IllnessandWellnessController for documentation
 * @author rkmanglani2018
 */
@ManagedBean
@ViewScoped
public class ClinicalEvaluationAndDiagnosisController {

    
    @EJB
    private QuestionsFacade questionsFacade;
    
    @EJB
    private AnsweroptionsFacade optionsFacade;
    
    @EJB
    private PlayerFacade playerFacade;
    
    private String question;
    private String answer;
    
    private List<Questions> questionsList;
    private List<Questions> categoryQuestionslevel1;
    private List<Questions> categoryQuestionslevel2;
    private List<Questions> categoryQuestionslevel3;
    private List<Questions> categoryQuestionslevel4;
    private List<Questions> categoryQuestionslevel5;
    
    private List<Answeroptions> optionsList;
    private List<String> options;
    
    private int correctCount = 1;
    private int wrongCount = 2;
    private int currentLevel = 1;
    private int level1Count = 0;
    private int level2Count = 0;
    private int level3Count = 0;
    private int level4Count = 0;
    private int level5Count = 0;
    private int timer = 20;
    private int showAnswerCount = 0;
    
    private Questions currentQuestion;
    
    private Player user;
    /**
     * Creates a new instance of IllnessandWellnessController
     */
    public ClinicalEvaluationAndDiagnosisController() {
        questionsFacade = new QuestionsFacade();
        optionsFacade = new AnsweroptionsFacade();
        playerFacade = new PlayerFacade();
        questionsList = new ArrayList<>();
        categoryQuestionslevel1 = new ArrayList<>();
        categoryQuestionslevel2 = new ArrayList<>();
        categoryQuestionslevel3 = new ArrayList<>();
        categoryQuestionslevel4 = new ArrayList<>();
        categoryQuestionslevel5 = new ArrayList<>();
        optionsList = new ArrayList<>();
        options = new ArrayList<>();
      
    }
    
    @PostConstruct
    public void init(){
        user = (Player) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("user");
        System.out.println(user.getFname());
        questionsList = questionsFacade.findAll();
        optionsList = optionsFacade.findAll();
        for(Questions q : questionsList){
            if(q.getCategory().getCategoryname().equals("clinical")){
                if(q.getQuestionLevel().equals(1)){
                    categoryQuestionslevel1.add(q);
                }else if(q.getQuestionLevel() == 2){
                    categoryQuestionslevel2.add(q);
                }else if(q.getQuestionLevel() == 3){
                    categoryQuestionslevel3.add(q);
                }else if(q.getQuestionLevel() == 4){
                    categoryQuestionslevel4.add(q);
                }else{
                    categoryQuestionslevel5.add(q);
                }
            }
        }
        
        categoryQuestionslevel1 = getRandomList(categoryQuestionslevel1);
        categoryQuestionslevel2 = getRandomList(categoryQuestionslevel2);
        categoryQuestionslevel3 = getRandomList(categoryQuestionslevel3);
        categoryQuestionslevel4 = getRandomList(categoryQuestionslevel4);
        categoryQuestionslevel5 = getRandomList(categoryQuestionslevel5);
        
        currentQuestion = categoryQuestionslevel1.get(level1Count);
        question = currentQuestion.getText();
        List<String> allOptionsForQ = new ArrayList<>();
        for(Answeroptions o : optionsList){
            if(o.getQid().getQid().equals(currentQuestion.getQid())){
                allOptionsForQ.add(o.getOptionText());
            }
        }
        
        Collections.shuffle(allOptionsForQ);
        options.add(allOptionsForQ.get(0)); options.add(allOptionsForQ.get(1));options.add(allOptionsForQ.get(2));
        options.add(currentQuestion.getCorrectAnswer());
        Collections.shuffle(options);
        level1Count++;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
    
    public void onTimerInc(){
        if(timer == 0){
            evaluateAnswer();
        }else{
            timer--;
        }
    }
    
    private List<Questions> getRandomList(List<Questions> qList){
        Collections.shuffle(qList);
        return qList;
    }
    
    public void evaluateAnswer(){
        if(correctCount == 5 && currentLevel != 5){
            currentLevel++;
            correctCount = 1;
            wrongCount = 1;
        }
        if(wrongCount == 3 && currentLevel != 1){
            currentLevel--;
            wrongCount = 1;
            correctCount = 1;
        }
        if(this.getAnswer() == null){
            makeQuestion();
            wrongCount++;
            FacesMessage msg = new FacesMessage("Incorrect !!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(wrongCount + " wc");
            timer = 20;
            RequestContext.getCurrentInstance().update("ques1");RequestContext.getCurrentInstance().update("opt1");
        }
        else if(this.getAnswer().equals(currentQuestion.getCorrectAnswer())){
            if(currentLevel == 1){
                user.setLevel1Correct(user.getLevel1Correct()+1);
            }else if(currentLevel == 2){
                user.setLevel2Correct(user.getLevel2Correct()+1);
            }else if(currentLevel == 3){
                user.setLevel3Correct(user.getLevel3Correct()+1);
            }else if(currentLevel == 4){
                user.setLevel4Correct(user.getLevel4Correct()+1);
            }else{
                user.setLevel5Correct(user.getLevel5Correct()+1);
            }
            playerFacade.edit(user);
            makeQuestion();
            correctCount++;
            wrongCount =1;
            System.out.println("cc " + correctCount);
            timer = 20;
            // update form 
//            RequestContext.getCurrentInstance().update("qForm");
        
        }else{
            makeQuestion();
            wrongCount++;
            FacesMessage msg = new FacesMessage("Incorrect !!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(wrongCount + " wc");
            timer = 20;
        }
    }
    
    private void makeQuestion(){
        if(currentLevel == 1){
                currentQuestion = categoryQuestionslevel1.get(level1Count);
                level1Count++;
            }else if(currentLevel == 2){
                currentQuestion = categoryQuestionslevel2.get(level2Count);
                level2Count++;
            }else if(currentLevel == 3){
                currentQuestion = categoryQuestionslevel3.get(level3Count);
                level3Count++;
            }else if(currentLevel == 4){
                currentQuestion = categoryQuestionslevel4.get(level4Count);
                level4Count++;
            }else{
                currentQuestion = categoryQuestionslevel5.get(level5Count);
                level5Count++;
            }
            
            question = currentQuestion.getText();
            List<String> allOptionsForQ = new ArrayList<>();
            for(Answeroptions o : optionsList){
                if(o.getQid().getQid().equals(currentQuestion.getQid())){
                    allOptionsForQ.add(o.getOptionText());
                }
            }

            Collections.shuffle(allOptionsForQ);
            options.clear();
            options.add(allOptionsForQ.get(0)); options.add(allOptionsForQ.get(1));options.add(allOptionsForQ.get(2));
            options.add(currentQuestion.getCorrectAnswer());
            Collections.shuffle(options);
    }
    
    public void showAnswer(){
        if(showAnswerCount < 3){
            FacesMessage msg = new FacesMessage(currentQuestion.getCorrectAnswer());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            showAnswerCount++;
        }else{
            FacesMessage msg = new FacesMessage("Sorry!! Out of tries");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
     public String quit(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
        return "UserHomePage.xhtml";
    }
    
}
