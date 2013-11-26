package org.vaadin.securityauction.vaadin;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.vaadin.securityauction.vaadin.views.ErrorView;

import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@CDIUI
@Title("Edit acution items")
public class AdministrationUI extends UI {

    @Inject
    private CDIViewProvider provider;

    private HorizontalLayout layout = new HorizontalLayout();

    private CssLayout buttonLayout;

    private ViewDisplay viewDisplay = new ViewDisplay() {

        @Override
        public void showView(View view) {
            layout.removeAllComponents();
            layout.addComponent(buttonLayout);
            layout.addComponent((Component) view);
            layout.setExpandRatio((Component) view, 1);
            ((Component) view).setSizeFull();
        }
    };

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, viewDisplay);
        navigator.addProvider(provider);
        navigator.setErrorView(ErrorView.class);

        layout.setSpacing(true);
        layout.setSizeFull();
        setContent(layout);

        buttonLayout = new CssLayout();
        buttonLayout.setWidth("150px");
        createButton("My items", "");
        createButton("+", "/new");

        if (SecurityUtils.getSubject().hasRole("ADMIN")) {
            createButton("Administration", "admin");
        }

        buttonLayout.addComponent(new Button("Back to browsing",
                new ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        getPage().setLocation(VaadinServlet.getCurrent()
                                .getServletContext().getContextPath());
                    }
                }));

        layout.addComponent(buttonLayout);
    }

    private void createButton(String name, final String view) {
        Button button = new Button(name, new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                getNavigator().navigateTo(view);

            }
        });
        buttonLayout.addComponent(button);
    }

}
