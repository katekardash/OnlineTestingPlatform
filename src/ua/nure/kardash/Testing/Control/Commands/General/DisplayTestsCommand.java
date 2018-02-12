package ua.nure.kardash.Testing.Control.Commands.General;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.DBManager;
import ua.nure.kardash.Testing.DB.Entity.Test;

public class DisplayTestsCommand extends Command{

	private int level;

	public DisplayTestsCommand(int level) {
		this.level=level;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Executing test list retrieval for users with access level "+level);
		List<Test> tests = null;
		try {
			if(level==1){
				tests = DBManager.instance().getAllTests(false);
			}
			else if(level==2){
				tests = DBManager.instance().getAllTests(true);
			}
		} catch (SQLException e) {
			logger.error("Test list retrieval failed: "+e.getMessage());
		}

		if(tests!=null){
			request.setAttribute("tests", tests);
			switch (level){
				case 1:
					request.getRequestDispatcher("/Pages/Student/TestsStudent.jsp").forward(request, response);
					break;
				case 2:
					request.getRequestDispatcher("/Pages/Admin/TestsAdmin.jsp").forward(request, response);
					break;
				default:
					response.sendRedirect("General/Error");
			}
		}
		else{
			localize(request.getSession());
			request.getSession().setAttribute("errormessage", rb.getString("error.testfail"));
			response.sendRedirect("/Testing/Error");
		}
	}
}