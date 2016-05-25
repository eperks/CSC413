
package lexer;


public class Lexer {
    /** atEOF - Boolean - indicates if you are at the end of the file   */
    private boolean atEOF = false;
    
    /** ch char - holds the next character to process   */
    private char ch;     
    
    /** source SourceReader - input file to process   */
    private SourceReader source;

    /** startPosition int - starting position of Token String   */
    private int startPosition;
    
    /** endPosition int - ending position of Token String  */
    private int endPosition; 

    /**
     *  Lexer constructor. Runs TokenType to create symbols and tokens 
     *  HashMaps for reserved words. Also, the input file is opened in 
     *  SourceReader. 
     *  <p>
     *  try/catch block was added to catch an exception that occurs for 
     *  reading the first character. This indicates an empty file or some
     *  file read problem. This provides better help to the user to track down
     *  any problems. 
     *  <p>
     *  @param sourceFile string - input file to open
     */
    public Lexer(String sourceFile) {
        new TokenType();  // init token table
        
        // Open the input file. Exception caught by SourceReader. 
        source = new SourceReader(sourceFile);
        
        try {
        // Read first char. Architecture designed to always have ch with
        // next character for Token processing.
        ch = source.read();
        } catch (Exception e) {
            System.out.println(
                    "Error: Could not read first character from file.");
            System.exit(1);
        }
    }

    
    /**
     * main method is used to run the Lexer process on the input file 
     * specified on the command line. The Lexer is a part of the compiler 
     * process. It divides the input file into Tokens. The Token contains
     * a String of the characters, a Symbol including the Token enum type, 
     * the start and end position of the String and finally the line number
     * in which Token appeared. This location information is reported to 
     * the user with any error message.
     * <p>
     * The main() method repeatedly invokes the nextToken() method in Lexer to
     * get the next Token which is output. For those Tokens that are part of
     * a group the group name is also printed. The groups include integer, 
     * float, scientific, string and character literals as well as identifiers.
     * <p>
     * If an invalid character string is detected an error message is printed
     * and Token processing terminates. When Token processing ends, either by
     * an invalid character string (error) or because the end of file has been 
     * reached, a listing of the program, with line numbers is printed. In the
     * case of an error, the listing is printed through the line with the 
     * invalid Token. 
     * 
     * @param args String[] - one entry expected - the input file name. 
     */
    public static void main(String args[]) {
        /** tok - Token object being processed  */
        Token tok;
        
        try {
            // file to process is command line input.
            if (args.length < 1) {
                System.out.println("No file name specified");
                System.exit(0);
            }
            Lexer lex = new Lexer(args[0]);
            // loop ends when no more Tokens. Exception breaks out of loop.
            while (true) {
                tok = lex.nextToken();  // get next Token
                String p = "" + TokenType.tokens.get(tok.getKind()) + " ";
                // If Token is part of identifier group, print out group also. 
                if ((tok.getKind() == Tokens.Identifier) ||
                    (tok.getKind() == Tokens.FLOAT) ||
                    (tok.getKind() == Tokens.INTeger) ||
                    (tok.getKind() == Tokens.STRING) ||
                    (tok.getKind() == Tokens.CHAR) ||
                    (tok.getKind() == Tokens.ScientificN))
                    p += tok.toString();
                // Including location information. 
                p += "\t\tleft: " + tok.getLeftPosition() +
                     "  right: " + tok.getRightPosition() +
                     "  line: " + tok.getLineno();
                System.out.println(p);                
            }
        } catch (Exception e) {}
        
        // When the EOF is reached, an exception is thrown which breaks out
        // of the loop. 
        // Print out program with line numbers.
        // If processing ended early because of illegal Token, print just
        // to that line. 
        SourceReader.printListing();
    }

    /**
     *  newIdTokens are either ids or reserved words; new id's will be inserted
     *  in the symbol table with an indication that they are id's. Reserved 
     *  words are already in the symbol table. A reference to their location 
     *  will be stored in the Token. The line number is obtained from the 
     *  getLineno() method of the SourceReader object, source.
     *  <p>
     *  @param id is the String just scanned - it's either id or reserved word.
     *  @param startPosition is the column in the source file where the token 
     *  begins.
     *  @param endPosition is the column in the source file where the token ends.
     *  @return Token - either an id or one for the reserved words.
    */
    public Token newIdToken(String id,int startPosition,int endPosition) {
        return new Token(startPosition,endPosition,
                Symbol.symbol(id,Tokens.Identifier),source.getLineno());
    }
    
