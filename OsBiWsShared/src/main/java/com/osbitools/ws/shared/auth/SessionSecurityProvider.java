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

import java.math.BigInteger;
import java.security.Principal;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import java.security.SecureRandom;

import org.apache.catalina.*;

import com.osbitools.ws.shared.Constants;
import com.osbitools.ws.shared.WsSrvException;

/**
 * 
 * Security provider based on Tomcat Users file structure
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public abstract class SessionSecurityProvider extends AbstractSecurityProvider {

	static ConcurrentHashMap<String, Session> activeSessions;
				
	private SecureRandom random = new SecureRandom();
	
	private long _timeout;
	
	@Override
	public void init(Properties properties) throws RuntimeException {
	  super.init(properties);
	  
	  // Read session expiration
	  try {
	    _timeout = Integer.parseInt(properties.getProperty("session_timeout", 
	                          Constants.DEFAULT_SESSION_TIMEOUT.toString()));
	  } catch (NumberFormatException e) {
	    _timeout = Constants.DEFAULT_SESSION_TIMEOUT;
	  }
	  
	  synchronized(this) {
	  	if (activeSessions == null)
		  	activeSessions = new ConcurrentHashMap<String,Session>();
	  }
	}
	
  protected String createUserSession(LoginUser user) {

		String token = nextSessionId();
		createUserSession(token, new Session(user, token, _timeout));
		
		return token;
	}

  /*
  protected void createUserSession(String user, String stoken) {
    createUserSession(user, stoken, null);
  }
  */
  
  protected Session createUserSession(String user, 
                                    String stoken, Object params) {
    return createUserSession(stoken, new Session(
                new LoginUser(user), stoken, _timeout, params));
  }

  protected synchronized Session createUserSession(
                                    String stoken, Session session) {
    // Check if session exists otherwise refresh
    Session s = activeSessions.get(stoken);
    if (s == null)
      activeSessions.put(stoken, session);
    else
      s.refresh(_timeout);
    
    return session;
  }
  
  String nextSessionId() {
		return new BigInteger(130, random).toString(16);
	}
	  
	@Override
	public void validate(HttpServletRequest req, String token) 
                                                  throws WsSrvException {
		findSession(token);
	}

	protected Session findSession(String token) throws WsSrvException {
		Session session = activeSessions.get(token);
	    
		if (session == null)
				throw new WsSrvException(3, "",
						"Invalid token '" + token + "'");
		else if (session.isFinished())
				throw new WsSrvException(4, "",
						"Session '" + token + "' completed");
			
		return session;
	}
	
  public synchronized void logout(String stoken) throws WsSrvException {
		Session session = findSession(stoken);
		
		session.logout();
	}

	@Override
  public String getUser(String stoken) throws WsSrvException {
	  return findSession(stoken).getUser().getName();
  }

  @Override
  public void logout(HttpServletRequest req, 
                              String  stoken) throws WsSrvException {
    logout(stoken);
  }
}

class Session {
	// Date Stamp when was started
	private final long _started;
	
	// Date Stamp when session will be finished
	private long _finished;
	
	// User who open this session
	private final LoginUser _user;
	
	// Security Token
	private final String _token;

	// Date Stamp when it was terminated by logout or by validation
	private long _terminated = 0;
	
	// Custom parameters
	private final Object _params;
	
	public Session(LoginUser user, String stoken, long timeout) {
    this(user, stoken, timeout, null);
  }
	
	public Session(LoginUser user, String stoken, long timeout, Object params) {
		_started = System.currentTimeMillis();
		_user = user;
		_token = stoken;
		_params = params;
		_finished = _started + timeout;
	}
	
	public void refresh(long timeout) {
	  _terminated = 0;
	  _finished = System.currentTimeMillis() + timeout;
	}
	
	public void logout() {
	  _terminated = System.currentTimeMillis();
	}
	
	public boolean isFinished() {
	  boolean fdone = _terminated != 0;
	  if (!fdone && System.currentTimeMillis() > _finished)
	    logout();
	  
		return _terminated != 0;
	}

	public long getStarted() {
		return _started;
	}

	public LoginUser getUser() {
		return _user;
	}

	public String getToken() {
		return _token;
	}
	
	/**
	 * Return time in msec when session was terminated 
	 *                           either by logout or by validation
	 * @return
	 */
	public long getTerminated() {
	  return _terminated;
	}
	
	public Object getCustomParams() {
	  return _params;
	}
}

class LoginUser implements Principal {

  // UserName
  private final String _usr;
  
  // Login Time
  private final long _dts;

  public LoginUser(User user) {
    this(user.getUsername());
  }
  
  public LoginUser(String username) {
    _usr = username;
    _dts = System.currentTimeMillis();
  }
  
  @Override
  public String getName() {
    return _usr;
  }
  
  public long getLoginTime() {
    return _dts;
  } 
}