package ua.nure.kardash.Testing.Control.Commands.Admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;

/**
 * Command: Attempts to delete the given test
 */
public class DeleteTestCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int testID = Integer.parseInt(request.getParameter("testID"));
		logger.info("Deleting test with ID "+testID);

		try {
			DBManager.instance().removeTest(testID);
			logger.info("Deletion successful.");
			response.sendRedirect("/Testing/Tests");
		}
		catch (SQLException e) {
			localize(request.getSession());
			logger.error("Failed to delete test: "+e.getMessage());
			request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/General/Tests.jsp").forward(request, response);
		}

	}

}
