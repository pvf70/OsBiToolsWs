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

package com.osbitools.ws.me.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

import com.osbitools.ws.me.shared.LocalTestConstants;
import com.osbitools.ws.shared.DsMapTestResConfig;
import com.osbitools.ws.shared.DsTestConstants;
import com.osbitools.ws.shared.prj.utils.BasicJarDemoFileUtils;
import com.osbitools.ws.shared.prj.utils.TestFileItem;

/**
 * Generic utilities read demo maps resources out of jar file
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */

public class MeTestFileUtils extends BasicJarDemoFileUtils {

  public MeTestFileUtils() {
    super(new DsMapTestResConfig());
  }

  public static String CSV_FILE_LIST;
  
  //List of test csv files in test project
  public static String[][] TEST_CSV_FILES = new String[][] {
    new String[] {"filter_complex.csv",
       "@\\{\"file_info\":\\{" +
           "\"size\":3604," +
           "\"read\":true," +
           "\"write\":true," +
           "\"last_modified\":\\d*" +
         "\\}" +
       "\\}@"
    },
    new String[] {"filter_complex_no_header.csv",
       "@\\{\"file_info\":\\{" +
           "\"size\":3583," +
           "\"read\":true," +
           "\"write\":true," +
           "\"last_modified\":\\d*" +
         "\\}" +
       "\\}@"
    },
    new String[] {"sort1.csv",
       "@\\{\"file_info\":\\{" +
           "\"size\":760," +
           "\"read\":true," +
           "\"write\":true," +
           "\"last_modified\":\\d*" +
         "\\}" +
       "\\}@"
    },
    new String[] {"sort2.csv",
       "@\\{\"file_info\":\\{" +
           "\"size\":531," +
           "\"read\":true," +
           "\"write\":true," +
           "\"last_modified\":\\d*" +
         "\\}" +
       "\\}@"
    }
  };

  static {
    String flist = "";
    for (String[] fs: TEST_CSV_FILES)
      flist += "," + fs[0];
    
    CSV_FILE_LIST = flist.substring(1);
  }
  
  //Create test params
  static HashMap <String, String> params1 = new HashMap<String, String>();
  static HashMap <String, String> params2 = new HashMap<String, String>();
  static HashMap <String, String> params3 = new HashMap<String, String>();
  
  static {
    params1.put("delim", ";");
    params1.put("quote_char", "\"");
    params1.put("escape_char", "\\");
    params2.put("col_first_row", "false");
  }
  
  @SuppressWarnings("unchecked")
  static HashMap <String, String>[] TEST_CSV_FILES_PARAMS = 
                        new  HashMap[] {params1, params1, params2, params3};
  
  @SuppressWarnings("unchecked")
  public static HashMap<String, String>[] TEST_CSV_COL_PARAMS = 
    new  HashMap[] {params3, params2, params3, params3};
  
  public static String CSV1_TEST = "COL_1;COL_2;COL_3;COL_4;COL_5\n1;2;3;4;5";
  public static String JSON_CSV1 = "[" +
    "{\"name\":\"COL_1\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COL_2\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COL_3\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COL_4\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COL_5\",\"java_type\":\"java.lang.String\"}" +
  "]";
  
  public static String CSV2_TEST = "COLA;COLB;COLC;COLD;COLE" +
                                                  "\nA;B;C;D;E\nА;Б;В;Г;Д";
  public static String JSON_CSV2 = "[" +
    "{\"name\":\"COLA\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COLB\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COLC\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COLD\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COLE\",\"java_type\":\"java.lang.String\"}" +
  "]";

  public static String CSV3_TEST = "А,Б,В,Г,Д\nA,B,C,D,E";
  
  public static String JSON_CSV3 = "[" +
    "{\"name\":\"COL1\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COL2\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COL3\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COL4\",\"java_type\":\"java.lang.String\"}," +
    "{\"name\":\"COL5\",\"java_type\":\"java.lang.String\"}" +
  "]";
  
  public static String JSON_CSV31 = "[" +
      "{\"name\":\"А\",\"java_type\":\"java.lang.String\"}," +
      "{\"name\":\"Б\",\"java_type\":\"java.lang.String\"}," +
      "{\"name\":\"В\",\"java_type\":\"java.lang.String\"}," +
      "{\"name\":\"Г\",\"java_type\":\"java.lang.String\"}," +
      "{\"name\":\"Д\",\"java_type\":\"java.lang.String\"}" +
    "]";
  
  public static TestFileItem[] TEST_CSV_SET = new TestFileItem[] {
    new TestFileItem(CSV1_TEST.getBytes(StandardCharsets.UTF_8), JSON_CSV1),
    new TestFileItem(CSV2_TEST.getBytes(StandardCharsets.UTF_8), JSON_CSV2),
    new TestFileItem(CSV3_TEST.getBytes(StandardCharsets.UTF_8), JSON_CSV3),
    new TestFileItem(CSV3_TEST.getBytes(StandardCharsets.UTF_8), JSON_CSV31)
  };
  
