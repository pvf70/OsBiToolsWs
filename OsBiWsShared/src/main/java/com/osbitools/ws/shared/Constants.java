/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 * Date: 2014-10-02
 * 
 */

package com.osbitools.ws.shared;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Default and Project specific constants
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

public class Constants {
		
	// Security flag
	public static final Boolean DEFAULT_SECURITY_FLAG = Boolean.FALSE;
	
	// Session Timeout in msec. 12 hours by default
	public static final Integer DEFAULT_SESSION_TIMEOUT = 43200000;
	
	// Default locale
	public static final String DEFAULT_LANG = "en";
	
	// Default minified parameter
	public static final Boolean DEFAULT_MINIFIED = Boolean.TRUE;
	
	// Application specific parameters 
	
	// Buffer size for I/O operations
	public static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	
	// End Of File
	public static final int EOF = -1;
	
	// Configuration file name
	public static final String CONFIG_FILE_NAME = "config.properties";
		
  // Public Configuration file name
  public static final String PUBIC_CONFIG_FILE_NAME = "public.config";

  // Configuration prefix for public parameters
	public static final String PUBLIC_CONFIG_PREFIX = "public.";
	
	// Security Token name
	public static final String SECURE_TOKEN_NAME = "STOKEN";
	
	// Security Token not found message
	public static final String STOKEN_NOT_FOUND = "Security Token not found";
	
	// Name of Security Provider parameter
	public static final String SPROVIDER_NAME = "security.provider";
	
	// Name of Restricted user id. 
	// If its defined than only this user allowed to login
	public static final String SUID_NAME = "security.user_id";
	
  // Name of Security Flag parameter
  public static final String SFLAG_NAME = "security.enabled";
  
  // Name of Security Cookie Name parameter
  public static final String SCOOKIE_NAME = "security.cookie_name";
  
  // Name of Security Session Timeout parameter
  public static final String STIMEOUT_NAME = "security.session_timeout";

  // Name of Default Language parameter
  public static final String DEF_LANG_NAME = "default_lang";
  
  // Name of Minified parameter
  public static final String MINIFIED_NAME = "minified";
  
	// Array of known locales
	public static HashMap<String, Locale> LOCALES = 
										new HashMap<String, Locale>();
	
	// Patter for param values
	public static String PARAM_VAL_SUFIX = "param_";
	public static Pattern PARAM_VAL = Pattern.compile(PARAM_VAL_SUFIX + ".*");
	
  // Pattern for Project/Map names
  public static final Pattern ID_PATTERN = 
      Pattern.compile("^[A-Za-z0-9_]*[A-Za-z0-9]+[A-Za-z0-9_]*$");

  //Max supported project level
  public static final int MAX_PROJ_LVL = 1;
  
  //Name of file with IDP Metadata
  public static final String IDP_METADATA_FILE = "idp_metadata.xml";
 
  // Name of JKS keystore file
  public static final String KEYSTORE_FILE = "osbitools.jks";
 
	static {
		LOCALES.put("en", Locale.US);
		LOCALES.put("fr-CA", Locale.CANADA_FRENCH);
		LOCALES.put("ru-RU", new Locale("ru"));
	}
		
}
