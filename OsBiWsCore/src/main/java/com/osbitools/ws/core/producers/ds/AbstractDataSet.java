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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.core.proc.AbstractDataSetProc;
import com.osbitools.ws.core.producers.AbstractDataSetProducer;
import com.osbitools.ws.core.shared.TraceRecorder;

/**
 * Abstract DataSet
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class AbstractDataSet implements IJsonObj {

	// Record count
	int _cnt;
	
	// HashMap columns & Types
	private HashMap<String, Constructor<?>> _columns = 
						new HashMap<String, Constructor<?>>();
	
	// Ordered array of columns
	private ArrayList<String> _clist = new ArrayList<String>();
	
	// Array of calculated columns
	// HashMap<String, ColumnEval> _calc;
	private ArrayList<ColumnEval> _calc;
	
	// Values for current record. Used by expression evaluation
	private HashMap<String,Object[]> _records;
	
	// Reference on top level procucer
	private final AbstractDataSetProducer<?> _dsp;
	
	// Columns
	private String _cls;
	
	// Language
	private String _lang;
	
	// Minified flag
	private boolean _minified;
	
	public AbstractDataSet(AbstractDataSetProducer<?> dsProd, 
								              String lang, boolean minified) {
		_lang = lang;
		_dsp = dsProd;
		_minified = minified;
	}
	
	public abstract void addLocaleValue(String Column, 
								Object value, String lang);
	
	public abstract void appendData(AbstractDataSet ds) throws WsSrvException;
	
	public abstract String getDataAsString();
	
	public void addValue(String column, Object value) {
		Object v = checkLocaleValue(column, value, _lang);
		addLocaleValue(column, v,  _lang);
		
		// Check if calc column activated
		if (_calc != null) {
			for (ColumnEval eval: _calc) {
				Integer[] arr = eval.getColumnIndexes(column);
				
				if (arr == null)
					continue;
				
				Object[] o = _records.get(eval.getColumnName());
				for (Integer idx: arr)
					o[idx] = v;
			}			
		}
	}
	
	public void startRecord() {
		_records = null;
		
		if (_calc == null)
			return;
		
		_records = new HashMap<String,Object[]>();
		
		for (ColumnEval eval: _calc) {
		
			HashMap<String, ArrayList<Integer>> pval = eval.getColumnIndexes();
			if (pval == null)
				return;
			
			int len = 0;
			for (ArrayList<Integer> p: pval.values())
				len += p.size();
			
			_records.put(eval.getColumnName(), new Object[len]);
		}
	}
	
	public String getJsonSimple() {
	  return getJson(null, null);
	}
	
	public String getJson(ArrayList<String> warn, TraceRecorder trace) {
		String res = "{" + Utils.getCRT(isMinified()) + 
			"\"columns\":" + Utils.getSPACE(isMinified()) + 
			  "[" + getColumnsAsString() + Utils.getCRT(isMinified()) + "]," + 
			Utils.getCRT(isMinified()) + "\"data\":" + 
			          Utils.getSPACE(isMinified()) + "[" + 
			getDataAsString() + Utils.getCRT(isMinified()) + "]";
		
		if (trace != null && trace.isEnabled())
			res += trace.getJsonRecord(isMinified());
		
		if (warn != null && warn.size() > 0) {
			String wres = "";
			
			for (String s: warn)
				wres += "," + "\"" + s.replace("\"", "\\\"") + "\"";
			
			res += "," + Utils.getCRT(isMinified()) + "\"warn\":" + 
					Utils.getSPACE(isMinified()) + "[" + wres.substring(1) + "]";
		}
	
		return res + Utils.getCR(isMinified()) + "}";
	}
	
	public void startData() {
		_cnt = 0;
		_cls = "";
		_clist.clear();
	}

	public int getRecordCount() {
		return _cnt;
	}
	
	public void endRecordSuccess() throws WsSrvException {
	  endRecord(false);
	}
	
	public void endRecordError() throws WsSrvException {
    endRecord(true);
  }
	
	public void endRecord(boolean isError) throws WsSrvException {
		AbstractDataSetProc dsp = getDataSetProducer().getDataSetProc();
		
		if (dsp.hasExtraColumns()) {
		
			List<AutoIncColumn> acl = dsp.getAutoIncColumns();
			if (acl != null) {
				for (AutoIncColumn ac: acl)
					addValue(ac.getName(), 
						ac.getStartFrom() + ac.getIncBy() * _cnt);
			}
			
			if (_calc != null) {
				for (ColumnEval eval: _calc) {
				  String column = eval.getColumnName();
				  
				  if (isError) {
				    addValue(column, eval.getCalcColumn().getErrorValue());
				  } else {
  					Object[] record = _records.get(column);
  					
  					try {
  						addValue(column, eval.evaluate(record));
  					} catch (InvocationTargetException e) {
  						HashMap<String, ArrayList<Integer>> eparams = 
  													eval.getColumnIndexes();
  						    
  						int len = (record == null) ? 0 : record.length;
  						String[] dmsg = new String[len + 1];
  						dmsg[0] = "Unable evaluate expression '" +  
                                      eval.getCalcColumn().getValue() + "'";
  						
  						if (e.getTargetException() != null)
  						  dmsg[0] += "; Reason: '" + e.getTargetException().
  						                                    getMessage() + "'";
  						
  						if (len > 0)
  							for (String s : eparams.keySet())
  								for (int idx: eparams.get(s))
  									dmsg[idx + 1] = s + ": " + record[idx];
  						
  						CalcColumn calc = eval.getCalcColumn();
  						Boolean stop = eval.getCalcColumn().isStopOnError();
  						if (stop != null && stop)
  						    //-- 115
  								throw new WsSrvException(115, dmsg);
  						
  						addValue(column, calc.getErrorValue());
  						
  						//-- 111
  						_dsp.getDataSetProc().error(111, dmsg);
  					} catch (Exception e) {
  					  // Something wrong with parameters
  					  HashMap<String, ArrayList<Integer>> eparams = 
                                                  eval.getColumnIndexes();
  					  List<Class<?>> classes = eval.getParamTypes();
  					  
  					  int len = (record == null) ? 0 : record.length;
  					  String[] dmsg = new String[len + 2];
              dmsg[0] = "Unable evaluate expression '" + 
                                eval.getCalcColumn().getValue();
              dmsg[1] = e.getMessage();
              
              if (len > 0)
                for (String s : eparams.keySet())
                  for (int idx: eparams.get(s))
                    dmsg[idx + 2] = s + ": " + record[idx] + " (given " + 
                      record[idx].getClass().getSimpleName() + 
                        " - expected " + classes.get(idx).getSimpleName() + ")";
              //-- 106
  					  throw new WsSrvException(106, dmsg);
  					}
				  }
				}
			}
		}
		
		_cnt++;
	}
	
	public void endData() {}

	public void addColumn(String column) throws WsSrvException {
		addColumn(column, "java.lang.String");
	}
	
	public void addColumn(String column, String jtype) throws WsSrvException {
		Class<?> c;
		try {
			c = Class.forName(jtype);
		} catch (ClassNotFoundException e) {
		  //-- 107
			throw new WsSrvException(107, "Unknown Java Type: " + jtype, e);
		}
		
		try {
		  addColumn(column.toUpperCase(), c.getConstructor(String.class));
		} catch (NoSuchMethodException | SecurityException e) {
		  //-- 108
			throw new WsSrvException(108, "Unable find constructor for class: " + 
					c.getName(), e);
		}
	}

	 public void addColumn(String column,  Constructor<?> co) {
	      _columns.put(column, co);
	      _cls += "," + Utils.getCRTT(isMinified()) + 
	        "{" + Utils.getCRTTT(isMinified()) + "\"name\":" + 
	          Utils.getSPACE(isMinified()) + "\"" + column + "\"," + 
	          Utils.getCRTTT(isMinified()) + "\"jtype\":" + 
	          Utils.getSPACE(isMinified()) + "\"" + 
	                          co.getDeclaringClass().getName() + "\"" + 
	          Utils.getCRTT(isMinified()) + 
	       "}";
	      _clist.add(column.toUpperCase());
	 }

	public void appendColumns(AbstractDataSet ds) {
	  for (String cname: ds.getColList())
	    if (!hasColumn(cname))
	      addColumn(cname, ds.getColumnCo(cname));
	}
	
	public void append(AbstractDataSet ds) throws WsSrvException {
	  appendColumns(ds);
	  endColumn();
	  
	  appendData(ds);
	}
	
	public void endColumn() throws WsSrvException {
		AbstractDataSetProc dsp = getDataSetProducer().getDataSetProc();
		if (!dsp.hasExtraColumns())
			return;
		
		List<AutoIncColumn> acl = dsp.getAutoIncColumns();
		if (acl != null) {
			for (AutoIncColumn ac: acl)
			  if (!hasColumn(ac.getName()))
			    // Auto Inc columns always integer
			    addColumn(ac.getName(), "java.lang.Integer");
		}
		
		List<CalcColumn> ccl = dsp.getCalcColumns();
		if (ccl != null) {
			_calc = new ArrayList<ColumnEval>();
			for (CalcColumn cc: ccl) {
			  if (!hasColumn(cc.getName())) {
  				addColumn(cc.getName(), cc.getJavaType());
  				_calc.add(new ColumnEval(this, cc));
			  }
			}
		}
	}
	
	public Object createValue(String column, String value) 
										throws WsSrvException {
		Object res;
		Constructor<?> co = _columns.get(column.toUpperCase());
		
		if (co == null)
		  //-- 109
			throw new WsSrvException(109, "Column definitions not found " +
													                      "for column : " + column);
		
		try {
			res = co.newInstance(value);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
	    //-- 110
			throw new WsSrvException(110, "Unable instantinate variable '" + 
					value + "' for class: " + 
						co.getName(), e);
		}
		
		return res;
	}
		
	public Object checkLocaleValue(String column, Object value, String lang) {
		Object res = value;
		AbstractDataSetProducer<?> dsp = getDataSetProducer();
		if (dsp.getLangColumns().isLangColumn(column)) {
			// Find value in loaded languages
			String s = getDataSetProducer().findLocaleValue(lang, value.toString());
			if (s != null)
				res = s;
		}
			
		return res;
	}
	
	public Class<?> getColumnClass(String column) {
		Constructor<?> co = _columns.get(column);
		
		return (co != null) ? co.getDeclaringClass() : null;
	}
	
	public Constructor<?> getColumnCo(String column) {
	  return _columns.get(column);
  }
	
	public AbstractDataSetProducer<?> getDataSetProducer() {
		return _dsp;
	}
	
	public HashMap<String, Constructor<?>> getColumns() {
	  return _columns;
	}
	
	public String[] getColList() {
    return _clist.toArray(new String[_clist.size()]);
  }
	
	public String getColumnsAsString() {
		return Utils.isEmpty(_cls) ? "" : _cls.substring(1);
	}

	public String getLang() {
		return _lang;
	}
	
	public boolean hasColumn(String column) {
		return _columns.containsKey(column);
	}
	
	@Override
	public String getJson() {
	  return getJsonSimple();
	}
	
	 public boolean isMinified() {
	    return _minified;
	  }
}

class ColumnEval {
	// Calc Column
	private CalcColumn _calc;
	
	// Pattern for column parameter
	public static final Pattern COLUMN_PARAM = 
		Pattern.compile("\\@\\{([0-9A-Za-z\\.\\_\\\"\\(\\)\\!\\=\\&\\|]+)\\}");

  //Pattern for request parameter
  public static final Pattern RPARAM = Pattern.compile("\\$\\{([a-zA-Z0-9-_]+)\\}");
  
	// Temp List of Expression Data Column parameters
  private HashMap<String, ArrayList<Integer>> _eparams = 
				                 new HashMap<String, ArrayList<Integer>>();
  
  //Temp List of Expression Request parameters
  private HashMap<String, ArrayList<Integer>> _rparams = 
                         new HashMap<String, ArrayList<Integer>>();
	
	// Pointer on Expression Evaluator object
	private ExpressionEvaluator _eval;
	
	// Pointer on parent dataset
	private AbstractDataSet _ds;
	
	// List of expression input parameters
	private List<String> _params = new ArrayList<String>();
	
	// List of expression classes for input parameters
	private List<Class<?>> _classes = new ArrayList<Class<?>>();
	
	// Final expression
	private String _expression;
	
	// Total number of parameters
	private int _pcnt;
	
	public ColumnEval(AbstractDataSet ds, CalcColumn calcColumn) 
												throws WsSrvException {
		_ds = ds;
		_calc = calcColumn;
		_expression = _calc.getValue();
		
		String jtype = _calc.getJavaType();
		Class<?> c;
		try {
			c = Class.forName(jtype);
		} catch (ClassNotFoundException e) {
		  //-- 112
			throw new WsSrvException(112, 
					"Unknown Java Type: " + jtype, e);
		}

		// Parse expression
		Matcher m = COLUMN_PARAM.matcher(_expression);
		
		_pcnt = 0;
		while (m.find()) {
			String col = m.group(1);
			
			// Check if column exists in dataset and get it type
			Class<?> ctype = _ds.getColumnClass(col);
			if (ctype == null)
			  //-- 113
				throw new WsSrvException(113, 
						"Cannot find class type for column '" +
								col + "'");
			
			// Check if column already used
			ArrayList<Integer> cnt;
			if (_eparams.containsKey(col)) {
				cnt = _eparams.get(col);
				cnt.add(_pcnt);
			} else {
				cnt = new ArrayList<Integer>();
				cnt.add(_pcnt);
				_eparams.put(col, cnt);
			}
				
			String cn = "C_" + col + "_" + (cnt.size() - 1);
			_expression = _expression.replaceFirst("\\@\\{" + col + "\\}", cn);
				
			_params.add(cn);
			_classes.add(ctype);
			
			_pcnt++;
		}
		
		// Replace request parameters
    m = RPARAM.matcher(_expression);
    
    while (m.find()) {
      String pname = m.group(1);
      Object value = ds.getDataSetProducer().getDataSetProc().
                                getRequestParameters().get(pname);
      
      if (value == null)
        //-- 38
        throw new WsSrvException(38, "", 
            "Mandatory parameter '" + pname + 
              "' required for calculated expression not found");
      
      // Check if parameter already used
      ArrayList<Integer> cnt;
      if (_rparams.containsKey(pname)) {
        cnt = _eparams.get(pname);
        cnt.add(_pcnt);
      } else {
        cnt = new ArrayList<Integer>();
        cnt.add(_pcnt);
        _rparams.put(pname, cnt);
      }
        
      String cn = "R_" + pname + "_" + (cnt.size() - 1);
      cn = cn.toUpperCase();
      
      _expression = _expression.replaceFirst("\\$\\{" + pname + "\\}", cn);
      
      _params.add(cn);
      _classes.add(value.getClass());
      
      _pcnt++;
    }
    
		// Compile condition. Slow process
		try {
			compile(c);
		} catch (CompileException e) {
		  //-- 114
			throw new WsSrvException(114,  
					"Unable compile expression '" + _calc.getValue() + "'", e);
		}
	}
	
	public Integer[] getColumnIndexes(String column) {
		List<Integer> cnt = _eparams.get(column);
		return (cnt == null) ? null : cnt.toArray(new Integer[cnt.size()]);
	}
	
	public Integer[] getParamsIndexes(String column) {
    List<Integer> cnt = _rparams.get(column);
    return (cnt == null) ? null : cnt.toArray(new Integer[cnt.size()]);
  }
	
	public HashMap<String, ArrayList<Integer>> getColumnIndexes() {
		return _eparams;
	}
	
	public void compile(Class<?> jtype) throws CompileException {
		
		_eval = new ExpressionEvaluator(
			_expression,     // expression
			jtype, // expressionType
		    _params.toArray(new String[_params.size()]), // parameterNames
		    _classes.toArray(new Class<?>[_classes.size()])  // parameterTypes
		);
	}
	
	public Object evaluate(Object[] values) 
			throws InvocationTargetException {
		Object res = _eval.evaluate(values);
		// Check special condition for Double.INFINITY
		if (res.getClass().equals(Double.class) && ((Double) res).isInfinite())
			throw new InvocationTargetException(
					new ArithmeticException("Value is Infinite"));
		return res;
	}
	
	public String getExpression() {
		return _expression;
	}
	
	public String getColumnName() {
		return _calc.getName();
	}
	
	public CalcColumn getCalcColumn() {
		return _calc;
	}
	
	public int getParamCnt() {
		return _pcnt;
	}
	
	public List<Class<?>> getParamTypes() {
	  return _classes;
	}
}
