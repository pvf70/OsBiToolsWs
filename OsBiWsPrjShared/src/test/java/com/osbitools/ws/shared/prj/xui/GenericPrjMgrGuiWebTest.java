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

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.osbitools.ws.shared.ExtFileFilter;
import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.TestConstants;
import com.osbitools.ws.shared.xui.BasicGuiWebTest;
import com.osbitools.ws.shared.prj.PrjMgrTestConstants;
import com.osbitools.ws.shared.prj.utils.GenericPrjMgrTest;
import com.osbitools.ws.shared.prj.utils.IDemoFileUtils;

/**
 * Abstract Class for generic PrjMgr test methods
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */

public abstract class GenericPrjMgrGuiWebTest extends BasicGuiWebTest {

  //Random generator
  private SecureRandom random = new SecureRandom();
 
  protected static IDemoFileUtils DFU;
  
  private String _unq_name;
  
  @BeforeClass
  public static void cleanWorkDir() throws Exception {
    DFU = GenericPrjMgrTest.getDemoFileUtilsHandler();
    
    // Activate lang set
    Class.forName("com.osbitools.ws.shared.prj.PrjMgrTestConstants");
    
    // Delete top files
    File dir = new File(DFU.getProjWorkDir());
    String[] files = dir.list(new ExtFileFilter(DFU.getBaseExt()));
    
    assertNotNull("Invalid work directory", files);
    
    for (String fname: files)
      assertTrue((new File(DFU.getProjWorkDir() + 
                    File.separator + fname)).delete());
    
    // Add to the LL_SET
    
    // LL_PRJ_MGR_TOOL_TITLE
    TestConstants.LL_SET.get("en").put("LL_PRJ_MGR_TOOL_TITLE",
                                  "Project Manager Demo Web Application");
    TestConstants.LL_SET.get("ru").put("LL_PRJ_MGR_TOOL_TITLE",
                                "Демо Веб Программа Управления Проектами");
    TestConstants.LL_SET.get("fr").put("LL_PRJ_MGR_TOOL_TITLE",
                                    "Chef de Projet Web Application Démo");

    // LL_PROJECT_NAME
    TestConstants.LL_SET.get("en").put("LL_PROJECT_NAME", "Project Name");
    TestConstants.LL_SET.get("ru").put("LL_PROJECT_NAME", "Имя Проекта");
    TestConstants.LL_SET.get("fr").put("LL_PROJECT_NAME", "Nom du Projet");

    // LL_CREATE
    TestConstants.LL_SET.get("en").put("LL_CREATE", "Create");
    TestConstants.LL_SET.get("ru").put("LL_CREATE", "Создать");
    TestConstants.LL_SET.get("fr").put("LL_CREATE", "Créer");

    // LL_ENTITY_NAME
    TestConstants.LL_SET.get("en").put("LL_ENTITY_NAME", "Entity Name");
    TestConstants.LL_SET.get("ru").put("LL_ENTITY_NAME", "Имя Объекта");
    TestConstants.LL_SET.get("fr").put("LL_ENTITY_NAME", "Nom Entité");

    // LL_CANCEL
    TestConstants.LL_SET.get("en").put("LL_CANCEL", "Cancel");
    TestConstants.LL_SET.get("ru").put("LL_CANCEL", "Отменить");
    TestConstants.LL_SET.get("fr").put("LL_CANCEL", "Annuler");

    // LL_SELECT_PROJECT
    TestConstants.LL_SET.get("en").put("LL_SELECT_PROJECT", "Select Project");
    TestConstants.LL_SET.get("ru").put("LL_SELECT_PROJECT", "Выберите Проект");
    TestConstants.LL_SET.get("fr").put("LL_SELECT_PROJECT", "Projet Choisi");
    
    // LL_NEW
    TestConstants.LL_SET.get("en").put("LL_NEW", "New");
    TestConstants.LL_SET.get("ru").put("LL_NEW", "Новый");
    TestConstants.LL_SET.get("fr").put("LL_NEW", "Nouveau");

    // LL_RENAME
    TestConstants.LL_SET.get("en").put("LL_RENAME", "Rename");
    TestConstants.LL_SET.get("ru").put("LL_RENAME", "Переименовать");
    TestConstants.LL_SET.get("fr").put("LL_RENAME", "Rebaptiser");

    // LL_DELETE
    TestConstants.LL_SET.get("en").put("LL_DELETE", "Delete");
    TestConstants.LL_SET.get("ru").put("LL_DELETE", "Удалить");
    TestConstants.LL_SET.get("fr").put("LL_DELETE", "Effacer");

    // LL_GIT_PUSH
    TestConstants.LL_SET.get("en").put("LL_GIT_PUSH", "Git Push");
    TestConstants.LL_SET.get("ru").put("LL_GIT_PUSH", "Git Push");
    TestConstants.LL_SET.get("fr").put("LL_GIT_PUSH", "Git Push");

    // LL_PLEASE_CONFIRM
    TestConstants.LL_SET.get("en").put("LL_PLEASE_CONFIRM", 
                                                          "Please confirm");
    TestConstants.LL_SET.get("ru").put("LL_PLEASE_CONFIRM", 
                                                  "Пожалуйста подтвердите");
    TestConstants.LL_SET.get("fr").put("LL_PLEASE_CONFIRM",
                                              "S'il vous plaît confirmer");
    
    // LL_ERROR_CREATING_PROJECT
    TestConstants.LL_SET.get("en").put("LL_ERROR_CREATING_PROJECT", 
                                                    "Error Creating Project");
    TestConstants.LL_SET.get("ru").put("LL_ERROR_CREATING_PROJECT", 
                                                    "Ошибка создания проекта");
    TestConstants.LL_SET.get("fr").put("LL_ERROR_CREATING_PROJECT",
                                              "Projet de Création d'erreur");
    
    // LL_DATA_SOURCE
    TestConstants.LL_SET.get("en").put("LL_DATA_SOURCE", "Data Source");
    TestConstants.LL_SET.get("ru").put("LL_DATA_SOURCE", "Источник Данных");
    TestConstants.LL_SET.get("fr").put("LL_DATA_SOURCE", "Source de Données");
   
    // LL_DB_CONNECTION
    TestConstants.LL_SET.get("en").put("LL_DB_CONNECTION", "DB Connection");
    TestConstants.LL_SET.get("ru").put("LL_DB_CONNECTION", "БД Соединение");
    TestConstants.LL_SET.get("fr").put("LL_DB_CONNECTION", "DB Connexion");
    
    // LL_CREATE_ENTITY
    TestConstants.LL_SET.get("en").put("LL_CREATE_ENTITY", "Create Entity");
    TestConstants.LL_SET.get("ru").put("LL_CREATE_ENTITY", "Создать Объект");
    TestConstants.LL_SET.get("fr").put("LL_CREATE_ENTITY", "Créer Entité");
    
    // LL_UPLOAD_ENTITY
    TestConstants.LL_SET.get("en").put("LL_UPLOAD_ENTITY", "Upload Entity");
    TestConstants.LL_SET.get("ru").put("LL_UPLOAD_ENTITY", "Загрузить Объект");
    TestConstants.LL_SET.get("fr").put("LL_UPLOAD_ENTITY", "Ajouter Entité");
    
    // LL_DEMO_COMPONENTS
    TestConstants.LL_SET.get("en").put("LL_DEMO_COMPONENTS", "Demo Components");
    TestConstants.LL_SET.get("ru").put("LL_DEMO_COMPONENTS", "Демо компоненты");
    TestConstants.LL_SET.get("fr").put("LL_DEMO_COMPONENTS", "Composants Demo");
    
    // LL_COMMIT
    TestConstants.LL_SET.get("en").put("LL_COMMIT", "Commit");
    TestConstants.LL_SET.get("ru").put("LL_COMMIT", "Зафиксировать");
    TestConstants.LL_SET.get("fr").put("LL_COMMIT", "Commettre");
    
    // LL_CHANGE_LOG
    TestConstants.LL_SET.get("en").put("LL_CHANGE_LOG", "Change Log");
    TestConstants.LL_SET.get("ru").put("LL_CHANGE_LOG", "Журнал Изменений");
    TestConstants.LL_SET.get("fr").put("LL_CHANGE_LOG",
                                                "Journal de Modification");
    
    // LL_VERSION
    TestConstants.LL_SET.get("en").put("LL_VERSION", "Version #");
    TestConstants.LL_SET.get("ru").put("LL_VERSION", "Версия №");
    TestConstants.LL_SET.get("fr").put("LL_VERSION", "Version #");
    
    // LL_LOAD_CURRENT
    TestConstants.LL_SET.get("en").put("LL_LOAD_CURRENT", 
                                                "Load Current");
    TestConstants.LL_SET.get("ru").put("LL_LOAD_CURRENT", 
                                                "Загрузить текущий файл");
    TestConstants.LL_SET.get("fr").put("LL_LOAD_CURRENT", "Courant de charge");
    
    // LL_PREVIEW
    TestConstants.LL_SET.get("en").put("LL_PREVIEW", "Preview");
    TestConstants.LL_SET.get("ru").put("LL_PREVIEW", "Просмотр");
    TestConstants.LL_SET.get("fr").put("LL_PREVIEW", "Aperçu");
    
    // LL_SAVE
    TestConstants.LL_SET.get("en").put("LL_SAVE", "Save");
    TestConstants.LL_SET.get("ru").put("LL_SAVE", "Сохранить");
    TestConstants.LL_SET.get("fr").put("LL_SAVE", "Conserver");
    
    // LL_CANCEL
    TestConstants.LL_SET.get("en").put("LL_CANCEL", "Cancel");
    TestConstants.LL_SET.get("ru").put("LL_CANCEL", "Отменить");
    TestConstants.LL_SET.get("fr").put("LL_CANCEL", "Annuler");

    // LL_RENAME_ENTITY
    TestConstants.LL_SET.get("en").put("LL_RENAME_ENTITY", "Rename Entity");
    TestConstants.LL_SET.get("ru").put("LL_RENAME_ENTITY",
                                                    "Переименовать Объект");
    TestConstants.LL_SET.get("fr").put("LL_RENAME_ENTITY", "Renommez Entité");
    
    // LL_DELETE_ENTITY
    TestConstants.LL_SET.get("en").put("LL_DELETE_ENTITY", "Delete Entity");
    TestConstants.LL_SET.get("ru").put("LL_DELETE_ENTITY", "Удалить Объект");
    TestConstants.LL_SET.get("fr").put("LL_DELETE_ENTITY", "Supprimer Entité");

    // LL_DOWNLOAD
    TestConstants.LL_SET.get("en").put("LL_DOWNLOAD", "Download");
    TestConstants.LL_SET.get("ru").put("LL_DOWNLOAD", "Загрузить");
    TestConstants.LL_SET.get("fr").put("LL_DOWNLOAD", "Télécharger");

    // LL_ENTITY_NAME_EXISTS
    TestConstants.LL_SET.get("en").put("LL_ENTITY_NAME_EXISTS", 
                              "Entity with name [fname] already exists.");
    TestConstants.LL_SET.get("ru").put("LL_ENTITY_NAME_EXISTS", 
                               "Объект c именем [fname] уже существует.");
    TestConstants.LL_SET.get("fr").put("LL_ENTITY_NAME_EXISTS",
                              "Entité avec le nom [fname] existe déjà.");
    
    // LL_PROJ_KEY_EXISTS
    TestConstants.LL_SET.get("en").put("LL_PROJ_KEY_EXISTS", 
                                  "Project with name [key] already exists.");
    TestConstants.LL_SET.get("ru").put("LL_PROJ_KEY_EXISTS", 
                                      "Проекта' c именем [key] уже создан.");
    TestConstants.LL_SET.get("fr").put("LL_PROJ_KEY_EXISTS",
                                    "Projet avec le nom [key] existe déjà");
    
    // NEW_ENTITY_BODY_MSG
    TestConstants.LL_SET.get("en").put("NEW_ENTITY_BODY_MSG", 
                                      "Drag & Drop Component Icon here");
    TestConstants.LL_SET.get("ru").put("NEW_ENTITY_BODY_MSG", 
                          "Перетащить и бросить значок компоненты сюда");
    TestConstants.LL_SET.get("fr").put("NEW_ENTITY_BODY_MSG",
                                "Drag & Goutte Icône Composant ici");

    // LL_CLOSE
    TestConstants.LL_SET.get("en").put("LL_CLOSE", "Close");
    TestConstants.LL_SET.get("ru").put("LL_CLOSE", "Закрыть");
    TestConstants.LL_SET.get("fr").put("LL_CLOSE", "Fermer");
    
    // LL_CONFIRM_ENTITY_CHANGES_CANCEL
    TestConstants.LL_SET.get("en").put("LL_CONFIRM_ENTITY_CHANGES_CANCEL", 
                          "Last Entity changes will be lost.\nPlease confirm");
    TestConstants.LL_SET.get("ru").put("LL_CONFIRM_ENTITY_CHANGES_CANCEL", 
        "Последние изменения для Объекта будут утеряны." + 
                                            "\nПожалуйста подтвердите.");
    TestConstants.LL_SET.get("fr").put("LL_CONFIRM_ENTITY_CHANGES_CANCEL",
                            "Derniers changements entité sera perdu." +
                                              "\nS'il vous plaît confirmer");
    
    // LL_COMMIT_MESSAGE
    TestConstants.LL_SET.get("en").put("LL_COMMIT_MESSAGE", 
                                                  "Commit Message");
    TestConstants.LL_SET.get("ru").put("LL_COMMIT_MESSAGE", 
                                                  "Комментарии изменения");
    TestConstants.LL_SET.get("fr").put("LL_COMMIT_MESSAGE",
                                                      "Message de Commit");
    
    /*
     // 
     TestConstants.LL_SET.get("en").put("", "");
     TestConstants.LL_SET.get("ru").put("", "");
     TestConstants.LL_SET.get("fr").put("", "");
    */
  }
  
