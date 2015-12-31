/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared.web;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.shared.TestConstants;

/**
 * Abstract class for MultiThread Web Test
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class AbstractMultiWebTest implements ITestInfoProvider {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Same as for single load
		BasicWebUtils.setUpBeforeClass();
	}

	@Test
	public void testConcurrentLoad() throws InterruptedException {
		int tnum = getThreadNum();
		TestConstants.done = new CountDownLatch(tnum);
	  System.out.println("Concurrent " + getTestClass().getSimpleName() + 
	                    " test for " + tnum + " Threads");
	  
		long wtime = getWaitTime();;
		// Create threads
		for (int i = 0; i < tnum; i++)
			new Thread(new MultiTestRunner(this)).start();

		Thread.sleep(1000);
		TestConstants.start.countDown();

		if (!TestConstants.done.await(wtime, TimeUnit.MILLISECONDS))
			assertEquals(TestConstants.done.getCount() + " thread(s) hasn't been " +
				"completed during " + wtime + " " + 
					TimeUnit.MILLISECONDS, 0, TestConstants.done.getCount());
		assertEquals("Total " + TestConstants.errCount + 
					" thread(s) completed with errors", 0, TestConstants.errCount);
	}

	public int getThreadNum() {
		return TestConstants.THREAD_NUM;
	}
}
