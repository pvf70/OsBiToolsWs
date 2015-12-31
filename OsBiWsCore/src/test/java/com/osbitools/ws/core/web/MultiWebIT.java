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

package com.osbitools.ws.core.web;

import com.osbitools.ws.shared.TestConstants;
import com.osbitools.ws.shared.web.AbstractMultiWebTest;

public class MultiWebIT extends AbstractMultiWebTest {

  @Override
  public Class<?> getTestClass() {
    return WebIT.class;
  }

  @Override
  public long getWaitTime() {
    return  TestConstants.WAIT_TIME * 25;
  }
}
