package ua.nure.kardash.Testing.Control.Commands.General;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;

/**
 * Command: Displays the error page. If possible, pulls the last errormessage from the session.
 * <br> Otherwise displays standard error message encoded in the jsp page.
 */
public class DisplayErrorCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Displaying error page with info: ");

		String errormessage = (String) request.getSession().getAttribute("errormessage");
		if(errormessage!=null){
			request.setAttribute("errormessage", errormessage);
			request.getSession().removeAttribute("errormessage");
		}

		request.getRequestDispatcher("/Pages/General/Error.jsp").forward(request, response);
	}

}