  @Before
  public void clearWorkDirList() {
    File dir = new File(DFU.getProjWorkDir());
    
    // Delete demo projects
    File[] files = dir.listFiles();
    
    assertNotNull("Invalid work directory", files);
    for (File f: files)
      if (f.isDirectory() && !f.getName().equals(".git"))
        try {
          GenericUtils.delDirRecurse(f);
        } catch (IOException e) {
          fail(e.getMessage());
        }
    
    // Generate temp file prefix
    _unq_name = new BigInteger(130, random).toString(16);
  }
  
  @Test
  public void testFirstProjCreate() throws InterruptedException {
    String pname = "qwerty";
    
    init(TestConstants.LANG);
    login(false);
    checkAfterSimpleLogin(TestConstants.LANG);
    
    driver.findElement(By.id("sd_input")).clear();
    driver.findElement(By.id("sd_input")).sendKeys(pname);
    
    driver.findElement(By.id("btn_ok")).click();
    
    Thread.sleep(1000);
    
    checkProjectJustCreated(TestConstants.LANG, pname);
    
    // Try create project with same name
    driver.findElement(By.id("ll_new")).click();
    driver.findElement(By.id("sd_input")).sendKeys(pname);
    driver.findElement(By.id("btn_ok")).click();
    
    assertEquals(getLabelText(TestConstants.LANG, "LL_PROJ_KEY_EXISTS").
        replace("[key]", "[" + pname + "]"), closeAlertAndGetItsText(true));
    
    // Cancel input dialog
    cancelSimpleDialog();
    
    // Rename project to itself
    driver.findElement(By.id("ll_rename")).click();
    checkProjectRenameSimpleDialog(TestConstants.LANG);
    
    WebElement sdi = driver.findElement(By.id("sd_input"));
    sdi.sendKeys(pname);
    driver.findElement(By.id("btn_ok")).click();
    
    assertEquals(getLabelText(TestConstants.LANG, "LL_PROJ_KEY_EXISTS").
        replace("[key]", "[" + pname + "]"), closeAlertAndGetItsText(true));
    
    // Rename project to different name
    String pname1 = pname + "_1";
    sendSimpleDialogInput(pname1);
    
    // Check input dialog closed
    checkElementVisible("simple_dialog", false);
    
    // Check for new project
    checkProjectList(new String[] {pname1}, pname1);
    
    // Reload browser
    driver.get(getGuiUrl());
    Thread.sleep(1000);
    
    // Check that new project is active
    checkProjectJustCreated(TestConstants.LANG, pname1);
    
    // Delete project
    driver.findElement(By.id("ll_delete")).click();
    assertEquals(getLabelText(TestConstants.LANG, "LL_PLEASE_CONFIRM"), 
                                      closeAlertAndGetItsText(true));
    
    checkAfterSimpleLogin(TestConstants.LANG, true);
    
    logout(TestConstants.LANG, true);
  }
  
