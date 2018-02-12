package ua.nure.kardash.Testing.Control.Commands.General;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;

/**
 * Displays the index page. Depending on user authorization - none, student, or admin - different page is returned.
 * <br> Guest version has site description and register\login buttons.
 * <br> Student version has brief summary of tests and some
 * <br> Guest version has site description and register\login buttons.
 *
 */
public class DisplayIndexCommand extends Command{
	private int level;
	public DisplayIndexCommand(int level) {
		super();
		this.level=level;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		localize(request.getSession());
		String logMessage = "Displaying index page for user with access level: ";
		switch (level){
			case 0:
				request.getRequestDispatcher("/Pages/General/IndexGuest.jsp").forward(request, response);
				logger.info(logMessage+"guest");
				break;
			case 1:
				request.getRequestDispatcher("/Pages/Student/IndexStudent.jsp").forward(request, response);
				logger.info(logMessage+"student");
				break;
			case 2:
				request.getRequestDispatcher("/Pages/Admin/IndexAdmin.jsp").forward(request, response);
				logger.info(logMessage+"admin");
				break;
			default: //This, technically, shouldn't happen, but if for some ungodly reason it does...
				logger.error(rb.getString("error.accessOOB"));
				response.sendRedirect("General/Error");
		}


	}

}