package ua.nure.kardash.Testing.Control.Commands.General;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;

/**
 * Command: Redirects to error page. Used when user tries to access a page that does not exist or is outside of user's auth level.
 */
public class RedirectToErrorCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/Testing/Error");
	}

}