  public static String DEMO_CSV = "LANG,COLA,COLB,COLC,COLD,COLE" +
      "\nen,A,B,C,D,E\nru,А,Б,В,Г,Д\n";
  
  public static String DEMO_CSV_COLUMNS = "[" +
  		"{\"name\":\"LANG\",\"java_type\":\"java.lang.String\"}," +
  		"{\"name\":\"COLA\",\"java_type\":\"java.lang.String\"}," +
  		"{\"name\":\"COLB\",\"java_type\":\"java.lang.String\"}," +
  		"{\"name\":\"COLC\",\"java_type\":\"java.lang.String\"}," +
  		"{\"name\":\"COLD\",\"java_type\":\"java.lang.String\"}," +
  		"{\"name\":\"COLE\",\"java_type\":\"java.lang.String\"}]";
  
  private static final String MAIN_DEMO_DS = "{\"type\":\"DataSetDescr\"," +
      "\"descr\":\"Test DataSet\"," +
      "\"enabled\":true,\"ver_max\":1,\"ver_min\":0,\"lang_map\":{" +
      "\"column\":[{\"name\":\"COL1\"},{\"name\":\"COL2\"}]}," +
      "\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"A11\"},{" +
      "\"name\":\"B22\"}]},\"calc\":{\"column\":[{\"name\":\"CALC1\"," +
      "\"java_type\":\"java.lang.Integer\",\"value\":\"A + B\"},{" +
      "\"name\":\"CALC2\",\"java_type\":\"java.lang.String\"," +
      "\"value\":\"C + D\"}]}},\"sort_by_grp\":{\"sort_by\":[{\"idx\":1," +
      "\"column\":\"COL1\",\"sequence\":\"asc\",\"unique\":false},{" +
      "\"idx\":2,\"column\":\"COL2\",\"sequence\":\"desc\"," +
      "\"unique\":false}]},\"filter\":\"A < B\",\"group_data\":{" +
      "\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":" +
      "\"java.lang.String\",\"on_error\":\"\"}," +
      "{\"name\":\"COL2\"," +
      "\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{" +
      "\"name\":\"A11\",\"java_type\":\"java.lang.Integer\"," +
      "\"on_error\":\"\"},{\"name\":\"B22\",\"java_type\":" +
      "\"java.lang.Integer\",\"on_error\":\"\"},{\"name\":\"CALC1\"," +
      "\"java_type\":\"java.lang.Integer\",\"on_error\":\"\"},{" +
      "\"name\":\"CALC2\",\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"\"}]},\"ds_list\":{\"group_ds\":[{" +
      "\"group_data\":{\"ds_list\":{\"group_ds\":[{\"sort_by_grp\":{" +
      "\"sort_by\":[{\"idx\":1,\"column\":\"COL1\"}]}," +
      "\"group_data\":{\"ds_list\":{\"static_ds\":[{\"static_data\":{" +
      "\"columns\":{\"column\":[{\"name\":\"COL1\"," +
      "\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"ERROR GRP 1-1-1 !!!\"},{\"name\":\"COL2\"," +
      "\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"ERROR GRP 1-1-1 !!!\"}]},\"static_rows\":{\"row\":[{" +
      "\"cell\":[{\"name\":\"COL1\",\"value\":\"UUu\"},{\"name\":\"COL2\"," +
      "\"value\":\"ЦцЦ\"}]},{\"cell\":[{\"name\":\"COL1\"," +
      "\"value\":\"dDd\"},{\"name\":\"COL2\",\"value\":\"ЗЗз\"}]}]}}},{" +
      "\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\"," +
      "\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"ERROR GRP 1-1-2 !!!\"},{\"name\":\"COL2\"," +
      "\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"ERROR GRP 1-1-2 !!!\"}]},\"static_rows\":{\"row\":[{" +
      "\"cell\":[{\"name\":\"COL1\",\"value\":\"uuU\"},{\"name\":\"COL2\"," +
      "\"value\":\"Ццц\"}]},{\"cell\":[{\"name\":\"COL1\"," +
      "\"value\":\"Ddd\"},{\"name\":\"COL2\",\"value\":\"ЗЗз\"}]}]}}}]}}}]," +
      "\"static_ds\":[{\"static_data\":{\"columns\":{\"column\":[{" +
      "\"name\":\"COL1\",\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"ERROR GRP 1-2-1 !!!\"},{\"name\":\"COL2\"," +
      "\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"ERROR GRP 1-2-1 !!!\"}]},\"static_rows\":{\"row\":[{" +
      "\"cell\":[{\"name\":\"COL1\",\"value\":\"uUu\"},{\"name\":\"COL2\"," +
      "\"value\":\"цЦц\"}]},{\"cell\":[{\"name\":\"COL1\"," +
      "\"value\":\"Ddd\"},{\"name\":\"COL2\",\"value\":\"ЗзЗ\"}]}]}}}]," +
      "\"csv_ds\":[{\"csv_data\":{" +
      "\"file_name\":\"filter_complex_no_header.csv\"," +
      "\"col_first_row\":false,\"columns\":{\"column\":[{\"name\":\"COL1\"," +
      "\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{" +
      "\"name\":\"COL2\",\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"\"}]}}}],\"sql_ds\":[{\"sql_data\":{\"sql\":{" +
      "\"descr\":\"Simple Select\"," +
      "\"sql_text\":\"SELECT * FROM TEST_DATA\"}}}]}}}]," +
      "\"static_ds\":[{\"lang_map\":{\"column\":[{\"name\":\"COL1\"},{" +
      "\"name\":\"COL2\"}]},\"static_data\":{\"columns\":{\"column\":[{" +
      "\"name\":\"COL1\",\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"ERROR GRP 2 !!!\"},{\"name\":\"COL2\"," +
      "\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"ERROR GRP 2 !!!\"}]},\"static_rows\":{\"row\":[{" +
      "\"cell\":[{\"name\":\"COL1\",\"value\":\"bBb\"},{\"name\":\"COL2\"," +
      "\"value\":\"УуУ\"}]},{\"cell\":[{\"name\":\"COL1\"," +
      "\"value\":\"AaA\"},{\"name\":\"COL2\",\"value\":\"пПп\"}]}]}}}]}}}";
    
