package it.cnr.isti.cophir.ui.tools;

import it.cnr.isti.cophir.ui.bean.LoggingInfo;

import org.apache.log4j.Logger;

public class QueryLog {
	
	private static Logger log = Logger.getLogger("queryLog");
	
	public static void log(LoggingInfo loggingInfo) {
		String xmlLog = buildXMLLog(loggingInfo);
		log.info(xmlLog);
		
	}
	
	private static String buildXMLLog(LoggingInfo loggingInfo) {
		StringBuilder xmlLog = new StringBuilder();
		
		String[][] results = loggingInfo.getQueryResults();
		double[] confidences = loggingInfo.getConfidences();
		
		xmlLog.append("<log timestamp=\"").append(loggingInfo.getTimestamp()).append("\" sessionId=\"").append(loggingInfo.getSessionID()).append("\" ip=\"").append(loggingInfo.getIpAddress()).append("\">\n");
		xmlLog.append("<src>").append(loggingInfo.getSrc()).append("</src>\n");
		
		if (loggingInfo.getQueryID() == null || loggingInfo.getQueryID().trim().equals("")) {
			xmlLog.append("<objectId/>\n");
		}
		else {
			xmlLog.append("<objectQueryURL>").append(loggingInfo.getQueryID()).append("</objectQueryURL>\n");
		}
		
		if (loggingInfo.getEuropeanaUri() == null || loggingInfo.getEuropeanaUri().trim().equals("")) {
			xmlLog.append("<europeanaUri/>\n");
		}
		else {
			xmlLog.append("<europeanaUri>").append(loggingInfo.getEuropeanaUri()).append("</europeanaUri>\n");
		}
		
		if (loggingInfo.getTextQuery() == null || loggingInfo.getTextQuery().trim().equals("")) {
			xmlLog.append("<textQuery/>\n");
		}
		else {
			xmlLog.append("<textQuery>").append(loggingInfo.getTextQuery()).append("</textQuery>\n");
		}
		
		xmlLog.append("<searchTime>").append(loggingInfo.getSearchTime()).append("</searchTime>\n");
		
		if (results == null || results[0][0] == null) {
			xmlLog.append("<results/>\n");
		}
		else {
			xmlLog.append("<results>\n");
			for (int i = 0; i < results.length && i < 3; i++) {
				if (results[i][0] != null) {
					xmlLog.append("<result score=\"").append(results[i][0]).append("\" objectId=\"").append(results[i][1]).append("\" confidence=\"");
					if (confidences == null) {
						xmlLog.append("-1");	
					}
					else {
						xmlLog.append(confidences[i]);	
					}
					xmlLog.append("\"/>\n");
				}
				
			}
			xmlLog.append("</results>\n");
		}

		xmlLog.append("</log>");
		
		return xmlLog.toString();
	}

}
