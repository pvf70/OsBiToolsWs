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

package com.osbitools.ws.me.xui;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.osbitools.ws.me.shared.LocalTestConstants;
import com.osbitools.ws.shared.TestConstants;
import com.osbitools.ws.shared.prj.xui.GenericPrjMgrGuiWebTest;

public class MapEditorGuiWebTest extends GenericPrjMgrGuiWebTest {

  @BeforeClass
  public static void initConstants() throws ClassNotFoundException {
    // LL_MAP_EDITOR_TITLE
    TestConstants.LL_SET.get("en").put("LL_MAP_EDITOR_TITLE", "Map Editor");
    TestConstants.LL_SET.get("ru").put("LL_MAP_EDITOR_TITLE",
                                                "Редактор Карт Метаданных");
    TestConstants.LL_SET.get("fr").put("LL_MAP_EDITOR_TITLE", "Map Editor");
    
    /********** Rewrite Entity Variables **********/
    
    // LL_ENTITY_NAME
    TestConstants.LL_SET.get("en").put("LL_ENTITY_NAME", "DataSet Map Name");
    TestConstants.LL_SET.get("ru").put("LL_ENTITY_NAME", "Имя Карты Данных");
    TestConstants.LL_SET.get("fr").put("LL_ENTITY_NAME", "DataSet Carte Nom");

    // LL_CREATE_ENTITY
    TestConstants.LL_SET.get("en").put("LL_CREATE_ENTITY", 
                                                "Create DataSet Map");
    TestConstants.LL_SET.get("ru").put("LL_CREATE_ENTITY", 
                                                "Создать Карту Данных");
    TestConstants.LL_SET.get("fr").put("LL_CREATE_ENTITY",
                                                  "Créer DataSet Plan");
    
    // LL_UPLOAD_ENTITY
    TestConstants.LL_SET.get("en").put("LL_UPLOAD_ENTITY", 
                                                "Upload DataSet Map");
    TestConstants.LL_SET.get("ru").put("LL_UPLOAD_ENTITY", 
                                                  "Загрузить Карту Данных");
    TestConstants.LL_SET.get("fr").put("LL_UPLOAD_ENTITY",
                                                "Téléchargez DataSet Plan");
    
    // LL_ENTITY_NAME_EXISTS
    TestConstants.LL_SET.get("en").put("LL_ENTITY_NAME_EXISTS", 
                        "DataSet Map with name [fname] already exists.");
    TestConstants.LL_SET.get("ru").put("LL_ENTITY_NAME_EXISTS", 
                           "Карта Данных c именем [fname] уже существует.");
    TestConstants.LL_SET.get("fr").put("LL_ENTITY_NAME_EXISTS",
                          "DataSet carte avec le nom [fname] existe déjà.");
    
    // LL_CONTAINERS
    TestConstants.LL_SET.get("en").put("LL_CONTAINERS", "Containers");
    TestConstants.LL_SET.get("ru").put("LL_CONTAINERS", "Контейнеры");
    TestConstants.LL_SET.get("fr").put("LL_CONTAINERS", "Conteneurs");
    
    // LL_AVAIL_DS_TYPES
    TestConstants.LL_SET.get("en").put("LL_AVAIL_DS_TYPES", 
                                          "Available DataSet Types");
    TestConstants.LL_SET.get("ru").put("LL_AVAIL_DS_TYPES", 
                                          "Поддерживыемые Типы Данных");
    TestConstants.LL_SET.get("fr").put("LL_AVAIL_DS_TYPES", 
                                              "Types de DataSet Disponibles");
    
    // LL_RENAME_ENTITY
    TestConstants.LL_SET.get("en").put("LL_RENAME_ENTITY",
                                                        "Rename DataSet Map");
    TestConstants.LL_SET.get("ru").put("LL_RENAME_ENTITY",
                                                "Переименовать Карту Данных");
    TestConstants.LL_SET.get("fr").put("LL_RENAME_ENTITY",
                                                    "Renommez DataSet Plan");
    
    // LL_DELETE_ENTITY
    TestConstants.LL_SET.get("en").put("LL_DELETE_ENTITY",
                                                        "Delete DataSet Map");
    TestConstants.LL_SET.get("ru").put("LL_DELETE_ENTITY",
                                                      "Удалить Карту Данных");
    TestConstants.LL_SET.get("fr").put("LL_DELETE_ENTITY",
                                                    "Supprimer DataSet Plan");

    // NEW_ENTITY_BODY_MSG
    TestConstants.LL_SET.get("en").put("NEW_ENTITY_BODY_MSG", 
                                "Drag & Drop DataSet Component here");
    TestConstants.LL_SET.get("ru").put("NEW_ENTITY_BODY_MSG", 
            "Перетащить и бросить значок компоненты Типа Данных сюда");
    TestConstants.LL_SET.get("fr").put("NEW_ENTITY_BODY_MSG",
                                "Drag & Déposez Composant DataSet ici");

    // LL_GENERAL_DS_CONFIG
    TestConstants.LL_SET.get("en").put("LL_GENERAL_DS_CONFIG", 
                                          "General DataSet Configuration");
    TestConstants.LL_SET.get("ru").put("LL_GENERAL_DS_CONFIG", 
                                      "Общие Настройки для Набора Данных");
    TestConstants.LL_SET.get("fr").put("LL_GENERAL_DS_CONFIG",
                                         "Configuration Générale DataSet");

    // LL_DS_CONFIG
    TestConstants.LL_SET.get("en").put("LL_DS_CONFIG", 
                                            "DataSet Configuration");
    TestConstants.LL_SET.get("ru").put("LL_DS_CONFIG", 
                                            "Настройки для Набора Данных");
    TestConstants.LL_SET.get("fr").put("LL_DS_CONFIG",
                                                  "Configuration DataSet");

    // LL_DATA_SET
    TestConstants.LL_SET.get("en").put("LL_DATA_SET", "Data Set");
    TestConstants.LL_SET.get("ru").put("LL_DATA_SET", "Набор Данных");
    TestConstants.LL_SET.get("fr").put("LL_DATA_SET", "Data Set");

    
    // LL_CONFIRM_ENTITY_CHANGES_CANCEL
    TestConstants.LL_SET.get("en").put("LL_CONFIRM_ENTITY_CHANGES_CANCEL", 
                              "Last DataSet Map changes will be lost." +
                                                        "\nPlease confirm");
    TestConstants.LL_SET.get("ru").put("LL_CONFIRM_ENTITY_CHANGES_CANCEL", 
        "Последние изменения для Карты Данных будут утеряны." + 
                                              "\nПожалуйста подтвердите.");
    TestConstants.LL_SET.get("fr").put("LL_CONFIRM_ENTITY_CHANGES_CANCEL", 
                    "Dernière DataSet Plan changements seront perdus." +
                                              "\nS'il vous plaît confirmer");
    

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
    return "ww_icon_static";
  }

