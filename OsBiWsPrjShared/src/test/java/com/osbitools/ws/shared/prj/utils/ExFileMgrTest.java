/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2015-05-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.utils;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.*;

public class ExFileMgrTest extends GenericPrjMgrTest {
  
  @BeforeClass
  public static void prepDemoProjDir() throws IOException, URISyntaxException {
    // Copy all top demo maps into demo folder
    FUTILS.copyDemoProj(PrjMgrTestConstants.DEMO_PRJ_DIR_S);
  }
  
  @Test
  public void testExFileList() throws WsSrvException {
    String dname = "test";
    String fname = dname + ".*";
    
    assertTrue("Ext File Mask doesn't match",
        PrjMgrConstants.EXT_FILE_LIST_MASK.matcher(fname).matches());
    
    HashMap<String, String[]> res = EUT.getSubDirExtList();
    if (res == null)
      return;
    
    for (String rname: res.keySet()) {
      assertEquals("List of ex files doesn't match", 
          FUTILS.getExtFileList(rname), GenericUtils.
                  getResDirExtList(PrjMgrTestConstants.DEMO_PRJ_DIR_S, 
                      fname, rname, EUT.getExtLstFilenameFilter(rname)));
    }
  }
  
  @Test
  public void testExtFileInfo() throws WsSrvException {
    String dname = "test";
    
    HashMap<String, String[]> res = EUT.getSubDirExtList();
    if (res == null)
      return;
    
    for (String rname: res.keySet()) {
      String [][] files = FUTILS.getExtFileTestFiles(rname);
      for (int i = 0; i < files.length; i++) {
        String[] ftest = files[i];
        
        Pattern p = Pattern.compile(ftest[1].substring(1, 
                                                    ftest[1].length() - 1));
        String info = GenericUtils.getInfo(PrjMgrTestConstants.DEMO_PRJ_DIR, 
            dname + "." + ftest[0], EUT.getExtList(rname), 
            rname, new HashMap<String, String>(), true);
        
        assertTrue("File info for " + ftest[0] + " is invalid", 
                                              p.matcher(info).matches());
      }
    }
  }
  
  @Test
  public void testExFile() throws WsSrvException {
    HashMap<String, String[]> res = EUT.getSubDirExtList();
    if (res == null)
      return;
    
    String dname = "xxx";
    ProjectUtils.createProject(getRoot(), dname);
    
    for (String rname: res.keySet()) {
      HashSet<String> extl = EUT.getExtList(rname);
      for (String ext: extl) {
        HashMap <String, String>[] tp = FUTILS.getExtFileTestSetParams(rname);
        TestFileItem[] ts = FUTILS.getExtFileTestSet(rname, ext);
        for (int i = 0; i < ts.length; i++)
          testExFile(dname + ".test" + i, 
              ts[i].getFileText(), ts[i].getStrResponse(), extl,
                                        tp != null ? tp[i] : null, rname, ext);
      }
    }
    
    ProjectUtils.delProject(getRoot(), dname);
  }
  
  private void testExFile(String name, byte[] ftext, String fresp, 
      HashSet<String> extl, HashMap <String, String> params, String sdir, 
                                        String ext) throws WsSrvException {

    String cname = name + "." + ext;
    String rname = name + "_temp." + ext;
    
    String res = ExFileUtils.createFile(getRoot(), cname, 
                    new ByteArrayInputStream(ftext), 
                                          extl, sdir, params, EUT, true);
    
    assertEquals("Return result doesn't match for " + name, fresp, res);
    
    GenericUtils.renameFile(root, cname, rname, extl, sdir);
    
    assertArrayEquals("File after get doesn't match original file", 
        ftext, ExFileUtils.getFile(getRoot(), rname, extl, sdir));

    GenericUtils.deleteFile(root, rname, extl, sdir);
  }
}
