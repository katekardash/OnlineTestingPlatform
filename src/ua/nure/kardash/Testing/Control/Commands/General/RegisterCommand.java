package ua.nure.kardash.Testing.Control.Commands.General;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;

/**
 * Command: Attempts to register the user. If unsuccessful, displays the error. <br> Requires no authorization level.
 */
public class RegisterCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		localize(request.getSession());

		String login = request.getParameter("login");
		logger.info("Executing a register command for user "+login);
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");

		try {
			DBManager.instance().registerUser(login, pass, name, "student");

			HttpSession sesh = request.getSession();
			sesh.setAttribute("login", login);
			sesh.setAttribute("name", name);
			sesh.setAttribute("role", "student");
			sesh.setAttribute("language", "en");
			sesh.setAttribute("theme", "light");

			logger.info("Registered and logged in "+login);
			request.setAttribute("errormessage", null);
			response.sendRedirect("/Testing/Index");
		}
		catch (SQLException e) {
			logger.error("User failed to register: "+e.getMessage());
			request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			request.getRequestDispatcher("/Pages/General/Register.jsp").forward(request, response);
		}

	}

}
