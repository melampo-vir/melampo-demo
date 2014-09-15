package it.cnr.isti.cophir.ui.bean.image;


public interface ImageController {

	/**
	 * generating the URL of the image with the thumbnailId from the given dataset managed by the given application 
	 * @param dataset
	 * @param thumbnailId
	 * @param applicationUrl
	 * @return
	 */
	public String getImageUrl(String dataset, String thumbnailId, String applicationUrl);
	
	/**
	 * generating the image url by using the current folder ("./" ) as base application folder 
	 * @param dataset
	 * @param thumbnailId
	 * @return
	 */
	public String getImageUrl(String dataset, String thumbnailId);
}
