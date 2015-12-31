/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-19
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import com.osbitools.ws.shared.WsSrvException;

/**
 * Class with static Generic File utilities
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 * 
 */
public class GenericUtils {

	protected static File validateEntry(String base, String name, int lvl,
	                                     Boolean fexists) throws WsSrvException {
		return validateEntry(base, name, lvl, fexists, "", null);
	}
	
  protected static File validateEntry(String base, String name, int lvl) 
                                                        throws WsSrvException {
      return validateEntry(base, name, lvl, null, "", null);
}

	protected static File validateEntry(String base, String name, int lvl,
	                      Boolean fexists, String suffix) throws WsSrvException {
		return validateEntry(base, name, lvl, fexists, suffix, null);
	}
	
	protected static File validateEntry(String base, String name, int lvl,
	    Boolean fexists, String suffix, String sdir) throws WsSrvException {
		
		File f = new File(validateEntryName(base, name, lvl, sdir) + suffix);

		// Check if directory exists or not
		if (fexists != null && f.exists() != fexists) {
		  //-- 31
		  //-- 32
			throw (fexists) ? new WsSrvException(31, "Entry \\\"" +
			    f.getAbsolutePath() + "\\\" not found") : new WsSrvException(32,
			    			"Entry \\\"" + f.getAbsolutePath() + "\\\" already exists");
		}

		return f;
	}
	
	public static String validateEntryName(String base, String name) 
	                                                throws WsSrvException {
	  return validateEntryName(base, name, Constants.MAX_PROJ_LVL + 1, null);
	}
	
	protected static String validateEntryName(String base, String name, 
                                        int lvl) throws WsSrvException {
	  return validateEntryName(base, name, lvl, null);
	}

	protected static String validateEntryName(String base, String name, 
	                             int lvl, String sdir) throws WsSrvException {
	    
    // 1. Check if name correct
    String[] path = name.split("\\.");
    if (path.length > lvl)
      //-- 30
      throw new WsSrvException(30, "Only " + lvl +
          " level structure supported but found " + path.length +
                                      " in name \\\"" + name + "\\\"");

    String res = base;
    if (path.length > 0) {
      for (int i = 0; i < path.length; i++) {
        // Add subdirectory entry for external files
        if (i == path.length - 1 && sdir != null)
          res += File.separator + sdir;
        
        res += File.separator + checkName(path[i]);
      }
    } else {
      res += checkName(name);
    }
    
    return res;
	}
	 
	private static String checkName(String name) throws WsSrvException {
		if (!Constants.ID_PATTERN.matcher(name).matches())
		  //-- 33
			throw new WsSrvException(33, "Invalid name \\\"" + name + "\\\"");

		return name;
	}
	
	public static String getFileExt(String name) throws WsSrvException {
	  int len = name.length();
    
    if (len < 3)
      //-- 41
      throw new WsSrvException(41,
                        "Invalid name \\\"" + name + "\\\"");
    
    int pos = name.lastIndexOf(".");
    if (pos == -1)
      //-- 42
      throw new WsSrvException(42, "Extension not found in name \\\"" + 
                                                            name + "\\\"");
    
    return name.substring(pos + 1);
	}
	
	public static String checkFileExt(String name, String ext) 
                                           throws WsSrvException {
    int len = name.length();
    String fext = getFileExt(name);
    
    if (!fext.equals(ext))
      //-- 43
      throw new WsSrvException(43, "Expected " + ext + 
              " extension but found \\\"" + fext + "\\\"");
    
    return name.substring(0, len - ext.length() - 1);
	}

	/**
	 * Check if file extension supported
	 * 
	 * @param name File name
	 * @param extl List of supported extensions
	 * @return File name without extension
	 * @throws WsSrvException
	 */
	public static String checkFileExt(String name, String ext, 
	                      HashSet<String> extl) throws WsSrvException {
	  int len = name.length();
  	
  	if (!extl.contains(ext)) {
  	  String sl = "";
  	  for (String s: extl)
  	    sl += "," + s;
  	  
  	  //-- 43
  		throw new WsSrvException(43, "Expected " + 
  		    (extl.size() > 1 ? "one of " + sl.substring(1) : "") + 
  						      " extension" + (extl.size() > 1 ? "s" : "") + 
  															" but found \\\"" + ext + "\\\"");
  	}
  	
  	return name.substring(0, len - ext.length() - 1);
  }
	
	public static File checkFile(String base, String name, 
														String ext) throws WsSrvException {
		return checkFile(base, name, ext, null, true);
	}
	
