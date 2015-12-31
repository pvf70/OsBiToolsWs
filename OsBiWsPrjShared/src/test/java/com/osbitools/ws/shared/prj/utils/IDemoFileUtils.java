/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-04-15
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Interface for utilities read demo files
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public interface IDemoFileUtils {
  
  /**
   * Copy all demo files from source folder into physical directory
   * @param dest Destination folder
   * @throws IOException
   */
  public void copyDemoProj(String dest) throws IOException;
  

  /**
   * Partial copy demo files from source folder sub-directory dir 
   *      into physical directory dir
   * 
   * @param dest Destination directory to copy files
   * @param dir Sub-directory from top resource tree 
   * @throws IOException
   * @throws URISyntaxException
   */
  public void copyDemoProj(String dest, String dir) throws IOException;
  
  /**
   * Read Main Demo File
   * 
   * @return String with main demo file
   * @throws IOException 
   */
  public String readDemoFileAsText() throws IOException;

  /**
   * Read Response from Main Demo File upload
   * 
   * @return String with response on upload main demo file
   * @throws IOException 
   */
  public String readDemoFileRespAsText(boolean minified) throws IOException;
  
  /**
   * Read Size of Main Demo File upload
   * 
   * @return Size of main demo file
   * @throws IOException 
   */
  public int readDemoFileSize() throws IOException;

  /**
   * Read Demo file by name
   * 
   * @param fname File Name
   * @return String with demo file
   * @throws IOException 
   */
  public String readDemoFileAsText(String fname) throws IOException;
  
  /**
   * Read main demo file as input stream
   * 
   * @return InputStream with main demo file
   * @throws IOException 
   */
  public InputStream readDemoFileAsStream() throws IOException;
  
  /**
   * Read Demo file as input stream
   * 
   * @param fnaem File Name
   * @return InputStream with file text
   * @throws IOException 
   */
  public InputStream readDemoFileAsStream(String fname) throws IOException;
  
  /**
   * Copy Demo File into external file
   * 
   * @param name Input File Name
   * @param fname Output File Name
   * @throws IOException
   */
  public void copyDemoFileToFile(String name, 
                                        String fname) throws IOException;
  
  
  /**
   * Get 2 dimensional array with demo file(s) and corresponded text
   * 
   * @return 2 dimensional array with demo file(s) and corresponded text
   */
  public String[][] getDemoSet();

  /**
   * Get list of all files included into demo project
   * 
   * @return list of files included into demo project
   */
  public String[][] getProjList();
  
  /**
   * Get sorted list of all demo project
   * 
   * @return sorted list of all demo project
   */
  public String getProjListSorted();
  
  /**
   * Get sorted list of all demo project with all entities
   * 
   * @return sorted list of all demo project with all entities
   */
  public String getProjEntitiesListSorted();
  
  /**
   * Return 2 common files used for test from top project directory
   * @return
   */
  public String[][] getTestSet();
  
  /**
   * Return 2 common files used for test from top project directory
   * 
   * @param minified Minified flag for json
   * @return json string with file content
   */
  public String[][] getJsonTestSet(boolean minified);
  
  /**
   * Return all demo files used for test from top project directory
   * 
   * @param minified Minified flag for json
   * @return json string with file content
   */
  public String[][] getJsonDemoSet(boolean minified);
  
  /**
   * Get source directory with project test & demo files
   * @return
   */
  public String getProjSrcDir();
  
  /**
   * Get working directory with project test & demo files
   * @return
   */
  public String getProjWorkDir();
  
  /**
   * Return list of demo files source used for test
   * @return
   */
  public String[] getDemoSrcSet() throws IOException;
  
  /**
   * Get list of system components available for public use
   * 
   * @return list of system components
   */
  public String getCompList();
  
  /**
   * Get base file extension as xml, html etc
   * 
   * @return Base File extension
   */
  public String getBaseExt();
  
  /**
   * Get list of files in each resource subdirectory
   * 
   * @param resName Resource Name
   * @return List of files in each resource subdirectory
   */
  public String getExtFileList(String resName);
  
  public String[][] getExtFileTestFiles(String resName);
  
  public TestFileItem[] getExtFileTestSet(String resName, String fileExt);
  
  public HashMap <String, String>[] getExtFileTestSetParams(String resName);
  
  public String[] getExtList(String resName);
  
  public String[] getResDirList();
  
  public byte[] getDemoExFileAsBytes(String resName, String ext);
  
  public String getDemoExFileUploadResp(String resName, String ext);

}