  @Test
  public void testFirstEntityCreate() throws InterruptedException {
    String pname = "qwerty";
    String qname = "test1";
    
    createFirstEntity(TestConstants.LANG, pname, qname);

    // Check entity context menu
    checkItemContextMenuByClassName(TestConstants.LANG, "dynatree-active",
                                                    getEntityContextMenu());
    
    // Try create entity with same name
    newEntityBtnClick();
    testDuplicateEntityCreate(TestConstants.LANG, qname);
    
    // Try same but from context menu
    ctxMenuOpenClickById("proj_tree_wrapper", 0);
    testDuplicateEntityCreate(TestConstants.LANG, qname);
    
    ctxMenuOpenClickByClassName("dynatree-active", 0);
    checkEntityRenameSimpleDialog(TestConstants.LANG, qname);
    
    // Rename to itself
    sendSimpleDialogInput(qname);
    checkEntityExistsAlert(TestConstants.LANG, qname);
    
    delActiveEntity(TestConstants.LANG);
    checkEntityNameSimpleDialog(TestConstants.LANG);
    
    // Delete project
    deleteFirstProjectLogout(TestConstants.LANG);
  }
  
  @Test
  public void testEntityMultuSaveCommitReload() throws InterruptedException {
    String pname = "qwerty";
    String qname = _unq_name;
    String commit1 = "Second Change Commit";
    String commit2 = "Third Change Commit";
    
    WebElement body = createFirstEntity(TestConstants.LANG, pname, qname);
    dragDrop(driver.findElement(By.id(getDemoDragIconId())), 
                driver.findElement(By.id("new_entity_body_msg")));
    
    // Check new_entity_body_msg disappear
    try {
      driver.findElement(By.id("new_entity_body_msg"));
      Thread.sleep(2000);
      fail("Drag&Drop panel is still visible");
    } catch (NoSuchElementException e) {}
    
    // Check new initialized entity controls
    checkInitEntityCtrls(TestConstants.LANG, pname, qname);
    
    // Save entity
    doEntitySave();
    
    // Check contol buttons after save
    checkFirstSaveEntityCtrls(TestConstants.LANG, pname, qname);
    
    // Reload browser
    driver.get(getGuiUrl());
    Thread.sleep(1000);
    body = checkElementVisible("entity_body", true);
    
    // Check again
    checkFirstSaveEntityCtrls(TestConstants.LANG, pname, qname);
    
    // Make second change
    doSecondActionChange();
    
    // Check after
    checkSecondChangeEntityCtrls(TestConstants.LANG, pname, qname);
    
    // Cancel second changes
    checkButtonById(TestConstants.LANG, "ll_cancel", true).click();
    
    // Don't confirm
    assertEquals(getLabelText(TestConstants.LANG, 
        "LL_CONFIRM_ENTITY_CHANGES_CANCEL"), closeAlertAndGetItsText(false));
    
    // Cancel second changes again
    checkButtonById(TestConstants.LANG, "ll_cancel", true).click();
    
    // Confirm
    assertEquals(getLabelText(TestConstants.LANG, 
        "LL_CONFIRM_ENTITY_CHANGES_CANCEL"), closeAlertAndGetItsText(true));
    
    // Check everything returned to initial state
    checkFirstSaveEntityCtrls(TestConstants.LANG, pname, qname);
    
    // Repeat second changes
    doSecondActionChange();
    
    // Check after
    checkSecondChangeEntityCtrls(TestConstants.LANG, pname, qname);
    
    // Save second state
    doEntitySave();
        
    // Check after
    checkSecondSaveEntityCtrls(TestConstants.LANG, pname, qname);
    
    // Close body
    body.findElement(By.className("ui-icon-close")).click();
    
    // Cancel confirmation
    assertEquals(getLabelText(TestConstants.LANG, "LL_PLEASE_CONFIRM"), 
                                          closeAlertAndGetItsText(false));
    // Close again
    body.findElement(By.className("ui-icon-close")).click();
    
    // Confirm closing
    assertEquals(getLabelText(TestConstants.LANG, "LL_PLEASE_CONFIRM"), 
                                          closeAlertAndGetItsText(true));
    
    // Cancel changes
    checkButtonById(TestConstants.LANG, "ll_cancel", true).click();
    
    // Check state returned
    checkSecondSaveEntityCtrls(TestConstants.LANG, pname, qname);
    
    // Commit 2nd changes
    checkButtonById(TestConstants.LANG, "ll_commit", true).click();
    
    // Check commit dialog
    checkEntityCommitSimpleDialog(TestConstants.LANG);

    // Enter commit message
    sendSimpleDialogInput(commit1);
    
    // Check after commit
    checkCommitedBtns(TestConstants.LANG);
    
    // Do 3rd change
    doThirdActionChange();
    
    // Check after
    checkThirdChangeEntityCtrls(TestConstants.LANG, pname, qname);
    
    // Cancel third change
    checkButtonById(TestConstants.LANG, "ll_cancel", true).click();
    
    // Confirm
    assertEquals(getLabelText(TestConstants.LANG, 
        "LL_CONFIRM_ENTITY_CHANGES_CANCEL"), closeAlertAndGetItsText(true));
    
    // Check it returned to second state
    checkCommitedBtns(TestConstants.LANG);
    
    // Make 3rd change again
    doThirdActionChange();
    
    // Check after
    checkThirdChangeEntityCtrls(TestConstants.LANG, pname, qname);
    
    // Save third state
    doEntitySave();
    
    // Check after
    checkThirdSaveEntityCtrls(TestConstants.LANG, pname, qname);

    // Commit 3rd change
    checkButtonById(TestConstants.LANG, "ll_commit", true).click();
    
    // Check commit dialog
    checkEntityCommitSimpleDialog(TestConstants.LANG);

    // Enter commit message
    sendSimpleDialogInput(commit2);
    
    // Check after commit
    checkCommitedBtns(TestConstants.LANG);
    
    // Return to 3nd change (Second commit)
    List<WebElement> rinfo = getRevLog(2, 0);
    WebElement rev = rinfo.get(0).findElement(By.className("rev"));
    String ri = rev.getAttribute("id").substring(4);
    assertEquals(TestConstants.TEST_USER, rinfo.get(1).getText());
    assertEquals(commit2, rinfo.get(2).getText());
    rev.click();
    
    checkThirdSaveState(TestConstants.LANG, driver.findElement(
                                        By.id("body_ctx")), pname, qname);
    checkRevBtns(TestConstants.LANG, ri);
    
    // Return to 3nd change (Second commit)
    rinfo = getRevLog(2, 1);
    rev = rinfo.get(0).findElement(By.className("rev"));
    ri = rev.getAttribute("id").substring(4);
    assertEquals(TestConstants.TEST_USER, rinfo.get(1).getText());
    assertEquals(commit1, rinfo.get(2).getText());
    rev.click();
    
    checkSecondSaveState(TestConstants.LANG, driver.findElement(
                                        By.id("body_ctx")), pname, qname);
    checkRevBtns(TestConstants.LANG, ri);
    
    // Load current
    driver.findElement(By.id("ll_load_current")).click();
    Thread.sleep(1000);
    checkCommitedBtns(TestConstants.LANG);
    checkThirdSaveState(TestConstants.LANG, driver.findElement(
                                          By.id("body_ctx")), pname, qname);
    
    // Delete active entity
    ctxMenuOpenClickByClassName("dynatree-active", 1);
    
    // Confirm entity delete
    assertEquals(getLabelText(TestConstants.LANG, "LL_PLEASE_CONFIRM"), 
                                                 closeAlertAndGetItsText(true));
    
    // Cancel new entity dialog
    checkEntityNameSimpleDialog(TestConstants.LANG);
    escSimpleDialog();
    
    // Delete project
    deleteFirstProjectLogout(TestConstants.LANG);
  }
  
