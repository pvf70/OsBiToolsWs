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

import java.util.*;
import java.text.Collator;
import java.util.regex.Pattern;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.core.producers.AbstractDataSetProducer;

/**
 * Class for complex DataSet result Complex DataSet will be altered after data
 * retrieval
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class DataSetComplex extends AbstractDataSet {

  // Rows
  private ArrayList<HashMap<String, Object>> _rows = 
                            new ArrayList<HashMap<String, Object>>();

  public static Pattern DATA_PARAM = Pattern
      .compile("\\@\\{([0-9A-Za-z\\.\\_\\\"\\(\\)\\!\\=\\&\\|]+)\\}");

  public DataSetComplex(AbstractDataSetProducer<?> dsProd, String lang,
      boolean minified) {
    super(dsProd, lang, minified);
  }

  @Override
  public void startRecord() {
    super.startRecord();
    _rows.add(new HashMap<String, Object>(getColumns().size()));
  }

  @Override
  public void addLocaleValue(String column, Object value, String lang) {
    int idx = getRecordCount();
    _rows.get(idx).put(column, value);
  }

  @Override
  public void appendData(AbstractDataSet ds) throws WsSrvException {
    ArrayList<HashMap<String, Object>> data = 
                          ((DataSetComplex) ds).getDataSet();

    for (HashMap<String, Object> row : data) {
      startRecord();
      for (String column : row.keySet()) {
        addValue(column, row.get(column));
      }
      endRecordSuccess();
    }
  }

  public ArrayList<HashMap<String, Object>> getDataSet() {
    return _rows;
  }

  public void sort(List<SortCond> sortList) throws WsSrvException {
    // Last sorted column. Used for multi-column sorting
    String lcol = null;
    
    // Create array of unique values for the first sorted condition
    HashSet<Object> unq = null;
    
    boolean funq = sortList.get(0).isUnique();
    if (funq)
      unq = new HashSet<Object>();
    
    for (int i = 0; i < sortList.size(); i++) {
      SortCond sort = sortList.get(i);

      // Check if sorted column in list
      String column = sort.getColumn();

      if (!hasColumn(column))
        //-- 119
        throw new WsSrvException(119, new String[] { "Sorted column '" + column
            + "' not found in result dataset" });

      ArrayList<SortedRecord> sorted = new ArrayList<SortedRecord>();

      int idx = 0;
      if (i == 0) {

        // Create subset of dataset with sorted column as key
        for (int j = 0; j < _rows.size(); j++) {
          Object val = _rows.get(j).get(column);
          
          if (i == 0 && funq) {
            if (!unq.contains(val)) {
              unq.add(val);
              sorted.add(new SortedRecord(j, val, sort,
                  getLang()));
            }
          } else {
            sorted.add(new SortedRecord(j, val, sort, getLang()));
          }
        }
        resort(sorted, idx, funq);
      } else {
        // Sort partiall dataset
        if (_rows.size() == 0)
          continue;
        // Locate group of similar elements in dataset

        boolean f = false;
        Object co = _rows.get(0).get(lcol);
        sorted.add(new SortedRecord(0, _rows.get(0).get(column), sort,
            getLang()));
        for (int j = 1; j < _rows.size(); j++) {
          if (_rows.get(j).get(lcol).equals(co)) {
            f = true;
            sorted.add(new SortedRecord(j, _rows.get(j).get(column), sort,
                getLang()));
          } else if (f) {
            // Sort partial array first
            resort(sorted, idx);

            // Clear flag and arrays
            f = false;
            idx = j;
            sorted.clear();
            co = _rows.get(j).get(lcol);
            sorted.add(new SortedRecord(j, _rows.get(j).get(column), sort,
                getLang()));
          } else {
            // Set new start point
            idx = j;
            sorted.clear();
            co = _rows.get(j).get(lcol);
            sorted.add(new SortedRecord(j, _rows.get(j).get(column), sort,
                getLang()));
          }
        }

        // After check
        if (f)
          resort(sorted, idx);
      }

      lcol = sort.getColumn();
    }
  }

  public void filter(ConditionFilter filter) throws WsSrvException {

    // Create new dataset with different record order
    ArrayList<HashMap<String, Object>> rows = 
          new ArrayList<HashMap<String, Object>>(_rows.size());

    CalcColumn calc = new CalcColumn();
    calc.setValue(filter.getValue());
    calc.setJavaType("java.lang.Boolean");

    ColumnEval eval = new ColumnEval(this, calc);

    // Filter dataset
    for (HashMap<String, Object> row : _rows) {
      
      // Define array of parameters
      Object[] params = new Object[eval.getParamCnt()];

      // Process column set
      for (String column : getColList()) {

        Integer[] arr = eval.getColumnIndexes(column);

        if (arr == null)
          continue;

        for (Integer idx : arr)
          params[idx] = row.get(column);
      }
      
      // Process input parameters
      HashMap<String, Object> plist = 
              getDataSetProducer().getDataSetProc().getRequestParameters();
      if (plist != null) {
        for (Map.Entry<String, Object> param : plist.entrySet()) {
          Integer[] arr = eval.getParamsIndexes(param.getKey());
          
          if (arr == null)
            continue;

          for (Integer idx : arr)
            params[idx] = param.getValue();
        }
      }
      
      boolean res;
      try {
        Boolean fres = (Boolean) eval.evaluate(params);
        res = fres;
      } catch (Exception e) { // (InvocationTargetException e) {
        int len = params.length + 2;
        String[] str = new String[len];
        
        String msg = (e.getCause() != null) ? e.getCause().getMessage() : e
            .getMessage();
        
        if (msg == null)
          msg = e.getClass().getSimpleName();
        
        str[0] = msg;
        str[1] = eval.getExpression();
        for (int i = 2; i < len; i++) {
          Object o = params[i - 2];
          str[i] = "PARAM #" + i + ": " + o + "; TYPE: "
              + o.getClass().getName();
        }

        //-- 121
        throw new WsSrvException(121, str);
      }

      if (!res)
        continue;
      

      if (res)
        rows.add(row);
    }

    // Replace original dataset
    _rows = rows;
  }

  private void resort(ArrayList<SortedRecord> list, int idx) {
    resort(list, idx, false);
  }
  
  private void resort(ArrayList<SortedRecord> list, int idx, boolean funq) {
    // Sort indexed list
    Collections.sort(list);
    
    // Create new dataset with different record order
    ArrayList<HashMap<String, Object>> rows = 
          new ArrayList<HashMap<String, Object>>(_rows.size());

    // Copy old records
    for (int i = 0; i < idx; i++)
      rows.add(_rows.get(i));

    // Copy sorted records
    for (int i = 0; i < list.size(); i++)
      rows.add(_rows.get(list.get(i).getId()));

    if (!funq)
      // Copy remaining records
      for (int i = idx + list.size(); i < _rows.size(); i++)
        rows.add(_rows.get(i));

    // Replace original dataset
    _rows = rows;
  }

  @Override
  public void startData() {
    super.startData();
    _rows.clear();
  }

  @Override
  public String getDataAsString() {
    String res = "";
    for (int i = 0; i < _rows.size(); i++) {
      HashMap<String, Object> rec = _rows.get(i);
      String rs = "";
      for (String column : getColList()) {
        Object val = rec.get(column);
        rs += "," + ((val == null) ? "null" :
          (Number.class.isAssignableFrom(getColumnClass(column))? 
              val.toString() : "\"" + val + "\""));
      }

      res += Utils.getCR(isMinified())+ Utils.getTT(isMinified()) + 
                                              "[" + rs.substring(1) + "]";
      if (i < _rows.size() - 1)
        res += ",";
    }

    return res;
  }
}

class SortedRecord implements Comparable<SortedRecord> {

  private int _id;
  private Object _val;
  String _vtype;
  Collator _cltr;
  SortCond _sort;

  SortedRecord(int id, Object value, SortCond sortCond, String lang)
      throws WsSrvException {
    _id = id;
    _val = value;
    _sort = sortCond;

    // Check object type
    _vtype = _val.getClass().getName().toString();

    // String requires collator
    if (_vtype.equals("java.lang.String")) {
      if (!Constants.LOCALES.containsKey(lang))
        //-- 120
        throw new WsSrvException(120, "Locale '" + lang + "' is not found in "
            + "Constants.LOCALES list.");

      Locale l = Constants.LOCALES.get(lang);
      _cltr = Collator.getInstance(l);
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public int compareTo(SortedRecord srec) {
    int res;
    int m = (_sort.getSequence().equals(SortTypes.DESC)) ? -1 : 1;

    if (_vtype.equals("java.lang.String")
        && _sort.getStrSort().equals(StringSort.COLLATOR)) {
      res = _cltr.compare(_val, srec.getValue());
    } else {
      res = ((Comparable) _val).compareTo(srec.getValue());
    }

    return m * res;
  }

  public int getId() {
    return _id;
  }

  public Object getValue() {
    return _val;
  }
}
