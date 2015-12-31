/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-05-04
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared;

/**
 * Interface for objects to handle test resource configuration
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public interface ITestResourceConfig {

  public String getTopResDir();
  
  public  String getMainDemoFileName();

}
