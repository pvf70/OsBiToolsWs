/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-03-15
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

import com.osbitools.ws.shared.prj.PrjMgrTestConstants;

/**
 * Generic utilities read demo maps resources out of jar file
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class PrjTextDemoFileUtils extends BasicTextDemoFileUtils {
  
  private static String COMP_LIST = "{\"comp_list\":" +
  		                      "\"{\\\"ll_demo_components\\\":[\\\"demo\\\"]}\"}";
  
  private static String EX_DAT_FILE_LIST = 
              "f1.int,f1.num,f5.int,f9.num,t1_eng.txt,t1_ru.txt";
  
  private static String[][] TEST_EX_FILES = new String[][] {
    new String[] {"f1.int",
        "@\\{\"file_info\":\\{" +
            "\"size\":6," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\\d*" +
          "\\}" +
        "\\}@"
    },
    new String[] {"f1.num",
        "@\\{\"file_info\":\\{" +
            "\"size\":10," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\\d*" +
          "\\}" +
        "\\}@"
    },
    new String[] {"f5.int",
        "@\\{\"file_info\":\\{" +
            "\"size\":3," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\\d*" +
          "\\}" +
        "\\}@"
    },
    new String[] {"f9.num",
        "@\\{\"file_info\":\\{" +
            "\"size\":10," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\\d*" +
          "\\}" +
        "\\}@"
    },
    new String[] {"t1_eng.txt",
        "@\\{\"file_info\":\\{" +
            "\"size\":50," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\\d*" +
          "\\}" +
        "\\}@"
    },
    new String[] {"t1_ru.txt",
        "@\\{\"file_info\":\\{" +
            "\"size\":78," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\\d*" +
          "\\}" +
        "\\}@"
    }
  };
  
  private static TestFileItem[] TEST_NUM_SET = new TestFileItem[] {
    new TestFileItem("11.11".getBytes(), ""),
    new TestFileItem("22.22".getBytes(), ""),
    new TestFileItem("33.33".getBytes(), ""),
    new TestFileItem("44.44".getBytes(), "")
  };
  
  private static TestFileItem[] TEST_INT_SET = new TestFileItem[] {
    new TestFileItem("11".getBytes(), ""),
    new TestFileItem("22".getBytes(), ""),
    new TestFileItem("33".getBytes(), ""),
    new TestFileItem("44".getBytes(), "")
  };
  
  private static TestFileItem[] TEST_TXT_SET = new TestFileItem[] {
    new TestFileItem("ABCD abcd АБВГД абвгд".
                                getBytes(StandardCharsets.UTF_8), ""),
    new TestFileItem("QWERTY qwerty ЙЦУКЕН йцукен".
                                getBytes(StandardCharsets.UTF_8), ""),
    new TestFileItem("ASDFG asdfg ФЫВАП фывап".
                                getBytes(StandardCharsets.UTF_8), ""),
    new TestFileItem("ZXCVB zxcvb ЯЧСМИ ячсми".
                                getBytes(StandardCharsets.UTF_8), "")
  };
  
  private static final HashMap<String, TestFileItem[]> TEST_SET = 
                                      new HashMap<String, TestFileItem[]>();
  
  static {
    TEST_SET.put("int", TEST_INT_SET);
    TEST_SET.put("num", TEST_NUM_SET);
    TEST_SET.put("txt", TEST_TXT_SET);
  }
  
  // Demo files
  private static final HashMap<String, String> DEMO_EXT_FILES = 
                                              new HashMap<String, String>();
  
  static {
    DEMO_EXT_FILES.put("int", "11\n22\n33\n");
    DEMO_EXT_FILES.put("num", "11.11\n22.22\n33.33\n");
    DEMO_EXT_FILES.put("txt", "en\tDemo File\nru\tДемонстрационный Файл\n");
  }
  
  @Override
  public String getCompList() {
    return COMP_LIST;
  }

  @Override
  public String getProjSrcDir() {
    return PrjMgrTestConstants.SRC_PRJ_DIR;
  }

  @Override
  public String getProjWorkDir() {
    return PrjMgrTestConstants.WORK_PRJ_DIR;
  }
  
  @Override
  public String getBaseExt() {
    return TestEntityUtils.BASE_EXT;
  }

  @Override
  public String getExtFileList(String name) {
    return EX_DAT_FILE_LIST;
  }

  @Override
  public String[][] getExtFileTestFiles(String resName) {
    return TEST_EX_FILES;
  }

  @Override
  public TestFileItem[] getExtFileTestSet(String resName, String fileExt) {
    return TEST_SET.get(fileExt);
  }

  @Override
  public HashMap<String, String>[] getExtFileTestSetParams(String resName) {
    return null;
  }

  @Override
  public String[] getExtList(String resName) {
    return TEST_SET.keySet().toArray(new String[TEST_SET.size()]);
  }

  @Override
  public String[] getResDirList() {
    Set<String> set = TestEntityUtils.EXT_LIST.keySet();
    return set.toArray(new String[set.size()]);
  }

  @Override
  public byte[] getDemoExFileAsBytes(String resName, String ext) {
    return DEMO_EXT_FILES.get(ext).getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public String getDemoExFileUploadResp(String resName, String ext) {
    return "{}";
  }
  
}
