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

import com.osbitools.ws.shared.ErrorList;

/**
 * List of Errors for OsBiTools Core Web Service
 * Reserved error in range [100-149]
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class CustErrorList {

  static final String ERR_PROC_DS = "Fatal error processing dataset";
  static final String ERR_BAD_VERSION = "Data Map version is not supported";
  static final String ERR_IVALID_INPUT_PARAM = "Invalid input parameter";
  static final String ERR_GET_SQL_DATA = 
                                "Fatal error retrieving data from database";
	static {
		ErrorList.addError(100, "Data Map is not found", 404);
		
    ErrorList.addError(101, ERR_BAD_VERSION);
    ErrorList.addError(102, ERR_BAD_VERSION);
		
		// Fatal error processing dataset
    ErrorList.addError(103, ERR_PROC_DS);
    ErrorList.addError(104, ERR_PROC_DS);
    ErrorList.addError(105, ERR_PROC_DS, 500);
    ErrorList.addError(106, ERR_PROC_DS);
    ErrorList.addError(107, ERR_PROC_DS);
    ErrorList.addError(108, ERR_PROC_DS);
    ErrorList.addError(109, ERR_PROC_DS);
    ErrorList.addError(110, ERR_PROC_DS);
    ErrorList.addError(111, ERR_PROC_DS);
    ErrorList.addError(112, ERR_PROC_DS);
    ErrorList.addError(113, ERR_PROC_DS);
    ErrorList.addError(114, ERR_PROC_DS);
    ErrorList.addError(115, ERR_PROC_DS);
    ErrorList.addError(116, ERR_PROC_DS);
    ErrorList.addError(117, ERR_PROC_DS);
    ErrorList.addError(118, ERR_PROC_DS);
    ErrorList.addError(119, ERR_PROC_DS);
    ErrorList.addError(120, ERR_PROC_DS);
    ErrorList.addError(121, ERR_PROC_DS);
    ErrorList.addError(122, ERR_PROC_DS);
    ErrorList.addError(123, ERR_PROC_DS);
    ErrorList.addError(124, ERR_PROC_DS);
    ErrorList.addError(125, ERR_PROC_DS);
    ErrorList.addError(126, ERR_PROC_DS);
    ErrorList.addError(127, ERR_PROC_DS);
    ErrorList.addError(128, ERR_PROC_DS);
    ErrorList.addError(129, ERR_PROC_DS);
    
		ErrorList.addError(130, ERR_IVALID_INPUT_PARAM);
		ErrorList.addError(131, ERR_IVALID_INPUT_PARAM);
		
    ErrorList.addError(132, "Missing mandatory input parameter");
		ErrorList.addError(133, ERR_GET_SQL_DATA);
		ErrorList.addError(134, ERR_GET_SQL_DATA);
		ErrorList.addError(135, ERR_GET_SQL_DATA);
		ErrorList.addError(136, ERR_PROC_DS, 500);
		ErrorList.addError(137, ERR_PROC_DS, 500);

		// ErrorList.addError(, "");
	}
}
