/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-11
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.xui;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.osbitools.ws.shared.TestConstants;

/**
 * Test GUI with all languages
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */

public abstract class AbstractMultiLangTest {

  public abstract Class<?> getTestGuiClass();
  
  @Test
  public void testMultiLang() {
    JUnitCore junit = new JUnitCore();

    for (String lang: TestConstants.LL_SET.keySet()) {
      if (lang.equals(TestConstants.DEFAULT_LANG))
        continue;
      
      TestConstants.LANG = lang;
      Result res = junit.run(getTestGuiClass());
      
      if (res.getFailureCount() != 0) {
        System.out.println(res.getFailureCount());
        List<Failure> list = res.getFailures();
        for (int i = 0; i < list.size(); i++) {
          Failure f = list.get(i);
          System.out.println("\t" + i + 
              ":" + f.getTestHeader() + ":" + f.getMessage());
          System.out.println("\t============ TRACE START ============");
          System.out.println(f.getTrace());
          System.out.println("\t============ TRACE END ============");
        }
        
        System.out.println();
        fail("Test fail for lang: " + lang);
      }
    }
  }
  
  @AfterClass
  public static void resetLang() {
    TestConstants.LANG = null;
  }
}
