package ua.nure.kardash.Testing.Web;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listens for the initialization of the servlet and does some preemptive configuration, logging, initializing resource bundles and so on.
 */
public class InitListener implements ServletContextListener {

	private static final String BUNDLE_NAME = "resources/resources";

	@Override
	public void contextDestroyed(ServletContextEvent e) {}


	@Override
	public void contextInitialized(ServletContextEvent e) {
		ServletContext sc = e.getServletContext();
		sc.setAttribute("bundle-en", ResourceBundle.getBundle(BUNDLE_NAME, new Locale("en")));
		sc.setAttribute("bundle-ru", ResourceBundle.getBundle(BUNDLE_NAME, new Locale("ru")));
	}

}
