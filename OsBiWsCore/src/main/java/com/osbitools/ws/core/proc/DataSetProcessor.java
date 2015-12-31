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

import java.util.*;

import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;
import com.osbitools.ws.core.daemons.DsDescrResource;
import com.osbitools.ws.core.daemons.DsExtResource;
import com.osbitools.ws.core.producers.ds.AbstractDataSet;
import com.osbitools.ws.core.shared.ContextConfig;
import com.osbitools.ws.core.shared.TraceRecorder;

/**
 * Abstract JSON DataSet producer
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class DataSetProcessor extends AbstractDataSetProc {
	
	// Handle for active DataSet producer
	AbstractDataSetProc _dsp;
	
	public DataSetProcessor(DsDescrResource dsr, 
	    HashMap<String,Object> requestParameters, ContextConfig cfg) 
	                                            throws WsSrvException {
		super(null, requestParameters, cfg);
		
		setDsDescrResource(dsr);
		DataSetDescr dsd = dsr.getResource();
		
		if (dsd.getGroupData() != null) {
			_dsp = new GroupDataSetProc(
			          new DsExtResource(dsd), requestParameters, cfg);
		} else if (dsd.getStaticData() != null) { 
			_dsp = new StaticDataSetProc(
			          new DsExtResource(dsd), requestParameters, cfg);
		} else if (dsd.getCsvData() != null) { 
			_dsp = new CsvDataSetProc(new DsExtResource(dsd), requestParameters, cfg);
		} else if (dsd.getSqlData() != null) { 
      _dsp = new SqlDataSetProc(new DsExtResource(dsd), requestParameters, cfg);
		} else if (dsd.getXmlData() != null) { 
      _dsp = new XmlDataSetProc(new DsExtResource(dsd), requestParameters, cfg);
		}
		
		if (_dsp == null)
		  //-- 105
			throw new WsSrvException(105, "DataSet processor is not defined");
		
		_dsp.setDsDescrResource(dsr);
		setDsExtResource(new DsExtResource(dsd));
	}	
	
	@Override
	boolean checkComplex() throws WsSrvException {
	  return  _dsp.checkComplex() || super.checkComplex();
	}
	
	@Override
	public void initComplex() throws WsSrvException {
	  super.initComplex();
	  
	  if (isComplex())
      // Propagate positive complex flag
      _dsp.setComplex(true);
	}
	
	@Override
	public void validateRequestParams(Map<String,String[]> params) 
	                                                    throws WsSrvException {
	  _dsp.validateRequestParams(params);
	}
	
	@Override
	public AbstractDataSet readDataSet(String name, String lang, 
	    TraceRecorder trace, ArrayList<String> warn) throws WsSrvException {
	  return _dsp.readDataSet(name, lang, trace, warn);
	}
	
	public AbstractDataSetProc getDataSetProc() {
		return _dsp;
	}

	
}
