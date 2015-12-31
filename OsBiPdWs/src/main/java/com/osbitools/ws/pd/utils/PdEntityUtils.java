/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 * Date: 2015-05-02
 * 
 */

package com.osbitools.ws.pd.utils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import com.osbitools.ws.pd.shared.LocalConstants;
import com.osbitools.ws.pd.utils.ex_file.IconFileInfo;
import com.osbitools.ws.shared.WpConstants;
import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.binding.wp.WebPage;
import com.osbitools.ws.shared.prj.utils.BasicXmlEntityUtils;
import com.osbitools.ws.shared.prj.utils.IExFileInfo;

public class PdEntityUtils extends BasicXmlEntityUtils<WebPage> {

  public static final String BASE_EXT = "xml";

  public static final HashMap<String, String[]> EXT_LIST = 
                                          new HashMap<String, String[]>();
                                          
  public static final HashMap<String, IExFileInfo> EXT_FPROC = 
                                       new HashMap<String, IExFileInfo>();
                                          
  static {
    EXT_LIST.put("icons", new String[] {"png", "gif", "jpeg"});
    
    EXT_FPROC.put("icons", new IconFileInfo());
  }
  
  @Override
  public String getExt() {
    return BASE_EXT;
  }

  @Override
  public String getPrjRootDirName() {
    return LocalConstants.SITES_DNAME;
  }

  @Override
  public HashMap<String, String[]> getSubDirExtList() {
    return EXT_LIST;
  }

  @Override
  public String getBindPkgName() {
    return WpConstants.BIND_PKG_WEB_PAGE;
  }

  @Override
  public IExFileInfo getExFileInfoProc(String ext) {
    return EXT_FPROC.get(ext);
  }
  
  @Override
  public boolean hasInfoReq(String info) {
    return "base64".equals(info);
  }
  
  @Override
  public String execInfoReq(String info, String base, String name,
      HashSet<String> extl, String subDir, HashMap<String, String> params,
                                  boolean minified) throws WsSrvException {
    if ("base64".equals(info)) {
      IExFileInfo exf = EXT_FPROC.get(subDir);
      if (exf == null)
        //-- 350
        throw new WsSrvException(350, "Processing module for extension " + 
                                                    subDir + " is not found");
      
      File f =  checkFile(base, name, extl, subDir, true);
      
      return exf.getSaveInfo(f, name, params, minified);
    } else {
      return "";
    }
  }
}
