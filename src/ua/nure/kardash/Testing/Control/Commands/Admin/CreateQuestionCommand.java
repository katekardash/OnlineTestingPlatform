package ua.nure.kardash.Testing.Control.Commands.Admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;

/**
 * Command: Attempts to create a question with no answers yet.
 */
public class CreateQuestionCommand extends Command{
	private String address;

	public CreateQuestionCommand(String address) {
		this.address=address;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("text");
		int testID = Integer.parseInt(request.getParameter("testID"));

		logger.info("Attempting to create question "+text);

		try {
			DBManager.instance().addQuestion(testID, text);
			logger.info("Created test "+text);
			response.sendRedirect("/Testing/Tests/"+address+"/edit");
		}
		catch (SQLException e) {
			localize(request.getSession());
			logger.error("Question creation failed: "+e.getMessage());
			request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/Admin/TestEdit.jsp").forward(request, response);
		}

	}

}
