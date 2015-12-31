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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import com.osbitools.ws.shared.WsSrvException;

import com.osbitools.ws.shared.binding.ds.*;

import com.osbitools.ws.core.daemons.DsExtResource;
import com.osbitools.ws.core.producers.AbstractDataSetProducer;
import com.osbitools.ws.core.producers.ds.AbstractDataSet;
import com.osbitools.ws.core.producers.ds.DataSetComplex;
import com.osbitools.ws.core.shared.ContextConfig;
import com.osbitools.ws.core.shared.CoreConstants;
import com.osbitools.ws.core.shared.TraceRecorder;

/**
 * Abstract class for DataSet that reads real data (not group based)
 *  
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class RealDataSetProc<T1 extends AbstractDataSetProducer<T3>, 
			T2 extends DataSetExt, T3 extends DataSetSpec> 
									extends AbstractDataSetProc {

	private Class<T1> _pt1;
	
	public RealDataSetProc(Class<T1> dsProducerType, 
	      DsExtResource dsExtResource, HashMap<String,Object> requestParameters, 
	        ContextConfig cfg) throws WsSrvException {
		super(dsExtResource, requestParameters, cfg);
		_pt1 = dsProducerType;
	}
	
	@Override
	public AbstractDataSet readDataSet(String name, String lang, TraceRecorder trace, 
			ArrayList<String> warn) throws WsSrvException {
	  
	  AbstractDataSet ds = null;
	  T1 producer = getProducer();
	  
	  try {
	    trace.record(CoreConstants.TRACE_DS_PROC_START);
	    
	    ds = producer.read(name, lang, trace, warn);
	    trace.record(CoreConstants.TRACE_DS_READ_DATA);

	    if (isComplex()) {
	      DataSetComplex dsc = (DataSetComplex) ds;
	      DataSetExt dse = getDataSetExt();
	      ConditionFilter filter = dse.getFilter();
	      SortGroup sort = dse.getSortByGrp();
	      if (filter != null) {
	        dsc.filter(filter);
	        trace.record(CoreConstants.TRACE_FILTER_COMPLETED);
	      }
	      
	      if (sort != null) {
	        dsc.sort(sort.getSortBy());
	        trace.record(CoreConstants.TRACE_SORT_COMPLETED);
	      }
	      
	      trace.record(CoreConstants.TRACE_DS_PROC_END);
	    }
	    
	    return ds;
	  } catch (WsSrvException e) {
	    
	    // On Error processing. 
	    Columns columns = getDataSetSpec().getColumns();
	    if (columns == null) 
	      throw e;
	    
	    ds = producer.makeNewDataSet(lang);
      ds.startData();
      
	    for (ColumnHeader col: columns.getColumn())
	      ds.addColumn(col.getName(), col.getJavaType());
	    
	    ds.endColumn();
	    
	    ds.startRecord();
	    
	    for (ColumnHeader col: columns.getColumn()) {
	      Object value = ds.createValue(col.getName(), col.getOnError());
        ds.addValue(col.getName(), value);
	    }
	    
	    ds.endRecordError();
	    ds.endData();
	    
	    e.setJsonObj(ds);
	    
	    throw e;
	  }
	}
	
	abstract public T3 getDataSetSpec();
	
	@SuppressWarnings("unchecked")
	public T1 getProducer() throws WsSrvException {
		Constructor<T1> c = (Constructor<T1>) _pt1.getConstructors()[0];
		T1 res = null;
		try {
			res = c.newInstance(this);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
		  //-- 104
			throw new WsSrvException(104, 
			    "Unable instantinate DataSet Producer of type: " + _pt1.getName(), e);
		}
		
		return res;
	}
}
