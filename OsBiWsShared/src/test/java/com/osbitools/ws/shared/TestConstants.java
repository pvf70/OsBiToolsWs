/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-11-09T21:45Z
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Constants used solely by unit and/or integration test
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class TestConstants {

  // - target
  public static final String TARGET_DNAME = "target";
  
  // - target/
	public static final String TARGET_DIR = TARGET_DNAME + File.separator;
	
	public static final String SRC_DIR = "src" + File.separator;
	
	// - src/test/
	public static final String SRC_TEST_DIR = SRC_DIR + "test" + File.separator;
	
	// - config
	public static final String CONFIG_DNAME = "config";
	
	// - config/
	public static final String CONFIG_DIR = CONFIG_DNAME + File.separator;
	
	// - web_root
	public static final String WEB_ROOT_DNAME = "web_root";

  // - web_root/
  public static final String WEB_ROOT_DIR = WEB_ROOT_DNAME + File.separator;
  
  // Working test directory with WebApp configuration - target/web_root/config
  // Used by Jetty during integration test
  public static final String WORK_CONFIG_DIR = TARGET_DIR + 
                                        WEB_ROOT_DIR + CONFIG_DNAME;
  
  // -- src/test/web_root/config
  public static final String SRC_CONFIG_DIR = SRC_TEST_DIR + 
                                              WEB_ROOT_DIR  + CONFIG_DNAME;
      
	public static final String LOG_DIR = TARGET_DIR  + "logs" ;
	
	public static final int RESULT_CHECK_TIME = 10; // msec
	
	public static final int WAIT_TIME = 1500;
		
	public static final int THREAD_NUM = 100;
	
	public static final Logger LOG;
	
	public static final String TEST_USER = "user";
	
	public static final String TEST_PASSWORD = "test1234";
	
	// Default Jetty Port
	public static final int JETTY_PORT = 8090;
	
	// Default Jetty Web Context
	public static final String JETTY_WEB_CTX = "web_test";
	
	//Default Jetty Host
	public static final String JETTY_HOST = "localhost";
 
	//Default Jetty Server URL
	public static final String JETTY_SRV = "http://" + 
	                        JETTY_HOST + ":" + JETTY_PORT + "/";
 
	// Default Jetty Server URL for Web Service Test
	public static final String JETTY_SRV_URL = JETTY_SRV + JETTY_WEB_CTX;
	
	//Default test GUI URL
  public static final String GUI_SRV_URL_BASE = JETTY_SRV_URL + "/fr/";
 
	// Default test GUI URL
	public static final String GUI_SRV_URL = GUI_SRV_URL_BASE + "?debug=on";
	
	// Sync lock
	public static Object lock = new Object();

  //Synchronization counters
  public static final CountDownLatch start = new CountDownLatch(1);
  public static CountDownLatch done;
  
  //Number of errors
  public static int errCount = 0;
 
	static {
		// Add console appender to log4j
		ConsoleAppender console = new ConsoleAppender();
		console.setName("Console");
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		System.setProperty("catalina.base", TARGET_DIR);
		
		LOG = Logger.getRootLogger();
		LOG.addAppender(console);
	}
	
	/*************** GUI TEST ***************/
	
	//Default language
	public static String DEFAULT_LANG = "en";
 
	// Language used for testing specific, usually new language
	public static String LANG;
 
	// Language used for testLangLogin method i.e language change during 
	//test with default language test
	public static String LANG_CHANGE = "fr";
  public static String LOCALE_CHANGE = "fr_ca";
	
	// Initiate and load LL_SET
	public static HashMap<String,HashMap<String, String>> LL_SET = 
	                          new HashMap<String,HashMap<String, String>>();
  
	// Language available for test
  static {
    LL_SET.put("en", new HashMap<String,String>());
    LL_SET.put("ru", new HashMap<String,String>());
    LL_SET.put("fr", new HashMap<String,String>());
  };
}
