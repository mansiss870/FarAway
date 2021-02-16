package remotedesk.requestprocess;
import remotedesk.exceptions.*;
import remotedesk.remotedeskUI.*;
import rdProject.utilities.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class KeyPressActions extends Thread
{
Socket socket;
public KeyPressActions(Socket socket)
{
this.socket=socket;
this.start();
}
public void run()
{
try{
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
JLabel csLabel=ClientScreenFrame.getCSLabel();
JFrame csFrame=ClientScreenFrame.getCSFrame();

csFrame.addKeyListener(new KeyListener(){
public void keyPressed(KeyEvent e) 
{  
try{
byte b[]=new byte[8];
b[0]=0;
b[1]=(byte)e.getKeyCode();
System.out.println(e.getKeyCode()+" pressed");
os.write(b,0,8);
os.flush();
}catch(Exception ee)
{
ee.printStackTrace();
}
}  
public void keyReleased(KeyEvent e) 
{
try{
byte b[]=new byte[8];
b[0]=1;
b[1]=(byte)e.getKeyCode();
System.out.println(e.getKeyCode()+" relea");
os.write(b,0,8);
os.flush();
}catch(Exception ek)
{
ek.printStackTrace();
}
}  
public void keyTyped(KeyEvent e) 
{  
}  
});
}catch(Exception e)
{
e.printStackTrace();
}
}
}