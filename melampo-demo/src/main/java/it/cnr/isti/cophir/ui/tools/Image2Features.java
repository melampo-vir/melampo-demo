package it.cnr.isti.cophir.ui.tools;

import it.cnr.isti.cophir.ui.bean.Parameters;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

@Deprecated 
/**
 * This uses a webservice based implementation.
 * A stand alone implementation is available in INdexBuilder:
 * it.cnr.isti.featuresExtraction.Image2Features
 * 
 * @author Sergiu Gordea 
 *
 */
public class Image2Features {

	public static String BASE_URI;
	private it.cnr.isti.feature.extraction.Image2Features featureExtractor;
	
	static {
		try {
			BASE_URI = UITools.getProperty(Parameters.getUIConfigFile().getPath(), "img2FeaturesService").trim();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Client c;
	private WebResource service;
	private String queryURL;
	
	public Image2Features() {
		//TODO: replace implementation
//		c = Client.create();
//		service = c.resource(BASE_URI);
		File confDir = Parameters.getUIConfigFile().getParentFile();
		//.getPath();
		try {
			featureExtractor = new it.cnr.isti.feature.extraction.Image2Features(confDir);
		} catch (Exception e) {
			throw new RuntimeException("cannot instantiate feature extractor", e);
		}
	}

	public String image2Features(InputStream imgStream) throws IOException {
		byte[] imgBytes = UITools.inputStream2ByteArray(imgStream);
		String res = imgByte2Features(imgBytes);
		return res;
	}

	public String image2Features(File imgFile) throws IOException {
		byte[] imgBytes = UITools.getBytesFromFile(imgFile);
		String res = imgByte2Features(imgBytes);
		return res;
	}

	private String imgByte2Features(byte[] imgBytes) {
		queryURL = null;
		
		 FormDataMultiPart fdmp = new FormDataMultiPart(); 
			//FileDataBodyPart fdp = new FileDataBodyPart("imgFile", file);  
			FormDataBodyPart fdp = new FormDataBodyPart("imgFile", imgBytes, MediaType.MULTIPART_FORM_DATA_TYPE);  
			FormDataBodyPart formDataBodyPart = new FormDataBodyPart("features", "MPEG7");
			fdmp.bodyPart(fdp);
			fdmp.bodyPart(formDataBodyPart);
		    
		    // POST the request 
		    ClientResponse response = service.path("/FeatureExtractionService"). 
		      type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, fdmp); 
		
		String res = response.getEntity(String.class);
		//System.out.println("Response Status : " + res);
		String features = null;
		
		if (res != null) {
			String[] values = res.split("axrdstyaityfajhgf");
			if (values != null && values.length == 2) {
				queryURL = values[0];
				features = values[1];
			}
		}
		
		System.out.println("queryURL: " + queryURL);
		
		// System.out.println("Response Status : " + res);
		return features;
	}
	
	/*private String imgByte2Features(byte[] imgBytes) {
		queryURL = null;
		MultiPart multiPart = new MultiPart().bodyPart(new BodyPart(imgBytes,
				MediaType.APPLICATION_OCTET_STREAM_TYPE));

		// POST the request
		ClientResponse response = service.path("/FeatureExtractionService")
				.type("multipart/mixed").post(ClientResponse.class, multiPart);
		String res = response.getEntity(String.class);
		//System.out.println("Response Status : " + res);
		String features = null;
		
		if (res != null) {
			String[] values = res.split("axrdstyaityfajhgf");
			if (values != null && values.length == 2) {
				queryURL = values[0];
				features = values[1];
			}
		}
		
		System.out.println("queryURL: " + queryURL);
		
		// System.out.println("Response Status : " + res);
		return features;
	}*/

	public String imgURL2Features(String imgURL) {
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("url", imgURL);
		queryParams.add("features", "MPEG7");
//		ClientResponse response = service.path("/FeatureExtractionService/url")
//				.queryParams(queryParams).get(ClientResponse.class);
		
		String res;
		try {
			res = featureExtractor.extractFeatures(new URL(imgURL));
		} catch (Exception e) {
			throw new RuntimeException("cannot extract feature from URL", e);
		}
		//String res = response.getEntity(String.class);
		//System.out.println("Response Status : " + res);
		
		String features = null;
		
		//TODO: improve this
		if (res != null) {
			String[] values = res.split("axrdstyaityfajhgf");
			if (values != null && values.length == 2) {
				queryURL = values[0];
				features = values[1];
			}else{
				//query by URL
				queryURL = imgURL;
				features = res;
			}
		}
		
		System.out.println("queryURL: " + queryURL);
		
		// System.out.println("Response Status : " + res);
		return features;
	}
	
	public String getQueryURL() {
		return queryURL;
	}
	
	  public static void moveImgToFolder(String imgURL, String folderName) { 
		    Client client = Client.create(); 
			 WebResource service = client.resource(BASE_URI);

			 MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
			   queryParams.add("url", imgURL);
			   queryParams.add("folderName", folderName);
			   
			   ClientResponse response = service.path("/FeatureExtractionService/moveImgToFolder").queryParams(queryParams).get(ClientResponse.class);		  
			    //System.out.println("Response Status : " + response.getEntity(String.class)); 
		  }
}