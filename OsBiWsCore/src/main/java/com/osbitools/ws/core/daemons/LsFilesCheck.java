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
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ll_set.min.*;

/**
 * 
 * Monitor file with Language Labels
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class LsFilesCheck extends AbstractResourceCheck<LangLabelsSet, LsResource> {
  
  public LsFilesCheck(String base, long rescanInterval) 
        throws JAXBException, NoSuchMethodException, SecurityException {
    super(LsResource.class, base, rescanInterval);
  }

  /*
  @Override
  ResourceInfo<LangLabelsSet> doAfterResourceLoaded(
      ResourceInfo<LangLabelsSet> info) throws WsSrvException {
    LsResource res;
    if (info != null && info.getClass().equals(LsResource.class)) {
      res = (LsResource) info;
      // res.setLangLabelsSet();
    } else {
      res = new LsResource(info);
    }
    
    return res;
  }
  */
  
  @Override
  public void scan(File dir, String prefix) {
    
    String[] flist = dir.list();

    for (String pname : flist) {
      String path = dir + File.separator + pname;
      File fd = new File(path);
      if (!fd.isDirectory())
        continue;
       
      String fpath = path + File.separator + LsConstants.LANG_SET_FILE;
      if ((new File(fpath)).exists()) {
        String fkey = pname + "." + LsConstants.LANG_SET_FILE;
        
        checkResourceFile(fkey, fpath, pname);
      }
    }
  }

  public synchronized HashMap<String, String> getLangLabelSet(
                                        String prj, String lang) {
    LsResource res = getResource(prj);
    return res == null ? null : res.getLangLabelSet(lang);
  }

  public synchronized String getLangLabel(String prj, String lang, String key) {
    LsResource res = getResource(prj + LsConstants.LANG_SET_FILE);
    return res == null ? null : res.getLangLabel(lang, key);
  }
  
  @Override
  String getBindPkg() {
    return LsConstants.BIND_PKG_LANG_LABELS_SET_MIN;
  }

  @Override
  int[][] getVersions(LangLabelsSet res) {
    return new int[][] { new int[] { res.getVerMax(),
                        res.getVerMin() }, LsConstants.LS_VER };
  }
}
