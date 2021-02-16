import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

class FTFrame extends JFrame {
   private FTPanel ftPanel;
   private Container container;

   public FTFrame() {
      this.initComponent();
      this.setAppearance();
   }

   void initComponent() {
      try {
         Dimension var1 = Toolkit.getDefaultToolkit().getScreenSize();
         this.container = this.getContentPane();
         this.container.setLayout((LayoutManager)null);
         this.ftPanel = new FTPanel();
         this.ftPanel.setBounds(1, 1,1007, 658);
this.ftPanel.setBackground(new Color(119,104,100));
         this.container.add(this.ftPanel);
         this.setSize(1025, 700);
         this.setDefaultCloseOperation(3);
         this.setLocation(var1.width / 2 - this.getWidth() / 2, var1.height / 2 - this.getHeight() / 2);
         this.setVisible(true);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   void setAppearance() {
Image icon = Toolkit.getDefaultToolkit().getImage("logo2.png");
    this.setIconImage(icon);
      this.setTitle("Far Away");
      this.ftPanel.setBorder(BorderFactory.createLineBorder(new Color(112, 112, 112)));
   }

   public static void main(String[] var0) {
      new FTFrame();
   }
}