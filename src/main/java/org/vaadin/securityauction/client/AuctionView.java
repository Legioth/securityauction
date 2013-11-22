package org.vaadin.securityauction.client;

import org.vaadin.securityauction.shared.AuctionItem;
import org.vaadin.securityauction.shared.AuctionServiceAsync;
import org.vaadin.securityauction.shared.Bid;
import org.vaadin.securityauction.shared.BidType;
import org.vaadin.securityauction.shared.LoginRequiredException;
import org.vaadin.securityauction.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.OListElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AuctionView extends Composite {

    private static AuctionViewUiBinder uiBinder = GWT
            .create(AuctionViewUiBinder.class);

    interface AuctionViewUiBinder extends UiBinder<Widget, AuctionView> {
    }

    public interface AuctionStyle extends CssResource {
        public String hidden();
    }

    @UiField
    Button button;

    @UiField
    AuctionStyle style;

    @UiField
    HeadingElement subjectHeader;

    @UiField
    DivElement contentHolder;

    @UiField
    SpanElement descriptionSpan;

    @UiField
    DivElement bidBox;

    @UiField
    ListBox typeBox;

    @UiField
    TextBox amountBox;

    @UiField
    OListElement bidList;

    private int auctionId;

    private SecurityAuction auction;

    public AuctionView(SecurityAuction auction, int auctionId) {
        setStyleName("auction");
        this.auction = auction;
        this.auctionId = auctionId;
        initWidget(uiBinder.createAndBindUi(this));

        User user = auction.getCurrentUser();
        if (user == null) {
            bidBox.getStyle().setDisplay(Display.NONE);
        } else {
            BidType[] values = BidType.values();
            for (BidType bidType : values) {
                if (bidType.isAllowedWithRoles(user.getRoles())) {
                    typeBox.addItem(bidType.toString(), bidType.name());
                }
            }
            typeBox.setSelectedIndex(0);
        }

        AuctionServiceAsync service = AuctionServiceAsync.Util.getInstance();
        service.getAuctionItem(auctionId, new AsyncCallback<AuctionItem>() {
            @Override
            public void onSuccess(AuctionItem result) {
                updateFields(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        });
    }

    protected void updateFields(AuctionItem result) {
        if (result != null) {
            subjectHeader.setInnerText(result.getSubject());
            descriptionSpan.setInnerText(result.getDescription());
            contentHolder.removeClassName(style.hidden());

            for (int i = 0; i < bidList.getChildCount(); i++) {
                bidList.getChild(0).removeFromParent();
            }

            for (Bid bid : result.getBids()) {
                LIElement li = Document.get().createLIElement();
                li.setInnerText(bid.getAmount() + "€ - " + bid.getBidTime());
                bidList.appendChild(li);
            }

            if (bidList.getChildCount() == 0) {
                LIElement li = Document.get().createLIElement();
                li.setInnerText("No bids yet");
                bidList.appendChild(li);
            }

        } else {
            subjectHeader.setInnerText("Auction item not found");
        }
    }

    @UiHandler("typeBox")
    void onTypeChange(ChangeEvent e) {
        BidType bidType = getSelectedBidType();

        if (bidType == BidType.POSTPONED) {
            Window.alert("Sorry, this is not implemented right now");
            typeBox.setSelectedIndex(0);
        }
    }

    private BidType getSelectedBidType() {
        return BidType.valueOf(typeBox.getValue(typeBox.getSelectedIndex()));
    }

    @UiHandler("button")
    void onClick(ClickEvent e) {
        BidType bidType = getSelectedBidType();
        float amount = Float.parseFloat(amountBox.getValue());

        AuctionServiceAsync service = AuctionServiceAsync.Util.getInstance();
        service.bid(auctionId, amount, bidType, null,
                new AsyncCallback<AuctionItem>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        if (caught instanceof LoginRequiredException) {
                            Window.alert("You must be logged in to make a bet.");
                        } else {
                            Window.alert("Bid failed: " + caught.getMessage());
                        }
                    }

                    @Override
                    public void onSuccess(AuctionItem updatedItem) {
                        if (updatedItem == null) {
                            Window.alert("There was an undefined problem processing your bid");
                        } else {
                            Window.alert("Thank you for bidding");
                            updateFields(updatedItem);
                        }
                    }
                });

    }

}