  private List<WebElement> getRevLog(int cnt, int idx) 
                                          throws InterruptedException {
    // Open change log
    checkButtonById(TestConstants.LANG, "ll_change_log", true).click();
    WebElement rlog = driver.findElement(By.id("change_log_wrapper"));
    Thread.sleep(1000);
    assertTrue("Change Log Wrapper is not visible", rlog.isDisplayed());
    
    // Check revisions
    WebElement clog = checkElementVisible("change_log_wrapper", true);
    List<WebElement> rlist = clog.findElement(By.tagName("table")).
            findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
    assertEquals(cnt, rlist.size());
    
    return rlist.get(idx).findElements(By.tagName("td"));
  }
  
  private void doEntitySave() throws InterruptedException {
    // Click save button
    WebElement sbtn = checkButtonById(TestConstants.LANG, "ll_save", true);
    sbtn.click();
    
    // Wait
    while (sbtn.isDisplayed() == false)
      Thread.sleep(1000);
    
    // Extra wait for returning data reload
    Thread.sleep(1000);
    
    // Check for now errors
    checkElementVisible("error_window", false);
  }
  
  public void escSimpleDialog() throws InterruptedException {
    // Send ESC key
    sendEscKey();
    
    Thread.sleep(2000);
    
    // Check dialog disappear
    checkElementVisible("simple_dialog", false, 
                          "Fail ESCaping simple dialog: ");
  }
  
