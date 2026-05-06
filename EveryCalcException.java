/**Super Class For Every Exception Thrown In EveryCalc. (Yes A Lot Of Documentation Stuff Here Is Copied)*/
public class EveryCalcException extends Exception {
    /**
    * Constructs An EveryCalcException With A Message.
    * @param message The Message
    */
   public EveryCalcException(String message) {
      super(message);
   }
   /**
    * Constructs An EveryCalcException With A Throwable (For Whatever Reason)
    * @param throwable The Throwable?
    */
   public EveryCalcException(Throwable throwable) {
      super(throwable);
   }
   /**Constructs An EveryCalcException With N O T H I N G*/
   public EveryCalcException() {
      super();
   }
   /**
    * Constructs An EveryCalcException With A Message And A Throwable
    * @param message The Message.
    * @param throwable The Throwable (Honestly, IDK Why You'd Use This).
    */
   public EveryCalcException(String message, Throwable throwable) {
      super(message, throwable);
   }
}
/**InvalidEquationException is thrown when the equation is invalid. */
class InvalidEquationException extends EveryCalcException {
   /**
    * Constructs An InvalidEquationException With A Message.
    * @param message The Message
    */
   public InvalidEquationException(String message) {
      super(message);
   }
   /**
    * Constructs An InvalidEquationException With A Throwable (For Whatever Reason)
    * @param throwable The Throwable?
    */
   public InvalidEquationException(Throwable throwable) {
      super(throwable);
   }
   /**Constructs An InvalidEquationException With N O T H I N G*/
   public InvalidEquationException() {
      super();
   }
   /**
    * Constructs An InvalidEquationException With A Message And A Throwable
    * @param message The Message.
    * @param throwable The Throwable (Honestly, IDK Why You'd Use This).
    */
   public InvalidEquationException(String message, Throwable throwable) {
      super(message, throwable);
   }
}
/**InvalidInputException Is Thrown When The Input Of A One-Number Function Does Not Get That*/
class InvalidInputException extends EveryCalcException {
   /**Constructs An InvalidInputException With No Details. Not Sure Why You Would Want This... */
   public InvalidInputException() {
      super();
   }
   /**
    * Constructs An InvalidInputException With A Given Message
    * @param msg The Message To Give
    */
   public InvalidInputException(String msg) {
      super(msg);
   }
   /**
    * Constructs An InvalidInputException With Whatever A Throwable Is
    * @param throwable Your Throwable
    */
   public InvalidInputException(Throwable throwable) {
      super(throwable);
   }
   /**
    * Constructs An InvalidInputException With A Message And Some Throwable
    * @param msg The Message
    * @param throwable Your Awful 'Throwable'
    */
   public InvalidInputException(String msg, Throwable throwable) {
      super(msg, throwable);
   }
}