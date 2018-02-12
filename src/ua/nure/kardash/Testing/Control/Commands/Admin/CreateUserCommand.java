package ua.nure.kardash.Testing.Control.Commands.Admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;

/**
 * Command: Attempts to create user. Capable of creating admin users.<br>Requires admin auth level.
 */
public class CreateUserCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		String role = request.getParameter("role");
		logger.info("Executing a create user command for user "+login);

		try {
			DBManager.instance().registerUser(login, pass, name, role);
			logger.info("Created user "+login);
			request.setAttribute("errormessage", null);
			request.getRequestDispatcher("/Pages/Admin/Users.jsp").forward(request, response);
		}
		catch (SQLException e) {
			localize(request.getSession());
			logger.error("User creation failed: "+e.getMessage());
			request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/Admin/Users.jsp").forward(request, response);
		}

	}

}
