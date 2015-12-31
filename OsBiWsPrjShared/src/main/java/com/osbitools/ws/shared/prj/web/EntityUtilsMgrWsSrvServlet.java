/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-03-12
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.PrjMgrConstants;

/**
 * 
 * DsMapUtils Manager. Implements all CRUD spec
 * 
 * doPut - Create External File into the disk
 * doGet - Read External File. If info parameter required than read
 *      either "columns" or "file_info"
 *      Get all ext file for mask 'project.*.ext'
 * doPost - Update/Rename External File
 * doDelete - Delete External File
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

@WebServlet("/rest/entity_utils")
public class EntityUtilsMgrWsSrvServlet extends GenericPrjMgrWsSrvServlet {
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
    
    // Get DsMap name
    String name = req.getParameter(PrjMgrConstants.REQ_NAME_PARAM);

    // Check if rename required
   
    try {
      if (!ServletFileUpload.isMultipartContent(req)) {
        checkSendError(req, resp, 200, "Request is not multipart");
        return;
      }

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(
                                      getDiskFileItemFactory(req));
      InputStream in = null;
      List<FileItem> items;
      try {
        items = upload.parseRequest(req);
      } catch (FileUploadException e) {
        checkSendError(req, resp, 201, "Error parsing request", null, e);
        return;
      }

      for (FileItem fi : items) {
        if (!fi.isFormField()) {
          in = fi.getInputStream();
          break;
        }
      }
      
      if (in == null) {
        checkSendError(req, resp, 202, "File Multipart section is not found");
        return;
      }
      
      boolean minified = isMinfied(req);
      printJson(resp, "{" + Utils.getCRT(minified) + 
          "\"entity\":" + Utils.getSPACE(minified) + 
                getEntityUtils(req).create(getPrjRootDir(req), 
                                            name, in, false, minified) + "}");
      in.close();
    } catch (WsSrvException e) {
      checkSendError(req, resp, e);
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
  	
  	// Check if only info requires
  	boolean fcheck = req.getParameter("check") != null;
  	String name = req.getParameter(PrjMgrConstants.REQ_NAME_PARAM);

  	if (fcheck) {
  	  try {
  	    // Get info about single file
        printJson(resp, GenericUtils.getInfo(getPrjRootDir(req), name, 
              getBaseExt(req), getReqParamValues(req), isMinfied(req)));
      } catch (WsSrvException e) {
        return;
      }
  	} else {
  	  try {
        // Download file
  	    File f = GenericUtils.checkFile(getPrjRootDir(req), 
                                                       name, getBaseExt(req));
  	    printMimeFile(resp, Files.probeContentType(f.toPath()), 
  	                                 name, GenericUtils.readFile(f), "utf-8");
      } catch (WsSrvException e) {
        checkSendError(req, resp, e);
      }
  	}
  }
  
  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
                                      throws ServletException, IOException {
    sendNotImplemented(req, resp);
  }
  
  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
                                      throws ServletException, IOException {
    sendNotImplemented(req, resp);
  }
  
  @Override
  protected String[] getMandatoryParameters() {
    return new String[] {PrjMgrConstants.REQ_NAME_PARAM};
  }
}
