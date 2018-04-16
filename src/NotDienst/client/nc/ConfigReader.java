package NotDienst.client.nc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	 private boolean mFailed = false;
	  private Properties mAppCfg = new Properties();

	  public final static int DATA_SRC_DB   = 0;
	  public final static int DATA_SRC_FILE = 1;
	  
	  private ConfigReader()
	  {
	    if(init()!=0)
	      mFailed = true;
	  }
	  private static ConfigReader instance_= new ConfigReader();
	  public static ConfigReader getInstance()
	  {
	    return instance_;
	  }
	  
	  public boolean isLoadingFailed() { return mFailed; }
	  
	  public String getConfig(String param)
	  {
	    return mAppCfg.getProperty(param);
	  }
	  
	  public void setConfig(String param, String value)
	  {
	    mAppCfg.setProperty(param, value);
	    try {
			mAppCfg.store(new FileOutputStream("./cfg/app_defaults.cfg"), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public int getDataSrc()
	  {
	    return DATA_SRC_FILE;
	  }
	  
	  private int init()
	  { // load config
	    String cfgFile = System.getProperty("myCFG");
	    try {
	      mAppCfg.load(new FileInputStream(cfgFile));
	    } catch (IOException e) {
	      e.printStackTrace();
	      return 1;
	    }
	    return 0;
	  }

}
