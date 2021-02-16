import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;

class FTPanel extends JPanel 
{
JLabel fileTransferTitle;
JLabel transferingFilePath;   
JLabel remoteDeskPath;
JLabel deskPath;
JLabel remoteDeskLabel;
JLabel deskLabel;
JLabel remoteDesk;
JLabel thisDesk;
FTTree tree;
FTThisDeskTree fdTree;
JScrollPane remoteDeskJsp;
JScrollPane thisDeskJsp;
JProgressBar bar;
String[] roots;
int tm = 0;
int lm = 0;
Border border;
JButton download;
JButton upload;



FTPanel() throws Exception 
{
this.border = BorderFactory.createLineBorder(Color.black);
this.initComponents();
this.setBounds();
this.setAppearance();
this.addListeners();
}


   


public void initComponents() throws Exception 
{
FTTree.sendRequest("roots");
this.roots = FTTree.getListOfString("roots");
DefaultMutableTreeNode var1 = new DefaultMutableTreeNode("drives");
String[] var3 = this.roots;
int var4 = var3.length;
DefaultMutableTreeNode var2;
int var5;
String var6;
for(var5 = 0; var5 < var4; ++var5) 
{
var6 = var3[var5];
var2 = new DefaultMutableTreeNode(var6);
var2.add(new DefaultMutableTreeNode(".."));
var1.add(var2);
}
this.tree = new FTTree(var1, this);

//---------------------------------------------------------------------------------------------------------------------------

FTThisDeskTree.sendRequest("roots");
this.roots = FTThisDeskTree.getListOfString("roots");
var1 = new DefaultMutableTreeNode("drives");
var3 = this.roots;
var4 = var3.length;
for(var5 = 0; var5 < var4; ++var5) 
{
var6 = var3[var5];
var2 = new DefaultMutableTreeNode(var6);
var2.add(new DefaultMutableTreeNode(".."));
var1.add(var2);
}
this.fdTree = new FTThisDeskTree(var1, this);

//---------------------------------------------------------------------------------------------------------------------------

this.bar=new JProgressBar();
this.transferingFilePath=new JLabel(" ",SwingConstants.RIGHT);
this.fileTransferTitle = new JLabel("FILE TRANSFER",SwingConstants.CENTER);
this.thisDeskJsp = new JScrollPane(this.fdTree, 22, 30);
this.remoteDeskJsp = new JScrollPane(this.tree, 22, 30);
this.remoteDeskPath = new JLabel("");
this.deskPath = new JLabel("");
this.remoteDesk = new JLabel("REMOTE_DESK",SwingConstants.CENTER);
this.thisDesk = new JLabel("THIS_DESK",SwingConstants.CENTER);
this.remoteDeskLabel = new JLabel("Remote_Desk",SwingConstants.CENTER);
this.deskLabel = new JLabel("This_Desk",SwingConstants.CENTER);
this.download=new JButton("<<");
this.upload=new JButton(">>");
}


//---------------------------------------------------------------------------------------------------------------------------



public void setBounds() 
{
this.fileTransferTitle.setBounds(this.lm, this.tm + 10, 1007, 30);
this.fileTransferTitle.setFont(new Font("Verdana", 1, 25));
this.deskLabel.setFont(new Font("Verdana", 0, 13));
this.deskLabel.setBounds(this.lm + 10, this.tm + 50, 100, 40);
this.remoteDeskLabel.setBounds(this.lm + 10, this.tm + 95, 100, 40);
this.remoteDeskLabel.setFont(new Font("Verdana", 0, 14));
this.deskPath.setBounds(this.lm + 110, this.tm + 50, 883, 40);
this.deskPath.setFont(new Font("Verdana", 0, 15));
this.remoteDeskPath.setBounds(this.lm + 110, this.tm + 95, 883, 40);
this.remoteDeskPath.setFont(new Font("Verdana", 0, 15));
this.remoteDesk.setBounds(this.lm + 566, this.tm + 145, 426, 35);
this.remoteDesk.setFont(new Font("Verdana", 1, 15));
this.thisDesk.setBounds(this.lm + 10, this.tm + 145, 426, 35);
this.thisDesk.setFont(new Font("Verdana", 1, 15));
this.thisDeskJsp.setBounds(this.lm + 10, this.tm + 180, 486, 400);
this.download.setBounds(this.lm+506,this.tm+145,60,35);
this.upload.setBounds(this.lm+436,this.tm+145,60,35);
this.remoteDeskJsp.setBounds(this.lm + 506, this.tm + 180, 486, 400);
this.bar.setBounds(this.lm + 450, this.tm + 600, 486, 35);
this.transferingFilePath.setBounds(this.lm + 100, this.tm + 600, 300, 40);
this.transferingFilePath.setFont(new Font("Verdana", 0, 20));
}


//---------------------------------------------------------------------------------------------------------------------------



public void setAppearance() 
{
this.deskLabel.setOpaque(true);
this.remoteDeskLabel.setOpaque(true);
this.thisDesk.setOpaque(true);
this.remoteDesk.setOpaque(true);

this.deskLabel.setBackground(new Color(185,177,174));
this.remoteDeskLabel.setBackground(new Color(185,177,174));
this.thisDesk.setBackground(new Color(185,177,174));
this.remoteDesk.setBackground(new Color(185,177,174));
this.download.setEnabled(false);
this.upload.setEnabled(false);
this.bar.setVisible(false);
this.bar.setValue(0);   
this.bar.setStringPainted(true); 
this.bar.setForeground(Color.black);
this.deskPath.setBorder(this.border);
this.remoteDeskPath.setBorder(this.border);
this.deskLabel.setBorder(this.border);
this.remoteDeskLabel.setBorder(this.border);
this.deskPath.setBackground(Color.white);
this.remoteDeskPath.setBackground(Color.white);
this.deskPath.setOpaque(true);
this.remoteDeskPath.setOpaque(true);
this.thisDesk.setBorder(this.border);
this.remoteDesk.setBorder(this.border);
this.add(this.fileTransferTitle);
this.add(this.download);
this.add(this.upload);
this.add(this.remoteDeskLabel);
this.add(this.deskLabel);
this.add(this.remoteDeskPath);
this.add(this.deskPath);
this.add(this.remoteDesk);
this.add(this.thisDesk);
this.add(this.remoteDeskJsp);
this.add(this.thisDeskJsp);
this.add(transferingFilePath);
this.add(bar);      
this.setLayout((LayoutManager)null);
this.setVisible(true);
}



//---------------------------------------------------------------------------------------------------------------------------



public void addListeners()
{
download.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e)
{
Thread t=new Thread(()->{tree.download(remoteDeskPath.getText().trim(),deskPath.getText().trim());
});
t.start();
setEnabledButtons(false);
}
});
upload.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e)
{
Thread t=new Thread(()->{fdTree.upload(deskPath.getText().trim(),remoteDeskPath.getText().trim());
});
t.start();
setEnabledButtons(false);
}
});
}



