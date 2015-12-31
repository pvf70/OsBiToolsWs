/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-27
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

import com.osbitools.ws.core.daemons.DsFilesCheck;
import com.osbitools.ws.core.daemons.LsFilesCheck;
import com.osbitools.ws.shared.web.AbstractWsSrvServlet;

/**
 * 
 * Used solely for testing purposes
 * Return daemon ready status
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
@SuppressWarnings("serial")
public class ReadyServlet extends AbstractWsSrvServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doGet(req, resp);
    resp.getWriter().print(isDataSetCheckReady(req) && 
                                isLangLabelsCheckReady(req));
  }

  private boolean isLangLabelsCheckReady(HttpServletRequest req) {
    LsFilesCheck lcheck = (LsFilesCheck) req.getSession().
        getServletContext().getAttribute("lcheck");
    return lcheck.isInit();
  }
  
  public boolean isDataSetCheckReady(HttpServletRequest req) {
    DsFilesCheck dcheck = (DsFilesCheck) req.getSession().
                              getServletContext().getAttribute("dcheck");
    return dcheck.isInit();
  }
}
