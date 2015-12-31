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

package com.osbitools.ws.shared.xui;

import java.util.Calendar;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;

import com.osbitools.ws.shared.*;

public abstract class BasicGuiWebTest extends GenericGuiWebTest {

  public abstract void checkAfterSimpleLogin(String lang) 
                                    throws InterruptedException;
  
  @Test
  public void testLoginLangChange() throws Exception {
    // Only run for default language
    if (FLANG)
      return;
    
    init(TestConstants.LANG);
    
    driver.findElement(By.id("lang")).click();
    checkElementVisible("lang_sel", true);
    
    // Switch to alternative locale
    driver.findElement(By.id(TestConstants.LOCALE_CHANGE)).click();
    checkLoginState(TestConstants.LANG_CHANGE);
    
    driver.findElement(By.id("lang")).click();
    checkElementVisible("lang_sel", true);
    
    // Switch back to English
    driver.findElement(By.id("en_us")).click();
    checkLoginState(TestConstants.LANG);
  }
  
  @Test
  public void testFailLogin() throws Exception {
    init(TestConstants.LANG);
    
    driver.findElement(By.id("ll_sign_in")).click();
    assertEquals(getLabelText(TestConstants.LANG, "LL_USER_IS_EMPTY"), 
                                            closeAlertAndGetItsText(true));
    
    driver.findElement(By.id("usr")).clear();
    driver.findElement(By.id("usr")).sendKeys("qwerty");
    
    driver.findElement(By.id("ll_sign_in")).click();
    assertEquals(getLabelText(TestConstants.LANG, "LL_PASSWORD_IS_EMPTY"), 
                                                closeAlertAndGetItsText(true));
    
    driver.findElement(By.id("pwd")).clear();
    driver.findElement(By.id("pwd")).sendKeys("asdfgh");
    
    driver.findElement(By.id("ll_sign_in")).click();
    checkErrorWin(TestConstants.LANG, "C-2", 
      getLabelText(TestConstants.LANG, "LL_ERROR_AUTH_FAILED"), 
        "ERROR 401. ERROR #2 - Authentication Failed. " +
          "INFO: Invalid username and/or password. DETAILS: User Not Found;");
    
    driver.findElement(By.id("usr")).clear();
    driver.findElement(By.id("usr")).sendKeys(TestConstants.TEST_USER);
    
    driver.findElement(By.id("ll_sign_in")).click();
    checkErrorWin(TestConstants.LANG, "S-12", "Authentication Failed", 
                                      "Invalid username and/or password");
    
    driver.findElement(By.id("pwd")).clear();
    driver.findElement(By.id("pwd")).sendKeys(TestConstants.TEST_PASSWORD);
    
    driver.findElement(By.id("ll_sign_in")).click();
    Thread.sleep(1000);
    
    checkElementVisible("login_widget", false);
    checkElementVisible("webapp_ctx", true);
  }
  
  @Test
  public void testSimpleLogin() throws Exception {
    init(TestConstants.LANG);
    
    login(false);
    checkAfterSimpleLogin(TestConstants.LANG);
    logout(TestConstants.LANG, true);
    checkLoginState(TestConstants.LANG);
  }
  
  @Test
  public void testLangLogin() throws Exception {
    // Only run for default language
    if (FLANG)
      return;
    
    init(TestConstants.LANG);

    driver.findElement(By.id("lang")).click();
    checkElementVisible("lang_sel", true);
    
    driver.findElement(By.id(TestConstants.LOCALE_CHANGE)).click();
    checkLoginState(TestConstants.LANG_CHANGE);
    
    // Login with remember me checked
    login(true);
    checkAfterSimpleLogin(TestConstants.LANG_CHANGE);
    logout(TestConstants.LANG_CHANGE, true);
    checkLoginState(TestConstants.LANG_CHANGE, TestConstants.TEST_USER, true);
    
    // Uncheck remember
    driver.findElement(By.id("remember_me")).click();
    
    // Login again
    login(false);
    checkAfterSimpleLogin(TestConstants.LANG_CHANGE);
    logout(TestConstants.LANG_CHANGE, true);
    
    // Check interface reset to default language afer logout
    checkLoginState(TestConstants.LANG);
  }
  
  @Test
  public void testLangParamChangeLogin() throws Exception {
    // Only run when language is set
    if (!FLANG)
      return;
    
    init(TestConstants.LANG);
    
    // Login with remember me checked
    login(true);
    checkAfterSimpleLogin(TestConstants.LANG);
    logout(TestConstants.LANG, true);
    checkLoginState(TestConstants.LANG, TestConstants.TEST_USER, true);
    
    // Open browser with cookie set but without lang parameter
    driver.get(TestConstants.GUI_SRV_URL);
    Thread.sleep(1000);
    
    // Check for default language and user saved
    checkLoginState(TestConstants.DEFAULT_LANG, 
                          TestConstants.TEST_USER, true, false);
    
    // Return back to url with lang parameter
    driver.get(getGuiUrl());
    Thread.sleep(1000);
    checkLoginState(TestConstants.LANG, TestConstants.TEST_USER, true);
    
    // Uncheck remember
    driver.findElement(By.id("remember_me")).click();
    
    // Login again
    login(false);
    checkAfterSimpleLogin(TestConstants.LANG);
    logout(TestConstants.LANG, true);
    
    // Check interface reset to default language afer logout
    checkLoginState(TestConstants.LANG);
  }
  
  @Test
  public void testCopyright() throws InterruptedException {
    init(TestConstants.LANG);
    
    // Copyright is language independent
    String cr = driver.findElement(By.tagName("footer")).getText();
    assertEquals("Invalid copyright", "© Ivalab 2014-" + 
            Calendar.getInstance().get(Calendar.YEAR), cr);
  }
}
