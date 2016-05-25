package lexer;

/**
 *  The Symbol class is used to store reserved words and user strings along with
 *  an indication of the kind of string. 
 *  * e.g. The id "abc" will store the "abc" in name and Sym.Tokens.Identifier 
 *  in kind.
*/
public class Symbol {
  /** name String - character string of the Symbol.  */
  private String name;
  
  /** kind Tokens - Tokens enum kind of the Symbol.   */
  private Tokens kind;   // token kind of symbol

  /** Symbol Constructor - private - Symbol for Token objects.   
   * 
   * @param n String - the Symbol character string.
   * @param kind Tokens - the Tokens enum for the Symbol. 
   */
  private Symbol(String n, Tokens kind) {
    name=n;
    this.kind = kind;
  }

  /** symbols HashMap - static - holds all of the Symbols    */
  private static java.util.HashMap<String,Symbol> symbols = 
          new java.util.HashMap<String,Symbol>();

  /**    
   * toString - returns the name, a String, of the Symbol. 
   * Can be used for printing the Symbol. 
   * <p>
   * @return String - character string name of Symbol.
   */
  public String toString() {
	return name;
  }

  /**
   * getKind - returns the Tokens enum for this Symbol. This is the kind. 
   * <p> 
   * @return Tokens - the Tokens enum kind for the Symbol. 
   */
  public Tokens getKind() {
    return kind;
  }

  /**
   *  Return the unique symbol associated with a string.
   *  Repeated calls to <tt>symbol("abc")</tt> will return the same Symbol.
   *  <p> 
   *  @param newTokenString String - the String to check/add into symbol.
   *  @param kind Tokens - Token type. BogusToken if checking if valid.
   *  @return Symbol - Reference to Symbol entry for this Token. 
   */
  public static Symbol symbol(String newTokenString, Tokens kind) {
	Symbol s = symbols.get(newTokenString);
	if (s == null) { 
            // bogus string so don't enter into symbols.
	    if (kind == Tokens.BogusToken) { 
	        return null;
	    }
	    //System.out.println("new symbol: "+u+" Kind: "+kind);
		s = new Symbol(newTokenString,kind);
		symbols.put(newTokenString,s);
	}
	return s;
  }
}

