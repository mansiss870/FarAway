package com.remotedeskclient;
import com.remotedeskclient.exceptions.*;
import com.remotedeskclient.requestsender.*;
import java.net.*;
import java.io.*;
public class RemoteDesktopClient
{
private String server;
private int port;
private Socket socket;
public RemoteDesktopClient() throws RemoteDeskClientException
{
configureRD();
connect();
}
void configureRD() throws RemoteDeskClientException
{
try{
this.port=7777;
InputStreamReader isr=new InputStreamReader(System.in);
BufferedReader br=new BufferedReader(isr);
System.out.print("Enter server ip:");
this.server=br.readLine();
br.close();
}catch(IOException ioe)
{
ioe.printStackTrace();
}
}
public void connect()
{
try{
socket=new Socket(server,port);
new ScreenSender(socket);
socket=new Socket(server,port);
new MouseMove(socket);
socket=new Socket(server,port);
new MouseActions(socket);
socket=new Socket(server,port);
new KeyPressActions(socket);
}catch(IOException io)
{
io.printStackTrace();
}
}
}
