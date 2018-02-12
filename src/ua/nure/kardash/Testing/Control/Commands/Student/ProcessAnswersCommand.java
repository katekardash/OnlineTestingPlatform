package ua.nure.kardash.Testing.Control.Commands.Student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.DB.Entity.TestHandler;

/**
 * Command. Invoked by the AJAX command sent from test page.
 * Returns nothing and is intended to be processed by server as the user keeps answering questions.
 */
public class ProcessAnswersCommand extends Command {
	String address;

	public ProcessAnswersCommand(String testAddress) {
		address=testAddress;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesh = request.getSession();
		((TestHandler) sesh.getAttribute(address+"-handler")).processAnswers(request.getParameter("question"),request.getParameter("answers"));
	}
}
