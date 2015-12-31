/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-04-19
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.me.utils;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.osbitools.ws.shared.prj.utils.*;

@RunWith(Suite.class)
@SuiteClasses({ EntityMgrTest.class, GitMgrTest.class, 
               ProjMgrTest.class, ExFileMgrTest.class, LangSetFileTest.class})
public class SharedPrjMgrTests {
  
  @BeforeClass
  public static void initCustErrList() throws ClassNotFoundException {
    Class.forName("com.osbitools.ws.me.shared.CustErrorList");
  }
}
