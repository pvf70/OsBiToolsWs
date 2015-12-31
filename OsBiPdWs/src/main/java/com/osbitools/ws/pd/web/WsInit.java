/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-10-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.pd.web;

import javax.servlet.ServletContextEvent;

import com.osbitools.ws.pd.utils.PdEntityUtils;
import com.osbitools.ws.shared.WpConstants;
import com.osbitools.ws.shared.prj.utils.IEntityUtils;
import com.osbitools.ws.shared.prj.web.AbstractWsPrjInit;

/**
 * 
 * Servlet listener for OsBiMe Web Service
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class WsInit extends AbstractWsPrjInit {
	
	@Override
  public void contextInitialized(ServletContextEvent evt) {
    super.contextInitialized(evt);

    // Load Custom Error List 
    try {
      Class.forName("com.osbitools.ws.pd.shared.CustErrorList");
    } catch (ClassNotFoundException e) {
      // Ignore Error 
    }
	}

	@Override
  public String getWsName() {
    return "Page Designer Web Services";
  }

  @Override
  public String getLogCategory() {
    return "OsBiPdWs";
  }
	
  @Override
  public IEntityUtils getEntityUtils() {
    return new PdEntityUtils();
  }

  @Override
  public String[] getConfigSubDirList() {
    return new String[] {WpConstants.WP_DIR};
  }
}
