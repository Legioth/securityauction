package org.vaadin.securityauction.shared;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String username;

    public User() {
        // GWT RPC constructor
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

}