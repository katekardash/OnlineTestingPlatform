package ua.nure.kardash.Testing.Control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ua.nure.kardash.Testing.Control.Commands.Command;
import ua.nure.kardash.Testing.Control.Commands.Admin.*;
import ua.nure.kardash.Testing.Control.Commands.General.*;
import ua.nure.kardash.Testing.Control.Commands.Student.*;

public class CommandFactory {
	/**
	 * Constructs a command based on the url of incoming request, included post data, user cookies and other information.
	 * <br>
	 * GET requests automatically result in display commands; POST requests produce some changes and then (usually) return to the same page.
	 * <br>
	 * If the user tries to access a page\command that their authorization level shouldn't allow (either by editing page source or manually typing in the address) they're booted back to the index page (without an error display. They know what they did wrong)
	 * @param request HTTP request given to servlet
	 * @return Command produced by the factory. If no suitable command can be produced, returns RedirectToIndexCommand.
	 */
	public static Command getCommand(HttpServletRequest request){
		String[] addressTokens = request.getRequestURI().substring(1).split("/");
		Command comm=null;

		String page = addressTokens[1];
		String method = request.getMethod();
		int level = getAuthLevel(request);

		Log("Recieved URI: "+request.getRequestURI()+"  Tokens: "+addressTokens.length+"   Auth: "+level+"  Method: "+method);

		//GET: Simply displays the page
		if(method.equals("GET")){
			if(page.equals("Error")){
				Log("Displaying errorpage");
				comm = new DisplayErrorCommand();
			}
			if(page.equals("Login") && level==0){
				Log("Displaying login");
				comm = new DisplayLoginCommand();
			}
			else if(page.equals("Register") && level==0){
				Log("Displaying registration");
				comm = new DisplayRegisterCommand();
			}
			else if(page.equals("Index")){
				Log("Displaying index for auth level "+level);
				comm = new DisplayIndexCommand(level);
			}
			else if(page.equals("Tests") && level>0){
				if(addressTokens.length==2){
					Log("Displaying tests with auth level "+level);
					comm = new DisplayTestsCommand(level);
				}
				else if(addressTokens.length==3){
					Log("Displaying test at "+addressTokens[2]);
					comm = new DisplayTestIntroCommand(addressTokens[2]);
				}
				else if(addressTokens.length==4){
					if(addressTokens[3].equals("questions")){
						Log("Displaying full test page for "+addressTokens[2]);
						comm = new DisplayQuestionsCommand(addressTokens[2]);
					}
					else if(addressTokens[3].equals("results")){
						Log("Displaying results page for "+addressTokens[2]);
						comm = new DisplayResultsCommand(addressTokens[2]);
					}
					else if(addressTokens[3].equals("edit")&& level==2){
						Log("Displaying edit test page for"+addressTokens[2]);
						comm = new DisplayTestEditCommand(addressTokens[2]);
					}
				}
			}
			else if(page.equals("Journal") && level==1){
				Log("Displaying journal");
				comm = new DisplayJournalCommand();
			}
			else if(page.equals("Users") && level==2){
				Log("Displaying users");
				comm = new DisplayUsersCommand();
			}

			//Checks if the GET command also includes settings or a command to log out
			if(comm!=null){
				if(request.getParameter("lang")!=null){
					comm = new ChangeSettingsCommand(request.getParameter("lang"));
				}
				else if(request.getParameter("theme")!=null){
					comm = new ChangeSettingsCommand(request.getParameter("theme"));
				}
				else if(request.getParameter("logout")!=null){
					comm = new LogoutCommand();
				}
			}

		}

		//POST: Checks incoming commands and executes them.
		//Even if pages contain one possible command it is checked nevertheless to prevent potential unnecessary crashes
		else if(method.equals("POST")){
			String command = request.getParameter("command");
			Log("Recieved POST command "+command);
			if(command == null){
				comm = new RedirectToErrorCommand();
			}

			if(page.equals("Login") && level==0){
				if(command.equals("login")){
					comm = new LoginCommand();
				}
			}
			else if(page.equals("Register") && level==0){
				if(command.equals("register")){
					comm = new RegisterCommand();
				}
			}
			else if(page.equals("Tests")){
				if(command.equals("begintest") && level==1){
					Log("Requesting a test beginning");
					comm = new BeginTestCommand();
				}
				else if(command.equals("submitanswer") && level==1){
					Log("Test answer submitted");
					comm = new ProcessAnswersCommand(addressTokens[2]);
				}
				else if(command.equals("finishtest") && level==1){
					Log("Test finished");
					comm = new FinishTestCommand(addressTokens[2]);
				}
				else if(command.equals("deletetest") && level==2){
					Log("Deleting a test");
					comm = new DeleteTestCommand();
				}
				else if(command.equals("createtest") && level==2){
					Log("Creating a new test");
					comm = new CreateTestCommand();
				}
				else if(command.equals("edittestinfo") && level==2){
					Log("Editing a new test info");
					comm = new EditTestInfoCommand();
				}
				else if(command.equals("createquestion") && level==2){
					Log("Creating a new test");
					comm = new CreateQuestionCommand(addressTokens[2]);
				}
				else if(command.equals("editquestion") && level==2){
					Log("Editing a new test info");
					comm = new EditQuestionCommand(addressTokens[2]);
				}
				else if(command.equals("createanswer") && level==2){
					Log("Creating a new test");
					comm = new CreateAnswerCommand(addressTokens[2]);
				}
				else if(command.equals("editanswer") && level==2){
					Log("Editing a new test info");
					comm = new EditAnswerCommand(addressTokens[2]);
				}

			}
			else if(page.equals("Users") && level==2){
				if(command.equals("createuser")){
					Log("Creating user");
					comm = new CreateUserCommand();
				}
				else if(command.equals("setblock")){
					Log("Setting block for user");
					comm = new SetBlockCommand();
				}
			}

		}

		if(comm==null){
			Log("No suitable page found, redirecting to error page.");
			comm = new RedirectToErrorCommand();
		}
		return comm;
	}

	private static void Log(String string) {
		System.out.println("Factory: "+string);
	}

	/**
	 * Checks the request's authorization level via the session.
	 * @return 0 if user is not logged in; 1 if user's role is student; 2 if user's role is admin
	 */
	private static int getAuthLevel(HttpServletRequest request){
		HttpSession sesh = request.getSession();
		String role = (String) sesh.getAttribute("role");
		if(role!=null){
			if(role.equals("student")){
				return 1;
			}
			else if (role.equals("admin")){
				return 2;
			}
		}

		return 0;
	}
}
