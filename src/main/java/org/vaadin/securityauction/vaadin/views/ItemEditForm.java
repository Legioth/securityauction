package org.vaadin.securityauction.vaadin.views;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class ItemEditForm extends FormLayout {
    
    @PropertyId("subject")
    private TextField subject;
    
    @PropertyId("description")
    private TextArea description;
    
    
    public ItemEditForm() {
        subject = new TextField("Subject");
        subject.setWidth(30, Unit.EM);
        addComponent(subject);

        description = new TextArea("Description");
        description.setWidth(100, Unit.EM);
        description.setHeight(5, Unit.EM);
        addComponent(description);
    }

}
