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

package com.osbitools.ws.pd.shared;

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
  
  // Source test directory with Site files: /src/test/web_root/config/sites
  public static final String SRC_SITES_DIR = TestConstants.SRC_CONFIG_DIR + 
                                File.separator + LocalConstants.SITES_DNAME;
  
  // web directory with test files: /target/web_root/config/sites
  public static final String WORK_SITES_DIR_S = 
    TestConstants.WORK_CONFIG_DIR + File.separator + LocalConstants.SITES_DNAME;
  
  //-- /target/web_root/config/projects/
  public static final String WORK_SITES_DIR = WORK_SITES_DIR_S + File.separator;
  
  /*************** GUI TEST ***************/
  // List of component groups
  public static HashMap<String, ArrayList<String>> COMP_LIST = 
                            new HashMap<String, ArrayList<String>>();
  static {
    COMP_LIST.put("LL_CONTAINERS", new ArrayList<String>(
        Arrays.asList(new String[] {
            "web_widgets/com/osbitools/containers/tab_box/tab_box.png",
            "web_widgets/com/osbitools/containers/header_box/header_box.png"
        }
    )));
  }
  
  // Label name for page title
  public static String LL_TOOL_TITLE = "LL_PAGE_DESIGNER_TITLE";
  
  //List of dataset sources
  public static String[][] DS_LIST = new String[][] {
    new String[] {"local1", "osbiws_core"},
    new String[] {"local2", "osbiws_core"}
  };
  
  //Entity context menu
  public static String[][] EXTRA_EXPORT_CTX_MENU = new String[][] {
    // Divider
    null,
    // Export menu items
    new String[] {"LL_EXPORT_TO_WEB_SITE", "images/export.png"},
    new String[] {"LL_EXPORT_TO_JS_EMBEDDED", "images/export.png"}
  };
}
