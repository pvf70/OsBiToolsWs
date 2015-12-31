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

package com.osbitools.ws.core.daemons;

import java.io.File;

import com.osbitools.ws.shared.WsSrvException;

/**
 * Resource handle together with last modified date
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 * @param <T> Type
 */
public class ResourceInfo<T> {

	// Resource Handle
	private T _res = null;
	
	// Last file modified date
	private long _modified;
	
	// File Handle
	File _f;
	
	// Updated flag
	private boolean _upd;
	
	// Project name
  private String _prj;
  
	public ResourceInfo(T resHandle, File f, String prjName) {
		_f = f;
		_upd = true;
		_prj = prjName;
		
		setResource(resHandle);
	}

	public ResourceInfo(ResourceInfo<T> info) throws WsSrvException {
    this(info.getResource(), info.getFileHandle(), info.getProjectName());
    init();
  }
	
	 /*
   * Init resource info. This method must invoked after 
   *            resource info initially loaded or reloaded
   */
  public void init() throws WsSrvException {};

	public boolean isBad() {
	  return _res == null;
	}
	
	public File getFileHandle() {
		return _f;
	}
	
	public synchronized T getResource() {
		return _res;
	}
	
	public synchronized boolean isModified() {
		// Check if file removed
		if (!_f.exists()) {
			// Reset modified flag and wait until it comes back
			_modified = 0;
			return false;
		}
		
		return _f.lastModified() > _modified;
	}
	
	public synchronized void setResource(T resHandle) {
	  if (resHandle != null)
	    _res = resHandle;
		_modified = _f.lastModified();
	}
	
	public void resetUpdated() {
	  _upd = false;
	}
	
  public void setUpdated() {
    _upd = true;
  }

  public boolean isUpdated() {
    return _upd;
  }

  public String getProjectName() {
    return _prj;
  }  
}
