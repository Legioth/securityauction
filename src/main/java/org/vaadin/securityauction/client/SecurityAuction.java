package org.vaadin.securityauction.client;

import org.vaadin.securityauction.shared.AuctionServiceAsync;
import org.vaadin.securityauction.shared.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SecurityAuction implements EntryPoint {
    private DockLayoutPanel dock;
    private Widget currentView;
    private LoginWidget loginWidget;
    private User user;

    @Override
    public void onModuleLoad() {
        AuctionServiceAsync service = AuctionServiceAsync.Util.getInstance();
        service.getCurrentUser(new AsyncCallback<User>() {

            @Override
            public void onSuccess(User result) {
                user = result;
                bulidBaseUI();
                getLoginWidget().updateUI(user);

                History.addValueChangeHandler(new ValueChangeHandler<String>() {
                    @Override
                    public void onValueChange(ValueChangeEvent<String> event) {
                        String value = event.getValue();
                        if (value.startsWith("auction/")) {
                            int auctionId = Integer.parseInt(value
                                    .substring("auction/".length()));
                            showAuctionView(auctionId);
                        } else {
                            showMainView();
                        }
                    }
                });
                History.fireCurrentHistoryState();
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Could not check login status");
            }
        });
    }

    private void bulidBaseUI() {
        RootLayoutPanel rootPanel = RootLayoutPanel.get();
        dock = new DockLayoutPanel(Unit.PX);

        DockLayoutPanel header = new DockLayoutPanel(Unit.PX);
        header.addEast(getLoginWidget(), 300);
        header.add(new HTML("Header"));

        dock.addNorth(header, 100);
        rootPanel.add(dock);

        setCurrentView(new HTML("Content"));
    }

    private LoginWidget getLoginWidget() {
        if (loginWidget == null) {
            loginWidget = new LoginWidget(this);

        }

        return loginWidget;
    }

    private void setCurrentView(Widget view) {
        if (currentView != null) {
            dock.remove(currentView);
        }

        if (view != null) {
            dock.add(view);
        }

        currentView = view;
    }

    protected void showMainView() {
        MainView mainView = new MainView();
        History.newItem("", false);
        setCurrentView(mainView);
    }

    protected void showAuctionView(int auctionId) {
        setCurrentView(new AuctionView(this, auctionId));
    }

    public void setCurrentUser(User user) {
        this.user = user;
        showMainView();
    }

    public User getCurrentUser() {
        return user;
    }
}
