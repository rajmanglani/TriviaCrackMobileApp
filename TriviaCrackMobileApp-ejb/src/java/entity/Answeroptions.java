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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ANSWEROPTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Answeroptions.findAll", query = "SELECT a FROM Answeroptions a"),
    @NamedQuery(name = "Answeroptions.findByOptionid", query = "SELECT a FROM Answeroptions a WHERE a.optionid = :optionid"),
    @NamedQuery(name = "Answeroptions.findByOptionText", query = "SELECT a FROM Answeroptions a WHERE a.optionText = :optionText")})
public class Answeroptions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "OPTIONID")
    private String optionid;
    @Size(max = 255)
    @Column(name = "OPTION_TEXT")
    private String optionText;
    @JoinColumn(name = "QID", referencedColumnName = "QID")
    @ManyToOne
    private Questions qid;

    public Answeroptions() {
    }

    public Answeroptions(String optionid) {
        this.optionid = optionid;
    }

    public String getOptionid() {
        return optionid;
    }

    public void setOptionid(String optionid) {
        this.optionid = optionid;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Questions getQid() {
        return qid;
    }

    public void setQid(Questions qid) {
        this.qid = qid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (optionid != null ? optionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Answeroptions)) {
            return false;
        }
        Answeroptions other = (Answeroptions) object;
        if ((this.optionid == null && other.optionid != null) || (this.optionid != null && !this.optionid.equals(other.optionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Answeroptions[ optionid=" + optionid + " ]";
    }
    
}
