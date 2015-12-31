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

package com.osbitools.ws.shared.prj.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.eclipse.persistence.jaxb.MarshallerProperties;

import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.WsSrvException;

/**
 * 
 * @author Igor Peonte
 *
 * Basic utilites to read/write/get Json for Xml file
 * 
 * @param <T> Top XML class
 */
public abstract class BasicXmlFileUtils<T> extends GenericUtils {

  public abstract String getBindPkgName();

  // Jaxb context singleton
  private JAXBContext _jc = null;
  
  public String create(File dest, InputStream in,
      boolean overwrite, boolean minified) throws WsSrvException {
    // 1. Validate XML File
    T obj = validateFile(in);

    // Reset input stream
    try {
      in.reset();
    } catch (IOException e) {
      //-- 223
      throw new WsSrvException(223, e);
    }

    // 2. Save initial xml
    saveFile(dest, in);

    return getJson(obj, dest.getAbsolutePath(), minified);
  }

  @SuppressWarnings("unchecked")
  public String getJson(InputStream in, 
                    String name, boolean minified) throws WsSrvException {
    T obj;
    JAXBContext jc = getJAXBContext();

    try {
      obj = ((JAXBElement<T>) jc.createUnmarshaller().
                                              unmarshal(in)).getValue();
    } catch (JAXBException e) {
      String msg = "Invalid xml entity \"" + name + "\"";
      if (e.getMessage() != null)
        //-- 224
        throw new WsSrvException(224, msg, e);
      else
        //-- 224
        throw new WsSrvException(224, msg, e.getCause());
    }
    
    return getJson(obj, name, minified);
  }

  public T validateFile(InputStream in) throws WsSrvException {
    T res = null;
    JAXBContext jc = getJAXBContext();

    try {
      res = readFile(jc, in);
    } catch (JAXBException e) {
      //-- 225
      throw new WsSrvException(225, e.getCause(), "Error parsing xml");
    }

    return res;
  }

  private synchronized JAXBContext getJAXBContext() throws WsSrvException {
    if (_jc == null) {
      String pkg = getBindPkgName();
      try {
        _jc = JAXBContext.newInstance(pkg);
      } catch (JAXBException e) {
        //-- 226
        throw new WsSrvException(226, "Unable create JAXB instance for \"" +
                                                                pkg + "\"", e);
      }
    }
    
    return _jc;
  }

  private Marshaller getMarshaller(JAXBContext jc,
      boolean formattedOutput) throws WsSrvException {
    Marshaller m;

    try {
      m = jc.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formattedOutput);
    } catch (JAXBException e) {
      //-- 227
      throw new WsSrvException(227, "Unable create Marshaller for \"" +
                                                getBindPkgName() + "\"", e);
    }

    return m;
  }

  @SuppressWarnings("unchecked")
  private T readFile(JAXBContext jc, InputStream in)
      throws JAXBException {
    return ((JAXBElement<T>) jc.createUnmarshaller().
                                  unmarshal(in)).getValue();
  }
  
  private String getJson(T entity, String name, 
                        boolean minified) throws WsSrvException {
    JAXBContext jc = getJAXBContext();

    Marshaller m = getMarshaller(jc, !minified);
    
    try {
      m.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
      m.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
      m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    } catch (PropertyException e) {
      //-- 208
      throw new WsSrvException(208, e, "Unable create JSON Marshaller for \"" +
                                                  getBindPkgName() + "\"");
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      m.marshal(entity, out);
    } catch (JAXBException e) {
      //-- 209
      throw new WsSrvException(209, "Error converting \"" +
                                            name + "\" to JSON", e);
    }
    
    return out.toString();
  }
}
