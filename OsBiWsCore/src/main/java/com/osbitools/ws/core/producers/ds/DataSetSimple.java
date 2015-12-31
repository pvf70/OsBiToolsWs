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

import com.osbitools.ws.shared.*;
import com.osbitools.ws.core.producers.AbstractDataSetProducer;

/**
 * Build for cases when performance is important.
 * Simple DataSet producer. Used to cases when no post-processing required.
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class DataSetSimple extends AbstractDataSet {
	
	// Data
	String _data = "";
	
	// Current record
	String _rec;
	
	// For test purposes
	public DataSetSimple() {
		super(null, null, Constants.DEFAULT_MINIFIED);
	}
	
	public DataSetSimple(AbstractDataSetProducer<?> dsProd, 
								String lang, boolean minified) {
		super(dsProd, lang, minified);
	}
		
	@Override
	public void addLocaleValue(String column, Object value, String lang) {
	  // Check if column type is number type and optionally add double quotes
		_rec += "," +  (Number.class.isAssignableFrom(getColumnClass(column)) ? 
                                        value.toString() : "\"" + value + "\"");
	}
	
	@Override
	public void startRecord() {
		super.startRecord();
		_rec = "";
		_data += "," + Utils.getCR(isMinified()) + Utils.getTT(isMinified()) + "[";
	}

	@Override
	public void endRecord(boolean isError) throws WsSrvException {
		super.endRecord(isError);
		_data += _rec.substring(1) + "]";
	}

	@Override
	public void appendData(AbstractDataSet ds) {
		_data += "," + ((DataSetSimple) ds).getDataAsString();
		_cnt += ds.getRecordCount();
	}

	@Override
	public String getDataAsString() {
		return Utils.isEmpty(_data) ? _data : _data.substring(1);
	}

	public void setData(String data) {
		_data = data;
	}
}
