package it.cnr.isti.cophir.ui.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import org.apache.log4j.PropertyConfigurator;

public class UILogServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		// Get Fully Qualified Path to Properties File
		//use context specifig configurations
		String config = getServletContext().getRealPath("/")
				+  "/WEB-INF" + getServletContext().getContextPath() + "/" + getInitParameter("setup");
		
		
		File configFile = new File(config);
		//if not use default configurations
		if(!configFile.exists())
			config = getServletContext().getRealPath("/")
			+  "/WEB-INF/" + getInitParameter("setup");
		
		System.out.println("LoggingServlet Initialized using file : " + config);
		// Initialize Properties for All Servlets
		PropertyConfigurator.configure(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html>");
		out.println("<head>");
		out.println("<title>LoggingServlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>Called GET method of the Servlet.</p>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	public void destroy() {
	}
}