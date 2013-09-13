package org.vaadin.securityauction.vaadin;

import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@CDIUI
public class TestUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        setContent(new Label("Here"));
    }

}
