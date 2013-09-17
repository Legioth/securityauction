package org.vaadin.securityauction.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * The client side stub for the RPC service.
 */
public interface AuctionService extends RemoteService {
    public User authenticate(String username, String password);

    public List<AuctionItem> getAuctionItems(int firstItem, int itemCount);
}
