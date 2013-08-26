package it.cnr.isti.cophir.ui.bean;

import it.cnr.isti.cophir.ui.index.IndexSupport;
import it.cnr.isti.cophir.ui.index.Index_IF;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class MobileBean extends SearchBean {

	private static Logger log = Logger.getLogger(MobileBean.class);
	private String numResults;
	private String queryURL;

	private final static String DEFAULT_MAX_RESULTS_STRING = "100";

	public MobileBean() {
		super();
	}

	public MobileBean(Index_IF mmdls, Parameters advancedOptions) {
		super(mmdls);
	}

	public void search(String[] values, String[] fields) {
		resetParameters();		
		mmdls.search(values, fields);
	}
	
	public void resetParameters() {
		xqueryFields = null;
		xqueryValues = null;
	}

	public String getXMLResultsList() {
		results = null;
		String xml = null;
		int numOfResults = Integer.parseInt(DEFAULT_MAX_RESULTS_STRING);
		try {
			numOfResults = Integer.parseInt(numResults);
		} catch (Exception e) {
			log.error("Exception to parse the numResults value: " + numResults
					+ ". It will use the DEFAULT_MAX_RESULTS_STRING value: "
					+ DEFAULT_MAX_RESULTS_STRING);
			numOfResults = Integer.parseInt(DEFAULT_MAX_RESULTS_STRING);
		}

		results = mmdls.getResults(0, numOfResults);
		xml = createXMLResultsList(results);
		return xml;
	}
	
	public void setQueryURL(String queryURL) {
		this.queryURL = queryURL;
	}

	private String createXMLResultsList(String[][] results) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>").append("<assets>")
				.append("<results>");

		for (int i = 0; i < results.length; i++) {
			String id = results[i][1];
			String score = results[i][0];
			sb.append("<result score=\"").append(score).append("\">")
					.append(id).append("</result>");
		}
		sb.append("</results>").append("</assets>");
		String xml = sb.toString();
		log.debug("XML response for mobile:");
		log.debug(xml);
		System.out.println("XML response for mobile:");
		System.out.println(xml);
		return xml;
	}

}
