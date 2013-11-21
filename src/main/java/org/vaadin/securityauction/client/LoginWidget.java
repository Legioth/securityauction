package org.vaadin.securityauction.client;

import org.vaadin.securityauction.shared.AuctionServiceAsync;
import org.vaadin.securityauction.shared.User;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class LoginWidget extends Composite {
    SimplePanel content = new SimplePanel();
    private SecurityAuction auction;
    private Label errorText;
    private FlowPanel layout;

    public LoginWidget(SecurityAuction auction) {
        this.auction = auction;
        initWidget(content);
    }

    private void showLoginForm() {
        layout = new FlowPanel();

        errorText = new Label();

        final TextBox username = new TextBox();
        final PasswordTextBox password = new PasswordTextBox();
        Button loginButton = new Button("Login", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                AuctionServiceAsync service = AuctionServiceAsync.Util
                        .getInstance();
                service.authenticate(username.getText(), password.getText(),
                        new AsyncCallback<User>() {
                            @Override
                            public void onSuccess(User user) {
                                if (user == null) {
                                    showErrorText("Invalid credentials");
                                } else {
                                    setLoggedIn(user);
                                }
                            }

                            @Override
                            public void onFailure(Throwable caught) {
                                showErrorText(caught.getMessage());
                            }

                        });
            }
        });

        layout.add(username);
        layout.add(password);
        layout.add(loginButton);

        content.setWidget(layout);
    }

    private void showErrorText(String string) {
        errorText.setText(string);
        if (errorText.getParent() == null) {
            layout.add(errorText);
        }
    }

    private void setLoggedIn(User user) {
        auction.setCurrentUser(user);

        updateUI(user);
    }

    public void updateUI(User user) {
        if (user == null) {
            showLoginForm();
        } else {
            FlowPanel layout = new FlowPanel();

            layout.add(new Label("Logged in as " + user.getUsername()));
            layout.add(new Button("Log out", new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    AuctionServiceAsync service = AuctionServiceAsync.Util
                            .getInstance();
                    service.logout(new AsyncCallback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            auction.setCurrentUser(null);
                            showLoginForm();
                        }

                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                    });
                }
            }));
            content.setWidget(layout);
        }

    }
}
