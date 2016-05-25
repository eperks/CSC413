/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;
import visitor.*;
/**
 *
 * @author ellis
 */
public class EheadTree extends AST {
    
    public EheadTree(){
    }
    
    public Object accept(ASTVisitor v){
        return v.visitEheadTree(this);
    }
    
}
