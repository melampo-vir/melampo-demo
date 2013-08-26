package it.cnr.isti.cophir.ui.tools;

import it.cnr.isti.cophir.ui.bean.QueryComposer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class UITools {
	
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		try {
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
		} finally {
			if (baos != null)
				baos.close();
		}
		return baos.toString();
	}
	
	public static byte[] inputStream2ByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		try {
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
		} finally {
			if (baos != null)
				baos.close();
		}
		return baos.toByteArray();
	}
	
	public static String getVisualDescriptor(String mpeg7Image) throws JDOMException, IOException {
		StringBuilder sb = new StringBuilder();
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new StringReader(mpeg7Image));
			Element root = doc.getRootElement();
			Namespace ns = root.getNamespace();
			List<Element> visualDescriptorList = root.getChildren(
					"VisualDescriptor", ns);
			for (Element el : visualDescriptorList) {
				sb.append(new XMLOutputter(Format.getPrettyFormat())
						.outputString(el));
			}
		return sb.toString();
	}
	
	public static String getMatchedGroup(String pattern, String matcher,
			int index) {
		Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
		Matcher m = p.matcher(matcher);
		if (m.find()) {
			return m.group(index);
		}
		return null;
	}
	
	public static String getLicenseURL(String license) {
		String licenseURL = null;
		
		if (license != null && !license.trim().equals("")) {
			int licenseNumber = Integer.parseInt(license.trim());
			
			switch (licenseNumber) {
			case 1:
				licenseURL = "http://creativecommons.org/licenses/by-nc-sa/2.0/deed.en";
				
				break;
			case 2:
				licenseURL = "http://creativecommons.org/licenses/by-nc/2.0/deed.en";
				
				break;
			case 3:
				licenseURL = "http://creativecommons.org/licenses/by-nc-nd/2.0/deed.en";
				
				break;
			case 4:
				licenseURL = "http://creativecommons.org/licenses/by/2.0/deed.en";
				
				break;
			case 5:
				licenseURL = "http://creativecommons.org/licenses/by-sa/2.0/deed.en";
				
				break;
			case 6:
				licenseURL = "http://creativecommons.org/licenses/by-nd/2.0/deed.en";
				
				break;

			default:
				break;
			}
		}
		
		return licenseURL;
	}
	
	public static int countOccurrences(String base, String searchFor) {
	  int len = searchFor.length();
	  int result = 0;
	  if (len > 0) {  
	  int start = base.indexOf(searchFor);
	  while (start != -1) {
	            result++;
	            start = base.indexOf(searchFor, start+len);
	        }
	    }
	  return result;
	}
	
    public static void string2File(String text, File file) throws IOException {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(text);
		} finally {
				if (fileWriter != null) 
					fileWriter.close();
		}
	}
    
    public static String getProperty(String propertyFile, String prop) throws IOException {
		Properties props = new Properties();
		String value = "";
		FileInputStream fIS = null;
		try {
			fIS = new FileInputStream(new File(propertyFile));
			props.load(fIS);
			value = props.getProperty(prop);
		} finally {
				if (fIS != null)
					fIS.close();
		}
		return value;
	}
    
	private static Random random = new Random();
	private static final int GUID_LENGTH = 8;
	
	public static String randomGUID() {
		String guid = "";
		for (int i = 0; i < GUID_LENGTH; i++) {
			guid += Long.toHexString(random.nextInt(16));
		}
		return guid;
	}
	
	public static String file2String(File source) throws IOException {
		String content = "";
		DataInputStream dis = null;
		final byte[] buffer = new byte[(int) source.length()];
		try {
			dis = new DataInputStream(new BufferedInputStream(
					new FileInputStream(source)));

			dis.readFully(buffer);
			content = new String(buffer, "UTF-8");
		} finally {
				if (dis != null)
					dis.close();
		}
		return content;
	}
	
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		// Get the size of the file
		long length = file.length();
		// You cannot create an array using a long type.
		// It needs to be an int type. // Before converting to an int type,
		// check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		} // Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];
		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		try {
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}
		} finally {
			// Close the input stream and return bytes
			if (is != null)
			is.close();
		}
		return bytes;
	}

}
