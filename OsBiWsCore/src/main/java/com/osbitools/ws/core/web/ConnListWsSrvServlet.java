/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-11-06
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.core.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.Utils;
import com.osbitools.ws.shared.WsSrvException;

/**
 * 
 * Get list of configured JNDI connections. Return same result as 
 *    cfg?lst=conn but recognize CORS request and generate appropriate headers
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

@WebServlet("/rest/conn")
public class ConnListWsSrvServlet extends GenericCoreDsWsSrvServlet {

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
    
    String pname = "conn";
    printJson(resp, "{" + Utils.getCRT(isMinfied(req)) + "\"" + pname + "\":" +
      Utils.getSPACE(isMinfied(req)) + "\"" + getSysParam(req, pname) + "\"" + 
        Utils.getCR(isMinfied(req)) + "}");
  }
  
  @Override
  protected String[] getMandatoryParameters() {
    return null;
  }
}
