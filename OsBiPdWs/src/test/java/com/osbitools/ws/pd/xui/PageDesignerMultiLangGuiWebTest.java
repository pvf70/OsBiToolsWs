/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 * Date: 2015-05-02
 * 
 */

package com.osbitools.ws.pd.xui;

import com.osbitools.ws.shared.xui.AbstractMultiLangTest;

/**
 * Run multi language test for DemoGuiWebTest class
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class PageDesignerMultiLangGuiWebTest extends AbstractMultiLangTest {

  @Override
  public Class<PageDesignerGuiWebTest> getTestGuiClass() {
    return PageDesignerGuiWebTest.class;
  }
}
