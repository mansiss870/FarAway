import java.awt.Font;
import java.io.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

class FTThisDeskTree extends JTree implements TreeSelectionListener, TreeWillExpandListener 
{
DefaultMutableTreeNode mm;
DefaultTreeCellRenderer renderer;
Icon closedIcon;
Icon openIcon;
Icon leafIcon;
int expandtree;
FTPanel panel;

FTThisDeskTree(DefaultMutableTreeNode var1, FTPanel var2) {
super(var1);
this.panel = var2;
      this.expandtree = 0;
      this.addTreeWillExpandListener(this);
      this.renderer = (DefaultTreeCellRenderer)this.getCellRenderer();
      this.closedIcon = new ImageIcon("folder.png");
      this.openIcon = new ImageIcon("folder.png");
      this.leafIcon = new ImageIcon("page.png");
      this.renderer.setClosedIcon(this.closedIcon);
      this.renderer.setOpenIcon(this.openIcon);
      this.renderer.setLeafIcon(this.leafIcon);
      this.renderer.setFont(new Font("Verdana", 0, 25));
      this.setRowHeight(50);
      this.addTreeSelectionListener(this);
   }

public void valueChanged(TreeSelectionEvent var1) 
{
JTree var2 = (JTree)var1.getSource();
DefaultMutableTreeNode var3 = (DefaultMutableTreeNode)var2.getLastSelectedPathComponent();
TreePath var4 = var1.getPath();
if (!var4.toString().equals("[drives]")) 
{
if (var3 != null) 
{
panel.setEnabledButtons(true);
this.updateTree(var3, var4.toString());
this.expandtree = 1;
}
}
}

public void treeWillCollapse(TreeExpansionEvent var1) throws ExpandVetoException {
JTree var2 = (JTree)var1.getSource();
      DefaultMutableTreeNode var3 = (DefaultMutableTreeNode)var2.getLastSelectedPathComponent();
      TreePath var4 = var1.getPath();
      String var5 = var4.toString();
      int var6 = var5.length();
      int var7 = var6--;
      String var8 = var5.substring(9, var7 - 1).replaceAll(",\\s+", "\\\\");
      SwingUtilities.invokeLater(() -> {
         this.panel.updateThisDeskPath("  "+var8);
      });
   }

   public void treeWillExpand(TreeExpansionEvent var1) throws ExpandVetoException {
      if (this.expandtree == 0) {
         throw new ExpandVetoException(var1);
      } else {
         this.expandtree = 0;
      }
   }

   public void updateTree(DefaultMutableTreeNode var1, String var2) {
      try {
         byte[] var4 = new byte[8];
         int var5 = var2.length();
         int var6 = var5--;
         String var7 = var2.substring(9, var6 - 1).replaceAll(",\\s+", "\\\\");
         SwingUtilities.invokeLater(() -> {
            this.panel.updateThisDeskPath("  "+var7);
         });
         var4 = sendRequest(var7);
         if (var4[0] == 0) {
            return;
         }

         var1.remove(0);
         String[] var8 = getListOfString(var7);
         String[] var9 = var8;
         int var10 = var8.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            String var12 = var9[var11];
            DefaultMutableTreeNode var3;
            if (var12.endsWith("#")) {
               var3 = new DefaultMutableTreeNode(var12.substring(0, var12.length() - 1));
            } else {
               var3 = new DefaultMutableTreeNode(var12);
               DefaultMutableTreeNode var13 = new DefaultMutableTreeNode("..");
               var3.add(var13);
            }

            var1.add(var3);
         }
      } catch (Exception var14) {
         var14.printStackTrace();
      }

   }

   public static byte[] sendRequest(String var0) throws Exception {
      byte[] var1 = new byte[8];
      File var2 = new File(var0);
      if (var2.isDirectory()) {
         var1[0] = 1;
         return var1;
      } else {
         var1[0] = 0;
         return var1;
      }
   }

   public static String[] getListOfString(String var0) throws Exception {
      String[] var1 = null;
      int var6;
      if (!var0.equals("roots")) {
         File var9 = new File(var0);
         File[] var10 = var9.listFiles();
         var1 = new String[var10.length];
         int var11 = 0;
         File[] var12 = var10;
         var6 = var10.length;

         for(int var13 = 0; var13 < var6; ++var13) {
            File var8 = var12[var13];
            if (var8.isDirectory()) {
               var1[var11] = var8.getName();
            } else {
               var1[var11] = var8.getName() + "#";
            }

            ++var11;
         }

         return var1;
      } else {
         File[] var2 = File.listRoots();
         var1 = new String[var2.length];
         int var3 = 0;
         File[] var4 = var2;
         int var5 = var2.length;

         for(var6 = 0; var6 < var5; ++var6) {
            File var7 = var4[var6];
            var1[var3] = var7.getPath();
            System.out.println(var1[var3]);
            ++var3;
         }

         return var1;
      }
   }

public void upload(String path,String dst)
{
try{
String fileName=TrimUtility.trim(path);
Socket socket=new Socket("localhost",8000);
OutputStream outputStream=socket.getOutputStream();
InputStream inputStream=socket.getInputStream();
byte ack[]=new byte[2];
ack[0]=78;
ack[1]=56;
byte b[]=new byte[8];
b[0]=1;
System.out.println("dst:"+dst+" path "+fileName+" "+path);
outputStream.write(b,0,8);
outputStream.flush();
byte[] bytes=new byte[1024];
File file=new File(path);
fileName=file.getName();
panel.setVisibleBar(true);
panel.setTransferingFilePath(fileName);
File fileToUpload=new File(" ");
int m=-1;
outputStream.write(dst.getBytes());
outputStream.flush();
if(file.isDirectory()==true)
{
fileName=fileName+".zip";
outputStream.write(fileName.getBytes());
outputStream.flush();
m=-1;
while(m==-1) m=inputStream.read(ack);
System.out.println("ack received");
TMZipUtility.zipIt(file,fileName);
System.out.println("folder compressed");
fileToUpload=new File(fileName);
}
else
{
outputStream.write(fileName.getBytes());
outputStream.flush();
m=-1;
while(m==-1) m=inputStream.read(ack);
fileToUpload=new File(path);
}
// code to upload the zip file starts here
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
panel.fillBar(lengthOfFile,i);
}
panel.fillBar(lengthOfFile,i);
fileInputStream.close();
if(file.isDirectory()==true) 
{
File f=new File(fileName);
f.delete();
System.out.println(fileName+": deleted");
}
socket.close();
Thread.sleep(3000);
panel.setVisibleBar(false);
}catch(Exception e)
{
e.printStackTrace();
}
}
}