package ua.nure.kardash.Testing.Control.Commands.General;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;

/**
 * Command: Displays the login page. <br> Requires no authorization level.
 */
public class DisplayLoginCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Displaying login page.");
		request.getRequestDispatcher("/Pages/General/Login.jsp").forward(request, response);
	}

}
