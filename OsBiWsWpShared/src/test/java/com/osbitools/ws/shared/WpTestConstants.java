/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-02T21:00Z
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared;

import java.io.File;

public class WpTestConstants {

  //Path for demo web pages
  public static final String WEB_PAGES_PACKAGE = 
                                     "com/osbitools/ws/shared/demo/config/sites";
  
  // Working test directory with Web Page files - target/web_root/config/sites
  // Used by Jetty during integration test
  public static final String WORK_WP_DIR_S = TestConstants.WORK_CONFIG_DIR + 
                                          File.separator + WpConstants.WP_DIR;
 
  // - target/web_root/config/ds/
  // Used by Jetty during integration test
  public static final String WORK_WP_DIR = WORK_WP_DIR_S + File.separator;
}