	public static File checkFile(String base, String name, 
							String ext, Boolean overwrite) throws WsSrvException {
		return checkFile(base, name, ext, null, overwrite);
	}
	
	/**
   * Convert web path to real path and check if file exists or not
   * 
   * @param base Base configuration directory
   * @param name Web Path Name
   * @param extl List of allowed extensions
   * @param sdir Subdirectory (if any) where look for file
   * @param overwrite True Expect overwrite file so it should exist
   * @return
   * @throws WsSrvException
   */
  public static File checkFile(String base, String name, 
        String ext, String sdir, Boolean overwrite) throws WsSrvException {
    String dsn = checkFileExt(name, ext);
    
    return checkFileEx(base, dsn, ext, sdir, overwrite);
  }
  
	/**
	 * Convert web path to real path and check if file exists or not
	 * 
	 * @param base Base configuration directory
	 * @param name Web Path Name
	 * @param extl List of allowed extensions
	 * @param sdir Subdirectory (if any) where look for file
	 * @param overwrite True Expect overwrite file so it should exist
	 * @return
	 * @throws WsSrvException
	 */
	public static File checkFile(String base, String name, HashSet<String> extl, 
	                      String sdir, Boolean overwrite) throws WsSrvException {
	  String ext = getFileExt(name);
		String dsn = checkFileExt(name, ext, extl);
		
		return checkFileEx(base, dsn, ext, sdir, overwrite);
	}
	
	public static File checkFileEx(String base, String dsn, String ext,
	                String sdir, Boolean overwrite) throws WsSrvException {
		// 0. Get full path
		File f = validateEntry(base, dsn, 
					Constants.MAX_PROJ_LVL + 1, overwrite, "." + ext, sdir);
		
		// 1. Check that f is a file
		if ((overwrite == null || overwrite) && f.exists() && !f.isFile())
		  //-- 44
		  throw new WsSrvException(44, "Entry \\\"" + 
									f.getAbsolutePath() + "\\\" is not a file");
		
		return f;
  }
	
	public static void saveFile(File f, InputStream in) throws WsSrvException {
	  OutputStream out = null;
    try {
      try {
        out = new FileOutputStream(f);
      } catch (FileNotFoundException e) {
        //-- 29
        throw new WsSrvException(29, "Error creating file \"" + 
            f.getAbsolutePath() + "\"", e);
      }
      
      try {
        copy(in, out);
      } catch (IOException e) {
        //-- 34
        throw new WsSrvException(34, "Error creating file \"" + 
                                              f.getAbsolutePath() + "\"", e);
      }
    } finally {
      if (out != null)
        try {
          out.close();
        } catch (IOException e) {
          // Ignore
        }
    }
	}
	
	public static void saveFile(File f, String text) throws WsSrvException {
		Writer out = null;
    try {
      try {
        out = new OutputStreamWriter(
            new FileOutputStream(f), "UTF-8");
      } catch (UnsupportedEncodingException e) {
        //-- 35
        throw new WsSrvException(35, "Error creating file \"" + 
            f.getAbsolutePath() + "\" with UTF-8 encoding", e);
      } catch (FileNotFoundException e) {
        //-- 36
        throw new WsSrvException(36, "Error creating file \"" + 
                                        f.getAbsolutePath() + "\"", e);
      }
      
      try {
        out.write(text);
      } catch (IOException e) {
        //-- 37
        throw new WsSrvException(37, "Error creating file \"" + 
            f.getAbsolutePath() + "\"", e);
      }
    } finally {
      if (out != null)
        try {
          out.close();
        } catch (IOException e) {
          // Ignore
        }
    }
	}
	
	/**
	 * Read file into byte array
	 * 
	 * @param f File handle
	 * @return byte array
	 * 
	 * @throws WsSrvException
	 */
	public static byte[] readFile(File f) throws WsSrvException {	
	  try {
	    return Files.readAllBytes(Paths.get(f.toURI()));
	  } catch (IOException e) {
	    //-- 38
	    throw new WsSrvException(38, e);
    }
	}
	
	/**
	 * Read input stream into byte array
	 * 
	 * @param in Input Stream
	 * @return byte array
	 * 
	 * @throws IOException
	 */
  public static byte[] readInputStream(InputStream in) throws IOException { 
    ByteArrayOutputStream out = new ByteArrayOutputStream();   
  
    byte[] bytes = new byte[8192];
    int length;
    
    while ((length = in.read(bytes)) > 0)
      out.write(bytes, 0, length);

    in.close();
    out.close();

    return out.toByteArray();
  }
    
