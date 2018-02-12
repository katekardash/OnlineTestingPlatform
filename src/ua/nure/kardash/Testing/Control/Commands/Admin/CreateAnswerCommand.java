package ua.nure.kardash.Testing.Control.Commands.Admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;

/**
 * Command: Attempts to create an answer to a given question.
 */
public class CreateAnswerCommand extends Command{
	private String address;

	public CreateAnswerCommand(String address) {
		this.address=address;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("text");
		boolean correct = request.getParameter("correct")!=null;
		int questionID = Integer.parseInt(request.getParameter("questionID"));

		logger.info("Attempting to create answer "+text);

		try {
			DBManager.instance().addAnswer(questionID, text, correct);
			logger.info("Created answer "+text);
			response.sendRedirect("/Testing/Tests/"+address+"/edit");
		}
		catch (SQLException e) {
			localize(request.getSession());
			logger.error("Answer creation failed: "+e.getMessage());
			request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/Admin/TestEdit.jsp").forward(request, response);
		}

	}

}
