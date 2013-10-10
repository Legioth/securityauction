package org.vaadin.securityauction.vaadin.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class ErrorView extends VerticalLayout implements View {

    public ErrorView() {
        Label errorLabel = new Label(
                "The view you were looking for does not exist");
        errorLabel.setStyleName(Reindeer.LABEL_H1);
        addComponent(errorLabel);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
