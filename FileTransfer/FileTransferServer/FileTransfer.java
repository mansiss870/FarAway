import java.net.*;
import java.io.*;
class FileTransfer extends Thread
{
Socket socket;
FileTransfer(Socket socket)
{
this.socket=socket;
start();
}



//--------------------------------------------------------------------------------------------------------------------------




public void run()
{
try{
OutputStream outputStream=socket.getOutputStream();
InputStream inputStream=socket.getInputStream();
byte ack[]=new byte[2];
ack[0]=65;
ack[1]=66;
int m=-1;
byte b[]=new byte[8];
while(m==-1) m=inputStream.read(b);
m=-1;
if(b[0]==0) //download
{
System.out.println("Request for FileDownload");
byte[] bytes=new byte[1024];
while(m==-1) m=inputStream.read(bytes); //something needs tobe improve here
String fileName=new String(bytes).trim();
System.out.println("Request for FileDownload:"+fileName);
m=-1;
outputStream.write(ack);
outputStream.flush();
//-----------------------------------------------------------------------------------------------------------------
File file=new File(fileName);
if(file.isDirectory()==true)
{
fileName=file.getName()+".zip";
TMZipUtility.zipIt(file,fileName);
outputStream.write(fileName.getBytes());
outputStream.flush();
System.out.println("folder compressed");
}
else
{
outputStream.write(file.getName().getBytes());
outputStream.flush();
}
// code to upload the zip file starts here
File fileToUpload=new File(fileName);
long lengthOfFile=fileToUpload.length();
bytes=TMUtility.longToBytes(lengthOfFile);
outputStream.write(bytes);
outputStream.flush();
int i=-1;
while(i==-1) i=inputStream.read(ack);
int bufferSize=1024;
int numberOfBytesToWrite=bufferSize;
FileInputStream fileInputStream;
fileInputStream=new FileInputStream(fileToUpload);
BufferedInputStream bis=new BufferedInputStream(fileInputStream);
byte contents[]=new byte[1024];
int bytesRead;
i=0;
while(i<lengthOfFile)
{
bytesRead=bis.read(contents);
if(bytesRead<0) break;
outputStream.write(contents,0,bytesRead);
outputStream.flush();
m=-1;
while(m==-1) m=inputStream.read(ack);
i=i+bytesRead;
}
fileInputStream.close();
if(file.isDirectory()==true) 
{
File f=new File(fileName);
f.delete();
System.out.println(fileName+": deleted");
}
System.out.println("File: "+fileName+"sent");
socket.close();
}


//------------------------------------------------------------------------------------------------------------------------



if(b[0]==1) //upload
{
m=-1;
System.out.println("Request for FileUpload");
byte[] bytes=new byte[1024];
while(m==-1) m=inputStream.read(bytes); //something needs tobe improve here
String dst=new String(bytes).trim();
m=-1;
bytes=new byte[1024];
while(m==-1) m=inputStream.read(bytes); //something needs tobe improve here
String fileName=new String(bytes).trim();
outputStream.write(ack);
outputStream.flush();
m=-1;
while(m==-1) m=inputStream.read(bytes);
outputStream.write(ack);
outputStream.flush();
long lengthOfFile=TMUtility.bytesToLong(bytes,0,63);
m=-1;
FileOutputStream fileOutputStream;
fileOutputStream=new FileOutputStream(new File("farAwayDownloads"+File.separator+fileName));
BufferedOutputStream bos=new BufferedOutputStream(fileOutputStream);
//outputStream.write(ack);
//outputStream.flush();
int i=0;
int bytesRead;
while(true)
{
while(true)
{
bytesRead=inputStream.read(bytes);
if(bytesRead!=-1) break;
}
i=i+bytesRead;
bos.write(bytes,0,bytesRead);
bos.flush();
outputStream.write(ack);
outputStream.flush();
if(i==lengthOfFile) break;
}
fileOutputStream.close();
System.out.println("File received:"+fileName);
if(fileName.endsWith(".zip"))
{
i=fileName.length();
File f=new File("farAwayDownloads"+File.separator+fileName.substring(0,i-4));
f.mkdir();
TMZipUtility.unzipIt("farAwayDownloads"+File.separator+fileName,dst+File.separator+fileName.substring(0,i-4));
System.out.println("Folder created: "+dst+File.separator+fileName.substring(0,i-4));
new File("farAwayDownloads"+File.separator+fileName).delete();
}
}
socket.close();
}catch(Exception e)
{
e.printStackTrace();
}
}
}