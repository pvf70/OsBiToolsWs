/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 * Date: 2014-10-02T21:00Z
 * 
 */

package com.osbitools.ws.shared;

/**
 * Default and Project specific constants
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

public class DsConstants {
	// DataSet subdirectory
	public static final String DS_DIR = "ds";
	
	// Supported DataSet versions
	public static final int[] DS_VER = new int[]{1, 0};
	
  // List of directories for DataSet Web Services
  public static String[] DS_DIR_LIST = new String[] {"ds"};
  
  // Package name with auto-generated code for DataSet Map
	public static final String BIND_PKG_DS_MAP = 
						"com.osbitools.ws.shared.binding.ds";
}