  public abstract String getDemoDragIconId();
  
  public abstract void doSecondActionChange();
  public abstract void doThirdActionChange();
  
  public abstract String getEntityTitle(String lang, String pname, String qname);
  /**
   * Check contol elements for just created entity
   * 
   * @param lang Test language
   */
  private WebElement checkNewEntityCtrls(String lang) {
    // Check for error window hidden
    checkElementVisible("error_window", false);
    
    // Check entity control buttons
    checkElementVisible("entity_ctx_git_ctrl", true);
    checkButtonById(lang, "ll_commit", false);
    checkButtonById(lang, "ll_change_log", false);
    checkElementVisible("rev_info_wrapper", false);
    checkElementVisible("entity_ctx_ex_ctrl", true);
    checkElementVisible("ll_load_current", false);
    checkButtonById(lang, "ll_preview", false);
    checkElementVisible("entity_ctx_session_ctrl", true);
    checkButtonById(lang, "ll_save", false);
    checkButtonById(lang, "ll_cancel", false);
    
    WebElement body = checkElementVisible("entity_body", true);
    checkLangElementById(lang, "NEW_ENTITY_BODY_MSG");
    
    return body;
  }
  
  /**
   * Check entity that was just initialized but not saved
   * 
   * @param lang Test language
   */
  private void checkInitEntityCtrls(String lang, String pname, String qname) {
    // Check for error window hidden
    checkElementVisible("error_window", false);
    
 // Check title
    assertEquals("Entity Title doesn't match", 
        getEntityTitle(lang, pname, qname),  
                 driver.findElement(By.id("ctx_title")).getText());
    
    // Check close button
    assertEquals(driver.findElement(By.id("entity_body")).
       findElement(By.className("ui-icon-close")).
           getAttribute("title"), getLabelText(TestConstants.LANG, "LL_CLOSE"));
    
    checkInitBodyState(lang, driver.findElement(
                            By.id("body_ctx")), pname, qname);
    
    // Check entity control buttons
    // Save button should be enabled after this point
    checkInitBtns(lang, false);
  }
  
  private void checkInitBtns(String lang, boolean fcancel) {
    // Check for error window hidden
    checkElementVisible("error_window", false);
    
    // Check entity control buttons
    checkElementVisible("entity_ctx_git_ctrl", true);
    checkButtonById(lang, "ll_commit", false);
    checkButtonById(lang, "ll_change_log", false);
    checkElementVisible("rev_info_wrapper", false);
    checkElementVisible("entity_ctx_ex_ctrl", true);
    checkElementVisible("ll_load_current", false);
    checkButtonById(lang, "ll_preview", false);
    checkElementVisible("entity_ctx_session_ctrl", true);
    checkButtonById(lang, "ll_cancel", fcancel);
    checkButtonById(lang, "ll_save", true);
  }
  
  /**
   * Check entity controls after changes saved
   * 
   * @param lang Test language
   * 
   */
  private void checkSavedBtns(String lang, boolean flog) {
    // Check entity control buttons
    checkElementVisible("entity_ctx_git_ctrl", true);
    checkButtonById(lang, "ll_commit", true);
    checkButtonById(lang, "ll_change_log", flog);
    checkElementVisible("rev_info_wrapper", false);
    checkElementVisible("entity_ctx_ex_ctrl", true);
    checkElementVisible("ll_load_current", false);
    checkButtonById(lang, "ll_preview", true);
    checkElementVisible("entity_ctx_session_ctrl", true);
    checkButtonById(lang, "ll_save", false);
    checkButtonById(lang, "ll_cancel", false);
    
    checkActionLoaderHidden();
  }
  
  /**
   * Check entity controls after revision loaded
   * 
   * @param lang Test language
   * 
   */
  private void checkRevBtns(String lang, String rev) {
    // Check entity control buttons
    checkElementVisible("entity_ctx_git_ctrl", true);
    checkButtonById(lang, "ll_commit", false);
    checkButtonById(lang, "ll_change_log", true);
    checkElementVisible("rev_info_wrapper", true);
    checkElementVisible("entity_ctx_ex_ctrl", true);
    checkButtonById(lang, "ll_load_current", true);
    checkElementVisible("ll_preview", false);
    checkElementVisible("entity_ctx_session_ctrl", true);
    checkButtonById(lang, "ll_save", true);
    checkButtonById(lang, "ll_cancel", false);
    
    checkButtonById(lang, "ll_version", true);
    assertEquals(rev, driver.findElement(By.id("rev_id")).getText());
    
    checkActionLoaderHidden();
  }

  private void checkCommitedBtns(String lang) {
    // Check for error window hidden
    checkElementVisible("error_window", false);
    
    // Check entity control buttons
    checkElementVisible("entity_ctx_git_ctrl", true);
    checkButtonById(lang, "ll_commit", false);
    checkButtonById(lang, "ll_change_log", true);
    checkElementVisible("rev_info_wrapper", false);
    checkElementVisible("entity_ctx_ex_ctrl", true);
    checkElementVisible("ll_load_current", false);
    checkButtonById(lang, "ll_preview", true);
    checkElementVisible("entity_ctx_session_ctrl", true);
    checkButtonById(lang, "ll_cancel", false);
    checkButtonById(lang, "ll_save", false);
    
    checkActionLoaderHidden();
  }
  
