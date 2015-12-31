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

package com.osbitools.ws.core.proc;

import java.util.HashMap;

import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.core.daemons.DsExtResource;
import com.osbitools.ws.core.producers.CsvDataSetProducer;
import com.osbitools.ws.core.shared.ContextConfig;

/**
 * Csv DataSet Processor
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class CsvDataSetProc extends RealDataSetProc<CsvDataSetProducer, 
					CsvDataSetDescr, CsvData> {

	public CsvDataSetProc(DsExtResource dsExtResource, 
	      HashMap<String,Object> requestParameters, ContextConfig cfg) 
	                                                  throws WsSrvException {
		super(CsvDataSetProducer.class, dsExtResource, requestParameters, cfg);
	}

	@Override
	public CsvData getDataSetSpec() {
		DataSetExt dse = getDataSetExt();
		return (dse.getClass().equals(DataSetDescr.class)) ?
			((DataSetDescr) dse).getCsvData() :
			((CsvDataSetDescr) dse).getCsvData();
	}

}
