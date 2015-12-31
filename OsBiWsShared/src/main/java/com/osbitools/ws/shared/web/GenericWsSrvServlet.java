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
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.*;

/**
 * Ancestors of all servlets. Does token validation only
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

public abstract class GenericWsSrvServlet extends AbstractWsSrvServlet {

	// Default serial version uid
	private static final long serialVersionUID = 1L;

	// Security Token
	ThreadLocal<String> _tl = new ThreadLocal<>();

	// Validate cookie for valid SSO Token
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		super.doGet(req, resp);

		checkWebReq(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		super.doPost(req, resp);

		checkWebReq(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		super.doPut(req, resp);

		checkWebReq(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		super.doDelete(req, resp);

		checkWebReq(req, resp);
	}

	private void checkWebReq(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException {

		// 1. Check security
		String stoken;
		try {
		  stoken = checkSecurityToken(req, resp);
		} catch (WsSrvException e) {
			throw new ServletException(e);
		}

		if (stoken != null)
			_tl.set(stoken);

		// 2. Check mandatory parameters

		try {
			checkMandatoryParameters(req);
		} catch (WsSrvException e) {
			throw new ServletException(e);
		}

	}

	String getValidToken() {
		return _tl.get();
	}

	public String getLoginUser() throws WsSrvException {
		return getSecurityProvider().getUser(getValidToken());
	}
	
	protected String[] getMandatoryParameters() {
		return null;
	}

	public void checkMandatoryParameters(HttpServletRequest req)
	    throws WsSrvException {
		String[] params = getMandatoryParameters();

		if (Utils.isEmpty(params))
			return;

		int cnt = 0;
		String perr = "";
		for (String param : params) {
			if (Utils.isEmpty(req.getParameter(param))) {
				perr += "," + param;
				cnt++;
			}
		}

		if (cnt > 0)
			throw new WsSrvException(98, "Missing [" + perr.substring(1) +
			                          "] parameter" + ((cnt > 1) ? "s" : ""));
	}
	
	public String readRequestBody(HttpServletRequest req) 
      throws IOException, WsSrvException {
    byte[] buffer = new byte[Constants.DEFAULT_BUFFER_SIZE];
    StringBuffer ds = new StringBuffer();
    
    InputStream in = req.getInputStream();
    
    int n = 0;
    while (Constants.EOF != (n = in.read(buffer)))
      ds.append(new String(buffer, 0, n));
    
    if (ds.toString().equals(""))
    	throw new WsSrvException(233, "Empty Entity body");
    
    return ds.toString();
  }
}
