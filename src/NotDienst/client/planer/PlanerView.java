package NotDienst.client.planer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import NotDienst.client.nc.Arzt;
import NotDienst.client.nc.ChartView;
import NotDienst.client.nc.ConfigReader;
import NotDienst.client.nc.NotdienstCenterView;
import NotDienst.core.util.CSVtoTable;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.BoxLayout;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JScrollPane;

public class PlanerView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4474908195851975103L;



	NotdienstCenterView parent;
	private Vector<Arzt> unterDurchschnitt;
	private Vector<Arzt> ueberDurchschnitt;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_5;
	private JTextField textField_6;
	private JScrollPane scrollPane;
	private GregorianCalendar gc;
	private JTextField textField_4;
	private JTextField textField_7;
	private DefaultTableModel tm;
	private JTable jt;
	private JFrame jf;
	private JTable jt1;
	private JFrame jf1;
	private DefaultTableModel tm1;
	private JScrollPane scrollPane1;
	Vector<ntdstDate> alleTage;


	public PlanerView(NotdienstCenterView ncv){
		super("Perioden Planer");

		parent=ncv;
		prepareUI();


	}


	private void prepareUI() {


		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel label = new JLabel("Ende Periode: ");
		label.setBounds(10, 48, 70, 14);
		panel.add(label);
		this.setSize(new Dimension(1024, 620));
		textField = new JTextField();
		textField.setBounds(111, 43, 86, 25);
		textField.setText("01.01.1973");
		textField.setColumns(10);
		panel.add(textField);

		JLabel label_1 = new JLabel("Anfang Periode: ");
		label_1.setBounds(10, 12, 81, 14);
		panel.add(label_1);

		textField_1 = new JTextField();
		textField_1.setBounds(111, 7, 86, 25);
		textField_1.setText("01.01.1972");
		textField_1.setColumns(10);
		panel.add(textField_1);

		JLabel label_2 = new JLabel("Punkte Wochentag:");
		label_2.setBounds(10, 86, 116, 14);
		panel.add(label_2);

		textField_2 = new JTextField();
		textField_2.setBounds(111, 81, 86, 25);
		textField_2.setText("1");
		textField_2.setColumns(10);
		panel.add(textField_2);

		JLabel label_3 = new JLabel("Punkte Mittwoch: ");
		label_3.setBounds(10, 117, 138, 14);
		panel.add(label_3);

		JLabel label_4 = new JLabel("Punkte Freitag: ");
		label_4.setBounds(10, 136, 116, 14);
		panel.add(label_4);

		textField_3 = new JTextField();
		textField_3.setBounds(111, 111, 86, 25);
		textField_3.setText("2");
		textField_3.setColumns(10);
		panel.add(textField_3);

		JLabel lblPunkteSasoft = new JLabel("Punkte Sa,So,FT: ");
		lblPunkteSasoft.setBounds(10, 161, 153, 14);
		panel.add(lblPunkteSasoft);

		textField_5 = new JTextField();
		textField_5.setBounds(115, 156, 86, 25);
		textField_5.setText("5");
		textField_5.setColumns(10);
		panel.add(textField_5);

		JLabel lblPunkteBrueckentag = new JLabel("Punkte Brueckentag: ");
		lblPunkteBrueckentag.setBounds(10, 186, 138, 14);
		panel.add(lblPunkteBrueckentag);

		textField_6 = new JTextField();
		textField_6.setBounds(115, 181, 86, 25);
		textField_6.setText("4");
		textField_6.setColumns(10);
		panel.add(textField_6);

		JButton btnWeiter = new JButton("Weiter>");
		btnWeiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				makeVisible();
			}
		});
		btnWeiter.setBounds(111, 255, 89, 23);
		panel.add(btnWeiter);



		textField_4 = new JTextField();
		textField_4.setText("3");
		textField_4.setBounds(111, 133, 86, 20);
		panel.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblPunkteBesFt = new JLabel("Punkte bes. FT:");
		lblPunkteBesFt.setBounds(10, 211, 86, 14);
		panel.add(lblPunkteBesFt);

		textField_7 = new JTextField();
		textField_7.setText("6");
		textField_7.setBounds(115, 208, 86, 20);
		panel.add(textField_7);
		textField_7.setColumns(10);
		this.setVisible(true);
	}


	protected void makeVisible() {
		String[] datum = textField_1.getText().split("\\.");
		String[] datum1 = textField.getText().split("\\.");
		System.out.println(textField_1.getText());
		gc = new GregorianCalendar(Integer.parseInt(datum[2]), Integer.parseInt(datum[1])-1, Integer.parseInt(datum[0]));
		@SuppressWarnings("deprecation")
		GregorianCalendar endDate=new GregorianCalendar(Integer.parseInt(datum1[2]), Integer.parseInt(datum1[1]), Integer.parseInt(datum1[0]));
		Vector<ntdstDate> dates = new Vector<ntdstDate>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat wochentag= new SimpleDateFormat("EEEEEEEEE");
		int i=1;
		while(!gc.getTime().equals(endDate.getTime())){
			Date d = gc.getTime();
			System.out.println(gc.getTime()+" "+ wochentag.format(d));
			int punkte=0;
			if(wochentag.format(d).equals("Sonntag")||wochentag.format(d).equals("Samstag")){
				punkte=Integer.parseInt(textField_5.getText());
			}else if(wochentag.format(d).equals("Freitag")){
				punkte=Integer.parseInt(textField_4.getText());
			}else if(wochentag.format(d).equals("Mittwoch")){
				punkte=Integer.parseInt(textField_3.getText());
			}else{
				punkte=Integer.parseInt(textField_2.getText());
			}

			if(punkte==0){
				System.out.println("Fehler bei punktezuteilung");//sollte nicht eintreffen
				return;
			}
			ntdstDate nd = new ntdstDate(sdf.format(gc.getTime()), wochentag.format(d) , i, punkte);
			dates.add(nd);
			i++;
			gc.add(gc.DAY_OF_YEAR, 1);
		}
		//in ein dataarray einteilen
		String[][] dataArray= new String[dates.size()][3];
		for(int x=0;x<dates.size();x++){
			ntdstDate akt = dates.get(x);
			dataArray[x][0]=akt.getDate();
			dataArray[x][1]=akt.getTyp();
			dataArray[x][2]=""+akt.getPunkte();
		}
		String[] colNames = {
				"Datum","Typ","Punkte"
		};
		tm = new DefaultTableModel(dataArray, colNames);
		jt = new JTable(tm);
		jf = new JFrame();

		scrollPane = new JScrollPane(jt);
		JButton jb = new JButton("Ok>");
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jf.dispose();
				einteilen();
				
			}
		});

		jf.getContentPane().add(scrollPane,BorderLayout.CENTER);
		jf.getContentPane().add(jb, BorderLayout.SOUTH);

		jf.pack();
		jf.setVisible(true);
	}


	protected void einteilen() {
		//Liste der Aerzte einholen die in den letzten perioden an ostern, weihnachten, sylvester dienst hatten
		Vector<String> warWeihnachtenDran = new Vector<String>();
		CSVtoTable letztePeriode = new CSVtoTable("C:/ntdst/data/",ConfigReader.getInstance().getConfig("laufend"));
		CSVtoTable diesePeriode = new CSVtoTable("C:/ntdst/data/",ConfigReader.getInstance().getConfig("geplant"));
		for(int i=0;i<letztePeriode.getRowCount();i++){
				if(letztePeriode.getValueAt(i,1).equals("Weihnachten")||letztePeriode.getValueAt(i,1).equals("Ostern")||letztePeriode.getValueAt(i,1).equals("Sylvester")){
					warWeihnachtenDran.add(""+letztePeriode.getValueAt(i,3));
				}
		}
		
		for(int i=0;i<diesePeriode.getRowCount();i++){
			if(diesePeriode.getValueAt(i,1).equals("Weihnachten")||diesePeriode.getValueAt(i,1).equals("Ostern")||diesePeriode.getValueAt(i,1).equals("Sylvester")){
				warWeihnachtenDran.add(""+diesePeriode.getValueAt(i,3));
			}
		}

		//aus Tabelle einen Vektor machen
		alleTage = new Vector<ntdstDate>();
		int nr=1;
		for(int i = 0;i<tm.getRowCount();i++){
			ntdstDate neu = new ntdstDate(""+tm.getValueAt(i, 0), ""+tm.getValueAt(i, 1), nr, Integer.parseInt(""+tm.getValueAt(i, 2)));
			alleTage.add(neu);
			nr++;
		}
		
		Vector<ntdstDate> wochenTage = new Vector<ntdstDate>();
		Vector<ntdstDate> mittwochTage = new Vector<ntdstDate>();
		Vector<ntdstDate> freiTage = new Vector<ntdstDate>();
		Vector<ntdstDate> wochenendFeierTage = new Vector<ntdstDate>();
		Vector<ntdstDate> brueckenTage = new Vector<ntdstDate>();
		Vector<ntdstDate> besFeierTage = new Vector<ntdstDate>();
		
		for(int i = 0;i<alleTage.size();i++){
			ntdstDate akt=alleTage.get(i);
			if(akt.getTyp().equals("Montag")||akt.getTyp().equals("Dienstag")||akt.getTyp().equals("Donnerstag")){
				wochenTage.add(akt);
			}else if(akt.getTyp().equals("Mittwoch")){
				mittwochTage.add(akt);
			}else if(akt.getTyp().equals("Freitag")){
				freiTage.add(akt);
			}else if(akt.getTyp().equals("Samstag")||akt.getTyp().equals("Sonntag")||akt.getTyp().equals("Feiertag")){
				wochenendFeierTage.add(akt);
			}else if(akt.getTyp().equals("Brueckentag")){
				brueckenTage.add(akt);
			}else{
				besFeierTage.add(akt);
			}
			
			
		}
		

		//Punktedurchschnitt errechnen
		ChartView cv = new ChartView();
		cv.setVisible(false);
		double anzahlAerzte = cv.getAnzahlAerzte();
		double gesamtPunkte = cv.getGesamtPunkte();
		Vector<Arzt> alleAerzte=cv.getAlleAerzte();
		double durchschnitt = gesamtPunkte/anzahlAerzte;
		System.out.println("gesamtPunkte "+gesamtPunkte+" anzahlAeeerzte: "+anzahlAerzte+" durchschnitt: "+durchschnitt);
		//Überdurchschnittlich unterduchschnittlich 2 Listen
		
		unterDurchschnitt = new Vector<Arzt>();
		ueberDurchschnitt = new Vector<Arzt>();
		for(int i=0;i<alleAerzte.size();i++){
			if(alleAerzte.get(i).getPunkte()<=durchschnitt)
				unterDurchschnitt.add(alleAerzte.get(i));
			else
				ueberDurchschnitt.add(alleAerzte.get(i));
		}

		


		////bes.feiertage auf wenigste punkte aufteilen, wenn sie nicht auf den sperrlisten stehen
		
		for(int i =0;i<besFeierTage.size();i++){
			int j=0;
			ntdstDate aktDate=besFeierTage.get(i);
			Arzt aktArzt = unterDurchschnitt.get(j);
			while(arztInListe(aktArzt,warWeihnachtenDran)==true){
				aktArzt = unterDurchschnitt.get(j++);
			}
			aktArzt.setPunkte(aktArzt.getPunkte()+aktDate.getPunkte());
			aktDate.setaId(aktArzt.getAid());
			aktArzt.addDate(aktDate);
			gesamtPunkte+=aktDate.getPunkte();
			durchschnitt= gesamtPunkte/anzahlAerzte;
			
			umschichten(durchschnitt);
		}
		
		System.out.println(durchschnitt);
		
		
		
		/*
		 * Restliche Dates nach Punkten absteigend aufteilen(GenerationNumber darf dabei nicht näher als 30 an dem letzten date sein).
		 * nach jeder einteilung den durchschnitt und unterurchschnittlich und überdurchschnittlich
		 * neu einteilen
		 */
		
		for(int i =0;i<wochenendFeierTage.size();i++){
			int j=0;
			ntdstDate aktDate=wochenendFeierTage.get(i);
			Arzt aktArzt = unterDurchschnitt.get(j);
			while(tagZuNahDran(aktArzt,aktDate)==true){
				aktArzt = unterDurchschnitt.get(j++);
			}
			aktArzt.setPunkte(aktArzt.getPunkte()+aktDate.getPunkte());
			aktDate.setaId(aktArzt.getAid());
			aktArzt.addDate(aktDate);
			gesamtPunkte+=aktDate.getPunkte();
			durchschnitt= gesamtPunkte/anzahlAerzte;
			
			umschichten(durchschnitt);
		}
		
		for(int i =0;i<brueckenTage.size();i++){
			int j=0;
			ntdstDate aktDate=brueckenTage.get(i);
			Arzt aktArzt = unterDurchschnitt.get(j);
			while(tagZuNahDran(aktArzt,aktDate)==true){
				aktArzt = unterDurchschnitt.get(j++);
			}
			aktArzt.setPunkte(aktArzt.getPunkte()+aktDate.getPunkte());
			aktDate.setaId(aktArzt.getAid());
			aktArzt.addDate(aktDate);
			gesamtPunkte+=aktDate.getPunkte();
			durchschnitt= gesamtPunkte/anzahlAerzte;
			
			umschichten(durchschnitt);
		}
		
		for(int i =0;i<freiTage.size();i++){
			int j=0;
			ntdstDate aktDate=freiTage.get(i);
			Arzt aktArzt = unterDurchschnitt.get(j);
			while(tagZuNahDran(aktArzt,aktDate)==true){
				aktArzt = unterDurchschnitt.get(j++);
			}
			aktArzt.setPunkte(aktArzt.getPunkte()+aktDate.getPunkte());
			aktDate.setaId(aktArzt.getAid());
			aktArzt.addDate(aktDate);
			gesamtPunkte+=aktDate.getPunkte();
			durchschnitt= gesamtPunkte/anzahlAerzte;
			
			umschichten(durchschnitt);
		}
		
		for(int i =0;i<mittwochTage.size();i++){
			int j=0;
			ntdstDate aktDate=mittwochTage.get(i);
			Arzt aktArzt = unterDurchschnitt.get(j);
			while(tagZuNahDran(aktArzt,aktDate)==true){
				aktArzt = unterDurchschnitt.get(j++);
			}
			aktArzt.setPunkte(aktArzt.getPunkte()+aktDate.getPunkte());
			aktDate.setaId(aktArzt.getAid());
			aktArzt.addDate(aktDate);
			gesamtPunkte+=aktDate.getPunkte();
			durchschnitt= gesamtPunkte/anzahlAerzte;
			
			umschichten(durchschnitt);
		}
		
		for(int i =0;i<wochenTage.size();i++){
			int j=0;
			ntdstDate aktDate=wochenTage.get(i);
			Arzt aktArzt = unterDurchschnitt.get(j);
			while(tagZuNahDran(aktArzt,aktDate)==true){
				aktArzt = unterDurchschnitt.get(j++);
			}
			aktArzt.setPunkte(aktArzt.getPunkte()+aktDate.getPunkte());
			aktDate.setaId(aktArzt.getAid());
			aktArzt.addDate(aktDate);
			gesamtPunkte+=aktDate.getPunkte();
			durchschnitt= gesamtPunkte/anzahlAerzte;
			System.out.println("punkte: "+gesamtPunkte+"");
			
			umschichten(durchschnitt);
		}
		System.out.println("ferig eingeteilt");
		
		//Tabelle mit einteilung anzeigen
		
		String[][] dataArray = new String[alleTage.size()][4];
		int i=0;
		
			for(int j=0;j<besFeierTage.size();j++){
				dataArray[i][0]=besFeierTage.get(j).getDate();
				dataArray[i][1]=besFeierTage.get(j).getTyp();
				dataArray[i][2]=""+besFeierTage.get(j).getPunkte();
				dataArray[i][3]=""+besFeierTage.get(j).getaId();
				i++;
			}
			for(int j=0;j<wochenendFeierTage.size();j++){
				dataArray[i][0]=wochenendFeierTage.get(j).getDate();
				dataArray[i][1]=wochenendFeierTage.get(j).getTyp();
				dataArray[i][2]=""+wochenendFeierTage.get(j).getPunkte();
				dataArray[i][3]=""+wochenendFeierTage.get(j).getaId();
				i++;
			}
			for(int j=0;j<brueckenTage.size();j++){
				dataArray[i][0]=brueckenTage.get(j).getDate();
				dataArray[i][1]=brueckenTage.get(j).getTyp();
				dataArray[i][2]=""+brueckenTage.get(j).getPunkte();
				dataArray[i][3]=""+brueckenTage.get(j).getaId();
				i++;
			}
			for(int j=0;j<freiTage.size();j++){
				dataArray[i][0]=freiTage.get(j).getDate();
				dataArray[i][1]=freiTage.get(j).getTyp();
				dataArray[i][2]=""+freiTage.get(j).getPunkte();
				dataArray[i][3]=""+freiTage.get(j).getaId();
				i++;
			}
			for(int j=0;j<mittwochTage.size();j++){
				dataArray[i][0]=mittwochTage.get(j).getDate();
				dataArray[i][1]=mittwochTage.get(j).getTyp();
				dataArray[i][2]=""+mittwochTage.get(j).getPunkte();
				dataArray[i][3]=""+mittwochTage.get(j).getaId();
				i++;
			}
			for(int j=0;j<wochenTage.size();j++){
				dataArray[i][0]=wochenTage.get(j).getDate();
				dataArray[i][1]=wochenTage.get(j).getTyp();
				dataArray[i][2]=""+wochenTage.get(j).getPunkte();
				dataArray[i][3]=""+wochenTage.get(j).getaId();
				i++;
			}
		
			final HashMap<String,ntdstDate> hm = new HashMap<String,ntdstDate>();
			for(int j=0;j<besFeierTage.size();j++){
				hm.put(besFeierTage.get(j).getDate(), besFeierTage.get(j));
			}
			for(int j=0;j<wochenendFeierTage.size();j++){
				hm.put(wochenendFeierTage.get(j).getDate(), wochenendFeierTage.get(j));
			}
			for(int j=0;j<brueckenTage.size();j++){
				hm.put(brueckenTage.get(j).getDate(), brueckenTage.get(j));
			}
			for(int j=0;j<freiTage.size();j++){
				hm.put(freiTage.get(j).getDate(), freiTage.get(j));
			}
			for(int j=0;j<mittwochTage.size();j++){
				hm.put(mittwochTage.get(j).getDate(), mittwochTage.get(j));
			}
			for(int j=0;j<wochenTage.size();j++){
				hm.put(wochenTage.get(j).getDate(), wochenTage.get(j));
			}
			
			
		System.out.println("fertig im dataArray");
		
		String[] colNames = {
				"Datum","Typ","Punkte","AId"
		};
		
		tm1 = new DefaultTableModel(dataArray, colNames);
		jt1 = new JTable(tm1);
		jt1.setAutoCreateRowSorter(true);
		
		jf1 = new JFrame();

		scrollPane1 = new JScrollPane(jt1);
		JButton jb = new JButton("Ok>");
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jf1.dispose();
				try {
					schreiben(hm);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

		jf1.getContentPane().add(scrollPane1,BorderLayout.CENTER);
		jf1.getContentPane().add(jb, BorderLayout.SOUTH);

		jf1.pack();
		jf1.setVisible(true);
		
		
		//bei bestätigung einteilung in neuer csv speichern und periodendaten in config umstellen
		
		
		//TODO Anzeige der akt. Periode mit tauschmöglichkeit (von hauptfenster und von hier zum bestaetigen

	}


	protected void schreiben(HashMap<String,ntdstDate> hm) throws IOException {
		String prefix=alleTage.get(0).getDate();
		System.out.println("C:/ntdst/data/"+prefix+".csv");
		File dataFile= new File("C:/ntdst/data/"+prefix+".csv");
		File oldFile= new File("C:/ntdst/data/"+prefix+".old");
		
		try {
			if(dataFile.exists())
				Files.move(dataFile.toPath(), oldFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println("Fehler beim bewegen der datei");
			e.printStackTrace();
		}
		dataFile.createNewFile();
		FileWriter writer = new FileWriter(dataFile ,true);
		writer.write("Tag;Typ;Punkte;AID");
		writer.write(System.getProperty("line.separator"));
		for(int i=0;i<tm1.getRowCount();i++){
		writer.write(hm.get(alleTage.get(i).getDate()).toString());
		writer.write(System.getProperty("line.separator"));
		}
		writer.close();
		String alt=ConfigReader.getInstance().getConfig("geplant");
		ConfigReader.getInstance().setConfig("laufend", alt);
		ConfigReader.getInstance().setConfig("geplant", prefix);
		String alleAlt=ConfigReader.getInstance().getConfig("alle");
		ConfigReader.getInstance().setConfig("alle", ""+alleAlt+"___"+prefix);
		
	}


	private boolean tagZuNahDran(Arzt aktArzt, ntdstDate aktDate) {
		boolean zuNah = false;
		for(int i=0;i<aktArzt.getDates().size();i++){
			if(((aktDate.getErstellungsNr()-aktArzt.getDates().get(i).getErstellungsNr())<5)&&((aktDate.getErstellungsNr()-aktArzt.getDates().get(i).getErstellungsNr())>-5)) zuNah = true;
			if(aktArzt.getDates().size()>12)zuNah=true;
		}
		return zuNah;
	}


	private void umschichten(double durch) {
		for(int i=1;i<unterDurchschnitt.size();i++){
			if(Double.parseDouble(""+unterDurchschnitt.get(i).getPunkte())>(durch+1)){
				System.out.println(""+unterDurchschnitt.get(i).getPunkte()+">"+durch+1);
				ueberDurchschnitt.add(unterDurchschnitt.get(i));
				unterDurchschnitt.remove(i);
			}
		}
		Collections.shuffle(unterDurchschnitt);
		for(int i=1;i<ueberDurchschnitt.size();i++){
			if(Double.parseDouble(""+ueberDurchschnitt.get(i).getPunkte())<=(durch+1)){
				System.out.println(""+ueberDurchschnitt.get(i).getPunkte()+"<="+durch+1);
				unterDurchschnitt.add(ueberDurchschnitt.get(i));
				ueberDurchschnitt.remove(i);
			}
		}
		Collections.shuffle(ueberDurchschnitt);
		
	}


	private boolean arztInListe(Arzt aktArzt, Vector<String> warWeihnachtenDran) {
		boolean drin = false;
		for(int i=0;i<warWeihnachtenDran.size();i++){
			if(warWeihnachtenDran.get(i).equals(""+aktArzt.getAid())) drin = true;
		}
		return drin;
	}
}
