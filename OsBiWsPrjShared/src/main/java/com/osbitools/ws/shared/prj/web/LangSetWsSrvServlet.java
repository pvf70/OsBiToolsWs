/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-08-08
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.prj.PrjMgrConstants;
import com.osbitools.ws.shared.prj.utils.LangSetUtils;

/**
 * 
 * LangLabels File Manager. Implements next CRUD spec
 * 
 * doGet - Read Project ll_set
 * doPut - Save and automatically commit new file
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

@WebServlet("/rest/ll_set")
public class LangSetWsSrvServlet extends GenericPrjMgrWsSrvServlet {

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
    
    String name = req.getParameter(PrjMgrConstants.REQ_NAME_PARAM);
    LangSetUtils lu = (LangSetUtils) req.getSession().
          getServletContext().getAttribute("ll_set_utils");
    
    // Read project based lang file
    try {
      printJson(resp,  lu.read(
                              getPrjRootDir(req), name, isMinfied(req)));
    } catch (WsSrvException e) {
      checkSendError(req, resp, e);
    }
  }
  
  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      super.doPut(req, resp);
    } catch (ServletException e) {
      if (e.getCause().getClass().equals(WsSrvException.class)) {
        // Authentication failed
        checkSendError(req, resp, (WsSrvException) e.getCause());
        return;
      } else {
        throw e;
      }
    }
    
    String comment = req.getParameter("comment") != null ? 
                                    req.getParameter("comment") : "";
                                    
    String name = req.getParameter(PrjMgrConstants.REQ_NAME_PARAM);
                                    
    LangSetUtils lu = (LangSetUtils) req.getSession().
                             getServletContext().getAttribute("ll_set_utils");
    
    try {
      printJson(resp,  lu.save(getPrjRootDir(req), name,
            req.getInputStream(), comment, getLoginUser(), 
                                    getGit(req), isMinfied(req)));
    } catch (WsSrvException e) {
      checkSendError(req, resp, e);
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    sendNotImplemented(req, resp);
  }
  
  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    sendNotImplemented(req, resp);
  }
  
  @Override
  protected String[] getMandatoryParameters() {
    return new String[] {PrjMgrConstants.REQ_NAME_PARAM};
  }
}
