/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-04-18
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.xui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.osbitools.ws.shared.prj.PrjMgrTestConstants;

/**
 * Shared Project Manager GUI Test
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 * TODO - Add entity property test
 */
public class DemoBasicPrjMgrGuiWebTest extends GenericPrjMgrGuiWebTest {

  @Override
  public String getWebAppTitleMsg() {
    return PrjMgrTestConstants.LL_TOOL_TITLE;
  }

  @Override
  public HashMap<String, ArrayList<String>> getComponentList() {
    return PrjMgrTestConstants.COMP_LIST;
  }

  @Override
  public String getDemoDragIconId() {
    return "ww_icon_demo";
  }

  @Override
  public String getEntityTitle(String lang, String pname, String qname) {
    return pname + "." + qname + "." + DFU.getBaseExt();
  }

  @Override
  public void checkInitBodyState(String lang, WebElement ctx, 
                                        String pname, String qname) {
    checkStateByIdx(lang, ctx, pname, qname, 1);
  }

  @Override
  public void checkFirstSaveState(String lang, WebElement ctx, 
                                            String pname, String qname) {
    // State same as BodyInit
    checkStateByIdx(lang, ctx, pname, qname, 1);
  }

  @Override
  public void doSecondActionChange() {
    dragDrop(driver.findElement(By.id(getDemoDragIconId())), 
                              driver.findElement(By.id("body_ctx")));
  }

  @Override
  public void checkSecondSaveState(String lang, WebElement ctx, String pname,
      String qname) {
    checkStateByIdx(lang, ctx, pname, qname, 2);
  }

  private void checkStateByIdx(String lang, WebElement ctx, String pname,
                                                    String qname, int idx) {
    String res = "";
    String delim = "\n";
    String msg = getBodyMsg(pname, qname);
    
    for (int i = 0; i < idx; i++)
      res += delim + msg;
    
    assertEquals("Body text on save state #" + idx + "doesn't match", 
                            res.substring(delim.length()), ctx.getText());
  }

  private String getBodyMsg(String pname, String qname) {
    return pname + "." + qname + ".txt:demo";
  }

  @Override
  public void checkSecondChangeState(String lang, WebElement ctx, String pname,
      String qname) {
    checkStateByIdx(lang, ctx, pname, qname, 2);
  }

  @Override
  public void doThirdActionChange() {
    doSecondActionChange();
  }
  
  @Override
  public void checkThirdChangeState(String lang, WebElement ctx, String pname,
      String qname) {
    checkStateByIdx(lang, ctx, pname, qname, 3);
  }

  @Override
  public void checkThirdSaveState(String lang, WebElement ctx, String pname,
      String qname) {
    checkStateByIdx(lang, ctx, pname, qname, 3);
  }
  
}
