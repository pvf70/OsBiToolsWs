/*
 * Copyright 2014-2016 IvaLab Inc. and other contributors
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-08-09
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.core.daemons;

import java.io.File;
import java.util.HashMap;

import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.binding.ll_set.min.*;

/**
 * 
 * Handle loaded ll_set file
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class LsResource extends ResourceInfo<LangLabelsSet> {

  // Memory Map for data from ll_set
  private HashMap<String, HashMap<String, String>> _ll;
  
  public LsResource(LangLabelsSet resHandle, File f, String prjName) {
    super(resHandle, f, prjName);
  }
  
  public LsResource(ResourceInfo<LangLabelsSet> info) throws WsSrvException {
    super(info);
  }
  
  @Override
  public void init() throws WsSrvException {
    setLangLabelsSet();
  }
  
  public void setLangLabelsSet() {
    LangLabelsSet ll_set = getResource();

    // Index labels by locales for quick access
    String[] lls = ll_set.getLangList().split(",");
    _ll = new HashMap<String, HashMap<String, String>>();

    for (String lang : lls)
      _ll.put(lang, new HashMap<String, String>());

    for (LangLabel label : ll_set.getLangLabel())
      for (LangLabelDef ld : label.getLlDef())
        _ll.get(ld.getLang()).put(label.getId(), ld.getValue());
  }
  
  public HashMap<String, String> getLangLabelSet(String lang) {
    return (_ll != null) ? _ll.get(lang) : null;
  }
  
  public String getLangLabel(String lang, String key) {
    HashMap<String, String> ll = getLangLabelSet(lang);
    return (ll != null) ? ll.get(key) : null;
  }

  public int getLangLabelSetSize() {
    LangLabelsSet info = getResource();
    return (info == null) ? 0 : info.getLangLabel().size();
  }

}
