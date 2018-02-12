package ua.nure.kardash.Testing.Web.Tags;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Reformats the time taken (in seconds) into a more pleasant-looking string
 */
public class TimeTakenTag extends SimpleTagSupport {
		StringWriter sw = new StringWriter();
		public void doTag() throws JspException, IOException {
			getJspBody().invoke(sw);
			int time;

		    try{
		    	 time = Integer.parseInt(sw.toString());
		    }
		    catch(NumberFormatException e){
		    	time=0;
		    }

		    int mins = time/60;
		    int secs = time%60;

		    String minsS;
		    String secsS;

		    if(mins<0){
		    	minsS="0"+mins;
		    }
		    else{
		    	minsS=""+mins;
		    }

		    if(secs<0){
		    	secsS="0"+secs;
		    }
		    else{
		    	secsS=""+secs;
		    }

		    getJspContext().getOut().println(minsS+":"+secsS);

		}

}
