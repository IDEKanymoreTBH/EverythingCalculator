import java.awt.*;
import java.awt.event.*;
import java.util.prefs.Preferences;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.*;
public class Calculator extends JPanel implements MouseListener, KeyListener {
   public JFrame frame = new JFrame("EveryCalc");
   public SystemTray systemTray;
   public TrayIcon tIcon;
   public PopupMenu ppMenu;
   public static Options options = new Options();
   public static Preferences prefs = Preferences.userNodeForPackage(Calculator.class);
   public static HashMap<String, String> properties = new HashMap<>();
   public static JMenuItem[] basicMathItems = {new JMenuItem("Addition"), new JMenuItem("Subtraction"), new JMenuItem("Multiplication"), new JMenuItem("Division"), new JMenuItem("Square Root"), new JMenuItem("Exponents")};
   public static JMenuItem[] algebraItems = {new JMenuItem("Logarithm -> Exponential"), new JMenuItem("Exponential -> Logarithm"), new JMenuItem("Evaluate With i"), new JMenuItem("Square Root Negative")};
   public static JMenuItem[] chemItems = {new JMenuItem("Liters -> Moles"), new JMenuItem("Moles -> Liters"), new JMenuItem("Mass -> Moles"), new JMenuItem("Moles -> Mass"), new JMenuItem("Q = mc∆T: Find Q"), new JMenuItem("Q = mc∆T: Find m"), new JMenuItem("Q = mc∆T: Find c"), new JMenuItem("Q = mc∆T: find ∆T")};
   public static JTextArea currentThing = null;

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new Calculator();
         }
      });
   }

   public Calculator() {
      properties.put("Calculator.PreventOverflow", prefs.get("Calculator.PreventOverflow", "false"));
      System.setProperty("apple.awt.application.name", "EveryCalc");
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      this.frame.setUndecorated(false);
      this.frame.setResizable(false);
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
      ta.setBounds(10, 10, frame.getWidth() - 20, frame.getHeight() - 50);
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
      JMenu jm4 = new JMenu("Options");
      JMenuItem options = new JMenuItem("Show Options");
      options.addActionListener(e -> options());
      jm4.add(options);
      jmb.add(about);
      jmb.add(jm);
      jmb.add(jm2);
      jmb.add(jm3);
      jmb.add(jm4);
      for(JMenuItem jmi : basicMathItems) {
         ActionListener al = null;
         switch(jmi.getText()) {
            case "Addition":
               al = (e -> addition());
               break;
            case "Subtraction":
               al = (e -> subtraction());
               break;
            case "Multiplication":
               al = (e -> multiplication());
               break;
            case "Division":
               al = (e -> division());
               break;
            case "Square Root":
               al = (e -> sqrt());
               break;
            case "Exponents":
               al = (e -> atob());
               break;
            default:
               al = (e -> JOptionPane.showMessageDialog(null, "Something Went Wrong!", "Error!", JOptionPane.ERROR_MESSAGE));
               break;
         }
         jm.add(jmi);
         jmi.addActionListener(al);
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
   public void addition() {
      frame.getContentPane().removeAll();
      frame.revalidate();
      frame.repaint();
      JTextArea jta = new JTextArea("This Is The Addition Place. Type In A Valid Addition Equation.\nDon't Worry About Negatives. Just Type 9223372036854775807 + 1 As Long As 'Prevent Overflow' Isn't On ;)");
      jta.setLineWrap(true);
      jta.setBounds(10, 10, frame.getWidth() - 20, frame.getHeight() - 50);
      jta.addKeyListener(this);
      frame.add(jta);
      currentThing = jta;
   }
   public void subtraction() {
      System.out.println("Sub");
   }
   public void multiplication() {
      System.out.println("Mult");
   }
   public void division() {
      System.out.println("Div");
   }
   public void sqrt() {
      System.out.println("SQRT");
   }
   public void atob() {
      System.out.println("Exp");
   }
   public void options() {
      options.showOptions();
   }
   public static void updatePrefs() {
      for(int i = 0; i < properties.size(); i++) {
         System.out.println("Key: " + properties.keySet().toArray()[i].toString());
         System.out.println("Value: " + properties.get(properties.keySet().toArray()[i].toString()));
         prefs.put(properties.keySet().toArray()[i].toString(), properties.get(properties.keySet().toArray()[i].toString()));
      }
   }
   public void mousePressed(MouseEvent evt) {}

   public void mouseReleased(MouseEvent evt) {}

   public void mouseClicked(MouseEvent evt) {}

   public void mouseEntered(MouseEvent evt) {}

   public void mouseExited(MouseEvent evt) {}

   public void keyPressed(KeyEvent evt) {}

   public void keyReleased(KeyEvent evt) {}

   public void keyTyped(KeyEvent evt) {
      if(((Character)evt.getKeyChar()).equals('\n')) {
         frame.requestFocus();
         try {
            MathUtils.interpretAdditionEquation(currentThing.getText());
         } catch(InvalidEquationException err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), "Result:", JOptionPane.INFORMATION_MESSAGE);
         }
      }
   }
}
class AboutEveryCalc extends JPanel {
   public JFrame frame = new JFrame("About EveryCalc");