	/**
	 * Get list of file inside resource subdirectory
	 * 
	 * @param base Top root directory
	 * @param fileName Name of file including directory/project path
	 * @param resDirName Name of resource subdirectory
	 * @param extList List of file extensions to lookup inside resource subdirectory
	 * @return List of files
	 * @throws WsSrvException
	 */
	public static String getResDirExtList(String base, String fileName, 
	           String resDirName,  FilenameFilter filter) throws WsSrvException {
	  String res = "";
	  
	  // Remove mask and get directory name
	  String dname = fileName.substring(0, fileName.length() - 2);
	  
	  if (dname.equals(""))
	    //-- 39
	    throw new WsSrvException(39, "Invalid entry '" + fileName + "'");
	  
	  // 0. Get full path
    File dir = checkDir(base, dname);
    
    File extd = new File(dir.getAbsolutePath() + File.separator + resDirName);
    
    if (extd.exists()) {
      String[] flist = extd.list(filter);
      Arrays.sort(flist);
      for (String f: flist)
        res += "," + f;
    }
    
    return res.equals("") ? "" : res.substring(1);
	}
	
	/**
	 * Get directory of file
	 * @param base Base path
	 * @param name File Name
	 * @return Directory Name
	 * @throws WsSrvException 
	 */
	public static File getFileDir(String base, String name) 
	                                          throws WsSrvException {
	  // Strip extension
	  String fname = name.substring(0, name.length() - 4);
	  
	  int pos = fname.lastIndexOf(".");
	  if (pos < 0)
	    return new File(base);
	  else
	    return checkDir(base, fname.substring(0, pos));
	}
	
	public static File checkDir(String base, String name) throws WsSrvException {
    // 0. Get full path for old directory
    File dir = validateEntry(base, name, Constants.MAX_PROJ_LVL, true);

    // 1. Check if Directory exists
    if (!dir.isDirectory())
      //-- 40
      throw new WsSrvException(40, "Entry \\\"" + dir.getAbsolutePath() +
          "\\\" is not a directory");
    
    return dir;
  }
	
	public static void copy(InputStream input, OutputStream output) 
	                                                    throws IOException {
	  byte[] buffer = new byte[Constants.DEFAULT_BUFFER_SIZE];
	  
	  int n = 0;
	  while (Constants.EOF != (n = input.read(buffer)))
	    output.write(buffer, 0, n);
	}
	
	public static void copy(Reader input, Writer output) throws IOException {
	  char[] buffer = new char[Constants.DEFAULT_BUFFER_SIZE];
    
    int n = 0;
    while (Constants.EOF != (n = input.read(buffer)))
      output.write(buffer, 0, n);
	}
	
	public static void copyToFile(InputStream in, String fname) 
      throws IOException {
	  FileOutputStream out = new FileOutputStream(fname);
	  copyToFile(in, out);
	}
	
  public static void copyToFile(InputStream in, File f) 
      throws IOException {
    FileOutputStream out = new FileOutputStream(f);
    copyToFile(in, out);
  }
  
  public static void copyToFile(InputStream in, FileOutputStream out) 
                                                        throws IOException {
    copy(in, out);
    out.close();
  }
  
	public static void copyToFile(String s, String fname) throws IOException {
    FileOutputStream out = new FileOutputStream(fname);
    copyToFile(s, out);
  }
	
  public static void copyToFile(String s, File f) throws IOException {
    FileOutputStream out = new FileOutputStream(f);
    copyToFile(s, out);
  }
  
  public static void copyToFile(String s, FileOutputStream out) 
                                                        throws IOException {
    out.write(s.getBytes());
    out.close();
  }
  
	public static String getInfo(String base, String name, HashSet<String> extl, 
	       String subDir, HashMap<String, String> params, 
	                           boolean minified) throws WsSrvException {
    File f = checkFile(base, name, extl, subDir, true);

    return getInfo(f, minified);
	}
	
	public static String getInfo(String base, String name, String ext, 
         HashMap<String, String> params, 
                           boolean minified) throws WsSrvException {
    File f = checkFile(base, name, ext, true);
  
    return getInfo(f, minified);
	}
	  
  public static String getInfo(File f, boolean minified) 
	                                            throws WsSrvException {
    return "{" + Utils.getCRT(minified) + "\"file_info\":" + 
                                    Utils.getCRTT(minified) + "{" +
        "\"size\":" + Utils.getSPACE(minified) + 
                        f.length() + "," + Utils.getCRTTT(minified) +
        "\"read\":" + Utils.getSPACE(minified) + 
                        f.canRead() + "," + Utils.getCRTTT(minified) +
        "\"write\":" + Utils.getSPACE(minified) + 
                        f.canWrite() + "," + Utils.getCRTTT(minified) +
        "\"last_modified\":" + Utils.getSPACE(minified) + f.lastModified() +
        Utils.getCRT(minified) + "}" + Utils.getCR(minified) + "}";
  }

