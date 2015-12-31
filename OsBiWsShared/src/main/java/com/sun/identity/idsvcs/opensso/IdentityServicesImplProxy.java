package com.sun.identity.idsvcs.opensso;

public class IdentityServicesImplProxy implements com.sun.identity.idsvcs.opensso.IdentityServicesImpl {
  private String _endpoint = null;
  private com.sun.identity.idsvcs.opensso.IdentityServicesImpl identityServicesImpl = null;
  
  public IdentityServicesImplProxy() {
    _initIdentityServicesImplProxy();
  }
  
  public IdentityServicesImplProxy(String endpoint) {
    _endpoint = endpoint;
    _initIdentityServicesImplProxy();
  }
  
  private void _initIdentityServicesImplProxy() {
    try {
      identityServicesImpl = (new com.sun.identity.idsvcs.opensso.IdentityServicesImplServiceLocator()).getIdentityServicesImplPort();
      if (identityServicesImpl != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)identityServicesImpl)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)identityServicesImpl)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (identityServicesImpl != null)
      ((javax.xml.rpc.Stub)identityServicesImpl)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.sun.identity.idsvcs.opensso.IdentityServicesImpl getIdentityServicesImpl() {
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    return identityServicesImpl;
  }
  
  @Override
  public void log(com.sun.identity.idsvcs.opensso.Token app, com.sun.identity.idsvcs.opensso.Token subject, java.lang.String logName, java.lang.String message) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    identityServicesImpl.log(app, subject, logName, message);
  }
  
  @Override
  public com.sun.identity.idsvcs.opensso.UserDetails attributes(java.lang.String[] attributes, com.sun.identity.idsvcs.opensso.Token subject, java.lang.Boolean refresh) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    return identityServicesImpl.attributes(attributes, subject, refresh);
  }
  
  @Override
  public com.sun.identity.idsvcs.opensso.Token authenticate(java.lang.String username, java.lang.String password, java.lang.String uri, java.lang.String client) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.OrgInactive, com.sun.identity.idsvcs.opensso.UserNotFound, com.sun.identity.idsvcs.opensso.UserLocked, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.MaximumSessionReached, com.sun.identity.idsvcs.opensso.InvalidCredentials, com.sun.identity.idsvcs.opensso.AccountExpired, com.sun.identity.idsvcs.opensso.UserInactive, com.sun.identity.idsvcs.opensso.InvalidPassword{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    return identityServicesImpl.authenticate(username, password, uri, client);
  }
  
  @Override
  public void logout(com.sun.identity.idsvcs.opensso.Token subject) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    identityServicesImpl.logout(subject);
  }
  
  @Override
  public boolean authorize(java.lang.String uri, java.lang.String action, com.sun.identity.idsvcs.opensso.Token subject) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    return identityServicesImpl.authorize(uri, action, subject);
  }
  
  @Override
  public boolean isTokenValid(com.sun.identity.idsvcs.opensso.Token token) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.InvalidToken{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    return identityServicesImpl.isTokenValid(token);
  }
  
  @Override
  public java.lang.String getCookieNameForToken() throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    return identityServicesImpl.getCookieNameForToken();
  }
  
  @Override
  public java.lang.String[] getCookieNamesToForward() throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    return identityServicesImpl.getCookieNamesToForward();
  }
  
  @Override
  public java.lang.String[] search(java.lang.String filter, com.sun.identity.idsvcs.opensso.Attribute[] attributes, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    return identityServicesImpl.search(filter, attributes, admin);
  }
  
  @Override
  public void create(com.sun.identity.idsvcs.opensso.IdentityDetails identity, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.DuplicateObject, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    identityServicesImpl.create(identity, admin);
  }
  
  @Override
  public com.sun.identity.idsvcs.opensso.IdentityDetails read(java.lang.String name, com.sun.identity.idsvcs.opensso.Attribute[] attributes, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.ObjectNotFound, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    return identityServicesImpl.read(name, attributes, admin);
  }
  
  @Override
  public void update(com.sun.identity.idsvcs.opensso.IdentityDetails identity, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.ObjectNotFound, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    identityServicesImpl.update(identity, admin);
  }
  
  @Override
  public void delete(com.sun.identity.idsvcs.opensso.IdentityDetails identity, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.ObjectNotFound, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied{
    if (identityServicesImpl == null)
      _initIdentityServicesImplProxy();
    identityServicesImpl.delete(identity, admin);
  }
  
  
}