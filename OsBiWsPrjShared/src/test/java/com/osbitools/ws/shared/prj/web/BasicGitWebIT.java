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

import java.net.URLEncoder;
import java.util.HashMap;

import org.junit.Test;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.web.WebResponse;

/**
 * Test DsMap Single Thread processing using web access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class BasicGitWebIT extends BasicPrjMgrWebTestIT {
	
  private static String _git_map_app_name = "/git";
  
  private static String _git_map_app = BASE_URL + _git_map_app_name;

  private static String _git_map_path = 
                                  TestConstants.JETTY_SRV_URL + _git_map_app;
  
  private static String _git_mname_path = _git_map_path + "?name=";

  // Expected result from pen test
  private static final HashMap<String, WebResponse> TEST_RES = 
                                new HashMap<String, WebResponse>();
  
  static {
    TEST_RES.put("get", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("put", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("post", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("delete", HTTP_RESP_NOT_ALLOWED);
  }
  
  @Test
  public void testGitCommit() throws Exception {
    int len = 2;
    String dsname = "test" + getDirPrefix();
    
    String stoken = login();
    
    // Create temp directory
    testWebResponse("Fail creating '" + dsname + "'", 
           HTTP_RESP_OK, readPut(_proj_path + "?name=" + dsname, "", stoken));
    
    String prefix = dsname + ".";
    
    String[] src = new String[len];
    String[][] commits = new String[2][len];
    String[] comments = new String[] {"Created", "Updated"};
    
    String[][] dset = FUTILS.getJsonTestSet(true);
    
    for (int i = 0; i < len; i++)
      src[i] = FUTILS.readDemoFileAsText(dset[i][0]);
    
    for (int i = 0; i < len; i++) {
      String fname = "ds" + i + "." + FUTILS.getBaseExt();
      testWebResponse("Fail creating map " + fname, 
          new WebResponse("{" + dset[0][1] + "}"), 
            readPut(_entity_mname_path + prefix + fname, src[0], stoken));
      
      WebResponse commit = readPost(_git_mname_path + prefix + fname + 
      	"&comment=" + URLEncoder.encode(comments[0] + 
      												" #" + i, "UTF-8"), "", stoken);
      commits[i][0] = commit.getMsg();
      assertEquals(200, commit.getCode());
      
      testWebResponse("Fail creating map " + fname, 
          new WebResponse("{" + dset[1][1] + "}"), 
            readPost(_entity_mname_path + prefix + fname, src[1], stoken));
      
      commit = readPost(_git_mname_path + prefix + fname + "&comment=" + 
      						URLEncoder.encode(comments[1] + 
      								" #" + i, "UTF-8"), "", stoken);
      commits[i][1] = commit.getMsg();
      assertEquals(200, commit.getCode());
      
      // Read all commits for file
      String log = "";
      for (int j = 1; j >= 0; j--) {
        String rev_id = commits[i][j];
      	log += ",\\{\"id\":\"" + rev_id  + "\"," + "\"comment\":\"" + 
      	    comments[j] +" #" + i + "\",\"commit_time\":\\d*," +
      	    		                        "\"committer\":\"user\"\\}";
      	
      	// Read actual file
      	testWebResponse(new WebResponse("{" + dset[j][1] + "}"), 
      	    readGet(_git_mname_path + prefix + fname + 
      	                            "&rev=" + rev_id, stoken));
      }
      
      WebResponse wlog = readGet(_git_mname_path + prefix + fname, stoken);
      testWebResponse(new WebResponse("@\\[" + log.substring(1) +
      																											"\\]@"), wlog);
      assertEquals(200, wlog.getCode());
    }
    
    for (int i = 0; i < len; i++)
      	testWebResponse(HTTP_RESP_OK, 
      			readDelete(_entity_mname_path + prefix + 
                        "ds" + i + "." + 
                              FUTILS.getBaseExt(), "", stoken));
    
    testWebResponse("Temp project " + dsname + " is not empty", 
        HTTP_RESP_OK, readGet(_proj_path + "?name=" + dsname, stoken));
    
    testWebResponse("Fail deleting '" + dsname + "'", 
    		HTTP_RESP_OK, readDelete(_proj_path + "?name=" + dsname, "", stoken));
    
    logout(stoken, "Git Create");
    
  }
  
  @Test
  public void testGitRemote() throws Exception {
  	String stoken = login();
  	
  	// Test wrong command
  	testWebResponse("Trying invalid git remote action", 
  		new WebResponse(422, "ERROR #248 - Invalid remote operation." +
  			" INFO: Invalid operation [zzz]"), 
  					readPut(_git_mname_path + "zzz", "", stoken));
  	
  	String fstr = "{\"request_id\":,\"error\":{\"id\":246," +
  		"\"msg\":\"URL for remote git repository is not configured\"," +
  		"\"info\":\"Configuration parameter [git_remote_url] is not set\"," +
  		"\"details\":[]}}";
  	
  	// Fail push
   	testWebResponse("Trying fail push git remote", 
   		new WebResponse(fstr), 
   					readPut(_git_mname_path + "push", "", stoken));
   	
   	testWebResponse("Trying fail push git remote", 
     		new WebResponse(fstr), 
     					readPut(_git_mname_path + "pull", "", stoken));
   	
  	logout(stoken, "Git Create");
  }

  @Override
  public HashMap<String, WebResponse> getNonAuthTestExpectedSet() {
    return TEST_RES;
  }

  @Override
  public String getWepAppPath() {
    return _git_map_app_name;
  }
}
