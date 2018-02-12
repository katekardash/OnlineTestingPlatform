package ua.nure.kardash.Testing.Web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kardash.Testing.Control.CommandFactory;


public class MainController extends HttpServlet {
	private static final long serialVersionUID = 213155131L;

    public MainController(){
        super();
     }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommandFactory.getCommand(request).execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommandFactory.getCommand(request).execute(request, response);
	}
}
