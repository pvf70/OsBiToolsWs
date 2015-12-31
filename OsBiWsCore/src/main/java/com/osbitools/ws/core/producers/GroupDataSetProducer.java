/*
 * Copyright 2014-2016 IvaLab Inc. and other contributors
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-03-02
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.core.producers;

import java.util.ArrayList;

import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.core.daemons.DsExtResource;
import com.osbitools.ws.core.proc.AbstractDataSetProc;
import com.osbitools.ws.core.proc.GroupDataSetProc;
import com.osbitools.ws.core.producers.ds.AbstractDataSet;
import com.osbitools.ws.core.shared.TraceRecorder;

/**
 * Group DataSet Producer
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class GroupDataSetProducer extends AbstractDataSetProducer<GroupData> {
  
  public GroupDataSetProducer(AbstractDataSetProc dsProc) {
    super(dsProc);
  }

  @Override
  public AbstractDataSet read(String name, String lang, TraceRecorder trace,
      ArrayList<String> warn) throws WsSrvException {
    
    AbstractDataSet d = makeNewDataSet(lang);
    d.startData();
    
    // Read all included resources
    for (DsExtResource dser: ((GroupDataSetProc) 
                                getDataSetProc()).getDsExtList())
      d.append(dser.getDataSetProc().
                            readDataSet(name, lang, trace, warn));
    
    d.endData();
    return d;
  }

  @Override
  GroupData getDataSetSpec() {
    return ((GroupDataSetProc) getDataSetProc()).getDataSetSpec();
  }

}
