/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-03-15
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.utils;

import java.util.Arrays;

/**
 * Generic utilities read demo text resources for unit test
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class BasicDemoFileUtils implements IDemoFileUtils {
  
  @Override
  public String getProjListSorted() {
    String[][] plist = getProjList();
    String[] dlist = new String[plist.length];
    for (int i = 0; i < plist.length; i++)
      dlist[i] = plist[i][0];
    
    Arrays.sort(dlist);
    
    String res = "";
    for (String dname: dlist)
      res += "," + dname;
    
    return res.substring(1);
  }
  
  @Override
  public String getProjEntitiesListSorted() {
    String[][] dset = getDemoSet();
    String[] dlist = new String[dset.length];
    for (int i = 0; i < dset.length; i++)
      dlist[i] = dset[i][0];
    
    Arrays.sort(dlist);
    
    String res = "";
    for (String dname: dlist)
      res += "," + dname;
    
    return res.substring(1);
  }
}
