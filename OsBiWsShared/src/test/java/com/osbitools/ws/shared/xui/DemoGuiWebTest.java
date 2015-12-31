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

package com.osbitools.ws.shared.xui;

import static org.junit.Assert.*;

import org.openqa.selenium.*;

public class DemoGuiWebTest extends BasicGuiWebTest {

  @Override
  public void checkAfterLogout(String lang) {
    // Check bye msg
    assertEquals("h2 element text doesn't match", 
        getLabelText(lang, "LL_BYE_BYE"), 
          driver.findElement(By.tagName("h2")).getText());
  }
  
  private void checkDemoPage(String lang) throws InterruptedException {
    checkElementVisible("page_loader", false);
    checkElementVisible("login_widget", false);
    checkElementVisible("webapp_ctx", true);
    checkElementVisible("error_window", false);
    checkElementVisible("success_window", true);
    
    // Extra check
    checkWidgetsOnLogin();
    
    // Check success message
    assertEquals("Success message doesn't match", 
        getLabelText(lang, "LL_DEMO_MSG"), 
                          driver.findElement(By.id("ok_msg")).getText());
    // Click on close button
    driver.findElement(By.id("success_window")).
          findElement(By.className("b-close")).click();
    
    Thread.sleep(1000);
    
    checkElementVisible("webapp_ctx", true);
    checkElementVisible("error_window", false);
    checkElementVisible("success_window", false);
    
    // Check color of message
    WebElement dmsg = driver.findElement(By.className("demo"));
    assertEquals("demo message color doesn't match", 
                        "rgba(128, 128, 128, 1)", dmsg.getCssValue("color"));
    
    checkButtonById(lang, "ll_error", true);
    checkButtonById(lang, "ll_logout", true);
    
    // Check &  Press error button
    WebElement err = driver.findElement(By.id("ll_error"));
    err.click();
    Thread.sleep(500);
    checkErrorWin(lang, "C-DEMO", getLabelText(lang, "LL_INFO_MSG"),
        getLabelText(lang, "LL_DETAIL_MSG_1") + "\n" + 
            getLabelText(lang, "LL_DETAIL_MSG_2"));
  }
  
  @Override
  public void checkAfterSimpleLogin(String lang) throws InterruptedException {
    checkDemoPage(lang);
  }

  @Override
  public String getWebAppTitleMsg() {
    return "LL_TOOL_TITLE";
  }

  @Override
  public String getInitLogoutBtnName() {
    return "ll_logout";
  }
}
