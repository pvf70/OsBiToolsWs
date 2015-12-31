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
 * Implements IExFileInfo interface for test purposes
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */

public class TestExFileInfo implements IExFileInfo {

  @Override
  public String getSaveInfo(File f, String name, HashMap<String, 
          String> params, boolean minified) throws WsSrvException {
    return "";
  }
}
