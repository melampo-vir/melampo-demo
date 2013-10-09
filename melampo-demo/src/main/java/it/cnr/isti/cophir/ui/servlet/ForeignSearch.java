package it.cnr.isti.cophir.ui.servlet;

import it.cnr.isti.config.index.IndexConfiguration;
import it.cnr.isti.cophir.ui.bean.LoggingInfo;
import it.cnr.isti.cophir.ui.bean.MobileBean;
import it.cnr.isti.cophir.ui.bean.MobileCostants;
import it.cnr.isti.cophir.ui.bean.Parameters;
import it.cnr.isti.cophir.ui.bean.QueryComposer;
import it.cnr.isti.cophir.ui.index.Index_IF;
import it.cnr.isti.cophir.ui.tools.QueryLog;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author Paolo
 * @version
 */
public class ForeignSearch extends HttpServlet {

	private GregorianCalendar gc;
	private SimpleDateFormat sdf;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes the servlet.
	 */

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		gc = new GregorianCalendar();
		sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
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

		Index_IF mmdls = (Index_IF) session.getAttribute("imageQA");

		// synchronized (MobileCostants.getRootPath()) {
		if (MobileCostants.getRootPath() == null) {
			MobileCostants.setRootPath("http://" + request.getLocalAddr() + ":"
					+ request.getLocalPort() + request.getContextPath());

		}
		// }

		Parameters advancedOptions = (Parameters) session
				.getAttribute("advOptions");

		MobileBean mobileBean = (MobileBean) session.getAttribute("mobileBean");
		if (mobileBean == null) {
			mobileBean = new MobileBean(mmdls, advancedOptions);
			session.setAttribute("mobileBean", mobileBean);
		}

		QueryComposer parameters = (QueryComposer) session
				.getAttribute("parameters");
		
		if (parameters == null) {
			IndexConfiguration config = (IndexConfiguration) getServletContext().getAttribute("configuration");
			
			parameters = new QueryComposer(config.getDefaultDataset(), config);
			session.setAttribute("parameters", parameters);
		}

		System.out
				.println(sdf.format(new GregorianCalendar().getTime())
						+ " - foreign search - request from "
						+ request.getRemoteAddr());

		// logging
		LoggingInfo loggingInfo = (LoggingInfo) session
				.getAttribute("imgLoggingInfo");

		parameters.parseRequest(request);
		loggingInfo.setQueryID(parameters.getImageQueryURL());
		mobileBean.setQueryURL(parameters.getImageQueryURL());

		mobileBean.search(parameters.getXQueryValues(),
				parameters.getXQueryFields());
		String xmlResponse = mobileBean.getXMLResultsList();

		loggingInfo.setIpAddress(request.getRemoteAddr());

		loggingInfo.setTimestamp(System.currentTimeMillis());

		String src = "outside";
		loggingInfo.setSrc(src.trim());

		QueryLog.log(loggingInfo);
		loggingInfo.resetSearchInfo();
		// end logging

		PrintWriter out = null;
		BufferedInputStream buf = null;
		try {
			out = response.getWriter();
			// set response header
			response.setContentType("text/xml");

			buf = new BufferedInputStream((new ByteArrayInputStream(
					xmlResponse.getBytes())));
			int readBytes = 0;
			while ((readBytes = buf.read()) != -1)
				out.write(readBytes);
		} catch (IOException ioe) {
			throw new ServletException(ioe.getMessage());
		} finally {
			if (out != null)
				out.close();
			if (buf != null)
				buf.close();
		}
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
