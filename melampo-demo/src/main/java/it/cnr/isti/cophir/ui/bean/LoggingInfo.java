package it.cnr.isti.cophir.ui.bean;

public class LoggingInfo {
	
	private long timestamp;
	private String textQuery;
	private String ipAddress;
	private String sessionID;
	private String[][] queryResults;
	private long searchTime;
	private String queryID;
	private String europeanaUri;
	public String getEuropeanaUri() {
		return europeanaUri;
	}

	public void setEuropeanaUri(String europeanaUri) {
		this.europeanaUri = europeanaUri;
	}

	private String src;
	private double[] confidences;

	private String label;
	private double confidence;
	
	public void resetSearchInfo() {
		timestamp = -1;
		textQuery = null;
		ipAddress  = null;
		//sessionID = null;
		queryResults = null;
		searchTime = -1;
		queryID = null;
		src = null;
		confidences = null;
		label = null;
		confidence = -1;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
	public String getTextQuery() {
		return textQuery;
	}

	public void setTextQuery(String textQuery) {
		this.textQuery = textQuery;
	}


	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String[][] getQueryResults() {
		return queryResults;
	}

	public void setQueryResults(String[][] queryResults) {
		this.queryResults = queryResults;
	}

	public long getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(long searchTime) {
		this.searchTime = searchTime;
	}

	public String getQueryID() {
		return queryID;
	}

	public void setQueryID(String queryID) {
		this.queryID = queryID;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public double[] getConfidences() {
		return confidences;
	}

	public void setConfidences(double[] confidences) {
		this.confidences = confidences;
	}
}
