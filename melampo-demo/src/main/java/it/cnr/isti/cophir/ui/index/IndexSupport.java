package it.cnr.isti.cophir.ui.index;

import it.cnr.isti.cophir.ui.bean.Parameters;
import it.cnr.isti.cophir.ui.tools.UITools;

import java.io.IOException;

public class IndexSupport {

	private static String thumbServer;

	static {
		try {
			//TODO: change this
			thumbServer = UITools.getProperty(
					Parameters.getUIConfigFile().getPath(), "thumbServer")
					.trim();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getThumbnailUrl(String id) {
		String thumbnailURL = null;
		if (id != null) {
			thumbnailURL = thumbServer + "/" + id + ".jpg";
			
		}
		return thumbnailURL;
	}

}
