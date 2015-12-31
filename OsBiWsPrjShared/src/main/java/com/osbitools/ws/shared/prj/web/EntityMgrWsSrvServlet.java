/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-10-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.*;
import com.osbitools.ws.shared.prj.utils.*;

/**
 * 
 * DataSet Manager. Implements all CRUD spec
 * 
 * doPut - Create DsMap XML map into the disk
 * doGet - Read DsMap XML file and convert into JSON
 * doPost - Update/Rename DsMap
 * doDelete - Delete DsMap
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

@WebServlet("/rest/entity")
public class EntityMgrWsSrvServlet extends GenericPrjMgrWsSrvServlet {
  // Default serial version uid
  private static final long serialVersionUID = 1L;
  
  /*
   * (non-Javadoc)
   * @see com.osbitools.shared.web.GenericWsSrvServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      super.doPost(req, resp);
    } catch (ServletException e) {
      if (e.getCause().getClass().equals(WsSrvException.class)) {
        // Authentication failed
        checkSendError(req, resp, (WsSrvException) e.getCause());
        return;
      } else {
        throw e;
      }
    }
    
    String name = req.getParameter(PrjMgrConstants.REQ_NAME_PARAM);
    EntityUtils eut = getEntityUtils(req);

    // Check if rename required
    String rename = req.getParameter("rename_to");
    if (rename != null) {
    	if (rename.equals("")) {
    		checkSendError(req, resp, 230, "Empty parameter rename_to");
    		return;
      }
    
    	try {
    	  GenericUtils.renameFile(getPrjRootDir(req), 
    	                          name, rename, getBaseExt(req));
      } catch (WsSrvException e) {
      	checkSendError(req, resp, e);
      }
    } else {
    	// Read HTTP Stream Data
    	String ds;
    	boolean minified = isMinfied(req);
    	
     	try {
     		ds = readRequestBody(req);
     	} catch (WsSrvException e) {
      	checkSendError(req, resp, e);
      	return;
      }
	    
	    try {
	      // printJson(resp, utils.create(
	          // getPrjRootDir(req), name, ds, true, isMinfied(req)));
	      
	      printJson(resp, "{" + Utils.getCRT(minified) + 
	          "\"entity\":" + Utils.getSPACE(minified) +
	              eut.create(getPrjRootDir(req), name, ds, true, isMinfied(req)) +
	          "}");
	    } catch (WsSrvException e) {
	      checkSendError(req, resp, e);
	    }
    }
  }
  
  /*
   * (non-Javadoc)
   * @see com.osbitools.shared.web.GenericWsSrvServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
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
  	
  	// Get DsMap name
  	String name = req.getParameter(PrjMgrConstants.REQ_NAME_PARAM);
  	EntityUtils eut = getEntityUtils(req);
  	
  	boolean minified = isMinfied(req);
  	try {
  	  printJson(resp, "{" + Utils.getCRT(minified) + 
	      "\"entity\":" + Utils.getSPACE(minified) +
	        eut.getJson(getPrjRootDir(req), name, isMinfied(req)) + 
	                                        "," + Utils.getCRT(minified) + 
        "\"has_log\":" + Utils.getSPACE(minified) + 
          GitUtils.hasLog(getGit(req), getPrjRootDir(req), 
                  name, getBaseExt(req)) + "," + Utils.getCRT(minified) +
        "\"has_diff\":" + Utils.getSPACE(minified) + 
          GitUtils.hasDiff(getGit(req), getPrjRootDir(req), 
                  name, getBaseExt(req)) + Utils.getCR(minified) +
	    "}");
    } catch (WsSrvException e) {
      checkSendError(req, resp, e);
    }
  }
  
  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      super.doPut(req, resp);
    } catch (ServletException e) {
      if (e.getCause().getClass().equals(WsSrvException.class)) {
        // Authentication failed
        checkSendError(req, resp, (WsSrvException) e.getCause());
        return;
      } else {
        throw e;
      }
    }
    
    // Get DsMap name
    String ds;
    boolean minified = isMinfied(req);
   	String name = req.getParameter(PrjMgrConstants.REQ_NAME_PARAM);
   	EntityUtils eut =  getEntityUtils(req);

   	// Read HTTP Stream Data
   	try {
   		ds = readRequestBody(req);
   	} catch (WsSrvException e) {
    	checkSendError(req, resp, e);
    	return;
    }
   	
    try {
      // printJson(resp, utils.create(getPrjRootDir(req), 
         //                       name, ds, false, isMinfied(req)));
      
      printJson(resp, "{" + Utils.getCRT(minified) + 
          "\"entity\":" + Utils.getSPACE(minified) +
              eut.create(getPrjRootDir(req), name, ds, false, isMinfied(req)) +
          "}");
      
    } catch (WsSrvException e) {
    	checkSendError(req, resp, e);
    }
  }
  
  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      super.doDelete(req, resp);
    } catch (ServletException e) {
      if (e.getCause().getClass().equals(WsSrvException.class)) {
        // Authentication failed
        checkSendError(req, resp, (WsSrvException) e.getCause());
        return;
      } else {
        throw e;
      }
    }

    // Get DsMap name
   	String name = req.getParameter(PrjMgrConstants.REQ_NAME_PARAM);
   	
    try {
	    GenericUtils.deleteFile(getPrjRootDir(req), name, getBaseExt(req));
    } catch (WsSrvException e) {
    	checkSendError(req, resp, e);
    }
  }
  
  @Override
  protected String[] getMandatoryParameters() {
    return new String[] {PrjMgrConstants.REQ_NAME_PARAM};
  }
  
}
