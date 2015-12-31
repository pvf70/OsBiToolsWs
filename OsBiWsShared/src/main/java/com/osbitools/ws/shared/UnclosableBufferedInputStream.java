/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-08-07
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Wrapper around BufferedInputStream that cannot be closed i.e it can be reused
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public class UnclosableBufferedInputStream extends BufferedInputStream {

  public UnclosableBufferedInputStream(InputStream in) {
    super(in);
    super.mark(Integer.MAX_VALUE);
  }

  @Override
  public void close() throws IOException {
    super.reset();
  }
  
  public void done() throws IOException {
    super.close();
  }
}