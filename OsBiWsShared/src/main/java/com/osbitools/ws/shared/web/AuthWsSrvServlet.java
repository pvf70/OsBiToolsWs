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

package com.osbitools.ws.shared.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.auth.AbstractSecurityProvider;
import com.osbitools.ws.shared.auth.ISecurityProvider;

/**
 * Authentication Servlet
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
@WebServlet("/rest/auth")
public class AuthWsSrvServlet extends AbstractWsSrvServlet {
	// Default serial version uid
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		super.doPost(req, resp);

		if (!hasSecurity(req))
			return;

		ISecurityProvider isp = getSecurityProvider();

		String usr = req.getParameter("usr");
		String pwd = req.getParameter("pwd");

		String err = "";

		if (Utils.isEmpty(usr))
			err = "UserName is empty";
		else if (Utils.isEmpty(pwd))
			err = "Password is empty";

		if (!err.equals("")) {
		  //-- 1
			checkSendError(req, resp, 1, err);

			return;
		}

		// Check if restricted user defined
		
		String stoken = null;
		String uid = getContextAttribute(req, "user_id");
    if (uid != null && !uid.equals(usr))
      //-- 95
      checkSendError(req, resp, 95, AbstractSecurityProvider.AUTH_FAILED_MSG,
          new String[] {"User Id '" + usr + "' is not allowed"});

		try {
		  stoken = isp.authenticate(req, usr, pwd);
		} catch (WsSrvException e) {
			checkSendError(req, resp, e);

			return;
		}

		debug(req, "Created session " + stoken);

		// Read and parse response
		Cookie cookie = new Cookie(getSecurityCookieName(req), stoken);
		cookie.setPath("/");

		// Check if request was sequred
		if (req.isSecure())
			cookie.setSecure(true);

		// HttpOnly cookie only can be set for Servlet v.3 and above
		if (getServletContext().getMajorVersion() >= 3)
			cookie.setHttpOnly(true);
		resp.addCookie(cookie);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		super.doGet(req, resp);

		ISecurityProvider isp = getSecurityProvider();
		
		// Check if current cookie valid
		try {
			String stoken = checkSecurityToken(req, resp);
			
			// Check for single sign on
			if (stoken != null&& isp.hasSingleSignOn())
  			  // Return user id since it not authenticated directory
  			  resp.getOutputStream().print(isp.getUser(stoken));
		} catch (WsSrvException e) {
		  // Check for SSO adapter
	    if (isp.hasSingleSignOn())
        try {
          //-- 16
          printErrorAsJson(req, resp, 16, isp.authenticate(req, null, null));
        } catch (WsSrvException e1) {
          checkSendError(req, resp, e1);
        }
      else
	      checkSendError(req, resp, e);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		sendNotImplemented(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		sendNotImplemented(req, resp);
	}
}
