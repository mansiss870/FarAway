package remotedesk.requestprocess;
import java.io.*;
import java.net.*;
import remotedesk.exceptions.*;
public class FileSender extends Thread
{
private Socket socket;
private InputStream is;
private OutputStream os;
public FileSender(Socket socket)
{
this.socket=socket;
}

}