  @Override
  public void checkInitBodyState(String lang, WebElement ctx, 
                                            String pname, String qname) {
    WebElement bctx = checkElementVisible("body_ctx", true);
    WebElement dse = bctx.findElement(By.className("ds-ext-wrapper"));
    
    List<WebElement> wctrls = dse.findElements(By.className("wrapper-ctrl"));
    assertNotNull(wctrls);
    assertEquals(2, wctrls.size());
    
    // List of ctrl titles
    String[] tnames = new String[] {
      getLabelText(lang, "LL_GENERAL_DS_CONFIG"),
      "Static " + getLabelText(lang, "LL_DS_CONFIG"),
    };
    
    for (int i = 0; i < wctrls.size(); i++) {
      WebElement wctrl = wctrls.get(i);
      WebElement ctrl = wctrl.findElement(By.className("ctrl"));
      WebElement ctitle = ctrl.findElements(By.tagName("span")).get(0);
      
      assertEquals(tnames[i], ctitle.getText());
    }
    
    WebElement sd = driver.findElement(By.className("static-data"));
    
    addNewColumn(sd, "COL1", "Double");
    
    addColumnData(sd, 0, "COL1", "11");
  }

  @Override
  public String getEntityTitle(String lang, String pname, String qname) {
    return "Static " + getLabelText(lang, "LL_DATA_SET");
  }

  @Override
  public void doSecondActionChange() {
    // Add new data for existing column COL1
    addStaticColumnData(0, "COL1", "12");
  }

  @Override
  public void checkSecondChangeState(String lang, 
                    WebElement ctx, String pname, String qname) {
    
    List<WebElement> crows = getStaticColRows();
    
    assertEquals(1, crows.size());
    
    WebElement crow = crows.get(0);
    String cname = crow.findElement(By.cssSelector("span.name")).getText();
    assertEquals("COL1", cname);
    
    List<WebElement> cdata = getStaticData();
    assertEquals(2, cdata.size());
    
    for (int i = 0; i < cdata.size(); i++) {
      WebElement row = cdata.get(i);
      
      List<WebElement> cells = row.findElements(By.cssSelector("td.value"));
      assertEquals(1, cells.size());
      assertEquals((new Integer(11 + i)).toString(), getColData(cells, 0));
    }
  }
  
  private String getColData(List<WebElement> cells, int idx) {
    WebElement cell = cells.get(idx);
    return cell.findElement(By.tagName("span")).getText();
  }
  
  @Override
  public void doThirdActionChange() {
    // Add new data for existing column COL1
    addStaticColumnData(0, "COL1", "13");
  }

  @Override
  public void checkFirstSaveState(String lang, WebElement ctx, String pname,
      String qname) {
    checkChangeState(ctx, "COL1", 1);
  }
  
