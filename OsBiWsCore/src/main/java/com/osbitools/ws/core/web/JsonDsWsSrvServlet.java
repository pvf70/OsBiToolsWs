/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
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

package com.osbitools.ws.core.web;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.core.daemons.DsDescrResource;
import com.osbitools.ws.core.daemons.DsFilesCheck;
import com.osbitools.ws.core.daemons.LsFilesCheck;
import com.osbitools.ws.core.proc.DataSetProcessor;
import com.osbitools.ws.core.shared.ContextConfig;
import com.osbitools.ws.core.shared.CoreConstants;
import com.osbitools.ws.core.shared.TraceRecorder;

/**
 * 
 * Convert data set into json format
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

@WebServlet("/rest/ds")
public class JsonDsWsSrvServlet extends GenericCoreDsWsSrvServlet {

  // Default serial version uid
  private static final long serialVersionUID = 1L;

  //Name of parameter with request keys
  private static final String PARAM_NAME = "map";
 
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    long dts = System.currentTimeMillis();

    try {
      super.doGet(req, resp);
    } catch (ServletException e) {
      if (e.getCause().getClass().equals(WsSrvException.class)) {
        // Authentication failed
        checkSendError(req, resp, (WsSrvException) e.getCause());
        return;
      } else {
        throw e;
      }
    }

    String map = req.getParameter(PARAM_NAME);
    
    if (map.equals("*")) {
      // Get list of all loaded maps
      printText(resp, getDsMapListSorted(req));
      return;
    } 
    
    // Check optional parameters
    String lang = req.getParameter("lang");
    lang = (Utils.isEmpty(lang)) ? getDefaultLang(req) : lang;

    // Load map
    DsDescrResource dsr = getDsMap(req, map);
    if (dsr == null) {
      //-- 100
      checkSendError(req, resp, 100, "Map not found for '" + 
                                                    map + "'");

      return;
    }

    // Init warnings array
    ArrayList<String> warn = new ArrayList<String>();

    // Check if trace required
    TraceRecorder trace = new TraceRecorder(dts, isTrace(req) && 
                                "on".equals(req.getParameter("trace")));

    // Ready to launch
    trace.record(CoreConstants.TRACE_START_PROC);

    String res;

    // Prepare context configuration
    ContextConfig cfg = new ContextConfig(getRequestId(), getDsDir(req), 
                                            getLogger(req), isMinfied(req), 
      (LsFilesCheck) req.getSession().
                                getServletContext().getAttribute("lcheck"));
    try {
      DataSetProcessor dsp = new DataSetProcessor(dsr, getRequestParams(
                                               req, dsr.getResource()), cfg);
      res = dsp.getJson(map, req.getParameterMap(), 
                                    lang, trace, warn, isMinfied(req));
    } catch (WsSrvException e) {
      printErrorAsJson(req, resp, e);

      return;
    }

    printJson(resp, res);
  }

  HashMap<String, Object> getRequestParams(HttpServletRequest request,
      DataSetDescr dsDescr) throws WsSrvException {
    HashMap<String, Object> res = new HashMap<String, Object>();

    // Check request parameters
    RequestParameters params = dsDescr.getReqParams();
    if (params != null) {
      for (RequestParameter param : params.getParam()) {
        String pname = param.getName();
        String jtype = param.getJavaType();

        String pvalue = request.getParameter(pname);
        if (pvalue == null)
          continue;

        // Check if parameter correct type/size
        Object value;
        try {
          value = instValue(pvalue, jtype);
        } catch (ClassNotFoundException e) {
          //-- 127
          throw new WsSrvException(127, "Invalid class name " + jtype, e);
        } catch (Exception e) {
          //-- 130
          throw new WsSrvException(130, "Unable instantinate variable '"
              + pvalue + "' with class " + jtype, e);
        }

        // Check size for string types
        Integer psize = param.getSize();
        if (jtype.equals("java.lang.String") && psize != null && psize > 0
            && value.toString().length() > psize) {

          //-- 131
          throw new WsSrvException(131, "Value '" + value + "' for parameter '"
              + pname + "' exceed size " + psize);
        }

        res.put(pname, value);
      }
    }
    return res;
  }

  Object instValue(String pvalue, String ptype) throws ClassNotFoundException,
      NoSuchMethodException, SecurityException, InstantiationException,
      IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    Class<?> ctype = Class.forName(ptype);

    Constructor<?> co = ctype.getConstructor(String.class);
    return co.newInstance(pvalue);
  }
  
  private String getDsMapListSorted(HttpServletRequest req) {
    DsFilesCheck dcheck = (DsFilesCheck) req.getSession().
                        getServletContext().getAttribute("dcheck");
    String[] list = dcheck.getDsMapList();
    Arrays.sort(list);
    
    String res = "";
    
    for (String name: list)
      // Strip xml extension when appending
      res += "," + name.substring(0, name.length() - 4);
    
    return res.equals("") ? "" : res.substring(1);
  }
  
  public String getDsDir(HttpServletRequest req) {
    return getConfigDir(req) + File.separator + DsConstants.DS_DIR;
  }
}
