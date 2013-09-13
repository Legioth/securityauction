package org.vaadin.securityauction.server;

import org.vaadin.securityauction.shared.AuctionService;
import org.vaadin.securityauction.shared.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AuctionServiceImpl extends RemoteServiceServlet implements
        AuctionService {

    public User authenticate(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return new User(0, "admin");
        } else if ("user".equals(username) && "user".equals(password)) {
            return new User(1, "user");
        } else {
            return null;
        }
    }

}
