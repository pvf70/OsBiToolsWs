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

import com.osbitools.ws.shared.WsSrvException;

/**
 * Logout Servlet
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

@WebServlet("/rest/logout")
public class LogoutWsSrv extends AbstractWsSrvServlet {
	// Default serial version uid
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {

		String stoken = null;
		try {
		  // Just get security token without validation
		  stoken = checkSecurityToken(req, resp, false);
		} catch (WsSrvException e) {
			checkSendError(req, resp, e);
			return;
		}

		if (stoken == null)
			return;

		try {
			getSecurityProvider().logout(req, stoken);
		} catch (WsSrvException e) {
			checkSendError(req, resp, e);
			return;
		}

		// Remove cookie
		Cookie cookie = new Cookie(getSecurityCookieName(req), stoken);
		cookie.setMaxAge(0);
		resp.addCookie(cookie);

		debug(req, "Terminated session " + stoken);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		sendNotImplemented(req, resp);
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
