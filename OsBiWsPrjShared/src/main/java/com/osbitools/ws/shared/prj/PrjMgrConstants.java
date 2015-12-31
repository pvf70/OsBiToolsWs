/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-16
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared.prj;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Local constants for OsBiMe Web Service
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class PrjMgrConstants {

  // Name of directory with projects
  public static final String PRJ_DIR = "projects";
  
	// Name of Request 'name' parameter
	public static final String REQ_NAME_PARAM = "name";
	
	// Name of remote git name parameter
	public static final String PREMOTE_GIT_NAME = "git_remote_name";
	
	// Default remote git name
	public static final String DEFAULT_REMOTE_NAME = "origin";
	
	// Name for max upload file size parameter
	public static final String SMAX_FILE_UPLOAD_SIZE_NAME = 
	                              "security.max_upload_file_size";
	
	// Default Max size of upload file in Mb
	public static final String DEFAULT_MAX_FILE_UPLOAD_SIZE = "10";
	
	public static final HashMap<String, Pattern> EXT_MASK = 
	                                        new HashMap<String, Pattern>();
	
	public static final Pattern EXT_FILE_LIST_MASK = 
                                      Pattern.compile("^.*\\.\\*$");
}
