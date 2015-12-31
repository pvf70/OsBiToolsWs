/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Shared Utilities
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public class Utils {

	/**
	 * Check string for non-empty conditions
	 * 
	 * @param s Input String
	 * 
	 * @return True if string is null or equals "" 
	 */
	public static boolean isEmpty(String s) {
		return ((s == null) || s.equals("")); 
	}
	
	/**
	 * Check if string array is empty
	 * 
	 * @param arr Input Array of String
	 * 
	 * @return True if array is null or size equals 0
	 */
	public static boolean isEmpty(String[] arr) {
		return ((arr == null) || (arr.length == 0));
	}
	
	/**
	 * Check if HashMap is empty
	 * 
	 * @param arr HaspMap
	 * 
	 * @return True if array is null or size equals 0
	 */
	public static boolean isEmpty(HashMap<?,?> arr) {
		return ((arr == null) || (arr.size() == 0));
	}
	
	/**
	 * Check if boolean is False or null
	 * 
	 * @param arr Input Array of String
	 * 
	 * @return True if array is null or size equals 0
	 */
	public static boolean isEmpty(Boolean b) {
		return ((b == null) || !b);
	}
	
	/**
	 * Convert first character to upper case in input word
	 * 
	 * @param msg input word
	 * @return word with first character upper case
	 */
	public static String ucFirstChar(String msg) {
	  char first = Character.toUpperCase(msg.charAt(0));
	  return first + msg.substring(1);
	}
	
	/********************************************/
  /********** SPECIAL CHARACTERS ***********/
  /********************************************/

  public static String getCR(boolean minified) {
    return minified ? "" : "\n";
  }

  public static String getTAB(boolean minified) {
    return minified ? "" : "\t";
  }

  public static String getSPACE(boolean minified) {
    return minified ? "" : " ";
  }

  public static String getCRT(boolean minified) {
    return getCR(minified) + getTAB(minified);
  }

  public static String getCRTT(boolean minified) {
    return getCR(minified) + getTAB(minified) + getTAB(minified);
  }

  public static String getCRTTT(boolean minified) {
    return getCR(minified) + getTAB(minified) + getTAB(minified) + getTAB(minified);
  }

  public static String getTT(boolean minified) {
    return getTAB(minified) + getTAB(minified);
  }

  public static String getTTT(boolean minified) {
    return getTAB(minified) + getTAB(minified) + getTAB(minified);
  }
  
  public static String getTABS(boolean minified, int tabs) {
    String res = "";
    
    for (int i = 0; i< tabs; i++)
      res += getTAB(minified);
    
    return res;
  }
  
  public static String getCRTS(boolean minified, int tabs) {
    return getCR(minified) + getTABS(minified, tabs);
  }
  
  /********************************************/
  /**********         LOGGING       ***********/
  /********************************************/
  
  private static String getLogId(Long requestId) {
    return (requestId != null) ? requestId + "," : "";
  }
  
  public static void info(Logger log, Long requestId, String msg) {
    log.info(getLogId(requestId) + msg);
  }

  public static void error(Logger log, Long requestId, String msg) {
    log.error(getLogId(requestId) + msg);
  }
  
  public static void error(Logger log, Long requestId, String[] dmsg) {
    if (Utils.isEmpty(dmsg))
      return;
    
    for (String s : dmsg)
      error(log, requestId, "ERROR DETAILS:" + s);
  }
  
  public static void error(Logger log, Long requestId, 
                                      int errId, String[] errDetails) {
    error(log, requestId, "ERROR #" + errId);
    error(log, requestId, errDetails);
  }
  
  public static void debug(Logger log, Long requestId, String msg) {
    log.debug(getLogId(requestId) + msg);
  }

  public static void warn(Logger log, Long requestId, String msg) {
    log.warn(getLogId(requestId) + msg);
  }

  public static void fatal(Logger log, Long requestId, String msg) {
    log.fatal(getLogId(requestId) + msg);
  }
  
  public static String escJsonStr(String s) {
    return s.replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t")
          .replaceAll("\"", "\\\"").replaceAll("\\\\", "\\\\\\\\");
  }
}
