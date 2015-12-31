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

package com.osbitools.ws.shared.prj.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.Utils;
import com.osbitools.ws.shared.WsSrvException;

public abstract class BasicTextEntityUtils extends EntityUtils {

  public BasicTextEntityUtils() {
    super();
  }
  
  @Override
  public String create(String base, String name, InputStream in,
      boolean overwrite, boolean minified) throws WsSrvException {
    File f = GenericUtils.checkFile(base, name, getExt(), overwrite);
    
    // 1. Save initial xml
    GenericUtils.saveFile(f, in);
    
    // 2. Reset input stream
    try {
      in.reset();
      return getJson(in, name, minified);
    } catch (IOException e) {
      throw new WsSrvException(351, e);
    }
  }

  @Override
  public boolean hasInfoReq(String info) {
    return false;
  }

  @Override
  public String execInfoReq(String info, String base, String fname,
        HashSet<String> extl, String subDir, HashMap<String, String> params, 
                              boolean minified) throws WsSrvException {
    return null;
  }
  
  @Override
  public String getJson(InputStream in, String name, boolean minified)
      throws WsSrvException {
    return "\"" + Utils.escJsonStr(get(in, name, minified)) + "\"";
  }  
}
