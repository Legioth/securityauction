package org.vaadin.securityauction.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "bid")
@SuppressWarnings("serial")
public class Bid implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "amount")
    private float amount;

    @Column(name = "bidder")
    private int bidder;

    @Column(name = "item_id")
    private int itemId;
    
    @Column(name = "bid_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bidTime;
    
    @Column(name = "bid_type")
    @Enumerated(EnumType.STRING)
    private BidType bidType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getBidder() {
        return bidder;
    }

    public void setBidder(int bidder) {
        this.bidder = bidder;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

}
