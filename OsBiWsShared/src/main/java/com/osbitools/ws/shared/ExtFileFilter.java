/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared;

import java.io.File;
import java.io.FilenameFilter;

/**
 * XML Files Filter
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class ExtFileFilter implements FilenameFilter {

  // Extension filter
  private final String _ext;
  
  // Length of extension
  private final int _len;
  
  public ExtFileFilter(String ext) {
    _ext = ext;
    _len = _ext.length();
  }
  
  @Override
  public boolean accept(File f, String name) {
    int len = name.length();
    String ext = (len < _len + 2) ? "" : 
              name.substring(len - _len);
    return ext.equals(_ext);
  }
}
