/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *This class encapsulates the logic for information regarding a user to be put up on the leader board table. 
 * @author rkmanglani2018
 */
public class LeaderboardEntity implements Comparable{
    
    private int sno;
    private String fname;
    private String lname;
    private int points;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    // Method is used to arrange users wrt their points. 
    @Override
    public int compareTo(Object o) {
        int point = ((LeaderboardEntity)o).getPoints();
        return point-this.getPoints();
    }
    
    
    
}
