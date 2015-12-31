/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-12-09
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.WsSrvException;

/**
 * Class with static External File utilities
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class ExFileUtils extends GenericUtils {
	
	public static String createFile(String base, String name, InputStream in, 
				HashSet<String> extl, String sdir, HashMap<String, String> params, 
				          IEntityUtils eut, boolean minified) throws WsSrvException {
		File f =  checkFile(base, name, extl, sdir, false);
		
    // Check if ext directory exists
    File fdir = f.getAbsoluteFile().getParentFile();
    
    if (!fdir.exists() && !fdir.mkdir())
      throw new WsSrvException(231, "Unable create subdirectory " + 
                                                fdir.getAbsolutePath());
    
    saveFile(f, in);
    
    return eut.postExFileCreate(f, name, sdir, params, minified);
  }
	
  public static byte[] getFile(String base, String name, 
      HashSet<String> extl, String sdir) throws WsSrvException {
    File f = checkFile(base, name, extl, sdir, true);

    return readFile(f);
  }
}
  
