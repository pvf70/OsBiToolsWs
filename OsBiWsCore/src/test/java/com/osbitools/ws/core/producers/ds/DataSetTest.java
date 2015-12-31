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

package com.osbitools.ws.core.producers.ds;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.shared.WsSrvException;

public class DataSetTest {

	static AbstractDataSet ds;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ds = new DataSetSimple();
		
		ds.addColumn("TEXT", "java.lang.String");
		ds.addColumn("INT", "java.lang.Integer");
		ds.addColumn("DOUBLE", "java.lang.Double");
		ds.addColumn("FLOAT", "java.lang.Float");
		ds.addColumn("DATE", "java.util.Date");
	}

	@Test
	public void testWrongJavaType() {
		try {
			ds.addColumn("ZZZ", "java.util.zzz");
		} catch (WsSrvException e) {
			assertEquals(107, e.getErrorCode());
		}
		
		try {
			ds.createValue("ZZZ", "zzz");
		} catch (WsSrvException e) {
			assertEquals(109, e.getErrorCode());
		}
	}
	
	@Test
	public void testString() throws WsSrvException {
		
		ds.createValue("TEXT", "text");
		ds.createValue("TEXT", "1");
		ds.createValue("TEXT", "1.11");
	}
	
	@Test
	public void testInteger() throws WsSrvException {
		try {
			ds.createValue("INT", "text");
		} catch (WsSrvException e) {
			assertEquals(110, e.getErrorCode());
		}
		
		try {
			ds.createValue("INT", "1.11");
		} catch (WsSrvException e) {
			assertEquals(110, e.getErrorCode());
		}
		
		try {
			ds.createValue("INT", "1.0");
		} catch (WsSrvException e) {
			assertEquals(110, e.getErrorCode());
		}
		
		ds.createValue("INT", "1");
	}
	
	@Test
	public void testDouble() throws WsSrvException {
		try {
			ds.createValue("DOUBLE", "text");
		} catch (WsSrvException e) {
			assertEquals(110, e.getErrorCode());
		}
		ds.createValue("DOUBLE", "1");
		ds.createValue("DOUBLE", "1.11");
	}
	
	@Test
	public void testFloat() throws WsSrvException {
		try {
			ds.createValue("FLOAT", "text");
		} catch (WsSrvException e) {
			assertEquals(110, e.getErrorCode());
		}
		ds.createValue("FLOAT", "1");
		ds.createValue("FLOAT", "1.11");
	}
	
	@Test
	public void testDate() throws WsSrvException {
		try {
			ds.createValue("DATE", "text");
		} catch (WsSrvException e) {
			assertEquals(110, e.getErrorCode());
		}
		
		try {
			ds.createValue("DATE", "1");
		} catch (WsSrvException e) {
			assertEquals(110, e.getErrorCode());
		}
		
		ds.createValue("DATE", "12/31/2000");
	}

}
