/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-07
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.core.daemons;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.core.bindings.BindingTest;
import com.osbitools.ws.core.proc.*;
import com.osbitools.ws.core.producers.StaticDataSetProducer;

import com.osbitools.ws.shared.binding.ds.DataSetDescr;
import com.osbitools.ws.core.shared.ContextConfig;
import com.osbitools.ws.core.shared.TraceRecorder;
import com.osbitools.ws.core.shared.LocalTestConstants;

public class DsDaemonTest extends 
        AbstractResourceDaemonTest<DataSetDescr, DsDescrResource>  {
  
	static DsFilesCheck dcheck;

	// Destination file
	private static final String _fname = LocalTestConstants.WORK_DS_DIR + 
	                                            LocalTestConstants.DS_XML;

	// Test File alias
	static final String DBMAP_ALIAS = LocalTestConstants.DS_XML;

	static CountDownLatch ss1 = new CountDownLatch(1);
	static CountDownLatch ss2 = new CountDownLatch(1);
	static CountDownLatch ss3 = new CountDownLatch(1);
	static CountDownLatch ds1 = new CountDownLatch(TestConstants.THREAD_NUM);
	static CountDownLatch ds2 = new CountDownLatch(TestConstants.THREAD_NUM);
	static CountDownLatch ds3 = new CountDownLatch(TestConstants.THREAD_NUM);

	// Number of errors
	static int ecnt = 0;

	TraceRecorder trace;

	ArrayList<String> warn;
	
	private static DsMapTestResConfig _ds_cfg = new DsMapTestResConfig();

	public static final String STR_STATIC_TEST_LMAP_SINGLE = "{"
			+ "\"columns\":[{" +
			    "\"name\":\"COL1\"," +
			    "\"jtype\":\"java.lang.String\"}]," + "\"data\":[" +
			"[\"Username\"]," + 
			"[\"Password\"]" + 
			"]" + "}";

	public static final String STR_STATIC_TEST_LMAP_MULTI = "{"
			+ "\"columns\":[{" +
			    "\"name\":\"COL1\"," +
			    "\"jtype\":\"java.lang.String\"}]," + 
			"\"data\":[" +
				"[\"Username\"]," + 
				"[\"Password\"]," + 
				"[\"Username\"]," + 
				"[\"Password\"]," + 
				"[\"Username\"]," + 
				"[\"Password\"]" + 
			"]}";
	
	public static final String STR_STATIC_TEST_LMAP_GRP_COMPLEX_1 =
	    "{\"columns\":[" +
	        "{\"name\":\"COL1\",\"jtype\":\"java.lang.String\"}," +
	        "{\"name\":\"CNT\",\"jtype\":\"java.lang.Integer\"}]," +
			 "\"data\":[" +
	        "[\"Password\",1],[\"Password\",3],[\"Password\",5]," +
	        "[\"Username\",0],[\"Username\",2],[\"Username\",4]" +
	    "]}";
	
	public static final String STR_STATIC_TEST_LMAP_GRP_COMPLEX_2 = 
	    "{\"columns\":[" +
	        "{\"name\":\"COL1\",\"jtype\":\"java.lang.String\"}," +
	        "{\"name\":\"CNT\",\"jtype\":\"java.lang.Integer\"}]," +
			"\"data\":[" +
	        "[\"Password\",1],[\"Password\",3],[\"Password\",5]," +
	        "[\"Username\",0],[\"Username\",2],[\"Username\",4]" +
	    "]}";
	
	public static final String STR_STATIC_TEST_LMAP_GRP_COMPLEX_3 = "{" +
      "\"columns\":[{" +
			    "\"name\":\"COL1\"," +
			    "\"jtype\":\"java.lang.String\"}]," +
      "\"data\":[" +
        "[\"Password\"],[\"Password\"],[\"Password\"]," +
        "[\"Username\"],[\"Username\"],[\"Username\"]" +
    "]}";
	
	public static final String STR_STATIC_TEST_GROUP_MIXED = 
	  "{\"columns\":[" +
	      "{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\"}," +
	      "{\"name\":\"CNUM\",\"jtype\":\"java.lang.String\"}]," +
	   "\"data\":[[\"wnA\",\"2\"]," +
	      "[\"вжщ\",\"5\"],[\"gbJ\",\"4\"],[\"ГДц\",\"5\"],[\"bnW\",\"5\"]," +
	      "[\"ГБЧ\",\"2\"],[\"ruT\",\"7\"],[\"eTo\",\"4\"],[\"jqq\",\"5\"]," +
	      "[\"rMn\",\"9\"],[\"кРВ\",\"7\"],[\"tOD\",\"8\"],[\"ФЦЭ\",\"7\"]," +
	      "[\"wiZ\",\"0\"],[\"orZ\",\"2\"],[\"nuU\",\"9\"],[\"Glu\",\"4\"]," +
	      "[\"QRH\",\"0\"],[\"aeL\",\"8\"],[\"УдЭ\",\"8\"],[\"ЕюР\",\"4\"]," +
	      "[\"вжм\",\"4\"],[\"ЛОЖ\",\"4\"],[\"рзг\",\"0\"],[\"ЯБр\",\"3\"]," +
	      "[\"пТФ\",\"2\"],[\"ФцР\",\"7\"],[\"щЕХ\",\"9\"],[\"duS\",\"3\"]," +
	      "[\"hhg\",\"3\"],[\"щмф\",\"3\"],[\"XGh\",\"4\"],[\"Ншу\",\"6\"]," +
	      "[\"РЕЩ\",\"4\"],[\"ДЮЗ\",\"5\"],[\"ЦвР\",\"8\"],[\"DOn\",\"1\"]," +
	      "[\"khU\",\"3\"],[\"Esp\",\"4\"],[\"iyO\",\"3\"],[\"рЭБ\",\"4\"]," +
	      "[\"ЛрЗ\",\"2\"],[\"бсБ\",\"5\"],[\"зМг\",\"1\"],[\"ucq\",\"4\"]," +
	      "[\"RFV\",\"8\"],[\"яэд\",\"1\"],[\"ExL\",\"5\"],[\"юяЧ\",\"4\"]," +
	      "[\"СГж\",\"1\"],[\"хЧН\",\"6\"],[\"ЯГЮ\",\"6\"],[\"fcA\",\"3\"]," +
	      "[\"гРЛ\",\"5\"],[\"psx\",\"8\"],[\"yDl\",\"6\"],[\"PTM\",\"2\"]," +
	      "[\"pyQ\",\"8\"],[\"цБТ\",\"1\"],[\"KZq\",\"4\"],[\"QhO\",\"2\"]," +
	      "[\"ЗЛЛ\",\"7\"],[\"JKn\",\"3\"],[\"Fkp\",\"1\"],[\"ПщЯ\",\"2\"]," +
	      "[\"DSr\",\"3\"],[\"тРМ\",\"2\"],[\"zfT\",\"2\"],[\"ЖлЦ\",\"7\"]," +
	      "[\"YNh\",\"5\"],[\"jaL\",\"5\"],[\"BEC\",\"6\"],[\"jgB\",\"9\"]," +
	      "[\"vyH\",\"6\"],[\"LbP\",\"9\"],[\"мбм\",\"3\"],[\"уЛН\",\"7\"]," +
	      "[\"УчЮ\",\"2\"],[\"cho\",\"7\"],[\"vYB\",\"9\"],[\"гДд\",\"4\"]," +
	      "[\"ВеГ\",\"8\"],[\"PIu\",\"2\"],[\"nTx\",\"9\"],[\"pwE\",\"4\"]," +
	      "[\"пбщ\",\"0\"],[\"дЯШ\",\"1\"],[\"UGG\",\"5\"],[\"ЩЛщ\",\"7\"]," +
	      "[\"рДю\",\"1\"],[\"ждЮ\",\"7\"],[\"яЧН\",\"2\"],[\"ХЩК\",\"4\"]," +
	      "[\"Улд\",\"4\"],[\"щэЮ\",\"4\"],[\"цЯМ\",\"9\"],[\"cgT\",\"8\"]," +
	      "[\"WwK\",\"1\"],[\"ЖРГ\",\"8\"],[\"QXR\",\"5\"],[\"_UUu\",\"222\"]," +
	      "[\"_dDd\",\"333\"],[\"wnA\",\"2\"],[\"вжщ\",\"5\"],[\"gbJ\",\"4\"]," +
	      "[\"ГДц\",\"5\"],[\"bnW\",\"5\"],[\"ГБЧ\",\"2\"],[\"ruT\",\"7\"]," +
	      "[\"eTo\",\"4\"],[\"jqq\",\"5\"],[\"rMn\",\"9\"],[\"кРВ\",\"7\"]," +
	      "[\"tOD\",\"8\"],[\"ФЦЭ\",\"7\"],[\"wiZ\",\"0\"],[\"orZ\",\"2\"]," +
	      "[\"nuU\",\"9\"],[\"Glu\",\"4\"],[\"QRH\",\"0\"],[\"aeL\",\"8\"]," +
	      "[\"УдЭ\",\"8\"],[\"ЕюР\",\"4\"],[\"вжм\",\"4\"],[\"ЛОЖ\",\"4\"]," +
	      "[\"рзг\",\"0\"],[\"ЯБр\",\"3\"],[\"пТФ\",\"2\"],[\"ФцР\",\"7\"]," +
	      "[\"щЕХ\",\"9\"],[\"duS\",\"3\"],[\"hhg\",\"3\"],[\"щмф\",\"3\"]," +
	      "[\"XGh\",\"4\"],[\"Ншу\",\"6\"],[\"РЕЩ\",\"4\"],[\"ДЮЗ\",\"5\"]," +
	      "[\"ЦвР\",\"8\"],[\"DOn\",\"1\"],[\"khU\",\"3\"],[\"Esp\",\"4\"]," +
	      "[\"iyO\",\"3\"],[\"рЭБ\",\"4\"],[\"ЛрЗ\",\"2\"],[\"бсБ\",\"5\"]," +
	      "[\"зМг\",\"1\"],[\"ucq\",\"4\"],[\"RFV\",\"8\"],[\"яэд\",\"1\"]," +
	      "[\"ExL\",\"5\"],[\"юяЧ\",\"4\"],[\"СГж\",\"1\"],[\"хЧН\",\"6\"]," +
	      "[\"ЯГЮ\",\"6\"],[\"fcA\",\"3\"],[\"гРЛ\",\"5\"],[\"psx\",\"8\"]," +
	      "[\"yDl\",\"6\"],[\"PTM\",\"2\"],[\"pyQ\",\"8\"],[\"цБТ\",\"1\"]," +
	      "[\"KZq\",\"4\"],[\"QhO\",\"2\"],[\"ЗЛЛ\",\"7\"],[\"JKn\",\"3\"]," +
	      "[\"Fkp\",\"1\"],[\"ПщЯ\",\"2\"],[\"DSr\",\"3\"],[\"тРМ\",\"2\"]," +
	      "[\"zfT\",\"2\"],[\"ЖлЦ\",\"7\"],[\"YNh\",\"5\"],[\"jaL\",\"5\"]," +
	      "[\"BEC\",\"6\"],[\"jgB\",\"9\"],[\"vyH\",\"6\"],[\"LbP\",\"9\"]," +
	      "[\"мбм\",\"3\"],[\"уЛН\",\"7\"],[\"УчЮ\",\"2\"],[\"cho\",\"7\"]," +
	      "[\"vYB\",\"9\"],[\"гДд\",\"4\"],[\"ВеГ\",\"8\"],[\"PIu\",\"2\"]," +
	      "[\"nTx\",\"9\"],[\"pwE\",\"4\"],[\"пбщ\",\"0\"],[\"дЯШ\",\"1\"]," +
	      "[\"UGG\",\"5\"],[\"ЩЛщ\",\"7\"],[\"рДю\",\"1\"],[\"ждЮ\",\"7\"]," +
	      "[\"яЧН\",\"2\"],[\"ХЩК\",\"4\"],[\"Улд\",\"4\"],[\"щэЮ\",\"4\"]," +
	      "[\"цЯМ\",\"9\"],[\"cgT\",\"8\"],[\"WwK\",\"1\"],[\"ЖРГ\",\"8\"]," +
	      "[\"QXR\",\"5\"]]}";
	
	public static final String STR_STATIC_TEST_LMAP_AUTO_INC = "{" +
		"\"columns\":[" +
		  "{\"name\":\"COL1\",\"jtype\":\"java.lang.String\"}," +
		  "{\"name\":\"COL2\",\"jtype\":\"java.lang.Integer\"}," +
      "{\"name\":\"COL3\",\"jtype\":\"java.lang.Integer\"}]," +
		"\"data\":[[\"Username\",-5,0],[\"Password\",0,1]]}";
	
	public static final String STR_STATIC_TEST_LMAP_CALC = "{" +
		"\"columns\":[" +
		  "{\"name\":\"COL1\",\"jtype\":\"java.lang.String\"}," +
		  "{\"name\":\"COL2\",\"jtype\":\"java.lang.Integer\"}," +
      "{\"name\":\"COL3\",\"jtype\":\"java.lang.Integer\"}," +
      "{\"name\":\"COL4\",\"jtype\":\"java.lang.Integer\"}," +
      "{\"name\":\"COL5\",\"jtype\":\"java.lang.Integer\"}," +
      "{\"name\":\"COL6\",\"jtype\":\"java.lang.Integer\"}," +
      "{\"name\":\"COL7\",\"jtype\":\"java.lang.Double\"}," +
      "{\"name\":\"COL8\",\"jtype\":\"java.lang.String\"}]," +
		"\"data\":[[\"Username\",-5,0,-5,-15,-100,-5.0,\"Username Username 25\"]," +
		  "[\"Password\",0,1,1,2,0,0.0,\"Password Password 0\"]," +
		  "[\"Let's go\",5,2,7,31,2,1.6666666666666667,\"Let's go Let's go 25\"]]}";
	
	public static final String STR_STATIC_TEST_CALC_DATE1 = 
		"{\"columns\":[" +
		  "{\"name\":\"COL1\",\"jtype\":\"java.lang.Integer\"}," +
		  "{\"name\":\"COL2\",\"jtype\":\"java.lang.Integer\"}," +
      "{\"name\":\"COL3\",\"jtype\":\"java.lang.Integer\"}," +
      "{\"name\":\"COL4\",\"jtype\":\"java.lang.Long\"}," +
      "{\"name\":\"COL5\",\"jtype\":\"java.lang.String\"}]," +
		"\"data\":[[1,9,1995,789627600000,\"1/9/1995\"]," +
				  "[2,11,1996,824014800000,\"2/11/1996\"]," +
				  "[3,13,1997,858229200000,\"3/13/1997\"]," +
				  "[4,15,1998,892612800000,\"4/15/1998\"]," +
				  "[5,17,1999,926913600000,\"5/17/1999\"]," +
				  "[6,19,2000,961387200000,\"6/19/2000\"]," +
				  "[7,21,2001,995688000000,\"7/21/2001\"]," +
				  "[8,23,2002,1030075200000,\"8/23/2002\"]," +
				  "[9,25,2003,1064462400000,\"9/25/2003\"]," +
				  "[10,27,2004,1098849600000,\"10/27/2004\"]," +
				  "[11,29,2005,1133240400000,\"11/29/2005\"]," +
				  "[12,31,2006,1167541200000,\"12/31/2006\"]" +
		"]}";
	
	@SuppressWarnings("deprecation")
	public static final String STR_STATIC_TEST_CALC_DATE2 = 
			"{\"columns\":[" +
			  "{\"name\":\"COL1\",\"jtype\":\"java.util.Date\"}," +
			  "{\"name\":\"COL2\",\"jtype\":\"java.lang.String\"}]," +
			"\"data\":[[\"" + (new java.util.Date("12/31/2000")).toString() +
				"\",\"[978238800000]\"]" +
			"]}";
	
	static final String STR_STATIC_TEST_LMAP_NON_MINIFIED_NO_TRACE = "{\n" +
	    "	\"columns\": [\n" +
	    "\t\t{\n" +
	    "\t\t\t\"name\": \"COL1\",\n" +
	    "\t\t\t\"jtype\": \"java.lang.String\"\n" +
      "\t\t}\n" +
	    "\t],\n" +
      "	\"data\": [\n" +
			"		[\"Username\"],\n" + 
			"		[\"Password\"]\n" + 
			"	]\n" +
			"}";
	
	static final String STR_STATIC_TEST_LMAP_NON_MINIFIED = "{\n" +
	    "\t\"columns\": [\n" +
      "\t\t{\n" +
      "\t\t\t\"name\": \"COL1\",\n" +
      "\t\t\t\"jtype\": \"java.lang.String\"\n" +
      "\t\t}\n" +
      "\t],\n" +
      "	\"data\": [\n" +
			"		[\"Username\"],\n" + 
			"		[\"Password\"]\n" + 
			"	],\n" + 
			"	\"trace\": [\n" + 
			"\t\t{\n" + 
			"\t\t\t\"key\": \"Start processing\",\n" + 
			"\t\t\t\"duration\": \n" + 
			"\t\t},\n" + 
			"\t\t{\n" + 
			"\t\t\t\"key\": \"DataSet Proc Start\",\n" + 
			"\t\t\t\"duration\": \n" + 
			"\t\t},\n" +
			"\t\t{\n" + 
      "\t\t\t\"key\": \"DataSet Data Read\",\n" + 
      "\t\t\t\"duration\": \n" + 
      "\t\t},\n" + 
      "\t\t{\n" + 
      "\t\t\t\"key\": \"Data Read\",\n" + 
      "\t\t\t\"duration\": \n" + 
      "\t\t},\n" + 
			"\t\t{\n" + 
			"\t\t\t\"key\": \"End processing\",\n" + 
			"\t\t\t\"duration\": \n" + 
			"\t\t}\n" +
			"	],\n" +
			"	\"warn\": [\"Hi \\\"there\\\"!!!\",\"Hello 'here'???\"]\n" +
			"}";

	static final String STR_STATIC_TEST_LMAP_EX_NON_MINIFIED = "{\n" +
	    "\t\"columns\": [\n" +
      "\t\t{\n" +
      "\t\t\t\"name\": \"COL1\",\n" +
      "\t\t\t\"jtype\": \"java.lang.String\"\n" +
      "\t\t}\n" +
      "\t],\n" +
	    "	\"data\": [\n" +
			"		[\"Username\"],\n" + 
			"		[\"Password\"],\n" + 
			"		[\"Username\"],\n" + 
			"		[\"Password\"],\n" + 
			"		[\"Username\"],\n" + 
			"		[\"Password\"]\n" + 
			"	]\n}";
	
	static final String STR_STATIC_TEST_LMAP_COMPLEX_NON_MINIFIED = "{\n" +
	    "\t\"columns\": [\n" +
      "\t\t{\n" +
      "\t\t\t\"name\": \"COL1\",\n" +
      "\t\t\t\"jtype\": \"java.lang.String\"\n" +
      "\t\t}\n" +
      "\t],\n" +
      "\t\"data\": [\n" +
      "\t\t[\"Password\"],\n" +
      "\t\t[\"Password\"],\n" +
      "\t\t[\"Password\"],\n" +
      "\t\t[\"Username\"],\n" +
      "\t\t[\"Username\"],\n" +
      "\t\t[\"Username\"]\n" +
      "\t]\n}";
	
	public static final String STR_STATIC_TEST_STR1 = "{" +
			"\"columns\":[" +
			  "{\"name\":\"COL1\",\"jtype\":\"java.lang.String\"}," +
        "{\"name\":\"COL2\",\"jtype\":\"java.lang.String\"}," +
        "{\"name\":\"COL3\",\"jtype\":\"java.lang.String\"}]," +
			"\"data\":[[\"a1\",\"b1\",\"c1\"]," +
					  "[\"A1\",\"B1\",\"C1\"]," +
					  "[\"a2\",\"b2\",\"c2\"]," +
					  "[\"A2\",\"B2\",\"C2\"]]}";
	
	public static final String STR_STATIC_TEST_STR_SORTED = "{" +
		"\"columns\":[" +
		  "{\"name\":\"COL1\",\"jtype\":\"java.lang.String\"}," +
      "{\"name\":\"COL2\",\"jtype\":\"java.lang.String\"}," +
      "{\"name\":\"COL3\",\"jtype\":\"java.lang.String\"}]," +
		"\"data\":[[\"A1\",\"B1\",\"C1\"]," +
				  "[\"A2\",\"B2\",\"C2\"]," +
				  "[\"a1\",\"b1\",\"c1\"]," +
				  "[\"a2\",\"b2\",\"c2\"]]}";

	public static final String CSV_STR1 = "{" +
	  "\"columns\":[" +
	    "{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\"}," +
	    "{\"name\":\"CNUM\",\"jtype\":\"java.lang.String\"}]," +
		"\"data\":[[\"wnA\",\"2\"],[\"вжщ\",\"5\"],[\"gbJ\",\"4\"]," +
  		"[\"ГДц\",\"5\"],[\"bnW\",\"5\"],[\"ГБЧ\",\"2\"],[\"ruT\",\"7\"]," +
  		"[\"eTo\",\"4\"],[\"jqq\",\"5\"],[\"rMn\",\"9\"],[\"кРВ\",\"7\"]," +
  		"[\"tOD\",\"8\"],[\"ФЦЭ\",\"7\"],[\"wiZ\",\"0\"],[\"orZ\",\"2\"]," +
  		"[\"nuU\",\"9\"],[\"Glu\",\"4\"],[\"QRH\",\"0\"],[\"aeL\",\"8\"]," +
  		"[\"УдЭ\",\"8\"],[\"ЕюР\",\"4\"],[\"вжм\",\"4\"],[\"ЛОЖ\",\"4\"]," +
  		"[\"рзг\",\"0\"],[\"ЯБр\",\"3\"],[\"пТФ\",\"2\"],[\"ФцР\",\"7\"]," +
  		"[\"щЕХ\",\"9\"],[\"duS\",\"3\"],[\"hhg\",\"3\"],[\"щмф\",\"3\"]," +
  		"[\"XGh\",\"4\"],[\"Ншу\",\"6\"],[\"РЕЩ\",\"4\"],[\"ДЮЗ\",\"5\"]," +
  		"[\"ЦвР\",\"8\"],[\"DOn\",\"1\"],[\"khU\",\"3\"],[\"Esp\",\"4\"]," +
  		"[\"iyO\",\"3\"],[\"рЭБ\",\"4\"],[\"ЛрЗ\",\"2\"],[\"бсБ\",\"5\"]," +
  		"[\"зМг\",\"1\"],[\"ucq\",\"4\"],[\"RFV\",\"8\"],[\"яэд\",\"1\"]," +
  		"[\"ExL\",\"5\"],[\"юяЧ\",\"4\"],[\"СГж\",\"1\"],[\"хЧН\",\"6\"]," +
  		"[\"ЯГЮ\",\"6\"],[\"fcA\",\"3\"],[\"гРЛ\",\"5\"],[\"psx\",\"8\"]," +
  		"[\"yDl\",\"6\"],[\"PTM\",\"2\"],[\"pyQ\",\"8\"],[\"цБТ\",\"1\"]," +
  		"[\"KZq\",\"4\"],[\"QhO\",\"2\"],[\"ЗЛЛ\",\"7\"],[\"JKn\",\"3\"]," +
  		"[\"Fkp\",\"1\"],[\"ПщЯ\",\"2\"],[\"DSr\",\"3\"],[\"тРМ\",\"2\"]," +
  		"[\"zfT\",\"2\"],[\"ЖлЦ\",\"7\"],[\"YNh\",\"5\"],[\"jaL\",\"5\"]," +
  		"[\"BEC\",\"6\"],[\"jgB\",\"9\"],[\"vyH\",\"6\"],[\"LbP\",\"9\"]," +
  		"[\"мбм\",\"3\"],[\"уЛН\",\"7\"],[\"УчЮ\",\"2\"],[\"cho\",\"7\"]," +
  		"[\"vYB\",\"9\"],[\"гДд\",\"4\"],[\"ВеГ\",\"8\"],[\"PIu\",\"2\"]," +
  		"[\"nTx\",\"9\"],[\"pwE\",\"4\"],[\"пбщ\",\"0\"],[\"дЯШ\",\"1\"]," +
  		"[\"UGG\",\"5\"],[\"ЩЛщ\",\"7\"],[\"рДю\",\"1\"],[\"ждЮ\",\"7\"]," +
  		"[\"яЧН\",\"2\"],[\"ХЩК\",\"4\"],[\"Улд\",\"4\"],[\"щэЮ\",\"4\"]," +
  		"[\"цЯМ\",\"9\"],[\"cgT\",\"8\"],[\"WwK\",\"1\"],[\"ЖРГ\",\"8\"]," +
  		"[\"QXR\",\"5\"]]}";
	
	public static final String CSV_SORT1 = "{" +
	  "\"columns\":[" +
	    "{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\"}," +
	    "{\"name\":\"CNUM\",\"jtype\":\"java.lang.String\"}]," +
		"\"data\":[[\"BEC\",\"6\"],[\"DOn\",\"1\"],[\"DSr\",\"3\"]," +
  		"[\"Esp\",\"4\"],[\"ExL\",\"5\"],[\"Fkp\",\"1\"],[\"Glu\",\"4\"]," +
  		"[\"JKn\",\"3\"],[\"KZq\",\"4\"],[\"LbP\",\"9\"],[\"PIu\",\"2\"]," +
  		"[\"PTM\",\"2\"],[\"QRH\",\"0\"],[\"QXR\",\"5\"],[\"QhO\",\"2\"]," +
  		"[\"RFV\",\"8\"],[\"UGG\",\"5\"],[\"WwK\",\"1\"],[\"XGh\",\"4\"]," +
  		"[\"YNh\",\"5\"],[\"aeL\",\"8\"],[\"bnW\",\"5\"],[\"cgT\",\"8\"]," +
  		"[\"cho\",\"7\"],[\"duS\",\"3\"],[\"eTo\",\"4\"],[\"fcA\",\"3\"]," +
  		"[\"gbJ\",\"4\"],[\"hhg\",\"3\"],[\"iyO\",\"3\"],[\"jaL\",\"5\"]," +
  		"[\"jgB\",\"9\"],[\"jqq\",\"5\"],[\"khU\",\"3\"],[\"nTx\",\"9\"]," +
  		"[\"nuU\",\"9\"],[\"orZ\",\"2\"],[\"psx\",\"8\"],[\"pwE\",\"4\"]," +
  		"[\"pyQ\",\"8\"],[\"rMn\",\"9\"],[\"ruT\",\"7\"],[\"tOD\",\"8\"]," +
  		"[\"ucq\",\"4\"],[\"vYB\",\"9\"],[\"vyH\",\"6\"],[\"wiZ\",\"0\"]," +
  		"[\"wnA\",\"2\"],[\"yDl\",\"6\"],[\"zfT\",\"2\"],[\"ВеГ\",\"8\"]," +
  		"[\"ГБЧ\",\"2\"],[\"ГДц\",\"5\"],[\"ДЮЗ\",\"5\"],[\"ЕюР\",\"4\"]," +
  		"[\"ЖРГ\",\"8\"],[\"ЖлЦ\",\"7\"],[\"ЗЛЛ\",\"7\"],[\"ЛОЖ\",\"4\"]," +
  		"[\"ЛрЗ\",\"2\"],[\"Ншу\",\"6\"],[\"ПщЯ\",\"2\"],[\"РЕЩ\",\"4\"]," +
  		"[\"СГж\",\"1\"],[\"УдЭ\",\"8\"],[\"Улд\",\"4\"],[\"УчЮ\",\"2\"]," +
  		"[\"ФЦЭ\",\"7\"],[\"ФцР\",\"7\"],[\"ХЩК\",\"4\"],[\"ЦвР\",\"8\"]," +
  		"[\"ЩЛщ\",\"7\"],[\"ЯБр\",\"3\"],[\"ЯГЮ\",\"6\"],[\"бсБ\",\"5\"]," +
  		"[\"вжм\",\"4\"],[\"вжщ\",\"5\"],[\"гДд\",\"4\"],[\"гРЛ\",\"5\"]," +
  		"[\"дЯШ\",\"1\"],[\"ждЮ\",\"7\"],[\"зМг\",\"1\"],[\"кРВ\",\"7\"]," +
  		"[\"мбм\",\"3\"],[\"пТФ\",\"2\"],[\"пбщ\",\"0\"],[\"рДю\",\"1\"]," +
  		"[\"рЭБ\",\"4\"],[\"рзг\",\"0\"],[\"тРМ\",\"2\"],[\"уЛН\",\"7\"]," +
  		"[\"хЧН\",\"6\"],[\"цБТ\",\"1\"],[\"цЯМ\",\"9\"],[\"щЕХ\",\"9\"]," +
  		"[\"щмф\",\"3\"],[\"щэЮ\",\"4\"],[\"юяЧ\",\"4\"],[\"яЧН\",\"2\"]," +
  		"[\"яэд\",\"1\"]]}";
	
	public static final String CSV_SORT2 = "{" +
	  "\"columns\":[" +
	      "{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\"}," +
	      "{\"name\":\"CNUM\",\"jtype\":\"java.lang.String\"}]," +
		"\"data\":[[\"LbP\",\"9\"],[\"jgB\",\"9\"],[\"nTx\",\"9\"]," +
  		"[\"nuU\",\"9\"],[\"rMn\",\"9\"],[\"vYB\",\"9\"],[\"цЯМ\",\"9\"]," +
  		"[\"щЕХ\",\"9\"],[\"RFV\",\"8\"],[\"aeL\",\"8\"],[\"cgT\",\"8\"]," +
  		"[\"psx\",\"8\"],[\"pyQ\",\"8\"],[\"tOD\",\"8\"],[\"ВеГ\",\"8\"]," +
  		"[\"ЖРГ\",\"8\"],[\"УдЭ\",\"8\"],[\"ЦвР\",\"8\"],[\"cho\",\"7\"]," +
  		"[\"ruT\",\"7\"],[\"ЖлЦ\",\"7\"],[\"ЗЛЛ\",\"7\"],[\"ФЦЭ\",\"7\"]," +
  		"[\"ФцР\",\"7\"],[\"ЩЛщ\",\"7\"],[\"ждЮ\",\"7\"],[\"кРВ\",\"7\"]," +
  		"[\"уЛН\",\"7\"],[\"BEC\",\"6\"],[\"vyH\",\"6\"],[\"yDl\",\"6\"]," +
  		"[\"Ншу\",\"6\"],[\"ЯГЮ\",\"6\"],[\"хЧН\",\"6\"],[\"ExL\",\"5\"]," +
  		"[\"QXR\",\"5\"],[\"UGG\",\"5\"],[\"YNh\",\"5\"],[\"bnW\",\"5\"]," +
  		"[\"jaL\",\"5\"],[\"jqq\",\"5\"],[\"ГДц\",\"5\"],[\"ДЮЗ\",\"5\"]," +
  		"[\"бсБ\",\"5\"],[\"вжщ\",\"5\"],[\"гРЛ\",\"5\"],[\"Esp\",\"4\"]," +
  		"[\"Glu\",\"4\"],[\"KZq\",\"4\"],[\"XGh\",\"4\"],[\"eTo\",\"4\"]," +
  		"[\"gbJ\",\"4\"],[\"pwE\",\"4\"],[\"ucq\",\"4\"],[\"ЕюР\",\"4\"]," +
  		"[\"ЛОЖ\",\"4\"],[\"РЕЩ\",\"4\"],[\"Улд\",\"4\"],[\"ХЩК\",\"4\"]," +
  		"[\"вжм\",\"4\"],[\"гДд\",\"4\"],[\"рЭБ\",\"4\"],[\"щэЮ\",\"4\"]," +
  		"[\"юяЧ\",\"4\"],[\"DSr\",\"3\"],[\"JKn\",\"3\"],[\"duS\",\"3\"]," +
  		"[\"fcA\",\"3\"],[\"hhg\",\"3\"],[\"iyO\",\"3\"],[\"khU\",\"3\"]," +
  		"[\"ЯБр\",\"3\"],[\"мбм\",\"3\"],[\"щмф\",\"3\"],[\"PIu\",\"2\"]," +
  		"[\"PTM\",\"2\"],[\"QhO\",\"2\"],[\"orZ\",\"2\"],[\"wnA\",\"2\"]," +
  		"[\"zfT\",\"2\"],[\"ГБЧ\",\"2\"],[\"ЛрЗ\",\"2\"],[\"ПщЯ\",\"2\"]," +
  		"[\"УчЮ\",\"2\"],[\"пТФ\",\"2\"],[\"тРМ\",\"2\"],[\"яЧН\",\"2\"]," +
  		"[\"DOn\",\"1\"],[\"Fkp\",\"1\"],[\"WwK\",\"1\"],[\"СГж\",\"1\"]," +
  		"[\"дЯШ\",\"1\"],[\"зМг\",\"1\"],[\"рДю\",\"1\"],[\"цБТ\",\"1\"]," +
  		"[\"яэд\",\"1\"],[\"QRH\",\"0\"],[\"wiZ\",\"0\"],[\"пбщ\",\"0\"]," +
  		"[\"рзг\",\"0\"]]}";
	
	public static final String CSV_SORT3 = "{" +
	  "\"columns\":[" +
	    "{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\"}]," +
	  "\"data\":[[\"ABa\"],[\"aAb\"],[\"баБ\"],[\"баб\"]]}";
	
	public static final String CSV_FILTER1 = "{" +
		"\"columns\":[{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\"}," +
	      "{\"name\":\"CNUM\",\"jtype\":\"java.lang.String\"}]," +
		"\"data\":[[\"DSr\",\"3\"],[\"duS\",\"3\"],[\"fcA\",\"3\"]," +
		"[\"hhg\",\"3\"],[\"iyO\",\"3\"],[\"JKn\",\"3\"],[\"khU\",\"3\"]," +
		"[\"ЯБр\",\"3\"],[\"мбм\",\"3\"],[\"щмф\",\"3\"],[\"Esp\",\"4\"]," +
		"[\"eTo\",\"4\"],[\"gbJ\",\"4\"],[\"Glu\",\"4\"],[\"KZq\",\"4\"]," +
		"[\"pwE\",\"4\"],[\"ucq\",\"4\"],[\"XGh\",\"4\"],[\"ЕюР\",\"4\"]," +
		"[\"ЛОЖ\",\"4\"],[\"РЕЩ\",\"4\"],[\"Улд\",\"4\"],[\"ХЩК\",\"4\"]," +
		"[\"вжм\",\"4\"],[\"гДд\",\"4\"],[\"рЭБ\",\"4\"],[\"щэЮ\",\"4\"]," +
		"[\"юяЧ\",\"4\"]]}";
	
	@SuppressWarnings("deprecation")
  public static final String CSV_FILTER_COMPLEX = "{" +
			"\"columns\":[" +
			  "{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\"}," +
			  "{\"name\":\"CINT\",\"jtype\":\"java.lang.String\"}," +
			  "{\"name\":\"CNUM\",\"jtype\":\"java.lang.Double\"}," +
			  "{\"name\":\"CDATE\",\"jtype\":\"java.util.Date\"}]," +
			"\"data\":[[\"ewB\",\"4\",4.5204705669907845,\"" + 
			                                    new Date("02/03/2007") + "\"]," +
			          "[\"хдд\",\"7\",4.166499351125718,\"" + 
                                          new Date("05/18/2007") + "\"]," +
			          "[\"ааА\",\"0\",5.363522439789376,\"" + 
                                          new Date("08/15/2007") + "\"]]}";
	
	@SuppressWarnings("deprecation")
  public static final String CSV_FILTER_COMPLEX_NO_HEADER = "{" +
      "\"columns\":[" +
        "{\"name\":\"COL1\",\"jtype\":\"java.lang.String\"}," +
        "{\"name\":\"COL2\",\"jtype\":\"java.lang.String\"}," +
        "{\"name\":\"COL3\",\"jtype\":\"java.lang.Double\"}," +
        "{\"name\":\"COL4\",\"jtype\":\"java.util.Date\"}]," +
      "\"data\":[[\"ewB\",\"4\",4.5204705669907845,\"" + 
                                          new Date("02/03/2007") + "\"]," +
                "[\"хдд\",\"7\",4.166499351125718,\"" + 
                                          new Date("05/18/2007") + "\"]," +
                "[\"ааА\",\"0\",5.363522439789376,\"" + 
                                          new Date("08/15/2007") + "\"]]}";
	
	public static final String SQL_FILTER_COMPLEX = "{" +
    "\"columns\":[" +
      "{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\"}," +
      "{\"name\":\"CINT\",\"jtype\":\"java.lang.Integer\"}," +
      "{\"name\":\"CNUM\",\"jtype\":\"java.lang.Double\"}," +
      "{\"name\":\"CDATE\",\"jtype\":\"java.util.Date\"}]," +
    "\"data\":[[\"BAB\",4,5.165882140661617,\"2010-01-07\"]," +
			          "[\"BBa\",4,4.240214641484432,\"2010-03-11\"]]}";
	
	public static final String SQL_SELECT = "{" +
	    "\"columns\":[" +
        "{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\"}," +
        "{\"name\":\"CINT\",\"jtype\":\"java.lang.Integer\"}," +
        "{\"name\":\"CNUM\",\"jtype\":\"java.lang.Double\"}," +
        "{\"name\":\"CDATE\",\"jtype\":\"java.util.Date\"}]," +
			"\"data\":[[\"БбБ\",4,2.955086552138356,\"2001-05-03\"]," +
			          "[\"BBB\",2,3.2332353284050654,\"2008-02-23\"]," +
			          "[\"ааа\",8,7.310115639942376,\"2008-01-19\"]," +
			          "[\"AAb\",5,1.800337233825724,\"2000-10-25\"]," +
			          "[\"Абб\",2,7.762678666517692,\"2007-08-20\"]," +
			          "[\"bab\",7,6.603907566891581,\"2004-05-10\"]," +
			          "[\"Bba\",9,4.131337008752595,\"2007-07-22\"]," +
			          "[\"BBa\",3,6.363432849109767,\"2008-08-24\"]," +
			          "[\"Ааб\",5,0.5613011579408855,\"2009-12-17\"]," +
			          "[\"Ббб\",5,3.2349031533386907,\"2005-02-22\"]," +
			          "[\"Baa\",4,3.6063533556379546,\"2011-07-01\"]," +
			          "[\"Bbb\",7,6.178684489699045,\"2011-01-03\"]," +
			          "[\"BaB\",2,9.661964994457277,\"2000-04-10\"]," +
			          "[\"AAA\",4,1.5496807189474338,\"2008-05-06\"]," +
			          "[\"Bba\",0,8.116801410155947,\"2007-10-18\"]," +
			          "[\"baa\",0,8.526973987503299,\"2008-08-18\"]," +
			          "[\"АбА\",2,9.08979391361228,\"2001-05-22\"]," +
			          "[\"bAA\",4,9.500128543247566,\"2008-01-04\"]," +
			          "[\"baB\",6,7.119973700700886,\"2009-12-13\"]," +
			          "[\"BaA\",2,8.029349533986858,\"2006-05-01\"]," +
			          "[\"БаА\",1,4.67624994735985,\"2005-02-17\"]," +
			          "[\"BBA\",6,9.49667903999852,\"2007-06-03\"]," +
			          "[\"аАА\",3,1.8543233750941412,\"2007-12-12\"]," +
			          "[\"бББ\",3,2.0654223581606,\"2008-07-03\"]," +
			          "[\"aAa\",1,1.127919456855021,\"2005-02-09\"]," +
			          "[\"abA\",1,8.064986416553003,\"2011-11-02\"]," +
			          "[\"аББ\",0,8.52153202699897,\"2007-07-01\"]," +
			          "[\"Abb\",2,8.418324007586225,\"2007-01-20\"]," +
			          "[\"Ббб\",0,5.071862284829727,\"2007-11-25\"]," +
			          "[\"Bbb\",3,5.229165495026457,\"2010-06-12\"]," +
			          "[\"ААб\",2,4.945141049700554,\"2000-05-25\"]," +
			          "[\"аАА\",0,0.1462287772470361,\"2008-04-20\"]," +
			          "[\"BbA\",5,9.077523504011257,\"2008-05-12\"]," +
			          "[\"ббб\",7,8.62159504803369,\"2010-05-15\"]," +
			          "[\"аБа\",3,5.705742717455145,\"2003-03-08\"]," +
			          "[\"БАБ\",2,2.6804361453845935,\"2008-02-03\"]," +
			          "[\"АБА\",2,1.655207836089173,\"2002-06-05\"]," +
			          "[\"ABa\",2,7.3192819982081225,\"2008-02-11\"]," +
			          "[\"BAA\",9,0.752737625425054,\"2000-10-10\"]," +
			          "[\"бба\",9,8.150017165108979,\"2005-01-13\"]," +
			          "[\"БаБ\",0,8.23111547463195,\"2001-01-19\"]," +
			          "[\"ABa\",5,0.4067890892069337,\"2010-05-03\"]," +
			          "[\"ааА\",8,2.492651112124703,\"2005-04-19\"]," +
			          "[\"бБб\",0,1.9330230981491137,\"2004-09-08\"]," +
			          "[\"BBa\",6,8.098941621633367,\"2008-01-13\"]," +
			          "[\"аББ\",1,5.265723718522052,\"2004-12-14\"]," +
			          "[\"Abb\",8,6.354013954289926,\"2005-03-24\"]," +
			          "[\"БАб\",2,6.434256067225719,\"2011-01-06\"]," +
			          "[\"ааа\",1,2.348038918124752,\"2003-01-11\"]," +
			          "[\"ббБ\",2,0.5907908378234694,\"2007-02-17\"]," +
			          "[\"ABb\",2,9.82880562053704,\"2003-08-19\"]," +
			          "[\"abA\",5,7.0896026956224105,\"2010-10-14\"]," +
			          "[\"aba\",4,7.768139863126658,\"2008-06-21\"]," +
			          "[\"аАб\",2,9.132408058189716,\"2007-02-03\"]," +
			          "[\"Ааб\",0,4.9904591152361375,\"2009-04-19\"]," +
			          "[\"aaA\",8,5.596930659449094,\"2004-12-09\"]," +
			          "[\"АБа\",4,1.7306452559102548,\"2006-11-10\"]," +
			          "[\"BAB\",4,5.165882140661617,\"2010-01-07\"]," +
			          "[\"аАа\",5,9.488429573267059,\"2003-10-03\"]," +
			          "[\"BBB\",6,5.373545842917705,\"2000-11-04\"]," +
			          "[\"aab\",5,3.0464715835168787,\"2004-01-02\"]," +
			          "[\"ббБ\",5,1.6871490327877292,\"2009-02-19\"]," +
			          "[\"ABb\",0,1.0193234019082942,\"2002-04-23\"]," +
			          "[\"BBa\",4,4.240214641484432,\"2010-03-11\"]," +
			          "[\"абА\",8,3.8004639845524424,\"2007-01-02\"]," +
			          "[\"бБа\",5,2.3350761845627312,\"2005-11-14\"]," +
			          "[\"бАа\",5,0.27459281273628067,\"2004-03-12\"]," +
			          "[\"бБб\",7,9.33174701701589,\"2004-12-13\"]," +
			          "[\"Aaa\",0,7.162369946379357,\"2007-01-01\"]," +
			          "[\"bbA\",3,3.0403276652994315,\"2003-03-04\"]," +
			          "[\"ABA\",3,7.364789708564769,\"2007-10-10\"]," +
			          "[\"bBa\",6,9.234142316635994,\"2002-09-19\"]," +
			          "[\"АаА\",9,6.174375598351075,\"2001-05-03\"]," +
			          "[\"АаА\",5,8.400790218789226,\"2009-12-16\"]," +
			          "[\"аАа\",3,6.720384215336496,\"2004-06-12\"]," +
			          "[\"Баб\",1,8.348297929759346,\"2005-02-19\"]," +
			          "[\"аБА\",2,8.515205420653157,\"2010-02-07\"]," +
			          "[\"БбА\",9,7.7835642055587275,\"2003-07-22\"]," +
			          "[\"ааБ\",1,5.46914540027557,\"2003-09-07\"]," +
			          "[\"бАа\",4,8.480280261770485,\"2011-10-18\"]," +
			          "[\"Bba\",8,2.240783059070842,\"2009-12-15\"]," +
			          "[\"bAA\",7,6.153515781438093,\"2003-06-07\"]," +
			          "[\"bAA\",2,0.29008305871346174,\"2002-08-21\"]," +
			          "[\"бАА\",4,2.6581580988548192,\"2009-10-06\"]," +
			          "[\"aaA\",4,7.501456449432819,\"2005-06-11\"]," +
			          "[\"bAA\",0,4.62839053595843,\"2005-01-23\"]," +
			          "[\"AaA\",1,7.7439542203731175,\"2008-01-19\"]," +
			          "[\"BAb\",5,6.175399034533479,\"2002-06-22\"]," +
			          "[\"bbA\",4,9.235783600946657,\"2011-12-06\"]," +
			          "[\"БАа\",8,5.760492241362804,\"2003-07-06\"]," +
			          "[\"AaA\",6,3.0803307243266884,\"2005-09-16\"]," +
			          "[\"АбБ\",8,9.860827244268556,\"2003-11-16\"]," +
			          "[\"БАб\",0,5.840882093163984,\"2006-07-15\"]," +
			          "[\"BAB\",7,8.54492142231862,\"2011-12-24\"]," +
			          "[\"АаА\",2,2.521228682190743,\"2000-05-20\"]," +
			          "[\"ABb\",6,0.5751450668303459,\"2011-10-16\"]," +
			          "[\"ABb\",0,8.819340721953457,\"2008-11-04\"]," +
			          "[\"АББ\",8,8.154377844150684,\"2002-01-21\"]," +
			          "[\"abB\",6,4.021302735285386,\"2010-07-05\"]," +
			          "[\"BBA\",1,3.934016678793025,\"2011-08-24\"]" +
			"]}";
	
	public static final String SQL_SELECT_COND = "{" +
	    "\"columns\":[" +
        "{\"name\":\"CSTR\",\"jtype\":\"java.lang.String\"}," +
        "{\"name\":\"CINT\",\"jtype\":\"java.lang.Integer\"}," +
        "{\"name\":\"CNUM\",\"jtype\":\"java.lang.Double\"}," +
        "{\"name\":\"CDATE\",\"jtype\":\"java.util.Date\"}]," +
			"\"data\":[" +
			    "[\"BBa\",3,6.363432849109767,\"2008-08-24\"]," +
			    "[\"аАА\",3,1.8543233750941412,\"2007-12-12\"]," +
			    "[\"бББ\",3,2.0654223581606,\"2008-07-03\"]," +
			    "[\"Bbb\",3,5.229165495026457,\"2010-06-12\"]," +
			    "[\"аБа\",3,5.705742717455145,\"2003-03-08\"]," +
			    "[\"bbA\",3,3.0403276652994315,\"2003-03-04\"]," +
			    "[\"ABA\",3,7.364789708564769,\"2007-10-10\"]," +
			    "[\"аАа\",3,6.720384215336496,\"2004-06-12\"]," +
			    "[\"БбБ\",4,2.955086552138356,\"2001-05-03\"]," +
			    "[\"Baa\",4,3.6063533556379546,\"2011-07-01\"]," +
			    "[\"AAA\",4,1.5496807189474338,\"2008-05-06\"]," +
			    "[\"bAA\",4,9.500128543247566,\"2008-01-04\"]," +
			    "[\"aba\",4,7.768139863126658,\"2008-06-21\"]," +
			    "[\"АБа\",4,1.7306452559102548,\"2006-11-10\"]," +
			    "[\"BAB\",4,5.165882140661617,\"2010-01-07\"]," +
			    "[\"BBa\",4,4.240214641484432,\"2010-03-11\"]," +
			    "[\"бАа\",4,8.480280261770485,\"2011-10-18\"]," +
			    "[\"бАА\",4,2.6581580988548192,\"2009-10-06\"]," +
			    "[\"aaA\",4,7.501456449432819,\"2005-06-11\"]," +
			    "[\"bbA\",4,9.235783600946657,\"2011-12-06\"]," +
			    "[\"AAb\",5,1.800337233825724,\"2000-10-25\"]," +
			    "[\"Ааб\",5,0.5613011579408855,\"2009-12-17\"]," +
			    "[\"Ббб\",5,3.2349031533386907,\"2005-02-22\"]," +
			    "[\"BbA\",5,9.077523504011257,\"2008-05-12\"]," +
			    "[\"ABa\",5,0.4067890892069337,\"2010-05-03\"]," +
			    "[\"abA\",5,7.0896026956224105,\"2010-10-14\"]," +
			    "[\"аАа\",5,9.488429573267059,\"2003-10-03\"]," +
			    "[\"aab\",5,3.0464715835168787,\"2004-01-02\"]," +
			    "[\"ббБ\",5,1.6871490327877292,\"2009-02-19\"]," +
			    "[\"бБа\",5,2.3350761845627312,\"2005-11-14\"]," +
			    "[\"бАа\",5,0.27459281273628067,\"2004-03-12\"]," +
			    "[\"АаА\",5,8.400790218789226,\"2009-12-16\"]," +
			    "[\"BAb\",5,6.175399034533479,\"2002-06-22\"]" +
			"]}";
	
	private static ContextConfig _cfg;
	
	public DsDaemonTest() {
    super(dcheck, DBMAP_ALIAS, DataSetDescr.class, 
              new String[] {"ds_empty.xml", "ds.xml"}, _fname, _ds_cfg);
  }

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	  init();
	  
	  loadDefLsFile();
	  
    // Create subdirectory test/csv
    File tcsv = new File(LocalTestConstants.WORK_TEST_PROJ + 
                                              File.separator + "csv");
    if (!tcsv.exists() && !tcsv.mkdir())
      fail("Unable create " + tcsv.getAbsolutePath() + " directory.");
    
    // Copy csv files
    String[] flist = JarUtils.readJarDirList(DsTestConstants.DS_MAPS_PACKAGE + 
                              "/" + LocalTestConstants.TEST_PRJ_NAME + "/csv");
    for (String fname: flist)
      JarTestResourceUtils.copyDemoFileToFile(LocalTestConstants.TEST_PRJ_NAME + 
          "/csv/" + fname, LocalTestConstants.WORK_TEST_PROJ  + 
                      File.separator + "csv" + File.separator + fname, _ds_cfg);
    
    // Create subdirectory bad
    td = new File(LocalTestConstants.WORK_BAD_PROJ);
    if (!td.exists() && !td.mkdir())
      fail("Unable create " + LocalTestConstants.WORK_BAD_PROJ + 
                                                      " directory.");
		
		// Default
		_cfg = new ContextConfig(0, LocalTestConstants.WORK_DS_DIR, 
		                              TestConstants.LOG, true, lcheck);
		
		// Start with non-existing file
		dcheck = new DsFilesCheck(base, LocalTestConstants.DAEMON_SCAN_TIME);
		AbstractResourceCheck._log = TestConstants.LOG;
		dcheck.start();
	}

	public static void loadDefLsFile() throws Exception {
	  // Copy demo ll_set to test project
    JarTestResourceUtils.copyDemoFileToFile(
        LsConstants.LANG_SET_FILE + "_combo", 
            LocalTestConstants.LS_SET_TEST_FILE_PATH, 
                LangLabelsDaemonTest.LS_CFG);
    
    LsResource lsr = null;
	  
    long dts = System.currentTimeMillis();
    while (true && (System.currentTimeMillis() - dts) < 
                                  TestConstants.WAIT_TIME) {
      
      lsr = lcheck.getResource(LocalTestConstants.TEST_LS_FILE_KEY );
      if (lsr != null)
        break;

      Thread.sleep(TestConstants.RESULT_CHECK_TIME);
    }

    assertNotNull("'" + LocalTestConstants.TEST_LS_FILE_KEY  +
        "' is not loaded " + "after " + 
                    TestConstants.WAIT_TIME + " msec of waiting.", lsr);
    
    BindingTest.checkLangLabelsSetCombined(lsr.getResource());
	}
	
	@AfterClass
	public static void setUpAfterClass() throws InterruptedException {
		// Stop daemon
		if (dcheck == null)
			return;
		dcheck.interrupt();
		dcheck.join();
		
		// Stop lcheck
		done();
		
	// Remove remaining ll_set
    if (!(new File(LocalTestConstants.LS_SET_TEST_FILE_PATH)).delete())
      fail("Unable delete " + LocalTestConstants.LS_SET_TEST_FILE_PATH);
	}

	@Before
	public void initTest() {
		warn = new ArrayList<String>();
		trace = new TraceRecorder(System.currentTimeMillis(), false);

		// Initial check
		assertNotNull(dcheck);
		assertNull(dcheck.getResource(DBMAP_ALIAS));
		
    // Restore default
    // WsInit.setMinfied(true);
		_cfg.setMinified(true);
	}

	@Test
	public void testVersions() {

		try {
			// Test same version
			dcheck.checkVersionsEx(new int[] { 2, 0 }, new int[] { 2, 0 });
			// Test lower major version
			dcheck.checkVersionsEx(new int[] { 1, 0 }, new int[] { 2, 0 });
			// Test lower minor version
			dcheck.checkVersionsEx(new int[] { 1, 8 }, new int[] { 1, 9 });
		} catch (WsSrvException e) {
			fail(e.toString());
		}

		// Test higher major version
		try {
			// Test same version
			dcheck.checkVersionsEx(new int[] { 2, 0 }, new int[] { 1, 8 });
		} catch (WsSrvException e) {
			assertEquals(e.getErrorCode(), 101);
		}

		// Test higher minor version
		try {
			dcheck.checkVersionsEx(new int[] { 1, 9 }, new int[] { 1, 8 });
		} catch (WsSrvException e) {
			assertEquals(e.getErrorCode(), 102);
		}
	}

	
  @Test
  public void testSingleReload() throws Exception {
    testSingleResourceReload();
  }
  
  @Test
  public void multiTestReload() throws Exception {
    multiTestResourceReload(DsDaemonTest.class.getName());
  }

	@Test
	public void testStaticTestLmapMinified() throws Exception {
		testStaticFile("static_test_lmap", false, STR_STATIC_TEST_LMAP_SINGLE);
		testFile("static_test_lmap", false, STR_STATIC_TEST_LMAP_SINGLE);
	}

	@Test
  public void testStaticTestLmapMinifiedGroup() throws Exception {
    // testFile("static_test_lmap_grp_single", false, STR_STATIC_TEST_LMAP_SINGLE);
    testFile("static_test_lmap_grp_multi", false, STR_STATIC_TEST_LMAP_MULTI);
  }
	
	@Test
  public void testStaticTestLmapMinifiedGroupCsv() throws Exception {
	  // Map includes csv file
    testFile("group_mixed", false, STR_STATIC_TEST_GROUP_MIXED);
  }
	
	@Test
  public void testStaticTestLmapMinifiedGrpComplex() throws Exception {
    testFile("static_test_lmap_grp_complex1", 
          true, STR_STATIC_TEST_LMAP_GRP_COMPLEX_1);
 
    testFile("static_test_lmap_grp_complex2", 
        true, STR_STATIC_TEST_LMAP_GRP_COMPLEX_2);
    
    testFile("static_test_lmap_grp_complex3", 
        true, STR_STATIC_TEST_LMAP_GRP_COMPLEX_3);
  }
	
	@Test
	public void testStaticAutoInc() throws Exception {
		testFile("static_test_lmap_auto_inc", false, 
										STR_STATIC_TEST_LMAP_AUTO_INC);
	}
	
	@Test
	public void testStaticCalc() throws Exception {
		testFile("static_test_lmap_calc", false, STR_STATIC_TEST_LMAP_CALC);
		testFile("static_test_calc_date1", false, STR_STATIC_TEST_CALC_DATE1);
		testFile("static_test_calc_date2", false, STR_STATIC_TEST_CALC_DATE2);
	}

	@Test
	public void testStaticTestLmapNonMinified() throws Exception {
		// Add trace
		// WsInit.setMinfied(false);
		_cfg.setMinified(false);
		
		testStaticFile("static_test_lmap", 
							false, STR_STATIC_TEST_LMAP_NON_MINIFIED_NO_TRACE);
		
		trace.setEnabled(true);
		warn.add("Hi \"there\"!!!");
		warn.add("Hello 'here'???");
		
		testFile("static_test_lmap", 
							false, STR_STATIC_TEST_LMAP_NON_MINIFIED);
		testFile("static_test_lmap_grp_single", 
							false, STR_STATIC_TEST_LMAP_NON_MINIFIED);
		
		warn = null;
		trace.setEnabled(false);
		testFile("static_test_lmap_grp_multi", 
						false, STR_STATIC_TEST_LMAP_EX_NON_MINIFIED);
	}
	
	@Test
  public void testStaticTestLmapNonMinifiedComplex() throws Exception {
	  // WsInit.setMinfied(false);
	  _cfg.setMinified(false);
	  testFile("static_test_lmap_grp_complex3", true, 
	            STR_STATIC_TEST_LMAP_COMPLEX_NON_MINIFIED);
	}
	
	@Test
	public void testStaticCalcBad1() throws Exception {
		testStaticCalcBad("static_test_calc_bad1", 115, 3);
	}
	
	@Test
	public void testStaticCalcBad2() throws Exception {
		testStaticCalcBad("static_test_calc_bad2", 107, 2);
	}
	
	@Test
	public void testStaticCalcBad3() throws Exception {
		testStaticCalcBad("static_test_calc_bad3", 114, 2);
	}
	
	public void testStaticCalcBad(String falias, int errCode, int errMsgCnt) 
	    throws Exception {
		try {
		  testBad(falias, false, "");
			fail("Exception expected");
		} catch (WsSrvException e) {
			assertEquals(errCode, e.getErrorCode());
			String[] dmsg = e.getDetailMsgs();
			assertNotNull(dmsg);
			assertEquals(errMsgCnt, dmsg.length);
		}
	}
	
	public void testStaticFile(String falias, boolean complex, String res) 
			throws IOException, InterruptedException, WsSrvException {
		DsDescrResource dsr = loadTestFile(falias);
		
		StaticDataSetProc sdsp = new StaticDataSetProc(
		                      new DsExtResource(dsr.getResource()), null, _cfg);
		sdsp.setDsDescrResource(dsr);
		StaticDataSetProducer sdpr = new StaticDataSetProducer(sdsp);
		
		assertEquals(res, sdpr.read(LocalTestConstants.TEST_PRJ_PREFIX + 
		    falias + ".xml", Constants.DEFAULT_LANG, 
									trace, warn).getJson(warn, trace));
	}

	@Test
	public void testStaticTestStr1() throws Exception {
		testFile("static_test_str1", true, STR_STATIC_TEST_STR1);
	}

	@Test
	public void testStaticTestStr2() throws Exception {
		String falias = "static_test_str2";
		DsDescrResource dsr = loadTestFile(falias);

		DataSetProcessor jdsp = new DataSetProcessor(dsr, null, _cfg);

		AbstractDataSetProc dsp = jdsp.getDataSetProc();
		assertTrue(falias + " is complex", dsp.isComplex());

		assertEquals(falias, STR_STATIC_TEST_STR_SORTED, jdsp.getJson(
		    LocalTestConstants.TEST_PRJ_PREFIX + falias + ".xml", null,
								                Constants.DEFAULT_LANG, trace, warn, false));
	}

	@Test
	public void testStaticTestStr3() throws Exception {
		String falias = "static_test_str3";
		DsDescrResource dsr = loadTestFile(falias);

		DataSetProcessor jdsp = new DataSetProcessor(dsr, null, _cfg);

		AbstractDataSetProc dsp = jdsp.getDataSetProc();
		assertTrue(falias + " is complex", dsp.isComplex());

		assertEquals(STR_STATIC_TEST_STR_SORTED, jdsp.getJson(
		    LocalTestConstants.TEST_PRJ_PREFIX + falias + ".xml", null,
								                  Constants.DEFAULT_LANG, trace, warn, false));
	}

	@Test
	public void testCsvStr1() throws Exception {
		testFile("csv_str1", false, CSV_STR1);
	}
	
	@Test
	public void testCsvSort1() throws Exception {
		testFile("csv_sort1", true, CSV_SORT1);
	}
	
	@Test
	public void testCsvSort2() throws Exception {
		testFile("csv_sort2", true, CSV_SORT2);
	}
	
  @Test
  public void testCsvSort3() throws Exception {
    testFile("csv_sort3", true, CSV_SORT3);
  }
	 
	@Test
	public void testCsvFilter1() throws Exception {
		testFile("csv_filter1", true, CSV_FILTER1);
	}
	
	@Test
	public void testCsvFilterComplex() throws Exception {
	  testFile("csv_filter_complex", true, CSV_FILTER_COMPLEX);
  }
	
	@Test
  public void testCsvFilterComplexNoHeader() throws Exception {
    testFile("csv_filter_complex_no_header", 
                        true, CSV_FILTER_COMPLEX_NO_HEADER);
  }
	
  @SuppressWarnings("deprecation")
  @Test
  public void testCsvFilterParams() throws Exception {
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("date_from", new Date("01/01/2007"));
    params.put("date_to", new Date("12/31/2007"));
    params.put("num_from", new Double(4));
    params.put("num_to", new Double(6));
    
    testFile("csv_filter_params", true, CSV_FILTER_COMPLEX, params);
  }

  @Test
  public void testCsvFilterParamsBad() throws Exception {
    HashMap<String, Object> params = new HashMap<String, Object>();
    
    try {
      testFile("csv_filter_params", true, CSV_FILTER_COMPLEX, params);
      fail("Exception expected");
    } catch (WsSrvException e) {
      assertEquals(110, e.getErrorCode());
    }
  }
  
  @Test
  public void testCsvBad() throws Exception {
    // testFile("csv_bad", false, CSV_BAD);
    testStaticCalcBad("csv_bad", 116, 2);
  }
  
  @SuppressWarnings("deprecation")
  // @Test
  public void testSqlFilterComplex() throws Exception {
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("date_from", new Date("03/08/2003"));
    params.put("date_to", new Date("03/31/2010"));
    params.put("num_from", new Double(4));
    params.put("num_to", new Double(6));
    params.put("f", new Integer(3));
    params.put("t", new Integer(5));
    
    JDBCDataSource ds = new JDBCDataSource();
    ds.setDatabase(LocalTestConstants.TEST_DB_NAME);
    ds.setUser(LocalTestConstants.TEST_DB_USER);
    ds.setPassword(LocalTestConstants.TEST_DB_PASSWORD);
    
    HashMap<String, String[]> sparams = new HashMap<String, String[]>();
    sparams.put("conn", new String[] {"hsqldb"});
    testSqlFile("sql_filter_complex", true, 
                            SQL_FILTER_COMPLEX, params, ds, sparams);
  }
  
  private void testFile(String falias, boolean complex, String res) 
                                                        throws Exception {
    testFile(falias, complex, res, null);
  }
  
  private void testFile(String falias, boolean complex, String res, 
                        HashMap<String, Object> params) throws Exception {
    testFile(falias, LocalTestConstants.TEST_PRJ_NAME, 
                                          complex, res, params);
  }
  
  private void testBad(String falias, boolean complex, 
                                             String res) throws Exception {
    testFile(falias, LocalTestConstants.BAD_PRJ_NAME, 
                                        complex, res, null);
  }
  
  private void testFile(String falias, String prefix, 
     boolean complex, String res, 
      HashMap<String, Object> params) throws Exception {

    DsDescrResource dsr = loadJarFile(prefix, falias + ".xml");
    
    DataSetProcessor jdsp = new DataSetProcessor(dsr, params, _cfg);
    
    AbstractDataSetProc dsp = jdsp.getDataSetProc();
    
    assertEquals("Complex check fail", complex, dsp.isComplex());
    
    String json = jdsp.getJson(prefix + "." + falias + ".xml", null, 
                   Constants.DEFAULT_LANG, trace, warn, _cfg.isMinified());
    
    if (trace.isEnabled())
    // Remove trace numbers
    json = json.replaceAll("\"duration\": [\\d\\.]*", "\"duration\": ");
    
    assertEquals("Test failed for " + falias, res, json);
  }
  
  private void testSqlFile(String falias, boolean complex, String res, 
      HashMap<String, Object> params, DataSource conn,
      Map<String,String[]> sparams) throws Exception {
    testSqlFile(falias, complex, res, params, conn, sparams, true);
  }
  
  private void testSqlFile(String falias, boolean complex, String res, 
      HashMap<String, Object> params, DataSource conn,
      Map<String,String[]> sparams, boolean minified) throws Exception {

    DsDescrResource dsr = loadTestFile(falias);

    DataSetProcessor jdsp = new DataSetProcessor(dsr, params, _cfg);
    
    SqlDataSetProc dsp = (SqlDataSetProc) jdsp.getDataSetProc();
    assertEquals(complex, dsp.isComplex());

    // Set connection
    dsp.setConnection(conn);
    
    String json = jdsp.getJson(LocalTestConstants.TEST_PRJ_PREFIX + 
      falias + ".xml", sparams, Constants.DEFAULT_LANG, trace, warn, 
                                                      _cfg.isMinified());

    if (trace.isEnabled())
        // Remove trace numbers
      json = json.replaceAll("\"duration\": [\\d\\.]*", "\"duration\": ");

    assertEquals(res, json);
  }

	private DsDescrResource loadJarFile(String prefix, String falias) 
	                                  throws IOException, InterruptedException {
		// Copy partial static_test_lmap.xml

		JarTestResourceUtils.copyDemoFileToFile(prefix + "/" + falias, 
		    LocalTestConstants.WORK_DS_DIR + prefix + 
		                        File.separator + falias, _ds_cfg);
		
		// Wait file loaded
		DsDescrResource dsr = null;
		long dts = System.currentTimeMillis();
		while (true && (System.currentTimeMillis() - dts) < 
											TestConstants.WAIT_TIME) {
			dsr = dcheck.getResource(prefix + "." + falias);

			if (dsr != null)
				break;

			Thread.sleep(TestConstants.RESULT_CHECK_TIME);
		}

		assertNotNull("'" + falias + "' is not loaded after " + 
						TestConstants.WAIT_TIME + " msec of waiting.", dsr);

		return dsr;
	}

	private DsDescrResource loadTestFile(String falias) 
	                        throws IOException, InterruptedException {
	  return loadJarFile(LocalTestConstants.TEST_PRJ_NAME, falias + ".xml");
	}
	
	/*
	private void testThreadsCompleted(CountDownLatch start, CountDownLatch done,
			String msg) throws InterruptedException {
		start.countDown();
		if (!done.await(TestConstants.WAIT_TIME, TimeUnit.MILLISECONDS))
			assertEquals(done.getCount() + " thread(s) hasn't been "
					+ "completed with " + msg, 0, done.getCount());
		assertEquals(ecnt + " thread(s) completed with errors", 0, ecnt);
	}
  */
	
	@Override
	protected DsDescrResource checkPartialTestResourceLoaded() throws Exception {
	  DsDescrResource dsr = super.checkPartialTestResourceLoaded();
	  
	  // Check for complex
    XmlDataSetProc dgp = new XmlDataSetProc(new DsExtResource(
                                              dsr.getResource()), null, _cfg);
    dgp.initComplex();
    assertFalse("Partial ds.xml is not complex", dgp.isComplex());
    
    return dsr;
	};
	
	@Override
	protected DsDescrResource checkFullTestResourceLoaded() throws Exception {
	  DsDescrResource dsr = super.checkFullTestResourceLoaded();
	  
	  // Check for complex
	  GroupDataSetProc dgp = new GroupDataSetProc(
        new DsExtResource(dsr.getResource()), null, _cfg);
    dgp.initComplex();
    assertTrue("Full ds.xml is complex", dgp.isComplex());
    
	  return dsr;
	}
	/*
	private void checkEmptyDsLoaded() throws InterruptedException,
			WsSrvException {
		DsDescrResource dsr = null;
		long dts = System.currentTimeMillis();
		while (true && (System.currentTimeMillis() - dts) < 
													        TestConstants.WAIT_TIME) {
			dsr = dcheck.getResource(DBMAP_ALIAS);

			if (dsr != null)
				break;

			Thread.sleep(TestConstants.RESULT_CHECK_TIME);
		}

		assertNotNull("'" + _fname + "' is not loaded " + "after " + 
						TestConstants.WAIT_TIME + " msec of waiting.", dsr);
		BindingTest.checkEmptyDs(dsr.getResource());

		// Check for complex
		XmlDataSetProc dgp = new XmlDataSetProc(new DsExtResource(
		                                          dsr.getResource()), null, _cfg);
		dgp.initComplex();

		assertFalse("Partial ds.xml is not complex", dgp.isComplex());
	}
  */
	
	/*
	private void checkFullDsReloaded() throws InterruptedException,
			WsSrvException {
		// Initial check
		DsDescrResource dsr = null;
		
		long dts = System.currentTimeMillis();
		while (dsr == null && (System.currentTimeMillis() - dts) < 
											TestConstants.WAIT_TIME) {
			dsr = dcheck.getResource(DBMAP_ALIAS);

			if (dsr == null)
				Thread.sleep(TestConstants.RESULT_CHECK_TIME);
		}
		
		assertNotNull("'" + _fname + "' is not re-loaded " + "after " + 
										TestConstants.WAIT_TIME + " msec of waiting.", dsr);
		
		assertTrue("Map is disabled", dsr.getResource().isEnabled());

		assertTrue("'" + _fname + "' is not re-loaded " + "after " + 
				TestConstants.WAIT_TIME + " msec of waiting.", 
				dsr.getResource().isEnabled());
		BindingTest.checkFullDs(dsr.getResource());

		// Check for complex
		GroupDataSetProc dgp = new GroupDataSetProc(
		    new DsExtResource(dsr.getResource()), null, _cfg);
		    
		dgp.initComplex();
		assertTrue("Full ds.xml is complex", dgp.isComplex());
	}
  */
	
	/*
	private void checkFullDsRemoved() throws InterruptedException {
		DsDescrResource dsr = dcheck.getResource(DBMAP_ALIAS);
		assertNotNull(dsr);

		long dts = System.currentTimeMillis();
		while ((dsr != null) && (System.currentTimeMillis() - dts) < 
													TestConstants.WAIT_TIME) {
			dsr = dcheck.getResource(DBMAP_ALIAS);
			Thread.sleep(TestConstants.RESULT_CHECK_TIME);
		}

		assertNull("'" + DBMAP_ALIAS + "' resource is not removed " + "after "
				+ TestConstants.WAIT_TIME + " msec of waiting.", dsr);
	}

	@Override
	public void run() {
		try {
			ss1.await();
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}

		// Check loaded
		try {
			checkEmptyDsLoaded();
		} catch (InterruptedException e1) {
			fail(e1.getMessage());
		} catch (WsSrvException e) {
			fail(e.getMessage());
		}

		ds1.countDown();

		try {
			ss2.await();
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}

		// Check reloaded
		try {
			checkFullDsReloaded();
		} catch (InterruptedException e) {
			fail(e.getMessage());
		} catch (WsSrvException e) {
			fail(e.getMessage());
		}

		ds2.countDown();

		try {
			ss3.await();
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}

		// Check removed
		try {
			checkFullDsRemoved();
		} catch (InterruptedException e1) {
			fail(e1.getMessage());
		}

		ds3.countDown();
	}
	*/
}
