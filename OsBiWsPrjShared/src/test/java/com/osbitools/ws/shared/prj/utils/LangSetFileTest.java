/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-09
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.utils;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.CustErrorList;

/**
 * Test lang_set file
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class LangSetFileTest {

  // Pointer on LangUtils
  private static LangSetUtils _utils;
  
  // JSON template for demo ll_set
  public static final String LL_SET_JSON = "{\"lang_list\":\"en,fr,ru\"," +
                      "\"ver_max\":1,\"ver_min\":0,\"lang_label\":[" +
    "{\"id\":\"LL_USERNAME\",\"ll_def\":[" +
      "{\"lang\":\"en\",\"value\":\"Username\"}," +
      "{\"lang\":\"fr\",\"value\":\"Nom d'utilisateur\"}," +
      "{\"lang\":\"ru\",\"value\":\"Имя\"}]}," +
    "{\"id\":\"LL_PASSWORD\",\"ll_def\":[" +
      "{\"lang\":\"en\",\"value\":\"Password\"}," +
      "{\"lang\":\"fr\",\"value\":\"Mot de passe\"}," +
      "{\"lang\":\"ru\",\"value\":\"Пароль\"}]}" +
  "]}";
  
  public static final String LL_SET_TEST_JSON = "{\"lang_list\":\"en,fr,ru\"," +
                      "\"ver_max\":1,\"ver_min\":0,\"lang_label\":[" +
    "{\"id\":\"LL_LETS_GO\",\"ll_def\":[" +
      "{\"lang\":\"en\",\"value\":\"Let's go\"}," +
      "{\"lang\":\"fr\",\"value\":\"Allons-y\"}," +
      "{\"lang\":\"ru\",\"value\":\"Поехали\"}]}" +
  "]}";
  
  @BeforeClass
  public static void instUtils() throws Exception {
    _utils = new LangSetUtils();
    Class.forName(CustErrorList.class.getName());
  }
  
  @Test
  public void test() throws IOException, WsSrvException {
    testLangSetFile(LsTestConstants.LANG_SET_RES_PATH, 
                          LsConstants.LANG_SET_FILE, LL_SET_JSON);
    testLangSetFile(LsTestConstants.LANG_SET_RES_PATH + "_test", 
        LsConstants.LANG_SET_FILE + "_test", LL_SET_TEST_JSON);
  }

  // Read lang_set file from Jar File & convert into JSON
  private void testLangSetFile(String path, String fname, String expected) 
                                    throws WsSrvException, IOException {
    assertEquals(expected, _utils.getJson(
        JarUtils.readJarFileAsStream(path), fname, true));
  }
}
