package com.remotedeskclient.requestsender;
import java.io.*;
import rdProject.utilities.*;
import java.net.*;
import com.remotedeskclient.exceptions.*;
import java.awt.*;
import javax.swing.*;
public class MouseActions extends Thread
{
private Socket socket; 
public MouseActions(Socket socket)
{
this.socket=socket;
start();
}
public void run()
{
int j=0;
Robot robot;
try{
robot=new Robot();
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
int m=-1;
byte[] b=new byte[8];
while(true)
{
while(m==-1) m=is.read(b);
m=-1;
if(b[0]==0)
{
if(b[1]==1)
{
robot.mousePress(16);
}
if(b[1]==2)
{
robot.mouseRelease(8);
}

if(b[1]==3)
{
robot.mousePress(4);
}
}
else
{
if(b[1]==1)
{
robot.mouseRelease(16);
}
if(b[1]==2)
{
robot.mouseRelease(8);
}

if(b[1]==3)
{
robot.mouseRelease(4);
}
}
}
}catch(Exception ioe)
{
ioe.printStackTrace();
try{
stop();
socket.close();
}catch(IOException io)
{
io.printStackTrace();
}
}
}
}
