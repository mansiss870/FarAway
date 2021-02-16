package remotedesk.requestprocess;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import remotedesk.exceptions.*;
import remotedesk.remotedeskUI.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
public class ScreenReceiver extends Thread
{
private Socket socket;
private OutputStream os;
private InputStream is;
private ByteArrayOutputStream baos;
private ByteArrayInputStream bais;

public ScreenReceiver(Socket socket)
{
this.socket=socket;
start();
}
public void run()
{
try{
os=socket.getOutputStream();
is=socket.getInputStream();

byte[] header;
byte[] ack=new byte[1];
int headerSize;
int bc;
int bytesReaded;
ClientScreenFrame csf=new ClientScreenFrame();
JLabel CSLabel=csf.getCSLabel();
JFrame CSFrame=csf.getCSFrame();
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
while(true)
{
headerSize=0;
header=new byte[10];
bc=0;
while(true)
{
bc=is.read(header);
if(bc!=-1) break;
}
os.write(ack,0,1);
os.flush();
for(int i=0;i<=9;++i)
{
headerSize=(headerSize*10)+header[i];
}
//System.out.println("headerSize:"+headerSize);
byte buffer[]=new byte[1024];
bytesReaded=0;
baos=new ByteArrayOutputStream();
while(bytesReaded<headerSize)
{
bc=is.read(buffer);
if(bc==-1) continue;
os.write(ack,0,1);
os.flush();
baos.write(buffer,0,bc);
baos.flush();
bytesReaded=bytesReaded+bc;
}
byte[] imageBytes=baos.toByteArray();
bais=new ByteArrayInputStream(imageBytes);



Thread t=new Thread(){
public void run()
{
try{
int newx=CSLabel.getWidth();
int newy=CSLabel.getHeight();
BufferedImage bi=ImageIO.read(bais);
BufferedImage bImage=new BufferedImage(newx,newy,bi.getType());
Graphics2D g=bImage.createGraphics();
g.drawImage(bi,0,0,newx,newy,null);
g.dispose();
CSLabel.setIcon(new ImageIcon(bImage));
CSLabel.repaint();
CSLabel.revalidate();  
CSFrame.add(CSLabel);
}catch(Exception e)
{
e.printStackTrace();
}
}
};
t.start();
}



}catch(IOException io)
{
io.printStackTrace();
try{
socket.close();
}catch(IOException ioe)
{
ioe.printStackTrace();
}
}
}
}