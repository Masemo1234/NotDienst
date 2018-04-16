package NotDienst.client.nc;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import NotDienst.core.util.CSVtoTable;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Vector;

public class ChartView extends JFrame{
	public double durchschnitt;
	public int anzahlAerzte;
	public Vector<Arzt> alleAerzte;
	public int getAnzahlAerzte() {
		anzahlAerzte=alleAerzte.size();
		return anzahlAerzte;
	}

	public int getGesamtPunkte() {
		return gesamtPunkte;
	}

	public int gesamtPunkte;
	public ChartView() {
		super("Chart Fenster");
		prepareUI();
	}

	private void prepareUI() {


		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		if(!ConfigReader.getInstance().getConfig("geplant").equals("-")){
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			CSVtoTable mModel=new CSVtoTable("C:/ntdst/data/", "ArztDaten");
			HashMap<Integer, Integer> aerztePunkte = new HashMap<Integer, Integer>();
			Vector<String> aerzteNamen= new Vector<String>();
			for(int i =0; i<mModel.getRowCount();i++){
				aerztePunkte.put(Integer.parseInt((String) mModel.getValueAt(i, 0)),Integer.parseInt((String) mModel.getValueAt(i, 8)));
				aerzteNamen.add(mModel.getValueAt(i, 1)+" "+mModel.getValueAt(i,2));
			}
			mModel=new CSVtoTable("C:/ntdst/data/", ConfigReader.getInstance().getConfig("geplant"),true);	


			for(int i =0; i<mModel.getRowCount();i++){
				System.out.println(i);
				int value = aerztePunkte.get(Integer.parseInt((String) mModel.getValueAt(i, 3)));
				value+= Integer.parseInt((String) mModel.getValueAt(i,2));
				aerztePunkte.put(Integer.parseInt((String) mModel.getValueAt(i, 3)),value);
			}

			mModel=new CSVtoTable("C:/ntdst/data/", "ArztDaten");

			for(int i =0; i<mModel.getRowCount();i++){
				if(Integer.parseInt((String) mModel.getValueAt(i, 11))==1){
					int pkte = aerztePunkte.get(Integer.parseInt((String) mModel.getValueAt(i, 0)));
					String name =aerzteNamen.get(i);
					dataset.addValue(pkte, "punkte",name);
				}
			}


			// create the chart...
			JFreeChart chart = ChartFactory.createBarChart(
					null,  // chart title
					"Aerzte",             // domain axis label
					"Score",            // range axis label
					dataset,                // data
					PlotOrientation.HORIZONTAL,
					false,                  // include legend
					true,
					false
					);

			ChartPanel cp = new ChartPanel(chart);
			tabbedPane.add("geplante Periode", cp);}

		if(!ConfigReader.getInstance().getConfig("alle").equals("-")){

			DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();

			CSVtoTable mModel=new CSVtoTable("C:/ntdst/data/", "ArztDaten");
			HashMap<Integer, Integer> aerztePunkte = new HashMap<Integer, Integer>();
			Vector<String> aerzteNamen= new Vector<String>();
			for(int i =0; i<mModel.getRowCount();i++){
				aerztePunkte.put(Integer.parseInt((String) mModel.getValueAt(i, 0)),Integer.parseInt((String) mModel.getValueAt(i, 8)));

				aerzteNamen.add(mModel.getValueAt(i, 1)+" "+mModel.getValueAt(i,2));
			}

			String alleRaw = ConfigReader.getInstance().getConfig("alle");
			String[] alleSplit = alleRaw.split("___");
			for(int j = 0;j<alleSplit.length;j++){
				mModel=new CSVtoTable("C:/ntdst/data/", alleSplit[j],true);	

				for(int i =0; i<mModel.getRowCount();i++){
					int value = aerztePunkte.get(Integer.parseInt((String) mModel.getValueAt(i, 3)));
					System.out.println("Original value: "+value+". Adding: "+mModel.getValueAt(i,2));
					value+= Integer.parseInt((String) mModel.getValueAt(i,2));
					aerztePunkte.put(Integer.parseInt((String) mModel.getValueAt(i, 3)),value);
				}
			}
			mModel=new CSVtoTable("C:/ntdst/data/", "ArztDaten");
			alleAerzte = new Vector<Arzt>();

			for(int i =0; i<mModel.getRowCount();i++){
				if(Integer.parseInt((String) mModel.getValueAt(i, 11))==1){
					int pkte = aerztePunkte.get(Integer.parseInt((String) mModel.getValueAt(i, 0)));

					if(Integer.parseInt((String) mModel.getValueAt(i, 11))==1){
						alleAerzte.add(new Arzt(Integer.parseInt((String) mModel.getValueAt(i, 0)), (String) mModel.getValueAt(i, 2), (String) mModel.getValueAt(i, 1), 
								pkte, mModel.getValueAt(i, 3)+" "+mModel.getValueAt(i, 4), mModel.getValueAt(i, 6)+" "+mModel.getValueAt(i, 5), (String)mModel.getValueAt(i, 9),
								(String) mModel.getValueAt(i, 7),(String) mModel.getValueAt(i, 10), (String)mModel.getValueAt(i, 14)));}
					String name =aerzteNamen.get(i);
					System.out.println(name+": "+pkte);
					dataset1.addValue(pkte, "punkte",name);
				}
			}




			// create the chart...
			JFreeChart chart1 = ChartFactory.createBarChart(
					null,  // chart title
					"Aerzte",             // domain axis label
					"Score",            // range axis label
					dataset1,                // data
					PlotOrientation.HORIZONTAL,
					false,                  // include legend
					true,
					false
					);

			ChartPanel cp1= new ChartPanel(chart1);
			tabbedPane.add("alle Perioden", cp1);
		}

		if(!ConfigReader.getInstance().getConfig("laufend").equals("-")){

			CSVtoTable mModel=new CSVtoTable("C:/ntdst/data/", "ArztDaten");
			HashMap<Integer, Integer> aerztePunkte = new HashMap<Integer, Integer>();
			Vector<String> aerzteNamen= new Vector<String>();
			for(int i =0; i<mModel.getRowCount();i++){
				aerztePunkte.put(Integer.parseInt((String) mModel.getValueAt(i, 0)),Integer.parseInt((String) mModel.getValueAt(i, 8)));
				aerzteNamen.add(mModel.getValueAt(i, 1)+" "+mModel.getValueAt(i,2));
			}
			mModel=new CSVtoTable("C:/ntdst/data/", ConfigReader.getInstance().getConfig("laufend"),true);	

			for(int i =0; i<mModel.getRowCount();i++){
				int value = aerztePunkte.get(Integer.parseInt((String) mModel.getValueAt(i, 3)));
				value+= Integer.parseInt((String) mModel.getValueAt(i,2));
				aerztePunkte.put(Integer.parseInt((String) mModel.getValueAt(i, 3)),value);
			}
			DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();


			mModel=new CSVtoTable("C:/ntdst/data/", "ArztDaten");

			for(int i =0; i<mModel.getRowCount();i++){
				if(Integer.parseInt((String) mModel.getValueAt(i, 11))==1){
					int pkte = aerztePunkte.get(Integer.parseInt((String) mModel.getValueAt(i, 0)));
					String name =aerzteNamen.get(i);
					dataset3.addValue(pkte, "punkte",name);
				}
			}


			// create the chart...
			JFreeChart chart3 = ChartFactory.createBarChart(
					null,  // chart title
					"Aerzte",             // domain axis label
					"Score",            // range axis label
					dataset3,                // data
					PlotOrientation.HORIZONTAL,
					false,                  // include legend
					true,
					false
					);

			ChartPanel cp3 = new ChartPanel(chart3);
			tabbedPane.add("laufende Periode", cp3);}
			getContentPane().add(tabbedPane, BorderLayout.CENTER);
			this.pack();
			this.setVisible(true);
	}



	public double getDurchschnitt(){
		return durchschnitt;

	}
	public Vector<Arzt> getAlleAerzte(){
		return alleAerzte;

	}





}