    /**
     *  newCharTokens are character literal Tokens. They will be added to the
     *  symbol table if they are not already present. A reference to their 
     *  location will be stored in the Token. The line number is obtained from 
     *  the getLineno() method of the SourceReader object, source.
     *  <p> 
     *  @param id is the character string just scanned. It is a character 
     *  literal. 
     *  @param startPosition is the column in the source file where the token 
     *  begins.
     *  @param endPosition is the column in the source file where the token ends.
     *  @return Token - a character Token. 
    */
    public Token newCharToken(String id,int startPosition,int endPosition) {
        return new Token(startPosition,endPosition,
                Symbol.symbol(id,Tokens.CHAR),source.getLineno());
    }
    
    /**
     *  newStringTokens are string literal Tokens. They will be added to the
     *  symbol table if they are not already present. A reference to their 
     *  location will be stored in the Token. The line number is obtained from 
     *  the getLineno() method of the SourceReader object, source.
     *  <p>
     *  @param id is the string just scanned. It is a string literal.
     *  @param startPosition is the column in the source file where the token 
     *  begins.
     *  @param endPosition is the column in the source file where the token ends.
     *  @return Token - a string Token. 
    */
    public Token newStringToken(String id,int startPosition,int endPosition) {
        return new Token(startPosition,endPosition,
                Symbol.symbol(id,Tokens.STRING),source.getLineno());
    }
    
    /**
     *  newIntegerTokens are integer literal Tokens. They will be added to the
     *  symbol table if they are not already present. A reference to their 
     *  location will be stored in the Token. The line number is obtained from 
     *  the getLineno() method of the SourceReader object, source.
     *  <p>
     *  We don't convert the numeric strings to integers until we load the 
     *  bytecodes for interpreting. This ensures that any machine numeric 
     *  dependencies are deferred until we actually run the program. 
     *  i.e. the numeric constraints of the hardware used to compile the source
     *  program are not used.
     *  <p>
     *  @param number is the int String just scanned. It is an integer literal.
     *  @param startPosition is the column in the source file where the int 
     *  begins.
     *  @param endPosition is the column in the source file where the int ends.
     *  @return Token - a int Token.
    */
    public Token newIntegerToken(String number,int startPosition,
            int endPosition) {
        return new Token(startPosition,endPosition,
            Symbol.symbol(number,Tokens.INTeger),source.getLineno());
    }

    /**
     *  newFloatTokens are float literal Tokens. A float number is a number 
     *  that has a decimal point '.', but no e/E for a power of 10 exponent. 
     *  They will be added to the symbol table if they are not already present. 
     *  A reference to their location will be stored in the Token. The line 
     *  number is obtained from the getLineno() method of the SourceReader 
     *  object, source.
     *  <p>
     *  We don't convert the numeric strings to floating point values until we 
     *  load the bytecodes for interpreting. This ensures that any machine 
     *  numeric dependencies are deferred until we actually run the program. 
     *  i.e. the numeric constraints of the hardware used to compile the source
     *  program are not used.
     *  <p>
     *  @param number is the float String just scanned. It is a float literal. 
     *  @param startPosition is the column in the source file where the float
     *  begins.
     *  @param endPosition is the column in the source file where the float ends.
     *  @return Token - a float Token.
    */
    public Token newFloatToken(String number,int startPosition,int endPosition) {
        return new Token(startPosition,endPosition,
            Symbol.symbol(number,Tokens.FLOAT),source.getLineno());
    }
    
    /**
     *  newScientificTokens are scientific literal Tokens. A scientific number 
     *  is a number that has a decimal point '.' and an e/E followed by an 
     *  integer for power of 10 exponent. A scientific Token id is used for
     *  these numbers. However, for variable types, float is used. There is no
     *  scientific variable type in this Lexer. They will be added to the 
     *  symbol table if they are not already present. A reference to their 
     *  location will be stored in the Token. The line number is obtained from
     *  the getLineno() method of the SourceReader object, source.
     *  <p>
     *  We don't convert the numeric strings to floating point values until we 
     *  load the bytecodes for interpreting. This ensures that any machine 
     *  numeric dependencies are deferred until we actually run the program. 
     *  i.e. the numeric constraints of the hardware used to compile the source
     *  program are not used.
     *  <p>
     *  @param number is the scientific String just scanned. It is a Scientific 
     *  literal. 
     *  @param startPosition is the column in the source file where the 
     *  scientific begins.
     *  @param endPosition is the column in the source file where the scientific
     *  ends.
     *  @return Token - a scientific Token.
    */
    public Token newSciToken(String number,int startPosition,
            int endPosition) {
        return new Token(startPosition,endPosition,
            Symbol.symbol(number,Tokens.ScientificN), source.getLineno());
    }
    
