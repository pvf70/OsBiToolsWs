/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-11-14
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared;

/**
 * Class that hold HTTP Error Information
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class ErrorInfo {
	// Error Description
	private String _msg;
	
	// HTTP Error code that correspond this internal error
	private Integer _code;
	
	public ErrorInfo(String msg) {
		this(msg, null);
	}

	public ErrorInfo(String msg, Integer code) {
		_msg = msg;
		_code = code;
	}

	public String getMsg() {
	  return _msg;
  }

	public Integer getCode() {
	  return _code;
  }
}
