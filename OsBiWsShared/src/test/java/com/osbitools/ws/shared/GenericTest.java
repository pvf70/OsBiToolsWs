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

package com.osbitools.ws.shared;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;

import org.junit.Test;

import com.osbitools.util.Date;
import com.osbitools.ws.shared.Constants;
import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.WsSrvException;

/**
 * Test differen ID combinations
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class GenericTest {
	 
	public static final String[] BAD_ID_LIST = new String[] {
		"`","~","!","@","#","$","%","^","&","(",")",
		    "_","-","+","=","[","]","{","}","\"","'"
		    // ,"__","___"
	};
	
	public static final String[] BAD_ID_NAMES = new String[] {
		"@#~~&","#*@``","=&*$_","($#~%","%^=(_","$`~`$","#+_+(","%!(^~",")(%+_",
		"@`^()","^_##_","(+^=%","=%^(_","))=*%","_#@`&","*%=_%","_*@((","%%%##",
		"#((++",")-^#=",")%@+_","*`(*+","$!&_%","##$&@","-*(~(","@)#!@","~-=!-",
		"=^!+#","=+_^+","-=&+*","*(_(-","($~#-","_=!%+","*-#-^","()&@!","(+^*_",
		"_$_**","_@)-#","~~%%*","~@!!(","`#&=)","*~=(!","_*@_^","!^%`%","$)*_#",
		"``!-#","-)_=^","!@#_+","@$#$=",")~*(!","_&(_*","%(^)_","))_=_","*)+*_",
		"+$@-$","&#_++","^$+#)","`&-@(","@!-!+","(=_$$","^+^^~","_^%~_","%%&$@",
		")~)%+","(-=_+","$^`(&","&~~+@","&++^@","%%_*=","^_-=)","%(-_)","~`^!_",
		"%*)+$","$`@!&","_%=@!","+`&#@","*~#$)","$_*!=","%_)@+","#(&_-","(!&=)",
		"#*&-^","@_^=$","^*%#)","@)~#*","=$@(`","$=*#`","_(##^","^^@(+","+$&@%",
		"$*$)&","-`*+#","*#~!`","**`(&","_$`+$","#-#^*","****`","##@-=","!@=%@",
		"=$%=$"
	};
	
	public static final String[] GOOD_ID_LIST = new String[] {
		"qWeRtY", "qq_QQ", "qq__QQ",
		"_q_","_qq","_qQ","_qw","_qW","_Q_","_Qq","_QQ","_Qw","_QW","_w_","_wq",
		"_wQ","_ww","_wW","_W_","_Wq","_WQ","_Ww","_WW","q__","q_q","q_Q","q_w",
		"q_W","qq_","qqq","qqQ","qqw","qqW","qQ_","qQq","qQQ","qQw","qQW","qw_",
		"qwq","qwQ","qww","qwW","qW_","qWq","qWQ","qWw","qWW","Q__","Q_q","Q_Q",
		"Q_w","Q_W","Qq_","Qqq","QqQ","Qqw","QqW","QQ_","QQq","QQQ","QQw","QQW",
		"Qw_","Qwq","QwQ","Qww","QwW","QW_","QWq","QWQ","QWw","QWW","w__","w_q",
		"w_Q","w_w","w_W","wq_","wqq","wqQ","wqw","wqW","wQ_","wQq","wQQ","wQw",
		"wQW","ww_","wwq","wwQ","www","wwW","wW_","wWq","wWQ","wWw","wWW","W__",
		"W_q","W_Q","W_w","W_W","Wq_","Wqq","WqQ","Wqw","WqW","WQ_","WQq","WQQ",
		"WQw","WQW","Ww_","Wwq","WwQ","Www","WwW","WW_","WWq","WWQ","WWw","WWW"
	};
	
	private static final String[] DATES = {"1950-01-01", "1950-01-01 13:05:55", 
    "1970-01-01", "1970-01-01 11:00:15", "1999-12-31", "1999-12-31 23:59:59",
    "2000-01-01", "2000-01-01 00:00:00", "2008-11-16", "2008-11-16 14:37:15"
	};
	
	@Test
	public void testBadId() {
		for (String id: BAD_ID_LIST)
			assertEquals("ID: " + id, false, 
			    Constants.ID_PATTERN.matcher(id).matches());
	}

	@Test
	public void testGoodId() {
		for (String id: GOOD_ID_LIST)
			assertEquals("ID: " + id, true, 
			    Constants.ID_PATTERN.matcher(id).matches());
	}
	
	@Test
	public void testFileDir() throws WsSrvException {
	  assertEquals("test", GenericUtils.getFileDir(
	              TestConstants.SRC_DIR, "test.config").getName());
	   assertEquals("src", GenericUtils.getFileDir(
                 TestConstants.SRC_DIR, "test").getName());
	}
	
	@Test
	public void testExtListFilter() {
	  ExtListFileFilter filter = new ExtListFileFilter(
	                                new String[] {"a", "bb", "ccc"});
	  
	  assertTrue(filter.accept(new File("."), "a.a"));
    assertTrue(filter.accept(new File("."), "b.bb"));
    assertTrue(filter.accept(new File("."), "c.ccc"));
    assertTrue(filter.accept(new File("."), "qq.a"));
    
    assertFalse(filter.accept(new File("."), ".a"));
    assertFalse(filter.accept(new File("."), "x.abc"));
    assertFalse(filter.accept(new File("."), "y.bbc"));
    assertFalse(filter.accept(new File("."), ".ccc"));
    assertFalse(filter.accept(new File("."), "123"));
    
    filter = new ExtListFileFilter(new String[] {"aaa", "bbb"});
    assertTrue(filter.accept(new File("."), "x.aaa"));
    assertTrue(filter.accept(new File("."), "yy.aaa"));
    assertTrue(filter.accept(new File("."), "qq.bbb"));
    assertTrue(filter.accept(new File("."), "zzz.aaa"));
    
    assertFalse(filter.accept(new File("."), "ww.ccc"));
    assertFalse(filter.accept(new File("."), ".aaa"));
    assertFalse(filter.accept(new File("."), ".aaaa"));
	}
	
	@Test
	public void testDate() throws ParseException {
	  for (String ds: DATES)
	    assertEquals(ds, (new Date(ds)).toString());
	}
}
