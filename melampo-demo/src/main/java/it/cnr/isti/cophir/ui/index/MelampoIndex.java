package it.cnr.isti.cophir.ui.index;

import it.cnr.isti.config.index.ImageDemoConfigurationImpl;
import it.cnr.isti.config.index.IndexConfiguration;
import it.cnr.isti.cophir.ui.bean.LoggingInfo;
import it.cnr.isti.cophir.ui.bean.Parameters;
import it.cnr.isti.melampo.index.searching.MelampoSearcherHub;
import it.cnr.isti.melampo.vir.exceptions.VIRException;

import java.io.IOException;
import java.util.ArrayList;

public class MelampoIndex implements Index_IF {
	
	MelampoSearcherHub m_lucignoloSearcher;
	long m_time;
	
	protected Parameters settings;
	protected LoggingInfo loggingInfo;
	
	public MelampoIndex(Parameters settings, LoggingInfo loggingInfo) {
		this.settings = settings;
		this.loggingInfo = loggingInfo;	
		
		try {
			//String index = settings.getIndexPath();
			
			//if (m_lucignoloSearcher == null){
			m_lucignoloSearcher = new MelampoSearcherHub();
			IndexConfiguration indexConfiguration = getIndexConfiguration();
			m_lucignoloSearcher.openIndices(indexConfiguration.getIndexConfFolder(null).getAbsoluteFile());
			//m_lucignoloSearcher.openIndices(new File(Parameters.UI_HOME));
			//}
			//System.out.println("using index "+index);
		} catch (VIRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	protected IndexConfiguration getIndexConfiguration() {
		IndexConfiguration indexConfiguration = new ImageDemoConfigurationImpl();
		return indexConfiguration;
	}

	
	@Override
	public String getIndexParameter(String parameterName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] getResults(int startFrom, int numElements) {
		// TODO Auto-generated method stub
		String[][] retval = null;
		try {
			retval = m_lucignoloSearcher.getResults(startFrom, numElements);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retval;
	}

	@Override
	public long getSearchTime() {
		// TODO Auto-generated method stub
		return m_time;
	}

	@Override
	public void query(String query) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String search(String[] values, String[] fields) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		
		ArrayList<String> features = settings.getFeatures();
		
		boolean notextfield = true;
		boolean isQueryID = false;
		
			ArrayList<String> vals = new ArrayList<String>();
			ArrayList<String> flds = new ArrayList<String>();
			
			for(int i=0;i<values.length;i++){
				if (fields[i].equals("Image") || fields[i].equals("id")){
					
					for (String feature: features) {
						flds.add(feature);
						vals.add(values[i]);
					}
					
					
					if (fields[i].equals("id")) {
						isQueryID = true;
						loggingInfo.setEuropeanaUri(values[i]);
					}
					//TODO: correct this
					if (fields[i].equals("Image")) {
						//overwrite ID when searching with URL
						isQueryID = false;
						loggingInfo.setEuropeanaUri(values[i]);
					}
					
				}else if (fields[i].equals("text")){
					notextfield = false;
					flds.add(it.cnr.isti.melampo.index.Parameters.TEXT);
					vals.add(values[i]);
					loggingInfo.setTextQuery(values[i]);
				}				
			}
			
			for(String value: fields) 
				System.out.print(value + " ");
				System.out.println();
				
				for(String value: values) 
					System.out.print(value + " ");
					System.out.println();	
			
			try {
				m_lucignoloSearcher.query(vals, flds, isQueryID);
			} catch (VIRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		m_time = System.currentTimeMillis()-start;
		loggingInfo.setSearchTime(m_time);
		
		return "Have a nice day";
	}


	@Override
	public void setIndexParameter(String parameterName, String parameterValue) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getYiImageCode(String ID) {
		String r="";
		
//		try {
//			
//			int d = m_lucignoloSearcher.GetDocIDFromURI(ID);
//			r = m_lucignoloSearcher.GetYiEncFromDocID(d);
//
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
		
		return r;
	}

}