    public Token newDoToken(String id, int startPosition, int endPosition){
        return new Token(startPosition, endPosition, Symbol.symbol(id, Tokens.Do), source.getLineno());
    }
    
    public Token newForToken(String id, int startPosition, int endPosition){
        return new Token(startPosition, endPosition, Symbol.symbol(id, Tokens.For), source.getLineno());
    }
    
    public Token newSemiclnToken(String id, int startPosition, int endPosition){
        return new Token(startPosition, endPosition, Symbol.symbol(id, Tokens.Semicln), source.getLineno());
    }
    
    public Token newNotToken(String id, int startPosition, int endPosition){
        return new Token(startPosition, endPosition, Symbol.symbol(id, Tokens.Not), source.getLineno());
    }
    
    
    /**
     *  makeToken is invoked after all of the other Token-matching routines have 
     *  not found a match. It is the last test before concluding that the input
     *  contains an invalid Token. It handles the one and two character Tokens,
     *  like operators (+ -) or separators (parenthesis, braces). makeToken 
     *  also filters out comments which begin with two slashes. If no match is 
     *  found an error message is printed and program execution terminates. 
     *  <p>
     *  @param s is the String representing the token.
     *  @param startPosition is the column in the source file where the token 
     *  begins.
     *  @param endPosition is the column in the source file where the token ends.
     *  @return the Token just found.
    */
    public Token makeToken(String s,int startPosition,int endPosition) {
        if (s.equals("//")) {  // filter comment
            // Cycle through until done with this line. 
            try {
               int oldLine = source.getLineno();
               do {
                   ch = source.read();
               } while (oldLine == source.getLineno());
            } catch (Exception e) {
                    atEOF = true;
            }
            return nextToken();
        }
        // be sure it's a valid token
        Symbol sym = Symbol.symbol(s,Tokens.BogusToken); 
        if (sym == null) {  // if not found in symbol HashMap then not valid. 
             System.out.println("******** illegal character: " + s);
             atEOF = true;
             return nextToken();
        }
        // If found in symbol table (valid reserved word) then return Token. 
        return new Token(startPosition,endPosition,sym,source.getLineno());
        }

    /**
     *  makeScientificToken is a helper routine that is used by both d+.d* and 
     *  d*.d+ routines. Once these float checks are complete and the e/E 
     *  character is found, then it is known that the literal might be 
     *  scientific. If it is not a valid scientific then a float is generated 
     *  and the position is backed up to start the next Token with the e/E. 
     *  Having this routine eliminated duplicate code in the d+.d* and d*.d+
     *  routines. 
     *  <p> 
     *  @param number is the String representing the scientific literal up to, 
     *  but not including the e/E character. ch holds the e/E character. 
     *  @return Token - a scientific literal 
    */
    public Token makeScientificToken(String number) { 
        /** digitRead - boolean - flag to indicate a digit is required. */
        boolean digitRead = false; 
        
        /** endFloatPos int - captures end position for a float   */
        int endFloatPos = endPosition;  
        
        /** floatNumber String - captures number string for a float   */
        String floatNumber = number; 
        
        try {
            // Put in e/E, get next character & in floatExtra
            endPosition++;
            number +=ch;
            
            source.capturePos();  // stores position value before e/E
            
            ch = source.read();
            // If ch is + or - then add to the number string
            if ((ch == '+') || (ch == '-')) {
                endPosition++;
                number += ch;
                ch = source.read();
            }
 
            // Process digits. There must be at least one digit
            while (Character.isDigit(ch)) {  // get all exponent digits
                endPosition++;
                number += ch;
                digitRead = true;
                ch = source.read();           
            }
        } catch (Exception e) {
            if (digitRead) atEOF = true;
        }
        
        if (digitRead)
            return newSciToken(number,startPosition,endPosition);
        
        // We have float. Must point position before e/E and read that char.
        source.restorePos();
        try {
            ch = source.read();
        } catch (Exception e) {}  // No Exception should occur. Returns e/E.
        
        return newFloatToken(floatNumber,startPosition,endFloatPos);
    }

