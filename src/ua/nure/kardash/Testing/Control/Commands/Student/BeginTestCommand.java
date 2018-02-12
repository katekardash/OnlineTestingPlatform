package ua.nure.kardash.Testing.Control.Commands.Student;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;
import ua.nure.kardash.Testing.DB.Entity.Test;
import ua.nure.kardash.Testing.DB.Entity.TestHandler;

/**
 * Command. Begins the test, loading it into session, starting the countdown, then redirects to
 */
public class BeginTestCommand extends Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Gets the test selected from the intro page
		HttpSession sesh = request.getSession();
		Test test=null;

		try {
			test=DBManager.instance().getTestInfo(request.getParameter("testaddress"));
			test.setQuestions(DBManager.instance().getQuestions(test.getId()));
		} catch (SQLException e) {
			logger.error("Test or question retrieval failed.");
			sesh.setAttribute("errormessage", decodeSQLError(e.getMessage()));
		}

		if(test!=null){
			sesh.setAttribute(test.getAddress(), test);
			sesh.setAttribute(test.getAddress()+"-handler", new TestHandler(test));

			//Setting countdown time; current time + 60000 miliseconds (1 minute) * minutes given
			sesh.setAttribute(test.getAddress()+"-timeout", System.currentTimeMillis()+60000*test.getTime());

			response.sendRedirect(test.getAddress()+"/questions");

		}
		else{
			response.sendRedirect("/Testing/Error");
		}

	}
}
