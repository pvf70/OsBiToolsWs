/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-03-15T00:30Z
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared;

/**
 * DataSet Maps Resource Configuration
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */

public class DsMapTestResConfig implements ITestResourceConfig {

  @Override
  public String getTopResDir() {
    return DsTestConstants.DS_MAPS_PACKAGE;
  }

  @Override
  public String getMainDemoFileName() {
    return "ds.xml";
  }
}
