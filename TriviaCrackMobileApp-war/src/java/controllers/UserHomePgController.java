/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Player;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import session.PlayerFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@ViewScoped
public class UserHomePgController {

   
    private Player user;
    
    @EJB 
    private PlayerFacade playerFacade;
    
    private String fname;
    private String lname;
    /**
     * Creates a new instance of UserHomePageController
     */
    public UserHomePgController() {
        playerFacade = new PlayerFacade();
    }

    @PostConstruct
    public void init(){
        user = (Player) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("user");
        fname = user.getFname().toUpperCase();
        lname = user.getLname().toUpperCase();
        
    }

    
    
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
    
    public String injury(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
        return "InjuryAndWellnessPage.xhtml";
    }
    
    public String clinical(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
        return "ClinicalEvaluationAndDiagnosis.xhtml";
    }
    
    public String emergency(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
        return "ImmediateAndEmergencyCare.xhtml";
    }
    
    public String organizational(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
        return "OrganizationalandProfessionalHealthandWellbeing.xhtml";
    }
    
    public String rehab(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
        return "TreatmentandRehabilitation.xhtml";
    }
    
    public String other(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
        return "Other.xhtml";
    }
    
    public String leaderboard(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
        return "LeaderBoardTable.xhtml";
    }
}

