package org.vaadin.securityauction.server;

import java.util.Arrays;
import java.util.List;

import org.vaadin.securityauction.shared.AuctionItem;
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

    public List<AuctionItem> getAuctionItems(int firstItem, int itemCount) {
        // Waiting for a database
        return Arrays.asList(new AuctionItem(1, "My soul", "It's delicious"),
                new AuctionItem(2, "My phone", "It's old"));
    }

}
