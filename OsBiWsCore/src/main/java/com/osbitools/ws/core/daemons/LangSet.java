/*
 * Copyright 2014-2016 IvaLab Inc. and other contributors
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-07
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.core.daemons;

import java.util.HashSet;

import com.osbitools.ws.shared.binding.ds.*;

public class LangSet {
	// Hash set with indexed language columns
	HashSet<String> _lset;
	
	public LangSet(DataSetExt dsExt) {
		LangMap lmap =  dsExt.getLangMap();
		if (lmap != null) {
			_lset = new HashSet<String>();
			for (LangColumn column: lmap.getColumn())
				_lset.add(column.getName());
		}
	}
	
	public boolean isLangColumn(String column) {
		return (_lset != null && _lset.contains(column));
	}
}
