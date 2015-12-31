/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
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

package com.osbitools.ws.core.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.core.daemons.DsDescrResource;

/**
 * 
 * Convert data set into json format
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

@WebServlet("/rest/map_info")
public class MapInfoDsWsSrvServlet extends GenericCoreDsWsSrvServlet {

  // Default serial version uid
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    try {
      super.doGet(req, resp);
    } catch (ServletException e) {
      if (e.getCause().getClass().equals(WsSrvException.class)) {
        // Authentication failed
        checkSendError(req, resp, (WsSrvException) e.getCause());
        return;
      } else {
        throw e;
      }
    }

    String map = req.getParameter(PARAM_NAME);
    
    // Load map
    DsDescrResource dsr = getDsMap(req, map);
    if (dsr == null) {
      //-- 100
      checkSendError(req, resp, 100, "Map not found for '" + map + "'");

      return;
    }
  
    String cstr = "";
    String pstr = "";
    boolean minified = isMinfied(req);
    
    List<ColumnHeader> ch = dsr.getColumnSet();
    if (ch != null && ch.size() > 0) {
      for (ColumnHeader header: ch) {
        cstr += "," + Utils.getCRTT(minified) + "{" +
          Utils.getCRTTT(minified) + "\"name\":" + 
              Utils.getSPACE(minified) + "\"" + header.getName() + "\"," + 
          Utils.getCRTTT(minified) + "\"jtype\":" + 
              Utils.getSPACE(minified) + "\"" + header.getJavaType() + "\"," +
          Utils.getCRTTT(minified) + "\"on_error\":" + 
              Utils.getSPACE(minified) + "\"" + 
                (header.getOnError() == null ? "" : header.getOnError()) + "\"" +
          Utils.getCRTT(minified) + "}";
      }
    }
    
    RequestParameters rparams = dsr.getResource().getReqParams();
    if (rparams != null) {
      List<RequestParameter> params = rparams.getParam();
      if (params != null && params.size() > 0) {
        for (RequestParameter param: params) {
          pstr += "," + Utils.getCRTT(minified) + "{" +
              Utils.getCRTTT(minified) + "\"name\":" + 
              Utils.getSPACE(minified) + "\"" + param.getName() + "\"," + 
          Utils.getCRTTT(minified) + "\"jtype\":" + 
              Utils.getSPACE(minified) + "\"" + param.getJavaType() + "\"" +
          (param.getSize() != null ? "\"," +Utils.getCRTTT(minified) + 
              "\"size\":" + Utils.getSPACE(minified) + "\"" + 
                  param.getSize() + "\"" : "") + 
          Utils.getCRTT(minified) + "}";
        }
      }
    }
    
    printJson(resp, "{" + Utils.getCRT(minified) + 
        "\"columns\":" + 
          Utils.getSPACE(minified) + 
              "[" + (Utils.isEmpty(cstr) ? "" : cstr.substring(1)) + 
                     Utils.getCRT(minified) + "]," + Utils.getCRT(minified) +
        "\"req_params\":" + Utils.getSPACE(minified) +
              "[" + (Utils.isEmpty(pstr) ? "" : pstr.substring(1)) + 
                                          Utils.getCRT(minified) + "]" +
        Utils.getCR(minified) + "}");
  }
}
