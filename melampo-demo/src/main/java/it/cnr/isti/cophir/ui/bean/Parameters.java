package it.cnr.isti.cophir.ui.bean;

import java.io.File;
import java.util.ArrayList;

public class Parameters {
	
	//public static String COPHIR_UI_HOME = System.getenv("COPHIR_UI_HOME");
	
	//public static final String COPHIR_UI_CONFIG = "cophirUI.properties";
	//TODO change context param with the usage of properties
	//public static final String DATASET_FILE = "/../datasets/test_dataset.urls.csv";
	//public static String UI_HOME = "";
	public static final int DEFAULT_NUM_RESULTS = 60;
	
	private long timeout;
	private int numResults = DEFAULT_NUM_RESULTS;
	
	private static File uiConfigFile;
	private static File datasetFile;
	
	
	private ArrayList<String> m_f = new ArrayList<String>();
//	private String IndexPath;

	public Parameters() {
	}
	
//	public static void setUIHome(String uiHome) {
//		//UI_HOME = COPHIR_UI_HOME + File.separator + uiHome;
//		UI_HOME = uiHome;
//		//TODO change UI HOME to melampo home
//		uiConfigFile = new File(UI_HOME + File.separator + COPHIR_UI_CONFIG);
//		datasetFile = new File(UI_HOME + DATASET_FILE);
//	}
	
	public static File getUIConfigFile() {
		return uiConfigFile;
	}
	
	public static File getDatasetFile() {
		return datasetFile;
	}
	
	/*****************SESSION SETTINGS************************/
	
	public void setDefaultParameters() {
		
//		Properties props = new Properties();
//		FileInputStream fIS = null;
//		try {
//			fIS = new FileInputStream(uiConfigFile); 
//			props.load(fIS);
//
//			String numResults = props.getProperty("numResults").trim();
//			setNumResults(numResults);
//			
////			String index = props.getProperty("index").trim();
////			setIndexPath(index);
//			
//		} catch (FileNotFoundException e) {e.printStackTrace();}
//		catch (IOException e) {e.printStackTrace();}
//		finally {
//			try {
//				if (fIS != null)
//					fIS.close();
//			} catch (IOException ex){ex.printStackTrace();}
//		}
		//TODO: move the cophir ui properties to image-demo.propeties
		//setNumResults(DEFAULT_NUM_RESULTS) ;
		
		m_f.add(it.cnr.isti.melampo.index.Parameters.LIRE_MP7ALL);
	}

	public void setTimeout(String timeout) {
		System.out.println("setTimeout: " + timeout);
		this.timeout = Long.parseLong(timeout.trim());
	}

	public long getTimeout() {
		return timeout;
	}

	public void setNumResults(String numResults) {
		this.numResults = Integer.parseInt(numResults.trim());
		System.out.println("setNumResults: " + numResults);
	}

	public int getNumResults() {
		return numResults;
	}
	
	// Claudio	
	public void addFeatures(String f){
		m_f.add(f);
		for(String value: m_f) 
		System.out.print(value + " ");
		System.out.println();
	}
	
	public void removeFeatures(String f){
		m_f.remove(f);
		for(String value: m_f)
			System.out.print(value + " ");
			System.out.println();
	}
	
	public void setFeatures(String[] features) {
		m_f.clear();
		for (String feature: features) {
			m_f.add(feature);
		}
	}
	
	public ArrayList<String> getFeatures(){
		return m_f;
	}
	
	public String getFeaturesAsString(){
		String features = "";
		if (m_f.size() > 0) {
			for (String feature: m_f) {
				features += feature + "_";
			}
			features = features.substring(0, features.lastIndexOf("_"));
		}
		//System.out.println("features: " + features);
		return features;
	}
	
//	public void setIndexPath(String indexPath) {
//		IndexPath = indexPath;
//	}
//
//	public String getIndexPath() {
//		return IndexPath;
//	}
	
	/*****************END SESSION SETTINGS********************/

}
