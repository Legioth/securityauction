package org.vaadin.securityauction.vaadin.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@CDIView("")
public class ItemListView extends VerticalLayout implements View {

    public ItemListView() {
        addComponent(new Label("Placeholder"));
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