  private void checkCommitedChangedBtns(String lang, boolean fcancel) {
    // Check for error window hidden
    checkElementVisible("error_window", false);
    
    // Check entity control buttons
    checkElementVisible("entity_ctx_git_ctrl", true);
    checkButtonById(lang, "ll_commit", false);
    checkButtonById(lang, "ll_change_log", true);
    checkElementVisible("rev_info_wrapper", false);
    checkElementVisible("entity_ctx_ex_ctrl", true);
    checkElementVisible("ll_load_current", false);
    checkButtonById(lang, "ll_preview", false);
    checkElementVisible("entity_ctx_session_ctrl", true);
    checkButtonById(lang, "ll_cancel", fcancel);
    checkButtonById(lang, "ll_save", true);
  }
  
  private void checkActionLoaderHidden() {
    // Check loading is not visible
    assertTrue("btn-loading-right class is still assigned", driver.findElement(
        By.id("entity_ctx_session_ctrl")).getAttribute("class").
                              indexOf("btn-loading-right") == -1);
  }
  
  public abstract void checkInitBodyState(String lang, WebElement ctx, 
                                              String pname, String qname);
  
  private void checkFirstSaveEntityCtrls(String lang, 
                                          String pname, String qname) {
    checkSavedBtns(lang, false);
    
    checkFirstSaveState(lang, driver.findElement(
                                    By.id("body_ctx")), pname, qname);
  }
  
  private void checkSecondSaveEntityCtrls(String lang, 
      String pname, String qname) {
    checkSavedBtns(lang, false);
    
    checkSecondSaveState(lang, driver.findElement(
                                   By.id("body_ctx")), pname, qname);
  }
  
  /**
   * Check entity controls and body after second change
   * 
   * @param lang Test language
   * @param pname Project name
   * @param qname Entity name
   */
  private void checkSecondChangeEntityCtrls(String lang, 
      String pname, String qname) {
    checkInitBtns(lang, true);
    
    checkSecondChangeState(lang, driver.findElement(
                                   By.id("body_ctx")), pname, qname);
  }
  
  private void checkThirdSaveEntityCtrls(String lang, 
      String pname, String qname) {
    checkSavedBtns(lang, true);
    
    checkThirdSaveState(lang, driver.findElement(
                                   By.id("body_ctx")), pname, qname);
  }
  
  /**
   * Check entity controls and body after third change
   * 
   * @param lang Test language
   * @param pname Project name
   * @param qname Entity name
   */
  private void checkThirdChangeEntityCtrls(String lang, 
      String pname, String qname) {
    checkCommitedChangedBtns(lang, true);
    
    checkThirdChangeState(lang, driver.findElement(
                                        By.id("body_ctx")), pname, qname);
  }
  
  public abstract void checkSecondChangeState(String lang, WebElement ctx, 
                                                  String pname, String qname);
  
  public abstract void checkSecondSaveState(String lang, WebElement ctx, 
                                                  String pname, String qname);

  public abstract void checkThirdChangeState(String lang, WebElement ctx, 
                                                  String pname, String qname);
  
  public abstract void checkThirdSaveState(String lang, WebElement ctx, 
                                                  String pname, String qname);

  public abstract void checkFirstSaveState(String lang, WebElement ctx, 
                                                  String pname, String qname);
  
  public WebElement createFirstEntity(String lang, 
                      String pname, String qname) throws InterruptedException {
    // Create first project
    createFirstProject(pname);
    
    // Create entity
    sendSimpleDialogInput(qname);
    
    // Check project controls after entity created
    checkProjectWidget(lang, pname, true);
    
    // Check entity control buttons
    return checkNewEntityCtrls(lang);
  }
  
  private void checkEntityNameSimpleDialog(String lang) {
    checkSimpleInputDialog(lang, "LL_ENTITY_NAME", "LL_CREATE", "LL_CANCEL");
  }
  
  private void checkEntityRenameSimpleDialog(String lang, String prev) {
    checkSimpleInputDialog(lang, "LL_ENTITY_NAME", 
                                        "LL_RENAME", "LL_CANCEL", prev);
  }
  
  private void checkProjectRenameSimpleDialog(String lang) {
    checkSimpleInputDialog(lang, "LL_PROJECT_NAME", "LL_RENAME", "LL_CANCEL");
  }
  
  
  private void checkEntityCommitSimpleDialog(String lang) {
    checkSimpleInputDialog(lang, "LL_COMMIT_MESSAGE", "LL_COMMIT", "LL_CANCEL");
  }
  
  private void delActiveEntity(String lang) {
    ctxMenuOpenClickByClassName("dynatree-active", 1);
    assertEquals(closeAlertAndGetItsText(true), 
        getLabelText(lang, "LL_PLEASE_CONFIRM"));
  }
  
  private void newEntityBtnClick() {
    driver.findElement(By.className("node-new-btn")).click();
  }
  
  private void testDuplicateEntityCreate(String lang, String qname) 
                                              throws InterruptedException {
    checkEntityNameSimpleDialog(lang);
    sendSimpleDialogInput(qname);
    
    // Check for alert
    checkEntityExistsAlert(lang, qname);
  }
  
  private void checkEntityExistsAlert(String lang, String qname) 
                                        throws InterruptedException {
    assertEquals(getLabelText(lang, "LL_ENTITY_NAME_EXISTS").
        replace("[fname]", "[" + qname + "]"), closeAlertAndGetItsText(true));
    cancelSimpleDialog();
  }
  private void ctxMenuOpenClickById(String id, int idx) {
    ctxMenuListClick(ctxMenuClickById(id), idx);
  }
  
  private void ctxMenuOpenClickByClassName(String cname, int idx) {
    ctxMenuListClick(ctxMenuClickByClassName(cname), idx);
  }
  
  private void ctxMenuListClick(WebElement ctx, int idx) {
    List<WebElement> menu = ctx.findElements(By.tagName("a"));
    menu.get(idx).click();
  }
  
