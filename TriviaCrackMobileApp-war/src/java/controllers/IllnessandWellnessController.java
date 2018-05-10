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
import java.util.Random;
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
 *This controller encapsulates logic for user interaction with the InjuryAndWellnessPage.xhtml
 * @author rkmanglani2018
 */
@ManagedBean
@ViewScoped
public class IllnessandWellnessController {

    @EJB
    private QuestionsFacade questionsFacade;
    
    @EJB
    private AnsweroptionsFacade optionsFacade;
    
    @EJB
    private PlayerFacade playerFacade;
    
    private String question;
    private String answer;
    
    private List<Questions> questionsList;         // get all questions
    //separated list of qurstions of illness and wellness category and corresponding levels. 
    private List<Questions> categoryQuestionslevel1; 
    private List<Questions> categoryQuestionslevel2;
    private List<Questions> categoryQuestionslevel3;
    private List<Questions> categoryQuestionslevel4;
    private List<Questions> categoryQuestionslevel5;
    
    private List<Answeroptions> optionsList; 
    private List<String> options;      // list of options to be displayed on the webpage
    
    private int correctCount = 1; // correct answers answered - used to bump a level up 
    private int wrongCount = 2; // number of wrong answers answered - used to bump a level down
    private int currentLevel = 1; // current level of questions - default is 1 (game starts) 
    //Pointers used to go over lists categoryQuestionslevel1 and so on
    private int level1Count = 0; 
    private int level2Count = 0;
    private int level3Count = 0;
    private int level4Count = 0;
    private int level5Count = 0;
    // Default timer for each question during which the user has to answer a question.
    private int timer = 20;
    //User can check answers for maximum of 3 times which playing the game. 
    private int showAnswerCount = 0;
    
    //Boolean values to check the type of question and conditionally render different format of webpage. 
    private boolean mcq = false;
    private boolean fb = false;
    private boolean tf = false;
    
    private Questions currentQuestion;
    
