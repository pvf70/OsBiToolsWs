/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
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

package com.osbitools.ws.shared.prj.web;

import static org.junit.Assert.*;

import java.io.*;
import java.util.HashMap;

import org.junit.*;
import org.apache.log4j.Level;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.web.*;

/**
 * Test DsMap Single Thread processing using web access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class BasicEntityUtilsWebIT extends BasicPrjMgrWebTestIT {
  
  private static String _eu_app_name = "/entity_utils";
  
  private static String _eu_app = BASE_URL + _eu_app_name;

  private static String _eu_map_path = TestConstants.JETTY_SRV_URL + _eu_app;
  
  private static String _eu_mname_path = _eu_map_path + "?name=";
	
  // Original Log Level
  private static Level _lvl;
  
  //Name of test file
  private String _fname;
  
  File _f;
  
  // Expected result from pen test
  private static final HashMap<String, WebResponse> TEST_RES = 
                                new HashMap<String, WebResponse>();
  
  static {
    TEST_RES.put("get", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("put", HTTP_RESP_NOT_ALLOWED);
    TEST_RES.put("post", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("delete", HTTP_RESP_NOT_ALLOWED);
  }
  
  @BeforeClass
  public static void disableLog() {
    // Reference TestConstants to activate default Logger
    _lvl = TestConstants.LOG.getLevel();
    TestConstants.LOG.setLevel(Level.INFO);
  }
  
  @Before
  public void testFileExistsOnStart() {
    _fname = "test_" + getDirPrefix() + "." + FUTILS.getBaseExt();
    
    _f = new File(FUTILS.getProjWorkDir() + _fname);
    if (_f.exists())
      assertTrue("Can't delete " + _fname, _f.delete());
  }
  
  @Test
  public void testEntityUtils() throws Exception {
    String stoken = login();
    
    // Read map info for non-existing file
    testWebResponse(HTTP_RESP_OK, 
            readGet(_eu_mname_path + _fname + "&check", stoken));
    
    String fbody = FUTILS.readDemoFileAsText();
    String ftext = "{" + FUTILS.readDemoFileRespAsText(true) + "}";
    
    // Upload file
    testWebResponse(new WebResponse(ftext), 
      uploadFile(_eu_mname_path + _fname, _fname, 
                      FUTILS.readDemoFileAsStream(), stoken));
    
    // Try upload same file again
    testWebResponse(new WebResponse(422, 
      "@^ERROR #32 - Entry already exists. " +
        "INFO: Entry \\\\\".*" + _fname + "\\\\\" already exists$@"), 
            uploadFile(_eu_mname_path + _fname, _fname, 
                FUTILS.readDemoFileAsStream(), stoken));
    
    // Read file info after upload
    testWebResponse(new WebResponse("@\\{\"file_info\":\\{\"size\":" + 
        FUTILS.readDemoFileSize() + "," +
          "\"read\":true,\"write\":true,\"last_modified\":\\d*\\}\\}@"), 
                  readGet(_eu_mname_path + _fname + "&check", stoken));
    
    // Read map body after upload
    testWebResponse(new WebResponse(fbody), 
              readGet(_eu_mname_path + _fname, stoken));
        
    // Delete map after upload
    testWebResponse(HTTP_RESP_OK, 
       readDelete(_entity_mname_path + _fname, "", stoken));
    
    logout(stoken, "Test DsMap Utils");
  }
  
  @After
  public void checkTempFile() {
    assertTrue("File " + _fname + " still exists", !_f.exists());
  }
  
  @AfterClass
  public static void retLog() throws IOException {
    // Return back original log level
    TestConstants.LOG.setLevel(_lvl);
  }

  @Override
  public HashMap<String, WebResponse> getNonAuthTestExpectedSet() {
    return TEST_RES;
  }

  @Override
  public String getWepAppPath() {
    return _eu_app_name;
  }
}
