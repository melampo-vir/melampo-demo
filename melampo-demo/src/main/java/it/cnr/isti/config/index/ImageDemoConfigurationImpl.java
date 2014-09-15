package it.cnr.isti.config.index;

import java.io.File;

public class ImageDemoConfigurationImpl extends IndexConfigurationImpl implements ImageDemoConfiguration{	

	protected static final String SUFFIX_URLS_CSV = ".urls.csv";
	protected static final String PROP_IMAGE_CONTROLLER = "image.controller.class";
	
	
	public ImageDemoConfigurationImpl() {
		//call initialization through the super implementation
		super();
	}
	
	@Override
	public String getComponentName() {
		return "image-demo";
	}
		
	@Override
	public File getDatasetUrlsFile(String dataset){
		if(dataset == null)
			return new File(getDatasetsFolderAsFile(), getDefaultDataset() + SUFFIX_URLS_CSV);
		else 
			return new File(getDatasetsFolderAsFile(), dataset + SUFFIX_URLS_CSV);
	}

	
	@Override
	public String getImageControllerClass() {
		return getConfigProperty(PROP_IMAGE_CONTROLLER);
	}
}
