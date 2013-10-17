package org.vaadin.securityauction.vaadin.views;

import org.vaadin.securityauction.shared.Role;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

@SuppressWarnings("serial")
public class UserForm extends FormLayout {

    @PropertyId("username")
    private TextField username;

    @PropertyId("roles")
    private TwinColSelect roles;

    @SuppressWarnings("unchecked")
    public UserForm() {
        username = new TextField("Username");
        addComponent(username);

        roles = new TwinColSelect("Roles");
        roles.addContainerProperty("name", String.class, null);
        for (Role role : Role.values()) {
            Item item = roles.addItem(role);
            item.getItemProperty("name").setValue(toCamelCase(role.name()));
        }
        addComponent(roles);
        roles.setItemCaptionPropertyId("name");
    }

    private String toCamelCase(String string) {
        string = string.toLowerCase();
        string = string.substring(0, 1).toUpperCase() + string.substring(1);
        return string;
    }

}
