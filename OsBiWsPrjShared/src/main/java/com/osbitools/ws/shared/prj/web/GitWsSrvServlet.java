/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-12-12
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

import org.eclipse.jgit.revwalk.RevCommit;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.PrjMgrConstants;
import com.osbitools.ws.shared.prj.utils.GitUtils;

/**
 * 
 * Git Manager. Implements all CRUD spec
 * 
 * doGet - Read Previous revision
 * doPost - Commit revision
 * doPut - Push or Pull to/from remote repository
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
@WebServlet("/rest/git")
public class GitWsSrvServlet extends GenericPrjMgrWsSrvServlet {
	//Default serial version uid
	private static final long serialVersionUID = 1L;
	 
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
	 
	 String comment = req.getParameter("comment") != null ? 
	                                 req.getParameter("comment") : "";
	 try {
    resp.getWriter().print(GitUtils.commitFile(getGit(req),
        getPrjRootDir(req), req.getParameter(PrjMgrConstants.REQ_NAME_PARAM), 
                                  comment, getLoginUser(), getBaseExt(req)));
    } catch (WsSrvException e) {
      checkSendError(req, resp, e);
    }
	}
	
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
	  
	  // Check if specific revision required
	  String rev = req.getParameter("rev");
	  
	  if (Utils.isEmpty(rev)) {
      try {
        RevCommit[] rlist = GitUtils.getLog(getGit(req), getPrjRootDir(req), 
                    req.getParameter(PrjMgrConstants.REQ_NAME_PARAM), getBaseExt(req));
        boolean minified = isMinfied(req);
        
        String res = "";
        for (RevCommit rc: rlist) {
          res += "," + Utils.getCRT(minified) + "{" + 
          	Utils.getCRTT(minified) + "\"id\":" + 
          			Utils.getSPACE(minified) + "\"" + rc.getName() + "\"" +
        		"," + Utils.getCRTT(minified) + "\"comment\":" + 
        				Utils.getSPACE(minified) + "\"" +
            			rc.getFullMessage().replace("\"", "\\\"") + "\"" +
          	"," + Utils.getCRTT(minified) +"\"commit_time\":" + 
          			Utils.getSPACE(minified) + rc.getCommitTime() +
          	"," + Utils.getCRTT(minified) +"\"committer\":" + 
          			Utils.getSPACE(minified) + "\"" + 
          				rc.getCommitterIdent().getName() + "\"" +
            Utils.getCRT(minified) + "}";
        }
        
        printJson(resp, "[" + (res.equals("") ? "" : res.substring(1)) + 
        				Utils.getCR(minified) + "]");
      } catch (WsSrvException e) {
        checkSendError(req, resp, e);
      }
	  } else {
	    try {
	      boolean minified = isMinfied(req);
	      printJson(resp, "{" + Utils.getCRT(minified) + 
	          "\"entity\":" + Utils.getSPACE(minified) +
	              GitUtils.getRevEntityJson(getGit(req), getPrjRootDir(req), 
	                  req.getParameter(PrjMgrConstants.REQ_NAME_PARAM), 
	                      rev, getEntityUtils(req), isMinfied(req)) + 
	                                              Utils.getCR(minified) + "}");
      } catch (WsSrvException e) {
        checkSendError(req, resp, e);
      }
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
		
		// Read action
		String action = req.getParameter(PrjMgrConstants.REQ_NAME_PARAM);
		
	  try {
	  	if (action.equals("push"))
	  	  resp.getWriter().print(GitUtils.pushToRemote(getGit(req),
              	  	      getContextAttribute(req, "git_remote_name"), 
              	  	          getContextAttribute(req, "git_remote_url")));
	  	else if (action.equals("pull"))
	  	  resp.getWriter().print(GitUtils.pullFromRemote(getGit(req),
	  	            getContextAttribute(req, "git_remote_name"), 
	  	                    getContextAttribute(req, "git_remote_url")));
	  	else
	  		throw new WsSrvException(248, "Invalid operation [" + action + "]");
    } catch (WsSrvException e) {
    	checkSendError(req, resp, e);
    }
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
