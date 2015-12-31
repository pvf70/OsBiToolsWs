package com.osbitools.ws.core.shared;

import java.io.File;

import com.osbitools.ws.shared.*;

/**
 * Constants used solely by unit and/or integration test
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class LocalTestConstants {

  // src/test/
  public static final String TEST_DIR = "src" + 
                                File.separator + "test" + File.separator;

  // src/test/config
  public static final String TEST_CONFIG_DIR_SHORT = TEST_DIR + "config";

  // src/test/config/
  public static final String TEST_CONFIG_DIR = 
                                    TEST_CONFIG_DIR_SHORT + File.separator;

  public static final String TEST_PRJ_NAME = "test";
  
  public static final String BAD_PRJ_NAME = "bad";
  
  // target/ds
  public static final String WORK_DS_DIR_SHORT = 
                            TestConstants.TARGET_DIR + DsConstants.DS_DIR;

  // target/ds/
  public static final String WORK_DS_DIR = WORK_DS_DIR_SHORT + File.separator;
  
  // target/ds/test
  public static final String WORK_TEST_PROJ = WORK_DS_DIR + TEST_PRJ_NAME;
  
  //target/ds/bad
  public static final String WORK_BAD_PROJ = WORK_DS_DIR + BAD_PRJ_NAME;
 
  // target/csv
  public static final String WORK_CSV_DIR = TestConstants.TARGET_DIR + 
                                                        File.separator + "csv";
  
  // test.
  public static final String TEST_PRJ_PREFIX = TEST_PRJ_NAME + ".";

  // bad.
  public static final String BAD_PRJ_PREFIX = BAD_PRJ_NAME + ".";
  
  public static final String DS_XML = "ds.xml";
  
  public static final String DS_EMPTY_XML = "ds_empty.xml";

  // target/ds/test/ll_set
  public static final String LS_SET_TEST_FILE_PATH = 
                LocalTestConstants.WORK_TEST_PROJ +
                            File.separator + LsConstants.LANG_SET_FILE;
  
  // test.ls_set
  public static final String TEST_LS_FILE_KEY = 
          LocalTestConstants.TEST_PRJ_PREFIX + LsConstants.LANG_SET_FILE;
  
  public static final int DAEMON_SCAN_TIME = 200; // msec

  public static final int RESULT_CHECK_TIME = 10; // msec

  // Wait time to file change
 public static final int CHANGE_DETECT_TIME = DAEMON_SCAN_TIME * 2;
 
  // Wait time to load/reload file
  public static final int WAIT_TIME = DAEMON_SCAN_TIME * 5;

  public static final String TEST_USER = "user";

  public static final String TEST_PASSWORD = "test";

  // Number of lang labels for test
  public static final int LL_NUM = 3;
  
  // Database parameters
  public static final String TEST_DB_NAME = "jdbc:hsqldb:hsql://localhost/test";
  public static final String TEST_DB_USER = "SA";
  public static final String TEST_DB_PASSWORD = "test";

}
