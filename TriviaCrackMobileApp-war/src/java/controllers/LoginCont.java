/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Player;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import session.PlayerFacade;

/**
 *
 * @author rkmanglani2018
 */
@ManagedBean
@SessionScoped
public class LoginCont implements Serializable{

   @EJB
    private PlayerFacade playerFacade;
    
    private String username;
    private String password;
    private final String ADMIN = "admin";
    
    /**
     * Creates a new instance of LoginController
     */
    public LoginCont() {
        playerFacade = new PlayerFacade();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String login(){
        Player p = new Player();
        if(this.getUsername().trim().equalsIgnoreCase(ADMIN)){
            p = playerFacade.find(ADMIN);
            if(p.getPassword().equals(this.getPassword())){
                return "AdminHomePage.xhtml";
            }
        }
        p = playerFacade.find(this.getUsername().trim().toLowerCase());
        if(p==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("An Error Occured: Login failed", null));
        }
        else if(p.getPassword().equals(this.getPassword())){
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", p);
            return "UserHomePage.xhtml";
        }else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("An Error Occured: Login failed", null));
        
        }
        return null;
    }
    
    public String signUp(){
        return "SignUpPage.xhtml";
    }
    
    
}
