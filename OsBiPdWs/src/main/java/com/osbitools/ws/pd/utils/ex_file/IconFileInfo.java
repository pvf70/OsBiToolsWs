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

package com.osbitools.ws.pd.utils.ex_file;

import java.io.File;
import java.util.HashMap;

import org.apache.axis.encoding.Base64;

import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.prj.utils.IExFileInfo;

public class IconFileInfo implements IExFileInfo {

  @Override
  public String getSaveInfo(File f, String name,
      HashMap<String, String> params, boolean minified) throws WsSrvException {
    return "{\"base64\":\"" + new String(Base64.encode(
                  GenericUtils.readFile(f))) + "\"}";
  }
}
