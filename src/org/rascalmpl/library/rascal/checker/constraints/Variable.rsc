@bootstrapParser
module rascal::checker::constraints::Variable

import ParseTree;
import IO;
import rascal::types::Types;
import rascal::scoping::SymbolTable;
import rascal::checker::constraints::Constraints;
import rascal::checker::Annotations;
import rascal::checker::TreeUtils;
import rascal::syntax::RascalRascal;

//
// NOTE: Variable declarations are either part of the toplevel variable declaration or the
// local variable declaration statement, both of which are typed declarations. Untyped
// declarations, of the form name = expression, are actually assignment statements, with
// name an arbitrary assignable (tuples, fields, etc), and are handled in the statement
// logic, not here.
// 
public ConstraintBase gatherVariableConstraints(SymbolTable st, ConstraintBase cs, Variable v) {
    switch(v) {
        case (Variable) `<Name n>` : {
            <cs, t1> = makeFreshType(cs);
            cs.constraints = cs.constraints + TreeIsType(n,n@\loc,t1);
            if (n@\loc in st.itemUses)
                cs.constraints = cs.constraints + DefinedBy(t1,(st.itemUses)[n@\loc],n@\loc);
            return cs;
        }
        
        case (Variable) `<Name n> = <Expression e>` : {
            <cs, t1> = makeFreshType(cs);
            cs.constraints = cs.constraints + TreeIsType(n,n@\loc,t1);
            if (n@\loc in st.itemUses)
                cs.constraints = cs.constraints + DefinedBy(t1,(st.itemUses)[n@\loc],n@\loc);
            <cs, t2> = makeFreshType(cs);
            cs.constraints = cs.constraints + TreeIsType(e,e@\loc,t2) + Assignable(v,v@\loc,n,e,t2,t1);
            return cs;
        }
        
        default : throw "gatherVariableConstraints: unhandled case <v>";
    }
}
