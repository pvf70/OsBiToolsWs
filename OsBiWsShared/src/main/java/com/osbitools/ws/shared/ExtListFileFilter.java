/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-19
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;

/**
 * Files Filter for list of extensions
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class ExtListFileFilter implements FilenameFilter {

  // Minimal length of file with extension
  private int _mlen = Integer.MAX_VALUE;
  
  private final HashSet<String> _extl = new HashSet<String>();
  
  public ExtListFileFilter(String[] extList) {
    for (int i = 0; i < extList.length; i++) {
      String ext = extList[i];
      int len = ext.length();
      
      _extl.add(ext);
      if (len < _mlen)
        _mlen = len;
    }
    
    _mlen += 2;
  }
  
  @Override
  public boolean accept(File dirName, String fileName) {
    int len = fileName.length();
    
    if (len < _mlen)
        return false;
    
    // Extract file extension
    int pos = fileName.lastIndexOf(".");
    if (pos <= 0 || (len - pos) < (_mlen - 2))
      return false;
    
    String ext = fileName.substring(pos + 1);
    
    return _extl.contains(ext);
  }
}