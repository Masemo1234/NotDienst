package NotDienst.client.nc;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import NotDienst.core.util.CSVtoTable;

import javax.swing.JTextField;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.GridLayout;

public class TauscherFrame extends JFrame {
	private JTable mTable;
	private CSVtoTable mModel;
	private JTextField datumField;
	private JTextField txtpunkte;
	private JTextField txtid;
	public TauscherFrame() {
		if(ConfigReader.getInstance().getConfig("geplant").equals("-")){
			JOptionPane.showMessageDialog(this, "Erst einteilen");
			return;
		}
		String periode = ConfigReader.getInstance().getConfig("geplant");
		mModel = new CSVtoTable("C:/ntdst/data/", periode);
		mTable = new JTable(mModel);
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
	    mTable.getSelectionModel().addListSelectionListener(new MyRowListener());
	    JScrollPane scrollPane = new JScrollPane(mTable);
	    scrollPane.setAutoscrolls(true);
	    scrollPane.setWheelScrollingEnabled(true);
	    scrollPane.setEnabled(true);

		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnTauschen = new JButton("Tauschen");
		btnTauschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tauschen();
			}
		});
		panel.add(btnTauschen);
		
		JButton btnSchliessen = new JButton("Schliessen");
		btnSchliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel.add(btnSchliessen);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new GridLayout(4, 1, 0, 40));
		
		datumField = new JTextField();
		datumField.setEnabled(false);
		datumField.setEditable(false);
		datumField.setText("*datum*");
		panel_1.add(datumField);
		datumField.setColumns(10);
		
		txtpunkte = new JTextField();
		txtpunkte.setEditable(false);
		txtpunkte.setEnabled(false);
		txtpunkte.setText("*punkte*");
		panel_1.add(txtpunkte);
		txtpunkte.setColumns(10);
		
		txtid = new JTextField();
		txtid.setText("*id*");
		panel_1.add(txtid);
		txtid.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		panel_1.add(btnSave);
		this.setPreferredSize(new Dimension(100, 200));
		this.setVisible(true);
	}
	
	
	protected void save() {
		int row = mTable.getSelectedRow();
		mModel.fireTableDataChanged();
		mTable.repaint();
		mModel.setValueAt(this.txtid.getText(), row, 3);
		try {
			mModel.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	protected void tauschen() {
		int[] reihen = mTable.getSelectedRows();
		if(reihen.length!=2){
			System.out.println("Nicht zulässige Zahl an Reihenn ausgewählt");
			return;
		}
		String aid1 = ""+mModel.getValueAt(reihen[0], 3);
		String aid2 = ""+mModel.getValueAt(reihen[1], 3);
		mModel.setValueAt(aid2, reihen[0], 3);
		mModel.setValueAt(aid1, reihen[1], 3);
		mModel.fireTableDataChanged();
		mTable.repaint();
		write();
		
	}


	private void write() {
		// TODO Auto-generated method stub
		try {
			mModel.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
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
	        datumField.setText(""+mTable.getValueAt(row,0));
	        txtpunkte.setText(""+mTable.getValueAt(row, 2));
	        txtid.setText(""+mTable.getValueAt(row, 3));
	      }
	    }else if(numSelected>1){
	      int[] rows = mTable.getSelectedRows();
	      int num = rows.length;
	      datumField.setText("*datum*");
	        txtpunkte.setText("*punkte*");
	        txtid.setText("*id*");
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





