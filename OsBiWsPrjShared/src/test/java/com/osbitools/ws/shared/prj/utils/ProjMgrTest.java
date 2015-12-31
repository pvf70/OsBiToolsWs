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
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.utils.ProjectUtils;

/**
 * Test Project processing using direct utils access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class ProjMgrTest extends GenericPrjMgrTest {

  @Before
  public void checkEmptyDirOnStart() {
    // Clear root folder
    try {
      GenericUtils.delDirRecurse(froot, false);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @After
  public void checkEmptyDirOnEnd() {
    assertEquals("Check init proj list is empty", "",
    		ProjectUtils.getAllProjects(root));
  }
  
  @Test
  public void testBadProj() {
    for (int i = 0; i < GenericTest.BAD_ID_LIST.length; i++) {
      String id = GenericTest.BAD_ID_LIST[i];
      
      try {
	      ProjectUtils.createProject(root, id);
	      fail("33 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 33, e, 
                          "Invalid name \\\"" + id + "\\\"");
      }
      
      try {
	      ProjectUtils.createProject(root, id + "." + id);
	      fail("30 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 30, e, 
            "Only " + Constants.MAX_PROJ_LVL + 
              " level structure supported " +
                "but found 2 in name \\\"" + id + "." + id + "\\\"");
      }
      
      try {
        ProjectUtils.createProject(root, "." + id);
        fail("30 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 30, e, 
            "Only " + Constants.MAX_PROJ_LVL + 
            " level structure supported " +
              "but found 2 in name \\\"" + "." + id + "\\\"");
      }
      
      try {
	      ProjectUtils.createProject(root, "../" + id);
	      fail("30 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 30, e, 
            "Only 1 level structure supported " +
                "but found 3 in name \\\"../" + id + "\\\"");
      }
      
      try {
	      ProjectUtils.createProject(root, id);
	      fail("33 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 33, e, 
            "Invalid name \\\"" + id + "\\\"");
      }
      
      try {
      	ProjectUtils.getProject(root, id, EUT.getExt());
        fail("33 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 33, e, 
            "Invalid name \\\"" + id + "\\\"");
      }
    }
  }
  
  @Test
  public void testSelect() throws Exception {
    // Copy all top demo files into working ds folder
    FUTILS.copyDemoProj(root);
    
    // Test list of projects
    assertEquals("List of projects doesn't match", 
                              FUTILS.getProjListSorted(), 
                                    ProjectUtils.getAllProjects(root));
    
    // Test all entities for projects
    assertEquals("List of projects entities doesn't match", 
                            FUTILS.getProjEntitiesListSorted(), 
               ProjectUtils.getAllProjectsEntities(root, EUT.getExt()));
    
    // Test list of entities per project
    for (String[] tp : FUTILS.getProjList()) {
      String dlist = ProjectUtils.getProject(root, tp[0], EUT.getExt());
      
      assertNotNull(dlist);
      assertEquals(false, dlist.equals(""));
      
      assertEquals("Can't read test project", tp[1], dlist);
    }
    
    // Delete temp file
    GenericUtils.delDirRecurse(froot, false);
  }
  
  @Test
  public void testCreate() throws Exception {
  	String dst = "";
  	String[] dlist = new String[] {"test1", "test2", "test3"};
  	
  	for (String ds: dlist) {
  		dst += "," + ds;
	  	ProjectUtils.createProject(root, ds);
	  	assertEquals("Project test1 created", dst.substring(1), 
	  											ProjectUtils.getAllProjects(root));
	  	assertEquals("Project " + ds + " is empty", "", 
	  											ProjectUtils.getProject(root, ds, EUT.getExt()));
	  	
	  	// Try create project with same name
	  	try {
      	ProjectUtils.createProject(root, ds);
        fail("32 Exception expected");
      } catch (WsSrvException e) {
        assertEquals("Testing " + ds, 32, e.getErrorCode());
        assertEquals(ErrorList.getErrorById(32), e.getMessage());
      }
	  	
	  	Thread.sleep(1000);
  	}
  	
    for (String ds: dlist)
      ProjectUtils.delProject(root, ds);
  }
  
  @Test
  public void testUpdate() throws Exception {
  	// Create test1
  	ProjectUtils.createProject(root, "test1");
  	assertEquals("Project test1 created", "test1", 
  														ProjectUtils.getAllProjects(root));
  	
  	// Rename test1->test2
  	ProjectUtils.renameProject(root, "test1", "test2");
  	assertEquals("Project test1 created", "test2", 
													ProjectUtils.getAllProjects(root));
  	
  	Thread.sleep(1000);
  	
  	// Create test1
   	ProjectUtils.createProject(root, "test1");
   	assertEquals("Project test1 created", "test1,test2", 
   														ProjectUtils.getAllProjects(root));
   	
   	for (String ds: new String[] {"test1","test2"})
      ProjectUtils.delProject(root, ds);
  }
  
  @Test
  public void testInvalidProj() throws FileNotFoundException, IOException {
    // Create file
    File ftemp = new File(root + File.separator + "test");
    (new FileOutputStream(ftemp)).close();
    
    // Try read file instead of directory
    try {
      ProjectUtils.getProject(root, "test", EUT.getExt());
      fail("40 exception expected");
    } catch (WsSrvException e) {
      checkWsSrvException("test", 40, e,
      											"@Entry \\\\\".*test\\\\\" is not a directory@");
    }
    
    // Delete temp file
    assertTrue("Deleting test", ftemp.delete());
  }
}
