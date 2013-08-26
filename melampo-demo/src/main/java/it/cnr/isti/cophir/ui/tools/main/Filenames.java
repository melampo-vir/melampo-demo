package it.cnr.isti.cophir.ui.tools.main;

import it.cnr.isti.cophir.ui.tools.UITools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Filenames {
	
	public static void main(String[] args) {
		String filenames = file2String(new File("D:/demo/assets/europeanauri.txt"));
		try {
			UITools.string2File(filenames, new File("filenames.assets.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String file2String(File file) {
		StringBuffer content = new StringBuffer();
		String riga = "";
		int carriageReturn = 1;
		Reader leggiDati = null;
		BufferedReader leggiRiga = null;
		try {
			leggiDati = new FileReader(file);
			leggiRiga = new BufferedReader(leggiDati);
			int index = 0;
			while((riga = leggiRiga.readLine()) != null) 			
			content.append("photo").append(index++).append(" = ").append(riga).append("\r\n");	
			content.replace(content.length()- carriageReturn, content.length()- carriageReturn, "");
		} catch (IOException e) {e.printStackTrace();}
		finally {
			try {
				if (leggiRiga != null)
					leggiRiga.close();
				if (leggiDati != null)
					leggiDati.close();
			} catch (IOException ex){ex.printStackTrace();}
		}
		return content.toString();
	}

}