  public void createFirstProject(String pname) throws InterruptedException {
    init(TestConstants.LANG);
    login(false);
    checkAfterSimpleLogin(TestConstants.LANG);
    sendSimpleDialogInput(pname);
    Thread.sleep(1000);
    
    checkProjectList(new String[] {pname}, pname);
  }
  
  public void checkProjectList(String[] projects, String psel) {
    Select plist = new Select(driver.findElement(By.id("project_sel")));
    
    List<WebElement> options = plist.getOptions();
    assertEquals("Number of options doesn't match expected", 
                                    projects.length, options.size());
    for (int i = 0; i < projects.length; i++) {
      WebElement opt = options.get(i);
      assertEquals(projects[i], opt.getText());
      assertEquals(projects[i], opt.getAttribute("value"));
    }
    
    // Check selected value
    assertEquals(psel, plist.getFirstSelectedOption().getText());
  }
  
  public void sendSimpleDialogInput(String in) throws InterruptedException {
    driver.findElement(By.id("sd_input")).clear();
    driver.findElement(By.id("sd_input")).sendKeys(in);
    driver.findElement(By.id("btn_ok")).click();
    Thread.sleep(1000);
  }
  
  public void deleteFirstProjectLogout(String lang) throws InterruptedException {
    driver.findElement(By.id("ll_delete")).click();
    assertEquals(getLabelText(TestConstants.LANG, "LL_PLEASE_CONFIRM"), 
                                            closeAlertAndGetItsText(true));
    Thread.sleep(2000);
    
    checkAfterSimpleLogin(lang, true);
    logout(lang, true);
  }
  
  public void dragDrop(WebElement from, WebElement to) {
    Actions builder = new Actions(driver);

    Action dragAndDrop = builder.clickAndHold(from)
       .moveToElement(to)
       .release(to)
       .build();

    dragAndDrop.perform();
  }
  
  @Override
  public void checkAfterSimpleLogin(String lang) throws InterruptedException {
    checkAfterSimpleLogin(lang, false);
  }
  
  public void checkAfterSimpleLogin(String lang, 
      boolean pvisible) throws InterruptedException {
    checkElementVisible("page_loader", false);
    checkElementVisible("login_widget", false);
    checkElementVisible("webapp_ctx", true);
    checkWebAppCtxInit();
    checkElementVisible("error_window", false);
    checkElementVisible("success_window", false);
    checkElementVisible("simple_file_dialog", false);
    
    checkSimpleInputDialog(lang, "LL_PROJECT_NAME", "LL_CREATE", "LL_LOGOUT");
  }

  @After
  public void checkWorkDirEmpty() {
    String[] files = (new File(DFU.getProjWorkDir())).list();
    assertNotNull("Invalid work directory", files);
    assertFalse("Work directory is not empty after test", files.length > 1);
    assertFalse(".git directory is not found after test", files.length == 0);
    
    if (!files[0].equals(".git"))
        fail("Directory " + files[0] + 
              " still exists after test but .git directory is not found");
  }
  
  @AfterClass
  public static void restoreWorkDir() throws IOException, URISyntaxException {
    // Copy test files back
    DFU.copyDemoProj(DFU.getProjWorkDir());
  }
  
  @Override
  public String getInitLogoutBtnName() {
    return "btn_cancel";
  }
  
  public void checkSimpleInputDialog(String lang, String lblTitle,
                                            String btnOk, String btnCancel) {
    checkSimpleInputDialog(lang, lblTitle, btnOk, btnCancel, "");
  }
  
  public void checkSimpleInputDialog(String lang, String lblTitle,
                             String btnOk, String btnCancel, String value) {
    checkElementVisible("simple_dialog", true);
    checkElementVisible("sd_sel", false);
    checkLangElementById(lang, "sd_label", lblTitle);
    checkLangElementById(lang, "btn_ok", btnOk);
    checkLangElementById(lang, "btn_cancel", btnCancel);
    
    assertEquals(value, driver.findElement(By.id("sd_input")).
                                                getAttribute("value"));
  }
  
  private void checkProjectJustCreated(String lang, String pname) 
                                              throws InterruptedException {
    checkEntityNameSimpleDialog(lang);
    cancelSimpleDialog();
    
    checkItemContextMenuById(lang, "proj_tree_wrapper", 
                                  getProjectContextMenu(lang));

    checkProjectWidget(lang, pname, false);
  }
  
  private void cancelSimpleDialog() throws InterruptedException {
    driver.findElement(By.id("btn_cancel")).click();
    Thread.sleep(1000);
  }
  
  /**
   * Check project widget with/without first entity created
   * @param lang Test language
   * @param pname Project name
   * @param fent True when first entity created
   */
  private void checkProjectWidget(String lang, String pname, boolean fent) {
    checkLangElementById(lang, "LL_SELECT_PROJECT");
    
    WebElement psel = driver.findElement(By.id("project_sel"));
    assertEquals(pname, psel.getAttribute("value"));
    
    List<WebElement> ops = psel.findElements(By.tagName("option"));
    assertEquals(1, ops.size());
    assertEquals(pname, ops.get(0).getText());
    
    checkLangElementById(lang, "LL_NEW");
    checkLangElementById(lang, "LL_RENAME");
    checkLangElementById(lang, "LL_DELETE");
    checkLangElementById(lang, "LL_GIT_PUSH");
    checkLangElementById(lang, "LL_LOGOUT");

    // Look for new button
    WebElement btn = driver.findElement(By.className("node-new-btn"));
    assertNotNull("New Button is not found", btn);
    WebElement img = btn.findElement(By.tagName("img"));
    assertNotNull(img);
    assertEquals("http://localhost:8090/web_test/fr/" +
    		      "modules/prj/images/new_entity.png", img.getAttribute("src"));
    
    WebElement bname = btn.findElement(By.tagName("a"));
    assertNotNull(bname);
    assertEquals(getLabelText(lang, "LL_NEW"), bname.getText());
    assertEquals("pointer", bname.getCssValue("cursor"));
    
    // Check list of component groups and icons
    checkComponentList(lang, fent);
    
    // Check project controls disabled
    checkElementVisible("ws_list", fent);
    checkElementVisible("entity_ctx_ctrl", fent);
    
    if (fent) {
      checkDataSourceList(lang);
      checkDbConnList(lang);
    } 
  }
  
