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

import java.util.HashMap;

import org.junit.Test;
import com.osbitools.ws.shared.web.*;

/**
 * Test Project Single Thread processing using web access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class BasicProjSelWebIT extends BasicPrjMgrWebTestIT {
  
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
  public void testProjectSelect() throws Exception {
    String stoken = login();
    
    testWebResponse("List of project doesn't match", 
        new WebResponse(FUTILS.getProjListSorted()), 
              readGet(_proj_mname_path + "*", stoken));
    
    /*
    testWebResponse("List of project entities doesn't match", 
        new WebResponse(FUTILS.getProjEntitiesListSorted()), 
              readGet(_proj_mname_path + "all", stoken));
    */
    
    logout(stoken, "Test Project Select");
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
