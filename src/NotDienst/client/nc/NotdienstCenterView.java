package NotDienst.client.nc;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.DocumentException;

import NotDienst.client.planer.KollegenListeDrucker;
import NotDienst.client.planer.PlanerView;
import NotDienst.core.util.SerienbriefDrucker;

public class NotdienstCenterView extends JFrame implements NotdienstCenterObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8862263304372921871L;
	private ArztPanel arztPanel;
	private PeriodePanel laufendePPanel;
	private PeriodePanel geplantePPanel;

	public NotdienstCenterView(){
		super("Notdienst Manager");
		prepareUI();
	}

	private void prepareUI() {
		// TODO Auto-generated method stub
		String laufendePeriode = ConfigReader.getInstance().getConfig("laufend");
		String geplantePeriode = ConfigReader.getInstance().getConfig("geplant");
		
		laufendePPanel= new PeriodePanel(laufendePeriode,"1");
		laufendePPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Laufende Periode", 
                TitledBorder.LEFT, TitledBorder.TOP));
		geplantePPanel= new PeriodePanel(geplantePeriode,"2");
		geplantePPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Geplante Periode", 
                TitledBorder.LEFT, TitledBorder.TOP));
		arztPanel = new ArztPanel(laufendePPanel,geplantePPanel);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JSplitPane splitPane4 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane4.setResizeWeight(0.5);
		splitPane.add(arztPanel);
		splitPane.add(splitPane4);
		splitPane.setResizeWeight(0.5);
		splitPane4.add(laufendePPanel);
		splitPane4.setContinuousLayout(true);

		splitPane4.add(geplantePPanel);

		JMenuBar menuBarAC = createMenuBar();
		menuBarAC.setOpaque(true);
		menuBarAC.setPreferredSize(new Dimension(200, 20));

		this.setJMenuBar(menuBarAC);

		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setPreferredSize(new Dimension(1024,620));
		this.getContentPane().add(splitPane);
		this.pack();
		arztPanel.selectFirstRow();
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		// === File ===
		JMenu mFile = new JMenu("File");
		mFile.setFont(new Font("Dialog", Font.BOLD, 12));
		mFile.setMnemonic('f');

		JMenuItem mExit = new JMenuItem("Exit");
		mExit.setFont(new Font("Dialog", Font.BOLD, 12));
		mExit.setMnemonic('x');
		ActionListener lstExit = new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		mExit.addActionListener(lstExit);
		mFile.add(mExit);
		menuBar.add(mFile);
		JMenu mPlan = new JMenu("Planer");
		mPlan.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mFile.setFont(new Font("Dialog", Font.BOLD, 12));

		JMenuItem planen = new JMenuItem("Neue Periode Planen");
		planen.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mExit.setFont(new Font("Dialog", Font.BOLD, 12));
		ActionListener lstplanen = new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				planerOeffnen();
			}
		};
		planen.addActionListener(lstplanen);
		mPlan.add(planen);
		menuBar.add(mPlan);
		
		JMenuItem mntmPeriodenTauscher = new JMenuItem("Perioden Tauscher");
		mntmPeriodenTauscher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TauscherFrame tf = new TauscherFrame();
			}
		});
		mntmPeriodenTauscher.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mPlan.add(mntmPeriodenTauscher);
		
		JMenuItem mntmKollegenlisteAusgeben = new JMenuItem("Kollegenliste ausgeben");
		mntmKollegenlisteAusgeben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					try {
						KollegenListeDrucker kld = new KollegenListeDrucker();
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mntmKollegenlisteAusgeben.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mPlan.add(mntmKollegenlisteAusgeben);
		
		JMenuItem mntmSerienbriefDrucken = new JMenuItem("Serienbrief Drucken");
		mntmSerienbriefDrucken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SerienbriefDrucker sd = new SerienbriefDrucker();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mntmSerienbriefDrucken.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mPlan.add(mntmSerienbriefDrucken);
		
		JMenu mnAnzeige = new JMenu("Anzeige");
		mnAnzeige.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menuBar.add(mnAnzeige);
		
		JMenuItem mntmCharts = new JMenuItem("Charts");
		mntmCharts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChartView cv = new ChartView();
			}
		});
		mntmCharts.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnAnzeige.add(mntmCharts);
		return menuBar;
	}

	protected void planerOeffnen() {
		PlanerView pv = new PlanerView(this);
		
	}

	@Override
	public void arztChanged(long arztId) {
		String id= new String(""+arztId);
		laufendePPanel.reloadData(id);
		geplantePPanel.reloadData(id);
	}

	@Override
	public void dataChanged() {
		arztPanel.refreshTable();
		arztPanel.selectFirstRow();

	}



}
