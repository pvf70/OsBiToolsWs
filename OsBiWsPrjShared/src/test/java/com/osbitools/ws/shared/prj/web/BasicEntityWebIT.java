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

import org.junit.Test;

import java.net.URLEncoder;
import java.util.HashMap;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.web.*;

/**
 * Test Entity Single Thread processing using web access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class BasicEntityWebIT extends BasicPrjMgrWebTestIT {
  
  // Expected result from pen test
  private static final HashMap<String, WebResponse> TEST_RES = 
                                new HashMap<String, WebResponse>();
  
  static {
    TEST_RES.put("get", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("put", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("post", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("delete", HTTP_RESP_NON_AUTH_ALL);
  }
  
  @Test
  public void testEntityBadId() throws Exception {
    String ext = FUTILS.getBaseExt();
    String stoken = login();
    for (int i = 0; i < GenericTest.BAD_ID_LIST.length; i++) {
      String id = GenericTest.BAD_ID_LIST[i];
      String ide = URLEncoder.encode(id, "UTF-8");
      
      testWebResponse("Get '" + id + "'", new WebResponse(400, 
          "ERROR #41 - Invalid File Name. INFO: Invalid " +
              "name \\\"" + id + "\\\""), 
                  readGet(_entity_mname_path + ide, stoken));
      
      testWebResponse("Get '" + id + "." + id + "'", new WebResponse(400, 
          "ERROR #43 - Invalid File Extension. " +
              "INFO: Expected " + ext + " extension but found \\\"" + id + "\\\""), 
                  readGet(_entity_mname_path + ide + 
                                        UTF8_DOT + ide, stoken));
      
      testWebResponse("Get '." + id + "'", new WebResponse(400,
        "ERROR #41 - Invalid File Name. INFO: Invalid name \\\"." + 
           id + "\\\""), readGet(_entity_mname_path + UTF8_DOT + ide, stoken));
      
      testWebResponse("Get ../'" + id + "'", new WebResponse(400, 
        "ERROR #43 - Invalid File Extension. " +
            "INFO: Expected " + ext + 
                " extension but found \\\"/" + id + "\\\""), 
                    readGet(_entity_mname_path + 
                          URLEncoder.encode("../", "UTF-8") + ide, stoken));
    }
    
    logout(stoken, "Entity Bad Names");
  }
  
  @Test
  public void testEntityBadNames() throws Exception {
    String ext = FUTILS.getBaseExt();
    String stoken = login();
    
    for (int i = 0; i < GenericTest.BAD_ID_NAMES.length; i++) {
      String id = GenericTest.BAD_ID_NAMES[i];
      String ide = URLEncoder.encode(id, "UTF-8");
      
      testWebResponse("Get '" + id + "'", new WebResponse(400, 
          "ERROR #42 - Invalid File Name. INFO: Extension not found in " +
              "name \\\"" + id +"\\\""), 
                  readGet(_entity_mname_path + ide, stoken));
      
      testWebResponse("#" + i +" Get '" + id + "." + id + "'", 
          new WebResponse(400, "ERROR #43 - Invalid File Extension. " +
          		"INFO: Expected " + ext + " extension " +
                "but found \\\"" + id + "\\\""), 
                  readGet(_entity_mname_path + ide + 
                                          UTF8_DOT + ide, stoken));
      
      testWebResponse("Get '." + id + "'", new WebResponse(400,
          "ERROR #43 - Invalid File Extension. INFO: Expected " + 
            ext + " extension " +
              "but found \\\"" + id + "\\\""), 
                  readGet(_entity_mname_path + UTF8_DOT + ide, stoken));
      
      testWebResponse("Get ../'" + id + "'", new WebResponse(400, 
          "ERROR #43 - Invalid File Extension. INFO: Expected " + 
              ext + " extension " +
                  "but found \\\"/" + id + "\\\""), 
                    readGet(_entity_mname_path + 
                          URLEncoder.encode("../", "UTF-8") + ide, stoken));
    }
    
    testWebResponse("Wrong result reading file that doesn't exists", 
      	new WebResponse(404, "@ERROR #31 - Entry not found. " +
      			"INFO: Entry \\\\\".*test." + ext + "\\\\\" not found@"), 
          			readGet(_entity_mname_path + "test." + ext + "", stoken));
    
    logout(stoken, "Entity Bad Names");
  }
  
  @Test
  public void testTestEntityGood() throws Exception {
    String stoken = login();
    
    // Test both Test Set and Demo Set
    String[][][] dset = new String[][][] {
        FUTILS.getJsonTestSet(true),
        FUTILS.getJsonDemoSet(true),
    };
    
    for (String[][] jset: dset)
      for (int i = 0; i < jset.length; i++)
        testWebResponse("Fail getting " + jset[i][0] + " file", 
          new WebResponse("{" +jset[i][1] + 
              ",\"has_log\":false,\"has_diff\":true}"),
                readGet(_entity_mname_path + jset[i][0] + 
                                "." + FUTILS.getBaseExt(), stoken));
    
    logout(stoken, "Entity Bad Names");
  }
  
  @Test
  public void testEntityCreateSimple() throws Exception {
    String ext = FUTILS.getBaseExt();
  	String dsname = "test" + getDirPrefix();
  	
  	String stoken = login();
  	
  	// Create temp directory
   	testWebResponse("Fail creating '" + dsname + "'", 
    			 HTTP_RESP_OK, readPut(_proj_path + "?name=" + dsname, "", stoken));
   	
   	String prefix = dsname + ".";
  	
   	String[][] dset = FUTILS.getTestSet();
   	String[][] jset = FUTILS.getJsonTestSet(true);
   	
   	String ds = FUTILS.readDemoFileAsText(dset[0][0]);
  	
  	String dst = FUTILS.readDemoFileAsText(dset[1][0]);
  	
  	testWebResponse("Wrong result creating empty entity", 
        new WebResponse("{\"request_id\":,\"error\":{\"id\":233,\"msg\":" +
        	"\"Empty Entity\",\"info\":\"Empty Entity body\"," +
        		"\"details\":[]}}"), 
        			readPut(_entity_mname_path + 
        					prefix + "qq." + ext + "", "", stoken));
  	
    for (String fname: new String[] {"ds1." + 
                          FUTILS.getBaseExt(), "dst." + ext})
	    testWebResponse("Fail creating entity " + prefix + fname, 
	        new WebResponse("{" + jset[0][1] + "}"), 
	            readPut(_entity_mname_path + prefix + fname, ds, stoken));
    
    // Try create project with same name
    for (String fname: new String[] {"ds1." + 
                      FUTILS.getBaseExt(), "dst." + ext})
	  	testWebResponse("Create " + fname, 
				new WebResponse(422, "@^ERROR #32 - Entry already exists. " +
					"INFO: Entry \\\\\".*" + fname + "\\\\\" already exists$@"), 
					          readPut(_entity_mname_path + prefix + fname, ds, stoken));
  	
    // Test create result
    for (String fname: new String[] {"ds1." + ext, "dst." + ext})
      testWebResponse("Fail reading entity " + prefix + fname, 
          new WebResponse("{" + jset[0][1] + 
              ",\"has_log\":false,\"has_diff\":true}"), 
                readGet(_entity_mname_path + prefix + fname, stoken));
    
    // Update entities
    for (String fname: new String[] {"ds1." + ext, "dst." + ext})
	    testWebResponse("Fail updating entity " + prefix + fname, 
	        new WebResponse("{" + jset[1][1] + "}"), 
	            readPost(_entity_mname_path + prefix + fname, dst, stoken));
    
    // Test update result result
    for (String fname: new String[] {"ds1." + ext, "dst." + ext})
      testWebResponse("Fail reading entity " + prefix + fname, 
        new WebResponse("{" + jset[1][1] + 
                                    ",\"has_log\":false,\"has_diff\":true}"), 
            readGet(_entity_mname_path + prefix + fname, stoken));
    
  	// Try rename to itself
  	testWebResponse("Renaming ds1." + ext + " to itself", 
  			new WebResponse("@\\{\"request_id\":,\"error\":\\{\"id\":45," +
  				"\"msg\":\"Can not rename file to itself\",\"info\":\"Can not " +
  				"rename file \\\\\".*ds1." + ext + "\\\\\" to itself\"," +
  				"\"details\":\\[\\]\\}\\}@"), 
  					readPost(_entity_mname_path + prefix + "ds1." + ext + 
  					    "&rename_to=" + prefix + "ds1." + ext, "", stoken));
  	
  	// Try rename project with wrong parameter
  	testWebResponse(new WebResponse("{\"request_id\":,\"error\":{\"id\":233," +
  		"\"msg\":\"Empty Entity\",\"info\":\"Empty Entity body\"," +
  	    "\"details\":[]}}"),
      		readPost(_entity_mname_path + prefix + 
      				"ds1." + ext + "&rename=" + prefix + "ds2." + ext, "", stoken));
  	
  	// Try rename project with empty parameter
   	testWebResponse(new WebResponse(422, "ERROR #230 - Error renaming file. " +
   			"INFO: Empty parameter rename_to"),
       			readPost(_entity_mname_path +
       					prefix + "ds1." + ext + "&rename_to=", "", stoken));
  	
   	// Renaming ds1.xml -> ds2.xml
  	testWebResponse("Fail renaming ds1." + ext + " -> ds2." + ext, 
  			HTTP_RESP_OK, readPost(_entity_mname_path + 
  						prefix + "ds1." + ext + "&rename_to=" + 
  														prefix + "ds2." + ext, "", stoken));
  	
  	// Deleting project with wrong name
    testWebResponse(new WebResponse(404, "@^ERROR #31 - Entry not found. " +
    		"INFO: Entry \\\\\".*ds1." + ext + "\\\\\" " + "not found$@"), 
    		readDelete(_entity_mname_path + prefix + "ds1." + ext, "", stoken));
    
    testWebResponse(HTTP_RESP_OK, 
    		readDelete(_entity_mname_path + prefix + "ds2." + ext, "", stoken));
    
    testWebResponse(HTTP_RESP_OK, 
    		readDelete(_entity_mname_path + prefix + "dst." + ext, "", stoken));
    
  	testWebResponse("Temp project " + dsname + " is not empty", 
  			HTTP_RESP_OK, readGet(_proj_path + "?name=" + dsname, stoken));
  	
  	testWebResponse("Fail deleting '" + dsname + "'", 
 			 HTTP_RESP_OK, readDelete(_proj_path + "?name=" + dsname, "", stoken));
  	
  	logout(stoken, "Simple Entity Create");
  }

  @Override
  public HashMap<String, WebResponse> getNonAuthTestExpectedSet() {
    return TEST_RES;
  }

  @Override
  public String getWepAppPath() {
    return _entity_app_name;
  }
}
