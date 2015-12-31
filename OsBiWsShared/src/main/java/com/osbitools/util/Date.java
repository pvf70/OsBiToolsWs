/*
 * Copyright 2014-2016 IvaLab Inc. and other contributors
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-10-12
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Convert ISO date string into date
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public class Date {
  // Pattern to check if date string includes time
  public static Pattern ISO_DATE_TIME = Pattern.compile(".*\\s.*");
  
  // Long format for date parser
  public static DateFormat DFL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  // Short date format (no time)
  public static DateFormat DFS = new SimpleDateFormat("yyyy-MM-dd");
  
  // Flag indicating that time part of date is required
  private final boolean _ft;
  
  // Java date variable
  private java.util.Date _dts;
  
  static {
      DFL.setTimeZone(TimeZone.getTimeZone("UTC"));
      DFS.setTimeZone(TimeZone.getTimeZone("UTC"));
  }
  
  /**
   * Accept date string as input and convert in the date format yyyy-MM-dd 
   *    or yyyy-MM-dd HH:mm:ss
   * 
   * @param sd Date string. Can be in format 
   * 
   * @throws ParseException
   */
  public Date(String sd) throws ParseException {
    // Check if date has time section
    _ft = ISO_DATE_TIME.matcher(sd).matches();
    _dts = (_ft) ? DFL.parse(sd) : DFS.parse(sd);
  }
  
  public long getTime() {
    return _dts.getTime();
  }
  
  @Override
  public String toString() {
    return (_ft) ? DFL.format(_dts) : DFS.format(_dts);
  }
}
