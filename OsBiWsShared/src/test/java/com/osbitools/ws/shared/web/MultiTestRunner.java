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

package com.osbitools.ws.shared.web;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.osbitools.ws.shared.TestConstants;

/**
 * Multi Thread Test Runner
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class MultiTestRunner implements Runnable {

  private ITestInfoProvider _tp;

	public MultiTestRunner(ITestInfoProvider testProvider) { 
	  _tp = testProvider;
	}
	
	@Override
  public void run() {
    try {
      TestConstants.start.await();
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }

    try {
      JUnitCore junit = new JUnitCore();
      Result res = junit.run(_tp.getTestClass());

      if (res.getFailureCount() != 0) {
        TestConstants.errCount++;
        System.out.println(res.getFailureCount());
        List<Failure> list = res.getFailures();
        for (int i = 0; i < list.size(); i++)
          System.out.println("\t" + i + ":"
              + list.get(i).getMessage() + ";");

        System.out.println();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      TestConstants.errCount++;
    }

    TestConstants.done.countDown();
  }

}
