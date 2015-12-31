/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-28
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.web;

import com.osbitools.ws.shared.TestConstants;
import com.osbitools.ws.shared.prj.PrjMgrTestConstants;
import com.osbitools.ws.shared.web.AbstractMultiWebTest;

/**
 * Test Project Multi Thread processing using web access
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class MultiProjSelWebIT extends AbstractMultiWebTest {

  @Override
  public Class<BasicProjSelWebIT> getTestClass() {
    return BasicProjSelWebIT.class;
  }

	@Override
  public long getWaitTime() {
		return TestConstants.WAIT_TIME * 20;
  }
	
	@Override
	public int getThreadNum() {
	  return PrjMgrTestConstants.THREAD_NUM;
	}
}
