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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.ITestResourceConfig;
import com.osbitools.ws.shared.JarTestResourceUtils;
import com.osbitools.ws.shared.JarUtils;
import com.osbitools.ws.shared.Utils;

/**
 * Generic utilities read demo demo resources from jar file
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class BasicJarDemoFileUtils extends BasicDemoFileUtils {

  // private static DsMapTestResConfig _ds_cfg = new DsMapTestResConfig();
  private final ITestResourceConfig _ds_cfg;
  
  public BasicJarDemoFileUtils(ITestResourceConfig cfg) {
    _ds_cfg = cfg;
  }
  
  /**
   * Return name of 2 common files used for test from top project directory
   * @return
   */
  public abstract String[][] getTestEntities();
  
  public abstract String getMainDemoFileJson();
  
  public abstract String[][] getDemoEntities();
  
  @Override
  public void copyDemoFileToFile(String name, 
                                    String fname) throws IOException {
    JarTestResourceUtils.copyDemoFileToFile(name, fname, _ds_cfg);
  }

  /**
   * {@link com.osbitools.ws.shared.prj.utils.BasicJarDemoFileUtils#copyDemoProj(String)}
   */
  @Override
  public void copyDemoProj(String base) throws IOException {
    copyDemoProj(base, "");
    
    // Add bad.xml file
    GenericUtils.copyToFile("Bad File", base + File.separator + "bad.xml");
  }

  @Override
  public void copyDemoProj(String base, String dir) throws IOException {
    try {
      JarTestResourceUtils.copyJarDemoProj(base, dir, _ds_cfg);
    } catch (URISyntaxException e) {
      throw new IOException(e);
    }
  }

  @Override
  public String[][] getDemoSet() {
    String[][] entities = getDemoEntities();
    String[][] res= new String[entities.length][2];
    for (int i = 0; i < entities.length; i++) {
      String fname = entities[i][0];
      res[i][0] = fname;
      try {
        res[i][1] = readDemoFileAsText(fname.replace(".", "/"));
      } catch (IOException e) {
        res[i][1] = e.getMessage();
      }
    }
    
    return res;
  }
  
  @Override
  public String[][] getTestSet() {
    String[][] fnames = getTestEntities();
    String[][] res= new String[fnames.length][2];
    for (int i = 0; i < fnames.length; i++) {
      String fname = fnames[i][0];
      res[i][0] = fname;
      try {
        res[i][1] = readDemoFileAsText(fname);
      } catch (IOException e) {
        res[i][1] = e.getMessage();
      }
    }
    
    return res;
  }
  
  @Override
  public String[][] getJsonDemoSet(boolean minified) {
    String[][] entities = getDemoEntities();
    String[][] res = new String[entities.length][2];
    for (int i = 0; i < entities.length; i++) {
      res[i][0] = entities[i][0];
      res[i][1] = "\"entity\":" + Utils.getSPACE(minified) + entities[i][1];
    }
    
    return res;
  }

  @Override
  public InputStream readDemoFileAsStream() throws IOException {
    return JarTestResourceUtils.readMainDemoFileAsStream(_ds_cfg);
  }

  @Override
  public InputStream readDemoFileAsStream(String fname) throws IOException {
    return JarTestResourceUtils.readDemoFileAsStream(fname, _ds_cfg);
  }

  @Override
  public String readDemoFileAsText() throws IOException {
    return JarTestResourceUtils.readMainDemoFileAsText(_ds_cfg);
  }

  @Override
  public String readDemoFileAsText(String fname) throws IOException {
    return JarTestResourceUtils.readDemoFileAsText(
                        fname + "." + getBaseExt(), _ds_cfg);
  }

  @Override
  public String[] getDemoSrcSet() throws IOException {
    String[][] fnames = getTestEntities();
    return new String[] {
        JarUtils.readJarFileAsText(_ds_cfg.getTopResDir() +
                             "/" + fnames[0][0] + "." + getBaseExt()),
      
        JarUtils.readJarFileAsText(_ds_cfg.getTopResDir() + 
                              "/" + fnames[1][0] + "." + getBaseExt())  
    };
  }

  @Override
  public String readDemoFileRespAsText(boolean minified) throws IOException {
    return "\"entity\":" + Utils.getSPACE(minified) + getMainDemoFileJson();
  }

  @Override
  public String getProjSrcDir() {
    return _ds_cfg.getTopResDir();
  }

  @Override
  public String[][] getJsonTestSet(boolean minified) {
    String[][] ts = getTestEntities();
    String[][] res = new String[ts.length][2] ;
    for (int i = 0; i < ts.length; i++) {
      res[i][0] = ts[i][0];
      res[i][1] = "\"entity\":" + Utils.getSPACE(minified) + ts[i][1];
    }
    return res;
  }
}
