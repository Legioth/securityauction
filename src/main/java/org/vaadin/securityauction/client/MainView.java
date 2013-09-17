package org.vaadin.securityauction.client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.vaadin.securityauction.shared.AuctionItem;
import org.vaadin.securityauction.shared.AuctionServiceAsync;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

public class MainView extends Composite {
    public MainView() {
        VerticalPanel panel = new VerticalPanel();

        CellList<AuctionItem> cellList = new CellList<AuctionItem>(
                new AbstractCell<AuctionItem>() {
                    private final HashSet<String> consumedEvents = new HashSet<String>(
                            Arrays.asList(BrowserEvents.DBLCLICK));

                    @Override
                    public void render(Cell.Context context, AuctionItem value,
                            SafeHtmlBuilder sb) {
                        sb.appendHtmlConstant("<b>" + value.getSubject()
                                + "</b>");
                    }

                    @Override
                    public void onBrowserEvent(Cell.Context context,
                            Element parent, AuctionItem value,
                            NativeEvent event,
                            ValueUpdater<AuctionItem> valueUpdater) {
                        if (event.getType().equals(BrowserEvents.DBLCLICK)) {
                            History.newItem("auction/" + value.getId());
                        } else {
                            GWT.log("Unexpected event: " + event.getType());
                        }
                    }

                    @Override
                    public Set<String> getConsumedEvents() {
                        return consumedEvents;
                    }
                });
        cellList.setPageSize(10);

        AsyncDataProvider<AuctionItem> dataProvider = new AsyncDataProvider<AuctionItem>() {
            @Override
            protected void onRangeChanged(final HasData<AuctionItem> display) {
                AuctionServiceAsync service = AuctionServiceAsync.Util
                        .getInstance();
                final Range range = display.getVisibleRange();
                service.getAuctionItems(range.getStart(), range.getLength(),
                        new AsyncCallback<List<AuctionItem>>() {
                            public void onSuccess(List<AuctionItem> result) {
                                updateRowData(range.getStart(), result);
                                if (result.size() < range.getLength()) {
                                    updateRowCount(
                                            range.getStart() + result.size(),
                                            true);
                                }
                            }

                            public void onFailure(Throwable caught) {
                                Window.alert(caught.getMessage());
                            }
                        });
            }
        };

        dataProvider.addDataDisplay(cellList);

        SimplePager pager = new SimplePager();
        pager.setDisplay(cellList);

        panel.add(cellList);
        panel.add(pager);

        initWidget(panel);
    }
}
