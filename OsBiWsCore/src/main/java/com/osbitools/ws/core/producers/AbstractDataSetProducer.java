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

import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.core.daemons.LangSet;
import com.osbitools.ws.core.proc.AbstractDataSetProc;

import com.osbitools.ws.core.producers.ds.*;
import com.osbitools.ws.core.shared.TraceRecorder;

/**
 * Abstract DataSet Producer
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class AbstractDataSetProducer<T> {
	// DataSet handle
	AbstractDataSetProc _dsp;

	public AbstractDataSetProducer(AbstractDataSetProc dsProc) {
		_dsp = dsProc;
	}
	
	/**
	 * 
	 * @param name Name of spec file
	 * @param lang Required language
	 * @param trace Trace handler
	 * @param warn Warn handler
	 * @return
	 * @throws WsSrvException
	 */
	public abstract AbstractDataSet read(String name, String lang, 
	      TraceRecorder trace, ArrayList<String> warn) throws WsSrvException;

	abstract T getDataSetSpec();
	
	public LangSet getLangColumns() {
		return _dsp.getDsExtResource().getLangColumns();
	}
	
	public AbstractDataSetProc getDataSetProc() {
		return _dsp;
	}
	
	public AbstractDataSet makeNewDataSet(String lang) {
		return (_dsp.isComplex()) ? 
				new DataSetComplex(this, lang, getDataSetProc().getCfg().isMinified()) : 
				new DataSetSimple(this, lang, getDataSetProc().getCfg().isMinified());
	}
	
	public String findLocaleValue(String lang, String value) {
	  return getDataSetProc().getCfg().getLangLabelsCheck().
	      getLangLabel(getDataSetProc().getDsDescrResource().
	                                    getProjectName(), lang, value);
	}
	
	public void debug(String msg) {
	  getDataSetProc().debug(msg);
	}
}
