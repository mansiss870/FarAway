package remotedesk.requestprocess;
import java.io.*;
import java.net.*;
import remotedesk.exceptions.*;
public class FileReceiver extends Thread
{
private Socket socket;
private InputStream is;
private OutputStream os;
public FileReceiver(Socket socket)
{
this.socket=socket;
}
/*public void run()
{
try{
os=socket.getOutputStream();
is=socket.getInputStream();
ByteArrayOutputStream baos;
ByteArrayInputStream bais;
FileOutputStream fos;
BufferedOutputStream bos;
File file;
byte[] header;
int headerSize;
String fileName;
ObjectInputStream ois;
int bc=0;
byte ack[]=new byte[1];
int bytesReaded;
while(true)
{
header=new byte[10];
headerSize=0;
fileName=null;
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
System.out.println("Header for FileName:"+headerSize);
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
byte[] filenamebytes=baos.toByteArray();
bais=new ByteArrayInputStream(filenamebytes);
try{
ois=new ObjectInputStream(bais);
fileName=(String)ois.readObject();
}catch(ClassNotFoundException cnfe)
{
cnfe.printStackTrace();
}
header=new byte[10];
headerSize=0;
bytesReaded=0;
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
System.out.println("Header for file:"+headerSize);
file=new File(fileName);
if(file.exists()) file.delete();
fos=new FileOutputStream(file);
bos=new BufferedOutputStream(fos);
while(bytesReaded<headerSize)
{
bc=is.read(buffer);
if(bc==-1) continue;
os.write(ack,0,1);
os.flush();
bos.write(buffer,0,bc);
bos.flush();
bytesReaded=bytesReaded+bc;
}
System.out.println("File Received:"+fileName);
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
*/}