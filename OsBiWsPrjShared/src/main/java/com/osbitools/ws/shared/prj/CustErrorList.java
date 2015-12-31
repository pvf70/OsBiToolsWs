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

package com.osbitools.ws.shared.prj;

import com.osbitools.ws.shared.ErrorList;

/**
 * List of Errors for OsBiTools Shared Project Manager Libraries
 * Reserved error in range [200-299]
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class CustErrorList {

	static {
	  // Reserved 200-299
	  ErrorList.addError(200, "Error processing uploading request");
	  ErrorList.addError(201, "Error processing uploading request");
	  ErrorList.addError(202, "Error processing uploading request");
	  ErrorList.addError(203, "Error processing request");
	  
		// Project Utilities error
	  ErrorList.addError(204, "Error creating Project");
	  ErrorList.addError(205, "Invalid project name", 400);
    ErrorList.addError(206, "Unable delete Project");
    ErrorList.addError(207, "Error updating Project");
    ErrorList.addError(208, "Internal Error processing Entity", 500);
    ErrorList.addError(209, "Error reading Entity");
    
    ErrorList.addError(221, "Can't rename to itself", 422);
    ErrorList.addError(222, "Unable delete Project");
    ErrorList.addError(223, "Error processing request");
    ErrorList.addError(224, "Entity invalid or corrupted");
    ErrorList.addError(225, "Entity invalid or corrupted");
    ErrorList.addError(226, "Internal Error processing Entity", 500);
    ErrorList.addError(227, "Internal Error processing Entity", 500);
    ErrorList.addError(228, "Can not rename file to itself");
    ErrorList.addError(229, "Error renaming file");
    ErrorList.addError(230, "Error renaming file", 422);
    ErrorList.addError(231, "Error saving file");
    ErrorList.addError(232, "Unsupported resource type");
    ErrorList.addError(233, "Empty Entity");
    ErrorList.addError(234, "Unsupported resource type");
    ErrorList.addError(235, "Error renaming file", 422);
    ErrorList.addError(236, "Can not rename file to itself");
    ErrorList.addError(237, "Error renaming file");
    ErrorList.addError(238, "Unable delete file");
    ErrorList.addError(239, "Error commit file");
    ErrorList.addError(240, "Error commit file");
    ErrorList.addError(241, "Error read commit log");
    ErrorList.addError(242, "Error read saved revision");
    ErrorList.addError(243, "Unable find given revision");
    ErrorList.addError(244, "Unable find file in given revision");
    ErrorList.addError(245, "Error processing git repository");
    ErrorList.addError(246, "URL for remote git repository is not configured");
    ErrorList.addError(247, "URL for remote git repository is not configured");
    ErrorList.addError(248, "Invalid remote operation", 422);
    ErrorList.addError(249, "Error push to remote git");
    ErrorList.addError(250, "Error pull from remote git"); // 31
    ErrorList.addError(251, "Name for remote git repository is not configured");
    ErrorList.addError(252, "Request for this info type is not supported", 422);
    ErrorList.addError(253, "Error retrieving CSV File Info");
    ErrorList.addError(254, "Error retrieving CSV Columns");
    ErrorList.addError(255, "Error processing uploading request");
    ErrorList.addError(256, "Error processing uploading request");
    ErrorList.addError(257, "Error processing uploading request");
    ErrorList.addError(258, "Invalid file extension", 400);
    ErrorList.addError(259, "Internal error processing request", 500);
    ErrorList.addError(260, "Error getting Git Diff");
    ErrorList.addError(261, "Error saving custom language labels");
    ErrorList.addError(262, "Error saving file with custom language labels");
    ErrorList.addError(263, "Error saving file with custom language labels");
    
    ErrorList.addError(299, "Missing mandatory parameter", 400);
		// ErrorList.addError(, "", 0);
	}
}
