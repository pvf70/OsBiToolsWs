/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 * Date: 2015-05-02
 * 
 */

package com.osbitools.ws.shared.prj.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.LsConstants;

@WebServlet("/rest/ll_ver")
public class LsVersionWsSrvServlet extends HttpServlet {
	//Default serial version uid
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		resp.getWriter().print("[" + LsConstants.LS_VER[0] + "," + 
		                                  LsConstants.LS_VER[1] + "]");
	}
}
