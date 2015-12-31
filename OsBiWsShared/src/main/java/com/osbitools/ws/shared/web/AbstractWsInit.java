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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.management.RuntimeErrorException;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.auth.*;

/**
 * 
 * Abstract Servlet listener class
 * Initialize configuration
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class AbstractWsInit implements ServletContextListener {
  
  public abstract String getWsName();

  public abstract String[] getConfigSubDirList();

  public abstract String getLogCategory();

  // List of restricted security variables. IF any defined in web.xml than 
  // it cannot be overwritten by configuration.properties
  // Intended for hosting purposes only to restrict user from changing certain
  // important security variables
  private static final List<String> RESTRICTED_PARAMS = new ArrayList<String>(
      Arrays.asList(new String[] {
          Constants.SFLAG_NAME, Constants.SPROVIDER_NAME, Constants.SUID_NAME
  }));
  
  @Override
  public void contextInitialized(ServletContextEvent evt) {
    Logger log = Logger.getLogger(getLogCategory());
    
    // Read context parameters
    ServletContext ctx = evt.getServletContext();
    ctx.setAttribute("log", log);
    
    // Read and display version
    WebAppInfo info = new WebAppInfo(ctx);
    ctx.setAttribute("web_info", info);
    
    String intro = "Starting " + getWsName();
    if (info.hasMavenVersion())
      intro += " v." + info.getVersion();
    
    log.info(intro);

    // Read trace flag (optional)
    boolean trace = false;
    String s = ctx.getInitParameter("trace");
    if (s != null)
      trace = Boolean.parseBoolean(s);
    log.info("Trace is " + ((trace) ? "On" : "Off"));
    ctx.setAttribute("trace", trace);

    // Check if config_dir parameter found in context parameters (optional)
    String cdir = ctx.getInitParameter("config_dir");

    if (Utils.isEmpty(cdir))
      // Take home dir
      cdir = System.getProperty("user.home") + File.separator + "." +
                                                          info.getName();
    else if  (cdir.substring(0,1).equals("~"))
      // Replace ~ with user.home
      cdir = cdir.replace("~", System.getProperty("user.home"));
    
    log.info("Using " + cdir + " as configuration directory.");

    // Check if directory exists
    Boolean isNew = null;
    try {
      isNew = !GenericUtils.checkDirectory(cdir);

      if (isNew != null) {
        // Check subdirectories
        String[] sdl = getConfigSubDirList();
        if (sdl != null)
          for (String dir : sdl)
            GenericUtils.checkDirectory(cdir + File.separator + dir);
      }
    } catch (IOException e) {
      String err = "Unable create configuration directory '" + cdir
          + "'. " + e.getMessage();
      log.fatal(err);
      throw new RuntimeErrorException(new Error(err));
    }

    // Finally load configuration
    loadConfiguration(cdir, isNew, ctx, log);
    
    // Replace config directory with full name
    ctx.setAttribute("cfg_dir", (new File(cdir)).getAbsolutePath());
  }

  protected List<String> getRestrictedParamList() {
    return RESTRICTED_PARAMS;
  }
  
  /**
   * Load configuration file
   * 
   * @param configDir
   *            root configuration directory
   * @param isNew
   *            is configuration directory exists
   * @throws WsSrvException
   */
  private final void loadConfiguration(String configDir, Boolean isNew,
                                      ServletContext ctx, Logger log) {
    Properties props = new Properties();

    // Get list of restricted parameters
    HashMap<String, String> rmap = new HashMap<String, String>();
    for (String pname: getRestrictedParamList()) {
      String param = ctx.getInitParameter(pname);
      if (param != null)
        rmap.put(pname, param);
    }
    
    // Combine path for configuration file
    String cfile = configDir + File.separator + Constants.CONFIG_FILE_NAME;

    boolean fNew = !Utils.isEmpty(isNew) || !(new File(cfile)).exists();

    if (fNew) {
      log.info("Using default configuration parameters.");

      props.put(Constants.SFLAG_NAME, 
          rmap.get(Constants.SFLAG_NAME) != null ? 
              rmap.get(Constants.SFLAG_NAME) :
                                    Constants.DEFAULT_SECURITY_FLAG.toString());
      props.put(Constants.SPROVIDER_NAME, 
          rmap.get(Constants.SPROVIDER_NAME) != null ? 
                                rmap.get(Constants.SPROVIDER_NAME) : "");
      props.put(Constants.STIMEOUT_NAME,
                                  Constants.DEFAULT_SESSION_TIMEOUT.toString());
      props.put(Constants.SCOOKIE_NAME, Constants.SECURE_TOKEN_NAME);
      props.put(Constants.DEF_LANG_NAME, Constants.DEFAULT_LANG);
      props.put(Constants.MINIFIED_NAME, Constants.DEFAULT_MINIFIED.toString());
    }

    if (isNew != null) {
      try {
        if (fNew) {
          // Creating default configuration file
          log.info("Creating default configuration file " + "'" + cfile + "'");
          
          processConfig(ctx, props, rmap, true);
          props.store(new FileOutputStream(new File(cfile)),
              															"Default Parameters");
        } else {
          props.load(new FileInputStream(new File(cfile)));
          processConfig(ctx, props, rmap, false);
          
          // Check restricted values
          for (String pname: rmap.keySet())
            props.put(pname, rmap.get(pname));
        }
      } catch (FileNotFoundException e) {
        log.error(e.getMessage());
      } catch (IOException e) {
        log.error(e.getMessage());
      }
    }

    // Scan configuration for all public parameters
    // and put them into internal HashMap for fast access
    String s = "";
    
    // Server Side Configuration
    HashMap<String, String> config = new HashMap<String, String>();
    
    for (Object skey : props.keySet()) {
      String key = skey.toString();
      String value = props.get(skey).toString();

      s += " " + key + ":" + value + ";";
      
      
      if (key.indexOf(Constants.PUBLIC_CONFIG_PREFIX) == 0)
        config.put(
            key.substring(Constants.PUBLIC_CONFIG_PREFIX.length()),
            props.get(key).toString());
    }

    ctx.setAttribute("config", config);
    
    log.info("Loaded Configuration - " + s.substring(1));

    // Set security flag - restricted
    boolean security = Boolean.parseBoolean(props.getProperty(
            Constants.SFLAG_NAME, Constants.DEFAULT_SECURITY_FLAG.toString()));
    
    ctx.setAttribute("security", security);
    ctx.setAttribute("scookie_name", props.getProperty(
                      Constants.SCOOKIE_NAME, Constants.SECURE_TOKEN_NAME));

    // Set security provider - restricted as well
    String sprovider = props.getProperty(Constants.SPROVIDER_NAME, "");
    
    // Set Session Timeout
    ctx.setAttribute("timeout", 
        Long.parseLong(props.getProperty(Constants.STIMEOUT_NAME,
                        Constants.DEFAULT_SESSION_TIMEOUT.toString())));

    // Set lang
    ctx.setAttribute("default_lang", props.getProperty(
                    Constants.DEF_LANG_NAME, Constants.DEFAULT_LANG));

    // Set minified
    ctx.setAttribute("minified", 
        Boolean.parseBoolean(props.getProperty(Constants.MINIFIED_NAME,
                                  Constants.DEFAULT_MINIFIED.toString())));

    if (security) {
      if (Utils.isEmpty(sprovider)) {
        throw new RuntimeErrorException(new Error(
            "Security is On but Security Provider is not " +
                "defined for parameter '" + Constants.SPROVIDER_NAME));
      }

      ISecurityProvider sp;
      if (sprovider.equals("openam"))
        sp = new OpenAmSecurityProvider();
      else if (sprovider.equals("tomcat"))
        sp = new TomcatUserSecurityProvider();
      else if (sprovider.equals("resource"))
        sp = new ResourceSecurityProvider();
      else if (sprovider.equals("saml"))
        sp = new SamlSecurityProvider();
      else
        throw new RuntimeErrorException(new Error(
            "Unable locate security provider for '" + sprovider
                + "' type."));

      // Add extra properties from environment
      props.setProperty("cpath", ctx.getContextPath());
      props.setProperty("cdir", configDir);
      
      // Initialize security provider
      sp.init(props);

      ctx.setAttribute("sp", sp);
    }
    
    // Set restricted user, if defined
    String ruid = rmap.get(Constants.SUID_NAME);
    if (ruid != null)
      ctx.setAttribute("user_id", ruid);
    
    // Remember all properties
    ctx.setAttribute("props", props);
  }

  /**
   * Method to override in inherited classes
   * 
   * @param props Array of properties
   * @param isNew isNew flag. Used to detect if new configuration created 
   *  to inject default parameters. For old configuration used to 
   *  detect and read required parameters
   */
  public void processConfig(ServletContext ctx, Properties props, 
                          HashMap<String, String> rmap, boolean isNew) {
    HashMap<String, String> sparams = new HashMap<String, String>();
    
    // Add empty container for system parameters
    ctx.setAttribute("sparams", sparams);
    
    // Read all jdbc connectors
    InitialContext context;
    
    try {
      context = new InitialContext();
    } catch (NamingException e) {
      getLogger(ctx).error("Unable initialize JNDI context. ",  e);
      return;
    }
    
    NamingEnumeration<NameClassPair> list;
    try {
      list = context.list("java:comp/env/jdbc");
    } catch (NamingException e) {
      getLogger(ctx).error("Unable find JNDI jdbc entries.",  e);
      return;
    }
    
    // Add first jndi names into array list to search aftger
    ArrayList<String> carr = new ArrayList<String>();
    try {
      while (list.hasMore())
        carr.add(list.next().getName());
    } catch (NamingException e) {
      getLogger(ctx).error("Unable list JNDI entries. ",  e);
      return;
    }
    
    if (carr.size() > 0) {
      String clist = "";
      Collections.sort(carr);
      
      for (String cname: carr)
        clist += ";" + cname;
      
      sparams.put("conn", clist.substring(1));
    }
  }
  
  public void addSysParam(ServletContext ctx, String key, String value) {
    @SuppressWarnings("unchecked")
    HashMap<String, String> sparams = (HashMap<String, String>)
                                          ctx.getAttribute("sparams");
    sparams.put(key, value);
  }
  
  public String getContextAttr(ServletContext ctx, String key) {
    return (String) ctx.getAttribute(key);
  }
  
  public String getConfigDir(ServletContext ctx) {
    return getContextAttr(ctx, "cfg_dir");
  }
  
  public Logger getLogger(ServletContext ctx) {
    return (Logger) ctx.getAttribute("log");
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent arg0) {}
}
