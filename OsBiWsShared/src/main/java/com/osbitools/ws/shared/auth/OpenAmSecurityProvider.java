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

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import com.osbitools.ws.shared.WsSrvException;
import com.sun.identity.idsvcs.opensso.*;

/**
 * OpenAm Security Provider implementation
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class OpenAmSecurityProvider extends AbstractSecurityProvider {

  //IdentityServices URL
  private static URL _ids;

  // IdentityServices Port
  private static IdentityServicesImpl _idp;
  
  // OpenAm configuration URL
  String _openam;
  
  @Override
  public void init(Properties properties) throws RuntimeException {
    _openam = properties.getProperty("openam_url");
    if (_openam == null)
      throw new RuntimeErrorException(new Error( 
          "'openam' configuration parameter is not set"));
    
    // Cache IdentityServices
    String url = _openam + "identityservices/IdentityServices";

    try {
      _ids = new URL(url);
    } catch (MalformedURLException e) {
      throw new RuntimeErrorException(new Error("Invalid openam URL: " + 
                                                            e.getMessage()));
    }

    // Create port for OpenAm authentication services
    try {
      _idp = (new IdentityServicesImplServiceLocator())
                                  .getIdentityServicesImplPort(_ids);
    } catch (ServiceException e) {
      throw new RuntimeErrorException(new Error("Unable connect to '" + 
                                        _openam + "'. " + e.getMessage()));
    }
  }
  
	@Override
  public String authenticate(HttpServletRequest req, 
                String user, String password) throws WsSrvException {
		
	  if (_openam == null)
      throw new WsSrvException(13, 
            "'openam' configuration parameter is not set");
	  else if (_idp == null)
	    throw new WsSrvException(14, 
          "'openam' security provider is not initialized");
	  
		Token token = null;
		try {
			token = _idp.authenticate(user, password, null, null);
		} catch (InvalidCredentials | InvalidPassword | UserNotFound e) {
			throw new WsSrvException(15, "", e.getFaultDetails()[0].getTextContent());
			
		} catch (OrgInactive e) {
			throw new WsSrvException(24, "Organization Inactive", 
															e.getFaultDetails()[0].getTextContent());
		} catch (UserLocked e) {
			throw new WsSrvException(25, "User Locked", 
									e.getFaultDetails()[0].getTextContent());
		} catch (AccountExpired e) {
			throw new WsSrvException(26, "Account Expired", 
					e.getFaultDetails()[0].getTextContent());
		} catch (UserInactive e) {
			throw new WsSrvException(27, "User Inactive", 
					e.getFaultDetails()[0].getTextContent());
		} catch (RemoteException e) {
			throw new WsSrvException(17, "url: '" + _ids + "'", e.getCause());
			
		} catch (Exception e) {
			// Print stack to detect unknown errors
			e.printStackTrace();
			
			throw new WsSrvException(18, e.getMessage());
		}
		
		return token.getId();
	}

	@Override
  public void validate(HttpServletRequest req, String token) 
                                                  throws WsSrvException {
		Token t = new Token(token);
		
		Boolean f;
		try {
			f = _idp.isTokenValid(t);
		} catch (GeneralFailure | TokenExpired | InvalidToken e) {
			throw new WsSrvException(19, "Invalid token '" + token + "'", e);
		} catch (RemoteException e) {
			throw new WsSrvException(20, null, e);
		}
		
		if (!f)
			throw new WsSrvException(21, "",
					"Invalid security token '" + token + "'");
	}

	@Override
  public void logout(HttpServletRequest req,
                            String stoken) throws WsSrvException {
		try {
			_idp.logout(new Token(stoken));
		} catch (RemoteException e) {
			throw new WsSrvException(22, 
			      "Unable login from openam by url: " + _ids, e);
		}		
	}

	@Override
  public String getUser(String token) throws WsSrvException {
	  try {
	  	UserDetails user = 
	  			_idp.attributes(new String[] {"uid"}, new Token(token), true);
	  	
	  	return user.getAttributes()[0].getValues(0);
    } catch (RemoteException e) {
    	throw new WsSrvException(23, 
		      "Unable retrieve user details for token " + token, e);
    }
  }
}