  static public String[][] DS_TEST_MAPS = new String[][] {
    
    new String[] {"ds", MAIN_DEMO_DS},
    
    new String[] {
       "ds_single", 
    
      "{\"type\":\"DataSetDescr\",\"descr\":" +
      "\"Test DataSet\",\"enabled\":true,\"ver_max\":1,\"ver_min\":0," +
      "\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"A11\"}," +
      "{\"name\":\"B22\"}]},\"calc\":{\"column\":[{\"name\":\"CALC1\"," +
      "\"java_type\":\"Integer\",\"value\":\"A + B\"}," +
      "{\"name\":\"CALC2\",\"java_type\":\"String\"," +
      "\"value\":\"C + D\"}]}},\"sort_by_grp\":{\"sort_by\":[{\"idx\":1," +
      "\"column\":\"COL1\",\"sequence\":\"asc\",\"unique\":false}," +
      "{\"idx\":2,\"column\":\"COL2\",\"sequence\":\"desc\"," +
      "\"unique\":false}]},\"filter\":\"A < B\",\"static_data\":" +
      "{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":" +
      "\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL2\"," +
      "\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]}," +
      "\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\"," +
      "\"value\":\"UUu\"},{\"name\":\"COL2\",\"value\":\"ЦцЦ\"}]}," +
      "{\"cell\":[{\"name\":\"COL1\",\"value\":\"dDd\"},{\"name\":" +
      "\"COL2\",\"value\":\"ЗЗз\"}]}]}}}"
    },
    
    new String[] {
      "ds_empty",
      
      "{\"type\":\"DataSetDescr\",\"descr\":\"Empty DataSet\"," +
      "\"enabled\":false,\"ver_max\":1,\"ver_min\":0,\"xml_data\":{}}"
    }
  };
    
