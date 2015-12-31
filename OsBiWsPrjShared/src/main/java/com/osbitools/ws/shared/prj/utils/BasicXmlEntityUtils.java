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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.eclipse.persistence.jaxb.MarshallerProperties;

import com.osbitools.ws.shared.WsSrvException;
import com.osbitools.ws.shared.prj.utils.EntityUtils;

/**
 * 
 * @author Igor Peonte
 *
 * Basic utilites to read/write/get Json for Project Entity file
 * 
 * @param <T> Top XML class
 */
public abstract class BasicXmlEntityUtils<T> extends EntityUtils {

  public abstract String getBindPkgName();

  @Override
  public String create(String base, String name, InputStream in,
      boolean overwrite, boolean minified) throws WsSrvException {
    File f = checkFile(base, name, getExt(), overwrite);

    // 1. Validate XML Entity
    T entity = validateEntity(in);

    // Reset input stream
    try {
      in.reset();
    } catch (IOException e) {
      //-- 223
      throw new WsSrvException(223, e);
    }

    // 2. Save initial xml
    saveFile(f, in);

    return getXmlEntity(entity, name, minified);
  }

  @SuppressWarnings("unchecked")
  @Override
  public String getJson(InputStream in, 
                    String name, boolean minified) throws WsSrvException {
    T entity;
    JAXBContext jc = getJAXBContext();

    try {
      entity = ((JAXBElement<T>) jc.createUnmarshaller().
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
    
    return getXmlEntity(entity, name, minified);
  }

  public T validateEntity(String entity) throws WsSrvException {
    return validateEntity(new ByteArrayInputStream(entity.getBytes()));
  }

  public T validateEntity(InputStream in) throws WsSrvException {
    T res = null;
    JAXBContext jc = getJAXBContext();

    try {
      res = readEntity(jc, in);
    } catch (JAXBException e) {
      //-- 225
      throw new WsSrvException(225, "Invalid xml entity", e);
    }

    return res;
  }

  private JAXBContext getJAXBContext() throws WsSrvException {
    String pkg = getBindPkgName();
    try {
      return JAXBContext.newInstance(pkg);
    } catch (JAXBException e) {
      //-- 226
      throw new WsSrvException(226, "Unable create JAXB instance for \"" +
                                                              pkg + "\"", e);
    }
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
  private T readEntity(JAXBContext jc, InputStream in)
      throws JAXBException {
    return ((JAXBElement<T>) jc.createUnmarshaller().
                                  unmarshal(in)).getValue();
  }
  
  String getXmlEntity(T entity, String name, 
                        boolean minified) throws WsSrvException {
    JAXBContext jc = getJAXBContext();

    Marshaller m = getMarshaller(jc, !minified);
    
    try {
      m.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
      m.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
      m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    } catch (PropertyException e) {
      //-- 208
      throw new WsSrvException(208, "Unable create JSON Marshaller for \"" +
                                                  getBindPkgName() + "\"", e);
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
  
  @Override
  public boolean hasInfoReq(String info) {
    return false;
  }

  @Override
  public String execInfoReq(String info, String base, String fname,
      HashSet<String> extl, String subDir, HashMap<String, String> param,
      boolean minifieds) throws WsSrvException {
    return null;
  }
}
