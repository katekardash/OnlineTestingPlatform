package ua.nure.kardash.Testing.Control.Commands.Admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;
import ua.nure.kardash.Testing.DB.Entity.Test;

/**
 * Command: Displays the edit test page.
 */
public class DisplayTestEditCommand extends Command{
	private String address;

	public DisplayTestEditCommand(String address) {
		this.address = address;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Test test = null;

		try {
			test = DBManager.instance().getTestInfo(address);
			test.setQuestions(DBManager.instance().getQuestions(test.getId()));
		} catch (SQLException e) {
			logger.error("Test or question recovery failed: "+e.getMessage());
			request.getSession().setAttribute("errormessage", decodeSQLError(e.getMessage()));
		}

		//If the test has been acquired successfully
		if(test!=null){
			request.setAttribute("test", test);
			request.getRequestDispatcher("/Pages/Admin/TestEdit.jsp").forward(request, response);
		}
		else{
			response.sendRedirect("/Testing/Error");
		}

	}

}