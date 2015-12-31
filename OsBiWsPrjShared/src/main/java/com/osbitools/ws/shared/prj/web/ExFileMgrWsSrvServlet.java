/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-12-09
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
import java.util.HashMap;
import java.util.HashSet;
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
import com.osbitools.ws.shared.prj.utils.EntityUtils;
import com.osbitools.ws.shared.prj.utils.ExFileUtils;

/**
 * 
 * ExFile Manager. Implements all CRUD spec
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

@WebServlet("/rest/ex_file")
public class ExFileMgrWsSrvServlet extends GenericPrjMgrWsSrvServlet {
  // Default serial version uid
  private static final long serialVersionUID = 1L;
  
  // Name of ExFile name parameter
  private static final String FNAME = "name";
  
  // Name of Directory Name parameter
  private static final String DNAME = "dname";
  
  private HashSet<String> getExtListByDirName(EntityUtils eut, 
                                    String dname) throws WsSrvException {
  	HashMap<String, String[]> map = eut.getSubDirExtList();
  	if (map == null)
      //-- 232
      throw new WsSrvException(232, "Subdirectories are not supported");
  	  
  	HashSet<String> extl = eut.getExtList(dname);
    if (extl == null)
      //-- 234
      throw new WsSrvException(234, "Unknown subdirectory [" + dname + "]");
    
    return extl;
  }
  
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
    
    // Get File name and extension
    EntityUtils eut = getEntityUtils(req);
    String name = req.getParameter(FNAME);
    String dname = req.getParameter(DNAME);
    
    HashSet<String> extl;
    try {
      extl = getExtListByDirName(eut, dname);
    } catch (WsSrvException e) {
    	checkSendError(req, resp, e);
  		return;
    }
    
    // Check if rename required
    String rename = req.getParameter("rename_to");
    if (rename != null) {
    	if (rename.equals("")) {
    		//-- 235
    		checkSendError(req, resp, 235, "Empty parameter rename_to");
    		return;
      }
    
    	try {
    		GenericUtils.renameFile(getPrjRootDir(req), name, rename, extl, dname);
      } catch (WsSrvException e) {
      	checkSendError(req, resp, e);
      }
    } else {
	    try {
	      if (!ServletFileUpload.isMultipartContent(req)) {
	      	//-- 255
	        checkSendError(req, resp, 255, "Request is not multipart");
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
          //-- 256
          checkSendError(req, resp, 256, "Error parsing request", null, e);
          return;
        }

	      for (FileItem fi : items) {
	        if (!fi.isFormField()) {
	          in = fi.getInputStream();
	          break;
	        }
	      }
	      
	      if (in == null) {
	      	//-- 257
	        checkSendError(req, resp, 257, "Multipart section is not found");
          return;
        }
	      
	      String res = ExFileUtils.createFile(getPrjRootDir(req), name, in, 
	          extl, dname, getReqParamValues(req), getEntityUtils(req), 
	                                                      isMinfied(req));
	      printJson(resp, Utils.isEmpty(res) ? "{}" : res);
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
  	
  	// Check if only info requires
    HashSet<String> extl = null;
  	String info = req.getParameter("info");
  	String fname = req.getParameter(FNAME);
  	EntityUtils eut = getEntityUtils(req);
  	String dname = req.getParameter(DNAME);
    Boolean fmask = PrjMgrConstants.EXT_FILE_LIST_MASK.
                                  matcher(fname).matches();
    if (!fmask) {
      // Check if file extension supported
      String ext;
      try {
        ext = GenericUtils.getFileExt(fname);
      } catch (WsSrvException e) {
        checkSendError(req, resp, e);
        return;
      }
      
      if (!eut.hasExt(ext)) {
      	//-- 258
        checkSendError(req, resp, 258, "File extension '" + 
                                              ext + "' is not supported");
        return;
      }
    }
    
    try {
	    extl = getExtListByDirName(eut, dname);
    } catch (WsSrvException e) {
    	checkSendError(req, resp, e);
    	return;
    }
  	if (Utils.isEmpty(info)) {
	  	try {
	  	  // Check if file list required
	      if (fmask) {
	        resp.getWriter().print(GenericUtils.getResDirExtList(
	            getPrjRootDir(req), fname, dname, 
	                          eut.getExtLstFilenameFilter(dname)));
	      } else {
	        // Get single file
	        File f = GenericUtils.checkFile(getPrjRootDir(req), 
	                                                  fname, extl, dname, true);
	        printMimeFile(resp, Files.probeContentType(f.toPath()), 
	                                  fname, GenericUtils.readFile(f), "utf-8");
	      }
	    } catch (WsSrvException e) {
	      checkSendError(req, resp, e);
	    }
  	} else {
  	  if (info.toLowerCase().equals("file_info")) {
          try {
            printJson(resp, 
                GenericUtils.getInfo(getPrjRootDir(req), fname, extl, dname, 
                                    getReqParamValues(req), isMinfied(req)));
          } catch (WsSrvException e) {
            return;
          }
  	  } else {
  	    if (!eut.hasInfoReq(info)) {
  	      //-- 252
  	      checkSendError(req, resp, 252, null, 
  	              new String[] {"Info request \'" + 
  	                      info + "\' is not supported."});
  	      return;
  	    }

    	  try {
    	    printJson(resp, 
    	     eut.execInfoReq(info, getPrjRootDir(req), 
    	            fname, extl, dname, getReqParamValues(req), isMinfied(req)));
        } catch (WsSrvException e) {
          	checkSendError(req, resp, e);
        }
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
    
    // Get File name and extension
    EntityUtils eut = getEntityUtils(req);
    String name = req.getParameter(FNAME);
    String dname = req.getParameter(DNAME);
    
    HashSet<String> extl;
    try {
      extl = getExtListByDirName(eut, dname);
    } catch (WsSrvException e) {
      checkSendError(req, resp, e);
      return;
    }

    try {
    	GenericUtils.deleteFile(getPrjRootDir(req), name, extl, dname);
    } catch (WsSrvException e) {
    	checkSendError(req, resp, e);
    }
  }
  
  @Override
  protected String[] getMandatoryParameters() {
    return new String[] {FNAME, DNAME};
  }
}
