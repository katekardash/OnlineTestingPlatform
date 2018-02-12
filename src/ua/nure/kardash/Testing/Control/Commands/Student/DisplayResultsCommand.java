package ua.nure.kardash.Testing.Control.Commands.Student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.Entity.TestResult;

public class DisplayResultsCommand extends Command {

	String address;
	public DisplayResultsCommand(String a) {
		address=a;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesh = request.getSession();
		TestResult testRes = (TestResult) sesh.getAttribute(address+"-result");

		if(testRes==null){
			response.sendRedirect("/Testing/Tests/"+address);
		}

		request.setAttribute("testresult", testRes);
		request.setAttribute("testaddress", address);
		request.getRequestDispatcher("/Pages/Student/TestResult.jsp").forward(request, response);
	}

}

