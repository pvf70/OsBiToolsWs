/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2015-10-29
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared.web.saml2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.WsSrvException;

/**
 * Consume SAML Assertion for Single Logout
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 * 
 */

@WebServlet("/saml2/logout")
public class SamlSingleLogout extends GenericSamlServiceProvider {
  // Default serial version uid
  private static final long serialVersionUID = 1L;
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doPost(req, resp);
    
    debug(req, "Received Single Logout Request");
    
    try {
      byte[] out = getSamlHandler().procLogoutRequest(req);
      
      resp.setContentLength(out.length);
      resp.setContentType("text/xml");
      resp.getOutputStream().write(out);
    } catch (WsSrvException e) {
      checkSendError(req, resp, e);
      
      return;
    }
  }
}
