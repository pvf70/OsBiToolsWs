/*
 * Copyright 2014-2016 IvaLab Inc. and contributors below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-11
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.shared.web;

/**
 * Interface for Multi Thread Test Providers
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public interface ITestInfoProvider {
  abstract Class<?> getTestClass();
  abstract long getWaitTime();
}
