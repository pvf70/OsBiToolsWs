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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.osbitools.ws.shared.*;

/**
 * Constants used solely by unit and/or integration test
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class LocalTestConstants {

  // - target/ds
  public static final String TEMP_DS_DIR_SHORT = 
                      TestConstants.TARGET_DIR + DsConstants.DS_DIR;

  // - target/ds/
  public static final String TEMP_DS_DIR = TEMP_DS_DIR_SHORT + File.separator;

  // Source test directory with DataSet maps: /src/test/web_root/config/ds
  public static final String DS_DIR = TestConstants.SRC_CONFIG_DIR + 
                                      File.separator + DsConstants.DS_DIR;
  
  // web directory with test files: /target/web_root/config/ds
  public static final String WORK_DS_DIR_S = TestConstants.TARGET_DIR + 
    TestConstants.WEB_ROOT_DIR + TestConstants.CONFIG_DIR + DsConstants.DS_DIR;
  
  //-- /target/web_root/config/projects/
  public static final String WORK_DS_DIR = WORK_DS_DIR_S + File.separator;
  
  /*************** GUI TEST ***************/
  //List of component groups
  public static HashMap<String, ArrayList<String>> COMP_LIST = 
                           new HashMap<String, ArrayList<String>>();
  static {
   COMP_LIST.put("LL_CONTAINERS", new ArrayList<String>(
       Arrays.asList(new String[] {"images/comp_icons/group.png"})));
   
   COMP_LIST.put("LL_AVAIL_DS_TYPES", new ArrayList<String>(
       Arrays.asList(new String[] {"images/comp_icons/csv.png", "images/comp_icons/static.png", "images/comp_icons/sql.png"})));
  }
  
  public static String LL_TOOL_TITLE = "LL_MAP_EDITOR_TITLE";
}
