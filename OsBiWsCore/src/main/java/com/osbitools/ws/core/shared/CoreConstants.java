/*
 * Copyright 2014-2016 IvaLab Inc. and other contributors
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-10-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.core.shared;

/**
 * Default and Project specific constants
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

public class CoreConstants {
	// Default configuration parameters
	
  // Name of JNDI Connection parameters
  public static final String CONN_PARAM = "conn";
 
	// Rescan time in msec
  public static final Integer DEFAULT_RESCAN_TIME = 500;
  
	// Trace breakpoints
	public static final String TRACE_START_PROC = "Start processing";
	public static final String TRACE_CONNECTED_TO_DB = "Connected to database";
	public static final String TRACE_SQL_PREP = "SQL Prepared";
	public static final String TRACE_SQL_EXEC = "SQL Executed";
	public static final String TRACE_READ_DATA= "Data Read";
	public static final String TRACE_DS_PROC_START = "DataSet Proc Start";
	public static final String TRACE_DS_READ_DATA = "DataSet Data Read";
	public static final String TRACE_DS_PROC_END = "DataSet Proc End";
	public static final String TRACE_PROC_END = "End processing";
	public static final String TRACE_FILTER_COMPLETED = "Completed Filtering";
	public static final String TRACE_SORT_COMPLETED = "Completed Sorting";
	
}
