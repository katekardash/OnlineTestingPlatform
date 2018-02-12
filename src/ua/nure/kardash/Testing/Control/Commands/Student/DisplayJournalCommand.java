package ua.nure.kardash.Testing.Control.Commands.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;
import ua.nure.kardash.Testing.DB.Entity.TestResult;

public class DisplayJournalCommand extends Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Attempting to retrieve list of test results for display");
		HttpSession sesh = request.getSession();
		int userID = (int) sesh.getAttribute("userID");

		List<TestResult> testsRes = null;
		try {
			testsRes = DBManager.instance().getTestResults(userID);
		} catch (SQLException e) {
			logger.error("Journal retrieval failed: "+e.getMessage());
		}

		if(testsRes!=null){
			request.setAttribute("testResults", testsRes);
			request.getRequestDispatcher("/Pages/Student/Journal.jsp").forward(request, response);
		}
		else{
			localize(request.getSession());
			request.getSession().setAttribute("errormessage", rb.getString("error.journalfail"));
			response.sendRedirect("/Testing/Error");
		}
	}
}