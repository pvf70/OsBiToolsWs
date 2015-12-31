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

package com.osbitools.ws.core.producers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.core.proc.CsvDataSetProc;
import com.osbitools.ws.core.producers.ds.AbstractDataSet;
import com.osbitools.ws.core.shared.TraceRecorder;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Csv DataSet Producer
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class CsvDataSetProducer extends AbstractDataSetProducer<CsvData> {

	// Some defineds columns
	HashMap<String, String> _columns = new HashMap<String, String>();

	public CsvDataSetProducer(CsvDataSetProc dsProc) {
		super(dsProc);

		// Index columns
		CsvData cdata = dsProc.getDataSetSpec();
		Columns columns = cdata.getColumns();
		if (columns != null)
			for (ColumnHeader column : columns.getColumn())
				_columns.put(column.getName().toUpperCase(), column.getJavaType());
	}

	public void addColumn(AbstractDataSet d, String name) 
	                                          throws WsSrvException {
	// By default column type is String
    // Check if column type defined
	  String jtype = _columns.get(name);
    if (jtype == null)
      d.addColumn(name);
    else
      d.addColumn(name, jtype);
	}
	
	@Override
	public AbstractDataSet read(String name, String lang, 
	      TraceRecorder trace, ArrayList<String> warn) throws WsSrvException {
		CsvData spec = getDataSetSpec();
		
		// Read project folder
		File dir = GenericUtils.getFileDir(
		                getDataSetProc().getCfg().getDsDir(), name);
		
		String path = dir.getAbsolutePath() + File.separator + 
		            "csv" + File.separator + spec.getFileName();

		AbstractDataSet d = makeNewDataSet(lang);

		CSVReader reader = null;
		FileReader in;
		try {
			in = new FileReader(path);
		} catch (FileNotFoundException e) {
		  //-- 116
			throw new WsSrvException(116, "Unable read csv file '" + path + "'", e);
		}

		try {
			reader = new CSVReader(in, spec.getDelim().charAt(0), 
				    spec.getQuoteChar().charAt(0), spec.getEscapeChar().charAt(0));

			// Poehali
			d.startData();

			// Read columns
			String[] columns = null;

			if (spec.isColFirstRow()) {
  			try {
  				if ((columns = reader.readNext()) != null) {
  					for (String column : columns) {
  					  addColumn(d, column.toUpperCase());
  					}
  			  }
  			} catch (IOException e) {
  			  //-- 117
  				throw new WsSrvException(117, "Unable read csv column line", e);
  			}
			}
			
			// Read data
			int idx = 0;
			String[] line;

			try {
				while ((line = reader.readNext()) != null) {
				  if (idx == 0 && !spec.isColFirstRow()) {
				    // Late column initialization
				    columns = new String[line.length];
				    
				    for (int i = 0; i < line.length; i++) {
				      String cname = "COL" + (i + 1);
				      addColumn(d, cname);
				      columns[i] = cname;
				    }
				  }
				  
					d.startRecord();
					for (int i = 0; i < line.length; i++) {

						Object value = line[i];
						String column = columns[i];
						String jtype = _columns.get(column);
            if (jtype != null)
              value = d.createValue(column, value.toString());

						d.addValue(columns[i], value);
					}

					d.endRecordSuccess();
					idx++;
				}
			} catch (IOException e) {
			  //-- 118
				throw new WsSrvException(118, "Unable read csv record: "
						+ d.getRecordCount() + 1, e);
			}

			d.endData();
		} finally {
			try {
				in.close();
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				// Ignore errors
			}
		}

		return d;
	}

	@Override
	CsvData getDataSetSpec() {
		return ((CsvDataSetProc) getDataSetProc()).getDataSetSpec();
	}

}
