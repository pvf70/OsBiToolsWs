/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared.auth;

import java.util.Properties;

/**
 * 
 * Abstract class for security providers
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public abstract class AbstractSecurityProvider implements ISecurityProvider {

	public static final String AUTH_FAILED_MSG = 
	                "Invalid username and/or password";
	
  @Override
  public void init(Properties properties) throws RuntimeException {}

  @Override
  public boolean hasSingleSignOn() {
    return false;
  }
}
