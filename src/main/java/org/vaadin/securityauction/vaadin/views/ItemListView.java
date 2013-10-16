package org.vaadin.securityauction.vaadin.views;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.vaadin.securityauction.server.AuctionAdministrationService;
import org.vaadin.securityauction.shared.AuctionItem;
import org.vaadin.securityauction.shared.Bid;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.Reindeer;

@CDIView("")
public class ItemListView extends VerticalSplitPanel implements View,
        ValueChangeListener, ClickListener {

    @Inject
    private AuctionAdministrationService auctionService;

    private Table table;

    private BeanItemContainer<AuctionItem> container;

    private HorizontalLayout layout = new HorizontalLayout();

    private ItemEditForm form;

    private Button saveBtn = new Button("Save", this);

    private Button cancelBtn = new Button("Cancel", this);

    private FieldGroup binder = new FieldGroup();

    private Table bidsTable = new Table();
    private IndexedContainer bidsContainer = new IndexedContainer();

    public ItemListView() {
        table = new Table();
        table.setSizeFull();
        table.setSelectable(true);
        addComponent(table);

        layout.setVisible(false);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        form = new ItemEditForm();
        layout.addComponent(form);
        layout.setExpandRatio(form, 2);

        bidsContainer.addContainerProperty("date", String.class, null);
        bidsContainer.addContainerProperty("amount", String.class, null);
        bidsContainer.addContainerProperty("user", String.class, null);
        bidsContainer.addContainerProperty("type", String.class, null);
        bidsTable.setContainerDataSource(bidsContainer);
        bidsTable.setSizeFull();
        bidsTable.setSortEnabled(false);
        layout.addComponent(bidsTable);
        layout.setExpandRatio(bidsTable, 1);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addComponent(saveBtn);
        buttonLayout.addComponent(cancelBtn);
        buttonLayout.setSpacing(true);
        saveBtn.setStyleName(Reindeer.BUTTON_DEFAULT);
        form.addComponent(buttonLayout);

        addComponent(layout);
        container = new BeanItemContainer<AuctionItem>(AuctionItem.class);
        table.setContainerDataSource(container);
        table.setVisibleColumns(new Object[] { "subject", "description",
                "highestBid" });
        table.addValueChangeListener(this);
        table.setImmediate(true);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        List<AuctionItem> auctionItems = auctionService.getAuctionItems();
        container.removeAllItems();
        container.addAll(auctionItems);
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        if (event.getProperty().getValue() != null) {
            layout.setVisible(true);
            BeanItem<AuctionItem> item = container.getItem(event.getProperty().getValue());
            binder.setItemDataSource(item);
            binder.bindMemberFields(form);
            populateBids(item.getBean());
            
        } else {
            layout.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    private void populateBids(AuctionItem bean) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        
        bidsContainer.removeAllItems();
        if(bean.getBids() != null) {
            for(Bid bid : bean.getBids()) {
                Item item = bidsContainer.addItem(bid);
                item.getItemProperty("date").setValue(dateFormat.format(bid.getBidTime()));
                item.getItemProperty("amount").setValue(moneyFormat.format(bid.getAmount()));
                item.getItemProperty("user").setValue(bid.getBidder().getUsername());
                item.getItemProperty("type").setValue(bid.getBidType().name());
            }
        }
        
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (saveBtn.equals(event.getButton())) {
            try {
                binder.commit();
                Object selectedItemId = table.getValue();
                AuctionItem item = auctionService.saveAuctionItem(container
                        .getItem(selectedItemId).getBean());
                int index = container.indexOfId(selectedItemId);
                container.removeItem(selectedItemId);
                container.addItemAt(index, item);
                table.select(null);
            } catch (CommitException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            binder.discard();
            table.select(null);
        }
    }

}
