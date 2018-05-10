/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Player;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.PlayerFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@RequestScoped
public class SignupController {

    @EJB
    private PlayerFacade playerFacade;
    
    private String email;
    private String fname;
    private String lname;
    private String password;
    private String reppassword;
    /**
     * Creates a new instance of SignupController
     */
    public SignupController() {
        playerFacade = new PlayerFacade();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReppassword() {
        return reppassword;
    }

    public void setReppassword(String reppassword) {
        this.reppassword = reppassword;
    }
    
    public void signUp() throws IOException{
        Player temp = new Player();
        if(this.getReppassword().equals(this.getPassword()) && this.getEmail().contains("@")){
            temp.setEmail(this.getEmail().toLowerCase());
            temp.setFname(this.getFname());
            temp.setLname(this.getLname());
            temp.setPassword(this.getPassword());
            temp.setLevel1Correct(0);
            temp.setLevel2Correct(0);
            temp.setLevel3Correct(0);
            temp.setLevel4Correct(0);
            temp.setLevel5Correct(0);
            playerFacade.create(temp);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            FacesMessage msg = new FacesMessage("Profile Created! Go ahead and Login");
            context.addMessage(null, msg);
            context.getExternalContext().redirect("index.xhtml");
        }
        else{
        FacesMessage msg = new FacesMessage("Please check Email, Password and Repeat Password fields");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
}
