/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
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

import static org.junit.Assert.*;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.shared.*;

/**
 * Basic Single Thread Web Test
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public abstract class BasicWebUtils {

  //Encoded Dot (.)
  public static String UTF8_DOT;
 
  public static String BASE_URL = "/rest";

  public static final String VERSION_APP_NAME = "/version";
  
  public static String VERSION_PATH = TestConstants.JETTY_SRV_URL +
                                               BASE_URL + VERSION_APP_NAME;
  
  public static final String CONFIG_APP_NAME = "/cfg";
  
  public static String CFG_PATH = TestConstants.JETTY_SRV_URL + 
                                                BASE_URL + CONFIG_APP_NAME;

  public static String AUTH_PATH = TestConstants.JETTY_SRV_URL + 
                                                      BASE_URL + "/auth";
  
  public static String LOGOUT_PATH = TestConstants.JETTY_SRV_URL + 
                                                    BASE_URL + "/logout";

  public static final String HTTP_RESP_MISSING_PARAMS = 
      "ERROR #98 - Missing mandatory parameter(s). " +
                          "INFO: Missing [xxx] parameter";

  static final String HTTP_RESP_SESSION_TIMEOUT = 
    "ERROR #4 - Session Timeout. INFO: No additional details for this error. " +
        "DETAILS: Session 'xxx' completed;";

  public static final WebResponse HTTP_RESP_OK = new WebResponse("");

  public static final WebResponse HTTP_RESP_NON_AUTH = new WebResponse(401,
    "ERROR #1 - Not Authenticated. INFO: Security cookie not found." +
                                " DETAILS: STOKEN cookie not found;");
  
  public static final WebResponse HTTP_RESP_NON_AUTH_ALL = new WebResponse(401,
      "ERROR #5 - Not Authenticated. INFO: Security Token not found." +
          " DETAILS: Neiher Security cookie nor header found in request;");
  
  public static final WebResponse HTTP_RESP_NOT_ALLOWED = 
      new WebResponse(405, "ERROR #99 - Method Not Allowed." +
                    " INFO: No additional details for this error");
  
  public static final WebResponse HTTP_RESP_INVALID_TOKEN = new WebResponse(
      401, "ERROR #3 - Invalid Security Token. INFO: No additional details" +
          " for this error. DETAILS: Invalid token 'xxx';");

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    UTF8_DOT = URLEncoder.encode(".", "UTF-8");
    
    // check if web server started
    try {
      assertEquals("local", readGetEx(VERSION_PATH));
      assertNotSame("", readGetEx(TestConstants.JETTY_SRV_URL + "/test.jsp"));
    } catch (Exception e) {
      fail("Jetty Web Server is not started.");
    }
  }

  @Test
  public void urlPenTest() throws Exception {
    // result for each type of HTTP request i.e get, put, post and delete
    HashMap<String, WebResponse> expected = getNonAuthTestExpectedSet();
    if (expected == null)
      // Skip test
      return;
    
    testNonAuth(expected);
    testNoNameParam(expected);
  }

  public String login() throws Exception {
    String stoken = null;

    WebResponse res = readPost(AUTH_PATH, 
        "usr=" + TestConstants.TEST_USER +
          "&pwd=" + TestConstants.TEST_PASSWORD);
    testWebResponse("Login", HTTP_RESP_OK, res);

    // Read Security Token
    String cookie = res.getCookie();
    if (cookie != null) {
      cookie = cookie.substring(0, cookie.indexOf(';'));
      String[] carr = cookie.split("=");
      if (carr[0].equals(Constants.SECURE_TOKEN_NAME))
        stoken = carr[1];
    }

    return stoken;
  }

  protected void logout(String stoken, String msg)
      throws MalformedURLException, IOException {
    testWebResponse("Logout after " + msg + ": " + stoken, 
                    HTTP_RESP_OK, readGet(LOGOUT_PATH, stoken));
  }

  // Special static version
  public static String readGetEx(String url) throws MalformedURLException,
      IOException {
    String res = "";
    BufferedReader reader = null;

    try {
      HttpURLConnection conn;
      conn = (HttpURLConnection) (new URL(url)).openConnection();

      reader = new BufferedReader(
          new InputStreamReader(conn.getInputStream()));

      String line;

      while ((line = reader.readLine()) != null)
        res += line;

    } finally {
      if (reader != null)
        reader.close();
    }

    return res;
  }

  public WebResponse readGet(String url) {
    return readGet(url, null);
  }

  public WebResponse readGet(String url, String stoken) {
    // Get params
    return readGet(url, null, stoken);
  }

  public WebResponse readGet(String url, String sheader, String stoken) {
    // Get params
    return readHttpData("GET", url, "", sheader, stoken);
  }
  
  public WebResponse readPost(String url, String params) {
    return readPost(url, params, null, true);
  }
  
  public WebResponse readPost(String url, String params, String stoken) {
    return readPost(url, params.getBytes(), stoken);
  }

  public WebResponse readPost(String url, byte[] params, String stoken) {
    return readPost(url, params, stoken, false);
  }

  public WebResponse readPost(String url, 
                         String params, String stoken, boolean auth) {
    return readPost(url, params.getBytes(), stoken, auth);
  }
   
  public WebResponse readPost(String url, 
                          byte[] params, String stoken, boolean auth) {
    String ctype = (auth) ? 
        "application/x-www-form-urlencoded; charset=UTF-8" : 
                                      "text/plain; charset=UTF-8";
    
    return readHttpData("POST", url, params, stoken, ctype);
  }

  public WebResponse readPut(String url, String params) {
    return readPut(url, params, null);
  }

  public WebResponse readPut(String url, String params, String stoken) {
    return readHttpData("PUT", url, params, stoken);
  }

  public WebResponse readDelete(String url, String params) {
    return readDelete(url, params, null);
  }

  public WebResponse readDelete(String url, String params, String stoken) {
    return readHttpData("DELETE", url, params, stoken);
  }

  public WebResponse readHttpData(String method, String url, 
                                        String params, String stoken) {
    return readHttpData(method, url, params.getBytes(), null, stoken, null);
  }

  public WebResponse readHttpData(String method, String url, 
                        String params, String sheader, String stoken) {
    return readHttpData(method, url, params.getBytes(), sheader, stoken, null);
  }
  
  public WebResponse readHttpData(String method, String url, 
                                          byte[] params, String stoken) {
    return readHttpData(method, url, params, stoken, null);
  }
  
  public WebResponse readHttpData(String method, String url, byte[] params,
                                                String stoken, String ctype) {
    return readHttpData(method, url, params, null, stoken, ctype);
  }
  
  public WebResponse readHttpData(String method, String url, byte[] params,
                            String sheader, String stoken, String ctype) {
    WebResponse res;
    InputStreamReader in = null;
    HttpURLConnection conn = null;
    Boolean fparams = params.length != 0;

    try {
      conn = (HttpURLConnection) (new URL(url)).openConnection();
      conn.setDoOutput(fparams);
      conn.setRequestMethod(method);

      if (ctype != null) {
        conn.setRequestProperty("Content-Type", ctype);
        conn.setRequestProperty("Content-Length",
                                  String.valueOf(params.length));
      }
      
      if (stoken != null)
        conn.setRequestProperty(sheader == null ? "Cookie" : sheader, 
          (sheader == null ? Constants.SECURE_TOKEN_NAME + "=" : "") + stoken);

      // Initiate connection
      conn.connect();
      
      if (fparams) {
        OutputStream os = null;

        try {
          os = conn.getOutputStream();
          os.write(params);
        } catch (IOException e) {
          return new WebResponse(conn);
        } finally {
          if (os != null)
            os.close();
        }
      }

      // Response code
      int code;

      try {
        in = new InputStreamReader(conn.getInputStream());
      } catch (IOException e) {
        return new WebResponse(conn);
      }

      // Read response
      try {
        code = conn.getResponseCode();
      } catch (IOException e) {
        return null;
      }

      try {
        StringWriter out = new StringWriter();
        GenericUtils.copy(in, out);
        
        String msg = out.toString();
        out.close();
        
        in.close();
        
        res = new WebResponse(code, 
              msg.replaceFirst("\"request_id\":\\d*",
                                "\"request_id\":"));

        // Read and remember cookie for POST method
        if (method == "POST") {
          res.setCookie(conn.getHeaderField("Set-Cookie"));
        }

      } catch (IOException e) {
        return new WebResponse(code);
      }

    } catch (IOException e) {
      System.out.println("HTTP Request failed. " + e.getMessage());
      return null;
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          // Do nothing
        }
      }

      if (conn != null)
        conn.disconnect();
    }

    return res;
  }

  public WebResponse uploadFile(String path, String fname, InputStream in, 
                String stoken) throws ClientProtocolException, IOException {
    
    HttpPost post = new HttpPost(path);
    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    StringBody fn = new StringBody(fname, ContentType.MULTIPART_FORM_DATA);
    
    builder.addPart("fname", fn);
    builder.addBinaryBody("file", in, ContentType.APPLICATION_XML, fname);
    
    BasicCookieStore cookieStore = new BasicCookieStore();
    
    if (stoken != null) {
      BasicClientCookie cookie = new BasicClientCookie(
                  Constants.SECURE_TOKEN_NAME, stoken);
      cookie.setDomain(TestConstants.JETTY_HOST);
      cookie.setPath("/");
      cookieStore.addCookie(cookie);
    }
    
    TestConstants.LOG.debug("stoken=" + stoken);
    HttpClient client = HttpClientBuilder.create().
                    setDefaultCookieStore(cookieStore).build();
    HttpEntity entity = builder.build();
    
    post.setEntity(entity);
    HttpResponse response = client.execute(post);
    
    String body;
    ResponseHandler<String> handler = new BasicResponseHandler();
    try {
      body = handler.handleResponse(response);
    } catch (HttpResponseException e) {
      return new WebResponse(e.getStatusCode(), e.getMessage());
    }
    
    return new WebResponse(response.getStatusLine().getStatusCode(), body);
  }

  public byte[] downloadFile(String url, String stoken) {
    byte[] res;
    InputStream in = null;
    HttpURLConnection conn = null;
    
    try {
      conn = (HttpURLConnection) (new URL(url)).openConnection();
      conn.setRequestMethod("GET");

      
      if (stoken != null)
        conn.setRequestProperty("Cookie", 
            Constants.SECURE_TOKEN_NAME + "=" + stoken);

      // Initiate connection
      conn.connect();
      

      try {
        in = conn.getInputStream();
      } catch (IOException e) {
        return e.getMessage().getBytes();
      }

      // Read response body
      try {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GenericUtils.copy(in, out);
        res = out.toByteArray();
        out.close();
      } catch (IOException e) {
        return e.getMessage().getBytes();
      }
    } catch (IOException e) {
      return null;
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          // Do nothing
        }
      }

      if (conn != null)
        conn.disconnect();
    }

    return res;
  }
  
  public void testWebResponse(WebResponse expected, WebResponse actual) {
    testWebResponse("", expected, actual);
  }

  public void testWebResponse(String msg, WebResponse expected,
      WebResponse actual) {

    assertNotNull("Empty Expected Web Response", expected);
    assertNotNull("Empty Actual Web Response", actual);

    if (expected.getCode() != actual.getCode())
      System.out.println("Message: " + actual.getMsg());

    assertEquals(msg + " Code", expected.getCode(), actual.getCode());

    testMsg(msg, expected.getMsg(), actual.getMsg());
  }

  public static void testMsg(String msg, String expected, String actual) {
    if (expected != null && !expected.isEmpty() && expected.charAt(0) == 64 &&
        expected.charAt(expected.length() - 1) == 64) {
      // Process regular expression
      Pattern p = Pattern.compile(expected.substring(1, expected.length() - 1));
      assertTrue("Checking regex " + expected + " -> " + actual,
          p.matcher(actual).matches());
    } else {
      assertEquals(msg, expected, actual);
    }
  }
  
  public abstract HashMap<String, WebResponse> getNonAuthTestExpectedSet();
  
  /**
   * Get base path for web application
   * 
   * @return base path for web application
   */
  public abstract String getWepAppPath();
  /**
   * Name of request parameter used for pen test
   * 
   * @return Name of request parameter
   */
  public String getPenTestParamName() {
    return "name";
  };
  
  private String getWebAppUrl() {
    return TestConstants.JETTY_SRV_URL + BASE_URL + getWepAppPath();
  }
  
  /**
   * Test non-authenticated url without parameter or with name parameter
   * 
   * @throws Exception
   * 
   */
  public void testNonAuth(HashMap<String, 
                    WebResponse> expected) throws Exception {
    String url = getWebAppUrl();
    
    // Name of parameter (optional). If name of parameter is 
    //          not supplied than check for url?param request is skipped
    String pname = getPenTestParamName();
    String purl = (Utils.isEmpty(pname)) ? null : url + "?" + pname;
    
    testNonAuth(url, purl, expected);
  }
  
  /**
   * Test for only non-authenticated get request 
   * 
   * @param url URL Get request
   * 
   * @throws Exception
   */
  public void testBadGet(String url) throws Exception {
    // Expected result from pen test
    HashMap<String, WebResponse> expected = 
                                  new HashMap<String, WebResponse>();
    
    expected.put("get", HTTP_RESP_NON_AUTH_ALL);
    expected.put("put", HTTP_RESP_NOT_ALLOWED);
    expected.put("post", HTTP_RESP_NOT_ALLOWED);
    expected.put("delete", HTTP_RESP_NOT_ALLOWED);
    
    testNonAuth(url, null, expected);
  }
  
  public void testNonAuth(String url1, String url2, 
                HashMap<String, WebResponse> expected) throws Exception {
    testWebResponse("Empty NonAuth Get", 
                    expected.get("get"), readGet(url1, null));

    if (url2 != null)
      testWebResponse("NonEmpty NonAuth Get", 
          expected.get("get"), readGet(url2 + "test", null));
    
    testWebResponse("Empty NonAuth Put",  
        expected.get("put"), readPut(url1, "", null));

    if (url2 != null)
      testWebResponse("NonEmpty NonAuth Put", expected.get("put"),
                              readPut(url2 + "test", "", null));
    
    testWebResponse("EmptyNonAuth Post", 
        expected.get("post"), readPost(url1, "", null));

    if (url2 != null)
      testWebResponse("NonEmpty NonAuth Post", expected.get("post"),
                              readPost(url2 + "test", "", null));

    testWebResponse("EmptyNonAuth Delete", 
        expected.get("delete"), readDelete(url1, "", null));

    if (url2 != null)
      testWebResponse("NonEmpty NonAuth Delete", expected.get("delete"), 
         readDelete(url2 + "test", "", null));
  }

  /**
   * Test Empty or non-existing name parameter
   * 
   * @throws Exception
   * 
   */
  public void testNoNameParam(HashMap<String, WebResponse> expected) 
                                                          throws Exception {
    // Name of parameter (optional). If name of parameter is 
    //          not supplied than check for url?param request is skipped
    String pname = getPenTestParamName();
    
    if (pname == null)
      // Don't bother
      return;
    
    String stoken = login();
    
    String url = getWebAppUrl();
    String purl = (Utils.isEmpty(pname)) ? null : url + "?" + pname;

    WebResponse wr = new WebResponse(400,
        HTTP_RESP_MISSING_PARAMS.replace("xxx", "name"));
    
    if (expected.get("get").getCode() == 401) {
      testWebResponse("No name parameter",  wr, 
                            readGet(url, stoken));
      
      if (purl != null)
        testWebResponse("No name parameter",  wr, 
                    readGet(purl, stoken));
    }
    
    if (expected.get("put").getCode() == 401) {
      testWebResponse("Empty Project NonAuth Put", wr, 
                        readPut(url, "", stoken));
      
      if (purl != null)
        testWebResponse("NonEmpty NonAuth Put", wr, 
                  readPut(purl, "", stoken));
    }
      
    if (expected.get("post").getCode() == 401) {
      testWebResponse("Empty NonAuth Post", wr, 
                readPost(url, "", stoken));
      
      if (purl != null)
        testWebResponse("NonEmpty NonAuth Post", wr, 
                  readPost(purl, "", stoken));
    }
      
    if (expected.get("delete").getCode() == 401) {
      testWebResponse("Empty NonAuth Delete", wr, 
                readDelete(url, "", stoken));
      
      if (purl != null)
        testWebResponse("NonEmpty NonAuth Delete", wr, 
                  readDelete(purl, "", stoken));
    }
    
    logout(stoken, "Empty Names");
  }
}
