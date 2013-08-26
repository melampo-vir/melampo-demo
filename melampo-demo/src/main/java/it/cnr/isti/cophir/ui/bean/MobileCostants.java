package it.cnr.isti.cophir.ui.bean;

public class MobileCostants {
	
	private static String applicationRootPath;
	private final static String TEMP_IMG_SUBDIR = "temp/img";
	
	private static String facebookURLPrefix;

	public static void setRootPath(String applicationRootPath)  {
		MobileCostants.applicationRootPath = applicationRootPath;
	}
	
	public static String getRootPath() {
		return applicationRootPath;
	}
	
	public static String getFacebookURLPrefix() {
		return facebookURLPrefix;
	}
	
	public static String getTempImgDir() {
		return TEMP_IMG_SUBDIR;
	}

		
}
