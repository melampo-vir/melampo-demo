package it.cnr.isti.cophir.ui.servlet;

import it.cnr.isti.cophir.ui.bean.Parameters;

import java.io.IOException;

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
public class SetAdvancedOptions extends HttpServlet {

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

		Parameters advOptions = (Parameters) session
				.getAttribute("advOptions");

		setParameters(request, advOptions);
	}

	private void setParameters(HttpServletRequest request,
			Parameters advOptions) {

		String feature = request.getParameter("feature");

		if (feature != null) {
			feature = feature.trim();
			if (advOptions.getFeatures().contains(feature)) {
				advOptions.removeFeatures(feature);
			} else {
				advOptions.addFeatures(feature);
			}
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
