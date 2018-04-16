package NotDienst.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class CSVtoTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5697587210140518342L;
	private String mPathData;
	private String prefix;
	private String filter;
	Vector<Vector<String>> data;
	private String[] columnNames;


	public CSVtoTable(String pPathData, String pPrefix){
		super();
		mPathData = pPathData;
		prefix = pPrefix;
		data = new Vector<Vector<String>>();
		try {
			loadData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public CSVtoTable(String pPathData, String pPrefix, String filter){
		super();
		mPathData = pPathData;
		prefix = pPrefix;
		this.filter= filter;
		data = new Vector<Vector<String>>();
		try {
			loadData(filter);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public CSVtoTable(String pPathData, String pPrefix, boolean b) {
		super();
		mPathData = pPathData;
		prefix = pPrefix;
		data = new Vector<Vector<String>>();
		try {
			loadData(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void loadData(boolean b) throws FileNotFoundException {
		Vector<String> loadedData = new Vector<String>();
		System.out.println("loading: "+mPathData+prefix+".csv");
		FileReader fr = new FileReader(mPathData+prefix+".csv");
		BufferedReader br = new BufferedReader(fr);
		String newLine = null;
		try {
			newLine = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(newLine!=null){
			loadedData.add(newLine);
			try {
				newLine = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.columnNames=loadedData.get(0).split(";");
		for(int i = 1;i<loadedData.size();i++){
			String[] splitted = loadedData.get(i).split(";");
			Vector<String> dataVec = new Vector<String>();
			for(int x = 0 ;x<splitted.length;x++){
				dataVec.add(splitted[x]);
			}
			
			data.add(dataVec);
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
		
	}

	private void loadData(String filter2) throws FileNotFoundException {
		Vector<String> loadedData = new Vector<String>();
		File f= new File(mPathData+prefix+".csv");
		if(!f.exists()){
			Vector<String> b = new Vector<String>();
			b.add("File not found");
			data.addElement(b);
			return;
		}
		System.out.println("loading: "+mPathData+prefix+".csv");
		FileReader fr = new FileReader(mPathData+prefix+".csv");

		BufferedReader br = new BufferedReader(fr);
		String newLine = null;
		try {
			newLine = br.readLine();
			System.out.println(newLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(newLine!=null){
			loadedData.add(newLine);
			try {
				newLine = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.columnNames=loadedData.get(0).split(";");
		for(int i = 1;i<loadedData.size();i++){
			String[] splitted = loadedData.get(i).split(";");
			Vector<String> dataVec = new Vector<String>();
			for(int x = 0 ;x<splitted.length;x++){
				dataVec.add(splitted[x]);
			}
			if (dataVec.get(3).equals(filter2))
				data.add(dataVec);
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}

	private void loadData() throws FileNotFoundException {

		Vector<String> loadedData = new Vector<String>();
		System.out.println("loading: "+mPathData+prefix+".csv");
		FileReader fr = new FileReader(mPathData+prefix+".csv");
		BufferedReader br = new BufferedReader(fr);
		String newLine = null;
		try {
			newLine = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(newLine!=null){
			loadedData.add(newLine);
			try {
				newLine = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.columnNames=loadedData.get(0).split(";");
		for(int i = 1;i<loadedData.size();i++){
			String[] splitted = loadedData.get(i).split(";");
			Vector<String> dataVec = new Vector<String>();
			for(int x = 0 ;x<splitted.length;x++){
				dataVec.add(splitted[x]);
			}
			data.add(dataVec);
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public void reloadData(String pPathData, String pPrefix) {
		mPathData = pPathData;
		prefix = pPrefix;
		data.clear();
		try {
			loadData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void reloadData(String pPathData, String pPrefix, String filter) {
		mPathData = pPathData;
		prefix = pPrefix;
		data.clear();
		try {
			loadData(filter);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String getColumnName(int column) {
		System.out.println(columnNames[column]);
		return columnNames[column];
	}


	@Override
	public int getColumnCount() {
		return data.get(0).size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row).get(col);
	}

	public void setValueAt(String value,int row, int col){
		data.get(row).set(col, value);
		return;
	}

	public void write() throws IOException {
		// TODO Auto-generated method stub
		System.out.println(mPathData+prefix+".csv");
		File dataFile= new File(mPathData+prefix+".csv");
		File oldFile= new File(mPathData+prefix+".old");
		
		try {
			Files.move(dataFile.toPath(), oldFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println("Fehler beim bewegen der datei");
			e.printStackTrace();
		}
		dataFile.createNewFile();
		FileWriter writer = new FileWriter(dataFile ,true);
		writer.write("Tag;Typ;Punkte;AID");
		writer.write(System.getProperty("line.separator"));
		for(int i=0;i<data.size();i++){
			for(int x=0;x<data.get(i).size()-1;x++){
				writer.write(data.get(i).get(x)+";");
			}
		writer.write(data.get(i).get(data.get(i).size()-1));
		writer.write(System.getProperty("line.separator"));
		}
		writer.close();
		return;

	}

}
