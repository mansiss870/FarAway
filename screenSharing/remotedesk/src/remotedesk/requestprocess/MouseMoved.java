package remotedesk.requestprocess;
import remotedesk.exceptions.*;
import remotedesk.remotedeskUI.*;
import rdProject.utilities.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class MouseMoved extends Thread
{
private Socket socket;
private OutputStream os;
private InputStream is;
private ByteArrayInputStream bais;
private ByteArrayOutputStream baos;


public MouseMoved(Socket socket)
{
this.socket=socket;
start();
}


public void run()
{
try{
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
JFrame csFrame=ClientScreenFrame.getCSFrame();
JLabel csLabel=ClientScreenFrame.getCSLabel();
ObjectOutputStream oos;
ObjectInputStream ois;
byte buffer[]=new byte[1024];
byte ack[]=new byte[1];
MouseLocation mouseLocation;
int j;
while(true)
{
j=is.read(buffer);
if(j!=-1) break;
}
os.write(ack,0,1);
os.flush();
System.out.println("ack sent");

bais=new ByteArrayInputStream(buffer);
ois=new ObjectInputStream(bais);
mouseLocation=(MouseLocation)ois.readObject();
int clientScreenX=mouseLocation.getX();
int clientScreenY=mouseLocation.getY();
double yScale=(double)clientScreenY/(double)csLabel.getHeight();
double xScale=(double)clientScreenX/(double)csLabel.getWidth();
System.out.println(clientScreenX+","+clientScreenY+","+yScale+","+xScale);
System.out.println(csLabel.getWidth()+","+csLabel.getHeight());
csLabel.addMouseMotionListener(new MouseMotionListener(){
public void mouseMoved(MouseEvent ev)
{
ByteArrayOutputStream baos;
MouseLocation mouseLocation;
ObjectOutputStream oos;
byte buffer[]=new byte[1024];
try{
mouseLocation=new MouseLocation((int)(ev.getX()*xScale),(int)(ev.getY()*yScale));
System.out.println((int)(ev.getX()*xScale)+","+(int)(ev.getY()*yScale));
baos=new ByteArrayOutputStream();
oos=new ObjectOutputStream(baos);
oos.writeObject(mouseLocation);
oos.flush();
buffer=baos.toByteArray();
os.write(buffer,0,buffer.length);
os.flush();
}catch(Exception e)
{
e.printStackTrace();
}
}
public void mouseDragged(MouseEvent ev)
{

}
});
}catch(Exception e)
{
e.printStackTrace();
}
}
}
 