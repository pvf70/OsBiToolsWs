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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.*;

/**
 * 
 * Convert data set into json format
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

@WebServlet("/rest/cfg")
public class ConfigDsWsSrvServlet extends GenericWsSrvServlet {

	// Default serial version uid
	private static final long serialVersionUID = 1L;

	// Name of parameter with request keys
	private static final String PARAM_NAME = "lst";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {

		try {
			super.doGet(req, resp);
		} catch (ServletException e) {
			if (e.getCause().getClass().equals(WsSrvException.class)) {
				// Authentication failed
				checkSendError(req, resp, (WsSrvException) e.getCause());
				return;
			} else {
				throw e;
			}
		}

		// Check if public.config file exists in config directory
		String path = getConfigDir(req) + File.separator +
		                                        Constants.PUBIC_CONFIG_FILE_NAME;

		File config = new File(path);
		String res = "{";
		String pstr = req.getParameter(PARAM_NAME);

		if (config.exists()) {
			Properties props = new Properties();
			props.load(new FileInputStream(config));

			String[] params = pstr.split(",");
			pstr = "";
			for (String param : params) {
			  // First check system parameters
			  String pvalue = getSysParam(req, param);
			  if (Utils.isEmpty(pvalue))
			    // Lookup for custom parameters
			    pvalue = props.getProperty(param, "");
			    
				pstr += "," + Utils.getCRT(isMinfied(req)) + "\"" + param + "\":" +
				                 Utils.getSPACE(isMinfied(req)) + "\"" + pvalue + "\"";
			}
			
			res += pstr.substring(1) + Utils.getCR(isMinfied(req));
		}

		printJson(resp, res + "}");
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

	@Override
	protected String[] getMandatoryParameters() {
		return new String[] { PARAM_NAME };
	}
}
