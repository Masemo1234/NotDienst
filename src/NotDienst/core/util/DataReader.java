package NotDienst.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class DataReader {
	
	
	
	public DataReader(){
		
	}
	
	public String read(String param) throws FileNotFoundException{
		String ergebnis="";
		String path = "C:/ntdst/data/cfg/";
		File f = new File(path+param+".txt");
		if(!f.exists()){
			return "0";
		}
		FileReader fr = new FileReader(f);

		BufferedReader br = new BufferedReader(fr);
		String newLine = null;
		try {
			newLine = br.readLine();
			System.out.println(newLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(newLine!=null){
			ergebnis= ergebnis+"\n"+newLine;
			try {
				newLine = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		
		
		
		return ergebnis;
		
	}

}
