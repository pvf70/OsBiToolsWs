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

import java.util.HashMap;

/**
 * List of Errors for Shared Libraries
 * Reserved error in range [00-99]
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class ErrorList {

	private static final HashMap<Integer, ErrorInfo> _map = 
				                            new HashMap<Integer, ErrorInfo>();

	static {
	  // General Authentication Errors
    addError(1, "Not Authenticated", 401);
    
    // Tomcat Authentication Errors
    addError(2, "Authentication Failed", 401);
		addError(3, "Invalid Security Token", 401);
		addError(4, "Session Timeout", 403);
		addError(5, "Not Authenticated", 401);
		addError(6, "Authentication Failed");
		addError(7, "Error During Authentication", 500);
		
		// Resource Authentication Error
		addError(8, "Error validating security");
		addError(9, "Error validating security");
    addError(10, "User Configuration Error", 500);
    addError(11, "User Configuration Error", 500);
    addError(12, "Authentication Failed");
		
		// OpenAM Errors
    addError(13, "Error validating security");
    addError(14, "Error validating security");
    addError(15, "Authentication Failed");
    addError(16, "SSO Authentication requred");
    addError(17, "Error validating security");
    addError(18, "Error validating security");
    addError(19, "Invalid Security Token", 401);
    addError(20, "Error validating security");
    addError(21, "Invalid Security Token", 401);
    addError(22, "Logout Error", 401);
    addError(23, "Unable get user information");
    addError(24, "Access Denied");
    addError(25, "Access Denied");
    addError(26, "Access Denied");
    addError(27, "Access Denied");
    
    // General Project access errors
    addError(29, "Error creating file");
    addError(30, "Invalid project structure", 400);
    addError(31, "Entry not found", 404);
    addError(32, "Entry already exists", 422);
    addError(33, "Invalid Entry name", 400);
    addError(34, "Error creating file");
    addError(35, "Error creating file");
    addError(36, "File not found", 404);
    addError(37, "Error creating ");
    addError(38, "Error reading file");
    addError(39, "Entry not found", 404);
    addError(40, "Invalid OsBiTools Project");
    
    // File name validation
    addError(41, "Invalid File Name", 400);
    addError(42, "Invalid File Name", 400);
    addError(43, "Invalid File Extension", 400);
    addError(44, "Invalid File Name", 400);
    
    // File Utilities
    addError(45, "Can not rename file to itself");
    addError(46, "Unable rename file");
    addError(47, "Unable deleting file");
    
    // SAML Authentication
    addError(48, "Error producing SAML Authentication Request", 500);
    addError(49, "SAML Authentication Error", 403);
    addError(50, "SAML Authentication Error", 401);
    addError(51, "Error processing SAML Authentication Response");
    addError(52, "Error processing SAML Authentication Response");
    addError(53, "Error processing SAML Authentication Response");
    addError(54, "Error processing SAML Authentication Response");
    addError(55, "Error processing SAML Authentication Response");
    addError(56, "Error processing SAML Authentication Response");
    addError(57, "Error processing SAML Authentication Response");
    addError(58, "Error processing SAML Authentication Response");
    addError(59, "Error processing SAML Authentication Response");
    addError(60, "Error processing SAML Authentication Response");
    addError(61, "Error processing SAML Authentication Response");
    addError(62, "Error processing SAML Authentication Response");
    addError(63, "Error producing SAML Authentication Request", 500);
    addError(64, "SSO Session validation failed", 403);
    addError(65, "SSO Session validation failed", 403);
    addError(66, "SAML Authentication Error", 500);
    addError(67, "SAML Authentication Error", 500);
    addError(68, "Error producing Service Provider Metadata", 500);
    addError(69, "Error producing Service Provider Metadata", 500);
    addError(70, "Error producing SAML Logout Request", 500);
    addError(71, "Error processing SAML Authentication Response", 403);
    addError(72, "Error producing SAML Logout Request", 500);
    addError(73, "Error producing SAML Logout Request", 500);
    addError(74, "Error producing SAML Logout Request", 500);
    addError(75, "Error producing SAML Logout Request", 500);
    addError(76, "Error processing SAML Logout Request", 500);
    addError(77, "Error processing SAML Logout Request", 500);
    addError(78, "Error processing SAML Logout Request", 422);
    addError(79, "Error processing SAML Logout Request", 422);
    addError(80, "Error processing Signed SAML Message", 422);
    addError(81, "Error processing CDSSO Reqiest", 401);
    addError(82, "Error processing CDSSO Reqiest", 401);
    addError(83, "Error processing CDSSO Reqiest", 401);
    addError(84, "Error processing CDSSO Reqiest", 401);
    
    // General Errors
    addError(95, "Authentication Failed", 401);
    addError(96, "Authentication Failed", 401);
    addError(97, "Authentication Failed", 401);
    addError(98, "Missing mandatory parameter(s)", 400);
    addError(99, "Method Not Allowed", 405);
    
		// addError(, "");
	}
	
	public static String getErrorById(int id) {
		ErrorInfo info = _map.get(id);
		return (info == null ) ? "Unknown Error" : info.getMsg();
	}
	
	public static int getHttpErrorById(int id) {
		ErrorInfo info = _map.get(id);
		return (info == null) ? 0 : info.getCode();
	}
	
	public static void addError(int id, String msg) {
		addError(id, msg, 0);
	}
	
	public static void addError(int id, String msg, Integer code) {
	  _map.put(id, new ErrorInfo(msg, code));
	}
}
