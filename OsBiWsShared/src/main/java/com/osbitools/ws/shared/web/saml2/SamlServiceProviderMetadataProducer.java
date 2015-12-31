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

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.WsSrvException;

@WebServlet("/saml2/metadata")
public class SamlServiceProviderMetadataProducer 
                            extends GenericSamlServiceProvider {
  // Default serial version uid
  private static final long serialVersionUID = 1L;
  
  // Pre-generated metadata for service provider
  private static String _mdata;
  
  // Configured Service location.
  private static String _sloc = null;
  
  // Default value for empty _sloc
  private static final String SLOC_DEF = "##sp_location##";
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    // Remember configured service location
    _sloc = getSamlHandler().getConfigServiceLocation();
    if (_sloc == null)
      _sloc = SLOC_DEF;
    
    try {
      _mdata = getSamlHandler().getMetadata(_sloc);
    } catch (WsSrvException e) {
      throw new ServletException(e.getFullMessageString());
    }
  }
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (_mdata == null)
      throw new ServletException("Error producing SAML Metadata for OsBiWs " +
                  "Service Provider. Check servlet log file for more details");
    resp.getOutputStream().print(SLOC_DEF.equals(_sloc) ?
      _mdata.replaceAll(SLOC_DEF, getSamlHandler().
                        getDefServiceLocationUrl(req)) : _mdata);
  }
}
