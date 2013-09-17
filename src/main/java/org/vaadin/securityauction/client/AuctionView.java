package org.vaadin.securityauction.client;

import org.vaadin.securityauction.shared.AuctionItem;
import org.vaadin.securityauction.shared.AuctionServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
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

    private int auctionId;

    public AuctionView(int auctionId) {
        this.auctionId = auctionId;
        initWidget(uiBinder.createAndBindUi(this));

        AuctionServiceAsync service = AuctionServiceAsync.Util.getInstance();
        service.getAuctionItem(auctionId, new AsyncCallback<AuctionItem>() {
            public void onSuccess(AuctionItem result) {
                updateFields(result);
            }

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
        } else {
            subjectHeader.setInnerText("Auction item not found");
        }
    }

    @UiHandler("button")
    void onClick(ClickEvent e) {
        Window.alert("Hello!");
    }

}
