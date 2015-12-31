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

package com.osbitools.ws.core.daemons;

import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.core.proc.AbstractDataSetProc;

/**
 * Implementation of ResourceInfo for DataSet Description 
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class DsExtResource {

	// Hash set with indexed language columns
  private LangSet _lset;
	
	// Handle for DataSetExt
	private DataSetExt _dse;
	
	// Handle for corresponded DataSet processor
	private AbstractDataSetProc _dproc;
	
	public DsExtResource(DataSetExt dsExt) {
		_dse = dsExt;
		
		// Set LangSet
		setLangSet();
	}
	
	public void setLangSet() {
		_lset = new LangSet(_dse);		
	}

	public LangSet getLangColumns() {
		return _lset;
	}
	
	public DataSetExt getDataSetExt() {
		return _dse;
	}
	
	public AbstractDataSetProc getDataSetProc() {
		return _dproc;
	}
	
	public void setDataSetProc(AbstractDataSetProc dsProc) {
		_dproc = dsProc;
	}

}