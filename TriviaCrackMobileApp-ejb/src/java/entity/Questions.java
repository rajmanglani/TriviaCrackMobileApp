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
@Table(name = "QUESTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questions.findAll", query = "SELECT q FROM Questions q"),
    @NamedQuery(name = "Questions.findByQid", query = "SELECT q FROM Questions q WHERE q.qid = :qid"),
    @NamedQuery(name = "Questions.findByText", query = "SELECT q FROM Questions q WHERE q.text = :text"),
    @NamedQuery(name = "Questions.findByQuestionLevel", query = "SELECT q FROM Questions q WHERE q.questionLevel = :questionLevel"),
    @NamedQuery(name = "Questions.findByCorrectAnswer", query = "SELECT q FROM Questions q WHERE q.correctAnswer = :correctAnswer"),
    @NamedQuery(name = "Questions.findByQuestionType", query = "SELECT q FROM Questions q WHERE q.questionType = :questionType")})
public class Questions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "QID")
    private String qid;
    @Size(max = 1000)
    @Column(name = "TEXT")
    private String text;
    @Column(name = "QUESTION_LEVEL")
    private Integer questionLevel;
    @Size(max = 255)
    @Column(name = "CORRECT_ANSWER")
    private String correctAnswer;
    @Size(max = 255)
    @Column(name = "QUESTION_TYPE")
    private String questionType;
    @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORYNAME")
    @ManyToOne
    private Categories category;

    public Questions() {
    }

    public Questions(String qid) {
        this.qid = qid;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(Integer questionLevel) {
        this.questionLevel = questionLevel;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qid != null ? qid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questions)) {
            return false;
        }
        Questions other = (Questions) object;
        if ((this.qid == null && other.qid != null) || (this.qid != null && !this.qid.equals(other.qid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Questions[ qid=" + qid + " ]";
    }
    
}
