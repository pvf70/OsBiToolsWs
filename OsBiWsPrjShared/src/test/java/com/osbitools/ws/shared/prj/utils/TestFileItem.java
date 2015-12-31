/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-07-03
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.prj.utils;

/**
 * Class to keep single Test File
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class TestFileItem {

  private byte[] _ftext;
  private String _sresp;
  
  public TestFileItem(byte[] ftext, String sresp) {
    _ftext = ftext;
    _sresp = sresp;
  }

  public byte[] getFileText() {
    return _ftext;
  }

  public void setFileText(byte[] text) {
    _ftext = text;
  }

  public String getStrResponse() {
    return _sresp;
  }

  public void setStrResponse(String response) {
    _sresp = response;
  }

}
