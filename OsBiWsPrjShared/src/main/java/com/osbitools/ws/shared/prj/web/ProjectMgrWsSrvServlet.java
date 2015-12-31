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
import com.osbitools.ws.shared.prj.utils.ProjectUtils;

/**
 * 
 * Project Manager. Implements all CRUD spec
 * 
 * doPut - Create new Project into the disk
 * doGet - Read Project content (xml files only)
 * doPost - Rename Project
 * doDelete - Delete Project with all files
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

@WebServlet("/rest/project")
public class ProjectMgrWsSrvServlet extends GenericPrjMgrWsSrvServlet {

  // Default serial version uid
  private static final long serialVersionUID = 1L;
  
  /**
   * Create new project
   */
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
    
    String name = req.getParameter("name");
    debug(req, "Creating project '" + name + "'");
    
    try {
	    ProjectUtils.createProject(getPrjRootDir(req), name);
    } catch (WsSrvException e) {
    	checkSendError(req, resp, e);
    }
    
    debug(req, "Project '" + name + "' successfully created");
  }

  /**
   * Get project or search for projects
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
    
    String name = req.getParameter("name");

    if (name.equals("*")) {
      // Get all projects
      resp.getWriter().print(ProjectUtils.
                        getAllProjects(getPrjRootDir(req)));
    } else {
      // Get all files included into given project
      try {
        resp.getWriter().print(
        		ProjectUtils.getProject(getPrjRootDir(req), 
        		                                      name, getBaseExt(req)));
      } catch (WsSrvException e) {
        checkSendError(req, resp, e);
      }
    }
    
    debug(req, "Project '" + name + "' successfully retrieved");
  }
  
  /**
   * Update project or search for projects
   * Currently only project rename implemented
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
    
    String oldName = req.getParameter("name");
    String newName = req.getParameter("rename_to");

    if (Utils.isEmpty(newName)) {
      //-- 299
    	checkSendError(req, resp, 299, "Missing parameter 'rename_to'");
    	return;
    } else if (oldName.equals(newName)) {
    	checkSendError(req, resp, 221, "Renaming " + oldName + " to itself");
    	return;
    }
    
    debug(req, "Renaming project '" + oldName + "'->" + newName);

    // Get all files included into given project
    try {
      ProjectUtils.renameProject(getPrjRootDir(req), oldName, newName);
    } catch (WsSrvException e) {
      checkSendError(req, resp, e);
    }
    
    debug(req, "Project '" + oldName + "'->'" + 
    								newName + "' successfully renamed");
  }
  
  /**
   * Get project or search for projects
   */
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
    
    String name = req.getParameter("name");
    debug(req, "Deleting project '" + name + "'");
    
    // Get all files included into given project
    try {
      ProjectUtils.delProject(getPrjRootDir(req), name);
    } catch (WsSrvException e) {
        checkSendError(req, resp, e);
    }
    
    debug(req, "Project '" + name + "' successfully deleted");
  }
  
  @Override
  protected String[] getMandatoryParameters() {
    return new String[] {"name"};
  }
}
