package org.vaadin.securityauction.vaadin.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@CDIView("admin")
public class AdminView extends HorizontalLayout implements View{
    
    public AdminView() {
        addComponent(new Label("Placeholder"));
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        
    }

}
