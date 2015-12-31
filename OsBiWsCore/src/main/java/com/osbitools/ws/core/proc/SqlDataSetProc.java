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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.core.daemons.DsExtResource;
import com.osbitools.ws.core.producers.SqlDataSetProducer;
import com.osbitools.ws.core.shared.ContextConfig;
import com.osbitools.ws.core.shared.CoreConstants;

/**
 * Sql Data Set Processor
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class SqlDataSetProc extends RealDataSetProc<SqlDataSetProducer, 
                                                SqlDataSetDescr, SqlData> {

  // JNDI connection parameter
  String _cname;
  
  // PooledConnection 
  private DataSource _ds;
 
  // List of values to bind with sql
  private ArrayList<Object> _params = new ArrayList<Object>();
  
  public SqlDataSetProc(DsExtResource dsExtResource, 
          HashMap<String,Object> requestParameters, ContextConfig cfg) 
                                                      throws WsSrvException {
    super(SqlDataSetProducer.class, dsExtResource, requestParameters, cfg);
  }

  @Override
  public SqlData getDataSetSpec() {
    DataSetExt dse = getDataSetExt();
    return (dse.getClass().equals(DataSetDescr.class)) ?
      ((DataSetDescr) dse).getSqlData() :
      ((SqlDataSetDescr) dse).getSqlData();
  }
  
  @Override
  public void validateRequestParams(Map<String,String[]> params) 
                                                  throws WsSrvException {
    super.validateRequestParams(params);
    
    String[] cname = params.get(CoreConstants.CONN_PARAM);

    // Check for connection mandatory parameter
    if (cname == null || cname.length == 0 ||
          cname[0].equals(""))
      //-- 132 Return 400 Bad Request
      throw new WsSrvException(132, "'" + 
          CoreConstants.CONN_PARAM + "' parameter missing.");
    
    _cname = cname[0];
    
    if (_ds == null) {
      // Try locate connection in JNDI
      InitialContext context;
      try {
        context = new InitialContext();
      } catch (NamingException e) {
        //-- 125
        throw new WsSrvException(125, "Unable initialize JNDI context",  e);
      }
      
      try {
        _ds = (DataSource) context.lookup("java:comp/env/jdbc/" + _cname);
      } catch (NamingException e) {
        //-- 126
        throw new WsSrvException(126, "Unable retrieve '" + 
                                    _cname + "' JNDI resource",  e);
      }
    }
    
    // Get parameters if any
    SqlParameters sparams = getDataSetSpec().getSql().getSqlParams();
    if (sparams != null) {
      for (SqlParameter sparam : sparams.getParam())
        _params.add(getRequestParameters().get(
            ((RequestParameter)sparam.getReqParam()).getName()));
    }
  }
  
  public ArrayList<Object> getParameters() {
    return _params;
  }
  
  public Connection getConnection() throws SQLException {
    return (_ds == null) ? null : _ds.getConnection();
  }  
  
  public void setConnection(DataSource dataSource) 
                                                          throws SQLException {
    _ds = dataSource;
  } 
  
  public String getConnectionName() {
    return _cname;
  } 
}
