/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-08-10
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared;

/**
 * LangLables Set File Resource Configuration
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */

public class LsFileTestResConfig implements ITestResourceConfig {

  @Override
  public String getTopResDir() {
    return LsTestConstants.LANG_SET_RES_PACKAGE;
  }

  @Override
  public String getMainDemoFileName() {
    return "ll_set";
  }
}
