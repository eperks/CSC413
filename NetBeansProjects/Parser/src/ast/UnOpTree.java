/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;
import lexer.Symbol;
import lexer.Token;
import visitor.*;

public class UnOpTree extends AST {
    private Symbol symbol;

/**
 *  @param tok contains the Symbol which indicates the specific relational operator
*/
    public UnOpTree(Token tok) {
        this.symbol = tok.getSymbol();
    }

    public Object accept(ASTVisitor v) {
        return v.visitUnOpTree(this);
    }

    public Symbol getSymbol() {
        return symbol;
    }

}
