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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.junit.Test;
import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.web.BasicPrjMgrWebTestIT;
import com.osbitools.ws.shared.web.WebResponse;

/**
 * Test DsMap Single Thread processing using web access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class BasicExFileWebIT extends BasicPrjMgrWebTestIT {
  
  // Expected result from pen test
  private static final HashMap<String, WebResponse> TEST_RES = 
                                new HashMap<String, WebResponse>();
  
  static {
    TEST_RES.put("get", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("put", HTTP_RESP_NOT_ALLOWED);
    TEST_RES.put("post", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("delete", HTTP_RESP_NON_AUTH_ALL);
  }
	
  /**
   * Test Empty or non-existing name parameter
   * @throws Exception
   */
  @Test
  public void testExFileNoNameParam() throws Exception {
  	String stoken = login();
  	
  	for (String ext: FUTILS.getResDirList()) {
      String fpath = _ex_file_path + "?dname=" + ext + "&name=";
      
    	WebResponse wr = new WebResponse(400,
  				HTTP_RESP_MISSING_PARAMS.replace("xxx", "name"));
    	
    	WebResponse wrs = new WebResponse(400,
          HTTP_RESP_MISSING_PARAMS.replace("xxx", "name,dname") + "s");
    	
      testWebResponse("No name parameter",  wrs, 
                  										readGet(_ex_file_path, stoken));
      
      testWebResponse("No name parameter",  wr, 
                  										readGet(fpath, stoken));
   
      testWebResponse("Empty Project NonAuth Put", HTTP_RESP_NOT_ALLOWED, 
      									  readPut(_ex_file_path, "", stoken));
      
      testWebResponse("NonEmpty NonAuth Put", HTTP_RESP_NOT_ALLOWED, 
          	              readPut(fpath, "", stoken));
      
      testWebResponse("Empty Project NonAuth Post", wrs, 
      			              readPost(_ex_file_path, "", stoken));
      
      testWebResponse("NonEmpty NonAuth Post", wr, 
          	              readPost(fpath, "", stoken));
      
      testWebResponse("Empty Project NonAuth Delete", wrs, 
      			              readDelete(_ex_file_path, "", stoken));
      
      testWebResponse("NonEmpty NonAuth Delete", wr, 
        		              readDelete(fpath, "", stoken));
  	}
  	
    logout(stoken, "Project Empty Names");
  }
  
  @Test
  public void testExFileBadId() throws Exception {
    String stoken = login();
    
    for (String ext: FUTILS.getResDirList()) {
      String fpath = _ex_file_path + "?dname=" + ext + "&name=";
      
      testWebResponse("Get invalid info type 'xxx'", new WebResponse(400,
          "ERROR #42 - Invalid File Name." +
                  " INFO: Extension not found in name \\\"test\\\""
          ), readGet(fpath + "test", stoken));
      
      for (int i = 0; i < GenericTest.BAD_ID_LIST.length; i++) {
        String id = GenericTest.BAD_ID_LIST[i];
        String ide = URLEncoder.encode(id, "UTF-8");
        
        testWebResponse("Get '" + id + "'", new WebResponse(400, 
            "ERROR #41 - Invalid File Name. INFO: Invalid " +
                "name \\\"" + id +"\\\""), 
                    readGet(fpath + ide, stoken));
        
        testWebResponse("Get '" + id + "." + id + "'", new WebResponse(400, 
            "ERROR #258 - Invalid file extension." +
                        " INFO: File extension '" + id + "' is not supported"),
                    readGet(fpath + ide + 
                                          UTF8_DOT + ide, stoken));
        
        testWebResponse("Get '." + id + "'", new WebResponse(400,
            "ERROR #41 - Invalid File Name. INFO: Invalid " +
                "name \\\"." + id + "\\\""), 
                    readGet(fpath + UTF8_DOT + ide, stoken));
        
        testWebResponse("Get ../'" + id + "'", new WebResponse(400, 
            "ERROR #258 - Invalid file extension." +
                " INFO: File extension '/" + id + "' is not supported"),
                      readGet(fpath + 
                            URLEncoder.encode("../", "UTF-8") + ide, stoken));
      }
    }
    
    logout(stoken, "Project Bad Names");
  }
  
  @Test
  public void testExFileBadNames() throws Exception {
    String stoken = login();
    
    for (String dname: FUTILS.getResDirList()) {
      String fext = getFirstExt(dname);
      String fpath = _ex_file_path + "?dname=" + dname + "&name=";
      
      // Test bad info
      testWebResponse("Get invalid info type 'xxx'", new WebResponse(422,
          "ERROR #252 - Request for this info type is not supported." +
              " INFO: No additional details for this error." +
              " DETAILS: Info request 'xxx' is not supported.;"
          ), readGet(fpath + "test." + fext + "&info=xxx", stoken));
      
      for (int i = 0; i < GenericTest.BAD_ID_NAMES.length; i++) {
        String id = GenericTest.BAD_ID_NAMES[i];
        String ide = URLEncoder.encode(id, "UTF-8");
        
        testWebResponse("Get '" + id + "'", new WebResponse(400, 
            "ERROR #42 - Invalid File Name. INFO: Extension not found in " +
                "name \\\"" + id +"\\\""), 
                    readGet(fpath + ide, stoken));
        
        testWebResponse("#" + i +" Get '" + id + "." + id + "'", 
            new WebResponse(400, 
                "ERROR #258 - Invalid file extension. INFO: File extension '" + 
                                                      id + "' is not supported"),
                    readGet(fpath + ide + 
                                            UTF8_DOT + ide, stoken));
        
        testWebResponse("Get '." + id + "'", new WebResponse(400,
            "ERROR #258 - Invalid file extension. INFO: File extension '" + 
                                                      id + "' is not supported"), 
                    readGet(fpath + UTF8_DOT + ide, stoken));
        
        testWebResponse("Get ../'" + id + "'", new WebResponse(400, 
            "ERROR #258 - Invalid file extension. INFO: File extension '/" + 
                                                    id + "' is not supported"), 
                      readGet(fpath + 
                            URLEncoder.encode("../", "UTF-8") + ide, stoken));
      }
      
      testWebResponse("Wrong result reading file that doesn't exists", 
        	new WebResponse(404, "@ERROR #31 - Entry not found. " +
        			"INFO: Entry \\\\\".*test." + fext + "\\\\\" not found@"), 
            			readGet(fpath + "test." + fext, stoken));
    }
    
    logout(stoken, "Project Bad Names");
  }
  
  @Test
  public void testExFilePutGet() throws Exception {
    String stoken = login();
    
    String dname = "test";
    
    for (String ext: FUTILS.getResDirList()) {
      String fext = getFirstExt(ext);
      String fpath = _ex_file_path + "?dname=" + ext + "&name=";

      // Read list of test files
      testWebResponse("Read list of test files", 
          new WebResponse(FUTILS.getExtFileList(ext)), 
                readGet(fpath + dname + ".*", stoken));
      
      String[][] tset = FUTILS.getExtFileTestFiles(ext);

      for (int i = 0; i < tset.length; i++) {
        String[] ftest = tset[i];
        
        testWebResponse("Read test file_info for " + ftest[0], 
            new WebResponse(ftest[1]), 
                  readGet(fpath + dname + "." + 
                      ftest[0] + "&info=file_info", stoken));
      }
      
      testWebResponse("Wrong result reading file_info that doesn't exists", 
          HTTP_RESP_OK, readGet(fpath + dname + ".xxx." + fext + "" + 
                                                "&info=file_info", stoken));
    }
    
    logout(stoken, "Test Ex Files Put/Get");
  }
  
  @Test
  public void testExFileCreateSimple() throws Exception {
  	String dsname = "test_" + getDirPrefix();
  	
  	String stoken = login();

    for (String dname: FUTILS.getResDirList()) {
      String fpath = _ex_file_path + "?dname=" + dname + "&name=";
      

    	// Create temp directory
     	testWebResponse("Fail creating '" + dsname + "'", 
      			 HTTP_RESP_OK, readPut(_proj_path + "?name=" + dsname, "", stoken));
     	
     	String prefix = dsname + ".";
     	
     	// Try creating file with wrong extension
     	testWebResponse(new WebResponse("{\"request_id\":,\"error\":{\"id\":234," +
     			"\"msg\":\"Unsupported resource type\",\"info\":\"Unknown " +
     			                        "subdirectory [qq]\",\"details\":[]}}"),
         			readPost(_ex_file_path + "?name=" +
         					prefix + "test." + dname + "&dname=qq", "", stoken));
     	
     	// Try creating empty file
     	testWebResponse(new WebResponse("{\"request_id\":,\"error\":{\"id\":255," +
     		"\"msg\":\"Error processing uploading request\"," +
     		      "\"info\":\"Request is not multipart\",\"details\":[]}}"), 
     		                readPost(fpath + prefix , "", stoken));
     	
     	// Repeat for each file extension supported by resource 
     	for (String ext: FUTILS.getExtList(dname)) {
       	byte[] fbody = FUTILS.getDemoExFileAsBytes(dname, ext);
       	
       	String fname = dsname + ".test." + ext;
       	String npath = fpath + fname;
       	
       	// Upload multi-part file
       	InputStream in = new ByteArrayInputStream(fbody);
       	testWebResponse(new WebResponse(
       	    FUTILS.getDemoExFileUploadResp(dname, ext)), 
       	              uploadFile(npath, fname, in, stoken));
       	
       	// Try upload file with same name
       	in.reset();
       	testWebResponse(new WebResponse(422, 
            "@^ERROR #32 - Entry already exists. " +
                "INFO: Entry \\\\\".*" + dsname + "\\" + File.separator + 
                dname + "\\" + File.separator + "test\\." + ext + 
                                        "\\\\\" already exists$@"), 
            uploadFile(npath, fname, in, stoken));
       	
       	// Read uploaded file info
       	testWebResponse("Invalid file_info for demo file " + fname, 
            new WebResponse("@\\{\"file_info\":\\{\"size\":" + fbody.length + 
                ",\"read\":true,\"write\":true,\"last_modified\":\\d*\\}\\}@"), 
                                  readGet(npath + "&info=file_info", stoken));
       	
       	// Read uploaded file body
       	org.junit.Assert.assertArrayEquals("File " + fname + 
       	        " after download doesn't match ", 
       	                                    fbody, downloadFile(npath, stoken));
       	
       	// Delete file
       	testWebResponse(HTTP_RESP_OK, readDelete(npath, "", stoken));
     	}
     	
      testWebResponse("Temp project " + dsname + " is not empty", 
          HTTP_RESP_OK, readGet(fpath + dsname + ".*", stoken));
      
     	testWebResponse("Fail deleting '" + dsname + "'", 
    			 HTTP_RESP_OK, readDelete(_proj_path + "?name=" + 
    			                                       dsname, "", stoken));
   	
    }
    
   	logout(stoken, "Ex File Create");
  }
  
  private String getFirstExt(String rname) {
    String[] extl = FUTILS.getExtList(rname);
    if (extl == null)
      return null;
    
    return extl[0];
  }

  @Override
  public byte[] downloadFile(String url, String stoken) {
    byte[] res;
    InputStream in = null;
    HttpURLConnection conn = null;
    
    try {
      conn = (HttpURLConnection) (new URL(url)).openConnection();
      conn.setRequestMethod("GET");

      
      if (stoken != null)
        conn.setRequestProperty("Cookie", 
            Constants.SECURE_TOKEN_NAME + "=" + stoken);

      // Initiate connection
      conn.connect();
      

      try {
        in = conn.getInputStream();
      } catch (IOException e) {
        return e.getMessage().getBytes();
      }

      // Read response body
      try {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GenericUtils.copy(in, out);
        res = out.toByteArray();
        out.close();
      } catch (IOException e) {
        return e.getMessage().getBytes();
      }
    } catch (IOException e) {
      return null;
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          // Do nothing
        }
      }

      if (conn != null)
        conn.disconnect();
    }

    return res;
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
