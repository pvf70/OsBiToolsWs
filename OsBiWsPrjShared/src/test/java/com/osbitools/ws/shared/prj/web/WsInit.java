/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-04-18
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.web;

import com.osbitools.ws.shared.prj.PrjMgrConstants;
import com.osbitools.ws.shared.prj.utils.EntityUtils;
import com.osbitools.ws.shared.prj.utils.TestEntityUtils;

public class WsInit extends AbstractWsPrjInit {

  @Override
  public String getLogCategory() {
    return "OsBiPrjShared";
  }
  
  @Override
  public String getWsName() {
    return "OsBi Tool Demo Project Manager Web Services";
  }
  
  @Override
  public String getComponentList() {
    // Minified not initializes yet at this point so skip it
    return "{\"ll_demo_components\":[\"demo\"]}";
  }

  @Override
  public String[] getConfigSubDirList() {
    return new String[] {PrjMgrConstants.PRJ_DIR};
  }

  @Override
  public EntityUtils getEntityUtils() {
    return new TestEntityUtils();
  }

}
