package org.vaadin.securityauction.shared;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * The client side stub for the RPC service.
 */
public interface AuctionService extends RemoteService {
    
    public User getCurrentUser();
    
    public User authenticate(String username, String password);

    public void logout();

    public List<AuctionItem> getAuctionItems(int firstItem, int itemCount);

    public AuctionItem getAuctionItem(int id);

    public void bid(int auctionItemId, float amount, BidType bidType, Date bidTime);
}
