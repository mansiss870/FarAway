import java.awt.Component;
import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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

class FTTree extends JTree implements TreeSelectionListener, TreeWillExpandListener {
   DefaultMutableTreeNode mm;
   DefaultTreeCellRenderer renderer;
   Icon closedIcon;
   Icon openIcon;
   Icon leafIcon;
   int expandtree;
   FTPanel panel;

   FTTree(DefaultMutableTreeNode var1, FTPanel var2) {
      super(var1);
      System.out.println("Thread " + Thread.currentThread().getName() + " tree constructor is running");
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
this.panel.updateRemoteDeskPath("  "+var8);
});
}

public void treeWillExpand(TreeExpansionEvent var1) throws ExpandVetoException {
if (this.expandtree == 0) 
{
System.out.println("Thread " + Thread.currentThread().getName() + " expand is running");         
throw new ExpandVetoException(var1);
} else 
{
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
            this.panel.updateRemoteDeskPath("  "+var7);
         });
         var4 = sendRequest(var7);
         if (var4[0] == 0) {
            return;
         }

System.out.println("Thread " + Thread.currentThread().getName() + " update is running");
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
this.expandtree=0;
      } catch (Exception var14) {
         var14.printStackTrace();
      }

   }

   public static byte[] sendRequest(String var0) throws Exception {
      Socket var1 = new Socket("localhost", 7000);
      OutputStream var2 = var1.getOutputStream();
      InputStream var3 = var1.getInputStream();
      byte[] var4 = new byte[8];
      var4[0] = 1;
      var2.write(var4);
      var2.flush();
      var2.write(var0.getBytes());
      var2.flush();

      for(int var5 = -1; var5 == -1; var5 = var3.read(var4)) {
      }

      var1.close();
      return var4;
   }

   public static String[] getListOfString(String var0) throws Exception {
      Socket var1 = new Socket("localhost", 7000);
      OutputStream var2 = var1.getOutputStream();
      InputStream var3 = var1.getInputStream();
      byte[] var4 = new byte[8];
      var4[1] = 1;
      var2.write(var4);
      var2.flush();
      var2.write(var0.getBytes());
      var2.flush();
      byte[] var5 = new byte[1024];
      ByteArrayOutputStream var7 = new ByteArrayOutputStream();
      boolean var9 = true;
      byte[] var10 = new byte[1];

      while(true) {
         int var12 = var3.read(var5);
         if (var12 <= 0) {
            ByteArrayInputStream var8 = new ByteArrayInputStream(var7.toByteArray());
            ObjectInputStream var6 = new ObjectInputStream(var8);
            String[] var11 = (String[])((String[])var6.readObject());
            var1.close();
            return var11;
         }

         var7.write(var5, 0, var12);
         var7.flush();
         var2.write(var10, 0, 1);
         var2.flush();
      }
   }
public void download(String path,String dst)
{
try{
String fileName=TrimUtility.trim(path);
Socket socket=new Socket("localhost",8000);
System.out.println(path+" socket baad");
OutputStream outputStream=socket.getOutputStream();
InputStream inputStream=socket.getInputStream();
byte ack[]=new byte[2];
ack[0]=78;
ack[1]=56;
byte b[]=new byte[8];
b[0]=0;
outputStream.write(b,0,8);
outputStream.flush();
byte[] bytes=new byte[1024];
outputStream.write(path.getBytes());
outputStream.flush();
panel.setVisibleBar(true);
panel.setTransferingFilePath(path);
int m=-1;
while(m==-1) m=inputStream.read(ack);
m=-1;
bytes=new byte[1024];
while(m==-1) m=inputStream.read(bytes);
fileName=new String(bytes).trim();
m=-1;
while(m==-1) m=inputStream.read(bytes);
outputStream.write(ack,0,1);
outputStream.flush();
long lengthOfFile=TMUtility.bytesToLong(bytes,0,63);
m=-1;
FileOutputStream fileOutputStream;
fileOutputStream=new FileOutputStream(new File("farAwayDownloads"+File.separator+fileName));
BufferedOutputStream bos=new BufferedOutputStream(fileOutputStream);
outputStream.write(ack);
outputStream.flush();
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
panel.fillBar(lengthOfFile,i);
bos.write(bytes,0,bytesRead);
bos.flush();
outputStream.write(ack);
outputStream.flush();
System.out.println("File receiving"+fileName);
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
Thread.sleep(3000);
socket.close();
panel.setVisibleBar(false);
}catch(Exception e)
{
e.printStackTrace();
}
}
}
    