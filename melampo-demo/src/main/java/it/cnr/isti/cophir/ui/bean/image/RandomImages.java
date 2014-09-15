package it.cnr.isti.cophir.ui.bean.image;

import it.cnr.isti.indexer.IndexHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class RandomImages {

	private Random random = new Random();
//	private String rootPath = "";

	public RandomImages() {
	}

//	public RandomImages(String rootPath) {
//		this.rootPath = rootPath;
//	}

	private int numberOfImages = -1;
	// private static final String PHOTO_PREFIX = "photo";
	private static final String ID_PREFIX = "id";

	private Hashtable<Integer, String> assignedIds = new Hashtable<Integer, String>();

	private static Map<String, String> thumbnails;
	private static String[] idArray = null;

	Properties filenamesListProps = null;
	FileInputStream fIS = null;

//	public synchronized void openProps() throws IOException {
//		openProps(new File (FILENAME_LIST));
//	}

	
	public synchronized void openProps(File filename) throws IOException {
		// assignedIds.clear();
		// filenamesListProps = new Properties();
		// try {
		// fIS = new FileInputStream(new File(this.rootPath + File.separator +
		// filename));
		// filenamesListProps.load(fIS);
		// numberOfImages = filenamesListProps.size() /2;
		// } catch (Exception e) {e.printStackTrace();}
		assignedIds.clear();
		if (thumbnails == null) {
			thumbnails = getThumbnailsMap(filename);
			idArray = thumbnails.keySet()
					.toArray(new String[thumbnails.size()]);
		}
		
	}

	public synchronized Map<String, String> getThumbnailsMap(File filePath)
			throws IOException {
		if(thumbnails == null)
			thumbnails = (new IndexHelper()).getThumbnailsMap( filePath);
		
		return thumbnails;
	}

	public synchronized void closeProps() {
		try {
			if (fIS != null)
				fIS.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getSapirRandomID() {
		int index = -1;
		do {
			index = random.nextInt(numberOfImages);
		} while (assignedIds.containsKey(index));

		String id = filenamesListProps.getProperty(ID_PREFIX + index);
		assignedIds.put(index, id);

		return id;
	}

	int getRandomIndex() {
		int index = -1;
		do {
			numberOfImages = thumbnails.size();
			index = random.nextInt(numberOfImages);
		} while (assignedIds.containsKey(index));

		return index;
	}

	public String getRandomId() {
		return idArray[getRandomIndex()];
	}

	public String getThumbnailUrl(String thumbId) {
		return thumbnails.get(thumbId);
	}

}
