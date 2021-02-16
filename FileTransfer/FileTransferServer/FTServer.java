import java.net.*;
import java.io.*;
class FTServer
{
private ServerSocket ftServer;
private ServerSocket mdServer;
private int mdPortNumber=7000;
private int ftPortNumber=8000;
FTServer()
{
try{
System.out.println("Server constructor starts");
mdServer=new ServerSocket(mdPortNumber);
ftServer=new ServerSocket(ftPortNumber);
Thread md=new Thread(()->{startListeningForMD();});
Thread ft=new Thread(()->{startListeningForFT();});
md.start();
ft.start();
System.out.println("Server constructor ends");

md.join();
ft.join();
}catch(Exception e)
{
e.printStackTrace();
}
}
private void startListeningForFT()
{
System.out.println("FTServer start listening..");
Socket socket;
try{
while(true)
{
socket=ftServer.accept();
System.out.println("Request arrived for ft");
new FileTransfer(socket);
}
}catch(Exception e)
{
e.printStackTrace();
}
}
private void startListeningForMD()
{
System.out.println("Server start listening..");
Socket socket;
try{
while(true)
{
socket=mdServer.accept();
System.out.println("Request arrived");
new RequestProcessor(socket);
}
}catch(Exception e)
{
e.printStackTrace();
}
}
public static void main(String gg[])
{
FTServer ff=new FTServer();
}
}
class RequestProcessor extends Thread
{
private Socket socket;
RequestProcessor(Socket socket)
{
this.socket=socket;
start();
}
public void run()
{
try{
InputStream inputStream=socket.getInputStream();
OutputStream outputStream=socket.getOutputStream();
int m=-1;
byte b[]=new byte[8];
byte bytes[]=new byte[4096];
while(m==-1) m=inputStream.read(b);
m=-1;
while(m==-1) m=inputStream.read(bytes);
String directory=new String(bytes).trim();
System.out.println(directory);
if(b[0]==1)
{
File file=new File(directory);
if(file.isDirectory()==true)
{
b[0]=1;
outputStream.write(b);
outputStream.flush();
return;
}
b[0]=0;
outputStream.write(b);
outputStream.flush();
}
if(b[1]==1)
{
String[] files=null;
if(directory.equals("roots"))
{
File[] roots=File.listRoots();
files=new String[roots.length];
int i=0;
for(File file:roots)
{
files[i]=file.getPath();
i++;
}
byte[] ack=new byte[1];
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(files);
oos.flush();
byte oosBytes[]=baos.toByteArray();
int bufferSize=1024;
i=0;
int numberOfBytesToWrite;
m=-1;
while(i<oosBytes.length)
{
numberOfBytesToWrite=bufferSize;
if(i+bufferSize>oosBytes.length) numberOfBytesToWrite=oosBytes.length-i;
outputStream.write(oosBytes,i,numberOfBytesToWrite);
outputStream.flush();
i=i+bufferSize;
while(m==-1) m=inputStream.read(ack);
}

}
else{
File file=new File(directory);
File[] dirs=file.listFiles();
files=new String[dirs.length];
int i=0;
for(File f:dirs)
{
if(f.isDirectory()==true)
{
files[i]=f.getName();
}
else
{
files[i]=f.getName()+"#";
}
i++;
}
byte[] ack=new byte[1];
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(files);
oos.flush();
byte oosBytes[]=baos.toByteArray();
int bufferSize=1024;
i=0;
int numberOfBytesToWrite;
m=-1;
while(i<oosBytes.length)
{
numberOfBytesToWrite=bufferSize;
if(i+bufferSize>oosBytes.length) numberOfBytesToWrite=oosBytes.length-i;
outputStream.write(oosBytes,i,numberOfBytesToWrite);
outputStream.flush();
i=i+bufferSize;
while(m==-1) m=inputStream.read(ack);
}
}
}
if(b[2]==1)
{
File file=new File(directory);
String fileName=file.getName();
outputStream.write(fileName.getBytes());
outputStream.flush();
byte[] ack=new byte[1];
int bufferSize=1024;
int numberOfBytesToWrite=bufferSize;
long lengthOfFile=file.length();
FileInputStream fileInputStream;
fileInputStream=new FileInputStream(file);
BufferedInputStream bis=new BufferedInputStream(fileInputStream);
byte contents[]=new byte[1024];
int bytesRead;
int i=0;
while(i<lengthOfFile) {
bytesRead=bis.read(contents);
if(bytesRead<0) break;
outputStream.write(contents,0,bytesRead);
outputStream.flush();
m=-1;
while(m==-1) m=inputStream.read(ack);
i=i+bytesRead; 
}
fileInputStream.close();
bis.close();
System.out.println("bytes of file sent : "+i);
System.out.printf("File sent");
}
socket.close();
}catch(Exception e)
{
e.printStackTrace();
}
}
}