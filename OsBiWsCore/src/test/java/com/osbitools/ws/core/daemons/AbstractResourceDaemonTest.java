/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-08-10
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.core.daemons;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.ITestResourceConfig;
import com.osbitools.ws.shared.JarTestResourceUtils;
import com.osbitools.ws.shared.TestConstants;
import com.osbitools.ws.shared.Utils;
import com.osbitools.ws.core.bindings.BindingTest;
import com.osbitools.ws.core.shared.LocalTestConstants;

/**
 * Abstract daemon for resource file check
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */

public abstract class AbstractResourceDaemonTest<T1, 
                            T2 extends ResourceInfo<T1>> implements Runnable {
  
  static CountDownLatch ss1;
  static CountDownLatch ss2;
  static CountDownLatch ss3;
  static CountDownLatch ds1;
  static CountDownLatch ds2;
  static CountDownLatch ds3;

  // Base directory
  static final String base = LocalTestConstants.WORK_DS_DIR_SHORT;

  // Test directory
  static File td;

  // Number of errors
  static int ecnt = 0;

  // Pointer on LangLabels File check daemon
  static LsFilesCheck lcheck;

  // Daemon check
  private final AbstractResourceCheck<T1, T2> _check;
  
  // Demo key used in reload test
  private final String _dkey;
  
  // Test class
  private final Class<T1> _tclass;
  
  // Destination file used by reload tests
  private final String _dst;
  
  // Resource configuration
  private final ITestResourceConfig _cfg;
  
  // Source files used by reload test
  private final String[] _src;
  
  public AbstractResourceDaemonTest (AbstractResourceCheck<T1, T2> check,
      String key, Class<T1> tclass, String[] src, String dst, 
      ITestResourceConfig cfg) {
    
    _check = check;
    _dkey = key;
    _tclass = tclass;
    
    _src = src;
    _dst = dst;
    _cfg = cfg;
  }
  
  public static void init() throws Exception {
    // Check if target/ds dir exists
    File d = new File(base);
    if (!d.exists() && !d.mkdirs())
      fail("Unable create " + base + " directory.");

    // Clean directory
    GenericUtils.delDirRecurse(d, false);

    // Create subdirectory test
    createSubDir(LocalTestConstants.WORK_TEST_PROJ);

    // Start LangLabels check daemon
    lcheck = new LsFilesCheck(base,
        LocalTestConstants.DAEMON_SCAN_TIME);
    assertNotNull(lcheck);
    lcheck.start();

    AbstractResourceCheck._log = TestConstants.LOG;

    // Wait to initialize 
    long dts = System.currentTimeMillis();
    while (!lcheck.isInit() &&
        (System.currentTimeMillis() - dts) < TestConstants.WAIT_TIME)
      Thread.sleep(TestConstants.RESULT_CHECK_TIME);

    assertTrue("LangLabels daemon didn't initialized after " +
        TestConstants.WAIT_TIME + " msec of waiting", lcheck.isInit());

    // Check for empty labels
    String[] rlist = lcheck.getAllResources();

    assertNotNull(rlist);
    assertEquals(0, rlist.length);
  }

  static void createSubDir(String path) {
    td = new File(path);
    if (!td.exists() && !td.mkdir())
      fail("Unable create " + path + " directory.");
  }
  
  T2 checkResourceStatus(AbstractResourceCheck<T1, T2> check,  
                                    String key, boolean floaded) 
                                                  throws InterruptedException {
    T2 res = null;
    long dts = System.currentTimeMillis();
    while (true && (System.currentTimeMillis() - dts) < 
                                  TestConstants.WAIT_TIME) {
      
      res = check.getResource(key);

      if (res != null && floaded || res == null && !floaded)
        break;

      Thread.sleep(TestConstants.RESULT_CHECK_TIME);
    }

    if (floaded)
      assertNotNull("'" + key + "' is not loaded " + "after " + 
            TestConstants.WAIT_TIME + " msec of waiting.", res);
    else
      assertNull("'" + key + "' is not removed " + "after " + 
          TestConstants.WAIT_TIME + " msec of waiting.", res);

    return res;
  }
  
  public static void done() throws InterruptedException {
    // Stop daemon
    if (lcheck == null)
      return;
    lcheck.interrupt();
    lcheck.join();
  }
  
  protected synchronized T2 checkPartialTestResourceLoaded() throws Exception {
    return checkTestResourceLoaded("partial");
  }
  
  protected synchronized T2 checkFullTestResourceLoaded() throws Exception {
    return checkTestResourceLoaded("full");
  }
  
  protected T2 checkTestResourceLoaded(String stype) throws Exception {
    String sname = _tclass.getSimpleName();
    T2 res = checkResourceStatus(_check, _dkey, true);
    Method m = BindingTest.class.getMethod("check" + 
                          sname + Utils.ucFirstChar(stype), _tclass);
    m.invoke(null, res.getResource());
    
    return res;
  }

  protected void testSingleResourceReload() throws Exception {
    
    // Test bad file
    
    // Check partial file loaded
    JarTestResourceUtils.copyDemoFileToFile(_src[0], _dst, _cfg);
    checkPartialTestResourceLoaded();
    
    // Check full file loaded
    // Wait 1 sec to detect file time change
    Thread.sleep(1000);
    JarTestResourceUtils.copyDemoFileToFile(_src[1], _dst, _cfg);
    // Wait 1 sec for file to reload
    Thread.sleep(1000);
    // Test for resource reloaded
    checkFullTestResourceLoaded();
    
    // Remove file
    removeDstFile();
    
    // Check full ds file removed
    checkResourceStatus(_check, _dkey, false);
  }
  
  private void removeDstFile() {
    File f = new File(_dst);
    if (!f.delete())
      fail("Unable delete " + _dst + " file.");
  }
  
  protected void multiTestResourceReload(String cname) throws Exception {
    
    // Instantiate test class
    @SuppressWarnings("unchecked")
    Class<AbstractResourceDaemonTest<T1, T2>> tclass = 
          (Class<AbstractResourceDaemonTest<T1, T2>>) Class.forName(cname);
    AbstractResourceDaemonTest<T1, T2> test = 
                          tclass.getConstructor().newInstance();
    // Activate countdowns
    ss1 = new CountDownLatch(1);
    ss2 = new CountDownLatch(1);
    ss3 = new CountDownLatch(1);
    ds1 = new CountDownLatch(TestConstants.THREAD_NUM);
    ds2 = new CountDownLatch(TestConstants.THREAD_NUM);
    ds3 = new CountDownLatch(TestConstants.THREAD_NUM);
    
    // Create test threads
    for (int i = 0; i < TestConstants.THREAD_NUM; i++)
      new Thread(test).start();

    // Copy partial file
    JarTestResourceUtils.copyDemoFileToFile(_src[0], _dst, _cfg);

    // Test partial file reloaded
    testThreadsCompleted(ss1, ds1, "partial file load");

    Thread.sleep(1000);
    JarTestResourceUtils.copyDemoFileToFile(_src[1], _dst, _cfg);
    Thread.sleep(1000);
    
    // Test full ds file reloaded
    testThreadsCompleted(ss2, ds2, "full file reloaded");

    // Remove ds file
    removeDstFile();

    // Test ds file removed
    testThreadsCompleted(ss3, ds3, "full file removed");
  }
  
  private void testThreadsCompleted(CountDownLatch start, 
      CountDownLatch done, String msg) throws InterruptedException {
    start.countDown();
    if (!done.await(TestConstants.WAIT_TIME, TimeUnit.MILLISECONDS))
      assertEquals(done.getCount() + " thread(s) hasn't been "
          + "completed with " + msg, 0, done.getCount());
    assertEquals(ecnt + " thread(s) completed with errors", 0, ecnt);
  }
  
  @Override
  public void run() {
    try {
      ss1.await();
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }

    // Check loaded
    try {
      checkPartialTestResourceLoaded();
    } catch (Exception e) {
      fail(e.getMessage());
    }

    ds1.countDown();

    try {
      ss2.await();
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }

    // Check reloaded
    try {
      checkFullTestResourceLoaded();
    } catch (Exception e) {
      fail(e.getMessage());
    }
    
    ds2.countDown();
    
    try {
      ss3.await();
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }

    // Check removed
    try {
      checkResourceStatus(_check, _dkey, false);
    } catch (InterruptedException e1) {
      fail(e1.getMessage());
    }
    
    ds3.countDown();
  }
}
