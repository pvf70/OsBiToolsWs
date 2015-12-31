/*
 * Copyright 2014-2016 IvaLab Inc. and other contributors
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-07
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.core.shared;

import org.apache.log4j.Logger;

import com.osbitools.ws.core.daemons.LsFilesCheck;

/**
 * Contains current context configuration
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class ContextConfig {

  //Active logger
  private Logger _log;
 
  // Top DataSet Map directory
  private String _dir;
 
  // Minified flag
  private boolean _minified;
 
  // LangLabel Check
  private LsFilesCheck _lcheck;
  
  // Request Id
  private long _req;
  
  public ContextConfig(long requestId, String dir, Logger log, 
                              boolean minified, LsFilesCheck lcheck) {
    _dir = dir;
    _log = log;
    _req = requestId;
    _lcheck = lcheck;
    _minified = minified;
  }

  public Logger getLogger() {
    return _log;
  }

  public void setLogger(Logger log) {
    _log = log;
  }

  public String getDsDir() {
    return _dir;
  }

  public void setDsDir(String dir) {
    _dir = dir;
  }

  public boolean isMinified() {
    return _minified;
  }

  public void setMinified(boolean minified) {
    _minified = minified;
  }
  
  public LsFilesCheck getLangLabelsCheck() {
    return _lcheck;
  }

  public long getRequestId() {
    return _req;
  }

  public void setRequestId(long requestId) {
    _req = requestId;
  }
}
