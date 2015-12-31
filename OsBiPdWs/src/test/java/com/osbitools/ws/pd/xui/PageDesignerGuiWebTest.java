/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-04-26
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.pd.xui;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.osbitools.ws.shared.TestConstants;
import com.osbitools.ws.pd.shared.LocalTestConstants;
import com.osbitools.ws.shared.prj.PrjMgrTestConstants;
import com.osbitools.ws.shared.prj.xui.GenericPrjMgrGuiWebTest;

public class PageDesignerGuiWebTest extends GenericPrjMgrGuiWebTest {

  private static String[][] CTX_MENU;
  
  @BeforeClass
  public static void initConstants() {
    // Join base menu & extra export
    final int len = PrjMgrTestConstants.CTX_MENU.length + 
                        LocalTestConstants.EXTRA_EXPORT_CTX_MENU.length;
    int idx;
    CTX_MENU = new String[len][2];
    for (idx = 0; idx < PrjMgrTestConstants.CTX_MENU.length; idx++)
      CTX_MENU[idx] = PrjMgrTestConstants.CTX_MENU[idx];
          
    for (int j = idx; j < len; j++)
      CTX_MENU[j] = LocalTestConstants.EXTRA_EXPORT_CTX_MENU[j - idx];
    
    // LL_PAGE_DESIGNER
    TestConstants.LL_SET.get("en").put("LL_PAGE_DESIGNER_TITLE", 
                                                          "Page Designer");
    TestConstants.LL_SET.get("ru").put("LL_PAGE_DESIGNER_TITLE", 
                                                       "Редактор Страниц");
    TestConstants.LL_SET.get("fr").put("LL_PAGE_DESIGNER_TITLE",
                                                          "Page Designer");
    
    /********** Rewrite Entity Variables **********/
    
    // LL_ENTITY_NAME
    TestConstants.LL_SET.get("en").put("LL_ENTITY_NAME", "Web Page Name");
    TestConstants.LL_SET.get("ru").put("LL_ENTITY_NAME", "Имя Веб Страницы");
    TestConstants.LL_SET.get("fr").put("LL_ENTITY_NAME", "Page Web Nom");
    
    // LL_CREATE_ENTITY
    TestConstants.LL_SET.get("en").put("LL_CREATE_ENTITY", 
                                                  "Create Web Page");
    TestConstants.LL_SET.get("ru").put("LL_CREATE_ENTITY", 
                                                  "Создать Веб Страницу");
    TestConstants.LL_SET.get("fr").put("LL_CREATE_ENTITY",
                                                    "Créer une Page Web");
    
    // LL_UPLOAD_ENTITY
    TestConstants.LL_SET.get("en").put("LL_UPLOAD_ENTITY", "Upload Web Page");
    TestConstants.LL_SET.get("ru").put("LL_UPLOAD_ENTITY", 
                                                   "Загрузить Веб Страницу");
    TestConstants.LL_SET.get("fr").put("LL_UPLOAD_ENTITY",
                                                  "Télécharger la Page Web");
    
    // LL_CONTAINERS
    TestConstants.LL_SET.get("en").put("LL_CONTAINERS", "Containers");
    TestConstants.LL_SET.get("ru").put("LL_CONTAINERS", "Контейнеры");
    TestConstants.LL_SET.get("fr").put("LL_CONTAINERS", "Conteneurs");
    
    // LL_ENTITY_NAME_EXISTS
    TestConstants.LL_SET.get("en").put("LL_ENTITY_NAME_EXISTS", 
                        "Web Page with name [fname] already exists.");
    TestConstants.LL_SET.get("ru").put("LL_ENTITY_NAME_EXISTS", 
                          "Веб Страница c именем [fname] уже существует.");
    TestConstants.LL_SET.get("fr").put("LL_ENTITY_NAME_EXISTS",
                                  "Page Web avec nom [fname] existe déjà");
    
    // LL_RENAME_ENTITY
    TestConstants.LL_SET.get("en").put("LL_RENAME_ENTITY", "Rename Web Page");
    TestConstants.LL_SET.get("ru").put("LL_RENAME_ENTITY",
                                                "Переименовать Веб Страницу");
    TestConstants.LL_SET.get("fr").put("LL_RENAME_ENTITY",
                                                      "Renommer la Page Web");
    
    // LL_DELETE_ENTITY
    TestConstants.LL_SET.get("en").put("LL_DELETE_ENTITY", "Delete Web Page");
    TestConstants.LL_SET.get("ru").put("LL_DELETE_ENTITY",
                                                      "Удалить Веб Страницу");
    TestConstants.LL_SET.get("fr").put("LL_DELETE_ENTITY",
                                                    "Supprimer la Page Web");
    
    // LL_CONFIRM_ENTITY_CHANGES_CANCEL
    TestConstants.LL_SET.get("en").put("LL_CONFIRM_ENTITY_CHANGES_CANCEL", 
                      "Last Web Page changes will be lost.\nPlease confirm");
    TestConstants.LL_SET.get("ru").put("LL_CONFIRM_ENTITY_CHANGES_CANCEL", 
        "Последние изменения для Веб Страницы будут утеряны.\n" + 
                                                  "Пожалуйста подтвердите.");
    TestConstants.LL_SET.get("fr").put("LL_CONFIRM_ENTITY_CHANGES_CANCEL",
              "Les changements de dernière Page Web seront perdues." +
                                              "\nS'il vous plaît confirmer");
    
    // LL_EXPORT_TO_WEB_SITE
    TestConstants.LL_SET.get("en").put("LL_EXPORT_TO_WEB_SITE",
                                                    "Export to Web Site");
    TestConstants.LL_SET.get("ru").put("LL_EXPORT_TO_WEB_SITE",
                                                    "Экспорт в веб-сайт");
    TestConstants.LL_SET.get("fr").put("LL_EXPORT_TO_WEB_SITE",
                                                      "Exporter au site Web");
    
    // LL_EXPORT_TO_JS_EMBEDDED
    TestConstants.LL_SET.get("en").put("LL_EXPORT_TO_JS_EMBEDDED",
                                              "Export to Embedded Module");
    TestConstants.LL_SET.get("ru").put("LL_EXPORT_TO_JS_EMBEDDED",
                                "Экспорт во внедренный JavaScript модуль");
    TestConstants.LL_SET.get("fr").put("LL_EXPORT_TO_JS_EMBEDDED",
                                  "Exporter vers module JavaScript embarqué");
    
    /*
    // 
    TestConstants.LL_SET.get("en").put("", "");
    TestConstants.LL_SET.get("ru").put("", "");
    TestConstants.LL_SET.get("fr").put("", "");
    */
  }
  
