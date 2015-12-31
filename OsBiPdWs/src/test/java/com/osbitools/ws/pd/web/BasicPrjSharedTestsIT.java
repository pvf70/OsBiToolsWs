/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-04-18
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.pd.web;

import com.osbitools.ws.shared.prj.web.*;
import com.osbitools.ws.shared.web.BasicWebIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({BasicWebIT.class, BasicEntityUtilsWebIT.class, 
  BasicProjSelWebIT.class, BasicEntityWebIT.class, BasicGitWebIT.class, 
    BasicProjWebIT.class, BasicExFileWebIT.class, BasicConfigWebIT.class,
      BasicLangSetWebIT.class})
public class BasicPrjSharedTestsIT {}