   public static void run() {
      new AboutEveryCalc();
   }

   public AboutEveryCalc() {
      frame.setUndecorated(false);
      frame.setResizable(false);
      frame.add(this);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setSize(screenSize.width / 3, screenSize.height / 2);
      frame.setLocation(screenSize.width / 6, screenSize.height / 6);
      frame.setLayout(null);
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
class MathUtils {
   public static void interpretAdditionEquation(String equation) throws InvalidEquationException {
      String temp = equation.replace("\n", "").replace(" ", "").replace("\t", "");
      boolean isPlusFirst;
      try {
         isPlusFirst = ((Character)(temp.toCharArray()[0])).equals('+');
      } catch(ArrayIndexOutOfBoundsException err) {
         throw new InvalidEquationException("Error: Your Equation Is Non-Existent.");
      }
      boolean isInvalidCharacter = false;
      for(int i = 0; i < temp.toCharArray().length; i++) {
         if(!(Character.isDigit(temp.toCharArray()[i]) || ((Character)(temp.toCharArray()[i])).equals('+'))) {
            isInvalidCharacter = true;
            break;
         }
      }
      if(isPlusFirst) {
         throw new InvalidEquationException("Error: Your Equation Starts With A Plus, Which Is Not Allowed.");
      } else if(isInvalidCharacter) {
         throw new InvalidEquationException("Error: Your Equation Contains An Invalid Character.");
      }
      //Literally Just Learned About This BTW.
      try {
         long result = Arrays.stream(temp.split("\\+")).mapToLong(Long::parseLong).sum();
         //If The Prevent Overflow Option Is Selected, Check If The Equation Overflows
         if(Boolean.parseBoolean(Calculator.properties.get("Calculator.PreventOverflow"))) {
            long[] array = Arrays.stream(temp.split("\\+")).mapToLong(Long::parseLong).toArray();
            long lo = 0;
            for(int i = 0; i < array.length; i++) {
               try {
                  lo = Math.addExact(lo, array[i]);
               } catch(ArithmeticException err) {
                  throw new InvalidEquationException("Because Of Your Settings, This Equation Overflowed. Turn Off 'Prevent Overflow' To See Overflow Value.");
               }
            }
         }
         JOptionPane.showMessageDialog(null, "The Result Of Your Addition Is: " + Long.toString(result), "Result", JOptionPane.INFORMATION_MESSAGE);
      } catch(NumberFormatException err) {
         throw new InvalidEquationException("Error: A Number In This Equation Is Invalid.");
      }
   }
}
class Options extends JPanel {
   public JFrame frame = null;
   public void showOptions() {
      frame = new JFrame("Options");
      frame.setUndecorated(false);
      frame.setResizable(false);
      frame.add(this);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 3, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
      frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 6, Toolkit.getDefaultToolkit().getScreenSize().height / 4);
      frame.setLayout(null);
      JCheckBox jcb = new JCheckBox("Prevent Number Overflow");
      jcb.setSelected(Boolean.parseBoolean(Calculator.properties.get("Calculator.PreventOverflow")));
      jcb.addActionListener(e -> {
         if(jcb.isSelected()) {
            System.out.println("Enabled!");
            Calculator.properties.put("Calculator.PreventOverflow", "true");
            Calculator.updatePrefs();
         } else {
            System.out.println("Disabled!");
            Calculator.properties.put("Calculator.PreventOverflow", "false");
            Calculator.updatePrefs();
         }
      });
      jcb.setBounds(10, 10, frame.getWidth() - (frame.getWidth() - 20), frame.getHeight() - (frame.getHeight() - 20));
      frame.add(jcb);
   }
}