  static public String[][] DS_MAPS = new String[][] {
    new String[] {
      "bad.csv_bad",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"csv_data\":" +
      "{\"file_name\":\"test.csv\",\"columns\":{\"column\":" +
      "[{\"name\":\"COL1\",\"java_type\":\"java.lang.Long\"," +
      "\"on_error\":\"0\"},{\"name\":\"COL2\",\"java_type\":" +
      "\"java.lang.String\",\"on_error\":\"ERROR !!!\"},{\"name\":\"COL3\"," +
      "\"java_type\":\"java.util.Date\",\"on_error\":\"1/1/0\"}," +
      "{\"name\":\"COL4\",\"java_type\":\"java.lang.Integer\"," +
      "\"on_error\":\"0\"},{\"name\":\"COL5\",\"java_type\":" +
      "\"java.lang.Double\",\"on_error\":\"0\"},{\"name\":\"COL6\"," +
      "\"java_type\":\"java.lang.Float\",\"on_error\":\"0\"}]}}}"
    },
    
    new String[] {
      "bad.static_test_calc_bad1",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0," +
      "\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"COL2\"}]}," +
      "\"calc\":{\"column\":[{\"name\":\"COL3\",\"java_type\":" +
      "\"java.lang.Integer\",\"stop_on_error\":true,\"error_value\":\"-1\"," +
      "\"value\":\"@{COL1}/@{COL2}\"}]}},\"static_data\":{\"columns\":" +
      "{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.Integer\"," +
      "\"on_error\":\"99\"}]},\"static_rows\":{\"row\":[{\"cell\":" +
      "[{\"name\":\"COL1\",\"value\":\"1\"}]}]}}}"
    },
    
    new String[] {
        
      "bad.static_test_calc_bad2",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"ex_columns\":" +
      "{\"calc\":{\"column\":[{\"name\":\"COL3\",\"java_type\":\"xxx\"," +
      "\"value\":\"\"}]}},\"static_data\":{\"columns\":{\"column\":" +
      "[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":" +
      "\"\"},{\"name\":\"COL2\",\"java_type\":\"java.lang.String\"," +
      "\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":" +
      "\"COL1\",\"value\":\"1\"},{\"name\":\"COL2\",\"value\":\"1\"}]}]}}}"
    },
    
    new String[] {
      "bad.static_test_calc_bad3",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"ex_columns\":" +
      "{\"calc\":{\"column\":[{\"name\":\"COL3\",\"java_type\":" +
      "\"java.lang.Integer\",\"value\":\"@{COL1}*@{COL1} + " +
      "\\n                \\t\\t\\t\\t\\t\\t@{COL2}*@{COL2} \"}]}}," +
      "\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\"," +
      "\"java_type\":\"java.lang.String\",\"on_error\":\"ERROR !!!\"}," +
      "{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":" +
      "\"ERROR !!!\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":" +
      "\"COL1\",\"value\":\"1\"},{\"name\":\"COL2\",\"value\":\"1\"}]}]}}}"
    },
    
    new String[] {      
      "test.csv_filter_complex",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0," +
      "\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CDATE\"," +
      "\"sequence\":\"asc\"},{\"idx\":2,\"column\":\"CNUM\"," +
      "\"sequence\":\"desc\",\"str_sort\":\"natural\"}]},\"filter\":" +
      "\"(new java.util.Date(\\\"01/01/2007\\\")).before(" +
      "@{CDATE}) &&\\n          @{CNUM} > 4d &&\\n          " +
      "(new java.util.Date(\\\"12/31/2007\\\")).after(@{CDATE}) " +
      "&&\\n          @{CNUM} < 6d\",\"csv_data\":{\"file_name\":" +
      "\"filter_complex.csv\",\"columns\":{\"column\":[{\"name\":\"CDATE\"," +
      "\"java_type\":\"java.util.Date\",\"on_error\":\"\"}," +
      "{\"name\":\"CNUM\",\"java_type\":\"java.lang.Double\"," +
      "\"on_error\":\"\"}]}}}"
    },
          
    new String[] {      
      "test.csv_filter1",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0," +
      "\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CNUM\"," +
      "\"sequence\":\"asc\"},{\"idx\":2,\"column\":\"CSTR\"," +
      "\"str_sort\":\"collator\"}]},\"filter\":\"(Integer.parseInt(@{CNUM})" +
      " > 2) && \\n        \\t\\t\\t(Integer.parseInt(@{CNUM}) < 5)\"," +
      "\"csv_data\":{\"file_name\":\"sort1.csv\"}}"
    },
          
    new String[] {      
  
      "test.csv_filter_complex_no_header",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL4\",\"sequence\":\"asc\"},{\"idx\":2,\"column\":\"COL3\",\"str_sort\":\"natural\"}]},\"filter\":\"(new java.util.Date(\\\"01/01/2007\\\")).before(@{COL4}) &&\\n          @{COL3} > 4d &&\\n          (new java.util.Date(\\\"12/31/2007\\\")).after(@{COL4}) &&\\n          @{COL3} < 6d\",\"csv_data\":{\"file_name\":\"filter_complex_no_header.csv\",\"col_first_row\":false,\"columns\":{\"column\":[{\"name\":\"COL4\",\"java_type\":\"java.util.Date\",\"on_error\":\"\"},{\"name\":\"COL3\",\"java_type\":\"java.lang.Double\",\"on_error\":\"\"}]}}}"
    },
    
    new String[] {      
        "test.csv_filter_params",
        
        "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0," +
        "\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CDATE\"," +
        "\"sequence\":\"asc\"},{\"idx\":2,\"column\":\"CSTR\"," +
        "\"str_sort\":\"natural\"}]},\"filter\":\"${date_from}." +
        "before(@{CDATE}) &&\\n          @{CNUM} > ${num_from} &&\\n     " +
        "     ${date_to}.after(@{CDATE}) &&\\n          @{CNUM} < ${num_to}\\n" +
        "    \",\"req_params\":{\"param\":[{\"name\":\"date_from\"," +
        "\"java_type\":\"java.util.Date\"},{\"name\":\"date_to\"," +
        "\"java_type\":\"java.util.Date\"},{\"name\":\"num_from\"," +
        "\"java_type\":\"java.lang.Double\"},{\"name\":\"num_to\"," +
        "\"java_type\":\"java.lang.Double\"}]},\"csv_data\":{\"file_name\":" +
        "\"filter_complex.csv\",\"columns\":{\"column\":[{\"name\":\"CDATE\"," +
        "\"java_type\":\"java.util.Date\",\"on_error\":\"\"}," +
        "{\"name\":\"CNUM\",\"java_type\":\"java.lang.Double\"," +
        "\"on_error\":\"\"}]}}}"
      },
      
      new String[] {      
          "test.csv_sort1",
          
          "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0," +
          "\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CSTR\"," +
          "\"str_sort\":\"natural\"}]},\"csv_data\":{\"file_name\":\"sort1.csv\"}}"
      },
            
      new String[] {      
        "test.csv_sort2",
        
        "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0," +
        "\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CNUM\"," +
        "\"sequence\":\"desc\"},{\"idx\":2,\"column\":\"CSTR\"}]}," +
        "\"csv_data\":{\"file_name\":\"sort1.csv\"}}"
      },
        
      new String[] {      
        "test.csv_sort3",
        
        "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0," +
        "\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CSTR\"," +
        "\"unique\":true}]},\"csv_data\":{\"file_name\":\"sort2.csv\"}}"
      },
              
      new String[] {      
        "test.csv_str1",
        
        "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0," +
        "\"csv_data\":{\"file_name\":\"sort1.csv\"}}"
      },
            
      new String[] {      
        "test.group_mixed",
        
        "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0," +
        "\"group_data\":{\"columns\":{\"column\":[" +
          "{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\"," +
                                                "\"on_error\":\"\"}," +
          "{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\"," +
                                              "\"on_error\":\"\"}" +
        "]}," +
        "\"ds_list\":{" +
          "\"csv_ds\":[" +
            "{\"idx\":0,\"csv_data\":{\"file_name\":\"sort1.csv\"," +
              "\"columns\":{\"column\":[" +
                "{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\"," +
                                                    "\"on_error\":\"\"}," +
                "{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\"," +
                                                    "\"on_error\":\"\"}]}}}," +
            "{\"idx\":2,\"csv_data\":{\"file_name\":\"sort1.csv\"," +
              "\"columns\":{\"column\":[" +
                "{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\"," +
                                                    "\"on_error\":\"\"}," +
                "{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\"," +
                                                    "\"on_error\":\"\"}]}}}]," +
          "\"static_ds\":[" +
            "{\"idx\":1,\"static_data\":{\"columns\":{\"column\":[" +
              "{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\"," +
                  "\"on_error\":\"\"}," +
              "{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\"," +
                  "\"on_error\":\"\"}]}," +
              "\"static_rows\":{\"row\":[{\"cell\":[" +
                "{\"name\":\"CSTR\",\"value\":\"_UUu\"}," +
                "{\"name\":\"CNUM\",\"value\":\"222\"}]}," +
              "{\"cell\":[" +
                "{\"name\":\"CSTR\",\"value\":\"_dDd\"}," +
                "{\"name\":\"CNUM\",\"value\":\"333\"}]}" +
          "]}}}]}}}"
    },
      
    new String[] {      
      
      "test.sql_filter_complex",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0," +
      "\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CDATE\"," +
      "\"sequence\":\"asc\"}]},\"filter\":\"import java.sql.Date;" +
      "\\n        ${date_from}.before(@{CDATE}) &&\\n          @{CNUM} > " +
      "${num_from} &&\\n          ${date_to}.after(@{CDATE}) &&" +
      "\\n          @{CNUM} < ${num_to}\\n    \",\"req_params\":" +
      "{\"param\":[{\"name\":\"date_from\",\"java_type\":" +
      "\"java.util.Date\"},{\"name\":\"date_to\",\"java_type\":" +
      "\"java.util.Date\"},{\"name\":\"num_from\",\"java_type\":" +
      "\"java.lang.Double\"},{\"name\":\"num_to\",\"java_type\":" +
      "\"java.lang.Double\"},{\"name\":\"f\",\"java_type\":" +
      "\"java.lang.Integer\"},{\"name\":\"t\",\"java_type\":" +
      "\"java.lang.Integer\"}]},\"sql_data\":{\"sql\":" +
      "{\"sql_text\":\"SELECT * FROM TEST_DATA \\n                        " +
      "WHERE CINT >= ? and CINT <= ?\",\"sql_params\":{\"param\":[{\"idx\":1," +
      "\"req_param\":\"f\"},{\"idx\":2,\"req_param\":\"t\"}]}}}}"
    },
    
    new String[] {      
        
      "test.sql_select",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"sql_data\":" +
      "{\"sql\":{\"sql_text\":\"SELECT * FROM TEST_DATA\"}}}"
    },
      
    new String[] {      

      "test.sql_select_cond",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"sort_by_grp\"" +
      ":{\"sort_by\":[{\"idx\":1,\"column\":\"CINT\"}]},\"req_params\":{\"par" +
      "am\":[{\"name\":\"f\",\"java_type\":\"java.lang.Integer\"},{\"name\":" +
      "\"t\",\"java_type\":\"java.lang.Integer\"}]},\"sql_data\":{\"sql\":{\"" +
      "sql_text\":\"SELECT * FROM TEST_DATA \\n                        WHERE " +
      "CINT >= ? and CINT <= ?\",\"sql_params\":{\"param\":[{\"idx\":1,\"req_" +
      "param\":\"f\"},{\"idx\":2,\"req_param\":\"t\"}]}}}}"
    },
      
    new String[] {      
        
      "test.static_test_calc_date1",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"ex_columns\":" +
      "{\"auto_inc\":{\"column\":[{\"name\":\"COL2\",\"start_from\":9,\"inc_b" +
      "y\":2},{\"name\":\"COL3\",\"start_from\":1995}]},\"calc\":{\"column\":" +
      "[{\"name\":\"COL4\",\"java_type\":\"java.lang.Long\",\"value\":\"\\n  " +
      "          \\t(new java.util.Date(@{COL1} + \\\"/\\\" + @{COL2} + \\n  " +
      "          \\t\\t\\t\\t\\t\\t\\t\\t\\\"/\\\" + @{COL3})).getTime()\\n  " +
      "          \"},{\"name\":\"COL5\",\"java_type\":\"java.lang.String\"," +
      "\"value\":\"\\n            \\tnew java.text.SimpleDateFormat(\\\"M/d/y" +
      "\\\").format(\\n            \\t\\tnew java.util.Date(@{COL1} + \\\"/\\" +
      "\" + @{COL2} + \\\"/\\\" + @{COL3}))\\n            \"}]}},\"static_dat" +
      "a\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java." +
      "lang.Integer\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell" +
      "\":[{\"name\":\"COL1\",\"value\":\"1\"}]},{\"cell\":[{\"name\":\"COL1" +
      "\",\"value\":\"2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"3\"}]}" +
      ",{\"cell\":[{\"name\":\"COL1\",\"value\":\"4\"}]},{\"cell\":[{\"name\"" +
      ":\"COL1\",\"value\":\"5\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":" +
      "\"6\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"7\"}]},{\"cell\":[{" +
      "\"name\":\"COL1\",\"value\":\"8\"}]},{\"cell\":[{\"name\":\"COL1\",\"v" +
      "alue\":\"9\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"10\"}]},{\"c" +
      "ell\":[{\"name\":\"COL1\",\"value\":\"11\"}]},{\"cell\":[{\"name\":\"C" +
      "OL1\",\"value\":\"12\"}]}]}}}"
    },
    
    new String[] {
        
      "test.static_test_calc_date2",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"ex_columns\":" +
      "{\"calc\":{\"column\":[{\"name\":\"COL2\",\"java_type\":\"java.lang.St" +
      "ring\",\"value\":\"\\n            \\t\\\"[\\\" + @{COL1}.getTime() + " +
      "\\\"]\\\"\\n            \"}]}},\"static_data\":{\"columns\":{\"column" +
      "\":[{\"name\":\"COL1\",\"java_type\":\"java.util.Date\",\"on_error\":" +
      "\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"valu" +
      "e\":\"12/31/2000\"}]}]}}}"
    },
    
    new String[] {      

      "test.static_test_lmap",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"lang_map\":{" +
      "\"column\":[{\"name\":\"COL1\"}]},\"static_data\":{\"columns\":{\"colu" +
      "mn\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error" +
      "\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"v" +
      "alue\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"L" +
      "L_PASSWORD\"}]}]}}}"
    },
    
    new String[] {      
        
      "test.static_test_lmap_auto_inc",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"lang_map\":{" +
      "\"column\":[{\"name\":\"COL1\"}]},\"ex_columns\":{\"auto_inc\":{\"colu" +
      "mn\":[{\"name\":\"COL2\",\"start_from\":-5,\"inc_by\":5},{\"name\":\"C" +
      "OL3\"}]}},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\"" +
      ",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows" +
      "\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]" +
      "},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}"
    },
    
    new String[] {      
        
      "test.static_test_lmap_calc",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"lang_map\":{" +
      "\"column\":[{\"name\":\"COL1\"}]},\"ex_columns\":{\"auto_inc\":{\"colu" +
      "mn\":[{\"name\":\"COL2\",\"start_from\":-5,\"inc_by\":5},{\"name\":\"C" +
      "OL3\"}]},\"calc\":{\"column\":[{\"name\":\"COL4\",\"java_type\":\"java" +
      ".lang.Integer\",\"value\":\"@{COL2} + \\n                \\t\\t\\t\\" +
      "t\\t\\t\\t\\t\\t\\t@{COL3}\"},{\"name\":\"COL5\",\"java_type\":\"java." +
      "lang.Integer\",\"value\":\"@{COL2} + \\n                \\t@{COL3} + @" +
      "{COL2} * 2 + @{COL2}* @{COL3} + @{COL3} * \\n                \\t@{COL3" +
      "} \"},{\"name\":\"COL6\",\"java_type\":\"java.lang.Integer\",\"stop_on" +
      "_error\":false,\"error_value\":\"-100\",\"value\":\"@{COL2}/@{COL3} \"" +
      "},{\"name\":\"COL7\",\"java_type\":\"java.lang.Double\",\"value\":\"ne" +
      "w Double(@{COL2})/new Double(@{COL3} + 1) \"},{\"name\":\"COL8\",\"jav" +
      "a_type\":\"java.lang.String\",\"value\":\"@{COL1} + \\\" \\\" + \\n   " +
      "             \\t@{COL1} + \\\" \\\" + @{COL2}*@{COL2}\"}]}},\"static_d" +
      "ata\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"jav" +
      "a.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell" +
      "\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name" +
      "\":\"COL1\",\"value\":\"LL_PASSWORD\"}]},{\"cell\":[{\"name\":\"COL1\"" +
      ",\"value\":\"LL_LETS_GO\"}]}]}}}"
    },
    
    new String[] {      

      "test.static_test_lmap_grp_complex1",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"group_data\":" +
      "{\"ds_list\":{\"group_ds\":[{\"lang_map\":{\"column\":[{\"name\":\"COL" +
      "1\"}]},\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"CNT\"}]}}" +
      ",\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\"}]},\"gro" +
      "up_data\":{\"ds_list\":{\"static_ds\":[{\"static_data\":{\"columns\":{" +
      "\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on" +
      "_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1" +
      "\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value" +
      "\":\"LL_PASSWORD\"}]}]}}},{\"static_data\":{\"columns\":{\"column\":[{" +
      "\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"" +
      "}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":" +
      "\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWO" +
      "RD\"}]}]}}}],\"group_ds\":[{\"group_data\":{\"ds_list\":{\"static_ds\"" +
      ":[{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java" +
      "_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"r" +
      "ow\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"ce" +
      "ll\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}]}}}]}}}]}}}"
    },
    
    new String[] {      

      "test.static_test_lmap_grp_complex2",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"lang_map\":{" +
      "\"column\":[{\"name\":\"COL1\"}]},\"ex_columns\":{\"auto_inc\":{\"colu" +
      "mn\":[{\"name\":\"CNT\"}]}},\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"" +
      "column\":\"COL1\"}]},\"group_data\":{\"ds_list\":{\"group_ds\":[{\"gro" +
      "up_data\":{\"ds_list\":{\"static_ds\":[{\"static_data\":{\"columns\":{" +
      "\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on" +
      "_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1" +
      "\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value" +
      "\":\"LL_PASSWORD\"}]}]}}},{\"static_data\":{\"columns\":{\"column\":[{" +
      "\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"" +
      "}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":" +
      "\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWO" +
      "RD\"}]}]}}}],\"group_ds\":[{\"group_data\":{\"ds_list\":{\"static_ds\"" +
      ":[{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java" +
      "_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"r" +
      "ow\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"ce" +
      "ll\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}]}}}]}}}]}}}"
    },
    
    new String[] {      

      "test.static_test_lmap_grp_complex3",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"group_data\":" +
      "{\"ds_list\":{\"group_ds\":[{\"lang_map\":{\"column\":[{\"name\":\"COL" +
      "1\"}]},\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\"}]}" +
      ",\"group_data\":{\"ds_list\":{\"static_ds\":[{\"static_data\":{\"colum" +
      "ns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String" +
      "\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\"" +
      ":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\"," +
      "\"value\":\"LL_PASSWORD\"}]}]}}},{\"static_data\":{\"columns\":{\"colu" +
      "mn\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error" +
      "\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"v" +
      "alue\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL" +
      "_PASSWORD\"}]}]}}}],\"group_ds\":[{\"group_data\":{\"ds_list\":{\"stat" +
      "ic_ds\":[{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\"" +
      ",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows" +
      "\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]" +
      "},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}]}}}]}" +
      "}}]}}}"
    },
    
    new String[] {      

      "test.static_test_lmap_grp_multi",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"group_data\":{\"ds_list\":{\"static_ds\":[{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}},{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}],\"group_ds\":[{\"group_data\":{\"ds_list\":{\"static_ds\":[{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}]}}}]}}}"
    },
    
    new String[] {      

      "test.static_test_lmap_grp_single",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"group_data\":{\"ds_list\":{\"static_ds\":[{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}]}}}"
    },
    
    new String[] {      
        
      "test.static_test_str1",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\",\"str_sort\":\"collator\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL3\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"A1\"},{\"name\":\"COL2\",\"value\":\"B1\"},{\"name\":\"COL3\",\"value\":\"C1\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a2\"},{\"name\":\"COL2\",\"value\":\"b2\"},{\"name\":\"COL3\",\"value\":\"c2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"A2\"},{\"name\":\"COL2\",\"value\":\"B2\"},{\"name\":\"COL3\",\"value\":\"C2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a1\"},{\"name\":\"COL2\",\"value\":\"b1\"},{\"name\":\"COL3\",\"value\":\"c1\"}]}]}}}"
    },
    
    new String[] {      

      "test.static_test_str2",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"group_data\":{\"ds_list\":{\"group_ds\":[{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\"}]},\"group_data\":{\"ds_list\":{\"static_ds\":[{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL3\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"A1\"},{\"name\":\"COL2\",\"value\":\"B1\"},{\"name\":\"COL3\",\"value\":\"C1\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a2\"},{\"name\":\"COL2\",\"value\":\"b2\"},{\"name\":\"COL3\",\"value\":\"c2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"A2\"},{\"name\":\"COL2\",\"value\":\"B2\"},{\"name\":\"COL3\",\"value\":\"C2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a1\"},{\"name\":\"COL2\",\"value\":\"b1\"},{\"name\":\"COL3\",\"value\":\"c1\"}]}]}}}]}}}]}}}"
    },
    
    new String[] {      

      "test.static_test_str3",
      
      "{\"type\":\"DataSetDescr\",\"ver_max\":1,\"ver_min\":0,\"group_data\":" +
      "{\"ds_list\":{\"static_ds\":[{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1" +
      ",\"column\":\"COL1\"}]},\"static_data\":{\"columns\":{\"column\":[{\"n" +
      "ame\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{" +
      "\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"" +
      "},{\"name\":\"COL3\",\"java_type\":\"java.lang.String\",\"on_error\":" +
      "\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"valu" +
      "e\":\"A1\"},{\"name\":\"COL2\",\"value\":\"B1\"},{\"name\":\"COL3\",\"" +
      "value\":\"C1\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a2\"},{\"n" +
      "ame\":\"COL2\",\"value\":\"b2\"},{\"name\":\"COL3\",\"value\":\"c2\"}]" +
      "},{\"cell\":[{\"name\":\"COL1\",\"value\":\"A2\"},{\"name\":\"COL2\"," +
      "\"value\":\"B2\"},{\"name\":\"COL3\",\"value\":\"C2\"}]},{\"cell\":[{" +
      "\"name\":\"COL1\",\"value\":\"a1\"},{\"name\":\"COL2\",\"value\":\"b1" +
      "\"},{\"name\":\"COL3\",\"value\":\"c1\"}]}]}}}]}}}"
    },
    
  };
    
