package ua.nure.kardash.Testing.Control.Commands.General;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;

/**
 * Command: Logs user out by removing cookies. Should not raise any <br> Requires any non-zero authorization level.
 */
public class LogoutCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Logging out user: "+request.getSession().getAttribute("login"));

		request.getSession().invalidate();
		response.sendRedirect("/Testing/Index");
	}

}