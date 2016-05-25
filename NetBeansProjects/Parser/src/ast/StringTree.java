/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;
import lexer.Symbol;
import lexer.Token;
import visitor.*;
/**
 *
 * @author ellis
 */
public class StringTree extends AST{
    private Symbol symbol;
    
    public StringTree(Token tok){
        this.symbol = tok.getSymbol();
    }
    public Object accept(ASTVisitor v){
        return v.visitStringTree(this);
    }
    
    public Symbol getSymbol(){
        return symbol;
    }
    
}
