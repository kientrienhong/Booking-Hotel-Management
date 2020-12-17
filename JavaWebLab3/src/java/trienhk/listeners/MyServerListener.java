/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.listeners;

import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Treater
 */
public class MyServerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext(); 
        ResourceBundle bundle = ResourceBundle.getBundle("trienhk.properties.mapping");
        context.setAttribute("SITE_MAP", bundle);  
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
