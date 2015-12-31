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

package com.osbitools.ws.core.producers;

import java.util.ArrayList;

import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.core.proc.AbstractDataSetProc;
import com.osbitools.ws.core.proc.StaticDataSetProc;
import com.osbitools.ws.core.producers.ds.AbstractDataSet;
import com.osbitools.ws.core.shared.TraceRecorder;

/**
 * DataSet Producer for static records source
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class StaticDataSetProducer extends AbstractDataSetProducer<StaticData> {

	public StaticDataSetProducer(AbstractDataSetProc dsProc) {
		super(dsProc);
	}

	@Override
	StaticData getDataSetSpec() {
		return ((StaticDataSetProc) getDataSetProc()).getDataSetSpec();
	}
	
	@Override
	public AbstractDataSet read(String name, String lang, TraceRecorder trace, 
						                  ArrayList<String> warn) throws WsSrvException {
		AbstractDataSet d = makeNewDataSet(lang);
		StaticData sd = getDataSetSpec();
		
		d.startData();
		
		for (ColumnHeader ch: sd.getColumns().getColumn())
			d.addColumn(ch.getName(), ch.getJavaType());
		d.endColumn();
		
		for (RowDef rd: sd.getStaticRows().getRow()) {
			d.startRecord();
			
			for (RowCell cd: rd.getCell()) {
				Object value = d.createValue(cd.getName(), cd.getValue());
				d.addValue(cd.getName(), value);
			}
			
			d.endRecordSuccess();
		}
		
		d.endData();
		
		return d;
	}
}