    /**
     *  nextToken returns the next Token. null is returned if there are no more 
     *  Tokens. This is the method called by Lexer main() or by the next stage 
     *  in the compiler process to return the next Token. 
     *  <p> 
     *  CSC 413 Project 2 involved many modifications to this method. These 
     *  modifications comprise most of the programming work for this project.
     *  <p>
     *  A summary of the changes are:
     *  <p>
     *  1. Change the format of the output of the main() method in Lexer as 
     *  shown in the example below. Angle brackets not shown due to Javadoc 
     *  syntax error. 
     *  program    left: 0  right: 6  line: 1
     *  float 4.5     left: 5  right: 7  line: 2
     *  etc.
     *  The float indicates a literal of the float group. 
     *  <p> 
     *  2. Print out a listing of the program with line numbers after the Lexer
     *  Token generating process is complete, as in the example: 
     *  lines below:
     *  1. program { int i int j
     *  2.   i = 2
     *  etc. 
     *  <p>
     *  3. Add a float datatype and literal. This means that variables can be
     *  declared as float type and the Lexer will recognize float as a reserved
     *  word. The tokens file had to be updated to support this. A float literal
     *  is a number with a decimal point. There must be at least one digit. 
     *  Two formats are allowed: d+.d* and d*.d+ . The integer routine is used
     *  for the d+.d* format. If the first non-digit character after the integer
     *  is a decimal point - . , then the scanning process looks for a float
     *  value. 
     *  <p>
     *  4. Add a Scientific (exponential notation) literal. This means that 
     *  the Lexer will recognize numbers in scientific notation with e or E to 
     *  note the exponent. No scientific data type was created. Variables 
     *  holding scientific notation values will be declared as float. The 
     *  format for Scientific notation is:  float(E|e)(+?|-)integer
     *  The scanning process to determine a Scientific literal begins with
     *  determination of a float. Then if e or E is the next non-digit
     *  character, then the Scientific checking is performed. 
     *  <p>
     *  5. Add a character datatype, char, and literal. This means that 
     *  variables can be declared as char type and the Lexer will recognize char
     *  as a reserved word. The tokens file had to be updated to support this.
     *  A char literal is a single character enclosed by single quotes - ' . 
     *  <p>
     *  6. Add a string datatype, string, and literal. This means that variables
     *  can be declared as string type and the Lexer will recognize string as 
     *  a reserved word. The tokens file had to be updated to support this. 
     *  <p> 
     *  The previous code before the Project 2 modifications checked for 
     *  integers. This part of the code was retained and expanded. Also, the 
     *  previous code processed reserved words, identifies, including one and
     *  two special character identifiers. White space and // comments were 
     *  ignored. 
     *  <p>
     *  @return next Token - found in the source file. null returned if no 
     *  more. 
    */
    public Token nextToken() { // ch is always the next char to process
        if (atEOF) { 
            // End of file or error. Close and return null. 
            if (source != null) {
                source.close();
                source = null;
            }
            return null;
        }
        try {
            while (Character.isWhitespace(ch)) {  // scan past whitespace
                ch = source.read();
            }
        } catch (Exception e) {
            atEOF = true;
            return nextToken();
        }
        // Set position values to mark location of Token. 
        startPosition = source.getPosition();
        endPosition = startPosition - 1;

        if (Character.isJavaIdentifierStart(ch)) {
            // return tokens for ids and reserved words
            String id = "";
            try {
                do {
                    endPosition++;
                    id += ch;
                    ch = source.read();
                } while (Character.isJavaIdentifierPart(ch));
            } catch (Exception e) {
                atEOF = true;
            }
            // Identifier Token detected. Return Token. 
            return newIdToken(id,startPosition,endPosition);
        }
        
        // This block processes integers, floats & scientific numbers that 
        // begin with a digit
        if (Character.isDigit(ch)) {
            // return number token
            String number = "";
            try {
                do {
                    endPosition++;
                    number += ch;
                    ch = source.read();
                } while (Character.isDigit(ch));
                
                // Non-digit character detected. 
                // If next char is not decimal point, then we have an integer
                if (ch != '.')
                    return newIntegerToken(number,startPosition,endPosition);
                
            } catch ( Exception e) {
                atEOF = true;
                return newIntegerToken(number,startPosition,endPosition);
            }
            
            try {
                // Number is either float or scientific.
                // Add decimal as part of the number.
                // Cycle on digits. No digits is ok here.                 
                do {
                    endPosition++;
                    number += ch;
                    ch = source.read();
                } while (Character.isDigit(ch));
                
                // Non-digit character. if e/E then return scientific. 
                if ((ch == 'e') || (ch == 'E'))
                    return makeScientificToken(number);
                
            } catch (Exception e) {
                atEOF = true;
            }
            return newFloatToken(number,startPosition,endPosition);
        }
        
        // This block processes floats & scientific numbers that begin with a
        // decimal point. 
        if (ch == '.') {
            // return number token - either float or scientificN
            String number = "";
            
            // Need to read at least one digit. Indicates if digit read.  
            boolean digitRead = false;

            try {
                // Start number String with decimal point. 
                number = "" + ch;
                endPosition++;
                ch = source.read();
                
                while (Character.isDigit(ch)) {  // get digits right of decimal
                    endPosition++;
                    number += ch;
                    digitRead = true;
                    ch = source.read();
                }

                // Non-digit character. If e/E then return scientific. 
                // Must also confirm that syntax is valid - a digit was read
                if (((ch == 'e') || (ch == 'E')) && digitRead)
                    return makeScientificToken(number);
                  
            } catch (Exception e) {
                atEOF = true;
                ch = ' ';  // necessary in case of illegal input error. 
            }
            
            // Print error message and terminate if no digit to right of decimal.
            if (!digitRead) {
                    System.out.println("******* illegal input: " + number + ch);
                    atEOF = true;
                    return nextToken();
            }

            // Return a float number
            return newFloatToken(number,startPosition,endPosition);
        }
        
        // Check for character with single quote '. Any one character is 
        // allowed.
        if (ch == '\'') {
            // return character token - either float or scientificN
            String charString = "";
            try {
                // Character string starts with single quote ' 
                charString = "" + ch;
                endPosition++;
                int oldLine = source.getLineno();  // capture line # of open '
                
                ch = source.read();  // read the single character.
            
                charString += ch;
                endPosition++;
                ch = source.read();  // should be closing single quote. 
                
                charString += ch;  // Add in char that should be single quote.
                endPosition++;  // This code is here to help with error msg. 
                
                // verify closing single quote ' and same line. 
                if ((ch != '\'') || (oldLine != source.getLineno())) 
                    throw new Exception();
                
            } catch (Exception e) {
                System.out.println("******* illegal input: " + charString);
                atEOF = true;
                return nextToken();
            }
            try {                
                // read next character for next Token. 
                ch = source.read();
            } catch (Exception e) {
                atEOF = true;
            }
            return newCharToken(charString,startPosition,endPosition);
        }
        
        // Check for double quote. Any number of characters is allowed. 
        if (ch == '\"') {
            String inputString = "";
            try {
                inputString = "" + ch;
                endPosition++;
                int oldLine = source.getLineno();  // capture line # of open "
                
                ch = source.read();  // read a character
                
                // loop until closing " or switch to new line 
                while ((ch != '\"') && (oldLine == source.getLineno())) {  
                    inputString += ch;
                    endPosition++;
                    ch = source.read();
                }
                // If open " is on different line than close " throw 
                // Exception to print out error message. 
                if (oldLine != source.getLineno())
                    throw new Exception(); 
                inputString += ch;
                endPosition++;
            } catch (Exception e) {
                System.out.println("******* illegal input: " + inputString);
                atEOF = true;
                return nextToken();
            }
            try {  // read next character for next Token. 
                ch = source.read();
            } catch (Exception e) {
                atEOF = true;
            }
            return newStringToken(inputString,startPosition,endPosition);
        }
        
       /* if(ch == 'd'){
            String dostr = "";
            try{
                dostr = "" + ch;
                ch = source.read();
            }catch(Exception e){
                System.out.println("******* illegal input: " + dostr);
                atEOF = true;
                return nextToken();
            }
        } */

        // At this point the only tokens to check for are one or two
        // characters; we must also check for comments that begin with
        // 2 slashes
        String charOld = "" + ch;
        String op = charOld;
        Symbol sym;
        try {
            endPosition++;
            ch = source.read();
            op += ch;
            // check if valid 2 char operator; if it's not in the symbol
            // table then don't insert it since we really have a one char
            // token
            sym = Symbol.symbol(op, Tokens.BogusToken); 
            if (sym == null) {  // it must be a one char token
                return makeToken(charOld,startPosition,endPosition);
            }
            endPosition++;
            ch = source.read();
            return makeToken(op,startPosition,endPosition);
        } catch (Exception e) {}
        atEOF = true;
        if (startPosition == endPosition) {
            op = charOld;
        }
        return makeToken(op,startPosition,endPosition);
    }
}