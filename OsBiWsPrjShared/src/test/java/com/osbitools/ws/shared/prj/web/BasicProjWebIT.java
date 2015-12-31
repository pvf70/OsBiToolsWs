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

import java.net.URLEncoder;
import java.util.HashMap;

import static org.junit.Assert.*;

import org.junit.Test;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.web.*;

/**
 * Test Project Single Thread processing using web access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class BasicProjWebIT extends BasicPrjMgrWebTestIT {

	private static final String ERROR_RENAME_249 = 
			"ERROR #299 - Missing mandatory parameter. " +
									"INFO: Missing parameter 'rename_to'";
	
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
  public void testProjectBadNames() throws Exception {
    String stoken = login();
    
    for (int i = 0; i < GenericTest.BAD_ID_LIST.length; i++) {
      String id = GenericTest.BAD_ID_LIST[i];
      String ide = URLEncoder.encode(id, "UTF-8");
      
      testWebResponse("Get '" + id + "'", new WebResponse(400, 
          "ERROR #33 - Invalid Entry name. " +
              "INFO: Invalid name \\\"" + id +"\\\""), 
                  readGet(_proj_mname_path + ide, stoken));
      
      testWebResponse("Get '" + id + "." + id + "'", new WebResponse(400, 
          "ERROR #30 - Invalid project structure. INFO: " +
          "Only " + Constants.MAX_PROJ_LVL + 
            " level structure supported " +
                "but found 2 in name \\\"" + id + "." + id + "\\\""), 
                  readGet(_proj_mname_path + ide + 
                        URLEncoder.encode(".", "UTF-8") + ide, stoken));
      
      testWebResponse("Get '." + id + "'", new WebResponse(400, 
          "ERROR #30 - Invalid project structure. INFO: " +
          "Only " + Constants.MAX_PROJ_LVL + 
            " level structure supported " +
                "but found 2 in name \\\"." + id + "\\\""), 
                  readGet(_proj_mname_path + 
                        URLEncoder.encode(".", "UTF-8") + ide, stoken));
      
      testWebResponse("Get ../'" + id + "'", new WebResponse(400, 
          "ERROR #30 - Invalid project structure. INFO: Only " + 
              Constants.MAX_PROJ_LVL + " level structure supported " +
                "but found 3 in name \\\"../" + id + "\\\""), 
                  readGet(_proj_mname_path + 
                        URLEncoder.encode("../", "UTF-8") + ide, stoken));
    }
    
    logout(stoken, "Project Bad Names");
  }
  
  @Test
  public void testProjectSelect() throws Exception {
    String stoken = login();
    
    for (String[] tp : FUTILS.getProjList()) {
      WebResponse data = readGet(_proj_mname_path + tp[0], stoken);
      
      WebResponse list = new WebResponse(data.getMsg());

      // Check new project content
      testWebResponse("Fail getting test project",
                                    new WebResponse(tp[1]), list);
    }
    
    logout(stoken, "Test Project Select");
  }
  
  @Test
  public void testProjectCreateSimple() throws Exception {
  	String ds = "test" + getDirPrefix();
  	String dst = ds +"_tmp";
  	
  	String stoken = login();
  	
  	testWebResponse("Fail creating '" + ds + "'", 
   			 HTTP_RESP_OK, readPut(_proj_mname_path + ds, "", stoken));
  		
		// Check new project content
		testWebResponse("Fail getting '" + ds + "' project", 
  			 HTTP_RESP_OK, readGet(_proj_mname_path + ds, stoken));
  		
  		
  	assertEquals("Project with prefix '" + getDirPrefix() + "' not found", 
  																									ds, getProjDirList());
  		
  	// Try create project with same name
  	testWebResponse("Create '" + ds + "'", 
			new WebResponse(422, "@^ERROR #32 - Entry already exists. " +
				"INFO: Entry \\\\\".*" + ds + "\\\\\"" +
				" already exists$@"), readPut(_proj_mname_path + ds, "", stoken));
  		
  	// Try rename project with wrong parameter
  	testWebResponse(new WebResponse(400, ERROR_RENAME_249),
      		readPost(_proj_mname_path + ds + "&rename=" + dst, "", stoken));
  
  	// Try rename project with empty parameter
   	testWebResponse(new WebResponse(400, ERROR_RENAME_249),
       			readPost(_proj_mname_path + 
       					ds + "&rename_to=", "", stoken));
   	
  	// Try rename project to same name
   	testWebResponse(new WebResponse(422, "ERROR #221 - Can't rename to " +
   			"itself. INFO: Renaming " + ds + " to itself"),
       			readPost(_proj_mname_path + 
       					ds + "&rename_to=" + ds, "", stoken));
   	
  	// Renaming project to new name
  	testWebResponse("Fail renaming " + ds + "->" + dst,
  				HTTP_RESP_OK, readPost(_proj_mname_path + 
    					ds + "&rename_to=" + dst, "", stoken));
  	
  	// Deleting project with wrong name
    testWebResponse(new WebResponse(404, "@^ERROR #31 - Entry not found. " +
    		"INFO: Entry \\\\\".*" + ds + "\\\\\" " + "not found$@"), 
    		readDelete(_proj_mname_path + ds, "", stoken));
    
  	// Deleting project
    testWebResponse("Fail deleting " + dst, 
    			 HTTP_RESP_OK, readDelete(_proj_mname_path + dst, "", stoken));
    
    logout(stoken, "Project Create");
  }
  
  @Test
  public void testProjectCreateComplex() throws Exception {
  	int dnum = 3; // Directory cycles
  	String dst = "";
  	String dname = "test" + getDirPrefix();
  	
  	String stoken = login();
  	
  	for (int i = 0; i < dnum; i++) {
  		String dsi = dname + i;
  		
  		testWebResponse("Create '" + dname + "'", 
   			 HTTP_RESP_OK, readPut(_proj_mname_path + dname, "", stoken));
  		
  		// Check new project content
  		testWebResponse("Get '" + dname + "' project", 
    			 HTTP_RESP_OK, readGet(_proj_mname_path + dname, stoken));
  		
  		// Try create project with same name
  		testWebResponse("Create '" + dname + "'", 
  			new WebResponse(422, "@^ERROR #32 - Entry already exists. " +
  				"INFO: Entry \\\\\".*" + dname + "\\\\\" " +
  				"already exists?@"), readPut(_proj_path + 
  									"?name=" + dname, "", stoken));
  		
  		if (i > 0)
  			// Try rename project to existing name
  			testWebResponse("Renaming " + dname + "->" + dname + (i - 1),
    				new WebResponse(422, "@^ERROR #32 - Entry already exists. " +
    						"INFO: Entry \\\\\".*" + dname + (i - 1) + 
    																"\\\\\" already exists$@"),
      			readPost(_proj_mname_path + 
      					dname + "&rename_to=" + dname + (i - 1), "", stoken));
  		
  		// Try rename project
			testWebResponse("Renaming " + dname + "->" + dsi,
  				HTTP_RESP_OK, readPost(_proj_mname_path + 
  											dname + "&rename_to=" + dsi, "", stoken));
			
			dst += "," + dsi;
			
			assertEquals("Fail checking all projects with prefix '" + 
			    getDirPrefix() + "'", dst.substring(1), getProjDirList());
			
			// Wait 1 sec to detect different create time for directory
			Thread.sleep(1000);
  	}
  	
  	// Delete all working projects
    for (int i = 0; i < dnum; i++)
    	testWebResponse("Fail deleting " + dname + i, 
    		HTTP_RESP_OK, readDelete(_proj_path + 
    				"?name=" + dname + i, "", stoken));
    
    logout(stoken, "Project Create");
  }

  @Override
  public HashMap<String, WebResponse> getNonAuthTestExpectedSet() {
    return TEST_RES;
  }

  @Override
  public String getWepAppPath() {
    return _proj_app_name;
  }
  
}
