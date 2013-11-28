package it.cnr.isti.cophir.ui.bean;

import it.cnr.isti.config.index.ImageDemoConfiguration;
import it.cnr.isti.config.index.IndexConfiguration;
import it.cnr.isti.cophir.ui.index.IndexSupport;
import it.cnr.isti.exception.TechnicalRuntimeException;
import it.cnr.isti.feature.extraction.FeatureExtractionException;
import it.cnr.isti.feature.extraction.Image2Features;
//import it.cnr.isti.cophir.ui.tools.Image2Features;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class QueryComposer {

	private Image2Features mpeg7Handler;

	private volatile String mediaUri = null;
	private String imageQueryURL = null;

	protected String[] xqueryFields;
	protected String[] xqueryValues;
	private static Logger log = Logger.getLogger(QueryComposer.class);

	public QueryComposer(String dataset, IndexConfiguration config) {
		try {
			mpeg7Handler = new Image2Features(config.getIndexConfFolder(dataset));
		} catch (Exception e) {
			throw new TechnicalRuntimeException("Cannot instantiate the feature extractor", e);
		} 
	}

	public void parseRequest(HttpServletRequest request) {
		resetParameters();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			parseMultipartRequest(request);
		} else {
			try {
				parseStandardRequest(request);
			} catch (Exception e) {
				throw new TechnicalRuntimeException("Cannot parse standard request", e);
			}
		}
	}

	private void parseMultipartRequest(HttpServletRequest request) {
		String url = null;
		String mpeg7 = null;
		String id = null;

		Hashtable<String, String> parameters = new Hashtable<String, String>();

		// Create a factory for disk-based file items
		FileItemFactory factory = new DiskFileItemFactory();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		// Parse the request
		try {
			List items = upload.parseRequest(request);

			// Processing the uploaded items
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) {
					String name = item.getFieldName();
					if (name.equals("imgFile")) {
							mpeg7 = mpeg7Handler.extractFeatures(item.getInputStream());
					} else {
					String value = item.getString();
					if (name.equals("url")) {
						url = value.trim();
						log.debug("Query url: " + url);
							mpeg7 = mpeg7Handler.extractFeatures(new URL(url));
					} else if (name.equals("id")) {
						id = value;
						mediaUri = id;
						//mpeg7 = IndexSupport.getMetadata(value);
					} else {
						parameters.put(name, value);
					}
				}

				} else if (item.getSize() > 0) {
						mpeg7 = mpeg7Handler.extractFeatures(item.getInputStream());
				}
			}

			if (mpeg7 != null && !mpeg7.trim().equals("")) {
				fields.add("Image");
				values.add(mpeg7);
				setImageQueryURL(url);
				//setImageQueryURL(mpeg7Handler.getQueryURL());
			} else if (id != null) {
				fields.add("id");
				values.add(id);
				setImageQueryURL(IndexSupport.getThumbnailUrl(id));
			}

			int i = 0;
			while (parameters.containsKey("value" + i)) {
				String value = parameters.get("value" + i);
				if (value != null && !value.trim().equals("")) {

					log
							.debug("field" + i + " = "
									+ parameters.get("field" + i));
					fields.add(parameters.get("field" + i));

					log
							.debug("value" + i + " = "
									+ parameters.get("value" + i));
					values.add(parameters.get("value" + i));
				}
				i++;
			}

			xqueryFields = new String[fields.size()];
			xqueryValues = new String[values.size()];
			xqueryFields = fields.toArray(xqueryFields);
			xqueryValues = values.toArray(xqueryValues);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseStandardRequest(HttpServletRequest request) throws FeatureExtractionException, IOException {
		String url = null;
		String mpeg7 = null;
		String id = null;
		
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();

		id = request.getParameter("id");
		mediaUri = id;

		url = request.getParameter("imgUrl");
		if(url!=null)
			mediaUri = url;
		
		String featuresParam = request.getParameter("features");
		String[] features = null;
		if (featuresParam != null && !"".equals(featuresParam)) {
			//features = featuresParam.split("_");
			//temporaneo 26 settembre 2011 per LIRE_MP7ALL
			features = featuresParam.split("ppppppp");
			HttpSession session = request.getSession();

			Parameters advOptions = (Parameters) session
					.getAttribute("advOptions");
			advOptions.setFeatures(features);
		}

		if (url != null && !url.trim().equals("")) {
			mpeg7 = mpeg7Handler.extractFeatures(new URL(url));
		}

		if (mpeg7 != null && !mpeg7.trim().equals("")) {
			fields.add("Image");
			values.add(mpeg7);
			setImageQueryURL(url);
			//setImageQueryURL(mpeg7Handler.getQueryURL());
		} else if (id != null) {
			fields.add("id");
			values.add(id);
			
			HttpSession session = request.getSession();
			ImageDemoConfiguration configuration = (ImageDemoConfiguration) session.getAttribute("configuration");
			
			//TODO: Same as in Index.processRequest, consider refactoring
			RandomImages randomImages = (RandomImages) session.getAttribute("randomImages");
			if(randomImages == null){
				randomImages = new RandomImages();
				randomImages.openProps(configuration.getDatasetUrlsFile(null));
				session.setAttribute("randomImages", randomImages);
			}
				
			setImageQueryURL(randomImages.getThumbnailUrl(id));
			//setImageQueryURL(IndexSupport.getThumbnailUrl(id));
		}
		
		int i = 0;
		while (request.getParameter("value" + i) != null) {
			String value = request.getParameter("value" + i);
			if (value != null && !value.trim().equals("")) {

				log
						.debug("field" + i + " = "
								+ request.getParameter("field" + i));
				fields.add(request.getParameter("field" + i));

				log
						.debug("value" + i + " = "
								+ request.getParameter("value" + i));
				values.add(value);
			}
			i++;
		}
		
		xqueryFields = new String[fields.size()];
		xqueryValues = new String[values.size()];
		xqueryFields = fields.toArray(xqueryFields);
		xqueryValues = values.toArray(xqueryValues);

	}

	public String getMediaUri() {
		return this.mediaUri;
	}

	private void setImageQueryURL(String imageQueryURL) {
		this.imageQueryURL = imageQueryURL;
	}

	public String getImageQueryURL() {
		return this.imageQueryURL;
	}

	private void resetParameters() {
		this.xqueryFields = null;
		this.xqueryValues = null;
		this.mediaUri = null;
		this.imageQueryURL = null;
	}

	public String[] getXQueryValues() {
		return this.xqueryValues;
	}

	public String[] getXQueryFields() {
		return this.xqueryFields;
	}

}
