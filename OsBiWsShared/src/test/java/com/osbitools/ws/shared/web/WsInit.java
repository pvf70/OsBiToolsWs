/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-30
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared.web;

/**
 * 
 * Listener for shared module servlet
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class WsInit extends AbstractWsInit {

  public WsInit() {}

  @Override
  public String getWsName() {
    return "OsBi Shared";
  }

  @Override
  public String[] getConfigSubDirList() {
    return null;
  }

  @Override
  public String getLogCategory() {
    return "OsBiShared";
  }
}
