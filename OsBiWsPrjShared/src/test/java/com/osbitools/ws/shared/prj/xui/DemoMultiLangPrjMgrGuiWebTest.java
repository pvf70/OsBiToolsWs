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

package com.osbitools.ws.shared.prj.xui;

import com.osbitools.ws.shared.xui.AbstractMultiLangTest;

/**
 * Run multi language test for DemoGuiWebTest class
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class DemoMultiLangPrjMgrGuiWebTest extends AbstractMultiLangTest {

  @Override
  public Class<DemoBasicPrjMgrGuiWebTest> getTestGuiClass() {
    return DemoBasicPrjMgrGuiWebTest.class;
  }
}
