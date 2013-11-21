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
import org.vaadin.securityauction.shared.User;

public class UserService implements Serializable {

    @PersistenceUnit(unitName = "securityauction")
    private EntityManagerFactory factory;

    public User saveUser(User user) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (user.getId() == null) {
                em.persist(user);
                em.flush();
                return user;
            } else {
                user = em.merge(user);
                em.flush();
                return user;
            }
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
            return null;
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        EntityManager em = factory.createEntityManager();
        try {
            Query query = em
                    .createQuery("SELECT a FROM User a ORDER BY a.username");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
            return null;
        } finally {
            em.close();
        }

    }

    public User getCurrentUser() {
        Integer userId = (Integer) SecurityUtils.getSubject().getSession()
                .getAttribute("userID");
        if (userId == null) {
            return null;
        }

        EntityManager em = factory.createEntityManager();
        try {
            User user = em.find(User.class, userId);
            return user;
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

}
