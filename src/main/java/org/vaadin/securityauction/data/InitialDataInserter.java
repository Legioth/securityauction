package org.vaadin.securityauction.data;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitialDataInserter implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("TESTING TESTING");
    }

}
