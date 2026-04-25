import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;

public class Calculator extends JPanel implements MouseListener, KeyListener {
   public JFrame frame = new JFrame("EveryCalc");
   public SystemTray systemTray;
   public TrayIcon tIcon;
   public PopupMenu ppMenu;
   public static JMenuItem[] basicMathItems = {new JMenuItem("Addition"), new JMenuItem("Subtraction"), new JMenuItem("Multiplication"), new JMenuItem("Division"), new JMenuItem("Square Root"), new JMenuItem("Exponents")};
   public static JMenuItem[] algebraItems = {new JMenuItem("Logarithm -> Exponential"), new JMenuItem("Exponential -> Logarithm"), new JMenuItem("Evaluate With i"), new JMenuItem("Square Root Negative")};
   public static JMenuItem[] chemItems = {new JMenuItem("Liters -> Moles"), new JMenuItem("Moles -> Liters"), new JMenuItem("Mass -> Moles"), new JMenuItem("Moles -> Mass"), new JMenuItem("Q = mc∆T: Find Q"), new JMenuItem("Q = mc∆T: Find m"), new JMenuItem("Q = mc∆T: Find c"), new JMenuItem("Q = mc∆T: find ∆T")};

   public static void main(String[] args) {
      JOptionPane.showMessageDialog((Component)null, "Welcome To The Everything Calculator! This Calculator Provides Algebra, Chemistry, And Lots Of Other Calculations\n That Are Normally Spread Across Different Websites!", "Welcome.", JOptionPane.INFORMATION_MESSAGE);
      new Calculator();
   }

   public Calculator() {
      System.setProperty("apple.awt.application.name", "EveryCalc");
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      this.frame.setUndecorated(false);
      this.frame.setResizable(true);
      this.addMouseListener(this);
      this.addKeyListener(this);
      this.frame.add(this);
      this.frame.setVisible(true);
      this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      this.frame.setSize(screenSize.width / 2, screenSize.height / 2);
      this.frame.setLocation(screenSize.width / 4, screenSize.height / 4);
      this.frame.setLayout(null);
      JTextArea ta = new JTextArea("""
            Welcome! Use The OS Native Menus To Navigate. On Windows, It'll Most Likely Be The TaskBar. On MacOS,\n
            You Just Look At The Top And See Them. On Linux, It'll Be Whatever Global Panel You Have.
            """);
      ta.setEditable(false);
      ta.setOpaque(true);
      ta.setBounds(10, 10, screenSize.width - 10, screenSize.height - 10);
      frame.add(ta);
      JMenuBar jmb = new JMenuBar();
      frame.setJMenuBar(jmb);
      JMenu about = new JMenu("About");
      JMenuItem jmi1 = new JMenuItem("About EveryCalc");
      jmi1.addActionListener(e -> AboutEveryCalc.run());
      about.add(jmi1);
      JMenu jm = new JMenu("Basic Math");
      JMenu jm2 = new JMenu("Algebra");
      JMenu jm3 = new JMenu("Chemistry");
      jmb.add(about);
      jmb.add(jm);
      jmb.add(jm2);
      jmb.add(jm3);
      for(JMenuItem jmi : basicMathItems) {
         jm.add(jmi);
         jmi.addActionListener(e -> System.out.println("HERE"));
      }
      for(JMenuItem jmi : algebraItems) {
         jm2.add(jmi);
         jmi.addActionListener(e -> System.out.println("THERE"));
      }
      for(JMenuItem jmi : chemItems) {
         jm3.add(jmi);
         jmi.addActionListener(e -> System.out.println("WHERE"));
      }
      // if (SystemTray.isSupported()) {
      //    this.systemTray = SystemTray.getSystemTray();
      //    URL url = Calculator.class.getResource("TrayIcon.png");
      //    Image image = Toolkit.getDefaultToolkit().getImage(url);
      //    this.ppMenu = new PopupMenu("EveryCalc");
      //    MenuItem mItem = new MenuItem("About EveryCalc");
      //    mItem.addActionListener((e) -> {
      //       AboutEveryCalc.run();
      //    });
      //    this.ppMenu.add(mItem);
      //    this.tIcon = new TrayIcon(image, "EveryCalc", this.ppMenu);

      //    try {
      //       this.systemTray.add(this.tIcon);
      //    } catch (AWTException err) {
      //       JOptionPane.showMessageDialog((Component)null, "Error: " + err.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
      //    }
      // }

   }
   public void addition() {}
   public void subtraction() {}
   public void multiplication() {}
   public void division() {}
   public void sqrt() {}
   public void atob() {}

   public void mousePressed(MouseEvent evt) {}

   public void mouseReleased(MouseEvent evt) {}

   public void mouseClicked(MouseEvent evt) {}

   public void mouseEntered(MouseEvent evt) {}

   public void mouseExited(MouseEvent evt) {}

   public void keyPressed(KeyEvent evt) {
   }

   public void keyReleased(KeyEvent evt) {
   }

   public void keyTyped(KeyEvent evt) {
   }
}
class AboutEveryCalc extends JPanel {
   public JFrame frame = new JFrame("About EveryCalc");

   public static void run() {
      new AboutEveryCalc();
   }

   public AboutEveryCalc() {
      this.frame.setUndecorated(false);
      this.frame.setResizable(false);
      this.frame.add(this);
      this.frame.setVisible(true);
      this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      this.frame.setSize(screenSize.width / 3, screenSize.height / 2);
      this.frame.setLocation(screenSize.width / 6, screenSize.height / 6);
      this.frame.setLayout(null);
      JTextArea ta = new JTextArea("""
            EveryCalc, Or EverythingCalculator Is A Free, Open-Source Calculator That\n
            Helps With Complex Things Normal Calculators Can't Do. From Chemistry\n
            To Algebra, It Can Help With Basically Anything.\n\n\n
            Credits:\n
            Main Programmer: IDEKanymoreTBH On Github\n
            Graphic Design Lead: Also IDEKanymoreTBH On Github\n
            Packages Used: AWT, Swing\n
            Codebase: https://github.com/IDEKanymoreTBH/EveryCalc \n\n
            Alright, Now Go Use The Calculator...
            """);
      ta.setEditable(false);
      ta.setOpaque(true);
      ta.setBounds(10, 10, screenSize.width / 3, screenSize.height / 2);
      frame.add(ta);
   }
}
