/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-08-07
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.api.Git;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ll_set.LangLabelsSet;

/**
 * Utilities to process Language Labels
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public class LangSetUtils extends BasicXmlFileUtils<LangLabelsSet> {

  @Override
  public String getBindPkgName() {
    return LsConstants.BIND_PKG_LANG_LABELS_SET;
  }

  private String getFullFileName(String base, String name) {
    return base + File.separator + name + File.separator +
        LsConstants.LANG_SET_FILE;
  }
  
  private String getShortFileName(String name) {
    return name + "/" + LsConstants.LANG_SET_FILE;
  }

  public String read(String base, String name, boolean minified)
      throws WsSrvException {
    File f = new File(base + File.separator + name);
    // Check if it exists and it's directory
    if (!f.exists())
      //-- 261
      throw new WsSrvException(261, "Directory " + 
                      f.getAbsolutePath() + " doesn't exists");
    else if (!f.isDirectory())
      //-- 262
      throw new WsSrvException(262, 
                    f.getAbsolutePath() + " is not the directory");

    return read(getFullFileName(base, name), minified);
  }

  public String read(String fname, boolean minified)
      throws WsSrvException {
    String json;
    try {
      json = super.getJson(new FileInputStream(fname), fname, minified);
    } catch (FileNotFoundException e) {
      // Return empty list if file not found
      json = "{}";
    }

    return json;
  }

  public synchronized String save(String base, String name, InputStream in, 
            String comment, String user, Git git, 
                      boolean minified) throws WsSrvException {
    
    String fname = getFullFileName(base, name);
    
    // Transfer input stream BufferedInputStream because it's required reset
    UnclosableBufferedInputStream bis = new UnclosableBufferedInputStream(in);
    
    // 1. Validate file b4 save
    super.validateFile(bis);
    
    // 2. Save file first
    saveFile(new File(fname), bis);
    
    try {
      bis.done();
    } catch (IOException e) {
      // Ignore error
    }
    
    // 3. Commit file
    GitUtils.commitFile(git, getShortFileName(name), comment, user);
    
    // 4. Return JSON
    return read(fname, minified);
  }
}