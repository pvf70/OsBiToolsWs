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
import com.osbitools.ws.shared.web.WebResponse;

/**
 * Test comp_list configuration
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class BasicConfigWebIT extends BasicPrjMgrWebTestIT {

  // Expected result from pen test
  private static final HashMap<String, WebResponse> TEST_RES = 
                                new HashMap<String, WebResponse>();
  
  static {
    TEST_RES.put("get", HTTP_RESP_NON_AUTH_ALL);
    TEST_RES.put("put", HTTP_RESP_NOT_ALLOWED);
    TEST_RES.put("post", HTTP_RESP_NOT_ALLOWED);
    TEST_RES.put("delete", HTTP_RESP_NOT_ALLOWED);
  }
  
  @Test
  public void testComponentListConfig() throws Exception {
    String stoken = login();
    
    testWebResponse("Invalid comp_list configuration",  
            new WebResponse(FUTILS.getCompList()), 
                                 readGet(CFG_PATH + "?lst=comp_list", stoken));
    
    logout(stoken, "Component List Config Test");
  }

  @Override
  public HashMap<String, WebResponse> getNonAuthTestExpectedSet() {
    return TEST_RES;
  }

  @Override
  public String getWepAppPath() {
    return CONFIG_APP_NAME;
  }
  
  @Override
  public String getPenTestParamName() {
    return null;
  }
}
