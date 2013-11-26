package org.vaadin.securityauction.client;

import org.vaadin.securityauction.shared.AuctionServiceAsync;
import org.vaadin.securityauction.shared.User;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
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

    private Button loginButton;

    public LoginWidget(SecurityAuction auction) {
        this.auction = auction;
        initWidget(content);
        setStyleName("login");
        
        AuctionServiceAsync service = AuctionServiceAsync.Util
                .getInstance();
        service.getCurrentUser(new AsyncCallback<User>() {
            
            @Override
            public void onSuccess(User result) {
                if(result == null) {
                    showLoginForm();
                } else {
                    setLoggedIn(result);
                }
            }
            
            @Override
            public void onFailure(Throwable caught) {
                showLoginForm();
                showErrorText(caught.getMessage());
            }
        });

        addDomHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER
                        && loginButton != null && loginButton.isAttached()) {
                    loginButton.click();
                }
            }
        }, KeyDownEvent.getType());
    }

    private void showLoginForm() {
        layout = new FlowPanel();

        errorText = new Label();

        final TextBox username = new TextBox();
        final PasswordTextBox password = new PasswordTextBox();
        loginButton = new Button("Login", new ClickHandler() {
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

            loginButton = null;

            layout.add(new Label("Logged in as " + user.getUsername()));
            layout.add(new Anchor("Edit your items", "./edit"));
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
