package it.cnr.isti.cophir.ui.index;

/**
 *Index_IF UI Index Interface
 *@author Paolo Bolettieri
 *@version 1.0
 */
public interface Index_IF {
	
	public String search(String[] values, String[] fields);
	
	public void query(String query);
	
	public String[][] getResults(int startFrom, int numElements);
	
	public void setIndexParameter(String parameterName, String parameterValue);
	
	public String getIndexParameter(String parameterName);
	
	public long getSearchTime();
	
	public String getYiImageCode(String ID);
}