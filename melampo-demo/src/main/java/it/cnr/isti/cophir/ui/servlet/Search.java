package it.cnr.isti.cophir.ui.servlet;

import it.cnr.isti.config.index.ImageDemoConfigurationImpl;
import it.cnr.isti.config.index.IndexConfiguration;
import it.cnr.isti.cophir.ui.bean.LoggingInfo;
import it.cnr.isti.cophir.ui.bean.QueryComposer;
import it.cnr.isti.cophir.ui.bean.SearchBean;
import it.cnr.isti.cophir.ui.index.Index_IF;
import it.cnr.isti.cophir.ui.tools.QueryLog;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Application Lifecycle Listener implementation class UIContextListener
 * 
 */

public class Search extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes the servlet.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * Destroys the servlet.
	 */
	public void destroy() {

	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		LoggingInfo loggingInfo = (LoggingInfo) session
				.getAttribute("imgLoggingInfo");

		loggingInfo.setIpAddress(request.getRemoteAddr());

		loggingInfo.setTimestamp(System.currentTimeMillis());

		String src = request.getParameter("src");

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (src == null || src.trim().equals("")) {
			if (isMultipart) {
				src = "form";
			} else {
				src = "unknown";
			}
		}
		loggingInfo.setSrc(src.trim());

		// ---------------------

		Index_IF mmdls = (Index_IF) session.getAttribute("imageQA");

		SearchBean imageSearchBean = (SearchBean) session
				.getAttribute("imageSearchBean");
		if (imageSearchBean == null) {
			imageSearchBean = new SearchBean(mmdls);
			session.setAttribute("imageSearchBean", imageSearchBean);
		}

		QueryComposer parameters = (QueryComposer) session
				.getAttribute("parameters");

		if (parameters == null) {
			IndexConfiguration config = (IndexConfiguration) getServletContext()
					.getAttribute("configuration");

			if(config == null){
				config = new ImageDemoConfigurationImpl();
				session.setAttribute("configuration", config);
			}
				
				
			parameters = new QueryComposer(config.getDefaultDataset(), config);
			session.setAttribute("parameters", parameters);
		}

		int numberOfPage = 0;

		if (request.getParameter("page") != null) {
			String page = request.getParameter("page").trim();
			numberOfPage = Integer.parseInt(page);
		} else if (request.getParameter("xmlQuery") != null) {
			numberOfPage = 0;
			String xmlQuery = request.getParameter("xmlQuery");
			imageSearchBean.search(xmlQuery);
		}

		String id = request.getParameter("id");

		if ((isMultipart || (id != null && !id.equals(parameters.getMediaUri())))
				|| request.getParameter("page") == null) {
			// QueryComposer parameters = new QueryComposer();
			parameters.parseRequest(request);
			loggingInfo.setQueryID(parameters.getImageQueryURL());
			imageSearchBean.search(parameters.getXQueryFields(),
					parameters.getXQueryValues(), parameters.getMediaUri(),
					parameters.getImageQueryURL());

		}

		imageSearchBean.setIndex(numberOfPage);

		// writes logs
		QueryLog.log(loggingInfo);
		loggingInfo.resetSearchInfo();

		request.getRequestDispatcher("./SearchResults.jsp").forward(request,
				response);

	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}

}
