package lexer;


public class Token {
  /** leftPosition int - Starting column position for the Token.   */
  private int leftPosition;  
  /** rightPosition int - Ending column position for the Token.   */  
  private int rightPosition;  
  /** lineno int - Program line number for the Token.    */ 
  private int lineno;  
  /** symbol Symbol - Symbol (String name and Token kind) for the Token.   */  
  private Symbol symbol;

/**
 *  Create a new Token based on the given Symbol. 
 *  <p> 
 *  @param leftPosition int - Source file column where the Token begins.
 *  @param rightPosition int - Source file column where the Token ends.
 *  @param sym Symbol - Source file Symbol for the Token.
 *  @param lineno int - Source file line number for the Token. 
*/
  public Token(int leftPosition, int rightPosition, Symbol sym, int lineno) {
   this.leftPosition = leftPosition;
   this.rightPosition = rightPosition;
   this.symbol = sym;
   this.lineno = lineno;
  }
  
  /**
   *  getSymbol returns the Symbol for the Token.
   *  <p>
   *  @return symbol - The Symbol for the Token.
   */
  public Symbol getSymbol() {
    return symbol;
  }
  
  /**
   *  print - prints out the Token information, including the Symbol String
   *  name, and position in the input program. 
   *  
   */
  public void print() {
    System.out.println("       " + symbol.toString() + 
                       "             left: " + leftPosition +
                       " right: " + rightPosition +
                       " line: " + lineno);
  }
  
  /**
   *  toString - returns a String representation for the Token. The symbol 
   *  toString method is used. 
   *  <p>
   *  @return String - the symbol.toString() method result. 
   */
  public String toString() {
    return symbol.toString();
  }
  
  /**
   *  getLeftPosition - returns the left column position of the Token. 
   *  <p>
   *  @return int - left column position of the Token.
   */
  public int getLeftPosition() {
    return leftPosition;
  }

  /**
   * getRightPosition - returns the right column position of the Token.
   * @return int - right column position of the Token.
   */
  public int getRightPosition() {
    return rightPosition;
  }
  
  /** 
   * getLineno - returns the program line number of the Token.
   * @return int - returns the program line number of the Token. 
   */
  public int getLineno() {
    return lineno;
  }
  
  
/**
 *  @return the integer that represents the kind of symbol we have which
 *  is actually the type of token associated with the symbol
*/
  public Tokens getKind() {
    return symbol.getKind();
  }
}

