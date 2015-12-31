/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2015-10-27
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared.web.saml2;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.osbitools.ws.shared.auth.ISecurityProvider;
import com.osbitools.ws.shared.auth.SamlSecurityProvider;
import com.osbitools.ws.shared.web.AbstractWsSrvServlet;

public class GenericSamlServiceProvider extends AbstractWsSrvServlet {
  // Default serial version uid
  private static final long serialVersionUID = 1L;
  
  //SAML Security Provider
  private SamlSecurityProvider _ssp = null;
 
  @Override
  public void init(ServletConfig config) throws ServletException {
    ISecurityProvider isp = (ISecurityProvider) 
                   config.getServletContext().getAttribute("sp");
   
    if (isp == null || !isp.getClass().equals(SamlSecurityProvider.class))
      throw new ServletException("SAML security provider is not configured");
   
    _ssp = (SamlSecurityProvider) isp;
  }
  
  SamlSecurityProvider getSamlHandler() {
    return _ssp;
  }
}
