package com.remotedeskclient.requestsender;
import java.io.*;
import java.net.*;
import com.remotedeskclient.exceptions.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class ScreenSender extends Thread
{
private Socket socket;
public ScreenSender(Socket socket)
{
this.socket=socket;
start();
}
public void run()
{
try{
Rectangle r=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
ByteArrayOutputStream baos=new ByteArrayOutputStream();
byte header[]=new byte[10];
byte ack[]=new byte[1];
byte[] buffer=new byte[1024];
int headerSize;
int bytesToWrite=0;
Robot robot=null;
try{
robot=new Robot();
}catch(AWTException awte)
{
awte.printStackTrace();
}
BufferedImage bufferedImage;
int bc;
while(true)
{
baos=new ByteArrayOutputStream();
bufferedImage=robot.createScreenCapture(r);
ImageIO.write(bufferedImage,"jpg",baos);
baos.flush();
byte[] imageBytes=baos.toByteArray();
headerSize=imageBytes.length;
for(int i=9;i>=0;--i)
{
header[i]=(byte)(headerSize%10);
headerSize=headerSize/10;
}
os.write(header,0,10);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
headerSize=imageBytes.length;
int fromIndex=0;
bytesToWrite=1024;
while(fromIndex<headerSize)
{
if(bytesToWrite>headerSize-fromIndex)
{
bytesToWrite=headerSize-fromIndex;
}
os.write(imageBytes,fromIndex,bytesToWrite);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex=fromIndex+bytesToWrite;
}
//System.out.println(headerSize);
}
}catch(IOException ioe)
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