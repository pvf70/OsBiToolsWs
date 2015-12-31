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

import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.*;

import com.osbitools.ws.shared.WsSrvException;

/**
 * 
 * Security provider based on Tomcat Users file structure
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class TomcatUserSecurityProvider extends SessionSecurityProvider {

	static ConcurrentHashMap<String, Session> activeSessions;
	
	// Tomcat environment key
	private static final String env = "OsBiToolsUsers";
	
	@Override
	public void init(Properties properties) throws RuntimeException {
	  super.init(properties);
	  
	  synchronized(this) {
	  	if (activeSessions == null)
		  	activeSessions = new ConcurrentHashMap<String,Session>();
	  }
	}
	
	public LoginUser findUser(String user, String password) 
											throws WsSrvException {
		// Active user
		LoginUser res = null;
		
		try {
			InitialContext ctx = new InitialContext();
			
			UserDatabase users = (UserDatabase) ctx.lookup("java:comp/env/" + env);
			Iterator<User> it = users.getUsers();
			
			while (it.hasNext()) {
				User u = it.next();
				if (u.getName().equals(user)) {
					if (u.getPassword().equals(password)) {
						res = new LoginUser(u);
						break;
					} else {
						throw new WsSrvException(6, AUTH_FAILED_MSG, "Invalid Password");
					}
				}
			}
			
		} catch (NamingException e) {
			throw new WsSrvException(7, new String[] {"Unable locate Tomcat " +
				"environment parameter " + env, e.getExplanation()});
		}
		
		return res;
	}
	
	@Override
  public synchronized String authenticate(HttpServletRequest req, 
                String user, String password) throws WsSrvException {

		// Active user
		LoginUser usr = findUser(user, password);

		if (usr == null)
			throw new WsSrvException(2, AUTH_FAILED_MSG, "User Not Found");
		
		return createUserSession(usr);
	}
}
