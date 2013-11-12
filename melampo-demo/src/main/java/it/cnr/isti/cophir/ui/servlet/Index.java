package it.cnr.isti.cophir.ui.servlet;

import it.cnr.isti.config.index.ImageDemoConfiguration;
import it.cnr.isti.cophir.ui.bean.RandomImages;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author  Paolo
 * @version
 */
public class Index extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Initializes the servlet.
     */
	
	public void init(ServletConfig config) throws ServletException {
        super.init(config);       
    }
    
    /** Destroys the servlet.
     */
    public void destroy() {
        
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
				
		//String rootPath = getServletContext().getRealPath("/");
		RandomImages randomImages = (RandomImages) session.getAttribute("randomImages");
		//Parameters advOptions = (Parameters) session.getAttribute("advOptions");
		ImageDemoConfiguration configuration = (ImageDemoConfiguration) session.getAttribute("configuration");
		
		
		String advUI = request.getParameter("advUI");
		if (advUI != null) {
			if (advUI.equals("false")) {
				session.setAttribute("advUI", "false");
			}
			else {
				session.setAttribute("advUI", "true");
			}
		}
		
		if (randomImages == null) {
			randomImages = new RandomImages();
			
			//TODO: use index configuration instead index helper
			//randomImages.openProps(advOptions.getDatasetFile());
			randomImages.openProps(configuration.getDatasetUrlsFile(null));
			session.setAttribute("randomImages", randomImages);
		}
		
		if (request.getParameter("selected") != null) {
			if (request.getParameter("selected").equals("false")) {
				session.removeAttribute("selected");
			} else {
				session.setAttribute("selected", "true");
			}
		}
		
		request.getRequestDispatcher("./index.jsp").forward(request, response);
		//response.sendRedirect("./index.jsp");
	}
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    
}
