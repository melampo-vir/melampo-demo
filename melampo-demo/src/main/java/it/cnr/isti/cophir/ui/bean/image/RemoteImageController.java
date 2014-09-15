package it.cnr.isti.cophir.ui.bean.image;

import it.cnr.isti.exception.TechnicalRuntimeException;

public class RemoteImageController implements ImageController {

	@Override
	public String getImageUrl(String dataset, String thumbnailId, String applicationUrl) {
		// TODO Auto-generated method stub
		throw new TechnicalRuntimeException("Not supported yet!", null);
	}

	@Override
	public String getImageUrl(String dataset, String thumbnailId) {
		// TODO Auto-generated method stub
		return null;
	}

}
