package ast;

import visitor.*;

/**
 *
 * @author ellis
 */
public class FloatTypeTree extends AST {

    /**
     *
     */
    public FloatTypeTree() {
    }

    public Object accept(ASTVisitor v) {
        return v.visitFloatTypeTree(this);
    }
}