//---------------------------------------------------------------------------------------------------------------------------




public void updateRemoteDeskPath(String var1) 
{
this.remoteDeskPath.setText(var1);
}

public void updateThisDeskPath(String var1) 
{
this.deskPath.setText(var1);
}


//---------------------------------------------------------------------------------------------------------------------------



public void setVisibleBar(boolean op)
{
SwingUtilities.invokeLater(()->{
this.bar.setVisible(op);
this.transferingFilePath.setVisible(op);
});
}


//---------------------------------------------------------------------------------------------------------------------------


public void fillBar(long size,int receivedBytes)
{
int s=(int)size;
bar.setValue((receivedBytes/s)*100);  
}


//---------------------------------------------------------------------------------------------------------------------------


public void setTransferingFilePath(String path)
{
SwingUtilities.invokeLater(()->{
transferingFilePath.setText(path);
});
}


//---------------------------------------------------------------------------------------------------------------------------


public boolean setEnabledButtons(boolean op)
{
if(op)
{
String localDesk=deskPath.getText();
String remoteDesk=remoteDeskPath.getText();
if(!(localDesk.equals("C:\\")) && localDesk.length()!=0 && !(remoteDesk.equals("C:\\")) && remoteDesk.length()!=0)
{
this.download.setEnabled(true);
this.upload.setEnabled(true);
return true;
}
else
{
return false;
}
}else
{
this.download.setEnabled(false);
this.upload.setEnabled(false);
return true;
}
}


//---------------------------------------------------------------------------------------------------------------------------




}   