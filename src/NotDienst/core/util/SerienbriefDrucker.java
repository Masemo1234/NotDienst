package NotDienst.core.util;


import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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
import NotDienst.client.planer.ntdstDate;
import NotDienst.core.util.CSVtoTable;

public class SerienbriefDrucker extends JFrame {

	private CSVtoTable mModel;


	@SuppressWarnings("deprecation")
	public SerienbriefDrucker() throws IOException, DocumentException{
		super("SerienbriefDrucker");
		if(ConfigReader.getInstance().getConfig("geplant").equals("-")){
			JOptionPane.showMessageDialog(this, "Erst einteilen");
			return;
		}
		String geplantePeriode = ConfigReader.getInstance().getConfig("geplant");
		mModel = new CSVtoTable("C:/ntdst/data/", geplantePeriode, true);
		System.out.println("periode geladen");
		ChartView cv = new ChartView();
		cv.setVisible(false);
		double anzahlAerzte = cv.getAnzahlAerzte();
		double gesamtPunkte = cv.getGesamtPunkte();
		Vector<Arzt> alleAerzte=cv.getAlleAerzte();
		HashMap<Integer,Arzt> hm = new HashMap<Integer,Arzt>();
		for(int i=0;i<alleAerzte.size();i++){
			hm.put(alleAerzte.get(i).getAid(), alleAerzte.get(i));
		}
		Vector<ntdstDate> dateVector = new Vector<ntdstDate>();
		for(int i = 0;i<mModel.getRowCount();i++){
			int erstellungsNr = Integer.parseInt(""+mModel.getValueAt(i,2));
			int punkte = Integer.parseInt(""+mModel.getValueAt(i,3));
			ntdstDate date = new ntdstDate(""+mModel.getValueAt(i,0),""+mModel.getValueAt(i,1) , 0, Integer.parseInt(""+mModel.getValueAt(i,2)),Integer.parseInt(""+mModel.getValueAt(i,3)));
			dateVector.add(date);
		}
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date da = new Date();
		String[] datumAnfangSplit = dateVector.get(0).getDate().split("-");
		GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(datumAnfangSplit[0]), Integer.parseInt(datumAnfangSplit[1]), Integer.parseInt(datumAnfangSplit[2]));
		String datumAnfang=dateFormat.format(gc.getTime());
		datumAnfangSplit = dateVector.get(dateVector.size()-1).getDate().split("-");
		gc = new GregorianCalendar(Integer.parseInt(datumAnfangSplit[0]), Integer.parseInt(datumAnfangSplit[1]), Integer.parseInt(datumAnfangSplit[2]));
		String datumEnde=dateFormat.format(gc.getTime());
		
		for(int i = 0;i<dateVector.size();i++){
			Arzt a = hm.get(dateVector.get(i).getaId());
			ntdstDate d = dateVector.get(i);
			System.out.println(d.toString());
			System.out.println(a.toString());
			a.addDate(d);
			hm.put(dateVector.get(i).getaId(), a);
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
		DataReader dr = new DataReader();
		
		
		Object[] l= (Object[]) hm.values().toArray();
		for(int i=0;i<l.length;i++){ 
			Arzt a = (Arzt) l[i];
			Paragraph paragraph = new Paragraph(dr.read("Header_einteiler"));
    		paragraph.setAlignment(Element.ALIGN_LEFT);
    		Font f = new Font(Font.FontFamily.COURIER, 7);
    		paragraph.setFont(f);
    		d.add(paragraph);
    		paragraph = new Paragraph(dr.read("Header_Bezirksstellenobmann"));
    		paragraph.setAlignment(Element.ALIGN_RIGHT);
    		f = new Font(Font.FontFamily.COURIER, 7);
    		paragraph.setFont(f);
    		d.add(paragraph);
    		paragraph = new Paragraph(a.toStringAdresse());
    		paragraph.setAlignment(Element.ALIGN_LEFT);
    		f = new Font(Font.FontFamily.COURIER, 7);
    		paragraph.setFont(f);
    		d.add(paragraph);
    		String text =dr.read("Text_SerienBrief");
    		System.out.println("replacing aaaAnrede with "+a.getAnrede());
    		text=text.replace("aaaAnrede", a.getAnrede());
    		text=text.replace("aaaTitel", a.getTitel());
    		text=text.replace("aaaNachname", a.getNachname());
    		text=text.replace("aaaperiodeDatum", datumAnfang);
    		text=text.replace("aaaperiodeEnde", datumEnde);
    		String termine = "";
    		Vector<ntdstDate> v = a.getDates();
    		for(int j=0;j<v.size();j++){
    			ntdstDate nd = v.get(j);
    			String[] dateSplit = nd.getDate().split("-");
    			gc = new GregorianCalendar(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
    			String datum=dateFormat.format(gc.getTime());
    			termine=termine+nd.getTyp()+", "+datum+", "+nd.getPunkte()+"\n";
    		}
    		text=text.replace("aaaTermine", termine);

    		paragraph = new Paragraph(text);
    		paragraph.setAlignment(Element.ALIGN_LEFT);
    		f = new Font(Font.FontFamily.COURIER, 12);
    		paragraph.setFont(f);
    		d.add(paragraph);
    		dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    		Date date = new Date();
    		paragraph = new Paragraph("Bad Honnef, "+dateFormat.format(date));
    		paragraph.setAlignment(Element.ALIGN_RIGHT);
    		f = new Font(Font.FontFamily.COURIER, 5);
    		paragraph.setFont(f);
    		d.add(paragraph);
    		d.newPage();
		}
//		for(int i=1;i<mModel.getRowCount();i++){ 
//			Paragraph paragraph = new Paragraph("Datum: "+mModel.getValueAt(i, 0)+"  Typ: "+mModel.getValueAt(i, 1)+"  Punkte: "+mModel.getValueAt(i, 2)+"  "+
//												hm.get(Integer.parseInt(""+mModel.getValueAt(i, 3))).toStringA()+"\n"+
//												"_________________________________________________________________________________" +"\n"
//												);
//			paragraph.setAlignment(Element.ALIGN_LEFT);
//			Font f = new Font(Font.FontFamily.COURIER, 7);
//			paragraph.setFont(f);
//			d.add(paragraph);
//		}
		
		
		
		
		
		d.close();


	}

}