  @Override
  public void checkThirdChangeState(String lang, WebElement ctx, String pname,
      String qname) {
    checkChangeState(ctx, "COL1", 3);
  }

  @Override
  public void checkSecondSaveState(String lang, WebElement ctx, String pname,
      String qname) {
    checkSecondChangeState(lang, ctx, pname, qname);
  }

  @Override
  public void checkThirdSaveState(String lang, WebElement ctx, String pname,
      String qname) {
    checkThirdChangeState(lang, ctx, pname, qname);
  }

  private void checkChangeState(WebElement ctx, String cn, int cnt) {
    
    List<WebElement> crows = getStaticColRows();
    
    assertEquals(1, crows.size());
    
    WebElement crow = crows.get(0);
    String cname = crow.findElement(By.cssSelector("span.name")).getText();
    assertEquals(cn, cname);
    
    List<WebElement> cdata = getStaticData();
    assertEquals(cnt, cdata.size());
    
    for (int i = 0; i < cdata.size(); i++) {
      WebElement row = cdata.get(i);
      
      List<WebElement> cells = row.findElements(By.cssSelector("td.value"));
      assertEquals(1, cells.size());
      assertEquals((new Integer(11 + i)).toString(), getColData(cells, 0));
    }
  }

  private List<WebElement> getStaticColRows() {
    // Find col-list table

    return getStaticColRows(driver.findElement(By.className("static-data")).
                              findElement(By.cssSelector("table.col-list")));
  }
  
  private List<WebElement> getStaticColRows(WebElement clt) {
    // Find table body
    WebElement clb = clt.findElement(By.cssSelector("tbody.ds"));

    // Get current number of columns
    List<WebElement> clrows = clb.findElements(By.tagName("tr"));

    return clrows;
  }
  
  private void addNewColumn(WebElement sd, 
                                String cname, String jts) {
    // Find col-list table
    WebElement clt = sd.findElement(By.cssSelector("table.col-list"));
    
    // Find table body
    WebElement clb = clt.findElement(By.cssSelector("tbody.ds"));

    List<WebElement> clrows = getStaticColRows(clt);
    int clen = clrows.size();
    
    // Add column
    clt.findElement(By.cssSelector("button.col-list-ctrl")).click();
    
    // Get new row with new column elements
    clrows = clb.findElements(By.tagName("tr"));
    assertEquals(clen + 1, clrows.size());
    WebElement clr = clrows.get(clen);
    
    // Set column name
    WebElement name = clr.findElement(By.className("name")).
                                    findElement(By.tagName("input"));
    name.clear();
    name.sendKeys(cname);
    
    // Set column type as double
    Select jtype = new Select(clr.findElement(By.className("jtype")).
                                          findElement(By.tagName("select")));
    // jtype.deselectAll();
    jtype.selectByVisibleText(jts);
    
    // Save column
    clr.findElement(By.className("col-ctrl")).
                  findElement(By.className("save")).click();
    
    // Check that no error and new column added
    checkElementVisible("error_window", false);
    
    WebElement sdata = sd.findElement(By.className("static-ds-data"));
    List<WebElement> cheaders = sdata.findElements(By.tagName("th"));
    
    assertEquals(2, cheaders.size());
    assertEquals(cname, cheaders.get(0).getText());
  }

  private void addStaticColumnData(int idx, String cname, String value) {
    addColumnData(driver.findElement(
          By.className("static-data")), idx, cname, value);
  }

  private void addColumnData(WebElement sd, 
                                    int idx, String cname, String value) {
    WebElement sdata = sd.findElement(By.className("static-ds-data"));
    List<WebElement> cheaders = sdata.findElements(By.tagName("th"));
    
    assertEquals(cname, cheaders.get(idx).getText());
    
    sdata.findElement(By.cssSelector("button.ds-ctrl")).click();
    
    // Find data section and get first row
    WebElement data = sdata.findElement(By.className("data"));
    List<WebElement> rows = data.findElements(By.tagName("tr"));
    
    int rs = rows.size();
    assertTrue(rs > 0);
    
    // Get last row
    WebElement row = rows.get(rs - 1);
    row.findElement(By.tagName("input")).sendKeys(value);
    row.findElement(By.className("save")).click();

    // Check that no error and new column added
    checkElementVisible("error_window", false);
  }
  
  private List<WebElement> getStaticData() {
    WebElement sd = driver.findElement(By.className("static-data"));
    WebElement sdata = sd.findElement(By.className("static-ds-data")).
                                findElement(By.cssSelector("tbody.data"));
    return sdata.findElements(By.tagName("tr"));
  }
  
  @Override
  public void checkDataSourceList(String lang) {
    checkLangElementById(lang, "LL_DATA_SOURCE");
    checkElementValue("ds_sel", "/osbiws_core");
  }
}
