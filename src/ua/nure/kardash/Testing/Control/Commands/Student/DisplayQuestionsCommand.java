package ua.nure.kardash.Testing.Control.Commands.Student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.Entity.Test;

public class DisplayQuestionsCommand extends Command {

	String address;
	public DisplayQuestionsCommand(String a) {
		address=a;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesh = request.getSession();
		Test test = (Test) sesh.getAttribute(address);

		if(test==null){
			response.sendRedirect("/Testing/Tests/"+address);
		}

		Long timeleft = ((Long) sesh.getAttribute(address+"-timeout")-System.currentTimeMillis())/1000;

		request.setAttribute("timeleft", timeleft);
		request.setAttribute("test", test);
		request.getRequestDispatcher("/Pages/Student/TestQuestions.jsp").forward(request, response);
	}

}

