package NotDienst.client.planer;


import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;

import NotDienst.client.nc.Arzt;
import NotDienst.client.nc.ChartView;
import NotDienst.client.nc.ConfigReader;
import NotDienst.core.util.CSVtoTable;

public class KollegenListeDrucker extends JFrame {

	private CSVtoTable mModel;


	public KollegenListeDrucker() throws IOException, DocumentException{
		super("KollegenListeDrucker");
		if(ConfigReader.getInstance().getConfig("geplant").equals("-")){
			JOptionPane.showMessageDialog(this, "Erst einteilen");
			return;
		}
		String geplantePeriode = ConfigReader.getInstance().getConfig("geplant");
		mModel = new CSVtoTable("C:/ntdst/data/", geplantePeriode, true);
		ChartView cv = new ChartView();
		cv.setVisible(false);
		double anzahlAerzte = cv.getAnzahlAerzte();
		double gesamtPunkte = cv.getGesamtPunkte();
		Vector<Arzt> alleAerzte=cv.getAlleAerzte();
		HashMap<Integer,Arzt> hm = new HashMap<Integer,Arzt>();
		for(int i=0;i<alleAerzte.size();i++){
			hm.put(alleAerzte.get(i).getAid(), alleAerzte.get(i));
		}
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF File(.pdf)","pdf");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(this);
		//		if(returnVal==0){
		//			return;
		//		}
		File p = fc.getSelectedFile();
		//		p.createNewFile();
		//		FileWriter writer = new FileWriter(p ,true);
		//		for(int i=1;i<mModel.getRowCount();i++){
		//
		//			writer.write("Datum: "+mModel.getValueAt(i, 0)+"  Typ: "+mModel.getValueAt(i, 1)+"  Punkte: "+mModel.getValueAt(i, 2)+"  "+hm.get(Integer.parseInt(""+mModel.getValueAt(i, 3))).toStringA());
		//			writer.write(System.getProperty("line.separator"));
		//			writer.write("____________________________________________________________________________________________");
		//			writer.write(System.getProperty("line.separator"));
		//		}
		//		writer.close();
		p.createNewFile();
		Document d = new Document();
		
		PdfWriter.getInstance(d, new FileOutputStream(p));
		d.open();
		Object[] l= (Object[]) hm.values().toArray();
		for(int i=0;i<l.length;i++){ 
			Paragraph paragraph = new Paragraph("Datum: "+mModel.getValueAt(i, 0)+"  Typ: "+mModel.getValueAt(i, 1)+"  Punkte: "+mModel.getValueAt(i, 2)+"  "+
												hm.get(Integer.parseInt(""+mModel.getValueAt(i, 3))).toStringA()+"\n"+
												"_____________________________________________________________________________" +"\n"
												);
			paragraph.setAlignment(Element.ALIGN_LEFT);
			Font f = new Font(Font.FontFamily.COURIER, 7);
			paragraph.setFont(f);
			d.add(paragraph);
		}
		d.close();


	}

}


