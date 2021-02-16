/*package com.remotedeskclient.requestsender;
import java.io.*;
import java.net.*;
import com.remotedeskclient.exceptions.*;
public class FileSender extends Thread
{
private String server;
private int port;
private Socket socket;
public FileSender(String server)
{
this.server=server;
this.port=9995;
start();
}
public void run()
{
try{
socket=new Socket(server,port);
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
ByteArrayOutputStream baos=new ByteArrayOutputStream();
byte header[]=new byte[10];
byte ack[]=new byte[1];
byte[] buffer=new byte[1024];
int headerSize;
int bytesToWrite=0;
int bc;
while(true)
{
baos=new ByteArrayOutputStream();
bufferedImage=robot.createScreenCapture(r);
imageIcon=new ImageIcon(bufferedImage);
oos=new ObjectOutputStream(baos);
oos.writeObject(imageIcon);
oos.flush();
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
System.out.println(headerSize);
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
}*/