  @Override
  public String getWebAppTitleMsg() {
    return LocalTestConstants.LL_TOOL_TITLE;
  }

  @Override
  public HashMap<String, ArrayList<String>> getComponentList() {
    return LocalTestConstants.COMP_LIST;
  }

  @Override
  public String getDemoDragIconId() {
    return "ww_icon_com_osbitools_containers_tab_box";
  }

  @Override
  public String getEntityTitle(String lang, String pname, String qname) {
    return pname + "." + qname + "." + DFU.getBaseExt();
  }

  @Override
  public void checkInitBodyState(String lang, 
                                WebElement ctx, String pname, String qname) {
    WebElement ccw = ctx.findElement(By.className("comp-cont-wrapper"));
    WebElement ccont = ccw.findElement(By.tagName("table"));
    assertEquals("Component Container table class name doesn't match", 
        "comp-container", ccont.getAttribute("class"));
    
    WebElement cpanel = ccont.findElement(By.tagName("td"));
    assertEquals("Component Container table class name doesn't match", 
                                  "comp-panel", cpanel.getAttribute("class"));
    
    WebElement dpanel = cpanel.findElement(By.tagName("div"));
    assertEquals("Component Container table class name doesn't match", 
        "comp-panel dashed-border ui-droppable", dpanel.getAttribute("class"));
    
    // TODO Check for component close button
    /* 
    WebElement cbtn = dpanel.findElement(By.className("ctrl-panel"));
    assertFalse("Close button is still visible", cbtn.isDisplayed());
    
    // Move mouse over and check it visible
    cbtn = dpanel.findElement(By.className("ui-icon-close"));
    assertFalse("Close button is visible", cbtn.isDisplayed());
    
    // Move mouse over close button
    Actions builder = new Actions(driver);
    builder.moveToElement(cbtn).perform();
    assertTrue("Close button is not visible", cbtn.isDisplayed());
    */
  }

  @Override
  public void checkFirstSaveState(String lang, WebElement ctx, String pname,
      String qname) {
    checkPanelWidgetCnt(ctx, 1, 1);
  }

  @Override
  public void doSecondActionChange() {
    dragContainerToPanel();
  }

  @Override
  public void checkSecondChangeState(String lang, 
                              WebElement ctx, String pname, String qname) {
    checkPanelWidgetCnt(ctx, 1, 2);
  }
  
  @Override
  public void checkSecondSaveState(String lang, 
                              WebElement ctx, String pname, String qname) {
    checkSecondChangeState(lang, ctx, pname, qname);
  }

  @Override
  public void doThirdActionChange() {
    dragContainerToPanel();
  }

  @Override
  public void checkThirdChangeState(String lang, 
                              WebElement ctx, String pname, String qname) {
    checkPanelWidgetCnt(ctx, 1, 3);
  }

  @Override
  public void checkThirdSaveState(String lang, 
                              WebElement ctx, String pname, String qname) {
    checkThirdChangeState(lang, ctx, pname, qname);
  }
  
  private void checkPanelWidgetCnt(WebElement ctx, int pcnt, int wcnt) {
    // Check for number of panels
    List<WebElement> panels = ctx.findElements(
                                  By.cssSelector("td.comp-panel"));
    assertEquals("Number of panel(s) doesn't match", pcnt, panels.size());
    
    // Check for total number of web widgets on all panel
    List<WebElement> widgets = ctx.findElements(By.className("wwg"));
    assertEquals("Number of widget(s) doesn't match", wcnt, widgets.size());
    
    // Check property tab for container
  }
  
  private void dragContainerToPanel() {
    dragDrop(driver.findElement(By.id(getDemoDragIconId())), 
        driver.findElement(By.id("body_ctx")).
              findElement(By.cssSelector("div.comp-panel")));
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }
  }
  
  @Override
  public void checkDataSourceList(String lang) {
    checkLangElementById(lang, "LL_DATA_SOURCE");
    checkSelList(driver.findElement(By.id("ds_sel")), 
                                   LocalTestConstants.DS_LIST);
  }
  
  @Override
  public void checkDbConnList(String lang) {
    checkLangElementById(lang, "LL_DB_CONNECTION");
    checkSelList(driver.findElement(By.id("conn_sel")), new String[][] {});
  }
  
  @Override
  protected String[][] getEntityContextMenu() {
    return CTX_MENU;
  }
}
