package org.vaadin.securityauction.shared;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AuctionItem {
    
    @Id
    private int id;
    
    private String subject;
    
    private String description;

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
