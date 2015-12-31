/*
 * Copyright 2014-2016 IvaLab Inc. and contributors listed below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2015-10-26
 * 
 * Contributors:
 * 
 * Igor Peonte <igor.144@gmail.com>
 *
 */

package com.osbitools.ws.shared.auth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.LogoutResponse;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.SessionIndex;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.StatusMessage;
import org.opensaml.saml2.core.impl.AuthnRequestMarshaller;
import org.opensaml.saml2.core.impl.LogoutRequestMarshaller;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml2.metadata.impl.EntityDescriptorMarshaller;
import org.opensaml.saml2.metadata.provider.DOMMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.security.MetadataCredentialResolver;
import org.opensaml.security.MetadataCredentialResolverFactory;
import org.opensaml.security.MetadataCriteria;
import org.opensaml.security.SAMLSignatureProfileValidator;
import org.opensaml.ws.soap.client.BasicSOAPMessageContext;
import org.opensaml.ws.soap.client.http.HttpClientBuilder;
import org.opensaml.ws.soap.client.http.HttpSOAPClient;
import org.opensaml.ws.soap.common.SOAPException;
import org.opensaml.ws.soap.soap11.Body;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.security.Criteria;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.credential.KeyStoreCredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.security.x509.X509KeyInfoGeneratorFactory;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureConstants;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.signature.impl.SignatureBuilder;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.security.SecurityException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.osbitools.ws.shared.Constants;
import com.osbitools.ws.shared.Utils;
import com.osbitools.ws.shared.WsSrvException;

