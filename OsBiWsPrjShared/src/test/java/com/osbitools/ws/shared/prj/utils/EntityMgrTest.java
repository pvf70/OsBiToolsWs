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

package com.osbitools.ws.shared.prj.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.utils.ProjectUtils;

/**
 * Test File processing using direct utils access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class EntityMgrTest extends GenericPrjMgrTest {

  @Test
  public void testWrongFileName() throws WsSrvException {
    String ext = EUT.getExt();
    
    try {
      assertEquals("Converting ." + ext, 
           "", EUT.get(root, "." + ext, true));
      fail("33 exception expected");
    } catch (WsSrvException e) {
    	checkWsSrvException("test", 33, e, "Invalid name \\\"\\\"");
    }
    
    try {
      assertEquals("Converting .." + ext, "", 
          EUT.get(root, ".." + ext, true));
      fail("33 exception expected");
    } catch (WsSrvException e) {
    	checkWsSrvException("test", 33, e, "Invalid name \\\".\\\"");
    }
  	
    for (int i = 0; i < GenericTest.BAD_ID_LIST.length; i++) {
      String id = GenericTest.BAD_ID_LIST[i];
     
      try {
        assertEquals("Converting " + id + "." + ext, "", 
            EUT.get(root, id + "." + ext, true));
        fail("33 exception expected");
      } catch (WsSrvException e) {
    	  checkWsSrvException("test", 33, e, "Invalid name \\\"" + 
    																										id + "\\\"");
      }
    }
  }
  /**
   * Test File conversion to json
   * 
   * @throws WsSrvException
   * @throws IOException
   */
  @Test
  public void testDemoFileConversion() throws WsSrvException, IOException {
    // Copy all top demo files into working ds folder
    FUTILS.copyDemoProj(root);
    
    String[][] set;
    
    // Reading Top level files
    set = FUTILS.getTestSet();
    for (int i = 0; i <  set.length; i++)
      assertEquals("Reading " + set[i][0], 
           set[i][1], EUT.get(root, 
               set[i][0] + "." + EUT.getExt(), true));
    
    // Converting Top level files
    set = FUTILS.getJsonTestSet(true);
    for (int i = 0; i <  set.length; i++)
      assertEquals("Converting " + set[i][0], 
           set[i][1], "\"entity\":" +  EUT.getJson(root, 
               set[i][0] + "." + EUT.getExt(), true));
    
    // Reading project files
    set = FUTILS.getDemoSet();
		for (int i = 0; i <  set.length; i++) {
			assertEquals("Reading " + set[i][0], set[i][1], 
			    EUT.get(root, set[i][0] + "." + EUT.getExt(), true));
		}
		
	  // Converting project files
    set = FUTILS.getJsonDemoSet(true);
    for (int i = 0; i <  set.length; i++) {
      assertEquals("Converting " + set[i][0], set[i][1], 
          "\"entity\":" + EUT.getJson(root, 
                set[i][0] + "." + EUT.getExt(), true));
    }
    
		// Delete temp file
    GenericUtils.delDirRecurse(froot, false);
	}
	
  @Test
  public void testInvalidFile() throws FileNotFoundException, IOException {
    String ext = EUT.getExt();
    
    // Create direcotry
    File ftemp = new File(root + File.separator + "test" + "." + ext);
    if (!ftemp.mkdir())
      fail("Unable creating directory " + ftemp.getAbsolutePath());
    
    // Try read file instead of directory
    try {
      EUT.get(root, "test." + ext, false);
      fail("44 exception expected");
    } catch (WsSrvException e) {
      checkWsSrvException("test", 44, e, 
      					"@^Entry \\\\\".*test." + ext + "\\\\\" is not a file$@");
    }
    
    // Delete temp file
    if (!ftemp.delete())
      fail("Unable deleting " + ftemp.getAbsolutePath());
  }
  
  @Test
  public void testSimpleFileCreate() throws WsSrvException, IOException {
    testSimpleFile("");
  }

  @Test
  public void testSimpleProjFileCreate() throws WsSrvException, IOException {
    String dname = "xxx";
    ProjectUtils.createProject(root, dname);
    
    testSimpleFile(dname + ".");
    
    ProjectUtils.delProject(root, dname);
  }

  public void testSimpleFile(String prefix) throws WsSrvException, IOException {
    String ext = EUT.getExt();
    
  	String[] flist = new String[] {"ds." + ext, "dst." + ext};
    String ds = FUTILS.readDemoFileAsText();
        
    for (String fname: flist)
      EUT.create(root, prefix + fname, ds, false, true);
    
    // Try create file with same name
    try {
      EUT.create(root, prefix + "ds." + ext, ds, false, true);
      fail("32 Exception exepted");
    } catch (WsSrvException e) {
      checkWsSrvException("same file creation", 32, e, 
        "@Entry \\\\\".*ds." + ext + "\\\\\" already exists@");
    }
    
    // Update same files
    for (String fname: flist)
      EUT.create(root, prefix + fname, ds, true, true);
    
    // Try rename to itself
    try {
      GenericUtils.renameFile(root, prefix + "ds." + ext, 
                                    prefix + "ds." + ext, EUT.getExt());
      fail("45 Exception exepted");
    } catch (WsSrvException e) {
      checkWsSrvException("same file rename", 45, e, 
        "@Can not rename file \\\\\".*ds." + ext + "\\\\\" to itself@");
    }
    
    // Try rename to existing file
    try {
      GenericUtils.renameFile(root, prefix + "ds." + ext, 
                                      prefix + "dst." + ext, EUT.getExt());
      fail("32 Exception exepted");
    } catch (WsSrvException e) {
      checkWsSrvException("rename to existing file", 32, e, 
      						"@Entry \\\\\".*dst." + ext + "\\\\\" already exists@");
    }
    
    GenericUtils.renameFile(root, prefix + "ds." + ext, 
                                        prefix + "ds1." + ext, EUT.getExt());
    
    // Read ds and compare
    String dir = prefix.equals("") ? "" : 
              File.separator + prefix.substring(0, prefix.length() - 1);
    
    byte[] entity = GenericUtils.readFile(new File(root + dir +
    																			File.separator + "ds1." + ext));
    
    assertEquals("ds." + ext + " doesn't match after reload", ds, 
                            new String(entity, StandardCharsets.UTF_8));
    
    // Try delete non-existing file
    try {
      GenericUtils.deleteFile(root, prefix + "ds." + ext, EUT.getExt());
      fail(" Exception exepted");
    } catch (WsSrvException e) {
      checkWsSrvException("non-existing file delete", 31, e, 
        "@Entry \\\\\".*ds." + ext + "\\\\\" not found@");
    }
    
    GenericUtils.deleteFile(root, prefix + "ds1." + ext, EUT.getExt());
    GenericUtils.deleteFile(root, prefix + "dst." + ext, EUT.getExt());
  }
  
}
