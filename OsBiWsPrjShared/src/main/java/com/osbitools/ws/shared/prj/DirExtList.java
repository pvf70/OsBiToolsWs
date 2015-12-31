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

package com.osbitools.ws.shared.prj;

/**
 * List of file extension supported by directory.
 * Directory located in project root directory
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class DirExtList {

  // Subdirectory name
  private final String _dname;

  // List of file extension supported by directory
  private final String[] _extl;
  
  public DirExtList(String dirName, String[] extList) {
    _dname = dirName;
    _extl = extList;
  }
  
  public String getDirName() {
    return _dname;
  }

  public String[] getExtList() {
    return _extl;
  }
}
