/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-06-23
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.core.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.web.GenericWsSrvServlet;
import com.osbitools.ws.core.daemons.DsDescrResource;
import com.osbitools.ws.core.daemons.DsFilesCheck;

/**
 * 
 * Ancestor for all core servlets
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

public class GenericCoreDsWsSrvServlet extends GenericWsSrvServlet {

  // Default serial version uid
  private static final long serialVersionUID = 1L;

  //Name of parameter with request keys
  static final String PARAM_NAME = "map";
  
  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doOptions(req, resp);
    
    try {
      setResponseHeaders(resp, getCorsHeaders(req));
    } catch (WsSrvException e) {
      checkSendError(req, resp, e);
    }
  }
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      setResponseHeaders(resp, getCorsHeaders(req));
    } catch (WsSrvException e) {
      throw new ServletException(e);
    }
    
    super.doGet(req, resp);
  }
  
  @Override
  protected String[] getMandatoryParameters() {
    return new String[] {PARAM_NAME};
  }
  
  DsDescrResource getDsMap(HttpServletRequest req, String name) {
    DsFilesCheck dcheck = (DsFilesCheck) req.getSession().
                        getServletContext().getAttribute("dcheck");
    return dcheck.getResource(name);
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    sendNotImplemented(req, resp);
  }
  
  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    sendNotImplemented(req, resp);
  }
  
  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    sendNotImplemented(req, resp);
  }
}
