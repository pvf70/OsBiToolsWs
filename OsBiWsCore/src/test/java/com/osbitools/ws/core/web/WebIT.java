/*
 * Copyright 2014-2016 IvaLab Inc. and other contributors
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-07
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.core.web;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.web.*;
import com.osbitools.ws.core.bindings.BindingTest;
import com.osbitools.ws.core.daemons.DsDaemonTest;
import com.osbitools.ws.core.daemons.LangLabelsDaemonTest;
import com.osbitools.ws.core.shared.LocalTestConstants;

public class WebIT extends BasicWebIT {

	private static String _ds_app = BASE_URL + "/ds";
	private static String _mi_app = BASE_URL + "/map_info";
	private static String _ds_path = TestConstants.JETTY_SRV_URL + _ds_app;
	
  private static String _dsname_path = 
      TestConstants.JETTY_SRV_URL + _ds_app + "?map=";
  
  private static String _mi_path = 
      TestConstants.JETTY_SRV_URL + _mi_app + "?map=";
  
	private final static HashMap<String, String> rparams =
	                              new HashMap<String, String>();
	
	// List of tested services
	private final static String[] APP_URL_LIST = new String[] {
	    TestConstants.JETTY_SRV_URL + _ds_app, 
	    TestConstants.JETTY_SRV_URL + _mi_app
	};
	
	static {
	  rparams.put("sql_filter_complex", "conn=hsql&date_from=03/08/2003&" +
	  		                  "date_to=03/31/2010&num_from=4&num_to=6&f=3&t=5");
	  rparams.put("csv_filter_params", "date_from=01/01/2007&" +
                          "date_to=12/31/2007&num_from=4&num_to=6");
	  rparams.put("sql_select", "conn=hsql");
	  rparams.put("sql_select_cond", "conn=hsql&f=3&t=5");
	}
	
	private static final String EMPTY_MAP_INFO = 
	                        "{\"columns\":[],\"req_params\":[]}";
	
	private final static String[][] STATIC_TEST_DEMO = new String[][] {
    new String[] {      
      "csv_filter1",
      
      DsDaemonTest.CSV_FILTER1, 
      
      EMPTY_MAP_INFO
    },
      
    new String[] {      
      "csv_filter_complex",
      
      DsDaemonTest.CSV_FILTER_COMPLEX, 
      
      "{\"columns\":[" +
        "{\"name\":\"CDATE\",\"jtype\":\"java.util.Date\",\"on_error\":\"\"}," +
        "{\"name\":\"CNUM\",\"jtype\":\"java.lang.Double\",\"on_error\":\"\"}]" +
        ",\"req_params\":[]}"
    },
    
    new String[] {      
      "csv_filter_complex_no_header",
      
      DsDaemonTest.CSV_FILTER_COMPLEX_NO_HEADER, 
      
      "{\"columns\":[{\"name\":\"COL4\",\"jtype\":\"java.util.Date\",\"on_error\":\"\"}," +
       "{\"name\":\"COL3\",\"jtype\":\"java.lang.Double\",\"on_error\":\"\"}]" +
        ",\"req_params\":[]}"
    },
      
    new String[] {
      "csv_filter_params",
      
      DsDaemonTest.CSV_FILTER_COMPLEX, 
      
      "{\"columns\":[" +
        "{\"name\":\"CDATE\",\"jtype\":\"java.util.Date\",\"on_error\":\"\"}," +
        "{\"name\":\"CNUM\",\"jtype\":\"java.lang.Double\",\"on_error\":\"\"}]" +
      ",\"req_params\":[" +
        "{\"name\":\"date_from\",\"jtype\":\"java.util.Date\"}," +
        "{\"name\":\"date_to\",\"jtype\":\"java.util.Date\"}," +
        "{\"name\":\"num_from\",\"jtype\":\"java.lang.Double\"}," +
        "{\"name\":\"num_to\",\"jtype\":\"java.lang.Double\"}]}"
    },
    
    new String[] {
      "csv_sort1",
      
      DsDaemonTest.CSV_SORT1, 
      
      EMPTY_MAP_INFO
    },
    
    new String[] {
      "csv_sort2",
      
      DsDaemonTest.CSV_SORT2, 
      
      EMPTY_MAP_INFO
    },
      
    new String[] {
      "csv_sort3",
      
      DsDaemonTest.CSV_SORT3, 
      
      EMPTY_MAP_INFO
    },
        
    new String[] {
      "csv_str1",
      
      DsDaemonTest.CSV_STR1, 
      
      EMPTY_MAP_INFO
    },
      
    new String[] {
      "group_mixed",
      
      DsDaemonTest.STR_STATIC_TEST_GROUP_MIXED, 
      
      "{\"columns\":[{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}," +
       "{\"name\":\"CNUM\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}]" +
        ",\"req_params\":[]}"
    },
    
    new String[] {      
      "sql_filter_complex",
      
      DsDaemonTest.SQL_FILTER_COMPLEX, 
      
      "{\"columns\":[],\"req_params\":[" +
        "{\"name\":\"date_from\",\"jtype\":\"java.util.Date\"}," +
        "{\"name\":\"date_to\",\"jtype\":\"java.util.Date\"}," +
        "{\"name\":\"num_from\",\"jtype\":\"java.lang.Double\"}," +
        "{\"name\":\"num_to\",\"jtype\":\"java.lang.Double\"}," +
        "{\"name\":\"f\",\"jtype\":\"java.lang.Integer\"}," +
        "{\"name\":\"t\",\"jtype\":\"java.lang.Integer\"}]}"
    },
    
    new String[] {
      "sql_select",
        
      DsDaemonTest.SQL_SELECT, 
      
      EMPTY_MAP_INFO
    },
    
    new String[] {
      "sql_select_cond",
        
      DsDaemonTest.SQL_SELECT_COND, 
      
      "{\"columns\":[],\"req_params\":[" +
          "{\"name\":\"f\",\"jtype\":\"java.lang.Integer\"}," +
          "{\"name\":\"t\",\"jtype\":\"java.lang.Integer\"}]}"
    },
    
    new String[] {
      "static_test_calc_date1",
      
      DsDaemonTest.STR_STATIC_TEST_CALC_DATE1, 
      "{\"columns\":[" +
            "{\"name\":\"COL1\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"\"}," +
            "{\"name\":\"COL2\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"\"}," +
            "{\"name\":\"COL3\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"\"}," +
            "{\"name\":\"COL4\",\"jtype\":\"java.lang.Long\",\"on_error\":\"\"}," +
            "{\"name\":\"COL5\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}" +
          "]" +
          ",\"req_params\":[]}"
    },
    
    new String[] {
      "static_test_calc_date2",
      
      DsDaemonTest.STR_STATIC_TEST_CALC_DATE2, 
      
      "{\"columns\":[" +
          "{\"name\":\"COL1\",\"jtype\":\"java.util.Date\",\"on_error\":\"\"}," +
          "{\"name\":\"COL2\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}" +
        "]" +
        ",\"req_params\":[]}"
    },
    
		new String[] {
		  "static_test_lmap",
		  
		  DsDaemonTest.STR_STATIC_TEST_LMAP_SINGLE, 
		  
		  "{\"columns\":[{\"name\":\"COL1\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}]" +
        ",\"req_params\":[]}"
		},
    
    new String[] {
      "static_test_lmap_auto_inc",
      
      DsDaemonTest.STR_STATIC_TEST_LMAP_AUTO_INC, 
      
      "{\"columns\":[" +
          "{\"name\":\"COL1\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}," +
          "{\"name\":\"COL2\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"\"}," +
          "{\"name\":\"COL3\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"\"}" +
        "],\"req_params\":[]}"
    },
    
    new String[] {
      "static_test_lmap_calc",
      
      DsDaemonTest.STR_STATIC_TEST_LMAP_CALC, 
      
      "{\"columns\":[" +
          "{\"name\":\"COL1\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}," +
          "{\"name\":\"COL2\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"\"}," +
          "{\"name\":\"COL3\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"\"}," +
          "{\"name\":\"COL4\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"\"}," +
          "{\"name\":\"COL5\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"\"}," +
          "{\"name\":\"COL6\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"-100\"}," +
          "{\"name\":\"COL7\",\"jtype\":\"java.lang.Double\",\"on_error\":\"\"}," +
          "{\"name\":\"COL8\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}" +
      "]" +
        ",\"req_params\":[]}"
    },
    
    new String[] { 
      "static_test_lmap_grp_complex1",
      
      DsDaemonTest.STR_STATIC_TEST_LMAP_GRP_COMPLEX_1, 
      
      EMPTY_MAP_INFO
    },
    
    new String[] {
      "static_test_lmap_grp_complex2",
      
      DsDaemonTest.STR_STATIC_TEST_LMAP_GRP_COMPLEX_2, 
      
      "{\"columns\":[" +
          "{\"name\":\"CNT\",\"jtype\":\"java.lang.Integer\",\"on_error\":\"\"}" +
        "]" +
      ",\"req_params\":[]}"
    },
    
    new String[] {
      "static_test_lmap_grp_complex3",
      
      DsDaemonTest.STR_STATIC_TEST_LMAP_GRP_COMPLEX_3, 
      
      EMPTY_MAP_INFO
    },
    
    new String[] {
      "static_test_lmap_grp_multi",
      
      DsDaemonTest.STR_STATIC_TEST_LMAP_MULTI, 
      
      EMPTY_MAP_INFO
    },
    
		new String[] {
		  "static_test_lmap_grp_single",
		    
		  DsDaemonTest.STR_STATIC_TEST_LMAP_SINGLE, 
      
      EMPTY_MAP_INFO
    },
    
    new String[] {
		  "static_test_str1",
		  
			DsDaemonTest.STR_STATIC_TEST_STR1, 
			
      "{\"columns\":[{\"name\":\"COL1\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}," +
       "{\"name\":\"COL2\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}," +
       "{\"name\":\"COL3\",\"jtype\":\"java.lang.String\",\"on_error\":\"\"}]" +
        ",\"req_params\":[]}"
    },
    
    new String[] {
			"static_test_str2",
			
			DsDaemonTest.STR_STATIC_TEST_STR_SORTED, 
      
      EMPTY_MAP_INFO
    },
    
    new String[] {
			"static_test_str3",
			
			DsDaemonTest.STR_STATIC_TEST_STR_SORTED, 
      
			EMPTY_MAP_INFO
    }
	};
	
	private final static String[][] STATIC_TEST_BAD = new String[][] {
    new String[] {
        "csv_bad",
        
        "@\\{\"request_id\":,\"error\":\\{\"id\":116," + 
        "\"msg\":\"Fatal error processing dataset\"," +
        "\"info\":\"No additional details for this error\"," +
        "\"details\":\\[\"Unable read csv file " +
            "\\\\\".*test.csv\\\\\"\"," +
            "\".*test.csv " +
            "\\(No such file or directory\\)\"\\]\\}," +
        "\"error_ds\":" +
          "\\{\"columns\":\\[" +
          "\\{\"name\":\"COL1\",\"jtype\":\"java.lang.Long\"\\}," +
          "\\{\"name\":\"COL2\",\"jtype\":\"java.lang.String\"\\}," +
          "\\{\"name\":\"COL3\",\"jtype\":\"java.util.Date\"\\}," +
          "\\{\"name\":\"COL4\",\"jtype\":\"java.lang.Integer\"\\}," +
          "\\{\"name\":\"COL5\",\"jtype\":\"java.lang.Double\"\\}," +
          "\\{\"name\":\"COL6\",\"jtype\":\"java.lang.Float\"\\}\\]," +
        "\"data\":\\[\\[0,\"ERROR !!!\"," +
          "\"Sat Jan 01 00:00:00 EST 2000\",0,0.0,0.0\\]\\]\\}\\}@"
    },
    
		new String[] {
			"static_test_calc_bad1",
		    
			"{\"request_id\":," +
			"\"error\":{\"id\":115," +
			"\"msg\":\"Fatal error processing dataset\"," +
			"\"info\":\"No additional details for this error\"," +
			"\"details\":[\"Unable evaluate expression \\\"@{COL1}/@{COL2}\\\"; " +
			  "Reason: \\\"/ by zero\\\"\",\"COL1: 1\",\"COL2: 0\"]}," +
			"\"error_ds\":{" +
			  "\"columns\":[" +
			    "{\"name\":\"COL1\",\"jtype\":\"java.lang.Integer\"}," +
			    "{\"name\":\"COL2\",\"jtype\":\"java.lang.Integer\"}," +
			    "{\"name\":\"COL3\",\"jtype\":\"java.lang.Integer\"}]," +
			  "\"data\":[[99,0,-1]]}}"
		},
		
    new String[] {
			"static_test_calc_bad2",
			
		  "{\"request_id\":," +
			"\"error\":{\"id\":107," +
			"\"msg\":\"Fatal error processing dataset\"," +
			"\"info\":\"No additional details for this error\"," +
			"\"details\":[\"Unknown Java Type: xxx\",\"xxx\"]}}"
    },
    
    new String[] {      
			"static_test_calc_bad3",
			
      "{\"request_id\":," +
      "\"error\":{\"id\":114," +
      "\"msg\":\"Fatal error processing dataset\"," +
      "\"info\":\"No additional details for this error\"," +
      "\"details\":[\"Unable compile expression \\\"@{COL1}*@{COL1} " +
      "+ @{COL2}*@{COL2} \\\"\"," +
      "\"Line 1, Column 9: Binary numeric promotion not possible on " +
      "types \\\"java.lang.String\\\" and \\\"java.lang.String\\\"\"]}}"
    },
    
	};
	
  // Lock object for static synchronization
  private static final Object lock = new Object();
  
  // ll_set loaded flat
  private static boolean fls = false;
  
	@BeforeClass
	public static void testReady() throws Exception {
	  boolean b = Boolean.parseBoolean(
	      readGetEx(TestConstants.JETTY_SRV_URL + BASE_URL + "/rdy"));
	      
	  long dts = System.currentTimeMillis();
    while (!b && (System.currentTimeMillis() - dts) < TestConstants.WAIT_TIME) {
      b = Boolean.parseBoolean(
          readGetEx(TestConstants.JETTY_SRV_URL + "/rdy"));
      
      if (b)
        break;
        
      Thread.sleep(TestConstants.RESULT_CHECK_TIME);
    }
    
    assertTrue("WebApp is not ready " + "after " + 
        TestConstants.WAIT_TIME + " msec of waiting", b);
    
    // Singleton to use under MultiWebIt
    synchronized (lock) {
      if (!fls) {
        // Copy demo ll_set to test project
        JarTestResourceUtils.copyDemoFileToFile(
            LsConstants.LANG_SET_FILE + "_combo", 
            TestConstants.WORK_CONFIG_DIR +
            File.separator + DsConstants.DS_DIR + File.separator +
                LocalTestConstants.TEST_PRJ_NAME + File.separator + 
                    LsConstants.LANG_SET_FILE, LangLabelsDaemonTest.LS_CFG);
        
        fls = true;
        
        // Activate Bindint
        Class.forName(BindingTest.class.getName());
      }
    }
	}
	
	@Test
  public void testMapNoAuth() throws Exception {
    // Test no authentication with parameters
	  for (String url: APP_URL_LIST) {
	    String murl = url  + "?map=";
	    
	    testWebResponse("Test no map No Auth", HTTP_RESP_NON_AUTH_ALL,
                                              readGet(murl  + "*"));
	  
	    testWebResponse("Test error map No Auth", HTTP_RESP_NON_AUTH_ALL,
                                              readGet(murl + "xxx"));
	  }
	}
	
	@Test
	public void testNonAuthSrv() throws Exception {
	  for (String url: APP_URL_LIST)
	    testBadGet(url);
	}
	
	@Test
	public void testMapList() throws Exception {
	  // Login
    String stoken = login();
    
    String list = "";
    
    // Get list of test bad maps
    for (int i = 0; i < STATIC_TEST_BAD.length; i++)
      list += "," + LocalTestConstants.BAD_PRJ_PREFIX + 
                                            STATIC_TEST_BAD[i][0];
    
    // Get list of test maps
    /*
    for (int i = 0; i < STATIC_TEST_SET.length; i++)
      list += "," + STATIC_TEST_SET[i][0]; 
    */
    
    // Get list of test demo maps
    for (int i = 0; i < STATIC_TEST_DEMO.length; i++)
      list += "," + LocalTestConstants.TEST_PRJ_PREFIX + STATIC_TEST_DEMO[i][0]; 

    
	  testWebResponse("List of loaded maps doesn't match", 
	    new WebResponse(list.substring(1)), readGet(_dsname_path + "*", stoken));
	  
	  // Logout
    logout(stoken, "Logout after map list");
	}
	
	@Test
	public void testBadMapParam() throws Exception {
		// Login
		String stoken = login();
		
		// Test no map parameter
		testWebResponse("Test no map parameter", 
		    new WebResponse(400, 
		        HTTP_RESP_MISSING_PARAMS.replace("xxx",  "map")), 
		                                  readGet(_ds_path, stoken));
		
		testWebResponse("Test no map parameter", 
        new WebResponse(400, 
            HTTP_RESP_MISSING_PARAMS.replace("xxx",  "map")), 
                                      readGet(_mi_path, stoken));
		
		// Test empty map parameter
		testWebResponse("Test no map parameter", 
		    new WebResponse(400, 
            HTTP_RESP_MISSING_PARAMS.replace("xxx",  "map")), 
                          readGet(_dsname_path, stoken));
		testWebResponse("Test no map parameter", 
        new WebResponse(400, 
            HTTP_RESP_MISSING_PARAMS.replace("xxx",  "map")), 
                          readGet(_mi_path, stoken));
		
		// Test invalid map parameter
		testWebResponse("Test invalid map parameter", 
        new WebResponse(404, "ERROR #100 - Data Map is not found. " +
        		"INFO: Map not found for 'xxx'"), 
        		  readGet(_dsname_path + "xxx", stoken));
		
		testWebResponse("Test invalid map parameter", 
        new WebResponse(404, "ERROR #100 - Data Map is not found. " +
            "INFO: Map not found for 'xxx'"), 
              readGet(_mi_path + "xxx", stoken));
		
		// Logout
		logout(stoken, "Logout after bad ds");
	}
	
	@Test
	public void testStaticGood() throws Exception {
		// Login
		String stoken = login();
		
		// Demo set
		for (int i = 0; i < STATIC_TEST_DEMO.length; i++) {
		  String map = STATIC_TEST_DEMO[i][0];
		  String fname = LocalTestConstants.TEST_PRJ_PREFIX + map + ".xml";
		  String url = _dsname_path + fname;
		  
		  // Check if extra params required
		  String params = rparams.get(map);
		  if (params != null)
		    url += "&" + params;
			testWebResponse(STATIC_TEST_DEMO[i][0] + " test",
			    new WebResponse(STATIC_TEST_DEMO[i][1]), readGet(url, stoken));
			
			testWebResponse(STATIC_TEST_DEMO[i][0] + " columns test", 
			    new WebResponse(STATIC_TEST_DEMO[i][2]), 
			                  readGet(_mi_path + fname, stoken));
		}
		
		// Logout
		logout(stoken, "Logout after good ds");
	}
	
	@Test
	public void testStaticBad() throws Exception {
		// Login
		String stoken = login();
				
		for (int i = 0; i < STATIC_TEST_BAD.length; i++) {
		  testWebResponse(STATIC_TEST_BAD[i][0] + " test", 
	        new WebResponse(STATIC_TEST_BAD[i][1]), 
	          readGet(_dsname_path + LocalTestConstants.BAD_PRJ_PREFIX + 
	                                    STATIC_TEST_BAD[i][0] + ".xml", stoken));
		}
		
		logout(stoken, "Logout after ds");
	}
}
