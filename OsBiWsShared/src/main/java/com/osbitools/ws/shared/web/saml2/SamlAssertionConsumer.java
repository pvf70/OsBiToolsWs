/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 * Date: 2015-10-27
 * 
 */

package com.osbitools.ws.shared.web.saml2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.WsSrvException;

/**
 * Consume SAML Assertion with Authentication Response 
 *                        and redirect back to initiated page
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 * 
 */

@WebServlet("/saml2/consumer")
public class SamlAssertionConsumer extends GenericSamlServiceProvider {
  // Default serial version uid
  private static final long serialVersionUID = 1L;
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doPost(req, resp);
    
    String sr = req.getParameter("SAMLResponse");
    
    if (sr == null) {
      //-- 57
      resp.getOutputStream().print(getErrorMsgAsHtml(57, 
                    "SAML Response parameter is not found", req));
      return;
    }
    
    // Check if security cookie set by IDP
    Cookie cookie;
    try {
      cookie = getSecurityCookie(req);
    } catch (WsSrvException e) {
      resp.getOutputStream().print(getErrorMsgAsHtml(e, req));
      return;
    }
    
    if (cookie == null) {
      //-- 61
      resp.getOutputStream().print(getErrorMsgAsHtml(61, 
          "Security token not found", "Security cookie '" +
                              getSecurityCookieName(req) + "' not found", req));
      return;
    }
    
    String url;
    try {
      url = getSamlHandler().procAuthnResponse(
                      req.getParameter("SAMLResponse"), 
                                       getSecurityCookie(req).getValue());
    } catch (WsSrvException e) {
      resp.getOutputStream().print(getErrorMsgAsHtml(e, req));
      return;
    }
    
    if (url == null)
      //-- 58
      resp.getOutputStream().print(getErrorMsgAsHtml(58,
                                "Unknown SAML Response", req));
    else
      resp.sendRedirect(url);
  }
}
