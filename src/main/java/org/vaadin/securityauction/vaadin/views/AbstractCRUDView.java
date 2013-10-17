package org.vaadin.securityauction.vaadin.views;

import java.util.Collection;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.Reindeer;

public abstract class AbstractCRUDView<BEANTYPE> extends VerticalSplitPanel implements
        View, ValueChangeListener, ClickListener {

    private Table table;

    protected BeanItemContainer<BEANTYPE> container;

    protected HorizontalLayout layout = new HorizontalLayout();

    protected Layout form;

    private Button saveBtn = new Button("Save", this);

    private Button cancelBtn = new Button("Cancel", this);

    private FieldGroup binder = new FieldGroup();
    
    public AbstractCRUDView() {
        table = new Table();
        table.setSizeFull();
        table.setSelectable(true);
        addComponent(table);

        layout.setVisible(false);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        form = createForm();
        layout.addComponent(form);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addComponent(saveBtn);
        buttonLayout.addComponent(cancelBtn);
        buttonLayout.setSpacing(true);
        saveBtn.setStyleName(Reindeer.BUTTON_DEFAULT);
        form.addComponent(buttonLayout);

        addComponent(layout);
        container = new BeanItemContainer<BEANTYPE>(getBeanType());
        table.setContainerDataSource(container);
        table.setVisibleColumns(getVisibleColumns());
        table.addValueChangeListener(this);
        table.setImmediate(true);
    }
    
    protected abstract Class<BEANTYPE> getBeanType();
    
    protected abstract Object[] getVisibleColumns();
    
    protected abstract Collection<BEANTYPE> getBeans();
    
    protected abstract Layout createForm();

    @Override
    public void enter(ViewChangeEvent event) {
        Collection<BEANTYPE> beans = getBeans();
        container.removeAllItems();
        container.addAll(beans);
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        if (event.getProperty().getValue() != null) {
            layout.setVisible(true);
            BeanItem<BEANTYPE> item = container.getItem(event.getProperty().getValue());
            binder.setItemDataSource(item);
            binder.bindMemberFields(form);
        } else {
            layout.setVisible(false);
        }
    }
    
    protected abstract BEANTYPE saveBean(BEANTYPE bean);

    @Override
    public void buttonClick(ClickEvent event) {
        if (saveBtn.equals(event.getButton())) {
            try {
                binder.commit();
                Object selectedItemId = table.getValue();
                BEANTYPE item = saveBean(container
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
