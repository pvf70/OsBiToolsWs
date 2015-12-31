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

package com.osbitools.ws.shared;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

/**
 * Generic Web App tools
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public class WebAppInfo {

	// Non Maven flag
	private boolean _non_maven;
	
	// Detected version
	private String _version;
	
	// WebApp Name
	private String _name= "";
	
	/**
	 * Open and check version from Manifest.mf
	 * @param ctx Servlet Context
	 * @throws IOException
	 */
	public WebAppInfo(ServletContext ctx) {
		// 1. Read WebApp Name
		String s = File.separator;
		int sl = s.length();
		String rpath = ctx.getRealPath(File.separator);
		
		if (rpath != null) {
			if (rpath.substring(rpath.length() - sl).equals(s))
				_name = rpath.substring(0, rpath.length() - sl);
			else
				_name = rpath;
			
			_name = _name.substring(_name.lastIndexOf(s) + sl);
		}
					
		// 2. Read Version
		InputStream is = ctx.getResourceAsStream("META-INF/MANIFEST.MF");

        _non_maven = (is == null);
        
        if (!_non_maven) {
        	Manifest m;
			try {
				m = new Manifest(is);
				_version = m.getMainAttributes().getValue("Version");
			} catch (IOException e) {
				_version = "Unknown";
			}
        }
	}
	
	public boolean hasMavenVersion() {
		return !(_non_maven || Utils.isEmpty(_version));
	}
	
	public String getVersion() {
		return _version;
	}
	
	public String getName() {
		return _name;
	}
}
