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

package com.osbitools.ws.me.web;

import java.util.HashMap;
import org.junit.Test;

import com.osbitools.ws.shared.prj.web.BasicPrjMgrWebTestIT;
import com.osbitools.ws.shared.web.WebResponse;
import com.osbitools.ws.me.utils.CsvExFileMgrTest;
import com.osbitools.ws.me.utils.MeTestFileUtils;

/**
 * Test DsMap Single Thread processing using web access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class CsvExFileWebIT extends BasicPrjMgrWebTestIT {
  
	private final String _csv_fname_path = 
	                          _ex_file_path + "?dname=csv&name=";
	
  // Expected result from pen test
  private static final HashMap<String, WebResponse> TEST_RES = 
                                new HashMap<String, WebResponse>();
  
  static {
    TEST_RES.put("get", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("put", HTTP_RESP_NOT_ALLOWED);
    TEST_RES.put("post", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("delete", HTTP_RESP_NON_AUTH_ALL);
  }
  
  @Test
  public void testTestCsvFile() throws Exception {
    String stoken = login();
    
    String dname = "test";
    
    // Read list of test files
    testWebResponse("Read list of test files", 
        new WebResponse(MeTestFileUtils.CSV_FILE_LIST), 
              readGet(_csv_fname_path + dname + ".*", stoken));
    
    for (int i = 0; i < CsvExFileMgrTest.TEST_CSV_FILES.length; i++) {
      String[] ftest = CsvExFileMgrTest.TEST_CSV_FILES[i];
      
      // Get parameters
      String sparam = "";
      HashMap<String, String> params = MeTestFileUtils.TEST_CSV_COL_PARAMS[i];
      for (String key: params.keySet())
        sparam += "&param_" + key + "=" + params.get(key);
      
      testWebResponse("Read test columns for " + ftest[0] + " " + sparam + ":", 
          new WebResponse(ftest[1]), 
                readGet(_csv_fname_path + dname + "." + 
                            ftest[0] + "&info=columns" + sparam, stoken));
      
      testWebResponse("Read test file_info for " + ftest[0], 
          new WebResponse(ftest[3]), 
                readGet(_csv_fname_path + dname + "." + 
                    ftest[0] + "&info=file_info", stoken));
    }
    
    // Read columns and file_info for non-existing file
    testWebResponse("Wrong result reading columns that doesn't exists", 
        new WebResponse(404, "@ERROR #31 - Entry not found. " +
        		"INFO: Entry \\\\\".*xxx.csv\\\\\" not found@"), 
              readGet(_csv_fname_path + dname + ".xxx.csv" + 
                          "&info=columns", stoken));
    
    testWebResponse("Wrong result reading file_info that doesn't exists", 
        HTTP_RESP_OK, readGet(_csv_fname_path + dname + ".xxx.csv" + 
                                              "&info=file_info", stoken));
    
    logout(stoken, "Test Csv Files");
  }
  
  @Test
  public void testCsvFileCreateSimple() throws Exception {
  	String dsname = "test" + getDirPrefix();
  	
  	String stoken = login();
  	
  	// Create temp directory
   	testWebResponse("Fail creating '" + dsname + "'", 
    			 HTTP_RESP_OK, readPut(_proj_path + "?name=" + dsname, "", stoken));
   	
   	String prefix = dsname + ".";
   	
   	// Try creating file with wrong extension
   	testWebResponse(new WebResponse("{\"request_id\":,\"error\":{\"id\":234," +
   			"\"msg\":\"Unsupported resource type\",\"info\":\"Unknown " +
   			                        "subdirectory [qq]\",\"details\":[]}}"),
       			readPost(_ex_file_path + "?name=" +
       					prefix + "test.csv&dname=qq", "", stoken));
   	
   	// Try creating empty file
   	testWebResponse(new WebResponse("{\"request_id\":,\"error\":{\"id\":255," +
   		"\"msg\":\"Error processing uploading request\"," +
   		      "\"info\":\"Request is not multipart\",\"details\":[]}}"), 
   		                readPost(_csv_fname_path + prefix , "", stoken));
   	
   	// TODO Post multipart file
   	
    testWebResponse("Temp project " + dsname + " is not empty", 
        HTTP_RESP_OK, readGet(_proj_path + "?name=" + dsname, stoken));
    
   	testWebResponse("Fail deleting '" + dsname + "'", 
  			 HTTP_RESP_OK, readDelete(_proj_path + "?name=" + dsname, "", stoken));
   	
   	logout(stoken, "Csv File Create");
  }

  @Override
  public HashMap<String, WebResponse> getNonAuthTestExpectedSet() {
    return TEST_RES;
  }

  @Override
  public String getWepAppPath() {
    return _ex_file_app_name;
  }

  @Override
  public String getPenTestParamName() {
    return null;
  }
}
