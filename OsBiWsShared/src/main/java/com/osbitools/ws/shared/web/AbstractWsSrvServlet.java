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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.auth.*;

/**
 * Abstract Servlet for all Web Services
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractWsSrvServlet extends HttpServlet {

  // Local Thread Storage
	private static ThreadLocal<Long> _ts = new ThreadLocal<Long>();

	// HTTP Headers according CORS spec
	protected static final HashMap<String, String> CORS_HEADERS = 
	                                  new HashMap<String, String>();
	
	protected static final String CORS_ORIGIN_HEADER = 
	                          "Access-Control-Allow-Origin";
	
	static {
	  CORS_HEADERS.put("Access-Control-Allow-Credentials", "true");
	  CORS_HEADERS.put("Access-Control-Allow-Headers", "SToken");
	  CORS_HEADERS.put("Access-Control-Allow-Methods", "GET");
	}
  
	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		genRequestId();
	}

	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		genRequestId();
	}

	@Override
	protected void doPut(HttpServletRequest arg0, HttpServletResponse arg1)
	    throws ServletException, IOException {
	  genRequestId();
	}
	
	@Override
	protected void doDelete(HttpServletRequest arg0, HttpServletResponse arg1)
	    throws ServletException, IOException {
	  genRequestId();
	}
	/**
	 * Generate unique id for request and saved it into thread local storage
	 */
	private void genRequestId() {
		_ts.set(System.currentTimeMillis());
	}

	public Long getRequestId() {
		return _ts.get();
	}

  protected HashMap<String, String> getCorsHeaders(HttpServletRequest req) 
                                                    throws WsSrvException {
    String origin = req.getHeader("origin");
    if (origin == null)
      return null;
      
    Properties props = (Properties) req.getSession().
                  getServletContext().getAttribute("props");
    
    // Check domain validation method
    String dval = props.getProperty("security.domain_validation");
    if (Utils.isEmpty(dval))
      //-- 81
      throw new WsSrvException(81, "Domain validation not setup", 
                            "Empty security.domain_validation parameter");
    
    boolean fval = false;

    if (dval.equals("property")) {
      // Read domain_list property
      String dlist = props.getProperty("security.domain_list");
      if (Utils.isEmpty(dlist))
        //-- 82
        throw new WsSrvException(82, "Domain list for validation not setup", 
                              "Empty security.domain_list parameter");
      
      for (String domain: dlist.split(";")) {
        if (origin.indexOf(domain) >= 0) {
          fval = true;
          break;
        }
      }
    } else {
      //-- 83
      throw new WsSrvException(83, "Invalid domain validation method", 
                      "'" + dval + " validation method not implemented");
    }
    
    if (!fval)
      //-- 84
      throw new WsSrvException(84, "Unknown host", 
                     "Origin host '" + origin + "' not found in domain list");

    @SuppressWarnings("unchecked")
    HashMap<String, String> headers = (HashMap<String, String>) 
                                                  CORS_HEADERS.clone();
    headers.put(CORS_ORIGIN_HEADER, origin);
    
    return headers;
  }
  
	void printErrorAsJson(HttpServletRequest req, HttpServletResponse resp, 
	    int errId, String errInfo, String errDetail, Throwable e) 
	                                                throws IOException {
		printErrorAsJsonEx(req, resp, errId, errInfo,
				new String[] { errDetail, e.getMessage() });
	}

	void printErrorAsJson(HttpServletRequest req, HttpServletResponse resp, 
	      int errId, String errInfo, String errDetail, 
	                                Exception e) throws IOException {
		printErrorAsJsonEx(req, resp, errId, errInfo,
				new String[] { errDetail, e.getMessage() });
	}

	void printErrorAsJson(HttpServletRequest req, HttpServletResponse resp, 
	    int errId, String errInfo, Exception e) throws IOException {
		printErrorAsJsonEx(req, resp, errId, errInfo,
				                                  new String[] {e.getMessage()});
	}

	void printErrorAsJson(HttpServletRequest req, HttpServletResponse resp, 
	                          int errId, String errInfo) throws IOException {
		printErrorAsJsonEx(req, resp, errId, errInfo, null);
	}

	void printErrorAsJson(HttpServletRequest req, HttpServletResponse resp, 
	          int errId, String errInfo, String errDetail) throws IOException {
		printErrorAsJsonEx(req, resp, errId, errInfo, new String[] { errDetail });
	}
	 
	protected void printErrorAsJson(HttpServletRequest req, 
	    HttpServletResponse resp, WsSrvException e) throws IOException {
		printErrorAsJsonEx(req, resp, e.getErrorCode(), e.getMessage(), 
		              e.getErrorInfo(), e.getDetailMsgs(), e.getJsonObj());
	}

	void printErrorAsJson(HttpServletRequest req, 
	    HttpServletResponse resp, int errId) throws IOException {
		printErrorAsJsonEx(req, resp, errId, null, null);
	}

	void printErrorAsJsonEx(HttpServletRequest req, HttpServletResponse resp, 
	      int errId, String errInfo, String[] errDetails) throws IOException {
		printErrorAsJsonEx(req, resp, errId, 
				ErrorList.getErrorById(errId), errInfo, errDetails, null);
	}
	
	void printErrorAsJsonEx(HttpServletRequest req, HttpServletResponse resp, 
	    int errId, String errMsg, String errInfo, String[] errDetails, 
			  IJsonObj jobj) throws IOException {
		// Log result
		error(req, errId, errInfo, errDetails);

		printJson(resp, getErrorMsgAsJsonEx(errId, errMsg, errInfo, 
		                                          errDetails, jobj, req));
	}

	/**
	 * Print result in application/json format
	 * 
	 * @param response HttpServletResponse
	 * @param msg json string
	 * @param headers Extra set with HTTP headers 
	 * @throws IOException 
	 */
	protected void printJson(HttpServletResponse resp, String msg
	                                                      ) throws IOException {
	  printMimeMsg(resp, msg, "application/json");
	}
	
  /**
   * Print result in text/plain format
   * 
   * @param response HttpServletResponse
   * @param msg JSON string
   * @param headers Extra set with HTTP headers 
   * @throws IOException 
   */
  protected void printText(HttpServletResponse resp, String msg) 
                                                  throws IOException {
    printMimeMsg(resp, msg, "text/plain");
  }

  /**
   * Print result in Mime message format
   * 
   * @param response HttpServletResponse
   * @param msg text string
   * @param mime Mime type
   * @param headers Extra set with HTTP headers
   * @throws IOException 
   */
  protected void printMimeMsg(HttpServletResponse resp, String msg, 
                                        String mime) throws IOException {
    /*
    // Cache preventing headers according
    // http://stackoverflow.com/questions/49547/making-sure-a-web-page-is-not-cached-across-all-browsers
    resp.setHeader("Expires", "0");
    resp.setHeader("Pragma", "no-cache");
    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    
    resp.setContentType(mime + "; charset=utf-8");
    resp.setContentLength(msg.getBytes(StandardCharsets.UTF_8).length);
    
    // Check if extra headers required
    if (headers != null)
      for (Entry<String, String> header: headers.entrySet())
        resp.setHeader(header.getKey(), header.getValue());
    */
    
    setResponseHeaders(resp, msg.getBytes(StandardCharsets.UTF_8).length, 
                                                            mime);
    resp.getWriter().print(msg);
  }
  
  private void setResponseHeaders(HttpServletResponse resp, int len, 
      String mime) {
    setResponseHeaders(resp, len, mime, "utf-8");
  }

  private void setResponseHeaders(HttpServletResponse resp, int len, 
               String mime, String charset) {
    // Cache preventing headers according
    // http://stackoverflow.com/questions/49547/making-sure-a-web-page-is-not-cached-across-all-browsers
    resp.setHeader("Expires", "0");
    resp.setHeader("Pragma", "no-cache");
    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    
    resp.setContentType(mime + (charset != null ? "; charset=" + charset : ""));
    if (len > 0)
      resp.setContentLength(len);
  }
  
  protected void setResponseHeaders(HttpServletResponse resp, 
                                        HashMap<String, String> headers) {
    if (headers != null)
      for (Entry<String, String> header: headers.entrySet())
        resp.setHeader(header.getKey(), header.getValue());
  }
  
  /**
   * Print result and add content mime type
   * 
   * @param response
   *            HttpServletResponse
   * @param textType
   *            Application type as text/textType
   * @param fname
   *            File Name
   * @param ftext
   *            File Text
   * @throws IOException 
   */
  protected void printMimeFile(HttpServletResponse resp, 
      String mimeType, String fname, byte[] ftext) throws IOException {
    printMimeFile(resp, mimeType, fname, ftext, null);
  }
  
	/**
   * Print result and add content mime type
   * 
   * @param response HttpServletResponse
   * @param textType Application type as text/textType
   * @param fname File Name
   * @param ftext File Text
   * @param charset Encoding charset
   * @param headers Extra set with HTTP headers 
   * 
   * @throws IOException 
   */
  protected void printMimeFile(HttpServletResponse resp, String mime,
               String fname, byte[] ftext, String charset) throws IOException {
    
    setResponseHeaders(resp, ftext.length, mime, charset);
    resp.setHeader("Content-Disposition", "attachment; filename=" + fname);
    resp.getOutputStream().write(ftext);
  }
  
	public String getErrorMsgAsJsonEx(int errId, String errInfo,
			      String[] errDetails, boolean trace, boolean minified, 
			                                          HttpServletRequest req) {
		return getErrorMsgAsJsonEx(errId, ErrorList.getErrorById(errId), 
											errInfo, errDetails, null, req);
	}
	
	public String getErrorMsgAsJsonEx(int errId, String errMsg, 
								String errInfo, String[] errDetails, 
								   IJsonObj jobj, HttpServletRequest req) {
		// Check if any user friendly info
		if (Utils.isEmpty(errInfo))
			errInfo = "No additional details for this error";

		// Combine details into comma delimited string
		String dmsg = "";

		// Allow details on the client based on the trace context parameter
		if (isTrace(req) && !Utils.isEmpty(errDetails)) {
			for (String s : errDetails)
				if (s != null)
					dmsg += ",\"" + s.replaceAll("\"", "\\\\\"").
				    replaceAll("'", "\\\\\"").
				      replaceAll("\\n", "").
				        replaceAll("\\s{2,}", " ") + "\"";
			dmsg = dmsg.equals("") ? "" : dmsg.substring(1);
		}

		// Log Error
		error(req, errId, errInfo, dmsg);
		
		boolean minified = isMinfied(req);
		
		return "{" +
		  Utils.getCRT(minified) + "\"request_id\":" + getRequestId() + "," + 
			Utils.getCRT(minified) +"\"error\":" + 
			                  Utils.getSPACE(minified) +"{" +
			Utils.getCRTT(minified) + "\"id\":" + 
			                      Utils.getSPACE(minified) + errId + "," + 
			Utils.getCRTT(minified) + "\"msg\":" + 
			                          Utils.getSPACE(minified) + "\"" + 
					errMsg + "\"" + "," +
					Utils.getCRTT(minified) + "\"info\":" + 
					                              Utils.getSPACE(minified) + 
					"\"" + errInfo + "\"" + "," +
					Utils.getCRTT(minified) + "\"details\":" + 
					                              Utils.getSPACE(minified) + 
			  "[" + dmsg + "]" + Utils.getCRT(minified) + "}" +
			
      ((jobj != null) ? "," + Utils.getCRTT(minified) + "\"error_ds\":" + 
          Utils.getSPACE(minified) + jobj.getJson() + 
                                          Utils.getCRT(minified) : "") +
    
		  Utils.getCR(minified) + "}";
	}

  public String getErrorMsgAsHtml(WsSrvException e, HttpServletRequest req) {
    return getErrorMsgAsHtmlEx(e.getErrorCode(), e.getMessage(),
                              e.getErrorInfo(), e.getDetailMsgs(), req);
  }

  public String getErrorMsgAsHtml(int errId, 
                        String errInfo, HttpServletRequest req) {
    return getErrorMsgAsHtmlEx(errId, 
                  ErrorList.getErrorById(errId), errInfo, null, req);
  }
  
  public String getErrorMsgAsHtml(int errId, 
            String errInfo, String errDetail, HttpServletRequest req) {
    return getErrorMsgAsHtmlEx(errId, 
        ErrorList.getErrorById(errId), errInfo, new String[] {errDetail}, req);
  }

  private String getErrorMsgAsHtmlEx(int errId, String errMsg, String errInfo,
      String[] errDetails, HttpServletRequest req) {
    // Combine details into comma delimited string
    String dmsg = "";

    // Allow details on the client based on the trace context parameter
    if (isTrace(req) && !Utils.isEmpty(errDetails)) {
      for (String s : errDetails)
        if (s != null)
          dmsg += "<p>" + s.replaceAll("\\n", "<br />").
                replaceAll("\\s{2,}", " ") + "</p>";
    }

    // Log Error
    error(req, errId, errInfo, errDetails);

    // Make simple web page
    return "<html><head><title>OsBiTools Error #" + errId + "</title>" +
      "<meta http-equiv=\"Content-Type\" content=\"text/html; " +
                                                    "charset=utf-8\" />" +
      "<style>" +
        "p{margin:0;padding:2px;text-align:center}" +
        "footer{position: absolute;bottom: 2px;color:gray;font-size:small;" +
                                  "text-align: center;width: 100%;}" +
      "</style>" +
      "</head><body style=\"color:red;padding-top:1em;\">" + 
      "<p><b>ERROR #" + errId + " - " + errMsg + "</b></p>" +
      (!Utils.isEmpty(errInfo) ? "<p>INFO: " + errInfo + "</p>" : "") +
      
      "<footer>" + 
      (!dmsg.equals("") ? "<p>DETAILS</p>" + dmsg  + "</small><div>": "") +
      "<p>Request #: " + getRequestId() +
      "</footer>" +
      "</body></html>";
  }
  
	public void sendHttpErrorCode(HttpServletRequest req, 
	      HttpServletResponse resp, int httpCode, int errId, String errInfo) {
		sendHttpErrorCodeEx(req, resp, httpCode, errId, errInfo, null);
	}

	public void sendHttpErrorCode(HttpServletRequest req, 
	    HttpServletResponse resp, int errId, String errInfo, String errDetail) {
		sendHttpErrorCode(req, resp, ErrorList.getHttpErrorById(errId), 
																			      errId, errInfo, errDetail);
	}

	public void sendHttpErrorCode(HttpServletRequest req, 
	                    HttpServletResponse resp, WsSrvException e) {
		int errId = e.getErrorCode();
		sendHttpErrorCodeEx(req, resp,  ErrorList.getHttpErrorById(errId), 
														  errId, e.getErrorInfo(), e.getDetailMsgs());
	}
	
	public void sendHttpErrorCode(HttpServletRequest req, 
	    HttpServletResponse resp, int errId, String errInfo) {
		sendHttpErrorCodeEx(req, resp,  ErrorList.getHttpErrorById(errId), 
																									  errId, errInfo, null);
	}

	public void sendHttpErrorCode(HttpServletRequest req, 
	    HttpServletResponse resp, int httpCode,
			    int errId, String errInfo, String errDetail) {
		sendHttpErrorCodeEx(req, resp, httpCode, 
		                errId, errInfo, new String[] { errDetail });
	}

	public void sendHttpErrorCode(HttpServletRequest req, 
	    HttpServletResponse resp, int httpCode, WsSrvException e) {
		sendHttpErrorCodeEx(req, resp, httpCode, e.getErrorCode(), 
				e.getErrorInfo(), e.getDetailMsgs());
	}

	public void sendHttpErrorCodeEx(HttpServletRequest req, 
	                HttpServletResponse resp, int httpCode,
	                      int errId, String errInfo, String[] errDetails) {
		sendHttpErrorCodeEx(req, resp, httpCode, errId, 
				ErrorList.getErrorById(errId), errInfo, errDetails);
	}
	
	public void sendHttpErrorCodeEx(HttpServletRequest req, 
	    HttpServletResponse resp, int httpCode, int errId, String errMsg, 
	      String errInfo, String[] errDetails) {
		// Log result
		error(req, httpCode, errId, errInfo, errDetails);

		// Add extra headers
		setResponseHeaders(resp, 0, null);
		
		try {
			resp.sendError(httpCode, getErrorMsg(errId, errMsg, 
			    errInfo, errDetails, isTrace(req), isMinfied(req)));
		} catch (IOException e) {
			// Do Nothing
		}
	}

	public String getErrorMsg(int errId, String errMsg, String errInfo, 
										String[] errDetails, boolean trace, boolean minified) {
		// Check if any user friendly info
		String info = " INFO: " + (Utils.isEmpty(errInfo) ? 
				"No additional details for this error" : errInfo);

		// Combine details into comma delimited string
		String dmsg = "";

		// Allow details on the client based on the trace context parameter
		if (trace && !Utils.isEmpty(errDetails) && errDetails.length > 0) {
			for (String s : errDetails)
				dmsg += " " + s + ";";
			dmsg = ". DETAILS:" + dmsg;
		}
		
		return "ERROR #" + errId + " - " + errMsg + "." + info + dmsg;
	}
	
	protected void sendNotImplemented(HttpServletRequest req, 
	                  HttpServletResponse resp) throws IOException {
		checkSendErrorSimple(req, resp, 99);
	}
	
	protected void checkSendErrorSimple(HttpServletRequest req, 
	          HttpServletResponse resp, int errId) throws IOException {
		checkSendError(req, resp, errId, null);
	}
	
	protected void checkSendError(HttpServletRequest req, 
	    HttpServletResponse resp, int errId, String errInfo) throws IOException {
		checkSendError(req, resp, errId, errInfo, null);
	}
	
  protected void checkSendError(HttpServletRequest req, 
      HttpServletResponse resp, WsSrvException e) throws IOException {
    checkSendError(req, resp, e.getErrorCode(), e.getMessage(), 
              e.getErrorInfo(), e.getDetailMsgs());
  }
  
  protected void checkSendError(HttpServletRequest req, 
      HttpServletResponse resp, int errId, String errInfo, 
                    String[] errDetails) throws IOException {
    checkSendError(req, resp, errId, 
        ErrorList.getErrorById(errId), errInfo, errDetails);
  }
  
  protected void checkSendError(HttpServletRequest req, 
	     HttpServletResponse resp, int errId, String errInfo, 
	                     String dmsg, Throwable ex) throws IOException {
	   String detail = ex.getMessage() != null ? ex.getMessage() : 
	                                         ex.getCause().getMessage();
	   checkSendError(req, resp, errId, ErrorList.getErrorById(errId), 
	        errInfo, dmsg != null ? new String[] {dmsg, detail} : 
	                                        new String[] {detail});
	}
	
	void checkSendError(HttpServletRequest req, HttpServletResponse resp, 
	            int errId, String errMsg, String errInfo, String[] errDetails)
	                                                        throws IOException {
		int httpCode =  ErrorList.getHttpErrorById(errId);

		if (httpCode == 0)
			printErrorAsJsonEx(req, resp, errId, 
			                  errMsg, errInfo, errDetails, null);
		else
			sendHttpErrorCodeEx(req, resp, httpCode, 
					        errId, errMsg, errInfo, errDetails);		
	}

	public ISecurityProvider getSecurityProvider() {
		return (ISecurityProvider) getServletContext().getAttribute("sp");
	}

	/**
	 * Read from request all parameters started with "param_" 
	 * 			and return corresponded values
	 * @param req Http Request
	 * @return HashMap with parameters and values
	 */
	public HashMap<String, String> getReqParamValues(HttpServletRequest req) {
		HashMap<String, String> res = new HashMap<String, String>();
		
		Enumeration<String> params = req.getParameterNames();
		while (params.hasMoreElements()) {
			String pname = params.nextElement();
			if (Constants.PARAM_VAL.matcher(pname).matches()) {
			  res.put(pname.substring(
			      Constants.PARAM_VAL_SUFIX.length(), pname.length()), 
			                                            req.getParameter(pname));
			}
		}	
		return res;
	}
	
	String checkSecurityToken(HttpServletRequest req,
	                        HttpServletResponse resp) throws WsSrvException {
	  return checkSecurityToken(req, resp, true);
	}
	
	/**
	 * Check if security token exists in cookies and optionally validate
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @param fvalidate Validation flag. False only for logout
	 * @return security token
	 * @throws WsSrvException
	 */
	String checkSecurityToken(HttpServletRequest req, 
	       HttpServletResponse resp, boolean fvalidate) throws WsSrvException {
	  
	  if (!hasSecurity(req))
	        return null;
	  
    // Find Secure Token from request
	  String stoken;
	  try {
      Cookie sc = getSecurityCookie(req);
      
      if (sc == null)
        //-- 1
        throw new WsSrvException(1, Constants.STOKEN_NOT_FOUND, 
                    getSecurityCookieName(req) + " cookie not found");
    
      stoken = sc.getValue();
	  } catch (WsSrvException e) {
	    if (e.getErrorCode() == 97) {
	      // Cookihe empty. It might be Cross Domain request.
	      // Check for security token in header
	      stoken = getSecurityToken(req);
	    
	      if (stoken == null)
	        //-- 5
	        throw new WsSrvException(5, Constants.STOKEN_NOT_FOUND, 
	                       "Neiher Security cookie nor header found in request");

	    } else {
	      throw e;
	    }
	  }
	  
    // Check if extra validation required
    if (fvalidate) {
      ISecurityProvider sprovider = getSecurityProvider();
      sprovider.validate(req, stoken);
    
      // Check if restricted user defined
      String uid = getContextAttribute(req, "user_id");
      String uname = sprovider.getUser(stoken);
      if (uid != null && !uid.equals(uname))
        //-- 96
        throw new WsSrvException(96, AbstractSecurityProvider.AUTH_FAILED_MSG, 
                                      "User Id '" + uname + "' is not allowed");
    }
    
    return stoken;
	}
	
	protected Cookie getSecurityCookie(HttpServletRequest req) throws WsSrvException {
    return getCookieByName(req.getCookies(), getSecurityCookieName(req));
  }
  
  protected Cookie getCookieByName(Cookie[] cookies, String name) throws WsSrvException {
    if (cookies == null)
      //-- 97
      throw new WsSrvException(97, "Security Token not found", "Empty cookie");
    
    for (Cookie c : cookies) {
      if (c.getName().equals(name))
        return c;
    }
    
    return null;
  }
  
  private String getSecurityToken(HttpServletRequest req) {
    return req.getHeader(Constants.SECURE_TOKEN_NAME.toLowerCase());
  }
  
	/********************************************/
	/**********         LOGGING       ***********/
	/********************************************/

  protected void error(HttpServletRequest req, String s) {
		Utils.error(getLogger(req), _ts.get(), s);
	}

  protected void error(HttpServletRequest req, Exception e) {
		error(req, e.getMessage());
	}

  protected void error(HttpServletRequest req, WsSrvException e) {
		error(req, e.getErrorCode(), e.getErrorInfo(), e.getDetailMsgs());
	}

  void error(HttpServletRequest req, int errId, String errInfo, 
                                                  String[] errDetails) {
		error(req, "ERROR #" + errId +
		    (!Utils.isEmpty(errInfo) ? "; INFO: " + errInfo + ";" : ""));
		error(req, errDetails);
	}

  public void error(HttpServletRequest req, int errId, String[] errDetails) {
		error(req, "ERROR #" + errId);
		error(req, errDetails);
	}
	
  void error(HttpServletRequest req, int httpCode, int errId, 
	                        String errInfo, String[] errDetails) {
		error(req, "ERROR #" + errId + "/" + httpCode + 
		            (!Utils.isEmpty(errInfo) ? "; INFO: " + errInfo + ";" : ""));
		error(req, errDetails);
	}
	
  void error(HttpServletRequest req, String[] dmsg) {
		if (Utils.isEmpty(dmsg))
			return;
		for (String s : dmsg)
			error(req, "ERROR DETAILS:" + s);
	}

  void error(HttpServletRequest req, int id, String msg, String dmsg) {
		error(req, " ERROR #" + id + (msg != null ? "; INFO: " + msg + ";" : ""));
		if (!Utils.isEmpty(dmsg))
			error(req, new String[] {dmsg});
	}

  void error_ex(HttpServletRequest req, String key, String msg) {
		error(req, key + ":[" + msg + "]");
	}

  protected void warn(HttpServletRequest req, String msg) {
		Utils.warn(getLogger(req), _ts.get(), msg);
	}

  public void debug(HttpServletRequest req, String msg) {
		Utils.debug(getLogger(req), _ts.get(), msg);
	}

  void debug_ex(HttpServletRequest req, String key, String value) {
		debug(req, key + ":[" + value + "]");
	}

  void debug_ex(HttpServletRequest req, String key, int value) {
		debug(req, key + ":[" + value + "]");
	}

  void debug_ex(HttpServletRequest req, String key, String[] value) {
		String s = value[0];
		for (int i = 1; i < value.length; i++)
			s += " " + value[i];
		debug(req, key + ":[" + s + "]");
	}

  void fatal(HttpServletRequest req, String msg) {
		Utils.fatal(getLogger(req), _ts.get(), msg);
	}

  protected void info(HttpServletRequest req, String msg) {
		Utils.info(getLogger(req), _ts.get(), msg);
	}
	
	/**
   * Get System public parameter
   * 
   * @param param Param name
   * @return Param value or empty if param not found
   */
  public String getSysParam(HttpServletRequest req, String param) {
    String res = "";

    @SuppressWarnings("unchecked")
    HashMap<String, String> sparams = 
      (HashMap<String, String>) req.getSession().
              getServletContext().getAttribute("sparams");
    
    if (sparams == null)
       return res;
    
    String pval = sparams.get(param);
    
    if (pval != null)
      res = pval;
    
    return res;
  }

  /********************************************/
  /**********   Session Variables   ***********/
  /********************************************/

  public String getContextAttribute(HttpServletRequest req, String key) {
    return (String) req.getSession().
                getServletContext().getAttribute(key);
  }
  
  public String getConfigDir(HttpServletRequest req) {
    return getContextAttribute(req, "cfg_dir");
  }

  public String getDefaultLang(HttpServletRequest req) {
    return getContextAttribute(req, "default_lang");
  }

  public Logger getLogger(HttpServletRequest req) {
    return (Logger) req.getSession().
                    getServletContext().getAttribute("log");
  }
  
  public boolean isMinfied(HttpServletRequest req) {
    return (Boolean) req.getSession().
                  getServletContext().getAttribute("minified");
  }
  
  public boolean hasSecurity(HttpServletRequest req) {
    return (Boolean) req.getSession().
                  getServletContext().getAttribute("security");
  }
  
  public String getSecurityCookieName(HttpServletRequest req) {
    return getContextAttribute(req, "scookie_name");
  }
  
  public boolean isTrace(HttpServletRequest req) {
    return (Boolean) req.getSession().
                    getServletContext().getAttribute("trace");
  }
  
  public WebAppInfo getWebInfo(HttpServletRequest req) {
    return (WebAppInfo) req.getSession().
        getServletContext().getAttribute("web_info");
  }
}
