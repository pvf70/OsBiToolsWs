/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-04-22
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 */

package com.osbitools.ws.shared.prj.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.eclipse.jgit.api.Git;

import com.osbitools.ws.shared.prj.utils.EntityUtils;
import com.osbitools.ws.shared.prj.utils.IEntityUtils;
import com.osbitools.ws.shared.web.GenericWsSrvServlet;

/**
 * 
 * Abstract servlet with generic Project Management Methods
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */

public abstract class GenericPrjMgrWsSrvServlet extends GenericWsSrvServlet {
  // Default serial version uid
  private static final long serialVersionUID = 1L;
  
  Git getGit(HttpServletRequest req) {
    return (Git) req.getSession().getServletContext().getAttribute("git");
  }
  
  public EntityUtils getEntityUtils(HttpServletRequest req) {
    return (EntityUtils) req.getSession().
                    getServletContext().getAttribute("entity_utils");
  }
  
  public String getPrjRootDir(HttpServletRequest req) {
    return getConfigDir(req) + File.separator + 
                    getEntityUtils(req).getPrjRootDirName();
  }
  
  public String getBaseExt(HttpServletRequest req) {
    return ((IEntityUtils) req.getSession().
                    getServletContext().getAttribute("entity_utils")).getExt();
  }
  
  public DiskFileItemFactory getDiskFileItemFactory(HttpServletRequest req) {
    return (DiskFileItemFactory) req.getSession().
                            getServletContext().getAttribute("dfi");
  }
}
