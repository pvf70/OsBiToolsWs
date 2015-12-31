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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.core.daemons.DsDescrResource;
import com.osbitools.ws.core.daemons.DsExtResource;
import com.osbitools.ws.core.daemons.LangSet;
import com.osbitools.ws.core.producers.ds.AbstractDataSet;
import com.osbitools.ws.core.producers.ds.DataSetComplex;
import com.osbitools.ws.core.shared.ContextConfig;
import com.osbitools.ws.core.shared.CoreConstants;
import com.osbitools.ws.core.shared.TraceRecorder;

/**
 * Abstract class to handle DataSetExt processing including sorting and
 * filtering
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public abstract class AbstractDataSetProc {
  // DataSetExtension handle
  private DsExtResource _dse;

  // Complex flag
  private boolean _complex;

  // Extrac columns flag
  private boolean _exc;

  // Calculated columns
  private List<CalcColumn> _calc;

  // AutoInc columns
  private List<AutoIncColumn> _auto;

  // List of parsed request parameters
  private HashMap<String,Object> _rparams;
  
  // Context configuration
  private ContextConfig _cfg;
  
  // Configurable columns
  List<ColumnHeader> _columns;
  
  // Pointer on top resource file
  private DsDescrResource _dsr;
  
  public AbstractDataSetProc(DsExtResource dsExtResource, 
            HashMap<String,Object> requestParameters, 
                        ContextConfig cfg) throws WsSrvException {
    _cfg = cfg;
    _dse = dsExtResource;
    _rparams = requestParameters;

    ExColumns exc = (_dse != null) ? _dse.getDataSetExt().getExColumns() : null;
    _exc = (exc != null);

    if (_exc) {
      CalcColumns calc = exc.getCalc();
      if (calc != null)
        _calc = calc.getColumn();

      AutoIncColumns auto = exc.getAutoInc();
      if (auto != null)
        _auto = auto.getColumn();
    }
  }

  /**
   * Read DataSet
   * 
   * @param name Name of spec file
   * @param lang Required language
   * @param trace Trace handler
   * @param warn Warn handler
   * @return DataSet
   * @throws WsSrvException
   */
  public abstract AbstractDataSet readDataSet(String name, String lang, 
        TraceRecorder trace, ArrayList<String> warn) throws WsSrvException;

  public void validateRequestParams(Map<String,String[]> params) 
                                              throws WsSrvException {
    // Do nothing
  }

  public AbstractDataSet read(String name, Map<String,String[]> params, 
      String lang, TraceRecorder trace, ArrayList<String> warn) 
                                                    throws WsSrvException {
    trace.record(CoreConstants.TRACE_START_PROC);

    // 1. Validate request first
    validateRequestParams(params);

    // 2. Read dataset
    AbstractDataSet ds = readDataSet(name, lang, trace, warn);
    trace.record(CoreConstants.TRACE_READ_DATA);

    if (_complex) {
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
    }

    trace.record(CoreConstants.TRACE_PROC_END);
    return ds;
  }

  public String getJson(String name, Map<String,String[]> params, String lang,
      TraceRecorder trace, ArrayList<String> warn, boolean minified) 
                                                  throws WsSrvException {
    AbstractDataSet ds = read(name, params, lang, trace, warn);

    return ds.getJson(warn, trace);
  }

  public DsExtResource getDsExtResource() {
    return _dse;
  }

  void setDsExtResource(DsExtResource dsExtRes) throws WsSrvException {
    _dse = dsExtRes;
    
    // Initiate complex flag check after DataSet set/changed
    initComplex();
  }

  void setColumns(List<ColumnHeader> columns) {
    _columns = columns;
  }
  
  public DataSetExt getDataSetExt() {
    return _dse.getDataSetExt();
  }

  boolean checkComplex() throws WsSrvException {
    if (_dse == null)
      return false;

    DataSetExt dse = _dse.getDataSetExt();
    return !(dse.getFilter() == null && dse.getSortByGrp() == null);
  }

  public boolean isComplex() {
    return _complex;
  }

  public void initComplex() throws WsSrvException {
    if (_dse != null)
      _complex = checkComplex();
  }

  public void setComplex(boolean complex) {
    _complex = complex;
  }

  LangSet getLangColumns() {
    return _dse.getLangColumns();
  }

  public boolean hasExtraColumns() {
    return _exc;
  }

  public List<AutoIncColumn> getAutoIncColumns() {
    return _auto;
  }

  public List<CalcColumn> getCalcColumns() {
    return _calc;
  }

  public HashMap<String,Object> getRequestParameters() {
    return _rparams;
  }
  
  public void error(int errId, String[] errDetails) {
    Utils.error(getCfg().getLogger(), getCfg().getRequestId(), 
                                                errId, errDetails);
    // AbstractWsSrvServlet.error(_cfg.getLogger(), errId, errDetails);
  }

  public void debug(String msg) {
    Utils.debug(getCfg().getLogger(), getCfg().getRequestId(), msg);
  }
  
  public ContextConfig getCfg() {
    return _cfg;
  }

  public DsDescrResource getDsDescrResource() {
    return _dsr;
  }

  public void setDsDescrResource(DsDescrResource dsr) {
    _dsr = dsr;
  }
  
}
