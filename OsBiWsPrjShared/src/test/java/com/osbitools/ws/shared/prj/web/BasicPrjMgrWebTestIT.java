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

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.utils.GenericPrjMgrTest;
import com.osbitools.ws.shared.prj.utils.IDemoFileUtils;
import com.osbitools.ws.shared.web.BasicWebUtils;
import com.osbitools.ws.shared.web.WebResponse;

/**
 * Shared functions for Multi-thread test
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public abstract class BasicPrjMgrWebTestIT extends BasicWebUtils {
	
	// Random generator
	SecureRandom random = new SecureRandom();
	
	// WebApp path
	protected final String _proj_app_name = "/project";
  
	protected final String _proj_app = BASE_URL + _proj_app_name;

	protected final String _proj_path = TestConstants.JETTY_SRV_URL + _proj_app;

	protected final String _proj_mname_path = _proj_path + "?name=";
	
	protected final String _entity_app_name = "/entity";
	
	protected final String _entity_app = BASE_URL + _entity_app_name;

	protected final String _entity_path = 
	                                TestConstants.JETTY_SRV_URL + _entity_app;
  
	protected final String _entity_mname_path = _entity_path + "?name=";
	
	protected final String _ex_file_app_name = "/ex_file";
  
	protected final String _ex_file_app = BASE_URL + _ex_file_app_name;

	protected final String _ex_file_path = 
	                    TestConstants.JETTY_SRV_URL + _ex_file_app;
  
	private String _dprefix;
	private Pattern _p;
	
	// File Utils Test handler
  public IDemoFileUtils FUTILS;

  // Entity Utils handler
  
	public BasicPrjMgrWebTestIT() {
	  try {
      FUTILS = GenericPrjMgrTest.getDemoFileUtilsHandler();
    } catch (Exception e) {
      fail(e.getMessage());
    }
	}
	
	@Before
	public void testEmptyDirStart() throws Exception {
	  
		// Generate temp dir prefix
		_dprefix = new BigInteger(24, random).toString(16);
		_p = Pattern.compile(".*" + _dprefix + ".*");
		
		assertEquals("START: Found project with prefix '" + 
														_dprefix + "'", "", getProjDirList());
  }
	
	@After
	public void testEmptyDirEnd() throws Exception {
		assertEquals("END: Found project with prefix '" + _dprefix + "'", 
				"", getProjDirList());
	}
	  
  String getProjDirList() throws Exception {
    String stoken = login();
    
    WebResponse resp = readGet(_proj_path + "?name=*", stoken);
    assertEquals("Project Get Result Code", 200, resp.getCode());
    
    String dstr = resp.getMsg();
    assertNotNull("Project Get Result Non-Null Message", dstr);
    
    String res = "";
    String[] dlist = dstr.split(",");
    for (String ds: dlist)
      if (_p.matcher(ds).matches())
        res += "," + ds;
    
    logout(stoken, "After Dir Check");
    
    return res.equals("") ? "" : res.substring(1);
  }
  
  public String getDirPrefix() {
    return _dprefix;
  }
}
