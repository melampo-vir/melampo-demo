package it.cnr.isti.config.index;

import java.io.File;

public interface ImageDemoConfiguration extends IndexConfiguration{

	public abstract File getDatasetUrlsFile(String dataset);
	
	public abstract String getImageControllerClass();
	

}
