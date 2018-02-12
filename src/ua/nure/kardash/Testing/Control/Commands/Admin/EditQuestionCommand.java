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
public class EditQuestionCommand extends Command{
	private String address;

	public EditQuestionCommand(String address) {
		this.address=address;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isEdit = request.getParameter("edit");
		String text = request.getParameter("text");
		int questionID = Integer.parseInt(request.getParameter("questionID"));

		logger.info("Attempting to edit question "+text);

		try {
			if(isEdit != null){
				DBManager.instance().updateQuestionText(questionID, text);
			}
			else{
				DBManager.instance().removeQuestion(questionID);
			}
			logger.info("updated test "+text);
			response.sendRedirect("/Testing/Tests/"+address+"/edit");
		}
		catch (SQLException e) {
			localize(request.getSession());
			logger.error("Question update failed: "+e.getMessage());
			request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/Admin/TestEdit.jsp").forward(request, response);
		}

	}

}
