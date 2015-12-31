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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ds.SqlData;
import com.osbitools.ws.core.proc.SqlDataSetProc;
import com.osbitools.ws.core.producers.ds.AbstractDataSet;
import com.osbitools.ws.core.shared.CoreConstants;
import com.osbitools.ws.core.shared.TraceRecorder;

/**
 * Sql DataSet Producer
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class SqlDataSetProducer extends 
              AbstractDataSetProducer<SqlData> {

  public SqlDataSetProducer(SqlDataSetProc dsProc) {
    super(dsProc);
  }
  
  @Override
  public AbstractDataSet read(String name, String lang, TraceRecorder trace,
                                ArrayList<String> warn) throws WsSrvException {
    AbstractDataSet ds = null;
    SqlDataSetProc dsp = (SqlDataSetProc) getDataSetProc();
    ResultSet res = null;
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      try {
        conn = dsp.getConnection();
      } catch (SQLException e) {
        //-- 128
        throw new WsSrvException(128, "Unable retrieve connection for '" + 
                                            dsp.getConnectionName() + "'", e);
      }
      
      if (conn == null)
        //-- 129 Wrong schema
        throw new WsSrvException(129,
            "Wrong connection parameter [" + dsp.getConnectionName() + "]");
      
      trace.record(CoreConstants.TRACE_CONNECTED_TO_DB);
      
      String sql = getDataSetSpec().getSql().getSqlText();
      
      // Strip front and back special characters
      sql = sql.replaceAll("^[\\n|\\t]*", "").replaceAll("[\\n|\\t]*$", "").
          replaceAll("\\n*", "").replaceAll("\\s{2,}", " ");
      debug("Executing SQL: " + sql);
      
      // Prepare statement
      int pnum;
      ArrayList<Integer> ptypes = new ArrayList<Integer>();
      
      try {
        stmt = conn.prepareStatement(sql);
        pnum = stmt.getParameterMetaData().getParameterCount();
        for (int i = 0; i < pnum; i++)
          ptypes.add(stmt.getParameterMetaData().getParameterType(i + 1));
      } catch (SQLException e) {
        //-- 122
        throw new WsSrvException(122, sql, e);
      }
      
      // Check if all parameters in place
      ArrayList<Object> params = dsp.getParameters();
      int psize = params.size();
      if (psize != pnum)
        //-- 123
        throw new WsSrvException(123, "", "Number of expected sql parameters " + 
               pnum + " not equal the number of xml configured parameters " + 
               psize);
      
      // Prepare sql
      for (int i = 0; i < pnum; i++) {
        int idx = i + 1;
        Object value = params.get(i);
        boolean fnull = value == null;
        debug("P #" + idx + ": " + (fnull  ? "null" : value.toString()));
        
        try {
          if (fnull)
            stmt.setNull(idx, ptypes.get(idx - 1));
          else
            stmt.setObject(idx, value);
        } catch (SQLException e) {
          //-- 124
          throw new WsSrvException(124, "Error preparing #" + idx + 
              " sql parameter with value " + value.toString());
        }
      }
      
      trace.record(CoreConstants.TRACE_SQL_PREP);
      
      // Execute sql
      try {
        res = stmt.executeQuery();
      } catch (SQLException e) {
        // Collect parameters
        String[] err = new String[params.size() + 2];
        err[0] = sql;
        err[1] = e.getMessage();
        for (int i = 0; i < psize; i++) {
          Object pval = params.get(i);
          err[i + 2] = "PARAM #" + (i + 1) + ":" + 
                      (pval != null ? pval.toString() : pval);
        }
        //-- 133
        throw new WsSrvException(133, err);
      }
      
      trace.record(CoreConstants.TRACE_SQL_EXEC);
      
      ds = makeNewDataSet(lang);
      ds.startData();
      
      // Get columns
      int clen;
      ResultSetMetaData rmdata;
      String[] columns;
      try {
        rmdata = res.getMetaData();
        clen = rmdata.getColumnCount();
        columns = new String[clen];
        
        for (int i = 0; i < clen; i++) {
          String column = rmdata.getColumnName(i + 1).toUpperCase();
          columns[i] = column;
          String cname = rmdata.getColumnClassName(i+1);
          // Replace sql.Date with util.Date
          cname = (cname == "java.sql.Date") ? "java.util.Date" : cname;
          ds.addColumn(column, cname);
        }
      } catch (SQLException e) {
        //-- 134
        throw new WsSrvException(134, "Unable retrieve result metadata", e);
      }
      
      try {
        while (res.next()) {
          ds.startRecord();
          
          for (int i = 0; i < clen; i++)
            ds.addValue(columns[i], res.getObject(i + 1));
          
          // Finish record
          ds.endRecordSuccess();
        }
      } catch (SQLException e) {
        //-- 135
        throw new WsSrvException(135, "Unable retrieve result data set", e);
      }
      
      ds.endData();
    } finally {
      // Close all open handlers
      if (res != null)
        try {res.close();} catch (SQLException e) {}
      if (stmt != null)
        try {stmt.close();} catch (SQLException e) {}
      if (conn != null)
        try { conn.close(); } catch (SQLException e) {}
    }
    
    return ds;
  }

  @Override
  SqlData getDataSetSpec() {
    return ((SqlDataSetProc) getDataSetProc()).getDataSetSpec();
  }

}
