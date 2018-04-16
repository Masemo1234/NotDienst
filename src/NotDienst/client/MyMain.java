package NotDienst.client;

import javax.swing.JFrame;
import javax.swing.UIManager;

import NotDienst.client.nc.NotdienstCenterView;



public class MyMain {
	private static void createAndShowGUI() 
	  {
	    try 
	    { 
	      UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	    } 
	    catch(Exception e){
	    }
	    NotdienstCenterView frame1 = new NotdienstCenterView();
	    System.out.println(System.getProperty("myCFG"));
	    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Display the window.
	    //frame.setIconImage(frame.getImage());
	    frame1.pack();
	    frame1.setVisible(true);
	  }
	  
	  /**
	   * @param args
	   */
	  public static void main(String[] args)
	  {
	    System.out.println("test console output. ");
	    if (args.length == 1)
	      System.out.println("Hello " + args[0] + "!");
	    else{
	      System.out.println("Start jFDA!");
	      javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	          createAndShowGUI();
	        }
	      });
	    }
	  } // main
}
