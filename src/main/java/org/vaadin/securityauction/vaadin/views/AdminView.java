package org.vaadin.securityauction.vaadin.views;

import java.util.Collection;

import javax.inject.Inject;

import org.vaadin.securityauction.server.UserService;
import org.vaadin.securityauction.shared.User;

import com.vaadin.cdi.CDIView;
import com.vaadin.ui.Layout;

@CDIView("admin")
public class AdminView extends AbstractCRUDView<User> {

    @Inject
    private UserService userService;

    @Override
    protected Class<User> getBeanType() {
        return User.class;
    }

    @Override
    protected Object[] getVisibleColumns() {
        return new Object[] { "username" };
    }

    @Override
    protected Collection<User> getBeans() {
        return userService.getUsers();
    }

    @Override
    protected Layout createForm() {
        return new UserForm();
    }

    @Override
    protected User saveBean(User user) {
        return userService.saveUser(user);
    }

}