  public static final String[][] PROJ_SORT_LIST = {
    new String[] {
        "bad",
        
        "csv_bad,static_test_calc_bad1,static_test_calc_bad2," +
                                                      "static_test_calc_bad3"
    },
    
    new String[] {
      "test",
      
      "csv_filter1,csv_filter_complex,csv_filter_complex_no_header," +
      "csv_filter_params,csv_sort1,csv_sort2,csv_sort3,csv_str1,group_mixed," +
      "sql_filter_complex,sql_select,sql_select_cond,static_test_calc_date1," +
      "static_test_calc_date2,static_test_lmap,static_test_lmap_auto_inc," +
      "static_test_lmap_calc,static_test_lmap_grp_complex1," +
      "static_test_lmap_grp_complex2,static_test_lmap_grp_complex3," +
      "static_test_lmap_grp_multi,static_test_lmap_grp_single," +
      "static_test_str1,static_test_str2,static_test_str3"
    }
  };
  
  private static String COMP_LIST = "{\"comp_list\":\"{\\\"ll_containers\\\":" +
          "[\\\"group\\\"],\\\"ll_avail_ds_types\\\":" +
              "[\\\"csv\\\",\\\"static\\\",\\\"sql\\\"]}\"}";
  
  @Override
  public String[][] getProjList() {
    return PROJ_SORT_LIST;
  }

