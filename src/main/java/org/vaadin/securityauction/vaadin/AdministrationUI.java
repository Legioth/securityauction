package org.vaadin.securityauction.vaadin;

import javax.inject.Inject;

import org.vaadin.securityauction.vaadin.views.ErrorView;

import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@CDIUI
public class AdministrationUI extends UI {
    
    @Inject
    private CDIViewProvider provider;

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator =  new Navigator(this, this);
        navigator.addProvider(provider);
        navigator.setErrorView(ErrorView.class);
        
        setContent(new Label("Here"));
    }

}
