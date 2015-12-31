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

package com.osbitools.ws.core.shared;

import java.util.LinkedHashMap;
import com.osbitools.ws.shared.Utils;

/**
 * Trance Event Recorder
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class TraceRecorder {
	// Start DateStamp
	private long _dts;
	
	// List of Trace Events
	private LinkedHashMap<String, Long> _evl;

	// Enabled Flag
	private boolean _yes;
	
	public TraceRecorder() {
		this(System.currentTimeMillis(), false);
	}
	
	public TraceRecorder(long tstart, boolean enabled) {
		_dts = tstart;
		setEnabled(enabled);
	}

	public void record(String event) {
		if (_yes)
			_evl.put(event, System.currentTimeMillis() - _dts);
	}
	
	public String getJsonRecord(boolean minified) {
		if (!_yes || _evl.size() == 0)
			return "";

		String res = "";

		for (String key : _evl.keySet()) {
			res += "," + Utils.getCRTT(minified) + "{"  + 
				Utils.getCRTTT(minified) + "\"key\":" + 
							Utils.getSPACE(minified) + "\"" + key + "\"," +
				Utils.getCRTTT(minified) + "\"duration\":" +
					Utils.getSPACE(minified) + (_evl.get(key) / 1000f) + 
				Utils.getCRTT(minified) + "}";
		}

		return "," + Utils.getCRT(minified) + "\"trace\":" + 
		    Utils.getSPACE(minified) + "[" + res.substring(1) + 
		                                  Utils.getCRT(minified) + "]";
	}

	public boolean isEnabled() {
		return _yes;
	}
	
	public void setEnabled(boolean enabled) {
		_yes = enabled;
		if (_yes && _evl == null)
			_evl = new LinkedHashMap<String, Long>();
	}
	
	public LinkedHashMap<String, Long> getEventsList() {
		return _evl;
	}
}