package ua.nure.kardash.Testing.Control.Commands.Admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;

/**
 * Command: Attempts to update an answer to a given question.
 */
public class EditAnswerCommand extends Command{
	private String address;

	public EditAnswerCommand(String address) {
		this.address=address;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isEdit = request.getParameter("edit");
		String text = request.getParameter("text");
		boolean correct = request.getParameter("correct")!=null;
		int answerID = Integer.parseInt(request.getParameter("answerID"));

		logger.info("Attempting to update answer "+text);

		try {
			if(isEdit != null){
				DBManager.instance().updateAnswer(answerID, text, correct);
			}
			else{
				DBManager.instance().removeAnswer(answerID);
			}

			logger.info("Updated answer "+text);
			response.sendRedirect("/Testing/Tests/"+address+"/edit");
		}
		catch (SQLException e) {
			localize(request.getSession());
			logger.error("Answer update failed: "+e.getMessage());
			request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/Admin/TestEdit.jsp").forward(request, response);
		}

	}

}
