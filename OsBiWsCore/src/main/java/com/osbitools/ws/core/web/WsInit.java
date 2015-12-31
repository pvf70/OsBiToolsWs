/*
 * Copyright 2014-2016 IvaLab Inc. and other contributors
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-07
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.core.web;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.xml.bind.JAXBException;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.core.daemons.*;
import com.osbitools.ws.shared.web.AbstractWsInit;
import com.osbitools.ws.core.shared.CoreConstants;

public class WsInit extends AbstractWsInit {

  /*
	// Lang Labels scan daemon
	private static LangLabelsCheck _lcheck;

	// DataSet scan daemon
	private static DsFilesCheck _dcheck; 

	// Default Directory Scan Interval
	private static int _rescan;
  */
  
	@Override
	public String getWsName() {
		return "Core DataSet Web Services";
	}

	@Override
	public String[] getConfigSubDirList() {
		return DsConstants.DS_DIR_LIST;
	}

	@Override
	public String getLogCategory() {
		return "OsBiWsCore";
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		super.contextInitialized(evt);
		
		// Load Custom Error List 
    try {
      Class.forName("com.osbitools.ws.core.shared.CustErrorList");
    } catch (ClassNotFoundException e) {
      // Ignore Error 
    }
    
    int rescan = (Integer) evt.getServletContext().
                                getAttribute("scan_interval");
    ServletContext ctx = evt.getServletContext();
    String fdir = getConfigDir(ctx) + 
                            File.separator + DsConstants.DS_DIR;
    
		startLangLabelsCheck(ctx, fdir, rescan);
		startDataSetFilesCheck(ctx, fdir, rescan); 
	}

	/**
	 * 
	 * Start daemon checking file with lang labels
	 * 
	 * @param configDir
	 */
	private void startLangLabelsCheck(ServletContext ctx, 
	                                      String base, int rescan) {

		try {
		  LsFilesCheck lcheck = new LsFilesCheck(base, rescan);
			lcheck.start();
			
			ctx.setAttribute("lcheck", lcheck);
		} catch (JAXBException | NoSuchMethodException | SecurityException e) {
			getLogger(ctx).fatal(e.getMessage());
		}
	}

	// Start daemon checking file with lang labels
	/**
	 * Start daemon checking file for configured dataset
	 * 
	 * @param configDir
	 */
	private void startDataSetFilesCheck(ServletContext ctx, 
	                                              String base, int rescan) {
		
		try {
		  DsFilesCheck dcheck = new DsFilesCheck(base, rescan);
			dcheck.start();
			
			ctx.setAttribute("dcheck", dcheck);
		} catch (JAXBException | NoSuchMethodException | SecurityException e) {
		  getLogger(ctx).fatal("Invalid binding package " + 
		                  DsConstants.BIND_PKG_DS_MAP + ": " + e.getMessage());
		}
	} 
	
	@Override
	public void processConfig(ServletContext ctx, Properties props, 
	                          HashMap<String, String> rmap, boolean isNew) {
	  super.processConfig(ctx, props, rmap, isNew);
	  
	  int rescan;
	  
	  if (isNew) {
	    rescan = CoreConstants.DEFAULT_RESCAN_TIME;
	  	props.put("scan_interval", CoreConstants.DEFAULT_RESCAN_TIME.toString());
	  } else {
	  	// Set re-scan time
	    rescan = Integer.parseInt(props.getProperty("scan_interval",
	                    CoreConstants.DEFAULT_RESCAN_TIME.toString()));
	  }
	  
	  ctx.setAttribute("scan_interval", rescan);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent evt) {
		super.contextDestroyed(evt);
		
		DsFilesCheck dcheck = (DsFilesCheck) evt.getServletContext().
		                                                  getAttribute("dcheck");
         
		LsFilesCheck lcheck = (LsFilesCheck) evt.getServletContext().
		                                                  getAttribute("lcheck");
    
		dcheck.interrupt();
		lcheck.interrupt();
		
		try {
	    dcheck.join();
	    lcheck.join();
    } catch (InterruptedException e) {
    	// Ignore
    }
	}
	
	/*
	
	public String findLocaleValue(String lang, String value) {
    return _lcheck.getLangLabel(lang, value);
  }
  
  public int getLangLabelSize() {
    return _lcheck.getLangLabelSize();
  }
  
	public DsDescrResource getDsMap(String name) {
		return _dcheck.getDsMap(name);
	}

	public String[] getDsMapList() {
		return _dcheck.getDsMapList();
	}
	
	public int getDsMapListSize() {
	  return _dcheck.getDsMapListSize();
	}
	
	// Test purposes
	public void setLangLabelsCheck(LangLabelsCheck llCheck) {
		_lcheck = llCheck;
	}
	
	// Test purposes
	public boolean isDataSetCheckReady() {
	  return _dcheck.isInit();
	}
	
	//Test purposes
	public boolean isLangLabelsCheckReady() {
	  return _lcheck.isInit();
	}
	
	*/
	
  /*
  public String getDsDir(ServletContext ctx) {
    return getConfigDir(ctx) + File.separator + DsConstants.DS_DIR;
  }
  */

}
