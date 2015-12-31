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

package com.osbitools.ws.shared.xui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
// import org.openqa.selenium.interactions.Actions;
// import org.openqa.selenium.support.ui.Select;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.web.BasicWebUtils;

/**
 * Generic utilities for GUI Web Test
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class GenericGuiWebTest {
  // Debug level info b4 test start
  private static Level _lvl;
  
  public WebDriver driver;
  
  // private boolean acceptNextAlert = true;
  protected StringBuffer verificationErrors = new StringBuffer();

  public abstract String getWebAppTitleMsg();
  
  public abstract String getInitLogoutBtnName();

  // Language flag. Used when language defined;
  public static boolean FLANG;
  
  @BeforeClass
  public static void setLog() throws IOException {
    // Reference TestConstants to activate default Logger
    _lvl = TestConstants.LOG.getLevel();
    TestConstants.LOG.setLevel(Level.INFO);
    FLANG = TestConstants.LANG != null;
    
    if (!FLANG) {
      System.out.println("GUI test for default language");
      TestConstants.LANG = TestConstants.DEFAULT_LANG;
    } else {
      System.out.println("GUI test for language: " + TestConstants.LANG);
    }
    
    // LL_USER_IS_EMPTY
    TestConstants.LL_SET.get("en").put("LL_USER_IS_EMPTY", "User is empty");
    TestConstants.LL_SET.get("ru").put("LL_USER_IS_EMPTY", "Пустое имя");
    TestConstants.LL_SET.get("fr").put("LL_USER_IS_EMPTY", 
                                                "L'utilisateur est vide");
    
    // LL_TOOL_TITLE
    TestConstants.LL_SET.get("en").put("LL_TOOL_TITLE", "Demo Web Application");
    TestConstants.LL_SET.get("ru").put("LL_TOOL_TITLE", "Демо Веб Программа");
    TestConstants.LL_SET.get("fr").put("LL_TOOL_TITLE", "Démo Web Application");
    
    // LL_USERNAME
    TestConstants.LL_SET.get("en").put("LL_USERNAME", "Username");
    TestConstants.LL_SET.get("ru").put("LL_USERNAME", "Имя Пользователя");
    TestConstants.LL_SET.get("fr").put("LL_USERNAME", "Nom d'Utilisateur");
    
    // LL_PASSWORD
    TestConstants.LL_SET.get("en").put("LL_PASSWORD", "Password");
    TestConstants.LL_SET.get("ru").put("LL_PASSWORD", "Пароль");
    TestConstants.LL_SET.get("fr").put("LL_PASSWORD", "Mot de Passe");
    
    // LL_SIGN_IN
    TestConstants.LL_SET.get("en").put("LL_SIGN_IN", "Sign In");
    TestConstants.LL_SET.get("ru").put("LL_SIGN_IN", "Войти");
    TestConstants.LL_SET.get("fr").put("LL_SIGN_IN", "Signer Dans");
    
    // LL_LANGUAGE
    TestConstants.LL_SET.get("en").put("LL_LANGUAGE", "Language");
    TestConstants.LL_SET.get("ru").put("LL_LANGUAGE", "Язык");
    TestConstants.LL_SET.get("fr").put("LL_LANGUAGE", "Langue");
    
    // LBL_LANG_SEL
    TestConstants.LL_SET.get("en").put("LBL_LANG_SEL", "English");
    TestConstants.LL_SET.get("ru").put("LBL_LANG_SEL", "Русский");
    TestConstants.LL_SET.get("fr").put("LBL_LANG_SEL", "Français");
    
    // LL_REMEMBER_ME
    TestConstants.LL_SET.get("en").put("LL_REMEMBER_ME", "Remember Me");
    TestConstants.LL_SET.get("ru").put("LL_REMEMBER_ME", "Запомнить Меня");
    TestConstants.LL_SET.get("fr").put("LL_REMEMBER_ME", "Souviens-toi de moi");
    
    // LL_PASSWORD_IS_EMPTY
    TestConstants.LL_SET.get("en").put("LL_PASSWORD_IS_EMPTY",
                                                      "Password is empty");
    TestConstants.LL_SET.get("ru").put("LL_PASSWORD_IS_EMPTY", "Пустой пароль");
    TestConstants.LL_SET.get("fr").put("LL_PASSWORD_IS_EMPTY",
                                                    "Mot de passe est vide");
  
    // LL_ERROR
    TestConstants.LL_SET.get("en").put("LL_ERROR", "ERROR");
    TestConstants.LL_SET.get("ru").put("LL_ERROR", "ОШИБКА");
    TestConstants.LL_SET.get("fr").put("LL_ERROR", "ERREUR");
    
    // LL_ERROR_C
    TestConstants.LL_SET.get("en").put("LL_ERROR_C", "ERROR");
    TestConstants.LL_SET.get("ru").put("LL_ERROR_C", "ОШИБКА");
    TestConstants.LL_SET.get("fr").put("LL_ERROR_C", "ERREUR");

    // LL_DEMO_MSG
    TestConstants.LL_SET.get("en").put("LL_DEMO_MSG", "Thursday, March 12," +
                    " 2015 8:00:00 PM - OsBiTools Demo Application Ready !!!");
    TestConstants.LL_SET.get("ru").put("LL_DEMO_MSG",
          "12 марта 2015 г. 20:00:00 - OsBiTools Демо Программа готова !!!");
    TestConstants.LL_SET.get("fr").put("LL_DEMO_MSG", 
        "12 mars 2015 20:00:00 - OsBiTools Démo Application Ready !!!");

    // LL_INFO_MSG
    TestConstants.LL_SET.get("en").put("LL_INFO_MSG", "Info Message");
    TestConstants.LL_SET.get("ru").put("LL_INFO_MSG",
                                                  "Информационное Сообщение");
    TestConstants.LL_SET.get("fr").put("LL_INFO_MSG", "Message d'information");

    // LL_DETAIL_MSG_1
    TestConstants.LL_SET.get("en").put("LL_DETAIL_MSG_1", "Detail Message #1");
    TestConstants.LL_SET.get("ru").put("LL_DETAIL_MSG_1", 
                                                      "Детально сообщение №1");
    TestConstants.LL_SET.get("fr").put("LL_DETAIL_MSG_1",
                                                      "Détail du message #1");

    // LL_DETAIL_MSG_2
    TestConstants.LL_SET.get("en").put("LL_DETAIL_MSG_2", "Detail Message #2");
    TestConstants.LL_SET.get("ru").put("LL_DETAIL_MSG_2", 
                                                    "Детальное сообщение №2");
    TestConstants.LL_SET.get("fr").put("LL_DETAIL_MSG_2",
                                                      "Détail du message #2");

    // LL_BYE_BYE
    TestConstants.LL_SET.get("en").put("LL_BYE_BYE", "Bye-Bye");
    TestConstants.LL_SET.get("ru").put("LL_BYE_BYE", "Пока");
    TestConstants.LL_SET.get("fr").put("LL_BYE_BYE", "Au revoir");
    
    //  LL_LOGOUT
    TestConstants.LL_SET.get("en").put("LL_LOGOUT", "Logout");
    TestConstants.LL_SET.get("ru").put("LL_LOGOUT", "Выход");
    TestConstants.LL_SET.get("fr").put("LL_LOGOUT", "Se déconnecter");
    
    // LL_CLOSE
    TestConstants.LL_SET.get("en").put("LL_CLOSE", "Close");
    TestConstants.LL_SET.get("ru").put("LL_CLOSE", "Закрыть");
    TestConstants.LL_SET.get("fr").put("LL_CLOSE", "Se déconnecter");
    
    // LL_ERROR_AUTH_FAILED
    TestConstants.LL_SET.get("en").put("LL_ERROR_AUTH_FAILED",
                                                    "Authentication Failed");
    TestConstants.LL_SET.get("ru").put("LL_ERROR_AUTH_FAILED",
                                                    "Отказ в доступе");
    TestConstants.LL_SET.get("fr").put("LL_ERROR_AUTH_FAILED", 
                                                "Échec de l'authentification");
    
    /*
    //
    TestConstants.LL_SET.get("en").put("", "");
    TestConstants.LL_SET.get("ru").put("", "");
    TestConstants.LL_SET.get("fr").put("", "");
    */
  }
  
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }
  
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString))
      fail(verificationErrorString);
  }
  
  @AfterClass
  public static void retLog() throws IOException, URISyntaxException {
    // Return back original log level
    TestConstants.LOG.setLevel(_lvl);
  }

  public void login(boolean remember) throws InterruptedException {
    driver.findElement(By.id("usr")).clear();
    driver.findElement(By.id("usr")).sendKeys(TestConstants.TEST_USER);
    driver.findElement(By.id("pwd")).clear();
    driver.findElement(By.id("pwd")).sendKeys(TestConstants.TEST_PASSWORD);
    
    if (remember)
      driver.findElement(By.id("remember_me")).click();
    
    driver.findElement(By.id("ll_sign_in")).click();
    
    Thread.sleep(1000);
  }
  
  
  public void logout(String lang) throws InterruptedException {
    logout(lang, false);
  }
  
  public void logout(String lang, boolean finit) throws InterruptedException {
    // Press logout
    driver.findElement(By.id(finit ? 
                getInitLogoutBtnName() : "ll_logout")).click();
    checkAfterLogout(lang);
    Thread.sleep(1000);
  }
  
  public void checkErrorWin(String lang, String id, 
                    String info, String details) throws InterruptedException {
    // Check error messages
    checkElementVisible("error_window", true);
    checkElementVisible("err_info", false);

    assertEquals(getLabelText(lang, "LL_ERROR_C"), driver.findElement(
                                          By.id("ll_error_c")).getText());
    assertEquals(id, driver.findElement(
                            By.id("err_code")).getText());
    assertEquals(info, driver.findElement(
                                By.id("err_details")).getText());
    
    if (details != null) {
      // Click to see details
      driver.findElement(By.id("btn_err_toggle")).click();
      Thread.sleep(500);
      checkElementVisible("err_info", true);
      
      String actual = driver.findElement(By.id("err_info")).getText();
      BasicWebUtils.testMsg("Error info doesn't match", details, actual);
      
      // Click to hide details
      driver.findElement(By.id("btn_err_toggle")).click();
      Thread.sleep(800);
      checkElementVisible("err_info", false);
    }
    
    // Close error window
    driver.findElement(By.id("error_window")).
                          findElement(By.className("b-close")).click();
    Thread.sleep(500);
    checkElementVisible("error_window", false);
  }
  
  public void init(String lang) throws InterruptedException {
    driver.get(getGuiUrl());
    Thread.sleep(1000);
    
    checkLoginState(lang);
  }
  
  public void checkLoginState(String lang) {
    checkLoginState(lang, "", false);
  }
  
  public void checkLoginState(String lang, String usr, boolean remember) {
    checkLoginState(lang, usr, remember, FLANG);
  }
  
  public void checkLoginState(String lang, String usr, 
                                            boolean remember, boolean flang) {
    checkElementVisible("page_loader", false);
    checkElementVisible("login_widget", true);
    checkElementVisible("webapp_ctx", false);
    checkElementVisible("error_window", false);
    checkElementVisible("success_window", false);
    checkElementVisible("lang_sel", false);
    
    // Extra checks
    checkWidgetsOnLogin();
    
    assertEquals("OsBi Tools", driver.findElement(
                        By.className("osbi-header")).getText());
    checkLangElementById(lang, "LL_TOOL_TITLE", getWebAppTitleMsg());
    checkLangElementById(lang, "LL_USERNAME");
    checkLangElementById(lang, "LL_PASSWORD");
    
    checkElementVisible("lang_sel_wrapper", !flang);
    
    if (!flang) {
      checkLangElementById(lang, "LL_LANGUAGE");
      checkLangElementById(lang, "LBL_LANG_SEL");
    }
    
    checkLangElementById(lang, "LL_REMEMBER_ME");
    checkLangElementById(lang, "LL_SIGN_IN");
    
    checkElementValue("usr", usr);
    checkElementIsSelected("remember_me", remember);
  }
  
  public WebElement checkElementValue(String id, String expected) {
    WebElement el = driver.findElement(By.id(id));
    
    assertNotNull(el);
    assertEquals(expected, el.getAttribute("value"));
    
    return el;
  }
  
  public WebElement checkElementIsSelected(String id, boolean selected) {
    WebElement el = driver.findElement(By.id(id));
    
    assertNotNull(el);
    assertEquals(selected, el.isSelected());
    
    return el;
  }
  
  public void checkWidgetsOnLogin() {}
  
  public void checkAfterLogout(String lang) throws InterruptedException {
    Thread.sleep(1000);
  }
  
  public String getLabelText(String lang, String id) {
    return TestConstants.LL_SET.get(lang).get(id);
  }
  
  /**
   * Find element in global DOM by id and compare by lang label find by same id
   * 
   * @param lang Test Language
   * @param id UPPER-CASE ID
   * @return found web element if it's text match language id
   */
  public WebElement checkLangElementById(String lang, String id) {
    return checkLangElementById(lang, id, id);
  }
  
  public WebElement checkLangElementById(String lang, String id, String lid) {
    WebElement el = driver.findElement(By.id(id.toLowerCase()));
    assertEquals(getLabelText(lang, lid), el.getText());
    
    return el;
  }
  
  public WebElement checkLangElementByClassName(String lang, 
                                                    String cname, String lid) {
    WebElement el = driver.findElement(By.className(cname));
    assertEquals(getLabelText(lang, lid), el.getText());
    
    return el;
  }

  public WebElement checkLangElementByClassName(String lang, 
                                  WebElement base, String cname, String lid) {
    WebElement el = base.findElement(By.className(cname));
    assertEquals(getLabelText(lang, lid), el.getText());

    return el;
  }

  public WebElement checkElementVisible(String id, boolean visible) 
                                          throws NoSuchElementException {
    return checkElementVisible(id, visible, "");
  }
  
  public WebElement checkElementVisible(String id, boolean visible, 
                              String comment) throws NoSuchElementException {
    WebElement el = driver.findElement(By.id(id));
    checkElementVisible(el, id, visible, comment);
    
    return el;
  }

  public void checkElementVisible(WebElement el, String id, boolean visible) 
                                              throws NoSuchElementException {
    checkElementVisible(el, id, visible, "");
  }
  
  public void checkElementVisible(WebElement el, String id, boolean visible, 
                                String comment) throws NoSuchElementException {
    assertNotNull(comment + id + " is not found ", el);
    assertEquals(comment + id + " is " + (visible ? "not" : "") + 
                                    " visible", visible, el.isDisplayed());
  }
  
  public WebElement checkButtonById(String lang, String id, boolean enabled) {
    return checkButtonById(lang, id, id, enabled);
  }
  
  public WebElement checkButtonById(String lang, 
                    String id, String text, boolean enabled) {
    WebElement button = checkElementVisible(id, true);
    assertEquals(id + " is " + (enabled ? "not" : "") + 
                            " enabled", enabled, button.isEnabled());
    assertEquals(getLabelText(lang, text.toUpperCase()), button.getText());
    
    return button;
  }
  
  public String closeAlertAndGetItsText(boolean accept) {
    Alert alert = driver.switchTo().alert();
    String text = alert.getText();
    if (accept) {
      alert.accept();
    } else {
      alert.dismiss();
    }
    return text;
  }
  
  public String getGuiUrl() {
    return TestConstants.GUI_SRV_URL + (FLANG ? 
                              "&lang=" + TestConstants.LANG : "");
  }
  
  public void sendKey(Keys key) {
    Actions action = new Actions(driver);
    action.sendKeys(key).perform();
  }
  
  public void sendEscKey() {
    sendKey(Keys.ESCAPE);
  }
  
  /*
  
  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  
  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  
  */
}
