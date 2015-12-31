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

import javax.servlet.http.HttpServletRequest;

import com.osbitools.ws.shared.WsSrvException;

/**
 * User authentication interface
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public interface ISecurityProvider {

  public void init(Properties properties) throws RuntimeException;
  
  /**
   * Based on hasSingleSignOn flag authenticate user or 
   *      create authentication request for Identity Provider
   * @param user username  (optional)
   * @param password password (optional)
   * @return Based on hasSingleSignOn flag security token or 
   *                                      authentication request
   * @throws WsSrvException
   */
	public String authenticate(HttpServletRequest req, String user, 
	                            String password) throws WsSrvException;
	
	public void validate(HttpServletRequest req, String token) 
	                                                    throws WsSrvException;
	
	/**
	 * Get User Name by security token
	 * 
	 * @param stoken Security Token
	 * @return User Name
	 * 
	 * @throws WsSrvException
	 */
	public String getUser(String stoken) throws WsSrvException;
	
	/**
	 * Logout
	 * 
	 * @param req HttpServlet Request
	 * @param stoken Security Token
	 * 
	 * @throws WsSrvException
	 */
	public void logout(HttpServletRequest req, 
	                      String stoken) throws WsSrvException;
	
	/**
	 * Check if authentication module execute single sign on
	 * 
	 * @return True for SSO and False for local authentication
	 */
	public boolean hasSingleSignOn();
}
