/*package remotedesk.requestprocess;
import java.io.*;
import java.net.*;
import remotedesk.exceptions.*;
public class ClientHandler extends Thread
{
private ServerSocket serverSocket;
private Socket socket;
private Boolean firstRequest;
private int port;
public ClientHandler() throws RemoteDesktopException
{
configureCH();
firstRequest=true;
try{
serverSocket=new ServerSocket(port);
}catch(IOException io)
{
System.out.println(io);
}
start();
}
void configureCH() throws RemoteDesktopException
{
try{
File file=new File("ClientHandler.config");
if(file.exists()==false)
{
throw new RemoteDesktopException("ClientHandler.config is missing.");
}
RandomAccessFile raf=new RandomAccessFile(file,"rw");
this.port=Integer.parseInt(raf.readLine());
raf.close();
}catch(FileNotFoundException fnfe)
{
System.out.println(fnfe);

}catch(IOException ioe)
{
System.out.println(ioe);
}
}
public void run()
{
try{
while(true)
{
socket=serverSocket.accept();
if(firstRequest)
{
new ScreenReceiver(socket);
firstRequest=false;
}
new FileReceiver(socket); 
this.suspend();
}
}catch(IOException ioe)
{
System.out.println(ioe);
}
}
}*/