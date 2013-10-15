package org.vaadin.securityauction.server;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.vaadin.securityauction.shared.AuctionItem;

/**
 * Backend functionality for administrating functionality
 * 
 * @author Kim
 * 
 */
public class AuctionAdministrationService implements Serializable {

    @PersistenceUnit(unitName = "securityauction")
    private EntityManagerFactory factory;

    public AuctionItem saveAuctionItem(AuctionItem item) {
        EntityManager em = factory.createEntityManager();
        try {
            if (item.getId() == 0) {
                em.persist(item);
                return item;
            } else {
                item = em.merge(item);
                return item;
            }
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<AuctionItem> getAuctionItems() {
        Session session = SecurityUtils.getSubject().getSession();
        Integer userId = (Integer) session.getAttribute("userID");
        if(userId == null) {
            throw new RuntimeException("You are not logged in");
        }
        
        EntityManager em = factory.createEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM AuctionItem a WHERE a.owner = :owner");
            query.setParameter("owner", userId);
            return query.getResultList();
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
            return null;
        } finally {
            em.close();
        }

    }

}
