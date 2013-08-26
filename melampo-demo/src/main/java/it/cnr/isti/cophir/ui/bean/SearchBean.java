package it.cnr.isti.cophir.ui.bean;

import it.cnr.isti.cophir.ui.index.Index_IF;

import org.apache.log4j.Logger;

public class SearchBean {

	private static Logger log = Logger.getLogger(SearchBean.class);

	private String mediaUri = null;
	private String imageQueryURL = null;
	
	protected String[] xqueryFields;
	protected String[] xqueryValues;

	protected String[][] results = null;
	protected Index_IF mmdls;
	private int page = 0;

	protected int index = 0;
	private static int resultsForPage = 10;
	private static int resultsForRow = 3;

	public SearchBean() {
	}

	public SearchBean(Index_IF mmdls) {
		this();
		this.mmdls = mmdls;
	}

	public void search(String[] xqueryFields, String[] xqueryValues,
			String mediaUri, String imageURL) {
		resetSearchParameters();
		index = 0;
		this.mediaUri = mediaUri;
		this.imageQueryURL = imageURL;
		this.xqueryValues = xqueryValues;
		this.xqueryFields = xqueryFields;
		mmdls.search(this.xqueryValues, this.xqueryFields);
	}
	
	public void search(String xmlQuery) {
		resetSearchParameters();
		index = 0;
		mmdls.query(xmlQuery);
	}

	public String getMediaUri() {
		return this.mediaUri;
	}

	public String getImageQueryURL() {
		return this.imageQueryURL;
	}
	
	public String[] getXQueryValues() {
		return this.xqueryValues;
	}

	public String[] getXQueryFields() {
		return this.xqueryFields;
	}

	public void resetSearchParameters() {
		this.xqueryFields = null;
		this.xqueryValues = null;
		this.mediaUri = null;
		this.imageQueryURL = null;
	}

	public int getResultsForPage() {
		return SearchBean.resultsForPage;
	}

	public static void setResultsForPage(int resultsForPage) {
		SearchBean.resultsForPage = resultsForPage;
	}

	public static void setResultsForRow(int resultsForRow) {
		SearchBean.resultsForRow = resultsForRow;
	}

	public int getResultsForRow() {
		return SearchBean.resultsForRow;
	}

	public void decreaseIndex() {
		index -= 2 * resultsForPage;
	}

	public void setIndex(int page) {
		this.page = page;
		index = this.page * resultsForPage;
	}

	public int getPage() {
		return this.page;
	}

	public String[][] getResults() {
		results = null;
		log.debug("getResults startFrom: " + index + " numResults: "
				+ resultsForPage);
		results = mmdls.getResults(index, resultsForPage);
		index += resultsForPage;
		return results;
	}

	public int[] getResultsRange() {
		int start = index - resultsForPage;
		int end = index;
		for (int i = 0; i < resultsForPage; i++) {
			if (results[i][0] == null) {
				end = start + i;
				break;
			}
		}
		int[] range = new int[2];
		range[0] = start + 1;
		range[1] = end;
		return range;
	}

	public boolean next() {
		String[][] testAncoraRisultati = null;
		testAncoraRisultati = mmdls.getResults(index, 1);
		if ((testAncoraRisultati != null)
				&& (testAncoraRisultati[0][0] != null))
			return true;
		else
			return false;
	}

	public boolean prev() {
		if (index > resultsForPage) {
			return true;
		} else {
			return false;
		}
	}

	public long getSearchTime() {
		return mmdls.getSearchTime();
	}

	public String getScoreBar(String score) { 
		String scoreBar = "score10.jpg";
		/*float minScore = (float) 0.0;
		float step = (1 - minScore) / 10;
		int distance = Math
				.round(((Float.parseFloat(score) - minScore) / step));
		switch (distance) {
		case 0:
			scoreBar = "score0.jpg";
			break;
		case 1:
			scoreBar = "score1.jpg";
			break;
		case 2:
			scoreBar = "score2.jpg";
			break;
		case 3:
			scoreBar = "score3.jpg";
			break;
		case 4:
			scoreBar = "score4.jpg";
			break;
		case 5:
			scoreBar = "score5.jpg";
			break;
		case 6:
			scoreBar = "score6.jpg";
			break;
		case 7:
			scoreBar = "score7.jpg";
			break;
		case 8:
			scoreBar = "score8.jpg";
			break;
		case 9:
			scoreBar = "score9.jpg";
			break;
		case 10:
			scoreBar = "score10.jpg";
			break;
		default:
			scoreBar = "score0.jpg";
		}*/
		return scoreBar;
	}
}
