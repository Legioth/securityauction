package org.vaadin.securityauction.vaadin.views;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.vaadin.securityauction.server.AuctionAdministrationService;
import org.vaadin.securityauction.shared.AuctionItem;
import org.vaadin.securityauction.shared.Bid;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;

@CDIView("")
public class ItemListView extends AbstractCRUDView<AuctionItem> {

    @Inject
    private AuctionAdministrationService auctionService;

    private IndexedContainer bidsContainer = new IndexedContainer();
    private Table bidsTable = new Table();

    public ItemListView() {
        bidsContainer.addContainerProperty("date", String.class, null);
        bidsContainer.addContainerProperty("amount", String.class, null);
        bidsContainer.addContainerProperty("user", String.class, null);
        bidsContainer.addContainerProperty("type", String.class, null);
        bidsTable.setContainerDataSource(bidsContainer);
        bidsTable.setSizeFull();
        bidsTable.setSortEnabled(false);
        layout.addComponent(bidsTable);
        layout.setExpandRatio(bidsTable, 1);
        layout.setExpandRatio(form, 2);

    }

    @Override
    protected void showInForm(BeanItem<AuctionItem> item) {
        super.showInForm(item);
        if (item != null) {
            populateBids(item.getBean());
        }
    }

    @SuppressWarnings("unchecked")
    private void populateBids(AuctionItem bean) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,
                Locale.US);
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);

        bidsContainer.removeAllItems();
        if (bean.getBids() != null) {
            for (Bid bid : bean.getBids()) {
                Item item = bidsContainer.addItem(bid);
                item.getItemProperty("date").setValue(
                        dateFormat.format(bid.getBidTime()));
                item.getItemProperty("amount").setValue(
                        moneyFormat.format(bid.getAmount()));
                item.getItemProperty("user").setValue(
                        bid.getBidder().getUsername());
                item.getItemProperty("type").setValue(bid.getBidType().name());
            }
        }

    }

    @Override
    protected Class<AuctionItem> getBeanType() {
        return AuctionItem.class;
    }

    @Override
    protected Object[] getVisibleColumns() {
        return new Object[] { "subject", "description", "highestBid",
                "closeDate" };
    }

    @Override
    protected Collection<AuctionItem> getBeans() {
        return auctionService.getAuctionItems();
    }

    @Override
    protected AuctionItem saveBean(AuctionItem bean) {
        return auctionService.saveAuctionItem(bean);
    }

    @Override
    protected Layout createForm() {
        return new ItemEditForm();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        super.enter(event);
        if ("new".equals(event.getParameters())) {
            int userId = (Integer) SecurityUtils.getSubject().getSession()
                    .getAttribute("userID");
            AuctionItem bean = new AuctionItem();
            bean.setOwner(userId);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 7);
            bean.setCloseDate(cal.getTime());
            BeanItem<AuctionItem> item = new BeanItem<AuctionItem>(bean);
            showInForm(item);
        }
    }

}
