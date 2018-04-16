package NotDienst.core.util;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JButton;

public class testFrame extends JFrame {
	private JTable table;
	public testFrame() {
		
		table = new JTable();
		getContentPane().add(table, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("New button");
		getContentPane().add(btnNewButton, BorderLayout.SOUTH);
	}

}
