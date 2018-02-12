package ua.nure.kardash.Testing.Control.Commands.Admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;

/**
 * Command: Attempts to edit the given test.
 */
public class EditTestInfoCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int testID = Integer.parseInt(request.getParameter("testID"));
		String name = request.getParameter("name");
		String descr = request.getParameter("description");
		String subj = request.getParameter("subject");
		int difficulty = Integer.parseInt(request.getParameter("difficulty"));
		int time = Integer.parseInt(request.getParameter("time"));

		logger.info("Attempting to update test "+name);

		try {
			DBManager.instance().updateTestInfo(testID, name, descr, subj, difficulty, time);
			logger.info("Updated test "+name);
			request.getSession().removeAttribute("errormessage");
			String address=name.replace(" ", "").toLowerCase();
			response.sendRedirect("/Testing/Tests/"+address+"/edit");
		}

		catch (SQLException e) {
			localize(request.getSession());
			logger.error("Test update failed: "+e.getMessage());
			request.getSession().setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/Admin/TestEdit.jsp").forward(request, response);
		}

	}

}
