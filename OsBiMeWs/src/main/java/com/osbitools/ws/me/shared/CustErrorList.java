/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-09
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.me.shared;

import com.osbitools.ws.shared.ErrorList;

/**
 * List of Errors for OsBiTools Map Editor
 * Reserved error in range [300-349]
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class CustErrorList {

	static {
	  // ErrorList.addError(300, "Internal error processing request", 500);
	  ErrorList.addError(301, "Internal error processing request", 500);
	  ErrorList.addError(302, "Error processing info request");
	}
}
