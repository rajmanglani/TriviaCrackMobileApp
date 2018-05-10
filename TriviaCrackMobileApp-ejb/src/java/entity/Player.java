/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rkmanglani2018
 */
@Entity
@Table(name = "PLAYER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findByEmail", query = "SELECT p FROM Player p WHERE p.email = :email"),
    @NamedQuery(name = "Player.findByFname", query = "SELECT p FROM Player p WHERE p.fname = :fname"),
    @NamedQuery(name = "Player.findByLname", query = "SELECT p FROM Player p WHERE p.lname = :lname"),
    @NamedQuery(name = "Player.findByPassword", query = "SELECT p FROM Player p WHERE p.password = :password"),
    @NamedQuery(name = "Player.findByLevel1Correct", query = "SELECT p FROM Player p WHERE p.level1Correct = :level1Correct"),
    @NamedQuery(name = "Player.findByLevel2Correct", query = "SELECT p FROM Player p WHERE p.level2Correct = :level2Correct"),
    @NamedQuery(name = "Player.findByLevel3Correct", query = "SELECT p FROM Player p WHERE p.level3Correct = :level3Correct"),
    @NamedQuery(name = "Player.findByLevel4Correct", query = "SELECT p FROM Player p WHERE p.level4Correct = :level4Correct"),
    @NamedQuery(name = "Player.findByLevel5Correct", query = "SELECT p FROM Player p WHERE p.level5Correct = :level5Correct")})
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    @Column(name = "FNAME")
    private String fname;
    @Size(max = 255)
    @Column(name = "LNAME")
    private String lname;
    @Size(max = 16)
    @Column(name = "PASSWORD")
    private String password;
    @Lob
    @Column(name = "PICTURE")
    private Serializable picture;
    @Column(name = "LEVEL1_CORRECT")
    private Integer level1Correct;
    @Column(name = "LEVEL2_CORRECT")
    private Integer level2Correct;
    @Column(name = "LEVEL3_CORRECT")
    private Integer level3Correct;
    @Column(name = "LEVEL4_CORRECT")
    private Integer level4Correct;
    @Column(name = "LEVEL5_CORRECT")
    private Integer level5Correct;

    public Player() {
    }

    public Player(String email) {
        this.email = email;
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

    public Serializable getPicture() {
        return picture;
    }

    public void setPicture(Serializable picture) {
        this.picture = picture;
    }

    public Integer getLevel1Correct() {
        return level1Correct;
    }

    public void setLevel1Correct(Integer level1Correct) {
        this.level1Correct = level1Correct;
    }

    public Integer getLevel2Correct() {
        return level2Correct;
    }

    public void setLevel2Correct(Integer level2Correct) {
        this.level2Correct = level2Correct;
    }

    public Integer getLevel3Correct() {
        return level3Correct;
    }

    public void setLevel3Correct(Integer level3Correct) {
        this.level3Correct = level3Correct;
    }

    public Integer getLevel4Correct() {
        return level4Correct;
    }

    public void setLevel4Correct(Integer level4Correct) {
        this.level4Correct = level4Correct;
    }

    public Integer getLevel5Correct() {
        return level5Correct;
    }

    public void setLevel5Correct(Integer level5Correct) {
        this.level5Correct = level5Correct;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Player[ email=" + email + " ]";
    }
    
}
