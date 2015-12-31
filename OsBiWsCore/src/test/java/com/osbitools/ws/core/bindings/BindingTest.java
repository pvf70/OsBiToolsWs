/*
 * Copyright 2014-2016 IvaLab Inc. and other contributors
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

package com.osbitools.ws.core.bindings;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.shared.binding.ll_set.min.*;
import com.osbitools.ws.core.shared.LocalTestConstants;

/**
 * Unit Test for binding xml files
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class BindingTest {
	
	// Handle for XML Unmarshaller
	private static Unmarshaller _ll;
	private static Unmarshaller _ds;
	
	// Test locales
	private static final String[] _tlangs = new String[] {"en", "fr", "ru"};
	
	private static DsMapTestResConfig _ds_cfg = new DsMapTestResConfig();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		_ll = JAXBContext.newInstance(LsConstants.BIND_PKG_LANG_LABELS_SET_MIN)
												                              .createUnmarshaller();
		
		_ds = JAXBContext.newInstance(DsConstants.BIND_PKG_DS_MAP)
												                          .createUnmarshaller();
	}

	@Test
	public void testBadXml() throws IOException {
	  ByteArrayInputStream bad_xml = 
	          new ByteArrayInputStream("Bad File".getBytes());
		try {
			_ll.unmarshal(bad_xml);
			fail("Exception expected.");
		} catch (JAXBException e) {
		}
	}
	
	@Test
	public void testLangLabels() throws JAXBException, IOException {
		
		InputStream[] flist = new InputStream[] {
        JarUtils.readJarFileAsStream(
                            LsTestConstants.LANG_SET_RES_PATH + "_test"),
		    JarUtils.readJarFileAsStream(LsTestConstants.LANG_SET_RES_PATH),
		    JarUtils.readJarFileAsStream(
		                LsTestConstants.LANG_SET_RES_PATH + "_combo")
		};
		
		LangLabelsSet[] lmap = new LangLabelsSet[flist.length];
		
		for (int i = 0; i < flist.length; i++) {
		  InputStream f = flist[i];
			JAXBElement<?> jaxb = (JAXBElement<?>) _ll.unmarshal(f);
			LangLabelsSet ls = (LangLabelsSet) jaxb.getValue();
			
			lmap[i] = ls;
      testLangsDef(ls);
		}
		
		checkLangLabelsSetPartial(lmap[0]);
		checkLangLabelsSetFull(lmap[1]);
		checkLangLabelsSetCombined(lmap[2]);
	}
	
	private void testLangsDef(LangLabelsSet ls) {

	  String langs = ls.getLangList();
	  assertNotNull(langs);
	  assertEquals("Language set doesn't match", 
                            _tlangs.length, langs.split(",").length);
	}
	
  public static void checkLangLabelsSetPartial(LangLabelsSet ll) {
    assertEquals("Total Size", 1, ll.getLangLabel().size());
    
    checkLabel(ll.getLangLabel().get(0), "LL_LETS_GO", 
          new String[] {"Let's go", "Allons-y", "Поехали"});
  }
  
  public static void checkLangLabelsSetFull(LangLabelsSet ll) {
    assertEquals("Total Size", 2, ll.getLangLabel().size());
    
    checkLabel(ll.getLangLabel().get(0), "LL_USERNAME", 
        new String[] {"Username", "Nom d'utilisateur", "Имя"});
    checkLabel(ll.getLangLabel().get(1), "LL_PASSWORD", 
        new String[] {"Password", "Mot de passe", "Пароль"});
  }
	
  public static void checkLangLabelsSetCombined(LangLabelsSet ll) {
    assertEquals("Total Size", 3, ll.getLangLabel().size());
    
    checkLabel(ll.getLangLabel().get(0), "LL_USERNAME", 
        new String[] {"Username", "Nom d'utilisateur", "Имя"});
    checkLabel(ll.getLangLabel().get(1), "LL_PASSWORD", 
        new String[] {"Password", "Mot de passe", "Пароль"});
    checkLabel(ll.getLangLabel().get(2), "LL_LETS_GO", 
        new String[] {"Let's go", "Allons-y", "Поехали"});
  }
  
	private static void checkLabel(LangLabel label, 
	                              String ids, String[] lvalues) {
	  
		assertEquals(ids, label.getId());
		
		for (int i = 0; i < _tlangs.length; i++) {
			LangLabelDef ld = label.getLlDef().get(i);
		
			assertEquals(_tlangs[i], ld.getLang());
			assertEquals(lvalues[i], ld.getValue());
		}
	}
	
	@Test
	public void testDs() throws JAXBException, IOException {
		
		String[] flist = new String[] {LocalTestConstants.DS_XML, 
		                                LocalTestConstants.DS_EMPTY_XML};
		
		HashMap<String, DataSetDescr> dmap = 
				new HashMap<String, DataSetDescr>();
		
		for (String fname: flist) {
			JAXBElement<?> jaxb = (JAXBElement<?>) _ds
				.unmarshal(JarTestResourceUtils.readDemoFileAsStream(fname, _ds_cfg));
			dmap.put(fname, (DataSetDescr) jaxb.getValue());
		}
		
    checkDataSetDescrPartial(dmap.get(LocalTestConstants.DS_EMPTY_XML));
    checkDataSetDescrFull(dmap.get(LocalTestConstants.DS_XML));
	}

	@Test
	public void testStaticLangDs() throws JAXBException, IOException {
		JAXBElement<?> jaxb = (JAXBElement<?>) _ds.unmarshal(JarTestResourceUtils.
		                readDemoFileAsStream("test/static_test_lmap.xml", _ds_cfg));
		
		DataSetDescr dsd = (DataSetDescr) jaxb.getValue();
		
		StaticData sdf = dsd.getStaticData();
		assertEquals(1, sdf.getColumns().getColumn().size());
		assertEquals(2, sdf.getStaticRows().getRow().size());

		LangMap lm = dsd.getLangMap();
		assertNotNull(lm);
		assertEquals(1, lm.getColumn().size());
	}
	
	@Test
	public void testStaticStr1() throws JAXBException, IOException {
		JAXBElement<?> jaxb = (JAXBElement<?>) _ds
				.unmarshal(JarTestResourceUtils.readDemoFileAsStream(
				                      "test/static_test_str1.xml", _ds_cfg));
		
		DataSetDescr dsd = (DataSetDescr) jaxb.getValue();
		
		SortGroup sg = dsd.getSortByGrp();
		assertNotNull(sg);
		assertEquals(1, sg.getSortBy().size());
	}
	
	public static void checkDataSetDescrPartial(DataSetDescr ds) {
		assertEquals("Description", "Empty DataSet", ds.getDescr());
		assertEquals("Enabled", false, ds.isEnabled());
		assertEquals("Major Version", 1, ds.getVerMax());
		assertEquals("Minor Version", 0, ds.getVerMin());
		
		assertNull(ds.getLangMap());
		assertNull(ds.getExColumns());
		assertNull(ds.getFilter());
		assertNull(ds.getSortByGrp());
	}
	
	public static void checkDataSetDescrFull(DataSetDescr ds) {
		assertEquals("Description", "Test DataSet", ds.getDescr());
		assertEquals("Enabled", true, ds.isEnabled());
		
		LangMap lmap = ds.getLangMap();
		assertNotNull("lang_map tag check", lmap);
		List<LangColumn> lc = lmap.getColumn();
		assertNotNull("lang_map->column(s) tag check", lc);
		assertEquals("Lang Map Size", 2, lc.size());
		assertEquals("COL1 Lang Column not found", "COL1", lc.get(0).getName());
		assertEquals("COL2 Lang Column not found", "COL2", lc.get(1).getName());
		
		ExColumns ec = ds.getExColumns();
		assertNotNull("ex_columns tag check", ec);
		
		AutoIncColumns lai = ec.getAutoInc();
		assertNotNull("ex_columns->auto_inc tag check", lai);
		List<AutoIncColumn> ai = lai.getColumn();
		assertNotNull("ex_columns->auto_inc->column(s) tag check", ai);
		assertEquals("Auto Inc Column Size", 2, ai.size());
		assertEquals("Auto Inc Column #1 Name", "A11", ai.get(0).getName());
		assertEquals("Auto Inc Column #2 Name", "B22", ai.get(1).getName());
		
		CalcColumns lcc = ec.getCalc();
		assertNotNull("ex_columns->calc tag check", lcc);
		List<CalcColumn> cc = lcc.getColumn();
		assertNotNull("ex_columns->calc->column(s) tag check", lc);
		assertEquals("Calculated Column Size", 2, cc.size());
		assertEquals("Calculated Column #1 Name", "CALC1", cc.get(0).getName());
		assertEquals("Calculated Column #1 Formula", "A + B", cc.get(0).getValue());
		assertEquals("Calculated Column #2 Name", "CALC2", cc.get(1).getName());
		assertEquals("Calculated Column #2 Formula", "C + D", cc.get(1).getValue());
		
		SortGroup fs = ds.getSortByGrp();
		ConditionFilter cf = ds.getFilter();
		assertEquals("Condition", "A < B", cf.getValue());
		
		List<SortCond> sff = fs.getSortBy();
		checkFilter(sff.get(0), 1, "COL1", SortTypes.ASC, false);
		checkFilter(sff.get(1), 2, "COL2", SortTypes.DESC, false);
		
		assertNull("Non-existing static_ds", ds.getStaticData());
		// Check recursion
		GroupData dsg = ds.getGroupData();
		
		assertNotNull("group_ds tag check", dsg);
		List<DataSetExt> ldsd1 = dsg.getDsList().getGroupDsOrStaticDsOrCsvDs();
		assertEquals("DsGroup #1 size", 2, ldsd1.size());
		
		// Check nested group
    DataSetExt dse = ldsd1.get(0);
    assertEquals(dse.getClass(), GroupDataSetDescr.class);
    List<DataSetExt> ldsd2 = ((GroupDataSetDescr) dse).getGroupData().
                          getDsList().getGroupDsOrStaticDsOrCsvDs();
    assertEquals("DsGroup #2 size", 4, ldsd2.size());
    
    // Check just class of each element
    assertEquals(ldsd2.get(0).getClass(), GroupDataSetDescr.class);
    assertEquals(ldsd2.get(1).getClass(), StaticDataSetDescr.class);
    assertEquals(ldsd2.get(2).getClass(), CsvDataSetDescr.class);
    assertEquals(ldsd2.get(3).getClass(), SqlDataSetDescr.class);
    
    dse = ldsd1.get(1);
		assertEquals(dse.getClass(), StaticDataSetDescr.class);
    StaticDataSetDescr dsd = (StaticDataSetDescr) dse;

		StaticDataSetDescr sds = dsd;
		assertNotNull("group_ds->static_ds tag check", sds);
		StaticData sdf = sds.getStaticData();
		assertNotNull("static_ds->static_set tag check", sdf);
		Columns cs = sdf.getColumns();
		assertNotNull("static_ds->columns tag check", cs);
		List<ColumnHeader> ch = cs.getColumn();
		assertNotNull("columns->column tag check", ch);
		assertEquals(2, ch.size());
		checkColumnHeader(ch.get(0), "COL1", 
		                "java.lang.String", "ERROR GRP 2 !!!");
		checkColumnHeader(ch.get(1), "COL2", 
		                "java.lang.String", "ERROR GRP 2 !!!");
		
		StaticRecords sr = sdf.getStaticRows();
		assertNotNull("static_ds->static tag check", dsg);
		List<RowDef> lrd = sr.getRow();
		assertNotNull("static tag->row(s) check", dsg);
		assertEquals("Static Rows size", 2, lrd.size());
		
		RowDef srd = lrd.get(0);
		List<RowCell> lcd = srd.getCell();
		assertNotNull("row#1>column(s) check", lcd);
		assertEquals("Static Columns size", 2, lcd.size());
		
		checkRowColumn(lcd.get(0), 1, 1, "COL1", "bBb");
		checkRowColumn(lcd.get(1), 1, 2, "COL2", "УуУ");

		srd = lrd.get(1);
		lcd = srd.getCell();
		assertNotNull("row#2>column(s) check", lcd);
		checkRowColumn(lcd.get(0), 2, 1, "COL1", "AaA");
		checkRowColumn(lcd.get(1), 2, 2, "COL2", "пПп");
	}
	
	public static void checkColumnHeader(ColumnHeader ch, 
										String name, String type, String onError) {
		assertEquals(name, ch.getName());
		assertEquals(type, ch.getJavaType());
		assertEquals(onError, ch.getOnError());
	}
	
	public static void checkFilter(SortCond sc, int idx, String column, SortTypes stype, boolean fgrp) {
		assertEquals(idx, sc.getIdx());
		assertEquals(column, sc.getColumn());
		assertEquals(stype, sc.getSequence());
		assertEquals(fgrp, sc.isUnique());
	}
	
	public static void checkRowColumn(RowCell cd, int row, 
										int col, String name, String value) {
		assertEquals("Row #" + row + " Column #" + col + " Name", 
		                                            name, cd.getName());
		assertEquals("Row #" + row + " Column #" + col + " Value", 
		                                            value, cd.getValue());		
	}
}
