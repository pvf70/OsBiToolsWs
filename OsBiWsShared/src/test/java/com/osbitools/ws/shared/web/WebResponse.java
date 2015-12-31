/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
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

package com.osbitools.ws.shared.web;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Hold Web Response together with HTTP Code
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class WebResponse {
	private int _code;
	private String _msg;
	private String _cookie;
	
	public WebResponse(int code) {
		this(code, "");
	}
	
	public WebResponse(String msg) {
		this(200, msg);
	}
	
	public WebResponse(HttpURLConnection conn) throws IOException {
		this(conn.getResponseCode(), conn.getResponseMessage());
	}
	
	public WebResponse(int code, String msg) {
		_code = code;
		_msg = msg;
	}

	public int getCode() {
		return _code;
	}

	public String getMsg() {
		return _msg;
	}

  public String getCookie() {
    return _cookie;
  }

  public void setCookie(String _cookie) {
    this._cookie = _cookie;
  }
  
  @Override
  public String toString() {
    return "[" + _code + "] - " + _msg;
  }
}