	/**
   * Copy file from source to destination
   * 
   * @param from File Name to copy from
   * @param to File Name to copy into
   */
  public static boolean copyFile(String from, String to) throws IOException {
    FileInputStream in = null;
    FileOutputStream out = null;
    
    // Add bad file to sp directory
    try {
      in = new FileInputStream(from);
      FileChannel channel = in.getChannel();
      
      out = new FileOutputStream(to + ".tmp");

      out.getChannel().transferFrom(channel, 0, channel.size());
      channel.close();
      
      out.close();
      out = null;
      
      // Now rename temp file to avoid collisions
      return new File(to + ".tmp").renameTo(new File(to));
    } finally {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
    }
  }
  
  /**
   * Check if directory exists and create if not
   * 
   * @param dir directory name
   * @return True if directory existed and False if not and was created
   * @throws IOException
   */
  public static boolean checkDirectory(String dir)
            throws IOException {
      File d = new File(dir);
      Boolean res = d.exists();
      if (!(res || d.mkdirs())) {
        res = null;
          throw new IOException("Failed create directory '" + 
                                    d.getAbsolutePath() + "");
      }
      return res;
  }
  
  public static void renameFile(String base, String oldName, 
                String newName, String ext) throws WsSrvException {
    renameFile(base, oldName, newName, ext, null);
  }
  
  public static void renameFile(String base, String oldName, 
      String newName, HashSet<String> extl, String sdir) throws WsSrvException {
    // Check old file
    File fOld = checkFile(base, oldName, extl, sdir, true);
    renameFileCheck(oldName, newName, fOld);
    renameFile(base, fOld, checkFile(base, newName, extl, sdir, false), sdir);
  }
  
  public static void renameFileCheck(String oldName, 
                            String newName, File of) throws WsSrvException {
    // Quick check
    if (oldName.equals(newName))
      //-- 45
      throw new WsSrvException(45, "Can not rename file \\\"" + 
          of.getAbsolutePath() + "\\\" to itself");
  }
  
  public static void renameFile(String base, File oldName, File newName, 
                                        String sdir) throws WsSrvException {
    if (!oldName.renameTo(newName))
      //-- 46
      throw new WsSrvException(46, "Can not rename " + 
          oldName.getAbsolutePath() + " -> " + newName.getAbsolutePath());
  }
  
  public static void renameFile(String base, String oldName, 
        String newName, String ext, String sdir) throws WsSrvException {
    File fOld = checkFile(base, oldName, ext, sdir, true);
    renameFileCheck(oldName, newName, fOld);
    renameFile(base, fOld, checkFile(base, newName, ext, sdir, false), sdir);
  }
  
  public static void deleteFile(String base, String name, String ext)
                                                  throws WsSrvException {
    deleteFile(base, name, ext, null);
  }
  
  public static void deleteFile(String base, String name, 
                 HashSet<String> extl, String sdir) throws WsSrvException {
    // Get full path
    File f = checkFile(base, name, extl, sdir, true);
    
    // 1. Delete file
    deleteFile(f);
  }
  
  public static void deleteFile(String base, String name, 
                      String ext, String sdir) throws WsSrvException {
    // 0. Get full path
    File f = checkFile(base, name, ext, sdir, true);

    // 1. Delete file
    deleteFile(f);
  }
  
  public static void deleteFile(File f) throws WsSrvException {
    // 1. Delete directory
    if (!f.delete())
        throw new WsSrvException(47, "Unable deleting file \\\"" +
                                          f.getAbsolutePath() + "\\\"");
  }
  
  /**
   * Recursively delete directory content and directory itself
   * 
   * @param f File
   * @throws IOException 
   */
  public static void delDirRecurse(File f) throws IOException {
    delDirRecurse(f, true);
  }
  
  /**
   * Recursively delete directory
   * 
   * @param f File
   * @param self Delete itself
   * @throws IOException 
   */
  public static void delDirRecurse(File f, boolean self) throws IOException {
    File[] dlist = f.listFiles();
    if (dlist != null) {
      for (File d: dlist)
        if (d.isDirectory())
          delDirRecurse(d, true);
        else if (!d.delete())
          throw new IOException("Unable delete '" + d.getAbsolutePath() + "'");
    }
    
    if (self && !f.delete())
      throw new IOException("Unable delete '" + f.getAbsolutePath() + "'");
  }
}
