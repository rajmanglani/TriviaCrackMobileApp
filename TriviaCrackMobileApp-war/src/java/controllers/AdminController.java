/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Answeroptions;
import entity.Categories;
import entity.Questions;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.AnsweroptionsFacade;
import session.CategoriesFacade;
import session.QuestionsFacade;

/**
 *This controller encapsulates the logic for displaying content on the AdminHomePage.xhtml and adding new multiple choice, true/false,
 * and fill in the blanks questions to the database. 
 * @author rkmanglani2018
 */
@ManagedBean
@RequestScoped
public class AdminController {

    // Get all facades -- used to CRUD on db tables
    @EJB
    private QuestionsFacade questionsFacade;
    
    @EJB
    private AnsweroptionsFacade optionsFacade;
    
    @EJB
    private CategoriesFacade categoriesFacade;
    
    
    private String qid;
    private String qtext;
    private String qlevel;
    private String qcategory;
    private String correct_answer;
    private String op1, op2, op3, op4, op5, op6;
    
    /**
     * Creates a new instance of AdminController
     */
    public AdminController() {
        questionsFacade = new QuestionsFacade();
        optionsFacade = new AnsweroptionsFacade();
        categoriesFacade = new CategoriesFacade();
    }

    //Getters and setters are used to give and bring values from the xhtml page. Ties values to the controller and view
    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getQtext() {
        return qtext;
    }

    public void setQtext(String qtext) {
        this.qtext = qtext;
    }

    public String getQlevel() {
        return qlevel;
    }

    public void setQlevel(String qlevel) {
        this.qlevel = qlevel;
    }

    public String getQcategory() {
        return qcategory;
    }

    public void setQcategory(String qcategory) {
        this.qcategory = qcategory;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    public String getOp5() {
        return op5;
    }

    public void setOp5(String op5) {
        this.op5 = op5;
    }

    public String getOp6() {
        return op6;
    }

    public void setOp6(String op6) {
        this.op6 = op6;
    }
    
    /**
     * This method is used to create true and false questions and record it in the db.
     */
    public void createTFQuestion(){
        Categories c = new Categories();                   // Category of the question
        c = categoriesFacade.find(this.getQcategory());
        Questions temp = new Questions();
        //set question attributes
        temp.setQid(this.getQid());
        temp.setText(this.getQtext());
        temp.setQuestionLevel(Integer.parseInt(this.getQlevel()));
        temp.setCategory(c);
        temp.setCorrectAnswer(this.getCorrect_answer());   //correct answer is either true or false.
        temp.setQuestionType("tf");                        // tf is the string used to identify true or false questions
        questionsFacade.create(temp);             
        //Show admin that the operation was successful
        FacesMessage msg = new FacesMessage("Question created successfully!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /**
     * This method is used to create fill in the blank questions and record it in the db.
     */
    public void createFBQuestion(){
        Categories c = new Categories();
        c = categoriesFacade.find(this.getQcategory());
        Questions temp = new Questions();
        //set question attributes
        temp.setQid(this.getQid());
        temp.setText(this.getQtext());
        temp.setQuestionLevel(Integer.parseInt(this.getQlevel()));
        temp.setCategory(c);
        temp.setCorrectAnswer(this.getCorrect_answer());
        temp.setQuestionType("fb");
        questionsFacade.create(temp);
        //Show admin that the operation was successful
        FacesMessage msg = new FacesMessage("Question created successfully!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /**
     * This method is used to create multiple choice questions. 
     */
    public void createQuestion(){
        Categories c = new Categories();
        c = categoriesFacade.find(this.getQcategory());
        Questions temp = new Questions();
        //Set question attributes
        temp.setQid(this.getQid());
        temp.setText(this.getQtext());
        temp.setQuestionLevel(Integer.parseInt(this.getQlevel()));
        temp.setCategory(c);
        temp.setCorrectAnswer(this.getCorrect_answer());
        questionsFacade.create(temp);
        //Create options for the question and record in db
        Answeroptions ao1 = new Answeroptions();
        ao1.setQid(temp);
        ao1.setOptionText(this.getOp1());
        ao1.setOptionid(this.getQid() + "1");     //automated id for answer options. 
        optionsFacade.create(ao1);
        
        Answeroptions ao2 = new Answeroptions();
        ao2.setQid(temp);
        ao2.setOptionText(this.getOp2());
        ao2.setOptionid(this.getQid() + "2");
        optionsFacade.create(ao2);
        
        Answeroptions ao3 = new Answeroptions();
        ao3.setQid(temp);
        ao3.setOptionText(this.getOp3());
        ao3.setOptionid(this.getQid() + "3");
        optionsFacade.create(ao3);
        
        //Only 3 options are required (plus the correct answer) to make a mcq.
        if(!this.getOp4().equals("")){
            // if admin wants to add another option to shuffle options every time the user gets the question
            Answeroptions ao4 = new Answeroptions();
            ao4.setQid(temp);
            ao4.setOptionText(this.getOp4());
            ao4.setOptionid(this.getQid() + "4");
            optionsFacade.create(ao4);
        }
        if(!this.getOp5().equals("")){
            Answeroptions ao5 = new Answeroptions();
            ao5.setQid(temp);
            ao5.setOptionText(this.getOp5());
            ao5.setOptionid(this.getQid() + "5");
            optionsFacade.create(ao5);
        }
        if(!this.getOp6().equals("")){
            Answeroptions ao6 = new Answeroptions();
            ao6.setQid(temp);
            ao6.setOptionText(this.getOp6());
            ao6.setOptionid(this.getQid() + "6");
            optionsFacade.create(ao6);
        }
        //Show admin that the operation was successful 
        FacesMessage msg = new FacesMessage("Question created successfully!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
}
