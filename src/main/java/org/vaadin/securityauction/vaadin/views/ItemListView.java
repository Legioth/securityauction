package org.vaadin.securityauction.vaadin.views;

import java.util.List;

import javax.inject.Inject;

import org.vaadin.securityauction.server.AuctionAdministrationService;
import org.vaadin.securityauction.shared.AuctionItem;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@CDIView("")
public class ItemListView extends VerticalSplitPanel implements View,
        ValueChangeListener, ClickListener {

    @Inject
    private AuctionAdministrationService auctionService;

    private Table table;

    private BeanItemContainer<AuctionItem> container;

    private VerticalLayout layout = new VerticalLayout();

    private ItemEditForm form;

    private Button saveBtn = new Button("Save", this);

    private Button cancelBtn = new Button("Cancel", this);

    private FieldGroup binder = new FieldGroup();

    public ItemListView() {
        table = new Table();
        table.setSizeFull();
        table.setSelectable(true);
        addComponent(table);

        form = new ItemEditForm();
        layout.addComponent(form);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addComponent(saveBtn);
        buttonLayout.addComponent(cancelBtn);
        layout.addComponent(buttonLayout);

        addComponent(layout);
        container = new BeanItemContainer<AuctionItem>(AuctionItem.class);
        table.setContainerDataSource(container);
        table.setVisibleColumns(new Object[] { "subject", "description" });
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
            Item item = container.getItem(event.getProperty().getValue());
            binder.setItemDataSource(item);
            binder.bindMemberFields(form);
        } else {
            // TODO null selection
        }
    }

    @Override
    public void buttonClick(ClickEvent event) {
        // TODO Auto-generated method stub

    }

}
