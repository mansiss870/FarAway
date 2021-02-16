package remotedesk;
import java.io.*;
import java.net.*;
import remotedesk.exceptions.*;
import remotedesk.requestprocess.*;
//import com.google.Gson;
public class RemoteDesktopServer
{
private int RDPortNumber;
private int FTPortNumber;
private ServerSocket remoteDesktopServer;
private Socket socket;
private ServerSocket fileTransferServer;
public RemoteDesktopServer() throws RemoteDesktopException
{
configureRD();
startServer();
}
void configureRD() throws RemoteDesktopException
{
//try{
//Gson gson=new Gson();
//Configuration c=new Configuration(new FileReader("RDServer.config"),Configuration.class);
this.RDPortNumber=7777;
this.FTPortNumber=8888;
//}catch(IOException ioe )
//{
//ioe.printStackTrace();
//}
}
void startServer()
{
try{
remoteDesktopServer=new ServerSocket(RDPortNumber);
System.out.println("starts Listening..");
socket=remoteDesktopServer.accept();
ScreenReceiver sr=new ScreenReceiver(socket);
socket=remoteDesktopServer.accept();
MouseMoved mm=new MouseMoved(socket);
socket=remoteDesktopServer.accept();
MouseActions ma=new MouseActions(socket);
socket=remoteDesktopServer.accept();
KeyPressActions kpa=new KeyPressActions(socket);
sr.join();
mm.join();
ma.join();
kpa.join();
}catch(Exception e)
{
e.printStackTrace();
}
}
}
class Configuration
{
public int RDPortNumber;
public int FTPortNumber;
}