package it.cnr.isti.cophir.ui.servlet;

import it.cnr.isti.cophir.ui.bean.Parameters;
import it.cnr.isti.cophir.ui.bean.LoggingInfo;
import it.cnr.isti.cophir.ui.index.MelampoIndex;
import it.cnr.isti.cophir.ui.index.Index_IF;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class UIContextListener
 *
 */
public class UISessionListener implements HttpSessionListener {
	

    /**
     * Default constructor. 
     */
    public UISessionListener() {
    }

	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		Parameters advOptions  = new Parameters();
		session.setAttribute("advOptions", advOptions);
		System.out.println("setting default parameters...");
		advOptions.setDefaultParameters();
		
		LoggingInfo imgLoggingInfo = new LoggingInfo();
		session.setAttribute("imgLoggingInfo", imgLoggingInfo);
		
		imgLoggingInfo.setSessionID(session.getId());
		
		Index_IF imageQA = new MelampoIndex(advOptions, imgLoggingInfo);
		session.setAttribute("imageQA", imageQA);
		
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
