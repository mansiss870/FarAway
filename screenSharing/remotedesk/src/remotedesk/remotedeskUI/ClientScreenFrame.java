package remotedesk.remotedeskUI;
import remotedesk.exceptions.*;
//import remotedesk.requestprocess.*;
import javax.swing.*;
import java.awt.*;
public class ClientScreenFrame extends JFrame
{ 
public final static JFrame CSFrame=new JFrame();
public final static JLabel CSLabel=new JLabel();
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
public ClientScreenFrame()
{ 
CSFrame.setSize(1300,700);
CSFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
CSLabel.setSize(1300,700-35);
CSFrame.setVisible(true);
}
public static JFrame getCSFrame()
{
return CSFrame;
}
public static JLabel getCSLabel()
{
return CSLabel;
}
}