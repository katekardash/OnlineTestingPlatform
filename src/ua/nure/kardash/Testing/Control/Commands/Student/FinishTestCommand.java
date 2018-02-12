package ua.nure.kardash.Testing.Control.Commands.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;
import ua.nure.kardash.Testing.DB.Entity.Test;
import ua.nure.kardash.Testing.DB.Entity.TestHandler;
import ua.nure.kardash.Testing.DB.Entity.TestResult;

/**
 * Command. Finishes the test, gets the final results then clears all of the session variables.
 */
public class FinishTestCommand extends Command {
	private String address;

	public FinishTestCommand(String address) {
		this.address=address;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesh = request.getSession();

		Test test = (Test) sesh.getAttribute(address);
		TestHandler testh = (TestHandler) sesh.getAttribute(address+"-handler");
		int userID = (int) sesh.getAttribute("userID");
		int timetaken = Integer.parseInt(request.getParameter("timetaken"));
		int grade = testh.getTestGrade();
		int correctAnswers = testh.getCorrectAnswers();
		Timestamp now = new Timestamp(System.currentTimeMillis());

		try {
			DBManager.instance().addJournalEntry(test.getId(), userID, timetaken, now, grade);
		}

		catch (SQLException e) {
			logger.error("Failed to submit test result values: "+e.getMessage());
			request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/General/Error.jsp").forward(request, response);
			return;
		}

		//Clears session vars that we will no longer need so that GC can clean them up
		sesh.setAttribute(address+"-result", new TestResult(0, test.getName(), userID, timetaken, now, grade,correctAnswers, test.getQuestionCount()));
		sesh.removeAttribute(address);
		sesh.removeAttribute(address+"-handler");
		sesh.removeAttribute(address+"-timeout");

		response.sendRedirect("/Testing/Tests/"+address+"/results");
	}
}