/**
 * Implementation for SAML Security Provider
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public class SamlSecurityProvider extends SessionSecurityProvider {

  // Pool with parsers
  private BasicParserPool _pmgr;
  
  // Generic builder factory
  private XMLObjectBuilderFactory _bf;
  
  // IDP Id
  private String _idp;
  
  // IDP Signing credentials
  private X509Credential _cred;
  
  // POST Redirect URL for SSO
  private String _login;
  
  // Single Logout URL
  private String _logout;
    
  // SP credentials that build out of local keystore
  private X509Credential _scred;
    
  // Private key
  PrivateKey _key;
  
  // Service provider name. Taking parameter saml.<context_name>.sp_name from 
  // configuration file or taking default servlet context path
  private String _sname = null;
  
  //Service location. Taking parameter saml.<context_name>sp_loc from 
  // configuration file or using default 
  //              protocol://host_name:host_port/servlet_context_path
  private String _sloc = null;
  
  // List of issued SAML AuthnRequest for further validation and redirection
  private static final ConcurrentHashMap<String, SamlAbstractRequest> _rmap = 
                           new ConcurrentHashMap<String, SamlAbstractRequest>();
  
  // Cross reference table between SAML sessions and session objects
  private static final ConcurrentHashMap<String, Session> _smap = 
                                new ConcurrentHashMap<String, Session>();
  
  // Pattern to extract SAMLResponse our of html form
  private static final Pattern SAML_RESP = Pattern.compile(
      ".*<input type=\"hidden\" name=\"SAMLResponse\" value=\"(.*?)\" />.*", 
                                     Pattern.DOTALL);
  
  // Pattern to find JavaScript encoded Hex codes
  private static final Pattern HP = Pattern.compile("&#x([0-9A-Fa-f]{1,2});");

  @Override
  public void init(Properties properties) throws RuntimeException {
    super.init(properties);
    
    // Read keystore password
    String kpwd = properties.getProperty("keystore.pwd");
    if (Utils.isEmpty(kpwd))
      throw new RuntimeException("Keystore password is not found");
    byte[] bkpwd = Base64.decode(kpwd);
    
    // Read default service provider name which is same as servlet context
    String cpath = properties.getProperty("cpath").substring(1);
    
    // Read actual service provider name (if defined)
    _sname = properties.getProperty("saml." + cpath + ".sp_name", cpath);
    
    // Read service location
    _sloc = properties.getProperty("saml." + cpath + ".sp_loc");
    
    // Read service provider keystore password
    String spwd = properties.getProperty("keystore." + cpath + ".pwd");
    if (Utils.isEmpty(spwd))
      throw new RuntimeException("Keystore password for '" + cpath + 
                                                    "' key is not found");
    byte[] bspwd = Base64.decode(spwd);
    
    // Initialize internal variables
    try {
      DefaultBootstrap.bootstrap();
    } catch (ConfigurationException e) {
      throw new RuntimeException(e);
    }
    
    _bf = Configuration.getBuilderFactory();
    
    // Read servlet config directory
    String cdir = properties.getProperty("cdir");
    
    // Load service provider certificates
    KeyStore ks;
    try {
      ks = KeyStore.getInstance(KeyStore.getDefaultType());
    } catch (KeyStoreException e) {
      throw new RuntimeException(e);
    }

    FileInputStream fis;
    try {
      fis = new FileInputStream(cdir + 
            File.separator + Constants.KEYSTORE_FILE);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    try {
      ks.load(fis, new String(bkpwd).toCharArray());
    } catch (NoSuchAlgorithmException |
          CertificateException | IOException e) {
      throw new RuntimeException(e);
    }

    // Remember private key for sign request
    try {
      _key = (PrivateKey) ks.getKey(cpath, new String(bspwd).toCharArray());
    } catch (UnrecoverableKeyException | KeyStoreException |
                                NoSuchAlgorithmException e) {
      throw new RuntimeException("Error loading key for alias '" + cpath + 
                                               "'. ERROR: " + e.getMessage());
    }
    
    if (_key == null)
      throw new RuntimeException("Key for alias '" + cpath + 
          "' not found in keystore ");
    
    try {
      fis.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Map<String, String> passwordMap = new HashMap<String, String>();
    passwordMap.put(cpath, new String(bspwd));
    KeyStoreCredentialResolver resolver = 
        new KeyStoreCredentialResolver(ks, passwordMap);
     
    Criteria criteria = new EntityIDCriteria(cpath);
    CriteriaSet criteriaSet = new CriteriaSet(criteria);
     
    try {
      _scred = (X509Credential) resolver.resolveSingle(criteriaSet);
    } catch (SecurityException e) {
      throw new RuntimeException(e);
    }
    
    // Load IDP Metadata
    // Get parser pool manager
    _pmgr = new BasicParserPool();
    _pmgr.setNamespaceAware(true);
    
    // Parse metadata file
    InputStream in;
    try {
      in = new FileInputStream(cdir + 
                    File.separator + Constants.IDP_METADATA_FILE);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    
    Document doc;
    try {
      doc = _pmgr.parse(in);
    } catch (XMLParserException e) {
      throw new RuntimeException(e);
    }
    
    try {
      in.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    Element root = doc.getDocumentElement();
    
    UnmarshallerFactory unmarshallerFactory = 
        Configuration.getUnmarshallerFactory();
    Unmarshaller unmarshaller = 
        unmarshallerFactory.getUnmarshaller(root);
   
    EntityDescriptor eds;
    try {
      eds = (EntityDescriptor) unmarshaller.unmarshall(root);
    } catch (UnmarshallingException e) {
      throw new RuntimeException(e);
    }
    _idp = eds.getEntityID();
  
    DOMMetadataProvider mp = new DOMMetadataProvider(root);
    mp.setRequireValidMetadata(true);
    // mp.setParserPool(new BasicParserPool());
    try {
      mp.initialize();
    } catch (MetadataProviderException e) {
      throw new RuntimeException(e);
    }
    
    MetadataCredentialResolverFactory crf = 
                MetadataCredentialResolverFactory.getFactory();
     
    MetadataCredentialResolver cr = crf.getInstance(mp);
     
    // Look for signing key
    CriteriaSet cs = new CriteriaSet();
    cs.add((Criteria) new MetadataCriteria(
      IDPSSODescriptor.DEFAULT_ELEMENT_NAME, SAMLConstants.SAML20P_NS));
    cs.add(new EntityIDCriteria(_idp));
    cs.add(new UsageCriteria(UsageType.SIGNING));
    
    try {
      _cred = (X509Credential) cr.resolveSingle(cs);
    } catch (SecurityException e) {
      throw new RuntimeException(e);
    }
    
    if (_cred == null)
      throw new RuntimeException("Signing Key Descriptors " +
                  "not found in IDP Entity Descriptor");
    
    IDPSSODescriptor idps = eds.getIDPSSODescriptor(
                    SAMLConstants.SAML20P_NS);
    
    for (SingleSignOnService sss :idps.getSingleSignOnServices()) {
       if (sss.getBinding().equals(
                  SAMLConstants.SAML2_POST_BINDING_URI)) {
         _login = sss.getLocation();
         break;
       }
    }
    
    if (_login == null)
      throw new RuntimeException("IDP SSO POST Redirecting " +
                "Location not found in IDP Entity Descriptor");
    
    // Get Single Logout Service
    for (SingleLogoutService slo :idps.getSingleLogoutServices()) {
      if (slo.getBinding().equals(
                 SAMLConstants.SAML2_SOAP11_BINDING_URI))
        _logout = slo.getLocation();
    }
    
    if (_logout == null)
      throw new RuntimeException("IDP SLO SOAP " +
                "Location not found in IDP Entity Descriptor");
  }
  
  @Override
  public String authenticate(HttpServletRequest req, 
              String user, String password) throws WsSrvException {
    // Create AuthRequest
    try {
      return _login + "?" + URLEncoder.encode(createAuthnRequest(
                       getServiceLocation(req), getRefererUrl(req)), "UTF-8");
    } catch (MarshallingException | IOException | SignatureException e) {
      //-- 48
      throw new WsSrvException(48, e);
    }
  }

  /**
   * Look for a session and if it's completed or not found try 
   *                            re-validate existing security token
   */
  @Override
  public void validate(HttpServletRequest req, String stoken) 
                                                throws WsSrvException {
    Session session = activeSessions.get(stoken);
    
    if (!(session == null || session.isFinished()))
      // Everything fine
      return;

    getLogger(req).debug("Local session validation faied." +
                    " Sending POST request to validate SAML session");
    
    // Revalidate session
    PostMethod post = new PostMethod(_login);
    
    try {
      String ars = createAuthnRequest(getServiceLocation(req), 
                                                    getRefererUrl(req));
      post.setParameter("SAMLRequest", ars);
    } catch (MarshallingException | SignatureException | IOException e) {
      //-- 63
      throw new WsSrvException(63, e);
    }
    
    HttpClient hc = (new HttpClientBuilder()).buildClient();
    post.setRequestHeader("Cookie", (String) req.getSession().
        getServletContext().getAttribute("scookie_name") + "=" + stoken);
      
    int result;
    try {
      result = hc.executeMethod(post);
    } catch (IOException e) {
      //-- 66
      throw new WsSrvException(66, e);
    }
        
    // Expecting 200 if cookie valid and 302 if not, rest are errors
    if (result == HttpStatus.SC_OK) {
      // Extract end process SAML response from form
      String rb;
      try {
        rb = new String(post.getResponseBody());
      } catch (IOException e) {
        //-- 67
        throw new WsSrvException(67, e);
      }
      
      Matcher m = SAML_RESP.matcher(rb);
      
      if (m.matches() && m.groupCount() == 1) {
        String gs = m.group(1);
        
        // Convert hex decoded javascript variable
        String msg = "";
        int start = 0;
        Matcher m1 = HP.matcher(gs);
        
        while (m1.find()) {
          String dc = m1.group(1);
          int i = Integer.decode("#" + dc);
          
          int st = m1.start();
          int ed = m1.end();
          
          msg += gs.substring(start, st) + (char)i;
          start = ed;
        }
        
        try {
          procAuthnResponse(msg, stoken);
        } catch (Exception e) {
          //-- 62
          throw new WsSrvException(62, e);
        }
      }
    } else if (result == HttpStatus.SC_MOVED_TEMPORARILY) {
      //-- 64
      throw new WsSrvException(64, "Redirect received");
    } else {
      //-- 65
      throw new WsSrvException(65, "Unexpected http return code " + result);
    }
  }

  @Override
  public synchronized void logout(HttpServletRequest req, 
                          String stoken) throws WsSrvException {
    super.logout(req, stoken);
    
    LogoutRequest lreq;
    
    try {
      lreq = createLogoutRequest(activeSessions.get(stoken));
    } catch (MarshallingException | SignatureException | IOException e) {
      //-- 70
      throw new WsSrvException(70, e);
    }
    
    Body body = getSoapBody();
    body.getUnknownXMLObjects().add(lreq);
    
    BasicSOAPMessageContext ctx = new BasicSOAPMessageContext();
    ctx.setOutboundMessage(getSoapEnvelope(body));
    
    HttpClientBuilder cb = new HttpClientBuilder();
    HttpSOAPClient soapClient = new HttpSOAPClient(
                            cb.buildClient(), new BasicParserPool());
    
    try {
      soapClient.send(_logout, ctx);
    } catch (SOAPException | SecurityException e) {
      //-- 75
      throw new WsSrvException(75, e);
    }
    
    Envelope renv = (Envelope)ctx.getInboundMessage();
    
    LogoutResponse lresp;
    List<XMLObject> lobj = renv.getBody().getUnknownXMLObjects();
    
    // Expecting only one object with LogoutResponse
    if (lobj.size() != 1)
      //-- 66
      throw new WsSrvException(66, "Invalid SOAP envelope." +
          " Expected 1 return message but found " + lobj.size());
    
    try {
      lresp = (LogoutResponse) lobj.get(0);
    } catch (ClassCastException e) {
      //-- 72
      throw new WsSrvException(72, e, "Invalid type on logout response");
    }
    
    // Validate response id
    if (!lresp.getInResponseTo().equals(lreq.getID()))
      //-- 73
      throw new WsSrvException(73, lresp.getInResponseTo() + 
                      " doesn't match initial id " + lreq.getID());
    
    // Check status code
    Status status = lresp.getStatus();
    String scode = status.getStatusCode().getValue();
    if (!scode.equals(StatusCode.SUCCESS_URI)) {
      String smsg = status.getStatusMessage().getMessage();
      //-- 74
      throw new WsSrvException(74, "Invalid logout response status code " +
          scode + (smsg != null ? "; " + smsg + ";" : ""));
    }
    
    getLogger(req).debug("Single Logout successful with response " + scode);
  }
  
  @SuppressWarnings("unchecked")
  public String getMetadata(String url) throws WsSrvException {
    EntityDescriptor ed = ((SAMLObjectBuilder<EntityDescriptor>) 
        _bf.getBuilder(EntityDescriptor.DEFAULT_ELEMENT_NAME)).buildObject();
    ed.setEntityID(_sname);

    SPSSODescriptor sd = ((SAMLObjectBuilder<SPSSODescriptor>) 
        _bf.getBuilder(SPSSODescriptor.
                            DEFAULT_ELEMENT_NAME)).buildObject();
    
    sd.setAuthnRequestsSigned(false);
    sd.setWantAssertionsSigned(true);

    // Get key info for signing/encryption descriptors
    KeyInfo ki;
    X509KeyInfoGeneratorFactory kigf = new X509KeyInfoGeneratorFactory();
    kigf.setEmitEntityCertificate(true);
    KeyInfoGenerator kig = kigf.newInstance();
    try {
      ki = kig.generate(_scred);
    } catch (SecurityException e) {
      //-- 68
      throw new WsSrvException(68, e);
    }
    
    // Skip encryption keys
    /*
    KeyDescriptor ekd = ((SAMLObjectBuilder<KeyDescriptor>) 
        _sh.getBuilderFactory().getBuilder(
            KeyDescriptor.DEFAULT_ELEMENT_NAME)).buildObject();
    ekd.setUse(UsageType.ENCRYPTION);
    ekd.setKeyInfo(ki);
    sd.getKeyDescriptors().add(ekd);
    */

    KeyDescriptor skd = ((SAMLObjectBuilder<KeyDescriptor>) 
      _bf.getBuilder(
        KeyDescriptor.DEFAULT_ELEMENT_NAME)).buildObject();
    skd.setUse(UsageType.SIGNING); // Set usage
    skd.setKeyInfo(ki);
    sd.getKeyDescriptors().add(skd);
    
    
    NameIDFormat nid = ((SAMLObjectBuilder<NameIDFormat>) 
        _bf.getBuilder(NameIDFormat.
                                DEFAULT_ELEMENT_NAME)).buildObject();
    nid.setFormat( "urn:oasis:names:tc:SAML:2.0:nameid-format:transient");
    sd.getNameIDFormats().add(nid);

    // Setting AssertionConsumerService
    AssertionConsumerService acs = ((SAMLObjectBuilder<AssertionConsumerService>) 
      _bf.getBuilder(AssertionConsumerService.
                DEFAULT_ELEMENT_NAME)).buildObject();
    acs.setIndex(0);
    acs.setBinding(SAMLConstants.SAML2_ARTIFACT_BINDING_URI);
    acs.setLocation(url + "/saml2/consumer");
    sd.getAssertionConsumerServices().add(acs);
    
    SingleLogoutService slo = ((SAMLObjectBuilder<SingleLogoutService>) 
      _bf.getBuilder(SingleLogoutService.DEFAULT_ELEMENT_NAME)).buildObject();
    slo.setBinding(SAMLConstants.SAML2_SOAP11_BINDING_URI);
    slo.setLocation(url + "/saml2/logout");
    sd.getSingleLogoutServices().add(slo);

    sd.addSupportedProtocol(SAMLConstants.SAML20P_NS);
    ed.getRoleDescriptors().add(sd);
    
    EntityDescriptorMarshaller marshaller = new EntityDescriptorMarshaller();
    Element plaintextElement;
    try {
      plaintextElement = marshaller.marshall(ed);
    } catch (MarshallingException e) {
      //-- 69
      throw new WsSrvException(69, e);
    }

    return XMLHelper.nodeToString(plaintextElement);  
  }
  
  private String getRefererUrl(HttpServletRequest req) throws WsSrvException {
    // Check if referrer present to redirect after SSO authentication
    String rurl = req.getHeader("Referer");
    if (Utils.isEmpty(rurl))
      //-- 50
      throw new WsSrvException(50, "Missing HTTP referer");
    
    return rurl;
  }
  
  @Override
  public boolean hasSingleSignOn() {
    return true;
  }
  
  private String createAuthnRequest(String surl, String referer) 
                throws MarshallingException, IOException, SignatureException {
    return createAuthnRequest(surl, false, referer);
  }
  
  private String createAuthnRequest(String surl, 
                                            boolean fdeflate, String referer) 
          throws MarshallingException, IOException, SignatureException {
    AuthnRequest ar = createAuthnRequest(surl + "/saml2/consumer", 
          false, false, SAMLConstants.SAML2_POST_BINDING_URI, null, null);
    
    // Create signature and add to auth Request
    Signature sig = getSignature();
    ar.setSignature(sig);
    
    AuthnRequestMarshaller marshaller = new AuthnRequestMarshaller();
    Element arn = marshaller.marshall(ar);
    
    Signer.signObject(sig);
    
    byte[] res = XMLHelper.nodeToString(arn).getBytes();
    // System.out.println(new String(res));
    
    // Remember authentication request been sent
    String rid = ar.getID();
    SamlAbstractRequest sar = new SamlAbstractRequest(rid, referer);
    _rmap.put(rid, sar);
    
    return fdeflate ? deflate(res) : encode(res);
  }

  @SuppressWarnings("unchecked")
  private AuthnRequest createAuthnRequest(String surl, 
                  boolean fauth, boolean isp, String proto,
                        NameIDPolicy npolicy, RequestedAuthnContext actx) {
  
    AuthnRequest ar = ((SAMLObjectBuilder<AuthnRequest>) 
    _bf.getBuilder(AuthnRequest.DEFAULT_ELEMENT_NAME)).buildObject();
    ar.setAssertionConsumerServiceURL(surl);
    ar.setForceAuthn(fauth);
    String uid = UUID.randomUUID().toString();
    
    ar.setID(uid);
    ar.setIsPassive(isp);
    ar.setIssueInstant(new DateTime());
    ar.setProtocolBinding(proto);
    ar.setVersion(SAMLVersion.VERSION_20);
    ar.setIssuer(getIssuer());
    // ar.setNameIDPolicy(npolicy);
    // ar.setRequestedAuthnContext(actx);
    
    return ar;
  }
  
  public LogoutRequest createLogoutRequest(Session session) 
             throws IOException, SignatureException, MarshallingException {
    if (_logout == null)
      return null;
    
    // Retrieve initial authn response
    Response resp = (Response)session.getCustomParams();
    LogoutRequest lr = createLogoutRequest(resp);
    
    // Create signature and add to auth Request
    LogoutRequestMarshaller marshaller = new LogoutRequestMarshaller();
    
    Signature sig = getSignature();
    lr.setSignature(sig);
    // Marshall object to prepare for signature
    marshaller.marshall(lr);
    Signer.signObject(sig);
    
    return lr;
  }
  
  @SuppressWarnings("unchecked")
  public LogoutRequest createLogoutRequest(Response resp) {
    LogoutRequest lr = ((SAMLObjectBuilder<LogoutRequest>) 
          _bf.getBuilder(LogoutRequest.DEFAULT_ELEMENT_NAME)).buildObject();
    String uid = UUID.randomUUID().toString();
    
    lr.setID(uid);
    lr.setIssueInstant(new DateTime());
    lr.setVersion(SAMLVersion.VERSION_20);
    lr.setIssuer(getIssuer());
    
    // Get NameID and SessionIndex from first assertion from
    // Authentication Response object
    Assertion asr = resp.getAssertions().get(0);
    NameID nid = ((SAMLObjectBuilder<NameID>) 
        _bf.getBuilder(NameID.DEFAULT_ELEMENT_NAME)).buildObject();
    nid.setValue(asr.getSubject().getNameID().getValue());
    lr.setNameID(nid);
    
    // Set session index(es)
    List<AuthnStatement> ausl = asr.getAuthnStatements();
    if (ausl != null) {
      for (AuthnStatement aus :ausl) {
        SessionIndex sindex = ((SAMLObjectBuilder<SessionIndex>) 
            _bf.getBuilder(SessionIndex.DEFAULT_ELEMENT_NAME)).buildObject();
        sindex.setSessionIndex(aus.getSessionIndex());
        lr.getSessionIndexes().add(sindex);
      }
    }
    
    return lr;
  }
  
  public String procAuthnResponse(String msg, String stoken)
                                                  throws WsSrvException {
    Document doc;
    try {
      doc = _pmgr.parse(new ByteArrayInputStream(Base64.decode(msg)));
    } catch (XMLParserException e) {
      //-- 51
      throw new WsSrvException(51, e);
    }

    // Get Response
    Element element = doc.getDocumentElement();
    UnmarshallerFactory unmarshallerFactory = 
                  Configuration.getUnmarshallerFactory();
    Unmarshaller unmarshaller=unmarshallerFactory.getUnmarshaller(element);
  
    Response rs;
    try {
      rs = (Response) unmarshaller.unmarshall(element);
    } catch (UnmarshallingException e) {
      //-- 52
      throw new WsSrvException(52, e);
    }
  
    // Check status code
    String scode = rs.getStatus().getStatusCode().getValue();
    if (!scode.equals(StatusCode.SUCCESS_URI))
      //-- 71
      throw new WsSrvException(71, "Received Invalid Status Code '" + 
                                                                scode + "'");
    
    // Check if response has corresponded request
    String rid = rs.getInResponseTo();
    SamlAbstractRequest sar = _rmap.get(rid);
    if (sar == null)
      //-- 59
      throw new WsSrvException(59, "Unknown Authn Response");
    
    // Delete initiated request
    _rmap.remove(rid);
    
    // Validate signature of each Assertion
    List<Assertion> asl = rs.getAssertions();
    if (asl == null || asl.size() == 0)
      //-- 53
      throw new WsSrvException(53, "No assertions found in Authn Response");
    
    for (Assertion as: asl) {
      Signature sig = as.getSignature();
      
      if (sig == null)
        //-- 54
        throw new WsSrvException(54, "Signature not found");
      
      SAMLSignatureProfileValidator pvalidator = 
                new SAMLSignatureProfileValidator();
      try {
        pvalidator.validate(sig);
      } catch (ValidationException e) {
        //-- 55
        throw new WsSrvException(55, e);
      }
       
      SignatureValidator svalidator = new SignatureValidator(_cred);
      try {
        svalidator.validate(sig);
      } catch (ValidationException e) {
        //-- 56
        throw new WsSrvException(56, e);
      }
    }
    
    String uid = null;
    
    Assertion asr = asl.get(0);
    for (AttributeStatement ast: asr.getAttributeStatements()) {
      for (Attribute at: ast.getAttributes())
        // Look for UserId value
        if ("UserId".equals(at.getName())) {
          uid = "";
          for (XMLObject atv : at.getAttributeValues())
            uid += atv.getDOM().getTextContent();
        }
    }

    if (uid == null)
      //-- 60
      throw new WsSrvException(60, "User Id not found");
    
    // Register user session
    createUserSession(asr, uid, stoken, rs);
    
    return sar.getReqOriginUrl();
  }
  
  private synchronized void createUserSession(Assertion asr, String user, 
                                            String stoken, Object params) {
    Session session = createUserSession(user, stoken, params);
    List<AuthnStatement> ausl = asr.getAuthnStatements();
    
    if (ausl != null)
      for (AuthnStatement aus :ausl)
        _smap.put(aus.getSessionIndex(), session);
  }
  
  public byte[] procLogoutRequest(HttpServletRequest req) throws WsSrvException {
    LogoutResponse lresp;
    
    try {
      lresp = procLogoutRequest(req.getInputStream());
    } catch (XMLParserException | UnmarshallingException | 
                          IOException | ValidationException e) {
      //-- 76
      throw new WsSrvException(76, e);
    }
    
    // Return soap logout response
    Envelope evp = makeSoapEnvelope(lresp);
    
    Marshaller marshaller = Configuration.
                              getMarshallerFactory().getMarshaller(evp);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    OutputStreamWriter writer = new OutputStreamWriter(out, 
                                                Charset.forName("UTF-8"));
    
    try {
      XMLHelper.writeNode(marshaller.marshall(evp), writer);
    } catch (MarshallingException e) {
      //-- 77
      throw new WsSrvException(77, e);
    }
    
    return out.toByteArray();
  }
  
  private LogoutResponse procLogoutRequest(InputStream in) 
           throws XMLParserException, UnmarshallingException, 
                             WsSrvException, ValidationException {
    Document d = _pmgr.parse(in);
    
    Element rel = d.getDocumentElement();
    Unmarshaller um = Configuration.
          getUnmarshallerFactory().getUnmarshaller(rel);
      
    Envelope req;
    try {
      req = (Envelope) um.unmarshall(rel);
    } catch (ClassCastException e) {
      throw e;
    }
      
    LogoutRequest lreq;
    List<XMLObject> lobj = req.getBody().getUnknownXMLObjects();
    
    // Expecting only one object with LogoutResponse
    if (lobj.size() != 1)
      //-- 78
      throw new WsSrvException(78, "Invalid SOAP envelope." +
          " Expected 1 included element but found " + lobj.size());
      
    try {
      lreq = (LogoutRequest) lobj.get(0);
    } catch (ClassCastException e) {
      //-- 79
      throw new WsSrvException(79, e);
    }
    
    // Check signature
    validateSignature(lreq);
    
    // After logout request validated than close local session
    String smsg = "";
    String scode = StatusCode.SUCCESS_URI;

    for (SessionIndex sidx :lreq.getSessionIndexes()) {
      String sindex = sidx.getSessionIndex();
      Session session = _smap.get(sindex);
      if (session == null) {
        scode = StatusCode.RESPONDER_URI;
        smsg += "Session " + sindex + " not found;";
      } else if (session.isFinished()) {
        scode = StatusCode.RESPONDER_URI;
        smsg += "Session " + sindex + " already terminated;";
      } else {
        session.logout();
      }
    }
    
    return createLogoutResponse(lreq.getID(), scode, smsg);
  }
  
  @SuppressWarnings("unchecked")
  private LogoutResponse createLogoutResponse(String id, 
                                      String code, String msg) {
      
    // Consume LogoutResponse
    LogoutResponse lresp = ((SAMLObjectBuilder<LogoutResponse>) 
              _bf.getBuilder(LogoutResponse.DEFAULT_ELEMENT_NAME)).
                                                            buildObject();
    String uid = UUID.randomUUID().toString();
    
    lresp.setID(uid);
    lresp.setInResponseTo(id);
    lresp.setIssueInstant(new DateTime());
    lresp.setVersion(SAMLVersion.VERSION_20);
    lresp.setIssuer(getIssuer());
    
    // Set status code
    Status status = ((SAMLObjectBuilder<Status>) _bf.getBuilder(
                Status.DEFAULT_ELEMENT_NAME)).buildObject();
    
    StatusCode scode = ((SAMLObjectBuilder<StatusCode>) _bf.getBuilder(
                StatusCode.DEFAULT_ELEMENT_NAME)).buildObject();
    scode.setValue(code);
    status.setStatusCode(scode);
    
    if (!msg.equals("")) {
      StatusMessage smsg = ((SAMLObjectBuilder<StatusMessage>) _bf.getBuilder(
                            StatusMessage.DEFAULT_ELEMENT_NAME)).buildObject();
      smsg.setMessage(msg);
      status.setStatusMessage(smsg);
    }
    
    lresp.setStatus(status);
    
    return lresp;
  }
  
  private void validateSignature(SignableSAMLObject obj) 
                          throws WsSrvException, ValidationException {
    Signature sig = obj.getSignature();
      
    if (sig == null)
      throw new WsSrvException(80, "Signature not found");
    
    SAMLSignatureProfileValidator pvalidator = 
              new SAMLSignatureProfileValidator();
    pvalidator.validate(sig);
     
    SignatureValidator svalidator = new SignatureValidator(_cred);
    svalidator.validate(sig);
  }

  private Envelope makeSoapEnvelope(XMLObject obj) {
    Body body = getSoapBody();
    body.getUnknownXMLObjects().add(obj);
    Envelope evp = (Envelope) _bf.getBuilder(Envelope.DEFAULT_ELEMENT_NAME).
                                    buildObject(Envelope.DEFAULT_ELEMENT_NAME);
    evp.setBody(body);
   
    return evp;
  }
  
  private String deflate(byte[] msg) throws IOException {

    Deflater dfl = new Deflater(Deflater.DEFLATED, true);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    DeflaterOutputStream dos = 
              new DeflaterOutputStream(out, dfl);
    dos.write(msg);
    dos.close();

    return encode(out.toByteArray(), true);
  }
  
  private String encode(byte[] msg) throws UnsupportedEncodingException {
    return encode(msg, false);
  }
  
  private String encode(byte[] msg, boolean fdeflate) 
                  throws UnsupportedEncodingException {
    return fdeflate ? URLEncoder.encode(Base64.encodeBytes(msg, 
      Base64.DONT_BREAK_LINES), "UTF-8") : Base64.encodeBytes(msg);
  }
  
  
  // Signature disabled
  private Signature getSignature() {
    Signature sig = (new SignatureBuilder()).buildObject();
    sig.setSigningCredential(_scred);
    sig.setSignatureAlgorithm(
            SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
    sig.setCanonicalizationAlgorithm(
        SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
    
    return sig;
  }
  
  /*  
  public X509Credential getX509Credentials() {
    return _scred;
  }
  
  public XMLObjectBuilderFactory getBuilderFactory() {
    return _bf;
  }
  
  public String getServiceName() {
    return _sname;
  }
  
  */
  /*
  public X509Credential getX509Credential() {
    return _scred;
  }
  */
  
  public String getServiceLocation(HttpServletRequest req) {
    return _sloc == null ? getDefServiceLocationUrl(req) : _sloc;
  }
  
  public String getDefServiceLocationUrl(HttpServletRequest req) {
    return "http:" + (req.isSecure() ? "s" : "") + "//" + 
        req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
  }
  
  public String getConfigServiceLocation() {
    return _sloc;
  }
  
  private Issuer getIssuer() {
    @SuppressWarnings("unchecked")
    Issuer issuer = ((SAMLObjectBuilder<Issuer>) _bf.
          getBuilder(Issuer.DEFAULT_ELEMENT_NAME)).buildObject();
    issuer.setValue(_sname);
    
    return issuer;
  }

  private Logger getLogger(HttpServletRequest req) {
    return (Logger) req.getSession().getServletContext().getAttribute("log");
  }
  
  public Body getSoapBody() {
    return (Body) _bf.getBuilder(Body.DEFAULT_ELEMENT_NAME).
                  buildObject(Body.DEFAULT_ELEMENT_NAME);
  }
  
  public Envelope getSoapEnvelope(Body body) {
    Envelope evp = (Envelope) _bf.
          getBuilder(Envelope.DEFAULT_ELEMENT_NAME).
            buildObject(Envelope.DEFAULT_ELEMENT_NAME);
    evp.setBody(body);
    
    return evp;
  }
  
  class SamlAbstractRequest {
    private String _id;
    private String _url;
    
    public SamlAbstractRequest(String id) {
      this(id, null);
    }
    
    public SamlAbstractRequest(String id, String url) {
      _id = id;
      _url = url;
    }

    public String getAuthId() {
      return _id;
    }

    public String getReqOriginUrl() {
      return _url;
    }
  }
}
