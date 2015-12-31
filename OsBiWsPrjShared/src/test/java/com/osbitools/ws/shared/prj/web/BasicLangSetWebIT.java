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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.JarUtils;
import com.osbitools.ws.shared.LsConstants;
import com.osbitools.ws.shared.LsTestConstants;
import com.osbitools.ws.shared.TestConstants;
import com.osbitools.ws.shared.Utils;
import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.prj.utils.GitUtils;
import com.osbitools.ws.shared.prj.utils.LangSetFileTest;
import com.osbitools.ws.shared.web.WebResponse;

/**
 * Test ll_set web service using web access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class BasicLangSetWebIT extends BasicPrjMgrWebTestIT {

  private static final String _ll_set_app_name = "/ll_set";
  private static String _ll_set_app = BASE_URL + _ll_set_app_name;

  private static final String _ll_set_path = 
              TestConstants.JETTY_SRV_URL + _ll_set_app;
  
  private static final String _ll_set_mname_path = _ll_set_path + "?name=test";
  
  // Git repository for test project
  private static Git _git;
  
  // Expected result from pen test
  private static final HashMap<String, WebResponse> TEST_RES = 
                                new HashMap<String, WebResponse>();
  
  static {
    TEST_RES.put("get", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("put", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("post", HTTP_RESP_NOT_ALLOWED);
    TEST_RES.put("delete", HTTP_RESP_NOT_ALLOWED);
  }
  
  @Before
  public void clearLangSetFile() {
    File f = new File(FUTILS.getProjWorkDir() + 
        File.separator + "test" + File.separator + LsConstants.LANG_SET_FILE);
    
    if (f.exists())
      assertTrue("Unable delete ll_set from test project", f.delete());
  }
  
  @Test
  public void testBadReadWrite() throws Exception {
    String stoken = login();
    
    // Read lang_set from invalid project
    
    testWebResponse(new WebResponse("@\\{\"request_id\":,\"error\":\\{\"id\":261," +
        "\"msg\":\"Error saving custom language labels\"," +
        "\"info\":\"Directory .*/xxx doesn't exists\"," +
        "\"details\":\\[\\]\\}\\}@"), 
                  readGet(_ll_set_path + "?name=xxx", stoken));
    
    // Try upload invalid file
    testWebResponse(new WebResponse("@\\{\"request_id\":,\"error\":\\{\"id\":225," +
        "\"msg\":\"Entity invalid or corrupted\"," +
        "\"info\":\"Error parsing xml\"," +
        "\"details\":\\[\"Exception Description: An error occurred unmarshalling" +
        " the documentInternal Exception: javax.xml.stream.XMLStream" +
        "Exception: ParseError at \\[row,col\\]:\\[1,1\\]" +
              "Message: Content is not allowed in prolog.\"\\]\\}\\}@"),
          readPut(_ll_set_mname_path, "bad", stoken));
  }
  
  @Test
  public void testGoodReadWrite() throws Exception {
    // Open git
    _git = Git.open(new File(FUTILS.getProjWorkDir()  + 
                                  File.separator + ".git"));
    
    String stoken = login();
    
    String fname1 = "test/" + LsConstants.LANG_SET_FILE;
    
    testWebResponse(new WebResponse("{}"), readGet(_ll_set_mname_path, stoken));
    
    // Empty lang set and commit it to see the changes
    GenericUtils.saveFile(new File(FUTILS.getProjWorkDir() + 
      File.separator + "test" + File.separator + 
                                    LsConstants.LANG_SET_FILE), "");
    
    GitUtils.commitFile(_git, fname1, "Reseting file", "test");
    
    testLangSetFile(fname1, LsTestConstants.LANG_SET_RES_PATH, 
        "First Demo Load at " + (new Date()).getTime(), 
                          LangSetFileTest.LL_SET_JSON, stoken);
    
    
    // Upload ll_set_test and check
    testLangSetFile(fname1, LsTestConstants.LANG_SET_RES_PATH + "_test", 
        "Second Demo Load at " + (new Date()).getTime(),
                        LangSetFileTest.LL_SET_TEST_JSON, stoken);
  }
  
  private void testLangSetFile(String fname, String path, String comment, 
          String expected, String stoken) throws WsSrvException, IOException {
    // Read demo file and save it together with comments
    String text = JarUtils.readJarFileAsText(path);
    
    assertFalse("demo file " + path + " is empty", Utils.isEmpty(text));
    
    testWebResponse("File doesn't match after upload", 
        new WebResponse(expected), 
            readPut(_ll_set_mname_path + "&comment=" + 
                      comment.replaceAll(" ", "%20"), text, stoken));
    
    // Read commited log for file and compare last entry with uploaded file
    ArrayList<RevCommit> commits = GitUtils.getLogByFileName(_git, fname);
    
    assertTrue("No commits", commits.size() > 0);
    
    RevCommit commit = commits.get(0);
    assertEquals("Commit message doesn't match", comment, 
                                              commit.getFullMessage());
  }
  
  @Override
  public HashMap<String, WebResponse> getNonAuthTestExpectedSet() {
    return TEST_RES;
  }

  @Override
  public String getWepAppPath() {
    return _ll_set_app_name;
  }
  
}
