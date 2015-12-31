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

import com.osbitools.ws.shared.TestConstants;

/**
 * Multi Thread Test for Shared Module
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class MultiWebIT extends AbstractMultiWebTest {

  @Override
  public Class<?> getTestClass() {
    return BasicWebIT.class;
  }

  @Override
  public long getWaitTime() {
    return TestConstants.WAIT_TIME * 10;
  }

}
