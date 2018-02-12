package ua.nure.kardash.Testing.Control.Commands.Admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;

/**
 * Command: Attempts to create a test. Newly made test has no asociated answers or questions.<br>Requires admin auth level.
 */
public class CreateTestCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String descr = request.getParameter("description");
		String subj = request.getParameter("subject");
		int difficulty = Integer.parseInt(request.getParameter("difficulty"));
		int time = Integer.parseInt(request.getParameter("time"));

		logger.info("Attempting to create test "+name);

		try {
			DBManager.instance().addTestStub(name, descr, subj, difficulty, time);
			logger.info("Created test "+name);
			response.sendRedirect("/Testing/Tests");
		}
		catch (SQLException e) {
			localize(request.getSession());
			logger.error("Test creation failed: "+e.getMessage());
			request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/Admin/Users.jsp").forward(request, response);
		}

	}

}
