package lexer;

import java.io.*;

/**
 *  This class is used to manage the source program input stream.
 *  Each read request will return the next usable character. The source column
 *  and line number are maintained for the character.
 *  <p>
 *  The programListing Vector has been added to this class as part of the CSC
 *  413 Fall 2015 Project 2. This String vector holds a listing of the input
 *  file being processed by the Lexer. This Vector is used to print out the 
 *  program upon the completion of the Lexer process. The program is printed 
 *  through the end or until the first error occurs. programListing is a static
 *  variable. It is declared in SourceReader and filled by the read() method
 *  of SourceReader. The data is read out in the Lexer main() method. 
 * 
*/
public class SourceReader {
    /** programListing - String Vector that contains program for print out   */
    private static java.util.Vector<String> programListing;
    
    /** source BufferedReader - contains input file processed by Lexer  */
    private BufferedReader source;
    
    /** lineno int - program line number that is currently being processed.    */
    private int lineno = 0;   // line number of source program
    
    /** position int - position of last character processed   */
    private int position; 
    
    /** preFloatPos int - holds position before e/E in exponent  */
    private int preFloatPos;
    
    /** isPriorEndLine boolean - true if last character read was newline   */
    private boolean isPriorEndLine = true;  
    
    /** nextLine String - current line in program being processed.   */
    private String nextLine;
    
/*
    public static void main(String args[]) {
        SourceReader s = null;
        try {
            s = new SourceReader("t");
            while (true) {
                char ch = s.read();
                System.out.println("Char: " + ch + " Line: " + s.lineno +
                         "position: " + s.position);
            }
        } catch (Exception e) {}

        if (s != null) {
            s.close();
        }
    }
*/

/**
 *  Construct a new SourceReader. Prints out the input file and the user 
 *  directory path. These help the user verify that the setup is correct. 
 *  This code was improved to catch any exception thrown because of a file
 *  open / access problem. 
 *  <p>
 *  @param sourceFile the String describing the user's source file.
*/
    public SourceReader(String sourceFile) {
        try {
            System.out.println("Source file: "+sourceFile);
            System.out.println("user.dir: " + System.getProperty("user.dir"));
            source = new BufferedReader(new FileReader(sourceFile));
        
            // Create String Vector to hold the program listing. 
            programListing = new java.util.Vector<String>();
        } catch (Exception e){
            System.out.println("Error: Problem with file: "+sourceFile);
            System.exit(1);
        }
    } 

    void close() {
        try {
            source.close();
        } catch (Exception e) {}
    }

/**
 *  Read next char. Track line #, character position in line<br>
 *  return space for newline.
 *  <p>
 *  @return the character just read in.
 *  @throws IOException is thrown for IO problems such as end of file
*/
    public char read() throws IOException {

        if (isPriorEndLine) {
            lineno++;
            position = -1;
            nextLine = source.readLine();
            if (nextLine != null) {
                System.out.println("READLINE:   "+nextLine);
                // Add nextLine to programListing Vector for later print out. 
                programListing.add(nextLine);
            }
            isPriorEndLine = false;
        }
        if (nextLine == null) {  // hit eof or some I/O problem
            throw new IOException();
        }
        if ( nextLine.length() == 0) {
            isPriorEndLine = true;
            return ' ';
        }
        position++;
        if (position >= nextLine.length()) {
            isPriorEndLine = true;
            return ' ';
        }
        return nextLine.charAt(position);
    }
    
    
    /**
     *  printListing prints out the listing of the program stored in the
     *  programListing String Vector. Line numbers are added to the front
     *  of each line. 
     */
    public static void printListing() {
        
        System.out.println();
        for (int i=0; i < programListing.size(); i++) {
            System.out.print(i+1);
            System.out.println(" "+programListing.get(i));
        }
        System.out.println();

    }
    
    
    /**
     *  capturePos stores the position in the input line before the e/E in a 
     *  scientific number. This is necessary in case syntax does not match a
     *  scientific number and then we must treat it as a float Token. 
     *  position is pointing to the e/E. We must back it up to char before. 
     */
    protected void capturePos() {
        preFloatPos = position-1;
    }
    
    /** 
     *  restorePos restores the position variable pointing to the present 
     *  input char to the value stored by capturePos(). This allows us to 
     *  back up to the char before the e/E if our number turns out to be a 
     *  float. 
     */
    protected void restorePos() {
        position = preFloatPos;
        isPriorEndLine = false;  // not at end of line - at least e/E to read.
    }
    
    /**
     *  getPosition returns the column position of the present character read
     *  from the input file.
     *  <p> 
     *  @return int - Position of the character just read in.
    */
    public int getPosition() {
        return position;
    }
    
    /**
     *  getLineno returns the line number of the present character read from
     *  the input file. 
     *  <p> 
     *  @return int - line number of the character just read in.
    */
    public int getLineno() {
        return lineno;
    }
}