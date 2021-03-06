/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Answeroptions;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rkmanglani2018
 */
@Stateless
public class AnsweroptionsFacade extends AbstractFacade<Answeroptions> {
    @PersistenceContext(unitName = "TriviaCrackMobileApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnsweroptionsFacade() {
        super(Answeroptions.class);
    }
    
}
