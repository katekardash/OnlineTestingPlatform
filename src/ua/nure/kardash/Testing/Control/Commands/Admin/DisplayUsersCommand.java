package ua.nure.kardash.Testing.Control.Commands.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;
import ua.nure.kardash.Testing.DB.Entity.AccountInfo;

/**
 * Command. Displays user list and the form that allows editing of users.
 */
public class DisplayUsersCommand extends Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Attempting to retrieve list of all users");

		List<AccountInfo> users = null;
		try {
			users = DBManager.instance().getAllUsers();
		} catch (SQLException e) {
			logger.error("User retrieval failed: "+e.getMessage());
		}

		if(users!=null){
			request.setAttribute("users", users);
			request.getRequestDispatcher("/Pages/Admin/Users.jsp").forward(request, response);
		}
		else{
			localize(request.getSession());
			request.getSession().setAttribute("errormessage", rb.getString("error.usersfail"));
			response.sendRedirect("/Testing/Error");
		}
	}
}