    private Player user;
    /**
     * Creates a new instance of IllnessandWellnessController
     */
    public IllnessandWellnessController() {
        //Initializing all lists and facades
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
            if(q.getCategory().getCategoryname().equals("injury")){ // If the category of the question is corrsponding to this page
                if(q.getQuestionLevel().equals(1)){  // Separate questions based on the different levels
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
        //Shuffle all the lists to randomize the questions.
        categoryQuestionslevel1 = getRandomList(categoryQuestionslevel1);
        categoryQuestionslevel2 = getRandomList(categoryQuestionslevel2);
        categoryQuestionslevel3 = getRandomList(categoryQuestionslevel3);
        categoryQuestionslevel4 = getRandomList(categoryQuestionslevel4);
        categoryQuestionslevel5 = getRandomList(categoryQuestionslevel5);
        
        //Set up the first question (from level 1) for the user to get started
        currentQuestion = categoryQuestionslevel1.get(level1Count);
        question = currentQuestion.getText();
        if(currentQuestion.getQuestionType() == null){ // If it is a mcq
        List<String> allOptionsForQ = new ArrayList<>();
        for(Answeroptions o : optionsList){
            if(o.getQid().getQid().equals(currentQuestion.getQid())){// options for this question
                allOptionsForQ.add(o.getOptionText());
            }
        }
        
        Collections.shuffle(allOptionsForQ); // shuffle all the options 
        options.add(allOptionsForQ.get(0)); options.add(allOptionsForQ.get(1));options.add(allOptionsForQ.get(2)); // add first 3 options 
        options.add(currentQuestion.getCorrectAnswer()); // add the correct answer 
        Collections.shuffle(options); // 4 options in total and shuffle them up again to randomize.
        mcq = true; fb = false; tf = false;             // set boolean flags to correct values
        }else if(currentQuestion.getQuestionType().equals("tf")){  // if first question is a true/false 
            mcq = false; fb = false; tf = true;         // set flags to correct value. options are just 2 - true or false -- UI takes care 
        }else if(currentQuestion.getQuestionType().equals("fb")){ // if the first question is a fill in the blank
            mcq = false; fb = true; tf = false;  // set flags -- no options for this type of question
        }
        level1Count++;   // increment pointer of level 1 questions -- so that same question is not repeated again during the game. 
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
    
    /**
     * This method is called by the poll component on the webpage. it is a ajax call made every second.
     */
    public void onTimerInc(){
        if(timer == 0){   // If the timer is done, then check the answer either way
            evaluateAnswer();
        }else{               // If the timer has not yet completed, just decrement it.
            timer--;
        }
    }

    public boolean isMcq() {
        return mcq;
    }

    public void setMcq(boolean mcq) {
        this.mcq = mcq;
    }

    public boolean isFb() {
        return fb;
    }

    public void setFb(boolean fb) {
        this.fb = fb;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }
    
    /**
     * This method takes in a list and shuffles the list and returns it.
     * @param qList
     * @return 
     */
    private List<Questions> getRandomList(List<Questions> qList){
        Collections.shuffle(qList);
        return qList;
    }
    
    /**
     * This method is used evaluate a user's answer to a question. 
     */
    public void evaluateAnswer(){
        if(correctCount == 5 && currentLevel != 5){ // If the user got 5 answers correct then send him to next level (only if he is not in level 5)
            currentLevel++;
            correctCount = 1;
            wrongCount = 1;
        }
        if(wrongCount == 3 && currentLevel != 1){ // Bumb user a level down if he is not in level 1 already
            currentLevel--;
            wrongCount = 1;
            correctCount = 1;
        }
        if(this.getAnswer() == null){ // If the timer expires and the user doesn't answer anything
            makeQuestion();
            wrongCount++;    // Mark the question as incorrect
            FacesMessage msg = new FacesMessage("Incorrect !!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(wrongCount + " wc");
            timer = 20; // reset the timer
            RequestContext.getCurrentInstance().update("ques");RequestContext.getCurrentInstance().update("opt"); // update UI components
        }
        else if(this.getAnswer().trim().equalsIgnoreCase(currentQuestion.getCorrectAnswer().trim())){ // If the user answers the question correctly
            if(currentLevel == 1){
                user.setLevel1Correct(user.getLevel1Correct()+1);  // will be used to calculate his points 
            }else if(currentLevel == 2){
                user.setLevel2Correct(user.getLevel2Correct()+1);
            }else if(currentLevel == 3){
                user.setLevel3Correct(user.getLevel3Correct()+1);
            }else if(currentLevel == 4){
                user.setLevel4Correct(user.getLevel4Correct()+1);
            }else{
                user.setLevel5Correct(user.getLevel5Correct()+1);
            }
            playerFacade.edit(user);  // update user's stats in db 
            makeQuestion();
            correctCount++;      // Increment correct count for user
            wrongCount =1;          // reset count for wrong answers. Wrong has to be consecutive for user to get bumped a level down
            System.out.println("cc " + correctCount);
            timer = 20;    // reset timer
            // update form 
//            RequestContext.getCurrentInstance().update("qForm");
            answer = "";    // Set the answer to empty because if the next question is a fill in the blank, no text will show up on the 
                            // input box since it there is binding between the answer attribute and the input text box. 
        
        }else{  // If the user answers the question incorrectly 
            makeQuestion();    
            wrongCount++;   // increment wron count 
            FacesMessage msg = new FacesMessage("Incorrect !!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(wrongCount + " wc");
            timer = 20;    // reset timer
            answer = "";      
        }
    }
    
    /**
     * This method is used to make a new question depending on the current level the user is in. 
     */
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
            
            if(currentQuestion.getQuestionType() == null){ //if it is a mcq question -- refer to init method for information 
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
            mcq = true; fb = false; tf = false;
            }else if(currentQuestion.getQuestionType().equals("tf")){
            mcq = false; fb = false; tf = true;
            }else if(currentQuestion.getQuestionType().equals("fb")){
            mcq = false; fb = true; tf = false;
           }
    }
    
    /**
     * This method is used to show answer to the user when he click on the appropriate button. The user can view answers for
     * only 3 questions when he plays the game. 
     */
    public void showAnswer(){
        if(showAnswerCount < 3){
            FacesMessage msg = new FacesMessage( "    " + currentQuestion.getCorrectAnswer() + "    ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            showAnswerCount++;    // increment the count 
        }else{ // If out of chances, let the user know
            FacesMessage msg = new FacesMessage("Sorry!! Out of tries");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    //Quit the game and go to homepage. Add user to map used for data injection (to protect the session and keep user logged in)
    public String quit(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
        return "UserHomePage.xhtml";
    }
    
    
}
