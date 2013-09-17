package org.vaadin.securityauction.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
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
    private Widget loginWidget;

    public void onModuleLoad() {
        bulidBaseUI();

        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            public void onValueChange(ValueChangeEvent<String> event) {
                String value = event.getValue();
                if ("login".equals(value)) {
                    showLoginView();
                } else if (value.startsWith("auction/")) {
                    int auctionId = Integer.parseInt(value.substring(0,
                            "auction/".length()));
                    showAuctionView(auctionId);
                } else {
                    showMainView();
                }
            }
        });
        History.fireCurrentHistoryState();
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

    private Widget getLoginWidget() {
        if (loginWidget == null) {
            loginWidget = new LoginWidget();

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
        setCurrentView(mainView);
    }

    protected void showAuctionView(int auctionId) {
        // TODO Auto-generated method stub

    }

    protected void showLoginView() {
        // TODO Auto-generated method stub

    }
}
