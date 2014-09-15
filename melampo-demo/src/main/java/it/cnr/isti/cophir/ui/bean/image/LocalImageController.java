package it.cnr.isti.cophir.ui.bean.image;


public class LocalImageController implements ImageController{

	@Override
	public synchronized String getImageUrl(String dataset, String thumbnailId, String applicationUrl) {

		StringBuilder imageUrl = new StringBuilder(applicationUrl);
		if(!applicationUrl.endsWith("/"))
			imageUrl.append("/");
		
		imageUrl.append("datasets/");
		imageUrl.append(dataset);
		imageUrl.append("/image");
		imageUrl.append(thumbnailId).append(".jpg");
		
		return imageUrl.toString();
	}

	@Override
	public String getImageUrl(String dataset, String thumbnailId) {
		return getImageUrl(dataset, thumbnailId, "./");
	}

}
