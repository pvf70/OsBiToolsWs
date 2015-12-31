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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.osbitools.ws.shared.Utils;
import com.osbitools.ws.shared.WsSrvException;

/**
 * 
 * Security provider based on internal resource file
 * The structure of the file is next:
 * 
 * username=password,ROLE1<,ROLE2><,...>
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class ResourceSecurityProvider extends TomcatUserSecurityProvider {
	private static String USER_FILE_NAME = "/users.txt";
	
	@Override
	public void init(Properties properties) throws RuntimeException {
	  super.init(properties);
	  
	  // Check if resource file exists
    InputStream in = this.getClass().
        getResourceAsStream(USER_FILE_NAME);
    
    if (in == null)
      throw new RuntimeException(new Error("Can't load user file: " +
                                                               USER_FILE_NAME));

	}
	
	@Override
	public LoginUser findUser(String user, String password)
			throws WsSrvException {
		// Read file
		InputStream in = this.getClass().
				getResourceAsStream(USER_FILE_NAME);
		
		if (in == null)
			throw new WsSrvException(8, "Can't load user file: " +
					USER_FILE_NAME);
		
		Properties p = new Properties();
		
		try {
			p.load(in);
		} catch (IOException e) {
			throw new WsSrvException(9, "Error reading user file: " +
					                                            USER_FILE_NAME, e);
		}
		
		LoginUser res = null;
		for (Object skey : p.keySet()) {
			String u = skey.toString();
			
			if (user.equals(u)) {
				String value = p.get(u).toString();
				
				if (Utils.isEmpty(value))
					throw new WsSrvException(10, "Invalid user configuration", 
							"Missing password and role(s) for user: " + u);
				
				String[] info = value.split(",");
				
				if (info.length < 2)
					throw new WsSrvException(11, "Invalid user configuration", 
							"Missing role(s) for user: " + u);
				
				// First parameter is password
				if (!info[0].equals(password))
					throw new WsSrvException(12, AUTH_FAILED_MSG, "Invalid Password");
				
				res = new LoginUser(u);
				
				break;
			}
		}
		
		return res;
	}
}
