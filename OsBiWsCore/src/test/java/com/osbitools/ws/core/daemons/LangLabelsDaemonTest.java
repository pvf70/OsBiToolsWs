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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.core.shared.LocalTestConstants;
import com.osbitools.ws.shared.binding.ll_set.min.LangLabelsSet;

public class LangLabelsDaemonTest extends 
                        AbstractResourceDaemonTest<LangLabelsSet, LsResource> {

  //Destination file
  private static final String _fname = LocalTestConstants.LS_SET_TEST_FILE_PATH;
 
  public static LsFileTestResConfig LS_CFG = new LsFileTestResConfig();
  
  private static final String fkey = LocalTestConstants.TEST_LS_FILE_KEY;

  public LangLabelsDaemonTest() {
    super(lcheck, fkey, LangLabelsSet.class, new String[] {
        LsConstants.LANG_SET_FILE + "_test", 
              LsConstants.LANG_SET_FILE}, _fname, LS_CFG);
  }
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    init();
  }

  
	@Test
  public void testSingleReload() throws Exception {
	  testSingleResourceReload();
	}
	
  @Test
  public void multiTestReload() throws Exception {
    multiTestResourceReload(LangLabelsDaemonTest.class.getName());
  }
  
  @AfterClass
  public static void setUpAfterClass() throws InterruptedException {
    // Stop lcheck
    done();
  }
}
