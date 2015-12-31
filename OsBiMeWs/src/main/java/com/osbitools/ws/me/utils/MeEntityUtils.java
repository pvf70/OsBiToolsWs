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

package com.osbitools.ws.me.utils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
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

import com.osbitools.ws.me.utils.ex_file.CsvFileInfo;
import com.osbitools.ws.me.utils.ex_file.ExFileInfo;
import com.osbitools.ws.me.utils.ex_file.SqlFileInfo;
import com.osbitools.ws.shared.DsConstants;
import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;
import com.osbitools.ws.shared.prj.utils.BasicXmlEntityUtils;
import com.osbitools.ws.shared.prj.utils.IExFileInfo;

/**
 * Implement IEntityUtils interface for Map Editor
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class MeEntityUtils extends BasicXmlEntityUtils<DataSetDescr> {

  public static final String BASE_EXT = "xml";

  public static final HashMap<String, String[]> EXT_LIST = 
                        new HashMap<String, String[]>();
  
  static {
    EXT_LIST.put("csv", new String[] {"csv"});
  }

  public static final HashMap<String, ExFileInfo> SUP_EX_FILE_EXT = 
                                      new HashMap<String, ExFileInfo>();
  static {
    SUP_EX_FILE_EXT.put("csv", new CsvFileInfo());
    SUP_EX_FILE_EXT.put("sql", new SqlFileInfo());
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
  public IExFileInfo getExFileInfoProc(String ext) {
    return SUP_EX_FILE_EXT.get(ext);
  }
  
  @Override
  public boolean hasInfoReq(String info) {
    return "columns".equals(info);
  }

  @Override
  public String execInfoReq(String info, String base, String name,
      HashSet<String> extl, String subDir, HashMap<String, String> params,
                                  boolean minified) throws WsSrvException {
    String ext = getFileExt(name);
    
    if ("columns".equals(info)) {
      ExFileInfo exf = SUP_EX_FILE_EXT.get(ext);
      if (exf == null)
        //-- 301
        throw new WsSrvException(301, "Processing module for extension " + 
                                                      ext + " is not found");
      
      File f =  checkFile(base, name, extl, subDir, true);
      
      return exf.getColumns(f, name, params, minified);
    } else {
      //-- 302
      throw new WsSrvException(302, "Info request \'" + 
                                              info + "\' is not supported.");
    }
  }

  @Override
  public String getPrjRootDirName() {
    return DsConstants.DS_DIR;
  }

  @Override
  public String getBindPkgName() {
    return DsConstants.BIND_PKG_DS_MAP;
  }

}
