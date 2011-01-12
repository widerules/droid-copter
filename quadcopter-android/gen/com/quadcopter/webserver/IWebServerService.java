/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/freezrburn/workspace/quadcopter-android/src/com/quadcopter/webserver/IWebServerService.aidl
 */
package com.quadcopter.webserver;
public interface IWebServerService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.quadcopter.webserver.IWebServerService
{
private static final java.lang.String DESCRIPTOR = "com.quadcopter.webserver.IWebServerService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.quadcopter.webserver.IWebServerService interface,
 * generating a proxy if needed.
 */
public static com.quadcopter.webserver.IWebServerService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.quadcopter.webserver.IWebServerService))) {
return ((com.quadcopter.webserver.IWebServerService)iin);
}
return new com.quadcopter.webserver.IWebServerService.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getPort:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPort();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setWebCamByteArray:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
this.setWebCamByteArray(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_start:
{
data.enforceInterface(DESCRIPTOR);
this.start();
reply.writeNoException();
return true;
}
case TRANSACTION_stop:
{
data.enforceInterface(DESCRIPTOR);
this.stop();
reply.writeNoException();
return true;
}
case TRANSACTION_isRunning:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isRunning();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.quadcopter.webserver.IWebServerService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
//returns the the port number 
//that the server is running on

public int getPort() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPort, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//Send the byte array to the server. This image is
//in the jpeg format and can be viewed when a user
//goes to http://ip.address/webcam.jpg

public void setWebCamByteArray(byte[] img) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(img);
mRemote.transact(Stub.TRANSACTION_setWebCamByteArray, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//start the web server thread

public void start() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_start, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//stop the web server thread

public void stop() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stop, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//is the server running

public boolean isRunning() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isRunning, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getPort = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setWebCamByteArray = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_start = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_stop = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_isRunning = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
//returns the the port number 
//that the server is running on

public int getPort() throws android.os.RemoteException;
//Send the byte array to the server. This image is
//in the jpeg format and can be viewed when a user
//goes to http://ip.address/webcam.jpg

public void setWebCamByteArray(byte[] img) throws android.os.RemoteException;
//start the web server thread

public void start() throws android.os.RemoteException;
//stop the web server thread

public void stop() throws android.os.RemoteException;
//is the server running

public boolean isRunning() throws android.os.RemoteException;
}
