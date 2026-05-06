import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.prefs.Preferences;
import java.util.stream.LongStream;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
   public static String currentMode = "null";

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new Calculator();
         }
      });
   }

   public Calculator() {
      properties.put("Calculator.PreventOverflow", prefs.get("Calculator.PreventOverflow", "false"));
      properties.put("Calculator.PreventUnderflow", prefs.get("Calculator.PreventUnderflow", "false"));
      properties.put("Calculator.EngNotation", prefs.get("Calculator.EngNotation", "false"));
      properties.put("Calculator.PrecisionDecimal", prefs.get("Calculator.PrecisionDecimal", "1"));
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
         ActionListener al = null;
         al = switch(jmi.getText()) {
            case "Logarithm -> Exponential" -> (e -> logToExp());
            case "Exponential -> Logarithm" -> (e -> expToLog());
            case "Evaluate With i" -> (e -> evalI());
            case "Square Root Negative" -> (e -> sqrtI());
            default -> (e -> JOptionPane.showMessageDialog(null, "Something Went Wrong!", "Error!", JOptionPane.ERROR_MESSAGE));
         };
         jmi.addActionListener(al);
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
      currentMode = "ADD";
   }
   public void subtraction() {
      frame.getContentPane().removeAll();
      frame.revalidate();
      frame.repaint();
      JTextArea jta = new JTextArea("This Is The Subtraction Place. Type In A Valid Addition Equation.\nMust Type 0 - 1 If Wanting -1 Because My Code (And Java Streams) Suck.");
      jta.setLineWrap(true);
      jta.setBounds(10, 10, frame.getWidth() - 20, frame.getHeight() - 50);
      jta.addKeyListener(this);
      frame.add(jta);
      currentThing = jta;
      currentMode = "SUB";
   }
   public void multiplication() {
      frame.getContentPane().removeAll();
      frame.revalidate();
      frame.repaint();
      JTextArea jta = new JTextArea("This Is Multiplication. Yeah... IDK What To Say TBH");
      jta.setLineWrap(true);
      jta.setBounds(10, 10, frame.getWidth() - 20, frame.getHeight() - 50);
      jta.addKeyListener(this);
      frame.add(jta);
      currentThing = jta;
      currentMode = "MULT";
   }
   public void division() {
      frame.getContentPane().removeAll();
      frame.revalidate();
      frame.repaint();
      JTextArea jta = new JTextArea("This Is Division. Divide By WHOLE Numbers Please.");
      jta.setLineWrap(true);
      jta.setBounds(10, 10, frame.getWidth() - 20, frame.getHeight() - 50);
      jta.addKeyListener(this);
      frame.add(jta);
      currentThing = jta;
      currentMode = "DIV";
   }
   public void sqrt() {
      frame.getContentPane().removeAll();
      frame.revalidate();
      frame.repaint();
      JTextArea jta = new JTextArea("Enter Exactly ONE Number, And You'll Get It's Square Root.");
      jta.setLineWrap(true);
      jta.setBounds(10, 10, frame.getWidth() - 20, frame.getHeight() - 50);
      jta.addKeyListener(this);
      frame.add(jta);
      currentThing = jta;
      currentMode = "SQRT";
   }
   public void atob() {
      frame.getContentPane().removeAll();
      frame.revalidate();
      frame.repaint();
      JTextArea jta = new JTextArea("Enter The Base Number, Then A Comma, Then The Exponent");
      jta.setLineWrap(true);
      jta.setBounds(10, 10, frame.getWidth() - 20, frame.getHeight() - 50);
      jta.addKeyListener(this);
      frame.add(jta);
      currentThing = jta;
      currentMode = "EXP";
   }
   public void logToExp() {
      frame.getContentPane().removeAll();
      frame.revalidate();
      frame.repaint();
      JTextArea jta = new JTextArea("Enter A Logarithmic Equation, And Get Back An Exponential Equivalent. Use _ To Signify That The Next Number Is The Base, Or Just Do () For Base 10. Example: Log_2(32) = 5.");
      jta.setLineWrap(true);
      jta.setBounds(10, 10, frame.getWidth() - 20, frame.getHeight() - 50);
      jta.addKeyListener(this);
      frame.add(jta);
      currentThing = jta;
      currentMode = "LOGEXP";
   }
   public void expToLog() {}
   public void evalI() {}
   public void sqrtI() {}
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
         switch(currentMode) {
            case "ADD":
               try {
                  MathUtils.interpretAdditionEquation(currentThing.getText());
               } catch(InvalidEquationException err) {
                  JOptionPane.showMessageDialog(null, err.getMessage(), "Result", JOptionPane.ERROR_MESSAGE);
               }
               break;
            case "SUB":
               try {
                  MathUtils.interpretSubtractionEquation(currentThing.getText());
               } catch(InvalidEquationException err) {
                  JOptionPane.showMessageDialog(null, err.getMessage(), "Result", JOptionPane.ERROR_MESSAGE);
               }
               break;
            case "MULT":
               try {
                  MathUtils.interpretMultEquation(currentThing.getText());
               } catch(InvalidEquationException err) {
                  JOptionPane.showMessageDialog(null, err.getMessage(), "Result", JOptionPane.ERROR_MESSAGE);
               }
               break;
            case "DIV":
               try {
                  MathUtils.interpretDivisionEquation(currentThing.getText(), Integer.parseInt(Calculator.properties.get("Calculator.PrecisionDecimal")), MathUtils.RoundingStandard.ROUND_DOWN);
               } catch(InvalidEquationException err) {
                  JOptionPane.showMessageDialog(null, err.getMessage(), "Result", JOptionPane.ERROR_MESSAGE);
               }
               break;
            case "SQRT":
               try {
                  MathUtils.sqrtNumber(currentThing.getText());
               } catch(InvalidInputException err) {
                  JOptionPane.showMessageDialog(null, err.getMessage(), "Result", JOptionPane.ERROR_MESSAGE);
               }
               break;
            case "EXP":
               try {
                  MathUtils.expNumber(currentThing.getText());
               } catch(InvalidInputException err) {
                  JOptionPane.showMessageDialog(null, err.getMessage(), "Result", JOptionPane.ERROR_MESSAGE);
               }
               break;
            case "LOGEXP":
               try {
                  MathUtils.interpretLogarithm(currentThing.getText());
               } catch(InvalidEquationException err) {
                  JOptionPane.showMessageDialog(null, err.getMessage(), "Result", JOptionPane.ERROR_MESSAGE);
               }
               break;
            default:
               JOptionPane.showMessageDialog(null, "...HOW?!?!?", "...", JOptionPane.ERROR_MESSAGE);
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
   enum RoundingStandard {
      /**Rounds Away From Zero */
      ROUND_UP(RoundingMode.UP),
      /**Rounds Towards Zero */
      ROUND_DOWN(RoundingMode.DOWN),
      /**Rounds Up Towards +∞ No Matter The Sign*/
      ROUND_CEIL(RoundingMode.CEILING),
      /**Rounds Down Towards -∞ No Matter The Sign*/
      ROUND_FLOOR(RoundingMode.FLOOR),
      /**The Rounding Literally EVERYONE Knows.*/
      ROUND(RoundingMode.HALF_UP),
      /**Rounds Towards The Nearest Even Number If Both Neighbors Are Equidistant, Or The Nearest Neighbor*/
      ROUND_EVEN(RoundingMode.HALF_EVEN),
      /**Same As ROUND But Rounds Down Instead Of Up If Equidistant*/
      INV_ROUND(RoundingMode.HALF_DOWN),
      /**It Doesn't Round, Instead Taking The Entire Program Down Instead.*/
      ROUND_BAD(RoundingMode.UNNECESSARY);
      /**The RoundingMode Associated With A Thing */
      private final RoundingMode rm;
      /**
       * Constructs A New RoundingStandard.
       * @param rm The RoundingMode To Use
       */
      private RoundingStandard(RoundingMode rm) {
         this.rm = rm;
      }
      /**
       * Gets The RoundingMode Of Any RoundingStandard
       * @return The RoundingMode
       */
      public RoundingMode getRoundingMode() {
         return this.rm;
      }
      /**
       * Gets The Standard Associated With A Given Value
       * @param rm The RoundingMode To Check For.
       * @return The RoundingStandard That Has The Value, Or Null If It Does Not Exist.
       */
      public static RoundingStandard getForStandard(RoundingMode rm) {
         for(RoundingStandard rs : values()) {
            if(rs.rm == rm) {
               return rs;
            }
         }
         return null;
      } 
   }
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
            result = lo;
         }
         JOptionPane.showMessageDialog(null, "The Result Of Your Addition Is: " + Long.toString(result), "Result", JOptionPane.INFORMATION_MESSAGE);
      } catch(NumberFormatException err) {
         throw new InvalidEquationException("Error: A Number In This Equation Is Invalid.");
      }
   }
   public static void interpretSubtractionEquation(String equation) throws InvalidEquationException {
      String temp = equation.replace("\n", "").replace(" ", "").replace("\t", "");
      boolean isInvalidCharacter = false;
      for(int i = 0; i < temp.toCharArray().length; i++) {
         if(!(Character.isDigit(temp.toCharArray()[i]) || ((Character)temp.toCharArray()[i]).equals('-'))) {
            isInvalidCharacter = true;
            break;
         }
      }
      if(isInvalidCharacter) {
         throw new InvalidEquationException("Error: Your Equation Contains An Invalid Character.");
      }
      try {
         LongStream ls = Arrays.stream(temp.split("\\-")).mapToLong(Long::parseLong);
         LongStream ls2 = Arrays.stream(temp.split("\\-")).mapToLong(Long::parseLong).skip(1);
         long first = ls.findFirst().getAsLong();
         long result = ls2.reduce(first, (a, b) -> a - b);
         if(Boolean.parseBoolean(Calculator.properties.get("Calculator.PreventUnderflow"))) {
            long[] results = Arrays.stream(temp.split("\\-")).mapToLong(Long::parseLong).toArray();
            long finalNum = 0;
            for(int i = 0; i < results.length; i++) {
               if(i == 0) {
                  finalNum = results[0];
               } else {
                  try {
                     finalNum = Math.subtractExact(finalNum, results[i]);
                  } catch(ArithmeticException err) {
                     throw new InvalidEquationException("Because Of Your Settings, The Underflow Cause An Error.");
                  }
               }
            }
            result = finalNum;
         }
         JOptionPane.showMessageDialog(null, "The Result Of Your (Arguably Slightly More Complex) Subtraction Is: " + Long.toString(result), "Result", JOptionPane.INFORMATION_MESSAGE);
      } catch(NumberFormatException err) {
         throw new InvalidEquationException("Error: A Number In Your Equation Was Invalid.");
      }
   }
   public static void interpretMultEquation(String equation) throws InvalidEquationException {
      String temp = equation.replace("\n", "").replace(" ", "").replace("\t", "");
      boolean isAsteriskFirst = ((Character)(temp.toCharArray()[0])).equals('*');
      if(isAsteriskFirst) throw new InvalidEquationException("Error: Equation Starts With '*', But Should Start With: [Digit]");
      boolean isInvalidCharacter = false;
      for (int i = 0; i < temp.toCharArray().length; i++) {
         if(!(Character.isDigit(temp.toCharArray()[i]) || ((Character)(temp.toCharArray()[i])).equals('*') || ((Character)(temp.toCharArray()[i])).equals('-'))) {
            isInvalidCharacter = true;
            break;
         }
      }
      if(isInvalidCharacter) {
         throw new InvalidEquationException("Error: Equation Contains An Invalid Character.");
      }
      try {
         long[] results = Arrays.stream(temp.split("\\*")).mapToLong(Long::parseLong).toArray();
         long finalResult = 1;
         for (int i = 0; i < results.length; i++) {
            finalResult *= results[i];
         }
         if(Boolean.parseBoolean(Calculator.properties.get("Calculator.PreventOverflow")) || Boolean.parseBoolean(Calculator.properties.get("Calculator.PreventUnderflow"))) {
            long[] result = Arrays.stream(temp.split("\\*")).mapToLong(Long::parseLong).toArray();
            long resultActual = 1;
            for(int i = 0; i < result.length; i++) {
               if(isUnderflow(resultActual, result[i]) && Boolean.parseBoolean(Calculator.properties.get("Calculator.PreventUnderflow"))) {
                  throw new InvalidEquationException("Your Equation Underflows, Which Violates Your Settings.");
               }
               if(isOverflow(resultActual, result[i]) && Boolean.parseBoolean(Calculator.properties.get("Calculator.PreventOverflow"))) {
                  throw new InvalidEquationException("Your Equation Overflows, Which Violates Your Settings");
               }
               resultActual = resultActual * result[i];
               // try {
               //    resultActual = Math.multiplyExact(resultActual, result[i]);
               // } catch(ArithmeticException err) {
               //    if(resultActual * result[i] > 0 && Boolean.parseBoolean(Calculator.properties.get("Calculator.PreventUnderflow"))) {
               //       throw new InvalidEquationException("Error: The Equation Underflowed, And Options Prevent That.");
               //    } else if(resultActual * result[i] < 0 && Boolean.parseBoolean(Calculator.properties.get("Calculator.PreventOverflow"))) {
               //       throw new InvalidEquationException("Error: The Equation Provided Overflowed, And The Prevent Overflow Options Is Enabled.");
               //    }
               // }
            }
            finalResult = resultActual;
         }
         JOptionPane.showMessageDialog(null, "The Result Of The Multiplication Is: " + Long.toString(finalResult), "Result", JOptionPane.INFORMATION_MESSAGE);
      } catch(NumberFormatException err) {
         throw new InvalidEquationException("Error: Equation Contains A Number That Is Formatted Incorrectly.");
      }
   }
   public static void interpretDivisionEquation(String equation, int roundingAmt, RoundingStandard standard) throws InvalidEquationException {
      String temp = equation.replace("\n", "").replace(" ", "").replace("\t", "");
      boolean isSlashFirst = ((Character)(temp.toCharArray()[0])).equals('/');
      if(isSlashFirst) {
         throw new InvalidEquationException("Error: There Is A / First, Which Is Invalid");
      }
      boolean isInvalidCharacter = false;
      for (int i = 0; i < temp.toCharArray().length; i++) {
         if(!(Character.isDigit(temp.toCharArray()[i]) || ((Character)(temp.toCharArray()[i])).equals('/'))) {
            isInvalidCharacter = true;
            break;
         }
      }
      if(isInvalidCharacter) {
         throw new InvalidEquationException("Error: Your Equation Contains An Invalid Character");
      }
      try {
         long[] result = Arrays.stream(temp.split("\\/")).mapToLong(Long::parseLong).toArray();
         BigDecimal resultFinal = new BigDecimal(0);
         for(int i = 0; i < result.length; i++) {
            if(i == 0) {
               resultFinal = new BigDecimal(result[0]);
               continue;
            }
            resultFinal = resultFinal.divide(new BigDecimal(result[i]), roundingAmt, standard.getRoundingMode());
         }
         if(Boolean.parseBoolean(Calculator.properties.get("Calculator.EngNotation"))) {
            JOptionPane.showMessageDialog(null, "The Result Of Your Division Is: " + resultFinal.toEngineeringString(), "Result", JOptionPane.INFORMATION_MESSAGE);
         } else {
            JOptionPane.showMessageDialog(null, "The Result Of Your Division Is: " + resultFinal.toPlainString(), "Result", JOptionPane.INFORMATION_MESSAGE);
         }
      } catch(NumberFormatException er) {
         throw new InvalidEquationException("Error: A Number In Your Equation Is Formatted Incorrectly");
      }
   }
   public static void sqrtNumber(String interpretText) throws InvalidInputException {
      String temp = interpretText.substring(0, interpretText.indexOf("\n"));
      int i = 0;
      try {
         i = Integer.parseInt(temp);
      } catch(NumberFormatException err) {
         throw new InvalidInputException("Your Input To This Function Was Formatted Incorrectly.");
      }
      if(i < 0) {
         throw new InvalidInputException("Your Input Is Less Than One, Which Cannot Be Accepted As Input To A Square-Root Function");
      }
      BigDecimal temp2 = new BigDecimal(i);
      String temp3;
      if(Boolean.parseBoolean(Calculator.properties.get("Calculator.EngNotation"))) {
         temp3 = temp2.sqrt(MathContext.DECIMAL64).toEngineeringString();
      } else {
         temp3 = temp2.sqrt(MathContext.DECIMAL64).toPlainString();
      }
      JOptionPane.showMessageDialog(null, String.format("The Square Root Of %d Is %s", i, temp3), "Result", JOptionPane.INFORMATION_MESSAGE);
   }
   public static void expNumber(String numberText) throws InvalidInputException {
      String temp = numberText.replace("\n", "").replace("\t", "").replace(" ", "");
      String[] temp2 = temp.split("\\,");
      if(temp2.length > 2) {
         throw new InvalidInputException("You Have Input Too Many Things.");
      } else if(temp2.length < 2) {
         throw new InvalidInputException("You Have Input Too Few Things.");
      }
      try {
         double d = Math.pow(Double.parseDouble(temp2[0]), Double.parseDouble(temp2[1]));
         JOptionPane.showMessageDialog(null, String.format("The Result Of %f^%f Is %f", Double.parseDouble(temp2[0]), Double.parseDouble(temp2[1]), d), "Result", JOptionPane.INFORMATION_MESSAGE);
      } catch(NumberFormatException err) {
         throw new InvalidInputException("Error: Something Went Wrong Calculating Your Answer. Please Try Again.");
      }
   }
   public static void interpretLogarithm(String logEquation) throws InvalidEquationException {
      String temp = logEquation.replace("\n", "").replace("\t", "").replace(" ", "");
      String tempLower = temp.toLowerCase();
      System.out.println(temp);
      if(tempLower.indexOf("log") == -1) {
         throw new InvalidEquationException("Error: There Is No Log In Your LOGarithm Equation.");
      }
      String log = tempLower.substring(tempLower.indexOf("log"), tempLower.indexOf("log") + 3);
      String base;
      if(tempLower.indexOf("(") == -1 || tempLower.indexOf(")") == -1) {
         throw new InvalidEquationException("Error: One Or More Parethesis Are Missing.");
      }
      if(tempLower.indexOf("_") == -1 || tempLower.substring(tempLower.indexOf("_") + 1, tempLower.indexOf("(")).equals("")) {
         base = "10";
      } else {
         base = tempLower.substring(tempLower.indexOf("_") + 1, tempLower.indexOf("("));
      }
      String param = tempLower.substring(tempLower.indexOf("(") + 1, tempLower.indexOf(")"));
      if(param.equals("")) {
         throw new InvalidEquationException("Error: There Is No A Value For log_B(A) = N");
      }
      if(tempLower.indexOf("=") == -1) {
         throw new InvalidEquationException("Error: There Is No Equal Sign, Which Probably Means There's No N Value For log_B(A) = N Either");
      }
      String equal = tempLower.substring(tempLower.indexOf("=") + 1);
      if(equal.equals("")) {
         throw new InvalidEquationException("Error: There Is No N Value For log_B(A) = N.");
      }
      double baseD = Double.parseDouble(base);
      double paramD = Double.parseDouble(param);
      double equalD = Double.parseDouble(equal);
      JOptionPane.showMessageDialog(null, String.format("log_%f(%f) = %f Converted To Exponential Form Is %f^%f = %f", baseD, paramD, equalD, baseD, equalD, paramD), "Result", JOptionPane.INFORMATION_MESSAGE);
   }
   /**
    * <h2>Summary:</h2>
    * This Method Checks Whether Two Longs Would Go Above The Maximum Long Value (9223372036854775807) When Multiplied.
    * This Is Not The Same As {@code Math.multiplyExact()}, Since This Uses Simple Algebra Instead Of Bitwise Operations.
    * <h2>Functionality:</h2>
    * This Function Has Much Simpler Logic Than The {@code isUnderflow}, Since Each Parameter Is The Same Sign. If A*B > C,
    * A > C/B, Provided That All Are Positive. The Same Is True For Negatives, Except All > Signs Are Reversed.
    * @param a The First Number, Typically The Dividend / The Numerator
    * @param b The Second Number, Typically The Divisor / The Denominator
    * @return A Boolean Indicating Whether The Two Values Overflow When Multipled.
    */
   public static boolean isOverflow(long a, long b) {
      //Since If A * B > Long.MAX_VALUE, Dividing Both Sides By B Would Mean A Is Larger Than MAX_VAL / B
      //Also, Long.MAX_VALUE / B If B < 0 Would Be Negative, So Any Mult Between Them Would Be Too Large
      return (a > 0 && b > 0 && a > Long.MAX_VALUE / b) || (a < 0 && b < 0 && a < Long.MAX_VALUE / b);
   }
   /**
    * <h2>Summary:</h2>
    * This Method Checks If Two Longs Are Going To Underflow The Minimum Long Value (-9,223,372,036,854,775,808)
    * When Multiplied. This Is Not The Same As The Internal Checks Of {@code Math.multiplyExact}, As It Does Not Use
    * Bit Shifts And Bit-wise Operations To Check Them, But Rather Uses Basic Algebraic Principles To Find Them.
    * <h2>Functionality:</h2>
    * This Function Takes In Two Longs: Long a And Long b. In Order To Return True, ONLY One Can Be Negative.
    * After These Checks, It Checks If The Negative Value Is Less Than {@code Long.MIN_VALUE / [Positive_Value]}, Where
    * Positive Value Is The Other Variable Not On The Left Of The Equation. This And The {@code isOverflow} Function Use
    * This Basic Algebraic Principle: If a * b = c, a = c/b. Therefore, We Can Conclude That: If a * b > c, a > c/b, As Long As
    * We Know That They Are In Specific Ranges. And Since Each Will Only Be Active When Those Specific Ranges Are Fulfilled, There
    * Is A 100% Certainty To It. We Know That This Will Work, Even If We Can Directly Compare a * b To Long.MIN_VALUE.
    * @param a The First Number, Typically The Dividend / The Numerator
    * @param b The Second Number, Typically The Divisor / The Denominator
    * @return A Boolean Indicating Whether They Overflow
    */
   public static boolean isUnderflow(long a, long b) {
      //Since Mult Will ALways Be Negative, We Divide By The Positive Long So MIN_VALUE Does Not Become
      //Positive. This Ensures The Check Is Between Negative Numbers Only
      return (a > 0 && b < 0 && b < Long.MIN_VALUE / a) || (a < 0 && b > 0 && a < Long.MIN_VALUE / b);
   }
}
class Options extends JPanel {
   public JFrame frame = null;
   public static JCheckBox[] jcbs = {new JCheckBox("Prevent Number Overflow"), new JCheckBox("Prevent Number Underflow"), new JCheckBox("Use Engineering Notation")};
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
      int yIncrement = 10;
      for (JCheckBox jcb : jcbs) {
         String prefsPath = switch(jcb.getText()) {
            case "Prevent Number Overflow" -> "Calculator.PreventOverflow";
            case "Prevent Number Underflow" -> "Calculator.PreventUnderflow";
            case "Use Engineering Notation" -> "Calculator.EngNotation";
            default -> "null";
         };
         if(prefsPath.equals("null")) {
            JOptionPane.showMessageDialog(null, "How Did This Even Happen?", "???", JOptionPane.ERROR_MESSAGE);
            return;
         }
         jcb.setSelected(Boolean.parseBoolean(Calculator.properties.get(prefsPath)));
         jcb.addActionListener(e -> {
            if(jcb.isSelected()) {
               System.out.println("Enabled");
               Calculator.properties.put(prefsPath, "true");
               Calculator.updatePrefs();
            } else {
               System.out.println("Disabled");
               Calculator.properties.put(prefsPath, "false");
               Calculator.updatePrefs();
            }
         });
         jcb.setBounds(10, yIncrement, frame.getWidth() - (frame.getWidth() - 350), frame.getHeight() - (frame.getHeight() - 20 - yIncrement));
         frame.add(jcb);
         yIncrement += 20;
      }
      JSlider slider = new JSlider(1, 50);
      slider.setPaintTicks(true);
      slider.setPaintTrack(true);
      slider.setMinorTickSpacing(5);
      slider.setSnapToTicks(false);
      slider.setMajorTickSpacing(10);
      slider.setValue(Integer.parseInt(Calculator.properties.get("Calculator.PrecisionDecimal")));
      slider.setBounds(10, yIncrement + 20, frame.getWidth() - (frame.getWidth() - 350), frame.getHeight() - (frame.getHeight() - 20 - yIncrement));
      JLabel label = new JLabel("Precision (In Decimal Places): " + Integer.toString(slider.getValue()));
      slider.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent evt) {
            label.setText("Precision (In Decimal Places): " + Integer.toString(slider.getValue()));
            Calculator.properties.put("Calculator.PrecisionDecimal", Integer.toString(slider.getValue()));
            Calculator.updatePrefs();
         }
      });
      frame.add(slider);
      label.setVisible(true);
      label.setBounds(18, yIncrement + 5, frame.getWidth() - (frame.getWidth() - 350), frame.getHeight() - (frame.getHeight() - 20 - yIncrement));
      frame.add(label);
      //Add Rounding Mode
      //Add How Many Places To Round
   }
}