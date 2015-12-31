/**
 * IdentityServicesImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sun.identity.idsvcs.opensso;

public interface IdentityServicesImpl extends java.rmi.Remote {
    public void log(com.sun.identity.idsvcs.opensso.Token app, com.sun.identity.idsvcs.opensso.Token subject, java.lang.String logName, java.lang.String message) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied;
    public com.sun.identity.idsvcs.opensso.UserDetails attributes(java.lang.String[] attributes, com.sun.identity.idsvcs.opensso.Token subject, java.lang.Boolean refresh) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied;
    public com.sun.identity.idsvcs.opensso.Token authenticate(java.lang.String username, java.lang.String password, java.lang.String uri, java.lang.String client) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.OrgInactive, com.sun.identity.idsvcs.opensso.UserNotFound, com.sun.identity.idsvcs.opensso.UserLocked, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.MaximumSessionReached, com.sun.identity.idsvcs.opensso.InvalidCredentials, com.sun.identity.idsvcs.opensso.AccountExpired, com.sun.identity.idsvcs.opensso.UserInactive, com.sun.identity.idsvcs.opensso.InvalidPassword;
    public void logout(com.sun.identity.idsvcs.opensso.Token subject) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure;
    public boolean authorize(java.lang.String uri, java.lang.String action, com.sun.identity.idsvcs.opensso.Token subject) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired;
    public boolean isTokenValid(com.sun.identity.idsvcs.opensso.Token token) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.InvalidToken;
    public java.lang.String getCookieNameForToken() throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure;
    public java.lang.String[] getCookieNamesToForward() throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.GeneralFailure;
    public java.lang.String[] search(java.lang.String filter, com.sun.identity.idsvcs.opensso.Attribute[] attributes, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired;
    public void create(com.sun.identity.idsvcs.opensso.IdentityDetails identity, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.DuplicateObject, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired;
    public com.sun.identity.idsvcs.opensso.IdentityDetails read(java.lang.String name, com.sun.identity.idsvcs.opensso.Attribute[] attributes, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.ObjectNotFound, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied;
    public void update(com.sun.identity.idsvcs.opensso.IdentityDetails identity, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.ObjectNotFound, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied;
    public void delete(com.sun.identity.idsvcs.opensso.IdentityDetails identity, com.sun.identity.idsvcs.opensso.Token admin) throws java.rmi.RemoteException, com.sun.identity.idsvcs.opensso.NeedMoreCredentials, com.sun.identity.idsvcs.opensso.ObjectNotFound, com.sun.identity.idsvcs.opensso.GeneralFailure, com.sun.identity.idsvcs.opensso.TokenExpired, com.sun.identity.idsvcs.opensso.AccessDenied;
}
