/*
 * Copyright 2014-2016 IvaLab Inc. and other contributors
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-07
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.core.daemons;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;

/**
 * DataSet checking daemon
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class DsFilesCheck extends AbstractResourceCheck<DataSetDescr, 
                                                            DsDescrResource> {

	public DsFilesCheck(String base, long rescanInterval) 
	          throws JAXBException, NoSuchMethodException, SecurityException {
		super(DsDescrResource.class, base, rescanInterval);
	}
	
	@Override
	public void scan(File dir, String prefix) {
	  File[] flist = dir.listFiles();
	  
	  for (File f: flist) {
	    if (f.isDirectory()) {
	      // Recursion
	      scan(f, prefix + f.getName() + ".");
	    } else {
	      // Check extension
	      String fname = f.getName();
	      int pos = fname.lastIndexOf(".");
	      if (pos <= 0 || pos == fname.length() - 1 || 
	                              !"xml".equals(fname.substring(pos + 1)))
	        continue;
	      
        // Read file name with extension
        String mname = prefix + fname;
        
        String fpath = getBasePath() + File.separator + 
            prefix.replaceAll("\\.", File.separator) + fname;
        
        checkResourceFile(mname, fpath, prefix);
	    }
	  }
	}
	

	@Override
	String getBindPkg() {
		return DsConstants.BIND_PKG_DS_MAP;
	}

	/*
	@Override
	DsDescrResource doAfterResourceLoaded(ResourceInfo<DataSetDescr> info) 
	                                                  throws WsSrvException {
		DsDescrResource res;
		if (info != null && info.getClass().equals(DsDescrResource.class)) {
			res = (DsDescrResource) info;
			res.setLangSet();
		} else {
			res = new DsDescrResource(info);
		}
		
		return res;
	}
	*/
	
  /**
   * Return array with custom loaded maps (excluding system)
   * @return array with custom loaded maps (excluding system)
   */
	public String[] getDsMapList() {
	  String[] list = getAllResources();
	  ArrayList<String> res = new ArrayList<String>();
	  
	  for (String name: list) {
	    int pos = name.indexOf(".");
	    if (pos > 0 && pos < name.length() - 4) {
	      res.add(name);
	    }
	  }
	  
    return res.toArray(new String[res.size()]);
  }
	
	/*
	public int getDsMapListSize() {
    return _flist.size();
    getResListSize();
  }
	*/
	@Override
	int[][] getVersions(DataSetDescr res) {
		return new int[][] {new int[] {res.getVerMax(), 
				                  res.getVerMin()}, DsConstants.DS_VER};
	}
	
}

