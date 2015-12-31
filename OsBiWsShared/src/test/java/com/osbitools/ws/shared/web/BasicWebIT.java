/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared.web;


import java.util.HashMap;

import org.junit.Test;

import com.osbitools.ws.shared.TestConstants;

/**
 * Basic Single Thread Web Test
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class BasicWebIT extends BasicWebUtils {

	public static final String HTTP_RESP_MISSING_PARAMS = 
			"ERROR #98 - Missing mandatory parameter(s). " +
													"INFO: Missing [xxx] parameter";

	private static final String[][] GONFIG_TEST = new String[][] {
	    new String[] { "param1", "param1,param2", "param1,param2,paramxx" },
	    new String[] { "{\"param1\":\"one\"}",
	        "{\"param1\":\"one\",\"param2\":\"two\"}",
	        "{\"param1\":\"one\",\"param2\":\"two\",\"paramxx\":\"\"}", } };

  /*
	@Test
	public void testNotAllowed() {
		testWebResponse("Version NotAllowed Post", 
				new WebResponse(405, "HTTP method POST is not supported by this URL"),
        		readPost(VERSION_PATH, "", null));
		
		testWebResponse("Version NotAllowed Put", 
				new WebResponse(405, "HTTP method PUT is not supported by this URL"),
        		readPut(VERSION_PATH, "", null));
		
		testWebResponse("Version NotAllowed Delete", 
				new WebResponse(405, "Http method DELETE is not supported by this URL"),
        		readDelete(VERSION_PATH, "", null));
		
		testWebResponse("Config NotAllowed Post", 
        BasicWebIT.HTTP_RESP_NOT_ALLOWED, 
        		readPost(CFG_PATH, "", null));
		
		testWebResponse("Config NotAllowed Put", 
        BasicWebIT.HTTP_RESP_NOT_ALLOWED, 
        		readPut(CFG_PATH, "", null));
		
		testWebResponse("Config NotAllowed Delete", 
        BasicWebIT.HTTP_RESP_NOT_ALLOWED, 
        		readDelete(CFG_PATH, "", null));
		
		testWebResponse("Authentication NotAllowed Put", 
        BasicWebIT.HTTP_RESP_NOT_ALLOWED, 
        		readPut(AUTH_PATH, "", null));
		
		testWebResponse("Authentication NotAllowed Delete", 
        BasicWebIT.HTTP_RESP_NOT_ALLOWED, 
        		readDelete(AUTH_PATH, "", null));
	}
	*/
  
  /*
	@Test
	public void testNotAuth() throws Exception {
		// assertEquals(401, readResponseCode(AUTH_PATH));
		testWebResponse("Authentication check with no token", HTTP_RESP_NON_AUTH,
		    readGet(AUTH_PATH));
		testWebResponse("Authentication check with empty token",
		    HTTP_RESP_NON_AUTH, readGet(AUTH_PATH, ""));
		testWebResponse("Authentication check with invalid token",
		    HTTP_RESP_INVALID_TOKEN, readGet(AUTH_PATH, "xxx"));
	}
  */
  
	@Test
	public void testBadAuth() throws Exception {
		testWebResponse("Empty Auth", new WebResponse(401,
		    "ERROR #1 - Not Authenticated. INFO: UserName is empty"),
		    readPost(AUTH_PATH, ""));

		testWebResponse("Empty Auth", new WebResponse(401,
		    "ERROR #1 - Not Authenticated. INFO: UserName is empty"),
		    readPost(AUTH_PATH, "usr="));
		
		testWebResponse("Empty Auth", new WebResponse(401,
		    "ERROR #1 - Not Authenticated. INFO: Password is empty"),
		    readPost(AUTH_PATH, "usr=xx"));

		testWebResponse("Empty Auth", new WebResponse(401,
		    "ERROR #1 - Not Authenticated. INFO: Password is empty"),
		    readPost(AUTH_PATH, "usr=" + TestConstants.TEST_USER));

		testWebResponse("Empty Auth", new WebResponse(
		  "{\"request_id\":,\"error\":{\"id\":12,\"msg\":\"Authentication " +
		    "Failed\",\"info\":\"Invalid username and/or password\"," +
		      "\"details\":[\"Invalid Password\"]}}"),
		  readPost(AUTH_PATH, "usr=" + TestConstants.TEST_USER + "&pwd=xxx"));

		testWebResponse("Empty Auth", new WebResponse(401,
		    "ERROR #2 - Authentication Failed. INFO: Invalid username " +
		                    "and/or password. DETAILS: User Not Found;"),
		  /*
		  "{\"request_id\":,\"error\":{\"id\":2,\"msg\":\"Authentication " +
		    "Failed\",\"info\":\"Invalid username and/or password\"," +
		      "\"details\":[\"User Not Found\"]}}"),
		  */
		  readPost(AUTH_PATH, "usr=xx&pwd=xx"));
	}

	@Test
	public void testSimpleLogin() throws Exception {
		String stoken = login();
		logout(stoken, "Simple Login");
	}

	@Test
	public void testCheck() throws Exception {
		String stoken = login();

		// Test access with cookie set
		testWebResponse("Authentication check after login", HTTP_RESP_OK,
		    readGet(AUTH_PATH, stoken));

    // Test access with header set
    testWebResponse("Authentication check after login", HTTP_RESP_OK,
                                readGet(AUTH_PATH, "SToken", stoken));

    logout(stoken, "Auth Check");
		testWebResponse("Authentication check after logout: " + stoken,
		    new WebResponse(403, HTTP_RESP_SESSION_TIMEOUT.replace("xxx", stoken)),
		                readGet(AUTH_PATH, stoken));

		testWebResponse("Logout check after logout: " + stoken, new WebResponse(
		    403, HTTP_RESP_SESSION_TIMEOUT.replace("xxx", stoken)),
		                readGet(AUTH_PATH, stoken));
	}

	@Test
	public void testConfigBad() throws Exception {
	  testBadGet(CFG_PATH);
	  /*
	  // Expected result from pen test
	  HashMap<String, WebResponse> expected = 
	                                new HashMap<String, WebResponse>();
	  
	  expected.put("get", HTTP_RESP_NON_AUTH_ALL);
	  expected.put("put", HTTP_RESP_NOT_ALLOWED);
	  expected.put("post", HTTP_RESP_NOT_ALLOWED);
	  expected.put("delete", HTTP_RESP_NOT_ALLOWED);
	  
	  testNonAuth(CFG_PATH, null, expected);
	  
		testWebResponse("Empty NonAuth Cfg", 
		    HTTP_RESP_NON_AUTH_ALL, readGet(CFG_PATH));
		*/
	}

	@Test
	public void testConfigBadEx() throws Exception {
		// Test same but login
		String stoken = login();
		testWebResponse("Empty Auth Cfg", new WebResponse(400,
		    HTTP_RESP_MISSING_PARAMS.replace("xxx", "lst")),
		    readGet(CFG_PATH, stoken));
		logout(stoken, "Empty Auth Cfg");
	}

	@Test
	public void testConfigGood() throws Exception {
		// Login
		String stoken = login();

		int len = GONFIG_TEST[0].length;
		for (int i = 0; i < len; i++) {
			String params = GONFIG_TEST[0][i];
			String url = CFG_PATH + "?lst=" + params;

			testWebResponse(GONFIG_TEST[0][i] + " parameters",
					new WebResponse(GONFIG_TEST[1][i]), readGet(url, stoken));
		}

		// Logout
		logout(stoken, "good config");
	}

  @Override
  public HashMap<String, WebResponse> getNonAuthTestExpectedSet() {
    return null;
  }

  @Override
  public String getWepAppPath() {
    return VERSION_APP_NAME;
  }
}
