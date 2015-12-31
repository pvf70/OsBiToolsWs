/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-07-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.utils;

import java.util.HashMap;

import com.osbitools.ws.shared.ErrorList;
import com.osbitools.ws.shared.prj.PrjMgrConstants;

/**
 * Test class with IEntityUtils final implemenation
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class TestEntityUtils extends BasicTextEntityUtils {

  // Base Extension
  public static String BASE_EXT = "txt";
  
  public static final HashMap<String, String[]> EXT_LIST = 
                              new HashMap<String, String[]>();
  static {
    EXT_LIST.put("dat", new String[] {"num", "int", "txt"});
  }
  
  private static TestExFileInfo _exf = new TestExFileInfo();
  
  public TestEntityUtils() {
    super();
    
    ErrorList.addError(351, "Error processing uploading request");
    ErrorList.addError(352, "Error reading file");
  }
  
  @Override
  public String getExt() {
    return BASE_EXT;
  }

  @Override
  public HashMap<String, String[]> getSubDirExtList() {
    return EXT_LIST;
  }
  
  @Override
  public String getPrjRootDirName() {
    return PrjMgrConstants.PRJ_DIR;
  }

  @Override
  public IExFileInfo getExFileInfoProc(String ext) {
    return _exf;
  }
}
