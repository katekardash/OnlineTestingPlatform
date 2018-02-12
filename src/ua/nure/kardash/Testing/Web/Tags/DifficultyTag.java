package ua.nure.kardash.Testing.Web.Tags;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Tag that transforms numeric difficulty values to strings. Simple, but useful.
 */
public class DifficultyTag extends SimpleTagSupport {
		StringWriter sw = new StringWriter();

		public void doTag() throws JspException, IOException {
			PageContext pageContext = (PageContext) getJspContext();
			HttpSession sesh = ((HttpServletRequest) pageContext.getRequest()).getSession();
			String lang = (String) sesh.getAttribute("language");
			if(lang==null){
				lang="en";
			}

			ResourceBundle rb = (ResourceBundle) sesh.getServletContext().getAttribute("bundle-"+lang);

			getJspBody().invoke(sw);
			int diff;

		    try{
		    	 diff = Integer.parseInt(sw.toString());
		    }
		    catch(NumberFormatException e){
		    	diff=1;
		    }

		    String difficulty;
		    switch(diff){
		    case 1:
		    	difficulty=rb.getString("diff.veasy"); break;
		    case 2:
		    	difficulty=rb.getString("diff.easy"); break;
		    case 3:
		    	difficulty=rb.getString("diff.medium"); break;
		    case 4:
		    	difficulty=rb.getString("diff.hard"); break;
		    case 5:
		    	difficulty=rb.getString("diff.vhard"); break;
		    default:
		    	difficulty=rb.getString("diff.undef");
		    }

		    getJspContext().getOut().println(difficulty);

		}


}
