/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-05-04
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Generic utilities read test file resources out of jar file
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class JarTestResourceUtils {
  
  /**
   * Copy all demo files from jar file into physical directory
   * 
   * @param base Top root directory to copy files
   * @param dir Sub-directory from top resource tree 
   *          defined by Package Name to read test files from
   * @param cfg Resource Configuration object
   * @throws IOException
   * @throws URISyntaxException
   */
  public static void copyJarDemoProj(String base, String dir, 
             ITestResourceConfig cfg) throws IOException, URISyntaxException {
    JarUtils.copyJarDir(base, cfg.getTopResDir(), dir);
  }
  
  /**
   * Read Main Demo File from jar file into string
   * 
   * @return String with Main Demo File
   * @param cfg Resource Configuration object
   * @return String with file content
   * @throws IOException 
   */
  public static String readMainDemoFileAsText(ITestResourceConfig cfg) 
                                                    throws IOException {
    return readDemoFileAsText(cfg.getMainDemoFileName(), cfg);
  }

  /**
   * Read Demo File Name from jar file
   * @param fname 
   * @param cfg Resource Configuration object
   * @return String with Main Demo File
   * @throws IOException 
   */
  public static String readDemoFileAsText(String fname, 
                            ITestResourceConfig cfg) throws IOException {
    return JarUtils.readJarFileAsText(cfg.getTopResDir() + "/" + fname);
  }
  
  /**
   * Read Main Demo File from jar file into input stream
   * 
   * @param cfg Resource Configuration object
   * @return InputStream with Main Demo File
   * @throws IOException 
   */
  public static InputStream readMainDemoFileAsStream(
                        ITestResourceConfig cfg) throws IOException {
    return readDemoFileAsStream(cfg.getMainDemoFileName(), cfg);
  }
  
  /**
   * Read File by Name from jar file
   * 
   * @param fname File Name
   * @param cfg Resource Configuration object
   * @return InputStream with Main Demo File
   * @throws IOException 
   */
  public static InputStream readDemoFileAsStream(String fname, 
                          ITestResourceConfig cfg) throws IOException {
    return JarUtils.readJarFileAsStream(cfg.getTopResDir() + "/" + fname);
  }
  
  /**
   * Copy Demo File Name into external file
   * 
   * @param name File Name inside jar file
   * @param fname File Name output file name
   * @param cfg Resource Configuration object
   * @throws IOException
   */
  public static void copyDemoFileToFile(String name, String fname, 
                              ITestResourceConfig cfg) throws IOException {
    GenericUtils.copyToFile(readDemoFileAsStream(name, cfg), fname);
  }
}
