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
 * Date: 2014-10-02
 * 
 */

package com.osbitools.ws.shared;

/**
 * Default and Project specific constants
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

public class LsConstants {
	// Supported DataSet versions
	public static final int[] LS_VER = new int[]{1, 0};
	
  //Package name with auto-generated code for Language Labels
  public static final String BIND_PKG_LANG_LABELS_SET = 
                               "com.osbitools.ws.shared.binding.ll_set";
  
  // Package name with auto-generated code for Language Labels (no jaxb)
  public static final String BIND_PKG_LANG_LABELS_SET_MIN = 
                               "com.osbitools.ws.shared.binding.ll_set.min";
  
  // Name of lang_set file
  public static final String LANG_SET_FILE = "ll_set";
}
