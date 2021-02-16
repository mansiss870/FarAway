package com.remotedeskclient.requestsender;
import java.io.*;
import rdProject.utilities.*;
import java.net.*;
import com.remotedeskclient.exceptions.*;
import java.awt.*;
import javax.swing.*;
public class MouseMove extends Thread
{
private Socket socket; 
public MouseMove(Socket socket)
{
this.socket=socket;
start();
}
public void run()
{
MouseLocation mouseLocation;
ByteArrayInputStream bais;
ByteArrayOutputStream baos;
byte ack[]=new byte[1];
ObjectInputStream ois;
ObjectOutputStream oos;
int j=0;
Robot robot=null;
try{
robot=new Robot();
}catch(AWTException awte)
{
awte.printStackTrace();
}
try
{
byte[] b=new byte[2];
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
byte buffer[]=new byte[1024];
Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
mouseLocation=new MouseLocation((int)screenSize.getWidth(),(int)screenSize.getHeight());
baos=new ByteArrayOutputStream();
oos=new ObjectOutputStream(baos);
oos.writeObject(mouseLocation);
oos.flush();
buffer=baos.toByteArray();
os.write(buffer,0,buffer.length);
os.flush();
while(true)
{
j=is.read(ack);
if(j!=-1) break;
}
while(true)
{
j=-1;
j=is.read(buffer);
if(j==-1) continue;
bais=new ByteArrayInputStream(buffer);
ois=new ObjectInputStream(bais);
mouseLocation=(MouseLocation)ois.readObject();
robot.mouseMove(mouseLocation.getX(),mouseLocation.getY());
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
