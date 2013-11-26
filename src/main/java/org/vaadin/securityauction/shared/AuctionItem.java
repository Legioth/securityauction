package org.vaadin.securityauction.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "auction_item")
public class AuctionItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private int owner;

    @Column(name = "subject")
    private String subject;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    @OrderBy(value = "amount ASC")
    private List<Bid> bids;

    @Column(name = "close_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;

    public AuctionItem() {
        // GWT RPC constructor
    }

    public AuctionItem(int id, String subject, String description) {
        this.id = id;
        this.subject = subject;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public float getHighestBid() {
        List<Bid> bids = getBids();
        if (bids == null || bids.size() == 0) {
            return 0;
        }

        float highestBid = 0;
        for (Bid bid : bids) {
            if (!BidType.AUTOBID.equals(bid.getAmount())
                    && bid.getAmount() > highestBid) {
                highestBid = bid.getAmount();
            }
        }

        return highestBid;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public boolean isClosed() {
        return new Date().before(getCloseDate());
    }

}
