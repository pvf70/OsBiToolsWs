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

/**
 * Web Service Exception
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class WsSrvException extends Exception {
	// Error code
	private int _ecd;
	
	// Error Info
	private String _info;

	// Detail message(s)
	private String[] _dmsg;
	
	// Default serial version uid
	private static final long serialVersionUID = 1L;

	// Extra Json Object 
	private IJsonObj _jobj;
	
	public WsSrvException(int errCode, String errInfo, String detailMsg) {
		this(errCode, errInfo, new String[] {detailMsg});
	}
	
	/*
	public WsSrvException(long requestId, int errCode, 
																				String detailMsg, Exception e) {
		this(errCode, null, new String[] { detailMsg, e.getMessage() });
	}
	*/
	
	public WsSrvException(int errCode, Exception e) {
    this(errCode, null, (e == null ? null : 
      ((e.getMessage() != null) ?
          new String[] {e.getMessage()} :
            (e.getCause() != null && e.getCause().getMessage() != null ?
                  new String[] {e.getCause().getMessage()} : null))));   
  }
	
	public WsSrvException(int errCode, String detailMsg, Exception e) {
    this(errCode, null, (e == null ? new String[] {detailMsg} : 
      ((e.getMessage() != null) ?
        new String[] {detailMsg,e.getMessage()} :
          (e.getCause() != null && e.getCause().getMessage() != null ?
                new String[] {detailMsg, e.getCause().getMessage()} : 
                  new String[] {detailMsg}))));
  }
	
	public WsSrvException(int errCode, Exception e, String errInfo) {
    this(errCode, errInfo, (e == null ? null : 
      ((e.getMessage() != null) ?
        new String[] {e.getMessage()} :
          (e.getCause() != null && e.getCause().getMessage() != null ?
                new String[] {e.getCause().getMessage()} : null))));
  }
	
	public WsSrvException(int errCode, String detailMsg, Throwable t) {
		this(errCode, null, (t == null ? new String[] {detailMsg} : 
      ((t.getMessage() != null) ? new String[] {detailMsg, t.getMessage()} :
        (t.getCause() != null && t.getCause().getMessage() != null ?
              new String[] {detailMsg, t.getCause().getMessage()} : 
                                          new String[] {detailMsg}))));		
	}
	
	public WsSrvException(int errCode, Throwable t, String errInfo) {
		this(errCode, errInfo, (t == null ? null : 
		  ((t.getMessage() != null) ? new String[] {t.getMessage()} :
          (t.getCause() != null && t.getCause().getMessage() != null ?
                new String[] {t.getCause().getMessage()} : null))));		
	}
	
	public WsSrvException(int errCode, String errorInfo) {
		this(errCode, errorInfo, new String[] {});
	}
	
	public WsSrvException(int errCode, String[] detailMsg) {
		this(errCode, null, detailMsg);
	}
	
	public WsSrvException(int errCode, String errInfo, String[] detailMsg) {
		super(ErrorList.getErrorById(errCode));
		_ecd = errCode;
		_info = errInfo;
		_dmsg = detailMsg;
	}
	
	public WsSrvException(int errCode) {
	  super(ErrorList.getErrorById(errCode));
    _ecd = errCode;
	}
	
	public String getErrorInfo() {
		return _info;
	}
	
	public String[] getDetailMsgs() {
		return _dmsg;
	}
	
	public int getErrorCode() {
		return _ecd;
	}
	
	@Override
	public String toString() {
		String res = "ERROR #" + getErrorCode() + 
								" - " + getMessage();
		
		String info = getErrorInfo();
		
		String dmsg = "";
		if (_dmsg != null && _dmsg.length > 0) {
		  dmsg = "; DETAILS - ";
		  for (String msg: _dmsg)
				dmsg += "[" + msg + "]";
		}
		
		return res + ((info != null) ? "; INFO - " + info : "") + dmsg;
	}
	
	public String getFullMessageString() {
		return toString();
	}
  
  public IJsonObj getJsonObj() {
    return _jobj;
  }

  public void setJsonObj(IJsonObj _jobj) {
    this._jobj = _jobj;
  }
}
