package org.vaadin.securityauction.vaadin.views;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;

public class UserForm extends FormLayout {
    
    @PropertyId("username")
    private TextField username;
    
    public UserForm() {
        username = new TextField("Username");
        addComponent(username);
    }

}
