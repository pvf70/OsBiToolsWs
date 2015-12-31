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

import java.io.File;
import java.util.HashMap;

import com.osbitools.ws.shared.WsSrvException;

/**
 * Interfase for all External File processing
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public interface IExFileInfo {
  
  /**
   * Get file into in json format after ex_file saved
   * 
   * @param f File handle
   * @param params Extra parameters
   * @return File info in JSON format
   * @throws WsSrvException
   */
  public String getSaveInfo(File f, String name,  
                        HashMap<String, String> params, 
                                  boolean minified) throws WsSrvException;
}
