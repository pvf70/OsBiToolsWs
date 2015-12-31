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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.prj.PrjMgrConstants;
import com.osbitools.ws.shared.prj.utils.IEntityUtils;
import com.osbitools.ws.shared.prj.utils.LangSetUtils;
import com.osbitools.ws.shared.web.AbstractWsInit;

/**
 * 
 * Servlet listener for OsBiMe Web Service
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public abstract class AbstractWsPrjInit extends AbstractWsInit {
	
	public abstract IEntityUtils getEntityUtils();
	
	@Override
	public void contextInitialized(ServletContextEvent evt) {
		super.contextInitialized(evt);
    ServletContext ctx = evt.getServletContext();

		// First - Load Custom Error List 
    try {
      Class.forName("com.osbitools.ws.shared.prj.CustErrorList");
    } catch (ClassNotFoundException e) {
      // Ignore Error 
    }
    
    // Initialize Entity Utils
    IEntityUtils eut = getEntityUtils();
    ctx.setAttribute("entity_utils", eut);
    
    // Initiate LangSetFileUtils
    ctx.setAttribute("ll_set_utils", new LangSetUtils());
    
		// Check if git repository exists and create one
		// Using ds subdirectory as git root repository 
		File drepo = new File(getConfigDir(ctx) + File.separator +
		                eut.getPrjRootDirName() + File.separator + ".git");
		Git git;
		try {

			if (!drepo.exists()) {
				if (!drepo.mkdirs())
					throw new RuntimeErrorException(new Error(
							"Unable create directory '" +
									drepo.getAbsolutePath() + "'"));
				
				
				try {
          git = createGitRepo(drepo);
        } catch (Exception e) {
          throw new RuntimeErrorException(new Error(
              "Unable create new repo on path: " + drepo.getAbsolutePath() +
                  ". " + e.getMessage()));
        }
				
				getLogger(ctx).info("Created new git repository '" +
				                                drepo.getAbsolutePath() + "'");
			} else if (!drepo.isDirectory()) {
				throw new RuntimeErrorException(new Error(
						drepo.getAbsolutePath()
								+ " is regular file and not a directory"));
			} else {
				git = Git.open(drepo);
				getLogger(ctx).debug("Open existing repository " + 
				                              drepo.getAbsolutePath());
			}
		} catch (IOException e) {
		  // Something unexpected and needs to be analyzed
			e.printStackTrace();
			
			throw new RuntimeErrorException(new Error(e));
		}
		
		// Save git handler
    ctx.setAttribute("git", git);

		// Check if remote destination set/changed
		StoredConfig config = git.getRepository().getConfig();
		String rname = (String) ctx.getAttribute(PrjMgrConstants.PREMOTE_GIT_NAME);
		String rurl = (String) ctx.getAttribute("git_remote_url");
		
		if (!Utils.isEmpty(rname) && !Utils.isEmpty(rurl)) {
			String url = config.getString("remote", rname, "url");
			if (!rurl.equals(url)) {
				config.setString("remote", rname, "url", rurl);
				try {
	        config.save();
        } catch (IOException e) {
          getLogger(ctx).error("Error saving git remote url. " + 
                                                      e.getMessage());
        }
			}
		}
		
		// Temp directory for files upload
		String tname = System.getProperty("java.io.tmpdir");
		getLogger(ctx).info("Using temporarily directory '" + 
		                                      tname + "' for file uploads");
    File tdir = new File(tname);
    
    if (!tdir.exists())
      throw new RuntimeErrorException(new Error(
          "Temporarily directory for file upload '" + 
                                tname + "' is not found"));
    if (!tdir.isDirectory())
      throw new RuntimeErrorException(new Error(
          "Temporarily directory for file upload '" + 
                                tname + "' is not a directory"));

    DiskFileItemFactory dfi = new DiskFileItemFactory();
    dfi.setSizeThreshold(((Integer) ctx.getAttribute(
                  PrjMgrConstants.SMAX_FILE_UPLOAD_SIZE_NAME)) * 1024 * 1024);
    dfi.setRepository(tdir);
    evt.getServletContext().setAttribute("dfi", dfi);
    
    // Save entity utils in context
    evt.getServletContext().setAttribute("entity_utils", getEntityUtils());
	}
	
	/**
	 * Get list of system components (optional) that available for Front End use
	 * 
	 * @return list of system components
	 */
  public String getComponentList() {
    return null;
  }
	
  @Override
  protected List<String> getRestrictedParamList() {
    List<String> list = super.getRestrictedParamList();
    list.add(PrjMgrConstants.SMAX_FILE_UPLOAD_SIZE_NAME);
    
    return list;
  }
  
	@Override
	public void processConfig(ServletContext ctx, Properties props, 
	                      HashMap<String, String> rmap, boolean isNew) {
	  super.processConfig(ctx, props, rmap, isNew);
	  
	  if (isNew) {
	  	props.put(PrjMgrConstants.PREMOTE_GIT_NAME,
	  	                  PrjMgrConstants.DEFAULT_REMOTE_NAME);
	  	
	  	// Restricted value
	  	props.put(PrjMgrConstants.SMAX_FILE_UPLOAD_SIZE_NAME, 
	  	    rmap.get(PrjMgrConstants.SMAX_FILE_UPLOAD_SIZE_NAME) != null ? 
	  	        rmap.get(PrjMgrConstants.SMAX_FILE_UPLOAD_SIZE_NAME) : 
                              PrjMgrConstants.DEFAULT_MAX_FILE_UPLOAD_SIZE);
	  } 
	  
	  ctx.setAttribute(PrjMgrConstants.PREMOTE_GIT_NAME, 
	      props.getProperty(PrjMgrConstants.PREMOTE_GIT_NAME,
	                          PrjMgrConstants.DEFAULT_REMOTE_NAME));
	  ctx.setAttribute("git_remote_url", 
	                props.getProperty("git_remote_url", null));
	  
	  ctx.setAttribute(PrjMgrConstants.SMAX_FILE_UPLOAD_SIZE_NAME, 
        rmap.get(PrjMgrConstants.SMAX_FILE_UPLOAD_SIZE_NAME) != null ? 
            rmap.get(PrjMgrConstants.SMAX_FILE_UPLOAD_SIZE_NAME) : 
                Integer.parseInt(props.getProperty(
                    PrjMgrConstants.SMAX_FILE_UPLOAD_SIZE_NAME,
                                PrjMgrConstants.DEFAULT_MAX_FILE_UPLOAD_SIZE)));
	  
	  // Read system component list
	  String clist = getComponentList();
	  if (clist != null)
	    // Escape all double quotes so comp_list can be evaluated separate
	    addSysParam(ctx, "comp_list", clist.replace("\"", "\\\""));
	}
	
	public static Git createGitRepo(File dir) throws Exception {
    Repository repo = FileRepositoryBuilder.create(dir);
    repo.create(false);

    Git git = new Git(repo);
    
    // Commit first revision
    git.commit().setMessage("Repository created").call();
    
    return git;
  }

}
