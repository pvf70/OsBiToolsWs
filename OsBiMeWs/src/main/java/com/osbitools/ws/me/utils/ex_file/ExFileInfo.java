/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-12-29
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.me.utils.ex_file;

import java.io.File;
import java.util.HashMap;

import com.osbitools.ws.shared.Utils;
import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.prj.utils.IExFileInfo;

/**
 * Interfase for all MapEditor External File processing
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class ExFileInfo implements IExFileInfo {
  
  /**
   * Format column list into json format
   * 
   * @param base Base directory
   * @param name Entity name
   * @param params Extra parameters
   * @return Columns in JSON format
   * @throws WsSrvException
   */
  public String getColumns(File f, String name, 
      HashMap<String, String> params, boolean minified) throws WsSrvException {

    String res = "";
    String [][] columns = getColumnList(f, name, params);
    
    if (columns != null) {
      for (String[] column : columns) {
        res += "," + Utils.getCRT(minified) + "{" + Utils.getCRTT(minified) + 
          "\"name\":" + Utils.getSPACE(minified) + "\"" + 
          column[0].toUpperCase() + "\"," + Utils.getCRTT(minified) + 
          "\"java_type\":" + Utils.getSPACE(minified) + column[1] + 
          Utils.getCRT(minified) + "}";            
      }
    }
    
    return "[" + Utils.getCRT(minified) + (res.equals("") ? 
                   res : res.substring(1)) + Utils.getCR(minified) + "]";
  }
  
  @Override
  public String getSaveInfo(File f, String name, 
              HashMap<String, String> params, 
                              boolean minified) throws WsSrvException {
    return getColumns(f, name, params, minified);
  }
  
  /**
   * Extract columns from dataset map 
   * 
   * @param base Base directory
   * @param name Entity name
   * @param params Extra parameters
   * @return Array of columns
   * @throws WsSrvException
   */
	public abstract String[][] getColumnList(File f, String name,
	                     HashMap<String, String> params) throws WsSrvException;
	
}
