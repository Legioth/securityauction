package org.vaadin.securityauction.shared;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="auction_item")
public class AuctionItem implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int owner;

    @Column(name = "subject")
    private String subject;

    @Column(name = "description")
    private String description;

    public AuctionItem() {
        // GWT RPC constructor
    }

    public AuctionItem(int id, String subject, String description) {
        this.id = id;
        this.subject = subject;
        this.description = description;
    }

    public int getId() {
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

}
