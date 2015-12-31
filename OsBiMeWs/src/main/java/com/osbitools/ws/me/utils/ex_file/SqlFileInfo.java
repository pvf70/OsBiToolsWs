/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-02-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.me.utils.ex_file;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.osbitools.ws.shared.*;

/**
 * SQL File processing
 *
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class SqlFileInfo extends ExFileInfo {

  /* (non-Javadoc)
   * @see com.osbitools.ws.me.utils.ex_file.ExFileInfo#getColumns(java.io.File, java.util.HashMap)
   */
  @Override  
  public String[][] getColumnList(File f, String cname,  
                        HashMap<String, String> params) throws WsSrvException {
    String[][] res = null;
    
    String sql = params.get("sql");
    if (Utils.isEmpty(sql))
      //-- 261
      throw new WsSrvException(261, "Empty SQL");
    
    // Read JNDI parameter
    // Try locate connection in JNDI
    InitialContext context;
    try {
      context = new InitialContext();
    } catch (NamingException e) {
      //-- 262
      throw new WsSrvException(262, "Unable initialize JNDI context",  e);
    }
    
    DataSource ds;
    try {
      ds = (DataSource) context.lookup("java:comp/env/jdbc/" + cname);
    } catch (NamingException e) {
      //-- 263
      throw new WsSrvException(263, "Unable retrieve '" + 
                                  cname + "' JNDI resource",  e);
    }
    
    if (ds == null)
      //-- 266
      throw new WsSrvException(266, 
          "Unable get dataset handle out of " + cname);
    
    try {
      Connection conn = ds.getConnection();
      if (conn == null)
        //-- 265
        throw new WsSrvException(265, 
            "Unable get connection out of " + cname);
      
      ResultSetMetaData mdata = conn.prepareStatement(sql).getMetaData();
      
      int cnt = mdata.getColumnCount();
      res = new String[cnt][2];
      
      for (int i = 0; i < cnt; i++) {
        res[i][0] = mdata.getColumnName(i + 1).toUpperCase();
        String ctype = mdata.getColumnClassName(i+1);
        // Replace sql.Date with util.Date
        res[i][1] = "\"" + ((ctype == "java.sql.Date") ? 
                              "java.util.Date" : ctype) + "\"";
      }
    } catch (SQLException e) {
      //-- 264
      throw new WsSrvException(264, sql, e);
    }
    
    return res;
  }
}