  public void checkDataSourceList(String lang) {
    checkLangElementById(lang, "LL_DATA_SOURCE");
    checkSelList(driver.findElement(By.id("ds_sel")), 
                                    PrjMgrTestConstants.DS_LIST);
  }
  
  public void checkDbConnList(String lang) {
    checkLangElementById(lang, "LL_DB_CONNECTION");
    checkSelList(driver.findElement(By.id("conn_sel")), 
                                    PrjMgrTestConstants.DB_LIST);
  }
  
  private String[][] getProjectContextMenu(String lang) {
    String[][] mlist = new String[][] {
      new String[] {"LL_CREATE_ENTITY", "modules/prj/images/new_entity.png"},
      new String[] {"LL_UPLOAD_ENTITY", "modules/prj/images/upload_entity.png"}
    };
    
    return mlist;
  }
  
  protected String[][] getEntityContextMenu() {  
    return PrjMgrTestConstants.CTX_MENU;
  }
  
  private void checkCtxMenuNotVisible() {
    // Check context menu is not visible
    try {
      driver.findElement(By.className("contextMenuPlugin"));
      fail("Context menu is yet visible");
    } catch (NoSuchElementException e) {}
  }
  
  private void checkItemContextMenuById(String lang, 
                    String id, String[][] mlist) throws InterruptedException {
    checkCtxMenuNotVisible();
    
    // Right click on new button to check context menu
    WebElement menu = ctxMenuClickById(id);
    assertNotNull(menu);

    checkContextMenu(lang, menu, mlist);
  }
  
  private void checkItemContextMenuByClassName(String lang, 
                 String cname, String[][] mlist) throws InterruptedException {
    checkCtxMenuNotVisible();
    
    // Right click on new button to check context menu
    WebElement menu = ctxMenuClickByClassName(cname);

    checkContextMenu(lang, menu, mlist);
  }

  private void checkContextMenu(String lang, 
              WebElement menu, String[][] mlist) throws InterruptedException {
    
    List<WebElement> menus = menu.findElements(By.tagName("li"));
    assertEquals(mlist.length, menus.size());
    
    // Check each menu item
    for (int i = 0; i < mlist.length; i++) {
      String[] mitem = mlist[i];
      
      WebElement mi = menus.get(i);
      if (mitem == null)
        // Check for divider
        assertEquals("divider", mi.getAttribute("class"));
      else
        // Check for menu item
        checkContextMenuItem(lang, mi, 
          TestConstants.GUI_SRV_URL_BASE + mitem[1], mitem[0]);
    }
    
    // Cancel context menu
    driver.findElement(By.className("contextMenuPluginCtrl")).click();
    
    // Check menu disappear
    try {
      driver.findElement(By.className("contextMenuPlugin"));
      Thread.sleep(2000);
      fail("Context menu is still visible");
    } catch (NoSuchElementException e) {}
    
    Thread.sleep(500);
  }

  /**
   * Find web element by id, right click and 
   *                          return reference on context menu
   * 
   * @param id Element Id
   * @return reference on context menu
   */  
  public WebElement ctxMenuClickById(String id) {
    WebElement el = driver.findElement(By.id(id));
    assertNotNull(el);
    return ctxMenuClick(el);
  }
  
  /**
   * Find web element by class name, right click and 
   *                          return reference on context menu
   * 
   * @param cname Class Name
   * @return reference on context menu
   */
  public WebElement ctxMenuClickByClassName(String cname) {
    WebElement el = driver.findElement(By.className(cname));
    assertNotNull(el);
    return ctxMenuClick(el);
  }
  
  /**
   * Right click on web element and return reference on context menu
   * 
   * @param el WebElement
   * @return reference on context menu
   */
  public WebElement ctxMenuClick(WebElement el) {
    Actions action= new Actions(driver);
    action.contextClick(el).build().perform();
    WebElement ctx = driver.findElement(By.className("contextMenuPlugin"));
    assertNotNull(ctx);
    
    return ctx;
  }
  
  public void checkSelList(WebElement sel, String[][] list) {
    assertNotNull(sel);
    List<WebElement> ops = sel.findElements(By.tagName("option"));
    assertNotNull(ops);
    assertEquals(list.length, ops.size());
    for (int i = 0; i < list.length; i++) {
      WebElement op = ops.get(i);
      String[] el = list[i];
      
      assertEquals(el[0], op.getText());
      assertEquals(el[1], op.getAttribute("value"));
    }
  }
  
  public void checkContextMenuItem(String lang, 
                            WebElement menu, String src, String label) {
    WebElement img = menu.findElement(By.tagName("img"));
    assertNotNull(img);
    assertEquals(src, img.getAttribute("src"));
    WebElement name = menu.findElement(By.tagName("span"));
    assertNotNull(name);
    assertEquals(getLabelText(lang, label), name.getText());
  }
  
  private void checkWebAppCtxInit() {
    checkInitProjectWidget();
  }
  
  private void checkInitProjectWidget() {
    checkElementVisible("project_widget", true);
    checkElementVisible("change_log_wrapper", false);
    checkElementVisible("req_param_values_wrapper", false);
  }
  
  public void checkComponentList(String lang, boolean visible) {
    HashMap<String, ArrayList<String>> clist = getComponentList();
    // Count total number of elements
    int clen = 0;
    for (ArrayList<String> ar: clist.values())
      clen += ar.size() + 1;
    
    WebElement icons = driver.findElement(By.id("icon_list"));
    checkElementVisible(icons, "icon_list", visible);
    
    List<WebElement> components = icons.findElements(By.cssSelector("h4,img"));
    assertNotNull(components);
    
    assertEquals(clen, components.size());
    
    int idx = 0;
    for (String cname: clist.keySet()) {
      // Invisible element doesn't return text
      if (visible)
        assertEquals(getLabelText(lang, cname), components.get(idx).getText());
      idx++;
      
      for (String src: clist.get(cname)) {
        assertEquals(TestConstants.GUI_SRV_URL_BASE + src, 
                          components.get(idx).getAttribute("src"));
        idx++;
      }
    }
  }

  public abstract HashMap<String, ArrayList<String>> getComponentList();
}
