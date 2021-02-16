package remotedesk.requestprocess;
import remotedesk.exceptions.*;
import remotedesk.remotedeskUI.*;
import rdProject.utilities.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
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
try{
OutputStream outputStream=socket.getOutputStream();
InputStream inputStream=socket.getInputStream();
JFrame csFrame=ClientScreenFrame.getCSFrame();
JLabel csLabel=ClientScreenFrame.getCSLabel();
csLabel.addMouseListener(new MouseAdapter(){
public void mousePressed(MouseEvent ev)
{
try{
byte b[]=new byte[8];
b[0]=0;
b[1]=(byte)ev.getButton();
System.out.println(ev.getButton()+" pressed");
outputStream.write(b,0,8);
outputStream.flush();
}catch(Exception e)
{
e.printStackTrace();
}
}
public void mouseReleased(MouseEvent ev)
{
try{
byte b[]=new byte[8];
b[0]=1;
b[1]=(byte)ev.getButton();
System.out.println(ev.getButton()+" released");

outputStream.write(b,0,8);
outputStream.flush();
}catch(Exception e)
{
e.printStackTrace();
}
}
public void mouseExited(MouseEvent ev)
{
}
public void mouseEntered(MouseEvent ev)
{
}
});
}catch(Exception e)
{
e.printStackTrace();
}
}
}
 