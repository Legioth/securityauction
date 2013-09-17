package org.vaadin.securityauction.shared;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AuctionItem implements Serializable {

    @Id
    private int id;

    private String subject;

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

}
