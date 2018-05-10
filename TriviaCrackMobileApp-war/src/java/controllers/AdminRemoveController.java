/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Answeroptions;
import entity.Questions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.AnsweroptionsFacade;
import session.QuestionsFacade;

/**
 *This class encapsulates the logic for displaying stuff on AdminRemovePage.xhtml and 
 * removing questions from the db. Only the admin has access to it.
 * @author rkmanglani2018
 */
@ManagedBean
@RequestScoped
public class AdminRemoveController {

    @EJB
    private QuestionsFacade questionsFacade;
    
    @EJB
    private AnsweroptionsFacade optionsFacade;
    
    private List<Questions> questionsList;      // List of questions to be displayed on the page
    private List<Answeroptions> optionsList;
    /**
     * Creates a new instance of AdminRemoveController
     */
    public AdminRemoveController() {
        questionsFacade = new QuestionsFacade();
        optionsFacade = new AnsweroptionsFacade();
        questionsList = new ArrayList<>();
        optionsList = new ArrayList<>();
    }
    
    @PostConstruct
    public void init(){
        // Initializes the page with all the questions in the db
        questionsList = questionsFacade.findAll();
    }

    public List<Questions> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }
    
    /**
     * This method is used to remove a question from the db. All the options corresponding to a question are also removed. 
     * @param q -- question that is to be removed - object sent from the view page
     * @throws IOException 
     */
    public void remove(Questions q) throws IOException{
        optionsList =  optionsFacade.findAll();      // Find all options in the db
        for(Answeroptions o : optionsList){
            if(o.getQid().getQid().equals(q.getQid())){
                //options that have foreign key as the question that needs to be removed
                optionsFacade.remove(o);
            }
        }
        
        questionsFacade.remove(q);
        //This updates the admin that the question was removed and refreshes the page to show the updated list of questions.
             FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            FacesMessage msg = new FacesMessage("Question Removed");
            context.addMessage(null, msg);
            context.getExternalContext().redirect("AdminRemovePage.xhtml");
    }
    
}
