/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-09
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.PrjMgrTestConstants;
import com.osbitools.ws.shared.web.BasicWebUtils;

/**
 * Generic utilities for file test
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class GenericPrjMgrTest {

  static String root;
  
  static File froot;
  
  // Working ds folder
  private static File _wds;
  
  static IDemoFileUtils FUTILS;

  public static EntityUtils EUT;
  
  @BeforeClass
  public static void initTestEnv() throws Exception {
    
    // First
    Class.forName("com.osbitools.ws.shared.prj.CustErrorList");
    
    // Second
    EUT = getEntityUtilsHandler();
    FUTILS = getDemoFileUtilsHandler();
    
    // Check that root directory exists
    root = PrjMgrTestConstants.TEMP_PRJ_DIR_SHORT;
    froot = new File(root);
    
    boolean fexist = froot.exists();
    if (!fexist && !froot.mkdir())
      fail("Unable create root directory '" + froot.getAbsolutePath() + "'");
    
    // Just in case clear all prev temp directories
    if (fexist) {
	    File[] dlist = froot.listFiles();
	    for (File d: dlist)
	      GenericUtils.delDirRecurse(d);
    }
    
    // Recreate working ds directory (if exists)
    _wds = new File(PrjMgrTestConstants.DEMO_PRJ_DIR_S);
    if (_wds.exists())
      GenericUtils.delDirRecurse(_wds);
    
    assertTrue("Fail Creating working ds directory " + 
        PrjMgrTestConstants.DEMO_PRJ_DIR_S, _wds.mkdirs());
  }
  
  public static IDemoFileUtils getDemoFileUtilsHandler() throws Exception {
    // Load ExFileUtilsTest module
    Properties exf = new Properties();
    exf.load(new FileInputStream("prj_test.properties"));
    
    // Create Test File Utils
    String cname = exf.getProperty("demo.file.utils");
    @SuppressWarnings("unchecked")
    Class<IDemoFileUtils> cdfu = (Class<IDemoFileUtils>) Class.forName(cname);
    Constructor<IDemoFileUtils> ctor = cdfu.getConstructor();
    
    return ctor.newInstance();
  }
  
  public static EntityUtils getEntityUtilsHandler() throws Exception {
    // Load ExFileUtilsTest module
    Properties exf = new Properties();
    exf.load(new FileInputStream("prj_test.properties"));
    
    // Initialize Entity Utils
    String cname = exf.getProperty("entity.utils");
    @SuppressWarnings("unchecked")
    Class<EntityUtils> ceu = (Class<EntityUtils>) Class.forName(cname);
    Constructor<EntityUtils> cteu = ceu.getConstructor();
    return cteu.newInstance();
  }
  
  @AfterClass
  public static void checkRootDirEmpty() {
    File dir = new File(root);
    String[] dlist = dir.list();
    assertNotNull(dlist);
    assertEquals("Root dir is not empty", 0, dlist.length);
    
    // Delete demo maps
    try {
      GenericUtils.delDirRecurse(_wds);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
  
  void checkWsSrvException(String id, int code, 
      																WsSrvException e, String errMsg) {
    assertEquals("Testing '" + id + "' code", code, e.getErrorCode());
    assertEquals("Testing '" + id + "' message", 
        ErrorList.getErrorById(code), e.getMessage());
    
    BasicWebUtils.testMsg("Testing '" + id + "' info", 
    													errMsg, e.getErrorInfo());
  }
  
  public static String getRoot() {
    return root;
  }
}
