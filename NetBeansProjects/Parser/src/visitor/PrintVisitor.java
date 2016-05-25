package visitor;

import ast.*;

/**
 *  PrintVisitor is used to visit an AST and print it using
 *  appropriate indentation:<br>
 *  <pre>
 *  1. root
 *  2.   Kid1
 *  3.   Kid2
 *  4.     Kid21
 *  5.     Kid22
 *  6.     Kid23
 *  7.   Kid3
 *  </pre>
*/
public class PrintVisitor extends ASTVisitor {
    private int indent = 0;

    private void printSpaces(int num) {
        String s = "";
        for (int i = 0; i < num; i++) {
            s += ' ';
        }
        System.out.print(s);
    }

/**
 *  Print the tree
 *  @param s is the String for the root of t
 *  @param t is the tree to print - print the information
 *  in the node at the root (e.g. decoration) and its kids
 *  indented appropriately
*/
    public void print(String s,AST t) {
        // assume less than 1000 nodes; no problem for csc 413
        int num = t.getNodeNum();
        AST decoration = t.getDecoration();
        int decNum = (decoration == null)? -1 : decoration.getNodeNum();
        String spaces = "";
        if (num < 100) spaces += " ";
        if (num < 10) spaces += " ";
        System.out.print(num + ":" + spaces);
        printSpaces(indent);
        if (decNum != -1) {
            s += "           Dec: " + decNum;
        }
        String lab = t.getLabel();
        if ( lab.length() > 0 ) {
            s += "  Label: "+t.getLabel();
        }
        if (t.getClass() == IdTree.class) {
            int offset = ((IdTree)t).getFrameOffset();
            if (offset >= 0) {
                s += "  Addr: " + offset;
            }
        }
        System.out.println(s);
        indent += 2;
        visitKids(t);
        indent -= 2;
    }

    public Object visitProgramTree(AST t) { print("Program",t);  return null; }
    public Object visitBlockTree(AST t) { print("Block",t);  return null; }
    public Object visitFunctionDeclTree(AST t) { print("FunctionDecl",t);  return null; }
    public Object visitCallTree(AST t) { print("Call",t);  return null; }
    public Object visitDeclTree(AST t) { print("Decl",t);  return null; }
    public Object visitIntTypeTree(AST t) { print("IntType",t);  return null; }
    public Object visitBoolTypeTree(AST t) { print("BoolType",t);  return null; }
    public Object visitFormalsTree(AST t) { print("Formals",t);  return null; }
    public Object visitActualArgsTree(AST t) { print("ActualArgs",t);  return null; }
    public Object visitIfTree(AST t) { print("If",t);  return null; }
    public Object visitWhileTree(AST t) { print("While",t);  return null; }
    public Object visitReturnTree(AST t) { print("Return",t);  return null; }
    public Object visitAssignTree(AST t) { print("Assign",t);  return null; }
    public Object visitIntTree(AST t) { print("Int: "+((IntTree)t).getSymbol().toString(),t);  return null; }
    public Object visitIdTree(AST t) { print("Id: "+((IdTree)t).getSymbol().toString(),t);  return null; }
    public Object visitRelOpTree(AST t) { print("RelOp: "+((RelOpTree)t).getSymbol().toString(),t);  return null; }
    public Object visitAddOpTree(AST t) { print("AddOp: "+((AddOpTree)t).getSymbol().toString(),t);  return null; }
    public Object visitMultOpTree(AST t) { print("MultOp: "+((MultOpTree)t).getSymbol().toString(),t);  return null; }
    //new
    public Object visitNotOpTree(AST t){ print("NotOp: "+((NotOpTree)t).getSymbol().toString(),t);  return null; }
    public Object visitMinusOpTree(AST t){ print("NotOp: "+((MinusOpTree)t).getSymbol().toString(),t);  return null; }
    public Object visitDoTree(AST t) { print("do",t);  return null; }
    public Object visitForTree(AST t) { print("for",t); return null; }
    public Object visitFloatTree(AST t) { print("Float: "+((FloatTree)t).getSymbol().toString(),t);  return null; }
    public Object visitFloatTypeTree(AST t) { print("FloatType",t);  return null; }
    public Object visitCharTree(AST t) { print("Char: "+((CharTree)t).getSymbol().toString(),t);  return null; }
    public Object visitCharTypeTree(AST t) { print("CharType",t);  return null; }
    public Object visitStringTree(AST t) { print("String: "+((StringTree)t).getSymbol().toString(),t);  return null; }
    public Object visitStringTypeTree(AST t){ print("StringType",t); return null;}
    
    public Object visitPowerOpTree(AST t){ print("NotOp: "+((PowerOpTree)t).getSymbol().toString(),t);  return null; }
    public Object visitEheadTree(AST t){  print("Ehead",t); return null;  }
    public Object visitSciTree(AST t) { print("Sci: "+((SciTree)t).getSymbol().toString(),t);  return null; }
    public Object visitUnOpTree(AST t) { print("UnaryOp: "+((UnOpTree)t).getSymbol().toString(),t);  return null; }

}