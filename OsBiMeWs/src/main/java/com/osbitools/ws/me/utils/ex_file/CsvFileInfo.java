/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-12-29
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 * 
 */

package com.osbitools.ws.me.utils.ex_file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;

import com.osbitools.ws.shared.*;

/**
 * CSV File processing
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class CsvFileInfo extends ExFileInfo {
	
	@Override  
	public String[][] getColumnList(File f, String name, HashMap<String, 
	                                  String> params) throws WsSrvException {
		CSVReader reader = null;
		FileReader in;
		try {
			in = new FileReader(f);
		} catch (FileNotFoundException e) {
			throw new WsSrvException(253, "Unable find csv file \\\'" + 
																					f.getAbsolutePath() + "\\\'", e);
		}

    String[][] columns;
		
		try {
		  if (params.size() == 0) {
        reader = new CSVReader(in);
		  } else {
		    String delim = params.get("delim");
		    String quote_char = params.get("quote_char");
		    String escape_char = params.get("escape_char");
  			reader = new CSVReader(in, 
  			  Utils.isEmpty(delim) ? CSVReader.DEFAULT_SEPARATOR : delim.charAt(0), 
  			  Utils.isEmpty(quote_char) ? CSVReader.DEFAULT_QUOTE_CHARACTER : 
  			                                                quote_char.charAt(0),
  			  Utils.isEmpty(escape_char) ? CSVReader.DEFAULT_ESCAPE_CHARACTER : 
  			                                                escape_char.charAt(0));
		  }
		  
			// Read columns
			String cfl = params.get("col_first_row");
			Boolean fcol = cfl == null ? true : Boolean.parseBoolean(cfl);
			
			try {
			  // Read first line
			  String[] fline;
        if ((fline = reader.readNext()) == null)
          throw new WsSrvException(117, "", "csv column line is empty");
        
        columns = new String[fline.length][2];
        
		    for (int i = 0; i < fline.length; i++) {
		      String cname = (fcol) ? fline[i] : "COL" + (i + 1);
		      
		      // By default column type is String
		      columns[i] = new String[] {cname, "\"java.lang.String\""};
		    }
			} catch (IOException e) {
				throw new WsSrvException(117, "Unable read csv column line", e);
			}
		} finally {
			try {
	      reader.close();
      } catch (IOException e) {
	      // Ignore
      }
		}

    return columns;
	}
	
}
