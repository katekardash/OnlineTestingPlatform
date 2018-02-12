package ua.nure.kardash.Testing.Control.Commands.Admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;

/**
 * Command: Blocks or unblocks a given user.
 */
public class SetBlockCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		localize(request.getSession());

		int userID = Integer.parseInt(request.getParameter("userID"));
		boolean status = Boolean.parseBoolean(request.getParameter("status"));

		logger.info("Executing setblock command for user ID "+userID);

		try {
			DBManager.instance().updateUserBan(userID, !status);
			response.sendRedirect("/Testing/Users");
		}
		catch (SQLException e) {
			logger.error("Block setting failed: "+e.getMessage());
			request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/Admin/Users.jsp").forward(request, response);
		}

	}

}
