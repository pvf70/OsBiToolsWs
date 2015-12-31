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

package com.osbitools.ws.me.web;

import javax.servlet.ServletContextEvent;

import com.osbitools.ws.me.utils.MeEntityUtils;
import com.osbitools.ws.shared.DsConstants;
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
      Class.forName("com.osbitools.ws.me.shared.CustErrorList");
    } catch (ClassNotFoundException e) {
      // Ignore Error 
    }
	}

	@Override
  public String getWsName() {
    return "Map Editor Web Services";
  }

  @Override
  public String getLogCategory() {
    return "OsBiMeWs";
  }
	
  @Override
  public String getComponentList() {
    // Minified not initializes yet at this point so skip it
    return "{\"ll_containers\":[\"group\"]," +
        "\"ll_avail_ds_types\":[\"csv\",\"static\",\"sql\"]}";
  }

  @Override
  public IEntityUtils getEntityUtils() {
    return new MeEntityUtils();
  }

  @Override
  public String[] getConfigSubDirList() {
    return new String[] {DsConstants.DS_DIR};
  }
}
