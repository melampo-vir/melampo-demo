package it.cnr.isti.indexer;

import it.cnr.isti.config.index.ImageDemoConfigurationImpl;
import it.cnr.isti.exception.ImageIndexingException;
import it.cnr.isti.exception.TechnicalRuntimeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class ImageIndexingMain {

	private ImageIndexing test;
	IndexHelper helper = new IndexHelper(); 
	
	public static void main(String args[]) {
		ImageIndexingMain test = new ImageIndexingMain();
		try {
			test.openIndex();
			//test.insertImageObjects();
			//test.insertImageObjectsFromFile("/Rijksmuseum-portrets_90402_M_NL_Rijksmuseum.csv");
			//test.insertImageObjectsFromFile("/Rijksmuseum-miniatur_90402_M_NL_Rijksmuseum.csv");
//			test.insertImageObjectsFromFile("/NHM-LISABON-butterfly_2023901_Ag_EU_NaturalEurope_all.csv");
//			test.insertImageObjectsFromFile("/MIMO-trompe_09102_Ag_EU_MIMO_ESE.csv");
//			test.insertImageObjectsFromFile("/Galileo-optic_02301_Ag_IT_MG_catalogue.csv");
//			test.insertImageObjectsFromFile("/Galileo-electric_02301_Ag_IT_MG_catalogue.csv");
//			test.insertImageObjectsFromFile("/Teylers-eagle_10106_Ag_EU_STERNA_48.csv");
//			test.insertImageObjectsFromFile("/Teylers-woodpecker_10106_Ag_EU_STERNA_48.csv");
//			test.insertImageObjectsFromFile("/Teylers-duck_10106_Ag_EU_STERNA_48.csv");
//			test.insertImageObjectsFromFile("/Rijksmuseum-porcelain_90402_M_NL_Rijksmuseum.csv");
//			test.insertImageObjectsFromFile("/Rijksmuseum-drawing-lanscape_90402_M_NL_Rijksmuseum.csv");
//			test.insertImageObjectsFromFile("/Rijksmuseum-bottles_90402_M_NL_Rijksmuseum.csv");
//			test.insertImageObjectsFromFile("/Rijksmuseum-landscape_90402_M_NL_Rijksmuseum.csv");
			Long start = System.currentTimeMillis();
			test.insertImageObjectsFromFile("/dataset.csv");
			Long end = System.currentTimeMillis();
			System.out.println("Indexing time: " + (end - start));
			
			start = System.currentTimeMillis();  
			test.closeIndex();
			end = System.currentTimeMillis();
			
			System.out.println("Close index time: " + (end - start));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void openIndex() throws IOException {

		test = new ImageIndexing(null, new ImageDemoConfigurationImpl());

		try {
			test.openIndex();
		} catch (ImageIndexingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertImageObject() throws IOException {
		try {
			InputStream imageObj = new FileInputStream(new File(
					"../src/test/resources/indexhome/datasets/testImage.jpg"));
			test.insertImage("img1", imageObj);
		} catch (Exception e) {
			throw new TechnicalRuntimeException("cannot index test image", e);
		} 
	}

	public void closeIndex() {
		try {
			test.closeIndex();
		} catch (ImageIndexingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertImageObjectsFromFile(String filePath) throws IOException {

		Map<String, String> thumbsMap = helper.getThumbnailsMap(filePath);
		int indexedItems = 0;
		int skippedItems = 0;

		for (Map.Entry<String, String> thumbnail : thumbsMap.entrySet()) {
			try {

//				InputStream imageObj = new FileInputStream(new File(
//						getMelampoHome() + "/index/testImage.jpg"));
				test.insertImage(thumbnail.getKey(), new URL(thumbnail.getValue()));
				indexedItems++;
			
			} catch (Exception e) {
				System.out.println("Image indexing errors occured: " + e.getMessage());
				System.out.println("skip image: " + thumbnail.getKey() + "->" + thumbnail.getValue());
				
				//e.printStackTrace();
				skippedItems++;
			}
		}
		System.out.println("Items successfully inserted in index: " + indexedItems);
		System.out.println("Skipped Items: " + skippedItems);
	}
	
}
