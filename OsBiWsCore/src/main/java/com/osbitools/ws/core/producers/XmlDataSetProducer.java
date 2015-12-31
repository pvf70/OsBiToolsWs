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

import java.util.ArrayList;

import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.binding.ds.XmlData;
import com.osbitools.ws.core.proc.AbstractDataSetProc;
import com.osbitools.ws.core.proc.XmlDataSetProc;
import com.osbitools.ws.core.producers.ds.AbstractDataSet;
import com.osbitools.ws.core.shared.TraceRecorder;

/**
 * Xml dataSet Producer
 * 
 * @author "Igor Peonte <igor.144@gmail.com>"
 *
 */
public class XmlDataSetProducer extends AbstractDataSetProducer<XmlData> {

  public XmlDataSetProducer(AbstractDataSetProc dsProc) {
    super(dsProc);
  }

  @Override
  public AbstractDataSet read(String name, String lang, TraceRecorder trace,
      ArrayList<String> warn) throws WsSrvException {
    // TODO Add implementation
    return null;
  }

  @Override
  XmlData getDataSetSpec() {
    return ((XmlDataSetProc) getDataSetProc()).getDataSetSpec();
  }

}
