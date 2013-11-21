package org.vaadin.securityauction.server;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.annotation.WebServlet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.vaadin.securityauction.shared.AuctionItem;
import org.vaadin.securityauction.shared.AuctionService;
import org.vaadin.securityauction.shared.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/SecurityAuction/AuctionService" })
public class AuctionServiceImpl extends RemoteServiceServlet implements
        AuctionService {

    @PersistenceUnit(unitName = "securityauction")
    private EntityManagerFactory factory;

    @Override
    public User authenticate(String username, String password) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,
                password);
        EntityManager entityManager = null;
        try {
            currentUser.login(token);
            entityManager = factory.createEntityManager();
            Query query = entityManager
                    .createQuery("SELECT u FROM User u WHERE u.username = :username");
            query.setParameter("username", username);

            // Execute query and return result
            User user = (User) query.getSingleResult();

            Session session = currentUser.getSession();
            session.setAttribute("userID", user.getId());

            return user;
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            currentUser.logout();
        }
    }

    @Override
    public List<AuctionItem> getAuctionItems(int firstItem, int itemCount) {
        EntityManager em = factory.createEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM AuctionItem a");
            query.setFirstResult(firstItem);
            query.setMaxResults(itemCount);
            return query.getResultList();
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public AuctionItem getAuctionItem(int id) {
        EntityManager em = factory.createEntityManager();
        try {
            AuctionItem item = em.find(AuctionItem.class, id);
            return item;
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

}
