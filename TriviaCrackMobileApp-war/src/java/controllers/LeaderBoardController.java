/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import logic.LeaderboardEntity;
import session.PlayerFacade;

/**
 *This controller has logic for displaying the leader board table. 
 * @author rkmanglani2018
 */
@ManagedBean
@ViewScoped
public class LeaderBoardController {

    @EJB
    private PlayerFacade playerFacade;
    
    private List<LeaderboardEntity> users;  // users only 
    private List<Player> players;            // has everyone including the admin
    private List<LeaderboardEntity> leaders;
    
    private Player user;
    
    private final int level1_points = 1;
    private final int level2_points = 2;
    private final int level3_points = 3;
    private final int level4_points = 4;
    private final int level5_points = 5;
    
    private final int noOfLeaders = 10;
    /**
     * Creates a new instance of LeaderBoardController
     */
    public LeaderBoardController() {
        users = new ArrayList<>();
        players = new ArrayList<>();
        leaders = new ArrayList<>();
        playerFacade = new PlayerFacade();
    }
    
    @PostConstruct
    public void init(){
        user = (Player) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("user");
        System.out.println(user.getFname());
        players = playerFacade.findAll();
        for(Player p :players){
            if(!p.getEmail().equalsIgnoreCase("admin")){
            LeaderboardEntity temp = new LeaderboardEntity();
            temp.setFname(p.getFname());
            temp.setLname(p.getLname());
            int points = level1_points*p.getLevel1Correct() + level2_points*p.getLevel2Correct() + level3_points*p.getLevel3Correct() + level4_points*p.getLevel4Correct() + level5_points*p.getLevel5Correct();
            
            temp.setPoints(points);
            users.add(temp);
            }
        }
        Collections.sort(users);       // Sort the users based on the compare to method in the leader board entity class
        for(int i=0; i<noOfLeaders; i++){ // add the leaders (with highest points) to the leaders list to be displayed on the page.
            LeaderboardEntity le = new LeaderboardEntity();
            le.setSno(i+1);
            le.setFname(users.get(i).getFname());
            le.setLname(users.get(i).getLname());
            le.setPoints(users.get(i).getPoints());
            leaders.add(le);
        }
    }

    public List<LeaderboardEntity> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<LeaderboardEntity> leaders) {
        this.leaders = leaders;
    }
    
    public String goBack(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
        return "UserHomePage.xhtml";
    }
    
    
}
