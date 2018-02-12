package ua.nure.kardash.Testing.Control.Commands;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * This class represents a command given to a controller.
 * Has the main execute() method that takes request\response info, processes them and redirects it to correct page. <br>
 * Has a shared static logger that logs every command processed by the system.
 * Also has a protected method to "decode" SQL errors into more user-friendly messages to be displayed on the page.
 */
public abstract class Command {
	protected Logger logger;
	protected ResourceBundle rb;
	/**
	 * Processes information from request, redirecting to a correct page and\or executing necessary actions.
	 * @param request - User request
	 * @param response - User response
	 */

	public Command(){
		logger = Logger.getLogger("Command");
	}

	public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	/**
	 * Takes in the error message of an SQLException and returns a string with a clearer, more user-friendly version that will be displayed on the error page.
	 * <br> Some of the returned exceptions are already thrown in a "decoded" form. Some shouldn't happen at all, and thus will be
	 * @param errMessage SQLException message returned by the database
	 * @return
	 */
	protected String decodeSQLError(String errMessage){
		String returnMessage;
		if(errMessage.contains("Error connecting to server")){
			returnMessage=rb.getString("error.serveroffline");
		}
		else if(errMessage.contains("not found")){
			returnMessage=errMessage;
		}
		else if(errMessage.contains("truncation error")){
			returnMessage=rb.getString("error.valuetoolong");
		}
		else if(errMessage.contains("duplicate key value")){
			returnMessage=rb.getString("error.valueunique");
		}
		else{
			returnMessage = errMessage+rb.getString("error.internaldb");
		}

		return returnMessage;
	}

	/**
	 * Gets the resource bundle object from servlet context corresponding to currently selected language.
	 * <br>Used by most execute functions.
	 * @param s - Current session
	 */
	protected void localize(HttpSession session){
		String lang = (String) session.getAttribute("language");

		if(lang==null){
			rb = (ResourceBundle) session.getServletContext().getAttribute("bundle-ru");
			session.setAttribute("language", "ru");
		}
		else{
			rb = (ResourceBundle) session.getServletContext().getAttribute("bundle-"+lang);
		}
	}

}

