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

/**
 * Command. Displays the test description, general info and
 */
public class DisplayTestIntroCommand extends Command{
	String address;

	public DisplayTestIntroCommand(String testAddress) {
		address=testAddress;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Executing command to display test at /"+address);

		//If this test is already being taken - redirects to questions page
		HttpSession sesh = request.getSession();
		if(sesh.getAttribute(address)!=null){
			response.sendRedirect("/Testing/Tests/"+address+"/questions");
			return;
		}

		Test test=null;

		try {
			test = DBManager.instance().getTestInfo(address);
		} catch (SQLException e) {
			logger.error("Test recovery failed: "+e.getMessage());
		}

		//If the test has been acquired successfully
		if(test!=null){
			request.setAttribute("test", test);
			request.getRequestDispatcher("/Pages/Student/TestInfo.jsp").forward(request, response);
		}
		else{
			response.sendRedirect("/Testing/Error");
		}


	}

}
