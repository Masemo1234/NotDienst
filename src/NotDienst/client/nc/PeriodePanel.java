package NotDienst.client.nc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import NotDienst.core.util.CSVtoTable;

public class PeriodePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1746596768518333708L;
	String datum;
	String aId;
	private JTable mTable = null;
		 // filterButton, resetButton;
	  private CSVtoTable mModel;
	
	

	public PeriodePanel(String laufendePeriode, String aid) {
		super();
		aId = aid;
		datum = laufendePeriode;
		prepareUI();
	}

	public void reloadData(String id) {
		aId = id;
		mModel.reloadData("C:/ntdst/data/", datum, id);
		mTable.setModel(mModel);
		mTable.repaint();
		
	}

	
	

	private void prepareUI() {
		
		
		mModel = new CSVtoTable("C:/ntdst/data/", datum, aId);
		mTable= new JTable(mModel);
		mTable = new JTable( mModel );
	    mTable.setBackground(new Color(153, 255, 153));

	    mTable.setRowSelectionAllowed(true);
	    mTable.setColumnSelectionAllowed(false);
	    mTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	    mTable.setFillsViewportHeight(true);
	    mTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

	        

			/**
			 * 
			 */
			private static final long serialVersionUID = -6862643580268087369L;

			public Component getTableCellRendererComponent(JTable table, 
	                Object value, boolean isSelected, boolean hasFocus,
	                int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, 
	                value, isSelected, hasFocus, row, column);
	            if(isSelected==true){
		            	c.setBackground(Color.BLUE);
		            }else{
		            c.setBackground(row%2==0 ? new Color(153, 255, 153) : new Color(20,150,20));}                    
	            return c;
	        };
	    });
//	    mTable.getSelectionModel().addListSelectionListener(new MyRowListener());
	    JScrollPane scrollPane = new JScrollPane(mTable);
	    scrollPane.setAutoscrolls(true);
	    scrollPane.setWheelScrollingEnabled(true);
	    scrollPane.setEnabled(true);

	    this.setLayout(new BorderLayout());
	    this.add(scrollPane, BorderLayout.CENTER);
		
	}

	public void selectFirstRow() {
		mTable.setRowSelectionInterval(0, 0);
	}

	
}
