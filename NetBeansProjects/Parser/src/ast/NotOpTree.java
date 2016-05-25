/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import lexer.Symbol;
import lexer.Token;
import visitor.*;

public class NotOpTree extends AST {
    private Symbol symbol;

    /**
     *  @param tok contains the Symbol that indicates the specific multiplying operator
     */
    public NotOpTree(Token tok) {
        this.symbol = tok.getSymbol();
    }

    public Object accept(ASTVisitor v) {
        return v.visitNotOpTree(this);
    }

    public Symbol getSymbol() {
        return symbol;
    }

}