  @Override
  public int readDemoFileSize() throws IOException {
    return 5018;
  }
  
  @Override
  public String getCompList() {
    return COMP_LIST;
  }

  @Override
  public String getProjSrcDir() {
    return DsTestConstants.DS_MAPS_PACKAGE;
  }

  @Override
  public String getProjWorkDir() {
    return LocalTestConstants.WORK_DS_DIR_S;
  }

  @Override
  public String getBaseExt() {
    return MeEntityUtils.BASE_EXT;
  }

  @Override
  public String getExtFileList(String resName) {
    return CSV_FILE_LIST;
  }

  @Override
  public String[][] getExtFileTestFiles(String resName) {
    return TEST_CSV_FILES;
  }

  @Override
  public TestFileItem[] getExtFileTestSet(String resName, String fileExt) {
    return TEST_CSV_SET;
  }

  @Override
  public HashMap<String, String>[] getExtFileTestSetParams(String resName) {
    return TEST_CSV_FILES_PARAMS;
  }

  @Override
  public String[] getExtList(String resName) {
    return new String[] {"csv"};
  }

  @Override
  public String[] getResDirList() {
    Set<String> set = MeEntityUtils.EXT_LIST.keySet();
    return set.toArray(new String[set.size()]);
  }

  @Override
  public byte[] getDemoExFileAsBytes(String resName, String ext) {
    return DEMO_CSV.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public String getDemoExFileUploadResp(String resName, String ext) {
    return DEMO_CSV_COLUMNS;
  }
  
  @Override
  public String getMainDemoFileJson() {
    return MAIN_DEMO_DS;
  }
  
  @Override
  public String[][] getDemoEntities() {
    return DS_MAPS;
  }
  
  @Override
  public String[][] getTestEntities() {
    return DS_TEST_MAPS;
  }
}
