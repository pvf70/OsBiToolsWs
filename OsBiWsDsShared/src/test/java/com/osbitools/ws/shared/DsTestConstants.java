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

import java.io.File;

/**
 * DataSet Maps Test Contstants
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */

public class DsTestConstants {

  //Path for demo maps
  public static final String DS_MAPS_PACKAGE = 
                                     "com/osbitools/ws/shared/demo/config/ds";
  
  // Working test directory with DataSet maps - target/web_root/config/ds
  // Used by Jetty during integration test
  public static final String WORK_DS_DIR_S = TestConstants.WORK_CONFIG_DIR + 
                                          File.separator + DsConstants.DS_DIR;
 
  // - target/web_root/config/ds/
  // Used by Jetty during integration test
  public static final String WORK_DS_DIR = WORK_DS_DIR_S + File.separator;
  
}
