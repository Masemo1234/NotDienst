package NotDienst.client.nc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import NotDienst.core.util.CSVtoTable;

public class ArztPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -559525344289300088L;

	private JTable mTable = null;
		// filterButton, resetButton;
	  private PeriodePanel laufend;
	  private PeriodePanel geplant;

	  private CSVtoTable mModel;
	
	
	public ArztPanel(PeriodePanel laufendePPanel, PeriodePanel geplantePPanel){
		super();
		laufend=laufendePPanel;
		geplant=geplantePPanel;
		prepareUI();
	}

	private void prepareUI() {
		
		mModel = new CSVtoTable("C:/ntdst/data/", "ArztDaten");
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
			private static final long serialVersionUID = -4086939026254408262L;

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
    mTable.getSelectionModel().addListSelectionListener(new MyRowListener());
	    JScrollPane scrollPane = new JScrollPane(mTable);
	    scrollPane.setAutoscrolls(true);
	    scrollPane.setWheelScrollingEnabled(true);
	    scrollPane.setEnabled(true);

	    this.setLayout(new BorderLayout());
	    this.add(scrollPane, BorderLayout.CENTER);
		
	}
	public void refreshTable() {
		mModel.reloadData("C:/ntdst/data/", "ArztDaten");
	}

	public void selectFirstRow() {
		mTable.setRowSelectionInterval(0, 0);
		
	}
//TODO

	  class MyRowListener implements ListSelectionListener
	  {

	    @Override
	    public void valueChanged(ListSelectionEvent evt)
	    {
	      int numSelected = mTable.getSelectedColumnCount();
	      if(numSelected==1){
	        int row = mTable.getSelectedRow();
	        
	        if(row>=0){
	          row = mTable.convertRowIndexToModel(row);
	          // get the AlarmId of this row.
	          long almId=0;
	          if(mTable.getModel().getValueAt(row, 0).getClass()==Long.class)
	            almId = ( (Long) mTable.getModel().getValueAt(row, 0) ).longValue();
	          else
	            almId = Long.parseLong((String) mTable.getModel().getValueAt(row, 0) );
	          geplant.reloadData(new String(""+almId));
	          laufend.reloadData(new String(""+almId));
	        }
	      }else if(numSelected>1){
	        int[] rows = mTable.getSelectedRows();
	        int num = rows.length;
	        System.out.println("Select " + num + " alarms");
	      }
	/*
	      if (!evt.getValueIsAdjusting()) {
	        //System.out.println("valueChanged: " + evt.toString());
	      }
	*/
	    }

	  }

}
