package ua.nure.kardash.Testing.Control.Commands.General;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;

/**
 * Command. Changes user UI settings (language or theme) while reloading a given page.
 */
public class ChangeSettingsCommand extends Command{
	private String setting;

	public ChangeSettingsCommand(String param) {
		this.setting = param;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Parses what setting is changed, pages the dbmanager
		HttpSession sesh = request.getSession();
		String lang = (String) sesh.getAttribute("lang");
		String theme = (String) sesh.getAttribute("theme");

		if(setting.equals("ru")||setting.equals("en")){
			lang=setting;
			sesh.setAttribute("language", setting);
			ServletContext sc = sesh.getServletContext();
			sesh.setAttribute("lagnuage-bundle", sc.getAttribute("bundle-"+lang));
		}
		else if(setting.equals("dark")||setting.equals("light")){
			theme=setting;
			sesh.setAttribute("theme", setting);
		}

		Object userID = sesh.getAttribute("userID");
		if(userID != null){
			logger.info("Attempting to update settings");
			try {
				DBManager.instance().updateUserSettings((int)userID,lang+"-"+theme);
			} catch (SQLException e) {
				logger.error("Failed to update settings: "+e.getMessage());
				request.setAttribute("errormessage", decodeSQLError(e.getMessage()));
			}
		}

		response.sendRedirect(request.getRequestURI());
	}

}
