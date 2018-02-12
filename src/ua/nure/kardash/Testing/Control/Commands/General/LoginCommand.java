package ua.nure.kardash.Testing.Control.Commands.General;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;
import ua.nure.kardash.Testing.DB.Entity.AccountInfo;

/**
 * Command: Attempts to log the user in. If not, returns to login page and displays an error.  <br> Requires no authorization level.
 */
public class LoginCommand extends Command{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String log = request.getParameter("login");
		String pass = request.getParameter("pass");
		logger.info("Executing command to log in user: "+log);

		AccountInfo acc=null;

		//PUT THE LOGIN CHECKING OVER HEEEREEE


		try {
			acc = DBManager.instance().authorizeUser(log, pass);
		}
		catch (SQLException e) {
			logger.error("User failed to authorize: "+e.getMessage());
		}

		HttpSession sesh = request.getSession();

		//If user settings match - logs in
		if(acc!=null){

			if(acc.isBlocked()){
				sesh.setAttribute("errormessage", rb.getString("error.blocked"));
				response.sendRedirect("/Testing/Error");
				return;
			}

			sesh.setAttribute("userID", acc.getId());
			sesh.setAttribute("login", log);
			sesh.setAttribute("name", acc.getName());
			sesh.setAttribute("role", acc.getRole());

			processSettings(acc.getSettings(), sesh);

			logger.info("Login successful. Logged in "+log);
			request.setAttribute("errormessage", null);

			response.sendRedirect("/Testing/Index");
		}
		else{
			localize(sesh);
			request.setAttribute("errormessage", rb.getString("error.authfail"));
			request.getRequestDispatcher("/Pages/General/Login.jsp").forward(request, response);
		}

	}

	/**
	 * Interprets retrieved user settings string and records them into current session.
	 * <br> Default settings are en-light; if either of the params is missing,
	 * @param settings - User settings are stored and formatted as: language-theme (ex: en-light, ru-dark) or a null-string
	 * @param sesh - Current session
	 */
	private void processSettings(String settings, HttpSession sesh) {
		if(settings==null || settings.length()==0){
			sesh.setAttribute("language", "en");
			sesh.setAttribute("theme", "light");
		}
		else{
			String[] sett = settings.split("-");

			sesh.setAttribute("language", sett[0]);
			sesh.setAttribute("theme", sett[1]);

		}
	}

}