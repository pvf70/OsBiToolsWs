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
public class PrjMgrTestConstants {

  // - target/projects
  public static final String TEMP_PRJ_DIR_SHORT = TestConstants.TARGET_DIR +
      PrjMgrConstants.PRJ_DIR;

  // - target/projects/
  public static final String TEMP_PRJ_DIR = TEMP_PRJ_DIR_SHORT + File.separator;

  // web directory with test files: /target/web_root/config/projects
  public static final String WORK_PRJ_DIR_S = TestConstants.TARGET_DIR +
      TestConstants.WEB_ROOT_DIR + TestConstants.CONFIG_DIR +
      PrjMgrConstants.PRJ_DIR;

  //-- /target/web_root/config/projects/
  public static final String WORK_PRJ_DIR = WORK_PRJ_DIR_S + File.separator;

  // - target/demo
  public static final String DEMO_PRJ_DIR_S = TestConstants.TARGET_DIR + "demo";

  //- target/demo/
  public static final String DEMO_PRJ_DIR = DEMO_PRJ_DIR_S + "/";

  //-- /src/test/web_root/config/projects
  public static final String SRC_PRJ_DIR = TestConstants.SRC_CONFIG_DIR +
      File.separator + PrjMgrConstants.PRJ_DIR;

  // Number of concurrent threads to test project management
  public static int THREAD_NUM = 10;

  /*************** GUI TEST ***************/
  // List of component groups
  public static HashMap<String, ArrayList<String>> COMP_LIST = 
                            new HashMap<String, ArrayList<String>>();
  static {
    COMP_LIST.put("LL_DEMO_COMPONENTS", new ArrayList<String>(
        Arrays.asList(new String[] {"images/comp_icons/demo.png"})));
  }
  
  // List of dataset sources
  public static String[][] DS_LIST = new String[][] {
    new String[] {"local1", "/osbiws_core"},
    new String[] {"local2", "/osbiws_core"}
  };
  
  public static String[][] DB_LIST = new String[][] {
    new String[] {"hsql1", "hsql1"},
    new String[] {"hsql2", "hsql2"}
  };
  
  // Label name for page title
  public static String LL_TOOL_TITLE = "LL_PRJ_MGR_TOOL_TITLE";
  
  // Entity context menu
  public static String[][] CTX_MENU = new String[][] {
    new String[] {"LL_RENAME_ENTITY", 
                      "modules/prj/images/rename_entity.png"},
    new String[] {"LL_DELETE_ENTITY", 
                      "modules/prj/images/delete_entity.png"},
    // Divider
    null,

    new String[] {"LL_DOWNLOAD", 
                "modules/prj/images/download_entity.png"}
  };
}
